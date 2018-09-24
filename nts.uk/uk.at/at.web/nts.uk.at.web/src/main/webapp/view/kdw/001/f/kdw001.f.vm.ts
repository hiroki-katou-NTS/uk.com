module nts.uk.at.view.kdw001.f {
    import getText = nts.uk.resource.getText;
    export module viewmodel {
        export class ScreenModel {
            nameClosure : string;
            enable: KnockoutObservable<boolean>;
            required: KnockoutObservable<boolean>;
            dateValue: KnockoutObservable<any>;
            startDateString: KnockoutObservable<any>;
            endDateString: KnockoutObservable<any>;
            //table
            columns: Array<any>;//nts.uk.ui.NtsGridListColumn
            currentSelectedRow: KnockoutObservable<any>;
    
            //InputEmpCalAndSumByDate
            inputEmpCalAndSumByDate: KnockoutObservable<model.InputEmpCalAndSumByDate>;
            //list obj EmpCalAndSumExeLog 
            empCalAndSumExeLog: KnockoutObservableArray<model.EmpCalAndSumExeLog>;
            //list caseSpecExeContent
            listCaseSpecExeContent : KnockoutObservableArray<model.CaseSpecExeContent>;
            //listClosure
            listClosure : KnockoutObservableArray<any>;
            //list sid
            listSid : Array<string>;
            //list obj by sid
            listInfoPerson :  KnockoutObservableArray<any>;

            constructor() {
                let self = this;
                
                //
                self.enable = ko.observable(true);
                self.required = ko.observable(true);
                self.dateValue = ko.observable({});
                self.dateValue().startDate = moment.utc().subtract(7, "d").format("YYYY/MM/DD");
                self.dateValue().endDate = moment.utc().format("YYYY/MM/DD");
                self.startDateString = ko.observable('');
                self.endDateString = ko.observable(new Date());
                //table
                self.currentSelectedRow = ko.observable(null);

                //inputEmpCalAndSumByDate (startDate and endDate)
                self.inputEmpCalAndSumByDate = ko.observable(
                    new model.InputEmpCalAndSumByDate(self.dateValue().startDate, self.dateValue().endDate));
                // list obj EmpCalAndSumExeLog
                self.empCalAndSumExeLog = ko.observableArray([]);
                //list obj CaseSpecExeContent
                self.listCaseSpecExeContent =  ko.observableArray([]);
                //list obj listClosure
                self.listClosure =  ko.observableArray([]);
                //listSid
                self.listSid = [];
                //list obj by sid
                self.listInfoPerson =  ko.observableArray([]);
                self.columns = [
                    { headerText: getText('KDW001_73'), key: 'executionDate', width: 100 },
                    { headerText: '', key: 'empCalAndSumExecLogID', width: 1, hidden: true},
                    
                    { headerText: getText('KDW001_74'), key: 'personCode', width: 120 },
                    { headerText: getText('KDW001_75'), key: 'personName', width: 100 },
                    { headerText: getText('KDW001_76'), key: 'processingMonthName', width: 150 },
                    //doi mau
                    { headerText: getText('KDW001_77'), key: 'executedMenuName', width: 200,
                            formatter: function (executedMenuName, record) {
                                if(record.isTextRed.toString() === "true"){
                                    return "<label style='color: red;'> " + executedMenuName + " </label>";       
                                } else {
                                    return "<label> " + executedMenuName + " </label>";
                                }
                } },
                    { headerText: '', key: 'isTextRed', width: 1, hidden: true},
                    { headerText: getText('KDW001_78'), key: 'executionStatusName', width: 160 },
                    {
                        headerText: getText('KDW001_79'), key: 'executionStatus', width: 100,
                        template: '<button tabindex = "0" class="open-dialog-i" data-id="${empCalAndSumExecLogID}">参照</button>',
                        columnCssClass: "colStyleButton",
                    }
                ];
                $('#button-search').focus();
            }

            /**
             * functiton start pagea
             */
            startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                // get all CaseSpecExeContent
                let dfdAllCaseSpecExeContent = self.getAllCaseSpecExeContent();
                let dfdAllClosure = self.getAllClosure();
                $.when(dfdAllCaseSpecExeContent,dfdAllClosure).done((dfdAllCaseSpecExeContentData,dfdAllClosureData) => {
                     //get all EmpCalAndSumExeLog by date
                    self.getAllEmpCalAndSumExeLog(self.inputEmpCalAndSumByDate()).done(() => {
                        dfd.resolve();
                    });
                    
                });

                return dfd.promise();
            }//end start page

            /**
             * function get all EmpCalAndSumExeLog by startDate and endDate
             */
            getAllEmpCalAndSumExeLog(inputEmpCalAndSumByDate: model.InputEmpCalAndSumByDate) {
                let self = this;
                let dfd = $.Deferred<any>();
                nts.uk.ui.block.grayout();
                service.getAllEmpCalAndSumExeLog(inputEmpCalAndSumByDate).done(function(data: Array<model.IEmpCalAndSumExeLog>) {
                    //_.sortBy(self.empCalAndSumExeLog(data), 'executionDate');
                    data = _.orderBy(data, ['executionDate'], ['desc']);
                    let temp = [];
                    _.each(data, (value) => {
                        
                        if (self.listSid.indexOf(value.employeeID) == -1)
                            self.listSid.push(value.employeeID);
                        
                        let item = new model.EmpCalAndSumExeLog(value);
                        //executedMenuName
                        if( item.executedMenu == 1) {
                            item.executedMenuName = _.find(self.listCaseSpecExeContent(), function(caseSpecExeContent) { 
                                return caseSpecExeContent.caseSpecExeContentID == item.caseSpecExeContentID; }).useCaseName ;  
                        }
                        // set name closure by date
                        _.find(self.listClosure(), function(closure) { 
                            if (closure.closureId == item.closureID) {
                                //id
                                item.changeName(_.find(closure.listClosureHistoryForLog, (historyClosure: any) => {
                                    return item.processingMonth >= historyClosure.startYearMonth && item.processingMonth <= historyClosure.endYearMonth;
                                }).closureId,
                                //name
                                _.find(closure.listClosureHistoryForLog, (historyClosure: any) => {
                                    return item.processingMonth >= historyClosure.startYearMonth && item.processingMonth <= historyClosure.endYearMonth;
                                }).closureName);
                            }
                        });
                        // set isTextRed
                        item.changeIsTextRed(false); 
                        _.find(value.executionLogs, function(executionLog) { 
                            if(executionLog.executionContent ==0){
                                if(executionLog.dailyCreationSetInfo.executionType == 1){
                                    item.changeIsTextRed(true);
                                }
                            }else if (executionLog.executionContent ==1){
                                if(executionLog.dailyCalSetInfo.executionType == 1){
                                    item.changeIsTextRed(true);     
                                }
                            }else if (executionLog.executionContent ==2){
                                if(executionLog.reflectApprovalSetInfo.executionType == 1){
                                    item.changeIsTextRed(true);     
                                }
                            } else if (executionLog.executionContent ==3){
                                if(executionLog.monlyAggregationSetInfo.executionType == 1){
                                    item.changeIsTextRed(true);     
                                }
                            }
                        });
                        temp.push(item);
                    });
                    self.empCalAndSumExeLog(temp);
                    $('#single-list_container').attr('tabindex', -1); 
                    self.getListPersonInforLog(self.listSid).done(function(){
                        dfd.resolve(); 
                        nts.uk.ui.block.clear();   
                    });
                }).fail(function(res: any) {
                    dfd.reject();
                    nts.uk.ui.dialog.alertError(res).then(function() { nts.uk.ui.block.clear(); });
                });
                return dfd.promise();
            }//end function getAllEmpCalAndSumExeLog
            
            /**
             * function get all caseSpecExeContent
             */
            getAllCaseSpecExeContent(){
                let self = this;
                let dfd = $.Deferred<any>();
                service.getAllCaseSpecExeContent().done(function(data){
                    self.listCaseSpecExeContent(data);
                    dfd.resolve(data);
                }).fail(function(res: any) {
                    dfd.reject();
                    nts.uk.ui.dialog.alertError(res).then(function() { nts.uk.ui.block.clear(); });
                });
                return dfd.promise();
            }
            
            
            
            /**
             * get caseSpecExeContent by id
             */
            getCaseSpecExeContent(caseSpecExeContentID:string){
                let self = this;
                let dfd = $.Deferred<any>();
                service.getCaseSpecExeContentById(caseSpecExeContentID).done(function(data){
                    dfd.resolve(data);
                }).fail(function(res: any) {
                    dfd.reject();
                    nts.uk.ui.dialog.alertError(res).then(function() { nts.uk.ui.block.clear(); });
                });
                return dfd.promise();
            }
            
            /**
             * get all Closure
             */
            getAllClosure(){
                let self = this;
                let dfd = $.Deferred<any>();
                service.getAllClosure().done(function(data){
                     self.listClosure(data);
                    dfd.resolve(data);
                }).fail(function(res: any) {
                    dfd.reject();
                    nts.uk.ui.dialog.alertError(res).then(function() { nts.uk.ui.block.clear(); });
                });
                return dfd.promise();
            }
            /**
             * get all person info 
             */
            getListPersonInforLog(listSid:Array<string>){
                let self = this;
                let dfd = $.Deferred<any>();
                service.getListPersonInforLog(listSid).done(function(data){ 
                    self.listInfoPerson(data);
                    
                    _.each(self.empCalAndSumExeLog(), (value) => {
                            _.find(self.listInfoPerson(), function(person) { 
                                if(value.employeeID == person.employeeId){
                                    value.personCode = person.employeeCode;
                                    value.personName = person.namePerson;    
                                }
                            });
                        }); 
//                    _.find(data, function(personInforLog) { 
//                      listInfoPerson s
//                    
//                    });
                    self.empCalAndSumExeLog.valueHasMutated();
                    dfd.resolve(data);
                }).fail(function(res: any) {
                    dfd.reject();
                    nts.uk.ui.dialog.alertError(res).then(function() { nts.uk.ui.block.clear(); });
                });
                return dfd.promise();
                
            }
            

            //button search
            search() {
                let self = this;
                if(nts.uk.ui.errors.hasError()){
                    return;
                    }
                self.inputEmpCalAndSumByDate(new model.InputEmpCalAndSumByDate(self.dateValue().startDate, self.dateValue().endDate));
                self.getAllEmpCalAndSumExeLog(self.inputEmpCalAndSumByDate());
            }

            //open dialog I
            openDialogI() {
                let self = this;
                var param = {
                    nameClosure : _.find(self.empCalAndSumExeLog(), function(o){return o.empCalAndSumExecLogID == self.currentSelectedRow() }) == undefined?"": 
                            _.find(self.empCalAndSumExeLog(), function(o){return o.empCalAndSumExecLogID == self.currentSelectedRow() }).closureName,
                    empCalAndSumExecLogID : self.currentSelectedRow(),
                    executedMenuName : _.find(self.empCalAndSumExeLog(), function(o){return o.empCalAndSumExecLogID == self.currentSelectedRow() }) == undefined?"": _.find(self.empCalAndSumExeLog(), function(o){return o.empCalAndSumExecLogID == self.currentSelectedRow() }).executedMenuName
                    
                };
                nts.uk.ui.windows.setShared("openI", param);
                nts.uk.ui.windows.sub.modal("/view/kdw/001/i/index.xhtml");
            }
            
            openScreenA() {
                nts.uk.request.jump("/view/kdw/001/a/index.xhtml");
            }
            
        }//end screenModel
    }//end viewmodel

    //module model
    export module model {
        export interface IEmpCalAndSumExeLog {
            empCalAndSumExecLogID: string;
            processingMonth: number;
            processingMonthName: string;
            executedMenu: number;
            executedMenuName: string;
            executedMenuJapan: string;
            executionDate: string;
            executionStatus: number;
            executionStatusName : string;
            employeeID: string;
            closureID: number;
            closureName : string;
            caseSpecExeContentID: string;
            executionLogs: Array<IExecutionLog>;
            isTextRed : boolean;
            personCode : string;
            personName : string;
        }

        export interface IExecutionLog {
            empCalAndSumExecLogID: string;
            executionContent: number;
            executionContentName :string;
            existenceError: number;
            executionTime: ExecutionTime;
            processStatus: number;
            objectPeriod: ObjectPeriod;
            calExecutionSetInfoID :string;
            reflectApprovalSetInfo : SetInforReflAprResult;
            dailyCreationSetInfo : SettingInforForDailyCreation;
            dailyCalSetInfo : CalExeSettingInfor;
            monlyAggregationSetInfo : CalExeSettingInfor;
            numberPersonErr : number;
        }

        export interface IExecutionTime {
            startTime: string;
            endTime: string;
        }

        /**
         * class EmpCalAndSumExeLog
         */
        export class EmpCalAndSumExeLog {
            empCalAndSumExecLogID: string;
            processingMonth: number;
            processingMonthName: string;
            executedMenu: number;
            executedMenuName: string;
            executedMenuJapan: string;
            executionDate: string;
            executionStatus: number;
            executionStatusName : string;
            employeeID: string;
            closureID: number;
            closureName : string;
            caseSpecExeContentID: string;
            executionLogs: Array<ExecutionLog>;
            isTextRed : boolean;
            personCode : string;
            personName : string;
            
            constructor(data: IEmpCalAndSumExeLog) {
                this.empCalAndSumExecLogID = data.empCalAndSumExecLogID;
                this.processingMonth = data.processingMonth;
                //fix bug 100501
                this.closureName = data.closureName == null ? " " : data.closureName;
                this.processingMonthName = data.processingMonth%100 + "月度" + "   " + this.closureName || data.processingMonth%100 + "月度" + "   " + "" ;
                this.executedMenu = data.executedMenu;
                if (data.executedMenu == 0) {
                    this.executedMenuName = "詳細実行";
                }

                this.executedMenuJapan = data.executedMenuJapan;
                this.executionDate = data.executionDate.toString().slice(0,10);
                this.executionStatus = data.executionStatus;
                this.executionStatusName = data.executionStatusName;
                this.employeeID = data.employeeID;
                this.closureID = data.closureID;
                this.caseSpecExeContentID = data.caseSpecExeContentID;
                this.executionLogs = data.executionLogs;
                this.isTextRed = data.isTextRed;
                this.personCode = data.personCode;
                this.personName = data.personName;
            }
            
            public changeName(id : any,name: string): void {
                this.closureName = name == null ? " " : name;
                this.processingMonthName = this.processingMonth%100 +"月度   "+ this.closureName;
            }
            
            public changeIsTextRed(isTextRed: boolean): void {
                this.isTextRed = isTextRed;
            }
            
            public changeNameAndCode(personCode: string,personName : string): void {
                this.personCode = personCode;
                this.personName = personName;
            }
        }//end class EmpCalAndSumExeLog

        /**
         * class ExecutionLog
         */
        export class ExecutionLog {
            empCalAndSumExecLogID: string;
            executionContent: number;
            executionContentName :string;
            existenceError: number;
            executionTime: ExecutionTime;
            processStatus: number;
            objectPeriod: ObjectPeriod;
            calExecutionSetInfoID :string;
            reflectApprovalSetInfo : SetInforReflAprResult;
            dailyCreationSetInfo : SettingInforForDailyCreation;
            dailyCalSetInfo : CalExeSettingInfor;
            monlyAggregationSetInfo : CalExeSettingInfor;
            numberPersonErr : number;
            constructor(data: IExecutionLog) {
                this.empCalAndSumExecLogID = data.empCalAndSumExecLogID;
                this.executionContent = data.executionContent;
                this.executionContentName = data.executionContentName;   
                this.existenceError = data.existenceError;
                this.executionTime = new ExecutionTime(data.executionTime);
                this.processStatus = data.processStatus;
                this.objectPeriod = data.objectPeriod;
                this.calExecutionSetInfoID = data.calExecutionSetInfoID;
                this.reflectApprovalSetInfo = data.reflectApprovalSetInfo;
                this.dailyCreationSetInfo = data.dailyCreationSetInfo;
                this.dailyCalSetInfo = data.dailyCalSetInfo;
                this.monlyAggregationSetInfo = data.monlyAggregationSetInfo;
                this.numberPersonErr = data.numberPersonErr;
            }
        }//end class ExecutionLog
        
        /**
         * class SetInforReflAprResult 
         */
        export class SetInforReflAprResult{
            executionType: number;
            executionTypeName : string;
            forciblyReflect : boolean;
            constructor(executionType: number,executionTypeName : string,forciblyReflect : boolean){
                this.executionType = executionType;
                this.executionTypeName = executionTypeName;
                this.forciblyReflect = forciblyReflect;    
            }
        }//end classSetInforReflAprResult
        
        /**
         * class SettingInforForDailyCreation
         */
        export class SettingInforForDailyCreation{
            executionType: number;
            executionTypeName : string;
            creationType : number;
            partResetClassification : PartResetClassification;
            constructor(executionType: number,executionTypeName : string,creationType : number,partResetClassification : PartResetClassification){
                this.executionType = executionType;
                this.executionTypeName = executionTypeName; 
                this.creationType = creationType; 
                this.partResetClassification = partResetClassification; 
            }
            
        }//end class SettingInforForDailyCreation
        
        /**
         * class PartResetClassification
         */
        export class PartResetClassification{
            //マスタ再設定
            masterReconfiguration : boolean;
            //休業再設定
            closedHolidays : boolean ;
            // 就業時間帯再設定
            resettingWorkingHours : boolean ;
            // 打刻のみ再度反映
            reflectsTheNumberOfFingerprintChecks : boolean ;
            // 特定日区分再設定
            specificDateClassificationResetting :  boolean ;
            // 申し送り時間再設定
            resetTimeAssignment :  boolean ;
            // 育児・介護短時間再設定
            resetTimeChildOrNurseCare : boolean ;
            // 計算区分再設定
            calculationClassificationResetting : boolean ;
            constructor(
                masterReconfiguration : boolean,
                closedHolidays : boolean,
                resettingWorkingHours : boolean ,
                reflectsTheNumberOfFingerprintChecks : boolean ,
                specificDateClassificationResetting :  boolean ,
                resetTimeAssignment :  boolean,
                resetTimeChildOrNurseCare : boolean,
                calculationClassificationResetting : boolean ){
                
                this.masterReconfiguration = masterReconfiguration;
                this.closedHolidays = closedHolidays;
                this.resettingWorkingHours = resettingWorkingHours;
                this.reflectsTheNumberOfFingerprintChecks = reflectsTheNumberOfFingerprintChecks;
                this.specificDateClassificationResetting = specificDateClassificationResetting;
                this.resetTimeAssignment = resetTimeAssignment;
                this.resetTimeChildOrNurseCare = resetTimeChildOrNurseCare;
                this.calculationClassificationResetting = calculationClassificationResetting;
                
            }
            
            
        }//end class PartResetClassification
        
        /**
         * class ExecutionTime
         */
        export class ExecutionTime {
            startTime: string;
            endTime: string;
            timeSpan: string;
            constructor(data: IExecutionTime) {
                var momentStart = moment.utc(data.startTime, "YYYY/MM/DD HH:mm:ss");
                var momentEnd = moment.utc(data.endTime, "YYYY/MM/DD HH:mm:ss");
                
                this.startTime = momentStart.format("YYYY/MM/DD HH:mm:ss");
                this.endTime = momentEnd.format("YYYY/MM/DD HH:mm:ss");
                var ms = moment(momentEnd,"DD/MM/YYYY HH:mm:ss").diff(moment(momentStart,"DD/MM/YYYY HH:mm:ss"));
                var d = moment.duration(ms);
                this.timeSpan = Math.floor(d.asHours()) + moment.utc(ms).format(":mm:ss");
            }
        }//end class ExecutionTime

        /**
         * class CalExeSettingInfor
         */
        export class CalExeSettingInfor {
            executionContent: number;
            executionType: number;
            executionTypeName : string;
            calExecutionSetInfoID :  string;
            caseSpecExeContentID : string ;
            constructor(executionContent: number, executionType: number,executionTypeName : string,
            calExecutionSetInfoID :  string,
            caseSpecExeContentID : string ) {
                this.executionContent = executionContent;
                this.executionType = executionType;
                this.executionTypeName = executionTypeName;
                this.calExecutionSetInfoID = calExecutionSetInfoID;
                this.caseSpecExeContentID = caseSpecExeContentID;
            }//end class ExecutionTime
        }//end class CalExeSettingInfor

        /**
         * class ObjectPeriod
         */
        export class ObjectPeriod {
            startDate: string;
            endDate: string;
            constructor(startDate: string, endDate: string) {
                this.startDate = moment.utc(startDate, "YYYY/MM/DD").toISOString();
                this.endDate = moment.utc(endDate, "YYYY/MM/DD").toISOString();
            }
        }//end class ObjectPeriod

        /**
         * class InputEmpCalAndSumByDate 
         */
        export class InputEmpCalAndSumByDate {
            startDate: string;
            endDate: string;
            constructor(startDate: string, endDate: string) {
                this.startDate = moment.utc(startDate, "YYYY/MM/DD HH:mm:ss");
                this.endDate = moment.utc(endDate, "YYYY/MM/DD HH:mm:ss");
            }
        }//end class InputEmpCalAndSumByDate
        
        /**
         * class CaseSpecExeContent
         */
        export class CaseSpecExeContent{
            caseSpecExeContentID :string;
            orderNumber : number;
            useCaseName :string;
            constructor (caseSpecExeContentID :string,orderNumber : number,useCaseName :string){
                this.caseSpecExeContentID =caseSpecExeContentID ;
                this.orderNumber = orderNumber;
                this.useCaseName = useCaseName;
                
            }
        }//end class CaseSpecExeContent
        

    }//end module model

}//end module