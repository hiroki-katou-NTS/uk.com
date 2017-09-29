
module cps002.g {
    let __viewContext: any = window['__viewContext'] || {};
    __viewContext.ready(() => {
        __viewContext['viewModel'] = new vm.DemoViewModel();
        __viewContext.bind(__viewContext['viewModel']);
    });
}