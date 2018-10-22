module nts.uk.at.view.ksu007.b {

    import ScheduleBatchCorrectSetting = nts.uk.at.view.ksu007.a.viewmodel.ScheduleBatchCorrectSetting;
    import ScheduleBatchCorrectSettingSave = nts.uk.at.view.ksu007.a.service.model.ScheduleBatchCorrectSettingSave;
    import ErrorContentDto = nts.uk.at.view.ksu007.b.service.Model.ErrorContentDto;

    export module viewmodel {

        export class ScreenModel {
            errorLogs: KnockoutObservableArray<any>;
            columns: KnockoutObservableArray<any>;
            currentCode: KnockoutObservable<any>;
            currentCodeList: KnockoutObservableArray<any>;
            count: number = 100;
            executionStartDate: string;
            executionState: KnockoutObservable<string>;
            executionError: KnockoutObservable<string>;
            periodInfo: string;
            taskId: KnockoutObservable<string>;
            totalRecord: KnockoutObservable<number>;
            numberSuccess: KnockoutObservable<number>;
            numberFail: KnockoutObservable<number>;
            dataError: KnockoutObservableArray<ErrorContentDto>;
            inputData: ScheduleBatchCorrectSettingSave;
            isError: KnockoutObservable<boolean>;
            isFinish: KnockoutObservable<boolean>;
            readIndex: KnockoutObservableArray<number>;
            constructor() {
                var self = this;
                self.errorLogs = ko.observableArray([]);
                
                self.columns = ko.observableArray([
                    { headerText: nts.uk.resource.getText("KSU007_16"), key: 'employeeId', width: 80},
                    { headerText: nts.uk.resource.getText("KSU007_17"), key: 'employeeName', width: 150 },
                    { headerText: nts.uk.resource.getText("KSU007_18"), key: 'ymd', width: 150 },
                    { headerText: nts.uk.resource.getText("KSU007_19"), key: 'message', width: 150 }
                ]);

                self.currentCode = ko.observable();
                self.currentCodeList = ko.observableArray([]);
                self.taskId = ko.observable('');
                self.totalRecord = ko.observable(0);
                self.numberSuccess = ko.observable(0);
                self.numberFail = ko.observable(0);
                self.dataError = ko.observableArray([]);
                self.executionState = ko.observable('zzzzzz');
                self.executionError = ko.observable('yyyyyyyyy');
                self.isError = ko.observable(false);
                self.isFinish = ko.observable(false);
                self.readIndex = ko.observableArray([]);
            }
            
            /**
            * start page data 
            */
            public startPage(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();
                var self = this;
                var inputData: ScheduleBatchCorrectSettingSave = nts.uk.ui.windows.getShared('inputKSU007');
                if (inputData) {
                    self.inputData = inputData;
                    // update data view
                    dfd.resolve();
                }
                return dfd.promise();
            }
            /**
             * execution
             */
            public execution(): void {
                var self = this;
                // find task id
                service.executionScheduleBatchCorrectSetting(self.inputData).done(function(res: any) {
                    self.taskId(res.taskInfor.id);
                    // update state
                    self.updateState();
                }).fail(function(res: any) {
                    console.log(res);
                });
            }
            
            /**
             * function on click save CommonGuidelineSetting
             */
            public saveCommonGuidelineSetting(): void {
                var self = this;
                nts.uk.ui.windows.close();
            }

            /**
             * Event on click cancel button.
             */
            public cancelSaveCommonGuidelineSetting(): void {
                nts.uk.ui.windows.close();
            }
            
            
            /**
             * updateState
             */
            private updateState() {
                let self = this;
                // start count time
                $('.countdown').startCount();
                
                // Set execution state to processing
                self.executionState('処理中… ');
                
                nts.uk.deferred.repeat(conf => conf
                .task(() => {
                    return nts.uk.request.asyncTask.getInfo(self.taskId()).done(function(res: any) {
                        // update state on screen
                        if (res.running || res.succeeded || res.cancelled) {
                             _.forEach(res.taskDatas, item => {
                                if (item.key.substring(0, 5) == "DATA_") {
                                    if (self.readIndex.indexOf(parseInt(item.key.substring(5))) != -1) {
                                        return;
                                    }
                                    var errors = JSON.parse(item.valueAsString);
                                    _.forEach(errors, error => {
                                        var errorContent : ErrorContentDto = {
                                            employeeId : error.employeeCode,
                                            employeeName : error.employeeName,
                                            ymd : error.dateYMD,
                                            message : error.message.length >8?error.message:nts.uk.resource.getMessage(error.message)
                                        }   
                                        self.errorLogs.push(errorContent);
                                    });
                                    self.readIndex.push(parseInt(item.key.substring(5)));
                                }
                                if (item.key == 'NUMBER_OF_SUCCESS') {
                                     self.numberSuccess(item.valueAsNumber);
                                }
                                if (item.key == 'NUMBER_OF_ERROR') {
                                     self.numberFail(self.errorLogs().length);
                                }
                                //self.totalRecord(self.numberSuccess() + self.numberFail());
                            });
                        }
                        
                        //self.executionTotal(nts.uk.resource.getText("KSC001_84", [self.numberSuccess(), self.totalRecord()]));
                        self.executionError(nts.uk.resource.getText("KSC001_85", [self.numberFail()]));
                        // finish task
                        if (res.succeeded || res.failed || res.cancelled || res.status == "REQUESTED_CANCEL") {
                            self.errorLogs.sort(function(a,b) {
                                return a.employeeId.localeCompare(b.employeeId) || (moment(a.ymd, 'YYYY/MM/DD').toDate() - moment(b.ymd, 'YYYY/MM/DD').toDate());
                            });
                            
                            self.executionState('完了');
                            
                            $('.countdown').stopCount();
                            if (res.succeeded) {
                                $('#closeDialog').focus();
                            }
                            if (self.numberFail() > 0) {
                                self.isError(true);
                                $('#tableShowError').show();
                            }
                            
                            self.numberFail(self.errorLogs().length);
                            self.readIndex.removeAll();
                            self.isFinish(true);
                        }
                    });
                }).while(infor => {
                    return (infor.pending || infor.running) && infor.status != "REQUESTED_CANCEL";
                }).pause(1000));
            }

            /**
            * find by client service ScheduleBatchCorrectSetting by employee
            * */
            private findScheduleBatchCorrectSettingByEmployeeId(employeeId: string): JQueryPromise<ScheduleBatchCorrectSetting> {
                return nts.uk.characteristics.restore("PersonalSchedule_" + employeeId);
            }

            /**
             * find by client service ScheduleBatchCorrectSetting
            */
            private findScheduleBatchCorrectSetting(): JQueryPromise<ScheduleBatchCorrectSetting> {
                var self = this;
                var user: any = __viewContext.user;
                return self.findScheduleBatchCorrectSettingByEmployeeId(user.employeeId);
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
                
                //service.interrupt();
            }
            
            /**
             * funtion export file csv error
             */
            private exportFileError(): void {
                let self = this;
                _.forEach(self.errorLogs(), error => {
                    error.employeeCode = error.employeeId;
                    error.dateYMD = moment(error.ymd, 'YYYY/MM/DD').toDate();                   
                });
                nts.uk.ui.block.grayout();
                service.exportFileError(self.errorLogs()).done(function() {
                }).always(function() {
                    nts.uk.ui.block.clear();
                });
            }
        }
    }
}