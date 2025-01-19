package mk.finki.ukim.mk.mseanalyiser.bootstrap;

import jakarta.annotation.PostConstruct;
import mk.finki.ukim.mk.mseanalyiser.model.Codes;
import mk.finki.ukim.mk.mseanalyiser.model.User;
import mk.finki.ukim.mk.mseanalyiser.repository.inmemory.InMemoryUserRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataHolder {
    public static List<User> users = null;
    public static List<Codes> codes = null;


    private final InMemoryUserRepository userRepository;


    public DataHolder(InMemoryUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void init()
    {
        users = new ArrayList<>();
        users.add(new User("DameJov","dj","jov.damjan@gmail.com"));
        users.add(new User("Nikola","nt","nikola@gmail.com"));

        codes=new ArrayList<>();
        codes.add(new Codes(List.of("ADIN", "ALK", "ALKB", "AMEH", "APTK", "ATPP", "AUMK", "BANA", "BGOR", "BIKF", "BIM", "BLTU", "CBNG", "CDHV", "CEVI", "CKB", "CKBKO")));


    }
}
