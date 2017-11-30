module nts.uk.at.view.ksu007.b {

    import ScheduleBatchCorrectSetting = nts.uk.at.view.ksu007.a.viewmodel.ScheduleBatchCorrectSetting;
    import ScheduleBatchCorrectSettingSave = nts.uk.at.view.ksu007.a.service.model.ScheduleBatchCorrectSettingSave;
    export module viewmodel {

        export class ScreenModel {
            columns: KnockoutObservableArray<any>;
            currentCode: KnockoutObservable<any>;
            currentCodeList: KnockoutObservableArray<any>;
            count: number = 100;
            executionStartDate: string;
            executionTotal: KnockoutObservable<string>;
            executionError: KnockoutObservable<string>;
            periodInfo: string;
            taskId: KnockoutObservable<string>;
            totalRecord: KnockoutObservable<number>;
            numberSuccess: KnockoutObservable<number>;
            numberFail: KnockoutObservable<number>;
            inputData: ScheduleBatchCorrectSettingSave;
            isError: KnockoutObservable<boolean>;
            isFinish: KnockoutObservable<boolean>;
            constructor() {
                var self = this;

                self.columns = ko.observableArray([
                    { headerText: nts.uk.resource.getText("KSC001_55"), key: 'employeeId', width: 80},
                    { headerText: nts.uk.resource.getText("KSC001_56"), key: 'employeeCode', width: 150 },
                    { headerText: nts.uk.resource.getText("KSC001_57"), key: 'employeeName', width: 150 },
                    { headerText: nts.uk.resource.getText("KSC001_58"), key: 'errorContent', width: 150 }
                ]);

                self.currentCode = ko.observable();
                self.currentCodeList = ko.observableArray([]);
                self.taskId = ko.observable('');
                self.totalRecord = ko.observable(0);
                self.numberSuccess = ko.observable(0);
                self.numberFail = ko.observable(0);
                self.executionTotal = ko.observable('xxxxxx');
                self.executionError = ko.observable('yyyyyyyyy');
                self.isError = ko.observable(false);
                self.isFinish = ko.observable(false);
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
                
                nts.uk.deferred.repeat(conf => conf
                .task(() => {
                    return nts.uk.request.asyncTask.getInfo(self.taskId()).done(function(res: any) {
                        // update state on screen
                        if (res.running || res.succeeded || res.cancelled) {
                             _.forEach(res.taskDatas, item => {
                                if (item.key == 'DATA_EXECUTION') {
                                    console.log(item);
                                }
                            });
                        }
                        self.executionTotal(nts.uk.resource.getText("KSC001_84", [self.numberSuccess(), self.totalRecord()]));
                        self.executionError(nts.uk.resource.getText("KSC001_85", [self.numberFail()]));
                        // finish task
                        if (res.succeeded || res.failed || res.cancelled) {
                            $('.countdown').stopCount();
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
            }
        }     

        
    }
}