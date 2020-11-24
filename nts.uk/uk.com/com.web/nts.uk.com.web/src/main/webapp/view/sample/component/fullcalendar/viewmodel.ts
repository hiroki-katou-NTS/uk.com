module nts.uk.ui.com.sample.fullcalendar {
    const F_DATE = 'YYYY-MM-DDTHH:mm:00';

    @bean()
    export class ViewModel extends ko.ViewModel {
        events: KnockoutObservableArray<any> = ko.observableArray([{
            id: '000001',
            title: 'Conference',
            start: moment().set('hour', 8).set('minute', 30).toDate(),
            end: moment().set('hour', 9).set('minute', 30).toDate()
        }, {
            id: '000002',
            title: 'Meeting',
            start: moment().set('hour', 9).set('minute', 30).toDate(),
            end: moment().set('hour', 14).set('minute', 0).toDate()
        }]);

        dragItems: KnockoutObservableArray<any> = ko.observableArray([{
            id: '000003',
            title: 'UK就業・詳細設計',
            backgroundColor: '#ffc000'
        }, {
            id: '000004',
            title: 'UK就業機能強化・テスト設計',
            backgroundColor: '#ff5050'
        }, {
            id: '000005',
            title: 'UKプロジェクト・概要設計',
            backgroundColor: '#5b9bd5'
        }, {
            id: '000006',
            title: 'UKプロジェクト・統合テスト',
            backgroundColor: '#92d050'
        }]);

        employees: KnockoutObservableArray<any> = ko.observableArray([{
            id: '000001',
            code: '000001',
            name: '日通　太郎',
            selected: true
        }, {
            id: '000002',
            code: '000002',
            name: '日通　一郎',
            selected: false
        }, {
            id: '000003',
            code: '000003',
            name: '鈴木　太郎',
            selected: false
        }, {
            id: '000004',
            code: '000004',
            name: '加藤　良太郎',
            selected: false
        }, {
            id: '000005',
            code: '000005',
            name: '佐藤　花子',
            selected: false
        }, {
            id: '000006',
            code: '000006',
            name: '佐藤　龍馬',
            selected: false
        }, {
            id: '000007',
            code: '000007',
            name: '日通　二郎',
            selected: false
        }]);

        breakTime: KnockoutObservable<any> = ko.observable({
            startTime: 60 * 12,
            endTime: 60 * 13
        });

        businessHours: KnockoutObservableArray<any> = ko.observableArray([{
            // days of week. an array of zero-based day of week integers (0=Sunday)
            daysOfWeek: [1, 2, 3, 4, 5], // Monday - Thursday
            startTime: 60 * 8 + 30, // '08:30:00'
            endTime: 60 * 17 + 30 // '17:30:00'
        }, {
            daysOfWeek: [0, 6],
            startTime: 0,
            endTime: 0
        }]);

        weekends: KnockoutObservable<boolean> = ko.observable(true);
        editable: KnockoutObservable<boolean> = ko.observable(true);
        firstDay: KnockoutObservable<number> = ko.observable(1);
        scrollTime: KnockoutObservable<number> = ko.observable(420);
        slotDuration: KnockoutObservable<number> = ko.observable(30);
        initialDate: KnockoutObservable<Date> = ko.observable(new Date());
        initialView: KnockoutObservable<string> = ko.observable('fullWeek');
        availableView: KnockoutObservableArray<string> = ko.observableArray(['oneDay', 'fullWeek'])

        attendanceTimes: KnockoutObservableArray<any> = ko.observableArray([{
            date: moment().toDate(),
            events: [
                '勤怠　8:00 ~ 17:00',
                '休憩　1:00',
                '総労働時間　8:00'
            ]
        }, {
            date: moment().add(2, 'day').toDate(),
            events: [
                '勤怠　8:00 ~ 17:00',
                '休憩　1:00',
                '総労働時間　8:00'
            ]
        }, {
            date: moment().add(3, 'day').toDate(),
            events: [
                '勤怠　8:00 ~ 17:00',
                '休憩　1:00',
                '総労働時間　8:00'
            ]
        }]);

        show: KnockoutObservable<boolean> = ko.observable(true);

        changeVisible() {
            const vm = this;

            vm.show(!vm.show());
        }

        coppyDay(from: Date, to: Date) {
            console.log(from, to);
        }

        datesSet(start: Date, end: Date) {
            console.log(start, end);
        }

        mounted() {
            const vm = this;

            _.extend(window, { vm });
        }
    }
}