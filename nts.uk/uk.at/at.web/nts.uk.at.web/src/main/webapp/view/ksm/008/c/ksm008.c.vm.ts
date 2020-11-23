module nts.uk.at.ksm008.c {
    @bean()
    export class KSM008CViewModel extends ko.ViewModel {
        enableBtnDel: KnockoutObservable<boolean> = ko.observable(false);
        items: KnockoutObservableArray<ItemModel> = ko.observableArray([]);
        switchOps: KnockoutObservableArray<any>;

        // transfer data
        code: string;

        alarmCheckSet: KnockoutObservable<string> = ko.observable(null);
        alarmCondition: KnockoutObservableArray<string> = ko.observableArray([]);

        //C4_3 C4_4
        unit: KnockoutObservable<string> = ko.observable(null);
        workplaceCode: KnockoutObservable<string> = ko.observable(null);
        workplaceName: KnockoutObservable<string> = ko.observable(null);
        targetOrganizationInfor: KnockoutObservable<TargetOrgIdenInfor> = ko.observable(null);

        //C6
        columns: KnockoutObservableArray<NtsGridListColumn> = ko.observableArray([]);
        columnsWithoutNightShift: KnockoutObservableArray<NtsGridListColumn> = ko.observableArray([]);
        listBanWorkTogether: KnockoutObservableArray<ItemModel> = ko.observableArray([]);
        selectedProhibitedCode: KnockoutObservable<string> = ko.observable(null);
        banCode: KnockoutObservable<string> = ko.observable(null);
        banName: KnockoutObservable<string> = ko.observable(null);

        //C6_2
        isNightShiftDisplay: KnockoutObservable<boolean> = ko.observable(true);

        //C7_2
        isEnableCode: KnockoutObservable<boolean> = ko.observable(true);

        //C8_2
        selectedOperatingTime: KnockoutObservable<number> = ko.observable(0);

        //C9_3
        numOfEmployeeLimit: KnockoutObservable<number> = ko.observable(null);

        //C11_1
        selectableEmployeeList: KnockoutObservableArray<any> = ko.observableArray([]);
        selectedableCodes: KnockoutObservableArray<string> = ko.observableArray([]);
        selectableEmployeeComponentOption: any;

        //C11_4
        targetEmployeeList: KnockoutObservableArray<any> = ko.observableArray([]);
        targetSelectedCodes: KnockoutObservableArray<string> = ko.observableArray([]);
        targetEmployeeComponentOption: any;

        isWorkplaceMode: KnockoutObservable<boolean> = ko.observable(true);
        lstEmployeeSelectableBegin: KnockoutObservableArray<any> = ko.observableArray([]);

        constructor(params: any) {
            super();
            const vm = this;

            if (params) {
                vm.code = params.code;
            }
        }

        created() {
            const vm = this;

            vm.listBanWorkTogether = ko.observableArray([]);

            vm.columns = ko.observableArray([
                {headerText: vm.$i18n('KSM008_32'), key: 'code', width: 50, formatter: _.escape},
                {headerText: vm.$i18n('KSM008_33'), key: 'name', width: 150, formatter: _.escape},
                {headerText: vm.$i18n('KSM008_34'), key: 'nightShift', width: 50},
            ]);

            vm.columnsWithoutNightShift = ko.observableArray([
                {headerText: vm.$i18n('KSM008_32'), key: 'code', width: 50, formatter: _.escape},
                {headerText: vm.$i18n('KSM008_33'), key: 'name', width: 150, formatter: _.escape},
            ]);

            vm.switchOps = ko.observableArray([
                {code: '0', name: vm.$i18n("KSM008_40")},
                {code: '1', name: vm.$i18n("KSM008_41")}
            ]);

            vm.initEmployeeList();
        }

        mounted() {
            const vm = this;

            vm.loadData();

            vm.isWorkplaceMode.subscribe(value => {
                if (value) {
                    vm.isNightShiftDisplay(true);
                } else {
                    vm.isNightShiftDisplay(false);
                }
            });

            vm.selectedProhibitedCode.subscribe(value => {
                if (value) {
                    vm.changeBanCode(value);
                }
            });

            vm.numOfEmployeeLimit.subscribe(value => {
                $("#kcp005-component-right").ntsError("clear");
            });

        }

        loadData() {
            const vm = this;

            vm.$blockui("grayout");
            vm.$ajax(API.init, {code: vm.code}).done((res) => {
                if (res) {

                    let {alarmCheck, banWorkTogether, orgInfo} = res;

                    if (alarmCheck) {
                        let lstCondition = alarmCheck.explanationList;

                        vm.alarmCheckSet((vm.code || "") + " " + alarmCheck.conditionName);

                        if (lstCondition && lstCondition.length) {
                            vm.alarmCondition(lstCondition);
                        }
                    }

                    if (orgInfo) {

                        vm.workplaceCode(orgInfo.code);
                        vm.workplaceName(orgInfo.displayName);
                        vm.targetOrganizationInfor({
                            unit: orgInfo.unit,
                            workplaceId: orgInfo.workplaceId,
                            workplaceGroupId: orgInfo.workplaceGroupId
                        });
                        vm.changeWorkplace(vm.targetOrganizationInfor).then(() => {
                            if (banWorkTogether && banWorkTogether.length) {
                                let lstBanWorkTogether = _.map(banWorkTogether, function (item: any) {
                                    return new ItemModel(item, vm);
                                });
                                vm.listBanWorkTogether(lstBanWorkTogether);
                                vm.selectedProhibitedCode(lstBanWorkTogether[0].code);
                            } else {
                                vm.listBanWorkTogether([]);
                                vm.swithchNewMode();
                            }
                        });

                    }
                }
            }).fail((err) => {
                vm.$dialog.error(err);
            }).always(() => vm.$blockui('clear'));
        }

        changeWorkplace(param: any) {
            const vm = this;

            let data = ko.toJS(param);

            vm.isWorkplaceMode(!_.isEmpty(data.workplaceId));

            return vm.$ajax(API.getEmployeeInfo, data).done(res => {
                if (res) {
                    let listEmployee = _.map(res, function (item: any) {
                        return {
                            id: item.employeeID,
                            code: item.employeeCode,
                            name: item.businessName,
                            workplaceName: ''
                        };
                    });
                    vm.selectableEmployeeList(listEmployee);
                    vm.lstEmployeeSelectableBegin(listEmployee);
                } else {
                    vm.selectableEmployeeList([]);
                }
            }).fail(err => {
                vm.$dialog.error(err);
            });
        }

        getBanWorkListByCode() {
            const vm = this;

            return vm.$ajax(API.getByCodeAndWorkInfo, ko.toJS(vm.targetOrganizationInfor)).done((res) => {
                if (res && res.length) {
                    let listBanWork = _.map(res, function (i: any) {
                        return new ItemModel(i, vm);
                    });
                    vm.listBanWorkTogether(listBanWork);
                } else {
                    vm.listBanWorkTogether([]);
                    vm.swithchNewMode();
                }
            }).fail(err => {
                vm.$dialog.error(err);
            });
        }

        changeBanCode(code: string) {
            const vm = this;
            let data = {
                unit: vm.targetOrganizationInfor().unit,
                workplaceId: vm.targetOrganizationInfor().workplaceId,
                workplaceGroupId: vm.targetOrganizationInfor().workplaceGroupId,
                code
            };
            vm.$blockui("grayout");
            vm.$ajax(API.getBanWorkByBanCode, data).done((res: any) => {
                if (res) {
                    vm.swithchUpdateMode(new ItemModel(res, vm));
                }
            }).fail((err) => {
                vm.$dialog.error(err);
            }).always(() => vm.$blockui('clear'));

        }

        initEmployeeList() {
            const vm = this;

            vm.selectableEmployeeComponentOption = {
                listType: ListType.EMPLOYEE,
                employeeInputList: vm.selectableEmployeeList,
                isShowAlreadySet: false,
                isMultiSelect: true,
                selectType: SelectType.SELECT_BY_SELECTED_CODE,
                selectedCode: vm.selectedableCodes,
                isDialog: true,
                isShowNoSelectRow: false,
                isShowWorkPlaceName: false,
                isShowSelectAllButton: false,
                disableSelection: false,
                hasPadding: false,
                maxRows: 10
            };

            vm.targetEmployeeComponentOption = {
                listType: ListType.EMPLOYEE,
                employeeInputList: vm.targetEmployeeList,
                isShowAlreadySet: false,
                isMultiSelect: true,
                selectType: SelectType.SELECT_BY_SELECTED_CODE,
                selectedCode: vm.targetSelectedCodes,
                isDialog: true,
                isShowNoSelectRow: false,
                isShowWorkPlaceName: false,
                isShowSelectAllButton: false,
                disableSelection: false,
                hasPadding: false,
                maxRows: 10
            };

            $("#kcp005-component-left").ntsListComponent(vm.selectableEmployeeComponentOption).then(() => {
                $("#kcp005-component-right").ntsListComponent(vm.targetEmployeeComponentOption);
            });
        }

        moveItemToRight() {
            const vm = this;

            let currentSelectableList = ko.toJS(vm.selectableEmployeeList());
            let currentTagretList = ko.toJS(vm.targetEmployeeList);
            let selectedableCode = ko.toJS(vm.selectedableCodes());

            $("#kcp005-component-right").ntsError("clear");
            vm.selectedableCodes([]);
            vm.targetSelectedCodes([]);

            _.each(selectedableCode, function (item: any) {

                let selectedItem = _.filter(currentSelectableList, (i: any) => i.code == item);

                _.remove(currentSelectableList, (i: any) => i.code == item);

                currentTagretList.push(selectedItem[0]);

                vm.selectableEmployeeList(currentSelectableList);
                vm.targetEmployeeList(currentTagretList);
            });

        }

        moveItemToLeft() {
            const vm = this;

            let currentSelectableList = ko.toJS(vm.selectableEmployeeList());
            let currentTagretList = ko.toJS(vm.targetEmployeeList());
            let selectedTargetList = ko.toJS(vm.targetSelectedCodes());

            $("#kcp005-component-right").ntsError("clear");
            vm.targetSelectedCodes([]);
            vm.selectedableCodes([]);

            _.each(selectedTargetList, function (item: any) {

                let selectedItem = _.filter(currentTagretList, (i: any) => i.code == item);

                _.remove(currentTagretList, (i: any) => i.code == item);

                currentSelectableList.push(selectedItem[0]);

                vm.selectableEmployeeList(currentSelectableList);
                vm.targetEmployeeList(currentTagretList)
            });
        }

        swithchNewMode() {
            const vm = this;

            vm.$errors("clear");
            vm.isEnableCode(true);
            vm.enableBtnDel(false);
            vm.banCode('');
            vm.banName('');
            vm.numOfEmployeeLimit(null);
            vm.selectedOperatingTime(0);
            vm.selectedProhibitedCode(null);
            vm.targetEmployeeList([]);
            vm.selectableEmployeeList(vm.lstEmployeeSelectableBegin());
            vm.targetSelectedCodes([]);
            vm.selectedableCodes([]);
            $('#C7_2').focus();
        }

        swithchUpdateMode(data: any) {
            const vm = this;
            const firtBanListItem = data;
            const listBanEmployee = data.empBanWorkTogetherLst;
            let listSelectable = ko.toJS(vm.lstEmployeeSelectableBegin);

            vm.$errors("clear");
            vm.isEnableCode(false);
            vm.enableBtnDel(true);
            vm.selectedProhibitedCode(firtBanListItem.code);
            vm.selectedOperatingTime(firtBanListItem.applicableTimeZoneCls);
            vm.banCode(firtBanListItem.code);
            vm.banName(firtBanListItem.name);
            vm.numOfEmployeeLimit(firtBanListItem.upperLimit);

            if (listBanEmployee) {
                let listTarget: any = [];
                _.each(listBanEmployee, function (item) {
                    let currentEmployee = _.filter(listSelectable, i => i.id == item);
                    if (currentEmployee.length) {
                        _.remove(listSelectable, i => i.id == currentEmployee[0].id);
                        listTarget.push(currentEmployee[0]);
                    }
                });
                vm.selectableEmployeeList(listSelectable);
                vm.targetEmployeeList(listTarget);
            }

            vm.targetSelectedCodes([]);
            vm.selectedableCodes([]);
            $('#C7_3').focus();
        }

        register() {
            const vm = this;

            let targetList = _.map(vm.targetEmployeeList(), item => {
                return item.id
            });

            let data = {
                targetOrgIdenInfor: ko.toJS(vm.targetOrganizationInfor()),
                code: vm.banCode(),
                name: vm.banName(),
                applicableTimeZoneCls: vm.isWorkplaceMode() ? 0 : vm.selectedOperatingTime(),
                upperLimit: vm.numOfEmployeeLimit() - 1,
                targetList: targetList
            };
            let api = vm.isEnableCode() ? API.register : API.update;
            vm.$validate(['.nts-input']).then((valid) => {
                if (valid) {
                    return vm.validate();
                } else {
                    return false;
                }
            }).then((check) => {
                if (check) {
                    vm.$blockui("grayout");
                    vm.$ajax(api, data).done(() => {
                        vm.$dialog.info({messageId: "Msg_15"}).then(() => {
                            vm.getBanWorkListByCode().then(() => {
                                vm.selectedProhibitedCode('');
                                vm.selectedProhibitedCode(data.code);
                            })
                        });
                    }).fail((err) => {
                        vm.$dialog.error(err);
                    }).always(() => {
                        vm.$blockui('clear');
                    });
                }
            });
        }

        openDialogKDL046() {
            const vm = this;

            let orgInfo = ko.toJS(vm.targetOrganizationInfor());

            vm.$errors("clear");
            let params;
            if (vm.isWorkplaceMode()) {
                params = {
                    unit: orgInfo.unit,
                    workplaceId: orgInfo.workplaceId
                };
            } else {
                params = {
                    unit: orgInfo.unit,
                    workplaceGroupId: orgInfo.workplaceGroupId
                };
            }
            vm.$window
                .storage('dataShareDialog046', params)
                .then(() => vm.$window.modal('at', '/view/kdl/046/a/index.xhtml'))
                .then(() => vm.$window.storage('dataShareKDL046'))
                .then((data) => {
                    if (data) {

                        let orgInfo: TargetOrgIdenInfor = {
                            unit: data.unit,
                            workplaceId: data.workplaceId,
                            workplaceGroupId: data.workplaceGroupID
                        };
                        vm.workplaceCode(data.workplaceCode || data.workplaceGroupCode);
                        vm.workplaceName(data.workplaceName || data.workplaceGroupName);
                        vm.targetOrganizationInfor(orgInfo);
                        vm.changeWorkplace(orgInfo).then(() => {
                            vm.getBanWorkListByCode().then(() => {
                                if (vm.listBanWorkTogether().length) {
                                    vm.selectedProhibitedCode(null);
                                    vm.selectedProhibitedCode(vm.listBanWorkTogether()[0].code);
                                } else {
                                    vm.swithchNewMode();
                                }
                            });
                        })
                    }
                });
        }

        removeData() {
            const vm = this;

            vm.$errors("clear");
            vm.$dialog.confirm({ messageId: "Msg_18" }).then((result: 'no' | 'yes' | 'cancel') => {
                if (result === 'yes') {

                    let data = {
                        targetOrgIdenInfor: ko.toJS(vm.targetOrganizationInfor),
                        code: vm.selectedProhibitedCode()
                    };

                    let index = _.findIndex(vm.listBanWorkTogether(), i => i.code == vm.selectedProhibitedCode());

                    vm.$ajax(API.delete, data).done(() => {
                        vm.$dialog.info({messageId: "Msg_16"}).then(() => {
                            vm.getBanWorkListByCode().then(() => {
                                if (vm.listBanWorkTogether().length) {
                                    let newIndex = index == 0 ? 0 : index;
                                    vm.selectedProhibitedCode(vm.listBanWorkTogether()[newIndex].code);
                                } else {
                                    vm.swithchNewMode();
                                }
                            })
                        });
                    }).fail((err) => {
                        vm.$dialog.error(err);
                    });
                } else {
                    return;
                }
            });
        }

        validate() {
            const vm = this;

            let listBanEmp = ko.toJS(vm.targetEmployeeList());

            if (listBanEmp.length == 0 || listBanEmp.length == 1) {
                vm.$errors({
                    "#kcp005-component-right": {
                        messageId: "Msg_1875"
                    }
                });
                return false;
            }
            if (listBanEmp.length < vm.numOfEmployeeLimit()) {
                const message: any = {
                    messageId: "Msg_1787",
                    messageParams: [ko.toJS(vm.numOfEmployeeLimit())]
                };
                vm.$errors({
                    "#kcp005-component-right": message
                });
                return false;
            }
            return true;
        }
    }

    export interface UnitModel {
        id?: string;
        code: string;
        name?: string;
        workplaceName?: string;
        isAlreadySetting?: boolean;
        optionalColumn?: any;
    }

    export class SelectType {
        static SELECT_BY_SELECTED_CODE = 1;
        static SELECT_ALL = 2;
        static SELECT_FIRST_ITEM = 3;
        static NO_SELECT = 4;
    }

    export class ListType {
        static EMPLOYMENT = 1;
        static Classification = 2;
        static JOB_TITLE = 3;
        static EMPLOYEE = 4;
    }

    class ItemModel {
        code: string; // コード
        name: string; // 名称
        nightShift: string; // 適用する時間帯
        applicableTimeZoneCls: number; // 適用する時間帯
        upperLimit: number;
        targetOrgIdenInfor: TargetOrgIdenInfor;
        empBanWorkTogetherLst: any;

        constructor(banWorkTogether: BanWorkTogether, vm: any) {
            this.code = banWorkTogether.code;
            this.name = banWorkTogether.name;
            if (banWorkTogether.applicableTimeZoneCls == 1) {
                this.nightShift = 1 ? vm.$i18n('KSM008_102') : "";
            } else {
                this.nightShift = "";
            }
            this.applicableTimeZoneCls = banWorkTogether.applicableTimeZoneCls;
            this.upperLimit = banWorkTogether.upperLimit + 1;
            this.empBanWorkTogetherLst = banWorkTogether.empBanWorkTogetherLst;
            this.targetOrgIdenInfor = banWorkTogether.targetOrgIdenInfor;
        }
    }

    // 対象組織情報
    interface TargetOrgIdenInfor {
        unit: number; // 単位
        workplaceId: string; // Optional<職場ID>
        workplaceGroupId: string; // Optional<職場グループID>
    }

    class BanWorkTogether {
        applicableTimeZoneCls: number;
        code: string;
        name: string;
        empBanWorkTogetherLst: any;
        upperLimit: number;
        targetOrgIdenInfor: TargetOrgIdenInfor
    }

    class NtsGridListColumn {
        headerText: string;
        key: string;
        width: number;
    }

    const API = {
        init: "screen/at/ksm008/c/init",
        getEmployeeInfo: "screen/at/ksm008/c/getEmployeeInfo",
        register: "at/schedule/alarm/banworktogether/register",
        update: "at/schedule/alarm/banworktogether/update",
        getByCodeAndWorkInfo: "screen/at/ksm008/c/getBanWorkByWorkInfo",
        getBanWorkByBanCode: "at/schedule/alarm/banworktogether/getByCodeAndWorkplace",
        delete: "at/schedule/alarm/banworktogether/delete"
    }

}