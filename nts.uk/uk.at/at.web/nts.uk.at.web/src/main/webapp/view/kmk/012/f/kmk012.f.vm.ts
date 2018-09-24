module nts.uk.at.view.kmk012.f {
    
    import service = nts.uk.at.view.kmk012.f.service;
    
    export module viewmodel {
        
        const YYYYMMDD = "YYYY/MM/DD";
        const EMPTY = "";
        
        export class ScreenModel {
            
            operationStartDate: KnockoutObservable<string>;
            
            constructor() {
                let _self = this;            
                _self.operationStartDate = ko.observable('');
            }
            
            public start_page(): JQueryPromise<any> {
                let _self = this,
                    dfd = $.Deferred<any>();
                service.findByCid().done((data) => {
                    _self.operationStartDate(data.operateStartDateDailyPerform);
                    dfd.resolve();  
                })
                return dfd.promise();
            }
            
            private closeSaveDialog(): void {
                let _self = this;
                
                let command = new OperationStartSetDailyPerformCommand(__viewContext.user.companyId, 
                                                                        moment(_self.operationStartDate()).isValid() ? moment(_self.operationStartDate()).format(YYYYMMDD) : EMPTY);
                service.save(command).done(() => {
                    nts.uk.ui.dialog.info({ messageId: "Msg_15" })
                })
            }
            
            private closeDialog(): void {
                nts.uk.ui.windows.close();
            }
        }
        
        export class OperationStartSetDailyPerformCommand {
            // 会社ID
            private companyId: string;
            
            // 日別実績の運用開始日
            private operateStartDateDailyPerform: string;
            
            constructor(companyId: string, operateStartDateDailyPerform: string) {
                this.companyId = companyId;
                this.operateStartDateDailyPerform = operateStartDateDailyPerform;
            }
        }
    }
}