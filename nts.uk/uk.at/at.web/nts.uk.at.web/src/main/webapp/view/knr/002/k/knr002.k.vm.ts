module knr002.k {
    import blockUI = nts.uk.ui.block;
    import dialog = nts.uk.ui.dialog;
    import alertError = nts.uk.ui.alertError;
    import getText = nts.uk.resource.getText;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;

    export module viewmodel{
        export class ScreenModel{
            bentoMenu: KnockoutObservableArray<BentoMenuItem>;
            selectedList: KnockoutObservableArray<number>;
            empInfoTerCode: string;
            
            constructor(){
                var self = this;  
                self.bentoMenu = ko.observableArray<BentoMenuItem>([]);
                self.selectedList = ko.observableArray<number>([]);
                self.empInfoTerCode = '';
            }
            /**
             * Start Page
             * 起動する
             */
            public startPage(): JQueryPromise<void>{
                var self = this;										
                var dfd = $.Deferred<void>();
                blockUI.invisible();
                self.empInfoTerCode = getShared('KNR002K_empInfoTerCode');

                if(self.empInfoTerCode == ''){
                    self.bentoMenu([]);
                    self.selectedList([]);
                }else{
                    console.log("empInfoTerCode: ", self.empInfoTerCode);
                    service.getBentoMenu(self.empInfoTerCode).done((data)=>{
                        console.log("data: ", data);
                        if(!data){
                            //do something
                        }else{
                            //  self.bentoMenu(data.bentoMenuList);
                            //  self.selectedList(data.bentoMenu);
                            let bentoMenuTemp = [];
                            for(let item of data.bentoMenuList){
                                let bentoTemp = new BentoMenuItem(item.bentoMenuFrameNumber, item.bentoMenuName);
                                bentoMenuTemp.push(bentoTemp);
                            } 
                            self.bentoMenu(bentoMenuTemp);
                            let selectedListTemp = [];
                            for(let item of data.bentoMenu){
                                selectedListTemp.push(item);
                            }
                            self.selectedList(selectedListTemp);
                        }
                    });
                    console.log("bentoMenu", self.bentoMenu());
                    console.log("selectedList: ", self.selectedList());
                }
                blockUI.clear();   																			
                dfd.resolve();											
                return dfd.promise();											
            }
            /**
             * cancel_Dialog
             */
            private cancel_Dialog(): any {
                let self = this;
                nts.uk.ui.windows.close();
            }
   
        }
        class BentoMenuItem{
            frameNumber: number;
            bentoName: string;
            constructor(frameNumber: number, bentoName: string){
                this.frameNumber = frameNumber;
                this.bentoName = bentoName;
            }
        }
    }
}