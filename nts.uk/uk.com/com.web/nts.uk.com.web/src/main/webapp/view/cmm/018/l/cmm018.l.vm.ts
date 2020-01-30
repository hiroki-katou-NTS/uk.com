module nts.uk.com.view.cmm018.l {
    export module viewmodel {
        export class ScreenModel {
            date: KnockoutObservable<Date>;
            sysAtr: number;
            constructor() {
                let self = this;
                self.date =ko.observable(moment(new Date()).toDate());
                self.sysAtr = nts.uk.ui.windows.getShared('CMM018_SysAtr').sysAtr || 0;
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
                service.saveExcel({sysAtr: self.sysAtr, baseDate: self.date()})
                .done(function(){nts.uk.ui.block.clear();})
                .fail(function(res: any){
                    nts.uk.ui.dialog.alertError({ messageId: res.messageId});  
                    nts.uk.ui.block.clear();
                })
            }
        }
    }
}
