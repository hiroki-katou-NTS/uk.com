module nts.uk.at.view.kaf000_ref.b.component7.viewmodel {

    @component({
        name: 'kaf000-b-component7',
        template: '/nts.uk.at.web/view/kaf_ref/000/b/component7/index.html'
    })
    class Kaf000BComponent7ViewModel extends ko.ViewModel {
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
        }
    
        mounted() {
            const vm = this;
        }
    }
}