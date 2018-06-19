module nts.uk.at.view.ktg031.b.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    var paths: any = {
        getDetail: "sys/share/toppage/alarm/find/detail"
    }
    // viewmodel.BusinessType
    export function getDetail(param: any): JQueryPromise<void> {
        return nts.uk.request.ajax(paths.getDetail, param);
    }
}   