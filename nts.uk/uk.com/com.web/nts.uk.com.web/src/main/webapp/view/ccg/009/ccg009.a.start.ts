module nts.uk.com.view.ccg009.a {
    import ccg009 = nts.uk.com.view.ccg009.a;
    let __viewContext: any = window["__viewContext"] || {};
    __viewContext.ready(function() {
        let screenModel = new ccg009.viewmodel.ScreenModel();
        screenModel.start().done(function() {
            __viewContext.bind(screenModel);
//            $(document).ready(function() {
//                 setTimeout(function () { $('.ntsSearchBox').focus(); }, 100);
//                
//            });
                document.getElementById("single-list_container").focus();
        });
    });
}