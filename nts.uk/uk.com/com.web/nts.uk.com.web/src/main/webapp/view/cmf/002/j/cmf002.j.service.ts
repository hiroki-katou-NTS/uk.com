module nts.uk.com.view.cmf002.j.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    var paths = {
        getCharacterDataFormatSetting:          "exio/exo/char/getdatatype",
        setCharacterDataFormatSetting:          "exio/exo/character/add",
        regiterCharacterDataFormatSetting:          "exio/exo/character/update"

    }
    
    export function getCharacterDataFormatSetting(): JQueryPromise<any>{
        return ajax("com", format(paths.getCharacterDataFormatSetting));    
    }
    export function setCharacterDataFormatSetting(chardataset: any): JQueryPromise<any>{
        return ajax("com", format(paths.setCharacterDataFormatSetting, chardataset));    
    }
    export function regiterCharacterDataFormatSetting(chardataset: any): JQueryPromise<any>{
        return ajax("com", format(paths.regiterCharacterDataFormatSetting, chardataset));    
    }
}