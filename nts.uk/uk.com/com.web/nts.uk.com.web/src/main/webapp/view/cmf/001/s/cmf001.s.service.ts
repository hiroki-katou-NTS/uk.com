module nts.uk.com.view.cmf001.s.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    var paths = {
        getLogResultsList: "exio/exi/execlog/getLogResultsList",
    }

    export function getLogResultsList(time : any): JQueryPromise<any> {
        return ajax("com", paths.getLogResultsList, time);
    }
}