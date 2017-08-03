__viewContext.ready(function () {
    class ScreenModel {
        constructor() {
            var self = this;
        }
    }
    this.bind(new ScreenModel());
});


function craeteButton() {
    $("#switch-language").ntsSwitchMasterLanguage();
    
    $("#switch-language").on( "selectionChanged", function( event, arg1, arg2 ) {
        alert( event.detail.languageId ); // "bar"
    });
}