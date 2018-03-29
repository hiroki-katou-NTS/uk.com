module nts.uk.at.view.kaf018.e.viewmodel {
    import text = nts.uk.resource.getText;
    import getShared = nts.uk.ui.windows.setShared;
    export class ScreenModel {
        tempData: Array<model.ConfirmationStatus>;

        constructor() {
            var self = this;
            $("#fixed-table").ntsFixedTable({ width: 1000, height: 186 });

            self.tempData = [
                new model.ConfirmationStatus("01", "123", true, 1, null, 6, 8, 3),
                new model.ConfirmationStatus("01", "sda sd", false, 4, 5, 6, 8),
                new model.ConfirmationStatus("01", "1231 2s ", true, 6, null, 8),
                new model.ConfirmationStatus("01", "adas  12", true, 1, 4, 8),
                new model.ConfirmationStatus("01", "123", true, 1, null, 6, 8, 3),
                new model.ConfirmationStatus("01", "sda sd", false, 4, 5, 6, 8),
                new model.ConfirmationStatus("01", "1231 2s ", true, 6, null, 8),
                new model.ConfirmationStatus("01", "adas  12", true, 1, 4, 8)
            ];
        }

        /**
         * 起動する
         */
        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            let params: IParam = getShared("CPS001A_PARAMS") || { employeeId: undefined };
            service.getWorkplaceExperienceStartUp(params).done(function() {
            
            })
            dfd.resolve();
            return dfd.promise();
        }

        sendMail() {
        }

        private getRecord(value?: number) {
            return value ? value + "件" : "";
        }
    }

    export module model {
        export class ConfirmationStatus {
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