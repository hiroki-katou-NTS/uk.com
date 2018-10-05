module nts.uk.com.view.cmm018.l {
    export module viewmodel {
        export class ScreenModel {
            date: KnockoutObservable<Date>;
            constructor() {
                var self = this;
                self.date =ko.observable(moment(new Date()).toDate())
            }
            //閉じるボタン
            closeDialog(){
                nts.uk.ui.windows.close();    
            }
            //Excel出力
            printExcel(){
                if(nts.uk.ui.errors.hasError()) { return; }
                var self = this;
                nts.uk.ui.block.grayout();
                service.saveExcel(self.date())
                .done(function(){nts.uk.ui.block.clear();})
                .fail(function(res: any){
                    nts.uk.ui.dialog.alertError({ messageId: res.messageId});  
                    nts.uk.ui.block.clear();
                })
            }
        }
    }
}
