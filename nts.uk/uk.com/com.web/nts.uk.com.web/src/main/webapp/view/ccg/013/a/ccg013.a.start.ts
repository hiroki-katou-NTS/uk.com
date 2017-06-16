module ccg013.a {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        screenModel.startPage()
        __viewContext.bind(screenModel);

        
        $(function() {
                $("#sortable").sortable();
                $("#sortable").disableSelection();
            });
    });
}