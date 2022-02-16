module nts.uk.at.view.kmt013.a {
    import flat = nts.uk.util.flatArray;
    import getShared = nts.uk.ui.windows.getShared;
    import setShared = nts.uk.ui.windows.setShared;
    import modal = nts.uk.ui.windows.sub.modal;
    import modeless = nts.uk.ui.windows.sub.modeless;
    const PATH = {
        getAllSetWkps: "at/shared/scherec/taskmanagement/task/kmt010/getAlreadySetWkps",
        getAllRulesOfOrgShiftTable: 'screen/at/shift/table/setTissueList',
    };

    @bean()
    class ViewModel extends ko.ViewModel {

        unit: KnockoutObservable<number> = ko.observable(null); // WORKPLACE = 0, WORKPLACE GROUP = 1
        multiple: KnockoutObservable<boolean>;
        onDialog: KnockoutObservable<boolean>;
        showAlreadySetting: KnockoutObservable<boolean>;
        selectType: KnockoutObservable<number>;
        rows: KnockoutObservable<number>;
        baseDate: KnockoutObservable<any>;
        alreadySettingWorkplaces: KnockoutObservableArray<{workplaceId: string, isAlreadySetting: boolean}>;
        alreadySettingWorkplaceGroups: KnockoutObservableArray<string>;
        selectedWkpId: KnockoutObservable<any>;
        selectedWkpGroupId: KnockoutObservable<any>;

        isWorkplaceGroupMode: KnockoutObservable<boolean>;
        selectedWkp: KnockoutObservable<any>;
        selectedWkpGroup: KnockoutObservable<any>;

        items: KnockoutObservableArray<any>;
        currentCode: KnockoutObservable<any>;
        currentCodeList: KnockoutObservableArray<any>;
        count: number = 100;

        a3_1Txt: KnockoutObservable<string> = ko.observable(null);
        a3_2Txt: KnockoutObservable<string> = ko.observable(null);
        a4_1Txt: KnockoutObservable<string> = ko.observable(null);

        updateMode: KnockoutObservable<boolean>;
        isA5Checked: KnockoutObservable<boolean>;
        isA2NotEmpty: KnockoutObservable<boolean>;
        enableA43Btn: KnockoutObservable<boolean>;

        created(params: any) {
            const vm = this;
            vm.getAlreadySettingList();

            vm.unit = ko.observable(0);
            vm.multiple = ko.observable(false);
            vm.onDialog = ko.observable(false);
            vm.showAlreadySetting = ko.observable(true);
            vm.selectType = ko.observable(3);
            vm.rows = ko.observable(14);
            vm.baseDate = ko.observable(new Date);
            vm.alreadySettingWorkplaces = ko.observableArray([]);
            vm.alreadySettingWorkplaceGroups = ko.observableArray([]);
            vm.selectedWkpId = ko.observable(null);
            vm.selectedWkpGroupId = ko.observable(null);

            vm.isWorkplaceGroupMode = ko.observable(false);
            vm.selectedWkp = ko.observable(null);
            vm.selectedWkpGroup = ko.observable(null);

            vm.items = ko.observableArray([]);
            vm.currentCode = ko.observable();
            vm.currentCodeList = ko.observableArray([]);

            vm.a3_1Txt = ko.observable(vm.$i18n('Com_Workplace') + ': ');
            vm.a3_2Txt = ko.observable('');
            vm.a4_1Txt = ko.observable(vm.$i18n('応援可能') + vm.$i18n('Com_Workplace') + vm.$i18n('リスト'));

            vm.updateMode = ko.computed(() => {
                if (vm.isWorkplaceGroupMode()) {
                    return vm.alreadySettingWorkplaceGroups().indexOf(vm.selectedWkpGroupId()) >= 0;
                }
                return vm.alreadySettingWorkplaces().map(i => i.workplaceId).indexOf(vm.selectedWkpId()) >= 0;
            });

            vm.isA5Checked = ko.computed(() => {
                return vm.currentCodeList().length > 0;
            })

            vm.isA2NotEmpty = ko.observable(true);

            vm.enableA43Btn = ko.computed(() => {
                return vm.isA2NotEmpty() && vm.isA5Checked();
            })

            vm.selectedWkpId.subscribe((newValue) => {
                if (typeof ko.dataFor($("#workplace-tree-grid")[0]).itemList === 'function') {
                    let result: any = {};
                    const workplaces: Array<any> = ko.dataFor($("#workplace-tree-grid")[0]).itemList();
                    vm.isA2NotEmpty(workplaces.length > 0);
                    const flwps = flat(_.cloneDeep(workplaces), "children");
                    const itemWpl = _.find(flwps, (o) => o.id === vm.selectedWkpId());
                    result.workplaceCode = itemWpl.code ?? '';
                    result.workplaceID = itemWpl.id ?? '';
                    result.workplaceName = itemWpl.name ?? '';
                    vm.selectedWkp(result);
                    !vm.isWorkplaceGroupMode() && vm.a3_2Txt(result.workplaceName);
                }
            });

            vm.selectedWkpGroupId.subscribe((newValue) => {
                let result: any = {};
                const workplaceGroups: Array<any> = ko.dataFor($("#workplace-group-pannel")[0]).workplaceGroups();
                vm.alreadySettingWorkplaceGroups(workplaceGroups);
                vm.isA2NotEmpty(workplaceGroups.length > 0);
                const itemWplGr = _.find(workplaceGroups, (o) => o.id === vm.selectedWkpGroupId());
                result.workplaceGroupCode = itemWplGr.code ?? '';
                result.workplaceGroupID = itemWplGr.id ?? '';
                result.workplaceGroupName = itemWplGr.name ?? '';
                vm.selectedWkpGroup(result);
                vm.isWorkplaceGroupMode() && vm.a3_2Txt(result.workplaceGroupName);
            });

        }

        mounted() {
            const vm = this;
            $("#A4_2").focus();

            $('#kcp017-switch input').change(() => {
                $("#A4_2").focus();
                let isWplGrMode = $('#kcp017-switch input')[1].getAttribute('checked') == 'checked';
                vm.isWorkplaceGroupMode(isWplGrMode);
                if (isWplGrMode) {
                    vm.a3_1Txt(vm.$i18n('Com_WorkplaceGroup') + ': ');
                    vm.a4_1Txt(vm.$i18n('応援可能') + vm.$i18n('Com_WorkplaceGroup') + vm.$i18n('リスト'));
                    vm.a3_2Txt(vm.selectedWkpGroup().workplaceGroupName);
                } else {
                    vm.a3_1Txt(vm.$i18n('Com_Workplace') + ': ');
                    vm.a4_1Txt(vm.$i18n('応援可能') + vm.$i18n('Com_Workplace') + vm.$i18n('リスト'));
                    vm.a3_2Txt(vm.selectedWkp().workplaceName);
                }
            })
        }

        registerSupport() {
            const vm = this;
        }

        deleteSupport() {
            const vm = this;
        }

        getAlreadySettingList() {
            const vm = this;
            vm.$blockui("show");
            vm.$ajax(PATH.getAllSetWkps).done((data: Array<string>) => {
                vm.alreadySettingWorkplaces(data.map(id => ({workplaceId: id, isAlreadySetting: true})));
            }).fail(error => {
                vm.$dialog.error(error);
            }).always(() => {
                vm.$blockui("hide");
            });
        }

        openDialogCDL023() {
            const vm = this;
            let params: IObjectDuplication = {
                code: vm.selectedWkp().workplaceCode,
                name: vm.selectedWkp().workplaceName,
                targetType: TargetType.WORKPLACE,
                baseDate: new Date(),
                itemListSetting: vm.alreadySettingWorkplaces().map(w => w.workplaceId),
            };

            if (vm.isWorkplaceGroupMode()) {
                params.code = vm.selectedWkpGroup().workplaceGroupCode;
                params.name = vm.selectedWkpGroup().workplaceGroupName;
                params.itemListSetting = vm.alreadySettingWorkplaceGroups().map(w => w.workplaceGroupId);
            }

            setShared("CDL023Input", params);
            // open modal
            modal('com', 'view/cdl/023/a/index.xhtml').onClosed(() => {
                let lstSelection: Array<string> = getShared("CDL023Output");
                if (!_.isEmpty(lstSelection)) {
                    // open modeless b
                    vm.$window.modeless('at', '/view/kmt/013/b/index.xhtml', lstSelection).then(() => {
                        // business code when modal closed
                    });
                }
            });
        }

        a4_2BtnClick() {
            const vm = this;
            if (vm.isWorkplaceGroupMode()) {
                vm.openDialogCDL014();
            } else {
                vm.openDialogCDL008();
            }
        }

        openDialogCDL008() {
            const vm = this;
            setShared('inputCDL008', {
                selectedCodes: vm.selectedWkpId(),
                baseDate: moment(new Date()).toDate(),
                isMultiple: true,
                selectedSystemType: 2,
                isrestrictionOfReferenceRange: true,
                showNoSelection: false,
                isShowBaseDate: false
            });
            modal('com',"/view/cdl/008/a/index.xhtml").onClosed(function(){
                let data = getShared('outputCDL008');
                let baseDate = getShared('baseDateCDL008');
                console.log(data, baseDate)
            });
        }

        openDialogCDL014() {
            const vm = this;
            const request = {};
            setShared('inputCDL014', request);
            modal('com',"/view/cdl/014/a/index.xhtml").onClosed(function(){
                let response = getShared('outputCDL014');
                console.log(response)
            });
        }

        a4_3BtnClick() {
            const vm = this;
            console.log('a4_3 click')
        }

    }

    // Model Transfer
    interface IObjectDuplication {
        code: string;
        name: string;
        targetType: TargetType;
        itemListSetting: Array<string>;
        baseDate?: Date; // needed when target type: 職場 or 部門 or 職場個人 or 部門個人
        roleType?: number; // needed when target type: ロール
    }

    class TargetType {
        // 雇用
        static EMPLOYMENT = 1;
        // 分類
        static CLASSIFICATION = 2;
        // 職位
        static JOB_TITLE = 3;
        // 職場
        static WORKPLACE = 4;
        // 部門
        static DEPARTMENT = 5;
        // 職場個人
        static WORKPLACE_PERSONAL = 6;
        // 部門個人
        static DEPARTMENT_PERSONAL = 7;
        // ロール
        static ROLE = 8;
        // 勤務種別
        static WORK_TYPE = 9;
        //作業
        static  WORK = 10; //ver 6
    }

}