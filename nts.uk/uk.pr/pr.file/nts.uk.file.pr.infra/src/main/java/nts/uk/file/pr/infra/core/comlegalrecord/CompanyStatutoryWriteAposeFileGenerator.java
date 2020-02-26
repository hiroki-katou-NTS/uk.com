package nts.uk.file.pr.infra.core.comlegalrecord;

import com.aspose.cells.*;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.ctx.pr.file.app.core.comlegalrecord.CompanyStatutoryWriteExportData;
import nts.uk.ctx.pr.file.app.core.comlegalrecord.CompanyStatutoryWriteExportGenerator;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

import javax.ejb.Stateless;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@Stateless
public class CompanyStatutoryWriteAposeFileGenerator extends AsposeCellsReportGenerator implements CompanyStatutoryWriteExportGenerator {

    private static final String TEMPLATE_FILE = "report/QMM035.xlsx";

    private static final String REPORT_FILE_NAME = "QMM035法定調書用会社の登録.xlsx";

    private static final int FIRST_ROW_FILL = 3;

    private static final int CODE = 1;
    private static final int NAME = 2;
    private static final int KANANAME = 3;
    private static final int CORPORATENUMBER = 4;
    private static final int REPRESENTATIVE_NAME = 5;
    private static final int REPRESENTATIVE_POSITION = 6;
    private static final int POSTALCODE = 7;
    private static final int ADDRESS1 = 8;
    private static final int ADDRESSKANA1 = 9;
    private static final int ADDRESS2 = 10;
    private static final int ADDRESSKANA2 = 11;
    private static final int PHONENUMBER = 12;
    private static final int ACCOUNT_MANANAME = 13;
    private static final int CONTACT_CLASS = 14;
    private static final int CONTACT_NAME = 15;
    private static final int CONTRACT_PHONE_NUMBER = 16;
    private static final int ACCOUNTING_OFF_NAME = 17;
    private static final int ACCOUNTING_OFF_NAME_PHONE_NUMBER = 18;
    private static final int SALAPAY_METHOD_DUE_DATE1 = 19;
    private static final int SALAPAY_METHOD_DUE_DATE2 = 20;
    private static final int SALAPAY_METHOD_DUE_DATE3 = 21;
    private static final int BUSINESSLINE1 = 22;
    private static final int BUSINESSLINE2 = 23;
    private static final int BUSINESSLINE3 = 24;
    private static final int TAX_OFFICE = 25;
    private static final int NAME_BANK_TRANFEINS = 26;
    private static final int VIBLOCAFININS = 27;
    private static final String TITLE = "法定調書用会社の登録";


    @Override
    public void generate(FileGeneratorContext fileContext, List<CompanyStatutoryWriteExportData> exportData, String companyName) {
        try(AsposeCellsReportContext reportContext = this.createContext(TEMPLATE_FILE)){
            Workbook wb = reportContext.getWorkbook();
            WorksheetCollection wsc = wb.getWorksheets();
            this.writeFileExcel(wsc,exportData,companyName);
            reportContext.processDesigner();
            reportContext.saveAsExcel(this.createNewFile(fileContext,this.getReportName(REPORT_FILE_NAME)));
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void writeFileExcel(WorksheetCollection wsc, List<CompanyStatutoryWriteExportData> exportData, String companyName){
        try {
            int rowIndex = FIRST_ROW_FILL;
            Worksheet ws = wsc.get(0);
            int lineCopy = 3;
            this.settingHeader(ws, companyName);
            for (int i = 0; i < exportData.size(); i++) {
                CompanyStatutoryWriteExportData entity = exportData.get(i);
                if (i % 2 == 0) {
                    ws.getCells().copyRows(ws.getCells(), rowIndex, rowIndex + lineCopy - 1, lineCopy);
                }
                if (i == exportData.size() - 1) {
                    ws.getCells().deleteRows(rowIndex, exportData.size() % 2 == 0 ? 3 : 4);
                }
                ws.getCells().get(rowIndex, CODE).putValue(entity.getCode());
                ws.getCells().get(rowIndex, NAME).putValue(entity.getName());
                ws.getCells().get(rowIndex, KANANAME).putValue(entity.getKanaName());
                ws.getCells().get(rowIndex, CORPORATENUMBER).putValue(entity.getCorporateNumber());
                ws.getCells().get(rowIndex, REPRESENTATIVE_NAME).putValue(entity.getClubRepresentativeName());
                ws.getCells().get(rowIndex, REPRESENTATIVE_POSITION).putValue(entity.getClubRepresentativePosition());
                ws.getCells().get(rowIndex, POSTALCODE).putValue(entity.getPostalCode());
                ws.getCells().get(rowIndex, ADDRESS1).putValue(entity.getAddress1());
                ws.getCells().get(rowIndex, ADDRESSKANA1).putValue(entity.getAddressKana1());
                ws.getCells().get(rowIndex, ADDRESS2).putValue(entity.getAddress2());
                ws.getCells().get(rowIndex, ADDRESSKANA2).putValue(entity.getAddressKana2());
                ws.getCells().get(rowIndex, PHONENUMBER).putValue(entity.getPhoneNumber());
                ws.getCells().get(rowIndex, ACCOUNT_MANANAME).putValue(entity.getAccountManagerName());
                ws.getCells().get(rowIndex, CONTACT_CLASS).putValue(entity.getContactClass());
                ws.getCells().get(rowIndex, CONTACT_NAME).putValue(entity.getContactName());
                ws.getCells().get(rowIndex, CONTRACT_PHONE_NUMBER).putValue(entity.getContactPhoneNumber());
                ws.getCells().get(rowIndex, ACCOUNTING_OFF_NAME).putValue(entity.getAccountingOfficeName());
                ws.getCells().get(rowIndex, ACCOUNTING_OFF_NAME_PHONE_NUMBER).putValue(entity.getAccountingOfficeTelephoneNumber());
                ws.getCells().get(rowIndex, SALAPAY_METHOD_DUE_DATE1).putValue(entity.getSalaryPaymentMethodAndDueDate1());
                ws.getCells().get(rowIndex, SALAPAY_METHOD_DUE_DATE2).putValue(entity.getSalaryPaymentMethodAndDueDate2());
                ws.getCells().get(rowIndex, SALAPAY_METHOD_DUE_DATE3).putValue(entity.getSalaryPaymentMethodAndDueDate3());
                ws.getCells().get(rowIndex, BUSINESSLINE1).putValue(entity.getBusinessLine1());
                ws.getCells().get(rowIndex, BUSINESSLINE2).putValue(entity.getBusinessLine2());
                ws.getCells().get(rowIndex, BUSINESSLINE3).putValue(entity.getBusinessLine3());
                ws.getCells().get(rowIndex, TAX_OFFICE).putValue(entity.getTaxOffice());
                ws.getCells().get(rowIndex, NAME_BANK_TRANFEINS).putValue(entity.getNameBankTransferInstitution());
                ws.getCells().get(rowIndex, VIBLOCAFININS).putValue(entity.getVibrantLocationFinancialInstitutions());
                rowIndex++;
            }
            if(exportData.size() == 0) {
                ws.getCells().deleteRows(rowIndex, 2);
            }

            if(exportData.size() % 2 == 0 && exportData.size() > 1) {
                int totalColumn = 27;
                int columnStart = 1;
                for(int column = columnStart ; column < totalColumn + columnStart; column++) {
                    Style style = wsc.get(0).getCells().get(rowIndex - 1, column).getStyle();
                    style.setPattern(BackgroundType.SOLID);
                    style.setForegroundColor(Color.fromArgb(216,228, 188));
                    wsc.get(0).getCells().get(rowIndex - 1, column).setStyle(style);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void settingHeader(Worksheet ws, String companyName){

        // Set print page
        PageSetup pageSetup = ws.getPageSetup();
        pageSetup.setHeader(0, "&\"ＭＳ ゴシック\"&10 " + companyName);
        pageSetup.setHeader(1, "&\"ＭＳ ゴシック\"&16 " + TITLE);
        // Set header date
        DateTimeFormatter fullDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/M/d  HH:mm:ss", Locale.JAPAN);
        pageSetup.setHeader(2, "&\"ＭＳ ゴシック\"&10 " + LocalDateTime.now().format(fullDateTimeFormatter) + "\npage&P ");

    }
}
