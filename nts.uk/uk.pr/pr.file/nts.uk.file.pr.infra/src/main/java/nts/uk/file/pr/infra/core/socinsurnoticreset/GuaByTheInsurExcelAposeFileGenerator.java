package nts.uk.file.pr.infra.core.socinsurnoticreset;

import com.aspose.cells.PageSetup;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.WorksheetCollection;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.ctx.pr.file.app.core.socialinsurnoticreset.ExportDataCsv;
import nts.uk.ctx.pr.file.app.core.socialinsurnoticreset.GuaByTheInsurExportDto;
import nts.uk.ctx.pr.file.app.core.socialinsurnoticreset.GuaByTheInsurExportExcelGenerator;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

import javax.ejb.Stateless;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@Stateless
public class GuaByTheInsurExcelAposeFileGenerator extends AsposeCellsReportGenerator implements GuaByTheInsurExportExcelGenerator {

    private static final String TEMPLATE_FILE = "report/被保険者資格取得届_帳票テンプレート_QSI001.xlsx";

    private static final String REPORT_FILE_NAME = "被保険者資格取得届_帳票テンプレート_QSI001.xlsx";

    private static final int FIRST_ROW_FILL = 3;

    private static final String TITLE = "法定調書用会社の登録";


    @Override
    public void generate(FileGeneratorContext fileContext, ExportDataCsv exportData) {
        try (AsposeCellsReportContext reportContext = this.createContext(TEMPLATE_FILE)) {
            Workbook wb = reportContext.getWorkbook();
            WorksheetCollection wsc = wb.getWorksheets();
            Worksheet ws = wsc.get(0);
            this.writeFileExcel(ws, exportData.listContent, "");
            reportContext.processDesigner();
            reportContext.saveAsExcel(this.createNewFile(fileContext, this.getReportName(REPORT_FILE_NAME)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void writeFileExcel(Worksheet ws, List<GuaByTheInsurExportDto> exportData, String companyName) {

        final int MAX_LINE = 36;
        int rowIndex = 3;
        int count = 0;
        int numberRow = 39;
        int sourceRowIndex = 0;
        int destinationRowIndex = 39;

        // Index column to fill data
        final int COLUMN_INDEX_C1_10 = 32;
        //row index == 8
        final int COLUMN_INDEX_C1_1 = 14;
        final int COLUMN_INDEX_C1_2 = 28;
        final int COLUMN_INDEX_C1_3 = 46;
        //row index ==    17
        final int COLUMN_INDEX_C1_4 = 18;
        //row index ==    20
        final int COLUMN_INDEX_C1_5 = 14;
        //row index ==    24
        final int COLUMN_INDEX_C1_6 = 14;
        //row index ==    31
        final int COLUMN_INDEX_C1_7 = 14;
        //row index ==    40
        final int COLUMN_INDEX_C1_8 = 14;
        //row index ==    49
        final int COLUMN_INDEX_C1_9 = 22;
        //row index ==    55
        final int COLUMN_INDEX_C2_1 = 14;
        //row index ==    58
        final int COLUMN_INDEX_C2_2 = 28;
        //row index ==    58
        final int COLUMN_INDEX_C2_3 = 53;
        //row index ==    55
        final int COLUMN_INDEX_C2_4 = 28;
        //row index ==    55
        final int COLUMN_INDEX_C2_5 = 53;
        //row index == 58
        final int COLUMN_INDEX_C2_6 = 84;
        //row index == 60
        final int COLUMN_INDEX_C2_7 = 84;
        //row index == 62
        final int COLUMN_INDEX_C2_8 = 84;
        //row index == 60
        final int COLUMN_INDEX_C2_9 = 96;
        //row index == 56
        final int COLUMN_INDEX_C2_10 = 114;
        //row index == 58
        final int COLUMN_INDEX_C2_11 = 114;
        //row index == 60
        final int COLUMN_INDEX_C2_12 = 114;
        //row index == 56
        final int COLUMN_INDEX_C2_13 = 121;
        //row index == 58
        final int COLUMN_INDEX_C2_14 = 121;
        //row index == 60
        final int COLUMN_INDEX_C2_15 = 121;
        //row index == 66
        final int COLUMN_INDEX_C2_16 = 14;
        //row index == 68
        final int COLUMN_INDEX_C2_17 = 14;
        //row index == 70
        final int COLUMN_INDEX_C2_18 = 14;
        //row index == 65
        final int COLUMN_INDEX_C2_19 = 52;
        //row index == 69
        final int COLUMN_INDEX_C2_20 = 85;
        //row index == 69
        final int COLUMN_INDEX_C2_21 = 96;

        //row index == 65
        final int COLUMN_INDEX_C2_22 = 112;
        //row index == 65
        final int COLUMN_INDEX_C2_23 = 122;

        //row index == 74
        final int COLUMN_INDEX_C2_24 = 19;
        //row index == 78
        final int COLUMN_INDEX_C2_25 = 19;

        //row index == 77
        final int COLUMN_INDEX_C2_26 = 44;

        //row index == 84
        final int COLUMN_INDEX_C2_27 = 16;
        //row index == 84
        final int COLUMN_INDEX_C2_28 = 30;
        //row index == 77
        final int COLUMN_INDEX_C2_29 = 30;


        //row index == 77
        final int COLUMN_INDEX_C2_30 = 87;
        //row index == 79
        final int COLUMN_INDEX_C2_31 = 87;

        //row index == 75
        final int COLUMN_INDEX_C2_32 = 106;
        //row index == 77
        final int COLUMN_INDEX_C2_33 = 106;
        //row index == 79
        final int COLUMN_INDEX_C2_34 = 106;

        //row index == 79
        final int COLUMN_INDEX_C2_35 = 115;

        //row index == 82
        final int COLUMN_INDEX_C2_36 = 115;
        //row index == 84
        final int COLUMN_INDEX_C2_37 = 115;
        //row index == 86
        final int COLUMN_INDEX_C2_38 = 115;

        //row index == 86
        final int COLUMN_INDEX_C2_39 = 112;


        for (int i = 1; i < Math.ceil((float) exportData.size() / (float) MAX_LINE); i++) {
            try {
                ws.getCells().copyRows(ws.getCells(), sourceRowIndex, destinationRowIndex, numberRow);
            } catch (Exception e) {

            }
            numberRow = numberRow + MAX_LINE;
            sourceRowIndex = sourceRowIndex + MAX_LINE;
            destinationRowIndex = destinationRowIndex + MAX_LINE;
        }

        for (GuaByTheInsurExportDto element : exportData) {
            ws.getCells().get(rowIndex, COLUMN_INDEX_C1_1).putValue("");
            ws.getCells().get(rowIndex, COLUMN_INDEX_C1_2).putValue("");
//            ws.getCells().get(rowIndex, COLUMN_INDEX_C1_3).putValue(element.getOfficeNumber());
//            ws.getCells().get(rowIndex, COLUMN_INDEX_C1_4).putValue(element.getPostalCode());
//            ws.getCells().get(rowIndex, COLUMN_INDEX_C1_5).putValue(element.getOfficeAndressOne());
//            ws.getCells().get(rowIndex, COLUMN_INDEX_C1_6).putValue(element.getOfficeAndressTwo());
//            ws.getCells().get(rowIndex, COLUMN_INDEX_C1_7).putValue(element.getBussinessName());
//            ws.getCells().get(rowIndex, COLUMN_INDEX_C1_8).putValue(element.getBussinessName2());
//            ws.getCells().get(rowIndex, COLUMN_INDEX_C1_9).putValue(element.getPhoneNumber());
//            ws.getCells().get(rowIndex, COLUMN_INDEX_C2_1).putValue("COLUMN_INDEX_C2_1");
//            ws.getCells().get(rowIndex, COLUMN_INDEX_C2_2).putValue(element.getSubmittedName());
//            ws.getCells().get(rowIndex, COLUMN_INDEX_C2_3).putValue(element.getSubmittedName());
//            ws.getCells().get(rowIndex, COLUMN_INDEX_C2_4).putValue(element.getSubmittedName());
//            ws.getCells().get(rowIndex, COLUMN_INDEX_C2_5).putValue(element.getSubmittedName());
            ws.getCells().get(rowIndex, COLUMN_INDEX_C2_6).putValue("COLUMN_INDEX_C2_6");
            ws.getCells().get(rowIndex, COLUMN_INDEX_C2_7).putValue("COLUMN_INDEX_C2_7");
            ws.getCells().get(rowIndex, COLUMN_INDEX_C2_8).putValue("COLUMN_INDEX_C2_8");
            ws.getCells().get(rowIndex, COLUMN_INDEX_C2_9).putValue("COLUMN_INDEX_C2_9");
            ws.getCells().get(rowIndex, COLUMN_INDEX_C2_10).putValue("COLUMN_INDEX_C2_10");
            ws.getCells().get(rowIndex, COLUMN_INDEX_C2_11).putValue("COLUMN_INDEX_C2_11");
            ws.getCells().get(rowIndex, COLUMN_INDEX_C2_12).putValue("COLUMN_INDEX_C2_12");
            ws.getCells().get(rowIndex, COLUMN_INDEX_C2_13).putValue("COLUMN_INDEX_C2_13");
            ws.getCells().get(rowIndex, COLUMN_INDEX_C2_14).putValue("COLUMN_INDEX_C2_14");
            ws.getCells().get(rowIndex, COLUMN_INDEX_C2_15).putValue("COLUMN_INDEX_C2_15");
            ws.getCells().get(rowIndex, COLUMN_INDEX_C2_16).putValue("COLUMN_INDEX_C2_16");
            ws.getCells().get(rowIndex, COLUMN_INDEX_C2_17).putValue("COLUMN_INDEX_C2_17");
            ws.getCells().get(rowIndex, COLUMN_INDEX_C2_18).putValue("COLUMN_INDEX_C2_18");
            ws.getCells().get(rowIndex, COLUMN_INDEX_C2_19).putValue("COLUMN_INDEX_C2_19");
            ws.getCells().get(rowIndex, COLUMN_INDEX_C2_20).putValue("COLUMN_INDEX_C2_20");
            ws.getCells().get(rowIndex, COLUMN_INDEX_C2_21).putValue("COLUMN_INDEX_C2_21");
            ws.getCells().get(rowIndex, COLUMN_INDEX_C2_22).putValue("COLUMN_INDEX_C2_22");
            ws.getCells().get(rowIndex, COLUMN_INDEX_C2_23).putValue("COLUMN_INDEX_C2_23");
            ws.getCells().get(rowIndex, COLUMN_INDEX_C2_24).putValue("COLUMN_INDEX_C2_24");
            ws.getCells().get(rowIndex, COLUMN_INDEX_C2_25).putValue("COLUMN_INDEX_C2_25");
            ws.getCells().get(rowIndex, COLUMN_INDEX_C2_26).putValue("COLUMN_INDEX_C2_26");
            ws.getCells().get(rowIndex, COLUMN_INDEX_C2_27).putValue("COLUMN_INDEX_C2_27");
            ws.getCells().get(rowIndex, COLUMN_INDEX_C2_28).putValue("COLUMN_INDEX_C2_28");
            ws.getCells().get(rowIndex, COLUMN_INDEX_C2_29).putValue("COLUMN_INDEX_C2_29");
            ws.getCells().get(rowIndex, COLUMN_INDEX_C2_30).putValue("COLUMN_INDEX_C2_30");
            ws.getCells().get(rowIndex, COLUMN_INDEX_C2_31).putValue("COLUMN_INDEX_C2_31");
            ws.getCells().get(rowIndex, COLUMN_INDEX_C2_32).putValue("COLUMN_INDEX_C2_32");
            ws.getCells().get(rowIndex, COLUMN_INDEX_C2_33).putValue("COLUMN_INDEX_C2_33");
            ws.getCells().get(rowIndex, COLUMN_INDEX_C2_34).putValue("COLUMN_INDEX_C2_34");
            ws.getCells().get(rowIndex, COLUMN_INDEX_C2_35).putValue("COLUMN_INDEX_C2_35");
            ws.getCells().get(rowIndex, COLUMN_INDEX_C2_36).putValue("COLUMN_INDEX_C2_36");
            ws.getCells().get(rowIndex, COLUMN_INDEX_C2_37).putValue("COLUMN_INDEX_C2_37");
            ws.getCells().get(rowIndex, COLUMN_INDEX_C2_38).putValue("COLUMN_INDEX_C2_38");
            ws.getCells().get(rowIndex, COLUMN_INDEX_C2_39).putValue("COLUMN_INDEX_C2_39");


            rowIndex++;
            count++;
        }


    }

    private void settingHeader(Worksheet ws, String companyName) {

        // Set print page
        PageSetup pageSetup = ws.getPageSetup();
        pageSetup.setHeader(0, "&\"ＭＳ ゴシック\"&10 " + companyName);
        pageSetup.setHeader(1, "&\"ＭＳ ゴシック\"&16 " + TITLE);
        // Set header date
        DateTimeFormatter fullDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/M/d  HH:mm:ss", Locale.JAPAN);
        pageSetup.setHeader(2, "&\"ＭＳ ゴシック\"&10 " + LocalDateTime.now().format(fullDateTimeFormatter) + "\npage&P ");

    }
}
