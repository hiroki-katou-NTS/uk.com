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

        formatSelectionItems: KnockoutObservableArray<model.ItemModel>;
        nullValueReplacementItems: KnockoutObservableArray<model.ItemModel>;
        enableFixedValue: KnockoutObservable<boolean>;
        fixedValueItems: KnockoutObservableArray<model.ItemModel>;
        dateDataFormatSetting: KnockoutObservable<model.DateDataFormatSetting>;
        formatSelection: KnockoutObservable<number>;
        nullValueSubstitution: KnockoutObservable<number>;
        valueOfNullValueSubs: KnockoutObservable<string>;
        fixedValue: KnockoutObservable<number>;
        valueOfFixedValue: KnockoutObservable<string>;
        //Defaut Mode Screen
        // 0 = Individual
        // 1 = initial
        selectModeScreen: KnockoutObservable<number> = ko.observable(1);

        constructor() {
            let self = this;
            self.initComponent();
        }

        initComponent() {
            let self = this;
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
            self.nullValueReplacementItems = ko.observableArray([
                //使用する
                new model.ItemModel(0, getText('CMF002_149')),
                //使用しない
                new model.ItemModel(1, getText('CMF002_150'))
            ]);
            self.fixedValueItems = ko.observableArray([
                //使用する
                new model.ItemModel(0, getText('CMF002_149')),
                //使用しない
                new model.ItemModel(1, getText('CMF002_150'))
            ]);
            self.enableFixedValue = ko.observable(true);

            self.dateDataFormatSetting = ko.observable(new model.DateDataFormatSetting(0, 1, null, 1, null));
            let parameter = getShared('CMF002kParams');
            if (parameter) {
                self.selectModeScreen = parameter.selectModeScreen;
                self.dateDataFormatSetting = ko.observable(parameter.dateDataFormatSetting);
            }
            self.formatSelection = self.dateDataFormatSetting.formatSelection;
            self.nullValueSubstitution = self.dateDataFormatSetting.nullValueSubstitution;
            self.valueOfNullValueSubs = self.dateDataFormatSetting.valueOfNullValueSubs;
            self.fixedValue = self.dateDataFormatSetting.fixedValue;
            self.valueOfFixedValue = self.dateDataFormatSetting.valueOfFixedValues;
        }

        start(): JQueryPromise<any> {
            block.invisible();
            let self = this;
            let dfd = $.Deferred();



            if (self.selectModeScreen) {
                service.getDateFormatSetting().done(function(data: any) {
                    if (data != null) {
                        self.formatSelection(data.formatSelection);
                        self.nullValueSubstitution(data.nullValueSubstitution);
                        self.fixedValue(data.fixedValue);
                        self.valueOfNullValueSubs(data.valueOfNullValueSubs);
                        self.valueOfFixedValue(data.valueOfFixedValue);
                    }

                    block.clear();
                    dfd.resolve();
                }).fail(function(error) {
                    alertError(error);
                    block.clear();
                    dfd.reject();
                });
            } else {

            }
            return dfd.promise();
        }

        //enable component when not using fixed value
        enable() {
            let self = this;
            return (self.fixedValue() == 1);
        }

        //enable component replacement value editor
        enableReplacedValueEditor() {
            let self = this;
            let enable = (self.enable() && self.nullValueSubstitution() == 0);
            if (!enable) {
                nts.uk.ui.errors.clearAll()
            }
            return enable;
        }

        enableFixedValueEditor() {
            let self = this;
            let enable = !self.enable();
            if (!enable) {
                nts.uk.ui.errors.clearAll()
            }
            return enable;
        }

        selectDateDataFormatSetting() {
            let self = this;
            self.dateDataFormatSetting = ko.observable(new model.DateDataFormatSetting(
                self.formatSelection(), self.nullValueSubstitution(), self.valueOfNullValueSubs(),
                self.fixedValue(), self.valueOfFixedValue()));
            // Case initial
            if (self.selectModeScreen) {
                service.addDateFormatSetting(self.dateDataFormatSetting).done(result => {
                    nts.uk.ui.windows.close();
                }).fail(function(error) {
                    alertError(error);
                });
                // Case individual
            } else {
                setShared("CMF002kParams", { dateDataFormatSetting: self.dateDataFormatSetting });
            }
        }

        cancelSelectDateDataFormatSetting() {
            nts.uk.ui.windows.close();
        }

        ///////test, Xóa khi hoàn thành
        gotoScreenK() {
            let self = this;
            nts.uk.ui.windows.sub.modal("/view/cmf/002/k/index.xhtml");
        }
    }
}