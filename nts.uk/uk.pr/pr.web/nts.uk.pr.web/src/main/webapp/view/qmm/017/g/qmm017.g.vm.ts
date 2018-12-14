module nts.uk.pr.view.qmm017.g.viewmodel {
    import getShared = nts.uk.ui.windows.getShared;
    import block = nts.uk.ui.block;
    export class ScreenModel {
        calculationFormulaList: KnockoutObservableArray<any> = ko.observableArray([]);
        trialCalculationResult: KnockoutObservable<number> = ko.observable(null);
        formulaContent: KnockoutObservable<string> = ko.observable(null);
        formulaElement: any;

        CONCAT_CHAR = '＠'; COMMA_CHAR = ',';
        PLUS = '＋'; SUBTRACT = 'ー'; MULTIPLICY = '×'; DIVIDE = '÷'; POW = '^'; OPEN_BRACKET = '('; CLOSE_BRACKET = ')';
        GREATER = '>'; LESS = '<'; LESS_OR_EQUAL = '≦'; GREATER_OR_EQUAL = '≧'; EQUAL = '＝'; DIFFERENCE = '≠';

        PAYMENT = '支給'; DEDUCTION = '控除'; ATTENDANCE = '勤怠'; COMPANY_UNIT_PRICE = '会社単価'; FUNCTION = '関数';
        INDIVIDUAL_UNIT_PRICE = '個人単価';VARIABLE = '変数'; PERSON = '個人'; FORMULA = '計算式'; WAGE_TABLE = '賃金';
        CONDITIONAL = '条件式'; AND = 'かつ'; OR = 'または'; ROUND_OFF = '四捨五入'; TRUNCATION = '切り捨て';
        ROUND_UP = '切り上げ'; MAX_VALUE = '最大値'; MIN_VALUE = '最小値'; NUM_OF_FAMILY_MEMBER = '家族人数';
        YEAR_MONTH = '年月加算'; YEAR_EXTRACTION = '年抽出'; MONTH_EXTRACTION = '月抽出';
        SYSTEM_YMD_DATE = 'システム日付（年月日）'; SYSTEM_YM_DATE = 'システム日付（年月）'; SYSTEM_Y_DATE = 'システム日付（年）'; PROCESSING_YEAR_MONTH = '処理年月';
        PROCESSING_YEAR = '処理年'; REFERENCE_TIME = '基準時間'; STANDARD_DAY = '基準日数'; WORKDAY = '要勤務日数';
        constructor() {
            let self = this;
            let params = getShared("QMM017_G_PARAMS");
            self.formulaContent(params.formula);
            self.formulaElement = params.formulaElement;
            self.extractInputParameter(params.formula);
            $('#G1_2').ntsFixedTable({height: 178});
        }
        extractInputParameter (formula) {
            let self = this, separators = ['\\\＋', 'ー', '\\×', '÷', '\\^', '\\\(', '\\\)', '\\>', '\\<', '\\\≦', '\\\≧', '\\\＝', '\\\≠', '\\\,'].join("|");
            let operands = formula.split(new RegExp(separators, 'g')).map(item => item.trim()).filter(item => {
                return (item && item.length);
            });
            let calculationFormulaData = [];
            operands.forEach(operand => {
                if (operand.startsWith(self.PAYMENT) || operand.startsWith(self.DEDUCTION) || operand.startsWith(self.ATTENDANCE)
                    || operand.startsWith(self.COMPANY_UNIT_PRICE) || operand.startsWith(self.INDIVIDUAL_UNIT_PRICE)
                    || operand.startsWith(self.WAGE_TABLE)){
                    calculationFormulaData.push({
                        formulaItem: operand,
                        trialCalculationValue: ko.observable(null)
                    })
                }
            })
            self.calculationFormulaList(calculationFormulaData);
        }

        calculationInServer () {
            $('.nts-input').trigger("validate");
            if (nts.uk.ui.errors.hasError()) return;
            let self = this, calculationFormulaData = ko.toJS(self.calculationFormulaList), formulaContent = self.formulaContent();
            calculationFormulaData.forEach(item => {
                formulaContent = formulaContent.replace(item.formulaItem, item.trialCalculationValue);
            });
            service.calculation({formulaContent: formulaContent}).done(function(result){
                console.log(result);
            }).fail(function(err){
                console.log(err);
            })
        }

        calculation () {
            $('.nts-input').trigger("validate");
            if (nts.uk.ui.errors.hasError()) return;
            let self = this, calculationFormulaData = ko.toJS(self.calculationFormulaList), formulaContent = self.formulaContent();
            calculationFormulaData.forEach(item => {
                formulaContent = formulaContent.replace(item.formulaItem, item.trialCalculationValue);
            });
            formulaContent = self.doCalculationFunction(formulaContent);
            let postFix = self.convertToPostfix(formulaContent);
            self.calculationPostfixFormula(postFix);
        }

        convertToPostfix (formulaContent) {
            let self = this, regex = new RegExp('([' + ['\\\＋', 'ー', '\\×', '÷', '\\^', '\\\(', '\\\)'].join('|') + '])');
            let formulaElements:any = formulaContent.split(regex).filter(item => {return item && item.length});
            let postfix = [], operators = [], operator, currentChar, closeBracket;
            for(let index = 0; index < formulaContent.length; index++){
                currentChar = formulaContent[index];
                if (!isNaN(currentChar)) {
                    postfix.push(currentChar);
                    continue;
                }
                if (currentChar == self.OPEN_BRACKET) {
                    operators.push(self.OPEN_BRACKET);
                    continue;
                }
                if (currentChar == self.CLOSE_BRACKET) {
                    while (operators[operators.length-1] != self.OPEN_BRACKET) {
                        postfix.push(operators.pop());
                    }
                    continue;
                }
                while(self.getPriority(currentChar) <= self.getPriority(operators[operators.length-1]) && operators.length > 0){
                    postfix.push(operators.pop());
                }
                operators.push(currentChar);
            }
            while (operators.length > 0){
                postfix.push(operators.pop());
            }
            return postfix;
        }

        getPriority(operator): number{
            let self = this;
            if (operator == self.PLUS || operator == self.SUBTRACT)
                return 1 ;
            if (operator == self.MULTIPLICY || operator == self.DIVIDE)
                return 2;
            if (operator == self.POW)
                return 3;
            return 0;
        }

        calculationPostfixFormula (formulaContent) {
            let self = this,  currentChar, result, operands = [], operand1, operand2;
            for(let index = 0; index < formulaContent.length; index++){
                currentChar = formulaContent[index];
                if (!isNaN(currentChar)) {
                    operands.push(currentChar);
                    continue;
                }
                operand1 = operands.pop();
                operand2 = operands.pop();
                operands.push(self.doCalculation(operand1, operand2, currentChar));
            }
            self.trialCalculationResult(operands.pop());
        }
        doCalculationFunction (formulaElement) {
            let self = this, originalFormula = formulaElement, startFunctionIndex, endFunctionIndex, functionsSyntax = [];
            let self = this;
            while (formulaElement.indexOf(self.FUNCTION) > -1) {
                startFunctionIndex = formulaElement.indexOf(self.FUNCTION);
                endFunctionIndex = startFunctionIndex + formulaElement.substring(startFunctionIndex).indexOf(self.CLOSE_BRACKET) + 1;
                functionsSyntax.push(formulaElement.substring(startFunctionIndex, endFunctionIndex));
                formulaElement = formulaElement.substring( endFunctionIndex );
            }
            functionsSyntax.forEach(functionSyntax => {
                let functionName = functionSyntax.substring(functionSyntax.indexOf(self.CONCAT_CHAR) + 1, functionSyntax.indexOf(self.OPEN_BRACKET)),
                    functionParameter = functionSyntax.substring(functionSyntax.indexOf(self.OPEN_BRACKET) + 1, functionSyntax.length-1).split(self.COMMA_CHAR).map(item => item.trim());
                if (functionName == self.CONDITIONAL)
                    originalFormula = originalFormula.replace(functionSyntax, self.doCalculationCondition(functionParameter));
                if (functionName == self.ROUND_OFF)
                    originalFormula = originalFormula.replace(functionSyntax, (Number(Math.round(functionParameter[0]))));
                if (functionName == self.ROUND_UP)
                    originalFormula = originalFormula.replace(functionSyntax, (Number(Math.ceil(functionParameter[0]))));
                if (functionName == self.TRUNCATION)
                    originalFormula = originalFormula.replace(functionSyntax, (Number(Math.floor(functionParameter[0]))));
                if (functionName == self.MAX_VALUE)
                    originalFormula = originalFormula.replace(functionSyntax, (Number(Math.max(...functionParameter))));
                if (functionName == self.MIN_VALUE)
                    originalFormula = originalFormula.replace(functionSyntax, (Number(Math.min(...functionParameter))));
                if (functionName == self.NUM_OF_FAMILY_MEMBER)
                    originalFormula = originalFormula.replace(functionSyntax, 0);
                if (functionName == self.YEAR_MONTH)
                    originalFormula = originalFormula.replace(functionSyntax, (Number(moment(functionParameter[0]).add(functionParameter[1], 'm'))));
                if (functionName == self.YEAR_MONTH)
                    originalFormula = originalFormula.replace(functionSyntax, moment(functionParameter[0]).year());
                if (functionName == self.YEAR_MONTH)
                    originalFormula = originalFormula.replace(functionSyntax, moment(functionParameter[0]).month());
            })
            return originalFormula;
        }
        doCalculationCondition (functionParameter) {
            let self = this, conditionSeparators = ['\\>', '\\<', '\\\≦', '\\\≧', '\\\＝', '\\\≠'].join("|");
            let operand1, operand2, operator, result1 = functionParameter[1], result2 = functionParameter[2];
            let firstParameterData = functionParameter[0].split(new RegExp('([' +conditionSeparators + '])')).map(item => item.trim()).filter(item => {
                return (item && item.length);
            });
            operand1 = Number(firstParameterData[0]);
            operand2 = Number(firstParameterData[2]);
            operator = firstParameterData[1];
            switch (operator){
                case self.GREATER:
                    return operand1 > operand2 ? result1 : result2;
                case self.LESS:
                    return operand1 < operand2 ? result1 : result2;
                case self.GREATER_OR_EQUAL:
                    return operand1 >= operand2 ? result1 : result2;
                case self.LESS_OR_EQUAL:
                    return operand1 <= operand2 ? result1 : result2;
                case self.EQUAL:
                    return operand1 == operand2 ? result1 : result2;
                case self.DIFFERENCE:
                    return operand1 != operand2 ? result1 : result2;
                default :
                    return 0;
            }
        }
        doCalculation (operand1, operand2, operator) {
            operand1 = Number(operand1);
            operand2 = Number(operand2);
            let self = this;
            switch (operator){
                case self.PLUS:
                    return operand2 + operand1;
                case self.SUBTRACT:
                    return operand2 - operand1;
                case self.MULTIPLICY:
                    return operand2 * operand1;
                case self.DIVIDE:
                    return operand2 / operand1;
                case self.POW:
                    return Math.pow(operand2, operand1);
                default :
                    return 0;
            }
        }

        closeDialog () {
            nts.uk.ui.windows.close();
        }
    }
}


