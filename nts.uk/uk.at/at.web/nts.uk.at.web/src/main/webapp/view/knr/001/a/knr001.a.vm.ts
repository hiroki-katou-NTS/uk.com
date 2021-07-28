module nts.uk.at.view.knr001.a {
    import blockUI = nts.uk.ui.block;
    import dialog = nts.uk.ui.dialog;
    import alertError = nts.uk.ui.alertError;
    import getText = nts.uk.resource.getText;
    import modal = nts.uk.ui.windows.sub.modal;
    import getShared = nts.uk.ui.windows.getShared;
    import setShared = nts.uk.ui.windows.setShared;


    export module viewmodel{
        export class ScreenModel{
            enableBtnNew: KnockoutObservable<boolean>;
            enableBtnDelete: KnockoutObservable<boolean>; 
            isUpdateMode: KnockoutObservable<boolean>;
            empInfoTerminalModel: KnockoutObservable<EmpInfoTerminalModel>;
            selectedCode: KnockoutObservable<string>;
            empInfoTerminalList: KnockoutObservableArray<EmpInfoListDto>;
            enableBtnRegist: KnockoutObservable<boolean>;

            itemList1: KnockoutObservableArray<ItemModel1>;
            itemList2: KnockoutObservableArray<ItemModel1>;
            itemList3: KnockoutObservableArray<ItemModel1>;
            itemList4: KnockoutObservableArray<ItemModel1>;

            selectedIndex1: KnockoutObservable<number> = ko.observable(0);
            selectedIndex2: KnockoutObservable<number> = ko.observable(0);
            selectedIndex3: KnockoutObservable<number> = ko.observable(0);
            selectedIndex4: KnockoutObservable<number> = ko.observable(0);

            goOutReason: KnockoutObservable<number> = ko.observable(0);
            entranceExit: KnockoutObservable<boolean> = ko.observable(false);

            constructor(){
                var self = this;
                self.enableBtnNew = ko.observable(true);
                self.enableBtnDelete = ko.observable(true);
                self.isUpdateMode = ko.observable(true);
                self.empInfoTerminalModel = ko.observable(new EmpInfoTerminalModel);
                self.empInfoTerminalList = ko.observableArray<EmpInfoListDto>([]);
                self.selectedCode = ko.observable(null);
                self.enableBtnRegist = ko.observable(true);

                // 8_3
                self.itemList1 = ko.observableArray([
                    new ItemModel1(0, getText('KNR001_155')),
                    new ItemModel1(1, getText('KNR001_156')),
                    new ItemModel1(2, getText('KNR001_157'))
                ]);

                // 8_5
                self.itemList2 = ko.observableArray([
                    new ItemModel1(3, getText('KNR001_162')),
                    new ItemModel1(4, getText('KNR001_163'))
                ]);

                // 8_7
                self.itemList3 = ko.observableArray([
                    new ItemModel1(5, getText('KNR001_167')),
                    new ItemModel1(6, getText('KNR001_168')),
                    new ItemModel1(7, getText('KNR001_169')),
                    new ItemModel1(8, getText('KNR001_170')),
                    new ItemModel1(9, getText('KNR001_171'))
                ]);

                // 8_9
                self.itemList4 = ko.observableArray([
                    new ItemModel1(10, getText('KNR001_177')),
                    new ItemModel1(11, getText('KNR001_178'))
                ]);

                // getText('KNR001_53')

                self.selectedCode.subscribe(function(empInfoTerminalCode){
                    blockUI.invisible();
                    setTimeout(() => {
                        self.clearErrors();
                    }, 7);
                    if(empInfoTerminalCode){
                        self.enableBtnDelete(true);
                        self.loadEmpInfoTerminal(empInfoTerminalCode);
                        self.enableBtnNew(true);
                        // $('#multi-list').focus();
                    } else {
                        self.createNewMode();
                        self.enableBtnDelete(false);
                    }
                    blockUI.clear();
                });

                //select a value from selectbox 機種
                self.empInfoTerminalModel().modelEmpInfoTer.subscribe(function(modelEmpInfo){
                    self.empInfoTerminalModel().modelEmpInfoTer(modelEmpInfo);
                            if(modelEmpInfo == 7 || modelEmpInfo == 8){
                                self.empInfoTerminalModel().enableOutingClass(false);
                                self.selectedIndex1(0);
                                self.selectedIndex2(3);
                                self.selectedIndex3(6);
                                self.selectedIndex4(10);
                            }else{
                                self.empInfoTerminalModel().enableOutingClass(true);
                                self.goOutReason(10);
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
                                self.empInfoTerminalModel().workLocationName(res.workLocationName.substring(0, 20));              
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
                    if(data.length <= 0){
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
            private loadEmpInfoTerminal(empInfoTerCode: string): void{
                let self = this;          
                service.getDetails(empInfoTerCode).done(function(empInfoTer: any){
                    if(empInfoTer){
                        self.isUpdateMode(true);
                        self.enableBtnDelete(true);
                        self.selectedCode(empInfoTer.empInfoTerCode);
                        self.empInfoTerminalModel().updateData(empInfoTer);
                        if (_.isNull(self.empInfoTerminalModel().nRConvertInfo())) {
                            self.empInfoTerminalModel().lstMSConversion()
                                .forEach((item: MSConversionDto) => {
                                    switch (item.stampClassifi) {
                                        case 0: 
                                            self.selectedIndex1(item.stampDestination);
                                            break;
                                        case 1: 
                                            self.selectedIndex2(item.stampDestination);
                                            break;
                                        case 2: 
                                            self.selectedIndex3(item.stampDestination);
                                            break;
                                        case 3: 
                                            self.selectedIndex4(item.stampDestination);
                                            break;
                                    }
                                })
                                self.goOutReason(10); // しない
                        } else {
                            // nr
                            if (self.empInfoTerminalModel().nRConvertInfo().outPlaceConvert.replace === 0) {
                                self.goOutReason(10);
                            } else {
                                self.goOutReason(self.empInfoTerminalModel().nRConvertInfo().outPlaceConvert.goOutReason);
                            }

                            if (!_.isNil(self.empInfoTerminalModel().nRConvertInfo().entranceExit)) {
                                if (self.empInfoTerminalModel().nRConvertInfo().entranceExit === 1) {
                                    self.entranceExit(true);
                                }
                            }
                        }
                        self.empInfoTerminalModel().isEnableCode(false);              
                    }else{
                        self.createNewMode();
                        self.isUpdateMode(false);
                    }
                });
            }

            /**
             * clear Data
             */
            private createNewMode(): void{
                let self = this;
                self.selectedCode(null);
                self.empInfoTerminalModel().resetData();
                self.enableBtnDelete(false);
                self.clearErrors();
                self.isUpdateMode(false);
                self.selectedIndex1(0);
                self.selectedIndex2(3);
                self.selectedIndex3(6);
                self.selectedIndex4(10);
                self.entranceExit(false);
                self.goOutReason(10);
                $('#A3_2').focus();
                self.enableBtnNew(false);
            }

            /**
             * regist Employment information terminal 
             */
            private registEmpInfoTerminal(): void{
                let self = this;
                if(self.hasError()){
                    return;
                }
                
                let command: any = {};
                command.empInfoTerCode = self.empInfoTerminalModel().empInfoTerCode();
                command.empInfoTerName = self.empInfoTerminalModel().empInfoTerName();
                command.modelEmpInfoTer = self.empInfoTerminalModel().modelEmpInfoTer();               
                command.macAddress = self.empInfoTerminalModel().macAddress();
                
                if(    !self.checkIpAddress(self.empInfoTerminalModel().ipAddress1())
                    || !self.checkIpAddress(self.empInfoTerminalModel().ipAddress2())
                    || !self.checkIpAddress(self.empInfoTerminalModel().ipAddress3())
                    || !self.checkIpAddress(self.empInfoTerminalModel().ipAddress4())
                ){
                    command.ipAddress1 = null;
                    command.ipAddress2 = null;
                    command.ipAddress3 = null;
                    command.ipAddress4 = null;
                } else {
                    command.ipAddress1 = self.empInfoTerminalModel().ipAddress1().toString();
                    command.ipAddress2 = self.empInfoTerminalModel().ipAddress2().toString();
                    command.ipAddress3 = self.empInfoTerminalModel().ipAddress3().toString();
                    command.ipAddress4 = self.empInfoTerminalModel().ipAddress4().toString();
                }
                command.terSerialNo = self.empInfoTerminalModel().terSerialNo();
                command.workLocationCode = self.empInfoTerminalModel().workLocationCode();
                command.intervalTime = self.empInfoTerminalModel().intervalTime();
                command.memo = self.empInfoTerminalModel().memo();
                command.workplaceId = self.empInfoTerminalModel().workplaceId();

                if (command.modelEmpInfoTer == 9) {
                    let lstMSConversion: Array<MSConversionDto> = [];

                    for (let i = 0; i < 4; i++) {
                        let stampDestination;
                        switch (i) {
                            case 0: 
                                stampDestination = self.selectedIndex1();
                                break;
                            case 1: 
                                stampDestination = self.selectedIndex2();
                                break;
                            case 2: 
                                stampDestination = self.selectedIndex3();
                                break;
                            case 3: 
                                stampDestination = self.selectedIndex4();
                                break;
                        }

                        let item = {
                            stampClassifi: i,
                            stampDestination
                        }

                        lstMSConversion.push(item);
                    }
                    command.lstMSConversion = lstMSConversion;
                } else {
                    let replace = 1;
                    if (self.goOutReason() === 10) {
                        replace = 0;
                    }
                    let outPlaceConvert = {
                        replace,
                        goOutReason: self.goOutReason()
                    }
                    let nrconvertInfo = {
                        outPlaceConvert,
                        entranceExit: self.entranceExit() ? 1 : 0
                    }
                    command.nrconvertInfo = nrconvertInfo;
                }

                blockUI.invisible();
                if(self.isUpdateMode() == false){
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
                        if (error.messageId === "Msg_1895") {
                            $('#A3_2').ntsError('set', {messageId: error.messageId});                       
                        } else if (error.messageId === "Msg_1931") {
                            $('#A3_8').ntsError('set', {messageId: error.messageId});                     
                        } else {
                            dialog.error({messageId: error.messageId});
                        }
                    }).always(()=>{
                        blockUI.clear();    
                    });
                    
                } else {
                    //登録ボタン押下（更新モード）
                    service.update(command).done(() => {
                        dialog.info({messageId: "Msg_15"}).then(() => {
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
                        if (error.messageId === "Msg_1931") {
                            $('#A3_8').ntsError('set', {messageId: error.messageId});                       
                        } else {
                            dialog.error({messageId: error.messageId});
                        }
                    }).always(()=>{
                        blockUI.clear();    
                    });
                }
                self.enableBtnNew(true);
            }
            /**
             * remove Employment information terminal
             * 削除ボタン押下
             */
            private removeEmpInfoTerminal(): void{
                let self = this;
                
                var delCode = self.empInfoTerminalModel().empInfoTerCode();
                if(self.hasError()){
                    return;
                }
                
                dialog.confirm({messageId:"Msg_18"})
                    .ifYes(() => {
                        var index = _.indexOf(self.empInfoTerminalList().map(e => e.empInfoTerCode), delCode);
                        let command = {
                            empInfoTerCode: delCode
                        };
                        
                        blockUI.invisible();
                        service.removeEmpInfoTer(command).done(()=>{
                            dialog.info({messageId:"Msg_16"}).then(() => {
                                self.reloadData(index);
                            });
                        }).fail((res)=>{
                            dialog.error({messageId: res.messageId}).then(()=>{
                                self.reloadData(0);
                            });
                        });
                    }).ifNo(function(){
                        blockUI.clear();
                        $('#A3_4').focus();      
                    });
            }

            private reloadData(index: number) {
                let self = this;
                service.getAll().done((data)=>{
                    if(data.length <= 0){
                        self.empInfoTerminalList([]);
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

                    blockUI.clear();
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
                let checkIP = true;
                self.clearErrors();
                
                //code
                $('#A3_2').ntsEditor("validate");
                //name
                $('#A3_4').ntsEditor("validate");
                //empModel
                $('#A3_6').ntsEditor("validate");
                //macAddress
                $('#A3_8').ntsEditor("validate");
                //serialNo
                $('#A5_2').ntsEditor("validate");
                //MonitorIntervalTime
                $('#A5_7').ntsEditor("validate");
                //Memo
                $('#A6_8').ntsEditor("validate");
                
                //  ipAddress
                if (!self.checkIpAddress(self.empInfoTerminalModel().ipAddress1())
                    && !self.checkIpAddress(self.empInfoTerminalModel().ipAddress2())
                    && !self.checkIpAddress(self.empInfoTerminalModel().ipAddress3())
                    && !self.checkIpAddress(self.empInfoTerminalModel().ipAddress4())) {

                } else if(!self.checkIpAddress(self.empInfoTerminalModel().ipAddress1())
                    || !self.checkIpAddress(self.empInfoTerminalModel().ipAddress2())
                    || !self.checkIpAddress(self.empInfoTerminalModel().ipAddress3())
                    || !self.checkIpAddress(self.empInfoTerminalModel().ipAddress4())
                ) {
                    if(_.isNull(self.empInfoTerminalModel().ipAddress1()) || self.empInfoTerminalModel().ipAddress1().toString() == "")  $('#IP1').ntsError('set', {messageId: 'Msg_2036'});
                    if(_.isNull(self.empInfoTerminalModel().ipAddress2()) || self.empInfoTerminalModel().ipAddress2().toString() == ""  $('#IP2').ntsError('set', {messageId: 'Msg_2036'});
                    if(_.isNull(self.empInfoTerminalModel().ipAddress3()) || self.empInfoTerminalModel().ipAddress3().toString() == "")  $('#IP3').ntsError('set', {messageId: 'Msg_2036'});
                    if(_.isNull(self.empInfoTerminalModel().ipAddress4()) || self.empInfoTerminalModel().ipAddress4().toString() == "")  $('#IP4').ntsError('set', {messageId: 'Msg_2036'});

                    return true;
                }

                if ($('.nts-input').ntsError('hasError')
                    || $("#A3_6").ntsError("hasError")) {
                    return true;
                }

                return false;
            }
            /**
             * Check IpAddress
             */
            private checkIpAddress(ipAddress: number): boolean{
                if(_.isNil(ipAddress) || ipAddress.toString().length == 0){
                    return false;    
                }
                return true;
            }
            /**
             * clear Errors
             */
            private clearErrors(): void{     
                $('#A3_2').ntsError('clear');
                $('#A3_4').ntsError('clear');
                $('#A3_8').ntsError('clear');
                $('#A5_2').ntsError('clear');
                $('#A5_7').ntsError('clear');
                $('#A6_8').ntsError('clear');
                $('.nts-input').ntsError('clear');
                nts.uk.ui.errors.clearAll();
            }
        }

        /**
         * Employment information terminal Model
         */

         class ItemModel1 {
            index: number;
            name: string;
        
            constructor(index: number, name: string) {
                this.index = index;
                this.name = name;
            }
        }

        export interface MSConversionDto {
            stampClassifi: number;
            stampDestination: number;
        }

        export interface NRConvertInfoDto {
            outPlaceConvert: OutPlaceConvertDto;
            entranceExit: number;
        }

        export interface OutPlaceConvertDto {
            replace: number;
            goOutReason: number;
        }

        export class EmpInfoTerminalModel{
            //  端末No
            empInfoTerCode: KnockoutObservable<string>;
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
            enableOutingClass: KnockoutObservable<boolean>;
            checkedOutingClass: KnockoutObservable<boolean>;
            //  外出区分: combobox
            outingClassList: KnockoutObservableArray<ItemModel>;
            checkedEntranExit: KnockoutObservable<boolean>;
            //  メモ
            memo: KnockoutObservable<string>;

            lstMSConversion: KnockoutObservableArray<MSConversionDto>;
            nRConvertInfo: KnockoutObservable<NRConvertInfoDto>;
            workplaceId: KnockoutObservable<string>;
            workplaceName: KnockoutObservable<string>;  
            isEnableCode: KnockoutObservable<boolean>;
            date: KnockoutObservable<string> = ko.observable('');
            useAtr: boolean = false;

            constructor(){
                this.empInfoTerCode =  ko.observable(null);
                this.empInfoTerName =  ko.observable('');
                this.empInfoTerminalModelList = ko.observableArray([	
                                                new ItemModel(null, '未選択'),		
                                                new ItemModel(7, 'NRL-1'),			
                                                new ItemModel(8, 'NRL-2'),			
                                                new ItemModel(9, 'NRL-m/ms')				
                                            ]); 
                
                this.lstMSConversion =  ko.observableArray([]);
                this.nRConvertInfo = ko.observable(null);
                this.workplaceId = ko.observable('');
                this.workplaceName = ko.observable('')

                this.modelEmpInfoTer =  ko.observable(null);
                
                this.macAddress =  ko.observable('');
                
                this.ipAddress1 =  ko.observable(null);
                this.ipAddress2 =  ko.observable(null);
                this.ipAddress3 =  ko.observable(null);
                this.ipAddress4 =  ko.observable(null);
                
                this.terSerialNo =  ko.observable(null);
                this.workLocationCode =  ko.observable('');
                this.workLocationName =  ko.observable('');
                this.intervalTime =  ko.observable(null);
                this.enableOutingClass = ko.observable(true);
                this.checkedOutingClass = ko.observable(false);

                this.outingClassList = ko.observableArray([			
                                                new ItemModel(10, getText('KNR001_53')),			
                                                new ItemModel(0, getText('KNR001_54')),			
                                                new ItemModel(1, getText('KNR001_55')),	
                                                new ItemModel(2, getText('KNR001_56')),	
                                                new ItemModel(3, getText('KNR001_57'))
                                            ]); 
                this.memo =  ko.observable('');  
               
                this.isEnableCode =  ko.observable(true);     
                
            }
            /**
             * reset Data
             */
            resetData(){
                this.empInfoTerCode(null);
                this.empInfoTerName('');
                this.modelEmpInfoTer(null);
                this.macAddress('');
                //this.ipAddress('');
                this.ipAddress1(null);
                this.ipAddress2(null);
                this.ipAddress3(null);
                this.ipAddress4(null);
                this.terSerialNo(null);
                this.workLocationCode('');
                this.workLocationName('');
                this.intervalTime(null);
                this.memo('');  
                this.isEnableCode(true);
                this.enableOutingClass(true);
                this.checkedOutingClass(false);
                this.workplaceId('');
                this.workplaceName('');
                this.nRConvertInfo(null);
            }
            /**
             * update Data
             */
            updateData(dto: any){
                this.empInfoTerCode(dto.empInfoTerCode);
                this.empInfoTerName(dto.empInfoTerName);
                this.modelEmpInfoTer(dto.modelEmpInfoTer);
                this.macAddress(dto.macAddress);
                this.ipAddress1(dto.ipAddress1);               
                this.ipAddress2(dto.ipAddress2);
                this.ipAddress3(dto.ipAddress3);
                this.ipAddress4(dto.ipAddress4);
                this.terSerialNo(dto.terSerialNo);
                this.workLocationCode(dto.workLocationCode);
                this.workLocationName(dto.workLocationName.substring(0, 20));
                this.intervalTime(dto.intervalTime);
                this.checkedOutingClass(dto.outSupport == 1? true : false);
                this.memo(dto.memo);
                this.lstMSConversion(dto.lstMSConversion);
                this.nRConvertInfo(dto.nrconvertInfo);
                
                if (this.useAtr) {
                    this.workplaceId(dto.workplaceId);
                    this.workplaceName(dto.workplaceName);
                }
            }
            /**
             * Show Dialog Work Location
             * 勤務場所選択ダイアログ（KDL010）の表示　場所選択のボタン押下
             */
            callKDL010() {
                var self = this;
                blockUI.invisible();
                nts.uk.ui.windows.setShared('KDL010SelectWorkLocation', self.workLocationCode());
                nts.uk.ui.windows.sub.modal("/view/kdl/010/a/index.xhtml", { dialogClass: "no-close" }).onClosed(() => {
                    var self = this;
                    var returnWorkLocationCD = nts.uk.ui.windows.getShared("KDL010workLocation");
                    self.workLocationCode(returnWorkLocationCD !== undefined? returnWorkLocationCD : "");
                    blockUI.clear();
                });
            }

            callCDL008() {
                const self = this;
                if (self.date().length === 0) {
                    dialog.error({messageId: "Msg_2139"}).then(() => {
                        $('#A7_5').focus();
                    });
                    return;
                }
                blockUI.invisible();
                setShared('inputCDL008', {
                    selectedCodes: _.isNil(self.workplaceId()) ? '' : self.workplaceId,
                    baseDate: moment(self.date()).toDate(),
                    isMultiple: false,
                    selectedSystemType: 2,
                    isrestrictionOfReferenceRange: false,
                    showNoSelection: false,
                    isShowBaseDate: false,
                    startMode: 0
                });
                modal('com', '/view/cdl/008/a/index.xhtml').onClosed(() => {
                    let output = getShared('outputCDL008');
                    let workplaceInfor = getShared('workplaceInfor')[0];
                    self.workplaceId(output);
                    self.workplaceName(workplaceInfor.displayName);
                    
                    blockUI.clear();
                });
            }

            displayButtonHandle() {
                const self = this;
                moment.locale('en'); 
                let input = {
                    listWorkPlaceId: [self.workplaceId()],
                    baseDate: moment(self.date()).format("YYYY/MM/DD")
                };
                service.getWorkplaceNameChangingBaseDate(input).done((res: Array<GetWorkplaceNameChangingBaseDateDto>) => {
                    if (res[0].displayName !== '' && res[0].displayName !== 'コード削除済') {
                        self.workplaceName(res[0].displayName);
                    } else {
                        if (res[0].workplaceId == null) {
                            self.workplaceName('')
                        } else {
                            self.workplaceName('「本日日付」' + getText('KNR001_183'));
                        }
                    }
                })
                .fail(() => {})
                .done(() => blockUI.clear());
            }
        }

        export interface GetWorkplaceNameChangingBaseDateDto {
            workplaceId: string;
            hierarchyCode: string;
            workplaceCode: string;
            workplaceName: string;
            displayName: string;
            genericName: string;
            externalCode: string;
        }
        class EmpInfoListDto{
            empInfoTerCode: string;
            empInfoTerName: string;
            constructor(empInfoTerCode: string, empInfoTerName: string){
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