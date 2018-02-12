module nts.uk.com.view.cmf001.d.viewmodel {
    import block = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    import model = cmf001.share.model;
    import confirm = nts.uk.ui.dialog.confirm;
    import alertError = nts.uk.ui.dialog.alertError;
    import info = nts.uk.ui.dialog.info;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;

    export class ScreenModel {
        listCategory: KnockoutObservableArray<model.ExternalAcceptanceCategory>;
        selectedCategory: KnockoutObservable<string>;
        
        listCategoryItem: KnockoutObservableArray<model.ExternalAcceptanceCategoryItemData>;
        selectedCategoryItem: KnockoutObservable<string>;
        constructor() {
            var self = this;
            self.listCategory = ko.observableArray([
                new model.ExternalAcceptanceCategory('1', 'Category 1'),
                new model.ExternalAcceptanceCategory('2', 'Category 2'),
                new model.ExternalAcceptanceCategory('3', 'Category 3')
            ]);
            self.selectedCategory = ko.observable('1');
            
            self.listCategoryItem = ko.observableArray([
                new model.ExternalAcceptanceCategoryItemData('001', 'Item 1'),
                new model.ExternalAcceptanceCategoryItemData('002', 'Item 2'),
                new model.ExternalAcceptanceCategoryItemData('003', 'Item 3'),
                new model.ExternalAcceptanceCategoryItemData('004', 'Item 4'),
                new model.ExternalAcceptanceCategoryItemData('005', 'Item 5') 
            ]);
            self.selectedCategoryItem = ko.observable('001');
            $("#fixed-table").ntsFixedTable({ height: 300 });
        }
        
        
    }
}