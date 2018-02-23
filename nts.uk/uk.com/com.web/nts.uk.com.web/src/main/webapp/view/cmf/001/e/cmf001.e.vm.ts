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
            let selectedCategoryItem = getShared("selectedCategoryItem");
            //alert(selectedCategoryItem);\
            self.listCategoryItem(getShared("listCategory"));
            if(_.isEqual(selectedCategoryItem, self.selectedCategoryItem)){
                self.selectedCategoryItem = ko.observable(selectedCategoryItem);
            }else{
                self.selectedCategoryItem = ko.observable('001');
            }
        }
        cancel(){
            nts.uk.ui.windows.close();
        }
        save(){
            var self = this;
            setShared("selectedCategoryItem", self.selectedCategoryItem);
            alert(self.selectedCategoryItem);
            nts.uk.ui.windows.close();  
        }
     }
}