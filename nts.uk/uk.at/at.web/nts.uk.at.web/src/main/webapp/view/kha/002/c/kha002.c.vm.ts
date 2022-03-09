module nts.uk.at.kha002.c {
    const API = {
        getSetting: "at/function/supportworklist/aggregationsetting/get",
        register: "at/function/supportworklist/aggregationsetting/register"
    };

    @bean()
    export class ViewModel extends ko.ViewModel {
        aggregateUnit: KnockoutObservable<number>;
        judgmentAtr: KnockoutObservable<number>;
        standardHierarchy: KnockoutObservable<number>;

        created() {
            const vm = this;
            vm.aggregateUnit = ko.observable(null);
            vm.judgmentAtr = ko.observable(0);
            vm.standardHierarchy = ko.observable(1);
        }

        mounted() {
            const vm = this;
            vm.$blockui("show");
            vm.$ajax(API.getSetting).done(setting => {
                if (setting) {
                    vm.aggregateUnit(setting.aggregateUnit);
                    if (setting.judgmentAtr) vm.judgmentAtr(setting.judgmentAtr);
                    if (setting.standardHierarchy) vm.standardHierarchy(setting.standardHierarchy);
                } else {
                    vm.aggregateUnit(0);
                    const command = {
                        aggregateUnit: vm.aggregateUnit(),
                        judgmentAtr: vm.aggregateUnit() == 0 ? vm.judgmentAtr() : null,
                        standardHierarchy: vm.aggregateUnit() == 0 && vm.judgmentAtr() == 1 ? vm.standardHierarchy() : null
                    };
                    vm.$ajax(API.register, command).fail(error => {
                        vm.$dialog.error(error);
                    });
                }
                $("#C1_2").focus()
            }).fail(error => {
                vm.$dialog.error(error);
            }).always(() => {
                vm.$blockui("hide");
            });
        }

        register() {
            const vm = this;
            vm.$blockui("show");
            const command = {
                aggregateUnit: vm.aggregateUnit(),
                judgmentAtr: vm.aggregateUnit() == 0 ? vm.judgmentAtr() : null,
                standardHierarchy: vm.aggregateUnit() == 0 && vm.judgmentAtr() == 1 ? vm.standardHierarchy() : null
            };
            vm.$ajax(API.register, command).done(() => {
                vm.$dialog.info({messageId: "Msg_15"}).then(() => {
                    vm.$window.close(true);
                });
            }).fail(error => {
                vm.$dialog.error(error);
            }).always(() => {
                vm.$blockui("hide");
            });
        }

        closeDialog() {
            const vm = this;
            vm.$window.close();
        }
    }
}


