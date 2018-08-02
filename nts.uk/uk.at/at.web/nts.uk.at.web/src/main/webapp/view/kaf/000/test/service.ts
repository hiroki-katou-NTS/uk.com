module nts.uk.at.view.kaf000.test.service{
    import ajax = nts.uk.request.ajax;
    var paths = {
        getApplicationIdByDate : "at/request/application/getApplicationInfo",
        getAppInfoByAppID: "at/request/application/getAppInfoByAppID",
        getAppData: "shared/specialholiday/findByCID"
    }  
    
    export function getAppId(data: dateInfor){
         return ajax('at', paths.getApplicationIdByDate, data);    
    }
    
    export function getAppInfoByAppID(appID: string){
         return ajax('at', paths.getAppInfoByAppID, appID);    
    }
    
    export function getAppData(){
         return ajax('at', paths.getAppData);    
    }
    
    export class dateInfor{
        startDate: any;
        endDate: any;    
    }
}