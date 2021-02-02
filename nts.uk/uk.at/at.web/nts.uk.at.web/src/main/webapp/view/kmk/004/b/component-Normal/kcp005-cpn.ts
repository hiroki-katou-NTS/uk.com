/// <reference path="../../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmk004.b {
    const template = `
		<div id="employee-list">
		</div>
	`;

    export interface ParamsKcp005 {
        selectedCode: KnockoutObservable<string>;
        employees: KnockoutObservableArray<IEmployee>;
        isChange: KnockoutObservable<string>;
        isReload: KnockoutObservable<string>;
        model: Employee;
    }

    const API = {
        GET_EMPLOYEEIDS: 'screen/at/kmk004/viewe/sha/getEmployeeId'
    }


    @component({
        name: 'kcp005',
        template
    })

    export class KCP005VM extends ko.ViewModel {

        public selectedCode: KnockoutObservable<string> = ko.observable('');
        public employees: KnockoutObservableArray<IEmployee> = ko.observableArray([]);
        public alreadySettingList: KnockoutObservableArray<UnitAlreadySettingModel> = ko.observableArray([]);
        public isChange: KnockoutObservable<string> = ko.observable('');
        public model: Employee = new Employee();
        public isReload: KnockoutObservable<string> = ko.observable('');

        created(params: ParamsKcp005) {
            const vm = this;
            vm.selectedCode = params.selectedCode;
            vm.employees = params.employees;
            vm.isChange = params.isChange;
            vm.model = params.model;
            vm.isReload = params.isReload;

            vm.reload();

            vm.employees
                .subscribe(() => {
                    vm.$blockui('invisible')
                        .then(() => {
                            vm.reloadIsCheck();
                        })
                        .then(() => {
                            vm.$blockui('clear');
                        });
                });

            vm.isChange
                .subscribe(() => {
                    vm.reloadIsCheck();
                });

            vm.isReload
                .subscribe(() => {
                    const vm = this;
                    vm.reload();
                })
        }

        reloadIsCheck() {
            const vm = this;
            vm.$ajax(API.GET_EMPLOYEEIDS)
                .then((data: any) => {
                    let list: UnitAlreadySettingModel[] = [];
                    _.forEach(data, ((value) => {
                        const setting: IEmployee = _.find(ko.unwrap(vm.employees), e => e.id === value.employeeId);

                        if (setting) {
                            let object = { code: setting.code, isAlreadySetting: true } as UnitAlreadySettingModel;
                            list.push(object);
                        }
                    }));
                    vm.alreadySettingList(list);
                });
        }

        // checkStatus() {
        //     const vm = this;
        //     if (ko.unwrap(vm.selectedCode)) {
        //         const exist = _.find(ko.unwrap(vm.alreadySettingList), (m: UnitAlreadySettingModel) => m.code === ko.unwrap(vm));

        //         if (exist) {
        //             vm.employment.updateStatus(exist.isAlreadySetting);
        //         } else {
        //             vm.employment.updateStatus(false);
        //         }
        //     }
        // }

        reload() {

            const vm = this;

            $('#employee-list')
                .ntsListComponent({
                    isShowAlreadySet: true,
                    isMultiSelect: false,
                    listType: ListType.EMPLOYEE,
                    employeeInputList: vm.employees,
                    selectedCode: vm.selectedCode,
                    selectType: SelectType.SELECT_BY_SELECTED_CODE,
                    isDialog: false,
                    isShowNoSelectRow: false,
                    alreadySettingList: vm.alreadySettingList,
                    isShowWorkPlaceName: true,
                    isShowSelectAllButton: false,
                    disableSelection: false
                });
        }
    }

    class ListType {
        static EMPLOYMENT = 1;
        static Classification = 2;
        static JOB_TITLE = 3;
        static EMPLOYEE = 4;
    }

    class SelectType {
        static SELECT_BY_SELECTED_CODE = 1;
        static SELECT_ALL = 2;
        static SELECT_FIRST_ITEM = 3;
        static NO_SELECT = 4;
    }

    interface UnitAlreadySettingModel {
        code: string;
        isAlreadySetting: boolean;
    }
}