module nts.uk.at.view.kdw001.g {
    import getText = nts.uk.resource.getText;
    export module viewmodel {
        export class ScreenModel {
            //list
            columns: KnockoutObservableArray<any>;//nts.uk.ui.NtsGridListColumn
            currentSelectedRow: KnockoutObservable<any>;
            
            
            //param
            executionStartTime :string;
            processingMonth : string;
            nameClosue : string;
            objectPeriod : Array<any>;
            listPeson : Array<any>;
            listTargetPerson: KnockoutObservableArray<any>;
            executionContent : number;
            executionContentName :string;
            
            constructor() {
                let self = this;
                let param  = nts.uk.ui.windows.getShared("openG");
                self.currentSelectedRow = ko.observable(null);
                if(param !=null){
                    self.executionStartTime  = param.executionStartTime;  
                    self.processingMonth = param.processingMonth+ "月度";
                    self.nameClosue = param.nameClosue;
                    self.objectPeriod = param.objectPeriod;
                    self.listPeson = param.listTargetPerson;
                    self.listTargetPerson = ko.observableArray([]);
                    self.executionContent = param.executionContent;
                    self.executionContentName = param.executionContentName;
                    
                    for(let i = 0;i<self.listPeson.length;i++){
                        self.listTargetPerson().push({employeeCode: self.listPeson[i].employeeId,
                        personCode: self.listPeson[i].personCode,
                        personName: self.listPeson[i].personName,   
                         employeeName :self.listPeson[i].employeeId,
                         
                         status : _.find(self.listPeson[i].state, function(state) { 
                                return state.executionContent == self.executionContent; }).statusName 
                         
                         });
                    }
                }
                
                
                self.columns = ko.observableArray([
                    { headerText: '', key: 'employeeCode', width: 1, hidden: true},
                    { headerText: getText('KDW001_33'), key: 'personCode', width: 100 },
                    { headerText: getText('KDW001_35'), key: 'personName', width: 200 },
                    { headerText: getText('KDW001_51'), key: 'status', width: 100 }
                ]);
            }
            
            startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                dfd.resolve();
                return dfd.promise();
            }//end start page
            
            closeDialog(): void {
                nts.uk.ui.windows.close();
            }
            
            
        }//end screenModel
    }//end viewmodel    
}//end module
    