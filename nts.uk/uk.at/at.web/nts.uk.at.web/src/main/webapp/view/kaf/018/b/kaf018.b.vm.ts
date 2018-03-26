module nts.uk.at.view.kaf018.b.viewmodel {
    import text = nts.uk.resource.getText;

    export class ScreenModel {
        tempData: Array<model.ConfirmationStatus>;

        constructor() {
            var self = this;
            $("#fixed-table").ntsFixedTable({ width: 1000, height: 161 });
            self.tempData = [
                new model.ConfirmationStatus("01", 1,12, true, null, 8, 3),
                new model.ConfirmationStatus("01", 2, 23, false, 4, 5, 6),
                new model.ConfirmationStatus("01", 3, 23, true, 5, 6, 8),
                new model.ConfirmationStatus("01", 4, 23, true, 1,null, 4),
                new model.ConfirmationStatus("01", 4, 23, true, 1, null, 3),
            ];
        }

        sendMails() {
        }

        private getRecord1(value1: number, value2: number) : string {
            return value2 +"/"+ (value1 ? value1 + "件" : 0);
        }
        
        private getRecord(value?: number) {
            return value ? value + "件" : "";
        }
    }

    export module model {
        export class ConfirmationStatus {
            workSpaceName: string;
            totalNotReflected: number;
            notReflected: number;
            unapproved: number;
            approved: number;
            denail: number;
            sendMail: KnockoutObservable<boolean>;

            constructor(workSpaceName: string, notReflected?: number, totalNotReflected?: number, sendMail: boolean, unapproved?: number, approved?: number, denail?: number) {
                this.workSpaceName = workSpaceName;
                this.notReflected = notReflected? notReflected: null;
                this.totalNotReflected = totalNotReflected?  totalNotReflected : null;
                this.sendMail = ko.observable(sendMail);
                this.unapproved = unapproved ? unapproved : null;
                this.approved = approved ? approved : null;
                this.denail = denail ? denail : null;
            }
        }

    }
}