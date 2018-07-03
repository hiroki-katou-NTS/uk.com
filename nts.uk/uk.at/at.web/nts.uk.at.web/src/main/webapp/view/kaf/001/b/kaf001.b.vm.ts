module nts.uk.at.view.kaf001.b.viewmodel {
    import getText = nts.uk.resource.getText;
    export class ScreenModel {
        lstSID: Array<string> = [];
        //B2-普通残業申請- KAF001_15　　普通{0}申請   
        b2: KnockoutObservable<string> = ko.observable('');
        //B3-早出残業申請-KAF001_16　　早出{0}申請      
        b3: KnockoutObservable<string> = ko.observable('');
        //B4-早出・普通残業申請-KAF001_17　　早出・普通{0}申請       
        b4: KnockoutObservable<string> = ko.observable('');
        constructor(transfer: any){
            let self = this;
            let lstID = transfer == undefined ? [] : transfer.employeeIds;
            self.lstSID = lstID;
            self.getNameOverTime();
        }
        getNameOverTime(){
            let self = this;
            service.getNameDis(0).done(function(obj){
                self.b2(getText('KAF001_15', [obj.dispName]));
                self.b3(getText('KAF001_16', [obj.dispName]));
                self.b4(getText('KAF001_17', [obj.dispName]));
            });   
        }
        openOverTime(value: number){
            let self = this;
                let transfer = {
                   uiType: 0,
                   employeeIDs: self.lstSID
                };
                switch (value) {
                    case 0:
                        //KAF005-残業申請（早出）
                        nts.uk.request.jump("/view/kaf/005/a/index.xhtml?overworkatr=0", transfer);
                        break;
                        
                    case 1:
                        //KAF005-残業申請（通常） 
                        nts.uk.request.jump("/view/kaf/005/a/index.xhtml?overworkatr=1", transfer);
                        break;
                        
                    case 2:
                        //KAF005-残業申請（早出・通常）
                        nts.uk.request.jump("/view/kaf/005/a/index.xhtml?overworkatr=2", transfer);
                        break;
            }
        }
    }
}