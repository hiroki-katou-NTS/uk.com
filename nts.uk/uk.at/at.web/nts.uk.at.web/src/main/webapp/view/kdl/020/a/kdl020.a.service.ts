module nts.uk.at.view.kdl020.a.service {

    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    var paths: any = {
        startPage: "at/request/dialog/annualholiday/startPage"

    }
    
    export function startPage(param){
        return ajax(paths.startPage, param);
    }
}