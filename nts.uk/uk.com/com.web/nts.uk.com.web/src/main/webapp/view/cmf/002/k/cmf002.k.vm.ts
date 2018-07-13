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
        formatSelection: KnockoutObservable<any>;
        formatSelectionItems: KnockoutObservableArray<model.ItemModel>;
        nullValueReplacementSelected: KnockoutObservable<any>;
        nullValueReplacementItems: KnockoutObservableArray<model.ItemModel>;
        replacedValue: KnockoutObservable<string>;
        enableFixedValue: KnockoutObservable<boolean>;
        fixedValueSelected: KnockoutObservable<any>;
        fixedValueItems: KnockoutObservableArray<model.ItemModel>;
        fixedValue: KnockoutObservable<string>;

        constructor() {
            let self = this;
            self.initComponent();
        }

        initComponent() {
            let self = this;
            self.formatSelection = ko.observable(0);
            self.formatSelectionItems = ko.observableArray([
                //YYYY/MM/DD
                new model.ItemModel(0, getText('CMF002_180')),
                //YYYYMMDD
                new model.ItemModel(1, getText('CMF002_181')),
                //YY/MM/DD
                new model.ItemModel(2, getText('CMF002_182')),
                //YYMMDD
                new model.ItemModel(3, getText('CMF002_183')),
                //JJYY/MM/DD
                new model.ItemModel(4, getText('CMF002_184')),
                //JJYYMMDD
                new model.ItemModel(5, getText('CMF002_185')),
                //曜日
                new model.ItemModel(6, getText('CMF002_186'))
            ]);
            self.nullValueReplacementSelected = ko.observable(1);
            self.nullValueReplacementItems = ko.observableArray([
                //使用する
                new model.ItemModel(0, getText('CMF002_149')),
                //使用しない
                new model.ItemModel(1, getText('CMF002_150'))
            ]);
            self.replacedValue = ko.observable(null);
            self.fixedValueSelected = ko.observable(1);
            self.fixedValueItems = ko.observableArray([
                //使用する
                new model.ItemModel(0, getText('CMF002_149')),
                //使用しない
                new model.ItemModel(1, getText('CMF002_150'))
            ]);
            self.fixedValue = ko.observable(null);
            self.enableFixedValue = ko.observable(true);
        }

        start(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            dfd.resolve();
            return dfd.promise();
        }

        //enable component when not using fixed value
        enable() {
            let self = this;
            return (self.fixedValueSelected() == 1);
        }

        //enable component replacement value editor
        enableReplacedValueEditor() {
            let self = this;
            return (self.enable() && self.nullValueReplacementSelected() == 0);
        }

        selectConvertCode() {
        }

        cancelSelectConvertCode() {
            nts.uk.ui.windows.close();
        }
        
         ///////test, Xóa khi hoàn thành
        gotoScreenK() {
            let self = this;
            nts.uk.ui.windows.sub.modal("/view/cmf/002/k/index.xhtml");
        }
    }
}