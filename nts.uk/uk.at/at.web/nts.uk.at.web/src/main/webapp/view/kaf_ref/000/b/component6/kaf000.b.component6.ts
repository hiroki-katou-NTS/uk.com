module nts.uk.at.view.kaf000_ref.b.component6.viewmodel {

    @component({
        name: 'kaf000-b-component6',
        template: '/nts.uk.at.web/view/kaf_ref/000/b/component6/index.html'
    })
    class Kaf000BComponent6ViewModel extends ko.ViewModel {
        appDispInfoStartupOutput: any;
        appDateString: KnockoutObservable<string>;
        created(params: any) {
            const vm = this;
            vm.appDispInfoStartupOutput = params.appDispInfoStartupOutput;
            vm.appDateString = ko.observable("appDateString");
            
            vm.appDateString(vm.appDispInfoStartupOutput().appDetailScreenInfo.application.appDate);
            params.application().appDate(vm.appDispInfoStartupOutput().appDetailScreenInfo.application.appDate);
            params.application().opAppStartDate(vm.appDispInfoStartupOutput().appDetailScreenInfo.application.opAppStartDate);
		    params.application().opAppEndDate(vm.appDispInfoStartupOutput().appDetailScreenInfo.application.opAppEndDate);
        }
    
        mounted() {
            const vm = this;
        }
    }
}