module nts.uk.com.view.cmf002.c.viewmodel {
    import block = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    import model = cmf002.share.model;
    import confirm = nts.uk.ui.dialog.confirm;
    import alertError = nts.uk.ui.dialog.alertError;
    import info = nts.uk.ui.dialog.info;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;

    export class ScreenModel {
        conditionName: KnockoutObservable<string>;
        categoryName: KnockoutObservable<string>;
        itemCode: KnockoutObservable<string>;
        itemName: KnockoutObservable<string>;
        formula: KnockoutObservable<string>;
        
        itemType: KnockoutObservable<number>;
        itemTypes: KnockoutObservableArray<model.ItemModel> = ko.observableArray([]);
        
        selectedStandardOutputItemCode: KnockoutObservable<string>;
        listStandardOutputItem: KnockoutObservableArray<model.StandardOutputItem> = ko.observableArray([]);
        
        selectedExternalOutputCategoryItemData: KnockoutObservable<string>;
        listExternalOutputCategoryItemData: KnockoutObservableArray<model.ExternalOutputCategoryItemData> = ko.observableArray([]);
        
        constructor() {
            let self = this;
            // Todo
            let params = getShared("CMF002cParams");
            let _rsList: Array<model.ItemModel> = model.getSystemTypes();
            self.itemTypes(_rsList);
            
            self.conditionName = ko.observable("Condition Name"); // params.conditionName + "　" + params.conditionCode;
            self.categoryName = ko.observable("Category Name"); // params.categoryName;
            self.itemCode = ko.observable("Item Code");
            self.itemName = ko.observable("Item Name");
            self.itemType = ko.observable(0);
            self.formula = ko.observable("A1+B2+C3");
            self.selectedStandardOutputItemCode = ko.observable("123");
            self.selectedExternalOutputCategoryItemData = ko.observable("123");
        }
    }
}