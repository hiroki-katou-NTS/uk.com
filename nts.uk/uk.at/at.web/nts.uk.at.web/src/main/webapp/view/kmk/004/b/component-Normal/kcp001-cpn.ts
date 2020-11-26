/// <reference path="../../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmk004.b {
    const template = `
        <div id="empt-list-setting"></div>
	`;

    interface Params {
        emloyment: Employment;
        alreadySettings: KnockoutObservableArray<AlreadySettingEmployment>;
    }

    @component({
        name: 'kcp001',
        template
    })

    export class KCP001VM extends ko.ViewModel {

        public selectedCode: KnockoutObservable<string> = ko.observable('');
        public alreadySettings: KnockoutObservableArray<AlreadySettingEmployment>;
        public closureSelectionType: KnockoutObservable<number> = ko.observable(1);
        public employmentList: KnockoutObservableArray<IEmployment> = ko.observableArray([]);
        public employment: Employment;

        created(params: Params) {
            const vm = this;
            vm.employment = params.emloyment;
            vm.alreadySettings = params.alreadySettings;

            vm.selectedCode.subscribe(() => {
                const exist = _.find(ko.unwrap(vm.employmentList), (emp: IEmployment) => emp.code === ko.unwrap(vm.selectedCode));
                if (exist) {
                    vm.employment.update(exist);
                }
            })
            
            vm.alreadySettings.subscribe(() => {
                $('#empt-list-setting') 
                .ntsListComponent({
                    isShowAlreadySet: true,
                    isMultiSelect: false,
                    listType: ListType.EMPLOYMENT,
                    selectType: SelectType.SELECT_BY_SELECTED_CODE,
                    selectedCode: vm.selectedCode,
                    isDialog: false,
                    isShowNoSelectRow: false,
                    alreadySettingList: vm.alreadySettings,
                    isDisplayClosureSelection: true,
                    closureSelectionType: vm.closureSelectionType,
                    maxRows: 12
                }).done(()=>{
                    console.log($('#tree-grid').getDataList());
                });
            });

            $('#empt-list-setting') 
                .ntsListComponent({
                    isShowAlreadySet: true,
                    isMultiSelect: false,
                    listType: ListType.EMPLOYMENT,
                    selectType: SelectType.SELECT_FIRST_ITEM,
                    selectedCode: vm.selectedCode,
                    isDialog: false,
                    isShowNoSelectRow: false,
                    alreadySettingList: vm.alreadySettings,
                    isDisplayClosureSelection: true,
                    closureSelectionType: vm.closureSelectionType,
                    maxRows: 12
                }).done(()=>{
                    vm.employmentList($('#empt-list-setting').getDataList());
                });
        }

        mounted() {

        }
    }

    export class ListType {
        static EMPLOYMENT = 1;
        static Classification = 2;
        static JOB_TITLE = 3;
        static EMPLOYEE = 4;
    }

    export interface IEmployment {
        code: string;
        name?: string;
        isAlreadySetting?: boolean;
    }

    export class Employment {
        code: KnockoutObservable<String> = ko.observable('');
        name: KnockoutObservable<String> = ko.observable('');
        isAlreadySetting: KnockoutObservable<boolean> = ko.observable(false);

        constructor(param? : IEmployment) {
            const md = this;

            if (param) {
                md.update(param);
            }
        }

        update(param: IEmployment){
            const md = this;
            md.code(param.code);
            md.name(param.name);
            md.isAlreadySetting(param.isAlreadySetting);
        }
    }
    
    export class SelectType {
        static SELECT_BY_SELECTED_CODE = 1;
        static SELECT_ALL = 2;
        static SELECT_FIRST_ITEM = 3;
        static NO_SELECT = 4;
    }
    
    export interface AlreadySettingEmployment {
        code: string;
        isAlreadySetting: boolean;
    }
}
