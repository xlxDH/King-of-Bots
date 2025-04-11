# King of Bots

### **网页游戏-基于贪吃蛇的回合制双人对战游戏**

> *技术栈：Spring Boot , Vue3 , Spring Cloud , WebSocket , PostgreSQL , MyBatis-Plus , Java多线程，锁机制*

* **项目描述：** 一款基于$Spring Boot$和$Vue 3$的回合制双人对战游戏，玩家可以通过键盘(WASD) 或者$Bot$代码控制蛇的移动，在规定时间内进行对战，游戏支持匹配系统与实时对战
* **项目功能：** 登录注册，匹配系统，实时对战，人机对战，对局回放，对局记录，排行榜，添加和删除$Bot$代码
* **项目细节：** 
  * 登录验证采用$JWT$身份验证，用户登录成功会返回一个$token$，$token$会存入浏览器的$Local Storage$中，有效期内无需重新登录
  * 项目使用spring cloud框架，实现了三个核心微服务：*主服务、匹配服务和Bot代码执行服务*
  * 匹配功能由匹配服务实现，参与匹配的玩家会被加入到匹配服务中的 *MatchingPool* 统一维护，匹配成功后会为每个对局开一个线程来维护当前对局，这里采用继承$Thread$类的方式实现多线程，对战时前后端采用$WebSocket$通信以确保对局的实时性
  * 人机对战是通过玩家添加$Bot$代码，后端编译并执行$Bot$代码返回结果控制蛇的移动来实现；$Bot$的代码执行使用了消息队列，消息队列使用$ReentrantLock$锁，$Java$多线程和线程的睡眠与唤醒，$while$循环，队列$queeu$手写实现；$Bot$代码的动态编译和执行使用的是$joor$库
  * 对局记录，玩家信息,$Bot$代码等信息都持久化到$PostgreSQL$数据库中，对数据库的$CRUD$操作采用$MyBatis-Plus$ 框架实现
  * 游戏的前端逻辑全部使用$js$实现，并用$js$ + $requestAnimationFrame$函数 + $canvas$​标签实现了一个简易的游戏引擎来实现游戏的动画渲染
* **项目部署:**                            				

*<center>@2025 Made by DH_xlx</center>*