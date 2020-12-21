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
            
            //WorkType_ 
            posibleWorkTypes: KnockoutObservableArray<string>;
            workTypeCodes: KnockoutObservableArray<string>;
            selectableWorkTypes: KnockoutObservableArray<string>;
            isCloseWorkType: KnockoutObservable<boolean>;

            //WorkTime_ 就業時間帯の設定
            posibleWorkTimes: KnockoutObservableArray<string>;
            workTimeCodes: KnockoutObservableArray<string>;
            selectableWorkTimes: KnockoutObservableArray<string>;
            isCloseWorkTime: KnockoutObservable<boolean>;

            constructor(){
                var self = this;
                self.empInfoTerCode = ko.observable('');
                self.empInfoTerName = ko.observable('');
                self.modelEmpInfoTerName = ko.observable('');
                self.workLocationName = ko.observable('');
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
                // Worktype
                self.posibleWorkTypes = ko.observableArray([]);
                self.workTypeCodes = ko.observableArray([]);
                self.selectableWorkTypes = ko.observableArray([]);
                self.isCloseWorkType = ko.observable(false); 
                // Worktime
                self.posibleWorkTimes = ko.observableArray([]);
                self.workTimeCodes = ko.observableArray([]);
                self.selectableWorkTimes = ko.observableArray([]);
                self.isCloseWorkTime = ko.observable(false);               
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
                if(self.empInfoTerCode() === undefined || self.empInfoTerCode() === '' || self.empInfoTerCode().length <= 0){
                    self.empInfoTerCode('');
                    self.empInfoTerName('');
                    self.modelEmpInfoTerName('');
                    self.workLocationName('');
                }else{

                    //load process
                    service.getTimeRecordReqSettings(self.empInfoTerCode()).done((data)=>{
                        if(!data){
                            //do something
                            self.empInfoTerCode('');
                            self.empInfoTerName('');
                        }else{
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
                }
                $('#G11_1').focus();
                blockUI.clear();   																			
                dfd.resolve();											
                return dfd.promise();											
            }
            /**
             * get worktype to be sent
             * 
             */
            private call_KDL002(): void{
                var self = this;
                blockUI.invisible();
                service.getWorkTypes(self.empInfoTerCode()).done((data)=>{	
                    if(!data){	
                        //do something
                    }else{	
                        self.posibleWorkTypes(data.posibleWorkTypes);
                        self.workTypeCodes(self.isCloseWorkType()? self.workTypeCodes() : data.workTypeCodes);
                        setShared('KDL002_Multiple', true);
                        setShared('KDL002_AllItemObj', self.posibleWorkTypes());
                        setShared('KDL002_SelectedItemId', self.workTypeCodes());
                        nts.uk.ui.windows.sub.modal('/view/kdl/002/a/index.xhtml', { title: '乖離時間の登録＞対象項目', }).onClosed(() => {
                            var selectable = nts.uk.ui.windows.getShared("KDL002_SelectedNewItem");
                            self.selectableWorkTypes(selectable !== undefined? selectable : []);
                            self.isCloseWorkType(true);
                            blockUI.clear();
                        });
                    }	
                });	 
            }
            /**
             * get Worktime to be sent
             * 
             */
            private call_KDL001(): void{
                var self = this;
                blockUI.invisible();
                service.getWorkTimes(self.empInfoTerCode()).done((data)=>{	
                    if(!data){	
                        //do something
                    }else{	
                        self.posibleWorkTimes(data.posibleWorkTimes);
                        self.workTimeCodes(self.isCloseWorkTime()? self.workTimeCodes() : data.workTimeCodes);
                        setShared('kml001multiSelectMode', true);
                        setShared('kml001selectAbleCodeList', self.posibleWorkTimes());
                        setShared('kml001selectedCodeList', self.workTimeCodes());
                        nts.uk.ui.windows.sub.modal("/view/kdl/001/a/index.xhtml", { title: "割増項目の設定", dialogClass: "no-close" }).onClosed(function() {
                            var selectable = getShared("kml001selectedCodeList");
                            self.isCloseWorkTime(true);
                            self.selectableWorkTimes(selectable !== undefined? selectable : []);
                            blockUI.clear();
                        });       
                    }	
                });	 
            }
            /**
             * get employees
             * 
             */
            private call_H_Dialog(): void{
                var self = this;
                blockUI.invisible();
                setShared('KNR002H_empInfoTerCode', self.empInfoTerCode());
                modal('/view/knr/002/h/index.xhtml', { title: 'H_Screen', }).onClosed(() => {
                    blockUI.clear();
                });
            }
            /**
             * get bento
             * 
             */
            private call_K_Dialog(): void{
                var self = this;
                blockUI.invisible();
                setShared('KNR002K_empInfoTerCode', self.empInfoTerCode());
                modal('/view/knr/002/k/index.xhtml', { title: 'k_Screen', }).onClosed(() => {
                    blockUI.clear();
                });
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