__viewContext.ready(function() {
    var url_string = window.location.href;
    var urlID = _.split(url_string, '=')[1];
    var server_path = nts.uk.text.format("/ctx/sys/gateway/url/execution/{0}", urlID); 
    nts.uk.ui.block.invisible();
    nts.uk.request.ajax("com", server_path)
    .done((success) => {   
        switch(success.programID){
            case "ccg007": {
                // forgot password screen
                nts.uk.request.jump("com", "/view/ccg/007/"+success.screenID+"/index.xhtml");
            }
            case "kaf002": {
                nts.uk.request.jump("at", "/view/kaf/000/b/index.xhtml", { 'listAppMeta': [success.urlTaskValueList[0]], 'currentApp': success.urlTaskValueList[0] });    
            }
            case "kaf004": {
                nts.uk.request.jump("at", "/view/kaf/000/b/index.xhtml", { 'listAppMeta': [success.urlTaskValueList[0]], 'currentApp': success.urlTaskValueList[0] });     
            }
            case "kaf005": {
                nts.uk.request.jump("at", "/view/kaf/000/b/index.xhtml", { 'listAppMeta': [success.urlTaskValueList[0]], 'currentApp': success.urlTaskValueList[0] });     
            }
            case "kaf006": {
                nts.uk.request.jump("at", "/view/kaf/000/b/index.xhtml", { 'listAppMeta': [success.urlTaskValueList[0]], 'currentApp': success.urlTaskValueList[0] });     
            }
            case "kaf007": {
                nts.uk.request.jump("at", "/view/kaf/000/b/index.xhtml", { 'listAppMeta': [success.urlTaskValueList[0]], 'currentApp': success.urlTaskValueList[0] });      
            }
            case "kaf009": {
                nts.uk.request.jump("at", "/view/kaf/000/b/index.xhtml", { 'listAppMeta': [success.urlTaskValueList[0]], 'currentApp': success.urlTaskValueList[0] });     
            }
            case "kaf010": {
                nts.uk.request.jump("at", "/view/kaf/000/b/index.xhtml", { 'listAppMeta': [success.urlTaskValueList[0]], 'currentApp': success.urlTaskValueList[0] });     
            }   
            default: nts.uk.request.jump("com", "/view/ccg/007/d/index.xhtml");
        }
    })
    .fail((failure) => {
        if(!nts.uk.util.isNullOrEmpty(failure.messageId)){
            nts.uk.ui.dialog.alertError({ messageId: failure.messageId, messageParams: failure.parameterIds }).then(function() {
                nts.uk.request.jump("com", "/view/ccg/007/d/index.xhtml"); 
            });
        } else {
            nts.uk.request.jump("com", "/view/ccg/007/d/index.xhtml"); 
        }
    });
})