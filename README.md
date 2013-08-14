jpipe
=====

A command line tool which supports pipeline feature like Linux.

Currently, following commands are supported by jpipe:
- cat
- echo
- grep
- sort
- wc

More commands and command options will be integrated in the near future.

Demenstrations
-----

Here are some examples of this tool which could not be that much complex.

Exampe 1. Sort a file in alphabetic order and extract some words from it
```
$ cat data
orange abstract were abandon
111 somehow water
watermelon banana
apple candidate
222           extremely
333       duplicate
pineapple  hate
444  avatar

$ java -jar jpipe.jar "cat data | sort -r | grep -n -v a"
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

Example 2. Concate multiple text files and sort them in alphabetic order
```
$ cat numbers
213
451
122
-15
73
1245

$ java -jar jpipe.jar "cat data numbers | sort"
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
