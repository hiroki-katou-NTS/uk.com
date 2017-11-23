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

            //copy from cdl009 parent
            isMultiSelect: KnockoutObservable<boolean>;
            selectedIds: KnockoutObservableArray<string>;
            baseDate: KnockoutObservable<Date>;
            target: KnockoutObservable<number>;
            selectedEmployeeId: KnockoutObservable<string>;
            selectedEmps: KnockoutObservableArray<string>;
            selectionOption: KnockoutObservableArray<any>;
            selectedOption: KnockoutObservable<number>;
            targetList: KnockoutObservableArray<any>;
            selectedTarget: KnockoutObservable<number>;

            //
            roleSetList: KnockoutObservableArray<RoleSet>;
            roleSetPersonList: KnockoutObservableArray<RoleSetPerson>;
            dateValue: KnockoutObservable<Period>
            selectedRoleSet: KnockoutObservable<RoleSet>;
            selectedRoleSetPerson: KnockoutObservable<RoleSetPerson>;
            constructor() {
                let self = this;
                self.dateValue = ko.observable(new Period('', ''));
                self.selectedRoleSet = ko.observable(new RoleSet('', ''));
                self.selectedRoleSetPerson = ko.observable(new RoleSetPerson('', '', '', '', '', ''));
                self.roleSetList = ko.observableArray([]);
                self.roleSetPersonList = ko.observableArray([]);
                

                //copy from cdl009 parent
                self.isMultiSelect = ko.observable(true);
                self.selectedIds = ko.observableArray(['000000000000000000000000000000000006', '000000000000000000000000000000000009']);
                self.baseDate = ko.observable(moment(new Date()).toDate());
                self.target = ko.observable(TargetClassification.WORKPLACE);
                self.selectedEmployeeId = ko.observable('');
                self.selectedEmps = ko.observableArray([]);

                self.selectionOption = ko.observableArray([
                    { code: 0, name: 'Single' },
                    { code: 1, name: 'Multiple' },
                ]);
                self.selectedOption = ko.observable(self.isMultiSelect() ? 1 : 0);
                self.selectedOption.subscribe(function(data: number) {
                    if (data == 0) {
                        self.isMultiSelect(false);
                    }
                    else {
                        self.isMultiSelect(true);
                    }
                });
                self.targetList = ko.observableArray([
                    { code: 1, name: 'WorkPlace' },
                    { code: 2, name: 'Department' },
                ]);
                self.selectedTarget = ko.observable(self.target());
                self.selectedTarget.subscribe(function(data: number) {
                    if (data == TargetClassification.DEPARTMENT) {
                        alert("Department Target is not covered this time!");
                        self.selectedTarget(TargetClassification.WORKPLACE);
                    }
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
                //                        self.selectedRoleSet = ko.observable(ko.toJS(self.roleSetList[0]));
                //                        self.selectedRoleSet.subscribe(function(roleSet: RoleSet) {
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
                //                        });
                //
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
                
                self.selectedRoleSet = ko.observable(self.roleSetList()[0]);
//                self.selectedRoleSet.subscribe(function(roleSet: RoleSet) {
                    self.roleSetPersonList = ko.observableArray([
                        new RoleSetPerson('05', '000002', '02', 'Employee 2', new Date().toISOString(), new Date().toISOString()),
                        new RoleSetPerson('05', '000003', '03', 'Employee 3', new Date().toISOString(), new Date().toISOString()),
                        new RoleSetPerson('05', '000004', '04', 'Employee 4', new Date().toISOString(), new Date().toISOString()),
                        new RoleSetPerson('05', '000005', '05', 'Employee 5', new Date().toISOString(), new Date().toISOString()),
                        new RoleSetPerson('05', '000006', '06', 'Employee 6', new Date().toISOString(), new Date().toISOString()),
                        new RoleSetPerson('05', '000007', '07', 'Employee 7', new Date().toISOString(), new Date().toISOString()),
                        new RoleSetPerson('05', '000008', '08', 'Employee 8', new Date().toISOString(), new Date().toISOString()),
                        new RoleSetPerson('05', '000009', '09', 'Employee 9', new Date().toISOString(), new Date().toISOString()),
                        new RoleSetPerson('05', '000010', '10', 'Employee 10', new Date().toISOString(), new Date().toISOString())
                    ]);                   
                    self.selectedRoleSetPerson = ko.observable(self.roleSetPersonList()[0]);
//                });
                
//                self.roleSetPerson = ko.observable(new RoleSetPerson(self.roleSetList()[0], self.employeeList[0], new Date().toISOString(), new Date().toISOString()));

//                $(".fixed-table").ntsFixedTable({ height: 200 });
                dfd.resolve();
                block.clear();
                return dfd.promise();
            }

            createNewRoleSetPerson() {

            }

            registerRoleSetPerson() {
                let self = this, data: RoleSetPerson = ko.toJS(self.roleSetPersonList[0]);

                let command: any = {
                    roleSetCd: data.roleSetCd,
                    employeeId: data.employeeId,
                    period: data.period,
                    mode: 1
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
                let self = this, data: RoleSetPerson = ko.toJS(self.roleSetPersonList[0]);

                let command: any = {
                    roleSetCd: data.roleSetCd,
                    employeeId: data.employeeId,
                    period: data.period
                };

                block.invisible();

                new service.Service().deleteData(command).done(function() {
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
                    isMultiSelect: self.isMultiSelect(),
                    // For Workplace List Kcp004
                    selectedIds: self.selectedIds(),
                    // For Workplace List Kcp004
                    baseDate: self.baseDate(),
                    // Workplace or Department
                    target: self.target()
                }, true);

                modal("/view/cdl/009/a/index.xhtml").onClosed(function() {
                    var isCancel = getShared('CDL009Cancel');
                    if (isCancel) {
                        return;
                    }
                    var output = getShared('CDL009Output');
                    if (self.isMultiSelect()) {
                        self.selectedEmps(output);
                    } else {
                        self.selectedEmployeeId(output);
                    }

                });
            }

            // Get Code of Selected Employee(s)
            private getSelectedEmp(): string {
                var self = this;
                if (self.isMultiSelect()) {
                    return self.selectedEmps().join(', ');
                } else {
                    return self.selectedEmployeeId();
                }
            }

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
        period: Period;

        constructor(roleSetCd: string, employeeId: string, employeeCd: string, employeeName: string, start: string, end: string) {
            this.roleSetCd = roleSetCd;
            this.employeeId = employeeId;
            this.employeeCd = employeeCd;
            this.employeeName = employeeName;
            this.period = new Period(start, end);
        }
    }

    export class Period {
        startDate: string;
        endDate: string;

        constructor(start: string, end: string) {
            this.startDate = start;
            this.endDate = end;
        }
    }

    export class TargetClassification {
        static WORKPLACE = 1;
        static DEPARTMENT = 2;
    }

    export class ScreenMode {
        static NEW = 0;
        static UPDATE = 1;
    }
}

