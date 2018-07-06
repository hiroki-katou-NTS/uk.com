module nts.uk.com.view.cmf002.y {
        import getText = nts.uk.resource.getText;
    export module viewmodel {
        export class ScreenModel {
            
            externalOutLog: KnockoutObservableArray<ExternalOutLog> = ko.observableArray([]);
            columns: KnockoutObservableArray<NtsGridListColumn>;
            currentCode: KnockoutObservableArray<any>;
            count: number = 100;
            
            //grid error
            processCount: KnockoutObservable<string> = ko.observable('');
            errorItem: KnockoutObservabe<string> = ko.observable('');
            errorTargetValue: KnockoutObservable<string> = ko.observable('');
            errorContent: KnockoutObservable<string> = ko.observable('');
            errorEmployee: KnockoutObservable<string> = ko.observable('');
            
            //Y2_1_2
            nameSetting: KnockoutObservable<string> = ko.observable('');
            //Y2_2_2
            specifiedStartDate: KnockoutObservable<string> = ko.observable('');
            //Y2_2_4
            specifiedEndDate: KnockoutObservable<string> = ko.observable('');
            //Y2_3_2
            processStartDateTime: KnockoutObservable<string> = ko.observable('');
            //Y4_1_2
            totalCount: KnockoutObservable<number> = ko.observable(0);
            //Y4_2_2
            normalCount: KnockoutObservable<number> = ko.observable(0);
            //Y4_3_3
            totalErrorCount: KnockoutObservable<number> = ko.observable(0);
            //Y4_1_3, Y4_2_3, Y4_3_3
            processUnit: KnockoutObservable<string> = ko.observable('');
            
            constructor() {
                let self = this;
                self.totalCount.subscribe(function (value) {
                    totalCount = ko.observable(totalCount - totalErrorCount);
                }
                    
                self.totalErrorCount.subscribe(function (value) {
                    totalCount = ko.observable(totalCount - totalErrorCount);
                }    

                this.columnsExternalOutLog = ko.observableArray([
                    {headerText: getText('CMF002_336'), key: 'processCount', width: 150},
                    {headerText: getText('CMF002_337'), key: 'errorItem', width: 150},
                    {headerText: getText('CMF002_338'), key: 'errorTargetValue', width: 150},
                    {headerText: getText('CMF002_339'), key: 'errorEmployee', width: 350}
                ]);
                
                this.currentCode = ko.observableArray();

            }
            //開始
            start(): JQueryPromise<any> {
                let self = this,
                    dfd = $.Deferred();

                dfd.resolve();
                return dfd.promise();
            }

        }

        class ExternalOutLog {
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
                
                constructor(companyId: string, outputProcessId: string, errorContent?: string,
                errorTargetValue?: string, errorDate?: string, errorEmployee?: string,
                errorItem?: string, logRegisterDateTime: string, logSequenceNumber: number,
                processCount: number, processContent: string){
                    this.companyId = companyId;
                    this.outputProcessId = outputProcessId;
                    this.errorContent = errorContent ? errorContent : null;
                    this.errorTargetValue = errorTargetValue ? errorTargetValue : null;
                    this.errorDate = errorDate ? errorDate : null;
                    this.errorEmployee = errorEmployee ? errorEmployee : null;
                    this.errorItem = errorItem ? errorItem : null;
                    this.logRegisterDateTime = logRegisterDateTime;
                    this.logSequenceNumber = logSequenceNumber;
                    this.processCount = processCount;
                    this.processContent = processContent;
                }
            }
        
            class ExterOutExecLog {
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
                resultStatus?: number, nameSetting: string){
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