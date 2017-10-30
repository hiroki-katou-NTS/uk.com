module nts.uk.at.view.kdw001.i {
    import modelkdw001f = nts.uk.at.view.kdw001.f.model;
    export module viewmodel {
        export class ScreenModel {
            //table
            itemList: KnockoutObservableArray<any>;
            //obj
            empCalAndSumExecLogID :string;
            empCalAndSumExecLog: KnockoutObservable<modelkdw001f.EmpCalAndSumExeLog>;
            processingMonthName :KnockoutObservable<string>;
            executedMenuJapan:KnockoutObservable<string>;
            listExecutionLog: KnockoutObservableArray<modelkdw001f.ExecutionLog>;
            objectPeriod : KnockoutObservable<modelkdw001f.ObjectPeriod>;
            periodStartDate :KnockoutObservable<string>;
            periodEndDate :KnockoutObservable<string>;
            constructor() {
                let self = this;
               //table
                self.itemList  = ko.observableArray([]);
                self.empCalAndSumExecLogID = nts.uk.ui.windows.getShared("openI"); 
                self.empCalAndSumExecLog = ko.observable(null);
                self.processingMonthName = ko.observable('');
                self.executedMenuJapan = ko.observable('');
                self.listExecutionLog = ko.observableArray([]);
                self.objectPeriod = ko.observable(null);
                //start - end
                self.periodStartDate = ko.observable('');
                self.periodEndDate = ko.observable('');
                
                for (let i = 1; i <= 5; i++) {
                    self.itemList.push({column1: "2016/11/06 10:49:16 (" + i+")", 
                         column2 :"2016/11/06 10:50:16 (" + i+")",
                         column3 :"00:0"+i+":00",
                         column4 :"実行内容 "+i,
                         column5 :"実行種別"+i,
                         column6 :"実行詳細内容"+i,
                         column7 : i+i+"人",
                         column8 : i+"人" });
                }
            }
            
            startPage(): JQueryPromise<any> {
                
                let self = this;
                let dfd = $.Deferred();
                let dfdGetEmpCalAndSumExeLog = self.getByEmpCalAndSumExeLogId(self.empCalAndSumExecLogID);
                
                $.when(dfdGetEmpCalAndSumExeLog).done((dfdGetEmpCalAndSumExeLogData) => {
                    
                    dfd.resolve();
                });
                return dfd.promise();
            }//end start page
            
            getByEmpCalAndSumExeLogId(empCalAndSumExeLogId : string){
                let self = this;
                let dfd = $.Deferred();
                service.getByEmpCalAndSumExeLogId(empCalAndSumExeLogId).done(function(data){
                    self.empCalAndSumExecLog(new modelkdw001f.EmpCalAndSumExeLog(
                                    data[0].empCalAndSumExecLogID,
                                    data[0].caseSpecExeContentID,
                                    data[0].employeeID,
                                    data[0].executedMenu,
                                    data[0].executionStatus,
                                    data[0].executionDate,
                                    data[0].processingMonth,
                                    data[0].closureID,
                                    data[0].executionLogs
                                ));
                    self.processingMonthName(self.empCalAndSumExecLog().processingMonthName);
                    self.executedMenuJapan(self.empCalAndSumExecLog().executedMenuJapan);
                    self.listExecutionLog(self.empCalAndSumExecLog().executionLogs);
                    self.objectPeriod(self.listExecutionLog()[0].objectPeriod);
                    self.periodStartDate(self.objectPeriod().startDate);
                    self.periodEndDate(self.objectPeriod().endDate);
                    //self.empCalAndSumExecLog(data[0]);
                });
                
            }
            
            //open dialog G
            openDialogG() {
                nts.uk.ui.windows.sub.modal("/view/kdw/001/g/index.xhtml");
            }
            
             //open dialog H
            openDialogH() {
                nts.uk.ui.windows.sub.modal("/view/kdw/001/h/index.xhtml");
            }
            
            closeDialog(): void {
                nts.uk.ui.windows.close();
            }
        }//end screenModel
    }//end viewmodel    
}//end module
    