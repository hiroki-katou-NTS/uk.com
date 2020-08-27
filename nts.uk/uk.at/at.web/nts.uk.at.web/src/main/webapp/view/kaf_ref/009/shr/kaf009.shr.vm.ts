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

        dataFetch: KnockoutObservable<ModelDto>;
        created(params: any) {
              const vm = this;
              vm.model = params.model;
              vm.dataFetch = params.dataFetch;
              vm.mode = params.mode;

            vm.dataFetch.subscribe(value => {
                console.log('Change dataFetch');
                if (ko.toJS(value)) {
                    if (ko.toJS(value)) {
                        vm.bindData(true);                                            
                    }else {
                        vm.bindData(false);
                    }
                }
            });
            
        }
        bindData(isChangeDate: boolean) {
            const vm = this;
            let goBackApp = vm.dataFetch().goBackApplication();
            if ( goBackApp ) {
                vm.model.checkbox1( goBackApp.straightDistinction == 1 );
                vm.model.checkbox2( goBackApp.straightLine == 1 );
                if (goBackApp.isChangedWork != null) {
                    vm.model.checkbox3( goBackApp.isChangedWork == 1 );
                    
                } else {
                    vm.model.checkbox3( null );
                }
                
                if (!_.isEmpty(goBackApp.dataWork)) {
                    let codeWorkType = goBackApp.dataWork.workType;
                    let nameWorkType = _.find(ko.toJS(vm.dataFetch().lstWorkType), item => item.workTypeCode == codeWorkType).abbreviationName;
                    vm.model.workTypeCode(codeWorkType);
                    vm.model.workTypeName(nameWorkType);
                    if (!_.isEmpty(ko.toJS(vm.dataFetch().workTime))) {
                        let codeWorkTime = goBackApp.dataWork.workTime;
                        vm.model.workTimeCode(codeWorkTime);
                        let nameWorkTime = _.find(ko.toJS(vm.dataFetch().appDispInfoStartup).appDispInfoWithDateOutput.opWorkTimeLst, item => item.worktimeCode == codeWorkTime).workTimeDisplayName.workTimeName;
                        vm.model.workTimeName(nameWorkTime);
                        
                    }
                }
                
                
                
                
            } else {
                let refApp = vm.dataFetch().goBackReflect().reflectApplication;
                if (!vm.dataFetch().isChangeDate) {
                    vm.model.checkbox1( true );
                    vm.model.checkbox2( true );
                    if ( refApp == 3) {
                        vm.model.checkbox3( true );
                    } else if ( refApp ==2 ) {
                        vm.model.checkbox3( false );
                    }else {
                        vm.model.checkbox3( null );
                    }
                }
                

                if (!_.isEmpty(ko.toJS(vm.dataFetch().workType))) {
                    let codeWorkType = vm.dataFetch().workType();
                    vm.model.workTypeCode(codeWorkType);
                    let wt = _.find(ko.toJS(vm.dataFetch().lstWorkType), item => item.workTypeCode == codeWorkType);
                    if (!_.isNull(wt)) {
                        let nameWorkType = wt.abbreviationName;
                        vm.model.workTypeName(nameWorkType);       
                    }
    
                }
                if (!_.isEmpty(ko.toJS(vm.dataFetch().workTime))) {
                    let codeWorkTime = vm.dataFetch().workTime(); 
                    vm.model.workTimeCode(codeWorkTime);
                    let wt = _.find(ko.toJS(vm.dataFetch().appDispInfoStartup).appDispInfoWithDateOutput.opWorkTimeLst, item => item.worktimeCode == codeWorkTime);
                    if (!_.isNull(wt)) {
                        let nameWorkTime = wt.workTimeDisplayName.workTimeName;
                        vm.model.workTimeName(nameWorkTime);
                        
                    }
    
                }
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
                selectedWorkTypeCode: vm.model.workTypeCode(),
                workTimeCodes: _.map( vm.dataFetch().appDispInfoStartup().appDispInfoWithDateOutput.opWorkTimeLst, item => item.worktimeCode ),
                selectedWorkTimeCode: vm.model.workTimeCode()
            }, true);

            nts.uk.ui.windows.sub.modal( '/view/kdl/003/a/index.xhtml' ).onClosed( function(): any {
                //view all code of selected item 
                var childData = nts.uk.ui.windows.getShared( 'childData' );
                if ( childData ) {
                    vm.model.workTypeCode(childData.selectedWorkTypeCode);
                    vm.model.workTypeName(childData.selectedWorkTypeName);
                    vm.model.workTimeCode(childData.selectedWorkTimeCode);
                    vm.model.workTimeName(childData.selectedWorkTimeName);
                    //フォーカス制御 => 定型理由
                    //$("#combo-box").focus();
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