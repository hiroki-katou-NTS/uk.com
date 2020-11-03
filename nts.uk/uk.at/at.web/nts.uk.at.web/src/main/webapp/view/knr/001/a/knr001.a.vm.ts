module nts.uk.at.view.knr001.a{
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import showDialog = nts.uk.ui.dialog;
    import Text = nts.uk.resource.getText;
    import blockUI = nts.uk.ui.block;

    export module viewmodel{
        export class ScreenModel{
            enableBtnNew: KnockoutObservable<boolean>;
            enableBtnDelete: KnockoutObservable<boolean>; 
            isUpdateMode: KnockoutObservable<boolean>;
            empInfoTerminalModel: KnockoutObservable<EmpInfoTerminalModel>;
            selectedCode: KnockoutObservable<string>;
            empInfoTerminalList: KnockoutObservableArray<ItemModel>;
         
            constructor(){
                var self = this;
                self.enableBtnNew = ko.observable(true);
                self.enableBtnDelete = ko.observable(false);
                self.isUpdateMode = ko.observable(false);
                self.empInfoTerminalModel = ko.observable(new EmpInfoTerminalModel);
                self.empInfoTerminalList = ko.observableArray([		
                                                new ItemModel('1', 'Item_4'),		
                                                new ItemModel('2', 'Item_5'),		
                                                new ItemModel('3', 'Item_6')		
                                            ]);	

                self.selectedCode = ko.observable("");
                
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
            }
            /**
             * Start Page
             */
            public startPage(): JQueryPromise<void>{
                var self = this;										
                var dfd = $.Deferred<void>();
                service.getAll.done((data)=>{
                    if(data.length<=0){
                        self.createNewMode();
                    } esle {
                        
                    }
                });   																			
                dfd.resolve();											
                return dfd.promise();											
            }
            /**
             * load Employment information terminal
             */
            private loadEmpInfoTerminal(code: string): void{
                
            }

            /**
             * clear Data
             */
            private createNewMode(): void{

            }

            /**
             * regist Employment information terminal 
             */
            private registEmpInfoTerminal(): void{

            }
            /**
             * remove Employment information terminal
             */
            private removeEmpInfoTerminal(): void{

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
            this.ipAddress1 =  ko.observable('1');
            this.ipAddress2 =  ko.observable('2');
            this.ipAddress3 =  ko.observable('3');
            this.ipAddress4 =  ko.observable('4');
            this.terSerialNo =  ko.observable('12345678');
            this.workLocationCode =  ko.observable("");
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

            }
            /**
             * update Data
             */
            updateData(dto: any){

            }
            /**
             * Show Dialog Work Location
             */
            callKDL010(){
                

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