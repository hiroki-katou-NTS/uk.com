package nts.uk.ctx.pr.core.dom.wageprovision.formula.detailcalculationformula;
import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.core.dom.wageprovision.companyuniformamount.PayrollUnitPriceRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.formula.FormulaRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.formula.FormulaService;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.CategoryAtr;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.StatementItemCustom;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.StatementItemRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.unitpricename.SalaryPerUnitPriceRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.WageTableRepository;
import nts.uk.shr.com.context.AppContexts;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

@Stateless
public class DetailFormulaCalculationService {

    @Inject
    private FormulaService formulaService;

    @Inject
    private StatementItemRepository statementItemRepository;

    @Inject
    private PayrollUnitPriceRepository payrollUnitPriceRepository;

    @Inject
    private SalaryPerUnitPriceRepository salaryPerUnitPriceRepository;

    @Inject
    private WageTableRepository wageTableRepository;

    private FormulaRepository formulaRepository;

    /**
     * All following text is belong to business
     * Try to use text resource for other language
     */
    private static final String
            OPEN_CURLY_BRACKET = "{", CLOSE_CURLY_BRACKET = "}", COMMA_CHAR = ",",  PLUS = "＋",
            SUBTRACT = "ー", MULTIPLICY = "×", DIVIDE = "÷", POW = "^", OPEN_BRACKET = "(", CLOSE_BRACKET = ")",
            GREATER = ">", LESS = "<", LESS_OR_EQUAL = "≦", GREATER_OR_EQUAL = "≧", EQUAL = "＝", DIFFERENCE = "≠";

    /**
     * Error when deploy if using getText or get name from enum
     * Caused by: java.lang.ExceptionInInitializerError
     * Caused by: java.lang.IllegalStateException: WFLYWELD0039: Singleton not set for ModuleClassLoader for Module \"deployment.nts.uk.pr.web.war:main\
     * Can't find solution, temporary use fixed text
     */
    private static final String
        PAYMENT = "支給",
        DEDUCTION = "控除",
        ATTENDANCE = "勤怠",
        COMPANY_UNIT_PRICE = "会社単価",
        INDIVIDUAL_UNIT_PRICE = "個人単価",
        FUNCTION = "関数",
        VARIABLE = "変数",
        PERSON = "個人",
        FORMULA = "計算式",
        WAGE_TABLE = "賃金";
    private static final String
        CONDITIONAL = "条件式",
        AND = "かつ",
        OR = "または",
        ROUND_OFF = "四捨五入",
        TRUNCATION = "切り捨て",
        ROUND_UP = "切り上げ",
        MAX_VALUE = "最大値",
        MIN_VALUE = "最小値",
        NUM_OF_FAMILY_MEMBER = "家族人数",
        YEAR_MONTH = "年月加算",
        YEAR_EXTRACTION = "年抽出",
        MONTH_EXTRACTION = "月抽出";
    private static final String
        SYSTEM_YMD_DATE = "システム日付（年月日）",
        SYSTEM_YM_DATE = "システム日付（年月）",
        SYSTEM_Y_DATE = "システム日付（年）",
        PROCESSING_YEAR_MONTH = "処理年月",
        PROCESSING_YEAR = "処理年",
        REFERENCE_TIME = "基準時間",
        STANDARD_DAY = "基準日数",
        WORKDAY = "要勤務日数";
    private static final Map<String, String> calculationFormulaDictionary;
    static {
        calculationFormulaDictionary = new HashMap<>();
        calculationFormulaDictionary.put(PAYMENT, "0000_0");
        calculationFormulaDictionary.put(DEDUCTION, "0001_0");
        calculationFormulaDictionary.put(ATTENDANCE, "0002_0");
        calculationFormulaDictionary.put(COMPANY_UNIT_PRICE, "U000_0");
        calculationFormulaDictionary.put(INDIVIDUAL_UNIT_PRICE, "U001_0");
        calculationFormulaDictionary.put(combineElementTypeAndName(FUNCTION, CONDITIONAL), "Func_00001");
        calculationFormulaDictionary.put(combineElementTypeAndName(FUNCTION, AND), "Func_00002");
        calculationFormulaDictionary.put(combineElementTypeAndName(FUNCTION, OR), "Func_00003");
        calculationFormulaDictionary.put(combineElementTypeAndName(FUNCTION, ROUND_OFF), "Func_00004");
        calculationFormulaDictionary.put(combineElementTypeAndName(FUNCTION, TRUNCATION), "Func_00005");
        calculationFormulaDictionary.put(combineElementTypeAndName(FUNCTION, ROUND_UP), "Func_00006");
        calculationFormulaDictionary.put(combineElementTypeAndName(FUNCTION, MAX_VALUE), "Func_00007");
        calculationFormulaDictionary.put(combineElementTypeAndName(FUNCTION, MIN_VALUE), "Func_00008");
        calculationFormulaDictionary.put(combineElementTypeAndName(FUNCTION, NUM_OF_FAMILY_MEMBER), "Func_00009");
        calculationFormulaDictionary.put(combineElementTypeAndName(FUNCTION, YEAR_MONTH), "Func_00010");
        calculationFormulaDictionary.put(combineElementTypeAndName(FUNCTION, YEAR_EXTRACTION), "Func_00011");
        calculationFormulaDictionary.put(combineElementTypeAndName(FUNCTION, MONTH_EXTRACTION), "Func_00012");
        calculationFormulaDictionary.put(combineElementTypeAndName(VARIABLE, SYSTEM_YMD_DATE), "vari_00001");
        calculationFormulaDictionary.put(combineElementTypeAndName(VARIABLE, SYSTEM_Y_DATE), "vari_00002");
        calculationFormulaDictionary.put(combineElementTypeAndName(VARIABLE, SYSTEM_YM_DATE), "vari_00003");
        calculationFormulaDictionary.put(combineElementTypeAndName(VARIABLE, PROCESSING_YEAR_MONTH), "vari_00004");
        calculationFormulaDictionary.put(combineElementTypeAndName(VARIABLE, PROCESSING_YEAR), "vari_00005");
        calculationFormulaDictionary.put(combineElementTypeAndName(VARIABLE, REFERENCE_TIME), "vari_00006");
        calculationFormulaDictionary.put(combineElementTypeAndName(VARIABLE, STANDARD_DAY), "vari_00007");
        calculationFormulaDictionary.put(combineElementTypeAndName(VARIABLE, WORKDAY), "vari_00008");
        calculationFormulaDictionary.put(FORMULA, "calc_00");
        calculationFormulaDictionary.put(WAGE_TABLE, "wage_00");
    }
    /**
     *
     * @param formulaElements  must be sort asc
     * @param yearMonth  with qmm017 is selected year month
     * @return display detail calculation formula (ex: 支給{payment 1}＋控除{deduction 1})
     */
    public String convertFromRegisterContentToDisplayContent(List<String> formulaElements, int yearMonth) {
        String displayFormula = "", displayContent = "", cid = AppContexts.user().companyId();
        Map<String, String> paymentItem = statementItemRepository.getItemCustomByCategoryAndDeprecated(cid, CategoryAtr.PAYMENT_ITEM.value, false).stream().collect(Collectors.toMap(StatementItemCustom::getItemNameCd, StatementItemCustom::getName));
        Map<String, String> deductionItem = statementItemRepository.getItemCustomByCategoryAndDeprecated(cid, CategoryAtr.DEDUCTION_ITEM.value, false).stream().collect(Collectors.toMap(StatementItemCustom::getItemNameCd, StatementItemCustom::getName));
        Map<String, String> attendanceItem = statementItemRepository.getItemCustomByCategoryAndDeprecated(cid, CategoryAtr.ATTEND_ITEM.value, false).stream().collect(Collectors.toMap(StatementItemCustom::getItemNameCd, StatementItemCustom::getName));
        Map<String, String> companyUnitPriceItem = payrollUnitPriceRepository.getPayrollUnitPriceByYearMonth(yearMonth).stream().collect(Collectors.toMap(item -> item.getCode().v(), item -> item.getName().v()));
        Map<String, String> individualUnitPriceItem = salaryPerUnitPriceRepository.getAllAbolitionSalaryPerUnitPrice();
        Map<String, String> formulaItem = formulaRepository.getFormulaWithUsableDetailSetting();
        Map<String, String> wageTableItem = wageTableRepository.getAllWageTable(cid).stream().collect(Collectors.toMap(element -> element.getWageTableCode().v(), element -> element.getWageTableName().v()));
        for (String formulaElement: formulaElements) {
            displayFormula = convertToDisplayContent(formulaElement, paymentItem, deductionItem, attendanceItem, companyUnitPriceItem, individualUnitPriceItem, formulaItem, wageTableItem);
            displayFormula += displayContent;
        }
        return displayFormula;
    }
    public String convertToDisplayContent (String formulaElement, Map<String, String> paymentItem, Map<String, String> deductionItem,
           Map<String, String> attendanceItem, Map<String, String> companyUnitPriceItem, Map<String, String> individualUnitPriceItem,
           Map<String, String> formulaItem, Map<String, String> wageTableItem) {
        String elementType = formulaElement.substring(0, 6);
        String elementCode = formulaElement.substring(6, formulaElement.length());
        if (elementType.startsWith("Func") || elementType.startsWith("vari")){
            return calculationFormulaDictionary.get(formulaElement);
        }
        String registerContent = calculationFormulaDictionary.get(elementType);
        if (elementType.startsWith("0000_0"))
            return combineElementTypeAndName(registerContent, paymentItem.get(elementCode));
        if (elementType.startsWith("0001_0"))
            return combineElementTypeAndName(registerContent, deductionItem.get(elementCode));
        if (elementType.startsWith("0002_0"))
            return combineElementTypeAndName(registerContent, attendanceItem.get(elementCode));
        if (elementType.startsWith("U000_0"))
            return combineElementTypeAndName(registerContent, companyUnitPriceItem.get(elementCode));
        if (elementType.startsWith("U001_0"))
            return combineElementTypeAndName(registerContent, individualUnitPriceItem.get(elementCode));
        elementType = formulaElement.substring(0, 7);
        elementCode = formulaElement.substring(7, formulaElement.length());
        if (elementType.startsWith("calc")) {
            return combineElementTypeAndName(calculationFormulaDictionary.get(elementType), formulaItem.get(elementCode));
        }
        if (elementType.startsWith("wage")) {
            return combineElementTypeAndName(calculationFormulaDictionary.get(elementType),  wageTableItem.get(elementCode));
        }
        return formulaElement;
    }

    public static String combineElementTypeAndName (String elementType, String elementName) {
        return elementType + OPEN_CURLY_BRACKET + elementName + CLOSE_CURLY_BRACKET;
    }

    public String calculateDisplayCalculationFormula (int type, String formula, Map<String, String> replaceValues) {
        // type 1: Salary 給与
        // type 2: Bonus 賞与
        // type 3: Trial calculation お試し計算
        for(Map.Entry replaceValue : replaceValues.entrySet()) {
            formula = formula.replaceAll(replaceValue.getKey().toString(), replaceValue.getValue().toString());
        }
        formula = calculateSystemVariable(type, formula);
        formula = calculateFunction(formula);
        formula = calculateSuffixFormula(formula) + "";
        return formula;
    }
    public String calculateSystemVariable (int type, String formulaElement) {
        if (type != 0 || type !=2) return "";
        Map<String, String> processYearMonthAndReferenceTime = formulaService.getProcessYearMonthAndReferenceTime();
        int startFunctionIndex, endFunctionIndex;
        String systemVariable, systemVariableResult;
        while (formulaElement.indexOf(VARIABLE) > -1) {
            startFunctionIndex = formulaElement.lastIndexOf(VARIABLE);
            endFunctionIndex = formulaElement.substring(startFunctionIndex).indexOf(CLOSE_CURLY_BRACKET) + 1;
            systemVariable = formulaElement.substring(startFunctionIndex, endFunctionIndex);
            systemVariableResult = getSystemValueBySystemVariable(systemVariable, processYearMonthAndReferenceTime);
            formulaElement = formulaElement.replaceAll(systemVariable,  systemVariableResult);
        }
        return formulaElement;
    }

    public String getSystemValueBySystemVariable (String systemVariable, Map<String, String> processYearMonthAndReferenceTime) {
        String functionName = systemVariable.substring(systemVariable.indexOf(OPEN_CURLY_BRACKET) + 1, systemVariable.indexOf(CLOSE_CURLY_BRACKET));
        if (functionName.equals(SYSTEM_YMD_DATE)) return GeneralDate.today().toString();
        if (functionName.equals(SYSTEM_Y_DATE)) return GeneralDate.today().toString().format("YYYY");
        if (functionName.equals(SYSTEM_YM_DATE)) return GeneralDate.today().toString().format("YYYY/MM");
        if (functionName.equals(PROCESSING_YEAR)) return processYearMonthAndReferenceTime.get("processYearMonth");
        if (functionName.equals(PROCESSING_YEAR_MONTH)) return processYearMonthAndReferenceTime.get("processYearMonth");
        if (functionName.equals(REFERENCE_TIME)) return processYearMonthAndReferenceTime.get("referenceDate");
        throw new BusinessException("MsgQ_223", systemVariable);
    }
    public String calculateFunction (String formulaElement) {
        int startFunctionIndex, endFunctionIndex;
        String functionSyntax;
        while (formulaElement.indexOf(FUNCTION) > -1) {
            startFunctionIndex = formulaElement.lastIndexOf(FUNCTION);
            endFunctionIndex = indexOfEndElement(startFunctionIndex, formulaElement) + 1;
            functionSyntax = formulaElement.substring(startFunctionIndex, endFunctionIndex);
            formulaElement = formulaElement.replace(functionSyntax,  calculateSingleFunction(functionSyntax));
        }
        return formulaElement;
    }
    private String [] convertToPostfix (String formulaContent) {
        String regex = "";
        String [] formulaElements = formulaContent.split(regex);
        Stack<String> postfix = new Stack<>(), operators = new Stack<>();
        String operator, currentElement, closeBracket;
        for(int index = 0; index < formulaElements.length; index++){
            currentElement = formulaElements[index];
            if (!isNaN(currentElement)) {
                postfix.push(currentElement);
                continue;
            }
            if (currentElement == OPEN_BRACKET) {
                operators.push(OPEN_BRACKET);
                continue;
            }
            if (currentElement == CLOSE_BRACKET) {
                while ((operator = operators.pop()) != OPEN_BRACKET) {
                    postfix.push(operator);
                }
                continue;
            }
            while(getPriority(currentElement) <= getPriority(operators.get(operators.size() - 1)) && !operators.isEmpty()){
                postfix.push(operators.pop());
            }
            operators.push(currentElement);
        }
        while (operators.isEmpty()){
            postfix.push(operators.pop());
        }
        return postfix.toArray(new String[0]);
    }

    private int getPriority(String operator){
        if (operator == PLUS || operator == SUBTRACT)
            return 1 ;
        if (operator == MULTIPLICY || operator == DIVIDE)
            return 2;
        if (operator == POW)
            return 3;
        return 0;
    }

    private Double calculateSuffixFormula (String formulaContent) {
        String currentChar, operand1, operand2;
        Stack<String> operands = new Stack();
        String [] postFix = convertToPostfix(formulaContent);
        for(int index = 0; index < postFix.length; index++){
            currentChar = postFix[index];
            if (!isNaN(currentChar)) {
                operands.push(currentChar);
                continue;
            }
            operand1 = operands.pop();
            operand2 = operands.pop();
            operands.push(calculateSingleFormula(operand1, operand2, currentChar));
        }
        try {
            return Double.parseDouble(operands.pop());
        } catch (NumberFormatException e) {
            throw new RuntimeException("Value to calculate must be numeric");
        }

    }

    private String calculateSingleFunction (String functionSyntax) {
        String functionName = functionSyntax.substring(functionSyntax.indexOf(OPEN_CURLY_BRACKET) + 1, functionSyntax.indexOf(CLOSE_CURLY_BRACKET));
        String [] functionParameter = functionSyntax.substring(functionSyntax.indexOf(OPEN_BRACKET) + 1, functionSyntax.lastIndexOf(CLOSE_BRACKET)).split(COMMA_CHAR);
        if (functionName == CONDITIONAL)
            return calculateFunctionCondition(functionParameter);
        if (functionName == YEAR_MONTH) {
            Integer additionalMonth = Integer.parseInt(functionParameter[1]);
            return GeneralDate.fromString(functionParameter[0], "YYYY/MM/DD").addMonths(additionalMonth).toString();
        }
        if (functionName == AND) {
            return String.valueOf(!Arrays.asList().contains("FALSE")).toUpperCase();
        }
        if (functionName == OR) {
            return String.valueOf(Arrays.asList().contains("TRUE")).toUpperCase();
        }
        if (functionName == YEAR_EXTRACTION)
            return GeneralDate.today().year() + "";
        if (functionName == MONTH_EXTRACTION)
            return GeneralDate.today().month() + "";
        if (functionName == ROUND_OFF)
            return Math.round(Double.parseDouble(functionParameter[0])) + "";
        if (functionName == ROUND_UP)
            return Math.ceil(Double.parseDouble(functionParameter[0])) + "";
        if (functionName == TRUNCATION)
            return Math.floor(Double.parseDouble(functionParameter[0])) +"";
        if (functionName == MAX_VALUE)
            return getMaxValue(functionParameter);
        if (functionName == MIN_VALUE)
            return getMinValue(functionParameter);
        if (functionName == NUM_OF_FAMILY_MEMBER)
            return "0";
        return null;
    }

    private String calculateFunctionCondition (String [] functionParameter) {
        String conditionSeparators = "\\>|\\<|\\≦|\\≧|\\＝|\\≠";
        String operand1, operand2, operator, result1 = functionParameter[1], result2 = functionParameter[2];
        String [] firstParameterData = functionParameter[0].split("");
        Double operand1Value = calculateSuffixFormula(firstParameterData[0]);
        Double operand2Value = calculateSuffixFormula(firstParameterData[2]);
        operator = firstParameterData[1];
        switch (operator){
            case GREATER:
                return operand1Value > operand2Value ? result1 : result2;
            case LESS:
                return operand1Value < operand2Value ? result1 : result2;
            case GREATER_OR_EQUAL:
                return operand1Value >= operand2Value ? result1 : result2;
            case LESS_OR_EQUAL:
                return operand1Value <= operand2Value ? result1 : result2;
            case EQUAL:
                return operand1Value == operand2Value ? result1 : result2;
            case DIFFERENCE:
                return operand1Value != operand2Value ? result1 : result2;
        }
        return "";
    }
    private String calculateSingleFormula (String operand1, String operand2, String operator) {
        try {
            Double operand1Value = Double.parseDouble(operand1);
            Double operand2Value = Double.parseDouble(operand2);
            switch (operator) {
                case PLUS:
                    return operand2Value + operand1Value + "";
                case SUBTRACT:
                    return operand2Value - operand1Value + "";
                case MULTIPLICY:
                    return operand2Value * operand1Value + "";
                case DIVIDE:
                    return operand2Value / operand1Value + "";
                case POW:
                    return Math.pow(operand2Value, operand1Value) + "";
                default:
                    return operand1Value + "";
            }
        } catch (NumberFormatException e) {
            throw new RuntimeException("Value to calculate must be numeric");
        }
    }
    private int indexOfEndElement (int startFunctionIndex, String formula) {
        int index, openBracketNum = 0, closeBracketNum = 0;
        String currentChar;
        for(index = startFunctionIndex; index < formula.length() ; index ++ ) {
            currentChar = Character.toString(formula.charAt(index));
            if (currentChar == OPEN_BRACKET) openBracketNum ++;
            if (currentChar == CLOSE_BRACKET){
                closeBracketNum ++;
                if (openBracketNum == closeBracketNum){
                    return index;
                }
            }
        }
        return -1;
    }
    private Boolean isNaN (String number) {
        return !number.matches("-?\\d+(\\.\\d+)?");
    }

    private String getMinValue (String [] values) {
        long minValue = Long.MAX_VALUE;
        for (String value: values) {
            if (Long.parseLong(value) < minValue) {
                minValue = Long.parseLong(value);
            }
        }
        return minValue + "";
    }

    private String getMaxValue (String [] values) {
        long minValue = Long.MIN_VALUE;
        for (String value: values) {
            if (Long.parseLong(value) > minValue) {
                minValue = Long.parseLong(value);
            }
        }
        return minValue + "";
    }
}
