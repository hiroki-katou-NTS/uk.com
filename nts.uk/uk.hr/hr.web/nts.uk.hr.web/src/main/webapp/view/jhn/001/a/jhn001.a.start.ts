module jhn001.a {
    let __viewContext: any = window['__viewContext'] || {};

    __viewContext.ready(function() {

        //get param url
        let url = $(location).attr('search');
        let reportId: number = url.split("=")[1];

        __viewContext['viewModel'] = new viewmodel.ViewModel(reportId);
        __viewContext.bind(__viewContext['viewModel']);
    });
}
