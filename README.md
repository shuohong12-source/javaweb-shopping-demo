# ShoppingDemo

基于 **Servlet + JSP + Filter + Session + JDBC + MySQL** 实现的简易购物系统示例项目。

本项目采用 MVC 思想进行组织：

- **Model**：商品类、购物车项类
- **View**：JSP 页面
- **Controller**：各类 Servlet
- **DAO**：数据库访问层，用于用户登录验证
- **Util**：数据库连接工具类
- **Filter**：登录验证过滤器

适合作为 Java Web 课程中的 Servlet / JSP / Session / Filter / JDBC 综合练习项目。

---

## 一、项目功能

本系统实现了一个基础购物流程，主要功能如下：

1. 用户登录
2. 用户名和密码到 MySQL 数据库中进行验证
3. 登录成功后才能访问商品页面
4. 展示商品列表（不少于 4 个商品）
5. 使用 `session` 保存购物车信息
6. 可将商品加入购物车
7. 可查看购物车中的商品信息
8. 可进行结算并显示商品总价
9. 使用过滤器拦截未登录用户访问受保护资源
10. 用户可退出登录并销毁 session

---

## 二、项目技术点

本项目涉及以下 Java Web 知识点：

- Servlet
- JSP
- Session
- Filter
- MVC 模式
- JDBC 数据库连接
- MySQL 数据库验证
- DAO 数据访问层
- 请求转发与重定向
- Tomcat 部署运行

---

## 三、项目目录结构

```text
shopingDemo/
├─ src/
│  └─ com/xxxxx/
│     ├─ model/
│     │  ├─ Product.java
│     │  └─ CartItem.java
│     ├─ dao/
│     │  └─ UserDao.java
│     ├─ util/
│     │  └─ DBUtil.java
│     ├─ servlet/
│     │  ├─ LoginServlet.java
│     │  ├─ ProductServlet.java
│     │  ├─ AddToCartServlet.java
│     │  ├─ CartServlet.java
│     │  ├─ CheckoutServlet.java
│     │  └─ LogoutServlet.java
│     └─ filter/
│        └─ LoginFilter.java
├─ web/
│  ├─ login.jsp
│  ├─ product.jsp
│  ├─ cart.jsp
│  ├─ checkout.jsp
│  └─ WEB-INF/
│     ├─ lib/
│     │  └─ mysql-connector-j-8.x.x.jar
│     └─ web.xml
└─ sql/
   └─ init_user.sql
```

---

## 四、数据库设计

本项目使用 MySQL 数据库保存用户账号信息，登录时通过数据库验证用户名和密码。

### 1. 创建数据库

```sql
CREATE DATABASE IF NOT EXISTS shopping_demo DEFAULT CHARACTER SET utf8mb4;
```

### 2. 使用数据库

```sql
USE shopping_demo;
```

### 3. 创建用户表

```sql
CREATE TABLE IF NOT EXISTS user (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(50) NOT NULL
);
```

### 4. 插入测试账号

```sql
INSERT INTO user(username, password) VALUES ('admin', '123456');
INSERT INTO user(username, password) VALUES ('zhangsan', '123456');
```

默认测试账号：

```text
用户名：admin
密码：123456
```

---

## 五、数据库连接配置

数据库连接工具类为：

```text
src/com/xxxxx/util/DBUtil.java
```

核心配置如下：

```java
private static final String URL =
        "jdbc:mysql://localhost:3306/shopping_demo?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai";

private static final String USER = "root";

private static final String PASSWORD = "123456";
```

其中：

- `shopping_demo`：数据库名称
- `root`：MySQL 用户名
- `123456`：MySQL 密码

如果本机 MySQL 的账号或密码不同，需要修改 `DBUtil.java` 中的配置。

---

## 六、MySQL 驱动包说明

由于项目使用 JDBC 连接 MySQL，因此需要导入 MySQL 驱动包：

```text
mysql-connector-j-8.x.x.jar
```

可以从 MySQL 官网下载：

```text
https://dev.mysql.com/downloads/connector/j/
```

下载时选择：

```text
Platform Independent
```

解压后找到 `.jar` 文件，例如：

```text
mysql-connector-j-8.0.33.jar
```

然后放到项目目录：

```text
web/WEB-INF/lib/
```

最终结构类似：

```text
web/
└─ WEB-INF/
   ├─ lib/
   │  └─ mysql-connector-j-8.0.33.jar
   └─ web.xml
```

如果没有导入该驱动包，运行时可能会出现：

```text
ClassNotFoundException: com.mysql.cj.jdbc.Driver
```

---

## 七、项目中各文件作用说明

### 1. Model 层

#### `Product.java`

用于封装商品信息，包括：

- 商品 ID
- 商品名称
- 商品价格

#### `CartItem.java`

用于封装购物车中的每一项商品，包括：

- 商品对象 `product`
- 商品数量 `quantity`
- 小计金额计算

---

### 2. DAO 层

#### `UserDao.java`

负责访问数据库中的用户表，判断用户名和密码是否正确。

核心 SQL 语句：

```sql
SELECT id FROM user WHERE username = ? AND password = ?
```

这里使用 `PreparedStatement` 设置参数，可以避免直接拼接 SQL 字符串。

---

### 3. Util 工具层

#### `DBUtil.java`

负责加载 MySQL 驱动，并创建数据库连接。

主要作用：

- 加载数据库驱动 `com.mysql.cj.jdbc.Driver`
- 连接 MySQL 数据库
- 为 DAO 层提供 `Connection` 对象

---

### 4. Servlet 层

#### `LoginServlet.java`

负责处理登录请求。

功能：

- 获取表单提交的用户名和密码
- 调用 `UserDao` 到数据库中验证账号
- 登录成功后将用户名保存到 `session`
- 登录成功后跳转到商品页面
- 登录失败后返回登录页并显示错误信息

原来的登录方式是静态验证：

```java
if ("admin".equals(username) && "123456".equals(password)) {
```

现在修改为数据库验证：

```java
if (userDao.checkLogin(username, password)) {
```

#### `ProductServlet.java`

负责准备商品列表并跳转到商品展示页面。

#### `AddToCartServlet.java`

负责根据商品 id 将商品加入购物车，并把购物车保存到 session 中。

#### `CartServlet.java`

负责读取 session 中的购物车数据，并展示购物车内容和总价。

#### `CheckoutServlet.java`

负责显示结算信息，包括商品详情和总金额。

#### `LogoutServlet.java`

负责退出登录，销毁 session。

---

### 5. Filter 层

#### `LoginFilter.java`

负责拦截未登录用户访问系统中的受保护资源。

放行资源一般包括：

- `login.jsp`
- `/login`
- 静态资源，如 css、js、images

未登录访问其他资源时，自动跳转回登录页。

---

### 6. View 层

#### `login.jsp`

登录页面，用户输入用户名和密码。

表单提交地址为：

```html
<form action="login" method="post">
```

这里的 `login` 对应 `LoginServlet` 中的：

```java
@WebServlet("/login")
```

#### `product.jsp`

商品展示页面。

#### `cart.jsp`

购物车页面。

#### `checkout.jsp`

结算页面。

---

## 八、系统运行流程

### 1. 访问登录页

用户首先进入 `login.jsp` 页面，输入用户名和密码。

### 2. 提交登录表单

登录表单提交到：

```text
/login
```

然后由 `LoginServlet` 处理请求。

### 3. 数据库验证

`LoginServlet` 获取用户名和密码后，调用：

```java
userDao.checkLogin(username, password)
```

然后 `UserDao` 执行 SQL 查询：

```sql
SELECT id FROM user WHERE username = ? AND password = ?
```

如果数据库中存在对应用户，则登录成功；否则登录失败。

### 4. 登录成功

登录成功后，将用户名保存到 session 中：

```java
session.setAttribute("user", username);
```

然后跳转到商品页面。

### 5. 登录失败

登录失败后，将错误信息保存到 request 中：

```java
req.setAttribute("msg", "用户名或者密码错误！");
```

然后转发回登录页面：

```java
req.getRequestDispatcher("login.jsp").forward(req, resp);
```

### 6. 浏览商品

`ProductServlet` 准备商品列表，转发到 `product.jsp` 页面显示商品。

### 7. 加入购物车

用户点击“加入购物车”按钮，请求发送到 `AddToCartServlet`。

该 Servlet 根据商品 id 查找商品信息，并将商品加入 session 中的购物车。

### 8. 查看购物车

用户可进入 `cart.jsp` 页面查看：

- 商品编号
- 商品名称
- 商品价格
- 商品数量
- 小计金额
- 总价

### 9. 结算

用户点击结算后，进入 `checkout.jsp` 页面，查看总价格。

### 10. 退出登录

用户点击退出后，session 被销毁，需要重新登录。

---

## 九、MVC 模式说明

本项目遵循 MVC 思想。

### Model

负责数据封装：

- `Product`
- `CartItem`

### View

负责页面展示：

- `login.jsp`
- `product.jsp`
- `cart.jsp`
- `checkout.jsp`

### Controller

负责业务逻辑与请求分发：

- `LoginServlet`
- `ProductServlet`
- `AddToCartServlet`
- `CartServlet`
- `CheckoutServlet`
- `LogoutServlet`

### DAO

负责数据库访问：

- `UserDao`

### Util

负责工具支持：

- `DBUtil`

### Filter

负责统一权限验证：

- `LoginFilter`

---

## 十、运行环境

建议运行环境如下：

- JDK 17 及以上
- Tomcat 10 及以上
- IntelliJ IDEA
- MySQL 8.0 及以上
- MySQL Connector/J 驱动包
- Servlet API：`jakarta.servlet`

> 注意：本项目使用的是 `jakarta.servlet.*` 包，因此更适合在 **Tomcat 10+** 环境下运行。

---

## 十一、部署与运行方法

### 1. 创建数据库和用户表

在 MySQL 中执行数据库初始化 SQL：

```sql
CREATE DATABASE IF NOT EXISTS shopping_demo DEFAULT CHARACTER SET utf8mb4;

USE shopping_demo;

CREATE TABLE IF NOT EXISTS user (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(50) NOT NULL
);

INSERT INTO user(username, password) VALUES ('admin', '123456');
INSERT INTO user(username, password) VALUES ('zhangsan', '123456');
```

### 2. 修改数据库连接信息

打开：

```text
src/com/xxxxx/util/DBUtil.java
```

根据自己的 MySQL 环境修改：

```java
private static final String USER = "root";
private static final String PASSWORD = "123456";
```

### 3. 导入 MySQL 驱动包

将：

```text
mysql-connector-j-8.x.x.jar
```

放入：

```text
web/WEB-INF/lib/
```

### 4. 使用 IDEA 运行

1. 使用 IntelliJ IDEA 打开项目
2. 配置 Tomcat 服务器
3. 将项目添加到 Tomcat Deployment
4. 启动 Tomcat
5. 在浏览器访问登录页面

例如：

```text
http://localhost:8080/shopingDemo/login.jsp
```

如果你的项目访问路径不同，需要根据 Tomcat 配置进行调整。

---

## 十二、常见问题

### 1. 页面提交后没有进入 Servlet

检查 `login.jsp` 表单是否为：

```html
<form action="login" method="post">
```

不能写成：

```html
<form action="login.jsp" method="post">
```

否则表单只会提交给 JSP 页面，不会进入 `LoginServlet`。

### 2. 找不到 MySQL 驱动

如果出现：

```text
ClassNotFoundException: com.mysql.cj.jdbc.Driver
```

说明没有正确导入 MySQL 驱动包，需要把 `mysql-connector-j-8.x.x.jar` 放到：

```text
web/WEB-INF/lib/
```

### 3. 数据库连接失败

如果出现数据库连接错误，需要检查：

- MySQL 是否已经启动
- 数据库名是否为 `shopping_demo`
- 用户名是否正确
- 密码是否正确
- `DBUtil.java` 中的连接地址是否正确

### 4. 登录一直失败

可以先在 MySQL 中执行：

```sql
SELECT * FROM user;
```

确认表中是否存在：

```text
admin    123456
```

---

## 十三、实验总结

通过本项目，可以掌握 Servlet、JSP、Session、Filter、JDBC 和 MySQL 的基本使用方法，并理解 MVC 模式在 Java Web 开发中的应用。系统由原来的静态账号验证升级为数据库验证，登录功能更加接近真实 Web 项目的开发流程。虽然系统整体较简单，但已经实现了一个完整的登录、商品浏览、购物车、结算和退出流程，对学习 Java Web 基础开发具有较好的练习价值。
