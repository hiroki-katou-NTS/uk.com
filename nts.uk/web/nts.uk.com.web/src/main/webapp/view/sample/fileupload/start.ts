__viewContext.ready(function() {
    $("#button").click(function() {
        $("#file_upload").ntsFileUpload({ stereoType: "any" }).done(function(res) {
            alert(res);
        }).fail(function(err) {
            alert(err);
        });
    });


});