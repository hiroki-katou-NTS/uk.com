module kdp003.s {
    let __viewContext: any = window['__viewContext'] || {};
    __viewContext.ready(function() {
        var screenModel = new vm.ViewModel();
        screenModel.start().done(() => {

            __viewContext.bind(screenModel);
            
        });
    });
}



