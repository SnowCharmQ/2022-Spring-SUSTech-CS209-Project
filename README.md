# **2022-Spring-Java2-Project**

**Group Members:**
> JADDYK <br>
> Olin66

### **The Topic of This Project:**

##### *Data mining, analysis, and visualization of current mainstream development framework Spring Boot and MyBatis*

### Data Source:

##### *GitHub and Stack Overflow*

### Data Persistence (Database):

##### PostgreSQL

### Word Frequency Statistics:

##### Stanford Natural Language Processing

### The Framework of Our Project:

1. ##### Pojo Layer

   This layer consists of all entities in our project, these entities correspond to tables in the database.

   ![image-20220524102905260](https://s2.loli.net/2022/05/24/Ov7iZhy98HISPpl.png)

   | Entity                 | Corresponding Database                        |
      | ---------------------- | --------------------------------------------- |
   | IssueWord              | issue_word                                    |
   | MyBatisIssue           | mybatis_issue                                 |
   | MyBatisQuesion         | mybatis_question                              |
   | QuestionWord           | question_word                                 |
   | SpringBootIssue        | NONE                                          |
   | SpringBootIssueVersion | springboot_open_issue springboot_closed_issue |
   | SpringBootIteration    | springboot_iteration                          |
   | SpringBootQuestion     | springboot_question                           |

2. ##### Mapper layer

   This layer consists of the method interfaces of CRUD (create, read, update, delete) operations.

   ![image-20220524110831627](https://s2.loli.net/2022/05/24/1TwdbmPIleqHEou.png)

   The implementations of these interfaces are defined in the corresponding XML files.

   ![image-20220524110929133](https://s2.loli.net/2022/05/24/LXZDSlYkz5mRQeb.png)

3. ##### Service Layer

   This Layer mainly stores the business logic processing. In this project, the operations are basically database queries. In each class, the objects of the Mapper layer are imported and autowired to operate the database. Take the following method as an example:

   ![image-20220524111842522](https://s2.loli.net/2022/05/24/V6LFzMlhBQoaDSg.png)

4. ##### Controller Layer

   The Controller layer is used to decide what views to use and what data to prepare for the presentation in response to user needs. The Controller layer is responsible for the front-end and back-end interaction, receiving front-end requests, calling the Service layer, receiving the data returned by the Service layer, and finally returning specific data (JSON) and pages to the client. RestController and RequestMapping annotations are added to the class definition.

5. ##### View Layer

   All static pages in this project are defined by HTML, CSS, and JavaScript. The front-end interface mainly uses jQuery and AJAX technology to read back-end data and display it on the front-end.

### Some Demo Pages of The Project:

![image-20220524161856288](https://s2.loli.net/2022/05/24/MpzuQdFeXqYNsfV.png)

![image-20220524163814388](https://s2.loli.net/2022/05/24/GbzANKgE2IOY1fV.png)

![image-20220524165053477](https://s2.loli.net/2022/07/06/LBYRqy6Fmo9bHVX.png)

![img](https://s2.loli.net/2022/05/24/7PGIpaM5Tnx3R9e.jpg)

![image-20220524193931338](https://s2.loli.net/2022/05/24/xRohEb28r4dS1Di.png)

![image-20220524203131383](https://s2.loli.net/2022/05/24/xw8Qtieojfgs5ZD.png)

![image-20220524210514378](https://s2.loli.net/2022/05/24/4zOqmETvPky6GWl.png)

![image-20220524211135907](https://s2.loli.net/2022/05/24/mHQwWlzKx8CX9ct.png)