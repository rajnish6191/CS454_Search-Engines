Team Name: Jay & Rajnish                                                                                                                 
------------------------------------------------------------------------------------

Team Members:                                                                                                                           
1. Kumar, Rajnish [CIN: 304470392]                                                                                                                                                                                                               
2. Purohit, Jay [CIN: 304455065]

------------------------------------------------------------------------------------
Homework 1: <A Crawler Application>
* Crawl the Web
* Start with a URL
* Find all the links in the document
* Download the html documents and images located at the URL
* Handled Circular References
* Handled Exceptions while Crawling
* Proper Local Storage
* Implemented Multithreaded Crawler

To run:                                                                                                                                 
Crawler: java -jar crawler.jar -d 2 -u http://www.calstatela.edu

------------------------------------------------------------------------------------
Homework 2: <An Extractor Applpication>
* Integrated with Crawler Application
* Extract metadata and relevant data from downloaded documents
* Store all metadata and relevant data in csv format <No database used in this hw>
* Implemented a Data Dump Program

To run:                                                                                                                                 
Extractor: java -jar crawler.jar -d 2 -u http://www.calstatela.edu -e
For Dump: java -jar Dumper.jar

------------------------------------------------------------------------------------

Homework 3: <Indexing and Ranking Application>                                                                                                                                                                                                                  
* Implemented an Indexer Application:                                                                                                   
-> Crawl the Web Server-> Download and Pull the Data-> Able to index wiki small / wiki large / mungee data collection-> Extract the terms-> When presented with a term, give back a list of documents with the term of interest                                                    
* Implemented TF-IDF Ranking:                                                                                                           
-> Calculate TF Score
-> Calculate IDF Score
-> Calculate TF-IDF Score
-> Associated with a weight factor [0.7]
-> Find TF-IDF Rank
-> All Extracted terms with score will be stored in Database

* Implemented Link Analysis Ranking:                                                                                                    
-> Find initial ranking of all documents
-> Find all outgoing links
-> Find all incoming links
-> Find atleast 40 iteration 
-> Calculate Page Rank
-> Associated with a weight factor [0.3]
-> All Documents with score will be stored in Database

* Implemented Normalized Score:                                                                                                         
-> Already, we have TF-IDF Score with a weight factor 0.7
-> Already, we have Page Rank Score with a weight factor 0.3
-> Finally, we used a formula: (0.7*TF-IDF Score) + (0.3*Page Rank) for Normalization

Note:                                                                                                                                     
-> Here we have considered weightage for TfIdf more against Link Analysis so its weighting factor is 0.7 and for Page Ranking is 0.3.

------------------------------------------------------------------------------------
Homework 4: <A Simple User Web Interface>
* Query the System
* Return list of documents, TF-IDF Score, Page Rank and Normalized Score
* We get result of Rank in desceding order
* we performed boolean logic also
* We performed search recommendation also

------------------------------------------------------------------------------------
Project: <A Complete Search Engine>
* Crawling
* Extraction
* Indexing
* Ranking with TF-IDF
* Ranking with Link Analysis
* Normalization
* A User Web Interface
* Use of Google Map API & Open Weather API for Searching Location and Weather as a Novel Contribution     

------------------------------------------------------------------------------------
