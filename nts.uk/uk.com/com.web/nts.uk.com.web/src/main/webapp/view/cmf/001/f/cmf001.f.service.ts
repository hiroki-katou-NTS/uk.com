module nts.uk.com.view.cmf001.f.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    var paths = {
        getCodeConvertByCompanyId:     "exio/exi/codeconvert/getCodeConvertByCompanyId",
        getAcceptCodeConvert:          "exio/exi/codeconvert/getAcceptCodeConvert/{0}",
        addAcceptCodeConvert:          "exio/exi/codeconvert/addAcceptCodeConvert",
        updateAcceptCodeConvert:       "exio/exi/codeconvert/updateAcceptCodeConvert",
        deleteAcceptCodeConvert:       "exio/exi/codeconvert/removeAcceptCodeConvert"
    }

    //Get all accept code convert
    export function getCodeConvertByCompanyId(): JQueryPromise<any> {
        return ajax(paths.getCodeConvertByCompanyId);
    }

    //Get accept code convert by id
    export function getAcceptCodeConvert(convertCode: string): JQueryPromise<any> {
        return ajax(format(paths.getAcceptCodeConvert, convertCode));
    }

    //Add new accept code convert
    export function addAcceptCodeConvert(command): JQueryPromise<any> {
        return ajax(paths.addAcceptCodeConvert, command);
    }

    //Update accept code convert
    export function updateAcceptCodeConvert(command): JQueryPromise<any> {
        return ajax(paths.updateAcceptCodeConvert, command);
    }

    //Delete accept code convert
    export function deleteAcceptCodeConvert(command): JQueryPromise<any> {
        return ajax(paths.deleteAcceptCodeConvert, command);
    }

}