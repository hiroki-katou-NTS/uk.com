module nts.uk.at.view.kaf000_ref.a.component2.viewmodel {

    @component({
        name: 'kaf000-a-component2',
        template: '/nts.uk.at.web/view/kaf_ref/000/a/component2/index.html'
    })
    class Kaf000AComponent2ViewModel extends ko.ViewModel {
        appDispInfoStartupOutput: any;
        employeeName: KnockoutObservable<string>;
        created(params: any) {
            const vm = this;
            vm.appDispInfoStartupOutput = params.appDispInfoStartupOutput;
            vm.employeeName = ko.observable("employeeName");
            
            vm.appDispInfoStartupOutput.subscribe(value => {
                vm.employeeName(value.appDispInfoNoDateOutput.employeeInfoLst[0].bussinessName);                 
            });
        }
    
        mounted() {
            const vm = this;
        }
    }
}