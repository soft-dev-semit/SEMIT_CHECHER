package csit.semit.semitchecker;


import csit.semit.semitchecker.docutils.CalcDocStatistic;
import csit.semit.semitchecker.docutils.DocStatistic;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFStyle;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;

public class DocStatisticCalcTest {

    // Имя ворд-файлу для тестування обробки
//    String docName = "checker_test_file_kr_ua2.docx";
    String docName = "checker_test_2.docx";

    @Test
    void showDocParagraphs() throws IOException {

        Path path = Paths.get(docName);
        CalcDocStatistic calcDocStatistic = new CalcDocStatistic(Files.newInputStream(path),docName,"UA");
        List<XWPFParagraph> paragraphs = calcDocStatistic.getParagraphesDoc();
        for (int i = 0; i < paragraphs.size(); i++) {
            System.out.println("Абзац " + (i + 1) + "(Стиль - "+ paragraphs.get(i).getStyle()  +"): " + paragraphs.get(i).getText());
        }
    }

    @Test
    void showDocDefStyleParagraphs() throws IOException {
        System.out.println("TEST#showDocDefStyleParagraphs");
        Path path = Paths.get(docName);
        CalcDocStatistic calcDocStatistic = new CalcDocStatistic(Files.newInputStream(path),docName,"UA");
        String styleName = "Tablenumber";
        List<XWPFParagraph> paragraphs = calcDocStatistic.getParagraphesDocDefStyle(styleName);
        for (int i = 0; i < paragraphs.size(); i++) {
            System.out.println("Абзац " + (i + 1) + "(Стиль - "+ paragraphs.get(i).getStyleID()  +"): " + paragraphs.get(i).getText());
        }
    }

    @Test
    void calcParams() throws IOException {
        Path path = Paths.get(docName);
        CalcDocStatistic calcDocStatistic = new CalcDocStatistic(Files.newInputStream(path),docName,"UA");
        System.out.println("CountPages = "+ calcDocStatistic.getCountPages());
        System.out.println("CountFigures = "+ calcDocStatistic.getCountFigures());
        System.out.println("CountTables = "+ calcDocStatistic.getCountTables());
        System.out.println("CountSources = "+ calcDocStatistic.getCountSources());
        System.out.println("CountCountAppendixes = "+ calcDocStatistic.getCountAppendixes());

    }

    @Test
    void testPrepareAbstract() throws IOException {
        Path path = Paths.get(docName);
        CalcDocStatistic calcDocStatistic = new CalcDocStatistic(Files.newInputStream(path), docName, "UA");
        DocStatistic statistic = calcDocStatistic.calcParam();
        System.out.println("CountPages = "+ statistic.getCountPages());
        System.out.println("CountFigures = "+ statistic.getCountFigures());
        System.out.println("CountTables = "+ statistic.getCountTables());
        System.out.println("CountSources = "+ statistic.getCountSources());
        System.out.println("CountCountAppendixes = "+ statistic.getCountAppendixes());
        System.out.println("AbstractRow = "+ statistic.getAbstractRow());
    }


    @Test
    void showUsedStyles() throws IOException {
        Path path = Paths.get(docName);
        CalcDocStatistic calcDocStatistic = new CalcDocStatistic(Files.newInputStream(path), docName, "UA");
        Set<XWPFStyle> usedStyles = calcDocStatistic.getUsedStyles();
        if (usedStyles.isEmpty()) {
            System.out.println("В документе нет использованных стилей.");
        } else {
            usedStyles.forEach(style->System.out.println("ID: " + style.getStyleId() + " | Имя: " +
                    ((style != null) ? style.getName() : "Unknown")));
        }
    }

}
