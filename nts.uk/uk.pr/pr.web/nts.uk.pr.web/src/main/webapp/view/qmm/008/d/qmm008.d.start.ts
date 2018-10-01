module nts.uk.pr.view.qmm008.d {
    __viewContext.ready(function () {
        let screenModel = new nts.uk.pr.view.qmm008.d.viewmodel.ScreenModel();
        __viewContext.bind(screenModel);
        setTimeout(function () {
            $('#D4_23').on("keydown", function (e) {
                let code = e.keyCode || e.which;
                if (code === 9) {
                    e.preventDefault();
                    screenModel.selectedTab("tab-2");
                    $('#D5_4').focus();
                }
            });

            $('#D5_4').keydown(function (e) {
                if (e.shiftKey && e.key === 'Tab') {
                    e.preventDefault();
                    screenModel.selectedTab("tab-1");
                    $('#D4_23').focus();
                }
            });
        }, 2000);
    });
}
