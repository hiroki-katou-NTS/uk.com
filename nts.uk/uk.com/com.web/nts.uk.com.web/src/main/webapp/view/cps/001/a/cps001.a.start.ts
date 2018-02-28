module cps001.a {
    import setShared = nts.uk.ui.windows.setShared;

    let __viewContext: any = window['__viewContext'] || {};
    __viewContext.ready(() => {
        __viewContext.transferred.ifPresent(data => {
            setShared("CPS001A_PARAMS", data);
        });

        __viewContext['viewModel'] = new vm.ViewModel();
        __viewContext.bind(__viewContext['viewModel']);

        $(document).on('click', '#lefttabs', (evt) => {
            if ($(evt.target).hasClass('ui-tabs-tab')) {
                setShared("__RELOAD_DATA", undefined);
            }
        });
    });
}