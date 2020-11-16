module nts.uk.ui.com.sample.fullcalendar {
    const F_DATE = 'YYYY-MM-DDTHH:mm:00';

    @bean()
    export class ViewModel extends ko.ViewModel {
        events: KnockoutObservableArray<any> = ko.observableArray([{
            title: 'Conference',
            start: moment().set('hour', 8).set('minute', 0).format(F_DATE),
            end: moment().set('hour', 9).set('minute', 30).format(F_DATE)
        },
        {
            groupId: 'abc',
            title: 'Meeting',
            start: moment().set('hour', 9).set('minute', 30).format(F_DATE),
            end: moment().set('hour', 10).set('minute', 30).format(F_DATE)
        },
        {
            groupId: 'abc',
            title: 'Lunch',
            start: moment().set('hour', 11).set('minute', 0).format(F_DATE),
            end: moment().set('hour', 12).set('minute', 0).format(F_DATE)
        }, {
            groupId: 'abc',
            start: moment().set('hour', 9).set('minute', 30).format(F_DATE),
            end: moment().set('hour', 12).set('minute', 0).format(F_DATE),
            display: 'background',
            backgroundColor: 'transparent'
        }]);

        businessHours: KnockoutObservableArray<any> = ko.observableArray([{
            // days of week. an array of zero-based day of week integers (0=Sunday)
            daysOfWeek: [1, 2, 3, 4, 5], // Monday - Thursday
            startTime: 60 * 8 + 30, // '08:30:00'
            endTime: 60 * 12 // '12:00:00'
        }, {
            // days of week. an array of zero-based day of week integers (0=Sunday)
            daysOfWeek: [1, 2, 3, 4, 5], // Monday - Thursday
            startTime: 60 * 13, // '13:00:00'
            endTime: 60 * 17 + 30 // '17:30:00'
        }, {
            daysOfWeek: [0, 6],
            startTime: 0,
            endTime: 0
        }]);

        weekends: KnockoutObservable<boolean> = ko.observable(true);
        firstDay: KnockoutObservable<number> = ko.observable(1);
        scrollTime: KnockoutObservable<number> = ko.observable(420);
        slotDuration: KnockoutObservable<number> = ko.observable(30);

        show: KnockoutObservable<boolean> = ko.observable(true);

        changeVisible() {
            const vm = this;

            vm.show(!vm.show());
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