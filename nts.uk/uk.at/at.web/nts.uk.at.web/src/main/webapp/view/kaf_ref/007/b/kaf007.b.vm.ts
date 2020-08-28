module nts.uk.at.view.kaf007_ref.b.viewmodel {
    import Kaf000BViewModel = nts.uk.at.view.kaf000_ref.b.viewmodel.Kaf000BViewModel;
    import AppWorkChange = nts.uk.at.view.kaf007_ref.shr.viewmodel.AppWorkChange; 
    
    @component({
        name: 'kaf007-b',
        template: '/nts.uk.at.web/view/kaf_ref/007/b/index.html'
    })
    class Kaf007BViewModel extends ko.ViewModel {
        application: KnockoutObservable<Application>;
        appWorkChange: KnockoutObservable<AppWorkChange>;
        
        created(params: any) {
            const vm = this;
            vm.application = params.application;
            vm.appWorkChange = params.appWorkChange;
        }
    
        mounted() {
            const vm = this;
            vm.application().appDate(moment(new Date()).format("YYYY/MM/DD"));
        }
    }
}