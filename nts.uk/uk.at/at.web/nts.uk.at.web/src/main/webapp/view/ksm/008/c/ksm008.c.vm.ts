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
                {headerText: vm.$i18n('KSM008_32'), key: 'code', width: 100},
                {headerText: vm.$i18n('KSM008_33'), key: 'name', width: 150},
                {headerText: vm.$i18n('KSM008_34'), key: 'nightShift', width: 50},
            ]);

            vm.columnsWithoutNightShift = ko.observableArray([
                {headerText: vm.$i18n('KSM008_32'), key: 'code', width: 100},
                {headerText: vm.$i18n('KSM008_33'), key: 'name', width: 150},
            ]);

            vm.switchOps = ko.observableArray([
                {code: '0', name: vm.$i18n("KSM008_40")},
                {code: '1', name: vm.$i18n("KSM008_41")}
            ]);

            vm.initEmployeeList();
            vm.initEmployeeTargetList();
        }

        mounted() {
            const vm = this;

            vm.$blockui("grayout");
            vm.$ajax(API.init, {code: vm.code}).done((res) => {
                if (res) {

                    let { alarmCheck, banWorkTogether, orgInfo } = res;

                    if (alarmCheck) {
                        let lstCondition = alarmCheck.explanationList;

                        vm.alarmCheckSet(vm.code + " " + alarmCheck.conditionName);

                        if (lstCondition && lstCondition.length) {
                            vm.alarmCondition(lstCondition);
                        }
                    }

                    if (banWorkTogether && banWorkTogether.length) {
                        let lstBanWorkTogether = _.map(banWorkTogether, function (item: any) {
                           return new ItemModel(
                               item.code,
                               item.name,
                               item.applicableTimeZoneCls == 1 ? vm.$i18n('KSM008_102') : ""
                           );
                        });
                        vm.listBanWorkTogether(lstBanWorkTogether);
                        vm.swithchUpdateMode();
                    } else {
                        vm.swithchNewMode();
                    }

                    if (orgInfo) {

                        vm.targetOrganizationInfor({
                            unit: orgInfo.unit,
                            workplaceId: orgInfo.workplaceId,
                            workplaceGroupId: orgInfo.workplaceGroupId
                        });

                        vm.workplaceCode(orgInfo.code);
                        vm.workplaceName(orgInfo.displayName);

                    }
                }
            }).fail((err) => {
                vm.$dialog.error(err);
            }).always(() => vm.$blockui('clear'));

            vm.targetOrganizationInfor.subscribe(value => {
                if (value) {
                    vm.changeWorkplace();
                }
            });

            vm.isWorkplaceMode.subscribe(value => {
                if (value) {
                    vm.isNightShiftDisplay(true);
                } else {
                    vm.isNightShiftDisplay(false);
                }
            });

        }

        changeWorkplace() {
            const vm = this;

            let data = ko.toJS(vm.targetOrganizationInfor);

            vm.isWorkplaceMode(!_.isEmpty(data.workplaceId));

            vm.$blockui("grayout");
            vm.$ajax(API.getEmployeeInfo, data).done(res => {
                console.log(res);
                if (res) {
                    let listEmployee = _.map(res, function (item: any) {
                        return {id: item.employeeID, code: item.employeeCode, name: item.businessName, workplaceName: ''};
                    });

                    vm.selectableEmployeeList(listEmployee);
                } else {
                    vm.selectableEmployeeList([]);
                }
            }).fail(err => {
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
            };

            $("#kcp005-component-left").ntsListComponent(vm.selectableEmployeeComponentOption);
        }

        initEmployeeTargetList() {
            const vm = this;

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
            };

            $("#kcp005-component-right").ntsListComponent(vm.targetEmployeeComponentOption);
        }

        moveItemToRight() {
            const vm = this;

            let currentSelectableList = ko.toJS(vm.selectableEmployeeList());
            let currentTagretList = ko.toJS(vm.targetEmployeeList);
            let selectedableCode = ko.toJS(vm.selectedableCodes());

            vm.selectedableCodes([]);
            vm.targetSelectedCodes([]);

            _.each(selectedableCode, function (item:any) {

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

            vm.targetSelectedCodes([]);
            vm.selectedableCodes([]);

            _.each(selectedTargetList, function (item:any) {

                let selectedItem = _.filter(currentTagretList, (i: any) => i.code == item);

                _.remove(currentTagretList, (i: any) => i.code == item);

                currentSelectableList.push(selectedItem[0]);

                vm.selectableEmployeeList(currentSelectableList);
                vm.targetEmployeeList(currentTagretList)
            });

            console.log(vm.targetSelectedCodes());
        }

        swithchNewMode() {
            const vm = this;

            vm.isEnableCode(true);
        }

        swithchUpdateMode() {
            const vm = this;

            vm.isEnableCode(false);
        }

        register() {
            const vm = this;

            let targetList = _.map(vm.targetEmployeeList(), item => { return item.id });

            let data = {
                targetOrgIdenInfor: ko.toJS(vm.targetOrganizationInfor()),
                code: vm.banCode(),
                name: vm.banName(),
                applicableTimeZoneCls: vm.isWorkplaceMode ? 0 : vm.selectedOperatingTime(),
                upperLimit: vm.numOfEmployeeLimit(),
                targetList: targetList
            };

            vm.$blockui("grayout");
            vm.$ajax(API.register, data).done((res) => {
               if (res) {
                   vm.$dialog.info({messageId: "Msg_15"});
               }
            }).fail((err) => {
                vm.$dialog.error(err);
            });
        }

        openDialogKDL046() {
            const vm = this;

            let orgInfo = ko.toJS(vm.targetOrganizationInfor());

            const params = {
                unit: orgInfo.unit,
                workplaceId: orgInfo.workplaceId
            };
            vm.$window
                .storage('dataShareDialog046', params)
                .then(() => vm.$window.modal('at', '/view/kdl/046/a/index.xhtml'))
                .then(() => vm.$window.storage('dataShareKDL046'))
                .then((data) => {
                    if (data) {

                        let orgInfo: TargetOrgIdenInfor = {
                            unit: data.unit,
                            workplaceId: data.workplaceId,
                            workplaceGroupId:  data.workplaceGroupID
                        };
                        vm.workplaceCode(data.workplaceCode || data.workplaceGroupCode);
                        vm.workplaceName(data.workplaceName || data.workplaceGroupName);
                        vm.targetOrganizationInfor(orgInfo);
                    }
                });
        }

        newMode(){
            console.log("new mode");
            const vm = this;

        }

        removeData(){
            console.log("remove");
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
        code: string;
        name: string;
        nightShift: string;

        constructor(code: string, name: string, nightShift: string) {
            this.code = code;
            this.name = name;
            this.nightShift = nightShift;
        }
    }

    interface TargetOrgIdenInfor {
        unit: number;
        workplaceId: string;
        workplaceGroupId: string;
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
        update: "at/schedule/alarm/banworktogether/update"
    }

}