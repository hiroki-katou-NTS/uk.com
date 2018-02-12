module nts.uk.com.view.cmf001.b.viewmodel {
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
        itemList: KnockoutObservableArray<model.ItemModel>;
        selectedCode: KnockoutObservable<string>;
        
        constructor() {
            var self = this;
            self.itemList = ko.observableArray([
                new model.ItemModel('1', 'Item 1'),
                new model.ItemModel('2', 'Item 2'),
                new model.ItemModel('3', 'Item 3')
            ]);
    
            self.selectedCode = ko.observable('1');
        }
        
        
        
    }
}