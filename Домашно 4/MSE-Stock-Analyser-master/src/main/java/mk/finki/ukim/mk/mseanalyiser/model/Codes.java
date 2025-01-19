package mk.finki.ukim.mk.mseanalyiser.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class Codes {
    private List<String> codes;
    private Long id;

    public Codes(List<String> codes) {
        this.codes = codes;
    }

    public List<String> getCodes() {
        return codes;
    }

    public void setCodes(List<String> codes) {
        this.codes = codes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
