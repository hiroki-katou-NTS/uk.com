module nts.uk.com.view.cmf002.k.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    let paths = {
        getOutputCodeConvertByCid: "exio/exo/codeconvert/getAllOutputCodeConvert/{0}"
    }
    
    //Get output code convert by cid
    export function getOutputCodeConvertByCid(cId: string): JQueryPromise<any> {
        return ajax(format(paths.getOutputCodeConvertByCid, cId));
    }
}