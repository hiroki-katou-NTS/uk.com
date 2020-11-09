module nts.uk.at.view.kaf009_ref.shr.viewmodel {
    import ModelDto = nts.uk.at.view.kaf009_ref.a.viewmodel.ModelDto;
    
    const template = `
    <div data-bind="if: dataFetch()">
    <!--A7-->

    <div class="centerCheckBox">
        <div class="title"
                    data-bind="ntsFormLabel: {required:true , text: $i18n('KAF009_45')}"></div>
        <div class="firstCheckBox" data-bind="ntsCheckBox: { checked: model.checkbox1, text: $i18n('KAF009_16'), enable: ko.toJS(mode) == 'edit'}"></div>
        <div class="secondCheckBox"
                        data-bind="ntsCheckBox: { checked: model.checkbox2, text: $i18n('KAF009_18'), enable: ko.toJS(mode) == 'edit'}"></div>
    </div>
    
    <!--Work Change-->
    <div data-bind="if: dataFetch().goBackReflect()"
        class="valign-center control-group">
        <div
            data-bind="if: dataFetch().goBackReflect().reflectApplication == 3 
            || dataFetch().goBackReflect().reflectApplication == 2">
            <div data-bind="if : model.checkbox3 != null">
                <div data-bind="ntsCheckBox: { checked: model.checkbox3 , text: $i18n('KAF009_21'), enable: ko.toJS(mode) == 'edit'}">
                </div>
            </div>
        </div>
        <div
            data-bind="if: dataFetch().goBackReflect().reflectApplication == 1
            || dataFetch().goBackReflect().reflectApplication == 0">
            <br />
        </div>
        <div
            data-bind="if: dataFetch().goBackReflect().reflectApplication != 0">
            <HR class="hrStyle" />
        </div>

        <div class="table clsWorkType"
            data-bind="if: dataFetch().goBackReflect().reflectApplication == 3 
            || dataFetch().goBackReflect().reflectApplication == 2 
            || dataFetch().goBackReflect().reflectApplication == 1">
            <div class="cell valign-center ">
                <div class="valign-center control-group"
                    data-bind="ntsFormLabel:{ required: true, text: $i18n('KAF009_22') }"></div>
                <BR />
                <div class="valign-center control-group"
                    data-bind="ntsFormLabel:{ required: true, text: $i18n('KAF009_23')}"></div>
            </div>
            <div class="cell valign-center">
                <button id="workSelect"
                    data-bind=" click: openDialogKdl003, text: $i18n('KAF009_24'), enable: (ko.toJS(mode) == 'edit' && model.checkbox3() || dataFetch().goBackReflect().reflectApplication == 1) "></button>
            </div>
            <div class="cell valign-center">
                <label class="lblWorkTypeCd required" data-bind="text: model.workTypeCode"></label>
                <label data-bind="text: model.workTypeName"></label> <br /> <label
                    class="lblSiftCd" data-bind="text: model.workTimeCode, required: true"></label> <label
                    data-bind="text: model.workTimeName "></label>
            </div>
        </div>
    </div>

</div>
    `
    @component({
        name: 'kaf009-share',
        template: template
    })
    class Kaf009ShareViewModel extends ko.ViewModel {
        mode: KnockoutObservable<String>;
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
                    } else {
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
                    let wtype = _.find(ko.toJS(vm.dataFetch().lstWorkType), item => item.workTypeCode == codeWorkType);
                    let nameWorkType = !_.isUndefined(wtype) ? wtype.name : vm.$i18n('KAF009_63');
                    vm.model.workTypeCode(codeWorkType);
                    vm.model.workTypeName(nameWorkType);
                    if (!_.isEmpty(ko.toJS(vm.dataFetch().workTime))) {
                        let codeWorkTime = goBackApp.dataWork.workTime;
                        vm.model.workTimeCode(codeWorkTime);
                        let wtime = _.find(ko.toJS(vm.dataFetch().appDispInfoStartup).appDispInfoWithDateOutput.opWorkTimeLst, item => item.worktimeCode == codeWorkTime)
                        let nameWorkTime = !_.isUndefined(wtime) ? wtime.workTimeDisplayName.workTimeName : vm.$i18n('KAF009_63');
                        vm.model.workTimeName(nameWorkTime);
                        
                    }
                } else {
					// #112404
					vm.model.workTypeCode(null);
					vm.model.workTimeCode(null);
                    vm.model.workTypeName(vm.$i18n('KAF009_63'));
                    vm.model.workTimeName(vm.$i18n('KAF009_63'));
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
                    if (!_.isUndefined(wt)) {
                        let nameWorkType = wt.name;
                        vm.model.workTypeName(nameWorkType);       
                    }
    
                }
                if (!_.isEmpty(ko.toJS(vm.dataFetch().workTime))) {
                    let codeWorkTime = vm.dataFetch().workTime(); 
                    vm.model.workTimeCode(codeWorkTime);
                    let wt = _.find(ko.toJS(vm.dataFetch().appDispInfoStartup).appDispInfoWithDateOutput.opWorkTimeLst, item => item.worktimeCode == codeWorkTime);
                    if (!_.isUndefined(wt)) {
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