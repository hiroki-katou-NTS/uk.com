module kdp003.a {
    let __viewContext: any = window['__viewContext'] || {};

    __viewContext.ready(function() {

        //get info Administrator
        let infoAdministrator = localStorage.getItem("infoAdministrator");

        __viewContext['viewModel'] = new viewmodel.ViewModel();
        __viewContext['viewModel'].start(infoAdministrator).done(() => {
            __viewContext.bind(__viewContext['viewModel']);
        });
    });
}

