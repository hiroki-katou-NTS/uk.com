/// <reference path="../../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmk004.b {
    const template = `
        <div id="empt-list-setting"></div>
	`;

    interface Params {
    }

    @component({
        name: 'kcp001',
        template
    })

    export class KCP001VM extends ko.ViewModel {

        public selectedCode: KnockoutObservable<string> = ko.observable('');
        public alreadySettingList: KnockoutObservableArray<any> = ko.observableArray([]);
        public closureSelectionType: KnockoutObservable<number> = ko.observable(1);

        created(params: Params) {
            const vm = this;

            $('#empt-list-setting')
                .ntsListComponent({
                    isShowAlreadySet: true,
                    isMultiSelect: false,
                    listType: ListType.EMPLOYMENT,
                    selectType: SelectType.SELECT_FIRST_ITEM,
                    selectedCode: vm.selectedCode,
                    isDialog: false,
                    isShowNoSelectRow: false,
                    alreadySettingList: vm.alreadySettingList,
                    isDisplayClosureSelection: true,
                    closureSelectionType: vm.closureSelectionType,
                    maxRows: 12
                });
        }
    }

    export class ListType {
        static EMPLOYMENT = 1;
        static Classification = 2;
        static JOB_TITLE = 3;
        static EMPLOYEE = 4;
    }

    export interface UnitModel {
        code: string;
        name?: string;
        workplaceName?: string;
        isAlreadySetting?: boolean;
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
