module nts.uk.com.view.cas014.b {
    import getText = nts.uk.resource.getText;
    import confirm = nts.uk.ui.dialog.confirm;
    import alertError = nts.uk.ui.dialog.alertError;
    import info = nts.uk.ui.dialog.info;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import textUK = nts.uk.text;
    import block = nts.uk.ui.block;
    export module viewmodel {
        export class ScreenModel {

            roleSetList: KnockoutObservableArray<RoleSet>;
            roleSetPersonList: KnockoutObservableArray<RoleSetPerson>;
            dateValue: KnockoutObservable<any>
            selectedRoleSet: KnockoutObservable<string>;
            selectedEmployeeId: KnockoutObservable<string>;
            roleSetPerson: KnockoutObservable<RoleSetPerson>;
            screenMode: KnockoutObservable<number>;
            constructor() {
                let self = this;
                self.screenMode = ko.observable(ScreenMode.UPDATE);
                self.dateValue = ko.observable({});
                self.roleSetList = ko.observableArray([]);
                self.roleSetPersonList = ko.observableArray([]);
                self.selectedRoleSet = ko.observable('');
                self.selectedEmployeeId = ko.observable('');
                self.roleSetPerson = ko.observable(new RoleSetPerson('', '', '', '', '', ''));
                self.selectedEmployeeId.subscribe(function(data: any) {
                    if (data) {
                        let item = _.find(ko.toJS(self.roleSetPersonList), (x: RoleSetPerson) => x.employeeId == data);
                        if (item) {
                            self.roleSetPerson(item);
                            self.screenMode(ScreenMode.UPDATE);
                        } else {
                            self.getEmployeeInfo(data);
                            self.screenMode(ScreenMode.NEW);
                        }
                    }
                });
                self.selectedRoleSet.subscribe(function(data: any) {
                    if (data) {
                        self.roleSetPersonList([
                            new RoleSetPerson(data, '000002', data, 'Employee 2', new Date().toISOString(), new Date().toISOString()),
                            new RoleSetPerson(data, '000003', data, 'Employee 3', new Date().toISOString(), new Date().toISOString()),
                            new RoleSetPerson(data, '000004', data, 'Employee 4', new Date().toISOString(), new Date().toISOString()),
                            new RoleSetPerson(data, '000005', data, 'Employee 5', new Date().toISOString(), new Date().toISOString()),
                            new RoleSetPerson(data, '000006', data, 'Employee 6', new Date().toISOString(), new Date().toISOString()),
                            new RoleSetPerson(data, '000007', data, 'Employee 7', new Date().toISOString(), new Date().toISOString()),
                            new RoleSetPerson(data, '000008', data, 'Employee 8', new Date().toISOString(), new Date().toISOString()),
                            new RoleSetPerson(data, '000009', data, 'Employee 9', new Date().toISOString(), new Date().toISOString()),
                            new RoleSetPerson(data, '000010', data, 'Employee 10', new Date().toISOString(), new Date().toISOString())
                        ]);
                        self.selectedEmployeeId(self.roleSetPersonList()[0].employeeId);
                    } else {
                        self.roleSetPersonList.removeAll();
                    }
                });
                self.dateValue.subscribe((data: any) => {
                    if (self.roleSetPerson) {
                        self.roleSetPerson().startDate = moment.utc(data.startDate, "YYYY/MM/DD").toISOString();
                        self.roleSetPerson().endDate = moment.utc(data.endDate, "YYYY/MM/DD").toISOString();
                    }
                });
                self.roleSetPerson.subscribe((data: RoleSetPerson) => {
                    self.dateValue({ startDate: data.startDate, endDate: data.endDate });
                });

            }

            startPage(): JQueryPromise<any> {
                let self = this,
                    dfd = $.Deferred();
                block.invisible();

                //                new service.Service().getAllRoleSet().done(function(rsList: Array<any>) {
                //                    if (rsList && rsList.length) {
                //                        self.roleSetList.removeAll();
                //                        let _rsList: Array<RoleSet> = _.map(rsList, rs => {
                //                            return new RoleSet(rs.code, rs.name);
                //                        });
                //                        _rsList = _.sortBy(_rsList, ['code']);
                //                        _.each(_rsList, rs => self.roleSetList.push(rs));
                //
                //                        //select first role set
                //                        self.selectedRoleSet(self.roleSetList()[0].code);
                //                    }
                //                    dfd.resolve();
                //                }).fail(function(error) {
                //                    alertError("shit happened!");
                //                    dfd.reject();
                //                });
                self.roleSetList = ko.observableArray([
                    new RoleSet('01', 'Role Set 1'),
                    new RoleSet('02', 'Role Set 2'),
                    new RoleSet('03', 'Role Set 3'),
                    new RoleSet('04', 'Role Set 4'),
                    new RoleSet('05', 'Role Set 5'),
                    new RoleSet('06', 'Role Set 6'),
                    new RoleSet('07', 'Role Set 7'),
                    new RoleSet('08', 'Role Set 8'),
                    new RoleSet('09', 'Role Set 9'),
                    new RoleSet('10', 'Role Set 10')
                ]);

                self.selectedRoleSet(self.roleSetList()[0].code);


                //                self.roleSetPerson = ko.observable(new RoleSetPerson(self.roleSetList()[0], self.employeeList[0], new Date().toISOString(), new Date().toISOString()));

                //                $(".fixed-table").ntsFixedTable({ height: 200 });
                dfd.resolve();
                block.clear();
                return dfd.promise();
            }

            loadRoleSetHolder() {
                let self = this;
                //                            new service.Service().getAllRoleSetPerson(roleSet.code).done(function(data: Array<any>) {
                //                                if (data && data.length) {
                //                                    self.roleSetPersonList.removeAll();
                //                                    let _rspList: Array<RoleSetPerson> = _.map(data, rsp => {
                //                                        return new RoleSetPerson(rsp.roleSetCd, rsp.employeeId, rsp.employeeCd, rsp.employeeName, rsp.startDate, rsp.endDate);
                //                                    });
                //                                    _rspList = _.sortBy(_rspList, ['employeeCd']);
                //                                    _.each(_rspList, rsp => self.roleSetPersonList.push(rsp));
                //                                }
                //                                dfd.resolve();
                //                            }).fail(function(error) {
                //                                alertError("shit happened!");
                //                                dfd.reject();
                //                            });
            }

            getEmployeeInfo(empId: string) {
                let self = this;
                self.roleSetPerson(new RoleSetPerson(self.selectedRoleSet(), empId, '5555500', 'selected employee', '', ''));
                //                            new service.Service().getEmployeeInfo(self.selectedRoleSet).done(function(data: Array<any>) {
                //                                if (data) {
                //                                }
                //                                dfd.resolve();
                //                            }).fail(function(error) {
                //                                alertError("shit happened!");
                //                                dfd.reject();
                //                            });
            }

            createNewRoleSetPerson() {
                let self = this;
                self.selectedEmployeeId('');
                self.roleSetPerson(new RoleSetPerson('', '', '', '', '', ''));
                self.dateValue({});
                self.screenMode(ScreenMode.NEW);
            }

            registerRoleSetPerson() {
                let self = this, data: RoleSetPerson = ko.toJS(self.roleSetPerson);

                let command: any = {
                    roleSetCd: data.roleSetCd,
                    employeeId: data.employeeId,
                    startDate: data.startDate,
                    endDate: data.endDate,
                    mode: self.screenMode
                };

                block.invisible();

                new service.Service().registerData(command).done(function() {
                    //display registered data in selected state
                    //set to update mode 
                    info({ messageId: "Msg_15" }).then(() => {
                        block.clear();
                    });
                }).fail(error => {
                    alertError({ messageId: error.message });
                    block.clear();
                });
            }

            deleteRoleSetPerson() {
                let self = this, data: RoleSetPerson = ko.toJS(self.roleSetPerson);

                let command: any = {
                    roleSetCd: data.roleSetCd,
                    employeeId: data.employeeId,
                    startDate: data.startDate,
                    endDate: data.endDate,
                    mode: self.screenMode
                };

                nts.uk.ui.dialog.confirm({ messageId: "Msg_551" }).ifYes(() => {
                    // call service remove
                    block.invisible();

                    new service.Service().deleteData(command).done(function() {
                        //remove
                        //set to update mode, select item 
                        info({ messageId: "Msg_16" }).then(() => {
                            block.clear();
                        });
                    }).fail(error => {
                        alertError({ messageId: error.message });
                        block.clear();
                    });

                }).ifCancel(() => {
                    //$("#A_INP_NAME").focus();
                });

            }

            openDialogCDL009() {
                //                let self = this;
                //                block.invisible();
                //                //setShared('categoryId', self.roleSetPerson().employeeId());
                //                modal("/view/cdl/009/a/index.xhtml").onClosed(() => {
                //                    //let ctgCode = self.currentData().perInfoCtgSelectCode();
                //                    //self.currentData().perInfoCtgSelectCode("");
                //                    //self.currentData().perInfoCtgSelectCode(ctgCode);
                //                    block.clear();
                //                });
                let self = this;

                // Set Param
                setShared('CDL009Params', {
                    // isMultiSelect For Employee List Kcp005
                    isMultiSelect: false,
                    // For Workplace List Kcp004
                    //selectedIds: self.selectedIds(),
                    // For Workplace List Kcp004
                    baseDate: moment(new Date()).toDate(),
                    // Workplace or Department
                    target: TargetClassification.DEPARTMENT
                }, true);

                modal("/view/cdl/009/a/index.xhtml").onClosed(function() {
                    var isCancel = getShared('CDL009Cancel');
                    if (isCancel) {
                        return;
                    }
                    var output = getShared('CDL009Output');
                    self.selectedEmployeeId(output);
                });
            }

            // Get Code of Selected Employee(s)
            //            private getSelectedEmp(): string {
            //                var self = this;
            //                return self.selectedEmployeeId();
            //            }

        }
    }

    export class RoleSet {
        code: string;
        name: string;

        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }

    export class RoleSetPerson {
        roleSetCd: string;
        employeeId: string;
        employeeCd: string;
        employeeName: string;
        startDate: string;
        endDate: string;
        displayDateRange: string;

        constructor(roleSetCd: string, employeeId: string, employeeCd: string, employeeName: string, start: string, end: string) {
            this.roleSetCd = roleSetCd;
            this.employeeId = employeeId;
            this.employeeCd = employeeCd;
            this.employeeName = employeeName;
            this.startDate = start;
            this.endDate = end;
            this.displayDateRange = this.startDate.slice(0, 10).replace(/-/g, "/") + ' ~ ' + this.endDate.slice(0, 10).replace(/-/g, "/");
        }

    }

    //    export class Period {
    //        startDate: string;
    //        endDate: string;
    //
    //        constructor(start: string, end: string) {
    //            this.startDate = start;
    //            this.endDate = end;
    //        }
    //    }

    export class TargetClassification {
        static WORKPLACE = 1;
        static DEPARTMENT = 2;
    }

    export class ScreenMode {
        static NEW = 0;
        static UPDATE = 1;
    }
}

