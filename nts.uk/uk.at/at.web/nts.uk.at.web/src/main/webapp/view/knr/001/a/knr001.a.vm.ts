module nts.uk.at.view.knr001.a {
    import blockUI = nts.uk.ui.block;
    import dialog = nts.uk.ui.dialog;
    import alertError = nts.uk.ui.alertError;
    import getText = nts.uk.resource.getText;


    export module viewmodel{
        export class ScreenModel{
            enableBtnNew: KnockoutObservable<boolean>;
            enableBtnDelete: KnockoutObservable<boolean>; 
            isUpdateMode: KnockoutObservable<boolean>;
            empInfoTerminalModel: KnockoutObservable<EmpInfoTerminalModel>;
            selectedCode: KnockoutObservable<number>;
            empInfoTerminalList: KnockoutObservableArray<EmpInfoListDto>;
            
            constructor(){
                var self = this;
                self.enableBtnNew = ko.observable(true);
                self.enableBtnDelete = ko.observable(true);
                self.isUpdateMode = ko.observable(true);
                self.empInfoTerminalModel = ko.observable(new EmpInfoTerminalModel);
                self.empInfoTerminalList = ko.observableArray<EmpInfoListDto>([]);
                self.selectedCode = ko.observable('');
                //select an employment information terminal
                self.selectedCode.subscribe(function(empInfoTerminalCode){
                    if(empInfoTerminalCode){
                        self.clearErrors();
                        self.enableBtnDelete(true);
                        self.loadEmpInfoTerminal(empInfoTerminalCode);
                    } else {
                        self.createNewMode();
                        self.enableBtnDelete(false);
                    }
                });

                //select a value from selectbox 機種
                self.empInfoTerminalModel().modelEmpInfoTer.subscribe(function(modelEmpInfo){
                    _.each(self.empInfoTerminalModel().modelEmpInfoTer(), function(item, index){
                        if(item.code == modelEmpInfo){
                            self.empInfoTerminalModel().modelEmpInfoTer(item.code);
                            if(self.empInfoTerminalModel().checkedOutingClass()===false){
                                self.empInfoTerminalModel().enableEntranExit(true);
                            } else {
                                self.empInfoTerminalModel().enableEntranExit(false);
                            }
                        }
                        if(modelEmpInfo === 9){
                            self.empInfoTerminalModel().enableOutingClass(true);
                        }else{
                            self.empInfoTerminalModel().enableOutingClass(false);
                            self.empInfoTerminalModel().enableEntranExit(true);
                        }
                    });
                });
                //select a value from selectbox 外出区分
                self.empInfoTerminalModel().goOutReason.subscribe(function(reason){
                    _.each(self.empInfoTerminalModel().goOutReason, function(item, index){
                        if(item.code == reason){
                            self.empInfoTerminalModel().goOutReason(item.name);
                            if(self.empInfoTerminalModel().checkedOutingClass() == false){
                                self.empInfoTerminalModel().checkedEntranExit(true);
                            }else{
                                self.empInfoTerminalModel().enableEntranExit(false);
                            }
                        }
                        if(reason !== 0){
                            self.empInfoTerminalModel().replace(1);
                        } else{
                            self.empInfoTerminalModel().replace(0); 
                        }
                    })
                });
                //tick on checkbox outSupport
                self.empInfoTerminalModel().checkedOutingClass.subscribe(function(check){
                    if(check == true){
                        self.empInfoTerminalModel().outSupport(1);
                    }else{
                        self.empInfoTerminalModel().outSupport(0);
                    }
                });
                //tick on checkbox entranceExit
                //
                self.empInfoTerminalModel().checkedEntranExit.subscribe(function(check){
                    if(check == true){
                        self.empInfoTerminalModel().entranceExit(1);
                    }else{
                        self.empInfoTerminalModel().entranceExit(0);
                    }
                });

                //select a worklocation
                //勤務場所選択ダイアログ（KDL010）勤務場所選択ダイアログで場所選択後に決定ボタン
                self.empInfoTerminalModel().workLocationCode.subscribe(function(){
                    let workLocCode = self.empInfoTerminalModel().workLocationCode();
                    self.empInfoTerminalModel().workLocationName("");
                    if(workLocCode !== ""){
                        service.getworkLocationName(self.empInfoTerminalModel().workLocationCode()).done(function(res: any){
                            if(res){
                                self.empInfoTerminalModel().workLocationName(res.workLocationName);              
                            }
                        });
                    }
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
                service.getAll().done((data)=>{
                    if(data.length<=0){
                        self.createNewMode();
                    } else {
                        self.isUpdateMode(true);
                        self.empInfoTerminalList(data);
                        self.selectedCode(self.empInfoTerminalList()[0].empInfoTerCode);
                        self.loadEmpInfoTerminal(self.selectedCode());
                    }
                });
                blockUI.clear();   																			
                dfd.resolve();											
                return dfd.promise();											
            }
            /**
             * load Employment information terminal
             * 起動する／選択端末を変更する／削除ボタン押下後の表示処理
             */
            private loadEmpInfoTerminal(empInfoTerCode: number): void{
                let self = this;          
                service.getDetails(empInfoTerCode).done(function(empInfoTer: any){
                    if(empInfoTer){
                        self.isUpdateMode(true);
                        self.enableBtnDelete(true);
                        self.selectedCode(empInfoTer.empInfoTerCode);
                        self.empInfoTerminalModel().updateData(empInfoTer);
                        self.empInfoTerminalModel().isEnableCode(false);              
                    }else{
                        self.createNewMode();
                    }
                });
            }

            /**
             * clear Data
             */
            private createNewMode(): void{
                let self = this;
                self.selectedCode('');
                self.empInfoTerminalModel().resetData();
                self.enableBtnDelete(false);
                self.clearErrors();
                self.isUpdateMode(false);
            }

            /**
             * regist Employment information terminal 
             */
            private registEmpInfoTerminal(): void{
                let self = this;
                if(self.hasError()){
                    return;
                }
                let ipAddress = "";
                ipAddress += self.empInfoTerminalModel().ipAddress1() + "."
                            + self.empInfoTerminalModel().ipAddress2() + "."
                            + self.empInfoTerminalModel().ipAddress3() + "."
                            + self.empInfoTerminalModel().ipAddress4();
                let command: any = {};
                command.empInfoTerCode = self.empInfoTerminalModel().empInfoTerCode();
                command.empInfoTerName = self.empInfoTerminalModel().empInfoTerName();
                command.modelEmpInfoTer = self.empInfoTerminalModel().modelEmpInfoTer();
                command.macAddress = self.empInfoTerminalModel().macAddress();
                command.ipAddress = ipAddress;
                command.terSerialNo = self.empInfoTerminalModel().terSerialNo();
                command.workLocationCode = self.empInfoTerminalModel().workLocationCode();
                command.intervalTime = self.empInfoTerminalModel().intervalTime();
                command.outSupport = self.empInfoTerminalModel().outSupport();
                command.replace = self.empInfoTerminalModel().replace();
                command.goOutReason = self.empInfoTerminalModel().goOutReason() - 1;
                command.entranceExit = self.empInfoTerminalModel().entranceExit();
                command.memo = self.empInfoTerminalModel().memo();

                blockUI.invisible();
                if(self.isUpdateMode()==false){
                    //登録ボタン押下（新規モード）
                    service.register(command).done(()=>{
                        nts.uk.ui.dialog.info({messageId: "Msg_15"}).then(function(){
                            //Reload EmpList
                            service.getAll().done((data)=>{
                                    self.isUpdateMode(true);
                                    self.empInfoTerminalList(data);
                                    self.selectedCode(self.empInfoTerminalModel().empInfoTerCode());
                                    self.loadEmpInfoTerminal(self.selectedCode());
                                    self.empInfoTerminalModel().isEnableCode(false);
                                    $('#A3_4').focus();
                            });   
                        });
                    }).fail(error => {
                        $('#A3_2').ntsError('set', {messageId: error.messageId});  
                    }).always(()=>{
                        blockUI.clear();    
                    });
                    
                } else {
                    //登録ボタン押下（更新モード）
                    service.update(command).done(() => {
                        nts.uk.ui.dialog.info({messageId: "Msg_15"}).then(() => {
                            //Reload EmpList
                            service.getAll().done((data) => {
                                    self.isUpdateMode(true);
                                    self.empInfoTerminalList(data);
                                    self.selectedCode(self.empInfoTerminalModel().empInfoTerCode());
                                    self.loadEmpInfoTerminal(self.selectedCode());
                                    self.empInfoTerminalModel().isEnableCode(false);
                                    $('#A3_4').focus();
                            });   
                        });
                    }).fail(error => {
                        $('#A1_2').ntsError('set', {messageId: error.messageId});
                    }).always(()=>{
                        blockUI.clear();    
                    });
                }
            }
            /**
             * remove Employment information terminal
             * 削除ボタン押下
             */
            private removeEmpInfoTerminal(): void{
                let self = this;
                if(self.hasError()){
                    return;
                }
                dialog.confirm({messageId:"Msg_18"}).ifYes(()=>{
                    var delCode = self.empInfoTerminalModel().empInfoTerCode();
                    var index = self.empInfoTerminalList().indexOf(self.empInfoTerminalList().find(empInfoTer => delCode == empInfoTer.empInfoTerCode));
                    console.log(index, "index 1")
                    let command = {
                        empInfoTerCode: delCode
                    };
                    blockUI.invisible();
                    service.removeEmpInfoTer(command).done(()=>{
                        dialog.info({messageId:"Msg_16"}).then(() => {
                            //reload
                            service.getAll().done((data)=>{
                                if(data.length<=0){
                                    self.clearErrors();
                                    self.createNewMode();
                                } else {
                                    self.isUpdateMode(true);
                                    self.enableBtnDelete(true);
                                    self.empInfoTerminalList(data);
                                    let length = data.length;
                                    if(index === 0){
                                        self.selectedCode(self.empInfoTerminalList()[0].empInfoTerCode);
                                    } else if (index === length){
                                        self.selectedCode(self.empInfoTerminalList()[index-1].empInfoTerCode);
                                    }else{
                                        self.selectedCode(self.empInfoTerminalList()[index].empInfoTerCode);
                                    }
                                    self.loadEmpInfoTerminal(self.selectedCode());
                                }
                            });   
                        });
                        blockUI.clear();
                    }).fail((res)=>{
                        nts.uk.ui.alertError(res.message).then(()=>{
                            blockUI.clear();
                        });
                    });
                    }).ifNo(function(){
                        blockUI.clear();
                        $('#A3_4').focus();      
                });
            }
             /**
             * export Excel
             * マスタリストボタン押下
             */
            public knrExport(): void {
                let self = this;
                blockUI.invisible();
                service.knrExport().done(() => {
                }).fail((errExcel) =>{
                    alertError(errExcel);
                }).always(()=>{
                    blockUI.clear();    
                });
            }
            /**
             * Check Input Errors
             */
            private hasError(): boolean{
                var self = this;
                self.clearErrors();
                //code
                $('#A3_2').ntsEditor("validate");
                //name
                $('#A3_4').ntsEditor("validate");
                //macAddress
                $('#A3_8').ntsEditor("validate");
                //serialNo
                $('#A5_2').ntsEditor("validate");
                //MonitorIntervalTime
                $('#A5_7').ntsEditor("validate");
                //Memo
                $('#A6_8').ntsEditor("validate");

                if ($('.nts-input').ntsError('hasError')) {
                    return true;
                }
                return false;
            }
            /**
             * clear Errors
             */
            private clearErrors(): void{               
                $('.nts-input').ntsError('clear');
            }
        }

        /**
         * Employment information terminal Model
         */
        export class EmpInfoTerminalModel{
            //  端末No
            empInfoTerCode: KnockoutObservable<number>;
            //  名称
            empInfoTerName: KnockoutObservable<string>;
            //  機種: combobox
            empInfoTerminalModelList: KnockoutObservableArray<ItemModel>;
            //  機種
            modelEmpInfoTer: KnockoutObservable<number>;
            //  MACアドレス
            macAddress: KnockoutObservable<string>;
            //  ＩＰアドレス
            ipAddress: KnockoutObservable<string>;
            ipAddress1: KnockoutObservable<number>;
            ipAddress2: KnockoutObservable<number>;
            ipAddress3: KnockoutObservable<number>;
            ipAddress4: KnockoutObservable<number>;
            //  シリアルＮｏ
            terSerialNo: KnockoutObservable<number>;
            //  設置場所
            workLocationCode: KnockoutObservable<string>;
            workLocationName: KnockoutObservable<string>;
            //  状態監視間隔
            intervalTime: KnockoutObservable<number>;
            //  外出応援区分
            outSupport: KnockoutObservable<number>;
            enableOutingClass: KnockoutObservable<boolean>;
            checkedOutingClass: KnockoutObservable<boolean>;
            //  外出区分: combobox
            outingClassList: KnockoutObservableArray<ItemModel>;
            //  外出区分
            replace: KnockoutObservable<number>;
            //  外出理由
            goOutReason: KnockoutObservable<number>;
            //  入退門区分
            entranceExit: KnockoutObservable<number>; 
            enableEntranExit: KnockoutObservable<boolean>;
            checkedEntranExit: KnockoutObservable<boolean>;
            //  メモ
            memo: KnockoutObservable<string>;
          
            isEnableCode: KnockoutObservable<boolean>;
   
            
         

            constructor(){
                this.empInfoTerCode =  ko.observable('');
                this.empInfoTerName =  ko.observable('');
                this.empInfoTerminalModelList = ko.observableArray([			
                                                new ItemModel(7, 'NRL_1'),			
                                                new ItemModel(8, 'NRL_2'),			
                                                new ItemModel(9, 'NRL_M')				
                                            ]); 
                this.modelEmpInfoTer =  ko.observable('');
                
                this.macAddress =  ko.observable('');
                
                this.ipAddress = ko.observable('');
                this.ipAddress1 =  ko.observable('');
                this.ipAddress2 =  ko.observable('');
                this.ipAddress3 =  ko.observable('');
                this.ipAddress4 =  ko.observable('');
                
                this.terSerialNo =  ko.observable('');
                this.workLocationCode =  ko.observable('');
                this.workLocationName =  ko.observable('');
                this.intervalTime =  ko.observable(0);
                
                this.outSupport =  ko.observable(0);
                this.enableOutingClass = ko.observable(true);
                this.checkedOutingClass = ko.observable(false);

                this.outingClassList = ko.observableArray([			
                                                new ItemModel(0, getText('KNR001_53')),			
                                                new ItemModel(1, getText('KNR001_54')),			
                                                new ItemModel(2, getText('KNR001_55')),	
                                                new ItemModel(3, getText('KNR001_56')),	
                                                new ItemModel(4, getText('KNR001_57'))
                                            ]); 
                this.replace =  ko.observable('');
                this.goOutReason =  ko.observable('');
                
                this.entranceExit =  ko.observable(0); 
                this.enableEntranExit = ko.observable(true);;
                this.checkedEntranExit = ko.observable(false);

                this.memo =  ko.observable('');  
               
                this.isEnableCode =  ko.observable(true);               
            }
            /**
             * reset Data
             */
            resetData(){
                this.empInfoTerCode('');
                this.empInfoTerName('');
                this.modelEmpInfoTer('');
                this.macAddress('');
                this.ipAddress('');
                this.ipAddress1('');
                this.ipAddress2('');
                this.ipAddress3('');
                this.ipAddress4('');
                this.terSerialNo('');
                this.workLocationCode('');
                this.workLocationName('');
                this.intervalTime('');
                this.outSupport('');
                this.replace('');
                this.goOutReason('');
                this.entranceExit(''); 
                this.memo('');  
                this.isEnableCode(true);
                this.enableOutingClass(true);
                this.enableEntranExit(true);
                this.checkedOutingClass(false);
                this.checkedEntranExit(false);
            }
            /**
             * update Data
             */
            updateData(dto: any){
                this.empInfoTerCode(dto.empInfoTerCode);
                this.empInfoTerName(dto.empInfoTerName);
                this.modelEmpInfoTer(dto.modelEmpInfoTer);
                this.macAddress(dto.macAddress);
                this.ipAddress(dto.ipAddress);
                var arrIpAddress = dto.ipAddress.split(".");
                this.ipAddress1(arrIpAddress[0]);
                this.ipAddress2(arrIpAddress[1]);
                this.ipAddress3(arrIpAddress[2]);
                this.ipAddress4(arrIpAddress[3]);
                this.terSerialNo(dto.terSerialNo);
                this.workLocationCode(dto.workLocationCode);
                this.workLocationName(dto.workLocationName);
                this.intervalTime(dto.intervalTime);
                this.outSupport(dto.outSupport);
                if(dto.outSupport === 1){
                    this.checkedOutingClass(true);
                } else {
                    this.checkedOutingClass(false);
                }
                this.replace(dto.replace);

                this.goOutReason(dto.goOutReason >= 0? dto.goOutReason + 1 : 0 );
                this.entranceExit(dto.entranceExit);
                if(dto.entranceExit === 1){
                    this.checkedEntranExit(true);
                } else {
                    this.checkedEntranExit(false);
                }
                this.memo(dto.memo);
            }
            /**
             * Show Dialog Work Location
             * 勤務場所選択ダイアログ（KDL010）の表示　場所選択のボタン押下
             */
            callKDL010() {
                var self = this;
                nts.uk.ui.block.invisible();
                nts.uk.ui.windows.setShared('KDL010SelectWorkLocation', self.workLocationCode());
                nts.uk.ui.windows.sub.modal("/view/kdl/010/a/index.xhtml", { dialogClass: "no-close" }).onClosed(() => {
                    var self = this;
                    var returnWorkLocationCD = nts.uk.ui.windows.getShared("KDL010workLocation");
                    if (returnWorkLocationCD !== undefined) {
                        self.workLocationCode(returnWorkLocationCD);
                    }
                    else{
                        self.workLocationCode("");
                    }
                    nts.uk.ui.block.clear();
                });
            }
        }
        class EmpInfoListDto{
            empInfoTerCode: number;
            empInfoTerName: string;
            constructor(empInfoTerCode: number, empInfoTerName: string){
                this.empInfoTerCode = empInfoTerCode;
                this.empInfoTerName = empInfoTerName;
            }
        }
        class ItemModel{
            code: number;
            name: string;
            constructor(code: number, name: string){
                this.code = code;
                this.name = name;   
            }
        }
    }
}