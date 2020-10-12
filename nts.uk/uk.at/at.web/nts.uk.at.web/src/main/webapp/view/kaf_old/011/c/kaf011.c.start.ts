module nts.uk.at.view.kaf011.c.start {
    let __viewContext: any = window['__viewContext'] || {};
    __viewContext.ready(() => {

        __viewContext['viewModel'] = new c.screenModel.ViewModel();
        __viewContext['viewModel'].start().done(() => {
            __viewContext.bind(__viewContext['viewModel']);
            $("#absDatePickerC").focus();
            __viewContext['viewModel'].kaf000_a.start(__viewContext['viewModel'].employeeID(), 1, 10, moment(new Date()).format("YYYY/MM/DD")).done(() => {
            });
        });
    });
}