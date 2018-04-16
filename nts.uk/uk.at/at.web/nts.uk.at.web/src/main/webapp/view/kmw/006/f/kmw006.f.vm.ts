module nts.uk.at.view.kmw006.f.viewmodel {
    import block = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    import alertError = nts.uk.ui.dialog.alertError;
    import getShared = nts.uk.ui.windows.getShared;
    import kibanTimer = nts.uk.ui.sharedvm.KibanTimer;

    export class ScreenModel {

        items: KnockoutObservableArray<MonthlyClosureErrorInfor>;
        taskId: KnockoutObservable<string> = ko.observable("");
        startTime: KnockoutObservable<string> = ko.observable("");
        dispStartTime: KnockoutObservable<string>;
        endTime: KnockoutObservable<string> = ko.observable("");
        dispEndTime: KnockoutObservable<string>;
        elapseTime: kibanTimer = new kibanTimer('elapseTime');
        isComplete: KnockoutObservable<boolean> = ko.observable(false);
        totalCount: KnockoutObservable<number> = ko.observable(0);
        processedCount: KnockoutObservable<number> = ko.observable(0);
        dispProcessCount: KnockoutObservable<string>;
        completeStatus: KnockoutObservable<number> = ko.observable(COMPLETE_STATUS.INCOMPLETE);
        params: any;

        constructor() {
            var self = this;
            self.params = getShared("kmw006fParams");
            if (self.params)
                self.totalCount(self.params.listEmployeeId.length);
            else
                self.totalCount(sessionStorage.getItem("MonthlyClosureListEmpId").value.split(',').length);
            self.items = ko.observableArray([]);
            self.initIGrid();
            self.dispProcessCount = ko.computed(() => {
                return self.processedCount() + "/" + self.totalCount() + "人";
            });
            self.dispStartTime = ko.computed(() => {
                if (nts.uk.text.isNullOrEmpty(self.startTime()))
                    return "";
                else
                    return getText("KMW006_49", [moment.utc(self.startTime()).format("YYYY/MM/DD HH:mm:ss")]);
            });
            self.dispEndTime = ko.computed(() => {
                if (nts.uk.text.isNullOrEmpty(self.endTime()))
                    return "";
                else
                    return getText("KMW006_49", [moment.utc(self.endTime()).format("YYYY/MM/DD HH:mm:ss")]);
            });
            $("#fixed-table").ntsFixedTable({ height: 150 });
        }

        startPage(): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred();
            block.invisible();
            service.getMonthlyClosureLog(self.params ? self.params.monthlyClosureUpdateLogId : sessionStorage.getItem("MonthlyClosureUpdateLogId").value).done((result) => {
                self.startTime(result.executionDateTime);
                self.completeStatus(result.completeStatus);
                self.elapseTime.start();
                if (self.params)
                    self.processMonthlyUpdate();
                else {
                    self.taskId(sessionStorage.getItem("MonthlyClosureTaskId").value);
                    self.checkAsyncProcess();
                }
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
                sessionStorage.setItem("MonthlyClosureTaskId", res.id);
                self.checkAsyncProcess();
            });
        }

        private checkAsyncProcess(): void {
            var self = this;
            nts.uk.deferred.repeat(conf => conf
                .task(() => {
                    return nts.uk.request.asyncTask.getInfo(self.taskId()).done(info => {
                        self.processedCount(self.getAsyncData(info.taskDatas, "processed").valueAsNumber);
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

        private getAsyncData(data: Array<any>, key: string): any {
            var result = _.find(data, (item) => {
                return item.key == key;
            });
            return result || { valueAsString: "", valueAsNumber: 0, valueAsBoolean: false };
        }

        private checkResult(taskInfor) {
            let self = this;
            service.getResults(self.params ? self.params.monthlyClosureUpdateLogId : sessionStorage.getItem("MonthlyClosureUpdateLogId").value).done((result) => {
                let listErr: Array<MonthlyClosureErrorInfor> = [];
                for (let i = 0; i < result.listErrorInfor.length; i++) {
                    let item = result.listErrorInfor[i];
                    listErr.push(new MonthlyClosureErrorInfor(i + 1, item.employeeCode, item.employeeName, item.errorMessage, item.atr));
                }
                self.items(listErr);
                $("#single-list").igGrid("option", "dataSource", self.items());
                self.isComplete(true);
                self.endTime(taskInfor.finishedAt);
                self.elapseTime.end();
                self.completeStatus(result.updateLog.completeStatus);
            }).fail((error) => {
                alertError(error);
            });
        }

        private confirmComplete() {
            let self = this;
            block.invisible();
            service.completeConfirm(self.params ? self.params.monthlyClosureUpdateLogId : sessionStorage.getItem("MonthlyClosureUpdateLogId").value).done(() => {
                sessionStorage.removeItem("MonthlyClosureUpdateLogId");
                sessionStorage.removeItem("MonthlyClosureListEmpId");
                sessionStorage.removeItem("MonthlyClosureId");
                sessionStorage.removeItem("MonthlyClosureExecutionDateTime");
                sessionStorage.removeItem("MonthlyClosureTaskId");
                nts.uk.ui.windows.close();
            }).fail((error) => {
                alertError(error);
            }).always(() => {
                block.clear();
            });
        }

        private initIGrid() {
            let self = this;
            $("#single-list").ntsGrid({
                height: '300px',
                dataSource: self.items(),
                primaryKey: 'employeeCode',
                columns: [
                    { headerText: getText('KMW006_13'), key: 'no', dataType: 'number', width: '40px' },
                    { headerText: getText('KMW006_16'), key: 'employeeCode', dataType: 'string', width: '160px' },
                    { headerText: getText('KMW006_17'), key: 'employeeName', dataType: 'string', width: '160px' },
                    { headerText: getText('KMW006_39'), key: 'atr', dataType: 'string', width: '120px' },
                    { headerText: getText('KMW006_40'), key: 'errorMessage', dataType: 'string', width: '300px' }
                ],
                features: [
                    {
                        name: 'Paging',
                        pageSize: 15,
                        currentPageIndex: 0,
                        showPageSizeDropDown: false,
                        pageCountLimit: 20
                    }
                ]
            });
        }

    }

    class MonthlyClosureErrorInfor {
        no: number;
        employeeCode: string;
        employeeName: string;
        errorMessage: string;
        atr: string;

        constructor(no: number, employeeCode: string, employeeName: string, errorMessage: string, atr: number) {
            this.no = no;
            this.employeeCode = employeeCode;
            this.employeeName = employeeName;
            this.errorMessage = errorMessage;
            this.atr = atr == ERROR_ALARM_ATR.ALARM ? getText("Enum_MonthlyClosureUpdate_Alarm") : getText("Enum_MonthlyClosureUpdate_Error");
        }
    }

    export enum COMPLETE_STATUS {
        INCOMPLETE = 0,
        COMPLETE = 1,
        COMPLETE_WITH_ERROR = 2,
        COMPLETE_WITH_ALARM = 3
    }

    enum ERROR_ALARM_ATR {
        ALARM = 0,
        ERROR = 1
    }

}