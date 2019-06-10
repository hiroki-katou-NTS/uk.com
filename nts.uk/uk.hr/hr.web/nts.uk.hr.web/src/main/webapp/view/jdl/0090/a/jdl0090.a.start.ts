module nts.uk.com.view.jdl0090.a {

    __viewContext.ready(function() {

        var screenModel = new viewmodel.ScreenModel();
        __viewContext.bind(screenModel);
        $('#classification').ntsListComponent(screenModel.classifications).done(function() {
            $('#classification').focusComponent();
        });

    });
}