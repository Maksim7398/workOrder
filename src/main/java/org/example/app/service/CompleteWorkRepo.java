package org.example.app.service;

import org.apache.poi.openxml4j.opc.PackagePart;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlbeans.XmlCursor;
import org.example.web.model.CompleteWork;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblLayoutType;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblLayoutType;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CompleteWorkRepo implements WorkRepository<CompleteWork> {

    private final List<CompleteWork> completeWorks = new ArrayList<>();

    @Override
    public List<CompleteWork> getWorksList() {
        return new ArrayList<>(completeWorks);
    }



    @Override
    public void store(CompleteWork typesWorks) {
        completeWorks.add(typesWorks);
    }

    @Override
    public void clear() {
        completeWorks.clear();
    }


    @Override
    public void replace(List<CompleteWork> works) {


        String docFile = "C:\\Users\\Acer\\IdeaProjects\\Work-order\\src\\main\\resources\\outZakaz.docx";
        File file = new File(docFile);
        try (FileInputStream fis = new FileInputStream(file.getAbsolutePath())) {
            XWPFDocument doc = new XWPFDocument(fis);
            List<PackagePart> allEmbeddedParts = doc.getAllEmbeddedParts();
            List<XWPFParagraph> paragraphs = doc.getParagraphs();
            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            String date = LocalDate.now().format(format);
            XmlCursor cursor = paragraphs.get(11).getCTP().newCursor();

            System.out.println(paragraphs.size());
            XWPFParagraph paragraph = paragraphs.get(14);
            XWPFTable table = paragraph.getDocument().insertNewTbl(cursor);

            CTTblLayoutType type = table.getCTTbl().getTblPr().addNewTblLayout();
            type.setType(STTblLayoutType.AUTOFIT);


            XWPFRun run1 = paragraph.createRun();
            run1.setFontFamily("Times New Roman");
            run1.setFontSize(14);

            XWPFTableRow tableRow1 = table.getRow(0);
            tableRow1.getCell(0).setText("Код");
            tableRow1.addNewTableCell().setText("Наименование работ");
            tableRow1.addNewTableCell().setText("Цена");
            tableRow1.addNewTableCell().setText("Итог");



            int sum = 0;
            for (CompleteWork completeWork: works){
                XWPFTableRow tableRow2 = table.createRow();

                String code = completeWork.getCode();
                String name = completeWork.getName();
                String price = completeWork.getPrice();

                int summa = Integer.parseInt(price);
                sum += summa;
                tableRow2.getCell(0).setText(code);
                tableRow2.getCell(1).setText(name);
                tableRow2.getCell(2).setText(price);
            }
            XWPFTableRow tableRow3 = table.createRow();
            tableRow3.getCell(3).setText(String.valueOf(sum));

            for (XWPFParagraph paragraph2 : paragraphs) {
                List<XWPFRun> runs = paragraph2.getRuns();
                for (XWPFRun run : runs) {
                    String text = run.getText(0);
                    if (text != null) {
                        text = text.replace("summa", String.valueOf(sum));
                        text = text.replace("date", date);
                        text = text.replace("master",new CompleteWork().getMaster());
                        run.setText(text, 0);
                    }
                }
            }



            doc.write(Files.newOutputStream
                    (Paths.get("C:\\Users\\Acer\\IdeaProjects\\Work-order\\src\\main\\resources\\outZakaz.docx")));


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }





}
