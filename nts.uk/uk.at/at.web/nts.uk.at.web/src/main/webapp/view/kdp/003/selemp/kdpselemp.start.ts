module nts.uk.at.view.kdpselemp {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel(undefined, function(data) {
            console.log(data);
        });
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            $('#gridListEmployees_headers > thead > tr:nth-child(1)').css('display', 'none');
            $('#gridListEmployees_headers > thead > tr.ui-iggrid-filterrow.ui-widget').css('display', 'none');
        });
        $(window).resize(function() {});
    });
}
