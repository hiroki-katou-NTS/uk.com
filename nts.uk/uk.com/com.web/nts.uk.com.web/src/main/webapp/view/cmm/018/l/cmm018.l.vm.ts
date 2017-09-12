module nts.uk.at.view.cmm018.l {
    export module viewmodel {
        export class ScreenModel {
            date: KnockoutObservable<string>;
            constructor() {
                var self = this;
                //self.date = ko.observable('20000101');
                var currentDate = (new Date()).toISOString().split('T')[0];
                self.date = ko.observable(currentDate);
            }
            //閉じるボタン
            closeDialog(){
                nts.uk.ui.windows.close();    
            }
            //Excel出力
            printExcel(){
                
            }
        }
    }
}
