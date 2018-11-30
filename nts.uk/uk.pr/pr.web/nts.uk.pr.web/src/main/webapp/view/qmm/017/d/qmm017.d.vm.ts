module nts.uk.pr.view.qmm017.d.viewmodel {
    import block = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    import dialog = nts.uk.ui.dialog;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import model = nts.uk.pr.view.qmm017.share.model
    import getMessage = nts.uk.resource.getMessage;
    import current = nts.uk.request.location.current;
    export class ScreenModel {
        // tab 1
        lineItemCategoryItem: KnockoutObservableArray<model.EnumModel> = model.getLineItemCategoryItem();
        selectedCategoryValue: KnockoutObservable<number> = ko.observable(model.LINE_ITEM_CATEGORY.PAYMENT_ITEM);
        selectedCategory: KnockoutObservableArray<string> = ko.observable(null);
        statementItemList: KnockoutObservableArray<any> = ko.observableArray([]);
        selectedStatementItemCode: KnockoutObservable<string> = ko.observable(null);
        selectedStatementItem: KnockoutObservable<any> = ko.observable(null);
        displayItemNote: KnockoutObservable<string> = ko.observable(null); // D2_10
        // tab 2
        unitPriceItemCategoryItem: KnockoutObservableArray<model.EnumModel> = model.getUnitPriceItemCategoryItem();
        selectedPriceItemCategoryValue: KnockoutObservable<number> = ko.observable(model.UNIT_PRICE_ITEM_CATEGORY.COMPANY_UNIT_PRICE_ITEM);
        selectedPriceItemCategory: KnockoutObservable<any> = ko.observable(null);
        unitPriceItemList: KnockoutObservableArray<any> = ko.observableArray([]); // mix of 給与会社単価, 給与個人単価名称
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
        calculationFormulaDictionary = new model.DetailCalculationElement();

        operator: Array<any> = [];

        constructor() {
            var self = this;
            self.initTabPanel();
            self.changeDataByLineItemCategory();
            self.changeDataByUnitPriceItem();
            self.changeDataByFormula();
            self.initWageTableData();
            self.changeDataByWageTable();
            self.initCalculationFormulaDictionary();
            $('#D3_5').on('focus', function(){
                nts.uk.ui.errors.clearAll();
            })
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
                self.selectedFormula(new model.Formula(selectedFormula));
            })
        }

        initWageTableData () {
            let self = this;
            service.getAllWageTable().done(function(data){
                if (data){
                    self.wageTableList(data);
                    if (data.length > 0) self.selectedWageTableCode(data[0].wageTableCode);
                } else {
                    self.wageTableList([]);
                    self.selectedWageTableCode(null);
                }
            })
        }

        changeDataByWageTable() {
            let self = this;
            self.selectedWageTableCode.subscribe(newValue => {
                let currentWageTableList = ko.toJS(self.wageTableList), selectedWageTable;
                selectedWageTable = _.find(currentWageTableList, {wageTableCode: newValue});
                self.selectedWageTable(new model.WageTable(selectedWageTable));
            })
        }

        initTabPanel () {

        }
        // tab 1
        showListStatementItemData (categoryAtr) {
            let self = this;
            block.invisible();
            // 廃止区分＝廃止しない (false)
            service.getAllStatementItemData(categoryAtr, false).done(function(data) {
                if (data){
                    self.statementItemList(data);
                    if (data.length > 0) self.selectedStatementItemCode(data[0].itemNameCd)
                } else {
                    self.statementItemList([]);
                    self.selectedStatementItemCode(null);
                }
                block.clear();
            }).fail(function(err) {
                block.clear();
                dialog.alertError(err.message);
            });
        }

        showStatementItemData (categoryAtr, itemNameCode) {
            let self = this, dfd = $.Deferred();
            block.invisible();
            // 廃止区分＝廃止しない (false)
            service.getStatementItemData(categoryAtr, itemNameCode).done(function(data) {
                self.selectedStatementItem(ko.mapping.fromJS(data));
                self.displayItemNote(categoryAtr == model.LINE_ITEM_CATEGORY.PAYMENT_ITEM ? data.paymentItemSet.note : categoryAtr == model.LINE_ITEM_CATEGORY.DEDUCTION_ITEM ? data.deductionItemSet.note: data.timeItemSet.note);
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
            block.invisible();
            // 廃止区分＝廃止しない (false)
            service.getAllUnitPriceItem(unitPriceItemCategory, false, __viewContext.screenModel.selectedHistory().startMonth()).done(function(data) {
                if (data) {
                    self.unitPriceItemList(data);
                    if (data.length > 0) self.selectedUnitPriceItemCode(data[0].code);
                } else {
                    self.unitPriceItemList([]);
                    self.selectedUnitPriceItemCode(null);
                }

                block.clear();
            }).fail(function(err) {
                block.clear();
                dialog.alertError(err.message);
            });
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
            self.calculationFormulaDictionary.PAYMENT_ITEM = {displayContent: '支給{0}', registerContent: '0000_0{0}'};
            self.calculationFormulaDictionary.DEDUCTION_ITEM = {displayContent: '控除{0}', registerContent: '0000_1{0}'};
            self.calculationFormulaDictionary.ATTENDANCE_ITEM = {displayContent: '勤怠{0}', registerContent: '0000_2{0}'};
            self.calculationFormulaDictionary.COMPANY_UNIT_PRICE = {displayContent: '会社単価{0}', registerContent: 'U000_0{0}'};
            self.calculationFormulaDictionary.INDIVIDUAL_UNIT_PRICE = {displayContent: '個人単価{0}', registerContent: 'U000_1{0}'};
            self.calculationFormulaDictionary.CONDITION_EXPRESSION = {displayContent: '関数{条件式}', registerContent: 'Func_00001'};
            self.calculationFormulaDictionary.AND = {displayContent: '関数{かつ}', registerContent: 'Func_00002'};
            self.calculationFormulaDictionary.OR = {displayContent: '関数{または}', registerContent: 'Func_00003'};
            self.calculationFormulaDictionary.ROUND_OFF = {displayContent: '関数{四捨五入}', registerContent: 'Func_00004'};
            self.calculationFormulaDictionary.TRUNCATION = {displayContent: '関数{切り捨て}', registerContent: 'Func_00005'};
            self.calculationFormulaDictionary.ROUND_UP = {displayContent: '関数{切り上げ}', registerContent: 'Func_00006'};
            self.calculationFormulaDictionary.MAX_VALUE = {displayContent: '関数{最大値}', registerContent: 'Func_00007'};
            self.calculationFormulaDictionary.MIN_VALUE = {displayContent: '関数{最小値}', registerContent: 'Func_00008'};
            self.calculationFormulaDictionary.NUMBER_OF_FAMILY_MEMBER = {displayContent: '関数{家族人数}', registerContent: 'Func_00009'};
            self.calculationFormulaDictionary.ADDITIONAL_YEAR_MONTH = {displayContent: '関数{年月加算}', registerContent: 'Func_00010'};
            self.calculationFormulaDictionary.YEAR_EXTRACTION = {displayContent: '関数{年抽出}', registerContent: 'Func_00011'};
            self.calculationFormulaDictionary.MONTH_EXTRACTION = {displayContent: '関数{月抽出}', registerContent: 'Func_00012'};
            self.calculationFormulaDictionary.SYSTEM_YMD_DATE = {displayContent: 'システム日付（年月日）', registerContent: 'vari_00001'};
            self.calculationFormulaDictionary.SYSTEM_Y_DATE = {displayContent: 'システム日付（年）', registerContent: 'vari_00002'};
            self.calculationFormulaDictionary.SYSTEM_YM_DATE = {displayContent: 'システム日付（年月）', registerContent: 'vari_00003'};
            self.calculationFormulaDictionary.PROCESSING_YEAR_MONTH = {displayContent: '処理年月', registerContent: 'vari_00004'};
            self.calculationFormulaDictionary.PROCESSING_YEAR = {displayContent: '処理年', registerContent: 'vari_00005'};
            self.calculationFormulaDictionary.REFERENCE_TIME = {displayContent: '基準時間', registerContent: 'vari_00006'};
            self.calculationFormulaDictionary.STANDARD_DAY = {displayContent: '基準日数', registerContent: 'vari_00007'};
            self.calculationFormulaDictionary.WORKDAY = {displayContent: '要勤務日数', registerContent: 'vari_00008'};
            self.calculationFormulaDictionary.FORMULA = {displayContent: '計算式{0}', registerContent: 'calc_00{0}'};
            self.calculationFormulaDictionary.WAGE_TABLE = {displayContent: '賃金テーブル{0}', registerContent: 'wage_00{0}'};
        }

        addStatementItem () {
            let self = this, selectedCategory = self.selectedCategoryValue(), appendFormula = "";
            if (selectedCategory == model.LINE_ITEM_CATEGORY.PAYMENT_ITEM) {
                appendFormula = self.formatParameter(this.calculationFormulaDictionary.PAYMENT_ITEM.displayContent, [this.selectedStatementItem().name()]);
            } else if (selectedCategory == model.LINE_ITEM_CATEGORY.DEDUCTION_ITEM) {
                appendFormula = self.formatParameter(this.calculationFormulaDictionary.DEDUCTION_ITEM.displayContent, [this.selectedStatementItem().name()]);
            } else {
                appendFormula = self.formatParameter(this.calculationFormulaDictionary.ATTENDANCE_ITEM.displayContent, [this.selectedStatementItem().name()]);
            }
            self.addToFormulaByPosition(appendFormula);
        }
        addUnitPriceItem () {
            let self = this, selectedUnitPriceValue = self.selectedPriceItemCategoryValue(), appendFormula = "";
            let selectedUnitPrice = _.find(self.unitPriceItemList(), {code: self.selectedUnitPriceItemCode()});
            if (selectedUnitPriceValue == model.UNIT_PRICE_ITEM_CATEGORY.COMPANY_UNIT_PRICE_ITEM) {
                appendFormula = self.formatParameter(this.calculationFormulaDictionary.COMPANY_UNIT_PRICE.displayContent, [selectedUnitPrice ? selectedUnitPrice['name'] : ""]);
            } else {
                appendFormula = self.formatParameter(this.calculationFormulaDictionary.INDIVIDUAL_UNIT_PRICE.displayContent, [selectedUnitPrice ? selectedUnitPrice['name'] : ""]);
            }
            self.addToFormulaByPosition(appendFormula);
        }
        addFunctionItem () {

        }
        addVariableItem () {
            let self = this, selectedSystemVariableListValue = self.selectedSystemVariableListValue();
            self.addToFormulaByPosition(self.formatParameter('関数{0}', [self.systemVariableListItem()[selectedSystemVariableListValue]['name']]));
        }
        addFormulaItem () {
            let self = this;
            self.addToFormulaByPosition(self.formatParameter(self.calculationFormulaDictionary.FORMULA.displayContent, [self.selectedFormula().formulaName()]));
        }
        addWageTableItem () {
            let self = this;
            self.addToFormulaByPosition(self.formatParameter(self.calculationFormulaDictionary.WAGE_TABLE.displayContent, [self.selectedWageTable().wageTableName()]));
        }

        addToFormulaByPosition (formulaToAdd: string) {
            let self = this, calculationFormulaItem = $('#D3_5')[0];
            let startSelection = calculationFormulaItem['selectionStart'], endSelection = calculationFormulaItem['selectionEnd'];
            self.displayDetailCalculationFormula(self.displayDetailCalculationFormula().substring(0, startSelection) + formulaToAdd + self.displayDetailCalculationFormula().substring(startSelection));
            let newStartSelection = startSelection + formulaToAdd.length;
            $('#D3_5').focus();
            calculationFormulaItem.setSelectionRange(newStartSelection, newStartSelection);
        }
        startTrialCalculation () {
            let self = this;
            setShared("QMM017_G_PARAMS", {});
            modal("/view/qmm/017/g/index.xhtml").onClosed(function () {

            });
        }


        addSymbol (symbol: string) {
            let self = this;
            let symbol = event.target['innerText'];
            this.addToFormulaByPosition(symbol);
        }

        formatParameter (resource: string, parameters: Array) {
            for(var index = 0 ; index < parameters.length; index++){
                resource = resource.replace('{'+index+'}', '{'+parameters[index]+'}');
            }
            return resource;
        }
        validateSyntax () {
            let self = this, formula = self.displayDetailCalculationFormula();
            $('#D3_5').ntsError('clear');
            if (formula.split('(').length != formula.split(')').length) {
                self.setErrorToFormula('MsgQ_8');
            }
        }
        setErrorToFormula (messageId: string) {
            $('#D3_5').ntsError('error', {messageId: messageId});
        }
        calculation () {
            let self = this, formula = self.displayDetailCalculationFormula();
            let prefix = [], operators = [], operator, currentChar;
            for(let index = 0; index < formula.length; i++){
                currentChar = formula[index];
                if (!isNaN(currentChar)) {
                    prefix.push(currentChar);
                    continue;
                }
                if (currentChar == '(') {
                    while(operator = operator.pop() != '(') {
                        prefix.push(this.operator);
                    }
                }
            }
        }

    }
    
}