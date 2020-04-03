module cmm044.d.viewmodel {
    import EmployeeSearchDto = nts.uk.com.view.ccg.share.ccg.service.model.EmployeeSearchDto;
    export class ScreenModel {
        date: KnockoutObservable<string>;
        yearMonth: KnockoutObservable<number>;
        inp_startDate: KnockoutObservable<string>;
        inp_endDate: KnockoutObservable<string>;
        dateValue: KnockoutObservable<any>;
        histItems: KnockoutObservableArray<model.AgentDto>;
        personList: KnockoutObservableArray<AgentData>;
        dataPerson: any;
        tabs: KnockoutObservableArray<any>;

        constructor() {
            var self = this;
            self.date = ko.observable('20000101');
            self.yearMonth = ko.observable(200001);

            self.dateValue = ko.observable({ startDate: '', endDate: '' });
            self.histItems = ko.observableArray([]);

            self.personList = ko.observableArray([]);
            self.dataPerson = ko.observableArray([]);
            self.tabs = ko.observableArray(nts.uk.ui.windows.getShared("CMM044_TABS"));
            $("#fixed-table").ntsFixedTable({height:374});
        }
        start() : JQueryPromise<any>  {
            var self = this,
                dfd = $.Deferred();
             
            self.personList.removeAll();
            dfd.resolve();

            return dfd.promise();
        }

        closeDialog(): void {
            nts.uk.ui.windows.close();
        }

        findEmployee(employeeIds): JQueryPromise<any> {
            var self = this,
                dfd = $.Deferred();
            var option = {
                baseDate: moment().toDate(),
                employeeIds: employeeIds
            };
            service.findEmployees(option).done(function(res: Array<service.EmployeeResult>) {
                self.dataPerson(res);
                dfd.resolve();
            }).fail(function(error) {
                dfd.reject(error);
            });
            return dfd.promise();
        }

        findAgentByDate(): JQueryPromise<any> {
            var self = this,
                employeeIds = [];
            var dfd = $.Deferred();
            self.personList.removeAll();
            service.findAgentByDate(self.dateValue().startDate, self.dateValue().endDate).done(function(agent_arr: Array<model.AgentDto>) {

                if (agent_arr.length == 0) {
                    nts.uk.ui.dialog.alert(nts.uk.resource.getMessage("Msg_7")).then(() => {
                        self.dateValue({ startDate: '', endDate: '' });
                    });
                    return;
                }

                _.each(agent_arr, x => {
                    employeeIds.push(x.employeeId);
                    if (x.agentSid1) {
                        employeeIds.push(x.agentSid1);
                    }

                    if (x.agentSid2) {
                        employeeIds.push(x.agentSid2);
                    }

                    if (x.agentSid3) {
                        employeeIds.push(x.agentSid3);
                    }

                    if (x.agentSid4) {
                        employeeIds.push(x.agentSid4);
                    }
                });

                var employeUniqIds = _.uniq(employeeIds);

                self.findEmployee(employeUniqIds).done(function() {
                    _.forEach(agent_arr, function(agent: model.AgentDto) {
                        var employee = _.find(self.dataPerson(), function(e: service.EmployeeResult) {
                            return e.employeeId == agent.employeeId;
                        });
                        if (employee) {
                            self.personList.push(new AgentData(employee.employeeCode, employee.employeeName, employee.workplaceCode, employee.workplaceName, employee.jobTitleName, agent.startDate, agent.endDate, self.tabs()[0].title, self.getAgentAppType(agent.agentAppType1, agent.agentSid1), self.getEmpNameAgentAppType(agent.agentAppType1, agent.agentSid1), self.getColSpanAgentAppType(agent.agentAppType1)));
                            self.personList.push(new AgentData("", "", "", "", "", "", "", self.tabs()[1].title, self.getAgentAppType(agent.agentAppType2, agent.agentSid2), self.getEmpNameAgentAppType(agent.agentAppType2, agent.agentSid2), self.getColSpanAgentAppType(agent.agentAppType2)));
                            self.personList.push(new AgentData("", "", "", "", "", "", "", self.tabs()[2].title, self.getAgentAppType(agent.agentAppType3, agent.agentSid3), self.getEmpNameAgentAppType(agent.agentAppType3, agent.agentSid3), self.getColSpanAgentAppType(agent.agentAppType3)));
                            self.personList.push(new AgentData("", "", "", "", "", "", "", self.tabs()[3].title, self.getAgentAppType(agent.agentAppType4, agent.agentSid4), self.getEmpNameAgentAppType(agent.agentAppType4, agent.agentSid4), self.getColSpanAgentAppType(agent.agentAppType4)));
                        }
                    });
                });
                dfd.resolve();

            }).fail(function(error) {
                nts.uk.ui.dialog.alertError(error.message);
                dfd.reject(error);
            });
            return dfd.promise();
        }

        getAgentAppType(agentAppType: number, agentSID): string {
            var self = this;
            var result = "";
            switch (agentAppType) {
                case model.AgentAppType.SUBSTITUTE_DESIGNATION:
                    var employee = _.find(self.dataPerson(), function(e: service.EmployeeResult) {
                        return e.employeeId == agentSID;
                    });
                    result = employee.employeeCode; //convert to employeeCode
                    break;
                case model.AgentAppType.PATH:
                    result = nts.uk.resource.getText("CMM044_17");
                    break;
                case model.AgentAppType.NO_SETTINGS:
                    result = nts.uk.resource.getText("CMM044_18");
                    break;
            }

            return result;
        }

        getEmpNameAgentAppType(agentAppType: number, agentSID): string {
            var self = this;
            var result = "";
            switch (agentAppType) {
                case model.AgentAppType.SUBSTITUTE_DESIGNATION:
                    var employee = _.find(self.dataPerson(), function(e: service.EmployeeResult) {
                        return e.employeeId == agentSID;
                    });
                    result = employee.employeeName;
                    break;
                case model.AgentAppType.PATH:
                    result = "";
                    break;
                case model.AgentAppType.NO_SETTINGS:
                    result = "";
                    break;
            }

            return result;
        }

        getColSpanAgentAppType(agentAppType: number): boolean {
            var result = false;
            switch (agentAppType) {
                case model.AgentAppType.SUBSTITUTE_DESIGNATION:
                    result = false;
                    break;
                case model.AgentAppType.PATH:
                    result = false;
                    break;
                case model.AgentAppType.NO_SETTINGS:
                    result = true;
                    break;
            }

            return result;
        }
    }

    export module model {
        export class AgentDto {
            companyId: string;
            employeeId: string;
            requestId: string;
            startDate: string;
            endDate: string;
            agentAppType1: number;
            agentAppType2: number;
            agentAppType3: number;
            agentAppType4: number;
            agentSid1: string;
            agentSid2: string;
            agentSid3: string;
            agentSid4: string;

            constructor(companyId: string, employeeId: string, requestId: string, startDate: string, endDate: string) {
                this.companyId = companyId;
                this.employeeId = employeeId;
                this.requestId = requestId;
                this.startDate = startDate;
                this.endDate = endDate;
            }
        }

        export enum AgentAppType {
            SUBSTITUTE_DESIGNATION = 0,
            PATH,
            NO_SETTINGS
        }
    }
    interface IPersonModel {
        personId: string;
        code: string;
        name: string;
        baseDate?: number;
    }

    class PersonModel {
        personId: string;
        code: string;
        name: string;
        baseDate: number;

        constructor(param: IPersonModel) {
            this.personId = param.personId;
            this.code = param.code;
            this.name = param.name;
            this.baseDate = param.baseDate || 20170104;
        }
    }

    export class AgentData {
        employeeCode: KnockoutObservable<string>;
        employeeName: KnockoutObservable<string>;
        workPlaceCode: KnockoutObservable<string>;
        workPlaceName: KnockoutObservable<string>;
        position: KnockoutObservable<string>;
        startDate: KnockoutObservable<string>;
        endDate: KnockoutObservable<string>;
        agentTarget: KnockoutObservable<string>;
        agentCode: KnockoutObservable<string>;
        agentName: KnockoutObservable<string>;
        hasColSpan: KnockoutObservable<boolean>;
        constructor(employeeCode: string, employeeName: string, workPlaceCode: string, workPlaceName: string, position: string, startDate: string, endDate: string, agentTarget: string, agentCode: string, agentName: string, hasColSpan: boolean) {
            this.employeeCode = ko.observable(employeeCode);
            this.employeeName = ko.observable(employeeName);
            this.workPlaceCode = ko.observable(workPlaceCode);
            this.workPlaceName = ko.observable(workPlaceName);
            this.position = ko.observable(position);
            this.startDate = ko.observable(startDate);
            this.endDate = ko.observable(endDate);
            this.agentTarget = ko.observable(agentTarget);
            this.agentCode = ko.observable(agentCode);
            this.agentName = ko.observable(agentName);
            this.hasColSpan = ko.observable(hasColSpan);
        }
    }



}