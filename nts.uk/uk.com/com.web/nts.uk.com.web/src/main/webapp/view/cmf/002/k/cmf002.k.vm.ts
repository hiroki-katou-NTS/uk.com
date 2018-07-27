module nts.uk.com.view.cmf002.k.viewmodel {
    import block = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    import model = cmf002.share.model;
    import dataformatSettingMode = cmf002.share.model.DATA_FORMAT_SETTING_SCREEN_MODE;
    import alertError = nts.uk.ui.dialog.alertError;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;

    export class ScreenModel {
        notUse: number = model.NOT_USE_ATR.NOT_USE;
        use: number = model.NOT_USE_ATR.USE;
        formatSelectionItems: KnockoutObservableArray<model.ItemModel> = ko.observableArray(this.getFormatSelectionItems());
        enableFixedValue: KnockoutObservable<boolean> = ko.observable(true);
        dateDataFormatSetting: KnockoutObservable<model.DateDataFormatSetting> = ko.observable(
            new model.DateDataFormatSetting({
                formatSelection: FORMAT_SELECTION_ITEMS.YYYY_MM_DD,
                nullValueSubstitution: this.notUse,
                fixedValue: this.notUse,
                valueOfNullValueSubs: null,
                valueOfFixedValue: null
            }));
        selectModeScreen: KnockoutObservable<number> = ko.observable(dataformatSettingMode.INIT);
        nullValueReplacementItems: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.getNotUseAtr());
        fixedValueItems: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.getNotUseAtr());
        formatSetting: any;

        constructor() {
            let self = this;
            let parameter = getShared('CMF002_C_PARAMS');
            if (parameter) {
                self.formatSetting = parameter.formatSetting;
                self.selectModeScreen(parameter.screenMode);
            }
        }

        start(): JQueryPromise<any> {
            block.invisible();
            let self = this;
            let dfd = $.Deferred();

            if (self.selectModeScreen() == dataformatSettingMode.INDIVIDUAL && self.formatSetting) {
                self.dateDataFormatSetting(new model.DateDataFormatSetting(self.formatSetting));
                dfd.resolve();
                block.clear();
                return dfd.promise();
            }

            service.getDateFormatSetting().done(function(data: any) {
                if (data != null) {
                    self.dateDataFormatSetting(new model.DateDataFormatSetting(data));
                }
                dfd.resolve();
            }).fail(function(error) {
                alertError(error);
                dfd.reject();
            });

            block.clear();
            return dfd.promise();
        }

        //enable component when not using fixed value
        enable() {
            let self = this;
            return self.dateDataFormatSetting().fixedValue() == self.notUse;
        }

        //enable component replacement value editor
        enableReplacedValueEditor() {
            let self = this;
            return (self.enable() && self.dateDataFormatSetting().nullValueSubstitution() == self.use);
        }

        //enable component fixed value editor
        enableFixedValueEditor() {
            let self = this;
            return !self.enable();
        }

        selectDateDataFormatSetting() {
            let self = this;
            let dateDataFormatSettingSubmit = self.dateDataFormatSetting();
            if (dateDataFormatSettingSubmit.fixedValue() == this.use) {
                dateDataFormatSettingSubmit.formatSelection(FORMAT_SELECTION_ITEMS.YYYY_MM_DD);
                dateDataFormatSettingSubmit.nullValueSubstitution(this.notUse);
                dateDataFormatSettingSubmit.valueOfNullValueSubs(null);
            } else {
                dateDataFormatSettingSubmit.valueOfFixedValue(null);
            }

            if (dateDataFormatSettingSubmit.nullValueSubstitution() == this.notUse) {
                dateDataFormatSettingSubmit.valueOfNullValueSubs(null);
            }

            // Case initial
            if (self.selectModeScreen() == dataformatSettingMode.INIT) {
                service.addDateFormatSetting(dateDataFormatSettingSubmit).done(result => {
                    nts.uk.ui.windows.close();
                }).fail(function(error) {
                    alertError(error);
                });
                // Case individual
            } else {
                setShared('CMF002_C_PARAMS', { dateDataFormatSetting: dateDataFormatSettingSubmit });
                nts.uk.ui.windows.close();
            }
        }

        cancelSelectDateDataFormatSetting() {
            nts.uk.ui.windows.close();
        }

        getFormatSelectionItems(): Array<model.ItemModel> {
            return [
                //YYYY/MM/DD
                new model.ItemModel(FORMAT_SELECTION_ITEMS.YYYY_MM_DD, getText('CMF002_180')),
                //YYYYMMDD
                new model.ItemModel(FORMAT_SELECTION_ITEMS.YYYYMMDD, getText('CMF002_181')),
                //YY/MM/DD 
                new model.ItemModel(FORMAT_SELECTION_ITEMS.YY_MM_DD, getText('CMF002_182')),
                //YYMMDD
                new model.ItemModel(FORMAT_SELECTION_ITEMS.YYMMDD, getText('CMF002_183')),
                //JJYY/MM/DD
                new model.ItemModel(FORMAT_SELECTION_ITEMS.JJYY_MM_DD, getText('CMF002_184')),
                //JJYYMMDD
                new model.ItemModel(FORMAT_SELECTION_ITEMS.JJYYMMDD, getText('CMF002_185')),
                //曜日
                new model.ItemModel(FORMAT_SELECTION_ITEMS.DAY_OF_WEEK, getText('CMF002_186'))
            ];
        }

        ///////test, Xóa khi hoàn thành
        gotoScreenK_initial() {
            let self = this;
            nts.uk.ui.windows.sub.modal("/view/cmf/002/k/index.xhtml");
        };

        ///////test, Xóa khi hoàn thành
        gotoScreenK_individual() {
            let self = this;
            self.dateDataFormatSetting(new model.DateDataFormatSetting({
                formatSelection: FORMAT_SELECTION_ITEMS.YYYY_MM_DD,
                nullValueSubstitution: this.use,
                fixedValue: this.notUse,
                valueOfNullValueSubs: "123123",
                valueOfFixedValue: null
            }));
            setShared('CMF002_K_PARAMS', {
                selectModeScreen: dataformatSettingMode.INDIVIDUAL,
                dateDataFormatSetting: ko.toJS(self.dateDataFormatSetting())
            });
            nts.uk.ui.windows.sub.modal("/view/cmf/002/k/index.xhtml");
        };
    }

    export enum FORMAT_SELECTION_ITEMS {
        //YYYY/MM/DD
        YYYY_MM_DD = 0,
        //YYYYMMDD
        YYYYMMDD = 1,
        //YY/MM/DD 
        YY_MM_DD = 2,
        //YYMMDD
        YYMMDD = 3,
        //JJYY/MM/DD
        JJYY_MM_DD = 4,
        //JJYYMMDD
        JJYYMMDD = 5,
        //曜日
        DAY_OF_WEEK = 6
    }
}