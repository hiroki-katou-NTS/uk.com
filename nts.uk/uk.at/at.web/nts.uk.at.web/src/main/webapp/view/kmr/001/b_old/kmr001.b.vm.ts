/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.kmr001.b {

    const API = {
        GET_BENTO_RESERVATION: 'screen/at/record/reservation/bento-menu/getbentomenu',
        ADD_BENTO_RESERVATION: 'bento/bentomenusetting/add'
    };

    @bean()
    class ViewModel extends ko.ViewModel {
        enable: KnockoutObservable<boolean>;
        itemsReservationChange: KnockoutObservableArray<ReservationChange> = ko.observableArray([]);
        itemsReservationChangeDay: KnockoutObservableArray<TimePeriod> = ko.observableArray([]);
        selectedCode: KnockoutObservable<string>;
        isEnable: KnockoutObservable<boolean>;
        isEditable: KnockoutObservable<boolean>;
        model: KnockoutObservable<Reservation> = ko.observable(new Reservation());
        visibleContentChangeDeadline: KnockoutObservable<boolean> = ko.observable(false);

        constructor() {
            super();
            const vm = this;

            //combo box B10_2
            vm.itemsReservationChange([
                { appId: 0, appName: vm.$i18n('KMR001_85') },
                { appId: 1, appName: vm.$i18n('KMR001_86') },
                { appId: 2, appName: vm.$i18n('KMR001_87') }
            ]);

            //combo box B10_3
            let items = [];
            _.range(0, 31).map(item => items.push({
                appId: item,
                appValue: (item + 1).toString()
            }));
            vm.itemsReservationChangeDay(items);

            vm.enable = ko.observable(true);
        }

        created() {
            const vm = this;

            vm.model().contentChangeDeadline.subscribe(data => {
                if (data == 1) {
                    vm.visibleContentChangeDeadline(true);
                    return;
                }
                vm.visibleContentChangeDeadline(false);
            });

            vm.model().reservationEndTime1.subscribe(() => {
                vm.$errors("clear", "#end1");
            });
            vm.model().reservationEndTime2.subscribe(() => {
                vm.$errors("clear", "#end2");
            });

            _.extend(window, { vm });
        }

        mounted() {
            const vm = this;
            vm.$blockui("invisible");
            vm.$ajax(API.GET_BENTO_RESERVATION).done((data: Reservation) => {
                if (data) {
                    vm.model().updateData(data);
                    if (Number(data.contentChangeDeadline) == 1) {
                        vm.visibleContentChangeDeadline(true);
                    }
                }

            }).always(() => this.$blockui("clear"));
        }

        registerBentoReserveSetting() {
            const vm = this;
            vm.$validate(".nts-input").then((valid: boolean) => {
                if (!valid) {
                    return;
                }
                if (vm.model().reservationStartTime1() >= vm.model().reservationEndTime1()) {
                    vm.$errors("#end1", "Msg_849");
                    return;
                }
                if (vm.model().reservationStartTime2() != null && vm.model().reservationEndTime2() != null && vm.model().reservationStartTime2() >= vm.model().reservationEndTime2()) {
                    vm.$errors("#end2", "Msg_849");
                    return;
                }

                vm.$blockui("invisible");
                const dataRegister = {
                    operationDistinction: vm.model().operationDistinction(),
                    referenceTime: vm.model().referenceTime(),
                    dailyResults: vm.model().dailyResults(),
                    monthlyResults: vm.model().monthlyResults(),
                    contentChangeDeadline: vm.model().contentChangeDeadline(),
                    contentChangeDeadlineDay: vm.model().contentChangeDeadlineDay(),
                    orderedData: vm.model().orderedData(),
                    orderDeadline: vm.model().orderDeadline(),
                    name1: vm.model().reservationFrameName1(),
                    end1: vm.model().reservationEndTime1(),
                    start1: vm.model().reservationStartTime1(),
                    name2: vm.model().reservationFrameName2(),
                    end2: vm.model().reservationEndTime2(),
                    start2: vm.model().reservationStartTime2()
                };
                vm.$ajax(API.ADD_BENTO_RESERVATION, dataRegister).done(() => {
                    vm.$dialog.info({ messageId: "Msg_15" }).then(function () {
                        vm.$blockui("clear");
                    });
                }).always(() => this.$blockui("clear"));
            })
        }

    }

    interface ReservationChange {
        appId: number;
        appName: string;
    }

    interface TimePeriod {
        appId: number;
        appValue: string;
    }

    // class for kmr001 b
    class Reservation {
        //B3_2
        operationDistinction: KnockoutObservable<number> = ko.observable(0);
        //B5_2
        referenceTime: KnockoutObservable<number> = ko.observable(0);
        //B10_2
        contentChangeDeadline: KnockoutObservable<number> = ko.observable(0);
        //B10_3
        contentChangeDeadlineDay: KnockoutObservable<number> = ko.observable(0);
        //B14_2
        orderDeadline: KnockoutObservable<number> = ko.observable(0);
        //B17_2
        monthlyResults: KnockoutObservable<number> = ko.observable(0);
        //B18_2
        dailyResults: KnockoutObservable<number> = ko.observable(0);
        //B19_3
        reservationFrameName1: KnockoutObservable<string> = ko.observable('');
        //B19_5
        reservationStartTime1: KnockoutObservable<number> = ko.observable(0);
        //B19_7
        reservationEndTime1: KnockoutObservable<number> = ko.observable(0);
        //B20_3
        reservationFrameName2: KnockoutObservable<string> = ko.observable('');
        //B20_5
        reservationStartTime2: KnockoutObservable<number> = ko.observable(null);
        //B20_7	
        reservationEndTime2: KnockoutObservable<number> = ko.observable(null);
        //B21_2	
        orderedData: KnockoutObservable<number> = ko.observable(0);

        viewModel: ViewModel;

        constructor() { };

        updateData(data: any) {
            this.operationDistinction(Number(data.operationDistinction));
            this.referenceTime(Number(data.referenceTime));
            this.contentChangeDeadline(data.contentChangeDeadline ? Number(data.contentChangeDeadline) : 0);
            this.contentChangeDeadlineDay(Number(data.contentChangeDeadlineDay));
            this.orderDeadline(Number(data.orderDeadline));
            this.monthlyResults(Number(data.monthlyResults));
            this.dailyResults(Number(data.dailyResults));
            this.reservationFrameName1(data.reservationFrameName1.toString());
            this.reservationStartTime1(Number(data.reservationStartTime1));
            this.reservationEndTime1(Number(data.reservationEndTime1));
            this.reservationFrameName2(data.reservationFrameName2 ? data.reservationFrameName2.toString() : "");
            this.reservationStartTime2(data.reservationStartTime2 ? Number(data.reservationStartTime2) : null);
            this.reservationEndTime2(data.reservationEndTime2 ? Number(data.reservationEndTime2) : null);
            this.orderedData(Number(data.orderedData));
        }
    }
}
