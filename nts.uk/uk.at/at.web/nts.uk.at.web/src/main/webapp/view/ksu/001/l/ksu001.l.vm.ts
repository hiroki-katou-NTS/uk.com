module nts.uk.at.view.ksu001.l.viewmodel {
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;

    export class ScreenModel {
        listTeam: KnockoutObservableArray<any> = ko.observableArray([]);
        listTeamDB: KnockoutObservableArray<any> = ko.observableArray([]);
        listTeamSetting: KnockoutObservableArray<any> = ko.observableArray([]);
        selectedTeam: KnockoutObservable<any> = ko.observable();
        teamName: KnockoutObservable<any> = ko.observable();
        columnsTeam: KnockoutObservableArray<NtsGridListColumn> = ko.observableArray([
            { headerText: nts.uk.resource.getText("KSU001_1110"), key: 'code', width: 60 },
            { headerText: nts.uk.resource.getText("KSU001_1111"), key: 'name', width: 120, formatter: _.escape },
            { headerText: nts.uk.resource.getText("KSU001_1112"), key: 'amountOfPeople', width: 60 },
        ]);
        workPlaceId: string;
        workPlaceName: string;
        workPlaceCode: KnockoutObservable<any> = ko.observable();
        workPlaceDisplayName: KnockoutObservable<any> = ko.observable();
        listEmployee: Array<any>;
        listEmployeeSwap: KnockoutObservableArray<any> = ko.observableArray([]);
        listEmployeeSwapTemp: KnockoutObservableArray<any> = ko.observableArray([]);
        selectedEmployeeSwap: KnockoutObservableArray<any> = ko.observableArray([]);
        listEmployeeTemporary: KnockoutObservableArray<any> = ko.observableArray([]);
        columnsLeftSwap: KnockoutObservableArray<nts.uk.ui.NtsGridListColumn> = ko.observableArray([
            { headerText: nts.uk.resource.getText("KSU001_1119"), key: 'code', width: 120 },
            { headerText: nts.uk.resource.getText("KSU001_1120"), key: 'name', width: 120 },
            { headerText: nts.uk.resource.getText("KSU001_1121"), key: 'teamName', width: 100, formatter: _.escape },
        ]);
        columnsRightSwap: KnockoutObservableArray<nts.uk.ui.NtsGridListColumn> = ko.observableArray([
            { headerText: nts.uk.resource.getText("KSU001_1119"), key: 'code', width: 120 },
            { headerText: nts.uk.resource.getText("KSU001_1120"), key: 'name', width: 120, formatter: _.escape },
        ]);
        onlyEmpNotTeam: KnockoutObservable<boolean> = ko.observable(false);
        isOpenedDialogLX: boolean = false;

        constructor() {
            let self = this;

            self.workPlaceId = getShared('dataForScreenL').workplaceId;
            self.listEmployee = getShared('dataForScreenL').empItems;
            self.selectedEmployeeSwap = ko.observableArray([]);
            self.listEmployeeTemporary = ko.observableArray([]);
            self.selectedTeam('');
            self.teamName('');

            if (self.listEmployee.length > 0) {
                self.workPlaceName = self.listEmployee[0].workplaceName;
            } else {
                self.workPlaceName = '';
            }

            self.selectedTeam.subscribe(function(newValue) {
                if (self.onlyEmpNotTeam() == false) {
                    self.listEmployeeSwap().concat(self.listEmployeeTemporary());
                    let teamSelected = _.filter(self.listEmployeeSwap(), ['teamCode', newValue]);
                    let newListEmployeeSwap = self.listEmployeeSwap().concat(self.selectedEmployeeSwap());
                    self.selectedEmployeeSwap(_.orderBy(teamSelected, ['empId'], ['asc']));
                    self.listEmployeeSwap(_.orderBy(newListEmployeeSwap, ['empId'], ['asc']));
                } else {
                    //self.listEmployeeTemporary(self.selectedEmployeeSwap());
                    let teamSelected = _.filter(self.listEmployeeTemporary(), ['teamCode', newValue]);
                    let temporary = _.reject(self.listEmployeeSwap(), ['teamCode', 'なし']);
                    let teamSelectedTemp = self.selectedEmployeeSwap().concat(temporary);
                    self.listEmployeeTemporary(teamSelectedTemp);
                    self.selectedEmployeeSwap(_.orderBy(teamSelected, ['empId'], ['asc']));
                }

                //self.listEmployeeSwapTemp(_.clone(newListEmployeeSwap));
                self.teamName(_.find(self.listTeam(), ['code', self.selectedTeam()]).name);
            });
            self.selectedEmployeeSwap.subscribe(() => {
                let employees = _.orderBy(self.listEmployeeSwap(), ['empId'], ['asc']);
                if (!_.isEqual(employees, self.listEmployeeSwap())) {
                    self.listEmployeeSwap(employees);
                }
                let selectedEmployees = _.orderBy(self.selectedEmployeeSwap(), ['empId'], ['asc']);
                if (!_.isEqual(selectedEmployees, self.selectedEmployeeSwap())) {
                    self.selectedEmployeeSwap(selectedEmployees);
                }
            });
            self.onlyEmpNotTeam.subscribe(function(value) {
                self.filterEmpNotTeam(value);
            });
            let data = {
                workplaceId: self.workPlaceId,
                baseDate: moment().toISOString()
            }
            service.getWorkPlaceById(data).done((wkp) => {
                self.workPlaceCode(wkp.workplaceCode);
                self.workPlaceDisplayName(wkp.wkpDisplayName);
            });
        }

        startPage(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            self.getAllTeam().done(() => {
                if (self.listTeamDB().length == 0 && !self.isOpenedDialogLX) {
                    self.openDialogLX();
                }
                self.getAllTeamSetting().done(() => {
                    // init selectTeam is first value
                    if (self.listTeamDB().length > 0) {
                        self.selectedTeam(self.listTeamDB()[0].teamCode);
                    }
                    dfd.resolve();
                }).fail(() => {
                    dfd.reject();
                });
            });
            return dfd.promise();
        }

        /**
         * open dialog LX
         */
        openDialogLX(): void {
            let self = this;
            setShared("workPlaceId", self.workPlaceId);
            nts.uk.ui.windows.sub.modal("/view/ksu/001/lx/index.xhtml").onClosed(() => {
                self.isOpenedDialogLX = true;
                self.startPage();
            });
        }

        getListTeam(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            return dfd.promise();
        }

        /**
        * get all team
        */
        getAllTeam(): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred();
            service.findAllByWorkPlace(self.workPlaceId).done(data => {
                self.listTeamDB(data);
                dfd.resolve();
            }).fail(() => {
                dfd.reject();
            });
            return dfd.promise();
        }

        getAllTeamSetting(): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred();
            service.findAll().done(data => {
                let employeesSwapLeft = [];
                let employeesSwapRight = [];
                let teamDB = self.listTeamDB();
                _.forEach(self.listEmployee, value => {
                    // add teamcode to employee
                    let employeeSeting: any = _.find(data, ["sid", value.empId]);
                    let employee = new EmployeeModel(value);
                    if (employeeSeting) {
                        //check team exist
                        var team = _.find(teamDB, function(item) { return item.teamCode == employeeSeting.teamCode; });
                        if (team) {
                            employee.teamCode = team.teamCode;
                            employee.teamCodeOld = team.teamCode;
                            employee.teamName = team.teamName;
                        } else {
                            employee.teamName = "なし";
                        }
                    } else {
                        employee.teamName = "なし";
                    }
                    if (employee.teamCode == self.selectedTeam()) {
                        employeesSwapRight.push(employee);
                    } else {
                        employeesSwapLeft.push(employee);
                    }
                });
                //add employee to swaplistleft 
                self.listEmployeeSwap(employeesSwapLeft);
                //add employee to swaplistright
                self.selectedEmployeeSwap(employeesSwapRight);
                // add number of employee to TeamList
                let arrayTeam = [];
                _.forEach(teamDB, value => {
                    let count = _.filter(data, ['teamCode', value.teamCode]).length;
                    let team = new TeamModel(value.teamCode, value.teamName, count + '人');
                    arrayTeam.push(team);
                });
                self.listTeam(arrayTeam);
                self.filterEmpNotTeam(self.onlyEmpNotTeam());
                dfd.resolve();
            }).fail(() => {
                dfd.reject();
            });
            return dfd.promise();
        }

        /**
         * Close dialog
         */
        closeDialog(): void {
            nts.uk.ui.windows.close();
        }

        /**
         * add employee to team
         */
        addEmToTeam(): void {
            let self = this;
            nts.uk.ui.block.invisible();
            let data: any = {};
            let teamCodes = _.map(self.selectedEmployeeSwap(), 'teamCode');
            data.employeeCodes = _.map(self.selectedEmployeeSwap(), 'empId');
            data.teamCode = self.selectedTeam();
            data.workPlaceId = self.workPlaceId;
            let isSwapTeamCode = (_.filter(teamCodes, function(o) {
                if (o != 'なし') {
                    return o != self.selectedTeam();
                }
            }).length > 0) ? true : false;

            if (isSwapTeamCode) {
                nts.uk.ui.dialog.confirm({ messageId: "Msg_342" }).ifYes(() => {
                    service.addEmToTeam(data).done(function() {
                        self.getAllTeamSetting().done(function() {
                            self.selectedTeam(data.teamCode);
                        });
                        nts.uk.ui.dialog.info(nts.uk.resource.getMessage('Msg_15'));
                    }).fail(function(error) {
                        nts.uk.ui.dialog.alertError(error.messageId).then(function() {
                            nts.uk.ui.block.clear();
                        });
                    });
                }).then(function() {
                    nts.uk.ui.block.clear();
                });
            } else {
                service.addEmToTeam(data).done(function() {
                    self.getAllTeamSetting().done(function() {
                        self.selectedTeam(data.teamCode);
                    });
                    nts.uk.ui.dialog.info(nts.uk.resource.getMessage('Msg_15'));
                }).fail(function(error) {
                    nts.uk.ui.dialog.alertError(error.messageId).then(function() {
                        nts.uk.ui.block.clear();
                    });
                }).then(() => { nts.uk.ui.block.clear(); });
            }
        }

        /**
         * Event checkbox: filter employee not in team
         */
        filterEmpNotTeam(isChecked) {
            var self = this;
            if (isChecked == true) {
                let teamSelected = _.filter(self.listEmployeeSwap(), ['teamCode', 'なし']);
                let temporary = _.reject(self.listEmployeeSwap(), ['teamCode', 'なし']);
                self.listEmployeeTemporary(temporary);
                self.listEmployeeSwap(teamSelected);
            } else {
                let newListEmployeeSwap = self.listEmployeeSwap().concat(self.listEmployeeTemporary());
                let employees = _.orderBy(newListEmployeeSwap, ['empId'], ['asc']);
                self.listEmployeeSwap(employees);
            }
        }
    }

    class TeamModel {
        code: string;
        name: string;
        amountOfPeople: string;
        constructor(code: string, name: string, amountOfPeople: string) {
            this.code = code;
            this.name = name;
            this.amountOfPeople = amountOfPeople;
        }
    }

    interface IPersonModel {
        empId: string,
        empCd: string,
        empName: string,
        workplaceId: string,
        wokplaceCd: string,
        workplaceName: string,
        baseDate?: number
    }

    class EmployeeModel {
        empId: string;
        code: string;
        name: string;
        workplaceId: string;
        wokplaceCd: string;
        workplaceName: string;
        baseDate: number;
        teamCode: string;
        teamCodeOld: string;
        constructor(param: IPersonModel) {
            this.empId = param.empId;
            this.code = param.empCd;
            this.name = param.empName;
            this.workplaceId = param.workplaceId;
            this.wokplaceCd = param.wokplaceCd;
            this.workplaceName = param.workplaceName;
            this.baseDate = param.baseDate;
            this.teamCode = 'なし';
        }
    }

    class EmployeesAddModel {
        employeeCodes: Array<string>;
        teamCode: string;
        workPlaceId: string;
        constructor(employeeCodes: Array<string>, teamCode: string, workPlaceId: string) {
            this.employeeCodes = employeeCodes;
            this.teamCode = teamCode;
            this.workPlaceId = workPlaceId;
        }
    }

    class TeamSettingModel {
        teamCode: string;
        workPlaceId: string;
        sid: string;
        constructor(teamCode: string, workPlaceId: string, sid: string) {
            this.teamCode = teamCode;
            this.workPlaceId = workPlaceId;
            this.sid = sid;
        }
    }
}