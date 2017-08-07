module nts.uk.pr.view.ksu006.c {
    export module viewmodel {
        
        import EnumerationModel = service.model.EnumerationModel;
        import ExternalBudgetLogModel = service.model.ExternalBudgetLogModel;
        
        export class ScreenModel {
            
            // find enumeration
            completionList: KnockoutObservableArray<EnumerationModel>;
            
            dateRange: KnockoutObservable<any>;
            isIncomplete: KnockoutObservable<boolean>;
            isInterruption: KnockoutObservable<boolean>;
            isDone: KnockoutObservable<boolean>;
            
            dataLog: KnockoutObservableArray<ExternalBudgetLogModel>;
            listColumn: KnockoutObservableArray<any>;
            rowSelected: KnockoutObservable<string>;
            
            isBindingDone: boolean;
            
            constructor() {
                let self = this;
                
                self.completionList = ko.observableArray([]);
                
                self.dateRange = ko.observable({startDate: new Date(), endDate: new Date()});
                self.isIncomplete = ko.observable(true);
                self.isInterruption = ko.observable(true);
                self.isDone = ko.observable(true);
                
                self.dataLog = ko.observableArray([]);
                self.listColumn = ko.observableArray([
                    { headerText: "", key: 'executeId', dataType: "string", hidden: true, formatter: _.escape},
                    { headerText: nts.uk.resource.getText("KSU006_311"), key: 'startDate', width: 175, dataType: 'date', formatter: _.escape},
                    { headerText: nts.uk.resource.getText("KSU006_312"), key: 'endDate', width: 175, dataType: 'date', formatter: _.escape},
                    { headerText: nts.uk.resource.getText("KSU006_313"), key: 'extBudgetName', width: 150, dataType: 'string', formatter: _.escape},
                    { headerText: nts.uk.resource.getText("KSU006_314"), key: 'fileName', width: 200, dataType: 'string', formatter: _.escape},
                    { headerText: nts.uk.resource.getText("KSU006_315"), key: 'statusDes', width: 70, dataType: 'string', formatter: _.escape},
                    { headerText: nts.uk.resource.getText("KSU006_316"), key: 'numberSuccess', width: 70, dataType: 'number', formatter: _.escape},
                    { headerText: nts.uk.resource.getText("KSU006_317"), key: 'numberFail', width: 70, dataType: 'number', formatter: _.escape},
                    { headerText: nts.uk.resource.getText("KSU006_318"), key: 'download', width: 100, dataType: 'string', formatter: _.escape,
                        template: "{{if(${numberFail}) > 0}} <span style='text-decoration: underline;color: blue;'"
                            + "data-bind='click: downloadDetailError(${executeId})' tabindex='7'>" + nts.uk.resource.getText("KSU006_319") + "</span>{{/if}}"}
                ]);
                self.rowSelected = ko.observable('');
                
                self.isBindingDone = false;
            }

            public startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred<void>();
                $.when(self.loadCompletionList()).done(() => {
                    self.loadDataLog().done(() => {
                        dfd.resolve();
                    })
                });
                return dfd.promise();
            }
            
            public downloadDetailError(executeId: string) {
                let self = this;
                if (!self.isBindingDone) {
                    return;
                }
                // TODO: pending screen B.
                console.log(executeId);
                console.log(self.dateRange().startDate);
            }
            
            public search() {
                let self = this;
                let listState: Array<number> = [];
                
                // Completion: INCOMPLETE(0)
                if (self.isIncomplete()) {
                    listState.push(0);
                }
                // Completion: DONE(1)
                if (self.isDone()) {
                    listState.push(1);
                }
                // Completion: INTERRUPTION(2)
                if (self.isInterruption()) {
                    listState.push(2);
                }
                if (listState.length <= 0) {
                    nts.uk.ui.dialog.alertError(nts.uk.resource.getMessage("Msg_166"));
                    return;
                }
                self.loadDataLog(true, listState);
            }
            
            public closeDialog() {
                nts.uk.ui.windows.close();
            }
            
            private loadDataLog(isSearchMode?: boolean, listState?: Array<number>): JQueryPromise<Array<ExternalBudgetLogModel>> {
                let self = this;
                let dfd = $.Deferred();
                
                let query: any = {};
                if (!isSearchMode) {
                    query.startDate = self.dateRange().startDate;
                    query.endDate = self.dateRange().endDate;
                    query.listState = [0, 1, 2];
                } else {
                    let objStartDate: any = self.getComponentDate(self.dateRange().startDate);
                    let objEndDate: any = self.getComponentDate(self.dateRange().startDate);
                    
                    query.startDate = new Date(Date.UTC(objStartDate.year, objStartDate.month, objStartDate.day));
                    query.endDate = new Date(Date.UTC(objEndDate.year, objEndDate.month, objEndDate.day));
                    query.listState = listState;
                }
                service.findAllExternalBudgetLog(query).done(function(res: Array<ExternalBudgetLogModel>) {
                    self.dataLog(res);
                    dfd.resolve();
                }).fail(function(res) {
                   nts.uk.ui.dialog.alertError(res.message);
                });
                return dfd.promise();
            }
            
            private loadCompletionList(): JQueryPromise<Array<EnumerationModel>> {
                let self = this;
                let dfd = $.Deferred();
                service.findCompletionList().done(function(res: Array<EnumerationModel>) {
                    self.completionList(res);
                    dfd.resolve();
                }).fail(function(res) {
                   nts.uk.ui.dialog.alertError(res.message);
                });
                return dfd.promise();
            }
            
            private getComponentDate(date: string): any {
                let lstComponent: Array<string> = date.split('/');
                return {
                    year: parseInt(lstComponent[0]),
                    month: parseInt(lstComponent[1]) - 1,
                    day: parseInt(lstComponent[2]),
                }
            }
        }
    }
}
