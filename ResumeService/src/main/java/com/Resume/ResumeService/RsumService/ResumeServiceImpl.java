package com.Resume.ResumeService.RsumService;


import com.Resume.ResumeService.RsumEntity.ResumeEntity;
import com.Resume.ResumeService.RsumException.ResumeException;
import com.Resume.ResumeService.RsumRepository.ResumeRepository;

import com.cloudinary.Cloudinary;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional(rollbackFor = Exception.class)
public class ResumeServiceImpl implements ResumeService{
    private static final String UPLOAD_DIR = "uploads/";

    @Autowired
    private ResumeRepository repository;
    @Autowired
    private Cloudinary cloudinary;


    @Override
    public String saveResume(Long PartyId, MultipartFile file) {
        System.out.println("Hey I am inside SaveResumemethod with partyId -" + PartyId);
        String fileUrl = "";
        try {
            List<ResumeEntity> resumeEntities = repository.findByPartyId(PartyId);
           // String filePath = UPLOAD_DIR + file.getOriginalFilename();
            if(resumeEntities.size() != 0){
                ResumeEntity resume = resumeEntities.get(0);
//                Path uploadPath = Paths.get(UPLOAD_DIR);
//                if(!Files.exists(uploadPath)){
//                    Files.createDirectories(uploadPath);
//                }

                Map uploadResult = cloudinary.uploader().upload(
                        file.getBytes(),
                        Map.of(
                                "resource_type", "raw",
                                "folder", "resumes",
                                "type", "upload"
                        )
                );
                fileUrl = uploadResult.get("secure_url").toString();

               // Files.write(Paths.get(filePath),file.getBytes());

                String extractedText = extractTextFromFile(fileUrl,file.getContentType());
                ResumeEntity resumeEntity = new ResumeEntity();
                resumeEntity.setResumeId(resume.getResumeId());
                resumeEntity.setResumeFileName(file.getName());
                resumeEntity.setPartyId(PartyId);
                resumeEntity.setResumePath(fileUrl);
                resumeEntity.setResumeText(extractedText);
                resumeEntity.setResumeContentType(file.getContentType());
                repository.saveAndFlush(resumeEntity);
            }else{
//               // Path uploadPath = Paths.get(UPLOAD_DIR);
//                if(!Files.exists(uploadPath)){
//                    Files.createDirectories(uploadPath);
//                }

               // Files.write(Paths.get(filePath),file.getBytes());
                Map uploadResult = cloudinary.uploader().upload(
                        file.getBytes(),
                        Map.of(
                                "resource_type", "raw",
                                "folder", "resumes",
                                "type", "upload"
                        )
                );
                fileUrl = uploadResult.get("secure_url").toString();

                String extractedText = extractTextFromFile(fileUrl,file.getContentType());
                ResumeEntity resumeEntity = new ResumeEntity();
                resumeEntity.setResumeFileName(file.getName());
                resumeEntity.setPartyId(PartyId);
                resumeEntity.setResumePath(fileUrl);
                resumeEntity.setResumeText(extractedText);
                resumeEntity.setResumeContentType(file.getContentType());
                repository.saveAndFlush(resumeEntity);
            }

            System.out.println("File save at location -> "+ fileUrl);

        }catch (IOException e) {
            throw new RuntimeException(e);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

        return "Resume Uploaded successfully";

    }

    private String extractTextFromFile(String filePath, String contentType) {
        System.out.println("Hey I am inside method extractTextFromFile with filepath - "+filePath);

//        try (PDDocument document = PDDocument.load(new File(filePath))){
//            return new PDFTextStripper().getText(document);
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
        try (InputStream inputStream = new URL(filePath).openStream();
             PDDocument document = PDDocument.load(inputStream)) {

            PDFTextStripper stripper = new PDFTextStripper();
            stripper.setSortByPosition(true);

            String text = stripper.getText(document);

            System.out.println("Extracted Text Length: " + text.length());

            return text;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public List<Long> saerchByKeyWord(String keyWord) {

        List<ResumeEntity> list = repository.findByResumeTextContainingIgnoreCase(keyWord);
        List<Long> partyList = new ArrayList<>();
        for(ResumeEntity entity : list){
            partyList.add(entity.getPartyId());
        }
        return partyList;
    }

    @Override
    public String getResumeTextByPartyId(Long partyId) {
        System.out.println("Inside GetResumeTextByPartyId method of service class");
        String resumeText = "";
        List<ResumeEntity> entityList = new ArrayList<>();
        try {
            entityList = repository.findByPartyId(partyId);
            resumeText = entityList.get(0).getResumeText();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resumeText;

    }

    @Override
    public String downloadResume(Long partyId) {
        // Step 1: Fetch from DB
        List<ResumeEntity> resumelist = repository.findByPartyId(partyId);
        ResumeEntity resume = resumelist.get(0);

        if (resume == null) {
            throw new RuntimeException("Resume not found");
        }
        String url = resume.getResumePath();

        try {
            // Step 2: Load file
//            Path path = Paths.get(resume.getResumePath());
//            Resource resource = new UrlResource(path.toUri());

//            if (!resource.exists()) {
//                throw new RuntimeException("File not found");
//            }

            // Step 3: Return response
//            return ResponseEntity.ok()
//                    .contentType(MediaType.parseMediaType(resume.getResumeContentType()))
//                    .header(HttpHeaders.CONTENT_DISPOSITION,
//                            "attachment; filename=\"" + resume.getResumeFileName() + "\"")
//                    .body(resource);


        } catch (Exception e) {
            throw new RuntimeException("Error while downloading file", e);
        }
        return url;
    }
}
