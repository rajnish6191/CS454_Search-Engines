package hw3ranking; 

import java.io.*;
import java.util.*;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.WhitespaceAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.TermAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.*;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.NIOFSDirectory;
import org.apache.lucene.util.Version;



public class TfIdf
{

	private String docNames[];
	private String docIDS[];
	private String pathToIndex;
	private String pathToDocumentCollection;
	private String fiboTermList[]; //marked up fibo terms
	private String taxoTermList[]; // marked up taxonomy terms
	private RAMDirectory ramMemDir;
	private String fileNames[];
	private byte files[][];
	private String filesInText[];
	int noOfWordsOfDOc[];
	int noOfSentencesOfDoc[];
	ArrayList<String> ArrLstSentencesOfDoc[];
	String removedTermsOfDOc[][];
	int freqAfterRemovalOfDoc[][];
	private int curDocNo;
	private final int maxTerms = 1000000;




public TfIdf(String pathToIndex, String pathToDocumentCollection) {

    this.pathToIndex = pathToIndex;
    this.pathToDocumentCollection= pathToDocumentCollection;


}




	private int wordCount(String line) 
	{
		int numWords = 0;
		int index = 0;
		boolean prevWhiteSpace = true;
		while (index < line.length()) 
		{
			char c = line.charAt(index++);
			boolean currWhiteSpace = Character.isWhitespace(c);
			if (prevWhiteSpace && !currWhiteSpace) 
			{
				numWords++;
			}
			prevWhiteSpace = currWhiteSpace;
		}
		return numWords;
	}

	public static String fileReader(String filename) throws IOException 
	{

		String filetext = null;
		BufferedReader reader = null;
		File inFile = new File(filename);
    
		reader = new BufferedReader(new FileReader(inFile));
		String line = null;

		int numLine = 0;

		while ((line = reader.readLine()) != null) 
		{
    
			filetext = filetext + " " + line;

    
		}

		reader.close();
		return filetext;
	}

		
	public void indexDocs() throws IOException
	{
		File folder = new File(pathToDocumentCollection);
		File[] listOfFiles = folder.listFiles();
		int noOfFiles = listOfFiles.length;
		System.out.println("Number of files : " + noOfFiles);

		IndexWriter iW;
		int indexDocCount = 0;
			try {
				NIOFSDirectory dir = new NIOFSDirectory(new File(pathToIndex));
				iW = new IndexWriter(dir, new IndexWriterConfig(Version.LUCENE_36, new WhitespaceAnalyzer(Version.LUCENE_36)));

				for (int i = 0; i < noOfFiles; i++) 
				{
					if (listOfFiles[i].isFile()) 
					{
						String docName = listOfFiles[i].getName();
						System.out.println("doc name: " + docName + "length - " + listOfFiles[i].length());
						if (listOfFiles[i].length() > 1) 
						{
							String filesInText = fileReader(pathToDocumentCollection + docName);
							System.out.println("Added to index : " + docName);

                    
							StringReader strRdElt = new StringReader(filesInText.replaceAll("\\d+(?:[.,]\\d+)*\\s*", ""));
							StringReader docId = new StringReader(docName.substring(0, docName.length() - 4)); // give a unique doc Id here

							org.apache.lucene.document.Document doc = new org.apache.lucene.document.Document();	

							doc.add(new Field("doccontent", strRdElt, Field.TermVector.YES));
							doc.add(new Field("docid", docId, Field.TermVector.YES));
							iW.addDocument(doc);
							indexDocCount++;
						}
					}	
				}

				System.out.println("no of documents added to index : " + indexDocCount);

				iW.close(); 
			}
			catch (CorruptIndexException e) 
			{
				e.printStackTrace();
			} 
			catch (IOException e) 
			{
			e.printStackTrace();
			}
	}




	public HashMap<Integer, HashMap> tfIdfScore(int numberOfDocs) throws CorruptIndexException, ParseException 
	{

		int noOfDocs = docNames.length;

		HashMap<Integer, HashMap> scoreMap = new HashMap<Integer, HashMap>();
    


	try {

			IndexReader re = IndexReader.open(NIOFSDirectory.open(new File(pathToIndex)), true) ;
       

			int i = 0;
			for (int k = 0; k < numberOfDocs; k++) 
			{
				int freq[];
				TermFreqVector termsFreq;
				TermFreqVector termsFreqDocId;
            
				HashMap<String, Float> wordMap = new HashMap<String, Float>();
				String terms[];
				float score[] = null;

            
				termsFreq = re.getTermFreqVector(k, "doccontent");
				termsFreqDocId = re.getTermFreqVector(k, "docid");

				int aInt = Integer.parseInt(termsFreqDocId.getTerms()[0]);
				freq = termsFreq.getTermFrequencies();

				terms = termsFreq.getTerms();

				int noOfTerms = terms.length;
				score = new float[noOfTerms];
				DefaultSimilarity simi = new DefaultSimilarity();
				for (i = 0; i < noOfTerms; i++) 
				{
					int noofDocsContainTerm = re.docFreq(new Term("doccontent", terms[i]));
                
					float tf = simi.tf(freq[i]);
					float idf = simi.idf(noofDocsContainTerm, noOfDocs);
					wordMap.put(terms[i], (tf * idf));

				}
				scoreMap.put(aInt, wordMap);
			}


		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		return scoreMap;
	}


	public HashMap<Integer, HashMap> getTFIDF() throws IOException, CorruptIndexException, ParseException, ClassNotFoundException 
	{
		int noOfDocs = docNames.length;
		float tfIdfScore[][] = new float[noOfDocs][];
		
		HashMap<Integer, HashMap> scoreMap = new HashMap<Integer, HashMap>();


		scoreMap = tfIdfScore(noOfDocs);




		return  scoreMap;
	}
}