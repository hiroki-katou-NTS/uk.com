module nts.uk.com.view.cmf001.q {
    
    //import service = nts.uk.com.view.cmf001.q.service;
    export module viewmodel {
        export class ScreenModel {
            // mode 
            isCheckMode: KnockoutObservable<boolean>;
            // stop view error mode
            isStopMode: KnockoutObservable<boolean>;
            
            testData: KnockoutObservable<testModel> = ko.observable(new testModel("", 0));
            totalRecord: KnockoutObservable<number> =  ko.observable(0);
            currentRecord: KnockoutObservable<number> =  ko.observable(0);
            // dto from server
            //exAcOpManage: ExAcOpManage;
            // object param
            //params = getShared("CMF001qParams");
            codCode: string;
            codeName: string;
            timeOver: string;
            operatingCondition: string;
            errorLogs: KnockoutObservableArray<any>;
            columns: KnockoutObservableArray<any>;
            currentCode: KnockoutObservable<any>;
            currentCodeList: KnockoutObservableArray<any>;
            count: number = 100;
            executionStartDate: string;
            executionState: KnockoutObservable<string> =  ko.observable('');
            executionError: KnockoutObservable<string>;
            periodInfo: string;
            taskId: KnockoutObservable<string> =  ko.observable('');            
            numberSuccess: KnockoutObservable<number>;
            numberFail: KnockoutObservable<number> =  ko.observable(0);
            //dataError: KnockoutObservableArray<ErrorContentDto>;
            //inputData: ScheduleBatchCorrectSettingSave;
            isError: KnockoutObservable<boolean>;
            isFinish: KnockoutObservable<boolean>;
            constructor() {
                let self = this;
                // mode
                self.isCheckMode = ko.observable(true);
                self.isStopMode = ko.observable(false);
                
                // dump data. Delete after phase 2 
                //self.exAcOpManage = new ExAcOpManage('1', '001', 8, 0, 92, 100, 6);
                self.codCode = "001";
                self.codeName = "A社人事管理情報";
                self.timeOver = "00:01:27";
                //self.operatingCondition = getListProcessing()[ self.exAcOpManage.stateBehavior].value;
            }
            
            // 閉じる
            close(){
                 nts.uk.ui.windows.close();
            }
            /**
             * execution
             */
            public execution(): void {
                var self = this;
                let command: commandTest = new commandTest("Test123455666", 100, 1);
                // find task id
                service.executionImportCsvData(command).done(function(res: any) {
                    self.taskId(res.taskInfor.id);
                    // update state
                    self.updateState();
                }).fail(function(res: any) {
                    console.log(res);
                });
            }
            /**
             * updateState
             */
            private updateState() {
                let self = this;                
                // Set execution state to processing
                self.executionState('処理中… ');
                
                nts.uk.deferred.repeat(conf => conf
                .task(() => {
                    return nts.uk.request.asyncTask.getInfo(self.taskId()).done(function(res: any) {
                        // update state on screen
                        if (res.running || res.succeeded || res.cancelled) {
                             _.forEach(res.taskDatas, item => {
                                let serverData = JSON.parse(item.valueAsString);
                                //self.testData(serverData);
                                 console.log(serverData);
                                 self.executionState('処理中… ');
                                 //totalRecord
                                 if (item.key == 'TOTAL_RECORD') {
                                     self.totalRecord(item.valueAsNumber);
                                }
                                 if (item.key == 'NUMBER_OF_SUCCESS') {
                                     self.currentRecord(item.valueAsNumber);
                                }
                                if (item.key == 'NUMBER_OF_ERROR') {
                                     self.numberFail(item.valueAsNumber);
                                }
                            });
                            
                        }
                        
                        if (res.succeeded || res.failed || res.cancelled) {
//                          
                            self.executionState('完了');
                            
                            if (res.succeeded) {
                                $('#closeDialog').focus();
                            }                      
                        }
                    });
                }).while(infor => {
                    return infor.pending || infor.running;
                }).pause(1000));
            }
             /**
             * function cancel execution
             */
            private stopExecution(): void {
                let self = this;
                
                if (nts.uk.text.isNullOrEmpty(self.taskId())) {
                    return;
                }
                // interrupt process import then close dialog
                nts.uk.request.asyncTask.requestToCancel(self.taskId());
                //nts.uk.ui.windows.close();
            }
        }
     }
    export class commandTest {
        test: string;
        csvLine: number;
        currentLine: number;
        constructor(test: string, csvLine: number, currentLine: number) {
            let self = this;
            self.test = test;
            self.csvLine = csvLine;
            self.currentLine = currentLine;
        }
    }
    export class testModel{
         status: string;
        data: number;   
        constructor(status: string, data: number) {
            let self = this;
            self.status = status;
            self.data = data;
        }
    }    
}