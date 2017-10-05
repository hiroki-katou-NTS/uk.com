module nts.uk.at.view.kaf000.test.service{
    var paths = {
        getApplicationIdByDate : "at/request/application/getApplicationId"
    }  
    
    export function getAppId(data: dateInfor){
         return request.ajax('at', paths.getApplicationIdByDate, data);    
    }
    
    export class dateInfor{
        startDate: any;
        endDate: any;    
    }
}