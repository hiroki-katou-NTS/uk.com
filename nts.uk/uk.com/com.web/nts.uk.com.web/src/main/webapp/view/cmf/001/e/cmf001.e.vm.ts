module nts.uk.com.view.cmf001.e.viewmodel {
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
        listCategoryItem: KnockoutObservableArray<model.ExternalAcceptanceCategoryItemData>;
        selectedCategoryItem: KnockoutObservable<string>;
        constructor() {
            var self = this;
            self.listCategoryItem = ko.observableArray([
                new model.ExternalAcceptanceCategoryItemData('001', 'Item 1'),
                new model.ExternalAcceptanceCategoryItemData('002', 'Item 2'),
                new model.ExternalAcceptanceCategoryItemData('003', 'Item 3'),
                new model.ExternalAcceptanceCategoryItemData('004', 'Item 4'),
                new model.ExternalAcceptanceCategoryItemData('005', 'Item 5'),
                new model.ExternalAcceptanceCategoryItemData('006', 'Item 6'),
                new model.ExternalAcceptanceCategoryItemData('007', 'Item 7'),
                new model.ExternalAcceptanceCategoryItemData('008', 'Item 8'),
                new model.ExternalAcceptanceCategoryItemData('009', 'Item 9'),
                new model.ExternalAcceptanceCategoryItemData('010', 'Item 10'),
                new model.ExternalAcceptanceCategoryItemData('011', 'Item 11'),
                new model.ExternalAcceptanceCategoryItemData('012', 'Item 12'),
                new model.ExternalAcceptanceCategoryItemData('013', 'Item 13'),
                new model.ExternalAcceptanceCategoryItemData('014', 'Item 14'),
                new model.ExternalAcceptanceCategoryItemData('015', 'Item 15')
            ]);
            self.selectedCategoryItem = ko.observable('001');
        }
        open001E(){
            var self =this;
            nts.uk.ui.windows.sub.modal("/view/cmf/001/e/index.xhtml");
        }
        cancel(){
            nts.uk.ui.windows.close();
        }
        setCategory(){
            alert("save");    
        }
     }
}