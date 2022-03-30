module nts.uk.at.kha002.a {
    const API = {
        init: "at/screen/kha002/a/init",
        getSetting: "at/function/supportworklist/aggregationsetting/get",
        getAllOutputSettings: "at/function/supportworklist/outputsetting/all",
        exportFile: "at/function/kha/002/report/export"
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
                                tabindex: 4,
                                restrictionOfReferenceRange: false,
                                systemType : 2 // 就業
                            }).then(() => {
                                $('#A3_3').focusTreeGridComponent();
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
                vm.layoutSettings(_.sortBy(settings, ["code"]));
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

        private getListWorkplaces(wkps: Array<any>) {
            let result: Array<any> = wkps;
            result.forEach(wkp => {
                if (!_.isEmpty(wkp.children)) {
                    result = result.concat(this.getListWorkplaces(wkp.children));
                }
            });
            return result;
        }

        exportExcel() {
            const vm = this;
            vm.$validate().then(valid => {
                if (valid) {
                    vm.$blockui("show");
                    const allItems = vm.aggregateUnit() == AggregateUnit.WORKPLACE
                        ? vm.getListWorkplaces($('#A3_3').getDataList())
                        : []; // 今回対象外
                    let wkpSelected = $('#A3_3').getRowSelected();

                    const exportQuery = {
                        aggregationUnit: vm.aggregateUnit(),
                        supportWorkCode: vm.selectedLayout(),
                        periodStart: new Date(vm.dateValue().startDate).toISOString(),
                        periodEnd: new Date(vm.dateValue().endDate).toISOString(),
                        workplaceIds: vm.selectedWorkplaces(),
                        baseDate: vm.aggregateUnit() == AggregateUnit.WORKPLACE ? moment(vm.baseDate()).format("YYYY/MM/DD") : null,
                        workLocationCodes: vm.selectedWorkLocations(),
                        headerInfos: [{
                            firstLineCode: vm.aggregateUnit() == AggregateUnit.WORKPLACE
                                ? (wkpSelected.length > 0 ? wkpSelected[0].code : null)
                                : null, // 今回対象外
                            firstLineName: vm.aggregateUnit() == AggregateUnit.WORKPLACE
                                ? (wkpSelected.length > 0 ? _.find(allItems, i => i.id == wkpSelected[0].id).name : null)
                                : null, // 今回対象外
                            lastLineCode: vm.aggregateUnit() == AggregateUnit.WORKPLACE
                                ? (wkpSelected.length > 0 ? wkpSelected[wkpSelected.length - 1].code : null)
                                : null, // 今回対象外
                            lastLineName: vm.aggregateUnit() == AggregateUnit.WORKPLACE
                                ? (wkpSelected.length > 0 ? _.find(allItems, i => i.id == wkpSelected[wkpSelected.length - 1].id).name : null)
                                : null, // 今回対象外
                            numberOfSelect: vm.aggregateUnit() == AggregateUnit.WORKPLACE
                                ? vm.selectedWorkplaces().length
                                : vm.selectedWorkLocations().length
                        }],
                        exportCsv: false
                    };
                    nts.uk.request.exportFile(API.exportFile, exportQuery).done(() => {
                        nts.uk.characteristics.save(KEY, {
                            code: vm.selectedLayout(),
                            workplaceIds: vm.selectedWorkplaces(),
                            workLocationCodes: vm.selectedWorkLocations()
                        });
                    }).fail(function (error) {
                        vm.$dialog.error(error);
                    }).always(function () {
                        vm.$blockui("hide");
                    });
                }
            });
        }

        exportCsv() {
            const vm = this;
            vm.$validate().then(valid => {
                if (valid) {
                    vm.$blockui("show");
                    const allItems = vm.aggregateUnit() == AggregateUnit.WORKPLACE
                        ? vm.getListWorkplaces($('#A3_3').getDataList())
                        : []; // 今回対象外
                    let wkpSelected = $('#A3_3').getRowSelected();
                    const exportQuery = {
                        aggregationUnit: vm.aggregateUnit(),
                        supportWorkCode: vm.selectedLayout(),
                        periodStart: new Date(vm.dateValue().startDate).toISOString(),
                        periodEnd: new Date(vm.dateValue().endDate).toISOString(),
                        workplaceIds: vm.selectedWorkplaces(),
                        baseDate: vm.aggregateUnit() == AggregateUnit.WORKPLACE ? moment(vm.baseDate()).format("YYYY/MM/DD") : null,
                        workLocationCodes: vm.selectedWorkLocations(),
                        headerInfos: [{
                            firstLineCode: vm.aggregateUnit() == AggregateUnit.WORKPLACE
                                ? (wkpSelected.length > 0 ? wkpSelected[0].code : null)
                                : null, // 今回対象外
                            firstLineName: vm.aggregateUnit() == AggregateUnit.WORKPLACE
                                ? (wkpSelected.length > 0 ? _.find(allItems, i => i.id == wkpSelected[0].id).name : null)
                                : null, // 今回対象外
                            lastLineCode: vm.aggregateUnit() == AggregateUnit.WORKPLACE
                                ? (wkpSelected.length > 0 ? wkpSelected[wkpSelected.length - 1].code : null)
                                : null, // 今回対象外
                            lastLineName: vm.aggregateUnit() == AggregateUnit.WORKPLACE
                                ? (wkpSelected.length > 0 ? _.find(allItems, i => i.id == wkpSelected[wkpSelected.length - 1].id).name : null)
                                : null, // 今回対象外
                            numberOfSelect: vm.aggregateUnit() == AggregateUnit.WORKPLACE
                                ? vm.selectedWorkplaces().length
                                : vm.selectedWorkLocations().length
                        }],
                        exportCsv: true
                    };
                    nts.uk.request.exportFile(API.exportFile, exportQuery).done(() => {
                        nts.uk.characteristics.save(KEY, {
                            code: vm.selectedLayout(),
                            workplaceIds: vm.selectedWorkplaces(),
                            workLocationCodes: vm.selectedWorkLocations()
                        });
                    }).fail(function (error) {
                        vm.$dialog.error(error);
                    }).always(function () {
                        vm.$blockui("hide");
                    });
                }
            });
        }

        openDialogB() {
            const vm = this;
            vm.$window.modal("/view/kha/002/b/index.xhtml", {aggregateUnit: vm.aggregateUnit(), code: vm.selectedLayout()}).then((data) => {
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


