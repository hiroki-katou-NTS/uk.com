module nts.uk.com.view.cmf001.j.viewmodel {
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
        isEditMode: KnockoutObservable<boolean>;

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

        constructor() {
            var self = this;

            self.isEditMode = ko.observable(true);

            self.itemsDigit = ko.observableArray([
                new model.ItemModel(0, getText('CMF001_322')),
                new model.ItemModel(1, getText('CMF001_323'))
            ]);
            self.selectedDigit = ko.observable(1);
            self.fromDigit = ko.observable('');
            self.toDigit = ko.observable('');

            self.itemsDecimalType = ko.observableArray([
                new model.ItemModel(0, getText('CMF001_342')),
                new model.ItemModel(1, getText('CMF001_343'))
            ]);
            self.selectedDecimalType = ko.observable(0);

            self.itemsTimeType = ko.observableArray([
                new model.ItemModel(0, getText('CMF001_352')),
                new model.ItemModel(1, getText('CMF001_353'))
            ]);
            self.selectedTimeType = ko.observable(0);

            self.itemsDelimiterSet = ko.observableArray([
                new model.ItemModel(0, getText('CMF001_355')),
                new model.ItemModel(1, '1234567890123456789012345')
            ]);
            self.selectedDelimiterSet = ko.observable(0);

            self.itemsRoundProcess = ko.observableArray([
                new model.ItemModel(0, getText('CMF001_358')),
                new model.ItemModel(1, getText('CMF001_359'))
            ]);
            self.selectedRoundProcess = ko.observable(1);

            self.itemsRoundProcessContent = ko.observableArray([
                new model.ItemModel(0, getText('CMF001_355')),
                new model.ItemModel(1, getText('1234567890123456789012345'))
            ]);
            self.selectedRoundProcessContent = ko.observable(0);

            self.itemsFixedValue = ko.observableArray([
                new model.ItemModel(0, getText('CMF001_363')),
                new model.ItemModel(1, getText('CMF001_364'))
            ]);
            self.selectedFixedValue = ko.observable(0);
            self.fixedValue = ko.observable('');
        }

        saveNumericSetting() {

        }
        cancelNumericSetting() {
            nts.uk.ui.windows.close(); //Close current window
        }
    }
}