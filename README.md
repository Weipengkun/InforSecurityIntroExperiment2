# InforSecurityIntroExperiment2
运用简易AES加密解密算法实现16-bit二进制数单重、双重和三重加解密、CBC应用字符串加解密、文件加解密和中间相遇攻击获取密钥。

该系统用java实现。
# Introduction
本系统有着开发者手册、实验测试结果、用户指南、和源程序（S_AES_algorithm_final）四部分组成

S_AES_algorithm_final\src代码包组成：

1、CBCMode文件定义了 CBC应用字符串加解密 代码

2、MultipleEncryptionAndDecryption文件定义了 双重和三重加解密 代码

3、SimplifiedAES文件定义了16-bit二进制数单重加解密操作，可以处理ASCII码，可以对2进制txt文件进行读取加解密操作

4、MeetInTheMiddleAttack文件实现了中间相遇共计的任务

5、UI文件实现了界面展示功能，其下的S_AES.java文件时是程序的启动入口

# Environments
jdk>=13，下载安装了java.awt和javax.swing

# 系统主界面如下：
![PQ)FQ)N0{UY$Z(U}Y93W$IL](https://github.com/Weipengkun/InforSecurityIntroExperiment2/assets/121174204/992edb07-d315-4024-99e2-da440c352d8c)

