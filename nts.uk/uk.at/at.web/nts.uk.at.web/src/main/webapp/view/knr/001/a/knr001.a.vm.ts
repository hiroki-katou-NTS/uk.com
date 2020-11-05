module nts.uk.at.view.knr001.a{
    import blockUI = nts.uk.ui.block;
    import dialog = nts.uk.ui.dialog;

    export module viewmodel{
        export class ScreenModel{
            enableBtnNew: KnockoutObservable<boolean>;
            enableBtnDelete: KnockoutObservable<boolean>; 
            isUpdateMode: KnockoutObservable<boolean>;
            empInfoTerminalModel: KnockoutObservable<EmpInfoTerminalModel>;
            selectedCode: KnockoutObservable<string>;
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
                        console.log(self.empInfoTerminalList());
                       // self.loadEmpInfoTerminal(self.selectedCode());
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
                service.getDetails(empInfoTerCode, self.workLocationCD).done(function(empInfoTer: any){
                    if(empInfoTer){
                        self.isUpdateMode(true);
                        self.selectedCode(empInfoTer.empInfoTerCode);
                        self.empInfoTerminalModel().updateData(empInfoTer);
                        self.empInfoTerminalModel().isEnableCode(false);
                        self.enableBtnDelete(true);
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
            private exportExcel(): void{
                var self = this;
                blockUI.grayout();
                let langId = "ja";
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
            modelEmpInfoTer: KnockoutObservable<number>;
            macAddress: KnockoutObservable<string>;
            ipAddress1: KnockoutObservable<number>;
            ipAddress2: KnockoutObservable<number>;
            ipAddress3: KnockoutObservable<number>;
            ipAddress4: KnockoutObservable<number>;
            terSerialNo: KnockoutObservable<number>;
            workLocationCode: KnockoutObservable<string>;
            workLocationName: KnockoutObservable<string>;
            intervalTime: KnockoutObservable<number>;
            convertOuting: KnockoutObservable<string>;
            replace: KnockoutObservable<string>;
            goOutReason: KnockoutObservable<number>;
            entranceExit: KnockoutObservable<number>; 
            memo: KnockoutObservable<string>;   
            isEnableCode: KnockoutObservable<boolean>;
            empInfoTerminalModelList: KnockoutObservableArray<ItemModel>;
            outingClassificationList: KnockoutObservableArray<ItemModel>;
            selectedModelCode: KnockoutObservable<string>;
            selectedOutingClass: KnockoutObservable<string>;
            enableOutingClass: KnockoutObservable<boolean>;
            checkedOutingClass: KnockoutObservable<boolean>;
            enableOutingSupportClass: KnockoutObservable<boolean>;
            checkedOutingSupportClass: KnockoutObservable<boolean>;

            constructor(){
            this.empInfoTerCode =  ko.observable("");
            this.empInfoTerName =  ko.observable("");
            this.modelEmpInfoTer =  ko.observable("");
            this.macAddress =  ko.observable("");
            this.ipAddress1 =  ko.observable('');
            this.ipAddress2 =  ko.observable('');
            this.ipAddress3 =  ko.observable('');
            this.ipAddress4 =  ko.observable('');
            this.terSerialNo =  ko.observable('');
            this.workLocationCode =  ko.observable("");
            this.workLocationName =  ko.observable("");
            this.intervalTime =  ko.observable("");
            this.convertOuting =  ko.observable("");
            this.replace =  ko.observable("");
            this.goOutReason =  ko.observable("");
            this.entranceExit =  ko.observable(""); 
            this.memo =  ko.observable("");  
            this.isEnableCode =  ko.observable(true);
            this.empInfoTerminalModelList = ko.observableArray<ItemModel>([]);
            this.outingClassificationList = ko.observableArray<ItemModel>([]);
            this.selectedModelCode = ko.observable('1');
            this.selectedOutingClass = ko.observable('1');
            this.enableOutingClass = ko.observable(true);
            this.enableOutingSupportClass = ko.observable(true);
            }
            /**
             * reset Data
             */
            resetData(){
            this.empInfoTerCode('');
            this.empInfoTerName('');
            this.modelEmpInfoTer('');
            this.macAddress('');
            this.ipAddress1('');
            this.ipAddress2('');
            this.ipAddress3('');
            this.ipAddress4('');
            this.terSerialNo('');
            this.workLocationCode('');
            this.intervalTime('');
            this.convertOuting('');
            this.replace('');
            this.goOutReason('');
            this.entranceExit(''); 
            this.memo('');  
            this.isEnableCode(true);
            this.empInfoTerminalModelList([]);
            this.outingClassificationList([]);
            this.selectedModelCode('');
            this.selectedOutingClass('');
            this.enableOutingClass(true);
            this.enableOutingSupportClass(true);
            }
            /**
             * update Data
             */
            updateData(dto: any){

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
            code: string;
            name: string;
            constructor(code: string, name: string){
                this.code = code;
                this.name = name;   
            }
        }
    }
}