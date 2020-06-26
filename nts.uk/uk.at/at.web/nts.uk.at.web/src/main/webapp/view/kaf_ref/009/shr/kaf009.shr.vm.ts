module nts.uk.at.view.kaf009_ref.shr.viewmodel {

    @component({
        name: 'kaf009-share',
        template: '/nts.uk.at.web/view/kaf_ref/009/shr/index.html'
    })
    class Kaf009ShareViewModel extends ko.ViewModel {
        
        subscribers: Array<any>;
        model : Model;
//        checkbox1: KnockoutObservable<boolean>;
//        checkbox2: KnockoutObservable<boolean>;
//        workTypeCode: KnockoutObservable<String>;
//        workTypeName: KnockoutObservable<String>;
//        workTimeCode: KnockoutObservable<String>;
//        workTimeName: KnockoutObservable<String>;
        dataFetch: any;
        created(params: any) {
              this.model = params.model;
              this.dataFetch = params.dataFetch;
              
//            this.checkbox1 = params.model().checkbox1;
//            this.checkbox2 = params.model().checkbox2;
//            this.workTypeCode = params.model().workTypeCode;
//            this.workTypeName = params.model().workTypeName;
//            this.workTimeCode = params.model().workTimeCode;
//            this.workTimeName = params.model().workTimeName;
            this.dataFetch.subscribe(value => {
                console.log('Change dataFetch');
                this.bindData();
            });
            
        }
        bindData(){
            let goBackApp = this.dataFetch().goBackApplication();
            if (goBackApp) {
                this.model.checkbox1(goBackApp.straightDistinction == 1);
                this.model.checkbox2(goBackApp.straightLine == 1);
            }
//            else {
                this.model.checkbox1(true);
                this.model.checkbox2(true);
//            }
            
            this.model.workTypeCode(this.dataFetch().workType().workType);
            this.model.workTypeName(this.dataFetch().workType().nameWorkType);
            this.model.workTimeCode(this.dataFetch().workTime().workTime);
            this.model.workTimeName(this.dataFetch().workTime().nameWorkTime);
        }
        
        // 
    
        mounted() {
            
        }
        
        openDialogKdl003() {
//            let self = this;
//            let workTypeCodes = self.model.workTypeCode;
//            let workTimeCodes = self.model.workTimeCode;
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