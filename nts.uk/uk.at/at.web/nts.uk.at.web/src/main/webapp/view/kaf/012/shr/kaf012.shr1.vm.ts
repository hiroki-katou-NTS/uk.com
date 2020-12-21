module nts.uk.at.view.kaf012.shr.viewmodel {
    import ModelDto = nts.uk.at.view.kaf012.a.viewmodel.ModelDto;
    
    const template = `
    <div id="kaf012-share-component1" class="control-group valign-top label warning">
        <div style="display: inline-block" data-bind="text: $i18n('KAF012_2')"/>
        <table id="remaining-table">
            <colgroup>
                <col width="100px"/>
                <col width="100px"/>
                <col width="100px"/>
                <col width="100px"/>
                <col width="100px"/>
                <col width="100px"/>
            </colgroup>
            <thead>
                <tr>
                    <th class="ui-widget-header" data-bind="text: $i18n('KAF012_3')"/>
                    <th class="ui-widget-header" data-bind="text: $i18n('KAF012_4')"/>
                    <th class="ui-widget-header" data-bind="text: $i18n('Com_ChildNurseHoliday')"/>
                    <th class="ui-widget-header" data-bind="text: $i18n('Com_CareHoliday')"/>
                    <th class="ui-widget-header" data-bind="text: $i18n('Com_ExsessHoliday')"/>
                    <th class="ui-widget-header" data-bind="text: $i18n('KAF012_46')"/>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>aa</td>
                    <td>aa</td>
                    <td>aa</td>
                    <td>aa</td>
                    <td>ss</td>
                    <td>ss</td>
                </tr>
            </tbody>
        </table>
    </div>
    `;

    @component({
        name: 'kaf012-share-component1',
        template: template
    })
    class Kaf012Share1ViewModel extends ko.ViewModel {
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
            $("#remaining-table").ntsFixedTable({});
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