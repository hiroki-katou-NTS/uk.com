module nts.uk.at.view.kaf011.b.viewmodel {

    import dialog = nts.uk.ui.dialog.info;
    import text = nts.uk.resource.getText;
    import formatDate = nts.uk.time.formatDate;
    import common = nts.uk.at.view.kaf011.shr.common;
    import service = nts.uk.at.view.kaf011.shr.service;

    export class ScreenModel extends kaf000.b.viewmodel.ScreenModel {

        screenModeNew: KnockoutObservable<boolean> = ko.observable(false);

        employeeName: KnockoutObservable<string> = ko.observable('');

        comment: KnockoutObservable<common.Comment> = ko.observable(new common.Comment(null));

        recWk: KnockoutObservable<common.WorkItems> = ko.observable(new common.WorkItems());

        absWk: KnockoutObservable<common.WorkItems> = ko.observable(new common.WorkItems());

        prePostTypes = ko.observableArray([
            { code: 0, text: text('KAF011_14') },
            { code: 1, text: text('KAF011_15') }]);

        prePostSelectedCode: KnockoutObservable<number> = ko.observable(0);

        appComItems = ko.observableArray([
            { code: 0, text: text('KAF011_19') },
            { code: 1, text: text('KAF011_20') },
            { code: 2, text: text('KAF011_21') },
        ]);

        appComSelectedCode: KnockoutObservable<number> = ko.observable(0);

        appReasons = ko.observableArray([]);

        appReasonSelectedID: KnockoutObservable<string> = ko.observable('');

        reason: KnockoutObservable<string> = ko.observable('');

        showReason: KnockoutObservable<boolean> = ko.observable(false);

        update() {

        }

        constructor(listAppMetadata: Array<model.ApplicationMetadata>, currentApp: model.ApplicationMetadata) {
            super(listAppMetadata, currentApp);
            let self = this;

            self.startPage(self.appID());
        }

        startPage(appID: string): JQueryPromise<any> {
            var self = this,
                dfd = $.Deferred(),
                appParam = { appID: appID };
            service.findById(appParam).done((data) => {
                self.setDataFromStart(data);

            }).fail((error) => {
                dialog({ messageId: error.messageId });
            }).always(() => {
                dfd.resolve();

            });



            return dfd.promise();

        }
        setDataFromStart(data) {
            let self = this;
            if (data) {


                self.showReason(data.applicationSetting.appReasonDispAtr);

                self.comment(data.drawalReqSet || null);

                self.employeeName(data.employeeName);
                if (data.application) {
                    self.setDataCommon(data);
                }

                if (data.absApp && data.recApp) {
                    self.setDataBothApp(data);

                } else {
                    if (data.recApp) {
                        self.setDataApp(self.recWk(), data.recApp, 1);

                    }
                    if (data.absApp) {
                        self.setDataApp(self.absWk(), data.absApp, 2);

                    }

                }
            }
        }

        setDataCommon(data) {
            let self = this,
                app = data.application;

            self.prePostSelectedCode(app.prePostAtr);

        }

        setDataBothApp(data) {
            let self = this;
            self.appComSelectedCode(0);
            self.setDataApp(self.absWk(), data.absApp);
            self.setDataApp(self.recWk(), data.recApp);
        }




        setDataApp(control: common.WorkItems, data, comType?) {
            let self = this;
            control.wkTypeCD(data.workTypeCD);
            control.wkTimeCD(data.workTimeCD);
            control.changeWorkHoursType(data.changeWorkHoursType);
            control.workLocationCD(data.workLocationCD);
            control.appDate(data.appDate);
            if (data.wkTime1) {
                control.wkTime1().startTime(data.wkTime1.startTime);
                control.wkTime1().endTime(data.wkTime1.endTime);
                if (data.wkTime1.startType) {
                    control.wkTime1().startType(data.wkTime1.startType);
                    control.wkTime1().startTime(data.wkTime1.endType);
                }
            }
            if (comType) {
                self.appComSelectedCode(comType);
            }
        }
    }


}