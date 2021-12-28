module nts.uk.at.ksu008.a {
    import Moment = moment.Moment;
    const API = {
        init: "screen/at/ksu008/a/get-used-info",
        exportExcel: "at/file/form9/report/export-excel"
    };

    @bean()
    export class ViewModel extends ko.ViewModel {
        options: any;
        workplaceGroupList: KnockoutObservableArray<any> = ko.observableArray([]);
        selectedWkpGroupIds: KnockoutObservableArray<string> = ko.observableArray([]);

        targetPeriod: KnockoutObservable<number>;
        periodStart: KnockoutObservable<string>;
        periodEnd: KnockoutComputed<Moment>;
        displayPeriod: KnockoutComputed<string>;

        comboItemList: KnockoutObservableArray<any>;
        selectedCode: KnockoutObservable<string>;

        printTarget: KnockoutObservable<number>;

        deductionDateFromDeliveryTime: KnockoutObservable<boolean>;
        deductionDateFromDeliveryTimeTarget: KnockoutObservable<number>;
        deductionDateFromDeliveryTimeColor: KnockoutObservable<string>;

        workingHours: KnockoutObservable<boolean>;
        workingHoursTarget: KnockoutObservable<number>;
        workingHoursScheduleColor: KnockoutObservable<string>;
        workingHoursRecordColor: KnockoutObservable<string>;

        constructor() {
            super();
            const self = this;
            self.options = {
                currentIds: self.selectedWkpGroupIds,
                multiple: true,
                tabindex: 3,
                isAlreadySetting: false,
                showEmptyItem: false,
                showPanel: false,
                rows: 12,
                selectedMode: 3,
                itemList: self.checkWorkplaceGroups.bind(self),
                workplaceGroupTypes: [1]
            };

            self.targetPeriod = ko.observable(0);
            self.periodStart = ko.observable(moment.utc().startOf('month').toISOString());
            self.periodEnd = ko.computed(() => {
                if (self.periodStart() && !$("#periodStart").ntsError("hasError")) {
                    if (self.targetPeriod() == 0) {
                        return moment.utc(self.periodStart()).add(1, "month").add(-1, "day");
                    } else {
                        return moment.utc(self.periodStart()).add(27, "day");
                    }
                } else return null;
            });
            self.displayPeriod = ko.computed(() => {
                if (self.periodStart() && self.periodEnd()
                    && moment.utc(self.periodStart()).isSameOrAfter(moment.utc("01/01/1900"))
                    && moment.utc(self.periodStart()).isSameOrBefore(moment.utc("12/31/9999")))
                    return self.$i18n("KSU008_171", [moment.utc(self.periodStart()).format("YYYY/MM/DD") + "～" + self.periodEnd().format("YYYY/MM/DD")]);
                else return "";
            });

            self.comboItemList = ko.observableArray([]);
            self.selectedCode = ko.observable('1');

            self.printTarget = ko.observable(0);

            self.deductionDateFromDeliveryTime = ko.observable(false);
            self.deductionDateFromDeliveryTimeTarget = ko.observable(1);
            self.deductionDateFromDeliveryTimeColor = ko.observable("#ffff00");

            self.workingHours = ko.observable(false);
            self.workingHoursTarget = ko.observable(0);
            self.workingHoursScheduleColor = ko.observable("#00ff00");
            self.workingHoursRecordColor = ko.observable("#0000ff");

            self.deductionDateFromDeliveryTimeTarget.subscribe(value => {
                if (value == 0) {
                    if (self.workingHoursTarget() != 1) self.workingHoursTarget(1);
                } else {
                    if (self.workingHoursTarget() != 0) self.workingHoursTarget(0);
                }
            });

            self.workingHoursTarget.subscribe(value => {
                if (value == 0) {
                    if (self.deductionDateFromDeliveryTimeTarget() != 1) self.deductionDateFromDeliveryTimeTarget(1);
                } else {
                    if (self.deductionDateFromDeliveryTimeTarget() != 0) self.deductionDateFromDeliveryTimeTarget(0);
                }
            });
        }

        created() {
            const vm = this;
            nts.uk.characteristics.restore("KSU008Data").done((data: any) => {
                if (data) {
                    vm.targetPeriod(data.targetPeriod);
                    vm.periodStart(data.periodStart);
                    vm.selectedCode(data.itemCode);
                    vm.printTarget(data.printTarget);
                    vm.selectedWkpGroupIds(data.workplaceGroupIds || []);

                    vm.deductionDateFromDeliveryTime(data.colorSetting.deductionDate.use);
                    vm.deductionDateFromDeliveryTimeTarget(data.colorSetting.deductionDate.target);
                    vm.deductionDateFromDeliveryTimeColor(data.colorSetting.deductionDate.color);

                    vm.workingHours(data.colorSetting.workingHours.use);
                    vm.workingHoursTarget(data.colorSetting.workingHours.target);
                    vm.workingHoursScheduleColor(data.colorSetting.workingHours.scheduleColor);
                    vm.workingHoursRecordColor(data.colorSetting.workingHours.recordColor);
                }
            });
            vm.getAllSetting().then(() => {
                if (vm.comboItemList().length == 0) $("#A5_3").focus();
                else $("#A1_1").focus();
            });
        }

        checkWorkplaceGroups(data: Array<any>) {
            const vm = this;
            vm.workplaceGroupList(data || []);
            if (_.isEmpty(data)) vm.$dialog.alert({messageId: "Msg_1929"});
			else if (vm.selectedWkpGroupIds().length == 0) vm.selectedWkpGroupIds([data[0].id]);
        }

        getAllSetting(code?: string) {
            const vm = this, dfd = $.Deferred();
            vm.$blockui("show");
            vm.$ajax(API.init).done(settings => {
                const data = settings || [];
                data.forEach(i => {
                    i.type = i.systemFixed ? "" : vm.$i18n('KSU008_170');
                });
                vm.comboItemList(data);
                if (code) vm.selectedCode(code);
                dfd.resolve();
            }).fail(error => {
                vm.$dialog.error(error);
                dfd.reject();
            }).always(() => {
                vm.$blockui("hide");
            });
            return dfd.promise();
        }

        prevPeriodStart() {
            const vm = this;
            if (vm.periodStart() && moment.utc(vm.periodStart()).isValid()) {
                if (vm.targetPeriod() == 0) {
                    vm.periodStart(moment.utc(vm.periodStart()).add(-1, "month").toISOString());
                } else {
                    vm.periodStart(moment.utc(vm.periodStart()).add(-28, "day").toISOString());
                }
            }
        }

        nextPeriodStart() {
            const vm = this;
            if (vm.periodEnd()) vm.periodStart(vm.periodEnd().add(1, "day").toISOString());
        }

        openKsu008B() {
            let vm = this;
            const selectedSetting = _.find(vm.comboItemList(), i => i.code == vm.selectedCode());
            vm.$window.modal('/view/ksu/008/b/index.xhtml', {isSystemFixed: selectedSetting ? selectedSetting.systemFixed : true, layoutCode: vm.selectedCode()}).then((result: any) => {
				if (result) {
                    vm.getAllSetting(result.code).then(() => {
                        $("#A5_2").trigger("validate");
                    });
                }
                $("#A1_1").focus();
            });
        }

        openKsu008D() {
            let vm = this;
            vm.$blockui("invisible");
            vm.$window.modal('/view/ksu/008/d/index.xhtml').then((result: any) => {
                vm.$blockui("clear");
            });
        }

        exportExcel() {
            const vm = this;
            if (vm.selectedWkpGroupIds().length == 0) {
                vm.$dialog.error({messageId: "Msg_218", messageParams: [vm.$i18n("KSU011_6")]});
                return;
            }
            if (vm.periodEnd() && (vm.periodEnd().isBefore(moment.utc("01/01/1900")) || vm.periodEnd().isAfter(moment.utc("12/31/9999")))) {
                vm.$dialog.error({messageId: "Msg_2316"});
                return;
            }
            vm.$blockui("grayout");
            const exportQuery = {
                startDate: vm.periodStart(),
                endDate: vm.periodEnd().toISOString(),
                wkpGroupList: vm.workplaceGroupList().filter(i => vm.selectedWkpGroupIds().indexOf(i.id) >= 0),
                code: vm.selectedCode(),
                acquireTarget: vm.printTarget(),
                colorSetting: {
                    deliveryTimeDeductionDate: {
                        isUse: vm.deductionDateFromDeliveryTime(),
                        displayTarget: vm.deductionDateFromDeliveryTimeTarget(),
                        color: vm.deductionDateFromDeliveryTimeColor()
                    },
                    workingHours: {
                        isUse: vm.workingHours(),
                        displayTarget: vm.workingHoursTarget(),
                        scheduleColor: vm.workingHoursScheduleColor(),
                        actualColor: vm.workingHoursRecordColor()
                    }
                }
            };
            nts.uk.request.exportFile(API.exportExcel, exportQuery).done(() => {
                nts.uk.characteristics.save("KSU008Data", {
                    targetPeriod: vm.targetPeriod(),
                    periodStart: vm.periodStart(),
                    itemCode: vm.selectedCode(),
                    printTarget: vm.printTarget(),
                    workplaceGroupIds: vm.selectedWkpGroupIds(),
                    colorSetting: {
                        deductionDate: {
                            use: vm.deductionDateFromDeliveryTime(),
                            target: vm.deductionDateFromDeliveryTimeTarget(),
                            color: vm.deductionDateFromDeliveryTimeColor()
                        },
                        workingHours: {
                            use: vm.workingHours(),
                            target: vm.workingHoursTarget(),
                            scheduleColor: vm.workingHoursScheduleColor(),
                            recordColor: vm.workingHoursRecordColor()
                        }
                    }
                });
            }).fail(error => {
                vm.$dialog.error(error);
            }).always(() => {
                vm.$blockui("hide");
            });
        }
    }

}