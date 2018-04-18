module nts.uk.com.view.cmf001.d {
    __viewContext.ready(function() {
        this.transferred.ifPresent(data => {
            console.log(data);
            var screenModel = new viewmodel.ScreenModel(data);
            screenModel.startPage().done(function() {
                __viewContext.bind(screenModel);
                _.defer(() => {
                    $('#D4_3').find("input").first().focus();
                    $("#D3_2").attr('maxlength','2');
                    $("#D3_5").attr('maxlength','2');
                });
            });
        });
        
    });
}