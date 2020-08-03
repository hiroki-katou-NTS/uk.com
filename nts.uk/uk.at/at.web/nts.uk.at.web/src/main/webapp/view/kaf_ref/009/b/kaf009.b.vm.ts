module nts.uk.at.view.kaf009_ref.b.viewmodel {
  //import Kaf000BViewModel = nts.uk.at.view.kaf000_ref.b.viewmodel.Kaf000BViewModel;
    import Application = nts.uk.at.view.kaf000_ref.shr.viewmodel.Application;
    import AppWorkChange = nts.uk.at.view.kaf007_ref.shr.viewmodel.AppWorkChange; 
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
        applicationTest: any = {
            version: 1,
            // appID: '939a963d-2923-4387-a067-4ca9ee8808zz',
            prePostAtr: 1,
            employeeID: this.$user.employeeId,
            appType: 4,
            appDate: moment(new Date()).format('YYYY/MM/DD'),
            enteredPerson: '1',
            inputDate: moment(new Date()).format('YYYY/MM/DD HH:mm:ss'),
            reflectionStatus: {
                listReflectionStatusOfDay: [{
                    actualReflectStatus: 1,
                    scheReflectStatus: 1,
                    targetDate: '2020/01/07',
                    opUpdateStatusAppReflect: {
                        opActualReflectDateTime: '2020/01/07 20:11:11',
                        opScheReflectDateTime: '2020/01/07 20:11:11',
                        opReasonActualCantReflect: 1,
                        opReasonScheCantReflect: 0

                    },
                    opUpdateStatusAppCancel: {
                        opActualReflectDateTime: '2020/01/07 20:11:11',
                        opScheReflectDateTime: '2020/01/07 20:11:11',
                        opReasonActualCantReflect: 1,
                        opReasonScheCantReflect: 0
                    }
                }]
            },
            opStampRequestMode: 1,
            opReversionReason: '1',
             opAppStartDate: '2020/08/07',
             opAppEndDate: '2020/08/08',
             opAppReason: 'jdjadja',
             opAppStandardReasonCD: 1


        };
    
    
        created(
            params: { 
                appDispInfoStartupOutput: any, 
                eventUpdate: (evt: () => void ) => void
            }
        ) {
            const vm = this;
            vm.appDispInfoStartupOutput = params.appDispInfoStartupOutput;
            vm.application = ko.observable(new Application(vm.appDispInfoStartupOutput().appDetailScreenInfo.application.appID, 1, [], 2, "", "", 0));
            vm.model = new Model(true, true, true, '001', 'WorkType', '001', 'WorkTime');
            if (ko.toJS(vm.appDispInfoStartupOutput).appDetailScreenInfo) {
                vm.mode = ko.toJS(vm.appDispInfoStartupOutput).appDetailScreenInfo.outputMode == 1 ? 'edit' : 'view';    
            }
            vm.createParamKAF009();
            
            vm.applicationTest = vm.appDispInfoStartupOutput().appDetailScreenInfo.application;
            
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
                vm.$dialog.error({messageId: err.msgId});
            }).always(() => vm.$blockui('hide'));
        }
    
        mounted() {
            const vm = this;
        }
        
        // event update cần gọi lại ở button của view cha
        update() {
            const vm = this;
            let application = ko.toJS(vm.application);

            vm.applicationTest.prePostAtr = application.prePostAtr;
//            vm.applicationTest.opAppStartDate = application.opAppStartDate;
//            vm.applicationTest.opAppEndDate = application.opAppEndDate;
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
                    console.log(err);
                    vm.$dialog.error({messageId: err.msgId});
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
            .fail(errRegister => {
                console.log(errRegister);
                
                vm.$dialog.error({messageId: errRegister.msgId});
                
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