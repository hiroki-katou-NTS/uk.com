module nts.uk.at.view.kaf000.test.service{
    import ajax = nts.uk.request.ajax;
    var paths = {
        getApplicationIdByDate : "at/request/application/getApplicationInfo",
    }  
    
    export function getAppId(data: dateInfor){
         return ajax('at', paths.getApplicationIdByDate, data);    
    }
    
    export class dateInfor{
        startDate: any;
        endDate: any;    
    }
}