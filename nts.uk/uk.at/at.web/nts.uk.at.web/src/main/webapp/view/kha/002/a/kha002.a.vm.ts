module nts.uk.at.kha002.a {
    const API = {
        init: "at/screen/kha002/a/init",
        getSetting: "at/function/supportworklist/aggregationsetting/get",
        getAllOutputSettings: "at/function/supportworklist/outputsetting/all"
    };

    const KEY = "KHA002Data";

    @bean()
    export class ViewModel extends ko.ViewModel {
        aggregateUnit: KnockoutObservable<number>;
        dateValue: KnockoutObservable<any>;
        selectedWorkplaces: KnockoutObservableArray<string>;
        selectedWorkLocations: KnockoutObservableArray<string>;
        layoutSettings: KnockoutObservableArray<any>;
        selectedLayout: KnockoutObservable<string>;
        baseDate:KnockoutObservable<string | Date> = ko.observable(new Date());

        created() {
            const vm = this;
            vm.aggregateUnit = ko.observable(AggregateUnit.WORKPLACE);
            vm.dateValue = ko.observable({startDate: null, endDate: null});
            vm.selectedWorkplaces = ko.observableArray([]);
            vm.selectedWorkLocations = ko.observableArray([]);
            vm.layoutSettings = ko.observableArray([]);
            vm.selectedLayout = ko.observable(null);
        }

        mounted() {
            const vm = this;
            vm.$blockui("show");
            $.when(vm.$ajax(API.init), vm.$ajax(API.getSetting)).done((dateValue, setting) => {
                vm.dateValue(dateValue);
                vm.baseDate(moment.utc(dateValue.endDate, "YYYY/MM/DD").toISOString());
                if (setting) {
                    vm.aggregateUnit(setting.aggregateUnit);
                    nts.uk.characteristics.restore(KEY).done((data: any) => {
                        let code = null;
                        if (data) {
                            code = data.code;
                            if (vm.aggregateUnit() == AggregateUnit.WORKPLACE) vm.selectedWorkplaces(data.workplaceIds);
                            if (vm.aggregateUnit() == AggregateUnit.WORKLOCATION) vm.selectedWorkLocations(data.workLocationCodes);
                        }
                        if (vm.aggregateUnit() == AggregateUnit.WORKPLACE) {
                            $('#A3_3').ntsTreeComponent({
                                isShowAlreadySet: false,
                                isMultipleUse: false,
                                isMultiSelect: true,
                                startMode: 0, // WORKPLACE,
                                selectedId: vm.selectedWorkplaces,
                                baseDate: vm.baseDate,
                                selectType: 1, // Select by list
                                isShowSelectButton: true,
                                isDialog: false,
                                maxRows: 15,
                                restrictionOfReferenceRange: false,
                                systemType : 2 // 就業
                            });
                        } else {
                            // 今回対象外
                        }
                        vm.getAllSettingData(code);
                    });
                } else {
                    vm.$dialog.error({messageId: "Msg_3269"}).then(() => {
                        vm.openDialogC();
                    });
                }
            }).fail(error => {
                vm.$dialog.error(error).then(() => {
                    nts.uk.request.jumpToTopPage();
                });
            }).always(() => {
                vm.$blockui("hide");
            });
        }

        getAllSettingData(code?: string) {
            const vm = this;
            vm.$blockui("show");
            vm.$ajax(API.getAllOutputSettings).done(settings => {
                vm.layoutSettings(settings);
                if (_.isEmpty(settings)) {
                    vm.selectedLayout() == null ? vm.selectedLayout.valueHasMutated() : vm.selectedLayout(null);
                    vm.$dialog.error({messageId: "Msg_2154"}).then(() => {
                        $("#A5_2").focus();
                    });
                } else {
                    if (code && _.find(settings, (s: any) => s.code == code)) {
                        vm.selectedLayout() == code ? vm.selectedLayout.valueHasMutated() : vm.selectedLayout(code);
                    } else {
                        vm.selectedLayout() == settings[0].code ? vm.selectedLayout.valueHasMutated() : vm.selectedLayout(settings[0].code);
                    }
                }
            }).fail(error => {
                vm.$dialog.error(error);
            }).always(() => {
                vm.$blockui("hide");
            });
        }

        exportExcel() {
            const vm = this;
            nts.uk.characteristics.save(KEY, {
                code: vm.selectedLayout(),
                workplaceIds: vm.selectedWorkplaces(),
                workLocationCodes: vm.selectedWorkLocations()
            });
        }

        exportCsv() {
            const vm = this;
            nts.uk.characteristics.save(KEY, {
                code: vm.selectedLayout(),
                workplaceIds: vm.selectedWorkplaces(),
                workLocationCodes: []
            });
        }

        openDialogB() {
            const vm = this;
            vm.$window.modal("/view/kha/002/b/index.xhtml", {code: vm.selectedLayout()}).then((data) => {
                vm.getAllSettingData(data ? data.code : null);
            });
        }

        openDialogC() {
            const vm = this;
            vm.$window.modal("/view/kha/002/c/index.xhtml").then((data) => {
                if (data) vm.mounted();
            });
        }
    }

    enum AggregateUnit {
        WORKPLACE = 0,
        WORKLOCATION = 1
    }
}


