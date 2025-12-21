package com.Resume.ResumeService.RsumService;


import com.Resume.ResumeService.RsumEntity.ResumeEntity;
import com.Resume.ResumeService.RsumException.ResumeException;
import com.Resume.ResumeService.RsumRepository.ResumeRepository;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class ResumeServiceImpl implements ResumeService{
    private static final String UPLOAD_DIR = "uploads/";

    @Autowired
    private ResumeRepository repository;


    @Override
    public String saveResume(Long PartyId, MultipartFile file) {
        System.out.println("Hey I am inside SaveResumemethod with partyId -" + PartyId);
        try {

            Path uploadPath = Paths.get(UPLOAD_DIR);
            if(!Files.exists(uploadPath)){
                Files.createDirectories(uploadPath);
            }
            String filePath = UPLOAD_DIR + file.getOriginalFilename();

            Files.write(Paths.get(filePath),file.getBytes());

            String extractedText = extractTextFromFile(filePath,file.getContentType());
            ResumeEntity resumeEntity = new ResumeEntity();
            resumeEntity.setResumeFileName(file.getName());
            resumeEntity.setPartyId(PartyId);
            resumeEntity.setResumePath(filePath);
            resumeEntity.setResumeText(extractedText);
            resumeEntity.setResumeContentType(file.getContentType());
            repository.saveAndFlush(resumeEntity);
            System.out.println("File save at location -> "+Paths.get(filePath).toAbsolutePath());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return "Resume Uploaded successfully";

    }

    private String extractTextFromFile(String filePath, String contentType) {
        System.out.println("Hey I am inside method extractTextFromFile with filepath - "+filePath);

        try (PDDocument document = PDDocument.load(new File(filePath))){
            return new PDFTextStripper().getText(document);

        }catch (Exception e){
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
}
