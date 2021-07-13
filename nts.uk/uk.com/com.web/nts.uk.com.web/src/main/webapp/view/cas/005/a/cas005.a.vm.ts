module nts.uk.com.view.cas005.a {
    import getText = nts.uk.resource.getText;
    import ccg = nts.uk.com.view.ccg025.a;
    import ccg026 = nts.uk.com.view.ccg026;
    import errors = nts.uk.ui.errors;
    import block = nts.uk.ui.block;

    export module viewmodel {

        export class ScreenModel {
            //text
            roleName: KnockoutObservable<string>;
            roleCode: KnockoutObservable<string>;
            assignAtr: KnockoutObservable<number> =  ko.observable(0);
            approvalAuthority: KnockoutObservable<number> =  ko.observable(0);
            employeeReferenceRange: KnockoutObservable<number>;

            referenceAuthority: KnockoutObservableArray<any>;
            selectReferenceAuthority: any;
            //combobox
            selectedCode: KnockoutObservable<string>;
            isEnable: KnockoutObservable<boolean>;
            isEditable: KnockoutObservable<boolean>;
            isCategoryAssign: KnockoutObservable<boolean>;
            //table
            currentCodeList: KnockoutObservableArray<any>;
            columns: KnockoutObservableArray<any>;
            listWorkPlaceFunction: KnockoutObservableArray<model.WorkPlaceFunction>;
            listWpkAuthoritySelect: KnockoutObservableArray<model.WpkAuthoritySelect>;

            //table-right
            component: ccg.component.viewmodel.ComponentModel;
            componentCcg026: ccg026.component.viewmodel.ComponentModel;

            //table-left
            //enum
            listEnumRoleType: KnockoutObservableArray<any>;
            listEmployeeReferenceRange: KnockoutObservableArray<any>; //row 6
            listEmployeeRefRange: KnockoutObservableArray<any>; //row 2 8
            listScheduleEmployeeRef: KnockoutObservableArray<any>; //row 4

            selectedEmployeeReferenceRange: KnockoutObservable<number>;
            bookingScreen: KnockoutObservable<number>;
            scheduleScreen: KnockoutObservable<number>;
            registeredInquiries: KnockoutObservable<number>;
            specifyingAgent: KnockoutObservable<number>;
            //list 
            listWebMenu: KnockoutObservableArray<any>;
            selectWebMenu: any;
            listRole: KnockoutObservableArray<any>;
            //enable
            isRegister: KnockoutObservable<boolean>;
            isDelete: KnockoutObservable<boolean>;
            isCopy: KnockoutObservable<boolean>;
            enableRoleCode: KnockoutObservable<boolean>;
            visibleWebmenu: KnockoutObservable<boolean>;

            //obj roleCas005Command
            roleCas005Command: KnockoutObservable<model.RoleCas005Command>;
            listWorkPlaceSelect: KnockoutObservableArray<model.WorkPlaceAuthorityCommand>;
            listWorkPlaceAuthorityCommand: KnockoutObservableArray<model.WorkPlaceAuthorityCommand>;
            listWorkPlaceAuthorityParam: KnockoutObservableArray<model.WorkPlaceAuthorityCommand>;
            //enumEmployment
            enumEmployment: KnockoutObservable<boolean>;

            listWorkplace: KnockoutObservableArray<any>;
            //obj DeleteRoleCas005Command
            deleteRoleCas005Command: KnockoutObservable<model.DeleteRoleCas005Command>;
            //obj to screen dialog B
            objCommandScreenB: KnockoutObservable<model.RoleCas005Command>;

            enumRange: KnockoutObservableArray<EnumConstantDto>;
            constructor() {
                let self = this;
                //table enum RoleType,EmployeeReferenceRange
                self.listEnumRoleType = ko.observableArray(__viewContext.enums.RoleType);
                self.listEmployeeReferenceRange = ko.observableArray(__viewContext.enums.EmployeeReferenceRange);
                self.listEmployeeRefRange = ko.observableArray(__viewContext.enums.EmployeeRefRange);
                self.listScheduleEmployeeRef = ko.observableArray(__viewContext.enums.ScheduleEmployeeRef);
                self.selectedEmployeeReferenceRange = ko.observable(0);
                self.bookingScreen = ko.observable(0);
                self.scheduleScreen = ko.observable(0);
                self.registeredInquiries = ko.observable(0);
                self.specifyingAgent = ko.observable(0);

                self.visibleWebmenu = ko.observable(false);
                self.isCategoryAssign = ko.observable(false);
                self.enumEmployment = ko.observable(false);
                //text
                self.roleName = ko.observable('');
                self.roleCode = ko.observable('');
                let allEmployee = [];
                allEmployee.push(self.listEmployeeReferenceRange()[0]);
                let other = [];
                other.push(self.listEmployeeReferenceRange()[1]);
                other.push(self.listEmployeeReferenceRange()[2]);
                other.push(self.listEmployeeReferenceRange()[3]);
                self.assignAtr.subscribe(function(value) {
                    if (value == 0) {
                        self.listEmployeeReferenceRange(allEmployee);
                        self.visibleWebmenu(true);
                        self.enumEmployment(false)
                    } else {
                        self.listEmployeeReferenceRange(other);
                        self.visibleWebmenu(false);
                        self.enumEmployment(true);
                    }
                    self.bookingScreen(0);
                    self.scheduleScreen(0);
                    self.registeredInquiries(0);
                    self.specifyingAgent(0);

                });

                self.employeeReferenceRange = ko.observable(0);
                self.employeeReferenceRange.subscribe(function(value) {
                    if (value == 3) {
                        self.componentCcg026.enable(false);
                    } else {
                        self.componentCcg026.enable(true);
                    }
                });

                // //switch
                // self.categoryAssign = ko.observableArray([
                //     { code: '0', name: getText('CAS005_35') },
                //     { code: '1', name: getText('CAS005_36') }
                // ]);

                self.referenceAuthority = ko.observableArray([
                    { code: '0', name: getText('CAS005_18') },
                    { code: '1', name: getText('CAS005_19') }
                ]);


                // self.selectCategoryAssign = ko.observable(1);
                self.selectReferenceAuthority = ko.observable("1");

                //combobox
                self.selectedCode = ko.observable('1');
                self.isEnable = ko.observable(true);
                self.isEditable = ko.observable(true);
                //table
                self.listWorkPlaceFunction = ko.observableArray([]);
                self.listWpkAuthoritySelect = ko.observableArray([]);

                self.currentCodeList = ko.observableArray([]);
                //list
                self.listWebMenu = ko.observableArray([]);
                self.selectWebMenu = ko.observable(0);
                self.listRole = ko.observableArray([]);
                //enable
                self.isRegister = ko.observable(false);
                self.isDelete = ko.observable(true);
                self.isCopy = ko.observable(false);
                self.enableRoleCode = ko.observable(false);

                //table right
                self.component = new ccg.component.viewmodel.ComponentModel({
                    roleType: 3,
                    multiple: false,
                    tabindex: 6
                });
                self.assignAtr = self.component.roleClassification;
                //ccg026
                self.componentCcg026 = new ccg026.component.viewmodel.ComponentModel({
                    classification: 1,
                    maxRow: 3,
                    tabindex: 17
                });
                self.componentCcg026.listPermissions.subscribe((value) => {
                    self.listWorkPlaceAuthorityCommand([]);
                    for (let i = 0; i < value.length; i++) { //sucribe
                        let tempCommand = new model.WorkPlaceAuthorityCommand(
                            value[i].functionNo, value[i].availability());
                        self.listWorkPlaceAuthorityCommand().push(tempCommand);
                    }
                    if (self.objCommandScreenB()) {
                        self.objCommandScreenB().listWorkPlaceAuthority = self.listWorkPlaceAuthorityCommand();
                    }
                    self.listWorkPlaceAuthorityParam(self.listWorkPlaceAuthorityCommand());
                });
                //obj roleCas005Command
                self.roleCas005Command = ko.observable(null);
                self.listWorkPlaceSelect = ko.observableArray([]);
                self.listWorkPlaceAuthorityCommand = ko.observableArray([]);
                self.listWorkPlaceAuthorityParam = ko.observableArray([]);

                self.listWorkplace = ko.observableArray([]);
                //obj deleteRoleCas005Command
                self.deleteRoleCas005Command = ko.observable(null);
                //obj command cas005 a 
                self.objCommandScreenB = ko.observable(null);
                //current code
                self.component.currentCode.subscribe((value) => {
                    self.componentCcg026.roleId(value);
                    self.listRole(self.component.listRole());
                    self.findRoleByRoleId(value);
                });

                self.component.roleClassification.subscribe((value) => {
                    self.assignAtr(value)
                });
                self.enumRange = ko.observableArray([
                    {value: 1, localizedName: getText("CAS005_11")},
                    {value: 2, localizedName: getText("CAS005_12")},
                    {value: 3, localizedName: getText("CAS005_13")}
                ]);
            }

            private findRoleByRoleId(value: string) {
                var self = this;
                self.isDelete(true);
                self.isCopy(true);
                let item = _.find(self.listRole(), ['roleId', value]);
                if (item !== undefined) {
                    self.roleName(item.name);
                    self.enableRoleCode(true);
                    self.assignAtr(item.assignAtr);
                    self.employeeReferenceRange(item.employeeReferenceRange);
                    self.roleCode(item.roleCode);
                    self.listWpkAuthoritySelect([]);
                    //self.getEmploymentRoleById(value);
                    //web menu
                    //if (item.assignAtr == 0) {
                        self.getRoleByRoleTiesById(value);
                    //     self.scheduleScreen(0);
                    //     self.bookingScreen(0);
                    //     self.specifyingAgent(0);
                    //     self.registeredInquiries(0);
                    //     self.visibleWebmenu(true);
                    // };
                    $("#roleNameFocus").focus();
                    _.defer(() => { errors.clearAll(); });
                } else {
                    self.roleName('');
                    self.assignAtr(0);
                    self.employeeReferenceRange(0);
                    self.roleCode('');
                    self.assignAtr(0);
                    self.selectWebMenu(0);
                    self.selectReferenceAuthority("0");
                    self.visibleWebmenu(true);
                    self.createButton();
                }
            }

            /** Select TitleMenu by Index: Start & Delete case */
            private selectRoleCodeByIndex(index: number) {
                var self = this;
                var selectRoleCodeByIndex = _.nth(self.component.listRole(), index);
                if (selectRoleCodeByIndex !== undefined) {
                    self.component.currentCode(selectRoleCodeByIndex.roleId);
                }
            }
            /**
             * select by roleID
             */
            private selectRoleByRoleId(roleid: string) {
                let self = this;
                let item = _.find(self.listRole(), ['roleId', roleid]);
                self.component.currentCode((item) ? item.roleId : "");
            }
            /**
             * functiton start page
             */
            startPage(): JQueryPromise<any> {

                let self = this;
                let dfd = $.Deferred();
                block.invisible();
                self.isRegister(true);
                self.isDelete(true);
                self.getListWebMenu();
                self.getData().done(function() {
                    self.getListWorkplace().done(() => {
                        block.clear();
                        self.component.listRole(_.orderBy(self.component.listRole(), ['roleCode'], ['asc']));
                        if (self.component.listRole().length != 0)
                            self.selectRoleCodeByIndex(0);
                        else {
                            self.createButton();
                        }
                        dfd.resolve();
                    });
                });

                return dfd.promise();
            }
            private getData() {
                let self = this;
                let dfd = $.Deferred();
                block.invisible();
                self.component.startPage().done(function() {
                    let roles = _.orderBy(self.component.listRole(), ['roleCode'], ['asc']);
                    self.listRole(roles);
                    self.component.currentCode.valueHasMutated();
                    self.assignAtr.valueHasMutated();
                    dfd.resolve();
                    block.clear();
                });
                return dfd.promise();
            }
            /**
             * get list workplace
             */
            private getListWorkplace() {
                let self = this;
                let dfd = $.Deferred();
                self.componentCcg026.roleId(self.component.currentCode());
                self.componentCcg026.startPage().done(function() {

                    self.listWorkplace(self.componentCcg026.listPermissions());
                    dfd.resolve();
                });
                return dfd.promise();
            }
            /**
             * function getRoleByRoleTiesById
             */
            getRoleByRoleTiesById(roleId: string) {
                let self = this;
                let dfd = $.Deferred();
                let param = new model.RolesParam(roleId)
                if (self.assignAtr() == 0) {
                    block.invisible();
                    service.findRoleAndWebMenu(param).done(function(rs) {
                        let data = rs.roleByRoleTies;
                        self.selectWebMenu(data.webMenuCd);
                        dfd.resolve(data);
                    }).fail(function(res: any) {
                        dfd.reject();
                        nts.uk.ui.dialog.alertError(res).then(function() { nts.uk.ui.block.clear(); });
                    }).always(() => {
                        block.clear();
                    });
                } else {
                    dfd.resolve();
                }
                return dfd.promise();
            }

            /**
             * function  get AllWorkPlace Authority
             */
            getAllWorkPlaceAuthority() {
                let self = this;
                let dfd = $.Deferred();
                block.invisible();
                service.getAllWorkPlaceAuthority().done((data) => {
                    dfd.resolve(data);
                }).fail(function(res: any) {
                    dfd.reject();
                    nts.uk.ui.dialog.alertError(res).then(function() { nts.uk.ui.block.clear(); });
                }).always(() => {
                    block.clear();
                });
                return dfd.promise();
            }
            /**
             * btnCreate
             */
            createButton() {
                let self = this;
                self.roleName('');
                self.roleCode('');
                self.selectWebMenu(0);
                self.selectReferenceAuthority("0");
                $("#roleTypeCd").focus();
                _.defer(() => { errors.clearAll(); });
            }

            /**
             * btn register
             */
            registerButton() {
                let self = this;
                self.isRegister(true);
                $("#roleNameFocus").trigger("validate");
                if (!$(".nts-input").ntsError("hasError")) {
                    self.roleCas005Command(new model.RoleCas005Command(
                        "",
                        self.roleCode(),
                        3, //roletype
                        self.employeeReferenceRange(),
                        self.roleName(),
                        self.assignAtr(),
                        self.assignAtr() == 1 ? "": self.selectWebMenu(),
                        0,
                        self.assignAtr() == 0 ? null : (parseInt(self.selectReferenceAuthority()) == 1 ?true : false)
                    ));
                    if (self.enableRoleCode()) {
                        block.invisible();
                        self.addRoleCas005(self.roleCas005Command()).done(function() {
                            self.getData().done(function() {
                                let roleId = "";
                                self.component.listRole(_.orderBy(self.component.listRole(), ['roleCode'], ['asc']));
                                for (let i = 0; i < self.component.listRole().length; i++) {
                                    if (self.component.listRole()[i].roleCode == self.roleCas005Command().roleCode) {
                                        roleId = self.component.listRole()[i].roleId;
                                    }
                                }
                                self.selectRoleByRoleId(roleId);
                                block.clear();
                            });
                        });
                    } else {
                        self.roleCas005Command().roleId = self.component.currentCode();
                        block.invisible();
                        self.updateRoleCas005(self.roleCas005Command()).done(function() {
                            let index = 0;
                            for (let i = 0; i < self.component.listRole().length; i++) {
                                if (self.component.currentCode() == self.component.listRole()[i].roleId) {
                                    index = i;
                                }
                            };
                            self.getData().done(function() {
                                self.component.listRole(_.orderBy(self.component.listRole(), ['roleCode'], ['asc']));
                                self.selectRoleByRoleId(self.roleCas005Command().roleId);
                                block.clear();
                            });
                            self.isDelete(true);
                        });

                    }
                }
            }
            /**
             * btn delete
             */
            deleteButton() {
                let self = this;
                nts.uk.ui.dialog.confirm({ messageId: "Msg_18" }).ifYes(function() {
                    let temp = new model.DeleteRoleCas005Command(self.component.currentCode());
                    let index = 0;
                    for (let i = 0; i < self.component.listRole().length; i++) {
                        if (self.component.currentCode() == self.component.listRole()[i].roleId) {
                            index = i;
                        }
                    };
                    if (index == self.component.listRole().length - 1) {
                        index = index - 1;
                    }
                    block.invisible();
                    self.deleteRoleCas005(temp).done(function() {
                        self.getData().done(function() {
                            self.component.listRole(_.orderBy(self.component.listRole(), ['roleCode'], ['asc']));
                            if (index != -1) {
                                self.selectRoleCodeByIndex(index);
                            } else {
                                self.createButton();
                            }
                            block.clear();
                        });
                    });
                });
            }
            /**
             * get list  web menu 
             */
            getListWebMenu() {
                let self = this;
                let dfd = $.Deferred<any>();
                block.invisible();
                //service.getListWebMenu().done(function(data) {
                service.findAllMenu().done(function(data) {
                    self.listWebMenu(data);
                    dfd.resolve(data);
                }).fail(function(res: any) {
                    dfd.reject();
                    nts.uk.ui.dialog.alertError(res).then(function() { nts.uk.ui.block.clear(); });
                }).always(() => {
                    block.clear();
                });
                return dfd.promise();
            }
            /**
             * add Role screen Cas005
             */
            addRoleCas005(command: model.RoleCas005Command) {
                let self = this;
                let dfd = $.Deferred<any>();
                block.invisible();
                service.addRoleCas005(command).done(function() {
                    self.component.listRole(_.orderBy(self.component.listRole(), ['roleCode'], ['asc']));
                    self.enableRoleCode(false);
                    nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                    dfd.resolve();
                }).fail(function(res: any) {
                    dfd.reject();
                    nts.uk.ui.dialog.alertError(res).then(function() { nts.uk.ui.block.clear(); });
                }).always(() => {
                    block.clear();
                });
                return dfd.promise();
            }

            /**
            * 
                Role screen Cas005
            */
            updateRoleCas005(command: model.RoleCas005Command) {
                let self = this;
                let dfd = $.Deferred<any>();
                block.invisible();
                service.updateRoleCas005(command).done(function() {
                    self.component.listRole(_.orderBy(self.component.listRole(), ['roleCode'], ['asc']));
                    nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                    dfd.resolve();
                }).fail(function(res: any) {
                    dfd.reject();
                    nts.uk.ui.dialog.alertError(res).then(function() { nts.uk.ui.block.clear(); });
                }).always(() => {
                    block.clear();
                });
                return dfd.promise();
            }

            /**
             * delete Role screen Cas005
             */
            deleteRoleCas005(command: model.DeleteRoleCas005Command) {
                let self = this;
                let dfd = $.Deferred<any>();
                block.invisible();
                service.deleteRoleCas005(command).done(function() {
                    self.component.listRole(_.orderBy(self.component.listRole(), ['roleCode'], ['asc']));
                    nts.uk.ui.dialog.info({ messageId: "Msg_16" });
                    dfd.resolve();
                }).fail(function(res: any) {
                    dfd.reject();
                    nts.uk.ui.dialog.alertError(res).then(function() { nts.uk.ui.block.clear(); });
                }).always(() => {
                    block.clear();
                });
                return dfd.promise();
            }
        }
    }
    export module model {
        export class WorkPlaceFunction {
            functionNo: number;
            displayName: string;
            displayOrder: number;
            description: string;
            initialValue: boolean;
            constructor(functionNo: number, displayName: string, displayOrder: number, description: string, initialValue: boolean) {
                this.functionNo = functionNo;
                this.displayName = displayName;
                this.displayOrder = displayOrder;
                this.description = description;
                this.initialValue = initialValue;
            }
        }
        export class RoleCas005Command {
            roleId: string;
            roleCode: string; //row 1
            roleType: number; //
            employeeReferenceRange: number; //row 3
            name: string; //row 1
            contractCode: string;
            assignAtr: number; //row 2
            companyId: string;
            webMenuCd: string; //row 5
            futureDateRefPermit: number;//A3_12,13 row 4
            approvalAuthority: boolean;
            constructor(roleId: string,
            roleCode: string, //row 1
            roleType: number, //
            employeeReferenceRange: number, //row 3
            name: string, //row 1
            assignAtr: number, //row 2
            webMenuCd: string, //row 5
            futureDateRefPermit: number,
            approvalAuthority: boolean,
            ) {
                this.roleId = roleId;
                this.roleCode = roleCode;
                this.roleType = roleType;
                this.employeeReferenceRange = employeeReferenceRange;
                this.name = name;
                this.assignAtr = assignAtr;
                this.webMenuCd = webMenuCd;
                this.approvalAuthority = approvalAuthority;
                this.futureDateRefPermit = futureDateRefPermit;
            }

        }
        export class WorkPlaceAuthorityCommand {
            roleId: string;
            companyId: string;
            functionNo: number;
            availability: boolean;
            constructor(functionNo: number, availability: boolean) {
                this.functionNo = functionNo;
                this.availability = availability;
            }
        }

        export interface IWpkAuthoritySelect {
            roleId: string;
            functionNo: number;
            availability: KnockoutObservable<boolean>;
            displayName: string;
            description: string;
        }

        export class WpkAuthoritySelect {
            roleId: string;
            functionNo: number;
            availability: KnockoutObservable<boolean>;
            displayName: string;
            description: string;
            constructor(
                functionNo: number,
                availability: boolean,
                displayName: string,
                description: string) {
                this.functionNo = functionNo;
                this.availability = ko.observable(availability);
                this.displayName = displayName;
                this.description = description;
                this.availability.subscribe(function(x) {
                    $("#listWpkAuthoritySelect").trigger("rowavailabilitychanged");
                });
            }
        }
        export class DeleteRoleCas005Command {
            roleId: string;
            constructor(roleId: string) {
                this.roleId = roleId;
            }
        }
        export class RolesParam {
            roleId: string;
            constructor(roleId: string) {
                this.roleId = roleId;
            }
        }
    }
    interface EnumConstantDto {
        value: number;
        fieldName?: string;
        localizedName: string;
    }
}