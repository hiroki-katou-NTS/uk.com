module nts.uk.at.view.kdw002.b {
    export module service {
        var paths: any = {
            getBusinessTypes: "at/record/businesstype/findAll",
            getListDailyServiceTypeControl: "at/share/DailyServiceTypeControl/getListDailyServiceTypeControl/",
            updateDailyService: "at/share/DailyServiceTypeControl/updateListDailyServiceTypeControlItem"
            }
        
        export function getBusinessTypes(): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.getBusinessTypes);
        }
        
         export function getListDailyServiceTypeControl(businessCode): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.getListDailyServiceTypeControl + businessCode);
        }
        
         export function updateDailyService(command): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.updateDailyService , command);
        }
        
    }
}
