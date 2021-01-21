module nts.uk.at.view.ksm004.f.viewmodel {
    import share = nts.uk.at.view.ksm004.share;
    const paths: any = {
        getSixMonthsCalendarCompany: "screen/at/ksm004/ksm004/f/sixmonthscalendarcompany/",
        getSixMonthsCalendarWorkPlace: "screen/at/ksm004/ksm004/f/sixmonthscalendarworkplace/",
        getSixMonthsCalendarClass: "screen/at/ksm004/ksm004/f/sixmonthscalendarclass/",
    };

    @bean()
    class ViewModel extends ko.ViewModel {

        baseDate: KnockoutObservable<Date> = ko.observable(new Date);
        months: KnockoutObservableArray<HoliDayCalendar> = ko.observableArray([]);

        created() {
            const vm = this;
            let param = nts.uk.ui.windows.getShared('KSM004_F_PARAM') || { classification: 0, yearMonth: 20170101, workPlaceId: null, classId: null };

            let yearParam: number = Number(param.yearMonth.toString().substring(0, 4));
            let monthParam: number = Number(param.yearMonth.toString().substring(4,6))-1;
            vm.baseDate = ko.observable(new Date(yearParam, monthParam, 1));

            vm.baseDate.subscribe(value => {
                let momentDate = moment(value);
                if (momentDate instanceof moment && !momentDate.isValid()) {
                    return;
                }
                this.$blockui("show");
                let startDate = moment(this.getDateRange(value)[0]).format("YYYY-MM-DD");
                let endDate = moment(this.getDateRange(value)[5]).add(30, "days").format("YYYY-MM-DD");
                if(param.classification == 1) {
                    let workPlaceId = param.workPlaceId;
                    vm.$ajax(paths.getSixMonthsCalendarWorkPlace+ workPlaceId + "/" + startDate + "/" + endDate).done(dataRes => {
                        this.setDataCalendar(value, dataRes)
                    });
                } else if(param.classification == 2){
                    let classId = param.classId;
                    vm.$ajax(paths.getSixMonthsCalendarClass+ classId+ "/" + startDate + "/" + endDate).done(dataRes => {
                        this.setDataCalendar(value, dataRes)
                    });
                } else {
                    vm.$ajax(paths.getSixMonthsCalendarCompany+ startDate + "/" + endDate).done(dataRes => {
                        this.setDataCalendar(value, dataRes)
                    });
                }
                this.$blockui("hide");
            })
        }

        mounted() {

        }

        closeDialog() {
            nts.uk.ui.windows.close();
        }

        setDataCalendar(date: Date, dataRes: any) {
            const vm = this;
            vm.months.removeAll();
            let holidayDateAll = _.map(_.filter(dataRes, (item: any) => { return item.workingDayAtr != 0; }), (item: any) => { return new Date(item.date) });
            let workingDateAll = _.map(_.filter(dataRes, (item: any) => { return item.workingDayAtr == 0; }), (item: any) => { return new Date(item.date) });
            let baseDates = vm.getDateRange(date);
            _.forEach(baseDates, (baseDate: Date) => {
                let holidays = _.filter(holidayDateAll, (item: Date) => { return item.getMonth() == baseDate.getMonth(); });
                let workingdays = _.filter(workingDateAll, (item: Date) => { return item.getMonth() == baseDate.getMonth(); });
                vm.months.push(
                    {
                        baseDate: ko.observable(baseDate),
                        holidays: ko.observableArray(holidays),
                        isEmptyMonth: ko.observable(_.isEmpty(workingdays))
                    });
            })
        }

        getDateRange(baseDate: Date) {
            // vm: ViewModel
            const baseMoment = moment(baseDate);
            // Trả ra danh sách baseDate của 6 tháng liên tiếp
            return _.range(0, 6).map((m: number) => baseMoment.clone().add(m, 'month').toDate());
        }
    }

    interface HoliDayCalendar  extends  share.CalendarParam{
    }
}