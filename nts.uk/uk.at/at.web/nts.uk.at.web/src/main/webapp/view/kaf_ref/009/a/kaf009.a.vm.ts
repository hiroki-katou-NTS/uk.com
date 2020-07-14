module nts.uk.at.view.kaf009_ref.a.viewmodel {
    import Application = nts.uk.at.view.kaf000_ref.shr.viewmodel.Application;
    import CommonProcess = nts.uk.at.view.kaf000_ref.shr.viewmodel.CommonProcess;
    import Model = nts.uk.at.view.kaf009_ref.shr.viewmodel.Model;

    @bean()
    class Kaf009AViewModel extends ko.ViewModel {

        application: KnockoutObservable<Application>;
        model: Model;
        commonSetting: any;
        appDispInfoStartupOutput: any;
        dataFetch: KnockoutObservable<ModelDto>;
        mode :string = 'edit';

        created( params: any ) {
            const vm = this;
            vm.application = ko.observable( new Application( "", 1, 2, "" ) );
            vm.commonSetting = ko.observable( CommonProcess.initCommonSetting() );
            vm.model = new Model( true, true, true, '01', 'ddd', '30', '33' );
            vm.appDispInfoStartupOutput = ko.observable( CommonProcess.initCommonSetting() );
            this.fetchData();

        }

        mounted() {

        }

        register() {
            const vm = this;
            console.log( ko.toJS( vm.application() ) );
            console.log( this.model );
            
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
            this.dataFetch = ko.observable( {
                workType: ko.observable( { workType: '001', nameWorkType: 'Work Type' } ),
                workTime: ko.observable( { workTime: '001', nameWorkTime: 'Work Ttime' } ),
                appDispInfoStartup: ko.observable( CommonProcess.initCommonSetting() ),
                goBackReflect: ko.observable( { companyId: '00001', reflectApplication: 3 } ),
                lstWorkType: ko.observable( null ),
                goBackApplication: ko.observable( new Application( "", 1, 2, "" ) )
            } )
        }

    }
    export class ModelDto {

        workType: KnockoutObservable<any>;

        workTime: KnockoutObservable<any>;

        appDispInfoStartup: KnockoutObservable<any>;

        goBackReflect: KnockoutObservable<any>;

        lstWorkType: KnockoutObservable<any>;

        goBackApplication: KnockoutObservable<any>;
    }

    const API = {
        startNew: "at/request/application/workchange/startNew"
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

}