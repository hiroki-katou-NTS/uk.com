module nts.uk.at.view.kdp003.a {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        //get info Administrator
        let infoAdministrator = localStorage.getItem("infoAdministrator");
        
        screenModel.startPage(infoAdministrator).done(function() {
            __viewContext.bind(screenModel);
            screenModel.reCalGridWidthHeight();
        });
        $(window).resize(function () {
            screenModel.reCalGridWidthHeight();
        });
    });
}
