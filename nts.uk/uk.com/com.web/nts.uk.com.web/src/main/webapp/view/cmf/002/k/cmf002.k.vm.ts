module nts.uk.com.view.cmf002.k.viewmodel {
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
        enableFormatSelection: KnockoutObservable<boolean>;
        formatSelection: KnockoutObservable<any>;
        formatSelectionItems: KnockoutObservableArray<model.ItemModel>;
        enableNullValueReplacement: KnockoutObservable<boolean>;
        nullValueReplacementSelected: KnockoutObservable<any>;
        nullValueReplacementItems: KnockoutObservableArray<model.ItemModel>;
        replacedValue: KnockoutObservable<string>;
        enableReplacedValueEditor: KnockoutObservable<boolean>;
        enableFixedValue: KnockoutObservable<boolean>;
        fixedValueSelected: KnockoutObservable<any>;
        fixedValueItems: KnockoutObservableArray<model.ItemModel>;
        fixedValue: KnockoutObservable<string>;
        enableFixedValueEditor: KnockoutObservable<boolean>;

        constructor() {
            var self = this;
            self.initComponent();
        }

        initComponent() {
            var self = this;
            self.formatSelection = ko.observable(0);
            self.formatSelectionItems = ko.observableArray([
                new model.ItemModel(0, getText('CMF002_180')),
                new model.ItemModel(1, getText('CMF002_181')),
                new model.ItemModel(2, getText('CMF002_182')),
                new model.ItemModel(3, getText('CMF002_183')),
                new model.ItemModel(4, getText('CMF002_184')),
                new model.ItemModel(5, getText('CMF002_185')),
                new model.ItemModel(6, getText('CMF002_186'))
            ]);
            self.nullValueReplacementSelected = ko.observable(1);
            self.nullValueReplacementItems = ko.observableArray([
                new model.ItemModel(0, getText('CMF002_149')),
                new model.ItemModel(1, getText('CMF002_150'))
            ]);
            self.replacedValue = ko.observable(null);
            self.fixedValueSelected = ko.observable(1);
            self.fixedValueItems = ko.observableArray([
                new model.ItemModel(0, getText('CMF002_149')),
                new model.ItemModel(1, getText('CMF002_150'))
            ]);
            self.fixedValue = ko.observable(null);
            self.enableFixedValue = ko.observable(true);
        }
        
        start(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            dfd.resolve();
            return dfd.promise();
        }

        enableFormatSelection() {
            var self = this;
            return (self.fixedValueSelected() == model.NOT_USE_ATR.USE);
        }

        enableNullValueReplacement() {
            var self = this;
            return (self.fixedValueSelected() == model.NOT_USE_ATR.USE);
        }

        enableReplacedValueEditor() {
            var self = this;
            return (self.fixedValueSelected() == model.NOT_USE_ATR.USE &&
                self.nullValueReplacementSelected() == model.NOT_USE_ATR.NOT_USE);
        }

        enableFixedValueEditor() {
            var self = this;
            return (self.fixedValueSelected() == model.NOT_USE_ATR.NOT_USE)
        }

        selectConvertCode() {
        }

        cancelSelectConvertCode() {
            nts.uk.ui.windows.close();
        }

    }
}