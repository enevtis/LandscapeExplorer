package landscapeexplorer.nvs.com;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Utils {

	public static String cleanInvalidCharacters(String in) {
		StringBuilder out = new StringBuilder();
		char current;
		if (in == null || ("".equals(in))) {
			return "";
		}
		for (int i = 0; i < in.length(); i++) {
			current = in.charAt(i);
			if ((current == 0x9) || (current == 0xA) || (current == 0xD) || ((current >= 0x20) && (current <= 0xD7FF))
					|| ((current >= 0xE000) && (current <= 0xFFFD))
					|| ((current >= 0x10000) && (current <= 0x10FFFF))) {
				out.append(current);
			}

		}
		return out.toString().replaceAll("\\s", " ");
	}

	public static List<String> readFile(String fileName){
		
		
		List<String> out = new  ArrayList<String>();
		

	    try 
	    { 
	        FileInputStream fstream_school = new FileInputStream(fileName); 
	        DataInputStream data_input = new DataInputStream(fstream_school); 
	        BufferedReader buffer = new BufferedReader(new InputStreamReader(data_input,StandardCharsets.UTF_8)); 
	        String str_line; 

	        while ((str_line = buffer.readLine()) != null) 
	        { 
	            str_line = str_line.trim(); 
	            if ((str_line.length()!=0))  
	            { 
	                out.add(cleanInvalidCharacters(str_line));
	            } 
	        }
	        
	    } catch (Exception e) {
	    	
	    	System.out.println("Can't read file " + fileName);
	    	e.printStackTrace();
	    	
	    }
		return out;
		
	}

}
