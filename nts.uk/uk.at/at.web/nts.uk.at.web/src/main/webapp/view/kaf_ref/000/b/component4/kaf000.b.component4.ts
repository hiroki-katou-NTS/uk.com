module nts.uk.at.view.kaf000_ref.b.component4.viewmodel {

    @component({
        name: 'kaf000-b-component4',
        template: '/nts.uk.at.web/view/kaf_ref/000/b/component4/index.html'
    })
    class Kaf000BComponent4ViewModel extends ko.ViewModel {
        appDispInfoStartupOutput: any;
        employeeName: KnockoutObservable<string>;
        created(params: any) {
            const vm = this;
            vm.appDispInfoStartupOutput = params.appDispInfoStartupOutput;
            vm.employeeName = ko.observable("employeeName");
            
            vm.employeeName(vm.appDispInfoStartupOutput().appDispInfoNoDateOutput.employeeInfoLst[0].bussinessName);   
            params.application().employeeIDLst(_.map(vm.appDispInfoStartupOutput().appDispInfoNoDateOutput.employeeInfoLst, (o: any) => o.sid)); 
        }
    
        mounted() {
            const vm = this;
        }
    }
}