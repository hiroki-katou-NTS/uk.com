module nts.uk.com.view.cmf002.n.viewmodel {
    import model = nts.uk.com.view.cmf002.share.model;
    import block = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    import confirm = nts.uk.ui.dialog.confirm;
    import alertError = nts.uk.ui.dialog.alertError;
    import info = nts.uk.ui.dialog.info;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import dialog = nts.uk.ui.dialog;
    import error = nts.uk.ui.errors;

    export class ScreenModel {
        atWorkDataOutputItem: KnockoutObservable<model.AtWorkDataOutputItem> = ko.observable(new model.AtWorkDataOutputItem({
            closedOutput: "",
            absenceOutput: "",
            fixedValue: 0,
            valueOfFixedValue: "",
            atWorkOutput: "",
            retirementOutput: ""
        }));
        isUse: KnockoutObservable<boolean>;
        items: KnockoutObservableArray<model.ItemModel> = ko.observableArray([
            new model.ItemModel(model.NOT_USE_ATR.USE, getText('CMF002_149')),
            new model.ItemModel(model.NOT_USE_ATR.NOT_USE, getText('CMF002_150'))
        ]);
        modeScreen: KnockoutObservable<number> = ko.observable(0);
        isEnable: KnockoutObservable<boolean> = ko.observable(false);

        constructor() {
            var self = this;
        }

        start(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            let params = getShared('CMF002_N_PARAMS');
            self.modeScreen(params.screenMode);
            if (self.modeScreen() == model.DATA_FORMAT_SETTING_SCREEN_MODE.INDIVIDUAL && params.formatSetting) {
                // get data shared
                self.atWorkDataOutputItem(new model.AtWorkDataOutputItem(params.formatSetting));
            } else {
                service.getAWDataFormatSetting().done(result => {
                    if (result != null) {
                        self.atWorkDataOutputItem(new model.AtWorkDataOutputItem(result));
                    }
                })
            }
            dfd.resolve();
            return dfd.promise();
        }

        /**
          * Close dialog.
          */
        cancelSetting(): void {
            nts.uk.ui.windows.close();
        }

        enableCloseOutput() {
            var self = this;
            return (self.atWorkDataOutputItem().fixedValue() == model.NOT_USE_ATR.NOT_USE)
        }
        
        enableRegister(){
            return error.hasError();
        }
        
        retirementOutput() {
            var self = this;
            return (self.atWorkDataOutputItem().fixedValue() == model.NOT_USE_ATR.NOT_USE)
        }
        enableAbsenceOutput() {
            var self = this;
            return (self.atWorkDataOutputItem().fixedValue() == model.NOT_USE_ATR.NOT_USE)
        }
        enableAtWorkOutput() {
            var self = this;
            return (self.atWorkDataOutputItem().fixedValue() == model.NOT_USE_ATR.NOT_USE)
        }

        saveSetting() {
            let self = this;
            let command = ko.toJS(self.atWorkDataOutputItem());
            if (self.modeScreen() != model.DATA_FORMAT_SETTING_SCREEN_MODE.INDIVIDUAL) {
                // get data shared
                service.setAWDataFormatSetting(command).done(function() {
                    dialog.info({ messageId: "Msg_15" }).then(() => {
                        nts.uk.ui.windows.close();
                    });
                })
            } else {
                setShared('CMF002_C_PARAMS', { formatSetting: command });
                nts.uk.ui.windows.close();
            }
        }
        enableFixedValue() {
            var self = this;
            return (self.atWorkDataOutputItem().fixedValue() == model.NOT_USE_ATR.USE);
        }
    }
}