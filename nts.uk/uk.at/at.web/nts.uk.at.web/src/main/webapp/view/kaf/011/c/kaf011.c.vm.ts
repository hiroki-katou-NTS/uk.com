module nts.uk.at.view.kaf011.c.screenModel {

    import dialog = nts.uk.ui.dialog.info;
    import text = nts.uk.resource.getText;
    import formatDate = nts.uk.time.formatDate;
    import common = nts.uk.at.view.kaf011.shr.common;
    import service = nts.uk.at.view.kaf011.shr.service;
    import block = nts.uk.ui.block;
    import alError = nts.uk.ui.dialog.alertError;

    export class ViewModel {
        prePostTypes = ko.observableArray([
            { code: 0, text: text('KAF011_14') },
            { code: 1, text: text('KAF011_15') }]);

        prePostSelectedCode: KnockoutObservable<number> = ko.observable(0);

        reason: KnockoutObservable<string> = ko.observable('');

        appReasonComboItems = ko.observableArray([]);

        appReasonSelectedID: KnockoutObservable<string> = ko.observable('');

        appDate: KnockoutObservable<Date> = ko.observable(moment().toDate());

        kaf000_a = new kaf000.a.viewmodel.ScreenModel();

        drawalReqSet: KnockoutObservable<common.DrawalReqSet> = ko.observable(new common.DrawalReqSet(null));

        showReason: KnockoutObservable<number> = ko.observable(0);

        displayPrePostFlg: KnockoutObservable<number> = ko.observable(0);

        appTypeSet: KnockoutObservable<common.AppTypeSet> = ko.observable(new common.AppTypeSet(null));

        appComItems: KnockoutObservable<any> = ko.observableArray([]);

        screenModeNew: KnockoutObservable<boolean> = ko.observable(true);

        absWk: KnockoutObservable<common.AppItems> = ko.observable(new common.AppItems());

        recWk: KnockoutObservable<common.AppItems> = ko.observable(new common.AppItems());

        appComSelectedCode: KnockoutObservable<number> = ko.observable(2);

        version: KnockoutObservable<number> = ko.observable(0);

        employeeID: KnockoutObservable<string> = ko.observable('');



        constructor() {
            let self = this;
            let param = nts.uk.ui.windows.getShared('KAF_011_PARAMS');
            if (param) {
                self.prePostSelectedCode(param.prePostSelectedCode);
                self.reason(param.reason);
                self.appReasonSelectedID(param.appReasonSelectedID);
                self.absWk(new common.AppItems(param.absApp));
                self.version(param.version);
            }
        }

        start(): JQueryPromise<any> {
            block.invisible();
            var self = this,
                dfd = $.Deferred(),
                startParam = {
                    sID: null,
                    appDate: self.appDate(),
                    uiType: 10
                };

            service.start_c(startParam).done((data: common.IHolidayShipment) => {
                self.setDataFromStart(data);
            }).fail((error) => {
               alError({ messageId: error.messageId, messageParams: error.parameterIds });
            }).always(() => {
                block.clear();
                dfd.resolve();

            });
            return dfd.promise();
        }

        saveAbs() {
            let self = this,
                saveCmd: common.ISaveHolidayShipmentCommand = {
                    recCmd: ko.mapping.toJS(self.recWk()),
                    absCmd: ko.mapping.toJS(self.absWk()),
                    comType: self.appComSelectedCode(),
                    usedDays: 1,
                    appCmd: {
                        appReasonText: self.appReasonSelectedID(),
                        applicationReason: self.reason(),
                        prePostAtr: self.prePostSelectedCode(),
                        enteredPersonSID: self.employeeID(),
                        appVersion: self.version(),
                    }
                };

            saveCmd.absCmd.changeWorkHoursType = saveCmd.absCmd.changeWorkHoursType ? 1 : 0;;
            block.invisible();
            service.changeAbsDate(saveCmd).done((data) => {
                dialog({ messageId: 'Msg_15' }).then(function() {
                    nts.uk.ui.windows.setShared('KAF_011_C_PARAMS', data);
                    nts.uk.ui.windows.close();
                });
            }).fail((error) => {
                alError({ messageId: error.messageId, messageParams: error.parameterIds });

            }).always(() => {
                block.clear();
            });

        }

        setDataFromStart(data: common.IHolidayShipment) {
            let self = this;
            if (data) {
                self.prePostSelectedCode(data.preOrPostType);
                self.appReasonComboItems(data.appReasonComboItems || []);
                self.drawalReqSet(new common.DrawalReqSet(data.drawalReqSet || null));
                self.showReason(data.applicationSetting.appReasonDispAtr);
                self.displayPrePostFlg(data.applicationSetting.displayPrePostFlg);
                self.appTypeSet(new common.AppTypeSet(data.appTypeSet || null));
                self.employeeID(data.employeeID || '');
            }
        }

        closeDialog() {
            nts.uk.ui.windows.close();
        }

    }
}