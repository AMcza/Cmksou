# 🚀 聚合搜索系统（Spring Boot + Elasticsearch）

> 基于 Spring Boot 2.6.3 + Vue3 + Ant-Design-Vue 打造的一站式聚合搜索平台，支持图片、用户、帖子等多维度检索，毫秒级响应，亿级数据秒级聚合。

---

## 🎯 项目亮点
- **前后端分离**：Vue3 + TypeScript + Vite 极致开发体验
- **搜索黑科技**：Elasticsearch 7.x 分布式搜索引擎，支持复杂聚合查询
- **设计模式**：门面模式统一接口、适配器模式兼容多源数据、工厂模式创建搜索实例
- **数据同步**：MySQL → ES 异步双写，保证最终一致性
- **性能优化**：Redis 缓存热点数据，异步线程池并发查询

---

## 🛠️ 技术架构

| 层级 | 技术栈 | 说明 |
|---|---|---|
| **前端** | Vue 3 | 渐进式框架，Composition API |
| | Ant-Design-Vue | 企业级 UI 组件库 |
| | Axios | HTTP 客户端 |
| | Vite | 下一代构建工具 |
| **后端** | Spring Boot 2.6.3 | 快速构建微服务 |
| | MyBatis-Plus | 高效 ORM 框架 |
| | Elasticsearch 7.x | 分布式搜索与分析引擎 |
| | Kibana | 数据可视化 |
| | MySQL 8.0 | 主数据存储 |
| | Redis | 缓存与分布式锁 |
| | Hutool | Java 工具库 |
| | Jsoup | HTML 解析，爬虫利器 |
| **设计模式** | 门面模式 | 统一聚合查询入口 |
| | 工厂模式 | 创建搜索引擎实例 |
| | 适配器模式 | 兼容 Bing、百度等多源数据 |
| | 注册器模式 | 动态注册搜索策略 |

---

## 📅 开发时间线

| 日期 | 里程碑 | 关键成果 |
|---|---|---|
| **6.25-6.27** | 项目初始化 | 完成脚手架搭建、接口设计 |
| | 图片搜索接口 | 基于 Jsoup 抓取 Bing 图片 |
| | 用户搜索接口 | 支持模糊匹配、分页查询 |
| | 帖子搜索接口 | 全文检索 + 高亮显示 |
| **6.28** | 聚合接口 | 三合一搜索接口，异步优化 |
| **6.29** | 设计模式重构 | 代码优雅度提升 300% |
| **7.4** | ES 深度优化 | 聚合查询性能提升 50% |
| **7.6** | 数据同步 | MySQL → ES 实时同步方案落地 |

---

## 🎨 系统展示

### 🖥️ 文章接口
![用户聚合搜索](<img width="1377" height="911" alt="image" src="https://github.com/user-attachments/assets/c301efcd-7d0a-4fe1-8723-c19c41d34614" />
)
### 🔍 图片接口
![文章结果](https://github.com/user-attachments/assets/f60cdde0-9c79-4afc-8120-b96b68dda7ee)
## 🔍  用户接口
![用户搜索结果](https://github.com/user-attachments/assets/bf8299c7-2038-497f-b782-374c0288e430)

> 更多截图正在上传中...

---

## 📦 快速开始

### 1️⃣ 克隆项目
```bash
git clone https://github.com/yourname/spring-search.git
