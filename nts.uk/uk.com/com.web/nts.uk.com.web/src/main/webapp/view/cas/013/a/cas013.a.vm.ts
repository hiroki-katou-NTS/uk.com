module nts.uk.com.view.cas013.a.viewmodel {
    import modal = nts.uk.ui.windows.sub.modal;
    import block = nts.uk.ui.block;
    import NtsGridListColumn = nts.uk.ui.NtsGridListColumn;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import dialog = nts.uk.ui.dialog;
    export class ScreenModel {
        //A51
        selectRoleCheckbox: KnockoutObservable<string>;

        langId: KnockoutObservable<string> = ko.observable('ja');
        // Metadata
        isCreateMode: KnockoutObservable<boolean> = ko.observable(false);
        isSelectedUser: KnockoutObservable<boolean> = ko.observable(false);
        isDelete: KnockoutObservable<boolean> = ko.observable(false);

        //ComboBOx RollType
        listRoleType: KnockoutObservableArray<RollType>;
        selectedRoleType: KnockoutObservable<string>;

        //list Roll
        listRole: KnockoutObservableArray<Role> = ko.observableArray([]);
        selectedRole: KnockoutObservable<string>;
        selectedUserID: KnockoutObservable<string>;
        columns: KnockoutObservableArray<NtsGridListColumn>;

        //list Role Individual Grant
        listRoleIndividual: KnockoutObservableArray<RoleIndividual>;
        selectedRoleIndividual: KnockoutObservable<string>;
        columnsIndividual: KnockoutObservableArray<NtsGridListColumn>;

        // A7_3
        loginID: KnockoutObservable<string>;
        userName: KnockoutObservable<string>;

        //Date pick
        dateValue: KnockoutObservable<any>;

        // A8
        tabs: KnockoutObservableArray<nts.uk.ui.NtsTabPanelModel>;
        selectedTab: KnockoutObservable<string>;

        // Start declare KCP005
        listComponentOption: any;
        selectedCode: KnockoutObservable<string>;
        multiSelectedCode: KnockoutObservableArray<string>;
        isShowAlreadySet: KnockoutObservable<boolean>;
        alreadySettingPersonal: KnockoutObservableArray<UnitAlreadySettingModel>;
        isDialog: KnockoutObservable<boolean>;
        isShowNoSelectRow: KnockoutObservable<boolean>;
        isMultiSelect: KnockoutObservable<boolean>;
        isShowWorkPlaceName: KnockoutObservable<boolean>;
        isShowSelectAllButton: KnockoutObservable<boolean>;
        disableSelection : KnockoutObservable<boolean>;

        employeeList: KnockoutObservableArray<UnitModel>;
        baseDate: KnockoutObservable<Date>;

        // Employye +Company + Workplace + Jobtitle
        employyeCode: KnockoutObservable<string>;
        employyeName: KnockoutObservable<string>;
        companyId: KnockoutObservable<string>;
        companyCode: KnockoutObservable<string>;
        companyName: KnockoutObservable<string>;
        workplaceCode: KnockoutObservable<string>;
        workplaceName: KnockoutObservable<string> ;
        jobTitleCode: KnockoutObservable<string>;
        jobTitleName: KnockoutObservable<string>;
        EmployeeIDList: KnockoutObservableArray<any>;


        constructor() {
            var self = this;
            //A51
            self.selectRoleCheckbox = ko.observable('');

            self.employyeCode = ko.observable('');
            self.employyeName = ko.observable('');
            self.workplaceCode = ko.observable('');
            self.workplaceName = ko.observable('');
            self.companyId = ko.observable('');
            self.companyCode = ko.observable('');
            self.companyName = ko.observable('');
            self.jobTitleCode = ko.observable('');
            self.jobTitleName = ko.observable('');
            self.EmployeeIDList = ko.observableArray([]);
            self.listRoleType = ko.observableArray([]);
            self.listRole = ko.observableArray([]);
            self.selectedRoleType = ko.observable('');
            self.selectedRole = ko.observable('');
            self.selectedUserID = ko.observable('');
            self.listRoleIndividual = ko.observableArray([]);
            self.multiSelectedCode = ko.observableArray([]);
            self.alreadySettingPersonal = ko.observableArray([]);
            self.columns = ko.observableArray([
                {headerText: '', key: 'roleId', hidden: true},
                {headerText: nts.uk.resource.getText("CAS013_11"), key: 'roleCode', width: 60},
                {headerText: nts.uk.resource.getText("CAS013_12"), key: 'name', width: 200},
            ]);
            self.columnsIndividual = ko.observableArray([
                {headerText: '', key: 'userId', hidden: true},
                {headerText: nts.uk.resource.getText("CAS013_15"), key: 'loginId', width: 120},
                {headerText: nts.uk.resource.getText("CAS013_16"), key: 'name', width: 120},
                {headerText: nts.uk.resource.getText("CAS013_17"), key: 'datePeriod', width: 210},
            ]);
            //A41
            self.selectedRoleIndividual = ko.observable('');
            self.loginID = ko.observable('');
            self.userName = ko.observable('');
            self.dateValue = ko.observable({});


            self.selectedRoleType.subscribe((roleType: string) => {
                self.getRoles(roleType.toString());
                self.isCreateMode(false);
                self.isSelectedUser(false);
                self.isDelete(false);
            });

            self.selectedRole.subscribe((roleId: string) => {
                self.selectRole(roleId.toString(), '');
                self.isSelectedUser(false);
                self.isDelete(false);
            });

            self.selectedRoleIndividual.subscribe((userId: string) => {
                self.selectRoleGrant(userId.toString());
            });

            //tab
            self.tabs = ko.observableArray([
                { id: 'tab-1', title: nts.uk.resource.getText("CAS013_13"), content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-2', title: nts.uk.resource.getText("CAS013_14"), content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-3', title: nts.uk.resource.getText("CAS013_15"), content: '.tab-content-3', enable: ko.observable(true), visible: ko.observable(true) },
            ]);
            self.selectedTab = ko.observable('tab-1');
            //Load KCP005
            self.KCP005_load();

        }

        //A51
        setDefault() {
            var self = this;
            nts.uk.util.value.reset($("#combo-box, #A_SEL_001"), self.defaultValue() !== '' ? self.defaultValue() : undefined);
        }
        //A51
        validate() {
            $("#combo-box").trigger("validate");
        }


        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            $('#kcp005 table').attr('tabindex', '0')
            // initial screen
            new service.Service().getRoleTypes().done(function(data: Array<RollType>) {
                if(data){
                    if(nts.uk.util.isNullOrUndefined(data)){
                        self.backToTopPage();
                    }else{
                        //A41
                        self.listRoleType(data);
                        self.selectedRoleType('0');
                    }
                }else{
                    nts.uk.request.jump("/view/ccg/008/a/index.xhtml");
                }
                dfd.resolve();
            });
            return dfd.promise();
        }

        /**
         * back to top page - トップページに戻る
         */
        backToTopPage() {
            nts.uk.ui.windows.sub.modeless("/view/ccg/008/a/index.xhtml");
        }

        KCP005_load() {
            var self = this;

            // Start define KCP005
            self.baseDate = ko.observable(new Date());
            self.selectedCode = ko.observable('1');
            self.multiSelectedCode = ko.observableArray([]);
            self.isShowAlreadySet = ko.observable(false);
            self.alreadySettingPersonal = ko.observableArray([]);
            self.isDialog = ko.observable(false);
            self.isShowNoSelectRow = ko.observable(false);
            self.isMultiSelect = ko.observable(false);
            self.isShowWorkPlaceName = ko.observable(false);
            self.isShowSelectAllButton = ko.observable(false);
            self.disableSelection = ko.observable(false);
            self.employeeList = ko.observableArray<UnitModel>([]);

            self.listComponentOption = {
                isShowAlreadySet: false,
                isMultiSelect: false,
                listType: ListType.EMPLOYEE,
                employeeInputList: self.employeeList,
                selectType: SelectType.SELECT_BY_SELECTED_CODE,
                selectedCode: self.multiSelectedCode,
                isDialog: false,
                alreadySettingList: self.alreadySettingPersonal,
                isShowWorkPlaceName: true,
                isShowSelectAllButton: false,
                maxWidth: 580,
                maxHeight:250,
                maxRows: 10.5
            };
            //Fixing
            self.multiSelectedCode.subscribe((e) => {
                self.selectRoleEmployee(e.toString());
            });
            $('#kcp005').ntsListComponent(self.listComponentOption)
        }

        private getRoles(roleType: string): void {
            var self = this;
            if (roleType != '') {
                new service.Service().getRole(roleType).done(function(data: any) {
                    if (data != null && data.length > 0) {
                        data = _.orderBy(data, ['name', 'roleCode'], ['asc', 'asc']);
                        self.listRole(data);
                        self.selectedRole(data[0].roleId);
                        self.selectRoleCheckbox(data[0].roleId);
                    }
                    else {
                        self.listRole([]);
                        self.selectedRole('')
                        self.selectRoleCheckbox('');
                    }
                });
            } else {
                self.listRole([]);
                self.selectedRole('');
                self.selectRoleCheckbox('');
            }
        }

        private selectRole(roleId: string, userIdSelected: string): void {
            var self = this;
            var employeeSearchs: UnitModel[] = []; //KCP005
            if (roleId != '' ) {
                self.selectedRoleIndividual('');
                self.multiSelectedCode([]);
                new service.Service().getRoleGrants(roleId).done(function(data: any) {
                    if (data != null && data.length > 0) {
                        let items = [];
                        let leids = [];
                        var periodDate = '';//KCP005
                        for (let entry of data) {
                            items.push(new RoleIndividual(entry.userID, entry.loginID, entry.userName, entry.startValidPeriod, entry.endValidPeriod,
                                entry.employeeID, entry.employeeId, entry.businessName));
                            leids.push(new ListEmployyeID(entry.employeeId));
                            //KCO005
                            periodDate = (entry.startValidPeriod + " ~ " + entry.endValidPeriod).toString();
                            var employee: UnitModel = {
                                code: entry.userID,
                                name: entry.userName,
                                affiliationName: periodDate,
                                startValidPeriod: entry.startValidPeriod,
                                endValidPeriod: entry.endValidPeriod
                            };
                            employeeSearchs.push(employee);

                        }
                        self.EmployeeIDList(leids);
                        //Select First Employye
                        if(employeeSearchs != null) {
                            self.multiSelectedCode.push(employeeSearchs[0].code)
                            self.dateValue(new datePeriod(employeeSearchs[0].startValidPeriod, employeeSearchs[0].endValidPeriod));
                            self.selectRoleEmployee(employeeSearchs[0].code);
                        } else {
                            self.multiSelectedCode([]);
                            self.dateValue({});
                            self.companyId('');
                            self.companyName('');
                            self.companyCode('');
                            self.workplaceCode('');
                            self.workplaceName('');
                            self.jobTitleCode('');
                            self.jobTitleName('');
                            self.employyeCode('');
                            self.employyeName('');
                        }
                        self.employeeList(employeeSearchs);
                        //End KCP005
                        self.listRoleIndividual(items);
                        if (nts.uk.text.isNullOrEmpty(userIdSelected)) {
                            self.selectedRoleIndividual(items[0].userId);
                        } else {
                            self.selectedRoleIndividual(userIdSelected);
                        }
                    } else {
                        self.employeeList([]);//KCP005
                        self.selectedRoleIndividual('');//KCP005
                        self.listRoleIndividual([]);
                        self.loginID('');
                        self.userName('');
                        self.dateValue({});
                        self.New();
                    }
                });
            } else {
                self.employeeList([]);//KCP005
                self.selectedRoleIndividual('');//KCP005
                self.listRoleIndividual([]);
                self.loginID('');
                self.userName('');
                self.dateValue({});
                self.companyId('');
                self.companyName('');
                self.companyCode('');
                self.workplaceCode('');
                self.workplaceName('');
                self.jobTitleCode('');
                self.jobTitleName('');
                self.employyeCode('');
                self.employyeName('');
            }
        }


        private selectRoleGrant(UserId: string): void {
            var self = this;
            var roleId = self.selectedRole();
            if (roleId != '' && UserId != '') {
                var userSelected = _.find(self.listRoleIndividual(), ['userId',UserId]);
                new service.Service().getRoleGrant(roleId, UserId).done(function(data: any) {
                    if (data != null) {
                        //self.dateValue(new datePeriod(data.startValidPeriod, data.endValidPeriod));
                        self.isCreateMode(false);
                        self.isSelectedUser(false);
                        self.isDelete(true);
                    }
                });
            }else{
                self.isDelete(false);
            }
        }

        private selectRoleEmployee(UserId: string): void {
            var self = this;
            var roleId = self.selectedRole();
            self.selectedUserID(UserId);
            if (roleId != '' && UserId != '') {
                var userSelected = _.find(self.employeeList(), ['code',UserId]);
                var userEmployee = _.find(self.listRoleIndividual(), ['userId',UserId]);
                new service.Service().getRoleGrant(roleId, UserId).done(function(data: any) {
                    if (data != null) {
                        if(nts.uk.text.isNullOrEmpty(userSelected)
                        && nts.uk.text.isNullOrEmpty(userEmployee)
                        ){
                            self.loginID('');
                            self.userName('');
                        } else {
                            self.dateValue(new datePeriod(userEmployee.start, userEmployee.end));
                            self.loginID(userSelected.code);
                            self.userName(userSelected.name);
                        }
                        self.employyeCode(data.employeeCode);
                        self.employyeName(data.businessName);

                        new service.Service().getCompanyInfo().done(function (data: any) {
                            if(data != null) {
                                self.companyId(data.companyId);
                                self.companyName(data.companyName);
                                self.companyCode(data.companyCode);
                            } else {
                                self.companyId('');
                                self.companyName('');
                                self.companyCode('');
                            }
                        })

                        new service.Service().getWorkPlaceInfo(data.employeeId).done(function (data: any) {
                            if(data != null) {
                                self.workplaceCode(data.workPlaceCode);
                                self.workplaceName(data.workPlaceName);
                            } else {
                                self.workplaceCode('');
                                self.workplaceName('');
                            }
                        })
                        new service.Service().getJobTitle(data.employeeId).done(function (data: any) {
                            if(data != null) {
                                self.jobTitleCode(data.jobTitleCode);
                                self.jobTitleName(data.jobTitleName);
                            } else  {
                                self.jobTitleCode('');
                                self.jobTitleName('');
                            }
                        })
                        self.isCreateMode(false);
                        self.isSelectedUser(false);
                        self.isDelete(true);
                    }
                });
            } else {
                self.isDelete(false);
            }
        }

        New(): void {
            var self = this;
            self.isCreateMode(true);
            self.isDelete(false);
            self.isSelectedUser(false);
            self.selectedRoleIndividual('');
            self.loginID('');
            self.userName('');
            self.dateValue({});
            self.companyId('');
            self.companyName('');
            self.companyCode('');
            self.workplaceCode('');
            self.workplaceName('');
            self.jobTitleCode('');
            self.jobTitleName('');
            self.employyeCode('');
            self.employyeName('');
            nts.uk.ui.errors.clearAll();
        }
        openBModal(): void {
            var self = this;
            nts.uk.ui.windows.setShared("roleType", self.selectedRoleType());
            nts.uk.ui.windows.setShared("companyId", self.companyId());
            nts.uk.ui.windows.setShared("ListEmployyeId", self.EmployeeIDList());
            nts.uk.ui.windows.setShared("companyCode", self.companyCode());
            nts.uk.ui.windows.setShared("companyName", self.companyName());
            nts.uk.ui.windows.sub.modal("../b/index.xhtml").onClosed(() => {
                let data = nts.uk.ui.windows.getShared("EmployyeList");
                let data2 = nts.uk.ui.windows.getShared("CompanyInfo")
                let data3 = nts.uk.ui.windows.getShared("workplaceList")
                let data4 = nts.uk.ui.windows.getShared("syjobList")
                if (data != null) {
                    self.employyeCode(data.ecode);
                    self.employyeName(data.ename);
                }
                if (data2 != null) {
                    self.companyCode(data2.ccode);
                    self.companyName(data2.cname);
                }
                if (data3 != null) {
                    self.workplaceCode(data3.code);
                    self.workplaceName(data3.name);
                }
                if (data4 != null) {
                    self.jobTitleCode(data4.code);
                    self.jobTitleName(data4.name);
                }
            });
        }
        save(): void {
            var self = this;
            if (!nts.uk.text.isNullOrEmpty(self.employyeCode())
                && !nts.uk.text.isNullOrEmpty(self.employyeName())
                && !nts.uk.util.isNullOrUndefined(self.companyCode())
                && !nts.uk.util.isNullOrUndefined(self.companyName())
                && !nts.uk.util.isNullOrUndefined(self.workplaceCode())
                && !nts.uk.util.isNullOrUndefined(self.workplaceName())
                && !nts.uk.util.isNullOrUndefined(self.jobTitleName())
                && !nts.uk.util.isNullOrUndefined(self.jobTitleCode())
                && !nts.uk.util.isNullOrUndefined(self.dateValue().startDate)
                && !nts.uk.util.isNullOrUndefined(self.dateValue().endDate)
                && self.loginID() != ''
                && self.employyeName() != ''
                && !nts.uk.ui.errors.hasError()) {
                if (self.isSelectedUser() && self.isCreateMode()) {
                    self.insert();
                } else {
                    self.upDate();
                }
            }
            else if (nts.uk.text.isNullOrEmpty(self.employyeName())) {
                nts.uk.ui.dialog.alertError({ messageId: "Msg_218", messageParams: [nts.uk.resource.getText("CAS013_19")] });
            }
            else if (nts.uk.util.isNullOrUndefined(self.dateValue().startDate) || nts.uk.util.isNullOrUndefined(self.dateValue().endDate)) {
                $(".nts-input").trigger("validate");
            }

        }
        private insert(): void {
            var self = this;
            var roleType = self.selectedRoleType();
            var roleId = self.selectedRole();
            var userId = self.selectedUserID();
            var start = nts.uk.time.parseMoment(self.dateValue().startDate).format();
            var end = nts.uk.time.parseMoment(self.dateValue().endDate).format();
            block.invisible();
            new service.Service().insertRoleGrant(roleType, roleId, userId, start, end).done(function(data: any) {
                if (!nts.uk.util.isNullOrUndefined(data)) {
                    self.selectedUserID("");
                    self.selectRole(roleId, data);
                    nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                    self.isCreateMode(false);
                } else {
                    nts.uk.ui.dialog.alertError({ messageId: "Msg_716", messageParams: [nts.uk.resource.getText("CAS013_11")] });
                }
            }).always(() => {
                block.clear();
            });
        }
        private upDate(): void {
            var self = this;
            var roleTpye = self.selectedRoleType();
            var roleId = self.selectedRole();
            var userId = self.selectedUserID();
            var start = nts.uk.time.parseMoment(self.dateValue().startDate).format();
            var end = nts.uk.time.parseMoment(self.dateValue().endDate).format();
            block.invisible();
            new service.Service().upDateRoleGrant(roleTpye, roleId, userId, start, end).done(function(data: any) {
                if (!nts.uk.util.isNullOrUndefined(data)) {
                    self.selectRole(roleId, data);
                    nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                } else {
                    nts.uk.ui.dialog.alertError({ messageId: "Msg_716", messageParams: [nts.uk.resource.getText("CAS013_11")] });
                }
                self.isCreateMode(false);
            }).always(() => {
                block.clear();
            });
        }
        Delete(): void {
            if (!nts.uk.ui.errors.hasError()) {
                nts.uk.ui.dialog.confirm({ messageId: "Msg_18" }).ifYes(() => {
                    var self = this;
                    var roleTpye = self.selectedRoleType();
                    var userId = self.selectedRoleIndividual();
                    block.invisible();
                    new service.Service().deleteRoleGrant(roleTpye, userId).done(function() {
                        self.selectedRoleIndividual('');
                        nts.uk.ui.dialog.info({ messageId: "Msg_16" });
                    }).always(() => {
                        block.clear();
                    });
                    self.selectRole(self.selectedRole(), '');
                });
            }
        }

        exportExcel(): void {
            let self = this;
            let params = {
                date: null,
                mode: 1
            };
            if (!nts.uk.ui.windows.getShared("CDL028_INPUT")) {
                nts.uk.ui.windows.setShared("CDL028_INPUT", params);
            }
            nts.uk.ui.windows.sub.modal("../../../../../nts.uk.com.web/view/cdl/028/a/index.xhtml").onClosed(function() {
                var result = getShared('CDL028_A_PARAMS');
                if (result.status) {
                    nts.uk.ui.block.grayout();
                    let langId = self.langId();
                    let date = moment.utc(result.standardDate, "YYYY/MM/DD");
                    new service.Service().saveAsExcel(langId, date).done(function() {
                    }).fail(function(error) {
                        nts.uk.ui.dialog.alertError({ messageId: error.messageId });
                    }).always(function() {
                        nts.uk.ui.block.clear();
                    });
                }
            });
        }

    }



    class RollType {
        value: string;
        description: string;

        constructor(value: string, description: string) {
            this.value = value;
            this.description = description;
        }
    }

    class CompanyInfo {
        compCode: string;
        compName: string;

        constructor(compCode: string, compName: string) {
            this.compCode = compCode;
            this.compName = compName;
        }
    }
    class WorkPlaceInfo {
        workplaceCode: string;
        workplacepName: string;

        constructor(workplaceCode: string, workplacepName: string) {
            this.workplaceCode = workplaceCode;
            this.workplacepName = workplacepName;
        }
    }
    class Role {
        roleId: string;
        roleCode: string;
        name: string;
        assignAtr: string;

        constructor(roleId: string, roleCode: string, name: string, assignAtr: string) {
            this.roleId = roleId;
            this.roleCode = roleCode;
            this.name = name;
            this.assignAtr = assignAtr;
        }
    }
    class JobTitle{
        jobTitleCode: string;
        jobTitleName: string;
        constructor(jobTitleCode: string, jobTitleName: string) {
            this.jobTitleCode = jobTitleCode;
            this.jobTitleName = jobTitleName;
        }
    }
    class RoleIndividual {
        userId: string;
        loginId: string;
        name: string;
        start: string;
        end: string;
        datePeriod: string;
        employeeId: string;
        employeeCode: string;
        businessName: string;

        constructor(userId: string, loginId: string, name: string, start: string, end: string, employeeId: string, employeeCode:string, businessName:string) {
            this.userId = userId;
            this.loginId = loginId;
            this.name = name;
            this.start = start;
            this.end = end;
            this.datePeriod = start + ' ~ ' + end;
            this.employeeId = employeeId;
            this.employeeCode = employeeCode;
            this.businessName = businessName;
        }
    }

    class ListEmployyeID {
        employyeId: string;

        constructor(employyeId: string) {
            this.employyeId = employyeId;
        }
    }
    class datePeriod {
        startDate: string;
        endDate: string;
        constructor(startDate: string, endDate: string) {
            this.startDate = startDate;
            this.endDate = endDate;
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
        affiliationName?: string;
        startValidPeriod?: string;
        endValidPeriod?: string;
    }

    export class SelectType {
        static SELECT_BY_SELECTED_CODE = 1;
        static SELECT_ALL = 2;
        static SELECT_FIRST_ITEM = 3;
        static NO_SELECT = 4;
    }

    export interface UnitAlreadySettingModel {
        userID: string;
        isAlreadySetting: boolean;
    }

}

