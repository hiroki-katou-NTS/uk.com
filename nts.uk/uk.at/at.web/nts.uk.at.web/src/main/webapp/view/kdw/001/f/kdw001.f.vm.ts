module nts.uk.at.view.kdw001.f {
    import getText = nts.uk.resource.getText;
    export module viewmodel {
        export class ScreenModel {
            enable: KnockoutObservable<boolean>;
            required: KnockoutObservable<boolean>;
            dateValue: KnockoutObservable<any>;
            startDateString: KnockoutObservable<any>;
            endDateString: KnockoutObservable<any>;
            //table
            listClassification : KnockoutObservableArray<any>;
            columns: Array<any>;//nts.uk.ui.NtsGridListColumn
            currentSelectedRow: KnockoutObservable<any>;
            
            //InputEmpCalAndSumByDate
            inputEmpCalAndSumByDate : KnockoutObservable<model.InputEmpCalAndSumByDate>;
            //obj EmpCalAndSumExeLog 
            empCalAndSumExeLog :  KnockoutObservable<model.EmpCalAndSumExeLog>;
            
            constructor() {
                let self = this;
                //
                self.enable = ko.observable(true);
                self.required = ko.observable(true);
                self.dateValue = ko.observable({});
                let today = new Date;
                self.dateValue().startDate = new Date(today.getFullYear()-1, today.getMonth(), today.getDate()+2);
                self.dateValue().endDate =today;
                self.startDateString = ko.observable('');
                self.endDateString = ko.observable(new Date());
                //table
                self.listClassification = ko.observableArray([]);
                self.currentSelectedRow = ko.observable(null);
                
                //inputEmpCalAndSumByDate (startDate and endDate)
                self.inputEmpCalAndSumByDate  = ko.observable(
                                new model.InputEmpCalAndSumByDate(self.dateValue().startDate,self.dateValue().endDate));
                //obj EmpCalAndSumExeLog
                self.empCalAndSumExeLog = ko.observable(null);
                
                let temp = [];
                for (let i = 1; i <= 15; i++) {
                    temp.push({
                         date: "2017/01/0"+i, 
                         code :"A00000"+i,
                         name :"name "+i,
                         timeclose :"time close "+i,
                         menu :"menu"+i,
                         result :"result "+i,
                         detail : "参照 "});
                }
                self.listClassification(temp);
                self.columns = [
                    { headerText: getText('KDW001_73'), key: 'date', width: 100 },
                    { headerText: getText('KDW001_74'), key: 'code', width: 120 },
                    { headerText: getText('KDW001_75'), key: 'name', width: 100 },
                    { headerText: getText('KDW001_76'), key: 'timeclose', width: 150 },
                    { headerText: getText('KDW001_77'), key: 'menu', width: 200 },
                    { headerText: getText('KDW001_78'), key: 'result', width: 160 },
                    { headerText: getText('KDW001_79'), key: 'detail', width: 100, 
                        template: '<button data-bind="click :openDialogI">${detail}</button>', 
                        columnCssClass: "colStyleButton",
                         }
                ];
            }
            
            /**
             * functiton start page
             */
            startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                //get all EmpCalAndSumExeLog by date
                let dfdAllEmpCalAndSumExeLog = self.getAllEmpCalAndSumExeLog(self.inputEmpCalAndSumByDate());
                
                $.when(dfdAllEmpCalAndSumExeLog).done((dfdAllEmpCalAndSumExeLogData) => {
                    dfd.resolve();
                });
                
                return dfd.promise();
            }//end start page
            
            /**
             * function get all EmpCalAndSumExeLog by startDate and endDate
             */
            getAllEmpCalAndSumExeLog(inputEmpCalAndSumByDate : model.InputEmpCalAndSumByDate){
                let self = this;
                let dfd = $.Deferred<any>();
                service.getAllEmpCalAndSumExeLog(inputEmpCalAndSumByDate).done(function(data){
                    self.empCalAndSumExeLog(data);
                    dfd.resolve(data);
                }).fail(function(res: any) {
                    dfd.reject();
                    nts.uk.ui.dialog.alertError(res.message).then(function() { nts.uk.ui.block.clear(); });
                });
                return dfd.promise();
            }//end function getAllEmpCalAndSumExeLog
            
            
            //open dialog I
            openDialogI(){
                nts.uk.ui.windows.sub.modal("/view/kdw/001/i/index.xhtml");    
            }
        }//end screenModel
    }//end viewmodel
    
    //module model
    export module model {
        
        /**
         * class EmpCalAndSumExeLog
         */
        export class EmpCalAndSumExeLog{
            empCalAndSumExecLogID:number;
            caseSpecExeContentID :number;
            employeeID : string;
            executedMenu : number;
            executionStatus : number;
            executionDate : string;
            processingMonth : number;
            closureID : number;
            executionLogs : Array<ExecutionLog>;
            constructor(empCalAndSumExecLogID :number,
                    caseSpecExeContentID :number,
                    employeeID : string,
                    executedMenu : number,
                    executionStatus : number,
                    executionDate : string,
                    processingMonth : number,
                    closureID : number,
                    executionLogs : Array<ExecutionLog>){
                this.empCalAndSumExecLogID = empCalAndSumExecLogID;
                this.caseSpecExeContentID = caseSpecExeContentID;
                this.employeeID = employeeID;
                this.executedMenu = executedMenu;
                this.executionStatus = executionStatus;
                this.executionDate = executionDate;
                this.processingMonth = processingMonth;
                this.closureID = closureID;
                this.executionLogs = executionLogs;
            }
            
        }//end class EmpCalAndSumExeLog
        
        /**
         * class ExecutionLog
         */
        export class ExecutionLog{
            empCalAndSumExecLogID : number;
            caseSpecExeContentID:number;
            employeeID:string;
            executedLogID : string;
            existenceError : number;
            executeContenByCaseID : number;
            executionContent : number;
            executionTime : ExecutionTime;
            processStatus :number;
            calExeSetInfor : CalExeSettingInfor;
            objectPeriod : ObjectPeriod;
            constructor(empCalAndSumExecLogID : number,
                caseSpecExeContentID :number,
                employeeID :string,
                executedLogID : string,
                existenceError : number,
                executeContenByCaseID : number,
                executionContent : number,
                executionTime : ExecutionTime,
                processStatus :number,
                calExeSetInfor : CalExeSettingInfor,
                objectPeriod : ObjectPeriod){
                this.empCalAndSumExecLogID = empCalAndSumExecLogID;
                this.caseSpecExeContentID = caseSpecExeContentID;
                this.employeeID = employeeID;
                this.executedLogID = executedLogID;
                this.existenceError = existenceError;
                this.executeContenByCaseID = executeContenByCaseID;
                this.executionContent = executionContent;
                this.executionTime = executionTime;
                this.processStatus = processStatus;
                this.calExeSetInfor = calExeSetInfor;
                this.objectPeriod = objectPeriod;
            }
        }//end class ExecutionLog
        /**
         * class ExecutionTime
         */
        export class ExecutionTime{
            startDate : string;
            endDate : string;
            constructor(startDate : string,endDate : string){
                this.startDate = startDate;
                this.endDate = endDate;
            }
        }//end class ExecutionTime
        
        /**
         * class CalExeSettingInfor
         */
        export class CalExeSettingInfor{
            executionContent : number;
            executionType : number;
            constructor(executionContent : number,executionType : number){
                this.executionContent = executionContent;
                this.executionType = executionType;
            }//end class ExecutionTime
        }//end class CalExeSettingInfor
        
        /**
         * class ObjectPeriod
         */
        export class ObjectPeriod{
            
        }//end class ObjectPeriod
        
        /**
         * class InputEmpCalAndSumByDate 
         */
        export class InputEmpCalAndSumByDate {
            startDate: string;
            endDate: string;
            constructor(startDate: string, endDate: string) {
                this.startDate = startDate;
                this.endDate = endDate;
            }
        }//end class InputEmpCalAndSumByDate
        
    }//end module model
    
}//end module
    