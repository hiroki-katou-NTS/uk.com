module nts.uk.com.view.cps009.c.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    let paths: any = {
        copyInitValue: "ctx/bs/person/info/setting/init/ctg/copyInitValue"
    }
    
    export function updateHistory(data): JQueryPromise<any> {
            return nts.uk.request.ajax("com", paths.copyInitValue, data);
    }
}
