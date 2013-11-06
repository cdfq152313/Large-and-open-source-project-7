大型開源軟體 Proposal
=====================

Introduction
------------
這是這學期大型開源軟體課程的Project Site，我們打算利用網路上的Data作些有趣的分析

Project Goal
------------
找出現在的新聞記者都是上PTT抄新聞的證據，並分析出這些抄的新聞是否佔有很大的比例

Data Source
-----------
1. 各大新聞網站（如: [Yahoo](http://tw.news.yahoo.com/)、[ETtoday](http://www.ettoday.net/))
2. PTT或是[非官方的網頁版PTT](http://disp.cc/b/)

Motivation
----------
*  觀察現在素質低落的網路記者抄新聞的現象，然後笑一笑

Develop Environment
-------------------
* **Language:**  Java and Python
* **Development Tool:**  Eclipse 
* **Version Control Host:**  GitHub

Expected Function
-----------------
1. 每天自動蒐集data並存入資料庫
2. 自動列出抄PTT的新聞 
3. 如果最後呈現內容的方式是網頁的話，可以新增評論功能

Implementation Detail
---------------------
  先寫一個web crawler去抓出網路新聞的資料，
  再用同樣的方式抓取telnet ptt的資料。如果telnet
  抓取太難，可考慮一樣用web crawler去爬非官方網頁
  版的Ptt 取得文章。之後作一些前處理，把raw data 
  弄成方便後續分析的clean data存入database。比對文
  章標題和文章內容的詞彙重複程度，判斷是否是同一篇
  文章。詞彙重複可以分為日常用語以及專門用語，
  如**的**、**了** 這些即為所謂口頭用語，並非判斷
  文章語意的重要詞彙，而**油品**、**有毒**這些即為
  專門用語，在相同領域的文章中較容易出現，因此我們
  會設計一個評量規則，為每個詞彙打分，藉以判斷其
  相同程度。

Team Members
------------
詹上潁、曾與聖、鄭翔元、荊士懷

