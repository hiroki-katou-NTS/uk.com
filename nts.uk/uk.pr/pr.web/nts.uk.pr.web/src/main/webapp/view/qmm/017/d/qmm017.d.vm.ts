module nts.uk.pr.view.qmm017.d.viewmodel {
    import block = nts.uk.ui.block;
    import dialog = nts.uk.ui.dialog;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import model = nts.uk.pr.view.qmm017.share.model;
    import getText = nts.uk.resource.getText;
    export class ScreenModel {
        // tab 1
        lineItemCategoryItem: KnockoutObservableArray<model.EnumModel> = model.getLineItemCategoryItem();
        selectedCategoryValue: KnockoutObservable<number> = ko.observable(model.LINE_ITEM_CATEGORY.PAYMENT_ITEM);
        selectedCategory: KnockoutObservableArray<string> = ko.observable(null);
        paymentItemList: Array<any> = [];
        deductionItemList: Array<any> = [];
        attendanceItemList: Array<any> = [];
        // mix of 支給項目, 控除項目, 勤怠項目;
        statementItemList: KnockoutObservableArray<any> = ko.observableArray([]);
        selectedStatementItemCode: KnockoutObservable<string> = ko.observable(null);
        displayItemNote: KnockoutObservable<string> = ko.observable(null); // D2_10
        // tab 2
        unitPriceItemCategoryItem: KnockoutObservableArray<model.EnumModel> = model.getUnitPriceItemCategoryItem();
        selectedPriceItemCategoryValue: KnockoutObservable<number> = ko.observable(model.UNIT_PRICE_ITEM_CATEGORY.COMPANY_UNIT_PRICE_ITEM);
        selectedPriceItemCategory: KnockoutObservable<any> = ko.observable(null);
        companyUnitPriceList: Array<any> = [];
        individualUnitPriceList: Array<any> = [];
        // mix of 給与会社単価, 給与個人単価名称
        unitPriceItemList: KnockoutObservableArray<any> = ko.observableArray([]);
        selectedUnitPriceItemCode: KnockoutObservable<string> = ko.observable(null);
        selectedUnitPriceItem: KnockoutObservable<any> = ko.observable(null);
        displayUnitPriceItemNote: KnockoutObservable<string> = ko.observable(null); // D5_10
        // tab 3
        functionClassificationItem: KnockoutObservableArray<model.EnumModel> = model.getFunctionClassificationItem();
        selectedFunctionClassificationValue: KnockoutObservable<number> = ko.observable(model.FUNCTION_CLASSIFICATION.ALL);
        functionListItem: KnockoutObservableArray<model.EnumModel> = model.getFunctionListItem();
        selectedFunctionListValue: KnockoutObservable<number> = ko.observable(model.FUNCTION_LIST.CONDITIONAL_EXPRESSION);
        // TODO
        displayFunctionNote: KnockoutObservable<string> = ko.observable(null); // D6_10
        // tab 4
        systemVariableClassificationItem: KnockoutObservableArray<model.EnumModel> = model.getSystemVariableClassificationItem();
        selectedSystemVariableClassificationValue: KnockoutObservable<number> = ko.observable(model.SYSTEM_VARIABLE_CLASSIFICATION.ALL);
        systemVariableListItem: KnockoutObservableArray<model.EnumModel> = model.getSystemVariableListItem();
        selectedSystemVariableListValue: KnockoutObservable<number> = ko.observable(model.SYSTEM_VARIABLE_LIST.SYSTEM_YMD_DATE);
        // TODO
        displayVariableNote: KnockoutObservable<string> = ko.observable(null);
        // tab 5 - pending
        // tab 6
        formulaList: KnockoutObservableArray<model.IFormula>;
        selectedFormulaCode: KnockoutObservable<string> = ko.observable(null);
        selectedFormula: KnockoutObservable<model.Formula> = ko.observable(new model.Formula(null));
        // tab 7
        wageTableList: KnockoutObservableArray<model.IWageTable> = ko.observableArray([]);
        selectedWageTableCode: KnockoutObservable<string> = ko.observable(null);
        selectedWageTable: KnockoutObservable<model.WageTable> = ko.observable(new model.WageTable(null));
        // D3_5
        displayDetailCalculationFormula: KnockoutObservable<string> = ko.observable("");
        detailCalculationFormula: KnockoutObservableArray<any> = ko.observableArray([]);
        calculationFormulaDictionary:Array<any> = [];
        // formula
        OPEN_CURLY_BRACKET = '{'; CLOSE_CURLY_BRACKET = '}';
        COMMA_CHAR = ',';
        PLUS = '＋'; SUBTRACT = 'ー'; MULTIPLICY = '×'; DIVIDE = '÷'; POW = '^'; OPEN_BRACKET = '('; CLOSE_BRACKET = ')';
        GREATER = '>'; LESS = '<'; LESS_OR_EQUAL = '≦'; GREATER_OR_EQUAL = '≧'; EQUAL = '＝'; DIFFERENCE = '≠';
        operators: Array<any>
        separators: Array<any>;
        // for build formula
        acceptPrefix: Array<any>;
        acceptFunctionPostfix: Array<any>;
        acceptVariablePostfix: Array<any>;
        PAYMENT = getText('Enum_FormulaElementType_PAYMENT_ITEM');
        DEDUCTION = getText('Enum_FormulaElementType_DEDUCTION_ITEM');
        ATTENDANCE = getText('Enum_FormulaElementType_ATTENDANCE_ITEM');
        COMPANY_UNIT_PRICE = getText('Enum_FormulaElementType_COMPANY_UNIT_PRICE_ITEM');
        FUNCTION = getText('Enum_FormulaElementType_FUNCTION_ITEM');
        INDIVIDUAL_UNIT_PRICE = getText('Enum_FormulaElementType_INDIVIDUAL_UNIT_PRICE_ITEM');
        VARIABLE = getText('Enum_FormulaElementType_VARIABLE_ITEM');
        PERSON = getText('Enum_FormulaElementType_PERSON_ITEM');
        FORMULA = getText('Enum_FormulaElementType_FORMULA_ITEM');
        WAGE_TABLE = getText('Enum_FormulaElementType_WAGE_TABLE_ITEM');
        CONDITIONAL = getText('Enum_FunctionList_CONDITIONAL_EXPRESSION');
        AND = getText('Enum_FunctionList_AND');
        OR = getText('Enum_FunctionList_OR');
        ROUND_OFF = getText('Enum_FunctionList_ROUND_OFF');
        TRUNCATION = getText('Enum_FunctionList_TRUNCATION');
        ROUND_UP = getText('Enum_FunctionList_ROUND_UP');
        MAX_VALUE = getText('Enum_FunctionList_MAX_VALUE');
        MIN_VALUE = getText('Enum_FunctionList_MIN_VALUE');
        NUM_OF_FAMILY_MEMBER = getText('Enum_FunctionList_NUMBER_OF_FALIMY_MEMBER');
        YEAR_MONTH = getText('Enum_FunctionList_ADDITIONAL_YEARMONTH');
        YEAR_EXTRACTION = getText('Enum_FunctionList_YEAR_EXTRACTION');
        MONTH_EXTRACTION = getText('Enum_FunctionList_MONTH_EXTRACTION');
        SYSTEM_YMD_DATE = getText('Enum_SystemVariableList_SYSTEM_YMD_DATE');
        SYSTEM_YM_DATE = getText('Enum_SystemVariableList_SYSTEM_YM_DATE');
        SYSTEM_Y_DATE = getText('Enum_SystemVariableList_SYSTEM_Y_DATE');
        PROCESSING_YEAR_MONTH = getText('Enum_SystemVariableList_PROCESSING_YEAR_MONTH');
        PROCESSING_YEAR = getText('Enum_SystemVariableList_PROCESSING_YEAR');
        REFERENCE_TIME = getText('Enum_SystemVariableList_REFERENCE_TIME');
        STANDARD_DAY = getText('Enum_SystemVariableList_STANDARD_DAY');
        WORKDAY = getText('Enum_SystemVariableList_WORKDAY');
        autoComplete: KnockoutObservableArray<any> = ko.observableArray([

        ]);
        divValue: KnockoutObservable<any> = ko.observable('');
        autoSelected: KnockoutObservable<any> = ko.observable('');
        row: KnockoutObservable<number> = ko.observable(1);
        col: KnockoutObservable<number> = ko.observable(1);
        index: KnockoutObservable<number> = ko.observable(1);
        constructor() {
            var self = this;
            self.initTabPanel();
            self.changeDataByLineItemCategory();
            self.changeDataByUnitPriceItem();
            self.changeDataByFormula();
            self.changeDataByWageTable();
            self.initCalculationFormulaDictionary();
            self.initFormulaComponent();
        }
        getFormulaElements (yearMonth) {
            let self = this;
            if (yearMonth) {
                block.invisible();
                service.getFormulaElements(yearMonth).done(function (data) {
                    self.paymentItemList = data.paymentItem;
                    self.deductionItemList = data.deductionItem;
                    self.attendanceItemList = data.attendanceItem;
                    self.companyUnitPriceList = data.companyUnitPriceItem;
                    self.individualUnitPriceList = data.individualUnitPriceItem;
                    self.initWageTableData(data.wageTableItem);
                    self.selectedCategoryValue.valueHasMutated();
                    self.selectedPriceItemCategoryValue.valueHasMutated();
                    self.combineFormulaElement();
                    block.clear();
                }).fail(function (err) {
                    dialog.alertError(err.errorMessage);
                })
            }
        }
        initFormulaComponent () {
            let self = this;
            self.operators = [self.PLUS, self.SUBTRACT, self.MULTIPLICY, self.DIVIDE, self.POW, self.OPEN_BRACKET, self.CLOSE_BRACKET, self.LESS, self.GREATER, self.LESS_OR_EQUAL, self.GREATER_OR_EQUAL, self.EQUAL, self.DIFFERENCE];
            self.separators = ['\\\＋', 'ー', '\\×', '÷', '\\^', '\\\(', '\\\)', '\\>', '\\<', '\\\≦', '\\\≧', '\\\＝', '\\\≠', '\\\,'];
            self.acceptPrefix = [self.PAYMENT, self.DEDUCTION, self.ATTENDANCE, self.COMPANY_UNIT_PRICE, self.INDIVIDUAL_UNIT_PRICE, self.FUNCTION, self.VARIABLE, self.PERSON, self.FORMULA, self.WAGE_TABLE];
            self.acceptFunctionPostfix = [self.CONDITIONAL, self.AND, self.OR, self.ROUND_OFF, self.TRUNCATION, self.ROUND_UP, self.MAX_VALUE, self.MIN_VALUE, self.NUM_OF_FAMILY_MEMBER, self.YEAR_MONTH, self.YEAR_EXTRACTION, self.MONTH_EXTRACTION];
            self.acceptVariablePostfix = [self.SYSTEM_YM_DATE, self.SYSTEM_Y_DATE, self.SYSTEM_YMD_DATE, self.PROCESSING_YEAR_MONTH, self.PROCESSING_YEAR, self.REFERENCE_TIME, self.STANDARD_DAY, self.WORKDAY];
        }
        // tab 1
        changeDataByLineItemCategory () {
            let self = this;
            self.selectedCategoryValue.subscribe(newValue => {
                if (newValue != null) self.showListStatementItemData(newValue);
            });
            self.selectedStatementItemCode.subscribe(newValue => {
                if (newValue != null) self.showStatementItemData(self.selectedCategoryValue(), newValue);
            });
        }
        // tab 2
        changeDataByUnitPriceItem () {
            let self = this;
            self.selectedPriceItemCategoryValue.subscribe(newValue => {
                if (newValue != null) self.showListUnitPriceItem(newValue);
            })
            self.selectedUnitPriceItemCode.subscribe(newValue => {
                if (newValue != null) self.showUnitPriceItemData(self.selectedPriceItemCategoryValue(), newValue);
            })
        }

        changeDataByFormula () {
            let self = this;
            self.selectedFormulaCode.subscribe(newValue => {
                let currentFormulaList = ko.toJS(self.formulaList), selectedFormula = null;
                selectedFormula = _.find(currentFormulaList, {formulaCode: newValue});
                selectedFormula.formulaName = _.unescape(selectedFormula.formulaName);
                self.selectedFormula(new model.Formula(selectedFormula));
            })
        }

        initWageTableData (wageTableData) {
            let self = this;
            wageTableData.map(function(item){ item.name = _.escape(item.name); return item});
            if (wageTableData){
                self.wageTableList(wageTableData);
                if (wageTableData.length > 0) self.selectedWageTableCode(wageTableData[0].code);
            } else {
                self.wageTableList([]);
                self.selectedWageTableCode(null);
            }
        }

        changeDataByWageTable() {
            let self = this;
            self.selectedWageTableCode.subscribe(newValue => {
                let currentWageTableList = ko.toJS(self.wageTableList), selectedWageTable;
                selectedWageTable = _.find(currentWageTableList, {code: newValue});
                selectedWageTable.name = _.unescape(selectedWageTable.name);
                self.selectedWageTable(new model.WageTable(selectedWageTable));
            })
        }

        initTabPanel () {

        }
        // tab 1
        showListStatementItemData (categoryAtr) {
            let self = this;
            let statementItemList = self.paymentItemList;
            if (categoryAtr == model.LINE_ITEM_CATEGORY.DEDUCTION_ITEM) statementItemList = self.deductionItemList;
            if (categoryAtr == model.LINE_ITEM_CATEGORY.ATTENDANCE_ITEM) statementItemList = self.attendanceItemList;
            statementItemList.map(function(item){item.name = _.escape(item.name); return item;});
            self.statementItemList(statementItemList);
            if (statementItemList.length > 0) self.selectedStatementItemCode(statementItemList[0].code)
            else self.selectedStatementItemCode(null);
        }

        showStatementItemData (categoryAtr, itemNameCode) {
            let self = this, dfd = $.Deferred();
            block.invisible();
            // 廃止区分＝廃止しない (false)
            service.getStatementItemData(categoryAtr, itemNameCode).done(function(data) {
                self.displayItemNote(categoryAtr == model.LINE_ITEM_CATEGORY.PAYMENT_ITEM ? data.paymentItemSet ? data.paymentItemSet.note : "" : categoryAtr == model.LINE_ITEM_CATEGORY.DEDUCTION_ITEM ? data.deductionItemSet ? data.deductionItemSet.note: null : data.timeItemSet ? data.timeItemSet.note : null);
                block.clear();
                dfd.resolve();
            }).fail(function(err) {
                dfd.reject();
                block.clear();
                dialog.alertError(err.message);
            });
            return dfd.promise();
        }

        // tab 2
        showListUnitPriceItem (unitPriceItemCategory) {
            let self = this;
            let unitPriceList = self.companyUnitPriceList;
            if (unitPriceItemCategory == model.UNIT_PRICE_ITEM_CATEGORY.INDIVIDUAL_UNIT_PRICE_ITEM) unitPriceList = self.individualUnitPriceList;
            unitPriceList.map(function(item){item.name = _.escape(item.name); return item;})
            self.unitPriceItemList(unitPriceList);
            if (unitPriceList.length > 0) self.selectedUnitPriceItemCode(unitPriceList[0].code);
            else self.selectedUnitPriceItemCode(null);
        }

        showUnitPriceItemData (unitPriceItemCategory, code) {
            let self = this, dfd = $.Deferred();
            // block.invisible();
            service.getUnitPriceItemByCode(unitPriceItemCategory, code,__viewContext.screenModel.selectedHistory().startMonth()).done(function(data) {
                self.selectedUnitPriceItem(ko.mapping.fromJS(data));
                // TODO
                if (unitPriceItemCategory == model.UNIT_PRICE_ITEM_CATEGORY.INDIVIDUAL_UNIT_PRICE_ITEM)
                    self.displayUnitPriceItemNote(data ? data.salaryPerUnitPriceName ? data.salaryPerUnitPriceName.note : null : null);
                else {
                    self.displayUnitPriceItemNote(data ? data.notes : null);
                }
                block.clear();
                dfd.resolve();
            }).fail(function(err) {
                dfd.reject();
                block.clear();
                dialog.alertError(err.message);
            });
            return dfd.promise();
        }

        startPage(): JQueryPromise<any> {
            let self = this, dfd = $.Deferred();
            dfd.resolve();
            return dfd.promise();
        }

        // sheet 詳細計算式登録内容補足
        initCalculationFormulaDictionary () {
            let self = this;
            self.calculationFormulaDictionary.push({displayContent: self.PAYMENT, registerContent: '0000_0'});
            self.calculationFormulaDictionary.push({displayContent: self.DEDUCTION, registerContent: '0001_0'});
            self.calculationFormulaDictionary.push({displayContent: self.ATTENDANCE, registerContent: '0002_0'});
            self.calculationFormulaDictionary.push({displayContent: self.COMPANY_UNIT_PRICE, registerContent: 'U000_0'});
            self.calculationFormulaDictionary.push({displayContent: self.INDIVIDUAL_UNIT_PRICE, registerContent: 'U001_0'});
            self.calculationFormulaDictionary.push({displayContent: self.combineElementTypeAndName(self.FUNCTION, self.CONDITIONAL)    , registerContent: 'Func_00001'});
            self.calculationFormulaDictionary.push({displayContent: self.combineElementTypeAndName(self.FUNCTION, self.AND), registerContent: 'Func_00002'});
            self.calculationFormulaDictionary.push({displayContent: self.combineElementTypeAndName(self.FUNCTION, self.OR), registerContent: 'Func_00003'});
            self.calculationFormulaDictionary.push({displayContent: self.combineElementTypeAndName(self.FUNCTION, self.ROUND_OFF), registerContent: 'Func_00004'});
            self.calculationFormulaDictionary.push({displayContent: self.combineElementTypeAndName(self.FUNCTION, self.TRUNCATION), registerContent: 'Func_00005'});
            self.calculationFormulaDictionary.push({displayContent: self.combineElementTypeAndName(self.FUNCTION, self.ROUND_UP), registerContent: 'Func_00006'});
            self.calculationFormulaDictionary.push({displayContent: self.combineElementTypeAndName(self.FUNCTION, self.MAX_VALUE), registerContent: 'Func_00007'});
            self.calculationFormulaDictionary.push({displayContent: self.combineElementTypeAndName(self.FUNCTION, self.MIN_VALUE), registerContent: 'Func_00008'});
            self.calculationFormulaDictionary.push({displayContent: self.combineElementTypeAndName(self.FUNCTION, self.NUM_OF_FAMILY_MEMBER), registerContent: 'Func_00009'});
            self.calculationFormulaDictionary.push({displayContent: self.combineElementTypeAndName(self.FUNCTION, self.YEAR_MONTH), registerContent: 'Func_00010'});
            self.calculationFormulaDictionary.push({displayContent: self.combineElementTypeAndName(self.FUNCTION, self.YEAR_EXTRACTION), registerContent: 'Func_00011'});
            self.calculationFormulaDictionary.push({displayContent: self.combineElementTypeAndName(self.FUNCTION, self.MONTH_EXTRACTION), registerContent: 'Func_00012'});
            self.calculationFormulaDictionary.push({displayContent: self.combineElementTypeAndName(self.VARIABLE, self.SYSTEM_YMD_DATE), registerContent: 'vari_00001'});
            self.calculationFormulaDictionary.push({displayContent: self.combineElementTypeAndName(self.VARIABLE, self.SYSTEM_Y_DATE), registerContent: 'vari_00002'});
            self.calculationFormulaDictionary.push({displayContent: self.combineElementTypeAndName(self.VARIABLE, self.SYSTEM_YM_DATE), registerContent: 'vari_00003'});
            self.calculationFormulaDictionary.push({displayContent: self.combineElementTypeAndName(self.VARIABLE, self.PROCESSING_YEAR_MONTH), registerContent: 'vari_00004'});
            self.calculationFormulaDictionary.push({displayContent: self.combineElementTypeAndName(self.VARIABLE, self.PROCESSING_YEAR), registerContent: 'vari_00005'});
            self.calculationFormulaDictionary.push({displayContent: self.combineElementTypeAndName(self.VARIABLE, self.REFERENCE_TIME), registerContent: 'vari_00006'});
            self.calculationFormulaDictionary.push({displayContent: self.combineElementTypeAndName(self.VARIABLE, self.STANDARD_DAY), registerContent: 'vari_00007'});
            self.calculationFormulaDictionary.push({displayContent: self.combineElementTypeAndName(self.VARIABLE, self.WORKDAY), registerContent: 'vari_00008'});
            self.calculationFormulaDictionary.push({displayContent: self.FORMULA, registerContent: 'calc_00'});
            self.calculationFormulaDictionary.push({displayContent: self.WAGE_TABLE, registerContent: 'wage_00'});
        }

        addStatementItem () {
            let self = this, selectedCategory = self.selectedCategoryValue(), appendFormula = "",
                selectedStatementItemName = _.find(ko.toJS(this.statementItemList), {code: self.selectedStatementItemCode()}).name;
            if (selectedCategory == model.LINE_ITEM_CATEGORY.PAYMENT_ITEM) {
                appendFormula = self.PAYMENT;
            } else if (selectedCategory == model.LINE_ITEM_CATEGORY.DEDUCTION_ITEM) {
                appendFormula = self.DEDUCTION;
            } else {
                appendFormula = self.ATTENDANCE;
            }
            self.addToFormulaByPosition(self.combineElementTypeAndName(appendFormula, selectedStatementItemName));
        }

        addUnitPriceItem () {
            let self = this, selectedUnitPriceValue = self.selectedPriceItemCategoryValue(), appendFormula = "";
            let selectedUnitPrice:any = _.find(self.unitPriceItemList(), {code: self.selectedUnitPriceItemCode()});
            if (selectedUnitPriceValue == model.UNIT_PRICE_ITEM_CATEGORY.COMPANY_UNIT_PRICE_ITEM) {
                appendFormula = self.combineElementTypeAndName(self.COMPANY_UNIT_PRICE, (selectedUnitPrice ? selectedUnitPrice.name : ""));
            } else {
                appendFormula = self.combineElementTypeAndName(self.INDIVIDUAL_UNIT_PRICE, (selectedUnitPrice ? selectedUnitPrice.name : ""));
            }
            self.addToFormulaByPosition(appendFormula);
        }
        addFunctionItem () {
            let self = this, selectedFunctionItem:any = self.functionListItem()[self.selectedFunctionListValue()];
            self.addToFormulaByPosition(self.combineElementTypeAndName(self.FUNCTION, selectedFunctionItem.name));
        }
        addVariableItem () {
            let self = this, selectedSystemVariableItem:any = self.systemVariableListItem()[self.selectedSystemVariableListValue()];
            self.addToFormulaByPosition(self.combineElementTypeAndName(self.VARIABLE, selectedSystemVariableItem.name));
        }
        addFormulaItem () {
            let self = this;
            self.addToFormulaByPosition(self.combineElementTypeAndName(self.FORMULA, self.selectedFormula().formulaName()));
        }
        addWageTableItem () {
            let self = this;
            self.addToFormulaByPosition(self.combineElementTypeAndName(self.WAGE_TABLE, self.selectedWageTable().name()));
        }

        addToFormulaByPosition (formulaToAdd: string) {
            let self = this, calculationFormulaItem:any = $('#D3_5')[0];
            let startSelection = calculationFormulaItem.selectionStart, endSelection = calculationFormulaItem.selectionEnd;
            self.displayDetailCalculationFormula(calculationFormulaItem.value.substring(0, startSelection) + formulaToAdd + calculationFormulaItem.value.substring(startSelection));
            let newStartSelection = startSelection + formulaToAdd.length;
            $('#D3_5').focus();
            calculationFormulaItem.setSelectionRange(newStartSelection, newStartSelection);
        }
        startTrialCalculation () {
            let self = this;
            self.validateSyntax();
            if (nts.uk.ui.errors.hasError()) {
                return ;
            }
            setShared("QMM017_G_PARAMS", {formulaElement: self, formula: self.displayDetailCalculationFormula()});
            modal("/view/qmm/017/g/index.xhtml").onClosed(function () {

            });
        }

        addSymbol (symbol: string) {
            let self = this, targetItem:any = event.target;
            let symbol = targetItem.innerText;
            this.addToFormulaByPosition(symbol);
        }
        validateSyntaxOnClick () {
            let self = this;
            self.validateSyntax();

            if (!nts.uk.ui.errors.hasError()) {
                dialog.info("detail calculation formula is ok");
                self.extractFormulaElement();
            }
        }
        validateSyntax () {
            let self = this, formula = self.displayDetailCalculationFormula();
            $('#D3_5').ntsError('clear');
            if (formula.length == 0) {
                self.setErrorToFormula('MsgQ_236', []);
                return;
            }
            self.checkOperatorAndDivideZero(formula);
            self.checkResultIsNotNumber(formula);
            self.checkBracket (formula);
            self.checkInputContent(formula);
            self.checkFunctionSyntax(formula);
        }
        checkOperatorAndDivideZero (formula) {
            let self = this, regex = new RegExp('([' + self.separators.join('|') + '])');
            let formulaElements:any = formula.split(regex).filter(item => {return item && item.length}), elementType, elementName, detailFormula, calculationFormulaTransfer;
            let self = this, index = 0, currentChar, nextChar, nextElement, operators = self.operators;
            for(index = 0 ; index < formulaElements.length; index ++){
                currentChar = formulaElements[index];
                if (operators.indexOf(currentChar)>-1){
                    nextChar = formulaElements[index+1];
                    if (operators.indexOf(nextChar)>-1 && nextChar != self.OPEN_BRACKET && currentChar != self.CLOSE_BRACKET) {
                        self.setErrorToFormula('MsgQ_232', [currentChar, nextChar]);
                    }
                    if (currentChar == self.DIVIDE && nextChar == 0) self.setErrorToFormula('MsgQ_234', []);
                }
            }
        }
        checkResultIsNotNumber (formula) {
            let self = this;
            if (formula.startsWith(self.VARIABLE) || formula.startsWith(self.combineElementTypeAndName(self.FUNCTION, self.AND))
            || formula.startsWith(self.combineElementTypeAndName(self.FUNCTION, self.OR)) || formula.startsWith(self.combineElementTypeAndName(self.FUNCTION, self.YEAR_MONTH))
            || formula.startsWith(self.combineElementTypeAndName(self.FUNCTION, self.YEAR_EXTRACTION)) || formula.startsWith(self.combineElementTypeAndName(self.FUNCTION, self.MONTH_EXTRACTION))) self.setErrorToFormula('MsgQ_235', []);
        }
        checkBracket (formula) {
            let self = this, index, openBracketNum = 0, closeBracketNum = 0, currentChar;
            for(index = 0; index < formula.length ; index ++ ) {
                currentChar = formula [index];
                if (currentChar == self.OPEN_BRACKET) openBracketNum ++;
                if (currentChar == self.CLOSE_BRACKET) closeBracketNum ++;
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
            }
            return;
        }
        replaceTextInsideDoubleQuote (formula) {
            let self = this, indexToCheck = formula.length, startOfLastFunctionIndex = -1, endOfLastFunctionIndex,
                singleFunctionContent;
            let textInsideDoubleQuoteRegex = /"((?:\\.|[^"\\])*)"/g;
            while (formula.substr(0, indexToCheck).lastIndexOf(self.FUNCTION) > -1){
                startOfLastFunctionIndex = formula.substr(0, indexToCheck).lastIndexOf(self.FUNCTION);
                endOfLastFunctionIndex = self.indexOfEndFunction(startOfLastFunctionIndex, formula);
                singleFunctionContent = formula.substr(startOfLastFunctionIndex, endOfLastFunctionIndex -startOfLastFunctionIndex + 1);
                formula = formula.replace(singleFunctionContent, singleFunctionContent.replace(textInsideDoubleQuoteRegex, 0));
                indexToCheck = startOfLastFunctionIndex;
            }
            return formula;
        }
        checkInputContent (formula) {
            let self = this, operand, prevOperand, operands, dotIndex,
                separators: string = self.separators.join('|'),
            formula = this.replaceTextInsideDoubleQuote(formula);
            operands = formula.split(new RegExp(separators, 'g')).map(item => item.trim()).filter(item => {
                return (item && item.length);
            });
            for(operand of operands) {
                if (isNaN(operand)) {
                    if (! (operand.indexOf(self.OPEN_CURLY_BRACKET) > -1)) {
                        self.setErrorToFormula('MsgQ_233', [operand]);
                        continue;
                    }
                    let elementType = operand.substring(0, operand.indexOf(self.OPEN_CURLY_BRACKET)), elementName = operand.substring(operand.indexOf(self.OPEN_CURLY_BRACKET) + 1, operand.lastIndexOf(self.CLOSE_CURLY_BRACKET));
                    if (self.acceptPrefix.indexOf(elementType) < 0) self.setErrorToFormula('MsgQ_233', [operand]);
                    if (!self.checkElementName(elementType, elementName)) self.setErrorToFormula('MsgQ_233', [operand]);
                } else {
                    dotIndex = operand.indexOf('.');
                    if (dotIndex > - 1 && operand.length - 1 - dotIndex > 5) self.setErrorToFormula('MsgQ_241', [operand]);
                }
                prevOperand = operand;
            }
        }
        checkElementName (elementType: string, elementName: string): boolean {
            let self = this;
            if (elementType == self.PAYMENT)
                return self.paymentItemList.some(item => {return item.name == elementName});
            if (elementType == self.DEDUCTION)
                return self.deductionItemList.some(item => {return item.name == elementName});
            if (elementType == self.ATTENDANCE)
                return self.attendanceItemList.some(item => {return item.name == elementName});
            if (elementType == self.COMPANY_UNIT_PRICE)
                return self.companyUnitPriceList.some(item => {return item.name == elementName});
            if (elementType == self.INDIVIDUAL_UNIT_PRICE)
                return self.individualUnitPriceList.some(item => {return item.name == elementName});
            if (elementType == self.FORMULA)
                return (ko.toJS(self.formulaList).some(item => {return item.formulaName == elementName}));
            if (elementType == self.WAGE_TABLE )
                return (ko.toJS(self.wageTableList).some(item => {return item.name == elementName}));
            if (elementType == self.FUNCTION )
                return (self.acceptFunctionPostfix.indexOf(elementName) > -1);
            if (elementType == self.VARIABLE )
                return (self.acceptVariablePostfix.indexOf(elementName) > -1);
            return true;

        }
        checkFunctionSyntax (formula) {
            let self = this, startFunctionIndex, endFunctionIndex, functionsSyntax = [], index;
            while (formula.indexOf(self.FUNCTION) > -1) {
                startFunctionIndex = formula.lastIndexOf(self.FUNCTION);
                endFunctionIndex = self.indexOfEndFunction(startFunctionIndex, formula);
                if (endFunctionIndex == -1){
                    self.setErrorToFormula('MsgQ_233', [formula.substring(startFunctionIndex, formula.substring(startFunctionIndex).indexOf(self.OPEN_BRACKET))]);
                    break;
                }
                self.checkSingleFunctionSyntax(formula.substring(startFunctionIndex, endFunctionIndex + 1));
                formula = formula.replace(formula.substring(startFunctionIndex, endFunctionIndex + 1),  0 );
            }
        }
        checkSingleFunctionSyntax (functionSyntax) {
            let self = this, conditionRegex = new RegExp(['\\\<', '>', '\\\≧', '\\\≦', '\\\＝', '\\\≠'].join('|'));
            let functionName = functionSyntax.substring(functionSyntax.indexOf(self.OPEN_CURLY_BRACKET) + 1, functionSyntax.indexOf(self.CLOSE_CURLY_BRACKET)),
                functionParameter = functionSyntax.substring(functionSyntax.indexOf(self.OPEN_BRACKET) + 1, functionSyntax.lastIndexOf(self.CLOSE_BRACKET)).split(self.COMMA_CHAR).filter(item => item.length).map(item => item.trim());
            if (functionParameter.length == 0) {
                self.setErrorToFormula('MsgQ_238', [functionName]);
                return;
            }
            if (functionName == self.CONDITIONAL){
                if (functionParameter.length != 3) {
                    if (functionParameter.length < 3) self.setErrorToFormula('MsgQ_238', [functionName]);
                    else self.setErrorToFormula('MsgQ_239', [functionName]);
                } else if (functionParameter[0].split(conditionRegex).length > 2) {
                    // should have a different message, temporary use msg 16
                    // condition is invalid
                    self.setErrorToFormula('MsgQ_239', [functionName]);
                }
            }
            if (functionName == self.ROUND_OFF || functionName == self.ROUND_UP || functionName == self.TRUNCATION) {
                if (functionParameter.length > 1) self.setErrorToFormula('MsgQ_239', [functionName]);
                if (!self.checkNumberDataType(functionParameter)){
                    self.setErrorToFormula('MsgQ_240', [functionName]);
                }
            }
            if (functionName == self.YEAR_MONTH) {
                if (functionParameter.length != 2) {
                    if (functionParameter.length < 2) self.setErrorToFormula('MsgQ_238', [functionName]);
                    else self.setErrorToFormula('MsgQ_239', [functionName]);
                } else if (!self.checkDateDataType(functionParameter[0])){
                    self.setErrorToFormula('MsgQ_240', [functionName]);
                }
            }
            if (functionName == self.YEAR_EXTRACTION || functionName == self.MONTH_EXTRACTION) {
                if (functionParameter.length > 1) {
                    self.setErrorToFormula('MsgQ_239', [functionName]);
                } else if (!self.checkDateDataType(functionParameter[0])){
                    self.setErrorToFormula('MsgQ_240', [functionName]);
                }
            }
        }
        indexOfEndFunction (startFunctionIndex, formula) {
            let self = this, index, openBracketNum = 0, closeBracketNum = 0, currentChar;
            for(index = startFunctionIndex; index < formula.length ; index ++ ) {
                currentChar = formula [index];
                if (currentChar == self.OPEN_BRACKET) openBracketNum ++;
                if (currentChar == self.CLOSE_BRACKET){
                    closeBracketNum ++;
                    if (openBracketNum > 0 && openBracketNum == closeBracketNum){
                        return index;
                    }
                }
            }
            return -1;
        }
        checkNumberDataType (functionParameters) {
            let self = this;
            for(var functionParameter of functionParameters ) {
                if (functionParameter.indexOf('"') > -1 || functionParameter.startWiths(self.VARIABLE)) return false;
            }
            return true;
        }
        checkDateDataType (functionParameter) {
            let self = this;
            if (!isNaN(functionParameter)) return false;
            if (functionParameter.indexOf('"') > -1 && ! moment(functionParameter.split('"')[1], "YYYY/MM/DD", true).isValid()) return false;
            if (functionParameter.startsWith(self.FUNCTION) && !functionParameter.startsWith(self.FUNCTION + self.OPEN_CURLY_BRACKET + self.CONDITIONAL)) return false;
            return true;
        }
        setErrorToFormula (messageId: string, messageParams: Array) {
            $('#D3_5').ntsError('set', {messageId: messageId, messageParams: messageParams});
        }

        extractFormulaElement () {
            let self = this, regex = new RegExp('([' + self.separators.join('|') + '])');
            let formula = self.displayDetailCalculationFormula();
            let formulaElements:any = formula.split(regex).filter(item => {return item && item.length}), elementType, elementName, detailFormula, calculationFormulaTransfer;
            for(let index = 0; index < formulaElements.length; index++ ) {
                detailFormula = {formulaElement: formulaElements[index].trim(), elementOrder: index};
                detailFormula.formulaElement = self.convertToRegisterContent(detailFormula.formulaElement);
                formulaElements[index] = detailFormula;
            }
            return formulaElements;
        }
        convertToRegisterContent (formulaElement) {
            let self = this, elementName = formulaElement.substring(formulaElement.indexOf(self.OPEN_CURLY_BRACKET) + 1, formulaElement.indexOf(self.CLOSE_CURLY_BRACKET));
            if (!elementName) return formulaElement;
            let calculationFormulaTransfer;
            if (formulaElement.startsWith(self.FUNCTION) || formulaElement.startsWith(self.VARIABLE)){
                calculationFormulaTransfer = self.calculationFormulaDictionary.filter(item => {return item.displayContent == formulaElement}) [0];
                return calculationFormulaTransfer.registerContent;
            }
            calculationFormulaTransfer = self.calculationFormulaDictionary.filter(item => {return item.displayContent.startsWith(formulaElement.substring(0, formulaElement.indexOf(self.OPEN_CURLY_BRACKET)))})[0];
            if (formulaElement.startsWith(self.PAYMENT))
                return calculationFormulaTransfer.registerContent + _.find(self.paymentItemList, {name: elementName}).code;
            if (formulaElement.startsWith(self.DEDUCTION))
                return calculationFormulaTransfer.registerContent + _.find(self.deductionItemList, {name: elementName}).code;
            if (formulaElement.startsWith(self.ATTENDANCE))
                return calculationFormulaTransfer.registerContent + _.find(self.attendanceItemList, {name: elementName}).code;
            if (formulaElement.startsWith(self.COMPANY_UNIT_PRICE))
                return calculationFormulaTransfer.registerContent + _.find(self.companyUnitPriceList, {name: elementName}).code;
            if (formulaElement.startsWith(self.INDIVIDUAL_UNIT_PRICE))
                return calculationFormulaTransfer.registerContent + _.find(self.individualUnitPriceList, {name: elementName}).code;
            if (formulaElement.startsWith(self.FORMULA))
                return calculationFormulaTransfer.registerContent + _.find(ko.toJS(self.formulaList), {name: elementName}).code;
            if (formulaElement.startsWith(self.WAGE_TABLE))
                return calculationFormulaTransfer.registerContent + _.find(ko.toJS(self.wageTableList), {name: elementName}).code;

        }
        combineFormulaElement () {
            let self = this, displayFormula = "", formulaElements = __viewContext.screenModel.detailFormulaSetting().detailCalculationFormula(), formulaElement;
            for(let index = 0; index < formulaElements.length; index++ ) {
                formulaElement = formulaElements[index].formulaElement;
                displayFormula += self.convertToDisplayContent(formulaElement);
            }
            self.displayDetailCalculationFormula(displayFormula);
        }
        convertToDisplayContent (formulaElement) {
            let self = this, elementType = formulaElement.substring(0, 6);
            let elementCode = formulaElement.substring(6, formulaElement.length);
            if (!elementCode) return formulaElement;
            let calculationFormulaTransfer
            if (elementType.startsWith("Func") || elementType.startsWith("vari")){
                calculationFormulaTransfer = self.calculationFormulaDictionary.filter(item => {return item.registerContent == formulaElement}) [0];
                return calculationFormulaTransfer.displayContent;
            }
            calculationFormulaTransfer = self.calculationFormulaDictionary.filter(item => {return item.registerContent.startsWith(elementType)}) [0];
            if (elementType.startsWith("0000_0"))
                return self.combineElementTypeAndName(calculationFormulaTransfer.displayContent, _.find(self.paymentItemList, {code: elementCode}).name);
            if (elementType.startsWith("0001_0"))
                return self.combineElementTypeAndName(calculationFormulaTransfer.displayContent, _.find(self.deductionItemList, {code: elementCode}).name);
            if (elementType.startsWith("0002_0"))
                return self.combineElementTypeAndName(calculationFormulaTransfer.displayContent, _.find(self.attendanceItemList, {code: elementCode}).name);
            if (elementType.startsWith("U000_0"))
                return self.combineElementTypeAndName(calculationFormulaTransfer.displayContent, _.find(self.companyUnitPriceList, {code: elementCode}).name);
            if (elementType.startsWith("U001_0"))
                return self.combineElementTypeAndName(calculationFormulaTransfer.displayContent, _.find(self.individualUnitPriceList, {code: elementCode}).name);
            if (elementType.startsWith("calc")) {
                elementCode = formulaElement.substring(7, formulaElement.length);
                return self.combineElementTypeAndName(calculationFormulaTransfer.displayContent, _.find(ko.toJS(self.formulaList), {code: elementCode}).name);
            }
            if (elementType.startsWith("wage")) {
                elementCode = formulaElement.substring(7, formulaElement.length);
                return self.combineElementTypeAndName(calculationFormulaTransfer.displayContent,  _.find(ko.toJS(self.wageTableList), {code: elementCode}).name);
            }
        }
        combineElementTypeAndName (elementType, elementName) {
            let self = this;
            return elementType + self.OPEN_CURLY_BRACKET + elementName + self.CLOSE_CURLY_BRACKET;
        }
        showHintBox () {
            let self = this;
            $("#auto-complete-container").on('click', function(){
                let displayText = $(this).children("option:selected").text();
                if (displayText.indexOf("    ") > -1 ) displayText = displayText.split("    ")[1];
                if (displayText) self.addToFormulaFromHintBox(displayText + self.CLOSE_CURLY_BRACKET);
                $("#auto-complete-container").hide();
            });
            $("#D3_5").on("keypress", function(event){
                if (event.keyCode == 13){
                    event.preventDefault();
                    let displayText = $('#auto-complete-container option:selected').text();
                    if (displayText.indexOf("    ") > -1 ) displayText = displayText.split("    ")[1];
                    $("#auto-complete-container").hide();
                    if (displayText)
                        setTimeout(function(){
                            self.addToFormulaFromHintBox(displayText + self.CLOSE_CURLY_BRACKET);
                        }, 300)
                }
            });
            $("#D3_5").on("keydown", function(event){
                if ((event.keyCode == 38 || event.keyCode == 40) && $("#auto-complete-container").is(":visible")){
                    if (event.keyCode == 40) $('#auto-complete-container option:selected').next().prop('selected', true);
                    if (event.keyCode == 38) $('#auto-complete-container option:selected').prev().prop('selected', true);
                    return event.preventDefault();
                }
            });

            // show hint box if selection change ?
            $("#D3_5").on('keyup',(event) => {
                let self = this, target = event.target, start = target.selectionStart, end = target.selectionEnd,
                    keyCode = event.keyCode, currentRow, autoCompleteData = [];
                if (keyCode != 38 && keyCode != 40 && keyCode !=13) {
                    self.genVirualElement();
                    currentRow = this.getCurrentPosition(end);
                    self.index(end);
                    $("#auto-complete-container").css({
                        "top": (currentRow.top + 17) + "px",
                        "left": (currentRow.left + 15) + "px"
                    });
                    let indexOfNearestOpenCurlyBracket = target.value.substring(0, start).lastIndexOf(self.OPEN_CURLY_BRACKET);
                    autoCompleteData = self.getAutoCompleteDataByElementType(target.value.substring(0, indexOfNearestOpenCurlyBracket), target.value.substring(indexOfNearestOpenCurlyBracket + 1, start));
                    if (autoCompleteData.length > 0){
                        self.autoComplete(autoCompleteData);
                        $("#auto-complete-container").show();
                        self.autoSelected(autoCompleteData[0]);
                    } else {
                        $("#auto-complete-container").hide();
                    }
                }
            });
            $('body').on('click', function(event){
                if (event.target.id != "D3_5") $("#auto-complete-container").hide();
            })
        }
        insertString(original, sub, position) {
            if (original.length === position) {
                return original + sub;
            }
            return original.substr(0, position) + sub + original.substr(position);
        }

        genVirualElement() {
            var value = $("#D3_5").val();
            var count = 1;
            var toChar = value.split('');
            var html = "<span class='editor-line'>";
            for (var i = 0; i < toChar.length; i++) {
                if (toChar[i] === "\n") {
                    html += "</span>";
                    html += "<span class='editor-line'>";
                } else {
                    if (toChar[i] === "@") {
                        html += "<span id='span-" + count + "' class='autocomplete-char'>" + toChar[i] + "</span>";
                        count++;
                    } else if (this.checkJapanese(toChar[i])) {
                        if (toChar[i - 1] === undefined || toChar[i - 1] === "\n") {
                            html += "<span id='span-" + count + "' class='japanese-character'>" + toChar[i] + "</span>";
                            count++;
                        } else if (this.checkJapanese(toChar[i - 1])) {
                            html = this.insertString(html, toChar[i], html.length - 7);
                        } else {
                            html += "<span id='span-" + count + "' class='japanese-character'>" + toChar[i] + "</span>";
                            count++;
                        }
                    } else if (this.checkAlphaOrEmpty(toChar[i])) {
                        if (toChar[i - 1] === undefined || toChar[i - 1] === "\n") {
                            html += "<span id='span-" + count + "'>" + toChar[i] + "</span>";
                            count++;
                        } else if (this.checkAlphaOrEmpty(toChar[i - 1]) && toChar[i - 1] !== "@") {
                            html = this.insertString(html, toChar[i], html.length - 7);
                        } else {
                            html += "<span id='span-" + count + "'>" + toChar[i] + "</span>";
                            count++;
                        }
                    } else {
                        html += "<span id='span-" + count + "' class='special-char'>" + toChar[i] + "</span>";
                        count++;
                    }
                }
            }
            html += "</span>";
            this.divValue(html);
            this.divValue($("#input-content-area").html());
        }

        getCurrentPosition(position) {
            var uiPosition = {};
            var $lines = $("#input-content-area").find(".editor-line");
            var index = 0;
            $lines.each(function(index, line) {
                var $line = $(line);
                var char = _.find($line.children(), function(text) {
                    var current = index + $(text).text().length;
                    index += $(text).text().length;
                    return current === position;
                });
                if(char !== undefined){
                    uiPosition = $(char).position();
                    return;
                }
            });
            return uiPosition;
        }

        checkAlphaOrEmpty(char) {
            var speChar = new RegExp(/[~`!#$%\^&*+=\-\[\]\\;\',/{}|\\\":<>\?\(\)]/g);
            return !speChar.test(char) || char === " " || char === undefined;
        }

        checkJapanese(char) {
            return !nts.uk.text.allHalf(char);
        }
        changeAutoCompleteData (preString, postString) {

        }
        getAutoCompleteDataByElementType(preString, postString): any{
            let self = this;
            if (preString == null || postString == null) return [];
            if (preString.endsWith(self.PAYMENT))
               return self.paymentItemList.filter(function(item){return item.name.startsWith(postString) || item.code.startsWith(postString)}).map(item => new model.ItemModel(item.code, item.name));
            if (preString.endsWith(self.DEDUCTION))
                return self.deductionItemList.filter(function(item){return item.name.startsWith(postString) || item.code.startsWith(postString)}).map(item => new model.ItemModel(item.code, item.name));
            if (preString.endsWith(self.ATTENDANCE))
                return self.attendanceItemList.filter(function(item){return item.name.startsWith(postString) || item.code.startsWith(postString)}).map(item => new model.ItemModel(item.code, item.name));
            if (preString.endsWith(self.COMPANY_UNIT_PRICE))
                return self.companyUnitPriceList.filter(function(item){return item.name.startsWith(postString) || item.code.startsWith(postString)}).map(item => new model.ItemModel(item.code, item.name));
            if (preString.endsWith(self.INDIVIDUAL_UNIT_PRICE))
                return self.individualUnitPriceList.filter(function(item){return item.name.startsWith(postString) || item.code.startsWith(postString)}).map(item => new model.ItemModel(item.code, item.name));
            if (preString.endsWith(self.FUNCTION))
                return ko.toJS(self.functionListItem).filter(function(item){return item.name.startsWith(postString) || (item.code && item.code.startsWith(postString))}).map(item => new model.ItemModel(null, item.name));
            if (preString.endsWith(self.VARIABLE))
                return ko.toJS(self.systemVariableListItem).filter(function(item){return item.name.startsWith(postString) || (item.code && item.code.startsWith(postString))}).map(item => new model.ItemModel(null, item.name));
            if (preString.endsWith(self.FORMULA))
                return ko.toJS(self.formulaList).filter(function(item){return item.formulaName.startsWith(postString) || item.formulaCode.startsWith(postString)}).map(item => new model.ItemModel(item.formulaCode, item.formulaName));
            if (preString.endsWith(self.WAGE_TABLE))
                return ko.toJS(self.wageTableList).filter(function(item){return item.name.startsWith(postString) || item.code.startsWith(postString)}).map(item => new model.ItemModel(item.code, item.name));
            return [];
        }
        addToFormulaFromHintBox (formulaToAdd: string) {
            let self = this, calculationFormulaItem:any = $('#D3_5')[0];
            let startSelection = calculationFormulaItem.value.substring(0, calculationFormulaItem.selectionStart).lastIndexOf(self.OPEN_CURLY_BRACKET);
            self.displayDetailCalculationFormula(calculationFormulaItem.value.substring(0, startSelection + 1) + formulaToAdd + calculationFormulaItem.value.substring(calculationFormulaItem.selectionStart));
            let newStartSelection = startSelection + formulaToAdd.length +1;
            $('#D3_5').focus();
            calculationFormulaItem.setSelectionRange(newStartSelection, newStartSelection);
        }
    }
}