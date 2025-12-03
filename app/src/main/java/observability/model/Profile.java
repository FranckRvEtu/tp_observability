package observability.model;

import observability.enums.ProfileType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "profile")
public class Profile {
    @Id
    private String id;
    private String userId;
    private String email;
    private ProfileType profileType;

    public Profile(String id, String userId, String email, ProfileType profileType) {
        this.id = id;
        this.userId = userId;
        this.email = email;
        this.profileType = profileType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ProfileType getProfileType() {
        return profileType;
    }

    public void setProfileType(ProfileType profileType) {
        this.profileType = profileType;
    }
}
