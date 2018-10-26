__viewContext.ready(function() {
    debugger;
    var url_string = window.location.href;
    var urlID = _.split(url_string, '=')[1];
    var server_path = nts.uk.text.format("/ctx/sys/gateway/url/execution/{0}", urlID); 
    nts.uk.ui.block.invisible();
    nts.uk.request.ajax("com", server_path)
    .done((success) => {   
        switch(success.programID){
            case "ccg007": {
                // forgot password screen
                nts.uk.request.jump("com", "/view/ccg/007/"+success.screenID+"/index.xhtml?id="+urlID);
                break;
            }
            case "kaf002": {
                nts.uk.request.jump("at", "/view/kaf/000/b/index.xhtml", { 
                    'listAppMeta': [Object.values(success.urlTaskValueList)[0]], 
                    'currentApp': Object.values(success.urlTaskValueList)[0] });    
                break;
            }
            case "kaf004": {
                nts.uk.request.jump("at", "/view/kaf/000/b/index.xhtml", { 
                    'listAppMeta': [Object.values(success.urlTaskValueList)[0]], 
                    'currentApp': Object.values(success.urlTaskValueList)[0] });   
                break;   
            }
            case "kaf005": {
                nts.uk.request.jump("at", "/view/kaf/000/b/index.xhtml", { 
                    'listAppMeta': [Object.values(success.urlTaskValueList)[0]], 
                    'currentApp': Object.values(success.urlTaskValueList)[0] }); 
                break;     
            }
            case "kaf006": {
                nts.uk.request.jump("at", "/view/kaf/000/b/index.xhtml", { 
                    'listAppMeta': [Object.values(success.urlTaskValueList)[0]], 
                    'currentApp': Object.values(success.urlTaskValueList)[0] });  
                break;  
            }
            case "kaf007": {
                nts.uk.request.jump("at", "/view/kaf/000/b/index.xhtml", { 
                    'listAppMeta': [Object.values(success.urlTaskValueList)[0]], 
                    'currentApp': Object.values(success.urlTaskValueList)[0] });   
                break;   
            }
            case "kaf009": {
                nts.uk.request.jump("at", "/view/kaf/000/b/index.xhtml", { 
                    'listAppMeta': [Object.values(success.urlTaskValueList)[0]], 
                    'currentApp': Object.values(success.urlTaskValueList)[0] });     
                break;
            }
            case "kaf010": {
                nts.uk.request.jump("at", "/view/kaf/000/b/index.xhtml", { 
                    'listAppMeta': [Object.values(success.urlTaskValueList)[0]], 
                    'currentApp': Object.values(success.urlTaskValueList)[0] });  
                break;    
            }   
            case "kaf011": {
                nts.uk.request.jump("at", "/view/kaf/000/b/index.xhtml", { 
                    'listAppMeta': [Object.values(success.urlTaskValueList)[0]], 
                    'currentApp': Object.values(success.urlTaskValueList)[0] });  
                break;    
            }   
            case "cmm045": {
                nts.uk.request.jump("at", "/view/cmm/045/a/index.xhtml?a=1");  
                break;   
            } 
            case "kdw003": {
                var initParam = {
                    //画面モード
                    screenMode: success.urlTaskValueList["screenMode"]=="normal" ? 0 : 1,
                    //社員一覧
                    lstEmployee: [success.sID],
                    //エラー参照を起動する
                    errorRefStartAtr: success.urlTaskValueList["errorRef"],
                    //期間を変更する
                    changePeriodAtr: success.urlTaskValueList["changePeriod"],
                    //処理締め
                    targetClosue: null,
                    //Optional
                    //打刻初期値
                    initClock: null,
                    //遷移先の画面
                    transitionDesScreen: null,
                }
        
                var extractionParam = {
                    //表示形式
                    displayFormat: 0, // DPCorrectionDisplayFormat.INDIVIDUAl(個人別)
                    //期間
                    startDate: moment.utc(success.issueDate).format("YYYY/MM/DD"),
                    endDate: moment.utc(success.issueDate).format("YYYY/MM/DD"),
                    //抽出した社員一覧
                    lstExtractedEmployee: [success.sID],
                    //Optional
                    //日付別で起動
                    dateTarget: moment.utc(success.issueDate).format("YYYY/MM/DD"),
                    individualTarget: success.sID,
                }
                nts.uk.request.jump("at", "/view/kdw/003/a/index.xhtml", {initParam: initParam, extractionParam: extractionParam});    
                break;
            }
            case "kdw004": {
                nts.uk.request.jump("at", "/view/kdw/004/a/index.xhtml");  
                break;   
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