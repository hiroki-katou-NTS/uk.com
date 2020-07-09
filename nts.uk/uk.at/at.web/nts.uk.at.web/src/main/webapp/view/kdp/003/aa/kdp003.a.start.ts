module nts.uk.at.view.kdp003.a {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        //get info Administrator
        let infoAdministrator = localStorage.getItem("infoAdministrator");
        // neu infoAdministrator = null thi la lần đầu đăng nhập => mode admin
        // neu infoAdministrator != null thi sẽ lây thông tin này để login.
        
        screenModel.startPage(infoAdministrator).done(function() {
            __viewContext.bind(screenModel);
            screenModel.setWidth();
            screenModel.reCalGridWidthHeight();
        });
        $(window).resize(function () {
            screenModel.reCalGridWidthHeight();
        });
    });
}
