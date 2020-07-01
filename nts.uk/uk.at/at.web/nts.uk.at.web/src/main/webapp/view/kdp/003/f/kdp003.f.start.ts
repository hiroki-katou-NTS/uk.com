module kdp003.f {
    let __viewContext: any = window['__viewContext'] || {};
    __viewContext.ready(function() {
        __viewContext['viewModel'] = new vm.ViewModel();
        let mode = nts.uk.ui.windows.getShared('mode');
        __viewContext['viewModel'].startPage(mode).done(() => {
            __viewContext.bind(__viewContext['viewModel']);
        });
    });
}


