module  nts.uk.at.view.kdl046.a {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        __viewContext.bind(screenModel);
        setTimeout(() => {
            $("#kcp017-switch").focus();
        }, 500);
    });
}