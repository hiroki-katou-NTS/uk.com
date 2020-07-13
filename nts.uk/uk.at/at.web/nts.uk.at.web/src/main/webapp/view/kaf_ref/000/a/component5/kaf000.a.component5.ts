module nts.uk.at.view.kaf000_ref.a.component5.viewmodel {

    @component({
        name: 'kaf000-a-component5',
        template: '/nts.uk.at.web/view/kaf_ref/000/a/component5/index.html'
    })
    class Kaf000AComponent5ViewModel extends ko.ViewModel {
        appType: number;
        appDispInfoStartupOutput: any;
        opAppStandardReasonCD: KnockoutObservable<number>;
        opAppReason: KnockoutObservable<string>;
        reasonTypeItemLst: KnockoutObservableArray<any>;
        created(params: any) {
            const vm = this;
            vm.opAppStandardReasonCD = ko.observable(1);
            vm.opAppReason = ko.observable("");
            vm.reasonTypeItemLst = ko.observableArray([]);
            vm.reasonTypeItemLst([
                {
                    appStandardReasonCD: 1,
                    opReasonForFixedForm: "1"    
                }
            ]);
            vm.appDispInfoStartupOutput = params.appDispInfoStartupOutput;
            vm.appDispInfoStartupOutput.subscribe(value => {
                
            });
        }
    
        mounted() {
            const vm = this;
        }
    }
}