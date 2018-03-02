module nts.uk.com.view.cmf001.k.viewmodel {
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
        listConvertCode: KnockoutObservableArray<model.AcceptanceCodeConvert>
        selectedConvertCode: KnockoutObservable<string>
        /* screen */
        constructor() {
            var self = this;
            self.listConvertCode = ko.observableArray([
            new model.AcceptanceCodeConvert("001","XXX", [], 1),
            new model.AcceptanceCodeConvert("002","YYY", [], 1),
            new model.AcceptanceCodeConvert("003","XXX", [], 1),
            new model.AcceptanceCodeConvert("004","YYY", [], 1),
            new model.AcceptanceCodeConvert("005","XXX", [], 1),
            new model.AcceptanceCodeConvert("006","YYY", [], 1),
            new model.AcceptanceCodeConvert("007","XXX", [], 1),
            new model.AcceptanceCodeConvert("008","YYY", [], 1),
            new model.AcceptanceCodeConvert("009","XXX", [], 1),
            new model.AcceptanceCodeConvert("010","YYY", [], 1)
                
            ]);
            self.selectedConvertCode = ko.observable("001");
            
        }
        selectConvertCode(){
            nts.uk.ui.windows.close();
        }
        cancelSelectConvertCode(){
            nts.uk.ui.windows.close();
        }
    }
    
}