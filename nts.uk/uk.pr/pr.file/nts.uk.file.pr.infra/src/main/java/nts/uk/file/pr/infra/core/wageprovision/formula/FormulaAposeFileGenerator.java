package nts.uk.file.pr.infra.core.wageprovision.formula;

import com.aspose.cells.*;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.pr.core.dom.wageprovision.formula.*;
import nts.uk.ctx.pr.core.dom.wageprovision.formula.FormulaType;
import nts.uk.ctx.pr.file.app.core.wageprovision.formula.FormulaExportData;
import nts.uk.ctx.pr.file.app.core.wageprovision.formula.FormulaFileGenerator;
import nts.uk.shr.com.company.CompanyAdapter;
import nts.uk.shr.com.company.CompanyInfor;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Stateless
public class FormulaAposeFileGenerator extends AsposeCellsReportGenerator implements FormulaFileGenerator {

    private static final String TEMPLATE_FILE = "report/QMM017.xlsx";
    private static final String REPORT_FILE_EXTENSION = ".xlsx";
    private static final String FILE_NAME = "QMM017計算式の登録_";
    private static final String TITLE = "計算式の登録 ";
    private static final String SHEET_NAME = "マスタリスト";
    @Inject
    private CompanyAdapter company;

    @Override
    public void generate(FileGeneratorContext generatorContext, FormulaExportData exportData) {
        try (AsposeCellsReportContext reportContext = this.createContext(TEMPLATE_FILE)) {
            Workbook workbook = reportContext.getWorkbook();
            WorksheetCollection worksheets = workbook.getWorksheets();
            worksheets.get(0).setName(SHEET_NAME);
            settingPage(worksheets.get(0), this.company.getCurrentCompany().map(CompanyInfor::getCompanyName).orElse(""));
            printData(worksheets, exportData.getFormulas(), exportData.getFormulaDetails(), exportData.getTargetItems());
            worksheets.setActiveSheetIndex(0);
            reportContext.processDesigner();
            reportContext.saveAsExcel(this.createNewFile(generatorContext,
                    FILE_NAME + GeneralDateTime.now().toString("yyyyMMddHHmmss") + REPORT_FILE_EXTENSION));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void settingPage(Worksheet worksheet, String companyName){
        PageSetup pageSetup = worksheet.getPageSetup();
        pageSetup.setHeader(0, "&\"ＭＳ ゴシック\"&10 " + companyName);
        pageSetup.setHeader(1, "&\"ＭＳ ゴシック\"&16 "+ TITLE);
        DateTimeFormatter fullDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/M/d  H:mm:ss", Locale.JAPAN);
        String currentFormattedDate = LocalDateTime.now().format(fullDateTimeFormatter);
        pageSetup.setHeader(2, "&\"ＭＳ ゴシック\"&10 " + currentFormattedDate+"\npage&P");
    }

    private void printData(WorksheetCollection worksheets, List<Object[]> data, List<Object[]> fornula, List<Object[]> targetItem) {
        int numColumn = 15;
        int columnStart = 1;
        fillData(worksheets, data, fornula, targetItem, numColumn, columnStart);
    }

    private String convertYearMonth(Object startYearMonth){
        return startYearMonth.toString().substring(0,4) + "/" + startYearMonth.toString().substring(4,6);
    }

    private void fillData(WorksheetCollection worksheets, List<Object[]> data, List<Object[]> formula,
                          List<Object[]> targetItem, int numColumn, int startColumn) {
        try {
            int rowStart = 3;
            int lineCopy = 3;
            Worksheet sheet = worksheets.get(0);
            Cells cells = sheet.getCells();
            for (int i = 0; i < data.size(); i++) {
                if(i % 2 == 0) {
                    cells.copyRows(cells, rowStart, rowStart + lineCopy - 1, lineCopy);
                }
                if(i == data.size() - 1) {
                    cells.deleteRows(rowStart, data.size() % 2 == 0 ? 3 : 4);
                }
                Object[] dataRow = data.get(i);
                for (int j = 0; j < numColumn; j++) {
                    switch (j) {
                        case 2: case 3:
                            cells.get(rowStart, j + startColumn).setValue(convertYearMonth(dataRow[j]));
                            break;
                        case 4:
                            cells.get(rowStart, j + startColumn).setValue(dataRow[j] != null && ((BigDecimal) dataRow[j]).intValue() == 1
                                    ? TextResource.localize("Enum_FormulaSettingMethod_DETAIL_SETTING") : TextResource.localize("Enum_FormulaSettingMethod_BASIC_SETTING"));
                            break;
                        case 5:
                            cells.get(rowStart, j+ startColumn).setValue(dataRow[5] != null ? EnumAdaptor.valueOf(((BigDecimal) dataRow[j]).intValue(), NestedUseCls.class).nameId : "");
                            break;
                        case 6:
                            cells.get(rowStart, j + startColumn).setValue(dataRow[j] != null && ((BigDecimal) dataRow[j]).intValue() == 0 ?
                                    TextResource.localize("Enum_MasterBranchUse_NOT_USE") : TextResource.localize("Enum_MasterBranchUse_USE"));
                            break;
                        case 7:
                            cells.get(rowStart, j+ startColumn).setValue(getUsageMasterType(dataRow));
                            break;
                        case 8:
                            cells.get(rowStart, j+ startColumn).setValue(getUsageMasterName(dataRow));
                            break;
                        case 9:
                            cells.get(rowStart, j+ startColumn).setValue(getValueFomula(dataRow, formula, targetItem, data));
                            break;
                        case 10:
                            cells.get(rowStart, j+ startColumn).setValue(getReferenceMonth(dataRow));
                            break;
                        case 11:
                            cells.get(rowStart, j+ startColumn).setValue(getValueRoundingMethod(dataRow, data, formula, targetItem));
                            break;
                        case 12:
                            cells.get(rowStart, j+ startColumn).setValue(getValueRoundingPosition(dataRow, data, formula));
                            break;
                        case 13:
                            cells.get(rowStart, j+ startColumn).setValue(getValueRounding(dataRow, data));
                            break;
                        case 14:
                            cells.get(rowStart, j+ startColumn).setValue(getAdjustmentClassification(dataRow, data));
                            break;
                        default:
                            cells.get(rowStart, j + startColumn).setValue(dataRow[j] != null ? j > 9 ? dataRow[j - 1] : dataRow[j] : "");
                    }
                }
                rowStart++;
            }
            if(data.size() > 0) {
                cells.deleteRows(rowStart, 2);
            }

            if(data.size() > 1 && data.size() % 2 == 0) {
                int totalColumn = 15;
                int columnStart = 1;
                for(int column = columnStart; column < totalColumn +  columnStart; column++) {
                    Style style = cells.get(rowStart - 1, column).getStyle();
                    style.setForegroundColor(Color.fromArgb(216,228, 188));
                    cells.get(rowStart - 1, column).setStyle(style);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String getReferenceMonth(Object[] obj){
        if(((BigDecimal)obj[4]).intValue() == 1  && obj[9] == null) {
            return "";
        }
        if(((BigDecimal)obj[4]).intValue() == 0 ) {
            return EnumAdaptor.valueOf(0, ReferenceMonth.class).nameId;
        }
        return EnumAdaptor.valueOf(((BigDecimal) obj[9]).intValue(), ReferenceMonth.class).nameId;
    }

    private String getValueFomula(Object[] obj, List<Object[]> formulas, List<Object[]> targetItem, List<Object[]> objs){
        if(((BigDecimal) obj[4]).intValue() == 1) {
            return getDetailedFormula(formulas, obj[0].toString());
        }
        if(((BigDecimal) obj[4]).intValue() == 0 && (obj[24] != null && ((BigDecimal)obj[24]).intValue() == 1)) {
            return getSimpleFormula(obj, targetItem);
        }
        if(((BigDecimal) obj[4]).intValue() == 0 && (obj[24] != null && ((BigDecimal)obj[24]).intValue() == 0)) {
            return obj[25] != null ? obj[25].toString() : "";
        }
        if(((BigDecimal) obj[4]).intValue() == 0 && (obj[24] != null && ((BigDecimal)obj[24]).intValue() == 2)) {
            Object[] defaultValue = findDefault(objs, obj[0].toString());
            return defaultValue != null ? getValueFomula(defaultValue, formulas, targetItem, objs) : "";
        }
        return "";

    }

    private String getAdjustmentClassification(Object[] obj, List<Object[]> objs){
        if (((BigDecimal)obj[4]).intValue() == 1){
            return "調整しない";
        }
        if(((BigDecimal)obj[4]).intValue() == 0 && (obj[24] != null && ((BigDecimal)obj[24]).intValue() == 0)) {
            return "";
        }
        if(((BigDecimal)obj[4]).intValue() == 0 && (obj[24] != null && ((BigDecimal)obj[24]).intValue() == 2)) {
            Object[] defaultValue = findDefault(objs, obj[0].toString());
            return defaultValue != null ? getAdjustmentClassification(defaultValue, objs) : "";
        }
        if(((BigDecimal)obj[4]).intValue() == 0 && (obj[24] != null && ((BigDecimal)obj[24]).intValue() == 1)) {
            return EnumAdaptor.valueOf(((BigDecimal) obj[13]).intValue(), AdjustmentClassification.class).nameId;
        }
        return "";
    }

    private Object[] findDefault(List<Object[]> data, String fomulaCode){
        Optional<Object[]> defaultValue = data.stream().filter(item -> item[0].equals(fomulaCode) && "0000000000".equals(item[8])).findFirst();
        return defaultValue.orElse(null);
    }

    private String getValueRounding(Object[] obj, List<Object[]> objs){
        if(obj[12] == null ) {
            return "";
        }
        if (((BigDecimal)obj[4]).intValue() == 1){
            return TextResource.localize(EnumAdaptor.valueOf(((BigDecimal) obj[12]).intValue(), AmountRounding.class).nameId);
        }
        if(((BigDecimal)obj[4]).intValue() == 0 && (obj[24] != null && ((BigDecimal)obj[24]).intValue() == 0)) {
            return "";
        }
        if(((BigDecimal)obj[4]).intValue() == 0 && (obj[24] != null && ((BigDecimal)obj[24]).intValue() == 2)) {
            Object[] defaultValue = findDefault(objs, obj[0].toString());
            return defaultValue != null ? getValueRounding(defaultValue, objs) : "";
        }
        if(((BigDecimal)obj[4]).intValue() == 0 && (obj[24] != null && ((BigDecimal)obj[24]).intValue() == 1)) {
            return EnumAdaptor.valueOf(((BigDecimal) obj[12]).intValue(), RoundingResult.class).nameId;
        }
        return "";
    }

    private String getValueRoundingPosition(Object[] obj, List<Object[]> objs, List<Object[]> formula){
        if(obj[11] == null || ("".equals(getDetailedFormula(formula, obj[0].toString())) )) {
            return "";
        }
        if(((BigDecimal)obj[4]).intValue() == 1 && (obj[24] != null && ((BigDecimal)obj[24]).intValue() == 0)) {
            return "";
        }
        if(((BigDecimal)obj[4]).intValue() == 1 && (obj[24] != null && ((BigDecimal)obj[24]).intValue() == 2)) {
            Object[] defaultValue = findDefault(objs, obj[0].toString());
            return defaultValue != null ? getValueRoundingPosition(defaultValue, objs, formula) : "";
        }
        if(((BigDecimal)obj[4]).intValue() == 1 && (obj[24] != null && ((BigDecimal)obj[24]).intValue() == 1)) {
            return EnumAdaptor.valueOf(((BigDecimal) obj[11]).intValue(), RoundingPosition.class).nameId;
        }
        return EnumAdaptor.valueOf(0, RoundingPosition.class).nameId;
    }

    private String getValueRoundingMethod(Object[] obj, List<Object[]> objs, List<Object[]> formula, List<Object[]> targetItem){
        if(obj[10] == null || (("".equals(getDetailedFormula(formula, obj[0].toString()))
                && ("".equals(getSimpleFormula(obj, targetItem)))))){
            return "";
        }
        if(((BigDecimal)obj[4]).intValue() == 1) {
            return "なし";
        }
        if((obj[24] != null && ((BigDecimal)obj[24]).intValue() == 0)) {
            return "";
        }
        if((obj[24] != null && ((BigDecimal)obj[24]).intValue() == 2)) {
            Object[] defaultValue = findDefault(objs, obj[0].toString());
            return defaultValue != null ? getValueRoundingMethod(defaultValue, objs, formula, targetItem) : "";
        }
        if((obj[15] != null && ((BigDecimal)obj[15]).intValue() == 0) && ((BigDecimal)obj[24]).intValue() == 1) {
            return "なし";
        }
        if(obj[24] != null && ((BigDecimal)obj[24]).intValue() == 1) {
            return TextResource.localize(EnumAdaptor.valueOf(((BigDecimal) obj[10]).intValue(), RoundingMethod.class).nameId);
        }
        return "";
    }

    private String getBaseTargetItem(List<Object[]> baseTarget, String formulaCode, BigDecimal amountCls){
        StringBuilder temp = new StringBuilder();
        int count = 0;
        for(int i = 0; i< baseTarget.size(); i++){
            if(baseTarget.get(i)[0].toString().equals(formulaCode) && ((amountCls.intValue() == (Integer)baseTarget.get(i)[3])
                    || amountCls.intValue() == 1 && (Integer)baseTarget.get(i)[3] == 0)) {
                temp.append(count == 0 ? getName((Integer) baseTarget.get(i)[3]) : "")
                        .append(count == 0 ? "｛" : "＋").append(baseTarget.get(i)[2]);
                count++;
            }
        }
        temp.append(count > 0 ? "｝" : "");
        return temp.toString();
    }

    private String getName(int standardCls){
        if(standardCls == 0) {
            return TextResource.localize("Enum_FormulaElementType_PAYMENT_ITEM");
        }
        if(standardCls == 1) {
            return TextResource.localize("Enum_FormulaElementType_DEDUCTION_ITEM");
        }
        if(standardCls == 3) {
            return TextResource.localize("Enum_FormulaElementType_COMPANY_UNIT_PRICE_ITEM");
        }
        if(standardCls == 4) {
            return TextResource.localize("Enum_FormulaElementType_INDIVIDUAL_UNIT_PRICE_ITEM");
        }
        return "";
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
        if(((BigDecimal)obj[24]).intValue() == 0) {
            return obj[25] != null ? obj[25].toString() : "";
        }
        if(((BigDecimal)obj[24]).intValue() == 2) {
            obj[16] = new BigDecimal(2);
        }
        c.append(obj[20] != null ? ((BigDecimal)obj[20]).intValue() : "");
        b.append(obj[22] != null ? (EnumAdaptor.valueOf(((BigDecimal)obj[22]).intValue(), BaseItemClassification.class).nameId) : "");
        if(((BigDecimal)obj[16]).intValue() == StandardAmountClassification.FIXED_AMOUNT.value) {
            a.append(obj[17].toString());
        }
        if(((BigDecimal)obj[16]).intValue() != StandardAmountClassification.FIXED_AMOUNT.value) {
            a.append(getBaseTargetItem(targetItem, obj[0].toString(), (BigDecimal) obj[16]));
        }
        if(((BigDecimal)obj[18]).intValue() == CoefficientClassification.FIXED_VALUE.value){
            e.append(obj[19].toString());
        }
        if(((BigDecimal)obj[18]).intValue() != CoefficientClassification.FIXED_VALUE.value){
            e.append(((BigDecimal)obj[18]).intValue() == 2 ? "（  " : "").
                    append((EnumAdaptor.valueOf(((BigDecimal)obj[18]).intValue(), CoefficientClassification.class).nameId))
                    .append(((BigDecimal)obj[18]).intValue() == 2 ? "  ）" : "");
        }
        if(((BigDecimal)obj[15]).intValue() == FormulaType.CALCULATION_FORMULA_TYPE1.value){
            temp.append(a).append(a.length() > 0 ? "×" : "").append(e);
        }

        if(((BigDecimal)obj[15]).intValue() == FormulaType.CALCULATION_FORMULA_TYPE2.value){
            temp.append(a).append( a.length() > 0 ? "×": "").append(c).append(c.length() > 0 ? "％ ×": "").append(e);
        }
        if(((BigDecimal)obj[15]).intValue() == FormulaType.CALCULATION_FORMULA_TYPE3.value){
            temp.append(a).append(a.length() > 0 ? "÷" : "").append(b).append(b.length() > 0 ? "×" : "").append(c).append(c.length() > 0 ? "％ ×": "").append(e);
        }

        return temp.toString();
    }

    private String getUsageMasterType(Object[] data){
        if(((BigDecimal)data[4]).intValue() == 1 || ((BigDecimal)data[6]).intValue() == 0) {
            return "なし";
        }
        return data[7] != null ? EnumAdaptor.valueOf(((BigDecimal) data[7]).intValue(), MasterUse.class).nameId : "" ;
    }

    private String getUsageMasterName(Object[] data){
        if(((BigDecimal)data[4]).intValue() == 1 || ((BigDecimal)data[6]).intValue() == 0) {
            return "なし";
        }
        if(data[8] != null && "0000000000".equals(data[8].toString())) {
            return "既定値";
        }
        return data[23] != null ? data[23].toString() : "";
    }
}
