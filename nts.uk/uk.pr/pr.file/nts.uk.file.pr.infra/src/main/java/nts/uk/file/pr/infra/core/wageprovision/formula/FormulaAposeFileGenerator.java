package nts.uk.file.pr.infra.core.wageprovision.formula;

import com.aspose.cells.*;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.pr.core.dom.wageprovision.formula.*;
import nts.uk.ctx.pr.core.dom.wageprovision.formula.FormulaType;
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
import java.util.Optional;

@Stateless
public class FormulaAposeFileGenerator extends AsposeCellsReportGenerator implements FormulaFileGenerator {

    private static final String TEMPLATE_FILE = "report/QMM017.xlsx";
    private static final String REPORT_FILE_EXTENSION = ".xlsx";
    private static final String FILE_NAME = "QMM017計算式の登録_";
    private static final String TITLE = "計算式の登録 ";
    private static final String SHEET_NAME = "マスタリスト";
    private static final int CODE = 0;
    private static final int START = 2;
    private static final int END = 3;
    private static final int SETTING = 4;
    private static final int NESTED_ATR = 5;
    private static final int MASTER_BRANCH_USE = 6;
    private static final int MASTER_USE = 7;
    private static final int MASTER_USE_CD = 8;
    private static final int FORMULA = 9;
    private static final int REFERENCE_MONTH = 10;
    private static final int ROUNDING_METHOD = 11;
    private static final int ROUNDING_POSITION = 12;
    private static final int ROUNDING_RESULT = 13;
    private static final int ADJUSTMENT_ATR = 14;
    private static final int FORMULA_TYPE = 15;
    private static final int STANDARD_AMOUNT_ATR = 16;
    private static final int STANDARD_FIXED_VALUE = 17;
    private static final int COEFFICIENT_ATR = 18;
    private static final int COEFFICIENT_FIXED_VALUE = 19;
    private static final int EXTRA_RATE = 20;
    private static final int BASE_ITEM_FIXED_VALUE = 21;
    private static final int BASE_ITEM_ATR = 22;
    private static final int MASTER_NAME = 23;
    private static final int CALCULATION_FORMULA_ATR = 24;
    private static final int BASIC_CALCULATION_FORMULA = 25;
    private static final String UNIT = "丸め";
    private static final String CODE_DEFAULT = "";
    private static final String ZERO = "0";

    @Override
    public void generate(FileGeneratorContext generatorContext, FormulaExportData exportData) {
        try (AsposeCellsReportContext reportContext = this.createContext(TEMPLATE_FILE)) {
            Workbook workbook = reportContext.getWorkbook();
            WorksheetCollection worksheets = workbook.getWorksheets();
            worksheets.get(0).setName(SHEET_NAME);
            settingPage(worksheets.get(0), exportData.getCompanyName());
            printData(worksheets, exportData.getFormulas(), exportData.getFormulaDetails(), exportData.getTargetItems(), exportData.getEmployments(), exportData.getDepartments(),exportData.getCls(), exportData.getJobs());
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

    private void printData(WorksheetCollection worksheets, List<Object[]> data, List<Object[]> fornula, List<Object[]> targetItem, List<MasterUseDto> employments, List<MasterUseDto> departments, List<MasterUseDto> cls, List<MasterUseDto> jobs) {
        int numColumn = 15;
        int columnStart = 1;
        fillData(worksheets, data, fornula, targetItem, numColumn, columnStart, employments, departments, cls, jobs);
    }

    private String convertYearMonth(Object startYearMonth){
        return startYearMonth.toString().substring(0,4) + "/" + startYearMonth.toString().substring(4,6);
    }

    private void fillData(WorksheetCollection worksheets, List<Object[]> data, List<Object[]> formula,
                          List<Object[]> targetItem, int numColumn, int startColumn, List<MasterUseDto> employments, List<MasterUseDto> departments, List<MasterUseDto> cls, List<MasterUseDto> jobs) {
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
                        case START: case END:
                            cells.get(rowStart, j + startColumn).setValue(convertYearMonth(dataRow[j]));
                            break;
                        case SETTING:
                            cells.get(rowStart, j + startColumn).setValue(dataRow[j] != null && ((BigDecimal) dataRow[j]).intValue() == 1
                                    ? TextResource.localize("Enum_FormulaSettingMethod_DETAIL_SETTING") : TextResource.localize("Enum_FormulaSettingMethod_BASIC_SETTING"));
                            break;
                        case NESTED_ATR:
                            cells.get(rowStart, j+ startColumn).setValue(dataRow[j] != null ? EnumAdaptor.valueOf(((BigDecimal) dataRow[j]).intValue(), NestedUseCls.class).nameId : "");
                            break;
                        case MASTER_BRANCH_USE:
                            cells.get(rowStart, j + startColumn).setValue(dataRow[j] != null && ((BigDecimal) dataRow[j]).intValue() == 1 ?
                                    TextResource.localize("Enum_MasterBranchUse_USE") : TextResource.localize("Enum_MasterBranchUse_NOT_USE"));
                            break;
                        case MASTER_USE:
                            cells.get(rowStart, j+ startColumn).setValue(getUsageMasterType(dataRow));
                            break;
                        case MASTER_USE_CD:
                            cells.get(rowStart, j+ startColumn).setValue(getUsageMasterName(dataRow, employments, departments, cls, jobs));
                            break;
                        case FORMULA:
                            cells.get(rowStart, j+ startColumn).setValue(getValueFomula(dataRow, formula, targetItem, data) );
                            break;
                        case REFERENCE_MONTH:
                            cells.get(rowStart, j+ startColumn).setValue(getReferenceMonth(dataRow, targetItem, formula));
                            break;
                        case ROUNDING_METHOD:
                            cells.get(rowStart, j+ startColumn).setValue(getValueRoundingMethod(dataRow, data, formula, targetItem));
                            break;
                        case ROUNDING_POSITION:
                            cells.get(rowStart, j+ startColumn).setValue(getValueRoundingPosition(dataRow, data, formula, targetItem));
                            break;
                        case ROUNDING_RESULT:
                            cells.get(rowStart, j+ startColumn).setValue(getValueRounding(dataRow, data, targetItem, formula));
                            break;
                        case ADJUSTMENT_ATR:
                            cells.get(rowStart, j+ startColumn).setValue(getAdjustmentClassification(dataRow, data, targetItem, formula));
                            break;
                        default:
                            cells.get(rowStart, j + startColumn).setValue(dataRow[j]);
                    }
                }
                rowStart++;
            }
            if(data.size() == 0) {
                cells.deleteRows(rowStart, 2);
            }

            if(data.size() > 1 && data.size() % 2 == 0) {
                int totalColumn = 15;
                int columnStart = 1;
                for(int column = columnStart; column < totalColumn +  columnStart; column++) {
                    Style style = worksheets.get(0).getCells().get(rowStart - 1, column).getStyle();
                    style.setForegroundColor(Color.fromArgb(216,228, 188));
                    style.setPattern(BackgroundType.SOLID);
                    worksheets.get(0).getCells().get(rowStart - 1, column).setStyle(style);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String getReferenceMonth(Object[] obj, List<Object[]> targetItem, List<Object[]> formulas){
        if( (obj[REFERENCE_MONTH - 1] == null && ((BigDecimal)obj[SETTING]).intValue() == FormulaSettingMethod.DETAIL_SETTING.value)
                || (((BigDecimal)obj[SETTING]).intValue() == FormulaSettingMethod.DETAIL_SETTING.value && "".equals(getDetailedFormula(formulas, obj[CODE].toString()))) ){
            return "";
        }
        if((obj[FORMULA_TYPE] == null && ((BigDecimal)obj[SETTING]).intValue() == FormulaSettingMethod.BASIC_SETTING.value)
                || (ZERO.equals(getSimpleFormula(obj, targetItem)) && ((BigDecimal)obj[MASTER_BRANCH_USE]).intValue() == 0)){
            return "";
        }
        if(((BigDecimal)obj[SETTING]).intValue() == FormulaSettingMethod.BASIC_SETTING.value) {
            return EnumAdaptor.valueOf(0, ReferenceMonth.class).nameId;
        }
        return EnumAdaptor.valueOf(((BigDecimal) obj[REFERENCE_MONTH -1]).intValue(), ReferenceMonth.class).nameId;
    }

    private String getValueFomula(Object[] obj, List<Object[]> formulas, List<Object[]> targetItem, List<Object[]> objs){
        if(((BigDecimal) obj[SETTING]).intValue() == FormulaSettingMethod.DETAIL_SETTING.value) {
            return getDetailedFormula(formulas, obj[CODE].toString());
        }
        if(((BigDecimal) obj[SETTING]).intValue() == FormulaSettingMethod.BASIC_SETTING.value && (obj[CALCULATION_FORMULA_ATR] != null
                && ((BigDecimal)obj[CALCULATION_FORMULA_ATR]).intValue() == CalculationFormulaClassification.FORMULA.value)) {
            return getSimpleFormula(obj, targetItem);
        }
        if(((BigDecimal) obj[SETTING]).intValue() == FormulaSettingMethod.BASIC_SETTING.value && (obj[CALCULATION_FORMULA_ATR] != null
                && ((BigDecimal)obj[CALCULATION_FORMULA_ATR]).intValue() == CalculationFormulaClassification.FIXED_VALUE.value)) {
            return obj[BASIC_CALCULATION_FORMULA] != null ? ZERO.equals(obj[BASIC_CALCULATION_FORMULA].toString()) && ((BigDecimal)obj[MASTER_BRANCH_USE]).intValue() == 0 ? "" : obj[BASIC_CALCULATION_FORMULA].toString() : "";
        }
        if(((BigDecimal) obj[SETTING]).intValue() == FormulaSettingMethod.BASIC_SETTING.value && (obj[CALCULATION_FORMULA_ATR] != null
                && ((BigDecimal)obj[CALCULATION_FORMULA_ATR]).intValue() == CalculationFormulaClassification.DEFINITION_FORMULA.value)) {
            Object[] defaultValue = findDefault(objs, obj[CODE].toString());
            return defaultValue != null ? getValueFomula(defaultValue, formulas, targetItem, objs) : "";
        }
        return "";

    }

    private String getAdjustmentClassification(Object[] obj, List<Object[]> objs, List<Object[]> targetItem, List<Object[]> formulas){
        if((ZERO.equals(getSimpleFormula(obj, targetItem)) && ((BigDecimal)obj[MASTER_BRANCH_USE]).intValue() == 0 )
                || (((BigDecimal)obj[SETTING]).intValue() == FormulaSettingMethod.DETAIL_SETTING.value && "".equals(getDetailedFormula(formulas, obj[CODE].toString()))) ) {
            return "";
        }
        if (((BigDecimal)obj[SETTING]).intValue() == FormulaSettingMethod.DETAIL_SETTING.value){
            return "調整しない";
        }
        if(((BigDecimal)obj[SETTING]).intValue() == FormulaSettingMethod.BASIC_SETTING.value && (obj[CALCULATION_FORMULA_ATR] != null
                && ((BigDecimal)obj[CALCULATION_FORMULA_ATR]).intValue() == CalculationFormulaClassification.FIXED_VALUE.value)) {
            return "なし";
        }
        if(((BigDecimal)obj[SETTING]).intValue() == FormulaSettingMethod.BASIC_SETTING.value && (obj[CALCULATION_FORMULA_ATR] != null
                && ((BigDecimal)obj[CALCULATION_FORMULA_ATR]).intValue() == CalculationFormulaClassification.DEFINITION_FORMULA.value)) {
            Object[] defaultValue = findDefault(objs, obj[CODE].toString());
            return defaultValue != null ? getAdjustmentClassification(defaultValue, objs, targetItem, formulas) : "";
        }
        if(((BigDecimal)obj[SETTING]).intValue() == FormulaSettingMethod.BASIC_SETTING.value && (obj[CALCULATION_FORMULA_ATR] != null
                && ((BigDecimal)obj[CALCULATION_FORMULA_ATR]).intValue() == CalculationFormulaClassification.FORMULA.value)) {
            return EnumAdaptor.valueOf(((BigDecimal) obj[ADJUSTMENT_ATR]).intValue(), AdjustmentClassification.class).nameId;
        }
        return "";
    }

    private Object[] findDefault(List<Object[]> data, String fomulaCode){
        Optional<Object[]> defaultValue = data.stream().filter(item -> item[CODE].equals(fomulaCode) && CODE_DEFAULT.equals(item[MASTER_USE_CD])).findFirst();
        return defaultValue.orElse(null);
    }

    private String getValueRounding(Object[] obj, List<Object[]> objs, List<Object[]> targetItem, List<Object[]> formula){
        if((obj[ROUNDING_RESULT - 1] == null && ((BigDecimal)obj[SETTING]).intValue() == FormulaSettingMethod.DETAIL_SETTING.value)
                || (ZERO.equals(getSimpleFormula(obj, targetItem)) && ((BigDecimal)obj[MASTER_BRANCH_USE]).intValue() == 0)
                || (((BigDecimal)obj[SETTING]).intValue() == FormulaSettingMethod.DETAIL_SETTING.value && "".equals(getDetailedFormula(formula, obj[CODE].toString())))){
            return "";
        }
        if (((BigDecimal)obj[SETTING]).intValue() == FormulaSettingMethod.DETAIL_SETTING.value){
            return TextResource.localize(EnumAdaptor.valueOf(((BigDecimal) obj[ROUNDING_POSITION]).intValue(), AmountRounding.class).nameId);
        }
        if(((BigDecimal)obj[SETTING]).intValue() == FormulaSettingMethod.BASIC_SETTING.value && (obj[CALCULATION_FORMULA_ATR] != null
                && ((BigDecimal)obj[CALCULATION_FORMULA_ATR]).intValue() == CalculationFormulaClassification.FIXED_VALUE.value)) {
            return "なし";
        }
        if(((BigDecimal)obj[SETTING]).intValue() == FormulaSettingMethod.BASIC_SETTING.value && (obj[CALCULATION_FORMULA_ATR] != null
                && ((BigDecimal)obj[CALCULATION_FORMULA_ATR]).intValue() == CalculationFormulaClassification.DEFINITION_FORMULA.value)) {
            Object[] defaultValue = findDefault(objs, obj[CODE].toString());
            return defaultValue != null ? getValueRounding(defaultValue, objs, targetItem, formula) : "";
        }
        if(((BigDecimal)obj[SETTING]).intValue() == FormulaSettingMethod.BASIC_SETTING.value && (obj[CALCULATION_FORMULA_ATR] != null
                && ((BigDecimal)obj[CALCULATION_FORMULA_ATR]).intValue() == CalculationFormulaClassification.FORMULA.value)) {
            return EnumAdaptor.valueOf(((BigDecimal) obj[ROUNDING_RESULT]).intValue(), RoundingResult.class).nameId;
        }
        return "";
    }

    private String getValueRoundingPosition(Object[] obj, List<Object[]> objs, List<Object[]> formula, List<Object[]> targetItem){
        if( (obj[11] == null && ((BigDecimal)obj[SETTING]).intValue() == FormulaSettingMethod.DETAIL_SETTING.value)
                || (ZERO.equals(getSimpleFormula(obj, targetItem)) && ((BigDecimal)obj[MASTER_BRANCH_USE]).intValue() == 0)
                || (((BigDecimal)obj[SETTING]).intValue() == FormulaSettingMethod.DETAIL_SETTING.value && "".equals(getDetailedFormula(formula, obj[CODE].toString())))){
            return "";
        }
        if(((BigDecimal)obj[SETTING]).intValue() == FormulaSettingMethod.DETAIL_SETTING.value) {
            return EnumAdaptor.valueOf(((BigDecimal) obj[11]).intValue(), RoundingPosition.class).nameId + UNIT;
        }
        if(((BigDecimal)obj[SETTING]).intValue() == FormulaSettingMethod.BASIC_SETTING.value && (obj[CALCULATION_FORMULA_ATR] != null
                && ((BigDecimal)obj[CALCULATION_FORMULA_ATR]).intValue() == CalculationFormulaClassification.FIXED_VALUE.value)) {
            return "なし";
        }
        if(((BigDecimal)obj[SETTING]).intValue() == FormulaSettingMethod.BASIC_SETTING.value && (obj[CALCULATION_FORMULA_ATR] != null
                && ((BigDecimal)obj[CALCULATION_FORMULA_ATR]).intValue() == CalculationFormulaClassification.DEFINITION_FORMULA.value)) {
            Object[] defaultValue = findDefault(objs, obj[CODE].toString());
            return defaultValue != null ? getValueRoundingPosition(defaultValue, objs, formula, targetItem) : "";
        }
        if(((BigDecimal)obj[SETTING]).intValue() == FormulaSettingMethod.BASIC_SETTING.value && (obj[CALCULATION_FORMULA_ATR] != null
                && ((BigDecimal)obj[CALCULATION_FORMULA_ATR]).intValue() == CalculationFormulaClassification.FORMULA.value)) {
            return EnumAdaptor.valueOf(0, RoundingPosition.class).nameId + UNIT;
        }
        return "";
    }

    private String getValueRoundingMethod(Object[] obj, List<Object[]> objs, List<Object[]> formula, List<Object[]> targetItem){
        if( ((obj[10] == null) && ((BigDecimal)obj[SETTING]).intValue() == FormulaSettingMethod.BASIC_SETTING.value)
                || ( ZERO.equals(getSimpleFormula(obj, targetItem)) && ((BigDecimal)obj[MASTER_BRANCH_USE]).intValue() == 0)
                || (((BigDecimal)obj[SETTING]).intValue() == FormulaSettingMethod.DETAIL_SETTING.value && "".equals(getDetailedFormula(formula, obj[CODE].toString())))){
            return "";
        }

        if(((BigDecimal)obj[SETTING]).intValue() == FormulaSettingMethod.DETAIL_SETTING.value) {
            return "なし";
        }
        if((obj[CALCULATION_FORMULA_ATR] != null && ((BigDecimal)obj[CALCULATION_FORMULA_ATR]).intValue() == CalculationFormulaClassification.FIXED_VALUE.value)) {
            return "なし";
        }
        if((obj[CALCULATION_FORMULA_ATR] != null && ((BigDecimal)obj[CALCULATION_FORMULA_ATR]).intValue() == CalculationFormulaClassification.DEFINITION_FORMULA.value)) {
            Object[] defaultValue = findDefault(objs, obj[0].toString());
            return defaultValue != null ? getValueRoundingMethod(defaultValue, objs, formula, targetItem) : "";
        }
        if((obj[FORMULA_TYPE] != null && ((BigDecimal)obj[FORMULA_TYPE]).intValue() == 0)
                && ((BigDecimal)obj[CALCULATION_FORMULA_ATR]).intValue() == CalculationFormulaClassification.FORMULA.value) {
            return "なし";
        }
        if(obj[24] != null && ((BigDecimal)obj[CALCULATION_FORMULA_ATR]).intValue() == CalculationFormulaClassification.FORMULA.value) {
            return TextResource.localize(EnumAdaptor.valueOf(((BigDecimal) obj[10]).intValue(), RoundingMethod.class).nameId);
        }
        return "";
    }

    private String getBaseTargetItem(List<Object[]> baseTarget, String formulaCode, BigDecimal amountCls, String masterCd){
        StringBuilder temp = new StringBuilder();
        int count = 0;
        for(int i = 0; i< baseTarget.size(); i++){
            if(baseTarget.get(i)[0].toString().equals(formulaCode) && baseTarget.get(i)[4].toString().equals(masterCd) &&
                    ((amountCls.intValue() == (Integer)baseTarget.get(i)[3]) || amountCls.intValue() == 1 && (Integer)baseTarget.get(i)[3] == 0)) {
                count++;
                temp.append(getName((Integer) baseTarget.get(i)[3])).append("｛").append(baseTarget.get(i)[2]).append("｝").append("＋");
            }
        }
        if(count > 0) {
            temp.deleteCharAt(temp.length() - 1);
        }
        if(count > 1) {
            temp.insert(0, "（");
            temp.append("）");
        }
        return temp.toString();
    }

    private String getName(int standardCls){
        if(standardCls == 1) {
            return TextResource.localize("Enum_FormulaElementType_PAYMENT_ITEM");
        }
        if(standardCls == 2) {
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
                if(i[CODE].toString().equals(formulaCode)) {
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
        if(obj[FORMULA_TYPE] == null) {
            return "";
        }
        if(((BigDecimal)obj[CALCULATION_FORMULA_ATR]).intValue() == CalculationFormulaClassification.FIXED_VALUE.value) {
            return obj[BASIC_CALCULATION_FORMULA] != null ? obj[BASIC_CALCULATION_FORMULA].toString() : "";
        }
        if(((BigDecimal)obj[CALCULATION_FORMULA_ATR]).intValue() == CalculationFormulaClassification.DEFINITION_FORMULA.value) {
            obj[STANDARD_AMOUNT_ATR] = new BigDecimal(2);
        }
        c.append(obj[EXTRA_RATE] != null ? ((BigDecimal)obj[EXTRA_RATE]).intValue() : "");
        if(obj[BASE_ITEM_ATR] != null) {
            b.append(((BigDecimal)obj[BASE_ITEM_ATR]).intValue() == BaseItemClassification.FIXED_VALUE.value ? obj[BASE_ITEM_FIXED_VALUE].toString() : "");
            b.append(((BigDecimal)obj[BASE_ITEM_ATR]).intValue() > BaseItemClassification.ATTENDANCE_DAY.value && ((BigDecimal)obj[BASE_ITEM_ATR]).intValue() != BaseItemClassification.ATTENDANCE_TIME.value
                    ? "（" + (EnumAdaptor.valueOf(((BigDecimal)obj[22]).intValue(), BaseItemClassification.class).nameId) + "）" : "");
            b.append(((BigDecimal)obj[BASE_ITEM_ATR]).intValue() <= BaseItemClassification.ATTENDANCE_DAY.value && ((BigDecimal)obj[BASE_ITEM_ATR]).intValue() > 0
                    ? (EnumAdaptor.valueOf(((BigDecimal)obj[22]).intValue(), BaseItemClassification.class).nameId) : "");
            b.append(((BigDecimal)obj[BASE_ITEM_ATR]).intValue() == BaseItemClassification.ATTENDANCE_TIME.value
                    ? (EnumAdaptor.valueOf(((BigDecimal)obj[22]).intValue(), BaseItemClassification.class).nameId) : "");
        }
        if(((BigDecimal)obj[STANDARD_AMOUNT_ATR]).intValue() == StandardAmountClassification.FIXED_AMOUNT.value) {
            a.append(obj[STANDARD_FIXED_VALUE].toString());
        }
        if(((BigDecimal)obj[STANDARD_AMOUNT_ATR]).intValue() != StandardAmountClassification.FIXED_AMOUNT.value) {
            a.append(getBaseTargetItem(targetItem, obj[CODE].toString(), (BigDecimal) obj[STANDARD_AMOUNT_ATR], obj[MASTER_USE_CD].toString()));
        }
        if(((BigDecimal)obj[COEFFICIENT_ATR]).intValue() == CoefficientClassification.FIXED_VALUE.value){
            e.append(obj[COEFFICIENT_FIXED_VALUE].toString());
        }
        if(((BigDecimal)obj[COEFFICIENT_ATR]).intValue() != CoefficientClassification.FIXED_VALUE.value){
            e.append(((BigDecimal)obj[COEFFICIENT_ATR]).intValue() == CoefficientClassification.WORKDAY_AND_HOLIDAY.value ? "（" : "").
                    append((EnumAdaptor.valueOf(((BigDecimal)obj[COEFFICIENT_ATR]).intValue(), CoefficientClassification.class).nameId))
                    .append(((BigDecimal)obj[COEFFICIENT_ATR]).intValue() == CoefficientClassification.WORKDAY_AND_HOLIDAY.value ? "）" : "");
        }
        if(((BigDecimal)obj[FORMULA_TYPE]).intValue() == FormulaType.CALCULATION_FORMULA_TYPE1.value){
            temp.append(a).append(a.length() > 0 ? "×" : "（）× ").append(e);
        }

        if(((BigDecimal)obj[FORMULA_TYPE]).intValue() == FormulaType.CALCULATION_FORMULA_TYPE2.value){
            temp.append(a).append( a.length() > 0 ? "×": "（）×").append(c).append(c.length() > 0 ? "％×": "").append(e);
        }
        if(((BigDecimal)obj[FORMULA_TYPE]).intValue() == FormulaType.CALCULATION_FORMULA_TYPE3.value){
            temp.append(a).append(a.length() > 0 ? "÷" : "（）÷").append(b).append(b.length() > 0 ? "×" : "").append(c).append(c.length() > 0 ? "％ ×": "").append(e);
        }

        return temp.toString();
    }

    private String getUsageMasterType(Object[] data){
        if(((BigDecimal)data[SETTING]).intValue() == FormulaSettingMethod.DETAIL_SETTING.value || ((BigDecimal)data[MASTER_BRANCH_USE]).intValue() == 0) {
            return "なし";
        }
        return data[MASTER_USE] != null ? EnumAdaptor.valueOf(((BigDecimal) data[MASTER_USE]).intValue(), MasterUse.class).nameId : "" ;
    }

    private String getUsageMasterName(Object[] data, List<MasterUseDto> employments, List<MasterUseDto> departments, List<MasterUseDto> cls, List<MasterUseDto> jobs){
        if(((BigDecimal)data[SETTING]).intValue() == FormulaSettingMethod.DETAIL_SETTING.value || ((BigDecimal)data[6]).intValue() == 0) {
            return "なし";
        }
        if(data[MASTER_USE_CD] != null && CODE_DEFAULT.equals(data[MASTER_USE_CD].toString())) {
            return "既定値";
        }
        if(data[MASTER_USE] != null && ((BigDecimal)data[MASTER_USE]).intValue() == MasterUse.EMPLOYMENT.value) {
            Optional<MasterUseDto> masterUse = employments.stream().filter(item -> item.getCode().equals(data[MASTER_USE_CD])).findFirst();
            return masterUse.isPresent() ? masterUse.get().getName() : "";
        }
        if(data[MASTER_USE] != null && ((BigDecimal)data[MASTER_USE]).intValue() == MasterUse.DEPARTMENT.value) {
            Optional<MasterUseDto> masterUse = departments.stream().filter(item -> item.getCode().equals(data[MASTER_USE_CD])).findFirst();
            return masterUse.isPresent() ? masterUse.get().getName() : "";
        }
        if(data[MASTER_USE] != null && ((BigDecimal)data[MASTER_USE]).intValue() == MasterUse.CLASSIFICATION.value) {
            Optional<MasterUseDto> masterUse = cls.stream().filter(item -> item.getCode().equals(data[MASTER_USE_CD])).findFirst();
            return masterUse.isPresent() ? masterUse.get().getName() : "";
        }
        if(data[MASTER_USE] != null && ((BigDecimal)data[MASTER_USE]).intValue() == MasterUse.JOB_TITLE.value) {
            Optional<MasterUseDto> masterUse = jobs.stream().filter(item -> item.getCode().equals(data[MASTER_USE_CD])).findFirst();
            return masterUse.isPresent() ? masterUse.get().getName() : "";
        }
        return data[MASTER_NAME] != null ? data[MASTER_NAME].toString() : "";
    }
}
