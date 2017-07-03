module cmm044.a.viewmodel {
    import alert = nts.uk.ui.dialog.alert;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import DirtyChecker = nts.uk.ui.DirtyChecker;
    import modal = nts.uk.ui.windows.sub.modal;
    import formatym = nts.uk.time.parseYearMonthDate;
    import EmployeeSearchDto = nts.uk.com.view.ccg.share.ccg.service.model.EmployeeSearchDto;
    import GroupOption = nts.uk.com.view.ccg.share.ccg.service.model.GroupOption;

    export class ScreenModel {

        empItems: KnockoutObservableArray<PersonModel>;
        empSelectedItem: KnockoutObservable<any>;

        tabs: KnockoutObservableArray<nts.uk.ui.NtsTabPanelModel>;
        itemList: KnockoutObservableArray<any>;
        index_of_itemDelete: any;
        isEnableDelete: KnockoutObservable<boolean>;

        displayEmployeeInfo1: KnockoutObservable<boolean>;
        displayEmployeeInfo2: KnockoutObservable<boolean>;
        displayEmployeeInfo3: KnockoutObservable<boolean>;
        displayEmployeeInfo4: KnockoutObservable<boolean>;
        
        histItems: KnockoutObservableArray<model.AgentDto>;
        histSelectedItem: KnockoutObservable<any>;
        currentItem: KnockoutObservable<model.AgentAppDto>;

        agentAppType1: KnockoutObservable<number>;
        agentAppType2: KnockoutObservable<number>;
        agentAppType3: KnockoutObservable<number>;
        agentAppType4: KnockoutObservable<number>;
        
        agentSid1: KnockoutObservable<string>;
        agentSid2: KnockoutObservable<string>;
        agentSid3: KnockoutObservable<string>;
        agentSid4: KnockoutObservable<string>;
        

        employeeList : KnockoutObservable<any>;
        employeeNameScreen1: KnockoutObservable<any>;
        employeeNameScreen2: KnockoutObservable<any>;
        employeeNameScreen3: KnockoutObservable<any>;
        employeeNameScreen4: KnockoutObservable<any>;

        //Date Range Picker
        dateValue: KnockoutObservable<any>;

        selectedTab: KnockoutObservable<string>;

        ccgcomponent: GroupOption;
        selectedCode: KnockoutObservableArray<string>;
        showinfoSelectedEmployee: KnockoutObservable<boolean>;
        selectedEmployee: KnockoutObservableArray<any>;

        constructor() {
            let self = this;
            self.index_of_itemDelete = ko.observable(-1);
            self.currentItem = ko.observable(null);
            self.tabs = ko.observableArray([
                { id: 'tab-1', title: nts.uk.resource.getText("CMM044_11"), content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-2', title: nts.uk.resource.getText("CMM044_12"), content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-3', title: nts.uk.resource.getText("CMM044_37"), content: '.tab-content-3', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-4', title: nts.uk.resource.getText("CMM044_13"), content: '.tab-content-4', enable: ko.observable(true), visible: ko.observable(true) }
            ]);

            self.dateValue = ko.observable({});

            self.displayEmployeeInfo1 = ko.observable(true);
            self.displayEmployeeInfo2 = ko.observable(true);
            self.displayEmployeeInfo3 = ko.observable(true);
            self.displayEmployeeInfo4 = ko.observable(true);

            self.isEnableDelete = ko.observable(true);

            self.agentAppType1 = ko.observable(0);
            self.agentAppType2 = ko.observable(0);
            self.agentAppType3 = ko.observable(0);
            self.agentAppType4 = ko.observable(0);
            
            self.agentSid1 = ko.observable('');
            self.agentSid2 = ko.observable('');
            self.agentSid3 = ko.observable('');
            self.agentSid4 = ko.observable('');
            
            self.agentAppType1.subscribe(function(newValue) {
                self.displayEmployeeInfo1(newValue == 0);
                self.currentItem().agentAppType1(newValue);
 
            });
            self.agentAppType2.subscribe(function(newValue) {
                self.displayEmployeeInfo2(newValue == 0);
                self.currentItem().agentAppType2(newValue);
            });
            self.agentAppType3.subscribe(function(newValue) {
                self.displayEmployeeInfo3(newValue == 0);
                self.currentItem().agentAppType3(newValue);
            });
            self.agentAppType4.subscribe(function(newValue) {
                self.displayEmployeeInfo4(newValue == 0);
                self.currentItem().agentAppType4(newValue);
            });

            self.histItems = ko.observableArray([]);
            self.selectedTab = ko.observable('tab-1');
            self.selectedTab.subscribe(function(newValue){
                
            })
            self.empItems = ko.observableArray([]);
            self.empSelectedItem = ko.observable();
            self.empSelectedItem.subscribe(function(newValue) {
                if (newValue) {
                    $.when(self.getAllAgen(newValue.personId)).done(function() {
                        if (self.histItems().length > 0) {
                            self.histSelectedItem(self.histItems()[0].requestId);
                        } else {
                            self.initAgent();
                        }
                    });
                }
            });

            self.histSelectedItem = ko.observable("");
            self.histSelectedItem.subscribe(function(requestId) {
                if (requestId) {
                    self.getAgen(self.empSelectedItem().personId, requestId);
                    $("#daterangepicker").find(".ntsStartDatePicker").blur();
                    nts.uk.ui.errors.clearAll();
                    self.isEnableDelete(true);
                }
            });

            self.itemList = ko.observableArray([
                new BoxModel(0, nts.uk.resource.getText("CMM044_16")),
                new BoxModel(1, nts.uk.resource.getText("CMM044_17")),
                new BoxModel(2, nts.uk.resource.getText("CMM044_18"))
            ]);
            self.dateValue = ko.observable({ startDate: '', endDate: '' });
            self.employeeList  = ko.observable('');
            self.employeeNameScreen1 = ko.observable('');
            self.employeeNameScreen2 = ko.observable('');
            self.employeeNameScreen3 = ko.observable('');
            self.employeeNameScreen4 = ko.observable('');
            
            self.ccgcomponent = ko.observable();
            self.selectedCode = ko.observableArray('');
            self.showinfoSelectedEmployee = ko.observable(true);
            self.selectedEmployee = ko.observableArray([]);
        }
        start() {
            let self = this;
            var dfd = $.Deferred();
            self.empItems.removeAll();

            /**
             * Demo EmployeeCode & EmployeeId
             */
            _.range(30).map(i => {
                i++;
                if (i < 10) {
                    self.empItems.push(new PersonModel({
                        personId: '99900000-0000-0000-0000-00000000000' + i,
                        code: 'A00000000' + i,
                        name: '日通　社員' + i,
                    }));
                } else {
                    self.empItems.push(new PersonModel({
                        personId: '99900000-0000-0000-0000-00000000000' + i,
                        code: 'A0000000' + i,
                        name: '日通　社員' + i,
                    }));
                }
            });
            self.initCCG001();
            dfd.resolve();
            return dfd.promise();
        }

        /**
         * find agen by employee
         */
        getAllAgen(employeeId): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            self.histItems.removeAll();
            service.findAllAgent(employeeId).done(function(agent_arr: Array<model.AgentDto>) {
                if (agent_arr && agent_arr.length) {
                    self.histItems.removeAll();
                    for (var i = 0; i < agent_arr.length; i++) {
                        self.histItems.push(new model.AgentDto(
                            agent_arr[i].companyId,
                            agent_arr[i].employeeId,
                            agent_arr[i].requestId,
                            agent_arr[i].startDate,
                            agent_arr[i].endDate,
                            agent_arr[i].agentSid1,
                            agent_arr[i].agentAppType1,
                            agent_arr[i].agentSid2,
                            agent_arr[i].agentAppType2,
                            agent_arr[i].agentSid3,
                            agent_arr[i].agentAppType3,
                            agent_arr[i].agentSid4,
                            agent_arr[i].agentAppType4));
                    }
                }
                dfd.resolve();
            }).fail(function(error) {
                alert(error.message);
                dfd.reject(error);
            });

            return dfd.promise();
        }

        /**
         * find agent by requestId
         */
        getAgen(employeeId: string, requestId: string): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            if (!requestId) {
                return;
            }

            var param = {
                employeeId: employeeId,
                requestId: requestId
            };
            service.findAgent(param).done(function(agent: model.AgentDto) {
                self.currentItem(new model.AgentAppDto(employeeId, requestId, agent.startDate, agent.endDate,
                    agent.agentSid1, agent.agentAppType1,
                    agent.agentSid2, agent.agentAppType2,
                    agent.agentSid3, agent.agentAppType3,
                    agent.agentSid4, agent.agentAppType4));

                self.agentAppType1(agent.agentAppType1);
                self.agentAppType2(agent.agentAppType2);
                self.agentAppType3(agent.agentAppType3);
                self.agentAppType4(agent.agentAppType4);

                self.dateValue({ startDate: agent.startDate, endDate: agent.endDate });
                $("#daterangepicker").find(".ntsStartDatePicker").focus();
                dfd.resolve();
            }).fail(function(error) {
                alert(error.message);
                dfd.reject(error);
            });

            return dfd.promise();
        }

        /**
         * Find agent
         */
        findInHistItem(employeeId: string, requestId: string): model.AgentDto {
            var self = this;
            return _.find(self.histItems(), function(item: any) {
                if (item.employeeId == employeeId && item.requestId == requestId) {
                    return item;
                }
            });
        }

        /**
         * Add new agent and Update agent
         */
        addAgent() {
            var self = this;

            self.currentItem().agentAppType1(self.agentAppType1());
            self.currentItem().agentAppType2(self.agentAppType2());
            self.currentItem().agentAppType3(self.agentAppType3());
            self.currentItem().agentAppType4(self.agentAppType4());
            self.currentItem().startDate(new Date(self.dateValue().startDate));
            self.currentItem().endDate(new Date(self.dateValue().endDate));
            self.currentItem().agentSid1(self.agentSid1());
            self.currentItem().agentSid2(self.agentSid2());
            self.currentItem().agentSid3(self.agentSid3());
            self.currentItem().agentSid4(self.agentSid4());

            var agent = ko.toJSON(self.currentItem());
            agent["employeeId"] = self.empSelectedItem().personId;
            var existsItem = self.findInHistItem(self.currentItem().employeeId(), self.histSelectedItem());

            if (existsItem) {
                service.updateAgent(agent).done(function() {
                    self.getAllAgen(self.empSelectedItem().personId);
                    nts.uk.ui.dialog.alert(nts.uk.resource.getMessage("Msg_15"));
                    $("#daterangepicker").find(".ntsStartDatePicker").focus();
                }).fail(function(res) {
                    nts.uk.ui.dialog.alertError(res.message);
                })
            } else {
                service.addAgent(agent).done(function(res) {
                    var resObj = ko.toJS(res);
                    if (self.histSelectedItem) {
                        self.getAllAgen(self.empSelectedItem().personId);
                        self.histSelectedItem(res);
                        nts.uk.ui.dialog.alert(nts.uk.resource.getMessage("Msg_15"));
                        $("#daterangepicker").find(".ntsStartDatePicker").focus();
                    }
                }).fail(function(res) {
                    nts.uk.ui.dialog.alertError(res.message);
                })
            }
        }

        deleteAgent() {
            let self = this;
            var index_of_itemDelete = _.findIndex(self.histItems(), ['requestId', self.histSelectedItem()]);
            nts.uk.ui.dialog.confirm("データを削除します。\r\nよろしいですか？").ifYes(function() {
                var agent = {
                    employeeId: self.empSelectedItem().personId,
                    requestId: self.currentItem().requestId()
                };
                service.deleteAgent(agent).done(function() {
                    $.when(self.getAllAgen(self.empSelectedItem().personId)).done(function() {
                        var requestId = "";
                        if (self.histItems().length == 0) {
                            self.initAgent();
                        } else if (self.histItems().length == 1) {
                            requestId = self.histItems()[0].requestId;
                        } else if (index_of_itemDelete == self.histItems().length) {
                            requestId = self.histItems()[index_of_itemDelete - 1].requestId;
                        } else {
                            requestId = self.histItems()[index_of_itemDelete].requestId;
                        }
                        nts.uk.ui.dialog.alert(nts.uk.resource.getMessage("Msg_16"));
                        self.histSelectedItem(requestId);
                        $("#daterangepicker").find(".ntsStartDatePicker").focus();
                    });
                }).fail(function(res) {
                    nts.uk.ui.dialog.alertError(res.message);
                })
            }).ifNo(function() {
            });
        }

        initAgent() {
            let self = this;
            self.dateValue = ko.observable({});
            self.displayEmployeeInfo1(true);
            self.displayEmployeeInfo2(true);
            self.displayEmployeeInfo3(true);
            self.displayEmployeeInfo4(true);
            self.isEnableDelete(false);
            nts.uk.ui.errors.clearAll();
            self.agentAppType1(0);
            self.agentAppType2(0);
            self.agentAppType3(0);
            self.agentAppType4(0);
            self.selectedTab('tab-1');
            self.histSelectedItem("");
            self.currentItem(new model.AgentAppDto(self.empSelectedItem().personId, "", "", "", "", self.agentAppType1(), "", self.agentAppType2(), "", self.agentAppType3(), "", self.agentAppType4()));
            $("#daterangepicker").find(".ntsStartDatePicker").focus();
        }
        openDDialog() {
            let self = this;
            nts.uk.ui.windows.setShared('cmm044_DataPerson', self.empItems());
            nts.uk.ui.windows.sub.modal('/view/cmm/044/d/index.xhtml', { title: '代行リスト', height: 550, width: 1050, dialogClass: 'no-close' }).onClosed(function(): any {
            });

        }
        openCDL021(tab: number, employeeId: string) {
            let self = this;
            nts.uk.ui.windows.setShared('cmm044_Name', employeeId);
            nts.uk.ui.windows.setShared('cmm044_DataPerson', self.empItems());
            nts.uk.ui.windows.setShared('cmm044_Tab', tab);
            nts.uk.ui.windows.sub.modal('/view/cmm/044/e/index.xhtml', { title: '代行リスト', height: 650, width: 550, dialogClass: 'no-close' }).onClosed(function(): any {
                if (!!nts.uk.ui.windows.getShared('cmm044_Employee')) {
                    var employee = nts.uk.ui.windows.getShared('cmm044_Employee');
                    var tabNumber = nts.uk.ui.windows.getShared('cmm044_TabNumber');
                    if (tabNumber == 1) {
                        self.agentSid1(employee.employeId);
                        self.employeeNameScreen1(employee.employeeName);
                    }else if (tabNumber == 2) {      
                        self.agentSid2(employee.employeId);
                        self.employeeNameScreen2(employee.employeeName);
                    }else if (tabNumber == 3) {      
                        self.agentSid3(employee.employeId);
                        self.employeeNameScreen3(employee.employeeName);
                    }else {
                        self.agentSid4(employee.employeId);
                        self.employeeNameScreen4(employee.employeeName);
                    }
                }
            });
        }
        
        openCCG001() {
            let self = this;
            $("#ccgcomponent, #ccgcomponent_contents").toggleClass("show");    
        }
        
        initCCG001() {
            let self = this;
            self.ccgcomponent = {
               baseDate: ko.observable(new Date()),
               // Show/hide options 
               isQuickSearchTab: ko.observable(true),
               isAdvancedSearchTab: ko.observable(true),
               isAllReferableEmployee: ko.observable(true),
               isOnlyMe: ko.observable(true),
               isEmployeeOfWorkplace: ko.observable(true),
               isEmployeeWorkplaceFollow: ko.observable(true),
               isMutipleCheck: ko.observable(true),
               isSelectAllEmployee: ko.observable(true),
               
               //Event options
               /**
               * @param dataList: list employee returned from component.
               * Define how to use this list employee by yourself in the function's body.
               */
               onSearchAllClicked: function(dataList: EmployeeSearchDto[]) {
                   self.showinfoSelectedEmployee(true);
                   self.selectedEmployee(dataList);
               },
               onSearchOnlyClicked: function(data: EmployeeSearchDto) {
                   self.showinfoSelectedEmployee(true);
                   var dataEmployee: EmployeeSearchDto[] = [];
                   dataEmployee.push(data);
                   self.selectedEmployee(dataEmployee);
               },
               onSearchOfWorkplaceClicked: function(dataList: EmployeeSearchDto[]) {
                   self.showinfoSelectedEmployee(true);
                   self.selectedEmployee(dataList);
               },
               onSearchWorkplaceChildClicked: function(dataList: EmployeeSearchDto[]) {
                   self.showinfoSelectedEmployee(true);
                   self.selectedEmployee(dataList);
               },
               onApplyEmployee: function(dataEmployee: EmployeeSearchDto[]) {
                   self.showinfoSelectedEmployee(true);
                   self.selectedEmployee(dataEmployee);
               }
           }

           $('#ccgcomponent').ntsGroupComponent(self.ccgcomponent);
        
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

    class BoxModel {
        id: number;
        name: string;
        constructor(id, name) {
            var self = this;
            self.id = id;
            self.name = name;
        }
    }

    export module model {
        export class AgentDto {
            companyId: string;
            employeeId: string;
            requestId: string;
            startDate: string;
            endDate: string;
            agentSid1: string;
            agentAppType1: number;
            agentSid2: string;
            agentAppType2: number;
            agentSid3: string;
            agentAppType3: number;
            agentSid4: string;
            agentAppType4: number;
            text: string;
            constructor(companyId: string, employeeId: string, requestId: string, startDate: string, endDate: string, agentSid1: string, agentAppType1: number, agentSid2: string, agentAppType2: number, agentSid3: string, agentAppType3: number, agentSid4: string, agentAppType4: number) {
                this.companyId = companyId;
                this.employeeId = employeeId;
                this.requestId = requestId;
                this.startDate = startDate;
                this.endDate = endDate;
                this.agentSid1 = agentSid1;
                this.agentAppType1 = agentAppType1;
                this.agentSid2 = agentSid2;
                this.agentAppType2 = agentAppType2;
                this.agentSid3 = agentSid3;
                this.agentAppType3 = agentAppType3;
                this.agentSid4 = agentSid4;
                this.agentAppType4 = agentAppType4;
                this.text = startDate + ' ~ ' + endDate;
            }
        }

        export class AgentAppDto {
            employeeId: KnockoutObservable<string>;
            requestId: KnockoutObservable<string>;
            startDate: KnockoutObservable<string>;
            endDate: KnockoutObservable<string>;
            agentSid1: KnockoutObservable<string>;
            agentAppType1: KnockoutObservable<number>;
            agentSid2: KnockoutObservable<string>;
            agentAppType2: KnockoutObservable<number>;
            agentSid3: KnockoutObservable<string>;
            agentAppType3: KnockoutObservable<number>;
            agentSid4: KnockoutObservable<string>;
            agentAppType4: KnockoutObservable<number>;
            constructor(employeeId: string, requestId: string, startDate: string, endDate: string, agentSid1: string, agentAppType1: number, agentSid2: string, agentAppType2: number, agentSid3: string, agentAppType3: number, agentSid4: string, agentAppType4: number) {
                this.employeeId = ko.observable(employeeId);
                this.requestId = ko.observable(requestId);
                this.startDate = ko.observable(startDate);
                this.endDate = ko.observable(endDate);
                this.agentSid1 = ko.observable(agentSid1);
                this.agentAppType1 = ko.observable(agentAppType1);
                this.agentSid2 = ko.observable(agentSid2);
                this.agentAppType2 = ko.observable(agentAppType2);
                this.agentSid3 = ko.observable(agentSid3);
                this.agentAppType3 = ko.observable(agentAppType3);
                this.agentSid4 = ko.observable(agentSid4);
                this.agentAppType4 = ko.observable(agentAppType4);
            }
        }

        export class DeleteAgent {
            companyId: string;
            employeeId: string;
            requestId: string;
            constructor(companyId: string, employeeId: string, requestId: string) {
                this.companyId = employeeId;
                this.employeeId = employeeId;
                this.requestId = requestId;
            }
        }
    }
}