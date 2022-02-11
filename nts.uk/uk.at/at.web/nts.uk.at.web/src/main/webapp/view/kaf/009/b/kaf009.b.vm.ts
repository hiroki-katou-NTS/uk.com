module nts.uk.at.view.kaf009_ref.b.viewmodel {
    import Application = nts.uk.at.view.kaf000.shr.viewmodel.Application;
    import Model = nts.uk.at.view.kaf009_ref.shr.viewmodel.Model;
	import AppType = nts.uk.at.view.kaf000.shr.viewmodel.model.AppType;
	import PrintContentOfEachAppDto = nts.uk.at.view.kaf000.shr.viewmodel.PrintContentOfEachAppDto;
	import CommonProcess = nts.uk.at.view.kaf000.shr.viewmodel.CommonProcess;
    const template = `
	<div id="kaf009-b">
            <div id="contents-area" style="background-color: inherit; height: calc(100vh - 137px);">
                <div class="two-panel" style="height: 100%; width: 1260px">
                    <div class="left-panel" style="width: calc(1260px - 388px); padding-bottom: 5px;">
                            <div class="table form-header">
                                <div class="cell" style="vertical-align: middle;">
                                    <div data-bind="component: { name: 'kaf000-b-component4',
                                                        params: {
                                                            appType: appType,
                                                            application: application,
                                                            appDispInfoStartupOutput: appDispInfoStartupOutput
                                                        } }"></div>
                                </div>
                                <div class="cell" style="text-align: right; vertical-align: middle;">
                                    <div data-bind="component: { name: 'kaf000-b-component8', 
                                                        params: {
                                                            appType: appType,
                                                            appDispInfoStartupOutput: appDispInfoStartupOutput
                                                        } }"></div>
                                </div>
                            </div>
							<div data-bind="component: { name: 'kaf000-b-component2', 
														params: {
															appType: appType,
															appDispInfoStartupOutput: appDispInfoStartupOutput
														} }"></div>
                            <div data-bind="component: { name: 'kaf000-b-component5', 
                                                        params: {
                                                            appType: appType,
                                                            application: application,
                                                            appDispInfoStartupOutput: appDispInfoStartupOutput
                                                        } }"></div>
                            <div data-bind="component: { name: 'kaf000-b-component6', 
                                                        params: {
                                                            appType: appType,
                                                            application: application,
                                                            appDispInfoStartupOutput: appDispInfoStartupOutput
                                                        } }" style="width: fit-content; display: inline-block; vertical-align: middle; margin-top: -15px"></div>
                                                        
                                                        
                            <div style="margin-top: -19px;"
                                data-bind="component: { name: 'kaf009-share', params: {dataFetch: dataFetch, model:model, mode: mode } }"></div>                             
                                                        
                                                        
                            <div style="margin-top: -3px;" data-bind="component: { name: 'kaf000-b-component7', 
                                                        params: {
                                                            appType: appType,
                                                            application: application,
                                                            appDispInfoStartupOutput: appDispInfoStartupOutput
                                                        } }"></div>
                                                        
                                                        
                            <div style="padding-top: 30px;">
                                        
                            </div>                            
                    </div>
                    <div class="right-panel" style="width: 388px; padding-bottom: 5px; padding-right: 0px">
                        <div data-bind="component: { name: 'kaf000-b-component1', 
                                params: {
                                    appType: appType,
                                    appDispInfoStartupOutput: appDispInfoStartupOutput	
                                } }"></div>
                        <div data-bind="component: { name: 'kaf000-b-component9',
                                params: {
                                    appType: appType,
                                    application: application,
                                    appDispInfoStartupOutput: $vm.appDispInfoStartupOutput
                                } }"></div>
                    </div>
                </div>
            </div>
        </div>

`
    @component({
        name: 'kaf009-b',
        template: template
    })
    class Kaf009BViewModel extends ko.ViewModel {

		appType: KnockoutObservable<number> = ko.observable(AppType.GO_RETURN_DIRECTLY_APPLICATION);
        appDispInfoStartupOutput: any;
        application: KnockoutObservable<Application>;
        model: Model;
        dataFetch: KnockoutObservable<ModelDto> = ko.observable(null);
        mode: KnockoutObservable<String> = ko.observable('edit');
        approvalReason: KnockoutObservable<string>;
        printContentOfEachAppDto: KnockoutObservable<PrintContentOfEachAppDto>;
        applicationTest: any = {
            employeeID: this.$user.employeeId,
            appDate: moment(new Date()).format('YYYY/MM/DD'),
            enteredPerson: this.$user.employeeId,
            inputDate: moment(new Date()).format('YYYY/MM/DD HH:mm:ss'),
            opStampRequestMode: 1,
            opReversionReason: '1',
            opAppStartDate: '2020/08/07',
            opAppEndDate: '2020/08/08',
            opAppReason: 'jdjadja',
            opAppStandardReasonCD: 1


        };


        created(
            params: {
				appType: any,
				application: any,
				printContentOfEachAppDto: PrintContentOfEachAppDto,
            	approvalReason: any,
                appDispInfoStartupOutput: any,
                eventUpdate: (evt: () => void ) => void,
				eventReload: (evt: () => void) => void
            }
        ) {
            const vm = this;
            vm.printContentOfEachAppDto = ko.observable(params.printContentOfEachAppDto);
            vm.appDispInfoStartupOutput = params.appDispInfoStartupOutput;
            vm.application = params.application;
			vm.appType = params.appType;
            vm.model = new Model(true, true, true, '', '', '', '');
            if (ko.toJS(vm.appDispInfoStartupOutput).appDetailScreenInfo) {
                let mode = ko.toJS(vm.appDispInfoStartupOutput).appDetailScreenInfo.outputMode == 1 ? 'edit' : 'view';
				vm.mode(mode); 
            }
            vm.createParamKAF009();

            vm.applicationTest = vm.appDispInfoStartupOutput().appDetailScreenInfo.application;
            vm.approvalReason = params.approvalReason;
            // gui event con ra viewmodel cha
            // nhớ dùng bind(vm) để ngữ cảnh lúc thực thi
            // luôn là component
            params.eventUpdate(vm.update.bind(vm));
			params.eventReload(vm.reload.bind(vm));
        }

		reload() {
			const vm = this;
			if(vm.appType() === AppType.GO_RETURN_DIRECTLY_APPLICATION) {
				vm.createParamKAF009();
			}
		}

        createParamKAF009() {
            let vm = this;
            vm.$blockui('show');
			if (ko.toJS(vm.appDispInfoStartupOutput).appDetailScreenInfo) {
                let mode = ko.toJS(vm.appDispInfoStartupOutput).appDetailScreenInfo.outputMode == 1 ? 'edit' : 'view';
				vm.mode(mode); 
            }
            return vm.$ajax(API.getDetail, {
                companyId: vm.$user.companyId,
                applicationId: vm.application().appID()
            }).done(res => {
                console.log(res);
                if (res) {
                    vm.dataFetch({
                        workType: ko.observable(res.workType),
                        workTime: ko.observable(res.workTime),
                        appDispInfoStartup: ko.observable(res.appDispInfoStartup),
                        goBackReflect: ko.observable(res.goBackReflect),
                        lstWorkType: ko.observable(res.lstWorkType),
                        goBackApplication: ko.observable(res.goBackApplication),
						workInfo: res.workInfo
                    });
                    vm.printContentOfEachAppDto().opInforGoBackCommonDirectOutput = ko.toJS(vm.dataFetch);
                }
            }).fail(err => {
                vm.handleError(err);
            }).always(() => vm.$blockui('hide'));
        }



        mounted() {
            const vm = this;
        }

        // event update cần gọi lại ở button của view cha
        update() {
            const vm = this;
            if (!vm.appDispInfoStartupOutput().appDetailScreenInfo) {
                return;
            }
            let application = ko.toJS(vm.application);
            vm.applicationTest = vm.appDispInfoStartupOutput().appDetailScreenInfo.application;

            vm.applicationTest.prePostAtr = application.prePostAtr;
            vm.applicationTest.opAppReason = application.opAppReason;
            vm.applicationTest.opAppStandardReasonCD = application.opAppStandardReasonCD;
            vm.applicationTest.opReversionReason = application.opReversionReason;
			if (vm.model) {
				/*
				
					let isCondition1 = 
						!_.isNil(vm.model.checkbox3())
						&& _.isNil(vm.model.workTypeCode())
					 	&& (vm.dataFetch().goBackReflect().reflectApplication === ApplicationStatus.DO_REFLECT_1 || vm.dataFetch().goBackReflect().reflectApplication === ApplicationStatus.DO_NOT_REFLECT_1);
					
					
					let isCondition2 = 
							_.isNil(vm.model.checkbox3())
							&& _.isNil(vm.model.workTypeCode()) 
							&& vm.dataFetch().goBackReflect().reflectApplication === ApplicationStatus.DO_REFLECT;
							
				
				 */
				const isDisplayMsg2150 = 
					((!_.isNil(vm.model.checkbox3()) ? vm.model.checkbox3() : false) // ②「勤務を変更する」がチェックしている
					|| vm.dataFetch().goBackReflect().reflectApplication === ApplicationStatus.DO_REFLECT) // ①直行直帰申請の反映.勤務情報を反映する　＝　反映する
                	&& _.isNil(vm.model.workTypeCode())

				if (isDisplayMsg2150) {
					
					vm.$dialog.error({messageId: 'Msg_2150'});
					
                    return vm.$validate().then(() => false);
                } 
            }
            let model = ko.toJS( vm.model );
            let goBackApp = new GoBackApplication(
                model.checkbox1 ? 1 : 0,
                model.checkbox2 ? 1 : 0,
            );
			// is change can be null
            if (!_.isNull(model.checkbox3) || vm.dataFetch().goBackReflect().reflectApplication == 2 || vm.dataFetch().goBackReflect().reflectApplication == 3) {
                goBackApp.isChangedWork = model.checkbox3 ? 1 : 0;

            }
            
			if	(!(vm.dataFetch().goBackReflect().reflectApplication == 2 || vm.dataFetch().goBackReflect().reflectApplication == 3)) {
				goBackApp.isChangedWork = null;
			}
            if (vm.mode && vm.model.checkbox3() || vm.dataFetch().goBackReflect().reflectApplication == 1) {
                let dw = new DataWork( model.workTypeCode );
                if ( model.workTimeCode ) {
                    dw.workTime = model.workTimeCode
                }
                goBackApp.dataWork = dw;

            }
			
            vm.$blockui("show");

            return vm.$validate('.nts-input', '#kaf000-a-component3-prePost', '#kaf000-a-component5-comboReason')
                .then(isValid => {
                    if (isValid) {
                        return true;

                    }
                }).then(result => {
                    if(!result) return;
                    let param = {
                            companyId: this.$user.companyId,
                            agentAtr: true,
                            applicationDto: vm.applicationTest,
                            goBackDirectlyDto: goBackApp,
                            inforGoBackCommonDirectDto: ko.toJS( vm.dataFetch ),
                            mode: false
                        };


                    return vm.$ajax(API.checkRegister, param);
                }).then(res => {
                    if (res == undefined) return;
                    if ( _.isEmpty( res ) ) {
                        return vm.registerData( goBackApp );
                    } else {
                        let listTemp = _.clone( res );
                        return vm.handleConfirmMessage( listTemp, goBackApp );

                    }

                }).done(result => {
                    if (result != undefined) {
                        vm.$dialog.info( { messageId: "Msg_15" } ).then(() => {
							CommonProcess.handleMailResult(result, vm).then(() => {
								ko.contextFor($('#contents-area')[0]).$vm.loadData();	
							});
                        });
                    }
                }).fail(err => {
                    vm.handleError(err);

                }).always(() => vm.$blockui("hide"));




        }
        public handleConfirmMessage(listMes: any, res: any) {
            let vm = this;
            if (!_.isEmpty(listMes)) {
                let item = listMes.shift();
                return vm.$dialog.confirm({ messageId: item.msgID }).then((value) => {
                    if (value == 'yes') {
                        if (_.isEmpty(listMes)) {
                            return vm.registerData(res);
                        } else {
                            return vm.handleConfirmMessage(listMes, res);
                        }

                    }
                });
            }
        }

        registerData(goBackApp) {
            let vm = this;
            let paramsUpdate = {
                applicationDto: vm.applicationTest,
                goBackDirectlyDto: goBackApp,
                inforGoBackCommonDirectDto: ko.toJS(vm.dataFetch)
            }

             return vm.$ajax(API.updateApplication, paramsUpdate);

        }
        public handleError(err: any) {
            const vm = this;
            let param;
            if (err.message && err.messageId) {
                param = {messageId: err.messageId, messageParams: err.parameterIds};
            } else {

                if (err.message) {
                    param = {message: err.message, messageParams: err.parameterIds};
                } else {
                    param = {messageId: err.messageId, messageParams: err.parameterIds};
                }
            }
            vm.$dialog.error(param).then(res => {
                if (err.messageId == 'Msg_197') {
                	ko.contextFor($('#contents-area')[0]).$vm.loadData();
                }
            });
        }






        dispose() {
            const vm = this;

        }
    }
    export class GoBackReflect {
        companyId: string;
        reflectApplication: ApplicationStatus;
    }
    export class ModelDto {

        workType: KnockoutObservable<any>;

        workTime: KnockoutObservable<any>;

        appDispInfoStartup: KnockoutObservable<any>;

        goBackReflect: KnockoutObservable<GoBackReflect> = ko.observable( null );

        lstWorkType: KnockoutObservable<any>;

        goBackApplication: KnockoutObservable<any>;

		workInfo: any;
    }
    export class GoBackApplication {
        straightDistinction: number;
        straightLine: number;
        isChangedWork?: number;
        dataWork?: DataWork;
        constructor(straightDistinction: number, straightLine: number, isChangedWork?: number, dataWork?: DataWork) {
            this.straightDistinction = straightDistinction;
            this.straightLine = straightLine;
            this.isChangedWork = isChangedWork;
            this.dataWork = dataWork;
        }
    }


    export class DataWork {
        workType: string;
        workTime?: string;
        constructor(workType: string, workTime?: string) {
            this.workType = workType;
            this.workTime = workTime;
        }
    }
    const API = {
        checkRegister: "at/request/application/gobackdirectly/checkBeforeRegisterNew",
        updateApplication: "at/request/application/gobackdirectly/updateNewKAF009",
        getDetail: "at/request/application/gobackdirectly/getDetail"
    }

	export class ApplicationStatus {
        // 反映しない
        public static DO_NOT_REFLECT: number = 0;
        // 反映する
        public static DO_REFLECT: number = 1;
        // 申請時に決める(初期値：反映しない)
        public static DO_NOT_REFLECT_1: number = 2;
        // 申請時に決める(初期値：反映する)
        public static DO_REFLECT_1: number = 3;
    }

}