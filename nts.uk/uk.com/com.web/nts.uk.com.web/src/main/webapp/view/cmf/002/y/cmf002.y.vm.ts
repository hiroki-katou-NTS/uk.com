module nts.uk.com.view.cmf002.y {
    import getText = nts.uk.resource.getText;
    import alertError = nts.uk.ui.dialog.alertError;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    export module viewmodel {
        export class ScreenModel {
            //param
            storeProcessingId: string;
            logSequenceNumber: number;
            
            iErrorContentCSV: KnockoutObservable<IErrorContentCSV>;
            
            exterOutExecLog: KnockoutObservable<ExterOutExecLog>;
            externalOutLog: KnockoutObservableArray<ExternalOutLog> = ko.observableArray([]);
            columnsExternalOutLog: KnockoutObservableArray<NtsGridListColumn>;
            currentCode: KnockoutObservableArray<any>;
            count: number = 100;

            //grid error
            processCount: KnockoutObservable<string> = ko.observable('');
            errorItem: KnockoutObservabe<string> = ko.observable('');
            errorTargetValue: KnockoutObservable<string> = ko.observable('');
            errorContent: KnockoutObservable<string> = ko.observable('');
            errorEmployee: KnockoutObservable<string> = ko.observable('');
            
            normalCount: KnockoutObservable<number> = ko.observable(0);
            
            totalCount: KnockoutObservable<number> = ko.observable(0);
            totalErrorCount: KnockoutObservable<number> = ko.observable(0);
            
            constructor() {
                let self = this;
                let getProcessingId = getShared('CMF002_Y_PROCESINGID');
                storeProcessingId = getProcessingId;
                
                self.exterOutExecLog = ko.observable({
                    //Y2_1_2
                    nameSetting: '',
                    //Y2_2_2
                    specifiedStartDate: '',
                    //Y2_2_4
                    specifiedEndDate: '',
                    //Y2_3_2
                    processStartDateTime: '',
                    //Y4_1_2
                    totalCount: 0,
                    //Y4_3_3
                    totalErrorCount: 0,
                    //Y4_1_3, Y4_2_3, Y4_3_3
                    processUnit: '',
                });
                
                self.totalCount.subscribe(function(value) {
                    self.normalCount(self.totalCount() - self.totalErrorCount());
                });
                self.totalErrorCount.subscribe(function(value) {
                    self.normalCount(self.totalCount() - self.totalErrorCount());
                });
                
                self.iErrorContentCSV =  ko.observable(new IErrorContentCSV("", self.exterOutExecLog(), self.externalOutLog()));
                
                service.getExterOutExecLog(storeProcessingId).done(function(res: ExterOutExecLog) {
                    self.totalCount(res.totalCount);
                    self.totalErrorCount(res.totalErrorCount);
                    self.exterOutExecLog(res);
                    self.iErrorContentCSV(new IErrorContentCSV(self.exterOutExecLog().nameSetting, self.exterOutExecLog(), self.externalOutLog()));
                }).fail(function(res: any) {
                    console.log("FindExterOutExecLog fail");
                });
                

                service.getExternalOutLog(storeProcessingId).done(function(res: Array<ExterOutExecLog>) {
                    let sortByExternalOutLog =  _.orderBy(res, ["logRegisterDateTime"]);
                        if (sortByExternalOutLog && sortByExternalOutLog.length) {
                        _.forOwn(sortByExternalOutLog, function(index) {
                            self.externalOutLog.push(new ExternalOutLog(
                            index.errorContent,
                            index.errorEmployee,
                            index.errorTargetValue,
                            index.errorItem
                            ));
                            self.iErrorContentCSV(new IErrorContentCSV("", self.exterOutExecLog(), self.externalOutLog()));
                        });
                    }
                }).fail(function(res: any) {
                    console.log("FindgetExternalOutLog fail");
                });

                this.columnsExternalOutLog = ko.observableArray([
                    { headerText: getText('CMF002_337'), key: 'errorItem', width: 120},
                    { headerText: getText('CMF002_338'), key: 'errorTargetValue', width: 120 },
                    { headerText: getText('CMF002_339'), key: 'customerrorContent', width: 300 }
                ]);

                this.currentCode = ko.observableArray();

            }
            //開始
            start(): JQueryPromise<any> {
                $('#listlog_container').removeAttr('tabindex');
                let self = this,
                    dfd = $.Deferred();

                dfd.resolve();
                return dfd.promise();
            }
            
            // エラー出力
            errorExport() {
                let self = this;
                nts.uk.ui.block.invisible();
                service.exportDatatoCsv(self.iErrorContentCSV()).fail(function(res: any) {
                    alertError({ messageId: res.messageId });
                }).always(function() {
                    nts.uk.ui.block.clear();
                });
            }
            // close popup
            public close(): void {
                nts.uk.ui.windows.close();
            }
        }
        
        class IErrorContentCSV {
         nameSetting: string;
         resultLog: ExterOutExecLog;
         errorLog: ExternalOutLog[];
            constructor (nameSetting: string, resultLog: ExterOutExecLog, errorLog: ExternalOutLog[]){
                this.nameSetting = nameSetting;
                this.resultLog = resultLog;
                this.errorLog = errorLog;
            }
     }

        //外部出力結果ログ
        export class ExternalOutLog {
            companyId: string;
            outputProcessId: string;
            errorContent: string;
            errorTargetValue: string;
            errorDate: string;
            errorEmployee: string;
            errorItem: string;
            logRegisterDateTime: string;
            logSequenceNumber: number;
            processCount: number;
            processContent: string;
            customerrorContent: string;

            constructor(errorContent?: string, errorTargetValue?: string, errorEmployee?: string, errorItem?: string, customerrorContent: string) {
                this.errorContent = errorContent ? errorContent : null;
                this.errorTargetValue = errorTargetValue ? errorTargetValue : null;
                this.errorEmployee = errorEmployee ? errorEmployee : null;
                this.errorItem = errorItem ? errorItem : null; 
                this.customerrorContent = errorContent + "(" + getText('CMF002_356') + errorEmployee + ")" ;
            }
        }

        //外部出力実行結果ログ
        export class ExterOutExecLog {
            companyId: string;
            outputProcessId: string;
            userId: string;
            totalErrorCount: number;
            totalCount: number;
            fileId: string;
            fileSize: number;
            deleteFile: number;
            fileName: string;
            roleType: number;
            processUnit: string;
            processEndDateTime: string;
            processStartDateTime: string;
            standardClass: number;
            executeForm: number;
            executeId: string;
            designatedReferenceDate: string;
            specifiedEndDate: string;
            specifiedStartDate: string;
            codeSettingCondition: string;
            resultStatus: number;
            nameSetting: string;

            constructor(companyId: string, outputProcessId: string, userId?: string,
                totalErrorCount: number, totalCount: number, fileId?: string,
                fileSize?: number, deleteFile: number, fileName?: string,
                roleType?: number, processUnit?: string, processEndDateTime?: string,
                processStartDateTime: string, standardClass: number, executeForm: number,
                executeId: string, designatedReferenceDate: string, specifiedEndDate: string,
                specifiedStartDate: string, codeSettingCondition: string,
                resultStatus?: number, nameSetting: string) {
                this.companyId = companyId;
                this.outputProcessId = outputProcessId;
                this.userId = userId ? userId : null;
                this.totalErrorCount = totalErrorCount;
                this.totalCount = totalCount;
                this.fileId = fileId ? fileId : null;
                this.fileSize = fileSize ? fileSize : null;
                this.deleteFile = deleteFile;
                this.fileName = fileName ? fileName : null;
                this.roleType = roleType ? roleType : null;
                this.processUnit = processUnit ? processUnit : null;
                this.processEndDateTime = processEndDateTime ? processEndDateTime : null;
                this.processStartDateTime = processStartDateTime;
                this.standardClass = standardClass;
                this.executeForm = executeForm;
                this.executeId = executeId;
                this.designatedReferenceDate = designatedReferenceDate;
                this.specifiedEndDate = specifiedEndDate;
                this.specifiedStartDate = specifiedStartDate;
                this.codeSettingCondition = codeSettingCondition;
                this.resultStatus = resultStatus ? resultStatus : null;
                this.nameSetting = nameSetting;
            }
        }


    }
}