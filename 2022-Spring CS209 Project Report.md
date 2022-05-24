# 			    				**2022-Spring CS209 Project Report**

## 一、项目主题

### 主题：对当前主流开发框架Spring Boot + MyBatis的数据挖掘、分析和可视化

### 研究原因：

​		Spring Boot是近年最火热的Java Web开发框架，老师在上课时也介绍了很多相关的知识。MyBatis而往往在开发spring项目中作为辅助开发框架存在。本次project我们同时使用了Spring Boot和MyBatis的框架进行完成。出于好奇心理和求知心理，我们在这次project中想要通过爬取Spring Boot和MyBatis相关信息来更深入地学习了解这两个框架。

### 分析结果：

​		通过对Spring Boot和MyBatis二者在GitHub上的commit数量、issue数量、issue主题和Stack Overflow上的问题的搜集和分析，我们发现Spring Boot的GitHub仓库的commit数量从2014年到2018年在逐年增长随后一直保持着每年接近5000的数量（其中2020年的commit数高达5738），整个框架仍在快速发展。MyBatis作为一款体现ORM思想的框架，主要负责对数据库的事务管理，数据显示在2019年和2020年MyBatis的commit数量较高，发展较快，2021年开始commit数量减少，可见MyBatis发展逐渐成熟。Spring Boot和MyBatis二者配合使用受到开发者的广泛欢迎。

​		同时，我们发现springboot框架集成了六个子框架，分别为：Dependency Injection、Data Access 年Framework、Spring MVC、Transaction Management、Spring Web Services、JDBC abstraction layer。相比于同样使用了Dependency Injection的Guice框架，在Dependency Injection方面，springboot更加简便高效；在其他方面springboot还支持更多框架的特性，因此二者使用热度截然不同：springboot越来越受欢迎，Guice逐渐淡出人们的视野（Guice近四年的提问数和commit数均不足100条）。相比于spring下属的其他分支，springboot较好的整合了其他分支的优势，方便开发者们更快的部署spring项目，因此在众多分支中脱颖而出（springboot的star数远高于其他spring分支的star数，大约是它们的10倍）。

## 二、数据爬虫

我们的数据来源主要来自两个部分：**GitHub**和**Stack Overflow**。

### GitHub上数据的爬取：

首先我们自定义一个工具类ReadHTML，在这个工具类中我们传入一个参数url并建立网页链接，为了避免文件读入中断，我们将得到的InputStream转为ByteArrayOutputStream，最后将ByteArrayOutputStream转为字符串进行输出，最后用Jsoup把输出的字符串转为Doument类型，方便进行HTML内容的提取。

```java
   public static String getURLSource(URL url) throws Exception    {
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(5 * 1000);
        InputStream inStream =  conn.getInputStream();
        byte[] data = readInputStream(inStream);
        return new String(data);
    }

    public static byte[] readInputStream(InputStream instream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[]  buffer = new byte[1204];
        int len = 0;
        while ((len = instream.read(buffer)) != -1){
            outStream.write(buffer,0,len);
        }
        instream.close();
        return outStream.toByteArray();
    }
```

接下来我们应用stream处理方式在得到的Document类型中进行数据提取。通过观察目标数据在HTML标签中的位置，我们用流处理的方式进行一步步筛选和映射，最终提取出目标信息并写入文本文件。

```java
Supplier<Stream<Element>> te = () -> d.getElementsByTag("div").stream().filter((r) -> r.attr("id").startsWith("issue"));
                Stream<Element> Atemp = te.get().flatMap((t) -> t.getElementsByTag("a").stream()
                        .filter((e) -> e.attr("id").startsWith("issue_")));
                List<String> context = Atemp.map(Element::text).toList();
                List<String> time = te.get().flatMap((t) -> t.getElementsByTag("relative-time").stream()).map(Element::text).toList();
                List<String> label = te.get().map((t) -> t.getElementsByTag("a").stream()
                        .filter((e) -> e.attr("id").startsWith("label-"))
                        .map(Element::text)
                        .reduce((a, b) -> {
                            System.out.println(a);
                            System.out.println(b);
                            return a +"\t"+ b;})
                        .orElse("null")
                ).toList();
```

### Stack Overflow上数据的爬取：

不同于GitHub，如果我们直接访问Stackoverflow的界面会进入人工检验界面如下所示，我们的脚本程序无法点击人工检验窗口，因此无法正常访问Stackoverflow。

![image-20220524192435953](https://s2.loli.net/2022/05/24/dNGJtfK57gA4YHl.png)

所以我们采用Stackoveflow官方api进行数据爬取。与上面的爬取不同的是，Stackoverflow传出的inputstream需要用GZIPInputstream解压，随后我们把解压的数据拼成字符串并转为json类型，通过对Json数据的访问，我们可以提取到指定信息并写入文本文件。

```java
String str = "https://api.stackexchange.com/2.3/tags/Guice/faq?page="+q+"&pagesize=100&site=stackoverflow";
                URL url = new URL(str);
                HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();
                httpUrlConn.setDoInput(true);
                httpUrlConn.setRequestMethod("GET");
                BufferedReader br = new BufferedReader(new InputStreamReader(new GZIPInputStream(httpUrlConn.getInputStream()), StandardCharsets.UTF_8));
JSONObject json = JSONObject.fromObject(j);
                JSONArray json_array = JSONArray.fromObject(json.getString("items"));
```

<hr>

## 三、数据处理

### 数据持久化：

为了更好的实现数据持久化并方便后端数据与前端交互，我们将文本文件的内容写入数据库。在本项目中我们使用**postgresql**作为数据库语言。在项目中我们通过构建实体类保存数据内容、自定义接口、用xml文件管理sql语句并实现接口中函数的功能，我们将文本文件内容逐条插入数据库。

```java
while ((line = inline.readLine()) != null) {
                String[] content = line.split("\t");
                SpringBootQuestion sqb = new SpringBootQuestion();
                sqb.setQuestion(content[0]);
                sqb.setDate(Date.valueOf(content[1]));
                sqb.setViews(Integer.parseInt(content[2]));
                sqb.setAnswers(Integer.parseInt(content[3]));
                sqb.setHref(content[4]);
                springBootQuestions.add(sqb);
            }
            stmt = con.prepareStatement("insert into springboot_question (question, date, views, answers, href) values(?,?,?,?,?);");
            for (SpringBootQuestion sbq: springBootQuestions) {
                stmt.setString(1, sbq.getQuestion());
                stmt.setDate(2, sbq.getDate());
                stmt.setInt(3, sbq.getViews());
                stmt.setInt(4, sbq.getAnswers());
                stmt.setString(5, sbq.getHref());
                stmt.addBatch();
            }
            stmt.executeBatch();
			con.commit();
```

### natural language processing词频统计：

仅仅通过人眼观察大量的issue和question词条很难发现有价值的信息，因此我们采用stanford natural language processing 来帮助我们从数据中发现有价值的信息。导入stanford NLP相关库的import语句如下所示：

![image-20220524191914245](https://s2.loli.net/2022/05/24/oulNJ7ACOfBZr9H.png)

由于进行nlp处理非常消耗heap空间，处理较长String类型数据时会导致IDEA的heap内存不足，所以我们用nlp单独去处理每一条issue或question，最后我们统计所有名词出现的次数并将数据写入数据库。

```java
			Annotation document = new Annotation(text);
            pipeline.annotate(document);
            List<CoreMap> sentences = document.get(SentencesAnnotation.class);
            List<String> words = new ArrayList<>();
            List<String> posTags = new ArrayList<>();
            List<String> nerTags = new ArrayList<>();
            for (CoreMap sentence : sentences) {
                for (CoreLabel token : sentence.get(TokensAnnotation.class)) {
                    String word = token.get(TextAnnotation.class);
                    words.add(word);
                    String pos = token.get(PartOfSpeechAnnotation.class);
                    posTags.add(pos);
                    String ne = token.get(NamedEntityTagAnnotation.class);
                    nerTags.add(ne);
                }
            }
```

<hr>

## 四、项目框架

​		本次项目我们根据学习的Java Web方面的相关内容，使用了Spring Boot + MyBatis的框架完成了项目的后端开发。本次选择Spring Boot框架的主要原因为：Spring Boot无需配置XML文件，会尝试根据我们添加的jar依赖自动配置应用。且Spring Boot有嵌入式的Tomcat，无需部署war文件。在服务器启动SpringBoot项目时可以通过jar指令直接启动， 在开发IDE中启动时只需运行Application类的main方法即可。而本次我们的程序使用了Ajax和jQuery的技术连接了前端和后端，使用Spring Boot框架可以极大降低开发难度。在本次开发中我们使用了postgresql数据库用来存储数据，传统的JDBC连接数据库需要经历一系列的步骤，先要加载驱动，然后获取连接，获取预处理对象，然后通过预处理对象进行查询，得到结果集。查询结束后还需要关闭连接。这一系列的过程都需要我们自己手动去实现，包括对结果集的遍历封装对象，而MyBatis作为一个优秀的持久层框架，免除了几乎所有的 JDBC 代码以及设置参数和获取结果集的工作，可以通过让实体类的属性名与数据库中的列名一一对应，通过简单的 XML 或注解即可配置和映射原始类型、接口和 Java POJO（Plain Old Java Objects，普通老式 Java 对象）为数据库中的记录。

​		本次我们使用的Spring Boot框架分为了Pojo层、Mapper层、Service层、Controller层和View层。

------

### 1、Pojo层

​		Pojo用于存放了本项目所用的实体类，对应数据库中具体的表，具体所用的实体类如下所示：

![image-20220524102905260](https://s2.loli.net/2022/05/24/Ov7iZhy98HISPpl.png)

| Entity                 | Corresponding Database                        | Explanation                                                  |
| ---------------------- | --------------------------------------------- | ------------------------------------------------------------ |
| IssueWord              | issue_word                                    | 本实体类包含的参数为word和count。本实体类代表了NLP对Spring Boot的GitHub仓库的所有issue进行分词并去重后得到的结果每个单词对应的数量。word代表一个具体的单词，count代表了单词在issue中的数量。 |
| MyBatisIssue           | mybatis_issue                                 | 本实体类包含的参数为version，date，year和info。本实体类代表了从MyBatis的GitHub仓库爬取到的每个issue，version存储了该issue对应的MyBatis版本，date存储了该issue发布的具体时间，year存储了该issue发布的年份，info存储了该issue的具体信息。 |
| MyBatisQuesion         | mybatis_question                              | 本实体类包含的参数为question，date，views，answers和href。本实体类代表了从Stack Overflow上爬取到的所有带MyBatis的tag的问题。question存储了这个问题的文本，date存储了这个问题发布的时间，views存储了这个问题在Stack Overflow上的访问量，answers存储了这个问题在Stack Overflow上的回答数，href存储了该问题对应的在Stack Overflow上的访问链接。 |
| QuestionWord           | question_word                                 | 本实体类包含的参数为word和count。本实体类代表了NLP对Stack Overflow上所有带SpringBoot的tag的问题的文本进行分词并去重后得到的结果每个单词对应的数量。word代表一个具体的单词，count代表了单词在issue中的数量。 |
| SpringBootIssue        | NONE                                          | 本实体类包含的参数为version，publishDate，year，month和info。本实体类是用于将Spring Boot的GitHub仓库的open issue和closed issue导入到对应的数据库的工具类。version存储了该issue对应的Spring Boot的对应版本，publishDate存储了该issue发布的具体时间，year存储了该issue发布的年份，month存储了该issue发布的月份，info存储了该issue所包含的具体文本信息。 |
| SpringBootIssueVersion | springboot_open_issue springboot_closed_issue | 本实体类包含的参数为version，year，month和count。本实体类是用于存储Spring Boot每个版本有多少对应的closed issue或open issue。version存储了Spring Boot的具体版本，year存储了当前版本所在的年份，month存储了当前版本所在的月份，count存储了这个Spring Boot版本在当前存储时间的issue数量。 |
| SpringBootIteration    | springboot_iteration                          | 本实体类包含的参数为version，time，year，month和day。本实体类存储了Spring Boot对应的版本号的最后更新时间。version存储了当前的Spring Boot版本，time是代表时间的字符串，year代表了时间的年份，moth代表了时间的月份，day代表了时间的具体日期。 |
| SpringBootQuestion     | springboot_question                           | 本实体类包含的参数为question，date，views，answers和href。本实体类代表了从Stack Overflow上爬取到的所有带SpringBoot的tag的问题。question存储了这个问题的文本，date存储了这个问题发布的时间，views存储了这个问题在Stack Overflow上的访问量，answers存储了这个问题在Stack Overflow上的回答数，href存储了该问题对应的在Stack Overflow上的访问链接。 |

------

### 2、Mapper层

​		Mapper层，又称DAO层，是数据库CRUD（create, read, update, delete）的接口，只有方法名，具体实现在每个mapper层接口对应的.xml文件中，对数据库进行数据持久化操作。在本项目中表现为用对应的数据库存储爬取到的信息，并提供了增删改查的操作。

​		在本项目中主要有如下mapper接口：

![image-20220524110831627](https://s2.loli.net/2022/05/24/1TwdbmPIleqHEou.png)

​		每个mapper接口对应了如下的.xml文件：

![image-20220524110929133](https://s2.loli.net/2022/05/24/LXZDSlYkz5mRQeb.png)

------

### 3、Service层

​		在本项目中Service层分为了主要两方面：

​		1：Service接口，用来声明对应的方法，如下所示：

![image-20220524111633212](https://s2.loli.net/2022/05/24/HKLWuf467rvpB8R.png)

​		2、ServiceImpl类，实现了接口，在类的定义时有@Service的注释，是将mapper和service接口整合的文件，如下所示：

![image-20220524111656498](https://s2.loli.net/2022/05/24/BhVjsK5Gfp6TDWS.png)

​		Sevice主要存放业务逻辑处理，在本项目中基本都为数据库查询的操作，在每个ServiceImpl都导入了对应的mapper类，并通过mapper类来对数据库进行操作。以如下方法为例：

![image-20220524111842522](https://s2.loli.net/2022/05/24/V6LFzMlhBQoaDSg.png)

​		该方法是用于根据不同情况在springboot_question数据表中查询对应的question信息，springBootQuestionMapper是mapper对象，通过调用mapper类相应的方法得到了需要作为返回值的List<SpringBootQuestion>。

------

### 4、Controller层

​		Ctroller层是用于响应用户需求，决定用什么视图，需要准备什么数据来展示。Controller层负责前后端交互，接收前端请求，调用Service层，接收Service层返回的数据，最后返回具体的数据和页面到客户端。在类的定义时加上了RestController的和RequestMapping的注释。

​		项目中具体使用的controller类如下所示：

![image-20220524112851288](https://s2.loli.net/2022/05/24/VnCrEGIa4KR6yPd.png)

​		为了在前端更好地接收和分析数据，我们使用了一个泛型的JsonResult<E>类，该类有模拟作为一个json对象，有状态码state信息、message作为可能的报错信息、data存储了json中传到前端的数据。

------

### 5、View层

​		View层是视图层，根据接到的数据展示界面给用户。所有前端相关的文件均存储于src/main/resources/static文件目录中作为静态资源。具体的文件目录结构如下所示：

![image-20220524114534794](https://s2.loli.net/2022/05/24/u49eGBXwSI3QRqL.png)

​		bootstrap3文件夹存储了一些网页使用到的框架结构代码，css文件夹存储了css层叠样式表的代码文件用于美化、定义网页的样式和调整HTML元素，fonts文件夹存储了部分从IcoMoon下载下的icon，img文件夹存储了前端网页中使用的一些图片，js文件夹存储了项目中使用到的JavaScript脚本语言，用于控制网页的行为，web文件夹存储了具体的HTML前端页面代码。

​		前端使用的HTML界面中的折线图、柱状图和饼图等主要使用了开源的echart工具包进行制作，动画效果使用了css的animate动画样式进行制作，线条和部分背景使用了canvas画布进行制作。

​		前端界面主要通过了jQuery AJAX技术用于读取后端数据在前端进行展示。AJAX（异步 JavaScript 和 XML，Asynchronous JavaScript and XML）是与服务器交换数据的技术，它可以在不重载全部页面的情况下，实现对部分网页的更新，故而我们能够使用 HTTP Get 和 HTTP Post 从远程服务器上请求文本、HTML、XML 或 JSON ， 同时把这些外部数据直接载入网页的被选元素中。以查找Stack Overflow上的question为例：

![image-20220524140936824](C:/Users/OS/AppData/Roaming/Typora/typora-user-images/image-20220524140936824.png)

​		在本函数中，我们使用ajax技术获取到了controller对应的RequestMapping注解中的地址，以POST从远端服务器请求文本，传给后端服务器的数据为通过id获取到的HTML元素form中的提交信息，dataType为json。将数据传到后端后controller会调用service中的相应方法如下所示：

![image-20220524153334332](https://s2.loli.net/2022/05/24/6dCqoRHUF84Ltfz.png)

​		在方法内iSpringBootService是ISpringBootService的一个实例对象，其根据方法中传入的参数（前端表单的提交）调用相应的方法，根据list的数据返回一个220状态码或正常的SpringBootQuestion的list里的数据。在前端得到后端传来的JsonResult后，可以根据JsonResult里的数据，先根据class获取HTML元素并调用empty方法清空内部的HTML元素，再循环遍历JsonResult里的数据构造对应的HTML元素并用append方法添加入获取到的HTML元素。

------

​		总结Spring Boot的五层架构如下（引用自CSDN）：

![img](https://s2.loli.net/2022/05/24/k5YuKHAfx4h9vGR.jpg)



## 五、前端展示

​		前端的展示图表主要分为以下三方面：开始界面模块、Spring Boot模块和MyBatis模块。

### 1、开始界面模块

​		首先我们实现了一个简单的登录和注册界面如下所示：

![image-20220524161720079](https://s2.loli.net/2022/05/24/c14wJz7qV3ZAOdb.png)

​		这个简单的用户登录界面可以允许用户先在注册页面注册对应的账号并以账号登录。

​		在成功登录后账户后页面会挑战到一个卡片页面选项如下所示：

![image-20220524161856288](https://s2.loli.net/2022/05/24/MpzuQdFeXqYNsfV.png)

![image-20220524162043598](https://s2.loli.net/2022/05/24/aUT76pnFmSCPHxl.png)

​		在本次项目我们研究了Spring Boot和MyBatis两种框架的数据，当鼠标未聚焦于卡片上时，卡片会展示为正常的icon图片；当鼠标悬浮在对应的卡片上时，会显示对于Spring Boot和MyBatis的相关介绍。点击悬浮时的Details按钮，可以进入到Spring Boot和MyBatis相应的菜单界面。

<hr>

### 2、Spring Boot模块

​		在卡片页面点击Spring Boot的details可以进入到Spring Boot的菜单页面：

![image-20220524163814388](https://s2.loli.net/2022/05/24/GbzANKgE2IOY1fV.png)

​		对每个子模块，通过单击界面上的按钮即可进入到相关模块部分，在Spring Boot模块，我们主要实现了八个子模块：**GITHUB ISSUE NLP，SPRING BOOT VERSION，GITHUB OPEN ISSUE，STACK OVERFLOW QUESTION NLP，GITHUB CLOSED ISSUE，STACK OVEFLOW QUESTION，GITHUB COMMIT，SPRING SUB-FRAMEWORK**。

<hr>

**GITHUB ISSUE NLP：**

​		在这部分，我们使用NLP分词技术分析了爬取到的Spring Boot的GitHub仓库里的issue，根据分词的结果分析了每个词在issue中的出现次数，并根据出现的次数的不同生成了词云图如下所示：

![image-20220524165053477](C:/Users/OS/AppData/Roaming/Typora/typora-user-images/image-20220524165053477.png)

**SPRING BOOT VERSION：**

​		在这部分我们根据SpringBoot版本的最后更新时间生成了一个自动滚动的表，当鼠标悬浮在版本数据上时表会停止滚动，生成的表如下所示：

![image-20220524165323679](https://s2.loli.net/2022/05/24/EgGJZVzie6mUWdN.png)

**GITHUB OPEN ISSUE：**

​		在这部分我们对Spring Boot的GitHub仓库上爬取到的open issue进行时间上的处理，处理了每个Spring Boot版本在对应时间线上的open issue数目，并以可以浮动的条形柱状图作为展示，生成的柱状图如下所示：

![image-20220524170535654](https://s2.loli.net/2022/05/24/6Jg978Cy1EVuQXM.png)

**STACK OVERFLOW QUESTION NLP：**

​		在这部分，我们使用NLP分词技术分析了爬取到的Stack overflow上带SpringBoot的tag的question文本，根据分词的结果分析了每个词在question中的出现次数，并根据出现的次数的不同生成了词云图如下所示：

![image-20220524171204624](https://s2.loli.net/2022/05/24/29PQ6nZLgAacYJO.png)

​		为深入分析，从上述的词云图可以观测到分词出的Spring在Stack Overflow上的question数是最多的。我们已知Spring里有多种project项目，而Spring Boot即为其中的佼佼者，而其他project和Spring Boot相比或各有千秋或有着较大差距，故而我们再统计了其他spring-project对应的GitHub仓库的stars数量，来和Spring Boot进行一定的比较，结果所得柱状图如下所示：

![img](https://s2.loli.net/2022/05/24/7PGIpaM5Tnx3R9e.jpg)

**GITHUB CLOSED ISSUE：**

​		在这部分我们对Spring Boot的GitHub仓库上爬取到的closed issue进行时间上的处理，处理了每个Spring Boot版本在对应时间线上的open issue数目，并以可以浮动的条形柱状图作为展示，生成的柱状图如下所示：

![image-20220524193931338](https://s2.loli.net/2022/05/24/xRohEb28r4dS1Di.png)

**STACK OVEFLOW QUESTION：**

​		在这部分我们根据我们爬虫得到的Stack Overflow上带SpringBoot的tag的问题信息，制作出了一个可供查询带SpringBoot的tag的question相关信息的表格如下所示：

![image-20220524203131383](https://s2.loli.net/2022/05/24/xw8Qtieojfgs5ZD.png)

​		在此页面，有三个可选参数，分别是sorting method，page和key。

​		本界面共有七种可供选择的搜索方法： sort by time asc，sort by time desc，sort by view number asc，sort by view number desc，sort by answer number asc，sort by answer number desc，分别对应了根据数据库的存储数据顺序搜索、根据时间升序排序搜索、根据时间降序排序搜索、根据访问量升序排序搜索、根据访问量降序排序搜索、根据回答数升序排序搜索、根据回答数降序排序搜索。

![image-20220524203435604](https://s2.loli.net/2022/05/24/5hVywSbqZjaTJOd.png)

​		page代表了当前搜索的页码数，因一页只能最多显示5条数据，若想访问下5条数据，需更改page的值，page的值依靠HTML元素的placeholder参数设置默认值为1。

​		key表示关键词搜索，该简单的搜索引擎可以不区别大小写全字匹配包含输入的key值的所有question数，该方法的实现基于postgresql的like语句，后端将依据如下语句进行查询（摘录自*SpringBootQuestionMapper.xml*）：

```plsql
SELECT *
FROM springboot_question
WHERE upper(question) LIKE concat('%', #{key}, '%')
ORDER BY date DESC
LIMIT 5 OFFSET #{offset};
```

​		其中question代表每个Stack Overflow的问题对应的文本在数据库中的变量，date代表每个Stack Overflow的问题对应提出的时间，此处以时间降序排序，springboot_question是数据表，key是输入的key值，offset为基于page数计算所得。当根据时间降序排序搜索key值为json的数据且page为2时，返回的界面结果如下所示：

![image-20220524205013377](C:/Users/OS/AppData/Roaming/Typora/typora-user-images/image-20220524205013377.png)

**GITHUB COMMIT：**

​		在本部分我们爬取了Spring Boot框架的GitHub仓库每年的commit的数量，并根据每年的commit数将结果绘制成柱状图如下所示：

![image-20220524205457554](https://s2.loli.net/2022/05/24/nlFeouCWOExXVvy.png)

**SPRING SUB-FRAMEWORK：**

​		为将Spring Boot框架和其他框架做对比，我们学习得知SpringBoot框架集成了六个子框架，分别为：Dependency Injection、Data Access Framework、Spring MVC、Transaction Management、Spring Web Services、JDBC abstraction layer。我们统计了在GitHub上能搜索到的分别使用这六个子框架的repository的数量，将结果制作成了饼图如下所示：

![image-20220524210514378](https://s2.loli.net/2022/05/24/4zOqmETvPky6GWl.png)

​		为深入分析，我们搜索到了GitHub上同样运用了Dependency Injection的框架guice，我们同样去爬取了每年在Stack Overflow上的带guice的tag的question数量、每年该框架GitHub Repository上的commit数量和该框架在GitHub仓库中的star数量，绘制成折线图如下所示：

![image-20220524210809641](https://s2.loli.net/2022/05/24/PNHzT9K1r65g4GU.png)

​		为深入分析，我们比较了每年在Stack Overflow上与JSP和Spring MVC相关的question数，将结果绘制成折线图如下所示：

![image-20220524211135907](https://s2.loli.net/2022/05/24/mHQwWlzKx8CX9ct.png)

<hr>

### 3、MyBatis模块

​		在卡片页面点击MyBatis的details可以进入到MyBatis的菜单页面：

![image-20220524211607368](https://s2.loli.net/2022/05/24/V2adIBy1MHxpCPA.png)

​		对每个子模块，通过单击界面上的按钮即可进入到相关模块部分，在MyBatis模块，我们主要实现了三个子模块：**GITHUB COMMIT，GITHUB ISSUE，STACKOVERFLOW QUESTION。**

<hr>

**GITHUB COMMIT：**

​		在本部分我们爬取了MyBatis框架的GitHub仓库每年的commit的数量，并根据每年的commit数将结果绘制成柱状图如下所示：

![image-20220524211819396](https://s2.loli.net/2022/05/24/OaBRWu51jdHXimI.png)

**GITHUB ISSUE：**

​		在本部分我们根据MyBatis的GitHub仓库每年的issue的数量，绘制成饼图如下所示：

![image-20220524212636862](https://s2.loli.net/2022/05/24/RG3W4vOJs8USCao.png)

**STACKOVERFLOW QUESTION：**

​		在这部分我们根据我们爬虫得到的Stack Overflow上带MyBatis的tag的问题信息，制作出了一个可供查询带MyBatis的tag的question相关信息的表格如下所示：

![image-20220524211946110](https://s2.loli.net/2022/05/24/gT8zAbSEUHDF1fa.png)

​		该部分其余操作皆与Spring Boot模块操作相仿。

<hr>

# 六、Reference

（以下不分先后）

https://www.jianshu.com/p/350972a3a258 SpringBoot简介 - 简书

https://blog.csdn.net/SUNLAOHAN/article/details/107028103 mybatis与jdbc的区别_牛肉汉宝宝的博客-CSDN博客_jdbc和mybatis区别

https://www.runoob.com/jquery/jquery-ajax-intro.html jQuery AJAX 简介 | 菜鸟教程

https://blog.csdn.net/huzia/article/details/124345353 图解springboot 五层结构 view controller service mapper model_杨治中的博客-CSDN博客_springboot中的model

