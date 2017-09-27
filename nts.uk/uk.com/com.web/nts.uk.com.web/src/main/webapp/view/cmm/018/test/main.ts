module nts.uk.com.view.cmm018.test.viewmodel{
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import windows = nts.uk.ui.windows;
    import shrVm = cmm018.shr.vmbase;
    export class ScreenModel{
        approverInfor : KnockoutObservableArray<string> = ko.observableArray([]);
        confirmedPerson : KnockoutObservable<string> = ko.observable("");
        selectTypeSet : KnockoutObservable<number> = ko.observable(0);
        //test
        openDialogK() {
            var self = this;
            //承認者
            self.approverInfor.removeAll();
            self.approverInfor.push("CE82367D-929C-4872-A51C-12BE4426EB6C");
            self.approverInfor.push("CE82367D-929C-4872-A51C-12BE4426EC6C");

            //確定者 code
            self.confirmedPerson('A00000000001');
            
            setShared("CMM018K_PARAM", { 
                                        appType: "0", //設定する対象申請名 
                                        formSetting: 0,//0: 全員確認、１：誰か一人
                                        approverInfor: self.approverInfor(),//承認者一覧
                                        confirmedPerson: self.confirmedPerson, //確定者
                                        selectTypeSet: 0, //職位指定（1）、個人指定（0）
                                        tab: 0//０：会社、１：職場、２：個人
                                        });
            windows.sub.modal("/view/cmm/018/k/index.xhtml", { title: "", dialogClass: "no-close" });    
            
        }
        
        openDialogL(){
           windows.sub.modal("/view/cmm/018/l/index.xhtml", { title: "", dialogClass: "no-close" });   
        }
        openDialogM(){
           windows.sub.modal("/view/cmm/018/m/index.xhtml", { title: "", dialogClass: "no-close" });
        }
        openDialogN(){
           windows.sub.modal("/view/cmm/018/n/index.xhtml", { title: "", dialogClass: "no-close" });
        }
    }   
}