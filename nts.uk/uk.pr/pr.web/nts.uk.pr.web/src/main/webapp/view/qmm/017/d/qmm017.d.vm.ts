module nts.uk.pr.view.qmm017.d.viewmodel {
    import block = nts.uk.ui.block;
    import dialog = nts.uk.ui.dialog;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import model = nts.uk.pr.view.qmm017.share.model
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
        PAYMENT = '支給'; DEDUCTION = '控除'; ATTENDANCE = '勤怠'; COMPANY_UNIT_PRICE = '会社単価'; FUNCTION = '関数';
        INDIVIDUAL_UNIT_PRICE = '個人単価';VARIABLE = '変数'; PERSON = '個人'; FORMULA = '計算式'; WAGE_TABLE = '賃金';
        CONDITIONAL = '条件式'; AND = 'かつ'; OR = 'または'; ROUND_OFF = '四捨五入'; TRUNCATION = '切り捨て';
        ROUND_UP = '切り上げ'; MAX_VALUE = '最大値'; MIN_VALUE = '最小値'; NUM_OF_FAMILY_MEMBER = '家族人数';
        YEAR_MONTH = '年月加算'; YEAR_EXTRACTION = '年抽出'; MONTH_EXTRACTION = '月抽出';
        SYSTEM_YMD_DATE = 'システム日付（年月日）'; SYSTEM_YM_DATE = 'システム日付（年月）'; SYSTEM_Y_DATE = 'システム日付（年）'; PROCESSING_YEAR_MONTH = '処理年月';
        PROCESSING_YEAR = '処理年'; REFERENCE_TIME = '基準時間'; STANDARD_DAY = '基準日数'; WORKDAY = '要勤務日数';

        autoComplete = ko.observableArray([
            new model.ItemModel('001', '基本給'),
            new model.ItemModel('150', '役職手当'),
            new model.ItemModel('ABC', '基12本ghj給'),
            new model.ItemModel('002', '基本給'),
            new model.ItemModel('153', '役職手当'),
            new model.ItemModel('AB4', '基12本ghj給'),
            new model.ItemModel('003', '基本給'),
            new model.ItemModel('155', '役職手当'),
            new model.ItemModel('AB5', '基12本ghj給')
        ]);

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
            self.displayDetailCalculationFormula(self.displayDetailCalculationFormula().substring(0, startSelection) + formulaToAdd + self.displayDetailCalculationFormula().substring(startSelection));
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
                self.setErrorToFormula('MsgQ_13', []);
                return;
            }
            self.checkDivideZero(formula);
            self.checkBracket (formula);
            self.checkOperator (formula);
            self.checkInputContent(formula);
            self.checkFunctionSyntax(formula);
        }
        checkDivideZero (formula) {
            let self = this;
            if (formula.indexOf("÷0") > -1) self.setErrorToFormula('MsgQ_11', []);
        }
        checkBracket (formula) {
            let self = this, index, openBracketNum = 0, closeBracketNum = 0, currentChar;
            for(index = 0; index < formula.length ; index ++ ) {
                currentChar = formula [index];
                if (currentChar == self.OPEN_BRACKET) openBracketNum ++;
                if (currentChar == self.CLOSE_BRACKET) closeBracketNum ++;
                if (openBracketNum - closeBracketNum < 0) {
                    self.setErrorToFormula('MsgQ_8', []);
                    return;
                }
            }
            if (openBracketNum != closeBracketNum) {
                self.setErrorToFormula('MsgQ_8', []);
            }
            return;
        }
        checkOperator(formula) {
            let self = this, index = 0, currentChar, nextChar, nextElement, operators = self.operators;
                for(index = 0 ; index < formula.length; index ++){
                currentChar = formula[index];
                if (operators.indexOf(currentChar)>-1){
                    nextChar = formula[index+1];
                    if (operators.indexOf(nextChar)>-1 && nextChar != self.OPEN_BRACKET && currentChar != self.CLOSE_BRACKET) {
                        self.setErrorToFormula('MsgQ_9', [currentChar, nextChar]);
                    }
                }
            }
        }
        checkInputContent (formula) {
            let self = this, operand, prevOperand, operands, dotIndex,
                separators: string = self.separators.join('|'),
                textInsideDoubleQuoteRegex = /"((?:\\.|[^"\\])*)"/;
            operands = formula.split(new RegExp(separators, 'g')).map(item => item.trim()).filter(item => {
                return (item && item.length);
            });
            for(operand of operands) {
                if (isNaN(operand)) {
                    if (! (operand.indexOf(self.OPEN_CURLY_BRACKET) > -1) && operand.replace(textInsideDoubleQuoteRegex, "").length > 0) {
                        self.setErrorToFormula('MsgQ_10', [operand]);
                        continue;
                    }
                    let elementType = operand.substring(0, operand.indexOf(self.OPEN_CURLY_BRACKET)), elementName = operand.substring(operand.indexOf(self.OPEN_CURLY_BRACKET) + 1, operand.indexOf(self.CLOSE_CURLY_BRACKET));
                    if (self.acceptPrefix.indexOf(elementType) < 0) self.setErrorToFormula('MsgQ_10', [operand]);
                    if (!self.checkElementName(elementType, elementName)) self.setErrorToFormula('MsgQ_10', [operand]);
                } else {
                    dotIndex = operand.indexOf('.');
                    if (dotIndex > - 1 && operand.length - 1 - dotIndex > 5) self.setErrorToFormula('MsgQ_18', [operand]);
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
                    self.setErrorToFormula('MsgQ_15', [formula.substring(startFunctionIndex, formula.substring(startFunctionIndex).indexOf(self.OPEN_BRACKET))]);
                    break;
                }
                self.checkFunctionSyntax(formula.substring(startFunctionIndex, endFunctionIndex + 1));
                formula = formula.replace(formula.substring(startFunctionIndex, endFunctionIndex + 1),  0 );
            }
        }
        checkFunctionSyntax (functionSyntax) {
            let self = this, conditionRegex = new RegExp( ['\\\<', '>', '\\\≧', '\\\≦', '\\\＝', '\\\≠'].join('|'));;
            let functionName = functionSyntax.substring(functionSyntax.indexOf(self.OPEN_CURLY_BRACKET) + 1, functionSyntax.indexOf(self.OPEN_BRACKET)),
                functionParameter = functionSyntax.substring(functionSyntax.indexOf(self.OPEN_BRACKET) + 1, functionSyntax.lastIndexOf(self.CLOSE_BRACKET)).split(self.COMMA_CHAR).map(item => item.trim());
            if (functionParameter.length == 0) self.setErrorToFormula('MsgQ_15', [functionName]);
            if (functionName == self.CONDITIONAL){
                if (functionParameter.length != 3) {
                    if (functionParameter.length < 3) self.setErrorToFormula('MsgQ_15', [functionName]);
                    else self.setErrorToFormula('MsgQ_16', [functionName]);
                } else if (functionParameter[0].split(conditionRegex).length > 2) {
                    // should have a different message, temporary use msg 16
                    // condition is invalid
                    self.setErrorToFormula('MsgQ_16', [functionName]);
                }
            }
            if (functionName == self.ROUND_OFF || functionName == self.ROUND_UP || functionName == self.TRUNCATION) {
                if (functionParameter.length > 1) self.setErrorToFormula('MsgQ_16', [functionName]);
                if (!self.checkNumberDataType(functionParameter)){
                    self.setErrorToFormula('MsgQ_17', [functionName]);
                }
            }
            if (functionName == self.YEAR_MONTH) {
                if (functionParameter.length != 2) {
                    if (functionParameter.length < 2) self.setErrorToFormula('MsgQ_15', [functionName]);
                    else self.setErrorToFormula('MsgQ_16', [functionName]);
                } else if (!self.checkDateDataType(functionParameter)){
                    self.setErrorToFormula('MsgQ_17', [functionName]);
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
            for(var functionParameter of functionParameters ) {
                if (functionParameter.indexOf('"') > -1) return false;
            }
            return true;
        }
        checkDateDataType (functionParameters) {
            for(var functionParameter of functionParameters ) {
                if (functionParameter.indexOf('"') > -1) return false;
            }
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
        showHindBox () {
            let self = this;
            $('#D3_5').on('change', function () {

            })
        }
    }
}