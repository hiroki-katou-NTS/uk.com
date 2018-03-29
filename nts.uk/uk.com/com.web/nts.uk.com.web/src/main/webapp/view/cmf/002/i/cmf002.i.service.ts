module nts.uk.com.view.cmf002.i.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    var paths = {
        getNumericDataFormatSetting:          "",
        setNumericDataFormatSetting:          ""
    }
    
    export function getNumericDataFormatSetting(): JQueryPromise<any>{
        return ajax(format(paths.getNumericDataFormatSetting));    
    }
    export function setNumericDataFormatSetting(): JQueryPromise<any>{
        return ajax(format(paths.setNumericDataFormatSetting));    
    }
}