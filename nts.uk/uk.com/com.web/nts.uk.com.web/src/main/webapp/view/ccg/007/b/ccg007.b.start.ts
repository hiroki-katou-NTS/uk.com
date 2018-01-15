module nts.uk.pr.view.ccg007.b {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        screenModel.start().done(function() {
            __viewContext.bind(screenModel);
            $('#login-id-inp').focus();
            
            // Enter keypress
            $("#password-input").keyup(function(event) {
                if (event.keyCode == 13) {
                    screenModel.password($('#password-input').val());
                    $("#login-btn").click();
                }
            });
            
        });
    });
}