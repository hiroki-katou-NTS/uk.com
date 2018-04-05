module nts.uk.at.view.kaf018.e.viewmodel {
    import text = nts.uk.resource.getText;
    import getShared = nts.uk.ui.windows.getShared;
    import formatDate = nts.uk.time.formatDate;

    export class ScreenModel {
        listWkpStatusConfirm: Array<model.ApprovalStatusActivity>;

        closureId: string;
        closureName: string;
        processingYm: string;
        startDate: string;
        endDate: string;
        isConfirmData: boolean
        listWorkplaceId: Array<string>;
        listEmpCd: Array<string>;

        constructor() {
            var self = this;
            $("#fixed-table").ntsFixedTable({ width: 1000, height: 186 });
        }

        /**
         * 起動する
         */
        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            let params = getShared("KAF018E_PARAMS");
            if (params) {
                self.closureId = params.closureId;
                self.closureName = params.closureName;
                self.processingYm = params.processingYm;
                self.startDate = formatDate(new Date(params.startDate), 'yyyy/MM/dd');
                self.endDate = formatDate(new Date(params.endDate), 'yyyy/MM/dd');
                self.isConfirmData = params.isDailyComfirm;
                self.listWorkplaceId = params.listWorkplaceId;
                self.listEmpCd = params.listEmployeeCode;

                self.listWkpStatusConfirm = [
                    new model.ApprovalStatusActivity("01", "123", true, 1, null, 6, 8, 3),
                    new model.ApprovalStatusActivity("01", "sda sd", false, 4, 5, 6, 8),
                    new model.ApprovalStatusActivity("01", "1231 2s ", true, 6, null, 8),
                    new model.ApprovalStatusActivity("01", "adas  12", true, 1, 4, 8),
                    new model.ApprovalStatusActivity("01", "123", true, 1, null, 6, 8, 3),
                    new model.ApprovalStatusActivity("01", "sda sd", false, 4, 5, 6, 8),
                    new model.ApprovalStatusActivity("01", "1231 2s ", true, 6, null, 8),
                    new model.ApprovalStatusActivity("01", "adas  12", true, 1, 4, 8)
                ];
                let obj = {
                    startDate: self.startDate,
                    endDate: self.endDate,
                    isConfirmData: self.isConfirmData,
                    listWorkplaceId: self.listWorkplaceId,
                   // listEmpCd: self.listEmpCd
                };
                service.getStatusActivity(obj).done(function(data: any) {
                    console.log(data);
                    dfd.resolve();
                })
            }
            else {
                dfd.reject();
            }
            return dfd.promise();
        }

        sendMail() {
        }

        private getRecord(value?: number) {
            return value ? value + "件" : "";
        }
    }

    export module model {
        export class ApprovalStatusActivity {
            code: string;
            name: string;
            monthConfirm: number;
            dayBossUnconfirm: number;
            dayBossConfirm: number;
            dayPrincipalUnconfirm: number;
            dayPrincipalConfirm: number;
            check: KnockoutObservable<boolean>;

            constructor(code: string, name: string, check: boolean, monthConfirm?: number, dayBossUnconfirm?: number, dayBossConfirm?: number, dayPrincipalUnconfirm?: number, dayPrincipalConfirm?: number) {
                this.code = code;
                this.name = name;
                this.check = ko.observable(check);
                this.monthConfirm = monthConfirm ? monthConfirm : null;
                this.dayBossUnconfirm = dayBossUnconfirm ? dayBossUnconfirm : null;
                this.dayBossConfirm = dayBossConfirm ? dayBossConfirm : null;
                this.dayPrincipalUnconfirm = dayPrincipalUnconfirm ? dayPrincipalUnconfirm : null;
                this.dayPrincipalConfirm = dayPrincipalConfirm ? dayPrincipalConfirm : null;
            }
        }

    }
}