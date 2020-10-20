module nts.uk.at.view.kaf009_ref.b.viewmodel {
    import Application = nts.uk.at.view.kaf000.shr.viewmodel.Application;
    import Model = nts.uk.at.view.kaf009_ref.shr.viewmodel.Model;
	import AppType = nts.uk.at.view.kaf000.shr.viewmodel.model.AppType;
	import PrintContentOfEachAppDto = nts.uk.at.view.kaf000.shr.viewmodel.PrintContentOfEachAppDto;
    const template = `
        <div>
    <div data-bind="component: { name: 'kaf000-b-component1',
                                params: {
                                    appType: appType,
                                    appDispInfoStartupOutput: appDispInfoStartupOutput
                                } }"></div>
    <div data-bind="component: { name: 'kaf000-b-component2',
                                params: {
                                    appType: appType,
                                    appDispInfoStartupOutput: appDispInfoStartupOutput
                                } }"></div>
    <div data-bind="component: { name: 'kaf000-b-component3',
                                params: {
                                    appType: appType,
                                    approvalReason: approvalReason,
                                    appDispInfoStartupOutput: appDispInfoStartupOutput
                                } }"></div>
    <div class="table">
        <div class="cell" style="width: 825px;" data-bind="component: { name: 'kaf000-b-component4',
                            params: {
                                appType: appType,
                                application: application,
                                appDispInfoStartupOutput: appDispInfoStartupOutput
                            } }"></div>
        <div class="cell" style="position: absolute;" data-bind="component: { name: 'kaf000-b-component9',
                            params: {
                                appType: appType,
                                application: application,
                                appDispInfoStartupOutput: $vm.appDispInfoStartupOutput
                            } }"></div>
    </div>
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
                                } }"></div>
    <div data-bind="component: { name: 'kaf009-share', params: {dataFetch: dataFetch, model:model, mode: mode } }"></div>
    <div data-bind="component: { name: 'kaf000-b-component7',
                                params: {
                                    appType: appType,
                                    application: application,
                                    appDispInfoStartupOutput: appDispInfoStartupOutput
                                } }"></div>
    <div data-bind="component: { name: 'kaf000-b-component8',
                                params: {
                                    appType: appType,
                                    appDispInfoStartupOutput: appDispInfoStartupOutput
                                } }"></div>
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
        mode: string = 'edit';
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
                vm.mode = ko.toJS(vm.appDispInfoStartupOutput).appDetailScreenInfo.outputMode == 1 ? 'edit' : 'view';
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
                        goBackApplication: ko.observable(res.goBackApplication)
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
                if ((vm.model.checkbox3() == true || vm.model.checkbox3() == null) && !vm.model.workTypeCode() && (vm.dataFetch().goBackReflect().reflectApplication === 0 || vm.dataFetch().goBackReflect().reflectApplication === 2)) {
                   // $('#workSelect').focus();
					let el = document.getElementById('workSelect');
	                if (el) {
	                    el.focus();                                                    
	                }
                    return;
                } 
			}
            let model = ko.toJS( vm.model );
            let goBackApp = new GoBackApplication(
                model.checkbox1 ? 1 : 0,
                model.checkbox2 ? 1 : 0,
            );
            // is change can be null
            if (!_.isNull(model.checkbox3)) {
                goBackApp.isChangedWork = model.checkbox3 ? 1 : 0;

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
                            location.reload();
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
                    location.reload();
                }
            });
        }






        dispose() {
            const vm = this;

        }
    }
    export class GoBackReflect {
        companyId: string;
        reflectApplication: number;
    }
    export class ModelDto {

        workType: KnockoutObservable<any>;

        workTime: KnockoutObservable<any>;

        appDispInfoStartup: KnockoutObservable<any>;

        goBackReflect: KnockoutObservable<GoBackReflect> = ko.observable( null );

        lstWorkType: KnockoutObservable<any>;

        goBackApplication: KnockoutObservable<any>;
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

}