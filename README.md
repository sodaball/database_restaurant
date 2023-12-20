### 基于JAVA和mysql实现的学校餐厅订餐数据库

#### 结构：

```
code
│
└── lib
	├── hutool-all-5.8.10.jar
	└── mysql-connector-j-8.0.31.jar
├── out/production/untitled
	├── service
		├── GoodsService.class
		├── OrderService.class
		├── RestService.class
		└── StuService.class
	└── view
		├── LoginFrame.class
		├── LoginFrame$1.class
		├── LoginFrame$2.class
		└── MainFrame.class
└── src
	├── service
		├── GoodsService.java
		├── OrderService.java
		├── RestService.java
		└── StuService.java
	└── view
		├── AddGoodsFrame.java
		├── LoginFrame.java
		├── MainFrame.java
		└── UpdateGoodesFrame.java

```

`src`和`out/production/untitled`都包含`service`和`view`两个包



#### 功能：

* 学生充值、订餐
* 餐厅经历管理库存



#### 使用：

1. mysql配置

   运行`./data/db_order.sql`或者再MySQL WorkBench中导入`./data/db_order.sql`，创建所需的databse和table

2. jdk配置

   配置java

3. 运行

   `code/src/`中是java文件，编译之后在`code/out/production/untitled`中生成编译后的.class文件，运行即可

   用vscode打开直接`run`即可



