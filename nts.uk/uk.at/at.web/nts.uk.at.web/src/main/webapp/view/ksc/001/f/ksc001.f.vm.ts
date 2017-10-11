module nts.uk.at.view.ksc001.f {

import ScheduleExecutionLogSaveRespone = nts.uk.at.view.ksc001.b.service.model.ScheduleExecutionLogSaveRespone;
import ScheduleExecutionLogDto = service.model.ScheduleExecutionLogDto;
    export module viewmodel {

        export class ScreenModel {
            items: KnockoutObservableArray<ItemModel>;
            columns: KnockoutObservableArray<any>;
            currentCode: KnockoutObservable<any>;
            currentCodeList: KnockoutObservableArray<any>;
            count: number = 100;
            scheduleExecutionLog: ScheduleExecutionLogDto;
            executionStartDate: string;
            executionTotal: KnockoutObservable<string>;
            executionError: KnockoutObservable<string>;
            periodInfo: string;
            taskId: KnockoutObservable<string>;
            totalRecord: KnockoutObservable<number>;
            numberSuccess: KnockoutObservable<number>;
            numberFail: KnockoutObservable<number>;
            inputData: ScheduleExecutionLogSaveRespone;
            constructor() {
                var self = this;
                self.items = ko.observableArray([]);

                for (let i = 1; i < 100; i++) {
                    self.items.push(new ItemModel('00' + i, '基本給', "description " + i, "2010/1/1"));
                }

                self.columns = ko.observableArray([
                    { headerText: nts.uk.resource.getText("KSC001_55"), key: 'code', width: 80},
                    { headerText: nts.uk.resource.getText("KSC001_56"), key: 'name', width: 150 },
                    { headerText: nts.uk.resource.getText("KSC001_57"), key: 'description', width: 150 },
                    { headerText: nts.uk.resource.getText("KSC001_58"), key: 'other', width: 150 }
                ]);

                self.currentCode = ko.observable();
                self.currentCodeList = ko.observableArray([]);
                self.taskId = ko.observable('');
                self.totalRecord = ko.observable(0);
                self.numberSuccess = ko.observable(0);
                self.numberFail = ko.observable(0);
            }

            /**
            * start page data 
            */
            public startPage(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();
                var self = this;
                var inputData: ScheduleExecutionLogSaveRespone = nts.uk.ui.windows.getShared('inputData');
                if (inputData) {
                    service.findScheduleExecutionLogById(inputData.executionId).done(function(data) {
                        self.scheduleExecutionLog = data;
                        self.executionTotal = ko.observable('0');
                        self.executionError = ko.observable('0');
                        self.executionStartDate = moment.utc(data.executionDateTime.executionStartDate).format("YYYY/MM/DD HH:mm:ss");
                        self.periodInfo = nts.uk.resource.getText("KSC001_46", [moment(data.period.startDate).format('YYYY/MM/DD'), (moment(data.period.endDate).format('YYYY/MM/DD'))])
                        self.inputData = inputData;
                        dfd.resolve();
                    });
                }
                return dfd.promise();
            }
            /**
             * execution
             */
            public execution(): void {
                var self = this;
                // find task id
                service.executionScheduleExecutionLog(self.inputData).done(function(res: any) {
                    self.taskId(res.taskInfor.id);
                    // updateState
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
                                if (item.key == 'TOTAL_RECORD') {
                                    self.totalRecord(item.valueAsNumber);
                                }
                                if (item.key == 'SUCCESS_CNT') {
                                    self.numberSuccess(item.valueAsNumber);
                                }
                                if (item.key == 'FAIL_CNT') {
                                    self.numberFail(item.valueAsNumber);
                                }
                            });
                        }
                        self.executionTotal(nts.uk.resource.getText("KSC001_84", [self.numberSuccess(), self.totalRecord()]));
                        self.executionError(nts.uk.resource.getText("KSC001_85", [self.numberFail()]));
                        // finish task
                        if (res.succeeded || res.failed || res.cancelled) {
                            $('.countdown').stopCount();
                            /*self.isDone(true);
                            self.status(nts.uk.resource.getText("KSU006_217"));
                            
                            // end count time
                            if (res.error) {
                                self.showMessageError(res.error);
                            }*/
                            if (res.succeeded) {
                                $('#closeDialog').focus();
                            }
                        }
                    });
                }).while(infor => {
                    return infor.pending || infor.running;
                }).pause(1000));
            }
            

        }     
        
        
        export class ItemModel {
            code: string;
            name: string;
            description: string;
            other: string
            constructor(code: string, name: string, description: string, other: string) {
                this.code = code;
                this.name = name;
                this.description = description;
                this.other = other;
            }
        }
    }
}