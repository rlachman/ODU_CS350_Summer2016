package edu.odu.cs.cs350.blue5;

import java.util.ArrayList;
import java.util.List;

public class ImageADT {

	private int internal_image_count;
	private int external_image_count;
	private List<Image> images;
	
	public ImageADT(){
		internal_image_count = 0;
		external_image_count = 0;
		images = new ArrayList<Image>();
	}
	public int getInternal_image_count() {
		return internal_image_count;
	}
	public void setInternal_image_count(int internal_image_count) {
		this.internal_image_count = internal_image_count;
	}
	public int getExternal_image_count() {
		return external_image_count;
	}
	public void setExternal_image_count(int external_image_count) {
		this.external_image_count = external_image_count;
	}
	public List<Image> getImages() {
		return images;
	}
	public void setImages(List<Image> images) {
		this.images = images;
	}
	
	
}
