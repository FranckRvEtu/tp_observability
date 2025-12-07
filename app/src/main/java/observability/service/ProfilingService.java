package observability.service;


import observability.DTO.UserStatsDTO;
import observability.enums.ProfileType;
import observability.model.Profile;
import observability.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfilingService {

    private final ProfileRepository profileRepository;

    @Autowired
    public ProfilingService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public void updateProfile(String userId, UserStatsDTO stats) {
        Profile profile = profileRepository.findByUserId(userId).orElse(new Profile());
        if (profile.getId()==null){
            profile.setUserId(userId);
        }

        ProfileType profileType = determineProfileType(stats);

        profile.setProfileType(profileType);
        profileRepository.save(profile);
    }

    private ProfileType determineProfileType(UserStatsDTO stats) {
        if (stats.getExpensiveSearchCount() > stats.getReadCount()) {
            if (stats.getExpensiveSearchCount() > stats.getWriteCount()) {
                return ProfileType.MOST_EXPENSIVE;
            }
            return ProfileType.WRITE_DOMINANT;
        }
        if (stats.getReadCount() > stats.getWriteCount()) {
            return ProfileType.READ_DOMINANT;
        }
        //En cas d'égalité, par défaut
        return  ProfileType.WRITE_DOMINANT;
    }
}
