module nts.uk.pr.view.ccg007.a {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        screenModel.start().done(function() {
            __viewContext.bind(screenModel);
            $('#contract-code-inp').focus();
            
            // Enter key
            $("#password-input").keyup(function(event) {
                if (event.keyCode == 13) {
                    screenModel.password($('#password-input').val());
                    $("#login-btn").click();
                }
            });
        });
    });
}