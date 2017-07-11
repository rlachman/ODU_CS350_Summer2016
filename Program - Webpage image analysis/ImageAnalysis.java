/**
 * A subclass of the main program. This class's main function is to analyze the
 * internal and external images. These must be recorded.
 * For the internal images the size of the Image and the Image source is returned
 * For external images the source of the Image is returned.
 * @ Ryan Lachman
 **/
 
 package edu.odu.cs.cs350.blue5;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

//import com.google.gson.Gson;

import edu.odu.cs.cs350.blue5.Image.ImageClassification;



public class ImageAnalysis {

	private File file;
	private ImageADT imageADT;
	
	
	public ImageAnalysis(File file){	// Constructor which takes in the file as the input
		this.file = file;							// HTML file from which the Images have to be analyzed
		this.imageADT = new ImageADT();
		
	}
	
	
	public File getFile() {							//Get File
		return file;
	}
	
	public ImageADT getImageADT(){				// Get Image ADT
		return imageADT;
	}

	public void analysis(){		// Analysis of all the JPEGS, PNGS in the current HTML File
		
		Document htmlFile = null;
        try {
            htmlFile = Jsoup.parse(file, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        } 
        
        Elements pngs = htmlFile.select("img[src$=.png]");		// Extract all the pngs
        Elements jpgs = htmlFile.select("img[src$=.jpg]");		// Extract all the jpgs
        Elements gifs = htmlFile.select("img[src$=.gif]");		// Extract all the gifs
        
        analysis_helper(pngs,Image.ImageType.PNG);		// Analysis of all the extracted pngs
        analysis_helper(jpgs,Image.ImageType.JPG);		// Analysis of all the extracted jpgs
        analysis_helper(gifs,Image.ImageType.GIF);		// Analysis of all the extracted gifs
	}
	
	public void analysis_helper(Elements elements, Image.ImageType type){
		
		
		for(Element e: elements){
			
			boolean search = search_image_source(e);		// Search the Image. If the image already exists
			if(search == false){							// In the list then update the count and page number
			
				Image image = new Image();
				image.setType(type);
				image_analysis_classifier(image,e.attr("src"));
				String path = file.getParent() + "\\" + e.attr("src");
			
				File file_new = new File(path);
				if(file_new.exists()){			// Analysis of image size only for internal images
					if(image.getClassification() == Image.ImageClassification.Internal){
						image.setPath(path);
						double bytes = file_new.length();
						double kilobytes = bytes/1000;			// Gives the size of the image in kilobytes
						image.setSize(kilobytes);
						imageADT.setInternal_image_count(imageADT.getInternal_image_count()+1);					// Increment the count for Internal Images
					}
				}
				if(image.getClassification() == Image.ImageClassification.External){
					image.setPath(e.attr("src"));			// Set Image Path as Src of the Image
					imageADT.setExternal_image_count(imageADT.getExternal_image_count()+1);				// Increase the count for external Images
				}
				image.setNum_of_pages(1);
				List<String> list_of_pages = new ArrayList<String>();
				list_of_pages.add(file.getAbsolutePath());
				image.setList_of_pages(list_of_pages);
				imageADT.getImages().add(image);
			}
		}
	}
	
	public void image_analysis_classifier(Image image,String source){
		int index = source.indexOf("http");		// Search for external links in the image source
		if(index  ==  -1){						// Indicates no external link image found.
			image.setClassification(Image.ImageClassification.Internal);
		}
		else{									// Indicates External link image found
			image.setClassification(Image.ImageClassification.External);
		}
			
	}
	
	public boolean search_image_source(Element e){			// Method searches for image in the existing image
															// Through Linear Search
		for(Image image: imageADT.getImages())
		{
			if(image.getPath().equals(e.attr("src"))){
				image.setNum_of_pages(image.getNum_of_pages()+1);
				image.getList_of_pages().add(file.getAbsolutePath());
			}
		}
		
		return false;
	}
	
	public void print_analysis(){		// This function prints the JSON output
		
		
		
		//Gson gson = new Gson();			
		//String json = gson.toJson(imageADT);		// To convert ArrayList to JSON
		
		//try{
		//	File file = new File("ImageAnalysis.json");		// Write the analysis to ImageAnalysis.json
		//	if (!file.exists()) {
		//		file.createNewFile();
		//	}

		//	FileWriter fw = new FileWriter(file.getAbsoluteFile());
		//	BufferedWriter bw = new BufferedWriter(fw);
		//	bw.write(json);
		//	bw.close();
		//}
		//catch(Exception e){
		//	e.printStackTrace();
		//}
		
		//System.out.println(json);
	}
}
