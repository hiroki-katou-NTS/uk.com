module cps002.a {
    let __viewContext: any = window['__viewContext'] || {};
    __viewContext.ready(() => {
        __viewContext['viewModel'] = new vm.ViewModel();
        __viewContext.bind(__viewContext['viewModel']);
        __viewContext['viewModel'].selectedId(3);
        __viewContext['viewModel'].next();
//        ko.cleanNode($('#emp_reg_info_wizard')[0]);
//         ko.applyBindings(__viewContext['viewModel'], $('#emp_reg_info_wizard')[0]);
    });
}