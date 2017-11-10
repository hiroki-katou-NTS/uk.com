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
                var self = this;
                service.saveExcel(self.date())
                .done(function(){})
                .fail(function(res: any){
                      nts.uk.ui.dialog.alertError({ messageId: res.messageId});  
                })
            }
        }
    }
}
