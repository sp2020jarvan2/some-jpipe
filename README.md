swordess-jpipe
==============

A command line tool which supports [pipeline](http://en.wikipedia.org/wiki/Pipeline) feature like in Linux.

Builtin Commands
----------------
Current supported commands and its options are listed as following.

**[cat](https://github.com/xingyuli/jpipe/blob/master/src/main/java/org/swordess/jpipe/command/builtin/Cat.java)**
concatenate files and print on the standard output
+ -n number all output lines

**[echo](https://github.com/xingyuli/jpipe/blob/master/src/main/java/org/swordess/jpipe/command/builtin/Echo.java)**
display line of text

**[grep](https://github.com/xingyuli/jpipe/blob/master/src/main/java/org/swordess/jpipe/command/builtin/Grep.java)**
print lines matching a pattern
+ -n prefix each line of output with the line number within its input
+ -v output in verbose format, indicaters of each match will be shown

**[sort](https://github.com/xingyuli/jpipe/blob/master/src/main/java/org/swordess/jpipe/command/builtin/Sort.java)**
sort lines of text files
+ -r reverse the result of comparisons

**[wc](https://github.com/xingyuli/jpipe/blob/master/src/main/java/org/swordess/jpipe/command/builtin/Wc.java)**
print the number of newlines, words
+ -l print the newline counts
+ -w print the word counts

More commands and command options will be integrated in the near future.

Install
-------
Before using [jpipe](https://github.com/xingyuli/jpipe), you need to use [maven](http://maven.apache.org/) to build or package this project. After
that, all testing data files under */src/main/resources* will be copied to
*/target/classes*.

When you finish the installation, you could execute the commands using [jpipe](https://github.com/xingyuli/jpipe) by:
```
$ java org.swordess.jpipe.Main "shell"
```
or
```
$ java -jar jpipe.jar "shell"
```
Please ensure the path to your data files are specified correctly so that [jpipe](https://github.com/xingyuli/jpipe)
could find them!

> **NOTE**  
> As currently the project doesn't depend on any third party libraries, you could
> also export this project by either approach listed below.
> + using the built-in [jar](http://docs.oracle.com/javase/1.4.2/docs/tooldocs/windows/jar.html) tool provided by java
> + using the export feature of the IDE your are using
>
> Please remember to specify *[org.swordess.jpipe.Main](https://github.com/xingyuli/jpipe/blob/master/src/main/java/org/swordess/jpipe/Main.java)* as the main class!

Demenstrations
--------------

Here are some examples of this tool which could not be that much complex.

### 1. Sort a file in alphabetic order and extract some words from it
```
$ pwd
path_to_jpipe_project/target/classes

$ cat data
orange abstract were abandon
111 somehow water
watermelon banana
apple candidate
222           extremely
333       duplicate
pineapple  hate
444  avatar

$ java org.swordess.jpipe.Main "cat data | sort -r | grep -n -v a"
1:watermelon banana
   ^          ^ ^ ^
2:pineapple  hate
      ^       ^
3:orange abstract were abandon
    ^    ^    ^        ^ ^
4:apple candidate
  ^      ^    ^
5:444  avatar
       ^ ^ ^
6:333       duplicate
                  ^
8:111 somehow water
               ^
```

### 2. Concate multiple text files and sort them in alphabetic order
```
$pwd
path_to_jpipe_project/target/classes

$ cat numbers
213
451
122
-15
73
1245

$ java org.swordess.jpipe.Main "cat data numbers | sort"
-15
111 somehow water
122
1245
213
222           extremely
333       duplicate
444  avatar
451
73
apple candidate
orange abstract were abandon
pineapple  hate
watermelon banana
```
