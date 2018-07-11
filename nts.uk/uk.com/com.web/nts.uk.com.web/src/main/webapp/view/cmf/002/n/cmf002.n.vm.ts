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
        AtWorkDataOutputItem: KnockoutObservable<model.AtWorkDataOutputItem>;
        isUse: KnockoutObservable<boolean>;
        items: KnockoutObservableArray<model.ItemModel> = ko.observableArray([
            new model.ItemModel(model.NOT_USE_ATR.USE, getText('CMF002_149')),
            new model.ItemModel(model.NOT_USE_ATR.NOT_USE, getText('CMF002_150'))
        ]);
        modeScreen: KnockoutObservable<number>;
        isEnable: KnockoutObservable<boolean>;

        constructor() {
            var self = this;
            self.initComponent();
        }
        initComponent() {
            var self = this;
            let parrams = getShared('CMF002CParams');
            self.modeScreen(parrams.modeScreen);
            service.getAWDataFormatSetting().done(function(data: Array<any>) {
                if (data && data.length) {
                    let _rsList: Array<model.AtWorkDataOutputItem> = _.map(data, rs => {
                        return new model.AtWorkDataOutputItem(rs.closedOutput, rs.absenceOutput, rs.fixedValue, rs.valueOfFixedValue, rs.atWorkOutput, rs.retirementOutput);
                    });
                    self.AtWorkDataOutputItem(_rsList);
                } else {
                    self.AtWorkDataOutputItem = ko.observable(new model.AtWorkDataOutputItem("", "", 2, "", "", ""));
                }
            }
            if (parrams.modeScreen) {
                self.isEnable(false);
            }
            if (!parrams.modeScreen) {
                self.isEnable(true);
            }
        }

        /**
          * Close dialog.
          */
        cancelSetting(): void {
            nts.uk.ui.windows.close();
        }

        saveSetting() {
            let self = this;
            let command = ko.toJS(self.AtWorkDataOutputItem());
            service.setAWDataFormatSetting(command).done(function() {
                nts.uk.ui.windows.close();
            });
        }).fail(error => {
            alertError({ messageId: "Msg" });
        });
    }
}