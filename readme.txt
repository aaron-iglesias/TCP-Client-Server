{\rtf1\ansi\ansicpg1252\cocoartf1404\cocoasubrtf340
{\fonttbl\f0\fswiss\fcharset0 Helvetica;}
{\colortbl;\red255\green255\blue255;}
\margl1440\margr1440\vieww10800\viewh8400\viewkind0
\pard\tx720\tx1440\tx2160\tx2880\tx3600\tx4320\tx5040\tx5760\tx6480\tx7200\tx7920\tx8640\pardirnatural\partightenfactor0

\f0\fs24 \cf0 Compile\
\
UNIX Environment \
1. Server \
	1. cd to directory where Server.java is saved \
	2. javac Server.java \
	3. java Server 9151 \
2. Client \
	1. cd to directory where Client.java is saved \
	2. javac Client.java \
	3. java Client 127.0.0.1 9151 (args[0] is IP Address of server)\
\
Examples of valid Client inputs:\
add 5 4 3 2\
subtract 10 2\
multiply 10 3 4\
divide 10 5\
\
Note that only \'93add\'94, \'93subtract\'94, \'93multiply\'94, and \'93divide\'94 are valid operations, and only 2 to 4 nonnegative integers are allowed for the operands.}