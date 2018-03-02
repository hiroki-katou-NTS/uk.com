module nts.uk.com.view.cmf001.f.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    var paths = {
        getCodeConvert:                "exio/codeconvert/getCodeConvert",
        getAcceptCodeConvert:          "exio/codeconvert/getAcceptCodeConvert/{0}",
        getCodeConvertDetails:         "exio/codeconvert/getCodeConvertDetails/{0}",
        addAcceptCodeConvert:          "exio/codeconvert/addAcceptCodeConvert",
        updateAcceptCodeConvert:       "exio/codeconvert/updateAcceptCodeConvert"
    }

    //Get all accept code convert
    export function getCodeConvert(): JQueryPromise<any> {
        return ajax(paths.getCodeConvert);
    }

    //Get accept code convert by id
    export function getAcceptCodeConvert(convertCode: string): JQueryPromise<any> {
        return ajax(format(paths.getAcceptCodeConvert, convertCode));
    }

    //get accept code convert detail
    export function getCodeConvertDetails(convertCode: string): JQueryPromise<any> {
        return ajax(format(paths.getCodeConvertDetails, convertCode));
    }
    
    //Add new accept code convert
    export function addAcceptCodeConvert(command): JQueryPromise<any> {
        return ajax(paths.getCodeConvert);
    }
    
    //Update accept code convert
    export function updateAcceptCodeConvert(command): JQueryPromise<any> {
        return ajax(paths.getCodeConvert);
    }

}