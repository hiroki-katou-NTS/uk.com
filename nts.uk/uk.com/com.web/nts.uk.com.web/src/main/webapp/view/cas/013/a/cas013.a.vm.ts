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
        listNewRole: Array<any>; //A41
        listRoleType: KnockoutObservableArray<RollType>;
        selectedRoleType: KnockoutObservable<string>;

        //list Roll
        listRole: KnockoutObservableArray<Role> = ko.observableArray([]);
        selectedRole: KnockoutObservable<string>;
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

        constructor() {
            var self = this;
            //A51
            self.selectRoleCheckbox = ko.observable('');

            self.listRoleType = ko.observableArray([]);
            self.listRole = ko.observableArray([]);
            self.selectedRoleType = ko.observable('');
            self.selectedRole = ko.observable('');
            self.listRoleIndividual = ko.observableArray([]);
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
            self.listNewRole = [
                {
                    description: nts.uk.resource.getText("CAS013_26"),
                    value: 1
                },
                {
                    description: nts.uk.resource.getText("CAS013_27"),
                    value: 2
                },
                {
                    description: nts.uk.resource.getText("CAS013_28"),
                    value: 3
                },
                {
                    description: nts.uk.resource.getText("CAS013_29"),
                    value: 4
                },
                {
                    description: nts.uk.resource.getText("CAS013_30"),
                    value: 5
                },
                {
                    description: nts.uk.resource.getText("CAS013_31"),
                    value: 6
                },
            ];
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
            $('#kcp005 table').attr('tabindex', '-1');

            // initial screen
            new service.Service().getRoleTypes().done(function(data: Array<RollType>) {
                if(data){
                    if(nts.uk.util.isNullOrUndefined(data)){
                        self.backToTopPage();
                    }else{
                        //A41
                        self.listRoleType(self.listNewRole);
                        self.selectedRoleType(data[0].value);
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
            self.selectedCode = ko.observable('');
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
                isShowNoSelectRow: false,
                alreadySettingList: self.alreadySettingPersonal,
                isShowWorkPlaceName: true,
                isShowSelectAllButton: false,
                maxWidth: 580,
                maxRows: 15
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
            if (roleId != '') {
                self.selectedRoleIndividual('');
                new service.Service().getRoleGrants(roleId).done(function(data: any) {
                    if (data != null && data.length > 0) {
                        let items = [];
                        var periodDate = '';//KCP005
                        for (let entry of data) {
                            items.push(new RoleIndividual(entry.userID, entry.loginID, entry.userName, entry.startValidPeriod, entry.endValidPeriod))

                            //KCO005
                            periodDate = (entry.startValidPeriod + " ~ " + entry.endValidPeriod).toString();
                            var employee: UnitModel = {
                                code: entry.userID,
                                name: entry.userName,
                                affiliationName: periodDate,
                            };
                            employeeSearchs.push(employee);
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
            }
        }


        private selectRoleGrant(UserId: string): void {
            var self = this;
            var roleId = self.selectedRole();
            if (roleId != '' && UserId != '') {
                var userSelected = _.find(self.listRoleIndividual(), ['userId',UserId]);
                //self.userName(userSelected.name);
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
                            self.dateValue('');
                        } else {
                            self.dateValue(new datePeriod(userEmployee.start, userEmployee.end));
                            self.loginID(userSelected.code);
                            self.userName(userSelected.name);
                        }
                        self.isCreateMode(false);
                        self.isSelectedUser(false);
                        self.isDelete(true);
                    }
                });
            }else{
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
            nts.uk.ui.errors.clearAll();
        }
        openBModal(): void {
            var self = this;
            nts.uk.ui.windows.setShared("roleType", self.selectedRoleType());
            nts.uk.ui.windows.sub.modal("../b/index.xhtml").onClosed(() => {
                let data = nts.uk.ui.windows.getShared("UserInfo");
                if (data != null) {
                    self.userName(data.decisionName);
                    self.isSelectedUser(true);
                    self.selectedRoleIndividual(data.decisionUserID);
                }
            });
        }
        save(): void {
            var self = this;
            if (!nts.uk.text.isNullOrEmpty(self.selectedRoleType())
                && !nts.uk.text.isNullOrEmpty(self.selectedRole())
                && !nts.uk.util.isNullOrUndefined(self.dateValue().startDate)
                && !nts.uk.util.isNullOrUndefined(self.dateValue().endDate)
                && self.loginID() != ''
                && self.userName() != ''
                && !nts.uk.ui.errors.hasError()) {
                if (self.isSelectedUser() && self.isCreateMode()) {
                    self.insert();
                } else {
                    self.upDate();
                }
            } else if (nts.uk.text.isNullOrEmpty(self.userName())) {
                nts.uk.ui.dialog.alertError({ messageId: "Msg_218", messageParams: [nts.uk.resource.getText("CAS013_19")] });
            } else if (nts.uk.util.isNullOrUndefined(self.dateValue().startDate) || nts.uk.util.isNullOrUndefined(self.dateValue().endDate)) {
                $(".nts-input").trigger("validate");
            }

        }
        private insert(): void {
            var self = this;
            var roleType = self.selectedRoleType();
            var roleId = self.selectedRole();
            var userId = self.selectedRoleIndividual();
            var start = nts.uk.time.parseMoment(self.dateValue().startDate).format();
            var end = nts.uk.time.parseMoment(self.dateValue().endDate).format();
            block.invisible();
            new service.Service().insertRoleGrant(roleType, roleId, userId, start, end).done(function(data: any) {
                if (!nts.uk.util.isNullOrUndefined(data)) {
                    self.selectedRoleIndividual("");
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
            var userId = self.selectedRoleIndividual();
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
                        self.selectRole(self.selectedRole(), '');
                        nts.uk.ui.dialog.info({ messageId: "Msg_16" });
                    }).always(() => {
                        block.clear();
                    });
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

        constructor(value: string, nameId: string, description: string) {
            this.value = value;
            this.description = description;
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
    class RoleIndividual {
        userId: string;
        loginId: string;
        name: string;
        start: string;
        end: string;
        datePeriod: string;

        constructor(userId: string, loginId: string, name: string, start: string, end: string) {
            this.userId = userId;
            this.loginId = loginId;
            this.name = name;
            this.start = start;
            this.end = end;
            this.datePeriod = start + ' ~ ' + end;
        }
    }

    class ItemModel {
        roleCode: string;
        name: string;

        constructor(roleCode: string, name: string) {
            this.roleCode = roleCode;
            this.name = name;
        }
    }

    class ObjectRole {
        roleDtos: Array<Role>;
        roleIndividualGrantDtos: Array<RoleIndividual>;
        roleTypeDtos: Array<RollType>;
        constructor(roleDtos: Array<Role>, roleIndividualGrantDtos: Array<RoleIndividual>, roleTypeDtos: Array<RollType>) {
            this.roleDtos = roleDtos;
            this.roleIndividualGrantDtos = roleIndividualGrantDtos;
            this.roleTypeDtos = roleTypeDtos;

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

