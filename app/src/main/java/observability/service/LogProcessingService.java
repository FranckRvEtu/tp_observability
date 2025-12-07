package observability.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import observability.DTO.UserStatsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class LogProcessingService {

    private final ProfilingService profilingService;
    private final ObjectMapper objectMapper;

    @Value("${logging.profile.file:logs/profile.json}")
    private String logFilePath;

    // On injecte le ProfileService (le métier), pas le Repository !
    @Autowired
    public LogProcessingService(ProfilingService profileService) {
        this.profilingService = profileService;
        this.objectMapper = new ObjectMapper();
    }

    public void parse(){
        File logFile = new File(logFilePath);

        if(!logFile.exists()){
            System.err.println("Log file does not exist");
            return;
        }

        Map<String, UserStatsDTO> statsMap = new HashMap<>();
        try(BufferedReader br = new BufferedReader(new FileReader(logFile))){
            String line;
            while((line = br.readLine()) != null){
                try{
                    JsonNode jsonNode = objectMapper.readTree(line);
                    String userId = jsonNode.get("user").asText();
                    String action = jsonNode.get("action").asText();

                    statsMap.putIfAbsent(userId, new UserStatsDTO());
                    statsMap.get(userId).increment(action);

                }catch(Exception e){
                    System.err.println("Error while parsing log file :" + line);
                }
            }
        }
        catch(IOException e){
            e.printStackTrace();
            return;
        }
        System.err.println("Finished parsing log file");

        //TODO
        statsMap.forEach(profilingService::updateProfile);
    }
}
