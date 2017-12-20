module nts.uk.at.view.kdw001.h {
    import getText = nts.uk.resource.getText;
    export module viewmodel {
        export class ScreenModel {
            //list
            listClassification: KnockoutObservableArray<any>;
            columns: KnockoutObservableArray<any>;//nts.uk.ui.NtsGridListColumn
            currentSelectedRow: KnockoutObservable<any>;

            //param
            empCalAndSumExecLogID: string;
            executionStartTime: string;
            processingMonth: string;
            nameClosue: string;
            objectPeriod: Array<any>;
            listPeson: Array<any>;
            listTargetPerson: KnockoutObservableArray<any>;
            listErrMessage: Array<any>;
            listErrMessageInfo: KnockoutObservableArray<model.ErrMessageInfo>;
            executionContent: number;
            executionContentName:string;

            constructor() {
                let self = this;
                let param = nts.uk.ui.windows.getShared("openH");
                if (param != null) {
                    self.executionContent = param.executionContent;
                    self.empCalAndSumExecLogID = param.empCalAndSumExecLogID;
                    self.executionStartTime = param.executionStartTime;
                    self.processingMonth = param.processingMonth + "月度";
                    self.nameClosue = param.nameClosue;
                    self.objectPeriod = param.objectPeriod;
                    self.listPeson = param.listTargetPerson;
                    self.listErrMessage = param.listErrMessageInfo;
                    self.executionContentName = param.executionContentName;
                    self.listErrMessageInfo = ko.observableArray([]);
                    self.listTargetPerson = ko.observableArray([]);
                    


                }
                //list
                self.currentSelectedRow = ko.observable(null);

                self.columns = ko.observableArray([
                    { headerText: getText('KDW001_33'), key: 'employeeID', width: 100 },
                    { headerText: getText('KDW001_35'), key: 'resourceID', width: 100 },
                    { headerText: getText('KDW001_36'), key: 'disposalDay', width: 100 },
                    { headerText: getText('KDW001_37'), key: 'messageError', width: 199 },
                    { headerText: '', key: 'GUID', width: 1 ,hirren :true },
                ]);
            }
            printError(): void {
                let self = this;
                service.saveAsCsv(self.listErrMessageInfo());
            }
            startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();

                let dfdGetAllErrMessageInfoByEmpID = self.getAllErrMessageInfoByEmpID(self.empCalAndSumExecLogID);
                $.when(dfdGetAllErrMessageInfoByEmpID)
                    .done((dfdGetAllErrMessageInfoByEmpIDData) => {

                        dfd.resolve();
                    });
                return dfd.promise();

            }//end start page

            /**
             * getAllErrMessageInfoByEmpID
             */
            getAllErrMessageInfoByEmpID(empCalAndSumExeLogId: string) {
                let self = this;
                let dfd = $.Deferred();
                service.getAllErrMessageInfoByEmpID(empCalAndSumExeLogId).done(function(data) {
                    //lọc lấy theo executionContent
                    let temp = [];
                    for (let i = 0; i < data.length; i++) {
                        if (data[i].executionContent == self.executionContent) {
                            let item  = new model.ErrMessageInfo(
                                    data[i].employeeID,
                                    data[i].empCalAndSumExecLogID,
                                    data[i].resourceID,
                                    data[i].executionContent,
                                    data[i].disposalDay,
                                    data[i].messageError
                                );
                            temp.push(item);
                        }
                    }
                    self.listErrMessageInfo(_.orderBy(temp, ["employeeID", "disposalDay"], ["asc", "asc"]));
                    dfd.resolve();
                }).fail(function(res: any) {
                    dfd.reject();
                    nts.uk.ui.dialog.alertError(res.message).then(function() { nts.uk.ui.block.clear(); });
                });
                return dfd.promise();
            }
            closeDialog(): void {
                nts.uk.ui.windows.close();
            }
            
        }//end screenModel
        
    }//end viewmodel
    
    export module model{
        /**
         * class SetInforReflAprResult 
         */
        export class ErrMessageInfo{
            GUID : string;
            employeeID: string;
            empCalAndSumExecLogID : string;
            resourceID : string;
            executionContent :number;
            disposalDay :string;
            messageError :string;
            constructor(employeeID: string,empCalAndSumExecLogID : string,resourceID : string,
            executionContent:number,disposalDay:string,messageError:string){
                this.employeeID = employeeID;
                this.empCalAndSumExecLogID = empCalAndSumExecLogID;
                this.resourceID = resourceID;
                this.executionContent = executionContent;
                this.disposalDay = disposalDay;
                this.messageError = messageError;    
                this.GUID = nts.uk.util.randomId();
            }
        }//end classSetInforReflAprResult
    }
}//end module
