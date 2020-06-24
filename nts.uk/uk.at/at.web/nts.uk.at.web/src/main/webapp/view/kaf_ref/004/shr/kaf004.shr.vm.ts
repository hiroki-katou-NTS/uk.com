module nts.uk.at.view.kaf004_ref.shr.viewmodel {

    @component ({
        name: 'kaf004_share',
        template: '/nts.uk.at.web/view/kaf_ref/004/shr/index.html'
    })

    class Kaf004ShareViewModel extends ko.ViewModel {

        created(params: any) {
            const vm = this;
        }

        mounted() {

        }
    }
    export class WorkManagement {
        // 予定出勤時刻ラベル
        // A6_3
        scheAttendanceTime: KnockoutObservable<String>;

        // 予定退勤時刻ラベル
        // A6_9
        scheWorkTime: KnockoutObservable<String>;

        // 予定出勤時刻２ラベル
        // A6_15
        scheAttendanceTime2: KnockoutObservable<String>;

        // 予定退勤時刻２ラベル
        // A6_21
        scheWorkTime2: KnockoutObservable<String>;

        // 出勤時刻
        // A6_4
        workTime: KnockoutObservable<String>;

        // 退勤時刻
        // A6_10
        leaveTime: KnockoutObservable<String>;

        // 出勤時刻２
        // A6_16
        workTime2: KnockoutObservable<String>;

        // 退勤時刻２
        // A6_22
        leaveTime2: KnockoutObservable<String>;

        constructor(scheAttendanceTime: string, scheWorkTime: string, scheAttendanceTime2: string, scheWorkTime2: string,
            workTime: string, leaveTime: string, workTime2: string, leaveTime2: string) {
                const self = this;

                self.scheAttendanceTime = ko.observable(scheAttendanceTime);
                self.scheWorkTime = ko.observable(scheWorkTime);
                self.scheAttendanceTime2 = ko.observable(scheAttendanceTime2);
                self.scheWorkTime2 = ko.observable(scheWorkTime2);

                self.workTime = ko.observable(workTime);
                self.leaveTime = ko.observable(leaveTime);
                self.workTime2 = ko.observable(workTime2);
                self.leaveTime2 = ko.observable(leaveTime2);
        }
    }
}