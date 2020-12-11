module knr002.g {
    import blockUI = nts.uk.ui.block;
    import dialog = nts.uk.ui.dialog;
    import alertError = nts.uk.ui.alertError;
    import getText = nts.uk.resource.getText;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import modal =  nts.uk.ui.windows.sub.modal;

    export module viewmodel{
        export class ScreenModel{
            //get shared from A
            empInfoTerCode: KnockoutObservable<string>;//端末NO
            empInfoTerName: KnockoutObservable<string>;//名称
            modelEmpInfoTerName: KnockoutObservable<string>;//機種
            workLocationName: KnockoutObservable<string>;//設置場所
            // checkbox
            sendEmployeeId: KnockoutObservable<boolean>;//社員ＩＤ送信
            sendWorkType: KnockoutObservable<boolean>;//勤務種類コード送信
            sendWorkTime: KnockoutObservable<boolean>;//就業時間帯コード送信
            overTimeHoliday: KnockoutObservable<boolean>;//残業・休日出勤送信
            applicationReason: KnockoutObservable<boolean>;//申請理由送信
            sendBentoMenu: KnockoutObservable<boolean>;//弁当メニュー枠番送信
            timeSetting: KnockoutObservable<boolean>;//時刻セット
            reboot: KnockoutObservable<boolean>;//再起動を行う
            stampReceive: KnockoutObservable<boolean>;//全ての打刻データ
            applicationReceive: KnockoutObservable<boolean>;//全ての申請データ
            reservationReceive: KnockoutObservable<boolean>;//全ての予約データ




            constructor(){
                var self = this;
                self.empInfoTerCode = ko.observable("");
                self.empInfoTerName = ko.observable("");
                self.modelEmpInfoTerName = ko.observable("");
                self.workLocationName = ko.observable("");
                //checkbox
                self.sendEmployeeId = ko.observable(false);
                self.sendWorkType = ko.observable(false);
                self.sendWorkTime = ko.observable(false);
                self.overTimeHoliday = ko.observable(false);
                self.applicationReason = ko.observable(false);
                self.sendBentoMenu = ko.observable(false);
                self.timeSetting = ko.observable(false);
                self.reboot = ko.observable(false);
                self.stampReceive = ko.observable(false);
                self.applicationReceive = ko.observable(false);
                self.reservationReceive = ko.observable(false);
            }
            /**
             * Start Page
             * 起動する
             */
            public startPage(): JQueryPromise<void>{
                var self = this;										
                var dfd = $.Deferred<void>();
                blockUI.invisible();
                //get shared from A
                self.empInfoTerCode(getShared('KNR002G_empInfoTerCode'));
                self.empInfoTerName(getShared('KNR002G_empInfoTerName'));
                self.modelEmpInfoTerName(getShared('KNR002G_modelEmpInfoTer'));
                self.workLocationName(getShared('KNR002G_workLocationName'));
                if(!self.empInfoTerCode()){
                    self.empInfoTerCode("0001");
                    self.empInfoTerName("isn't the shared");
                }

                //load process
                service.getTimeRecordReqSettings(self.empInfoTerCode()).done((data)=>{
                    if(!data){
                        //do something
                        console.log("can't get data from server");
                    }else{
                        console.log("show data from server");
                        self.sendEmployeeId(data.sendEmployeeId);
                        self.sendWorkType(data.sendWorkType);
                        self.sendWorkTime(data.sendWorkTime);
                        self.overTimeHoliday(data.overTimeHoliday);
                        self.applicationReason(data.applicationReason);
                        self.sendBentoMenu(data.sendBentoMenu);
                        self.timeSetting(data.timeSetting);
                        self.reboot(data.reboot);
                        self.stampReceive(data.stampReceive);
                        self.applicationReceive(data.applicationReceive);
                        self.reservationReceive(data.reservationReceive);                       
                    }
                });
                $('#G11_1').focus();
                blockUI.clear();   																			
                dfd.resolve();											
                return dfd.promise();											
            }
            /**
             * get Model Name
             */
            private getModelName(modelEmpInfoTer: Number): string{
                switch(modelEmpInfoTer){
                    case 7: return 'NRL_1';
                    case 8: return 'NRL_2';
                    case 9: return 'NRL_M';
                    default : return '';
                }	
            }
            
            /**
             * cancel_Dialog
             */
            private cancel_Dialog(): any {
                let self = this;
                nts.uk.ui.windows.close();
            }
        }
    }
}