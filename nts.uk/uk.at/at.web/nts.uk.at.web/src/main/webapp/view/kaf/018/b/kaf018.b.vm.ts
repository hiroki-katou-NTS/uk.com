module nts.uk.at.view.kaf018.b.viewmodel {
    import text = nts.uk.resource.getText;

    export class ScreenModel {
        tempData: Array<model.ConfirmationStatus> = [
                new model.ConfirmationStatus("01", 1,12, true, null, 8, 3),
                new model.ConfirmationStatus("01", 2, 23, false, 4, 5, 6),
                new model.ConfirmationStatus("01", 3, 23, true, 5, 6, 8),
                new model.ConfirmationStatus("01", 4, 23, true, null,null, 4),
                new model.ConfirmationStatus("01", 4, 23, true, 1, null, 3),
            ];
        confirmStatus: KnockoutObservable<model.ConfirmationStatus> = ko.observable(new model.ConfirmationStatus(null, null,null, null,null, null,null));
        enable: KnockoutObservable<boolean> = ko.observable(false);
        listWorkplaceId: KnockoutObservableArray<string> =  ko.observableArray([]);
        closureId: KnockoutObservable<number> = ko.observable(0);
        closureName: KnockoutObservable<string> = ko.observable('');
        closureDate: KnockoutObservable<number> = ko.observable(0);
        startDate: KnockoutObservable<string> = ko.observable('');
        endDate: KnockoutObservable<string> = ko.observable('');
        isDailyComfirm: KnockoutObservable<boolean> = ko.observable(false);
        listEmployeeCode: KnockoutObservableArray<any> = ko.observableArray([]);
        constructor() {
            var self = this;
            $("#fixed-table").ntsFixedTable({ width: 1000, height: 161 });
            if(self.confirmStatus().unapproved != null){
                self.enable(true);    
            }
            
        }
        
        startPage(): JQueryPromise<any> {
            var self = this;

            var dfd = $.Deferred();
            let params: model.IParam = nts.uk.ui.windows.getShared('KAF018BInput');
            appAproveByWorkSpace = params;
            self.workplaceId = params.workplaceId;
            self.closureId = params.closureId;
            self.closureName = params.closureName;
            self.closureDate = params.closureDate;
            self.startDate = nts.uk.time.formatDate(new Date(params.startDate), 'yyyy/MM/dd');
            self.endDate = nts.uk.time.formatDate(new Date(params.endDate), 'yyyy/MM/dd');
            self.listWorkplaceId = params.listWorkplaceId;
            self.listEmployeeCode = params.listEmployeeCode;
            dfd.resolve();
            return dfd.promise();
        }


        sendMails() {
        }

        private getRecord1(value1: number, value2: number) : string {
            return value2 +"/"+ (value1 ? value1 + "件" : 0);
        }
        
        private getRecord(value?: number) {
            return value ? value + "件" : "";
        }
        
        private getTargetDate(){
            var self = this;
            return self.closureDate +"("+ self.startDate +"～"+ self.endDate+")";
        }
    }

    export module model {
        
        export class IParam {
           closureId: number;
                       /** The closure name. */
            closureName: string;
            /** The start date. */
           startDate: string;
            
            /** The end date. */
            endDate: string;
             
            /** The closure date. */
            //処理年月
            closureDate: number; 
            
            isConfirmData: boolean;
            
            listEmployeeCode: Array<any>;
            
            listWorkplaceId: Array<string>;
        }
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