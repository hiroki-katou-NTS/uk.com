module nts.uk.com.view.cmm018.test.viewmodel{
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import windows = nts.uk.ui.windows;
    import shrVm = cmm018.shr.vmbase;
    export class ScreenModel{
        approverInfor : KnockoutObservableArray<shrVm.Approver> = ko.observableArray([]);
        confirmedPerson : KnockoutObservable<string> = ko.observable("");
        //test
        openDialogK() {
            var self = this;
            //承認者
            self.approverInfor.removeAll();
            self.approverInfor.push(new shrVm.Approver('35453433352534','A00000000001','日通システム'));
            self.approverInfor.push(new shrVm.Approver('35351453345344','A00000000002','尾林　日通システム'));
            self.approverInfor.push(new shrVm.Approver('35334534534534','A00000000003','日通システム　ベトナム'));
            self.approverInfor.push(new shrVm.Approver('35394534333534','A00000000004','日通システム　日本'));
            self.approverInfor.push(new shrVm.Approver('35745343534534','A00000000005','日通システム　武藤'));
            //確定者 code
            self.confirmedPerson('A00000000001');
            setShared("CMM008K_PARAM", { 
                                        appType: "0", //設定する対象申請名 
                                        formSetting: 0,//承認形態
                                        approverInfor: self.approverInfor(),//承認者一覧
                                        confirmedPerson: self.confirmedPerson //確定者
                                        });
            windows.sub.modal("/view/cmm/018/k/index.xhtml", { title: "", dialogClass: "no-close" });    
            
        }
        
        
        
    }   
}