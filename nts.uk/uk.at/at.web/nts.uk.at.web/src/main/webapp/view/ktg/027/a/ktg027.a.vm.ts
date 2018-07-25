module nts.uk.at.view.ktg027.a.viewmodel {
    import block = nts.uk.ui.block;
    export class ScreenModel {
        /**YM Picker **/
        targetMonth: KnockoutObservable<any>;
        cssRangerYM = ko.observable({});
        /**ComboBox**/
        selectedClosureID: KnockoutObservable<any>;
        inforOvertime: KnockoutObservableArray<InforOvertime>;
        inforOvertimeFooter: KnockoutObservable<InforOvertime>;
        closureResultModel: KnockoutObservableArray<ClosureResultModel> = ko.observableArray([]);
        check: KnockoutObservable<boolean>;
        //color
        backgroundColor: KnockoutObservable<String>;
        color: KnockoutObservable<String>;
        constructor() {
            var self = this;
            var today = moment();
            var month = today.month() + 1;
            if (month < 10) {
                month = '0' + month;
            }
            else {
                month = month;
            }
            var year = today.year();
            var targetMonth = year + "" + month

            self.targetMonth = ko.observable(targetMonth);
            self.selectedClosureID = ko.observable('1');
            var inforOvertime: Array<InforOvertime> = [];
            self.inforOvertimeFooter = ko.observable(new InforOvertime("", null, null, null, null, "", ""));
            self.targetMonth.subscribe((newSelect) => {
                self.clickExtractionBtn();

            });
            self.inforOvertime = ko.observableArray([]);
            self.backgroundColor = ko.observable('');
            self.color = ko.observable('');
            self.check = ko.observable(false);
        }

        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            block.grayout();
            service.getOvertimeHours(self.targetMonth()).done((data) => {
                self.closureResultModel(data.listclosureID);
                var inforOvertime = [];
                var inforOvertimeFooter = [];
                let backgroundColor = '#ffffff';
                let color = '';
                let total1 = 0;
                let total2 = 0;
                let total3 = 0;

                _.forEach(data.overtimeHours.overtimeLaborInfor, function(e) {
                    let timeLimit = e.afterAppReflect.limitErrorTime;
                    let actualTime = e.confirmed.agreementTime;
                    let applicationTime = e.afterAppReflect.agreementTime - actualTime;
                    let totalTime = e.afterAppReflect.agreementTime;
                    let status = e.afterAppReflect.status;
                    if (status == 2 || status == 5) { backgroundColor = '#F6F636'; color = '#ff0000'; }
                    else if (status == 1 || status == 4) { backgroundColor = '#FD4D4D'; color = '#ffffff'; }
                    else if (status == 3) { backgroundColor = '#eb9152'; }

                    if (!nts.uk.text.isNullOrEmpty(e.afterAppReflect.exceptionLimitErrorTime) || e.afterAppReflect.exceptionLimitErrorTime == "") {
                        timeLimit = e.afterAppReflect.exceptionLimitErrorTime;
                    }
                    inforOvertime.push(new InforOvertime(e.employeeCD + " " + e.empName, timeLimit, actualTime, applicationTime, totalTime, backgroundColor, color));

                    total1 += actualTime;
                    total2 += applicationTime;
                    total3 += totalTime;
                });
                self.inforOvertimeFooter(new InforOvertime(nts.uk.resource.getText("KTG027_10"), null, total1, total2, total3, '', ''));
                inforOvertime.push(self.inforOvertimeFooter())
                self.inforOvertime(inforOvertime);
                if (!nts.uk.text.isNullOrEmpty(data.overtimeHours.errorMessage))
                    var MsgID = data.overtimeHours.errorMessage;
                nts.uk.ui.dialog.alertError({ messageId: MsgID, messageParams: [nts.uk.resource.getText("MsgID")] })

                $.each(data.overtimeHours.overtimeLaborInfor, function(item) {
                    if (item.errorMessage != null) {
                        self.check(true);
                        return false;
                    }
                });

                dfd.resolve();
                block.clear();
            });

            // Init Fixed Table
            $("#fixed-table").ntsFixedTable({ height: 300, width: 600 });
            return dfd.promise();
        }

        clickExtractionBtn() {
            var self = this;
            var dfd = $.Deferred();
            block.grayout();
            service.buttonPressingProcess(self.targetMonth(), self.selectedClosureID()).done((data) => {
              
                var inforOvertime = [];
                var inforOvertime = [];
                var inforOvertimeFooter = [];
                let backgroundColor = '#ffffff';
                let color = '';
                let total1 = 0;
                let total2 = 0;
                let total3 = 0;
                _.forEach(data.overtimeLaborInfor, function(e) {
                    let timeLimit = e.afterAppReflect.limitErrorTime;
                    let actualTime = e.confirmed.agreementTime;
                    let applicationTime = e.afterAppReflect.agreementTime - actualTime;
                    let totalTime = e.afterAppReflect.agreementTime;
                    let status = e.afterAppReflect.status;
                    if (status == 2 || status == 5) { backgroundColor = '#F6F636'; color = '#ff0000'; }
                    else if (status == 1 || status == 4) { backgroundColor = '#FD4D4D'; color = '#ffffff'; }
                    else if (status == 3) { backgroundColor = '#eb9152'; }

                    if (!nts.uk.text.isNullOrEmpty(e.afterAppReflect.exceptionLimitErrorTime) || e.afterAppReflect.exceptionLimitErrorTime == "") {
                        timeLimit = e.afterAppReflect.exceptionLimitErrorTime;
                    }
                    inforOvertime.push(new InforOvertime(e.employeeCD + " " + e.empName, timeLimit, actualTime, applicationTime, totalTime, backgroundColor, color));

                    total1 += actualTime;
                    total2 += applicationTime;
                    total3 += totalTime;
                });
                self.inforOvertimeFooter(new InforOvertime(nts.uk.resource.getText("KTG027_10"), null, total1, total2, total3, '', ''));
                inforOvertime.push(self.inforOvertimeFooter())
                self.inforOvertime(inforOvertime);
                if (!nts.uk.text.isNullOrEmpty(data.errorMessage))
                    var MsgID = data.errorMessage;
                nts.uk.ui.dialog.alertError({ messageId: MsgID, messageParams: [nts.uk.resource.getText("MsgID")] });
            }).always(() => {
                block.clear();
            });
        }
        printData(): void {
            block.invisible();
            let self = this;
            service.saveAsCsv(self.inforOvertime());
            block.clear();
        }

    }
    export class ClosureResultModel {
        closureId: string;
        closureName: string;

        constructor(closureId: string, closureName: string) {
            this.closureId = closureId;
            this.closureName = closureName;
        }
    }
    //define
    export class InforOvertime {
        employee: string;
        timeLimit: number;
        actualTime: number;
        applicationTime: number;
        totalTime: number;
        backgroundColor: string;
        color: string;
        constructor(employee: string, timeLimit: number, actualTime: number, applicationTime: number, totalTime: number, backgroundColor: string, color: string) {
            this.employee = employee;
            this.timeLimit = timeLimit;
            this.actualTime = actualTime;
            this.applicationTime = applicationTime;
            this.totalTime = totalTime;
            this.backgroundColor = backgroundColor;
            this.color = color;
        }
    }
}
