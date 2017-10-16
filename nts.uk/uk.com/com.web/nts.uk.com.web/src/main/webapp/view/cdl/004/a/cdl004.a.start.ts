module nts.uk.com.view.cdl004.a {

    __viewContext.ready(function() {

        var screenModel = new viewmodel.ScreenModel();
        __viewContext.bind(screenModel);
        $('#jobtitle').ntsListComponent(screenModel.jobtitles).done(function() {
            $('#jobtitle').focusComponent();
        });

    });
}