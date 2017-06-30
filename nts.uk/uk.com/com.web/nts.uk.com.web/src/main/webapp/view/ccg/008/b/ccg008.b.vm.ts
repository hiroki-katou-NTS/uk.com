 module nts.uk.com.view.ccg008.b.viewmodel {  
    export class ScreenModel {
        checkTopPage: KnockoutObservable<string>;
        checkMyPage: KnockoutObservable<string>;
        constructor() {
            var self = this;
            self.checkTopPage = ko.observable(null);
            self.checkMyPage = ko.observable(null);
            self.start();
        }
        start(){
            var self = this;
            self.checkTopPage(nts.uk.ui.windows.getShared('checkTopPage'));
            self.checkMyPage(nts.uk.ui.windows.getShared('checkMyPage'));
        }
        closeDialog() {
             nts.uk.ui.windows.close();   
        }        
        positionHis() {
             nts.uk.ui.windows.close();   
        }
        openTopPageSet(): void{
            nts.uk.ui.windows.sub.modal("/view/ccg/008/c/index.xhtml");
        }
        openMyPageSet(): void{
            nts.uk.ui.windows.sub.modal("/view/ccg/031/a/index.xhtml");
        }
    }
}