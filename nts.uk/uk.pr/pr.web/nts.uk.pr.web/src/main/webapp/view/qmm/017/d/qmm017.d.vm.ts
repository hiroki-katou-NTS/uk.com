module nts.uk.pr.view.qmm017.d.viewmodel {
    import block = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    import dialog = nts.uk.ui.dialog;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import model = nts.uk.pr.view.qmm017.share.model;
    export class ScreenModel {
        // tabs variables
        screenDTabs: KnockoutObservableArray<any>;
        screenDSelectedTab: KnockoutObservable<string>;

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

        constructor() {
            var self = this;
            self.lineItemCategoryItem = ko.observableArray(model.getLineItemCategoryEnumModel());
            self.initTabPanel();
            self.changeDataByLineItemCategory();
            self.selectedPriceItemCategoryValue.subscribe(newValue => {
                self.showListUnitPriceItem(newValue);
            })
            self.selectedUnitPriceItemCode.subscribe(newValue => {
                self.showUnitPriceItemData(self.selectedPriceItemCategoryValue(), newValue);
            })
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

        }

        initTabPanel () {
            let self = this;
            self.screenDTabs = ko.observableArray([
                {id: 'tab-1', title: getText('QMM017_6'), content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true)},
                {id: 'tab-2', title: getText('QMM017_6'), content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true)},
                {id: 'tab-3', title: getText('QMM017_6'), content: '.tab-content-3', enable: ko.observable(true), visible: ko.observable(true)},
                {id: 'tab-4', title: getText('QMM017_6'), content: '.tab-content-4', enable: ko.observable(true), visible: ko.observable(true)},
                {id: 'tab-5', title: getText('QMM017_6'), content: '.tab-content-5', enable: ko.observable(true), visible: ko.observable(true)},
                {id: 'tab-6', title: getText('QMM017_6'), content: '.tab-content-6', enable: ko.observable(false), visible: ko.observable(false)},
                {id: 'tab-7', title: getText('QMM017_7'), content: '.tab-content-7', enable: ko.observable(true), visible: ko.observable(true)}
            ]);
            self.screenDSelectedTab = ko.observable('tab-1');
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
            service.getAllUnitPriceItem(unitPriceItemCategory, false).done(function(data) {
                self.unitPriceItemList(data);
                block.clear();
            }).fail(function(err) {
                block.clear();
                dialog.alertError(err.message);
            });
        }

        showUnitPriceItemData (unitPriceItemCategory, code) {
            let self = this, dfd = $.Deferred();
            block.invisible();
            // 廃止区分＝廃止しない (false)
            service.getUnitPriceItemByCode(unitPriceItemCategory, code).done(function(data) {
                self.selectedUnitPriceItem(ko.mapping.fromJS(data));
                self.displayUnitPriceItemNote(data.note);
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
    }
    
}