package com.movsci.processingapi.service;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;
import com.microsoft.azure.storage.blob.ListBlobItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.io.*;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;

@Service
@Slf4j
public class SharedService {

    public String downloadFile(String blobName) throws IOException, URISyntaxException, InvalidKeyException, StorageException {
        //TODO: download file
        storageAccount = CloudStorageAccount.parse(connString);
        blobClient = storageAccount.createCloudBlobClient();
        container = blobClient.getContainerReference(containerName);
        File downloadedFile = new File(blobName);
        CloudBlockBlob blob = container.getBlockBlobReference(blobName);
        try {
            blob.downloadToFile(downloadedFile.getAbsolutePath());
        }catch(Exception e){
            log.error(e.getMessage());
        }
        return downloadedFile.getAbsolutePath();
    }

    public Boolean uploadFile(String tmpName) throws URISyntaxException, InvalidKeyException, StorageException, IOException {
        //TODO: upload file
        storageAccount = CloudStorageAccount.parse(connString);
        blobClient = storageAccount.createCloudBlobClient();
        container = blobClient.getContainerReference(containerName);
        CloudBlockBlob blob = container.getBlockBlobReference(tmpName);
        blob.uploadFromFile(new File(tmpName).getAbsolutePath());
        log.info("Uploaded " + tmpName + " successfully.");
        return true;
    }


}
