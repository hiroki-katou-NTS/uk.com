module nts.uk.at.view.ksm004.f.viewmodel {
    @bean()
    class ViewModel extends ko.ViewModel {

        yearMonth: KnockoutObservable<number> = ko.observable(202007);
        yearMonthList: KnockoutObservableArray<any> = ko.observableArray([]);
        months: KnockoutObservableArray<HoliDayCalendar> = ko.observableArray([]);

        created() {
            const vm = this;
            // let param = nts.uk.ui.windows.getShared('KSM004_F_PARAM') || { classification: 0, yearMonth: 20170101, workPlaceId: null, classCD: null };
            this.generateYearMonth(vm.yearMonth).forEach(item => {
                let year = item().toString().substring(0, 4);
                let month = item().toString().substring(4, 6);
                let fulltime = `${year}/${month}/01`
                vm.months.push(
                    {
                        baseDate: ko.observable(new Date(fulltime)),
                        holidays: ko.observableArray([])
                    }
                )
            });
        }

        mounted() {
            let vm = this;
            vm.yearMonth.subscribe(function(newValue) {
                vm.months([]);
                this.generateYearMonth(ko.observable(newValue)).forEach(item => {
                    let year = item().toString().substring(0, 4);
                    let month = item().toString().substring(4, 6);
                    let fulltime = `${year}/${month}/01`;
                    vm.months.push(
                        {
                            baseDate: ko.observable(new Date(fulltime)),
                            holidays: ko.observableArray([])
                        }
                    )
                });
            });
        }

        closeDialog() {
            nts.uk.ui.windows.close();
        }

        generateYearMonth(yearMonth) {
            const self = this;
            console.log(yearMonth());
            let year = yearMonth().toString().substring(0, 4);
            let month = yearMonth().toString().substring(4, 6);
            for(let i = 0; i < 6; i++) {
             if(Number(month) === 12) {
                 self.yearMonthList.push(ko.observable(Number(year+month)));
                 month = '1';
                 year = (Number(year)+1).toString();
             } else if(Number(month) === 11 || Number(month) === 10){
                 self.yearMonthList.push(ko.observable(Number(year+month)));
                 month = (Number(month)+1).toString();
             } else {
                 self.yearMonthList.push(ko.observable(Number(year+month)));
                 if (Number(month) === 9) {
                     month = '10';
                 } else {
                     month = (Number(month)+1).toString();
                     month = '0' + month;
                 }
             }
            }
            self.yearMonthList().forEach(item => console.log(item()));
            return self.yearMonthList();
        }

    }

    interface ICalendarPanel{
        optionDates: KnockoutObservableArray<any>;
        yearMonth: KnockoutObservable<number>;
        firstDay: number;
        startDate: number;
        endDate: number;
        workplaceId: KnockoutObservable<string>;
        workplaceName: KnockoutObservable<string>;
        eventDisplay: KnockoutObservable<boolean>;
        eventUpdatable: KnockoutObservable<boolean>;
        holidayDisplay: KnockoutObservable<boolean>;
        cellButtonDisplay: KnockoutObservable<boolean>;
    }
    import share = nts.uk.at.view.kdp.share;

    interface HoliDayCalendar  extends  share.CalendarParam{
    }
}