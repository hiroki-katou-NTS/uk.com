/// <reference path="../../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmk004.b {
    const template = `
		<div id="employee-list">
		</div>
	`;

    export interface ParamsKcp005 {
        selectedCode: KnockoutObservable<string>;
        employeeList: KnockoutObservableArray<IEmployeeKcp005>;
    }

    @component({
        name: 'kcp005',
        template
    })

    export class KCP005VM extends ko.ViewModel {

        public selectedCode: KnockoutObservable<string> = ko.observable('');
        public employeeList: KnockoutObservableArray<IEmployeeKcp005> = ko.observableArray([]);
        public alreadySettingList: KnockoutObservableArray<UnitAlreadySettingModel> = ko.observableArray([]);

        created(params: Params) {
            const vm = this;

            $('#employee-list')
                .ntsListComponent({
                    isShowAlreadySet: true,
                    isMultiSelect: false,
                    listType: ListType.EMPLOYEE,
                    employeeInputList: vm.employeeList,
                    selectType: SelectType.SELECT_BY_SELECTED_CODE,
                    selectedCode: vm.selectedCode,
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

    export interface IEmployeeKcp005 {
        id?: string;
        code: string;
        name?: string;
        workplaceName?: string;
        isAlreadySetting?: boolean;
        optionalColumn?: any;
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