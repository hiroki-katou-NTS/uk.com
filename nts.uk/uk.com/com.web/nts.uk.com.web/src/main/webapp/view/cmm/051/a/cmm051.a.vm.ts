////// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />
module nts.uk.com.view.cmm051.a {
    import alert = nts.uk.ui.dialog.alert;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import FunctionPermission = base.FunctionPermission;
    import WorkplaceManager = base.WorkplaceManager;
    import ccg = nts.uk.com.view.ccg026;
    import model = nts.uk.com.view.ccg026.component.model;
    import block = nts.uk.ui.block;
    import errors = nts.uk.ui.errors;
    import isNullOrEmpty = nts.uk.util.isNullOrEmpty;
    import isNullOrUndefined = nts.uk.util.isNullOrUndefined;
    import NtsGridListColumn = nts.uk.ui.NtsGridListColumn;
    const API = {
        findAllWkpManager: "at/auth/workplace/manager/findAll/",
        saveWkpManager: "at/auth/workplace/manager/save/",
        deleteWkpManager: "at/auth/workplace/manager/remove/",
        getEmpInfo: "ctx/sys/auth/grant/rolesetperson/getempinfo/",

        getDataInit: "com/screen/cmm051/get-data-init",
        getListEmpByWpid: "com/screen/cmm051/get-employee-list-by-wplid",
        getListEmpInf: "com/screen/cmm051/get-data-init/employee-mode"

    };

    @bean()
    class ViewModel extends ko.ViewModel {
        langId: KnockoutObservable<string> = ko.observable('ja');
        // KCP010
        kcp010Model: kcp010.viewmodel.ScreenModel;
        ntsHeaderColumns: KnockoutObservableArray<any> = ko.observableArray([]);
        historyId: KnockoutObservable<any> = ko.observable("1");
        // Screen mode
        isNewMode: KnockoutObservable<boolean>;
        //Date Range Picker
        dateValue: KnockoutObservable<any> = ko.observable("");
        selectedWkpId: KnockoutObservable<string>;
        selectedWpkManagerId: KnockoutObservable<string>;
        selectedWkpManager: KnockoutObservable<WorkplaceManager>;
        wkpManagerTree: any;
        headers: any;
        // CCG026
        component: ccg.component.viewmodel.ComponentModel;
        listPermission: KnockoutObservableArray<FunctionPermission>;
        // start declare KCP005
        listComponentOption: any;
        multiSelectedCode: KnockoutObservable<string> = ko.observable("");
        isShowAlreadySet: KnockoutObservable<boolean>;
        alreadySettingPersonal: KnockoutObservableArray<any>;
        isDialog: KnockoutObservable<boolean>;
        isShowNoSelectRow: KnockoutObservable<boolean>;
        isMultiSelect: KnockoutObservable<boolean>;
        isShowWorkPlaceName: KnockoutObservable<boolean>;
        isShowSelectAllButton: KnockoutObservable<boolean>;
        disableSelection: KnockoutObservable<boolean>;
        employeeList: KnockoutObservableArray<any>;
        baseDate: KnockoutObservable<Date>;
        employInfors: KnockoutObservableArray<any> = ko.observableArray([]);
        listEmployee: KnockoutObservableArray<any> = ko.observableArray([]);
        dateHistoryList: KnockoutObservableArray<any> = ko.observableArray([]);
        workPlaceIdList: KnockoutObservableArray<string> = ko.observableArray([]);
        workPlaceList: KnockoutObservableArray<string> = ko.observableArray([]);
        mode: KnockoutObservable<number> = ko.observable(1);
        //A8
        workplaceCode: KnockoutObservable<string> = ko.observable("");
        workplaceName: KnockoutObservable<string> = ko.observable("");
        //A3
        employeeCode: KnockoutObservable<string> = ko.observable("");
        employeeName: KnockoutObservable<string> = ko.observable("");

        selectedCode: KnockoutObservable<string>;
        wplaceSelectedId: KnockoutObservable<string> = ko.observable("");
        columns: KnockoutObservableArray<NtsGridListColumn>;
        count : number ;

        constructor(params: any) {
            super();
            let vm = this;
            vm.selectedWkpId = ko.observable('');
            // Initial listComponentOption
            vm.listComponentOption = {
                targetBtnText: nts.uk.resource.getText("KCP010_3"),
                tabIndex: 4
            };
            vm.count = 1;
            vm.initScreen(Mode.WPL);
            vm.isNewMode = ko.observable(false);
            vm.ntsHeaderColumns = ko.observableArray([
                {headerText: '', key: 'code', hidden: true},
                {headerText: '', key: 'display', formatter: _.escape}
            ]);
            vm.columns = ko.observableArray([
                {headerText: '', key: 'id', hidden: true},
                {headerText: nts.uk.resource.getText("CMM051_44"), key: 'code', width: 100},
                {headerText: nts.uk.resource.getText("CMM051_45"), key: 'name', width: 300}
            ]);
        }

        initScreen(mode: number) {
            let vm = this;
            if (mode == Mode.WPL) {
                let workplaceManagerList: any[] = [];
                let listEmployee: any[] = [];
                let personList: any[] = [];

                block.invisible();
                vm.$ajax('com', API.getDataInit).done((data) => {
                    if (!isNullOrUndefined(data)) {
                        if (!isNullOrUndefined(data.workplaceInfoImport)) {
                            vm.workplaceCode(data.workplaceInfoImport.workplaceCode);
                            vm.workplaceName(data.workplaceInfoImport.workplaceName);
                        }
                        workplaceManagerList = data.employeeInformation.workplaceManagerList;
                        listEmployee = data.employeeInformation.listEmployee;
                        personList = data.employeeInformation.personList;
                        vm.setData(mode, workplaceManagerList, listEmployee, personList);
                    }
                }).always(() => {
                    block.clear();
                }).fail(() => {
                })
            }
            if (mode == Mode.EMPLOYMENT) {

            }
        }

        setData(mode: any, workplaceManagerList: any[], listEmployee: any[], personList: any[]): void {
            let vm = this;
            if (mode == Mode.WPL) {
                let emps: any = [];
                let listDatePeriod: any = [];
                if (!isNullOrEmpty(personList) && !isNullOrEmpty(listEmployee)) {
                    for (let i = 0; i < listEmployee.length; i++) {
                        let em = listEmployee[i];
                        let info = _.find(personList, (e) => e.pid == em.personId);
                        if (!isNullOrUndefined(info)) {
                            emps.push({
                                id: em.employeeId,
                                code: em.employeeCode,
                                name: info.businessName,
                            });
                        }

                    }
                }
                if (!isNullOrEmpty(workplaceManagerList)) {
                    for (let i = 0; i < workplaceManagerList.length; i++) {
                        let wpl = workplaceManagerList[i];
                        let id = wpl.workplaceManagerId;
                        let display = wpl.startDate + " - " + wpl.endDate;
                        listDatePeriod.push({
                            id: id,
                            display: display,
                            startDate: wpl.startDate,
                            endDate: wpl.endDate
                        })
                    }

                }
                vm.dateHistoryList(listDatePeriod);
                if (!isNullOrEmpty(listDatePeriod)) {
                    vm.historyId(listDatePeriod[0].id)
                }

                vm.employInfors(emps);
                vm.KCP005_load();
                if (!isNullOrEmpty(emps)) {
                    vm.multiSelectedCode(emps[0].code);
                }
            }
            if (mode == Mode.EMPLOYMENT) {

            }


        }

        KCP005_load() {
            let vm = this;
            vm.baseDate = ko.observable(new Date());
            vm.isShowAlreadySet = ko.observable(false);
            vm.alreadySettingPersonal = ko.observableArray([]);
            vm.isDialog = ko.observable(false);
            vm.isShowNoSelectRow = ko.observable(false);
            vm.isMultiSelect = ko.observable(false);
            vm.isShowWorkPlaceName = ko.observable(false);
            vm.isShowSelectAllButton = ko.observable(false);
            vm.disableSelection = ko.observable(false);
            vm.selectedCode = ko.observable("");
            vm.listComponentOption = {
                isShowAlreadySet: false,
                isMultiSelect: false,
                listType: ListType.EMPLOYEE,
                employeeInputList: vm.employInfors,
                selectType: SelectType.SELECT_ALL,
                selectedCode: vm.multiSelectedCode,
                isDialog: false,
                alreadySettingList: vm.alreadySettingPersonal,
                isShowSelectAllButton: false,
                showOptionalColumn: false,
                maxWidth: 400,
                maxRows: 15,
            };
            $('#kcp005').ntsListComponent(vm.listComponentOption)
        }

        // Start page
        created() {
            let vm = this;
            var dfd = $.Deferred();
            // CCG026
            vm.component = new ccg.component.viewmodel.ComponentModel({
                classification: 1,
                maxRow: 5
            });
            vm.component.startPage().done(() => {
            });
            dfd.resolve();
            return dfd.promise();
        }

        mounted() {
            let vm = this;
            vm.mode.subscribe((mode) => {
                console.log("MODE :" + mode);
                vm.initScreen(mode);
            });
            vm.multiSelectedCode.subscribe((e) => {
                let eminfo = _.find(vm.employInfors(), (i) => i.code == e);
                if (!isNullOrUndefined(eminfo)) {
                    vm.employeeCode(eminfo.code);
                    vm.employeeName(eminfo.name)
                }
            });
            vm.wplaceSelectedId.subscribe((e) => {

            });

        }

        private getWkpManagerList(wkpId: string, savedWkpMngId: string): JQueryPromise<void> {
            let vm = this;
            let dfd = $.Deferred<void>();

            return dfd.promise();
        }

        private initManager() {
            let vm = this;
            nts.uk.ui.errors.clearAll();
            if (vm.isNewMode() == true) {
                vm.dateHistoryList([]);
                vm.employeeCode("");
                vm.employeeName("")
            } else {
                vm.dateValue({startDate: vm.selectedWkpManager().startDate, endDate: vm.selectedWkpManager().endDate});
                vm.component.roleId(vm.selectedWpkManagerId());
            }

        }

        /**
         * Button on screen
         */
        // 新規 button
        createWkpManager() {
            let vm = this;
            nts.uk.ui.errors.clearAll();
            vm.isNewMode(true);
            vm.selectedCode('');
            vm.initManager();
        }

        // 登録 button
        saveWkpManager() {
            let vm = this;

            // validate
            if (!vm.validate()) {
                return;
            }

            // get JsObject
            let command: any = vm.toJsonObject();
            nts.uk.ui.block.grayout();
            // insert or update workplace
            vm.$ajax('com', API.saveWkpManager, ko.toJS(command)).done(function (savedWkpMngId) {
                nts.uk.ui.block.clear();

                // notice success
                nts.uk.ui.dialog.info({messageId: "Msg_15"}).then(() => {
                    // Get workplace manager list
                    vm.getWkpManagerList(vm.selectedWkpId(), savedWkpMngId);
                });
            }).fail((res: any) => {
                nts.uk.ui.block.clear();
                vm.showMessageError(res);
            });
        }

        /**
         * validate
         */
        private validate() {
            let vm = this;

            // clear error
            vm.clearError();

            // validate
            $(".ntsDatepicker ").ntsEditor('validate');
            $(".nts-editor").trigger("validate");
            return !$('.nts-input').ntsError('hasError');
        }

        /**
         * clearError
         */
        private clearError() {
            nts.uk.ui.errors.clearAll();
        }

        // 削除 button
        delWkpManager() {
            let vm = this;
            let nodeList: Array<Node> = vm.wkpManagerTree();
            let currentNodeIndex = _.findIndex(nodeList, x => x.wkpManagerId == vm.selectedWkpManager().employeeId);
            if (currentNodeIndex < 0) {
                nts.uk.ui.dialog.alert('エラーがあります');
                return;
            }
            let currentNode = nodeList[currentNodeIndex];
            let lastNodeIndex = nodeList.length - 1;
            let currentItemList: Array<WorkplaceManager> = currentNode.childs;
            let currentItemIndex = _.findIndex(currentItemList, x => x.wkpManagerId == vm.selectedWkpManager().wkpManagerId);
            let lastItemIndex = currentItemList.length - 1;
            let isFinalElement = currentItemList.length - 1 == 0 ? true : false;

            // show message confirm
            nts.uk.ui.dialog.confirm({messageId: 'Msg_18'}).ifYes(() => {
                }
            );
        }

        openDialogA3282() {
            let vm = this;
            let mode = vm.mode();
            if (mode == 1) {
                vm.openCDL008Dialog()
            } else if (mode == 0) {
                vm.openDialogCDL009()
            }

        }

        openDialogA62102() {
            let vm = this;
            let mode = vm.mode();
            if (mode == 1) {
                vm.openDialogCDL009()
            } else if (mode == 0) {
                vm.openCDL008Dialog()
            }

        }

        // 社員選択 button
        openDialogCDL009() {
            let vm = this;

            setShared('CDL009Params', {
                isMultiSelect: false,
                baseDate: moment(new Date()).toDate(),
                selectedCode: vm.multiSelectedCode(),
                target: 1
            }, true);

            modal("/view/cdl/009/a/index.xhtml").onClosed(function () {
                let isCancel = getShared('CDL009Cancel');
                if (isCancel) {
                    return;
                }
                let employeeId = getShared('CDL009Output');
                let sids: any[] = [];
                if (!isNullOrUndefined(employeeId)) {
                    sids.push(employeeId);
                    vm.getEmployeeInfo(sids);
                }
            });
        }

        private getEmployeeInfo(empId: string[]) {
            let vm = this;
            if (!isNullOrEmpty(empId)) {
                let param: any = {
                    "employIds": empId
                };
                block.invisible();
                vm.$ajax("com", API.getListEmpInf, param).done((data) => {
                    if (!isNullOrUndefined(data)) {
                        let eminfos = data.listEmployee;
                        let personList: any[] = data.personList;
                        if (!isNullOrEmpty(eminfos) && !isNullOrEmpty(personList)) {
                            let eminfo = eminfos[0];
                            let info = _.find(personList, (e) => e.pid == eminfo.personId);
                            vm.employeeCode(eminfo.employeeCode);
                            vm.employeeName(info.businessName)
                        }
                    }
                }).always(() => {
                    block.clear();
                }).fail(() => {

                })
            }
        }

        /**
         * Common
         */
        /**
         * showMessageError
         */
        private showMessageError(res: any) {
            if (res.businessException) {
                nts.uk.ui.dialog.alertError({messageId: res.messageId, messageParams: res.parameterIds});
            }
        }

        /**
         * toJsonObject
         */
        private toJsonObject(): any {
            let vm = this;

            // to JsObject
            let command: any = {};
            command.newMode = vm.isNewMode();
            command.startDate = new Date(vm.dateValue().startDate);
            command.endDate = new Date(vm.dateValue().endDate);
            command.employeeId = vm.selectedWkpManager().employeeId;
            command.wkpId = vm.selectedWkpId();
            command.wkpManagerId = vm.selectedWkpManager().wkpManagerId;
            command.roleList = vm.listPermission();

            return command;
        }

        /**
         * Screen D - openAddHistoryDialog
         */
        public openAddHistoryDialog() {
            let vm = this;
            let id = vm.historyId();
            let info = _.find(vm.dateHistoryList(), (e) => e.id == id);
            let startDate: any = "";
            let endDate: any = "";
            if (!isNullOrUndefined(info)) {
                startDate = info.startDate;
                endDate = info.endDate;
            }
            let dataToScreenB = {
                isCreate: true,
                isUpdate: false,
                startDate: startDate,
                endDate: endDate
            };
            let i =   vm.count;
            nts.uk.ui.windows.setShared("dataToScreenB", dataToScreenB);
            nts.uk.ui.windows.sub.modal('/view/cmm/051/b/index.xhtml').onClosed(() => {
                let prams = getShared('dataToScreenA');
                let display = prams.startDate + " - " + prams.endDate;
                let idNew ="idNew" + i;
                    let hist = {
                    id: idNew,
                    display: display,
                    startDate: prams.startDate,
                    endDate: prams.endDate
                };
                let hists: any[] = vm.dateHistoryList();
                hists.push(hist);
                vm.dateHistoryList(hists);
                vm.historyId(idNew);
                i++;
                vm.count = i;
            });
        }

        /**
         * Screen E - openUpdateHistoryDialog
         */
        public openUpdateHistoryDialog() {
            let vm = this;
            let id = vm.historyId();
            let info = _.find(vm.dateHistoryList(), (e) => e.id == id);
            let startDate: any = "";
            let endDate: any = "";
            if (!isNullOrUndefined(info)) {
                startDate = info.startDate;
                endDate = info.endDate;
            }
            let dataToScreenB = {
                isCreate: false,
                isUpdate: true,
                startDate: startDate,
                endDate: endDate
            };
            nts.uk.ui.windows.setShared("dataToScreenB", dataToScreenB);
            nts.uk.ui.windows.sub.modal('/view/cmm/051/b/index.xhtml').onClosed(() => {
                let prams = getShared('dataToScreenA');
                if (!isNullOrUndefined(prams)) {
                    let hists: any[] = vm.dateHistoryList();
                    let id = vm.historyId();
                    let index = _.findIndex(hists, (e) => e.id == id);
                    if (index > 0) {
                        let display = prams.startDate + " - " + prams.endDate;
                        let hist = {
                            id: id,
                            display: display,
                            startDate: prams.startDate,
                            endDate: prams.endDate
                        };
                        hists[index] = hist;
                        vm.dateHistoryList(hists);
                    }
                }
            });
        }

        public removeHistory(): void {
            let _self = this;
            nts.uk.ui.dialog.confirm({messageId: "Msg_18"})
                .ifYes(() => {
                    nts.uk.ui.block.grayout();
                })
                .ifNo(() => {
                });
        }

        openCDL008Dialog(): void {
            const vm = this;
            const inputCDL008: any = {
                startMode: StartMode.WORKPLACE,//  起動モード : 職場 (WORKPLACE = 0)
                isMultiple: false,////選択モード : 単一選択
                showNoSelection: false,// //未選択表示 : 非表示
                selectedCodes: vm.workplaceCode(),
                isShowBaseDate: true,//基準日表示区分 : 表示
                baseDate: moment.utc().toISOString(),
                selectedSystemType: SystemType.EMPLOYMENT,// //システム区分 : 就業
                isrestrictionOfReferenceRange: true//参照範囲の絞込: する
            };
            setShared('inputCDL008', inputCDL008);
            modal('/view/cdl/008/a/index.xhtml').onClosed(() => {
                const isCancel = getShared('CDL008Cancel');
                if (isCancel) {
                    return;
                }
                let wid = getShared('outputCDL008');
                let workplaceInfor = getShared('workplaceInfor');
                if (!isNullOrUndefined(wid) && !isNullOrEmpty(workplaceInfor)) {
                    vm.workplaceCode(workplaceInfor[0].code);
                    vm.workplaceName(workplaceInfor[0].name);
                    let wplParam: any = {
                        "workPlaceId": wid
                    }, workplaceManagerList = [], listEmployee = [], personList = [], mode = vm.mode();
                    block.invisible();

                    vm.$ajax('com', API.getListEmpByWpid, wplParam).done((data) => {
                        if (!isNullOrEmpty(data)) {
                            workplaceManagerList = data.workplaceManagerList;
                            listEmployee = data.listEmployee;
                            personList = data.personList;
                            vm.setData(mode, workplaceManagerList, listEmployee, personList);
                        }
                    }).always(() => {
                        block.clear();
                    }).fail()
                }
            });
        }
    }

    enum SystemType {
        PERSONAL_INFORMATION = 1,
        EMPLOYMENT = 2,
        SALARY = 3,
        HUMAN_RESOURCES = 4,
        ADMINISTRATOR = 5
    }

    enum StartMode {
        WORKPLACE = 0,
        DEPARTMENT = 1
    }

    /**
     * Interface ComponentOption of KCP010
     */
    export interface ComponentOption {
        targetBtnText: string;
        tabIndex: number;
    }

    class Node {
        wkpManagerId: string;
        code: string;
        name: string;
        nodeText: string;
        childs: any;

        constructor(code: string, name: string, childs: Array<WorkplaceManager>, parentId: string) {
            var vm = this;
            vm.wkpManagerId = parentId;
            vm.code = code;
            vm.name = name;
            vm.nodeText = vm.code + ' ' + vm.name;
            vm.childs = childs;
        }
    }

    export interface UnitModel {
        code: string;
        name?: string;
        id?: string;
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

    export class ListType {
        static EMPLOYMENT = 1;
        static Classification = 2;
        static JOB_TITLE = 3;
        static EMPLOYEE = 4;
    }

    export class Mode {
        static EMPLOYMENT = 0;
        static WPL = 1;
    }

}