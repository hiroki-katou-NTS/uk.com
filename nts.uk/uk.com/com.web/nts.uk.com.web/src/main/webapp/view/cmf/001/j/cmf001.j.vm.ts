module nts.uk.com.view.cmf001.j.viewmodel {
    import getText = nts.uk.resource.getText;
    import model = cmf001.share.model;
    import alertError = nts.uk.ui.dialog.alertError;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;

    export class ScreenModel {
        inputMode: KnockoutObservable<boolean>;

        selectedDigit: KnockoutObservable<any>;
        itemsDigit: KnockoutObservableArray<model.ItemModel>;
        fromDigit: KnockoutObservable<any>;
        toDigit: KnockoutObservable<any>;

        itemsDecimalType: KnockoutObservableArray<model.ItemModel>;
        selectedDecimalType: KnockoutObservable<any>;

        itemsTimeType: KnockoutObservableArray<model.ItemModel>;
        selectedTimeType: KnockoutObservable<any>;


        itemsDelimiterSet: KnockoutObservableArray<model.ItemModel>;
        selectedDelimiterSet: KnockoutObservable<any>;

        selectedRoundProcess: KnockoutObservable<any>;
        itemsRoundProcess: KnockoutObservableArray<model.ItemModel>


        itemsRoundProcessContent: KnockoutObservableArray<model.ItemModel>;
        selectedRoundProcessContent: KnockoutObservable<any>;

        selectedFixedValue: KnockoutObservable<any>;
        itemsFixedValue: KnockoutObservableArray<model.ItemModel>;
        fixedValue: KnockoutObservable<any>;

        atrUse: number;
        atrNotUse: number;
        decimal60: number;
        decimal10: number;
        timeHM: number;
        timeM: number;

        constructor() {
            var self = this;

            self.atrUse = model.NOT_USE_ATR.USE;
            self.atrNotUse = model.NOT_USE_ATR.NOT_USE;
            
            self.decimal60 = 0;
            self.decimal10 = 1;
            self.timeHM = 0;
            self.timeM = 1;

            self.inputMode = ko.observable(true);

            //J2
            self.itemsDigit = ko.observableArray([
                new model.ItemModel(self.atrUse, getText('CMF001_322')),
                new model.ItemModel(self.atrNotUse, getText('CMF001_323'))
            ]);
            self.selectedDigit = ko.observable(self.atrUse);
            self.fromDigit = ko.observable('');
            self.toDigit = ko.observable('');

            //J3
            self.itemsDecimalType = ko.observableArray([
                new model.ItemModel(self.decimal60, getText('CMF001_342')),
                new model.ItemModel(self.decimal10, getText('CMF001_343'))
            ]);
            self.selectedDecimalType = ko.observable(self.decimal60);

            //J4
            self.itemsTimeType = ko.observableArray([
                new model.ItemModel(self.timeHM, getText('CMF001_352')),
                new model.ItemModel(self.timeM, getText('CMF001_353'))
            ]);
            self.selectedTimeType = ko.observable(self.timeHM);

            //J5
            self.itemsDelimiterSet = ko.observableArray([
                new model.ItemModel(0, getText('CMF001_355')),
                new model.ItemModel(1, '1234567890123456789012345')
            ]);
            self.selectedDelimiterSet = ko.observable(0);

            //J6
            self.itemsRoundProcess = ko.observableArray([
                new model.ItemModel(self.atrUse, getText('CMF001_358')),
                new model.ItemModel(self.atrNotUse, getText('CMF001_359'))
            ]);
            self.selectedRoundProcess = ko.observable(self.atrUse);

            self.itemsRoundProcessContent = ko.observableArray([
                new model.ItemModel(0, getText('CMF001_355')),
                new model.ItemModel(1, getText('1234567890123456789012345'))
            ]);
            self.selectedRoundProcessContent = ko.observable(0);

            //J7
            self.itemsFixedValue = ko.observableArray([
                new model.ItemModel(self.atrUse, getText('CMF001_363')),
                new model.ItemModel(self.atrNotUse, getText('CMF001_364'))
            ]);
            self.selectedFixedValue = ko.observable(self.atrUse);
            self.fixedValue = ko.observable('');
        }

        checkActive1() {
            let self = this;
            if (self.selectedDigit() == self.atrUse) {
                return true;
            }
            return false;
        }

        checkActive2() {
            let self = this;
            if (self.selectedRoundProcess() == self.atrUse) {
                return true;
            }
            return false;
        }

        checkActive3() {
            let self = this;
            if (self.selectedFixedValue() == self.atrUse) {
                return true;
            }
            return false;
        }

        checkActive4() {
            let self = this;
            if (self.selectedDecimalType() == self.decimal60) {
                return true;
            }
            return false;
        }

        checkActive5() {
            let self = this;
            if (self.selectedDecimalType() == self.decimal60) {
                return false;
            }
            return true;
        }

        checkActive6() {
            let self = this;
            if (self.selectedFixedValue() == self.atrUse) {
                return false;
            }
            return true;
        }

        saveNumericSetting() {
            let self = this;
            console.log(self.selectedDelimiterSet());
        }
        cancelNumericSetting() {
            nts.uk.ui.windows.close(); //Close current window
        }
    }
}