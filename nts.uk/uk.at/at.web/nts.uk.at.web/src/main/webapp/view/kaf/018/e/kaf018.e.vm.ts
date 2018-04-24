module nts.uk.at.view.kaf018.e.viewmodel {
    import text = nts.uk.resource.getText;
    import getShared = nts.uk.ui.windows.getShared;
    import formatDate = nts.uk.time.formatDate;
    import info = nts.uk.ui.dialog.info;
    import error = nts.uk.ui.dialog.alertError;
    import block = nts.uk.ui.block;

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

        person: number;
        daily: number;
        monthly: number;
        
        constructor() {
            var self = this;
            $("#fixed-table").ntsFixedTable({ width: 1000, height: 186 });
            self.listWkpStatusConfirm = [];
            self.person = model.TransmissionAttr.PERSON;
            self.daily = model.TransmissionAttr.DAILY;
            self.monthly = model.TransmissionAttr.MONTHLY;
        }

        /**
         * 起動する
         */
        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            block.invisible();
            let params = getShared("KAF018E_PARAMS");
            if (params) {
                self.closureId = params.closureId;
                self.closureName = params.closureName;
                self.processingYm = params.processingYm;
                self.startDate = formatDate(new Date(params.startDate), 'yyyy/MM/dd');
                self.endDate = formatDate(new Date(params.endDate), 'yyyy/MM/dd');
                self.isConfirmData = params.isConfirmData;
                self.listWorkplaceId = params.listWorkplaceId;
                self.listEmpCd = params.listEmployeeCode;

                let obj = {
                    startDate: self.startDate,
                    endDate: self.endDate,
                    isConfirmData: self.isConfirmData,
                    listWorkplaceId: self.listWorkplaceId,
                    listEmpCd: self.listEmpCd
                };

                service.getStatusActivity(obj).done(function(data: any) {
                    console.log(data);
                    _.each(data, function(item) {
                        self.listWkpStatusConfirm.push(new model.ApprovalStatusActivity(item.wkpId, item.wkpId, item.monthConfirm, item.monthUnconfirm, item.bossConfirm, item.bossUnconfirm, item.personConfirm, item.personUnconfirm))
                        console.log(item)
                    })
                    block.clear();
                    dfd.resolve();
                })
            }
            else {
                dfd.reject();
            }
            return dfd.promise();
        }

        sendMail(value: model.TransmissionAttr) {
            var self = this;
            block.invisible();
            let listWkp = [];
            _.each(self.listWkpStatusConfirm, function(item) {
                listWkp.push({ wkpId: item.code, isCheckOn: item.check() ? 1 : 0 })
            })
            service.checkSendUnconfirmedMail(listWkp).done(function() {
                switch(value){
                    case model.TransmissionAttr.PERSON:
                        break;
                    case model.TransmissionAttr.DAILY:
                        break;
                    case model.TransmissionAttr.MONTHLY:
                        break;
                }
                block.clear();
            }).fail(function(err) {
                block.clear();
                error({ messageId: err.messageId });
            })
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
            monthUnconfirm: number;
            dayBossUnconfirm: number;
            dayBossConfirm: number;
            dayPrincipalUnconfirm: number;
            dayPrincipalConfirm: number;
            check: KnockoutObservable<boolean>;
            enable: boolean;

            constructor(code: string, name: string, monthConfirm: number, monthUnconfirm: number, dayBossUnconfirm: number, dayBossConfirm: number, dayPrincipalUnconfirm: number, dayPrincipalConfirm: number) {
                this.code = code;
                this.name = name;
                this.monthConfirm = monthConfirm ? monthConfirm : null;
                this.monthUnconfirm = monthUnconfirm ? monthUnconfirm : null;
                this.dayBossUnconfirm = dayBossUnconfirm ? dayBossUnconfirm : null;
                this.dayBossConfirm = dayBossConfirm ? dayBossConfirm : null;
                this.dayPrincipalUnconfirm = dayPrincipalUnconfirm ? dayPrincipalUnconfirm : null;
                this.dayPrincipalConfirm = dayPrincipalConfirm ? dayPrincipalConfirm : null;
                if (dayPrincipalUnconfirm == 0 && dayBossUnconfirm == 0 && monthUnconfirm == 0) {                    
                    this.enable = false;
                    this.check = ko.observable(this.enable);
                }
                else {
                    this.enable = true;
                    this.check = ko.observable(this.enable);
                }

            }
        }

        //送信区分
        export enum TransmissionAttr {
            //本人
            PERSON = 1,
            //日次
            DAILY = 2,
            //月次
            MONTHLY = 3
        }
    }
}