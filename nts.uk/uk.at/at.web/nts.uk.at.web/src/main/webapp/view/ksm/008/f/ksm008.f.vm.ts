/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.ksm008.f {
    @bean()
    export class KSM008FViewModel extends ko.ViewModel {

        listComponentOption: any;
        selectedCode: KnockoutObservable<string> = ko.observable('');
        employeeList: KnockoutObservableArray<UnitModel>;

        constructor(params: any) {
            super();
            const vm = this;

            this.employeeList = ko.observableArray<UnitModel>([
                { id: '1a', code: '1', name: 'Angela Babykasjgdkajsghdkahskdhaksdhasd', workplaceName: 'HN' },
                { id: '2b', code: '2', name: 'Xuan Toc Doaslkdhasklhdlashdhlashdl', workplaceName: 'HN' },
                { id: '3c', code: '3', name: 'Park Shin Hye', workplaceName: 'HCM' },
                { id: '3d', code: '4', name: 'Vladimir Nabokov', workplaceName: 'HN' }
            ]);
            vm.listComponentOption = {
                isMultiSelect: true,
                listType: ListType.EMPLOYEE,
                employeeInputList: vm.employeeList,
                selectType: SelectType.SELECT_BY_SELECTED_CODE,
                selectedCode: vm.selectedCode,
                //multiSelectedCode: [],
                isDialog: true,
                isShowSelectAllButton: false,
                maxRows: 10
            };
        }

        created() {
            const vm = this;

            $('#component-items-list').ntsListComponent(vm.listComponentOption);

            _.extend(window, {vm});
        }

        mounted() {

        }

        initData(){
            const vm = this;

            // Initial settings.

        }


        close() {
            let vm = this;
            vm.$window.close();
        }
    }

    export class ListType {
        static EMPLOYMENT = 1;
        static Classification = 2;
        static JOB_TITLE = 3;
        static EMPLOYEE = 4;
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

    export interface UnitAlreadySettingModel {
        code: string;
        isAlreadySetting: boolean;
    }
}
