module knr002.g {
    import blockUI = nts.uk.ui.block;
    import dialog = nts.uk.ui.dialog;
    import alertError = nts.uk.ui.alertError;
    import getText = nts.uk.resource.getText;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import modal =  nts.uk.ui.windows.sub.modal;
    import isNullOrUndefined = nts.uk.util.isNullOrUndefined;


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
            
            //  WorkType_ 
            posibleWorkTypes: KnockoutObservableArray<any>;
            workTypeCodes: KnockoutObservableArray<string>;
            selectableWorkTypes: KnockoutObservableArray<any>;

            //  WorkTime_ 就業時間帯の設定
            posibleWorkTimes: KnockoutObservableArray<any>;
            workTimeCodes: KnockoutObservableArray<string>;
            selectableWorkTimes: KnockoutObservableArray<any>;

            //  bentoMenu
            selectableBentos: KnockoutObservableArray<number>;

            //  employee
            selectableEmployees: KnockoutObservableArray<any>;
            initialSendStates: any;
            selectEmployee: KnockoutObservable<boolean>;
            selectWorkType: KnockoutObservable<boolean>;
            selectWorkTime: KnockoutObservable<boolean>;
            selectReservation: KnockoutObservable<boolean>;

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
                
                // Worktime
                self.posibleWorkTimes = ko.observableArray([]);
                self.workTimeCodes = ko.observableArray([]);
                self.selectableWorkTimes = ko.observableArray([]);
                self.initialSendStates = {};
                self.selectEmployee = ko.observable(false);
                self.selectWorkType = ko.observable(false);
                self.selectWorkTime = ko.observable(false);
                self.selectReservation = ko.observable(false);

                //  bento menu
                self.selectableBentos = ko.observableArray([]);

                //  employees
                self.selectableEmployees = ko.observableArray([]);

                // clear Errors
                self.sendEmployeeId.subscribe(check => {
                    self.clearErrors();
                });
                self.sendWorkType.subscribe(check => {
                    self.clearErrors();
                });
                self.sendWorkTime.subscribe(check => {
                    self.clearErrors();
                });
                self.sendBentoMenu.subscribe(check => {
                    self.clearErrors();
                });
                        
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
                            self.initialSendStates = _.cloneDeep(data);
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
                blockUI.clear();   
                $('#G11_1').focus();																			
                dfd.resolve();											
                return dfd.promise();											
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
                    let selectable = getShared("KNR002H_selectedList");
                    let isCancel = getShared("KNR002H_isCancel");
                    if(isCancel !== undefined && isCancel === false){
                        self.selectEmployee(true);
                        self.sendEmployeeId(true);
                        self.selectableEmployees(selectable !== undefined? selectable : []);
                        if(self.selectableEmployees().length <= 0){
                            dialog.error({ messageId: "Msg_2023" }).then(() => {
                                //  do something
                            });
                        } else {
                          //  let command: any = {};
                          //  command.terminalCode = self.empInfoTerCode();
                          //  command.selectedEmpIds = self.selectableEmployees();
                          //  service.makeSelectedEmployees(command).done(() => {
                          //      blockUI.invisible();
                          //      dialog.info({ messageId: "Msg_15" }).then(() => {
                          //          self.sendEmployeeId(true);
                          //      });                      
                          //  }).fail(() => {
                                // do something
                          //  }).always(() => {
                          //      blockUI.clear();
                          //  }); 
                        } 
                    }  else self.selectEmployee(false);
                });
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
                        self.workTypeCodes(data.workTypeCodes);
                        setShared('KDL002_Multiple', true);
                        setShared('KDL002_AllItemObj', self.posibleWorkTypes());
                        setShared('KDL002_SelectedItemId', self.workTypeCodes());
                        nts.uk.ui.windows.sub.modal('/view/kdl/002/a/index.xhtml', { title: '乖離時間の登録＞対象項目', }).onClosed(() => {
                            var selectable = getShared("KDL002_SelectedNewItem");
                            let isCancel = getShared("KDL002_IsCancel");
                            self.selectableWorkTypes(selectable !== undefined? selectable : []);
                            if(isCancel !== undefined && isCancel === false){
                                self.selectWorkType(true);
                                if(self.selectableWorkTypes().length <= 0){
                                    dialog.error({ messageId: "Msg_2024" }).then(() => {
                                        // do something
                                    });
                                } else {
                                    let command: any = {};
                                    command.terminalCode = self.empInfoTerCode();
                                    command.selectedWorkTypes = self.selectableWorkTypes().map(e => e.code);
                                    service.makeSelectedWorkTypes(command).done(() => {
                                        blockUI.invisible();
                                        dialog.info({ messageId: "Msg_15" }).then(() => {
                                            self.sendWorkType(true);
                                        });                      
                                    }).fail(() => {
                                        // do something
                                    }).always(() => {
                                        blockUI.clear();
                                    });              
                                }
                            } else self.selectWorkType(false);
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
                        self.workTimeCodes(data.workTimeCodes);
                        setShared('kml001multiSelectMode', true);
                        setShared('kml001selectAbleCodeList', self.posibleWorkTimes());
                        setShared('kml001selectedCodeList', self.workTimeCodes());
                        nts.uk.ui.windows.sub.modal("/view/kdl/001/a/index.xhtml", { title: "割増項目の設定", dialogClass: "no-close" }).onClosed(function() {
                            let selectable : Array<any> = getShared("kml001selectedCodeList");
                            let isCancel = getShared("KDL001_IsCancel");
                            if(isCancel !== undefined && isCancel === false){
                                self.selectWorkTime(true);
                                self.selectableWorkTimes(selectable !== undefined? selectable.filter(e => e != '') : []);
                                if(self.selectableWorkTimes().length <= 0 ){
                                    dialog.error({ messageId: "Msg_2025" }).then(() => {
                                        // do something
                                    });
                                } else {
                                    let command: any = {};
                                    command.terminalCode = self.empInfoTerCode();
                                    command.selectedWorkTimes = self.selectableWorkTimes();
                                    service.makeSelectedWorkTimes(command).done(() => {
                                        blockUI.invisible();
                                        dialog.info({ messageId: "Msg_15" }).then(() => {
                                            self.sendWorkTime(true);
                                        });                      
                                    }).fail(() => {
                                        // do something
                                    }).always(() => {
                                        blockUI.clear();
                                    });              
                                }
                            } else self.selectWorkTime(false);
                            blockUI.clear();
                        });       
                    }	
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
                    let selectable = getShared("KNR002K_selectedList");
                    let isCancel = getShared("KNR002K_isCancel");
                    if(isCancel !== undefined && isCancel === false){
                        self.selectReservation(true);
                        self.selectableBentos(selectable !== undefined? selectable : []);
                        if(self.selectableBentos().length <= 0){
                            dialog.error({ messageId: "Msg_2026" }).then(() => {
                                blockUI.clear();
                            });
                        } else {
                            let command: any = {};
                            command.terminalCode = self.empInfoTerCode();
                            command.selectedBentoMenuFrameNumbers = self.selectableBentos();
                            service.makeSelectedBentoMenu(command).done(() => {
                                blockUI.invisible();
                                dialog.info({ messageId: "Msg_15" }).then(() => {
                                    self.sendBentoMenu(true);
                                });                      
                            }).fail(() => {
                                // do something
                            }).always(() => {
                                blockUI.clear();
                            });              
                        }
                    } else self.selectReservation(false);
                });
            }

            private call_L_Dialog(): void{
                var self = this;
                blockUI.invisible();
                let data = {
                    mode: 'individual',
                    empInfoTerminalCode: self.empInfoTerCode()
                }
                setShared('dataShareL', data);
                modal('/view/knr/002/l/index.xhtml', { title: 'L_Screen', }).onClosed(() => {
                    blockUI.clear();
                });
            }
            /**
             * G11_1
             * 決定ボタン
             */
            private enter(): void{
                var self = this;
                self.clearErrors();
               service.confirm(self.empInfoTerCode()).done((data) => {
                   console.log("data: ", data);
                   if(!data){
                        // do nothing
                   } else {
                        if(!self.sendEmployeeId() && 
                            !self.sendWorkType() && 
                            !self.sendWorkTime() && 
                            !self.overTimeHoliday() &&
                            !self.applicationReason() &&
                            !self.sendBentoMenu() &&
                            !self.timeSetting() &&
                            !self.reboot() &&
                            !self.stampReceive() &&
                            !self.applicationReceive() &&
                            !self.reservationReceive()) {
                            dialog.error({ messageId: "Msg_2035" }).then(() => {
                                blockUI.clear();
                            });
                            return;
                        }
                        
                        // let selectedEmployees = self.selectableEmployees();
                        // if((!self.initialSendStates.sendEmployeeId && (isNullOrUndefined(selectedEmployees) || selectedEmployees.length == 0) && self.sendEmployeeId())
                        //     || (self.initialSendStates.sendEmployeeId && self.selectEmployee() && selectedEmployees.length == 0 && self.sendEmployeeId())) {
                        //     $('#G6_1').ntsError('set', { messageId:'Msg_2023' });
                        // }

                        // let selectedWorkTypes = self.selectableWorkTypes(); 
                        // if((!self.initialSendStates.sendWorkType && (isNullOrUndefined(selectedWorkTypes) || selectedWorkTypes.length == 0) && self.sendWorkType())
                        //     || (self.initialSendStates.sendWorkType && self.selectWorkType() && selectedWorkTypes.length == 0 && self.sendWorkType())) {
                        //     $('#G6_2').ntsError('set', { messageId:'Msg_2024' });
                        // }

                        // let selectedWorkTimes = self.selectableWorkTimes();
                        // if((!self.initialSendStates.sendWorkTime && (isNullOrUndefined(selectedWorkTimes) || selectedWorkTimes.length == 0) && self.sendWorkTime())
                        //     || (self.initialSendStates.sendWorkTime && self.selectWorkTime() && selectedWorkTimes.length == 0 && self.sendWorkTime())){
                        //     $('#G6_3').ntsError('set', { messageId:'Msg_2025' });
                        // }

                        // let selectedBentos = self.selectableBentos();
                        // if((!self.initialSendStates.sendBentoMenu && (isNullOrUndefined(selectedBentos) || selectedBentos.length == 0) && self.sendBentoMenu())
                        //     || (self.initialSendStates.sendBentoMenu && self.selectReservation() && selectedBentos.length == 0 && self.sendBentoMenu())) {
                        //     $('#G6_6').ntsError('set', { messageId:'Msg_2026' });
                        // }
                                                
                        if(self.sendEmployeeId() && (!data.employeeIds || data.employeeIds.length <= 0)) {
                            $('#G6_1').ntsError('set', { messageId:'Msg_2023' });
                        }

                        if(self.sendWorkType() && (!data.workTypeCodes || data.workTypeCodes.length <= 0)) {
                            $('#G6_2').ntsError('set', { messageId:'Msg_2024' });
                        }

                        if(self.sendWorkTime() && (!data.workTimeCodes || data.workTimeCodes.length <= 0)) {
                            $('#G6_3').ntsError('set', { messageId:'Msg_2025' });
                        }

                        if(self.sendBentoMenu() && (!data.bentoMenuFrameNumbers || data.bentoMenuFrameNumbers.length <= 0)) {
                            $('#G6_6').ntsError('set', { messageId:'Msg_2026' });
                        }
        
                        if(self.hasError()){
                            return;
                        }
                        
                        service.determine(self.empInfoTerCode(),
                                          self.sendEmployeeId(),
                                          self.sendBentoMenu(),
                                          self.sendWorkType(),
                                          self.sendWorkTime(),
                                          self.overTimeHoliday(),
                                          self.applicationReason(),
                                          self.stampReceive(),
                                          self.reservationReceive(),
                                          self.applicationReceive(),
                                          self.timeSetting(),
                                          self.reboot())
                                .done(() => {
                                    nts.uk.ui.windows.close();
                                    // do nothing
                                }).fail(() => {
                                    // do nothing
                                }).always(() => {
                                    blockUI.clear();
                                });
                   }
               });
            }
            
            /**
             * cancel_Dialog
             */
            private cancel_Dialog(): any {
                let self = this;
                nts.uk.ui.windows.close();
            }

            /**
             * Check List Err
             */
            private hasError(): boolean{
                if( $('#G6_1').ntsError('hasError') ||
                    $('#G6_2').ntsError('hasError') ||
                    $('#G6_3').ntsError('hasError') ||
                    $('#G6_6').ntsError('hasError'))    {
                    return true;
                }
                return false;                    
            }

            /**
             * clear Errors
             */
            private clearErrors(): void{     
                $('#G6_1').ntsError('clear');
                $('#G6_2').ntsError('clear');
                $('#G6_3').ntsError('clear');
                $('#G6_6').ntsError('clear');
                $('.nts-input').ntsError('clear');
                nts.uk.ui.errors.clearAll();
            }        }
    }
}