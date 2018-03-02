module nts.uk.at.view.kaf011.a.viewmodel {

    import dialog = nts.uk.ui.dialog;
    import text = nts.uk.resource.getText;
    export class ViewModel {

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

        kaf000_a = new kaf000.a.viewmodel.ScreenModel();

        start() {

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
}