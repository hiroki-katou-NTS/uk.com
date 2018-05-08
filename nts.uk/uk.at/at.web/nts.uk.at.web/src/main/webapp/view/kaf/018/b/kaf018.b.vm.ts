module nts.uk.at.view.kaf018.b.viewmodel {
    import text = nts.uk.resource.getText;
    import getShared = nts.uk.ui.windows.getShared;
    import formatDate = nts.uk.time.formatDate;
    import info = nts.uk.ui.dialog.info;
    import error = nts.uk.ui.dialog.alertError;
        import shareModel = kaf018.share.model;
    import confirm = nts.uk.ui.dialog.confirm;
    import block = nts.uk.ui.block;
    
    export class ScreenModel {
        tempData: Array<model.ConfirmationStatus> = [];
        enable: KnockoutObservable<boolean> = ko.observable(true);
        listWorkplaceId: KnockoutObservableArray<string> =  ko.observableArray([]);
        closureId: number;
        closureName: string;
        processingYm: string;
        startDate: Date;
        endDate: Date;
        isDailyComfirm: boolean;
        listEmployeeCode: Array<any>;
        listWorkplace: Array<model.WorkplaceInfor>;
        constructor() {
            var self = this;
            $("#fixed-table").ntsFixedTable({ width: 1000, height: 161 });         
        }
        
        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            block.invisible();
            let params: model.IParam = nts.uk.ui.windows.getShared('KAF018BInput');
            self.closureId = params.closureId;
            self.closureName = params.closureName;
            self.processingYm = params.processingYm;
            self.startDate = params.startDate;
            self.endDate = params.endDate;
            self.listEmployeeCode = params.listEmployeeCode;
            self.isDailyComfirm = params.isConfirmData ;
            self.listWorkplace = params.listWorkplace;
            let obj = {
                startDate: self.startDate,
                endDate: self.endDate,
                isConfirmData: self.isDailyComfirm,
                listWorkplace: self.listWorkplace,
                listEmpCd: self.listEmployeeCode
            };
            service.getAppSttByWorkpace(obj).done(function(data: any) {
                console.log(data);
                _.forEach(data, function(item) {
                    self.tempData.push(new model.ConfirmationStatus(item.workplaceId, item.workplaceName , item.enabled, item.checked, item.numOfApp, item.approvedNumOfCase, item.numOfUnreflected, item.numOfUnapproval, item.numOfDenials));
                
                })
                dfd.resolve();
                block.clear();
            }).fail(function() {
                block.clear();
            })
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
        
        private getTargetDate() : string{
            var self = this;
            let startDate = nts.uk.time.formatDate(new Date(self.startDate), 'yyyy/MM/dd');
            let endDate = nts.uk.time.formatDate(new Date(self.endDate), 'yyyy/MM/dd');
            return self.processingYm +"("+ startDate +" ～ "+ endDate+")";
        }

        gotoC(index) {
            var self = this;
            let listWorkplace = [];
            _.each(self.tempData, function(item) {
                listWorkplace.push(new shareModel.ItemModel(item.workplaceId, item.workplaceName));        
            });
            let params = {
                closureId: self.closureId,
                closureName: self.closureName,
                processingYm: self.processingYm,
                startDate: self.startDate,
                endDate: self.endDate,
                listWorkplace: listWorkplace,
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
           startDate: Date;
            
            /** The end date. */
            endDate: Date;
             
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
            code: string;
            
            name: string;
            constructor(code: string, name: string) {
                this.code = code;
                this.name = name;
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
                this.numOfApp = numOfApp ? numOfApp : 0;
                this.approvedNumOfCase = approvedNumOfCase ? approvedNumOfCase : 0;
                this.numOfUnreflected =  numOfUnreflected ? numOfUnreflected : 0;
                this.numOfUnapproval = numOfUnapproval ? numOfUnapproval : 0;
                this.numOfDenials = numOfDenials ? numOfDenials : 0;
                if(this.numOfUnapproval > 0) {
                    this.isEnabled = false;    
                }else {
                    this.isEnabled = true;
                }
                this.isChecked = ko.observable(isChecked);
            }
        }

    }
}