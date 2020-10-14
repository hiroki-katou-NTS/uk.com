module nts.uk.at.kaf020.b {
    import AppType = nts.uk.at.view.kaf000.shr.viewmodel.model.AppType;
    import Kaf000AViewModel = nts.uk.at.view.kaf000.a.viewmodel.Kaf000AViewModel;
    import Application = nts.uk.at.view.kaf000.shr.viewmodel.Application;
    @bean()
    export class Kaf020BViewModel extends Kaf000AViewModel {
        appType: KnockoutObservable<number> = ko.observable(AppType.BUSINESS_TRIP_APPLICATION);
        isSendMail: KnockoutObservable<boolean> = ko.observable(false);
        application: KnockoutObservable<Application> = ko.observable(new Application(this.appType()));
        constructor(props: any) {
            super();
        }
        created(){
            const vm = this;
            vm.loadData([], [], vm.appType());
        }
        mounted(){

        }
    }
}