package csit.semit.semitchecker.controller;

import csit.semit.semitchecker.docutils.CalcDocStatistic;
import csit.semit.semitchecker.docutils.DocStatistic;
import csit.semit.semitchecker.serviceenums.Lang;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

@Controller
public class MainController {

    @GetMapping(path = "/")
    public String showInvitePage(Model model) {
        return "InvitePage";
    }

    @GetMapping(path = "/{localInterface}/mainpage")
    public String viewIndexPage(Model model,
                                @PathVariable String localInterface,
                                HttpServletRequest request) {
        //System.out.println("localInterface="+localInterface);

        // Встановлення локали
        Locale locale = Lang.valueOf(localInterface).getLocale();
        HttpSession session = request.getSession();
        session.setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, locale);

        DocStatistic statistic = null;
        model.addAttribute("statistic", statistic);
        return "SemitCheckerMainPage";
    }

    @PostMapping("/{localInterface}/choose-file")
    public String checkDocxReport(@RequestParam MultipartFile file,
                                  @PathVariable String localInterface,
                                  @RequestParam String localeDoc,
                                  @RequestParam String localeWord,
                                  HttpServletRequest request,
                                  Model model) {


        String errorMessage = null;
        DocStatistic statistic = null;
        String docLocale = localeDoc;
        String wordLocale = localeWord;
        String docName = file.getOriginalFilename();
        CalcDocStatistic paramsCalc = null;
        try {
            InputStream inputStream = file.getInputStream();
            paramsCalc = new CalcDocStatistic(inputStream, docName, docLocale, wordLocale);
            statistic = paramsCalc.calcParam();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace(System.err);
            errorMessage = "Проблеми із обробкою файлу";
        }
        // Встановлення локали
        Locale locale = Lang.valueOf(localInterface).getLocale();
        HttpSession session = request.getSession();
        session.setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, locale);

        model.addAttribute("statistic", statistic);
        model.addAttribute("error_message", errorMessage);
        return "SemitCheckerMainPage";
    }


}
