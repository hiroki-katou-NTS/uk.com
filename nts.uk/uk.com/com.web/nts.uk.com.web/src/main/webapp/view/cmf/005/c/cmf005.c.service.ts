module nts.uk.com.view.cmf005.c.service {
    import ajax = nts.uk.request.ajax;
   
    var paths = {
        getSystemType: "ctx/sys/assist/systemtype/getlistsystemtype"
    }

    //Get all accept code convert
    export function getListSystemType(): JQueryPromise<any> {
        return ajax(paths.getSystemType);
    }
}