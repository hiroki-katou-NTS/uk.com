module nts.uk.com.view.kdp001.a.service {

    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    var paths: any = {
        startPage: "bs/employee/group_common_master/start-page-a",
        getStampData: 'at/record/stamp/management/personal/stamp/getStampData'

    }

    export function startPage(param) {
        return ajax(paths.startPage, param);
    }
    
    
    export function getStampData(param) {
        return ajax(paths.getStampData, param);
    }


}