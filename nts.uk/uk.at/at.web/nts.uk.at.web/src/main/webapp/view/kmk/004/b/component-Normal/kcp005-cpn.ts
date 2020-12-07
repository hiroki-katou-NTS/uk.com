/// <reference path="../../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmk004.b {
    const template = `
		<div id="employee-list">
		</div>
	`;

    export interface ParamsKcp005 {
        selectedCode: KnockoutObservable<string>;
        employees: KnockoutObservableArray<IEmployee>;
    }

    @component({
        name: 'kcp005',
        template
    })

    export class KCP005VM extends ko.ViewModel {

        public selectedCode: KnockoutObservable<string> = ko.observable('');
        public employees: KnockoutObservableArray<IEmployee> = ko.observableArray([]);
        public employeeList: KnockoutObservableArray<IEmployee> = ko.observableArray([]);
        public alreadySettingList: KnockoutObservableArray<UnitAlreadySettingModel> = ko.observableArray([]);
        public workPlaces: KnockoutObservableArray<IWorkTime> = ko.observableArray([]);

        created(params: ParamsKcp005) {
            const vm = this;
            vm.selectedCode = params.selectedCode;
            vm.employees = params.employees;

            $('#employee-list')
                .ntsListComponent({
                    isShowAlreadySet: true,
                    isMultiSelect: false,
                    listType: ListType.EMPLOYEE,
                    employeeInputList: vm.employees,
                    selectedCode: vm.selectedCode,
                    isDialog: false,
                    isShowNoSelectRow: false,
                    alreadySettingList: vm.alreadySettingList,
                    isShowWorkPlaceName: true,
                    isShowSelectAllButton: false,
                    disableSelection: false
                });

            vm.selectedCode.subscribe(() => {
                console.log(ko.unwrap(vm.selectedCode));
            })
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

    interface IworkPlace{
        id: string;
        code: string;
        name: string;
        nodeText?: string;
        level: number;
        heirarchyCode: string;
        isAlreadySetting?: boolean;
        children: Array<IworkPlace>;
    }
}