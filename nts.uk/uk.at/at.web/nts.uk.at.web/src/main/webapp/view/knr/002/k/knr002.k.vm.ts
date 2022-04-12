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
            isCancel: boolean;
            
            constructor(){
                var self = this;  
                self.bentoMenu = ko.observableArray<BentoMenuItem>([]);
                self.selectedList = ko.observableArray<number>([]);
                self.empInfoTerCode = '';
                self.isCancel = true;
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
                    service.getBentoMenu(self.empInfoTerCode).done((data)=>{
                        if(!data){
                            //do something
                        }else if(data.bentoMenuList){
                            let bentoMenuTemp = [];
                            for(let item of data.bentoMenuList){
                                let bentoTemp = new BentoMenuItem(item.bentoMenuFrameNumber, item.bentoMenuName);
                                bentoMenuTemp.push(bentoTemp);
                            } 
                            self.bentoMenu(_.sortBy(bentoMenuTemp,[function(u:BentoMenuItem){return u.frameNumber}]));
                            let selectedListTemp = [];
                            for(let item of data.bentoMenu){
                                selectedListTemp.push(item);
                            }
                            if(selectedListTemp.length > 0){
                                self.selectedList(selectedListTemp);
                            } else if (self.bentoMenu().length > 0){
                               // self.selectedList.push(self.bentoMenu()[0].frameNumber);
                            }
                        }
                    });
                }
                setTimeout(() => {
                    $('#multi-list > tbody > tr:nth-child(1)').focus();
                }, 25);
                blockUI.clear();   																			
                dfd.resolve();											
                return dfd.promise();											
            }
            /**
             * K4_1
             * 決定ボタン
             */
            private enter(): any{
                let self = this;
                setShared('KNR002K_isCancel', false);
                if(!self.selectedList() || self.selectedList().length <= 0){
                    dialog.error({ messageId: "Msg_2026" }).then(() => {
                        blockUI.clear();
                    });
                }else {
                    setShared('KNR002K_selectedList', self.selectedList());
                    nts.uk.ui.windows.close();
                }
                
            }
            /**
             * cancel_Dialog
             */
            private cancel_Dialog(): any {
                setShared('KNR002K_isCancel', true);
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