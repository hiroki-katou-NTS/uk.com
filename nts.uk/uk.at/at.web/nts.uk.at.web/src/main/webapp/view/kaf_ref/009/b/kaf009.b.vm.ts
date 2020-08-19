module nts.uk.at.view.kaf009_ref.b.viewmodel {
    import Application = nts.uk.at.view.kaf000_ref.shr.viewmodel.Application;
    import Model = nts.uk.at.view.kaf009_ref.shr.viewmodel.Model;
    
    @component({
        name: 'kaf009-b',
        template: '/nts.uk.at.web/view/kaf_ref/009/b/index.html'
    })
    class Kaf009BViewModel extends ko.ViewModel {
        
        appDispInfoStartupOutput: any;
        application: KnockoutObservable<Application>;
        model: Model;
        dataFetch: KnockoutObservable<ModelDto> = ko.observable(null);
        mode: string = 'edit';
		approvalReason: KnockoutObservable<string>;
        applicationTest: any = {
            employeeID: this.$user.employeeId,
            appDate: moment(new Date()).format('YYYY/MM/DD'),
            enteredPerson: '',
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
				application: any,
				printContentOfEachAppDto: PrintContentOfEachAppDto,
            	approvalReason: any,
                appDispInfoStartupOutput: any, 
                eventUpdate: (evt: () => void ) => void
            }
        ) {
            const vm = this;
            vm.appDispInfoStartupOutput = params.appDispInfoStartupOutput;
            vm.application = ko.observable(new Application(vm.appDispInfoStartupOutput().appDetailScreenInfo.application.appID, 1, [], 2, "", "", 0));
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
        }
        createParamKAF009() {
            let vm = this;
            vm.$blockui('show');
            vm.$ajax(API.getDetail, {
                companyId: vm.$user.companyId,
                applicationId: ko.toJS(vm.appDispInfoStartupOutput).appDetailScreenInfo.application.appID
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
                }
            }).fail(err => {
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
                vm.$dialog.error(param);
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
            let model = ko.toJS( vm.model );
            let goBackApp = new GoBackApplication(
                model.checkbox1 ? 1 : 0,
                model.checkbox2 ? 1 : 0,
            );
            // is change can be null
            goBackApp.isChangedWork = model.checkbox3 ? 1 : 0;
            let dw = new DataWork( model.workTypeCode );
            if ( model.workTimeCode ) {
                dw.workTime = model.workTimeCode
            }
            goBackApp.dataWork = dw;
            console.log( goBackApp );
            
            
            vm.$blockui("show");
            
            vm.$validate('.nts-input', '#kaf000-a-component3-prePost', '#kaf000-a-component5-comboReason')
                .then(isValid => {
                    if (isValid) {
                        let param = {
                                companyId: this.$user.companyId,
                                agentAtr: true,
                                applicationDto: vm.applicationTest,
                                goBackDirectlyDto: goBackApp,
                                inforGoBackCommonDirectDto: ko.toJS( vm.dataFetch ),
                                mode: false
                            };
                        
                        
                        return vm.$ajax(API.checkRegister, param);
                    }
                })
                .then(res => {
                    console.log( res );

                    if ( _.isEmpty( res ) ) {
                        return vm.registerData( goBackApp );
                    } else {
                        let listTemp = _.clone( res );
                        vm.handleConfirmMessage( listTemp, goBackApp );

                    }
                    
                })
                .fail(err => {
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
                    vm.$dialog.error(param);
                })
                .always(() => vm.$blockui("hide"))
             
        }
        public handleConfirmMessage(listMes: any, res: any) {
            let vm = this;
            if (!_.isEmpty(listMes)) {
                let item = listMes.shift();
                vm.$dialog.confirm({ messageId: item.msgID }).then((value) => {
                    if (value == 'yes') {
                        if (_.isEmpty(listMes)) {
                            vm.registerData(res);
                        } else {
                            vm.handleConfirmMessage(listMes, res);
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
                    inforGoBackCommonDirectDto: ko.toJS(vm.dataFetch),
                   
            }
            
             vm.$ajax( API.updateApplication, paramsUpdate )
                .done( resRegister => {
                    console.log( resRegister );
                    this.$dialog.info( { messageId: "Msg_15" } ).then(() => {
                        // bussiness logic after error show
                        location.reload();
                    } );
                })
            .fail(err => {
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
                vm.$dialog.error(param);
                
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