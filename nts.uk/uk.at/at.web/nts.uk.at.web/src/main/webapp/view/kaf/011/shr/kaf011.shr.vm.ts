module nts.uk.at.view.kaf011.shr.screenModel {

    import dialog = nts.uk.ui.dialog;
    import text = nts.uk.resource.getText;
    import formatDate = nts.uk.time.formatDate;
    export class ViewModel {

        constructor() {

        }

        prePostTypes = ko.observableArray([
            { code: 1, text: text('KAF011_14') },
            { code: 2, text: text('KAF011_15') }]);

        prePostSelectedCode: KnockoutObservable<number> = ko.observable(1);

        appComItems = ko.observableArray([
            { code: 1, text: text('KAF011_19') },
            { code: 2, text: text('KAF011_20') },
            { code: 3, text: text('KAF011_21') },
        ]);
        appComSelectedCode: KnockoutObservable<number> = ko.observable(1);

        appDate: KnockoutObservable<Date> = ko.observable(moment().toDate());

        dutyTypes = ko.observableArray([]);

        dutySelectedType: KnockoutObservable<number> = ko.observable(0);

        workingHour: KnockoutObservable<WorkingHour> = ko.observable(new WorkingHour());

        stereoTypes = ko.observableArray([]);

        stereoSelectedType: KnockoutObservable<number> = ko.observable(0);

        reason: KnockoutObservable<string> = ko.observable('');
        
        DATE_FORMAT = "yyyy/MM/dd";

        kaf000_a = new kaf000.a.viewmodel.ScreenModel();

        start(): JQueryPromise<any> {
            var self = this,
                dfd = $.Deferred(),
                startParam = {
                    sID: null,
                    appDate: formatDate(self.appDate(), self.DATE_FORMAT).format(),
                    uiType: 1
                };

            service.start(startParam).done((data: any) => {



            }).always(() => {
                dfd.resolve();

            });
            return dfd.promise();
        }

        register() {
            let self = this;
            dialog.alertError({ messageId: "register" });
        }

        openKDL003() {

            let self = this;
            dialog.alertError({ messageId: "openKDL003" });
        }

        genWorkingText() {
            let self = this;
            return '001' + ' ' + 'KDL' + ' ' + '08:30' + '~' + '09:30';
        }




    }

    interface IWorkingHour {
        startTime: Date;
        endTime: Date;
        startType: number;
        endType: number;
    }
    export class WorkingHour {
        startTime: KnockoutObservable<number> = ko.observable(100);
        endTime: KnockoutObservable<number> = ko.observable(100);
        startTypes: KnockoutObservableArray<any> = ko.observableArray([
            { code: 1, text: text('KAF011_39') },
            { code: 2, text: text('KAF011_40') }
        ]);
        startType: KnockoutObservable<number> = ko.observable(1);
        endTypes: KnockoutObservableArray<any> = ko.observableArray([
            { code: 1, text: text('KAF011_42') },
            { code: 2, text: text('KAF011_43') }
        ]);
        endType: KnockoutObservable<number> = ko.observable(1);

        timeOption = ko.mapping.fromJS({
            width: "130px"
        });
        constructor(IWorkingHour?) {
            this.startTime = ko.observable(IWorkingHour ? IWorkingHour.startTime || null : 100);
            this.endTime = ko.observable(IWorkingHour ? IWorkingHour.endTime || null : 100);
            this.startType = ko.observable(IWorkingHour ? IWorkingHour.startType || 1 : 1);
            this.endType = ko.observable(IWorkingHour ? IWorkingHour.endType || 1 : 1);

        }
    }

    interface IApprovalSetting {
        bottomComment: boolean;
        bottomCommentFontColor: string;
        bottomCommentFontWeight: boolean;
        companyID: string;
        resultDisp: number;
        stampAtr_Care_Disp: number;
        stampAtr_Child_Care_Disp: number;
        stampAtr_GoOut_Disp: number;
        stampAtr_Sup_Disp: number;
        stampAtr_Work_Disp: number;
        stampGoOutAtr_Compensation_Disp: number;
        stampGoOutAtr_Private_Disp: number;
        stampGoOutAtr_Public_Disp: number;
        stampGoOutAtr_Union_Disp: number;
        stampPlaceDisp: number;
        supFrameDispNO: number;
        topComment: string;
        topCommentFontColor: string;
        topCommentFontWeight: boolean;
    }
}