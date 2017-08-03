module cps008.a {
    let __viewContext: any = window['__viewContext'] || {};
    __viewContext.ready(function() {
        __viewContext['viewModel'] = new viewmodel.ViewModel();
        __viewContext['viewModel'].start().done(function() {
            __viewContext.bind(__viewContext['viewModel']);
        });
    });
}