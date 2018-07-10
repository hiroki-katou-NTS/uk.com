module nts.uk.com.view.cmf002.n.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    var paths = {
        getAWDataFormatSetting:              "exio/exo/aw/getdatatype",
        setAWDataFormatSetting:              "exio/exo/aw/add",
        regiterAWDataFormatSetting:          "exio/exo/aw/update"
    }
    
    export function getAWDataFormatSetting(): JQueryPromise<any>{
        return ajax("com", format(paths.getAWDataFormatSetting));    
    }
    export function setAWDataFormatSetting(awdataset: any): JQueryPromise<any>{
        return ajax("com", format(paths.setAWDataFormatSetting, awdataset));    
    }
    export function regiterAWDataFormatSetting(awdataset: any): JQueryPromise<any>{
        return ajax("com", format(paths.regiterAWDataFormatSetting, awdataset));    
    }
}