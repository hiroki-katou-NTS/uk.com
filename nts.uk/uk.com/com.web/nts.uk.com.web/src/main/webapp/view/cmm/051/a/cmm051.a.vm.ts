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

        deleteWkpManager: "at/auth/cmm051/workplace/manager/delete",
        deleteWkpHist: "at/auth/cmm051/workplace/manager/delete-history",
        addWkpManager: "at/auth/cmm051/workplace/manager/add",
        addHistWkpManager: "at/auth/cmm051/workplace/manager/register",


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
        isNewModeHist: KnockoutObservable<boolean> = ko.observable(true);
        isUpdateModeHist: KnockoutObservable<boolean> = ko.observable(true);
        isDeleteModeHist: KnockoutObservable<boolean> = ko.observable(true);
        isClicked: KnockoutObservable<boolean> = ko.observable(false);
        isDelete: KnockoutObservable<boolean> = ko.observable(false);
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
        dateHistoryListFull: KnockoutObservableArray<any> = ko.observableArray([]);
        workPlaceIdList: KnockoutObservableArray<string> = ko.observableArray([]);
        workPlaceList: KnockoutObservableArray<string> = ko.observableArray([]);
        mode: KnockoutObservable<number> = ko.observable(1);
        //A8
        workplaceCode: KnockoutObservable<string> = ko.observable("");
        workplaceName: KnockoutObservable<string> = ko.observable("");
        wplaceMnSelectedId: KnockoutObservable<string> = ko.observable("");
        employeeMnSelectedId: KnockoutObservable<string> = ko.observable("");
        //A3
        employeeCode: KnockoutObservable<string> = ko.observable("");
        employeeName: KnockoutObservable<string> = ko.observable("");

        selectedCode: KnockoutObservable<string>;
        wplaceSelectedId: KnockoutObservable<string> = ko.observable("");
        columns: KnockoutObservableArray<NtsGridListColumn>;
        count: number;
        idAddOrUpdate: KnockoutObservable<string> = ko.observable(null);
        startDate :KnockoutObservable<any> = ko.observable(null);
        endDate   :KnockoutObservable<any> = ko.observable(null);
        idDelete : KnockoutObservable<any> = ko.observable(null);

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
            vm.initScreen(Mode.WPL,null);
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

        initScreen(mode: number,sid:string) {
            let vm = this;
            if (mode == Mode.WPL) {
                let workplaceManagerList: any[] = [];
                let listEmployee: any[] = [];
                let personList: any[] = [];

                block.invisible();
                vm.$ajax('com', API.getDataInit).done((data) => {
                    if (!isNullOrUndefined(data)) {
                        if (!isNullOrUndefined(data.workplaceInfoImport)) {
                            vm.wplaceMnSelectedId(data.workplaceInfoImport.workplaceId);
                            vm.workplaceCode(data.workplaceInfoImport.workplaceCode);
                            vm.workplaceName(data.workplaceInfoImport.workplaceName);
                        }
                        workplaceManagerList = data.employeeInformation.workplaceManagerList;
                        listEmployee = data.employeeInformation.listEmployee;
                        personList = data.employeeInformation.personList;
                        vm.setData(mode, workplaceManagerList, listEmployee, personList,sid);
                    }
                }).always(() => {
                    block.clear();
                }).fail((error) => {
                    nts.uk.ui.block.clear();
                    nts.uk.ui.dialog.alertError({messageId: error.messageId}).then(() => {
                        vm.backToTopPage();
                    });
                })
            }
            if (mode == Mode.EMPLOYMENT) {

            }
        }
        backToTopPage() {
            nts.uk.request.jump("/view/ccg/008/a/index.xhtml");
        }
        setData(mode: any, workplaceManagerList: any[], listEmployee: any[], personList: any[],sid:string): void {
            let vm = this;
            if (mode == Mode.WPL) {
                let emps: any = [];
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
                vm.employInfors(emps);
                vm.dateHistoryListFull(workplaceManagerList);
                let info = _.find(vm.employInfors(), (e) => e.id == sid);

                if(!isNullOrUndefined(info)){
                    vm.multiSelectedCode(info.code);
                }else {
                    if (!isNullOrEmpty(emps)) {
                        vm.multiSelectedCode(emps[0].code);
                    }
                }
                vm.KCP005_load();
            }
            if (mode == Mode.EMPLOYMENT) {
            }

        }
        setDataHist(sid: string,workplaceManagerList :any[]):void {
            let vm = this;
            let listDatePeriod: any[] = [];
            let listHist :any[] = _.filter(workplaceManagerList, (e) => e.employeeId == sid);
            if (!isNullOrEmpty(workplaceManagerList)) {
                for (let i = 0; i < listHist.length; i++) {
                    let wpl = listHist[i];
                    let id = wpl.workplaceManagerId;
                    let display = wpl.startDate + " - " + wpl.endDate;
                    listDatePeriod.push({
                        id: id,
                        sid: wpl.employeeId,
                        display: display,
                        startDate: wpl.startDate,
                        endDate: wpl.endDate
                    })
                }
            }
            vm.dateHistoryList(listDatePeriod);
            if (!isNullOrEmpty(listDatePeriod)) {
                vm.historyId(listDatePeriod[0].id);
                vm.isNewModeHist(true);
                vm.isUpdateModeHist(true);
                vm.isDeleteModeHist(true);
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
                maxWidth: 480,
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
                vm.initScreen(mode,null);
            });
            vm.multiSelectedCode.subscribe((e) => {
                let eminfo = _.find(vm.employInfors(), (i) => i.code == e);
                if (!isNullOrUndefined(eminfo)) {
                    vm.employeeCode(eminfo.code);
                    vm.employeeName(eminfo.name);
                    vm.isDelete(true);
                    vm.employeeMnSelectedId(eminfo.id);
                    vm.setDataHist(eminfo.id,vm.dateHistoryListFull());
                }else {
                    vm.employeeCode("");
                    vm.employeeName("")
                }

            });
            vm.wplaceSelectedId.subscribe((e) => {

            });
            vm.historyId.subscribe((id) => {
                let idAddOrUpdate = vm.idAddOrUpdate();
                if (!isNullOrUndefined(idAddOrUpdate)) {
                    if (id == idAddOrUpdate) {
                        if (vm.isClicked()) {
                            vm.isNewModeHist(false);
                            vm.isDeleteModeHist(false);
                            vm.isUpdateModeHist(true);
                        }
                    } else {
                        vm.isNewModeHist(false);
                        vm.isDeleteModeHist(false);
                        vm.isUpdateModeHist(false);
                    }
                }
            })
        }

        private initManager() {
            let vm = this;
            nts.uk.ui.errors.clearAll();
            if (vm.isNewMode() == true) {
                vm.dateHistoryList([]);
                vm.employeeCode("");
                vm.employeeName("")
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
            vm.isUpdateModeHist(false);
            vm.isDeleteModeHist(false);
            vm.isNewModeHist(true);
            vm.isDelete(false);
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
            let mode = vm.mode();
            if (mode == Mode.WPL) {
                let workplaceId = vm.wplaceMnSelectedId();
                let sid = vm.employeeMnSelectedId();
                let startDate = nts.uk.time.parseMoment(vm.startDate()).format();
                let endDate = nts.uk.time.parseMoment(vm.endDate()).format();
                let command = {
                    "workPlaceId": workplaceId,
                    "sid": sid,
                    "startDate":startDate,
                    "endDate":endDate
                };
                block.invisible();
                if(vm.isNewMode()){
                    vm.$ajax("com", API.addWkpManager, command).done(() => {
                            vm.initScreen(mode,sid);
                            vm.isNewMode(false);
                        }
                    ).always(() => {
                        block.clear();
                    }).fail((res) => {
                        nts.uk.ui.block.clear();
                        vm.showMessageError(res);
                    })
                }else {
                    let commandHist = {
                        "wkpManagerId":vm.historyId(),
                        "startDate":startDate,
                        "endDate":endDate
                    };
                    vm.$ajax("com", API.addHistWkpManager, commandHist).done(() => {
                            vm.initScreen(mode,sid);
                            vm.isNewMode(false);
                        }
                    ).always(() => {
                        block.clear();
                    }).fail((res) => {
                        nts.uk.ui.block.clear();
                        vm.showMessageError(res);
                    })
                }
            }
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
        remove() {
            let vm = this;
            let mode = vm.mode();
            if (mode == Mode.WPL) {
                // show message confirm
                nts.uk.ui.dialog.confirm({messageId: 'Msg_18'}).ifYes(() => {
                        let workplaceId = vm.wplaceMnSelectedId();
                        let sid = vm.employeeMnSelectedId();
                        let command = {
                            "workplaceId": workplaceId,
                            "sid": sid
                        };
                        block.invisible();
                        vm.$ajax("com", API.deleteWkpManager, command).done(() => {
                                let indexRemove = _.findIndex(vm.employInfors(),(e)=>e.id == sid);
                                let emif: any = "";
                                if(indexRemove == (vm.employInfors().length -1 )){
                                    emif =  vm.employInfors()[indexRemove - 1].id;
                                }else {
                                    emif =  vm.employInfors()[indexRemove + 1].id;
                                }
                                vm.initScreen(mode,emif);
                            }
                        ).always(() => {
                            block.clear();
                        }).fail((res) => {
                            nts.uk.ui.block.clear();
                            vm.showMessageError(res);
                        })
                    }
                )
            }
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
                            vm.employeeName(info.businessName);
                            vm.employeeMnSelectedId(eminfo.employeeId)
                        }
                    }
                }).always(() => {
                    block.clear();
                }).fail((res) => {
                    nts.uk.ui.block.clear();
                    vm.showMessageError(res);
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
            nts.uk.ui.windows.setShared("dataToScreenB", dataToScreenB);
            nts.uk.ui.windows.sub.modal('/view/cmm/051/b/index.xhtml').onClosed(() => {
                vm.isNewMode(true);
                vm.isClicked(true);
                vm.isNewModeHist(false);
                vm.isUpdateModeHist(true);
                vm.isDeleteModeHist(false);
                let prams = getShared('dataToScreenA');
                let display = prams.startDate + " - " + prams.endDate;
                vm.startDate(prams.startDate);
                vm.endDate(prams.endDate);
                let idNew = "idNew";
                vm.idAddOrUpdate(idNew);
                let hist = {
                    id: idNew,
                    sid : "",
                    display: display,
                    startDate: prams.startDate,
                    endDate: prams.endDate
                };
                let hists: any[] = vm.dateHistoryList();
                hists.push(hist);
                hists = _.orderBy(hists, ['startDate'], ['desc']);
                vm.dateHistoryList(hists);
                vm.historyId(idNew);
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
            let dataToScreenC = {
                isCreate: false,
                isUpdate: true,
                startDate: startDate,
                endDate: endDate
            };
            nts.uk.ui.windows.setShared("dataToScreenC", dataToScreenC);
            nts.uk.ui.windows.sub.modal('/view/cmm/051/c/index.xhtml').onClosed(() => {
                let prams = getShared('dataToScreenA');
                vm.isNewMode(false);
                if (!isNullOrUndefined(prams)) {
                    let hists: any[] = vm.dateHistoryList();
                    let id = vm.historyId();
                    let index = _.findIndex(hists, (e) => e.id == id);
                    if (index >= 0) {
                        let display = prams.startDate + " - " + prams.endDate;
                        vm.startDate(prams.startDate);
                        vm.endDate(prams.endDate);
                        let hist = {
                            id: id,
                            sid: hists[index].sid,
                            display: display,
                            startDate: prams.startDate,
                            endDate: prams.endDate
                        };
                        hists[index] = hist;
                        hists = _.orderBy(hists, ['startDate'], ['desc']);
                        vm.dateHistoryList(hists);
                        vm.isNewModeHist(false);
                        if (!vm.isNewMode()) {
                            vm.isDeleteModeHist(true);
                        }
                        if (id == vm.idAddOrUpdate()) {
                            vm.isDeleteModeHist(false);
                        }
                        vm.idAddOrUpdate(id);

                    }
                }
            });
        }

        public removeHistory(): void {
            let vm = this;
            nts.uk.ui.dialog.confirm({messageId: "Msg_18"})
                .ifYes(() => {
                    let id = vm.historyId();
                    if(!isNullOrUndefined(id)){
                        block.invisible();
                        let command = {
                            "wkpManagerId":id
                        };
                        vm.$ajax("com", API.deleteWkpHist, command).done(() => {
                            let info = _.find(vm.dateHistoryList(), (e) => e.id == id);
                                vm.initScreen(vm.mode(),info.sid);
                                vm.isNewMode(false);
                            }
                        ).always(() => {
                            block.clear();
                        }).fail((res) => {
                            nts.uk.ui.block.clear();
                            vm.showMessageError(res);
                        })
                    }
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
                    vm.wplaceMnSelectedId(workplaceInfor[0].id);
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
                            vm.setData(mode, workplaceManagerList, listEmployee, personList,null);
                        }
                    }).always(() => {
                        block.clear();
                    }).fail((res)=>{
                        nts.uk.ui.block.clear();
                        vm.showMessageError(res);
                    })
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