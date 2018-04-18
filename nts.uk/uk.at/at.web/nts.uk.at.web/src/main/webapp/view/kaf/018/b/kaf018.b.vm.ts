module nts.uk.at.view.kaf018.b.viewmodel {
    import text = nts.uk.resource.getText;

    export class ScreenModel {
        tempData: Array<model.ConfirmationStatus> = [
                new model.ConfirmationStatus("01", "Anh1", 1, 12, true, null, 8, 3, false),
                new model.ConfirmationStatus("01", "Anh2", 2, 23, false, 4, 5, 6, true),
                new model.ConfirmationStatus("01", "Anh3", 3, 23, true, 5, 6, 8, false),
                new model.ConfirmationStatus("01", "Anh4", 4, 23, true, null,null, 4, true),
                new model.ConfirmationStatus("01", "Anh5", 4, 23, true, 1, null, 3, true),
            ];
        confirmStatus: KnockoutObservable<model.ConfirmationStatus> = ko.observable(new model.ConfirmationStatus(null, null, null, false, null, null,null, false));
        enable: KnockoutObservable<boolean> = ko.observable(true);
        listWorkplaceId: KnockoutObservableArray<string> =  ko.observableArray([]);
        closureId: KnockoutObservable<number> = ko.observable(0);
        closureName: KnockoutObservable<string> = ko.observable('');
        processingYm: KnockoutObservable<string> = ko.observable('');
        startDate: KnockoutObservable<string> = ko.observable('');
        endDate: KnockoutObservable<string> = ko.observable('');
        isDailyComfirm: KnockoutObservable<boolean> = ko.observable(false);
        listEmployeeCode: KnockoutObservableArray<any> = ko.observableArray([]);
        constructor() {
            var self = this;
            $("#fixed-table").ntsFixedTable({ width: 1000, height: 161 });         
        }
        
        startPage(): JQueryPromise<any> {
            var self = this;

            var dfd = $.Deferred();
            let params: model.IParam = nts.uk.ui.windows.getShared('KAF018BInput');
            appAproveByWorkSpace = params;
            self.workplaceId = params.workplaceId;
            self.closureId = params.closureId;
            self.closureName = params.closureName;
            self.processingYm = params.processingYm;
            self.startDate = nts.uk.time.formatDate(new Date(params.startDate), 'yyyy/MM/dd');
            self.endDate = nts.uk.time.formatDate(new Date(params.endDate), 'yyyy/MM/dd');
            self.listWorkplaceId = params.listWorkplaceId;
            self.listEmployeeCode = params.listEmployeeCode;
            self.isDailyComfirm = params.isConfirmData;

            let obj = {
                startDate: nts.uk.time.formatDate(new Date(self.startDate), 'yyyy/MM/dd'),
                endDate: nts.uk.time.formatDate(new Date(self.endDate), 'yyyy/MM/dd'),
                isConfirmData: self.isDailyComfirm,
                listWorkplaceId: self.listWorkplaceId,
                listEmpCd: self.listEmployeeCode
                };
            service.getAppSttByWorkpace(obj).done(function(data: any) {
                console.log(data);
                dfd.resolve();
            });
            return dfd.promise();
        }


//        private sendMails(value: model.ConfirmationStatus) {
//            var self = this;
//            let listWsp: [];
//            _.forEach(self.tempData, function(item) {
//                listWsp.push([workPlaceId: item.workPlaceId, isChecked: item.isSendMail()]);   
//            });
//        }

        private getRecord1(value1: number, value2: number) : string {
            return value2 +"/"+ (value1 ? value1 + "件" : 0);
        }
        
        private getRecord(value?: number) {
            return value ? value + "件" : "";
        }
        
        private getTargetDate(){
            var self = this;
            return self.processingYm +"("+ self.startDate +"～"+ self.endDate+")";
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
            workPlaceId: string;
            workPlaceName: string;
            totalNotReflected: number;
            notReflected: number;
            unapproved: number;
            approved: number;
            denail: number;
            isSendMail: KnockoutObservable<boolean>;
            enable:  KnockoutObservable<boolean>;

            constructor(workPlaceId: string, workSpaceName: string, notReflected?: number, totalNotReflected?: number, isSendMail: boolean, unapproved?: number, approved?: number, denail?: number, enable: boolean) {
                this.workPlaceId = workPlaceId;
                this.workSpaceName = workSpaceName;
                this.notReflected = notReflected? notReflected: null;
                this.totalNotReflected = totalNotReflected?  totalNotReflected : null;
                this.isSendMail = ko.observable(isSendMail);
                this.unapproved = unapproved ? unapproved : null;
                this.approved = approved ? approved : null;
                this.denail = denail ? denail : null;
                this.enable = ko.observable(enable);
            }
        }

    }
}