package com.example.napoleon.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.UUID;
import com.google.cloud.storage.StorageOptions;

import java.util.HashMap;
import java.util.Map;

@Service
public class FirebaseFileService {

    private Storage storage;
    
	@Value("${firebase.credentials.path}")
	private String firebaseCredentialsPath;

    @EventListener
    public void init(ApplicationReadyEvent event) {
        try {
            FileInputStream serviceAccount =
                new FileInputStream("C:\\its time to work\\Project\\napoleon\\libra-project-77ddd-firebase-adminsdk-4e1ar-6a6ad24485.json");
            storage = StorageOptions.newBuilder().
                    setCredentials(GoogleCredentials.fromStream(serviceAccount)).
                    setProjectId("libra-project-77ddd").build().getService();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String saveToFirebaseStorage(String path, String fileName, MultipartFile file) throws IOException{
        String imageName = fileName;
        Map<String, String> map = new HashMap<>();
        map.put("firebaseStorageDownloadTokens", imageName);
        BlobId blobId = BlobId.of("libra-project-77ddd.appspot.com", path + imageName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                .setMetadata(map)
                .setContentType(file.getContentType())
                .build();
        storage.create(blobInfo, file.getInputStream());
        return getPublicUrl(path + imageName);
    }
    
    private String generateFileName(String originalFileName) {
        return UUID.randomUUID().toString() + "." + getExtension(originalFileName);
    }

    public String getExtension(String originalFileName) {
        return StringUtils.getFilenameExtension(originalFileName);
    }

    private String getPublicUrl(String fileName){
        try {
            return "https://firebasestorage.googleapis.com/v0/b/libra-project-77ddd.appspot.com/o/" + URLEncoder.encode(fileName, "UTF-8") + "?alt=media";
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}
