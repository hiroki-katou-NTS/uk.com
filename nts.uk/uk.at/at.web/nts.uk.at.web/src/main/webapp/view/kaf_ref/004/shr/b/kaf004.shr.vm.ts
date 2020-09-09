module nts.uk.at.view.kaf004_ref.shr.b.viewmodel {
    import WorkManagement = nts.uk.at.view.kaf004_ref.shr.common.viewmodel.WorkManagement;

    @component({
        name: 'kaf004_share',
        template: '/nts.uk.at.web/view/kaf_ref/004/shr/b/index.html'
    })

    class Kaf004ShareViewModel extends ko.ViewModel {
        // workManagement: WorkManagement;
        created(params: any) {
            const vm = this;
            // vm.workManagement = new WorkManagement('--:--', '--:--', '--:--', '--:--', null, null, null, null);
        }

        mounted() {

        }
    }
}