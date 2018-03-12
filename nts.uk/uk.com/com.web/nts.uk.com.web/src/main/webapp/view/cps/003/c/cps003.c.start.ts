/*
module nts.uk.com.view.cps003.c {
    __viewContext.ready(function() {
        __viewContext["viewModel"] = new viewmodel.ScreenModel();
        
        __viewContext["viewModel"].start().done(function(){
        __viewContext.bind(__viewContext["viewModel"]);
        });
    });
}
*/


module nts.uk.com.view.cps003.c {
    __viewContext.ready(function() {
        __viewContext.screenModel = new viewmodel.ScreenModel();
        __viewContext.bind(__viewContext.screenModel);
    });
}





/*
module cps003.c {
    let __viewContext: any = window['__viewContext'] || { ready: () => { }, bind: (viewModel: any) => { } };

    __viewContext.ready(() => {
        __viewContext['viewModel'] = new vm.ViewModel();
        __viewContext.bind(__viewContext['viewModel']);
    });
}
*/