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
        valueTexBox: KnockoutObservable<any> = ko.observable('');

        enable: KnockoutObservable<boolean>;
        
        selectedValue1: KnockoutObservable<any>;
        items1: KnockoutObservableArray<any>;

        selectedValue2: KnockoutObservable<any>;
        items2: KnockoutObservableArray<any>;
        constructor() {
            let self = this;
            
            self.enable = ko.observable(true);
            self.items1 = ko.observableArray([
                { value: 1, text: getText('CMF001_303') },
                { value: 2, text: getText('CMF001_304') },
                { value: 3, text: getText('CMF001_305') },
                { value: 4, text: getText('CMF001_306') },
                { value: 5, text: getText('CMF001_307') },
                { value: 6, text: getText('CMF001_308') },
            ]);
            self.selectedValue1 = ko.observable(1);
            
            self.items2 = ko.observableArray([
                { value: 0, text: getText('CMF001_322') },
                { value: 1, text: getText('CMF001_323') }
            ]);
            self.selectedValue2 = ko.observable(1);
        }

        saveNumericSetting() {
            console.log(this.selectedValue1());
            console.log(this.selectedValue2());
        }
        cancelNumericSetting() {
            nts.uk.ui.windows.close(); //Close current window
        }
    }
}