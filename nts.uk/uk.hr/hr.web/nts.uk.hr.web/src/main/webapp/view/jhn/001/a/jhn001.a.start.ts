module jhn001.a {
    let __viewContext: any = window['__viewContext'] || {};

    __viewContext.ready(function() {

        let dataShareJhn003: any;
        this.transferred.ifPresent(data => {
            dataShareJhn003 = data;
        });

        __viewContext['viewModel'] = new viewmodel.ViewModel(dataShareJhn003);
        __viewContext.bind(__viewContext['viewModel']);
    });
}
