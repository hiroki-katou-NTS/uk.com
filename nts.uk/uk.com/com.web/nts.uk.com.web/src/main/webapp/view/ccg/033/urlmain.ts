__viewContext.ready(function() {
    debugger;
    var url_string = window.location.href;
    var urlID = _.split(url_string, '=')[1];
    var server_path = nts.uk.text.format("/ctx/sys/gateway/url/execution/{0}", urlID); 
    nts.uk.ui.block.invisible();
    nts.uk.request.ajax("com", server_path)
    .done((success) => {   
        switch(success.webAppID){
            case "com": {
                switch(success.programID){
                    case "ccg007": {
                        // forgot password screen
                        nts.uk.request.jump(success.webAppID, "/view/ccg/007/"+success.screenID+"/index.xhtml?id="+urlID);
                        break;             
                    }   
                    default: {
                        let programString = success.programID.substring(0,3);
                        let programNumber = success.programID.substring(3);   
                        nts.uk.request.jump(success.webAppID, "/view/"+programString+"/"+programNumber+"/"+success.screenID+"/index.xhtml"); 
                    }
                }
                break;    
            }
            case "at": {
                switch(success.programID){
                    case "cmm045": {
                        nts.uk.request.jump(success.webAppID, "/view/cmm/045/a/index.xhtml?a=1");  
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
                            startDate: null,
                            endDate: null,
                            //抽出した社員一覧
                            lstExtractedEmployee: [success.sID],
                            //Optional
                            //日付別で起動
                            dateTarget: null,
                            individualTarget: success.sID,
                        }
                        nts.uk.request.jump(success.webAppID, "/view/kdw/003/a/index.xhtml", {initParam: initParam, extractionParam: extractionParam});    
                        break;
                    }
                    case "kmw003": {
                        if(success.urlTaskValueList["activeMode"]=="approval"){
                            nts.uk.request.jump(success.webAppID, "/view/kmw/003/a/index.xhtml?initmode=2");    
                            break;    
                        } else {
                            nts.uk.request.jump(success.webAppID, "/view/kmw/003/a/index.xhtml");    
                            break; 
                        }
                    }    
                    default: {
                        let programString = success.programID.substring(0,3);
                        let programNumber = success.programID.substring(3);   
                        if(success.programID.substring(0,3) == "kaf"){
                            nts.uk.request.jump(success.webAppID, "/view/kaf/000/b/index.xhtml", { 
                                'listAppMeta': [_.first(_.map(success.urlTaskValueList))], 
                                'currentApp': _.first(_.map(success.urlTaskValueList)) });  
                        } else {
                            nts.uk.request.jump(success.webAppID, "/view/"+programString+"/"+programNumber+"/"+success.screenID+"/index.xhtml");     
                        }
                    } 
                }
                break;              
            }
            case "pr": {
                let programString = success.programID.substring(0,3);
                let programNumber = success.programID.substring(3);   
                nts.uk.request.jump(success.webAppID, "/view/"+programString+"/"+programNumber+"/"+success.screenID+"/index.xhtml");        
                break;
            }
            default: {
                nts.uk.ui.dialog.alertError({ messageId: "screen is not available" }).then(function() {
                    nts.uk.request.jump("com", "/view/ccg/007/d/index.xhtml"); 
                });        
            }   
        }
    })
    .fail((failure) => {
        if(!nts.uk.util.isNullOrEmpty(failure.messageId)){
            nts.uk.ui.dialog.alertError({ messageId: failure.messageId, messageParams: failure.parameterIds }).then(function() {
                nts.uk.request.jump("com", "/view/ccg/007/d/index.xhtml"); 
            });
        } else {
            nts.uk.ui.dialog.alertError({ messageId: "unknown error" }).then(function() {
                nts.uk.request.jump("com", "/view/ccg/007/d/index.xhtml"); 
            });
        }
    });
})