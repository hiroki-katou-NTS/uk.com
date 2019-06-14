package nts.uk.file.pr.infra.core.rsdttaxpayee;

import com.aspose.cells.*;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.ctx.pr.file.app.core.rsdttaxpayee.ResidentTexPayeeExportData;
import nts.uk.ctx.pr.file.app.core.rsdttaxpayee.ResidentTexPayeeFileGenerator;
import nts.uk.shr.com.company.CompanyAdapter;
import nts.uk.shr.com.company.CompanyInfor;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@Stateless
public class ResidentTexPayeeAposeFileGenerator extends AsposeCellsReportGenerator implements ResidentTexPayeeFileGenerator {
    private static final String TEMPLATE_FILE = "report/QMM003.xlsx";

    private static final String REPORT_FILE_NAME = "QMM003住民税納付先の登録.pdf";

    private static final int COLUMN_START = 1;

    private static final int MAX_ROWS = 49;


    @Inject
    private CompanyAdapter company;

    @Override
    public void generate(FileGeneratorContext fileContext, List<ResidentTexPayeeExportData> data) {
        try (AsposeCellsReportContext reportContext = this.createContext(TEMPLATE_FILE)) {
            int rowStart = 4;
            Workbook wb = reportContext.getWorkbook();
            WorksheetCollection wsc = wb.getWorksheets();
            Worksheet ws = wsc.get(0);
            ws.setName(TextResource.localize("QMM003_39"));
            //set headler
            // Company name
            String companyName = this.company.getCurrentCompany().map(CompanyInfor::getCompanyName).orElse("");
            PageSetup pageSetup = ws.getPageSetup();
            pageSetup.setHeader(0, "&\"ＭＳ ゴシック\"&10 " + companyName);

            // Output item name
            pageSetup.setHeader(1, "&\"ＭＳ ゴシック\"&16 " + "住民税納付先の登録");

            // Set header date
            DateTimeFormatter fullDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/M/d  H:mm:ss", Locale.JAPAN);
            String currentFormattedDate = LocalDateTime.now().format(fullDateTimeFormatter);
            pageSetup.setHeader(2, "&\"ＭＳ ゴシック\"&10 " + currentFormattedDate+"\npage&P");

            //break page
            Cells cells = ws.getCells();
            // create page
            for (int i = 1; i <  Math.ceil((float)data.size()/(float)MAX_ROWS); i++) {
                wsc.get(wsc.addCopy(0)).setName("sheetName" + i);
            }
            //fill data
            for (int i = 0; i < data.size(); i++) {
                if (i % MAX_ROWS == 0 && i != 0) {
                    Worksheet sheet = wsc.get("sheetName" + i / MAX_ROWS);
                    cells = sheet.getCells();
                    rowStart = 4;
                }

                ResidentTexPayeeExportData e = data.get(i);
                cells.get(rowStart, COLUMN_START).setValue(e.getCode());
                cells.get(rowStart, COLUMN_START + 1).setValue(e.getName());
                cells.get(rowStart, COLUMN_START + 2).setValue(e.getKanaName());
                cells.get(rowStart, COLUMN_START + 3).setValue(getPrefectures(e.getPrefectures()));
                cells.get(rowStart, COLUMN_START + 4).setValue(e.getReportCd());
                cells.get(rowStart, COLUMN_START + 5).setValue(e.getReportName());
                cells.get(rowStart, COLUMN_START + 6).setValue(e.getAccountNumber());
                cells.get(rowStart, COLUMN_START + 7).setValue(e.getSubscriberName());
                cells.get(rowStart, COLUMN_START + 8).setValue(e.getDesignationNum());
                cells.get(rowStart, COLUMN_START + 9).setValue(e.getCompileStationZipcode());
                cells.get(rowStart, COLUMN_START + 10).setValue(e.getCompileStationName());
                rowStart++;
            }
            //export file
            reportContext.processDesigner();
            reportContext.saveAsPdf(this.createNewFile(fileContext, this.getReportName(REPORT_FILE_NAME)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private String getPrefectures(int index){
        switch (index){
            case 1:  {
                return "北海道";
            }
            case 2 :{
                return "青森県";
            }
            case 3 :{
                return "岩手県";
            }
            case 4 :{
                return "宮城県";
            }
            case 5 :{
                return "秋田県";
            }
            case 6 :{
                return "山形県";
            }
            case 7 :{
                return "福島県";
            }
            case 8 :{
                return "茨城県";
            }
            case 9 :{
                return "栃木県";
            }
            case 10 :{
                return "群馬県";
            }
            case 11 :{
                return "埼玉県";
            }
            case 12 :{
                return "千葉県";
            }
            case 13 :{
                return "東京都";
            }
            case 14 :{
                return "神奈川県";
            }
            case 15 :{
                return "新潟県";
            }
            case 16 :{
                return "富山県";
            }
            case 17 :{
                return "石川県";
            }
            case 18 :{
                return "福井県";
            }
            case 19 :{
                return "山梨県";
            }
            case 20 :{
                return "長野県";
            }
            case 21 :{
                return "岐阜県";
            }
            case 22 :{
                return "静岡県";
            }
            case 23 :{
                return "愛知県";
            }
            case 24 :{
                return "三重県";
            }
            case 25 :{
                return "滋賀県";
            }
            case 26 :{
                return "京都府";
            }
            case 27 :{
                return "大阪府";
            }
            case 28 :{
                return "兵庫県";
            }
            case 29 :{
                return "奈良県";
            }
            case 30 :{
                return "和歌山県";
            }
            case 31 :{
                return "鳥取県";
            }
            case 32 :{
                return "島根県";
            }
            case 33 :{
                return "岡山県";
            }
            case 34 :{
                return "広島県";
            }
            case 35 :{
                return "山口県";
            }
            case 36 :{
                return "徳島県";
            }
            case 37 :{
                return "香川県";
            }
            case 38 :{
                return "愛媛県";
            }
            case 39 :{
                return "高知県";
            }
            case 40 :{
                return "福岡県";
            }
            case 41 :{
                return "佐賀県";
            }
            case 42 :{
                return "長崎県";
            }
            case 43 :{
                return "熊本県";
            }
            case 44 :{
                return "大分県";
            }
            case 45 :{
                return "宮崎県";
            }
            case 46 :{
                return "鹿児島県";
            }
            case 47 :{
                return "沖縄県";
            }
            default: return "";
        }

    }

}
