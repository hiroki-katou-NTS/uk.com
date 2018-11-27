module nts.uk.pr.view.qmm017.d.viewmodel {
    import block = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    import dialog = nts.uk.ui.dialog;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import model = nts.uk.pr.view.qmm017.share.model
    export class ScreenModel {
        // tab 1
        lineItemCategoryItem: KnockoutObservableArray<model.EnumModel> = model.getLineItemCategoryItem();
        selectedCategoryValue: KnockoutObservable<number> = ko.observable(null);
        selectedCategory: KnockoutObservableArray<string> = ko.observable(null);
        statementItemList: KnockoutObservableArray<any> = ko.observableArray([]);
        selectedStatementItemCode: KnockoutObservable<string> = ko.observable(null);
        selectedStatementItem: KnockoutObservable<any> = ko.observable(null);
        displayItemNote: KnockoutObservable<string> = ko.observable(null); // D2_10
        // tab 2
        unitPriceItemCategoryItem: KnockoutObservableArray<model.EnumModel> = model.getUnitPriceItemCategoryItem();
        selectedPriceItemCategoryValue: KnockoutObservable<number> = ko.observable(null);
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
        // parent model - screen A

        constructor() {
            var self = this;
            self.initTabPanel();
            self.changeDataByLineItemCategory();
            self.changeDataByUnitPriceItem();
            self.changeDataByFormula();
            self.initWageTableData();
            self.changeDataByWageTable();
        }
        // tab 1
        changeDataByLineItemCategory () {
            let self = this;
            self.selectedCategoryValue.subscribe(newValue => {
                self.showListStatementItemData(newValue);
            })
            self.selectedStatementItemCode.subscribe(newValue => {
                self.showStatementItemData(self.selectedCategoryValue(), newValue);
            })
        }
        // tab 2
        changeDataByUnitPriceItem () {
            let self = this;
            self.selectedPriceItemCategoryValue.subscribe(newValue => {
                self.showListUnitPriceItem(newValue);
            })
            self.selectedUnitPriceItemCode.subscribe(newValue => {
                self.showUnitPriceItemData(self.selectedPriceItemCategoryValue(), newValue);
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
                if (data && data.length) self.wageTableList(data);
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
                self.statementItemList(data);
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
                self.unitPriceItemList(data);
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
        startTrialCalculation () {
            let self = this;
            setShared("QMM017_G_PARAMS", {});
            modal("/view/qmm/017/g/index.xhtml").onClosed(function () {

            });
        }
    }
    
}