module nts.uk.com.view.cps009.a {

    let __viewContext: any = window["__viewContext"] || {};

    __viewContext.ready(function() {

        var screenModel = new viewmodel.ViewModel();
        __viewContext["viewModel"] = screenModel;
        __viewContext.bind(__viewContext["viewModel"]);
        

    });
}