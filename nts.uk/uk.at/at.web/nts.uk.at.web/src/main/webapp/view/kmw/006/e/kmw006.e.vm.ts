module nts.uk.at.view.kmw006.e.viewmodel {
    import block = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    import alertError = nts.uk.ui.dialog.alertError;
    import getShared = nts.uk.ui.windows.getShared;

    export class ScreenModel {

        items: KnockoutObservableArray<MonthlyClosureErrorInfor>;
        closureIdName: KnockoutObservable<string> = ko.observable("");
        dispTargetYm: KnockoutObservable<string> = ko.observable("");
        executionDt: KnockoutObservable<string> = ko.observable("");
        currentCode: KnockoutObservable<string> = ko.observable("");
        params: any;

        constructor() {
            var self = this;
            self.params = getShared("Kmw006eParams");
            self.closureIdName(self.params.closure);
            self.dispTargetYm(self.params.targetYm);
            self.executionDt(self.params.executionDt);
            self.items = ko.observableArray([]);
        }

        startPage(): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred();
            block.invisible();
            service.getResults(self.params.logId, self.params.listEmpId).done((result) => {
                let listErr: Array<MonthlyClosureErrorInfor> = [];
                for (let i = 0; i < result.length; i++) {
                    let item = result[i];
                    listErr.push(new MonthlyClosureErrorInfor(i + 1, item.employeeCode, item.employeeName, item.errorMessage, item.atr));
                }
                self.items(listErr);
                dfd.resolve();
            }).fail((error) => {
                dfd.reject();
                alertError(error);
            }).always(() => {
                block.clear();
            });
            return dfd.promise();
        }

        private exportCsv(): void {
            let self = this;
            block.invisible();
            service.exportCsv({executionDt: self.executionDt(), data: ko.toJS(self.items)}).always(() => {
                block.clear();
            });
        }

        private closeDialog() {
            nts.uk.ui.windows.close();
        }

    }

    class MonthlyClosureErrorInfor {
        no: number;
        employeeCode: string;
        employeeName: string;
        errorMessage: string;
        atr: number;
        dispAtr: string;

        constructor(no: number, employeeCode: string, employeeName: string, errorMessage: string, atr: number) {
            this.no = no;
            this.employeeCode = employeeCode;
            this.employeeName = employeeName;
            this.errorMessage = errorMessage;
            this.atr = atr;
            this.dispAtr = atr == ERROR_ALARM_ATR.ALARM ? getText("Enum_MonthlyClosureUpdate_Alarm") : getText("Enum_MonthlyClosureUpdate_Error");
        }
    }

    enum ERROR_ALARM_ATR {
        ALARM = 0,
        ERROR = 1
    }

}