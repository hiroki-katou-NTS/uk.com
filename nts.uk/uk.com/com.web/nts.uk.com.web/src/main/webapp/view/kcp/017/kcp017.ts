module nts.uk.com.view.kcp017.a.viewmodel {

    const template = `
    <div id="kcp017-component" data-bind="ntsPanel:{showIcon: true}" style="width: fit-content;">
        <div class="control-group valign-center">
            <div data-bind="ntsFormLabel: {text: $i18n('KCP017_2')}"/>
            <div class="cf" data-bind="ntsSwitchButton: {
                name: $i18n('KCP017_2'),
                options: [
                    {code: 0, name: $i18n('Com_Workplace')},
                    {code: 1, name: $i18n('Com_WorkplaceGroup')}
                ],
                optionsValue: 'code',
                optionsText: 'name',
                value: selectedUnit 
            }"/>
        </div>
        <hr />
        <div data-bind="if: selectedUnit() == 0">
            <div id="workplace-tree-grid"/>
        </div>
        <div data-bind="if: selectedUnit() == 1">
            <div data-bind="component: {
                name: 'workplace-group',
                params: {
                    options: kcp011Options
                }
            }"/>
        </div>
    </div>
    `;

    @component({
        name: 'kcp017-component',
        template: template
    })
    class ViewModel extends ko.ViewModel {
        selectedUnit: KnockoutObservable<number>;
        baseDate: KnockoutObservable<any>;
        selectType: KnockoutObservable<number | SelectType>; // kcp004
        selectMode: KnockoutObservable<number | SELECTED_MODE>; // kcp011
        alreadySettingWorkplaces: KnockoutObservableArray<any>;
        alreadySettingWorkplaceGroups: KnockoutObservableArray<any>;
        selectedIds: KnockoutObservableArray<any>;
        kcp011Options: any;
        kcp004Options: any;

        created(params: Params) {
            const vm = this;
            if (params) {
                vm.selectedUnit = ko.isObservable(params.init) ? params.init : ko.observable(params.init || 0);
                vm.baseDate = ko.isObservable(params.baseDate) ? params.baseDate : ko.observable(params.baseDate);
                vm.alreadySettingWorkplaces = params.alreadySettingWorkplaces;
                vm.alreadySettingWorkplaceGroups = params.alreadySettingWorkplaceGroups;
                vm.selectedIds = params.selectedIds;
                vm.selectType = ko.isObservable(params.selectType) ? params.selectType : ko.observable(params.selectType);
            } else {
                vm.selectedUnit = ko.observable(0);
                vm.baseDate = ko.observable(new Date());
                vm.alreadySettingWorkplaces = ko.observableArray([]);
                vm.alreadySettingWorkplaceGroups = ko.observableArray([]);
                vm.selectedIds = ko.observableArray([]);
                vm.selectType = ko.observable(SelectType.SELECT_FIRST_ITEM);
            }
            vm.selectMode = ko.computed(() => {
                if (vm.selectType() == SelectType.SELECT_FIRST_ITEM) return SELECTED_MODE.FIRST;
                if (vm.selectType() == SelectType.NO_SELECT) return SELECTED_MODE.NONE;
                if (vm.selectType() == SelectType.SELECT_ALL) return SELECTED_MODE.ALL;
                if (vm.selectType() == SelectType.SELECT_BY_SELECTED_ID) return SELECTED_MODE.SELECT_ID;
            });

            vm.kcp004Options = {
                isShowAlreadySet: true,
                isMultipleUse: false,
                isMultiSelect: true,
                startMode: 0, // WORKPLACE
                baseDate: vm.baseDate,
                selectType: vm.selectType(), // SELECT_FIRST_ITEM
                systemType: 2, // EMPLOYMENT
                isShowSelectButton: true,
                isDialog: true,
                hasPadding: false,
                maxRows: 12,
                alreadySettingList: vm.alreadySettingWorkplaces,
                selectedId: vm.selectedIds
            };
            vm.kcp011Options = {
                currentIds: vm.selectedIds,
                alreadySettingList: vm.alreadySettingWorkplaceGroups,
                multiple: true,
                isAlreadySetting: true,
                showPanel: false,
                showEmptyItem: false,
                reloadData: ko.observable(''),
                selectedMode: vm.selectMode(), // SELECT FIRST ITEM
                rows: 12
            };
            if (vm.selectedUnit() == 0) {
                $('#workplace-tree-grid').ntsTreeComponent(vm.kcp004Options);
            }
        }

        mounted() {
            const vm = this;
            vm.selectedUnit.subscribe(value => {
                if (value == 0) {
                    $('#workplace-tree-grid').ntsTreeComponent(vm.kcp004Options);
                }
            });
        }
    }

    interface Params {
        init: number | KnockoutObservable<number>; // WORKPLACE = 0, WORKPLACE GROUP = 1
        selectType: SelectType | KnockoutObservable<SelectType>;
        baseDate: string | Date | KnockoutObservable<string> | KnockoutObservable<Date>;
        alreadySettingWorkplaces: KnockoutObservableArray<{workplaceId: string, isAlreadySetting: boolean}>;
        alreadySettingWorkplaceGroups: KnockoutObservableArray<string>;
        selectedIds: KnockoutObservableArray<string>;
    }

    enum SelectType {
        SELECT_BY_SELECTED_ID = 1,
        SELECT_ALL = 2,
        SELECT_FIRST_ITEM = 3,
        NO_SELECT = 4
    }

    enum SELECTED_MODE {
        NONE = 0,
        FIRST = 1,
        ALL = 2,
        SELECT_ID = 3
    }
}