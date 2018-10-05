module nts.uk.pr.view.qmm005.a {
    __viewContext.ready(function() {
        __viewContext['screenModel'] = new viewmodel.ScreenModel();
        __viewContext['screenModel'].startPage().done(function() {
            __viewContext.bind(__viewContext['screenModel']);
            $('#A2_2 tr').on("change", function(event){
                console.log(this);
            });
        });
    });
}
