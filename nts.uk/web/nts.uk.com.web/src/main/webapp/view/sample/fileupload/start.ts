__viewContext.ready(function() {
    $("#button").click(function() {
        $("#file_upload").ntsFileUpload({ stereoType: "flowmenu" }).done(function(res) {
            alert(res);
        }).fail(function(err) {
           nts.uk.ui.dialog.alertError(err);
        });
    });
    $("#download").click(function(){
        nts.uk.request.specials.donwloadFile($("#fileid").val());    
    });
    
    
    class ScreenModel {
        
    }
    
    this.bind(new ScreenModel());

});