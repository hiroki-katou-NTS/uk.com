module nts.uk.at.view.kaf000_ref.b.component3.viewmodel {

    @component({
        name: 'kaf000-b-component3',
        template: '/nts.uk.at.web/view/kaf_ref/000/b/component3/index.html'
    })
    class Kaf000BComponent3ViewModel extends ko.ViewModel {
        appType: number;
        approvalReason: KnockoutObservable<string>;
        created(params: any) {
            const vm = this;
            vm.approvalReason = ko.observable("");
        }
    
        mounted() {
            const vm = this;
        }
    }
}