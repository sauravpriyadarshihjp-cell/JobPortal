package com.Party.PartyService.UWRService;


import com.Party.PartyService.PartyException.PrtException;
import com.Party.PartyService.PartyException.PrtTransactionMessage;
import com.Party.PartyService.UWREntity.PartyEntity;
import com.Party.PartyService.UWREntity.PartyValues;
import com.Party.PartyService.UWREntity.TParty;
import com.Party.PartyService.UWREntity.UserEntity;
import com.Party.PartyService.UWRParty.EmployeeEntityDetails;
import com.Party.PartyService.UWRParty.EntityParameter;
import com.Party.PartyService.UWRService.Security.JwtService;
import com.Party.PartyService.UWRepository.PartyRepository;
import com.Party.PartyService.UWRepository.TPartyRepository;
import com.Party.PartyService.UWRepository.TPartySummaryRepository;
import com.Party.PartyService.UWRepository.UserRepository;
//import com.Party.PartyService.UWRService.JwtService;
import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional(rollbackFor = Exception.class)
public class PartyServiceImpl implements PartyService{

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    @Autowired
    private PartyRepository repository;
    @Autowired
    private TPartyRepository partyRepository;
    @Autowired
    private TPartySummaryRepository tPartySummaryRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtService jwtService;
    @Override
    public EmployeeEntityDetails saveEmplDtls(EmployeeEntityDetails employeeEntityDetails) {
        System.out.println("Happily entered in method saveEmplDtls with value :- "+employeeEntityDetails);

        //Creating Party Id
        TParty tParty = new TParty();
        tParty.setInsertedBy(employeeEntityDetails.getUserCode());
        tParty.setPartyType(employeeEntityDetails.getPartyType());
        tParty = partyRepository.saveAndFlush(tParty);
        Long presentPartyId = tParty.getPartyId();
        PartyValues partyValues = new PartyValues();


        try {
            for(EntityParameter entityParameter : employeeEntityDetails.getLstparameter()){
                PartyEntity entity = new PartyEntity();
                if(entityParameter.getParameterName().equals("Mobile Number")){
                    if(entityParameter.getParameterValue().length() != 10){
                        PrtTransactionMessage msg = new PrtTransactionMessage();
                        msg.setStrMessageType("ERROR");
                        msg.setStrMessageId("500");
                        msg.setStrMessageSource("SYSTEM");
                        msg.setProbableCause("Please enter valid Mobile Number");
                        msg.setStrMessageSeverity("FATAL");
                        employeeEntityDetails.setPrtTransactionMessageMessage(msg);

                        throw new PrtException(msg);
                    }
                }
                entity.setParameterName(entityParameter.getParameterName());
                entity.setParameterValue(entityParameter.getParameterValue());
                entity.setPartyId(presentPartyId);
                entity.setUpdatedBy(employeeEntityDetails.getUserCode());
                entity.setInsertedBy(employeeEntityDetails.getUserCode());
                repository.saveAndFlush(entity);

                if(entityParameter.getParameterName().equals("Name")){
                    partyValues.setName(entityParameter.getParameterValue());
                }
                if(entityParameter.getParameterName().equals("MobileNo")){
                    partyValues.setMobileNo(entityParameter.getParameterValue());
                }
                if(entityParameter.getParameterName().equals("Email")){
                    partyValues.setEmail(entityParameter.getParameterValue());
                }
                if(entityParameter.getParameterName().equals("skills")){
                    partyValues.setSkills(entityParameter.getParameterValue());
                }
                partyValues.setPartyId(presentPartyId);

            }
            tPartySummaryRepository.saveAndFlush(partyValues);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        employeeEntityDetails.setPartyId(presentPartyId);
        employeeEntityDetails.setPartyCode(tParty.getPartyCode());


        return employeeEntityDetails;

    }

    @Override
    public void updateEmplDtls(EmployeeEntityDetails employeeEntityDetails) {
        System.out.println("Happily Entered in method updateEmplDtls with value :- "+employeeEntityDetails);

        try {

            for(EntityParameter entityParameter : employeeEntityDetails.getLstparameter()){
                PartyEntity entity = new PartyEntity();
                entity.setUpdatedBy(employeeEntityDetails.getUserCode());
                entity.setParameterName(entityParameter.getParameterName());
                entity.setParameterValue(entityParameter.getParameterValue());
                entity.setPartyId(employeeEntityDetails.getPartyId());
                entity.setPartyType(employeeEntityDetails.getPartyType());
                repository.saveAndFlush(entity);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public EmployeeEntityDetails getEmplDtls(Long id, String user) {
        EmployeeEntityDetails employeeEntityDetails = new EmployeeEntityDetails();
        try {

            List<PartyEntity> parameterList = repository.getByPartyId(id);
            employeeEntityDetails.setPartyId(id);
            employeeEntityDetails.setUserCode(user);
            List<EntityParameter> parameters = new ArrayList<>();
            for(PartyEntity entity : parameterList){
                EntityParameter entityParameter = new EntityParameter();
                entityParameter.setParameterName(entity.getParameterName());
                entityParameter.setParameterValue(entity.getParameterValue());
                parameters.add(entityParameter);
            }
            employeeEntityDetails.setLstparameter(parameters);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return employeeEntityDetails;
    }

    @Override
    public String getPartyMatchingSkills(String skills) {
        List<PartyValues> list = tPartySummaryRepository.findBySkillsContaining(skills);
        String result = "";
        for(PartyValues partyValues : list){
            result = partyValues.getEmail() + "####";
        }
        return result;
    }

    @Override
    public Map<String, Object> register(Map<String, String> body) {
        String email = body.get("email");
        String password = body.get("password");
        String name = body.get("name");
        if(userRepository.existsByEmail(email)){
            return Map.of("error", "Email Already exists");
        }
        UserEntity user = new UserEntity();
        user.setName(name);
        user.setPassword(encoder.encode(password));
        user.setEmail(email);
        user.setActive(true);
        userRepository.saveAndFlush(user);
        return Map.of("status","User Registered");
    }

    @Override
    public Map<String, Object> login(Map<String, String> body) {
        String email = body.get("email");
        String password = body.get("password");
        Optional<UserEntity> entityOptional = userRepository.findByEmail(email);
        if(entityOptional.isEmpty()){
            return Map.of("error","Invalid Credentials");
        }
        UserEntity user = entityOptional.get();
        if(!encoder.matches(password,user.getPassword())){
            return Map.of("error", "Invalid Credentials");
        }
        
        String token = jwtService.generateToken(user);
        return Map.of("token", token);
    }
}