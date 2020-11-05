module nts.uk.at.view.knr001.a{
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
                self.enableBtnDelete = ko.observable(false);
                self.isUpdateMode = ko.observable(false);
                self.empInfoTerminalModel = ko.observable(new EmpInfoTerminalModel);
                self.empInfoTerminalList = ko.observableArray<EmpInfoListDto>([]);
                self.selectedCode = ko.observable('');
                self.selectedCode.subscribe(function(empInfoTerminalCode){
                    if(empInfoTerminalCode){
                        self.clearErrors();
                        self.enableBtnDelete(true);
                       // self.loadEmpInfoTerminal(empInfoTerminalCode);
                    } else {
                        self.createNewMode();
                        self.enableBtnDelete(false);
                    }
                });
                self.empInfoTerminalModel().outSupport.subscribe(function(){

                });
            }
            /**
             * Start Page
             */
            public startPage(): JQueryPromise<void>{
                var self = this;										
                var dfd = $.Deferred<void>();
                
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
                dfd.resolve();											
                return dfd.promise();											
            }
            /**
             * load Employment information terminal
             */
            private loadEmpInfoTerminal(empInfoTerCode: number): void{
                let self = this;
                //find one
                service.getDetails(empInfoTerCode).done(function(empInfoTer: any){
                    if(empInfoTer){
                        self.isUpdateMode(true);
                        self.enableBtnDelete(true);
                        self.selectedCode(empInfoTer.empInfoTerCode);
                        self.empInfoTerminalModel().updateData(empInfoTer);
                        self.empInfoTerminalModel().isEnableCode(false);              
                    }
                });
            }

            /**
             * clear Data
             */
            private createNewMode(): void{
                let self = this;
                self.selectedCode("");
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
                let ipAddress = self.empInfoTerminalModel().ipAddress1().concat(self.empInfoTerminalModel().ipAddress2(), self.empInfoTerminalModel().ipAddress3(), self.empInfoTerminalModel().ipAddress4());
                let command: any = {};
                command.empInfoTerCode = self.empInfoTerminalModel().empInfoTerCode();
                command.empInfoTerName = self.empInfoTerminalModel().empInfoTerName();
                command.modelEmpInfoTer = self.empInfoTerminalModel().modelEmpInfoTer();
                command.macAddress = self.empInfoTerminalModel().macAddress();
                command.ipAddress = ipAddress;
                command.terSerialNo = self.empInfoTerminalModel().terSerialNo();
                command.workLocationCode = self.empInfoTerminalModel().workLocationCode();
                command.intervalTime = self.empInfoTerminalModel().intervalTime();
                command.outSupport = self.empInfoTerminalModel().checkedOutingSupportClass();
                command.replace = self.empInfoTerminalModel().replace();
                command.goOutReason = self.empInfoTerminalModel().goOutReason();
                command.entranceExit = self.empInfoTerminalModel().entranceExit();

                service.register(command).done(()=>{
                    blockUI.invisible();
                });


            }
            /**
             * remove Employment information terminal
             */
            private removeEmpInfoTerminal(): void{
                let self = this;
                if(self.hasError()){
                    return;
                }
                dialog.confirm({messageId:"Msg_18"}).ifYes(()=>{
                    var delCode = self.empInfoTerminalModel().empInfoTerCode();
                    var index = self.empInfoTerminalList().indexOf(self.empInfoTerminalList().find(empInfoTer => delCode == empInfoTer.empInfoTerCode));
                    let command = {
                        empInfoTerCode: delCode
                    }
                    blockUI.invisible();
                    service.removeEmpInfoTer(command).done(()=>{
                        dialog.info({messageId:"Msg_16"}).then(function(){
                            //reload
                        });
                    });
                })

            }
             /**
             * export Excel
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
                return false;
            }
            /**
             * clear Errors
             */
            private clearErrors(): void{

            }
        }

        /**
         * Employment information terminal Model
         */
        export class EmpInfoTerminalModel{
            empInfoTerCode: KnockoutObservable<number>;
            empInfoTerName: KnockoutObservable<string>;
            empInfoTerminalModelList: KnockoutObservableArray<ItemModel>;
            modelEmpInfoTer: KnockoutObservable<number>;
            macAddress: KnockoutObservable<string>;
            ipAddress: KnockoutObservable<string>;
            ipAddress1: KnockoutObservable<number>;
            ipAddress2: KnockoutObservable<number>;
            ipAddress3: KnockoutObservable<number>;
            ipAddress4: KnockoutObservable<number>;
            terSerialNo: KnockoutObservable<number>;
            workLocationCode: KnockoutObservable<string>;
            workLocationName: KnockoutObservable<string>;
            intervalTime: KnockoutObservable<number>;
            outingClassList: KnockoutObservableArray<ItemModel>;
            outSupport: KnockoutObservable<number>;
            enableOutingClass: KnockoutObservable<boolean>;
            replace: KnockoutObservable<number>;
            goOutReason: KnockoutObservable<number>;
            entranceExit: KnockoutObservable<number>; 
            memo: KnockoutObservable<string>;   
            isEnableCode: KnockoutObservable<boolean>;
   
            checkedOutingClass: KnockoutObservable<boolean>;
            enableOutingSupportClass: KnockoutObservable<boolean>;
            checkedOutingSupportClass: KnockoutObservable<boolean>;

            constructor(){
            this.empInfoTerCode =  ko.observable('');
            this.empInfoTerName =  ko.observable('');
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
            this.intervalTime =  ko.observable('');
            this.outSupport =  ko.observable('');
            this.replace =  ko.observable('');
            this.goOutReason =  ko.observable('');
            this.entranceExit =  ko.observable(''); 
            this.memo =  ko.observable('');  
            this.isEnableCode =  ko.observable(true);
            this.empInfoTerminalModelList = ko.observableArray([			
                                            new ItemModel(7, 'NRL_1'),			
                                            new ItemModel(8, 'NRL_2'),			
                                            new ItemModel(9, 'NRL_M')				
                                        ]);              			
            this.modelEmpInfoTer = ko.observable('');
            this.outingClassList = ko.observableArray([			
                                            new ItemModel(0, getText('＃KNR001_53')),			
                                            new ItemModel(1, getText('＃KNR001_54')),			
                                            new ItemModel(2, getText('＃KNR001_55')),	
                                            new ItemModel(3, getText('＃KNR001_56')),	
                                            new ItemModel(4, getText('＃KNR001_57'))
                                        ]); 
            this.enableOutingClass = ko.observable(true);
            this.enableOutingSupportClass = ko.observable(true);
            this.checkedOutingClass = ko.observable(true);
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
            this.empInfoTerminalModelList([]);
            this.outingClassList([]);
            this.modelEmpInfoTer('');
            this.enableOutingClass(true);
            this.enableOutingSupportClass(true);
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
                this.ipAddress1 = arrIpAddress[0];
                this.ipAddress2 = arrIpAddress[1];
                this.ipAddress3 = arrIpAddress[2];
                this.ipAddress4 = arrIpAddress[3];
                this.terSerialNo(dto.terSerialNo);
                this.workLocationName(dto.workLocationName);
                this.intervalTime(dto.intervalTime);
                this.outSupport(dto.outSupport);
                this.replace(dto.replace);
                this.goOutReason(dto.goOutReason);
                this.entranceExit(dto.entranceExit);
                this.memo(dto.memo);
            }
            /**
             * Show Dialog Work Location
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
                    nts.uk.ui.block.clear();
                }
                else{
                    self.workLocationCode = ko.observable("");
                    nts.uk.ui.block.clear();
                }
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