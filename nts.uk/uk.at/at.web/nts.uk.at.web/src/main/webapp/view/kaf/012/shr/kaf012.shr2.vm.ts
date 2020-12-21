module nts.uk.at.view.kaf012.shr.viewmodel {
    import ModelDto = nts.uk.at.view.kaf012.a.viewmodel.ModelDto;
    
    const template = `
    <div id="kaf012-share-component2">
        <div class="control-group valign-center">
            <div data-bind="ntsFormLabel: {required:true , text: $i18n('KAF012_46')}"></div>
            <div data-bind="ntsSwitchButton: {
						name: $i18n('KAF012_5'),
						options: ko.observableArray([
                            { prePostCode: 0, prePostName: function() { return $i18n('KAF012_3'); } },
                            { prePostCode: 1, prePostName: function() { return $i18n('KAF012_4'); } },
                            { prePostCode: 2, prePostName: function() { return $i18n('Com_ChildNurseHoliday'); } },
                            { prePostCode: 3, prePostName: function() { return $i18n('Com_CareHoliday'); } },
                            { prePostCode: 4, prePostName: function() { return $i18n('Com_ExsessHoliday'); } },
                            { prePostCode: 5, prePostName: function() { return $i18n('KAF012_46'); } },
                            { prePostCode: 6, prePostName: function() { return $i18n('KAF012_39'); } }
                        ]),
						optionsValue: 'prePostCode',
						optionsText: 'prePostName',
						value: prePostAtr,
						enable: true,
						required: true }">
			</div>
        </div>
        <div class="control-group valign-center">
            <div data-bind="ntsFormLabel: {required:true , text: $i18n('KAF012_47')}"></div>
            <div data-bind="ntsComboBox: {
                    name: $i18n('KAF000_47'),
                    options: ko.observableArray([
                        { prePostCode: 0, prePostName: function() { return $i18n('KAF012_3'); } },
                        { prePostCode: 1, prePostName: function() { return $i18n('KAF012_4'); } },
                        { prePostCode: 2, prePostName: function() { return $i18n('Com_ChildNurseHoliday'); } },
                        { prePostCode: 3, prePostName: function() { return $i18n('Com_CareHoliday'); } },
                        { prePostCode: 4, prePostName: function() { return $i18n('Com_ExsessHoliday'); } },
                        { prePostCode: 5, prePostName: function() { return $i18n('KAF012_46'); } },
                        { prePostCode: 6, prePostName: function() { return $i18n('KAF012_39'); } }
                    ]),
                    optionsValue: 'prePostCode',
                    optionsText: 'prePostName',
                    value: opAppStandardReasonCD,
                    columns: [{ prop: 'prePostName', length: 20 }],
                    required: true }">  
            </div>
        </div>
        <div class="control-group valign-top">
            <div data-bind="ntsFormLabel: {required:true , text: $i18n('KAF012_6')}"></div>
            <table id="kaf012-input-table">
                <colgroup>
                    <col width="100px"/>
                    <col width="300px"/>
                </colgroup>
                <thead>
                    <tr>
                        <th class="ui-widget-header">
                        <th class="ui-widget-header" data-bind="text: $i18n('KAF012_4')"/>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <th class="ui-widget-header">aa</th>
                        <td>aa</td>
                    </tr>
                    <tr>
                        <th class="ui-widget-header">aa</th>
                        <td>aa</td>
                    </tr>
                    <tr>
                        <th class="ui-widget-header">aa</th>
                        <td>aa</td>
                    </tr>
                    <tr>
                        <th class="ui-widget-header">aa</th>
                        <td>aa</td>
                    </tr>
                    <tr>
                        <th class="ui-widget-header">aa</th>
                        <td>aa</td>
                    </tr>
                </tbody>
            </table>
            <div style="display: inline-block; width: 100px; height: 100%;">
                <button class="proceed caret-right" data-bind="text: $i18n('KAF012_38')"/>
            </div>
            <table id="kaf012-calc-table">
                <colgroup>
                    <col width="100px"/>
                </colgroup>
                <thead>
                    <tr>
                        <th class="ui-widget-header" data-bind="text: $i18n('KAF012_4')"/>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>aa</td>
                    </tr>
                    <tr>
                        <td>aa</td>
                    </tr>
                    <tr>
                        <td>aa</td>
                    </tr>
                    <tr>
                        <td>aa</td>
                    </tr>
                    <tr>
                        <td>aa</td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
    `;

    @component({
        name: 'kaf012-share-component2',
        template: template
    })
    class Kaf012Share2ViewModel extends ko.ViewModel {
        prePostAtr: KnockoutObservable<number>;
        opAppStandardReasonCD: KnockoutObservable<number>;
        mode: KnockoutObservable<String>;
        subscribers: Array<any>;
        model : Model;

        dataFetch: KnockoutObservable<ModelDto>;
        created(params: any) {
              const vm = this;
              vm.model = params.model;
              vm.dataFetch = params.dataFetch;
              vm.mode = params.mode;
            vm.prePostAtr = ko.observable(0);
			  vm.opAppStandardReasonCD = ko.observable(0);

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
            $("#kaf012-input-table").ntsFixedTable({});
            $("#kaf012-calc-table").ntsFixedTable({});
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