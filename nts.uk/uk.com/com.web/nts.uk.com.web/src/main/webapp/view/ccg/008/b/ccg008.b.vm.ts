 module nts.uk.com.view.ccg008.b.viewmodel {  
  import commonModel = ccg.model;
    export class ScreenModel {
        checkTopPage: boolean;
        checkMyPage: boolean;
        transferData: KnockoutObservable<commonModel.TransferLayoutInfo>;
        constructor() {
            var self = this;
            self.checkTopPage = true;
            self.checkMyPage = true;
            self.transferData = ko.observable(null);
            self.start();
        }
        start(){
            var self = this;
            $("#btn_close").focus();
            self.transferData(nts.uk.ui.windows.getShared('CCG008_layout'));
            self.checkTopPage = nts.uk.ui.windows.getShared('checkTopPage');
            self.checkMyPage = nts.uk.ui.windows.getShared('checkMyPage');
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
            var self = this;
            nts.uk.ui.windows.setShared('layout', self.transferData());
            nts.uk.ui.windows.sub.modal("/view/ccg/031/a/index.xhtml");
        }
    }
}