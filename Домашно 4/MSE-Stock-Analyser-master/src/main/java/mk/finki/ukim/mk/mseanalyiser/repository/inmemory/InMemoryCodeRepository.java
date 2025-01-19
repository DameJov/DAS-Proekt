package mk.finki.ukim.mk.mseanalyiser.repository.inmemory;

import mk.finki.ukim.mk.mseanalyiser.bootstrap.DataHolder;
import mk.finki.ukim.mk.mseanalyiser.model.Codes;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryCodeRepository {
    public Codes getCode()
    {
        return DataHolder.codes.get(0);
    }
}
