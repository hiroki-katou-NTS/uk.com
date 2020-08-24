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
                if(value) {
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
                }
            })
        }

        mounted() {

        }

        closeDialog() {
            nts.uk.ui.windows.close();
        }

        setDataCalendar(date: Date, dataRes: any) {
            const vm = this;
            const dayOffArr: KnockoutObservableArray<Date> = ko.observableArray([]);
            vm.months.removeAll();
            dataRes.forEach(item => dayOffArr.push(new Date(item.date)));
            this.getDateRange(date).forEach(item => vm.months.push(
                {
                    baseDate: ko.observable(item),
                    holidays: dayOffArr
                }
            ));
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