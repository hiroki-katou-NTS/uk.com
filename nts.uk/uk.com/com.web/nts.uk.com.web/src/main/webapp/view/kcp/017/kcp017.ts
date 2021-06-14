module nts.uk.com.view.kcp017.a.viewmodel {

    const template = `
    <div id="kcp017-component" 
        class="panel" 
        style="display: inline-block; width: 100%;" 
        data-bind="css: {
            ntsPanel: !onDialog(), 
            'caret-right': !onDialog(), 
            'caret-background': !onDialog()
        }">
        <div id="switch-area" class="control-group valign-center">
            <div data-bind="ntsFormLabel: {text: $i18n('KCP017_2')}"/>
            <div id="kcp017-switch" data-bind="ntsSwitchButton: {
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
        <div data-bind="visible: selectedUnit() == 0">
            <div id="workplace-tree-grid"/>
        </div>
        <div data-bind="visible: selectedUnit() == 1">
            <div data-bind="component: {
                name: 'workplace-group',
                params: {
                    options: kcp011Options
                }
            }"/>
        </div>
        <i class="icon icon-searchbox" data-bind="visible: !onDialog()"></i>
    </div>
    <style>
      #kcp017-switch label span {
        width: 120px;
        display: flex;
        justify-content: center;
        align-items: center;
      }
      
      #switch-area {
        display: flex;
        width: 91%;
        padding-bottom: 10px;
        border-bottom: 1px solid gray;
      }
    </style>`;

    @component({
        name: 'kcp017-component',
        template: template
    })
    class ViewModel extends ko.ViewModel {
        selectedUnit: KnockoutObservable<number>;
        baseDate: KnockoutObservable<any>;
        selectType: KnockoutObservable<number | SelectType>; // kcp004
        selectMode: KnockoutObservable<number | SELECTED_MODE>; // kcp011
        onDialog: KnockoutObservable<boolean>;
        multiple: KnockoutObservable<boolean>;
        workplaceRows: KnockoutObservable<number>;
        workplaceGroupRows: KnockoutObservable<number>;
        showAlreadySetting: KnockoutObservable<boolean>;
        multipleUsage?: KnockoutObservable<boolean>; // KCP004, default: false
        isShowSelectButton?: KnockoutObservable<boolean>; // KCP004, default: true
        showEmptyItem?: KnockoutObservable<boolean>;

        alreadySettingWorkplaces: KnockoutObservableArray<any>;
        alreadySettingWorkplaceGroups: KnockoutObservableArray<any>;
        selectedIds: KnockoutObservable<any> | KnockoutObservableArray<any>;
        selectedGroupIds: KnockoutObservable<any> | KnockoutObservableArray<any>;

        kcp011Options: any;
        kcp004Options: any;

        created(params: Params) {
            const vm = this;
            if (params) {
                vm.selectedUnit = ko.isObservable(params.unit) ? params.unit : ko.observable(params.unit || 0);
                vm.baseDate = ko.isObservable(params.baseDate) ? params.baseDate : ko.observable(params.baseDate || new Date());
                vm.alreadySettingWorkplaces = params.alreadySettingWorkplaces;
                vm.alreadySettingWorkplaceGroups = params.alreadySettingWorkplaceGroups;
                vm.selectedIds = params.selectedWorkplaces;
                vm.selectedGroupIds = params.selectedWorkplaceGroups;
                vm.selectType = ko.observable(params.selectType || SelectType.SELECT_FIRST_ITEM);
                vm.onDialog = ko.observable(_.isNil(params.onDialog) ? false : params.onDialog);
                vm.multiple = ko.observable(_.isNil(params.multiple) ? false : params.multiple);
                vm.showAlreadySetting = ko.observable(_.isNil(params.showAlreadySetting) ? false : params.showAlreadySetting);
                vm.workplaceRows = ko.observable(params.workplaceRows || 10);
                vm.workplaceGroupRows = ko.observable(params.workplaceGroupRows || 10);
                vm.multipleUsage = ko.observable(_.isNil(params.multipleUsage) ? false : params.multipleUsage);
                vm.isShowSelectButton = ko.observable(_.isNil(params.isShowSelectButton) ? true : params.isShowSelectButton);
                vm.showEmptyItem = ko.observable(_.isNil(params.showEmptyItem) ? false : params.showEmptyItem);
            } else {
                vm.selectedUnit = ko.observable(0);
                vm.baseDate = ko.observable(new Date());
                vm.alreadySettingWorkplaces = ko.observableArray([]);
                vm.alreadySettingWorkplaceGroups = ko.observableArray([]);
                vm.selectedIds = ko.observable(null);
                vm.selectedGroupIds = ko.observable(null);
                vm.selectType = ko.observable(SelectType.SELECT_FIRST_ITEM);
                vm.onDialog = ko.observable(false);
                vm.multiple = ko.observable(false);
                vm.showAlreadySetting = ko.observable(false);
                vm.workplaceRows = ko.observable(10);
                vm.workplaceGroupRows = ko.observable(10);
                vm.multipleUsage = ko.observable(false);
                vm.isShowSelectButton = ko.observable(true);
                vm.showEmptyItem = ko.observable(false);
            }
            vm.selectMode = ko.computed(() => {
                if (vm.selectType() == SelectType.SELECT_FIRST_ITEM) return SELECTED_MODE.FIRST;
                if (vm.selectType() == SelectType.NO_SELECT) return SELECTED_MODE.NONE;
                if (vm.selectType() == SelectType.SELECT_ALL) return SELECTED_MODE.ALL;
                if (vm.selectType() == SelectType.SELECT_BY_SELECTED_ID) return SELECTED_MODE.SELECT_ID;
            });

            vm.kcp004Options = {
                isShowAlreadySet: vm.showAlreadySetting(),
                isMultipleUse: vm.multipleUsage(),
                isMultiSelect: vm.multiple(),
                startMode: 0, // WORKPLACE
                baseDate: vm.baseDate,
                selectType: vm.selectType(), // SELECT_FIRST_ITEM
                systemType: 2, // EMPLOYMENT
                isShowSelectButton: vm.isShowSelectButton(),
                isDialog: true,
                hasPadding: false,
                maxRows: vm.workplaceRows(),
                alreadySettingList: vm.alreadySettingWorkplaces,
                selectedId: vm.selectedIds,
                restrictionOfReferenceRange: false
            };
            vm.kcp011Options = {
                currentIds: vm.selectedGroupIds,
                alreadySettingList: vm.alreadySettingWorkplaceGroups,
                multiple: vm.multiple(),
                isAlreadySetting: vm.showAlreadySetting(),
                showPanel: false,
                showEmptyItem: vm.showEmptyItem(),
                reloadData: ko.observable(''),
                selectedMode: vm.selectMode(), // SELECT FIRST ITEM
                rows: vm.workplaceGroupRows()
            };
            $('#workplace-tree-grid').ntsTreeComponent(vm.kcp004Options);
        }

        mounted() {
            const vm = this;
            $($("#kcp017-switch button")[0]).width($($("#kcp017-switch button")[1]).width());
            $("#workplace-group-pannel").ready(() => {
              $("#workplace-group-pannel input.ntsSearchBox").css("width", "160px");
            })
            $("#nts-component-tree").ready(() => {
              $("#nts-component-tree input.ntsSearchBox").css("width", "160px");
            })
            vm.selectedUnit.subscribe(value => {
                if (value === 0) {
                  $("#nts-component-tree input.ntsSearchBox").css("width", "160px");
                }
                if (value == 1 && $("#workplace-group-pannel input.ntsSearchBox").width() == 0)
                  $("#workplace-group-pannel input.ntsSearchBox").css("width", "160px");
            });
        }
    }

    interface Params {
        unit?: number | KnockoutObservable<number>; // WORKPLACE = 0, WORKPLACE GROUP = 1
        onDialog?: boolean; // default: false
        multiple?: boolean; // default: false
        showAlreadySetting?: boolean; // default: false
        workplaceRows?: number; // default: 10
        workplaceGroupRows?: number; // default: 10
        selectType?: SelectType; // default: 3 (SELECT FIRST ITEM)
        baseDate?: string | Date; // default: today
        multipleUsage?: boolean; // KCP004, default: false
        isShowSelectButton?: boolean; // KCP004, default: true
        showEmptyItem?: boolean; // KCP011, default: false
        alreadySettingWorkplaces?: KnockoutObservableArray<{workplaceId: string, isAlreadySetting: boolean}>;
        alreadySettingWorkplaceGroups?: KnockoutObservableArray<string>;
        selectedWorkplaces: KnockoutObservableArray<any> | KnockoutObservable<any>;
        selectedWorkplaceGroups: KnockoutObservableArray<any> | KnockoutObservable<any>;
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