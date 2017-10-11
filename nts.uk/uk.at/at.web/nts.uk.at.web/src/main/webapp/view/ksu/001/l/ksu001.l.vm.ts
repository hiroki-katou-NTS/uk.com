module nts.uk.at.view.ksu001.l.viewmodel {

    export class ScreenModel {
        listTeam: KnockoutObservableArray<any> = ko.observableArray([]);
        listTeamDB: KnockoutObservableArray<any> = ko.observableArray([]);
        listTeamSetting: KnockoutObservableArray<any> = ko.observableArray([]);
        selectedTeam: KnockoutObservable<any> = ko.observable();
        teamName: KnockoutObservable<any> = ko.observable();
        columnsTeam: KnockoutObservableArray<NtsGridListColumn> = ko.observableArray([
            { headerText: nts.uk.resource.getText("KSU001_1110"), key: 'code', width: 60 },
            { headerText: nts.uk.resource.getText("KSU001_1111"), key: 'name', width: 120 },
            { headerText: nts.uk.resource.getText("KSU001_1112"), key: 'amountOfPeople', width: 60 },
        ]);
        workPlaceId: string;
        workPlaceName: string;
        listEmployee: Array<any>;
        listEmployeeSwap: KnockoutObservableArray<any> = ko.observableArray([]);
        selectedEmployeeSwap: KnockoutObservableArray<any> = ko.observableArray([]);
        columnsSwap: KnockoutObservableArray<nts.uk.ui.NtsGridListColumn> = ko.observableArray([
            { headerText: nts.uk.resource.getText("KSU001_1119"), key: 'code', width: 120 },
            { headerText: nts.uk.resource.getText("KSU001_1120"), key: 'name', width: 90 },
            { headerText: nts.uk.resource.getText("KSU001_1121"), key: 'teamCode', width: 100 },
        ]);

        constructor() {
            let self = this;
            self.workPlaceId = nts.uk.ui.windows.getShared('workPlaceId');
            self.listEmployee = nts.uk.ui.windows.getShared('listEmployee');
            self.selectedEmployeeSwap = ko.observableArray([]);
            self.selectedTeam('');
            self.teamName('');
            if (self.listEmployee.length > 0) {
                self.workPlaceName = self.listEmployee[0].workplaceName;
            } else {
                self.workPlaceName = '';
            }
            self.selectedTeam.subscribe(function(newValue) {
                let teamSelected = _.filter(self.listEmployeeSwap(), ['teamCode', newValue]);
                let newListEmployeeSwap = self.listEmployeeSwap().concat(self.selectedEmployeeSwap());
                self.selectedEmployeeSwap(teamSelected);
                self.listEmployeeSwap(newListEmployeeSwap);
                self.teamName(_.find(self.listTeam(), ['code', self.selectedTeam()]).name);
            });
        }
        startPage(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            self.getAllTeam().done(() => {
                if (self.listTeamDB().length == 0) {
                    self.openDialogLX();
                }
                self.getAllTeamSetting().done(() => {
                    // init selectTeam is first value
                    self.selectedTeam(self.listTeamDB()[0].teamCode);
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
            var self = this;
            nts.uk.ui.windows.setShared("workPlaceId", "000000A3");
            nts.uk.ui.windows.sub.modal("/view/ksu/001/lx/index.xhtml").onClosed(() => {
                self.startPage().done();
            });;
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
            })
                .fail(() => {
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
                //clear list selectedswaplist
                let newListEmployeeSwap = self.listEmployeeSwap().concat(self.selectedEmployeeSwap());
                self.selectedEmployeeSwap();
                self.listEmployeeSwap(newListEmployeeSwap);
                _.forEach(self.listEmployee, value => {
                    // add teamcode to employee
                    let employeeSeting = _.find(data, ["sid", value.empId]);
                    let employee = new EmployeeModel(value);
                    if (employeeSeting) {
                        //check team exist
                        if (_.findKey(teamDB, ['teamCode', employeeSeting.teamCode])) {
                            employee.teamCode = employeeSeting.teamCode;
                        }
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
                dfd.resolve();
            })
                .fail(() => {
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
            let data = {};
            data.employeeCodes = _.map(self.selectedEmployeeSwap(), 'empId');
            data.teamCode = self.selectedTeam();
            data.workPlaceId = self.workPlaceId;
            service.addEmToTeam(data).done(function() {
                self.getAllTeamSetting().done(function() {
                    self.selectedTeam(data.teamCode);
                });

            });
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