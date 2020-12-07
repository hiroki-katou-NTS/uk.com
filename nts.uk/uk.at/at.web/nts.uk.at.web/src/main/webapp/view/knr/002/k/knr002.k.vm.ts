module knr002.k {
    import blockUI = nts.uk.ui.block;
    import dialog = nts.uk.ui.dialog;
    import alertError = nts.uk.ui.alertError;
    import getText = nts.uk.resource.getText;

    export module viewmodel{
        export class ScreenModel{
            destinationCopyList: KnockoutObservableArray<ReservationItem>;
            selectedList: KnockoutObservableArray<string>;
            
            constructor(){
                var self = this;  
                self.destinationCopyList = ko.observableArray<ReservationItem>([]);
                self.selectedList = ko.observableArray<string>([]);
            }
            /**
             * Start Page
             * 起動する
             */
            public startPage(): JQueryPromise<void>{
                var self = this;										
                var dfd = $.Deferred<void>();
                blockUI.invisible();
                service.getDestinationCopyList('1').done((data)=>{
                    if(data.length < 0){
                        //do something
                    }else{
                        let desCopyTempList = [];
                        for(let item of data){
                            let desCopyTemp = new ReservationItem(item.empInfoTerCode, item.empInfoTerName);
                            desCopyTempList.push(desCopyTemp);
                        }
                        self.destinationCopyList(desCopyTempList);
                    }
                });
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
        class ReservationItem{
            code: string;
            name: string;
            constructor(code: string, name: string){
                this.code = code;
                this.name = name;
            }
        }
    }
}