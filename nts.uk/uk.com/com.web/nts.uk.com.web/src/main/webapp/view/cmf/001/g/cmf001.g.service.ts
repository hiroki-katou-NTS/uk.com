module nts.uk.com.view.cmf001.g.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    var paths = {
        getAcceptCodeConvert:          "exio/exi/codeconvert/getAcceptCodeConvert/{0}"
    }


    //Get accept code convert by id
    export function getAcceptCodeConvert(convertCode: string): JQueryPromise<any> {
        return ajax(format(paths.getAcceptCodeConvert, convertCode));
    }
}