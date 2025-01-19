package mk.finki.ukim.mk.mseanalyiser.service.impl;

import mk.finki.ukim.mk.mseanalyiser.model.Codes;
import mk.finki.ukim.mk.mseanalyiser.repository.inmemory.InMemoryCodeRepository;
import mk.finki.ukim.mk.mseanalyiser.service.CodeService;
import org.springframework.stereotype.Service;

@Service
public class CodeServiceImpl implements CodeService
{

    private final InMemoryCodeRepository codeRepository;

    public CodeServiceImpl(InMemoryCodeRepository codeRepository) {
        this.codeRepository = codeRepository;
    }

    @Override
    public Codes getCodes() {
        return codeRepository.getCode();
    }
}
