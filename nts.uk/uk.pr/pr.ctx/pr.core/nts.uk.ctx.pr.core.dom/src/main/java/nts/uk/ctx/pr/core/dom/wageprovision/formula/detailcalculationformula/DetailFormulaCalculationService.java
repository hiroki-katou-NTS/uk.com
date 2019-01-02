package nts.uk.ctx.pr.core.dom.wageprovision.formula.detailcalculationformula;
import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.core.dom.wageprovision.companyuniformamount.PayrollUnitPriceRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.formula.*;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.CategoryAtr;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.StatementItemCustom;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.StatementItemRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.unitpricename.SalaryPerUnitPriceRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.WageTableRepository;
import nts.uk.shr.com.context.AppContexts;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.time.format.DateTimeParseException;
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

    @Inject
    private FormulaRepository formulaRepository;

    @Inject
    private DetailFormulaSettingRepository detailFormulaSettingRepository;

    /**
     * All following text belong to business
     * Try to use text resource for other language
     */
    private static final String
            OPEN_CURLY_BRACKET = "{", CLOSE_CURLY_BRACKET = "}", COMMA_CHAR = "、",  HALF_SIZE_COMMA_CHAR = ",",
            PLUS = "＋", SUBTRACT = "ー", MULTIPLICITY = "×", DIVIDE = "÷", POW = "^", OPEN_BRACKET = "(", CLOSE_BRACKET = ")",
            GREATER = ">", LESS = "<", LESS_OR_EQUAL = "≦", GREATER_OR_EQUAL = "≧", EQUAL = "＝", DIFFERENCE = "≠",
            HALF_SIZE_PLUS = "+", HALF_SIZE_SUBTRACT = "-",
            HALF_SIZE_LESS_OR_EQUAL = "≤", HALF_SIZE_GREATER_OR_EQUAL = "≥", HALF_SIZE_EQUAL = "=",
            PROGRAMING_MULTIPLICITY = "*", PROGRAMING_DIVIDE = "/", PROGRAMMING_DIFFERENCE = "#";
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
    public String getDetailFormulaDisplayContent(List<String> formulaElements, int yearMonth) {
        String displayFormula = "", displayContent = "", cid = AppContexts.user().companyId();
        Map<String, String> paymentItem = statementItemRepository.getItemCustomByCategoryAndDeprecated(cid, CategoryAtr.PAYMENT_ITEM.value, false).stream().collect(Collectors.toMap(StatementItemCustom::getItemNameCd, StatementItemCustom::getName));
        Map<String, String> deductionItem = statementItemRepository.getItemCustomByCategoryAndDeprecated(cid, CategoryAtr.DEDUCTION_ITEM.value, false).stream().collect(Collectors.toMap(StatementItemCustom::getItemNameCd, StatementItemCustom::getName));
        Map<String, String> attendanceItem = statementItemRepository.getItemCustomByCategoryAndDeprecated(cid, CategoryAtr.ATTEND_ITEM.value, false).stream().collect(Collectors.toMap(StatementItemCustom::getItemNameCd, StatementItemCustom::getName));
        Map<String, String> companyUnitPriceItem = payrollUnitPriceRepository.getPayrollUnitPriceByYearMonth(yearMonth).stream().collect(Collectors.toMap(item -> item.getCode().v(), item -> item.getName().v()));
        Map<String, String> individualUnitPriceItem = salaryPerUnitPriceRepository.getAllAbolitionSalaryPerUnitPrice();
        Map<String, String> wageTableItem = wageTableRepository.getAllWageTable(cid).stream().collect(Collectors.toMap(element -> element.getWageTableCode().v(), element -> element.getWageTableName().v()));
        for (String formulaElement: formulaElements) {
            displayContent = convertToDisplayContent(formulaElement, paymentItem, deductionItem, attendanceItem, companyUnitPriceItem, individualUnitPriceItem, wageTableItem, yearMonth);
            displayFormula += displayContent;
        }
        return displayFormula;
    }
    private String convertToDisplayContent (String formulaElement, Map<String, String> paymentItem, Map<String, String> deductionItem,
           Map<String, String> attendanceItem, Map<String, String> companyUnitPriceItem, Map<String, String> individualUnitPriceItem,
           Map<String, String> wageTableItem, int yearMonth) {
        // is number or operator
        if (!isNaN(formulaElement) || formulaElement.length() < 2 ) return formulaElement;
        String elementType = formulaElement.substring(0, 6);
        String elementCode = formulaElement.substring(6, formulaElement.length());
        if (elementType.startsWith("Func") || elementType.startsWith("vari")){
            return getDisplayTypeByRegisterType(formulaElement, formulaElement);
        }
        String displayContent = getDisplayTypeByRegisterType(formulaElement, elementType);
        if (elementType.startsWith("0000_0"))
            return combineElementTypeAndName(displayContent, paymentItem.get(elementCode));
        if (elementType.startsWith("0001_0"))
            return combineElementTypeAndName(displayContent, deductionItem.get(elementCode));
        if (elementType.startsWith("0002_0"))
            return combineElementTypeAndName(displayContent, attendanceItem.get(elementCode));
        if (elementType.startsWith("U000_0"))
            return combineElementTypeAndName(displayContent, companyUnitPriceItem.get(elementCode));
        if (elementType.startsWith("U001_0"))
            return combineElementTypeAndName(displayContent, individualUnitPriceItem.get(elementCode));
        if (elementType.startsWith("calc"))
            return getEmbeddedFormulaDisplayContent(formulaElement, yearMonth);
        elementType = formulaElement.substring(0, 7);
        elementCode = formulaElement.substring(7, formulaElement.length());
        if (elementType.startsWith("wage"))
            return combineElementTypeAndName(getDisplayTypeByRegisterType(formulaElement, elementType),  wageTableItem.get(elementCode));
        return formulaElement;
    }

    private String getDisplayTypeByRegisterType (String formulaElement, String elementType) {
        for(Map.Entry dictionaryItem : calculationFormulaDictionary.entrySet()) {
            if (dictionaryItem.getValue().equals(elementType)) return dictionaryItem.getKey().toString();
        }
        throw new BusinessException("MsgQ_233", formulaElement);
    }

    public String getEmbeddedFormulaDisplayContent (String formulaElement, int yearMonth) {
        String formulaCode = formulaElement.substring(7, formulaElement.length());
        Optional<FormulaHistory> optFormulaHistory = formulaRepository.getFormulaHistoryByCode(formulaCode);
        if (!optFormulaHistory.isPresent() && optFormulaHistory.get().getHistory().isEmpty()) {
            throw new BusinessException("MsgQ_233", formulaElement);
        }
        FormulaHistory formulaHistory = optFormulaHistory.get();
        Optional<DetailFormulaSetting> optDetailFormulaSetting = detailFormulaSettingRepository.getDetailFormulaSettingById(formulaHistory.getHistory().get(0).identifier());
        if (!optDetailFormulaSetting.isPresent()) throw new BusinessException("MsgQ_233", formulaElement);
        DetailFormulaSetting detailFormulaSetting = optDetailFormulaSetting.get();
        List<String> formulaElements = detailFormulaSetting.getDetailCalculationFormula().stream().map(item -> item.getFormulaElement().v()).collect(Collectors.toList());
        return getDetailFormulaDisplayContent(formulaElements, yearMonth);
    }

    private static String combineElementTypeAndName (String elementType, String elementName) {
        return elementType + OPEN_CURLY_BRACKET + elementName + CLOSE_CURLY_BRACKET;
    }

    public String calculateDisplayCalculationFormula (int type, String formula, Map<String, String> replaceValues, int roundingMethod, int roundingResult) {
        // type 1: Salary 給与
        // type 2: Bonus 賞与
        // type 3: Trial calculation お試し計算
        for(Map.Entry replaceValue : replaceValues.entrySet()) {
            formula = formula.replace(replaceValue.getKey().toString(), replaceValue.getValue().toString());
        }
        formula = calculateSystemVariable(type, formula);
        formula = calculateFunction(formula);
        formula = calculateSuffixFormula(formula) + "";
        if (isNaN(formula)) throw new BusinessException("Msg_235");
        return roundingResult(Double.parseDouble(formula), roundingMethod, roundingResult);
    }

    private String roundingResult (Double result, int roundingMethod, int roundingResult) {
        int roundingValue = 0;
        if (roundingMethod == RoundingPosition.ONE_YEN.value) roundingValue = 1;
        if (roundingMethod == RoundingPosition.TEN_YEN.value) roundingValue = 10;
        if (roundingMethod == RoundingPosition.ONE_HUNDRED_YEN.value) roundingValue = 100;
        if (roundingMethod == RoundingPosition.ONE_THOUSAND_YEN.value) roundingValue = 1000;
        if (roundingResult == Rounding.ROUND_UP.value)
            return Math.ceil(result) * roundingValue + "";
        if (roundingResult == Rounding.TRUNCATION.value)
            return Math.floor(result) * roundingValue + "";
        if (roundingResult == Rounding.DOWN_1_UP_2.value)
            return Math.floor(result/roundingValue + 0.9) * roundingValue + "";
        if (roundingResult == Rounding.DOWN_2_UP_3.value)
            return Math.floor(result/roundingValue + 0.8) * roundingValue + "";
        if (roundingResult == Rounding.DOWN_3_UP_4.value)
            return Math.floor(result/roundingValue + 0.7) * roundingValue + "";
        if (roundingResult == Rounding.DOWN_4_UP_5.value)
            return Math.floor(result/roundingValue + 0.6) * roundingValue + "";
        if (roundingResult == Rounding.DOWN_5_UP_6.value)
            return Math.floor(result/roundingValue + 0.5) * roundingValue + "";
        if (roundingResult == Rounding.DOWN_6_UP_7.value)
            return Math.floor(result/roundingValue + 0.4) * roundingValue + "";
        if (roundingResult == Rounding.DOWN_7_UP_8.value)
            return Math.floor(result/roundingValue + 0.3) * roundingValue + "";
        if (roundingResult == Rounding.DOWN_8_UP_9.value)
            return Math.floor(result/roundingValue + 0.2) * roundingValue + "";
        return result + "";
    }

    private String calculateSystemVariable (int type, String formulaElement) {
        if (type != 0 && type !=2) return "";
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

    private String getSystemValueBySystemVariable (String systemVariable, Map<String, String> processYearMonthAndReferenceTime) {
        String functionName = systemVariable.substring(systemVariable.indexOf(OPEN_CURLY_BRACKET) + 1, systemVariable.indexOf(CLOSE_CURLY_BRACKET));
        if (functionName.equals(SYSTEM_YMD_DATE)) return GeneralDate.today().toString();
        if (functionName.equals(SYSTEM_Y_DATE)) return GeneralDate.today().toString().format("YYYY");
        if (functionName.equals(SYSTEM_YM_DATE)) return GeneralDate.today().toString().format("YYYY/MM");
        if (functionName.equals(PROCESSING_YEAR)) return processYearMonthAndReferenceTime.get("processYearMonth");
        if (functionName.equals(PROCESSING_YEAR_MONTH)) return processYearMonthAndReferenceTime.get("processYearMonth");
        if (functionName.equals(REFERENCE_TIME)) return processYearMonthAndReferenceTime.get("referenceDate");
        throw new BusinessException("MsgQ_233", systemVariable);
    }
    private String calculateFunction (String formulaElement) {
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
        String regex = "(?<=[＋ー*×÷^()><≦≧＝≠、+-=*/#=≤≥,])|(?=[＋ー*×÷^()><≦≧＝≠、+-=*/#=≤≥,])";
        String [] formulaElements = formulaContent.split(regex);
        Stack<String> postfix = new Stack<>(), operators = new Stack<>();
        String currentElement;
        for(int index = 0; index < formulaElements.length; index++){
            currentElement = formulaElements[index];
            if (!isNaN(currentElement)) {
                postfix.push(currentElement);
                continue;
            }
            if (currentElement.equals(OPEN_BRACKET)) {
                operators.push(OPEN_BRACKET);
                continue;
            }
            if (currentElement.equals(CLOSE_BRACKET)) {
                while (!operators.isEmpty() && !operators.lastElement().equals(OPEN_BRACKET)) {
                    postfix.push(operators.pop());
                }
                operators.pop();
                continue;
            }
            while(!operators.isEmpty() && getPriority(currentElement) <= getPriority(operators.lastElement())){
                postfix.push(operators.pop());
            }
            operators.push(currentElement);
        }
        while (!operators.isEmpty()){
            postfix.push(operators.pop());
        }
        return postfix.toArray(new String[0]);
    }

    private int getPriority(String operator){
        if (operator.equals(PLUS) || operator.equals(HALF_SIZE_PLUS) || operator.equals(SUBTRACT) || operator.equals(HALF_SIZE_SUBTRACT))
            return 1 ;
        if (operator.equals(MULTIPLICITY) || operator.equals(PROGRAMING_MULTIPLICITY) || operator.equals(DIVIDE) || operator.equals(PROGRAMING_DIVIDE))
            return 2;
        if (operator.equals(POW))
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
            throw new BusinessException("MsgQ_235");
        }

    }

    private String calculateSingleFunction (String functionSyntax) {
        String functionName = functionSyntax.substring(functionSyntax.indexOf(OPEN_CURLY_BRACKET) + 1, functionSyntax.indexOf(CLOSE_CURLY_BRACKET));
        String [] functionParameter = formatFunctionParameter(functionSyntax.substring(functionSyntax.indexOf(OPEN_BRACKET) + 1, functionSyntax.lastIndexOf(CLOSE_BRACKET)).split(COMMA_CHAR + "|" + HALF_SIZE_COMMA_CHAR));
        if (functionName.equals(CONDITIONAL))
            return calculateFunctionCondition(functionSyntax, functionParameter);
        if (functionName.equals(YEAR_MONTH)) {
            try {
                Integer additionalMonth = Integer.parseInt(functionParameter[1]);
                return GeneralDate.fromString(functionParameter[0], "yyyy/MM/dd").addMonths(additionalMonth).toString();
            } catch (DateTimeParseException | NumberFormatException e ) {
                throw new BusinessException("MsgQ_240", functionName);
            }
        }
        if (functionName.equals(AND)) {
            return logicAND(functionParameter);
        }
        if (functionName.equals(OR)) {
            return logicOR(functionParameter);
        }
        if (functionName.equals(YEAR_EXTRACTION)) {
            try {
                return GeneralDate.fromString(functionParameter[0], "yyyy/MM/dd").year() + "";
            } catch (DateTimeParseException | NumberFormatException e ) {
                throw new BusinessException("MsgQ_240", functionName);
            }
        }
        if (functionName.equals(MONTH_EXTRACTION)){
            try {
                return GeneralDate.fromString(functionParameter[0], "yyyy/MM/dd").month() + "";
            } catch (DateTimeParseException | NumberFormatException e ) {
                throw new BusinessException("MsgQ_240", functionName);
            }
        }
        if (functionName.equals(ROUND_OFF))
            return Math.round(calculateSuffixFormula(functionParameter[0])) + "";
        if (functionName.equals(ROUND_UP))
            return Math.ceil(calculateSuffixFormula(functionParameter[0])) + "";
        if (functionName.equals(TRUNCATION))
            return Math.floor(calculateSuffixFormula(functionParameter[0])) +"";
        if (functionName.equals(MAX_VALUE))
            return getMaxValue(functionParameter);
        if (functionName.equals(MIN_VALUE))
            return getMinValue(functionParameter);
        if (functionName.equals(NUM_OF_FAMILY_MEMBER))
            return "0";
        throw new BusinessException("MsgQ_233", functionSyntax.substring(0, functionSyntax.indexOf(OPEN_BRACKET)));
    }

    private String [] formatFunctionParameter (String [] functionParameters) {
        String functionParameter = "";
        for (int i = 0; i < functionParameters.length; i++){
            functionParameter = functionParameters[i];
            if (functionParameter.indexOf("\"") == 0 && functionParameter.lastIndexOf("\"") == functionParameter.length() - 1){
                functionParameters[i] = functionParameter.substring(1, functionParameter.length() - 1);
            }
        }
        return functionParameters;
    }

    private String logicAND (String [] functionParameters) {
        for (int i = 0; i < functionParameters.length ; i++) {
            if (functionParameters[i].toUpperCase().equals("FALSE")) return "FALSE";
        }
        return "TRUE";
    }

    private String logicOR (String [] functionParameters) {
        for (int i = 0; i < functionParameters.length ; i++) {
            if (functionParameters[i].toUpperCase().equals("TRUE")) return "TRUE";
        }
        return "FALSE";
    }

    private String calculateFunctionCondition (String functionSyntax, String [] functionParameter) {
        String conditionSeparators = "(?<=[><≦≧＝≠])|(?=[><≦≧＝≠])";
        String result1 = functionParameter[1], result2 = functionParameter[2];
        String [] firstParameterData = formatFunctionParameter(functionParameter[0].split(conditionSeparators));
        String conditionResult;
        if (firstParameterData.length == 1) {
            if (!firstParameterData[0].toUpperCase().equals("TRUE") && !firstParameterData[0].toUpperCase().equals("FALSE")) {
                throw new BusinessException("Msg_328", functionSyntax.substring(0, functionSyntax.indexOf(OPEN_BRACKET)));
            } else {
                conditionResult = firstParameterData[0];
            }
        } else {
            conditionResult = calculateSingleCondition (firstParameterData [0], firstParameterData [2], firstParameterData [1]);
        }
        if (conditionResult.toUpperCase().equals("TRUE")) return result1;
        return result2;
    }
    private String calculateSingleCondition (Comparable operand1, Comparable operand2, String operator) {
        if (operator.equals(GREATER) )
            return operand1.compareTo(operand2) > 0 ? "TRUE" : "FALSE";
        if (operator.equals(LESS))
            return operand1.compareTo(operand2) < 0 ? "TRUE" : "FALSE";
        if (operator.equals(GREATER_OR_EQUAL) || operator.equals(HALF_SIZE_GREATER_OR_EQUAL))
            return operand1.compareTo(operand2) >= 0 ? "TRUE" : "FALSE";
        if (operator.equals(LESS_OR_EQUAL) || operator.equals(HALF_SIZE_LESS_OR_EQUAL))
            return operand1.compareTo(operand2) <= 0 ? "TRUE" : "FALSE";
        if (operator.equals(EQUAL) || operator.equals(HALF_SIZE_EQUAL))
            return operand1.compareTo(operand2) == 0 ? "TRUE" : "FALSE";
        if (operator.equals(DIFFERENCE) || operator.equals(PROGRAMMING_DIFFERENCE))
            return operand1.compareTo(operand2) != 0 ? "TRUE" : "FALSE";
        return "FALSE";
    }
    private String calculateSingleFormula (String operand1, String operand2, String operator) {
        try {
            Double operand1Value = Double.parseDouble(operand1);
            Double operand2Value = Double.parseDouble(operand2);
            if (operator.equals(PLUS) || operator.equals(HALF_SIZE_PLUS))
                return operand2Value + operand1Value + "";
            if (operator.equals(SUBTRACT) || operator.equals(HALF_SIZE_SUBTRACT))
                return operand2Value - operand1Value + "";
            if (operator.equals(MULTIPLICITY) || operator.equals(PROGRAMING_MULTIPLICITY))
                return operand2Value * operand1Value + "";
            if (operator.equals(DIVIDE) || operator.equals(PROGRAMING_DIVIDE))
                return operand2Value / operand1Value + "";
            if (operator.equals(POW))
                return Math.pow(operand2Value, operand1Value) + "";
            return operand1Value + "";
        } catch (NumberFormatException e) {
            throw new BusinessException("MsgQ_235");
        }
    }
    private int indexOfEndElement (int startFunctionIndex, String formula) {
        int index, openBracketNum = 0, closeBracketNum = 0;
        String currentChar;
        for(index = startFunctionIndex; index < formula.length() ; index ++ ) {
            currentChar = Character.toString(formula.charAt(index));
            if (currentChar.equals(OPEN_BRACKET)) openBracketNum ++;
            if (currentChar.equals(CLOSE_BRACKET)){
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
        Double minValue = Double.MAX_VALUE, valueInDouble;;
        for (String value: values) {
            valueInDouble = calculateSuffixFormula(value);
            if (valueInDouble < minValue) {
                minValue = valueInDouble;
            }
        }
        return minValue + "";
    }

    private String getMaxValue (String [] values) {
        Double minValue = Double.MIN_VALUE, valueInDouble;
        for (String value: values) {
            valueInDouble = calculateSuffixFormula(value);
            if (valueInDouble > minValue) {
                minValue = valueInDouble;
            }
        }
        return minValue + "";
    }
}
