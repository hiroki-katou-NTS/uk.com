module nts.uk.com.view.cmf002.l.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    var paths = {
        getCharacterDataFormatSetting:          "",
        setCharacterDataFormatSetting:          ""
    }
    
    export function getCharacterDataFormatSetting(): JQueryPromise<any>{
        return ajax(format(paths.getCharacterDataFormatSetting));    
    }
    export function setCharacterDataFormatSetting(): JQueryPromise<any>{
        return ajax(format(paths.setCharacterDataFormatSetting));    
    }
}