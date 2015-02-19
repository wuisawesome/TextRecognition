package com.wucrazylabs.experiment;

import java.awt.Font;
import java.io.File;

import javax.imageio.ImageIO;

import wucrazylabs.hashing.HashMaker;

public class GenerateImage {

	public static void main(String[] args) throws Exception{
		String text = "Hello World!";
		File f = new File("/users/alex/desktop/" + text + ".png");
		ImageIO.write(HashMaker.imageForString(text, new Font("Times New Roman",Font.PLAIN,400)), "PNG", f);
		
	}

}
