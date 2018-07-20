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

    export class ScreenModel {
        atWorkDataOutputItem: KnockoutObservable<AtWorkDataOutputItem> = ko.observable(new AtWorkDataOutputItem({
            closedOutput: "",
            absenceOutput: "",
            fixedValue: null,
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
            let parrams = getShared('CMF002H_Params');
            self.modeScreen(parrams.mode);
            if (parrams != null) {
                if (parrams.mode) {
                    self.isEnable(false);
                }
                if (!parrams.mode) {
                    self.isEnable(true);
                }
            }
        }

        start(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            if (self.modeScreen() == 0) {
                service.getAWDataFormatSetting().done(result => {
                    if (result != null) {
                    self.atWorkDataOutputItem(new AtWorkDataOutputItem(result));
                    }
                }).fail(error => {

                });
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

        saveSetting() {
            let self = this;
            let command = ko.toJS(self.atWorkDataOutputItem());
            service.setAWDataFormatSetting(command).done(function() {
                nts.uk.ui.windows.close();
            }).fail(error => {

            });
        }
         enableFixedValue() {
            var self = this;
            return (self.atWorkDataOutputItem().fixedValue() == model.NOT_USE_ATR.USE);
        }
    }

    export interface IAtWorkDataOutputItem {
        closedOutput: string;
        absenceOutput: string;
        fixedValue: number;
        valueOfFixedValue: string;
        atWorkOutput: string;
        retirementOutput: string;
    }

    export class AtWorkDataOutputItem {
        closedOutput: KnockoutObservable<string>;
        absenceOutput: KnockoutObservable<string>;
        fixedValue: KnockoutObservable<number>;
        valueOfFixedValue: KnockoutObservable<string>;
        atWorkOutput: KnockoutObservable<string>;
        retirementOutput: KnockoutObservable<string>;

        constructor(params: IAtWorkDataOutputItem) {
            this.closedOutput = ko.observable(params.closedOutput);
            this.absenceOutput = ko.observable(params.absenceOutput);
            this.fixedValue = ko.observable(params.fixedValue);
            this.valueOfFixedValue = ko.observable(params.valueOfFixedValue);
            this.atWorkOutput = ko.observable(params.atWorkOutput);
            this.retirementOutput = ko.observable(params.retirementOutput);
        }
    }
}