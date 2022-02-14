/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmt013.a {
    import flat = nts.uk.util.flatArray;
    import getShared = nts.uk.ui.windows.getShared;
    import setShared = nts.uk.ui.windows.setShared;
    import modal = nts.uk.ui.windows.sub.modal;
    const PATH = {
        init: 'at/shared/scherec/workmanagement/work/kmt001/init',
        saveRegistrationWork: 'at/shared/scherec/workmanagement/work/kmt001/register',
        updateRegistrationWork: 'at/shared/scherec/workmanagement/work/kmt001/update',
        deleteRegistrationWork: 'at/shared/scherec/workmanagement/work/kmt001/delete',
        // getRegistrationWork: 'at/shared/scherec/workmanagement/work/kmt001/find',
        getTaskList: 'at/shared/scherec/taskmanagement/task/kmt009/getlist',
        getAllSetWkps: "at/shared/scherec/taskmanagement/task/kmt010/getAlreadySetWkps",
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
        switchOptions: KnockoutObservableArray<any>;

        a3_1: KnockoutObservable<string> = ko.observable(null);
        a3_2: KnockoutObservable<string> = ko.observable(null);
        a4_1: KnockoutObservable<string> = ko.observable(null);

        updateMode: KnockoutObservable<boolean>;
        alreadySettingList: KnockoutObservableArray<UnitAlreadySettingModel> = ko.observableArray([]);
        multiSelectedId: KnockoutObservable<any> = ko.observable(null);

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

            vm.a3_1 = ko.observable(vm.$i18n('Com_Workplace') + ': ');
            vm.a3_2 = ko.observable('');
            vm.a4_1 = ko.observable(vm.$i18n('応援可能') + vm.$i18n('Com_Workplace') + vm.$i18n('リスト'));

            vm.updateMode = ko.computed(() => {
                return vm.alreadySettingList().map(i => i.workplaceId).indexOf(vm.multiSelectedId()) >= 0;
            });
        }

        mounted() {
            const vm = this;

            $('#kcp017-switch input').change(() => {
                let isWplGrMode = $('#kcp017-switch input')[1].getAttribute('checked') == 'checked';
                vm.isWorkplaceGroupMode(isWplGrMode);
                if (isWplGrMode) {
                    vm.a3_1(vm.$i18n('Com_WorkplaceGroup'));
                    vm.a4_1(vm.$i18n('応援可能') + vm.$i18n('Com_WorkplaceGroup') + vm.$i18n('リスト'));
                    vm.a3_2(vm.selectedWkpGroup().workplaceGroupName);
                } else {
                    vm.a3_1(vm.$i18n('Com_Workplace') + ': ');
                    vm.a4_1(vm.$i18n('応援可能') + vm.$i18n('Com_Workplace') + vm.$i18n('リスト'));
                    vm.a3_2(vm.selectedWkp().workplaceName);
                }
            })

            vm.selectedWkpId.subscribe((newValue) => {
                if (typeof ko.dataFor($("#workplace-tree-grid")[0]).itemList === 'function') {
                    let result: any = {};
                    const workplaces: Array<any> = ko.dataFor($("#workplace-tree-grid")[0]).itemList();
                    const flwps = flat(_.cloneDeep(workplaces), "children");
                    const itemWpl = _.find(flwps, (o) => o.id === vm.selectedWkpId());
                    result.workplaceCode = itemWpl.code ?? '';
                    result.workplaceID = itemWpl.id ?? '';
                    result.workplaceName = itemWpl.name ?? '';
                    vm.selectedWkp(result);
                    !vm.isWorkplaceGroupMode() && vm.a3_2(result.workplaceName);
                }
            });

            vm.selectedWkpGroupId.subscribe((newValue) => {
                let result: any = {};
                const workplaceGroups: Array<any> = ko.dataFor($("#workplace-group-pannel")[0]).workplaceGroups();
                const itemWplGr = _.find(workplaceGroups, (o) => o.id === vm.selectedWkpGroupId());
                result.workplaceGroupCode = itemWplGr.code ?? '';
                result.workplaceGroupID = itemWplGr.id ?? '';
                result.workplaceGroupName = itemWplGr.name ?? '';
                vm.selectedWkpGroup(result);
                vm.isWorkplaceGroupMode() && vm.a3_2(result.workplaceGroupName);
            });

        }

        fn1st() {
            console.log('1st');
        }

        fn2nd() {
            const vm = this;
            console.log('2nd')
        }

        fn3rd() {
            // const vm = this;
            console.log('3rd')
        }

        getAlreadySettingList() {
            const vm = this;
            vm.$blockui("show");
            vm.$ajax(PATH.getAllSetWkps).done((data: Array<string>) => {
                console.log(data)
                // vm.alreadySettingList(data.map(id => ({workplaceId: id, isAlreadySetting: true})));
                vm.alreadySettingWorkplaces(data.map(id => ({workplaceId: id, isAlreadySetting: true})))
            }).fail(error => {
                vm.$dialog.error(error);
            }).always(() => {
                vm.$blockui("hide");
            });
        }

        openDialogCDL023() {
            const vm = this;
            let params: IObjectDuplication = {
                code: vm.selectedWkpGroup().workplaceGroupCode,
                name: vm.selectedWkpGroup().workplaceGroupName,
                targetType: TargetType.WORKPLACE,
                baseDate: moment('YYYY/MM/DD').toDate(),
                itemListSetting: vm.alreadySettingList().map(w => w.workplaceId),
            };

            setShared("CDL023Input", params);
            // open dialog
            modal('com', 'view/cdl/023/a/index.xhtml').onClosed(() => {
                console.log('closed')
                let lstSelection: Array<string> = getShared("CDL023Output");
                let data = getShared("CDL023Output");
                console.log(data)
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
                selectedSystemType:2 ,
                isrestrictionOfReferenceRange:true ,
                showNoSelection:false ,
                isShowBaseDate:true });
            modal('com',"/view/cdl/008/a/index.xhtml").onClosed(function(){
                let data = getShared('outputCDL008');
                let baseDate = getShared('baseDateCDL008');
                console.log(data)

            });
        }

        openDialogCDL014() {
            console.log('Open CDL014 Dialog')
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