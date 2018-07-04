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
        screenMode: KnockoutObservable<number>;
        
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
            let params = getShared("CMF002bParams");
            let _rsList: Array<model.ItemModel> = model.getItemTypes();
            self.itemTypes(_rsList);
            
            self.conditionName = ko.observable(params.conditionName + "　" + params.conditionCode); 
            self.categoryName = ko.observable(params.categoryName);
            self.itemCode = ko.observable("Item Code");
            self.itemName = ko.observable("Item Name");
            self.itemType = ko.observable(0);
            self.formula = ko.observable("A1+B2+C3");
            self.selectedStandardOutputItemCode = ko.observable("123");
            self.selectedExternalOutputCategoryItemData = ko.observable("123");
        }
        
        openCMF002g() {
            modal("/view/cmf/002/g/index.xhtml").onClosed(function() {

            });
        }

        openCMF002h() {
            modal("/view/cmf/002/h/index.xhtml").onClosed(function() {

            });
        }

        openCMF002f() {
            modal("/view/cmf/002/f/index.xhtml").onClosed(function() {

            });
        }

        openItemTypeSetting() {
            modal("/view/cmf/002/i/index.xhtml").onClosed(function() {

            });

            modal("/view/cmf/002/j/index.xhtml").onClosed(function() {

            });

            modal("/view/cmf/002/k/index.xhtml").onClosed(function() {

            });

            modal("/view/cmf/002/l/index.xhtml").onClosed(function() {

            });

            modal("/view/cmf/002/m/index.xhtml").onClosed(function() {

            });

            modal("/view/cmf/002/n/index.xhtml").onClosed(function() {

            });
        }
    }
}