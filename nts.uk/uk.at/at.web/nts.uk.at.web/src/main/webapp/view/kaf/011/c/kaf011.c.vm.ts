module nts.uk.at.view.kaf011.c.screenModel {

    import dialog = nts.uk.ui.dialog.info;
    import text = nts.uk.resource.getText;
    import formatDate = nts.uk.time.formatDate;
    import common = nts.uk.at.view.kaf011.shr.common;
    import service = nts.uk.at.view.kaf011.shr.service;
    import block = nts.uk.ui.block;

    export class ViewModel {
        prePostTypes = ko.observableArray([
            { code: 0, text: text('KAF011_14') },
            { code: 1, text: text('KAF011_15') }]);

        prePostSelectedCode: KnockoutObservable<number> = ko.observable(0);

        reason: KnockoutObservable<string> = ko.observable('');

        appReasons = ko.observableArray([]);

        appReasonSelectedID: KnockoutObservable<string> = ko.observable('');

        appDate: KnockoutObservable<Date> = ko.observable(moment().toDate());

        kaf000_a = new kaf000.a.viewmodel.ScreenModel();

        drawalReqSet: KnockoutObservable<common.DrawalReqSet> = ko.observable(new common.DrawalReqSet(null));

        showReason: KnockoutObservable<number> = ko.observable(0);

        displayPrePostFlg: KnockoutObservable<number> = ko.observable(0);

        appTypeSet: KnockoutObservable<common.AppTypeSet> = ko.observable(new common.AppTypeSet(null));

        constructor() {
            let self = this;
            let param = nts.uk.ui.windows.getShared('KAF_011_PARAMS');
            if (param) {
                self.prePostSelectedCode(param.prePostSelectedCode);
                self.reason(param.reason);
                self.appReasonSelectedID(param.appReasonSelectedID);
                self.appDate(param.appDate);
            }
        }

        start(): JQueryPromise<any> {
            block.invisible();
            var self = this,
                dfd = $.Deferred(),
                startParam = {
                    sID: null,
                    appDate: self.appDate(),
                    uiType: 0
                };

            service.start_c(startParam).done((data: common.IHolidayShipment) => {
                self.setDataFromStart(data);
            }).fail((error) => {
                dialog({ messageId: error.messageId });
            }).always(() => {
                block.clear();
                dfd.resolve();

            });
            return dfd.promise();
        }

        setDataFromStart(data: common.IHolidayShipment) {
            let self = this;
            if (data) {
                self.prePostSelectedCode(data.preOrPostType);
                self.appReasons(data.appReasons || []);
                self.drawalReqSet(new common.DrawalReqSet(data.drawalReqSet || null));
                self.showReason(data.applicationSetting.appReasonDispAtr);
                self.displayPrePostFlg(data.applicationSetting.displayPrePostFlg);
                self.appTypeSet(new common.AppTypeSet(data.appTypeSet || null));
            }
        }

        closeDialog() {
            nts.uk.ui.windows.close();
        }

    }
}