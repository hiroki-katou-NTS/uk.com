module nts.uk.pr.view.qmm017.g.viewmodel {
    import getShared = nts.uk.ui.windows.getShared;
    import block = nts.uk.ui.block;
    import dialog = nts.uk.ui.dialog;

    export class ScreenModel {
        calculationFormulaList: KnockoutObservableArray<any> = ko.observableArray([]);
        trialCalculationResult: KnockoutObservable<number> = ko.observable(null);
        formulaContent: KnockoutObservable<string> = ko.observable(null);
        formulaElement: any;
        formulaListItem: any;
        startMonth: any = 999912;
        roundingMethod: number;
        roundingPosition: number;

        OPEN_CURLY_BRACKET = '{';
        CLOSE_CURLY_BRACKET = '}';
        COMMA_CHAR = '、';
        HALF_SIZE_COMMA_CHAR = ',';
        PLUS = '＋';
        SUBTRACT = 'ー';
        MULTIPLICITY = '×';
        DIVIDE = '÷';
        POW = '^';
        OPEN_BRACKET = '(';
        CLOSE_BRACKET = ')';
        GREATER = '>';
        LESS = '<';
        LESS_OR_EQUAL = '≦';
        GREATER_OR_EQUAL = '≧';
        EQUAL = '＝';
        DIFFERENCE = '≠';
        HALF_SIZE_PLUS = '+';
        HALF_SIZE_SUBTRACT = '-';
        HALF_SIZE_LESS_OR_EQUAL = '≤';
        HALF_SIZE_GREATER_OR_EQUAL = '≥';
        HALF_SIZE_EQUAL = '=';
        PROGRAMING_MULTIPLICITY = '*';
        PROGRAMING_DIVIDE = '/';
        PROGRAMMING_DIFFERENCE = '#';

        PAYMENT = '支給';
        DEDUCTION = '控除';
        ATTENDANCE = '勤怠';
        COMPANY_UNIT_PRICE = '会社単価';
        FUNCTION = '関数';
        INDIVIDUAL_UNIT_PRICE = '個人単価';
        VARIABLE = '変数';
        PERSON = '個人';
        FORMULA = '計算式';
        WAGE_TABLE = '賃金';
        CONDITIONAL = '条件式';
        AND = 'かつ';
        OR = 'または';
        ROUND_OFF = '四捨五入';
        TRUNCATION = '切り捨て';
        ROUND_UP = '切り上げ';
        MAX_VALUE = '最大値';
        MIN_VALUE = '最小値';
        NUM_OF_FAMILY_MEMBER = '家族人数';
        YEAR_MONTH = '年月加算';
        YEAR_EXTRACTION = '年抽出';
        MONTH_EXTRACTION = '月抽出';
        SYSTEM_YMD_DATE = 'システム日付（年月日）';
        SYSTEM_YM_DATE = 'システム日付（年月）';
        SYSTEM_Y_DATE = 'システム日付（年）';
        PROCESSING_YEAR_MONTH = '処理年月';
        PROCESSING_YEAR = '処理年';
        REFERENCE_TIME = '基準時間';
        STANDARD_DAY = '基準日数';
        WORKDAY = '要勤務日数';

        separators = ['\\\＋', 'ー', '\\×', '÷', '\\^', '\\\(', '\\\)', '\\>', '\\<', '\\\≦', '\\\≧', '\\\＝', '\\\≠', '\\\,',
            '\\+', '\\-', '\\*', '\\/', '\\\≤', '\\\≥', '\\\=', '\\\#', '\\\、'
        ].join("|");

        operators = [this.OPEN_BRACKET, this.CLOSE_BRACKET, this.PLUS, this.HALF_SIZE_PLUS,
            this.SUBTRACT, this.HALF_SIZE_SUBTRACT, this.MULTIPLICITY, this.PROGRAMING_MULTIPLICITY,
            this.DIVIDE, this.PROGRAMING_DIVIDE, this.POW, this.LESS, this.GREATER,
            this.LESS_OR_EQUAL, this.GREATER_OR_EQUAL, this.EQUAL, this.DIFFERENCE,
            this.HALF_SIZE_LESS_OR_EQUAL, this.HALF_SIZE_GREATER_OR_EQUAL, this.HALF_SIZE_EQUAL,
            this.PROGRAMMING_DIFFERENCE, this.COMMA_CHAR, this.HALF_SIZE_COMMA_CHAR];

        processYearMonthAndReferenceTime: any;

        constructor() {
            block.invisible();
            let self = this;
            let params = getShared("QMM017_G_PARAMS");
            self.formulaElement = params.formulaElement;
            self.roundingMethod = params.roundingMethod;
            self.roundingPosition = params.roundingPosition;
            self.formulaListItem = params.formulaListItem;
            self.startMonth = params.startMonth;
            self.extractFormula(params.formula);

            if (/Chrome/.test(navigator.userAgent)) {
                $('#G1_2').ntsFixedTable({height: 279});
            } else {
                $('#G1_2').ntsFixedTable({height: 276.5});
            }

        }

        extractFormula(formula) {
            let self = this, separators = self.separators;
            let operands = formula.split(new RegExp(separators, 'g')).map(item => item.trim()).filter(item => {
                return (item && item.length);
            });
            let embeddedFormulaElement = {}, formulaName, formulaItem, registerContent;
            operands.forEach(operand => {
                if (operand.startsWith(self.FORMULA)) {
                    formulaName = operand.substring(operand.indexOf(self.OPEN_CURLY_BRACKET) + 1, operand.lastIndexOf(self.CLOSE_CURLY_BRACKET));
                    formulaItem = _.find(self.formulaListItem, {formulaName: formulaName});
                    if (!formulaItem) {
                        self.setErrorToFormula('MsgQ_248', [self.FORMULA, formulaName]);
                    }
                    registerContent = 'calc_00' + formulaItem.formulaCode;
                    embeddedFormulaElement[registerContent] = null;
                }
            });
            self.getEmbeddedFormulaAndDisplay(formula, embeddedFormulaElement);
        }

        extractInputParameter(formula) {
            let self = this, separators = self.separators;
            let operands = formula.split(new RegExp(separators, 'g')).map(item => item.trim()).filter(item => {
                return (item && item.length);
            });
            let calculationFormulaData = [];
            operands.forEach(operand => {
                if (operand.startsWith(self.PAYMENT) || operand.startsWith(self.DEDUCTION)) {
                    if (!(_.some(calculationFormulaData, {formulaItem: operand})))
                        calculationFormulaData.push({
                            formulaItem: operand,
                            trialCalculationValue: ko.observable(null),
                            constraint: 'PaymentAndDeductionItem',
                            decimalLength: 0,
                            readonly: false
                        });
                }
                if (operand.startsWith(self.ATTENDANCE)) {
                    if (!(_.some(calculationFormulaData, {formulaItem: operand})))
                        calculationFormulaData.push({
                            formulaItem: operand,
                            trialCalculationValue: ko.observable(null),
                            constraint: 'AttendanceItem',
                            decimalLength: 2,
                            readonly: false
                        });
                }
                if (operand.startsWith(self.COMPANY_UNIT_PRICE) || operand.startsWith(self.INDIVIDUAL_UNIT_PRICE)) {
                    if (!(_.some(calculationFormulaData, {formulaItem: operand})))
                        calculationFormulaData.push({
                            formulaItem: operand,
                            trialCalculationValue: ko.observable(null),
                            constraint: 'CompanyAndIndividualUnitPrice',
                            decimalLength: 2,
                            readonly: false
                        });
                }
                if (operand.startsWith(self.WAGE_TABLE)) {
                    if (!(_.some(calculationFormulaData, {formulaItem: operand})))
                        calculationFormulaData.push({
                            formulaItem: operand,
                            trialCalculationValue: ko.observable(1),
                            constraint: '',
                            decimalLength: 0,
                            readonly: true
                        });
                }
            });
            self.calculationFormulaList(calculationFormulaData);
        }

        getEmbeddedFormulaAndDisplay(formula, embeddedFormulaElement) {
            let self = this;
            let dto = {
                yearMonth: self.startMonth,
                formulaElements: embeddedFormulaElement
            };
            service.getEmbeddedFormulaDisplayContent(dto).done(function (data: any) {
                let formulaCode, formulaItem, displayContent;
                block.clear();
                Object.keys(data).forEach(key => {
                    formulaCode = key.substring(7);
                    formulaItem = _.find(self.formulaListItem, {formulaCode: formulaCode});
                    if (!formulaItem) {
                        self.setErrorToFormula('MsgQ_248', [self.FORMULA, formulaCode]);
                    }
                    displayContent = self.FORMULA + self.OPEN_CURLY_BRACKET + formulaItem.formulaName + self.CLOSE_CURLY_BRACKET;
                    formula = formula.replace(new RegExp(displayContent, 'g'), data[key]);
                });
                self.formulaContent(formula);
                self.extractInputParameter(formula);
                self.validateSyntax();
                $('#G1_2_container').focus();
            }).fail(function (err) {
                block.clear();
                dialog.alertError({messageId: err.messageId});
            })
        }

        validateSyntax() {
            let self = this;
            let formula = self.formulaContent();
            self.checkOperatorAndDivideZero(formula);
            self.checkBracket(formula);
            self.checkInputContent(formula);
        }

        checkOperatorAndDivideZero(formula) {
            let self = this, regex = new RegExp('([' + self.separators + '])');
            let formulaElements: any = formula.split(regex).filter(item => {
                return item && item.length
            });
            let self = this, currentChar, nextChar, operators = self.operators;
            if ((formulaElements[formulaElements.length - 1] && self.operators.indexOf(formulaElements[formulaElements.length - 1]) > 1) || self.operators.indexOf(formulaElements[0]) > 1) self.setErrorToFormula('MsgQ_235', []);
            for (index = 0; index < formulaElements.length; index++) {
                currentChar = formulaElements[index];
                if (operators.indexOf(currentChar) > -1) {
                    nextChar = formulaElements[index + 1];
                    if (operators.indexOf(nextChar) > -1 && (nextChar != self.OPEN_BRACKET && currentChar != self.CLOSE_BRACKET)
                        && !((currentChar == self.HALF_SIZE_COMMA_CHAR || currentChar == self.COMMA_CHAR) && (nextChar == self.SUBTRACT || nextChar == self.HALF_SIZE_SUBTRACT))) {
                        self.setErrorToFormula('MsgQ_232', [currentChar, nextChar]);
                    }
                    if (currentChar == self.DIVIDE && nextChar == 0) self.setErrorToFormula('MsgQ_234', []);
                }
            }
        }

        checkBracket(formula) {
            let self = this, index, openBracketNum = 0, closeBracketNum = 0, currentChar;
            for (index = 0; index < formula.length; index++) {
                currentChar = formula[index];
                if (currentChar == self.OPEN_BRACKET) openBracketNum++;
                if (currentChar == self.CLOSE_BRACKET) closeBracketNum++;
                if (openBracketNum - closeBracketNum > 10) {
                    self.setErrorToFormula('MsgQ_237', []);
                    return;
                }
                if (openBracketNum - closeBracketNum < 0) {
                    self.setErrorToFormula('MsgQ_231', []);
                    return;
                }
            }
            if (openBracketNum != closeBracketNum) {
                self.setErrorToFormula('MsgQ_231', []);
                return;
            }
        }

        checkInputContent(formula) {
            let self = this, operand, operands,
                separators: string = self.separators,
                formula = self.replaceTextInsideDoubleQuote(formula),
                /*Only Chrome support lookbehind assertion regex.
                formulaRegex = new RegExp(/(?<=}|$)/g);*/
                operands = formula.split(new RegExp(separators, 'g')).filter(item => item).map(item => item.trim());
            for (operand of operands) {
                let operandArray = operand.replace(new RegExp('}', 'g'), "}+").split('+').filter(item => item).map(item => item.trim());
                if (operandArray.length > 1) {
                    self.setErrorToFormula('MsgQ_256', []);
                    operandArray.forEach(operandItem => self.checkOperand(operandItem));
                } else {
                    self.checkOperand(operand);
                }
            }
        }

        checkOperand(operand) {
            let self = this;
            if (isNaN(operand)) {
                if (!operand.contains(self.OPEN_CURLY_BRACKET) || !operand.contains(self.CLOSE_CURLY_BRACKET)) {
                    self.setErrorToFormula('MsgQ_233', [operand]);
                } else {
                    let elementType = operand.substring(0, operand.indexOf(self.OPEN_CURLY_BRACKET));
                    let elementName = operand.substring(operand.indexOf(self.OPEN_CURLY_BRACKET) + 1, operand.indexOf(self.CLOSE_CURLY_BRACKET));

                    if (self.acceptPrefix.indexOf(elementType) < 0) {
                        self.setErrorToFormula('MsgQ_233', [elementType]);
                    }
                    if (!self.checkElementName(elementType, elementName)) {
                        self.setErrorToFormula('MsgQ_248', [elementType, elementName]);
                    }
                    if (elementType == self.FORMULA && self.checkNestedFormula(elementName)) self.setErrorToFormula('MsgQ_245', [elementName]);
                }
            } else {
                let dotIndex = operand.indexOf('.');
                if (dotIndex > -1 && operand.length - 1 - dotIndex > 5) self.setErrorToFormula('MsgQ_241', [operand]);
            }
        }


        replaceTextInsideDoubleQuote(formula) {
            let self = this, indexToCheck = formula.length, startOfLastFunctionIndex = -1, endOfLastFunctionIndex,
                singleFunctionContent;
            let textInsideDoubleQuoteRegex = /"((?:\\.|[^"\\])*)"/g;
            while (formula.substr(0, indexToCheck).lastIndexOf(self.FUNCTION) > -1) {
                startOfLastFunctionIndex = formula.substr(0, indexToCheck).lastIndexOf(self.FUNCTION);
                endOfLastFunctionIndex = self.indexOfEndFunction(startOfLastFunctionIndex, formula);
                singleFunctionContent = formula.substr(startOfLastFunctionIndex, endOfLastFunctionIndex - startOfLastFunctionIndex + 1);
                formula = formula.replace(singleFunctionContent, singleFunctionContent.replace(textInsideDoubleQuoteRegex, ""));
                indexToCheck = startOfLastFunctionIndex;
            }
            return formula;
        }

        indexOfEndFunction(startFunctionIndex, formula) {
            let self = this, index, openBracketNum = 0, closeBracketNum = 0, currentChar;
            for (index = startFunctionIndex; index < formula.length; index++) {
                currentChar = formula [index];
                if (currentChar == self.OPEN_BRACKET) openBracketNum++;
                if (currentChar == self.CLOSE_BRACKET) {
                    closeBracketNum++;
                    if (openBracketNum > 0 && openBracketNum == closeBracketNum) {
                        return index;
                    }
                }
            }
            return -1;
        }

        setErrorToFormula(messageId: string, messageParams: Array) {
            let isHasUniqueMessage = false;
            if (messageId == "MsgQ_231") {
                isHasUniqueMessage = _.some(nts.uk.ui.errors.getErrorList(), {errorCode: 'MsgQ_231'})
            }
            if (!isHasUniqueMessage) $('#G1_12').ntsError('set', {messageId: messageId, messageParams: messageParams});
        }


        // calculate via aspose cell
        calculateInServer() {
            $('.nts-input').trigger("validate");
            if (nts.uk.ui.errors.hasError()) return;
            let self = this;
            block.invisible();
            let replaceValues = ko.toJS(self.calculationFormulaList);
            service.calculate({
                formulaContent: self.formulaContent(),
                replaceValues: replaceValues,
                roundingMethod: self.roundingMethod,
                roundingPosition: self.roundingPosition
            }).done(function (result) {
                self.trialCalculationResult(result);
                block.clear();
            }).fail(function (err) {
                block.clear();
                dialog.alertError({messageId: err.messageId, message: err.message});
            })
        }

        //
        // calculate () {
        //     $('.nts-input').trigger("validate");
        //     if (nts.uk.ui.errors.hasError()) return;
        //     let self = this, calculationFormulaData = ko.toJS(self.calculationFormulaList), formulaContent = self.formulaContent();
        //     calculationFormulaData.forEach(item => {
        //         formulaContent = formulaContent.replace(new RegExp(item.formulaItem, 'g'), item.trialCalculationValue);
        //     });
        //     formulaContent = self.calculateSystemVariable(formulaContent);
        //     if (nts.uk.ui.errors.hasError()) {
        //         self.trialCalculationResult(null);
        //         return;
        //     }
        //     formulaContent = self.calculateFunction(formulaContent);
        //     if (nts.uk.ui.errors.hasError()) {
        //         self.trialCalculationResult(null);
        //         return;
        //     }
        //     if (formulaContent != null) {
        //         self.trialCalculationResult(self.calculateSuffixFormula(formulaContent));
        //     } else {
        //         // there are error here;
        //     }
        // }
        //
        // calculateSystemVariable (formulaElement) {
        //     let self = this, startFunctionIndex, endFunctionIndex, systemVariable, systemVariableResult;
        //     while (formulaElement.indexOf(self.VARIABLE) > -1) {
        //         startFunctionIndex = formulaElement.lastIndexOf(self.VARIABLE);
        //         endFunctionIndex = formulaElement.substring(startFunctionIndex).indexOf(self.CLOSE_CURLY_BRACKET) + 1;
        //         systemVariable = formulaElement.substring(startFunctionIndex, endFunctionIndex);
        //         systemVariableResult = self.getSystemValueBySystemVariable(systemVariable);
        //         if (systemVariableResult) {
        //             formulaElement = formulaElement.replace(systemVariable,  systemVariableResult);
        //         } else {
        //             // there are error here;
        //             return null;
        //         }
        //     }
        //     return formulaElement;
        // }
        //
        // getSystemValueBySystemVariable (systemVariable) {
        //     let self = this;
        //     let functionName = systemVariable.substring(systemVariable.indexOf(self.OPEN_CURLY_BRACKET) + 1, systemVariable.indexOf(self.CLOSE_CURLY_BRACKET));
        //     switch (functionName) {
        //         case self.SYSTEM_YMD_DATE: return moment().format("YYYY/MM/DD");
        //         case self.SYSTEM_YM_DATE: return moment().format("YYYY/MM");
        //         case self.SYSTEM_Y_DATE: return moment().format("YYYY");
        //         // Temporary can't decide value of following item
        //         case self.PROCESSING_YEAR: return moment(self.processYearMonthAndReferenceTime.processYearMonth).format("YYYY");
        //         case self.PROCESSING_YEAR_MONTH: return moment(self.processYearMonthAndReferenceTime.processYearMonth).format("YYYY/MM");
        //         case self.REFERENCE_TIME: return self.processYearMonthAndReferenceTime.referenceDate;
        //         default: return null;
        //     }
        // }
        //
        // calculateFunction (formulaElement) {
        //     let self = this, startFunctionIndex, endFunctionIndex, functionSyntax;
        //     let functionResult;
        //     while (formulaElement.indexOf(self.FUNCTION) > -1) {
        //         startFunctionIndex = formulaElement.lastIndexOf(self.FUNCTION);
        //         endFunctionIndex = self.indexOfEndElement(startFunctionIndex, formulaElement) + 1;
        //         functionSyntax = formulaElement.substring(startFunctionIndex, endFunctionIndex);
        //         functionResult = self.calculateSingleFunction(functionSyntax);
        //         if (functionResult) {
        //             formulaElement = formulaElement.replace(functionSyntax,  functionResult);
        //         } else {
        //             // must set error here
        //             return null;
        //         }
        //     }
        //     return formulaElement;
        // }
        // convertToPostfix (formulaContent) {
        //     let self = this, regex = new RegExp('([' + ['\\\＋', 'ー', '\\×', '÷', '\\^', '\\\(', '\\\)'].join('|') + '])');
        //     let formulaElements:any = formulaContent.split(regex).filter(item => {return item && item.length});
        //     let postfix = [], operators = [], operator, currentChar, closeBracket;
        //     for(let index = 0; index < formulaElements.length; index++){
        //         currentChar = formulaElements[index];
        //         if (!isNaN(currentChar)) {
        //             postfix.push(currentChar);
        //             continue;
        //         }
        //         if (currentChar == self.OPEN_BRACKET) {
        //             operators.push(self.OPEN_BRACKET);
        //             continue;
        //         }
        //         if (currentChar == self.CLOSE_BRACKET) {
        //             while (operators[operators.length-1] != self.OPEN_BRACKET) {
        //                 postfix.push(operators.pop());
        //             }
        //             operators.pop();
        //             continue;
        //         }
        //         while(self.getPriority(currentChar) <= self.getPriority(operators[operators.length-1]) && operators.length > 0){
        //             postfix.push(operators.pop());
        //         }
        //         operators.push(currentChar);
        //     }
        //     while (operators.length > 0){
        //         postfix.push(operators.pop());
        //     }
        //     return postfix;
        // }
        //
        // getPriority(operator): number{
        //     let self = this;
        //     if (operator == self.PLUS || operator == self.SUBTRACT)
        //         return 1 ;
        //     if (operator == self.MULTIPLICITY || operator == self.DIVIDE)
        //         return 2;
        //     if (operator == self.POW)
        //         return 3;
        //     return 0;
        // }
        //
        // calculateSuffixFormula (formulaContent) {
        //     let self = this,  currentChar, result, operands = [], operand1, operand2;
        //     let postFix = self.convertToPostfix(formulaContent);
        //     for(let index = 0; index < postFix.length; index++){
        //         currentChar = postFix[index];
        //         if (!isNaN(currentChar)) {
        //             operands.push(currentChar);
        //             continue;
        //         }
        //         operand1 = operands.pop();
        //         operand2 = operands.pop();
        //         operands.push(self.calculateSingleFormula(operand1, operand2, currentChar));
        //     }
        //     return operands.pop();
        // }
        //
        // calculateSingleFunction (functionSyntax) {
        //     let self = this;
        //     let functionName = functionSyntax.substring(functionSyntax.indexOf(self.OPEN_CURLY_BRACKET) + 1, functionSyntax.indexOf(self.CLOSE_CURLY_BRACKET)),
        //         functionParameter = functionSyntax.substring(functionSyntax.indexOf(self.OPEN_BRACKET) + 1, functionSyntax.lastIndexOf(self.CLOSE_BRACKET)).split(self.COMMA_CHAR).map(item => item.trim());
        //     if (functionName == self.CONDITIONAL)
        //         return self.calculateFunctionCondition(functionParameter);
        //     if (functionName == self.YEAR_MONTH)
        //         return moment(functionParameter[0]).add(functionParameter[1], 'M').format("YYYYMMDD");
        //     if (functionName == self.YEAR_EXTRACTION)
        //         return moment(functionParameter[0]).year();
        //     if (functionName == self.MONTH_EXTRACTION)
        //         return moment(functionParameter[0]).month() + 1;
        //     functionParameter = functionParameter.map(functionElement => self.calculateSuffixFormula(functionElement));
        //     if (functionName == self.ROUND_OFF)
        //        return Number(Math.round(functionParameter[0]));
        //     if (functionName == self.ROUND_UP)
        //         return Number(Math.ceil(functionParameter[0]));
        //     if (functionName == self.TRUNCATION)
        //         return Number(Math.floor(functionParameter[0]));
        //     if (functionName == self.MAX_VALUE)
        //         return (Math.max(...functionParameter));
        //     if (functionName == self.MIN_VALUE)
        //         return Number(Math.min(...functionParameter));
        //     if (functionName == self.NUM_OF_FAMILY_MEMBER)
        //         return 0;
        //     return null;
        // }
        // calculateFunctionCondition (functionParameter) {
        //     let self = this, conditionSeparators = ['\\>', '\\<', '\\\≦', '\\\≧', '\\\＝', '\\\≠'].join("|");
        //     let operand1, operand2, operator, result1 = functionParameter[1], result2 = functionParameter[2];
        //     let firstParameterData = functionParameter[0].split(new RegExp('([' +conditionSeparators + '])')).map(item => item.trim()).filter(item => {
        //         return (item && item.length);
        //     });
        //     operand1 = Number(self.calculateSuffixFormula(firstParameterData[0]));
        //     operand2 = Number(self.calculateSuffixFormula(firstParameterData[2]));
        //     operator = firstParameterData[1];
        //     switch (operator){
        //         case self.GREATER:
        //             return operand1 > operand2 ? result1 : result2;
        //         case self.LESS:
        //             return operand1 < operand2 ? result1 : result2;
        //         case self.GREATER_OR_EQUAL:
        //             return operand1 >= operand2 ? result1 : result2;
        //         case self.LESS_OR_EQUAL:
        //             return operand1 <= operand2 ? result1 : result2;
        //         case self.EQUAL:
        //             return operand1 == operand2 ? result1 : result2;
        //         case self.DIFFERENCE:
        //             return operand1 != operand2 ? result1 : result2;
        //         default :
        //             return 0;
        //     }
        // }
        // calculateSingleFormula (operand1, operand2, operator) {
        //     operand1 = Number(operand1);
        //     operand2 = Number(operand2);
        //     let self = this;
        //     switch (operator){
        //         case self.PLUS:
        //             return operand2 + operand1;
        //         case self.SUBTRACT:
        //             return operand2 - operand1;
        //         case self.MULTIPLICITY:
        //             return operand2 * operand1;
        //         case self.DIVIDE:
        //             return operand2 / operand1;
        //         case self.POW:
        //             return Math.pow(operand2, operand1);
        //         default :
        //             return operand1;;
        //     }
        // }
        // indexOfEndElement (startFunctionIndex, formula) {
        //     let self = this, index, openBracketNum = 0, closeBracketNum = 0, currentChar;
        //     for(index = startFunctionIndex; index < formula.length ; index ++ ) {
        //         currentChar = formula [index];
        //         if (currentChar == self.OPEN_BRACKET) openBracketNum ++;
        //         if (currentChar == self.CLOSE_BRACKET){
        //             closeBracketNum ++;
        //             if (openBracketNum == closeBracketNum){
        //                 return index;
        //             }
        //         }
        //     }
        //     return -1;
        // }
        //
        closeDialog() {
            nts.uk.ui.windows.close();
        }
    }
}


