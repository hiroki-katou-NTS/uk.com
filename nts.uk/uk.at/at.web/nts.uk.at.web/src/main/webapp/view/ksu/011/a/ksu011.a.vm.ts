module nts.uk.at.view.ksu011.a.viewmodel {

    import Moment = moment.Moment;
    const API = {
        getAllSetting: "ctx/at/aggregation/scheduledailytable/printsetting/get-all",
        exportExcel: "at/file/scheduledailytable/export"
    };

    @bean()
    class Ksu011AViewModel extends ko.ViewModel {
        outputItems: KnockoutObservableArray<any>;

        currentCodes: KnockoutObservableArray<string>;
        currentNames: KnockoutObservableArray<string>;
        currentIds: KnockoutObservableArray<string>;
        targetPeriod: KnockoutObservable<number>;
        periodStart: KnockoutObservable<string>;
        periodEnd: KnockoutComputed<Moment>;
        displayPeriod: KnockoutComputed<string>;
        selectedOutputItemCode: KnockoutObservable<string>;
        printTarget: KnockoutObservable<number>;
        displayBothWhenDiffOnly: KnockoutObservable<boolean>;

        constructor() {
            super();
            const vm = this;
            vm.outputItems = ko.observableArray([]);
            vm.currentCodes = ko.observableArray([]);
            vm.currentNames = ko.observableArray([]);
            vm.currentIds = ko.observableArray([]);
            vm.targetPeriod = ko.observable(0);
            vm.periodStart = ko.observable(moment.utc().startOf('month').toISOString());
            vm.selectedOutputItemCode = ko.observable(null);
            vm.printTarget = ko.observable(0);
            vm.displayBothWhenDiffOnly = ko.observable(false);
            vm.periodEnd = ko.computed(() => {
                if (vm.periodStart() && moment.utc(vm.periodStart(), "YYYY/MM/DD").isValid()) {
                    if (vm.targetPeriod() == 0) {
                        return moment.utc(vm.periodStart()).add(1, "month").add(-1, "day");
                    } else {
                        return moment.utc(vm.periodStart()).add(27, "day");
                    }
                } else return null;
            });
            vm.displayPeriod = ko.computed(() => {
                if (vm.periodStart() && vm.periodEnd()
                    && moment.utc(vm.periodStart()).isSameOrAfter(moment.utc("01/01/1900"))
                    && moment.utc(vm.periodStart()).isSameOrBefore(moment.utc("12/31/9999")))
                    return vm.$i18n("KSU011_79", [moment.utc(vm.periodStart()).format("YYYY/MM/DD") + "～" + vm.periodEnd().format("YYYY/MM/DD")]);
                else return "";
            });
        }

        created() {
            const vm = this;
            nts.uk.characteristics.restore("KSU011Data").done((data: any) => {
                if (data) {
                    vm.targetPeriod(data.targetPeriod);
                    vm.periodStart(data.periodStart);
                    vm.selectedOutputItemCode(data.itemCode);
                    vm.printTarget(data.printTarget);
                    vm.displayBothWhenDiffOnly(data.displayBothWhenDiffOnly);
                    vm.currentIds(data.workplaceGroupIds || []);
                }
            });
            vm.getAllSetting().then(() => {
                if (vm.outputItems().length == 0) $("#A4_4").focus();
                else $("#A1_1").focus();
            });
        }

        getAllSetting(code?: string) {
            const vm = this, dfd = $.Deferred();
            vm.$blockui("show");
            vm.$ajax(API.getAllSetting).done(settings => {
                vm.outputItems(settings || []);
                if (code) vm.selectedOutputItemCode(code);
                dfd.resolve();
            }).fail(error => {
                vm.$dialog.error(error);
                dfd.reject();
            }).always(() => {
                vm.$blockui("hide");
            });
            return dfd.promise();
        }

        exportExcel() {
            const vm = this;
            if (vm.currentIds().length == 0) {
                vm.$dialog.error({messageId: "Msg_218", messageParams: [vm.$i18n("KSU011_6")]});
                return;
            }
            if (vm.periodEnd() && (vm.periodEnd().isBefore(moment.utc("01/01/1900")) || vm.periodEnd().isAfter(moment.utc("12/31/9999")))) {
                vm.$dialog.error({messageId: "Msg_2316"});
                return;
            }
            vm.$blockui("grayout");
            const exportQuery = {
                workplaceGroupIds: vm.currentIds(),
                periodStart: vm.periodStart(),
                periodEnd: vm.periodEnd().toISOString(),
                outputItemCode: vm.selectedOutputItemCode(),
                printTarget: vm.printTarget(),
                displayBothWhenDiffOnly: vm.printTarget() == 2 && vm.displayBothWhenDiffOnly()
            };
            nts.uk.request.exportFile(API.exportExcel, exportQuery).done(() => {
                nts.uk.characteristics.save("KSU011Data", {
                    targetPeriod: vm.targetPeriod(),
                    periodStart: vm.periodStart(),
                    itemCode: vm.selectedOutputItemCode(),
                    printTarget: vm.printTarget(),
                    displayBothWhenDiffOnly: vm.displayBothWhenDiffOnly(),
                    workplaceGroupIds: vm.currentIds()
                });
            }).fail(error => {
                vm.$dialog.error(error);
            }).always(() => {
                vm.$blockui("hide");
            });
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

        openDialogB() {
            const vm = this;
            vm.$window.modal("/view/ksu/011/b/index.xhtml", {itemCode: vm.selectedOutputItemCode()}).then((data: any) => {
                if (data) {
                    vm.getAllSetting(data.itemCode).then(() => {
                        $(".ui-igcombo-wrapper").trigger("validate");
                    });
                }
            });
        }
    }
}