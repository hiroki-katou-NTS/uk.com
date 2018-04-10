module nts.uk.at.view.kaf018.b.viewmodel {
    import text = nts.uk.resource.getText;
    import getShared = nts.uk.ui.windows.getShared;
    import formatDate = nts.uk.time.formatDate;
    import info = nts.uk.ui.dialog.info;
    import error = nts.uk.ui.dialog.alertError;
    import confirm = nts.uk.ui.dialog.confirm;
    import block = nts.uk.ui.block;
    
    export class ScreenModel {
        tempData: Array<model.ConfirmationStatus> = [];
        enable: KnockoutObservable<boolean> = ko.observable(true);
        listWorkplaceId: KnockoutObservableArray<string> =  ko.observableArray([]);
        closureId: KnockoutObservable<number> = ko.observable(0);
        closureName: KnockoutObservable<string> = ko.observable('');
        processingYm: KnockoutObservable<string> = ko.observable('');
        startDate: KnockoutObservable<string> = ko.observable('');
        endDate: KnockoutObservable<string> = ko.observable('');
        isDailyComfirm: KnockoutObservable<boolean> = ko.observable(false);
        listEmployeeCode: KnockoutObservableArray<any> = ko.observableArray([]);
        listWorkplace: KnockoutObservableArray<model.WorkplaceInfor> =  ko.observableArray([]);
        constructor() {
            var self = this;
            $("#fixed-table").ntsFixedTable({ width: 1000, height: 161 });         
        }
        
        startPage(): JQueryPromise<any> {
            var self = this;

            var dfd = $.Deferred();
            let params: model.IParam = nts.uk.ui.windows.getShared('KAF018BInput');
            self.closureId = params.closureId;
            self.closureName = params.closureName;
            self.processingYm = params.processingYm;
            self.startDate = nts.uk.time.formatDate(new Date(params.startDate), 'yyyy/MM/dd');
            self.endDate = nts.uk.time.formatDate(new Date(params.endDate), 'yyyy/MM/dd');
            self.listEmployeeCode = params.listEmployeeCode;
            self.isDailyComfirm = params.isConfirmData;
            self.listWorkplace = params.listWorkplace;
            let obj = {
                startDate: nts.uk.time.formatDate(new Date(self.startDate), 'yyyy/MM/dd'),
                endDate: nts.uk.time.formatDate(new Date(self.endDate), 'yyyy/MM/dd'),
                isConfirmData: self.isDailyComfirm,
                listWorkplace: self.listWorkplace,
                listEmpCd: self.listEmployeeCode
            };
            service.getAppSttByWorkpace(obj).done(function(data: any) {
                console.log(data);
                _.forEach(data, function(item) {
                    self.tempData.push(new model.ConfirmationStatus(item.workplaceId, item.workplaceName , item.enabled, item.checked, item.numOfApp, item.approvedNumOfCase, item.numOfUnreflected, item.numOfUnapproval, item.numOfDenials));
                })
                console.log(self.tempData);
                dfd.resolve();
            });
            return dfd.promise();
        }

        private sendMails() {
            var self = this;
            block.invisible();
            let confirmStatus = [];
            _.forEach(self.tempData, function(item) {
                if (item.isChecked()) {
                    confirmStatus.push({ workplaceId: item.workplaceId, isChecked: item.isChecked });
                }
            });
            service.getCheckSendMail(confirmStatus).done(function() {
                confirm({ messageId: "Msg_795" }).ifYes(() => {
                    block.invisible();
                    let obj = {
                        listWkp: confirmStatus,
                        startDate: self.startDate,
                        endDate: self.endDate,
                        listEmpCd: self.listEmployeeCode
                    };
                    service.exeSendUnconfirmedMail(obj).done(function(result: any) {
                        if (result.ok) {
                            info({ messageId: "Msg_792" });
                        }
                        else {
                            error({ messageId: "Msg_793" });
                        }
                    }).fail(function(err) {
                        error({ messageId: err.messageId });
                    }).always(function() {
                        block.clear();
                    });
                })
            }).fail(function(err) {
                error({ messageId: err.messageId });
            }).always(function() {
                block.clear();
            });
        }

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
        
        gotoC(index) {
            var self = this;
            
            let params = {
                closureId: self.closureId,
                closureName: self.closureName,
                processingYm: self.processingYm,
                startDate: self.startDate,
                endDate: self.endDate,
                listWorkplace: self.listWorkplace,
                selectedWplIndex: index(),
                listEmployeeCode: self.listEmployeeCode,
            };
            nts.uk.request.jump('/view/kaf/018/c/index.xhtml', params);
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
            
            processingYm: string;
            
            isConfirmData: boolean;
            
            listEmployeeCode: Array<any>;
            
            listWorkplace: Array<WorkplaceInfor>;
        }

        export class WorkplaceInfor {
            // 職場ID
            wkpId: string;
            
            wkpName: string;
            constructor(wkpId: string, wkpName: string) {
                this.wkpId = wkpId;
                this.wkpName = wkpName;
            }
        }
        export class ConfirmationStatus {
            workplaceId: string;
            workplaceName: string;
            isEnabled: boolean;

            isChecked: KnockoutObservable<boolean>;
            /**
             * ・申請件数
             */
            numOfApp: number;
            /**
             * ・承認済件数
             */
            approvedNumOfCase: number;
            /**
             * ・未反映件数
             */
            numOfUnreflected: number;
            /**
             * ・未承認件数
             */
            numOfUnapproval: number;
            /**
             * ・否認件数
             */
            numOfDenials: number;  

            constructor( workplaceId: string, workplaceName: string, isEnabled: boolean, isChecked: boolean, numOfApp: number,
            approvedNumOfCase: number, numOfUnreflected: number, numOfUnapproval: number, numOfDenials: number) {
                this.workplaceId = workplaceId;
                this.workplaceName = workplaceName;
                this.numOfApp = numOfApp ? numOfApp : null;
                this.approvedNumOfCase = approvedNumOfCase ? approvedNumOfCase : null;
                this.numOfUnreflected =  numOfUnreflected ? numOfUnreflected : null;
                this.numOfUnapproval = numOfUnapproval ? numOfUnapproval : null;
                this.numOfDenials = numOfDenials ? numOfDenials : null;
                if(this.numOfUnapproval <= 0) {
                    this.isEnabled = false;    
                }else {
                    this.isEnabled = true;
                }
                this.isChecked = ko.observable(isChecked);
            }
        }

    }
}