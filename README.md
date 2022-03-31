核心功能

能够管理题目：保存很多题目信息

题目列表页：展示题目列表

题目详情页：题目信息+代码编辑框

提交并运行

查看运行结果：

# 编译运行模块

在初学java是通过`javac`编译文件，通过`java`执行文件

比如在文件上建立一个简单的.java文件，并通过命令运行

```java
public class Solution{
    public static void main(String[] args){
        System.out.println("hello word");
    }
}
```

直接在当前文件夹进行编译运行

![Snipaste20220331214225](pitcure/Snipaste20220331214225.png)

将文件编译到其他文件，然后指定文件路径进行运行

![Snipaste20220331215318](pitcure/Snipaste20220331215318.png)

修改代码为

```java
public class Solution{
    public static void main(String[] args){
        Syste.out.println("hello word");
    }
}
```

![Snipaste_2022-03-31_21-57-34](pitcure/Snipaste_2022-03-31_21-57-34.png)

修改代码为

```java
public class Solution{
    public static void main(String[] args){
        System.out.println("hello word");
        int a=10/0;
    }
}
```

![Snipaste_2022-03-31_21-58-48](pitcure/Snipaste_2022-03-31_21-58-48.png)

由上面编译或者执行的反馈信息进行判断程序是否正常。

通过标准输入输出得到`javac`或者`java`得到信息，然后写入文件中进行处理。

我们主要通过

```java
public void run(String cmd,String stdoutFile,String stderrFile){
    Process process = Runtime.getRuntime().exec(cmd);
    //对文件的操作
}
```

执行命令然后得到标准的输入输出反馈

有了对文件的操作，那我们需要文件，文件怎么得到呢？仿造leetcode，我们得到前端来的代码进行生成文件的操作。

需要一个一个实体类来放置代码。不妨叫它Code.

```java
public class Code{
    private String code;
    //set和get方法
}
```

同理，也需要一个实例类来返回编译运行信息。编译异常、运行正常、运行异常。如果异常还要返回信息。

```java
public class Result{
    //错误码
    private int error;
    //错误信息
    private String reason;
    //标准输出结果
    private String stdout;
    //标准错误结果
    private String stderr;
}
```

