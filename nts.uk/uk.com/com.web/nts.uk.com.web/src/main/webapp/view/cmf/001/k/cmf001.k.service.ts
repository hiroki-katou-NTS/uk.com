module nts.uk.com.view.cmf001.k.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    var paths = {
        getCodeConvert:                "exio/exi/codeconvert/getCodeConvertByCompanyId"
    }

    //Get all accept code convert
    export function getCodeConvert(): JQueryPromise<any> {
        return ajax(paths.getCodeConvert);
    }
}