module nts.uk.at.view.kaf009_ref.shr.viewmodel {

    @component({
        name: 'kaf009-share',
        template: '/nts.uk.at.web/view/kaf_ref/009/shr/index.html'
    })
    class Kaf009ShareViewModel extends ko.ViewModel {
        model : Model;
        checkbox1: KnockoutObservable<boolean>;
        checkbox2: KnockoutObservable<boolean>;
        workTypeCode: KnockoutObservable<String>;
        workTypeName: KnockoutObservable<String>;
        workTimeCode: KnockoutObservable<String>;
        workTimeName: KnockoutObservable<String>;
        created(params: any) {
            this.model = params;
            this.checkbox1 = params.checkbox1;
            this.checkbox2 = params.checkbox2;
            this.workTypeCode = params.workTypeCode;
            this.workTypeName = params.workTypeName;
            this.workTimeCode = params.workTimeCode;
            this.workTimeName = params.workTimeName;
            
        }
    
        mounted() {
            
        }
        
        openDialogKdl003() {
            let self = this;
            let workTypeCodes = self.model.workTypeCode;
            let workTimeCodes = self.model.workTimeCode;
//            nts.uk.ui.windows.setShared('parentCodes', {
//                workTypeCodes: workTypeCodes,
//                selectedWorkTypeCode: self.model.workTypeCode,
//                workTimeCodes: workTimeCodes,
//                selectedWorkTimeCode: self.model.workTypeCode
//            }, true);
//
//            nts.uk.ui.windows.sub.modal('/view/kdl/003/a/index.xhtml').onClosed(function(): any {
//                //view all code of selected item 
//                var childData = nts.uk.ui.windows.getShared('childData');
//                if (childData) {
//                    self.model.workTypeCode = childData.selectedWorkTypeCode;
//                    self.model.workTypeName = childData.selectedWorkTypeName;
//                    self.model.workTimeCode = childData.selectedWorkTimeCode;
//                    self.model.workTimeName = childData.selectedWorkTimeName;
//                    //フォーカス制御 => 定型理由
////                    $("#combo-box").focus();
//                }
//            })
            
        }
    }
    
    export class Kaf009Process {
        public static register() {  
        
        }    
        
        public static update() { 
              
        }
    }
  
    export class Model {
        checkbox1: KnockoutObservable<boolean>;
        checkbox2: KnockoutObservable<boolean>;
        workTypeCode: KnockoutObservable<String>;
        workTypeName: KnockoutObservable<String>;
        workTimeCode: KnockoutObservable<String>;
        workTimeName: KnockoutObservable<String>;
        constructor(cb1: boolean, cb2: boolean, workTypeCode: String, workTypeName: String, workTimeCode: String, workTimeName: String) {
            this.checkbox1 = ko.observable(cb1);
            this.checkbox2 = ko.observable(cb2);
            this.workTypeCode = ko.observable(workTypeCode);
            this.workTypeName = ko.observable(workTypeName);
            this.workTimeCode = ko.observable(workTimeCode);
            this.workTimeName = ko.observable(workTimeName);
            
        } 
    }
}