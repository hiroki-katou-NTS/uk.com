module nts.uk.at.ksm008.c {
    @bean()
    export class KSM008CViewModel extends ko.ViewModel {
        listComponentOption: any;
        enable: KnockoutObservable<boolean>;
        items: KnockoutObservableArray<ItemModel> = ko.observableArray([]);
        simpleValue: KnockoutObservable<string> = ko.observable("C7_2");
        otherValue: KnockoutObservable<string> = ko.observable("C7_3");
        columns: KnockoutObservableArray<NtsGridListColumn> = ko.observableArray([]);
        switchOps: KnockoutObservableArray<any>;
        selectedRuleCode: any = ko.observable(1);
        currentCode: KnockoutObservable<any>;
        currentCodeList: KnockoutObservableArray<any>;
        switchOptions: KnockoutObservableArray<any>;

        constructor(params: any) {
            super();

        }

        created() {
            const vm = this;
            vm.enable = ko.observable(true);
            vm.items = ko.observableArray([]);
            for (let i = 1; i < 100; i++) {
                vm.items.push(new ItemModel('00' + i, "description " + i));
            }
            vm.columns = ko.observableArray([
                {headerText: vm.$i18n('KSM008_32'), key: 'code', width: 100},
                {headerText: vm.$i18n('KSM008_33'), key: 'description', width: 150},
            ]);

            vm.switchOptions = ko.observableArray([
                {code: "1", name: '四捨五入'},
                {code: "2", name: '切り上げ'},
                {code: "3", name: '切り捨て'}
            ]);
            vm.currentCode = ko.observable(100);
            vm.currentCodeList = ko.observableArray([]);

            vm.switchOps = ko.observableArray([
                {code: '1', name: vm.$i18n("KSM008_40")},
                {code: '2', name: vm.$i18n("KSM008_41")}
            ]);

            const employeeList = ko.observableArray<UnitModel>([
                {id: '1a', code: '1', name: 'Angela Babykasjgdkajsghdkahskdhaksdhasd'},
                {id: '2b', code: '2', name: 'Xuan Toc Doaslkdhasklhdlashdhlashdl'},
                {id: '3c', code: '3', name: 'Park Shin Hye'},
                {id: '3d', code: '4', name: 'Vladimir Nabokov'}
            ]);

            vm.listComponentOption = {
                listType: ListType.EMPLOYEE,
                employeeInputList: employeeList,
                isShowAlreadySet: false,
                isMultiSelect: false,
                selectType: SelectType.SELECT_BY_SELECTED_CODE,
                selectedCode: ko.observable('1'),
                isDialog: true,
                isShowNoSelectRow: false,
                isShowWorkPlaceName: false,
                isShowSelectAllButton: false,
                disableSelection: false
            }
            $("#sample-component-left").ntsListComponent(vm.listComponentOption);
            $("#sample-component-right").ntsListComponent(vm.listComponentOption);
        }

        mounted() {

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
        description: string;

        constructor(code: string, description: string) {
            this.code = code;
            this.description = description;
        }
    }

    class NtsGridListColumn {
        headerText: string;
        key: string;
        width: number;
    }

}