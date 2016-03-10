package project;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.CDL;
import org.json.JSONArray;

public class Dumper {

	// Reading the csv file 
	public static void main(String[] args) throws IOException {
    	String csvContent = readFile("./metadata/attributes.csv", Charset.defaultCharset());
    	
    	JSONArray array = CDL.toJSONArray(csvContent);
        System.out.println(array.toString(2));
    }
    
    static String readFile(String path, Charset encoding) 
    		  throws IOException 
    		{
    		  byte[] encoded = Files.readAllBytes(Paths.get(path));
    		  return new String(encoded, encoding);
    		}
}