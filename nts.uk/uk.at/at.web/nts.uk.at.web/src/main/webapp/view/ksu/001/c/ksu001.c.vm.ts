module nts.uk.at.view.ksu001.c {

    export module viewmodel {
        export class ScreenModel {
            taskId: KnockoutObservable<string>;
            inputData: any;
            processingState: KnockoutObservable<string>;
            state: KnockoutObservable<string>;
            numberError: KnockoutObservable<string>;
            isError: KnockoutObservable<boolean>;
            inforError: KnockoutObservableArray<InforError>;
            constructor() {
                let self = this;
                let selectionCondition = nts.uk.ui.windows.getShared('selectionCondition');
                let startDate = nts.uk.ui.windows.getShared('startDate');
                let endDate = nts.uk.ui.windows.getShared('endDate');
                let listEmployee = nts.uk.ui.windows.getShared("listEmployee");
                self.inforError = ko.observableArray([]);
                self.inputData = {
                    employee: listEmployee,
                    conditionNos: selectionCondition,
                    endTime: 0,
                    startTime: 0
                }
                self.isError = ko.observable(false);
                self.processingState = ko.observable(nts.uk.resource.getText('KSU001_216'));
                self.state = ko.observable('-');
                self.numberError = ko.observable('');
                self.taskId = ko.observable('');
            }
            /**
             * start page  
             */
            public startPage(): JQueryPromise<any> {
                let self = this,
                    dfd = $.Deferred();
                dfd.resolve();
                return dfd.promise();
            }
            stop(): void {
                let self = this;
                $('#error-status').css('display', '');
                $('#error-output').css('display', '');
                $('#stop').css('display', 'none');
                nts.uk.ui.windows.getSelf().setHeight(730);
                nts.uk.ui.windows.getSelf().setWidth(910);
            }
            /**
            * Print file excel
            */
            private exportExcel(): void {
                let self = this;
                nts.uk.ui.block.grayout();
                service.saveAsExcel(ko.toJS(self.inforError())).done(function() {
                }).fail(function(error) {
                    nts.uk.ui.dialog.alertError(error.messageId);
                }).always(function() {
                    nts.uk.ui.block.clear();
                });
            }
            /**
             * execution
             */
            public execution(): void {
                let self = this;

                service.executionAlarmChecked(self.inputData).done((res) => {
                    self.taskId(res.taskInfor.id);
                    self.updateState();
                });
            }
            /**
             * Close dialog
             */
            public closeDialog(): void {
                nts.uk.ui.windows.close();
            }

            /**
              * updateState
              */
            public updateState(): void {
                let self = this;
                // start count time
                $('.countdown').startCount();
                nts.uk.deferred.repeat(conf => conf
                    .task(() => {
                        return nts.uk.request.asyncTask.getInfo(self.taskId()).done(res => {
                            if (res.error && res.error.messageId) {
                                $('.countdown').stopCount();
                                nts.uk.ui.dialog.alertError({ messageId: res.error.messageId });
                            }
                            if (res.running) {
                                self.processingState(nts.uk.resource.getText('KSU001_216'));
                                self.state('-');
                            }
                            if (res.succeeded) {
                                $('.countdown').stopCount();
                                let arrayItems = [];
                                _.forEach(res.taskDatas, item => {
                                    if (item.key == 'IS_ERROR') {
                                        if (item.valueAsBoolean == false) {
                                            self.processingState(nts.uk.resource.getText('KSU001_215'));
                                            self.state(nts.uk.resource.getText('KSU001_213'));
                                            self.isError(false);
                                        } else {
                                            self.processingState(nts.uk.resource.getText('KSU001_215'));
                                            self.state(nts.uk.resource.getText('KSU001_212'));
                                            $('#error-status').css('display', '');
                                            $('#error-output').css('display', '');
                                            $('#state').css({ 'color': 'red' });
                                            $('#number-error').css({ 'color': 'red' });
                                            self.isError(true);
                                            nts.uk.ui.windows.getSelf().setHeight(730);
                                            nts.uk.ui.windows.getSelf().setWidth(910);
                                        }
                                    } else {
                                        if (item.valueAsString) {
                                            arrayItems.push(item);
                                        }
                                    }
                                });
                                let sortList = _.sortBy(arrayItems, o => { return parseInt(o.key.slice(4)); });
                                let listInforErrors = _.map(sortList,obj2=>{
                                    return new InforError(JSON.parse(obj2.valueAsString));    
                                });
                                self.inforError(listInforErrors);
                                 self.numberError(self.inforError().length + '件');
                            }
                            if (res.cancelled || res.failed) {
                                $('.countdown').stopCount();
                                self.processingState(nts.uk.resource.getText('KSU001_215'));
                                self.state(nts.uk.resource.getText('KSU001_213'));
                            }
                        });
                    })
                    .while(info => info.pending || info.running)
                    .pause(1000));

            }

        }
        interface IError {
            employeeCode: string,
            employeeName: string,
            date: string,
            category: string,
            condition: string,
            message: string,
        }

        export class InforError {
            employeeCode: KnockoutObservable<string>;
            employeeName: KnockoutObservable<string>;
            date: KnockoutObservable<string>;
            category: KnockoutObservable<string>;
            condition: KnockoutObservable<string>;
            message: KnockoutObservable<string>;
            constructor(param: IError) {
                let self = this;
                self.employeeCode = ko.observable(param.employeeCode);
                self.employeeName = ko.observable(param.employeeName);
                self.date = ko.observable(param.date);
                self.category = ko.observable(param.category);
                self.condition = ko.observable(param.condition);
                self.message = ko.observable(param.message);
            }
        }
    }
}