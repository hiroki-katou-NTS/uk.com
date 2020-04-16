module nts.uk.at.view.kaf011.a.start {
    let __viewContext: any = window['__viewContext'] || {};
    __viewContext.ready(() => {

        let vm = __viewContext['viewModel'] = new a.screenModel.ViewModel();
        __viewContext['viewModel'].start().done((data) => {
            __viewContext.bind(__viewContext['viewModel']);
            __viewContext['viewModel'].kaf000_a.start(vm.employeeID(), 1, 10, formatDate(vm.appDate())).done(() => {
            });
            vm.kaf000_a.initData({
                errorFlag: data.appDispInfoStartup.appDispInfoWithDateOutput.errorFlag,
                listApprovalPhaseStateDto: data.appDispInfoStartup.appDispInfoWithDateOutput.listApprovalPhaseState        
            });
            let transDate = vm.transferDate();
            if (transDate) {
                __viewContext['viewModel'].recWk().appDate(formatDate(transDate));
            }
        });
    });
    function formatDate(date){
        return  moment(date).format("YYYY/MM/DD");
    }
}