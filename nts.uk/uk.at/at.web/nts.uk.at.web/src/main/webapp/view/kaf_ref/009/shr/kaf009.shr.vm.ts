module nts.uk.at.view.kaf009_ref.shr.viewmodel {
    import ModelDto = nts.uk.at.view.kaf009_ref.a.viewmodel.ModelDto;
    @component({
        name: 'kaf009-share',
        template: '/nts.uk.at.web/view/kaf_ref/009/shr/index.html'
    })
    class Kaf009ShareViewModel extends ko.ViewModel {
        mode: string = 'edit';
        subscribers: Array<any>;
        model : Model;
//        checkbox1: KnockoutObservable<boolean>;
//        checkbox2: KnockoutObservable<boolean>;
//        workTypeCode: KnockoutObservable<String>;
//        workTypeName: KnockoutObservable<String>;
//        workTimeCode: KnockoutObservable<String>;
//        workTimeName: KnockoutObservable<String>;
        dataFetch: KnockoutObservable<ModelDto>;
        created(params: any) {
              const vm = this;
              vm.model = params.model;
              vm.dataFetch = params.dataFetch;
              vm.mode = params.mode;
//            this.checkbox1 = params.model().checkbox1;
//            this.checkbox2 = params.model().checkbox2;
//            this.workTypeCode = params.model().workTypeCode;
//            this.workTypeName = params.model().workTypeName;
//            this.workTimeCode = params.model().workTimeCode;
//            this.workTimeName = params.model().workTimeName;
            vm.dataFetch.subscribe(value => {
                console.log('Change dataFetch');
                vm.bindData();
            });
            
        }
        bindData() {
            const vm = this;
            let goBackApp = this.dataFetch().goBackApplication();
            if ( goBackApp ) {
                vm.model.checkbox1( goBackApp.straightDistinction == 1 );
                this.model.checkbox2( goBackApp.straightLine == 1 );
            }
            //           else {
            vm.model.checkbox1( true );
            vm.model.checkbox2( true );
            //           }
            vm.model.checkbox3( true );
            //            this.model.checkbox3(this.dataFetch().goBackReflect().reflectApplication == 3);
            if ( ko.toJS( vm.dataFetch().workType ) ) {
                vm.model.workTypeCode( vm.dataFetch().workType().workType );
                vm.model.workTypeName( vm.dataFetch().workType().nameWorkType );

            } else {
                vm.model.workTypeCode( '001' );
            }
            if ( ko.toJS( vm.dataFetch().workTime ) ) {
                vm.model.workTimeCode( vm.dataFetch().workTime().workTime );
                vm.model.workTimeName( vm.dataFetch().workTime().nameWorkTime );

            } else {
                vm.model.workTimeCode( '001' );
            }
        }
        
        // 
    
        mounted() {
            
        }
        
        
        openDialogKdl003() {
            const vm = this;

            let workTypeCodes = vm.model.workTypeCode;
            let workTimeCodes = vm.model.workTimeCode;
            console.log( workTypeCodes );
            nts.uk.ui.windows.setShared( 'parentCodes', {
                workTypeCodes: _.map( _.uniqBy( vm.dataFetch().lstWorkType(), e => e.workTypeCode ), item => item.workTypeCode ),
                selectedWorkTypeCode: vm.model.workTypeCode,
                workTimeCodes: _.map( vm.dataFetch().appDispInfoStartup().appDispInfoWithDateOutput.opWorkTimeLst, item => item.worktimeCode ),
                selectedWorkTimeCode: vm.model.workTypeCode
            }, true );

            nts.uk.ui.windows.sub.modal( '/view/kdl/003/a/index.xhtml' ).onClosed( function(): any {
                //view all code of selected item 
                var childData = nts.uk.ui.windows.getShared( 'childData' );
                if ( childData ) {
                    vm.model.workTypeCode(childData.selectedWorkTypeCode);
                    vm.model.workTypeName(childData.selectedWorkTypeName);
                    vm.model.workTimeCode(childData.selectedWorkTimeCode);
                    vm.model.workTimeName(childData.selectedWorkTimeName);
                    //                                フォーカス制御 => 定型理由
                    //                                                   $("#combo-box").focus();
                }
            })
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
        checkbox3: KnockoutObservable<boolean>;
        workTypeCode: KnockoutObservable<String>;
        workTypeName: KnockoutObservable<String>;
        workTimeCode: KnockoutObservable<String>;
        workTimeName: KnockoutObservable<String>;
        constructor(cb1: boolean, cb2: boolean, cb3: boolean, workTypeCode: String, workTypeName: String, workTimeCode: String, workTimeName: String) {
            this.checkbox1 = ko.observable(cb1);
            this.checkbox2 = ko.observable(cb2);
            this.checkbox3 = ko.observable(cb3);
            this.workTypeCode = ko.observable(workTypeCode);
            this.workTypeName = ko.observable(workTypeName);
            this.workTimeCode = ko.observable(workTimeCode);
            this.workTimeName = ko.observable(workTimeName);
            
        } 
    }
}