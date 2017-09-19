module nts.uk.com.view.cmm011.b {
    export module viewmodel {
        
        import WorkplaceHistory = base.WorkplaceHistoryAbstract;
        import IHistory = nts.uk.com.view.cmm011.base.IHistory;
        
        export class ScreenModel {
            
            
            workplaceHistory: KnockoutObservable<WorkplaceHistoryModel>;
            startDate: KnockoutObservable<string>;
            endDate: KnockoutObservable<string>;
            
            constructor() {
                let self = this;
                
                self.workplaceHistory = ko.observable(new WorkplaceHistoryModel(self));
                self.startDate = ko.observable('');
                self.endDate = ko.observable('');
                self.workplaceHistory().selectedWpkHistory.subscribe(function(code) {
                    self.startDate(self.workplaceHistory().lstWpkHistory().filter(item => item.historyId == code)[0].startDate);
                });
            }
            
            /**
             * startPage
             */
            public startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred<any>();
                self.getAllHistory().done(function() {
                    dfd.resolve();
                });
                return dfd.promise();
            }
            
            private getAllHistory(): JQueryPromise<void> {
                let self = this;
                let dfd = $.Deferred<void>();
                service.findLstWkpConfigHistory().done(function(data) {
                    if (data.wkpConfigHistory&& data.wkpConfigHistory.length>0) {
                        self.workplaceHistory().init(data.wkpConfigHistory);
                        self.workplaceHistory().screenMode(ScreenMode.SelectionMode);
                    }
                    else {
                        self.workplaceHistory().screenMode(ScreenMode.NewMode);
                        self.endDate(nts.uk.resource.getText("CMM011_27"));
                    } 
                    dfd.resolve();
                });
                return dfd.promise();
            }
            
            /**
             * execution
             */
            public execution() {
                var self =this;
                //check screen mode
                //new mode
                if (self.workplaceHistory().screenMode() == ScreenMode.NewMode) {
                    var data = {
                        wkpConfigHistory: {
                            historyId: '',
                            period: {
                                startDate: new Date(self.startDate()),
                                endDate: new Date("9999-12-31")//TODO reset default date
                            }
                        }
                    }
                    service.registerWkpConfig(data).done(function() {
                        alert("ok");
                    });
                } 
                nts.uk.ui.windows.close();
            }
            
            /**
             * close
             */
            public close() {
                nts.uk.ui.windows.close();
            }
            
            /**
             * showMessageError
             */
            public showMessageError(res: any) {
                if (res.businessException) {
                    nts.uk.ui.dialog.alertError({ messageId: res.messageId, messageParams: res.parameterIds });
                }
            }
        }
        
        /**
         * WorkplaceHistoryModel
         */
        class WorkplaceHistoryModel extends WorkplaceHistory {
            
            screenModel: ScreenModel;
            
               // mode
            screenMode: KnockoutObservable<number>;
            addBtnControl: KnockoutObservable<boolean>;
            updateBtnControl: KnockoutObservable<boolean>;
            
            constructor(screenModel: ScreenModel) {
                super();
                let self = this;
                
                self.screenModel = screenModel;
                
                // mode
                self.screenMode = ko.observable(null);

                // subscribe
                self.lstWpkHistory.subscribe(newList => {
                    
                    // list empty or null -> new mode
                    if (!newList || newList.length <= 0) {
                        self.screenMode(ScreenMode.NewMode);
                        self.screenModel.endDate(nts.uk.resource.getText("CMM011_27"));
                    }
                });
                self.addBtnControl = ko.computed(function() {
                    if (self.screenMode() == ScreenMode.SelectionMode || self.screenMode() == ScreenMode.UpdateMode) {
                        return self.isSelectFirst();
                    }
                    return false;
                });
                
                self.updateBtnControl = ko.computed(function() {
                    if (self.screenMode() == ScreenMode.SelectionMode) {
                        return self.isSelectFirst();
                    }
                    return false;
                });
            }
            
            init(data: Array<any>) {
                let self = this;
                let lstWpkHistory: Array<IHistory> = [];
                data.forEach(function(item, index) {
                    //workplaceId not key => ""
                    lstWpkHistory.push({ workplaceId: "", historyId: item.historyId, startDate: item.period.startDate, endDate: item.period.endDate });
                });
                self.lstWpkHistory(lstWpkHistory);
                if (data && data.length > 0) {
                    self.selectFirst();
                }
            }
            
            /**
             * addHistory
             */
            public addHistory() {
                let self = this;
                self.screenMode(ScreenMode.AddMode);
            }
            
            /**
             * updateHistory
             */
            public updateHistory() {
                let self = this;
                self.screenMode(ScreenMode.UpdateMode);
            }
            
            /**
             * removeHistory
             */
            public removeHistory() {
                let self = this;
            }
        }
        
        //Screen mode define
        export enum ScreenMode {
            SelectionMode = 0,
            NewMode = 1,
            AddMode = 2,
            UpdateMode = 3
        }
    }
}