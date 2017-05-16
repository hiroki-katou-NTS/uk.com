__viewContext.ready(function() {
    $("#button").click(function() {
        $("#file_upload").ntsFileUpload({ stereoType: "flowmenu" }).done(function(res) {
            alert(res);
        }).fail(function(err) {
            alert(err);
        });
    });


});