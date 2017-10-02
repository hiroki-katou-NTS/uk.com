module nts.uk.at.view.kaf000.test.service{
    var paths = {
        getApplicationIdByDate : "at/request/application/getApplicationId"
    }  
    
    export function getAppId(data: dateInfor){
         return request.ajax('com', paths.getApplicationIdByDate, data);    
    }
    
    export class dateInfor{
        startDate: any;
        endate: any;    
    }
}