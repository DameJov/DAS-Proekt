package mk.finki.ukim.mk.mseanalyiser.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;

@Controller
public class DateController {

    @GetMapping("/date")
    public String showDate(Model model) {
        // Добивање на денот, месецот и годината
        LocalDate currentDate = LocalDate.now();
        int day = currentDate.getDayOfMonth();
        int month = currentDate.getMonthValue();
        int year = currentDate.getYear();

        // Додавање на дата во Model
        model.addAttribute("day", day);
        model.addAttribute("month", month);
        model.addAttribute("year", year);

        // Враќање на името на HTML шаблонот
        return "datePage";
    }
}
