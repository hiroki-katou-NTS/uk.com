/// <reference path="../../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.ui.at.ksu002.a {


    const template = `
	<div class="kcp009cpn">
        <div id="emp-component"></div>
	</div>
	`;

    const COMPONENT_NAME = 'kcp009-cpn';

    @component({
        name: COMPONENT_NAME,
        template
    })
    export class KCP009CPN extends ko.ViewModel {

        employeeInputList: KnockoutObservableArray<EmployeeModel>;
        systemReference: KnockoutObservable<number> = ko.observable(SystemType.EMPLOYMENT);
        isDisplayOrganizationName: KnockoutObservable<boolean> = ko.observable(false);
        isDisplayNumberOfEmployee: KnockoutObservable<boolean> = ko.observable(false);
        targetBtnText: string = nts.uk.resource.getText("KCP009_3");
        baseDate: KnockoutObservable<Date>;
        listComponentOption: ComponentOption;
        selectedItem: KnockoutObservable<string> = ko.observable('');
        tabindex: number = 1;

        employeeId: KnockoutObservable<string> = ko.observable(this.$user.employeeId);
        showError: boolean = false;

        constructor(private param: Parameter) {
            super();
            const vm = this;

            vm.employeeInputList = param.employeeInputList;
            vm.employeeId = param.employeeId;


            // Initial listComponentOption
            vm.listComponentOption = {
                systemReference: vm.systemReference(),
                isDisplayOrganizationName: vm.isDisplayOrganizationName(),
                isDisplayNumberOfEmployee: vm.isDisplayNumberOfEmployee(),
                employeeInputList: vm.employeeInputList,
                targetBtnText: vm.targetBtnText,
                selectedItem: vm.selectedItem,
                tabIndex: vm.tabindex
            };

            vm.selectedItem.subscribe((data: string) => {
                if (vm.showError) {
                    vm.employeeId(data);
                    vm.showError = false;
                } else {
                    if (ko.toJS(param.haschange)) {
                        if(data !== ko.unwrap(vm.employeeId)) {
                            vm.$dialog
                            .confirm({ messageId: 'Msg_1732' })
                            .then((v) => {
                                if (v === 'yes') {
                                    vm.employeeId(data);
                                    vm.showError = false;
                                } else {
                                    vm.selectedItem(ko.unwrap(vm.employeeId));
                                }
                            });
                        }
                    }
                    else {
                        vm.employeeId(data);
                        vm.showError = false;
                    }
                }
            });

            vm.employeeInputList.subscribe((data: [EmployeeModel]) => {
                if (data.length > 0) {
                    if (ko.toJS(param.haschange)) {
                        vm.$dialog
                            .confirm({ messageId: 'Msg_1732' })
                            .then((v) => {
                                if (v === 'yes') {
                                    vm.changeEmployee(data);
                                }
                            });
                        vm.showError = true;
                    } else {
                        vm.changeEmployee(data);
                    }
                }
            });
        }

        public changeEmployee(data: [EmployeeModel]) {
            const vm = this;

            $('#emp-component').ntsLoadListComponent(vm.listComponentOption);
            const exist = _.find(data, ((item: EmployeeModel) => { return item.id == vm.$user.employeeId }));
            if (exist) {
                vm.selectedItem(exist.id);
            } else {
                vm.selectedItem(data[0].id);
            }
        }
    }

    interface Parameter {
        employeeInputList: KnockoutObservableArray<EmployeeModel>;
        employeeId: KnockoutObservable<string>;
        haschange: any;
    }

    export interface ComponentOption {
        systemReference: SystemType;
        isDisplayOrganizationName: boolean;
        isDisplayNumberOfEmployee: boolean;
        employeeInputList: KnockoutObservableArray<EmployeeModel>;
        targetBtnText: string;
        selectedItem: KnockoutObservable<string>;
        tabIndex: number;
        baseDate?: KnockoutObservable<Date>;
    }
    export interface EmployeeModel {
        id: string;
        code: string;
        businessName: string;
    }
    export class SystemType {
        static EMPLOYMENT = 1;
        static SALARY = 2;
        static PERSONNEL = 3;
        static ACCOUNTING = 4;
        static OH = 6;
    }
}