package nts.uk.file.pr.infra.core.wageprovision.formula;

import com.aspose.cells.*;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.pr.core.dom.socialinsurance.welfarepensioninsurance.InsurancePremiumFractionClassification;
import nts.uk.ctx.pr.core.dom.wageprovision.formula.*;
import nts.uk.ctx.pr.core.dom.wageprovision.formula.FormulaType;
import nts.uk.ctx.pr.file.app.core.socialinsurance.healthinsurance.HealthInsuranceExportData;
import nts.uk.ctx.pr.file.app.core.socialinsurance.healthinsurance.HealthInsuranceFileGenerator;
import nts.uk.ctx.pr.file.app.core.wageprovision.formula.FormulaExportData;
import nts.uk.ctx.pr.file.app.core.wageprovision.formula.FormulaFileGenerator;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

import javax.ejb.Stateless;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@Stateless
public class FormulaAposeFileGenerator extends AsposeCellsReportGenerator implements FormulaFileGenerator {

    private static final String TEMPLATE_FILE = "report/QMM017.xlsx";
    private static final String REPORT_FILE_EXTENSION = ".xlsx";
    private static final String FILE_NAME = "QMM017計算式の登録";
    private static final String TITLE = "計算式の登録 ";
    private static final int RECORD_IN_PAGE = 71;
    private static final int LINE_IN_PAGE = 74;
    private static final String SHEET_NAME = "マスタリスト";

    @Override
    public void generate(FileGeneratorContext generatorContext, FormulaExportData exportData) {
        try (AsposeCellsReportContext reportContext = this.createContext(TEMPLATE_FILE)) {
            Workbook workbook = reportContext.getWorkbook();
            WorksheetCollection worksheets = workbook.getWorksheets();
            worksheets.get(0).setName(SHEET_NAME);
            int page = exportData.getFormulas().size() == RECORD_IN_PAGE ? 0 : exportData.getFormulas().size() / RECORD_IN_PAGE;
            createTable(worksheets, page, exportData.getCompanyName());
            printData(worksheets, exportData.getFormulas(), exportData.getFormulas(), exportData.getTargetItems());
            worksheets.setActiveSheetIndex(0);
            reportContext.processDesigner();
            reportContext.saveAsExcel(this.createNewFile(generatorContext,
                    FILE_NAME + GeneralDateTime.now().toString("yyyyMMddHHmmss") + REPORT_FILE_EXTENSION));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void createTable(WorksheetCollection worksheets,int pageMonth, String companyName) throws Exception {
        Worksheet worksheet = worksheets.get(0);
        //settingPage(worksheet, companyName);
        for(int i = 0 ; i< pageMonth; i++) {
            worksheet.getCells().copyRows(worksheet.getCells(), 0, LINE_IN_PAGE * (i+1), LINE_IN_PAGE + 1 );
        }
    }

    private void settingPage(Worksheet worksheet, String companyName){
        PageSetup pageSetup = worksheet.getPageSetup();
        pageSetup.setPaperSize(PaperSizeType.PAPER_A_4);
        pageSetup.setOrientation(PageOrientationType.LANDSCAPE);
        pageSetup.setHeader(0, "&\"ＭＳ ゴシック\"&10 " + companyName);
        pageSetup.setHeader(1, "&\"ＭＳ ゴシック\"&16 "+ TITLE);
        DateTimeFormatter fullDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/M/d  H:mm:ss", Locale.JAPAN);
        String currentFormattedDate = LocalDateTime.now().format(fullDateTimeFormatter);
        pageSetup.setHeader(2, "&\"ＭＳ ゴシック\"&10 " + currentFormattedDate+"\npage&P");
        /*pageSetup.setFitToPagesTall(1);
        pageSetup.setFitToPagesWide(1);*/
    }

    private void printData(WorksheetCollection worksheets, List<Object[]> data, List<Object[]> fornula, List<Object[]> targetItem) {
        int numColumn = 14;
        int columnStart = 1;
        fillData(worksheets, data, fornula, targetItem, numColumn, columnStart);
    }

    private void fillData(WorksheetCollection worksheets, List<Object[]> data, List<Object[]> formula,
                          List<Object[]> targetItem, int numColumn, int startColumn) {
        try {
            int rowStart = 0;
            Worksheet sheet = worksheets.get(0);
            Cells cells = sheet.getCells();
            for (int i = 0; i < data.size(); i++) {
                if(i % RECORD_IN_PAGE == 0) {
                    rowStart += 3;
                }
                Object[] dataRow = data.get(i);
                for (int j = 0; j < numColumn; j++) {
                    switch (j) {
                        case 4:
                            cells.get(rowStart, j + startColumn).setValue(dataRow[j] != null && ((BigDecimal) dataRow[j]).intValue() == 0
                                    ? TextResource.localize("Enum_MasterBranchUse_NOT_USE") : TextResource.localize("Enum_MasterBranchUse_USE"));
                            break;
                        case 5:
                            cells.get(rowStart, j+ startColumn).setValue(dataRow[5] != null ? EnumAdaptor.valueOf(((BigDecimal) dataRow[j]).intValue(), NestedUseCls.class).nameId : "");
                            break;
                        case 6:
                            cells.get(rowStart, j + startColumn).setValue(dataRow[j] != null
                                    ? TextResource.localize(EnumAdaptor.valueOf(((BigDecimal) dataRow[j]).intValue(), MasterBranchUse.class).nameId) : "");
                            break;
                        case 7:
                            cells.get(rowStart, j+ startColumn).setValue(getUsageMasterType(dataRow));
                            break;
                        case 8:
                            cells.get(rowStart, j+ startColumn).setValue(getUsageMasterCd(dataRow));
                            break;
                        case 9:
                            cells.get(rowStart, j+ startColumn).setValue(((BigDecimal)dataRow[4]).intValue() == 1 ? getDetailedFormula(formula, dataRow[0].toString()) : getSimpleFormula(dataRow, targetItem));
                            break;
                        case 10:
                            cells.get(rowStart, j+ startColumn).setValue(dataRow[10] != null ? ((BigDecimal)dataRow[4]).intValue() == 1 ? EnumAdaptor.valueOf(((BigDecimal) dataRow[j]).intValue(), ReferenceMonth.class).nameId
                                    : EnumAdaptor.valueOf(0, ReferenceMonth.class).nameId : "");
                            break;
                        case 11:
                            cells.get(rowStart, j+ startColumn).setValue(getValueRounding(dataRow));
                            break;
                        case 12:
                            cells.get(rowStart, j+ startColumn).setValue((dataRow[12]) != null ? ((BigDecimal)dataRow[4]).intValue() == 1 ? EnumAdaptor.valueOf(((BigDecimal) dataRow[j]).intValue(), RoundingPosition.class).nameId : EnumAdaptor.valueOf(0, RoundingPosition.class).nameId : "");
                            break;
                        case 13:
                            cells.get(rowStart, j+ startColumn).setValue((dataRow[13]) != null ? ((BigDecimal)dataRow[4]).intValue() == 1 ? EnumAdaptor.valueOf(((BigDecimal) dataRow[j]).intValue(), Rounding.class).nameId : EnumAdaptor.valueOf(0, RoundingResult.class).nameId : "");
                            break;
                        case 14:
                            cells.get(rowStart, j+ startColumn).setValue((dataRow[14]) != null ? ((BigDecimal)dataRow[4]).intValue() == 1 ?
                                    EnumAdaptor.valueOf(((BigDecimal) dataRow[j]).intValue(), AdjustmentClassification.class).nameId : EnumAdaptor.valueOf(0, AdjustmentClassification.class).nameId: "");
                            break;
                        default:
                            cells.get(rowStart, j + startColumn).setValue(dataRow[j] != null ? j > 9 ? dataRow[j - 1] : dataRow[j] : "");
                    }
                }
                rowStart++;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String getValueRounding(Object[] obj){
        if(((BigDecimal)obj[4]).intValue() == 1 || ((BigDecimal)obj[15]).intValue() == 1) {
            return "なし";
        }
        return obj[11] != null ? EnumAdaptor.valueOf(((BigDecimal) obj[11]).intValue(), RoundingMethod.class).nameId : "";
    }

    private String getBaseTargetItem(List<Object[]> baseTarget, String formulaCode, BigDecimal amountCls){
        StringBuilder temp = new StringBuilder();
        baseTarget.forEach(i ->{
            if(i[0].toString().equals(formulaCode) && amountCls.equals(i[3])) {
                temp.append( i[2] != null);
                temp.append("＋");
            }
        });
        return temp.length() > 0 ? temp.deleteCharAt(temp.length() - 1).toString() : "";
    }

    private String getDetailedFormula(List<Object[]> formula, String formulaCode){
        StringBuilder temp = new StringBuilder();
        formula.forEach(i -> {
                if(i[0].toString().equals(formulaCode)) {
                    temp.append( i[2] != null ? i[2].toString() : i[1].toString());
                }
        });
        return temp.toString();
    }

    private String getSimpleFormula(Object[] obj, List<Object[]> targetItem){
        StringBuilder a = new StringBuilder();
        StringBuilder b = new StringBuilder();
        StringBuilder c = new StringBuilder();
        StringBuilder e = new StringBuilder();
        StringBuilder temp = new StringBuilder();
        if(obj[15] == null) {
            return "";
        }
        c.append(obj[20] != null ? ((BigDecimal)obj[20]).intValue() : "");
        b.append(obj[22] != null ? TextResource.localize((EnumAdaptor.valueOf(((BigDecimal)obj[22]).intValue(), BaseItemClassification.class).nameId)) : "");
        if(((BigDecimal)obj[16]).intValue() == StandardAmountClassification.FIXED_AMOUNT.value) {
            a.append(obj[17].toString());
        }
        if(((BigDecimal)obj[16]).intValue() != StandardAmountClassification.FIXED_AMOUNT.value) {
            a.append(getBaseTargetItem(targetItem, obj[0].toString(), (BigDecimal)obj[22]));
        }
        if(((BigDecimal)obj[18]).intValue() == CoefficientClassification.FIXED_VALUE.value){
            e.append(obj[19].toString());
        }
        if(((BigDecimal)obj[18]).intValue() != CoefficientClassification.FIXED_VALUE.value){
            e.append(TextResource.localize((EnumAdaptor.valueOf(((BigDecimal)obj[19]).intValue(), CoefficientClassification.class).nameId)));
        }
        if(((BigDecimal)obj[15]).intValue() == FormulaType.CALCULATION_FORMULA_TYPE1.value){
            temp.append(a).append("×").append(e);
        }

        if(((BigDecimal)obj[15]).intValue() == FormulaType.CALCULATION_FORMULA_TYPE2.value){
            temp.append(a).append("×").append(c).append("×").append(e);
        }
        if(((BigDecimal)obj[15]).intValue() == FormulaType.CALCULATION_FORMULA_TYPE3.value){
            temp.append(a).append("÷").append(b).append("×").append(c).append("×").append(e);
        }

        return temp.toString();
    }

    private String getUsageMasterType(Object[] data){
        if(((BigDecimal)data[4]).intValue() == 0 || ((BigDecimal)data[5]).intValue() == 0) {
            return "なし";
        }
        return data[6] != null ? TextResource.localize(EnumAdaptor.valueOf(((BigDecimal) data[6]).intValue(), MasterUse.class).nameId) : "" ;
    }

    private String getUsageMasterCd(Object[] data){
        if(((BigDecimal)data[4]).intValue() == 0 || ((BigDecimal)data[5]).intValue() == 0) {
            return "なし";
        }
        return data[6] != null ? TextResource.localize(EnumAdaptor.valueOf(((BigDecimal) data[6]).intValue(), MasterUse.class).nameId) : "" ;
    }
}
