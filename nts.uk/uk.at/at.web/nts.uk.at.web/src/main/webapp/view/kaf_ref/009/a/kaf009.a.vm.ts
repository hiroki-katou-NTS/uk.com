module nts.uk.at.view.kaf009_ref.a.viewmodel {
    import Application = nts.uk.at.view.kaf000_ref.shr.viewmodel.Application;
    import CommonProcess = nts.uk.at.view.kaf000_ref.shr.viewmodel.CommonProcess;
    import Model = nts.uk.at.view.kaf009_ref.shr.viewmodel.Model;

    @bean()
    class Kaf009AViewModel extends ko.ViewModel {
        
        application: KnockoutObservable<Application>;
        applicationTest: any = {
                version: 1,
                // appID: '939a963d-2923-4387-a067-4ca9ee8808zz',
                prePostAtr: 1,
                // employeeID: '',
                appType: 2,
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
        model: Model;
//        commonSetting: any;
        appDispInfoStartupOutput: any;
        dataFetch: KnockoutObservable<ModelDto> = ko.observable(null);
        mode: string = 'edit';

        created(params: any) {
            const vm = this;
            vm.application = ko.observable(new Application("", 1, [], 2, "", "", 0));
//            vm.commonSetting = ko.observable(CommonProcess.initCommonSetting());
            vm.model = new Model(true, true, true, '001', 'WorkType', '001', 'WorkTime');
            vm.appDispInfoStartupOutput = ko.observable(CommonProcess.initCommonSetting());
            vm.application().appDate(moment(new Date()).format("YYYY/MM/DD"));
            

        }

        mounted() {
            const vm = this;
            vm.fetchData();
        }

        register() {
            const vm = this;
            console.log(vm.applicationTest);
            console.log(ko.toJS(vm.model));
            let model = ko.toJS(vm.model);
            let goBackApp = new GoBackApplication(
                    model.checkbox1 ? 1 : 0,
                    model.checkbox2 ? 1 : 0,                          
            ); 
            // is change can be null
            goBackApp.isChangedWork = model.checkbox3 ? 1 : 0 ;
            if (model.workTypeCode) {
               let dw = new DataWork(new InforType(model.workTypeCode, model.workTypeName));
               if (model.workTimeCode) {
                   dw.workTime = new InforType(model.workTimeCode, model.workTimeName);
               }
               goBackApp.dataWork = dw; 
                
            }
            console.log(goBackApp);
            
            let param = {
                companyId: this.$user.companyId,
                agentAtr: true,
                applicationDto: vm.applicationTest,
                goBackDirectlyDto: goBackApp,
                inforGoBackCommonDirectDto: vm.dataFetch,  
            };
            vm.$ajax(API.checkRegister,param)
                .done(res => {
                   console.log(res); 
                })
                .fail(err => {
                    console.log(err);
                });
            //            // assign A8
            //            if (this.dataFetch().goBackReflect()) {
            //                if (this.dataFetch().goBackReflect().reflectApplication == ApplicationStatus.DO_REFLECT_1 
            //                        || this.dataFetch().goBackReflect().reflectApplication == ApplicationStatus.DO_NOT_REFLECT_1) {
            //                    
            //                    this.dataFetch().goBackApplication().isChangedWork = this.model.checkbox3();
            //                }else if (this.dataFetch().goBackReflect().reflectApplication == ApplicationStatus.DO_REFLECT){
            //                    this.dataFetch().goBackApplication().isChangedWork = true;
            //                }
            //            }
            //            
            //            if (this.dataFetch().goBackApplication().isChangedWork) {
            //                
            ////                A8_2
            //                this.dataFetch().goBackApplication().dataWork.workType.workType = this.model.workTypeCode();
            //                this.dataFetch().goBackApplication().dataWork.workType.nameWorkType = this.model.workTypeName();
            //                
            ////                A8_4                
            //                this.dataFetch().goBackApplication().dataWork.workTime.workTime = this.model.workTimeCode();
            //                this.dataFetch().goBackApplication().dataWork.workTime.nameWorkTime = this.model.workTimeName();

            //}


        }

        fetchData() {
            const vm = this;
            vm.$blockui("show");
            let params = {
                ApplicantEmployeeID: null,
                ApplicantList: null
            }
            vm.$ajax(API.startNew, params)
                .done(res => {
                    console.log(res);
                    vm.dataFetch({
                        workType: ko.observable(res.workType),
                        workTime: ko.observable(res.workTime),
                        appDispInfoStartup: ko.observable(res.appDispInfoStartup),
                        goBackReflect: ko.observable(res.goBackReflect),
                        lstWorkType: ko.observable(res.lstWorkType),
                        goBackApplication: ko.observable(res.gobackdirectly)
                    });
                    vm.appDispInfoStartupOutput(res.appDispInfoStartup);
//                    vm.appDispInfoStartupOutput(CommonProcess.initCommonSetting());
                    vm.$blockui("hide");
                }).fail(err => {
                    vm.$blockui("hide");
                });
            
        }

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
    export class InforType {
        code: string;
        name: string;
        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }
    export class DataWork {
        workType: InforType;
        workTime?: InforType;
        constructor(workType: InforType, workTime?: InforType) {
            this.workType = workType;
            this.workTime = workTime;
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

    const API = {
        startNew: "at/request/application/gobackdirectly/getGoBackCommonSettingNew",
        checkRegister: "at/request/application/gobackdirectly/checkBeforeRegisterNew"
    }

    export class ApplicationStatus {
        //        反映しない
        public static DO_NOT_REFLECT: number = 0;
        //        反映する
        public static DO_REFLECT: number = 1;
        //      申請時に決める(初期値：反映しない)
        public static DO_NOT_REFLECT_1: number = 2;
        //        申請時に決める(初期値：反映する)
        public static DO_REFLECT_1: number = 3;
    }
    export class ParamBeforeRegister {
        companyId: string;
        agentAtr: boolean;
        applicationDto: any;
        goBackDirectlyDto: any;
        inforGoBackCommonDirectDto: any;

    }

}