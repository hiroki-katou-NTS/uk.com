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
            listErrMessageInfo: KnockoutObservableArray<any>;
            executionContent: number;

            constructor() {
                let self = this;
                let param = nts.uk.ui.windows.getShared("openH");
                if (param != null) {
                    self.executionContent = param.listTargetPerson[0].state.executionContent;
                    self.empCalAndSumExecLogID = param.empCalAndSumExecLogID;
                    self.executionStartTime = param.executionStartTime;
                    self.processingMonth = param.processingMonth + "月度";
                    self.nameClosue = param.nameClosue;
                    self.objectPeriod = param.objectPeriod;
                    self.listPeson = param.listTargetPerson;
                    self.listErrMessage = param.listErrMessageInfo;
                    self.listErrMessageInfo = ko.observableArray([]);
                    self.listTargetPerson = ko.observableArray([]);


                }
                //list
                self.currentSelectedRow = ko.observable(null);

                self.columns = ko.observableArray([
                    { headerText: getText('KDW001_33'), key: 'employeeID', width: 100 },
                    { headerText: getText('KDW001_35'), key: 'resourceID', width: 100 },
                    { headerText: getText('KDW001_36'), key: 'disposalDay', width: 100 },
                    { headerText: getText('KDW001_37'), key: 'messageError', width: 100 }
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
                            temp.push(data[i]);
                        }
                    }
                    self.listErrMessageInfo(temp);
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
}//end module
