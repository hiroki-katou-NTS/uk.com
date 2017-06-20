module cmm044.a.viewmodel {
    import alert = nts.uk.ui.dialog.alert;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import DirtyChecker = nts.uk.ui.DirtyChecker;
    import modal = nts.uk.ui.windows.sub.modal;
    import formatym = nts.uk.time.parseYearMonthDate;

    export class ScreenModel {
        empItems: KnockoutObservableArray<PersonModel>;
        empSelectedItem: KnockoutObservable<any>;
        tabs: KnockoutObservableArray<nts.uk.ui.NtsTabPanelModel>;
        selectedTab: KnockoutObservable<string>;
        startDate: KnockoutObservable<string>;
        endDate: KnockoutObservable<string>;
        itemList: KnockoutObservableArray<any>;
        selectedId1: KnockoutObservable<number>;
        selectedId2: KnockoutObservable<number>;
        selectedId3: KnockoutObservable<number>;
        selectedId4: KnockoutObservable<number>;
        index_of_itemDelete: any;

        //List Time
        //histItems: KnockoutObservableArray<ListModel> = ko.observableArray([new ListModel({ historyId: 'NEW', startDate: 20170104, endDate: 99991201 }),
        //new ListModel({ historyId: 'NEW', startDate: 20160104, endDate: 20170103 })
        //]);
        //histSelectedItem: KnockoutObservable<ListModel> = ko.observable(new ListModel({ historyId: 'NEW', startDate: 20170104, endDate: 99991201 }));
        histItems: KnockoutObservableArray<model.AgentDto>;
        currentAgent: KnockoutObservable<model.AgentDto>;
        histSelectedItem: KnockoutObservable<any>;
        currentItem: KnockoutObservable<model.AgentAppDto>;

        constructor() {
            let self = this;
            self.index_of_itemDelete = ko.observable(-1);
            self.currentItem = ko.observable(null);
            self.currentAgent = ko.observable(null);
            self.tabs = ko.observableArray([
                { id: 'tab-1', title: '就業承認', content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-2', title: '人事承認', content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-3', title: '給与承認', content: '.tab-content-3', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-4', title: '経理承認', content: '.tab-content-4', enable: ko.observable(true), visible: ko.observable(true) }
            ]);
            self.histItems = ko.observableArray([]);
            self.selectedTab = ko.observable('tab-1');
            self.empItems = ko.observableArray([]);
            self.empSelectedItem = ko.observable();
            self.empSelectedItem.subscribe(function(newValue) {
                $.when(self.getAllAgen(newValue.personId)).done(function() {
                    if (self.histItems().length > 0) {
                        self.histSelectedItem(self.histItems()[0].requestId);
                    } else {
                        self.currentItem(new model.AgentAppDto(newValue.personId, "", "", "", "", null, "", null, "", null, "", null));
                    }    
                });
            });
            self.histSelectedItem = ko.observable("");
            self.histSelectedItem.subscribe(function(requestId) {
                self.getAgen(self.empSelectedItem().personId, requestId);
            });
            self.itemList = ko.observableArray([
                new BoxModel(0, '代理'),
                new BoxModel(1, 'パス'),
                new BoxModel(2, '設定しない(待ってもらう)')
            ]);
            self.selectedId1 = ko.observable(0);
            self.selectedId1.subscribe(function(codeChanged) {
            
            });

            self.selectedId2 = ko.observable(0);
            self.selectedId2.subscribe(function(codeChanged) {

            });

            self.selectedId3 = ko.observable(0);
            self.selectedId3.subscribe(function(codeChanged) {

            });

            self.selectedId4 = ko.observable(0);
            self.selectedId4.subscribe(function(codeChanged) {

            });

            self.start();
        }
        start() {
            let self = this;
            var dfd = $.Deferred();
            self.empItems.removeAll();

            //Demo EmployeeCode & EmployeeId 
            _.range(10).map(i => {
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

            // fix 
            self.empSelectedItem(self.empItems()[0]);

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
                        self.histItems.push(new model.AgentDto(agent_arr[i].companyId, agent_arr[i].employeeId, agent_arr[i].requestId, agent_arr[i].startDate, agent_arr[i].endDate, agent_arr[i].agentSid1, agent_arr[i].agentAppType1, agent_arr[i].agentSid2, agent_arr[i].agentAppType2, agent_arr[i].agentSid3, agent_arr[i].agentAppType3, agent_arr[i].agentSid4, agent_arr[i].agentAppType4));
                    }
                }
                dfd.resolve();
            }).fail(function(error) {
                alert(error.message);
                dfd.reject(error);
            });

            return dfd.promise();
        }

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

                dfd.resolve();
            }).fail(function(error) {
                alert(error.message);
                dfd.reject(error);
            });

            return dfd.promise();
        }

        /**
         * 
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
            var dfd = $.Deferred<any>();

            if (self.histItems().length === 0) {
                service.addAgent(self.currentItem()).done(function() {
                }).fail(function(res) {
                    alert(res.message);
                    dfd.reject(res);
                })
            }

            var agent = ko.toJSON(self.currentItem());
            agent["employeeId"] = self.empSelectedItem().personId;

            var existsItem = self.findInHistItem(self.currentItem().employeeId(), self.histSelectedItem());

            if (existsItem) {
                service.updateAgent(agent).done(function() {
                    self.getAllAgen(self.empSelectedItem().personId);
                }).fail(function(res) {
                    alert(res.message);
                    dfd.reject(res);
                })
            } else {
                service.addAgent(agent).done(function(requestId) {
                    self.getAllAgen(self.empSelectedItem().personId);
                    self.histSelectedItem(requestId);
                }).fail(function(res) {
                    alert(res.message);
                    dfd.reject(res);
                })
            }
        }

        deleteAgent() {
            let self = this;

            let dfd = $.Deferred<any>();
            self.index_of_itemDelete = self.histItems().indexOf(self.histSelectedItem());
            if (self.histItems().length == 1) {
                nts.uk.ui.dialog.alert("選択している履歴の職位が1件のみのため、\r\n履歴の編集ボタンから履歴削除を行ってください。")
            } else {
                nts.uk.ui.dialog.confirm("データを削除します。\r\nよろしいですか？").ifYes(function() {
                    var agent = {
                        employeeId: self.empSelectedItem().personId,
                        requestId: self.currentItem().requestId()
                    }
                    service.deleteAgent(agent).done(function(res) {
                        self.getAllAgen(self.empSelectedItem().personId);
                    }).fail(function(res) {
                        dfd.reject(res);
                    })
                }).ifNo(function() {
                });
            }

        }

        initAgent() {
            let self = this;
            self.selectedId1 = ko.observable(0);
            self.selectedId2 = ko.observable(0);
            self.selectedId3 = ko.observable(0);
            self.selectedId4 = ko.observable(0);
            self.histSelectedItem("");
            self.currentItem(new model.AgentAppDto(self.empSelectedItem().personId, "", "", "", "", null, "", null, "", null, "", null));
        }
        openDDialog() {
            let self = this;
            nts.uk.ui.windows.sub.modal('/view/cmm/044/b/index.xhtml', { title: '代行リスト', height: 550, width: 1050, dialogClass: 'no-close' }).onClosed(function(): any {
            });
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