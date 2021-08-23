/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />
module nts.uk.com.view.cas013.a {
    import block = nts.uk.ui.block;
    import errors = nts.uk.ui.errors;
    import dialog = nts.uk.ui.dialog;
    import windows = nts.uk.ui.windows;
    import resource = nts.uk.resource;
    import NtsGridListColumn = nts.uk.ui.NtsGridListColumn;
    import isNullOrUndefined = nts.uk.util.isNullOrUndefined;
    import isNullOrEmpty = nts.uk.util.isNullOrEmpty;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;

    let format = nts.uk.text.format;
    const API = {
        getCompanyIdOfLoginUser: "ctx/sys/auth/roleset/companyidofloginuser",
        getRoleType: "ctx/sys/auth/grant/roleindividual/getRoleType",

        getRole: "ctx/sys/auth/grant/roleindividual/getRoles/incharge/{0}",
        getRoleGrants: "ctx/sys/auth/grant/roleindividual/getRoleGrants",
        getRoleGrant: "ctx/sys/auth/grant/roleindividual/getRoleGrant",

        insertRoleGrant: "ctx/sys/auth/grant/roleindividual/insertRoleGrant",
        upDateRoleGrant: "ctx/sys/auth/grant/roleindividual/upDateRoleGrant",
        deleteRoleGrant: "ctx/sys/auth/grant/roleindividual/deleteRoleGrant",

        getCompanyInfo: "ctx/sys/auth/grant/roleindividual/getCompanyInfo",
        getWorkPlaceInfo: "ctx/sys/auth/grant/roleindividual/getWorkPlaceInfo",
        getJobTitle: "ctx/sys/auth/grant/roleindividual/getJobTitle"

    };

    @bean()
    class ViewModel extends ko.ViewModel {
        //A51
        selectRoleCheckbox: KnockoutObservable<string> = ko.observable('');

        langId: KnockoutObservable<string> = ko.observable('ja');
        // Metadata
        isCreateMode: KnockoutObservable<boolean> = ko.observable(false);
        isSelectedUser: KnockoutObservable<boolean> = ko.observable(false);
        isDelete: KnockoutObservable<boolean> = ko.observable(false);

        //ComboBOx RollType
        listRoleType: KnockoutObservableArray<RollType>;
        selectedRoleType: KnockoutObservable<string> =  ko.observable('');

        //list Roll
        listRole: KnockoutObservableArray<Role> = ko.observableArray([]);
        selectedRole: KnockoutObservable<string> = ko.observable('');
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
        multiSelectedCode: KnockoutObservable<string>;
        isShowAlreadySet: KnockoutObservable<boolean>;
        alreadySettingPersonal: KnockoutObservableArray<UnitAlreadySettingModel>;
        isDialog: KnockoutObservable<boolean>;
        isShowNoSelectRow: KnockoutObservable<boolean>;
        isMultiSelect: KnockoutObservable<boolean>;
        isShowWorkPlaceName: KnockoutObservable<boolean>;
        isShowSelectAllButton: KnockoutObservable<boolean>;
        disableSelection: KnockoutObservable<boolean>;

        employeeList: KnockoutObservableArray<UnitModel>;
        baseDate: KnockoutObservable<Date>;

        // Employye +Company + Workplace + Jobtitle
        employyeCode: KnockoutObservable<string>;
        employyeName: KnockoutObservable<string>;
        companyId: KnockoutObservable<string>;
        companyCode: KnockoutObservable<string>;
        companyName: KnockoutObservable<string>;
        workplaceCode: KnockoutObservable<string>;
        workplaceName: KnockoutObservable<string>;
        jobTitleCode: KnockoutObservable<string>;
        jobTitleName: KnockoutObservable<string>;
        EmployeeIDList: KnockoutObservableArray<any>;

        constructor(params: any) {
            super();
            let vm = this;
            let dfd = $.Deferred();
            //A51

            vm.employyeCode = ko.observable('');
            vm.employyeName = ko.observable('');
            vm.workplaceCode = ko.observable('');
            vm.workplaceName = ko.observable('');

            vm.companyId = ko.observable('');
            vm.companyCode = ko.observable('');
            vm.companyName = ko.observable('');
            vm.jobTitleCode = ko.observable('');
            vm.jobTitleName = ko.observable('');
            vm.EmployeeIDList = ko.observableArray([]);
            vm.listRoleType = ko.observableArray([]);
            vm.listRole = ko.observableArray([]);
            vm.selectedUserID = ko.observable('');
            vm.listRoleIndividual = ko.observableArray([]);
            vm.multiSelectedCode = ko.observable();
            vm.alreadySettingPersonal = ko.observableArray([]);
            vm.columns = ko.observableArray([
                {headerText: '', key: 'roleId', hidden: true},
                {headerText: nts.uk.resource.getText("CAS013_11"), key: 'roleCode', width: 60},
                {headerText: nts.uk.resource.getText("CAS013_12"), key: 'name', width: 200},
            ]);
            vm.columnsIndividual = ko.observableArray([
                {headerText: '', key: 'userId', hidden: true},
                {headerText: nts.uk.resource.getText("CAS013_15"), key: 'loginId', width: 120},
                {headerText: nts.uk.resource.getText("CAS013_16"), key: 'name', width: 120},
                {headerText: nts.uk.resource.getText("CAS013_17"), key: 'datePeriod', width: 210},
            ]);
            //A41
            vm.selectedRoleIndividual = ko.observable('');
            vm.loginID = ko.observable('');
            vm.userName = ko.observable('');
            vm.dateValue = ko.observable({});
            //tab
            vm.tabs = ko.observableArray([
                {
                    id: 'tab-1',
                    title: nts.uk.resource.getText("CAS013_13"),
                    content: '.tab-content-1',
                    enable: ko.observable(true),
                    visible: ko.observable(true)
                },
                {
                    id: 'tab-2',
                    title: nts.uk.resource.getText("CAS013_14"),
                    content: '.tab-content-2',
                    enable: ko.observable(true),
                    visible: ko.observable(true)
                },
                {
                    id: 'tab-3',
                    title: nts.uk.resource.getText("CAS013_15"),
                    content: '.tab-content-3',
                    enable: ko.observable(true),
                    visible: ko.observable(true)
                },
            ]);
            vm.selectedTab = ko.observable('tab-1');
            block.invisible();
            vm.$ajax('com', API.getCompanyIdOfLoginUser).done((companyId: any) => {
                if (!companyId) {
                    vm.backToTopPage();
                    dfd.resolve();
                } else {
                    // initial screen
                    vm.initialScreen(dfd, '');
                }
                dfd.resolve();
            }).fail(() => {
                vm.backToTopPage();
                dfd.reject();
            }).always(()=>{
            });
            //Load KCP005
            vm.KCP005_load();
            vm.selectedRole.subscribe((roleId: string) => {
                vm.selectRole(roleId.toString(), '');
                vm.isSelectedUser(false);
                vm.isDelete(false);
                console.log(roleId);
            });
        }
        created(params: any) {
            let vm = this;
        }
        mounted() {
            const vm = this;
            vm.selectedRoleType.subscribe((roleType: string) => {
                vm.getRoles(roleType);
                vm.isCreateMode(false);
                vm.isSelectedUser(false);
                vm.isDelete(false);
            });

            vm.selectRoleCheckbox.subscribe((e)=>{
                let itemRole = _.find(vm.listRole(),(i)=>{
                    return i.roleCode == e;
                });
                if(!isNullOrUndefined(itemRole)){
                    vm.selectedRole(itemRole.roleId);
                }
            })
            vm.setFocus();
        }
        //A51
        setDefault() {
            let vm = this;
            nts.uk.util.value.reset($("#combo-box, #A_SEL_001"), vm.defaultValue() !== '' ? vm.defaultValue() : undefined);
        }
        //A51
        validate() {
            $("#combo-box").trigger("validate");
        }
        initialScreen(deferred: any, roleSetCd: string) {
            let vm = this;
            let dfd = $.Deferred();
            $('#kcp005 table').attr('tabindex', '0');
            block.invisible();
            vm.$ajax('com', API.getRoleType).done(function (data: Array<RollType>) {
                if (!isNullOrEmpty(data)) {
                    vm.listRoleType(data);
                } else {
                    vm.backToTopPage();
                }
                dfd.resolve();
            }).fail((error) => {
                dialog.alertError({messageId: error.messageId}).then(() => {
                    vm.backToTopPage();
                });
                dfd.reject();
            }).always(() => {
            });
            return dfd.promise();
        }
        backToTopPage() {
            nts.uk.request.jump("/view/ccg/008/a/index.xhtml");
        }
        setFocus(){
            let vm = this;
            if(vm.isCreateMode()){
                $('#combo-box').focus();
            }else {
                $("#daterangepicker").find(".ntsStartDatePicker").focus();
            }

        }
        KCP005_load() {
            let vm = this;
            // Start define KCP005
            vm.baseDate = ko.observable(new Date());

            vm.isShowAlreadySet = ko.observable(false);
            vm.alreadySettingPersonal = ko.observableArray([]);
            vm.isDialog = ko.observable(false);
            vm.isShowNoSelectRow = ko.observable(false);
            vm.isMultiSelect = ko.observable(false);
            vm.isShowWorkPlaceName = ko.observable(false);
            vm.isShowSelectAllButton = ko.observable(false);
            vm.disableSelection = ko.observable(false);
            vm.employeeList = ko.observableArray<UnitModel>([]);

            vm.listComponentOption = {
                isShowAlreadySet: false,
                isMultiSelect: false,
                listType: ListType.EMPLOYEE,
                employeeInputList: vm.employeeList,
                selectType: SelectType.SELECT_BY_SELECTED_CODE,
                selectedCode: vm.multiSelectedCode,
                isShowWorkPlaceName: true,
                isDialog: false,
                alreadySettingList: vm.alreadySettingPersonal,
                isShowSelectAllButton: false,
                maxWidth: 580,
                maxRows: 10,
            };

            //Fixing
            vm.multiSelectedCode.subscribe((e) => {
                let item = _.find(vm.employeeList(), (i) => i.code == e.toString());
                if (!isNullOrUndefined(item)) {
                    let id = item.id.toString();
                    vm.selectRoleEmployee(id);
                }
                vm.setFocus();
            });
            $('#kcp005').ntsListComponent(vm.listComponentOption)

        }

        private getRoles(roleType: string): void {
            let vm = this;
            let dfd = $.Deferred();
            if (roleType != '') {
                let _path = format(API.getRole, roleType);
                block.invisible();
                vm.$ajax('com', _path).done(function (data: any) {
                    if (data != null && data.length > 0) {
                        vm.listRole(data);
                        vm.selectedRole(data[0].roleId);
                        vm.selectRoleCheckbox(data[0].roleId);
                    }
                    else {
                        vm.listRole([]);
                        vm.selectedRole('');
                        vm.selectRoleCheckbox('');
                    }
                    dfd.resolve();
                }).always(()=>{
                    block.clear();
                }).fail(()=>{
                    dfd.reject();
                });
            } else {
                vm.listRole([]);
                vm.selectedRole('');
                vm.selectRoleCheckbox('');
            }
        }

        private selectRole(roleId: string, userIdSelected: string): void {
            let vm = this;
            let employeeSearchs: UnitModel[] = []; //KCP005
            let index = _.findIndex(vm.employeeList(),(e)=>{return e.id == userIdSelected});
            if (roleId != '') {
                vm.selectedRoleIndividual('');
                block.invisible();
                vm.$ajax('com', API.getRoleGrants, roleId).done(function (data: any) {
                    if (data != null && data.length > 0) {
                        let items = [];
                        let leids = [];
                        let periodDate = '';//KCP005
                        for (let entry of data) {
                            items.push(new RoleIndividual(entry.userID, entry.loginID, entry.userName, entry.startValidPeriod, entry.endValidPeriod,
                                entry.employeeID, entry.employeeId, entry.businessName));
                            leids.push(new ListEmployyeID(entry.employeeId));
                            //KCO005
                            periodDate = (entry.startValidPeriod + " ~ " + entry.endValidPeriod).toString();
                            let employee: UnitModel = {
                                id: entry.userID,
                                code: entry.employeeCode,
                                name: entry.userName,
                                affiliationName: periodDate,
                                startValidPeriod: entry.startValidPeriod,
                                endValidPeriod: entry.endValidPeriod
                            };
                            employeeSearchs.push(employee);

                        }
                        vm.EmployeeIDList(leids);
                        //Select First Employye
                        if (isNullOrEmpty(employeeSearchs)) {

                            vm.multiSelectedCode();
                            vm.dateValue({});
                            vm.companyId('');
                            vm.companyName('');
                            vm.companyCode('');
                            vm.workplaceCode('');
                            vm.workplaceName('');
                            vm.jobTitleCode('');
                            vm.jobTitleName('');
                            vm.employyeCode('');
                            vm.employyeName('');
                        }
                        vm.listRoleIndividual(items);
                        vm.employeeList(employeeSearchs);
                        let indexNew = _.findIndex(vm.employeeList(),(e)=>{return e.id == userIdSelected});

                        if(index >=0 && indexNew < 0){
                            vm.multiSelectedCode(vm.employeeList()[index == 0 ? index : (index - 1)].code);
                            vm.dateValue(new datePeriod(employeeSearchs[index == 0 ? index : (index - 1)].startValidPeriod, employeeSearchs[index == 0 ? index : (index - 1)].endValidPeriod));
                          //  vm.selectRoleEmployee(employeeSearchs[index == 0 ? index : (index - 1)].id);
                        }
                        if(index <0 && indexNew >=0){
                            vm.multiSelectedCode(vm.employeeList()[indexNew].code);
                            vm.dateValue(new datePeriod(employeeSearchs[indexNew].startValidPeriod, employeeSearchs[indexNew].endValidPeriod));
                           // vm.selectRoleEmployee(employeeSearchs[indexNew].id);
                        }
                        if(index == indexNew && index <0){
                            vm.multiSelectedCode(vm.employeeList()[0].code);
                            vm.dateValue(new datePeriod(employeeSearchs[0].startValidPeriod, employeeSearchs[0].endValidPeriod));
                           // vm.selectRoleEmployee(employeeSearchs[0].id);
                        }
                        if(index == indexNew && index >0){
                            vm.multiSelectedCode(vm.employeeList()[index].code);
                            vm.dateValue(new datePeriod(employeeSearchs[index].startValidPeriod, employeeSearchs[index].endValidPeriod));
                            //vm.selectRoleEmployee(employeeSearchs[index].id);
                        }
                        //End KCP005

                        if (nts.uk.text.isNullOrEmpty(userIdSelected)) {
                            vm.selectedRoleIndividual(items[0].userId);
                        } else {
                            vm.selectedRoleIndividual(userIdSelected);
                        }
                    } else {
                        vm.employeeList([]);//KCP005
                        vm.selectedRoleIndividual('');//KCP005
                        vm.listRoleIndividual([]);
                        vm.loginID('');
                        vm.userName('');
                        vm.dateValue({});
                        vm.New();
                    }
                }).always(()=>{
                    block.clear()
                }).fail(()=>{

                });
            } else {
                vm.employeeList([]);//KCP005
                vm.selectedRoleIndividual('');//KCP005
                vm.listRoleIndividual([]);
                vm.loginID('');
                vm.userName('');
                vm.dateValue({});
                vm.companyId('');
                vm.companyName('');
                vm.companyCode('');
                vm.workplaceCode('');
                vm.workplaceName('');
                vm.jobTitleCode('');
                vm.jobTitleName('');
                vm.employyeCode('');
                vm.employyeName('');
            }
        }
        private selectRoleEmployee(UserId: string): void {
            let vm = this;
            let roleId = vm.selectedRole();
            vm.selectedUserID(UserId);
            if (roleId != '' && UserId != '') {
                let userSelected = _.find(vm.employeeList(), (e) => e.id == UserId);
                let userEmployee = _.find(vm.listRoleIndividual(), ['userId', UserId]);
                let data = {
                    roleID: roleId,
                    userID: UserId
                };
                vm.$ajax('com', API.getRoleGrant, data).done(function (data: any) {
                    if (data != null) {
                        if (nts.uk.text.isNullOrEmpty(userSelected)
                            && nts.uk.text.isNullOrEmpty(userEmployee)
                        ) {
                            vm.loginID('');
                            vm.userName('');
                        } else {
                            vm.dateValue(new datePeriod(userEmployee.start, userEmployee.end));
                            vm.loginID(userSelected.code);
                            vm.userName(userSelected.name);
                        }
                        vm.employyeCode(data.employeeCode);
                        vm.employyeName(data.businessName);

                        vm.$ajax('com', API.getCompanyInfo).done(function (data: any) {
                            if (data != null) {
                                vm.companyId(data.companyId);
                                vm.companyName(data.companyName);
                                vm.companyCode(data.companyCode);
                            } else {
                                vm.companyId('');
                                vm.companyName('');
                                vm.companyCode('');
                            }
                        });

                        vm.$ajax('com', API.getWorkPlaceInfo, data.employeeId).done(function (data: any) {
                            if (data != null) {
                                vm.workplaceCode(data.workPlaceCode);
                                vm.workplaceName(data.workPlaceName);
                            } else {
                                vm.workplaceCode('');
                                vm.workplaceName('');
                            }
                        });
                        vm.$ajax('com', API.getJobTitle, data.employeeId).done(function (data: any) {
                            if (data != null) {
                                vm.jobTitleCode(data.jobTitleCode);
                                vm.jobTitleName(data.jobTitleName);
                            } else {
                                vm.jobTitleCode('');
                                vm.jobTitleName('');
                            }
                        });
                        vm.isCreateMode(false);
                        vm.isSelectedUser(false);
                        vm.isDelete(true);
                    }
                }).always(()=>{
                    block.clear();
                });
            } else {
                vm.isDelete(false);
            }
        }
        New(): void {
            let vm = this;
            vm.isCreateMode(true);
            vm.isDelete(false);
            vm.isSelectedUser(true);
            vm.selectedRoleIndividual('');
            vm.loginID('');
            vm.userName('');
            vm.dateValue({});
            vm.companyId('');
            vm.companyName('');
            vm.companyCode('');
            vm.workplaceCode('');
            vm.workplaceName('');
            vm.jobTitleCode('');
            vm.jobTitleName('');
            vm.employyeCode('');
            vm.employyeName('');
            $('#combo-box').focus();
            nts.uk.ui.errors.clearAll();
        }

        openBModal(): void {
            let vm = this;
            nts.uk.ui.windows.setShared("cid_from_a", vm.companyId());
            nts.uk.ui.windows.sub.modal('/view/cas/013/b/index.xhtml').onClosed(() => {
                let employeeInf = nts.uk.ui.windows.getShared("employeeInf");
                let cidSelected = nts.uk.ui.windows.getShared("cid");
                if (!isNullOrUndefined(employeeInf)) {
                    vm.employyeCode(employeeInf.employeeCode);
                    vm.employyeName(employeeInf.businessName);
                    vm.jobTitleCode(employeeInf.jobTitleCode);
                    vm.jobTitleName(employeeInf.jobTitleName);
                    vm.workplaceCode(employeeInf.workplaceCode);
                    vm.workplaceName(employeeInf.workplaceName);
                    vm.selectedUserID(employeeInf.userId);
                }
                if (!isNullOrUndefined(cidSelected)) {
                    vm.companyName(cidSelected.name);
                    vm.companyCode(cidSelected.code);
                    vm.companyId(cidSelected.id);
                }

            });
        }

        save(): void {
            let vm = this;
            if (!nts.uk.util.isNullOrUndefined(vm.employyeCode())
                && !nts.uk.util.isNullOrUndefined(vm.employyeName())
                && !nts.uk.util.isNullOrUndefined(vm.companyCode())
                && !nts.uk.util.isNullOrUndefined(vm.dateValue().startDate)
                && !nts.uk.util.isNullOrUndefined(vm.dateValue().endDate)
                && vm.employyeName() != ''
                && !nts.uk.ui.errors.hasError()) {
                if (vm.isSelectedUser() && vm.isCreateMode()) {
                    vm.insert();
                } else {
                    vm.upDate();
                }
            }
            else if (nts.uk.util.isNullOrUndefined(vm.employyeName())) {
                nts.uk.ui.dialog.alertError({
                    messageId: "Msg_218",
                    messageParams: ["A7_1"]
                });
            }
            else if (nts.uk.util.isNullOrUndefined(vm.dateValue().startDate) || nts.uk.util.isNullOrUndefined(vm.dateValue().endDate)) {
                $(".nts-input").trigger("validate");
            }

        }

        private insert(): void {
            let vm = this;
            let roleType = vm.selectedRoleType();
            let roleId = vm.selectedRole();
            let userId = vm.selectedUserID();
            let start = nts.uk.time.parseMoment(vm.dateValue().startDate).format();
            let end = nts.uk.time.parseMoment(vm.dateValue().endDate).format();
            block.invisible();
            let roleGrant = {
                userID: userId,
                roleID: roleId,
                roleType: roleType,
                startValidPeriod: start,
                endValidPeriod: end
            };
            vm.$ajax('com', API.insertRoleGrant, roleGrant).done(function (data: any) {
                if (!nts.uk.util.isNullOrUndefined(data)) {
                    vm.selectedUserID("");
                    vm.selectRole(roleId, data);
                    nts.uk.ui.dialog.info({messageId: "Msg_15"});
                    vm.isCreateMode(false);
                } else {
                    nts.uk.ui.dialog.alertError({
                        messageId: "Msg_716",
                        messageParams: [nts.uk.resource.getText("CAS013_11")]
                    });
                }
            }).always(() => {
                block.clear();
            });
        }

        private upDate(): void {
            let vm = this;
            let roleTpye = vm.selectedRoleType();
            let roleId = vm.selectedRole();
            let userId = vm.selectedUserID();
            let start = nts.uk.time.parseMoment(vm.dateValue().startDate).format();
            let end = nts.uk.time.parseMoment(vm.dateValue().endDate).format();
            let roleGrant = {
                userID: userId,
                roleID: roleId,
                roleType: roleTpye,
                startValidPeriod: start,
                endValidPeriod: end
            };
            vm.$ajax('com', API.upDateRoleGrant, roleGrant).done(function (data: any) {
                if (!nts.uk.util.isNullOrUndefined(data)) {
                    vm.selectRole(roleId, data);
                    nts.uk.ui.dialog.info({messageId: "Msg_15"});
                } else {
                    nts.uk.ui.dialog.alertError({
                        messageId: "Msg_716",
                        messageParams: [nts.uk.resource.getText("CAS013_11")]
                    });
                }
                vm.isCreateMode(false);
            }).always(() => {
                block.clear();
            });
        }

        Delete(): void {
            let vm = this;
            if (!nts.uk.ui.errors.hasError()) {
                nts.uk.ui.dialog.confirm({messageId: "Msg_18"}).ifYes(() => {
                    let vm = this;
                    let roleTpye = vm.selectedRoleType();
                    let userId = vm.selectedUserID();
                    block.invisible();
                    let roleGrant = {
                        userID: userId,
                        roleType: roleTpye
                    };
                    vm.$ajax('com', API.deleteRoleGrant, roleGrant).done(function () {
                        vm.selectedRoleIndividual('');
                        nts.uk.ui.dialog.info({messageId: "Msg_16"});
                    }).always(() => {
                        block.clear();
                    });
                    vm.selectRole(vm.selectedRole(), userId);
                });
            }
            vm.setFocus();
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

    class JobTitle {
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

        constructor(userId: string, loginId: string, name: string, start: string, end: string, employeeId: string, employeeCode: string, businessName: string) {
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
        id?: string;
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