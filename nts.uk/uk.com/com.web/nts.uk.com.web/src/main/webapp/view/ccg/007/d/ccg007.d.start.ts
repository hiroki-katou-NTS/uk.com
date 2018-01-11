module nts.uk.pr.view.ccg007.d {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        screenModel.start().done(function() {
            __viewContext.bind(screenModel);
            $('#employee-code-inp').focus();
            $("#password-input").keyup(function(event) {
                if (event.keyCode == 13) {
                    screenModel.password($('#password-input').val());
                    $("#login-btn").click();
                }
            });
        });
    });
}