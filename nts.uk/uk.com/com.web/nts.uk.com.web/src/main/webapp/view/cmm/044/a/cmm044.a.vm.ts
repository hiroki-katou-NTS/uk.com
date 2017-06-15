module cmm044.a.viewmodel {
    import alert = nts.uk.ui.dialog.alert;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import DirtyChecker = nts.uk.ui.DirtyChecker;
    import modal = nts.uk.ui.windows.sub.modal;
    import formatym = nts.uk.time.parseYearMonthDate;

    export class ScreenModel {
        empItems: KnockoutObservableArray<PersonModel> = ko.observableArray([new PersonModel({ personId: '99900000-0000-0000-0000-000000000001', code: 'A000000001', name: '日通　社員1', baseDate: 20170104 })]);
        empSelectedItem: KnockoutObservable<PersonModel> = ko.observable(new PersonModel({ personId: '99900000-0000-0000-0000-000000000002', code: 'A000000001', name: '日通　社員1', baseDate: 20170104 }));
        tabs: KnockoutObservableArray<nts.uk.ui.NtsTabPanelModel>;
        selectedTab: KnockoutObservable<string>;
        simpleValue: KnockoutObservable<string>;
        simpleValue1: KnockoutObservable<string>;
        itemList: KnockoutObservableArray<any>;
        selectedId: KnockoutObservable<number>;
        date: KnockoutObservable<string>;
        date1: KnockoutObservable<string>;
        index_of_itemDelete: any;

        //List Time
        //histItems: KnockoutObservableArray<ListModel> = ko.observableArray([new ListModel({ historyId: 'NEW', startDate: 20170104, endDate: 99991201 }),
        //new ListModel({ historyId: 'NEW', startDate: 20160104, endDate: 20170103 })
        //]);
        //histSelectedItem: KnockoutObservable<ListModel> = ko.observable(new ListModel({ historyId: 'NEW', startDate: 20170104, endDate: 99991201 }));
        histItems: KnockoutObservableArray<model.AgentDto>;
        histSelectedItem: KnockoutObservable<string>;
        currentItem: KnockoutObservable<model.AgentDto>;


        constructor() {
            let self = this;
            self.index_of_itemDelete = ko.observable(-1);
            self.currentItem = ko.observable(null);
            self.simpleValue = ko.observable("2017/01/04");
            self.simpleValue1 = ko.observable("9999/12/01");
            self.date = ko.observable('20000101');
            self.date1 = ko.observable('99990112');
            self.tabs = ko.observableArray([
                { id: 'tab-1', title: '就業承認', content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-2', title: '人事承認', content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-3', title: '給与承認', content: '.tab-content-3', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-4', title: '経理承認', content: '.tab-content-4', enable: ko.observable(true), visible: ko.observable(true) }
            ]);
            self.histItems = ko.observableArray([]);
            self.histSelectedItem = ko.observable("");
            self.selectedTab = ko.observable('tab-1');
            self.empSelectedItem.subscribe(function(newValue) {

            });
            self.itemList = ko.observableArray([
                new BoxModel(0, '代理'),
                new BoxModel(1, 'パス'),
                new BoxModel(2, '設定しない(待ってもらう)')
            ]);
            self.selectedId = ko.observable(0);
            self.selectedId.subscribe(function(codeChanged) {
                self.disableRadioBox(codeChanged);
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
                        baseDate: 20170105
                    }));
                } else {
                    self.empItems.push(new PersonModel({
                        personId: '99900000-0000-0000-0000-00000000000' + i,
                        code: 'A0000000' + i,
                        name: '日通　社員' + i,
                        baseDate: 20170105
                    }));

                }
            });

            service.findAllAgent(self.empSelectedItem().personId).done(function(agent_arr: Array<model.AgentDto>) {
                self.histItems(agent_arr);
                if (self.histItems().length > 0) {
                    self.histSelectedItem(self.histItems()[0].startDate)
                }
                //                     else {
                //                    self.initRegisterPayClassification();
                //                }
                dfd.resolve();
            }).fail(function(error) {
                alert(error.message);
                dfd.reject(error);
            })

            return dfd.promise();
        }

        disableRadioBox(value: number) {
            var self = this;
            if (value == 1 || value == 2) {
                $('#BTN_A4_003').hide();
                $('#A4-004').hide();
            } else {
                $('#BTN_A4_003').show();
                $('#A4-004').show();
            }
        }

//        addAgent() {
//            var self = this;
//            var dfd = $.Deferred<any>();
//
//            if (self.histItems().length === 0) {
//                let agent = new viewmodel.model.AgenApptDto(self.currentItem().startDate, self.currentItem().endDate, self.currentItem().agentSid, self.currentItem().agentAppType);
//                service.addAgent(agent).done(function() {
//
//                    self.getAgentListFirst();
//                }).fail(function(res) {
//                    alert(res.message);
//                    dfd.reject(res);
//                })
//            }
//        }
        getAgentListFirst() { }
        deleteAgent() {
            let self = this;

            let dfd = $.Deferred<any>();
            let item = new model.DeleteAgent(self.currentItem().companyId, self.currentItem().employeeId, self.currentItem().startDate);
            self.index_of_itemDelete = self.histItems().indexOf(self.currentItem());
            if (self.histItems().length == 1) {
                nts.uk.ui.dialog.alert("選択している履歴の職位が1件のみのため、\r\n履歴の編集ボタンから履歴削除を行ってください。")
            } else {
                nts.uk.ui.dialog.confirm("データを削除します。\r\nよろしいですか？").ifYes(function() {
                    service.deleteAgent(item).done(function(res) {
                        self.getAgentList_afterDelete();
                    }).fail(function(res) {
                        dfd.reject(res);
                    })
                }).ifNo(function() {
                });
            }

        }
        getAgentList_afterDelete() {
            var self = this;
            var dfd = $.Deferred<any>();
            service.findAllAgent(self.histSelectedItem()).done(function(agent_arr: Array<model.AgentDto>) {
                self.histItems = ko.observableArray([]);
                self.histItems(agent_arr);
                if (self.histItems().length > 0) {
                    if (self.index_of_itemDelete === self.histItems().length) {
                        self.histSelectedItem(self.histItems()[self.index_of_itemDelete - 1].employeeId)
                        self.date(self.histItems()[self.index_of_itemDelete - 1].startDate);
                        self.date1(self.histItems()[self.index_of_itemDelete - 1].endDate);
                    } else {
                        self.histSelectedItem(self.histItems()[self.index_of_itemDelete].employeeId)
                        self.date(self.histItems()[self.index_of_itemDelete].startDate);
                        self.date1(self.histItems()[self.index_of_itemDelete].endDate);
                    }
                } else {
                    self.initAgent();
                }
                dfd.resolve();
            }).fail(function(error) {
                alert(error.message);
            })
            dfd.resolve();
            return dfd.promise();
        }


        initAgent() { }
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

    interface IListModel {
        companyId: string;
        employeeId: string;
        startDate: string;
        endDate: string;
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
    export class ListModel {
        companyId: string;
        employeeId: string;
        startDate: string;
        endDate: string;
        agentSid: string;

        text: string;
        isMaxDate: boolean = false;

        constructor(param: IListModel) {
            this.companyId = param.employeeId;
            this.employeeId = param.employeeId;
            this.startDate = param.startDate;
            this.endDate = param.endDate;


            this.update();
        }

        update() {
            let sDate = formatym(this.startDate);
            let endDate = formatym(this.endDate);
            this.text = moment.utc([sDate.year, sDate.month - 1, sDate.date]).format("YYYY/MM/DD") + " ~ " + moment.utc([endDate.year, endDate.month - 1, endDate.date]).format("YYYY/MM/DD");
        }
    }


    export module model {
        export class AgentDto {
            companyId: string;
            employeeId: string;
            startDate: string;
            endDate: string;
            agentSid: string;
            agentAppType: number;
            constructor(companyId: string, employeeId: string, startDate: string, endDate: string, agentSid: string, agentAppType: number) {
                this.companyId = employeeId;
                this.employeeId = employeeId;
                this.startDate = startDate;
                this.endDate = endDate;
                this.agentSid = agentSid;
                this.agentAppType = agentAppType;
            }
        }
        
           export class AgenApptDto {

            startDate: string;
            endDate: string;
            agentSid: string;
            agentAppType: number;
            constructor(startDate: string, endDate: string, agentSid: string, agentAppType: number) {

                this.startDate = startDate;
                this.endDate = endDate;
                this.agentSid = agentSid;
                this.agentAppType = agentAppType;
            }
        }


        export class DeleteAgent {
            companyId: string;
            employeeId: string;
            startDate: string;
            constructor(companyId: string, employeeId: string, startDate: string) {
                this.companyId = employeeId;
                this.employeeId = employeeId;
                this.startDate = startDate;
            }
        }


        export class InputField {
            date: KnockoutObservable<string>;
            date1: KnockoutObservable<string>;

            constructor(agent: AgenApptDto, enable) {
                this.date = ko.observable(agent.startDate);
                this.date1 = ko.observable(agent.endDate);

            }
        }
    }


}