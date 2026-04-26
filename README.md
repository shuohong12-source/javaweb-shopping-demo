# ShoppingDemo

基于 **Servlet + JSP + Filter + Session** 实现的简易购物系统示例项目。

本项目采用 MVC 思想进行组织：

- **Model**：商品类、购物车项类
- **View**：JSP 页面
- **Controller**：各类 Servlet
- **Filter**：登录验证过滤器

适合作为 Java Web 课程中的 Servlet / JSP / Session / Filter 综合练习项目。

---

## 一、项目功能

本系统实现了一个基础购物流程，主要功能如下：

1. 用户登录
2. 登录成功后才能访问商品页面
3. 展示商品列表（不少于 4 个商品）
4. 使用 `session` 保存购物车信息
5. 可将商品加入购物车
6. 可查看购物车中的商品信息
7. 可进行结算并显示商品总价
8. 使用过滤器拦截未登录用户访问受保护资源

---

## 二、项目技术点

本项目涉及以下 Java Web 知识点：

- Servlet
- JSP
- Session
- Filter
- MVC 模式
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
│     └─ web.xml
└─ out/
```

---

## 四、项目中各文件作用说明

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

### 2. Servlet 层

#### `LoginServlet.java`

负责处理登录请求。

功能：

- 获取用户名和密码
- 进行静态验证
- 登录成功后将用户名保存到 session
- 登录失败返回登录页并提示错误信息

默认登录账号：

```text
用户名：admin
密码：123456
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

### 3. Filter 层

#### `LoginFilter.java`

负责拦截未登录用户访问系统中的受保护资源。

放行资源一般包括：

- `login.jsp`
- `/login`
- 静态资源（如 css、js、images）

未登录访问其他资源时，自动跳转回登录页。

---

### 4. View 层

#### `login.jsp`

登录页面。

#### `product.jsp`

商品展示页面。

#### `cart.jsp`

购物车页面。

#### `checkout.jsp`

结算页面。



## 五、系统运行流程

### 1. 访问登录页

用户首先进入 `login.jsp` 页面，输入用户名和密码。

### 2. 登录验证

表单提交到 `LoginServlet`，进行静态验证：

- 若用户名为 `admin` 且密码为 `123456`，则登录成功
- 登录成功后将用户信息存入 `session`
- 然后跳转到商品列表页面

### 3. 浏览商品

`ProductServlet` 准备商品列表，转发到 `product.jsp` 页面显示商品。

### 4. 加入购物车

用户点击“加入购物车”按钮，请求发送到 `AddToCartServlet`。

该 Servlet 根据商品 id 查找商品信息，并将商品加入 session 中的购物车。

### 5. 查看购物车

用户可进入 `cart.jsp` 页面查看：

- 商品编号
- 商品名称
- 商品价格
- 商品数量
- 小计金额
- 总价

### 6. 结算

用户点击结算后，进入 `checkout.jsp` 页面，查看总价格。

### 7. 退出登录

用户点击退出后，session 被销毁，需要重新登录。

----

## 六、MVC 模式说明

本项目遵循 MVC 思想：

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

### Filter

负责统一权限验证：

- `LoginFilter`

-----

## 七、运行环境

建议运行环境如下：

- JDK 17 及以上
- Tomcat 10 及以上
- IntelliJ IDEA
- Servlet API：`jakarta.servlet`

> 注意：本项目使用的是 `jakarta.servlet.*` 包，因此更适合在 **Tomcat 10+** 环境下运行。



## 八、部署与运行方法

### 方法一：使用 IDEA 运行

1. 使用 IntelliJ IDEA 打开项目
2. 配置 Tomcat 服务器
3. 将项目添加到 Tomcat Deployment
4. 启动 Tomcat
5. 在浏览器访问项目首页

例如：

```text
http://localhost:8080/项目名/login.jsp
```

-----

## 九、实验总结

通过本项目，可以掌握 Servlet、JSP、Session 和 Filter 的基本使用方法，并理解 MVC 模式在 Java Web 开发中的应用。系统虽然较简单，但已经实现了一个完整的登录、购物、结算流程，对学习 Java Web 基础开发具有较好的练习价值。

