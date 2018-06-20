__viewContext.ready(function() {
    var url_string = window.location.href;
    var url = new URL(url_string);
    var urlID = url.searchParams.get("id");
    var server_path = nts.uk.text.format("ctx/sys/gateway/url/execution/{0}", urlID); 
    nts.uk.ui.block.invisible();
    nts.uk.request.ajax("com", server_path)
    .done((success) => {
        nts.uk.request.jump("at", "/view/cmm/045/a/index.xhtml?a=0");        
    })
    .fail((failure) => {
        nts.uk.ui.dialog.alertError({ messageId: failure.messageId }).then(function() {
            nts.uk.request.jump("com", "/view/ccg/007/d/index.xhtml"); 
        });
    });
})