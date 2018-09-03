module nts.uk.at.view.kmw006.f.viewmodel {
    import block = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    import alertError = nts.uk.ui.dialog.alertError;
    import getShared = nts.uk.ui.windows.getShared;
    import setShared = nts.uk.ui.windows.setShared;
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
                self.totalCount(localStorage.getItem("MonthlyClosureListEmpId") ? localStorage.getItem("MonthlyClosureListEmpId").split(',').length : 0);
            self.items = ko.observableArray([]);
            self.initIGrid();
            self.dispProcessCount = ko.computed(() => {
                return getText("KMW006_62", [self.processedCount(), self.totalCount()]);
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
            service.getMonthlyClosureLog(self.params ? self.params.monthlyClosureUpdateLogId : localStorage.getItem("MonthlyClosureUpdateLogId")).done((result) => {
                self.startTime(result.executionDateTime);
                self.completeStatus(result.completeStatus);
                self.elapseTime.start();
                if (self.params.check != 2 || !self.params.check)
                    self.processMonthlyUpdate();
                else {
                    self.taskId(localStorage.getItem("MonthlyClosureTaskId"));
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
//            var command = {
//                    closureDay : self.params.closureDay,
//                    closureId: self.params.closureId,
//                    currentMonth: self.params.currentMonth,
//                    endDT: self.params.endDT,
//                    isLastDayOfMonth: self.params.isLastDayOfMonth,
//                    listEmployeeId: self.params.listEmployeeId,
//                    monthlyClosureUpdateLogId: self.params.monthlyClosureUpdateLogId,
//                    periodEnd: self.params.periodEnd,
//                    periodStart: moment(self.params.periodStart).format("yyyy-MM-dd hh:mm:ss.SSS"),
//                    startDT: self.params.startDT
//                }
            service.executeMonthlyClosure(command).done(res => {
                self.taskId(res.id);
                localStorage.setItem("MonthlyClosureTaskId", res.id);
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
                            if (info.status == "COMPLETED" || localStorage.getItem("MCUdpStatus") == "COMPLETED") {
                                localStorage.setItem("MCUdpStatus", info.status);
                                self.processedCount(self.totalCount());
                            }
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
            block.invisible();
            service.getResults(self.params ? self.params.monthlyClosureUpdateLogId : localStorage.getItem("MonthlyClosureUpdateLogId")).done((result) => {
                let listErr: Array<MonthlyClosureErrorInfor> = [];
                for (let i = 0; i < result.listErrorInfor.length; i++) {
                    let item = result.listErrorInfor[i];
                    listErr.push(new MonthlyClosureErrorInfor(i + 1, item.employeeCode, item.employeeName, item.errorMessage, item.atr));
                }
                self.items(listErr);
                $("#single-list").igGrid("option", "dataSource", self.items());
                self.isComplete(true);
                $("#F3_2").focus();
                self.endTime(taskInfor.finishedAt);
                localStorage.setItem("MonthlyClosureExecutionEndDate", self.endTime());
                self.elapseTime.end();
                self.completeStatus(result.updateLog.completeStatus);
            }).fail((error) => {
                alertError(error);
            }).always(() => {
                block.clear();
            });
        }

        private confirmComplete() {
            let self = this;
            block.invisible();
            service.completeConfirm(self.params ? self.params.monthlyClosureUpdateLogId : localStorage.getItem("MonthlyClosureUpdateLogId")).done(() => {
                localStorage.removeItem("MonthlyClosureUpdateLogId");
                localStorage.removeItem("MonthlyClosureListEmpId");
                localStorage.removeItem("MonthlyClosureId");
                localStorage.removeItem("MonthlyClosureExecutionDateTime");
                localStorage.removeItem("MonthlyClosureTaskId");
//                localStorage.removeItem("MonthlyClosureStartDT");
//                localStorage.removeItem("MonthlyClosureEndDT");
//                localStorage.removeItem("MonthlyClosureCurrentMonth");
//                localStorage.removeItem("MonthlyClosureDay");
//                localStorage.removeItem("MonthlyClosureDayOfMonth");
//                localStorage.removeItem("MonthlyClosurePeriodStart");
//                localStorage.removeItem("MonthlyClosurePeriodEnd");
                setShared("kmw006fConfirm", {check: true});
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
                height: '429px',
                dataSource: self.items(),
                primaryKey: 'employeeCode',
                columns: [
                    { headerText: getText('KMW006_13'), key: 'no', dataType: 'number', width: '40px' },
                    { headerText: getText('KMW006_16'), key: 'employeeCode', dataType: 'string', width: '160px' },
                    { headerText: getText('KMW006_17'), key: 'employeeName', dataType: 'string', width: '160px' },
                    { headerText: getText('KMW006_39'), key: 'atr', dataType: 'string', width: '120px' },
                    { headerText: getText('KMW006_40'), key: 'errorMessage', dataType: 'string', width: '400px' }
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