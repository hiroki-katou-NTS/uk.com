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
    const API = {
        findAllWkpManager: "at/auth/workplace/manager/findAll/",
        saveWkpManager: "at/auth/workplace/manager/save/",
        deleteWkpManager: "at/auth/workplace/manager/remove/",
        getEmpInfo: "ctx/sys/auth/grant/rolesetperson/getempinfo/"
    };
    const SHARE_IN_DIALOG_ADD_OR_UPDATE_HISTORY = 'SHARE_IN_DIALOG_ADD_UPDATE_HISTORY';

    @bean()
    class ViewModel extends ko.ViewModel {
        langId: KnockoutObservable<string> = ko.observable('ja');
        // KCP010
        kcp010Model: kcp010.viewmodel.ScreenModel;
        ntsHeaderColumns: KnockoutObservableArray<any> = ko.observableArray([]);
        historyId : KnockoutObservable<any> = ko.observable("1");
        // Screen mode
        isNewMode: KnockoutObservable<boolean>;
        //Date Range Picker
        dateValue: KnockoutObservable<any> = ko.observable("");
        selectedWkpId: KnockoutObservable<string>;
        selectedWpkManagerId: KnockoutObservable<string>;
        selectedWkpManager: KnockoutObservable<WorkplaceManager>;
        wkpManagerTree: any;
        selectedCode: any;
        headers: any;
        // CCG026
        component: ccg.component.viewmodel.ComponentModel;
        listPermission: KnockoutObservableArray<FunctionPermission>;
        // start declare KCP005
        listComponentOption: any;
        multiSelectedCode: KnockoutObservable<string>;
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
        mode:KnockoutObservable<number>= ko.observable(1);
        constructor(params: any) {
            super();
            let vm = this;
            vm.selectedWkpId = ko.observable('');
            // Initial listComponentOption
            vm.listComponentOption = {
                targetBtnText: nts.uk.resource.getText("KCP010_3"),
                tabIndex: 4
            };
            vm.selectedCode = ko.observable('');
            let listDatePeriod : any = [];
            let emps : any = [];
            for (let i = 0; i <10;i++){
                emps.push({
                    id: i,
                    code: i,
                    name: i,
                });
                listDatePeriod.push({
                    id: i,
                    display: "AAAAAAAAAA"
                })
            }
            vm.employInfors(emps);
            vm.dateHistoryList(listDatePeriod);

            vm.isNewMode = ko.observable(false);
            vm.KCP005_load();
            vm.ntsHeaderColumns = ko.observableArray([
                { headerText: '', key: 'code', hidden: true },
                { headerText: '', key: 'display', formatter: _.escape }
            ]);
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
            vm.multiSelectedCode = ko.observable("1");
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
                maxWidth: 320,
                maxRows: 15,
            };
            vm.multiSelectedCode.subscribe((e) => {

            });
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
        }

        private getWkpManagerList(wkpId: string, savedWkpMngId: string): JQueryPromise<void> {
            let vm = this;
            let dfd = $.Deferred<void>();

            return dfd.promise();
        }

        private initWkpManager() {
            let vm = this;
            nts.uk.ui.errors.clearAll();
            if (vm.isNewMode() == true) {
                vm.dateValue({});
                vm.component.roleId('');
                vm.component.roleId.valueHasMutated();
            } else {
                vm.dateValue({startDate: vm.selectedWkpManager().startDate, endDate: vm.selectedWkpManager().endDate});
                vm.component.roleId(vm.selectedWpkManagerId());
            }
            setTimeout(function () {
                $(".ntsStartDatePicker").focus();
            }, 100);
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
            vm.initWkpManager();
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
        openDialogA32(){
            let vm = this;
            let mode = vm.mode();
            if(mode == 1){
                vm.openDialogCDL009()
            }else if(mode ==0){
                vm.openCDL008Dialog()
            }

        }
        openDialogA62(){
            let vm = this;
            let mode = vm.mode();
            if(mode == 1){
                vm.openCDL008Dialog()
            }else if(mode ==0){
                vm.openDialogCDL009()

            }

        }
        // 社員選択 button
        openDialogCDL009() {
            let vm = this;

            setShared('CDL009Params', {
                isMultiSelect: false,
                baseDate: moment(new Date()).toDate(),
                target: 1
            }, true);

            modal("/view/cdl/009/a/index.xhtml").onClosed(function () {
                var isCancel = getShared('CDL009Cancel');
                if (isCancel) {
                    return;
                }
                var employeeId = getShared('CDL009Output');
                vm.getEmployeeInfo(employeeId);
            });
        }

        private getEmployeeInfo(empId: string) {
            let vm = this;
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
            let _self = this;
            let dataToScreenB = {
                isCreate: true,
                isUpdate: false
            };
            nts.uk.ui.windows.setShared(SHARE_IN_DIALOG_ADD_OR_UPDATE_HISTORY,dataToScreenB);
            nts.uk.ui.windows.sub.modal('/view/cmm/051/b/index.xhtml').onClosed(() => {

            });
        }
        /**
         * Screen E - openUpdateHistoryDialog
         */
        public openUpdateHistoryDialog() {
            let _self = this;
            let dataToScreenB = {
                isCreate: false,
                isUpdate: true
            };
            nts.uk.ui.windows.setShared(SHARE_IN_DIALOG_ADD_OR_UPDATE_HISTORY,dataToScreenB);
            nts.uk.ui.windows.sub.modal('/view/cmm/051/b/index.xhtml').onClosed(() => {

            });
        }
        public removeHistory(): void {
            let _self = this;
                nts.uk.ui.dialog.confirm({ messageId: "Msg_18" })
                    .ifYes(() => {
                        nts.uk.ui.block.grayout();
                    })
                    .ifNo(() => {
                    });
        }
        openCDL008Dialog(): void {
            const vm = this;
            const inputCDL008: any = {
                startMode: StartMode.WORKPLACE,
                isMultiple: true,
                showNoSelection: false,
                selectedCodes: vm.workPlaceIdList(),
                isShowBaseDate: true,
                baseDate: moment.utc().toISOString(),
                selectedSystemType: SystemType.EMPLOYMENT,
                isrestrictionOfReferenceRange: true
            };
            setShared('inputCDL008', inputCDL008);
            modal('/view/cdl/008/a/index.xhtml').onClosed(() => {
                const isCancel = getShared('CDL008Cancel');
                if (isCancel) {
                    return;
                }
                vm.workPlaceIdList(getShared('outputCDL008'));

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

}