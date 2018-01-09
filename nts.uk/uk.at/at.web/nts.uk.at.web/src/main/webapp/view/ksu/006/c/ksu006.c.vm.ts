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
            
            isFilterData: boolean;
            
            constructor() {
                let self = this;
                
                self.completionList = ko.observableArray([]);
                
                self.dateRange = ko.observable(self.initDateRange());
                self.isIncomplete = ko.observable(true);
                self.isInterruption = ko.observable(true);
                self.isDone = ko.observable(true);
                
                self.dataLog = ko.observableArray([]);
                self.listColumn = ko.observableArray([
                    { headerText: "", key: 'executeId', dataType: "string", hidden: true, formatter: _.escape},
                    { headerText: nts.uk.resource.getText("KSU006_311"), key: 'startDate', width: 175, dataType: 'date', formatter: _.escape},
                    { headerText: nts.uk.resource.getText("KSU006_312"), key: 'endDate', width: 175, dataType: 'date', formatter: _.escape},
                    { headerText: nts.uk.resource.getText("KSU006_313"), key: 'extBudgetName', width: 180, dataType: 'string', formatter: _.escape},
                    { headerText: nts.uk.resource.getText("KSU006_314"), key: 'fileName', width: 200, dataType: 'string', formatter: _.escape},
                    { headerText: nts.uk.resource.getText("KSU006_315"), key: 'statusDes', width: 70, dataType: 'string', formatter: _.escape},
                    { headerText: nts.uk.resource.getText("KSU006_316"), key: 'numberSuccess', width: 70, dataType: 'number', formatter: _.escape},
                    { headerText: nts.uk.resource.getText("KSU006_317"), key: 'numberFail', width: 70, dataType: 'number', formatter: _.escape},
                    { headerText: nts.uk.resource.getText("KSU006_318"), key: 'download', width: 100, dataType: 'string', formatter: _.escape,
                        template: "{{if(${numberFail}) > 0}} <span id='download-log-${executeId}' style='text-decoration: underline;color: blue;'"
                            + "data-execute='${executeId}'>" + nts.uk.resource.getText("KSU006_319") + "</span>{{/if}}"}
                ]);
                self.rowSelected = ko.observable('');
                
                self.isFilterData = false;
                
                // Create Customs handle For event rened nts grid.
                (<any>ko.bindingHandlers).rended = {
                update: function(element: any, valueAccessor: any, allBindings: KnockoutAllBindingsAccessor) {
                        let dataLog = ko.unwrap(valueAccessor());                                       
                        if (!self.isFilterData) {
                            self.eventClick(dataLog);
                        }
                    }
                }
            }

            /**
             * startPage
             */
            public startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred<void>();
                
                nts.uk.ui.block.invisible();
                
                self.loadCompletionList().done(() => {
                    self.loadDataLog().done(() => {
                        nts.uk.ui.block.clear();
                        dfd.resolve();
                    })
                });
                return dfd.promise();
            }
            
            /**
             * initDateRange
             */
            private initDateRange(): any {
                let current: Date = new Date();
                return {
                    startDate: new Date(current.setMonth(current.getMonth() - 1)), // a month ago.
                    endDate: new Date()
                }
            }
            
            /**
             * eventClick
             */
            public eventClick(dataLog: any) {
                let self = this;
                let dfd = $.Deferred<void>();
                
                _.forEach(self.dataLog(), item => {
                    $('#download-log-' + item.executeId).on('click', function() {
                        nts.uk.ui.block.grayout();
                         
                        // download detail error
                        service.downloadDetailError(item.executeId).done(function() {                        
                            dfd.resolve();
                        }).fail(function(res: any) {
                            self.showMessageError(res);
                        }).always(function() {
                            nts.uk.ui.block.clear();
                        });
                  });
                });
            }
            
            /**
             * search
             */
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
                }else{
                    nts.uk.ui.dialog.alertError({messageId: 'Msg_160'});
                }
                // Completion: INTERRUPTION(2)
                if (self.isInterruption()) {
                    listState.push(2);
                }
                if (listState.length <= 0) {
                    nts.uk.ui.dialog.alertError({messageId: 'Msg_166'});
                    return;
                }
                
                // find data log
                self.loadDataLog(true, listState).done(() => {
                    self.isFilterData = true;
                });
                
                if (self.dataLog().length == 0) {
                    nts.uk.ui.dialog.alertError({ messageId: 'Msg_160' });
                }  
            }
            
            /**
             * closeDialog
             */
            public closeDialog() {
                nts.uk.ui.windows.close();
            }
            
            /**
             * loadDataLog
             */
            private loadDataLog(isSearchMode?: boolean, listState?: Array<number>): JQueryPromise<Array<ExternalBudgetLogModel>> {
                let self = this;
                let dfd = $.Deferred();
                
                let query: any = {};
                
                // initial screen
                if (!isSearchMode) {
                    query.startDate = self.dateRange().startDate;
                    query.endDate = self.dateRange().endDate;
                    
                    // INCOMPLETE(0), DONE(1), INTERRUPTION(2)
                    query.listState = self.completionList().map(item => item.value);//[0, 1, 2];
                }
                // mode search
                else {
                    let objStartDate: any = self.getComponentDate(self.dateRange().startDate);
                    let objEndDate: any = self.getComponentDate(self.dateRange().endDate);
                    
                    query.startDate = new Date(Date.UTC(objStartDate.year, objStartDate.month, objStartDate.day));
                    query.endDate = new Date(Date.UTC(objEndDate.year, objEndDate.month, objEndDate.day));
                    query.listState = listState;
                }
                
                // find all log
                service.findAllExternalBudgetLog(query).done(function(res: Array<ExternalBudgetLogModel>) {
                    self.dataLog(res);
                    dfd.resolve();
                }).fail(function(res: any) {
                    self.showMessageError(res);
                });
                return dfd.promise();
            }
            
            /**
             * loadCompletionList
             */
            private loadCompletionList(): JQueryPromise<Array<EnumerationModel>> {
                let self = this;
                let dfd = $.Deferred();
                service.findCompletionList().done(function(res: Array<EnumerationModel>) {
                    self.completionList(res);
                    dfd.resolve();
                }).fail(function(res) {
                   self.showMessageError(res);
                });
                return dfd.promise();
            }
            
            /**
             * getComponentDate
             */
            private getComponentDate(date: string): any {
                let lstComponent: Array<string> = date.split('/');
                return {
                    year: parseInt(lstComponent[0]),
                    month: parseInt(lstComponent[1]) - 1,
                    day: parseInt(lstComponent[2]),
                }
            }
            
            /**
             * showMessageError
             */
            private showMessageError(res: any) {
                if (res.businessException) {
                    nts.uk.ui.dialog.alertError({ messageId: res.messageId, messageParams: res.parameterIds });
                }
            }
        }
    }
}
