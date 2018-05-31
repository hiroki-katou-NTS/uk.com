module nts.uk.at.view.kaf011.a.start {
    let __viewContext: any = window['__viewContext'] || {};
    __viewContext.ready(() => {

        let vm =  __viewContext['viewModel'] = new a.screenModel.ViewModel();
        __viewContext['viewModel'].start().done(() => {
            __viewContext.bind(__viewContext['viewModel']);
            __viewContext['viewModel'].kaf000_a.start(vm.employeeID(), 1, 10, moment(new Date()).format("YYYY/MM/DD")).done(() => {
            });
        });
    });
}