package com.example.napoleon.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

public class FileUtil {
    public static void writeImage(MultipartFile file, File serverFile) throws Exception {
        try (InputStream is = file.getInputStream();
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile))) {
            int i;
            while ((i = is.read()) != -1) {
                stream.write(i);
            }
            stream.flush();
        } catch (IOException e) {
            throw new Exception(e.getMessage());
        }
    }

    
	public static String getFileExtension(MultipartFile file) {
	    String name = file.getOriginalFilename();
	    int lastIndexOf = name.lastIndexOf(".");
	    if (lastIndexOf == -1) {
	        return "";
	    }
		
	    return name.substring(lastIndexOf).replace(".", "");
	}
	
	public static String getFileExtension(File file) {
	    String name = file.getAbsolutePath();
	    int lastIndexOf = name.lastIndexOf(".");
	    if (lastIndexOf == -1) {
	        return "";
	    }
	    return name.substring(lastIndexOf).replace(".", "");
	}
}
