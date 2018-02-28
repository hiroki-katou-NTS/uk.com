module nts.uk.com.view.cmf001.i.viewmodel {
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

        listRadioBox1: KnockoutObservableArray<model.ItemModel> = ko.observableArray([]);
        selectedValue1: KnockoutObservable<any> = ko.observable(0);

        listRadioBox2: KnockoutObservableArray<model.ItemModel> = ko.observableArray([]);
        selectedValue2: KnockoutObservable<any> = ko.observable(0);
        
        valueTexBox: KnockoutObservable<any> = ko.observable('123123123');
        constructor() {
            let self = this;

            self.listRadioBox1 = ko.observableArray([
                new model.ItemModel(0, getText('CMF001_303')),
                new model.ItemModel(1, getText('CMF001_304')),
                new model.ItemModel(2, getText('CMF001_305')),
                new model.ItemModel(3, getText('CMF001_306')),
                new model.ItemModel(4, getText('CMF001_307')),
                new model.ItemModel(5, getText('CMF001_308'))
            ]);
            
            self.listRadioBox2 = ko.observableArray([
                new model.ItemModel(0, getText('CMF001_322')),
                new model.ItemModel(1, getText('CMF001_323'))
            ]);
        }

        saveNumericSetting() {

        }
        cancelNumericSetting() {
            nts.uk.ui.windows.close(); //Close current window
        }
    }
}