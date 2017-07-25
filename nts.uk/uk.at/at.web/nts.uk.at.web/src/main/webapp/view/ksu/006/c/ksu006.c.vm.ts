module nts.uk.pr.view.ksu006.c {
    export module viewmodel {
        
        import ExternalBudgetLogModel = service.model.ExternalBudgetLogModel;
        
        export class ScreenModel {
            
            dateRange: KnockoutObservable<any>;
            isIncomplete: KnockoutObservable<boolean>;
            isInterruption: KnockoutObservable<boolean>;
            isDone: KnockoutObservable<boolean>;
            
            dataLog: KnockoutObservableArray<ExternalBudgetLogModel>;
            listColumn: KnockoutObservableArray<any>;
            rowSelected: KnockoutObservable<string>;
            
            constructor() {
                let self = this;
                
                self.dateRange = ko.observable({startDate: new Date(), endDate: new Date()});
                self.isIncomplete = ko.observable(true);
                self.isInterruption = ko.observable(true);
                self.isDone = ko.observable(true);
                
                self.dataLog = ko.observableArray([]);
                self.listColumn = ko.observableArray([
                    { headerText: "", key: 'executeId', dataType: "string", hidden: true, formatter: _.escape},
                    { headerText: nts.uk.resource.getText("KSU006_311"), key: 'startDate', width: 150, dataType: 'date', format: 'yyyy/MM/dd HH:MM:SS'},
                    { headerText: nts.uk.resource.getText("KSU006_312"), key: 'endDate', width: 150, dataType: 'date', formatter: _.escape},
                    { headerText: nts.uk.resource.getText("KSU006_313"), key: 'target', width: 90, dataType: 'string', formatter: _.escape},
                    { headerText: nts.uk.resource.getText("KSU006_314"), key: 'fileName', width: 200, dataType: 'string', formatter: _.escape},
                    { headerText: nts.uk.resource.getText("KSU006_315"), key: 'status', width: 70, dataType: 'string', formatter: _.escape},
                    { headerText: nts.uk.resource.getText("KSU006_316"), key: 'numberSuccess', width: 70, dataType: 'number', formatter: _.escape},
                    { headerText: nts.uk.resource.getText("KSU006_317"), key: 'numberFail', width: 70, dataType: 'number', formatter: _.escape},
                    { headerText: nts.uk.resource.getText("KSU006_318"), key: 'download', width: 100, dataType: 'string', formatter: _.escape,
                        template: "{{if(${numberFail}) > 0}} <a href='#'>" + nts.uk.resource.getText("KSU006_319") + "</a>{{/if}}"}
                ]);
                self.rowSelected = ko.observable('');
            }

            public startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred<void>();
                self.fakeData();
                dfd.resolve();
                return dfd.promise();
            }
            
            public closeDialog() {
                nts.uk.ui.windows.close();
            }
            
            private fakeData() {
                let self = this;
                
                let item1: ExternalBudgetLogModel = {
                    executeId: 'addew',
                    startDate: new Date(),
                    endDate: new Date(),
                    target: "abc",
                    fileName: "bcd",
                    status: "完了",
                    numberSuccess: 5,
                    numberFail: 0,
                    download: ''
                }
                let item2: ExternalBudgetLogModel = {
                    executeId: 'addesdasw',
                    startDate: new Date(),
                    endDate: new Date(),
                    target: "abc",
                    fileName: "bcd",
                    status: "未完了",
                    numberSuccess: 5,
                    numberFail: 2,
                    download: ''
                }
                self.dataLog.push(item1);
                self.dataLog.push(item2);
            }
        }
    }
}
