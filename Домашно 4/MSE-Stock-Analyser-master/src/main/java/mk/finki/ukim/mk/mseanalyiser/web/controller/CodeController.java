package mk.finki.ukim.mk.mseanalyiser.web.controller;

import org.springframework.ui.Model;;
import mk.finki.ukim.mk.mseanalyiser.service.CodeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/codes")
public class CodeController {
    private final CodeService codeService;

    public CodeController(CodeService codeService) {
        this.codeService = codeService;
    }

    @GetMapping
    public String getCodes(Model model)
    {
        model.addAttribute("stockCodes", codeService.getCodes().getCodes());
        return "codes";
    }
    @GetMapping("/{code}")
    public String tablePage(Model model, @PathVariable("code") String code)
    {
        return "table";
    }
    @PostMapping
    public String goBack()
    {
        return "redirect:/codes";
    }
}
