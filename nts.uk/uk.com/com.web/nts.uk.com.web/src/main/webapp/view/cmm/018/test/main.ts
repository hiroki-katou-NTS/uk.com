module cmm018.test.viewmodel{
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import windows = nts.uk.ui.windows;
    export class ScreenModel{
        approverInfor : KnockoutObservableArray<Approver> = ko.observableArray([]);
        confirmedPerson : KnockoutObservableArray<Approver> = ko.observableArray([]);
        //test
        openDialog() {
            var self = this;
            //承認者
            self.approverInfor.removeAll();
            self.approverInfor.push(new Approver('35353433352534','A00000000001','tran 4325 1234 1234'));
            self.approverInfor.push(new Approver('35353453345344','A00000000002','tran 4325 1234 1234'));
            self.approverInfor.push(new Approver('35334534534534','A00000000003','tran 4325 1234 1234'));
            self.approverInfor.push(new Approver('35334534333534','A00000000004','tran 4325 1234 1234'));
            self.approverInfor.push(new Approver('35345343534534','A00000000005','tran 4325 1234 1234'));
            //確定者
            self.confirmedPerson.push(new Approver('11111111','A00000000001','tran 4325 1234 1234'));
            setShared("CMM008K_PARAM", { 
                                        appType: "0", //設定する対象申請名 
                                        formSetting: 0,//承認形態
                                        approverInfor: self.approverInfor,//承認者一覧
                                        confirmedPerson: self.confirmedPerson //確定者
                                        });
            windows.sub.modal("/view/cmm/018/k/index.xhtml", { title: "", dialogClass: "no-close" });    
            
        }
        
        
        
    }   
    export class Approver{
        id: string;
        code: string;
        name: string;
        constructor(id: string, code: string, name: string){
            this.id = id;
            this.code = code;
            this.name = name;    
        }
    } 
}