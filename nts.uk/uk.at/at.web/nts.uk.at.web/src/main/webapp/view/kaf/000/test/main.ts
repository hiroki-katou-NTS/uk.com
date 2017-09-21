module nts.uk.at.view.kaf000.test.viewmodel{
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import windows = nts.uk.ui.windows;
    export class ScreenModel{
        appInfor : KnockoutObservableArray<ApproverDtoK> = ko.observableArray([]);
        //test
        openDialogK() {
            var self = this;
            //承認者
            self.appInfor.removeAll();
            self.appInfor.push(new ApproverDtoK('18a9bf80-4a30-4e3b-9b63-6aee4e0bac1b',4));
            self.appInfor.push(new ApproverDtoK('e3ee58d6-4ed3-4b88-a6e9-e91e2545ea7d',7));
            self.appInfor.push(new ApproverDtoK('1445DA47-3CF9-4B0A-B819-96D20721881C',4));
            //確定者 code
            setShared("CMM008K_PARAM", { 
                                        appInfor: self.appInfor()
                                        });
            windows.sub.modal("/view/kaf/000/b/index.xhtml", { title: "", dialogClass: "no-close" });    
            
        }        
    }   
        export class ApproverDtoK{
            appId: string;
            appType: number;
            constructor(appId: string, appType: number){
                this.appId = appId;
                this.appType = appType;
            }
        } 
}