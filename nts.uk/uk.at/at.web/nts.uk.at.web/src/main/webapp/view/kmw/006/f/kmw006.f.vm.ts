module nts.uk.at.view.kmw006.f.viewmodel {
    import block = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    import confirm = nts.uk.ui.dialog.confirm;
    import alertError = nts.uk.ui.dialog.alertError;
    import info = nts.uk.ui.dialog.info;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import kibanTimer = nts.uk.ui.sharedvm.KibanTimer;

    export class ScreenModel {

        items: KnockoutObservableArray<MonthlyClosureErrorInfor>;
        taskId: KnockoutObservable<string> = ko.observable("");
        startTime: KnockoutObservable<string> = ko.observable("");
        endTime: KnockoutObservable<string> = ko.observable("");
        elapseTime: kibanTimer = new kibanTimer('elapseTime');
        isComplete: KnockoutObservable<boolean> = ko.observable(false);
        totalCount: KnockoutObservable<number> = ko.observable(0);
        processedCount: KnockoutObservable<number> = ko.observable(0);
        completeStatus: KnockoutObservable<number> = ko.observable(COMPLETE_STATUS.INCOMPLETE);
        params: any;

        constructor() {
            var self = this;
            self.params = getShared("kmw006fParams");
            self.totalCount(self.params.listEmployeeId.length);
            this.items = ko.observableArray([]);

            $("#single-list").ntsGrid({
                height: '300px',
                dataSource: this.items(),
                primaryKey: 'code',
                columns: [
                    { headerText: getText('KMW006_13'), key: 'no', dataType: 'number', width: '60px' },
                    { headerText: getText('KMW006_16'), key: 'employeeCode', dataType: 'string', width: '160px' },
                    { headerText: getText('KMW006_17'), key: 'employeeName', dataType: 'string', width: '160px' },
                    { headerText: getText('KMW006_39'), key: "atr", dataType: "number", width: '160px' },
                    { headerText: getText('KMW006_40'), key: 'errorMessage', dataType: 'string', width: '160px' }
                ],
                features: [
                    {
                        name: 'Paging',
                        pageSize: 15,
                        currentPageIndex: 0,
                        showPageSizeDropDown: false
                    }
                ]
            });
        }

        startPage(): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred();
            block.invisible();
            service.getMonthlyClosureLog(self.params.monthlyClosureUpdateLogId).done((result) => {
                self.startTime(result.executionDateTime);
                self.completeStatus(result.completeStatus);
                self.elapseTime.start();
                self.processMonthlyUpdate();
                dfd.resolve();
            }).fail((error) => {
                dfd.reject();
                alertError(error);
            }).always(() => {
                block.clear();
            });
            return dfd.promise();
        }

        private processMonthlyUpdate(): void {
            var self = this;
            var command = self.params;
            service.executeMonthlyClosure(command).done(res => {
                self.taskId(res.id);
                self.checkAsyncProcess();
            });
        }

        private checkAsyncProcess(): void {
            var self = this;
            nts.uk.deferred.repeat(conf => conf
                .task(() => {
                    return nts.uk.request.asyncTask.getInfo(self.taskId()).done(info => {
                        if (!info.pending && !info.running) {
                            self.checkResult(info);
                            if (info.error) {
                                alertError(info.error);
                                self.completeStatus(COMPLETE_STATUS.COMPLETE_WITH_ERROR);
                            }
                        }
                    });
                })
                .while(info => info.pending || info.running)
                .pause(1000)
            );
        }

        private checkResult(taskInfor) {
            let self = this;
            service.getResults(self.params.monthlyClosureUpdateLogId).done((result) => {
                for (let i = 0; i < result.listErrorInfor.length; i++) {
                    let item = result.listErrorInfor[i];
                    this.items.push(new MonthlyClosureErrorInfor(i+1, item.employeeCode, item.employeeName, item.errorMessage, item.atr));
                }
                self.isComplete(true);
                // Get EndTime from server, fallback to client
                self.endTime(taskInfor.finishedAt);
                // End count time
                self.elapseTime.end();
                // display status
                self.completeStatus(result.updateLog.completeStatus);
            }).fail((error) => {
                alertError(error);
            });
        }

        private confirmComplete() {
            let self = this;
            block.invisible();
            service.completeConfirm(self.params.monthlyClosureUpdateLogId).done(() => {
                nts.uk.ui.windows.close();
            }).fail((error) => {
                alertError(error);
            }).always(() => {
                block.clear();
            });
        }

    }

    class MonthlyClosureErrorInfor {
        no: number;
        employeeCode: string;
        employeeName: string;
        errorMessage: string;
        atr: number;

        constructor(no: number, employeeCode: string, employeeName: string, errorMessage: string, atr: number) {
            this.no = no;
            this.employeeCode = employeeCode;
            this.employeeName = employeeName;
            this.errorMessage = errorMessage;
            this.atr = atr;
        }
    }

    export enum COMPLETE_STATUS {
        INCOMPLETE = 0,
        COMPLETE = 1,
        COMPLETE_WITH_ERROR = 2,
        COMPLETE_WITH_ALARM = 3
    }

    enum EXECUTE_STATUS {
        RUNNING = 0,
        COMPLETED_NOT_CONFIRMED = 1,
        COMPLETED_CONFIRMED = 2
    }

}