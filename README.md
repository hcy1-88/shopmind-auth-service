# ShopMind 认证服务

ShopMind 认证服务提供用户认证、登录、注册等功能，基于 Spring Boot 构建，使用 JWT 进行令牌管理。

## 功能概览

### 1. 认证接口 (AuthorizationController)

| 接口 | 方法 | 描述 |
|------|------|------|
| `/authorization/send-sms-code` | POST | 发送短信验证码（用于登录或注册） |
| `/authorization/sms-login` | POST | 短信验证码登录/注册 |
| `/authorization/login` | POST | 密码登录 |
| `/authorization/set-password` | POST | 首次设置密码 |
| `/authorization/me` | POST | 获取当前登录用户信息 |

#### 接口详情

**发送短信验证码**
```
POST /authorization/send-sms-code
```
请求参数：
```json
{
  "phoneNumber": "13800138000"
}
```
响应：
```json
{
  "code": 200,
  "data": {
    "token": "短信验证令牌"
  }
}
```

**短信验证码登录/注册**
```
POST /authorization/sms-login
```
请求参数：
```json
{
  "phoneNumber": "13800138000",
  "code": "123456",
  "token": "短信验证令牌"
}
```

**密码登录**
```
POST /authorization/login
```
请求参数：
```json
{
  "phoneNumber": "13800138000",
  "password": "password123"
}
```

**设置密码**
```
POST /authorization/set-password
```
请求参数：
```json
{
  "phoneNumber": "13800138000",
  "code": "123456",
  "token": "短信验证令牌",
  "password": "newPassword123",
  "confirmPassword": "newPassword123"
}
```

**获取当前用户信息**
```
POST /authorization/me
```
请求头：
```
Authorization: Bearer {access_token}
```

---

### 2. 验证码接口 (CaptchaController)

| 接口 | 方法 | 描述 |
|------|------|------|
| `/captcha` | POST | 获取验证码图片 |
| `/captcha/verify` | POST | 验证验证码 |

#### 接口详情

**获取验证码**
```
POST /captcha
```
响应：
```json
{
  "code": 200,
  "data": {
    "imageKey": "图片key",
    "imageData": "base64图片数据"
  }
}
```

**图片验证码**
```
POST /captcha/verify
```
请求参数：
```json
{
  "imageKey": "图片key",
  "blockX": 100
}
```
响应：
```json
{
  "code": 200,
  "data": true
}
```

---

### 3. JWKS 公钥接口 (JwksController)

| 接口 | 方法 | 描述 |
|------|------|------|
| `/.well-known/jwks.json` | GET | 获取 JWT 公钥（符合 RFC 7517 标准） |

#### 接口详情

**获取 JWKS 公钥**
```
GET /.well-known/jwks.json
```
供其他微服务调用，用于验证 JWT 令牌的签名。

---

## 技术栈

- **Spring Boot** - 应用框架
- **Spring Cloud** - 微服务框架
- **JWT (JWS)** - 令牌认证
- **JWKS (JSON Web Key Set)** - 公钥分发（RFC 7517）
- **Nimbus JOSE + JWT** - JWT 处理库
- **Lombok** - 简化代码

---

## 项目结构

```
src/main/java/com/shopmind/authcore/
├── controller/          # 控制器层
│   ├── AuthorizationController.java
│   ├── CaptchaController.java
│   └── JwksController.java
├── dto/                 # 数据传输对象
│   ├── request/        # 请求 DTO
│   └── response/       # 响应 DTO
├── service/            # 服务层
├── exception/          # 异常处理
├── utils/              # 工具类
└── config/             # 配置类
```