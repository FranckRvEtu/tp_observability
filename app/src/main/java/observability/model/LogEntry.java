package observability.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "LogEntry")
public class LogEntry {
    @Id
    private String id;
    private String userId;
    private String method;
    private String type;
    private LocalDateTime timestamp;
}
