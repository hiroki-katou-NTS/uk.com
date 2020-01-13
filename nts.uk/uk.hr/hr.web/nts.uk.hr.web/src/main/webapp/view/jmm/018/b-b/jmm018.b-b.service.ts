module nts.uk.com.view.jmm018.tabb.service {

    var paths: any = {
        getLatestHistId: "employmentRegulationHistory/getLatestHistId",
        
        
        find: "sys/share/toppagealarm/find",
        updateWorkType: "sys/share/toppagealarm/add",
    }

    export function find() {
        return nts.uk.request.ajax(paths.find);
    }

    export function update(command: Array<viewmodel.TopPageAlarmSetDto>): JQueryPromise<any> {
        return nts.uk.request.ajax("com", paths.updateWorkType, command);
    };
    
    export function getLatestHistId(): JQueryPromise<any> {
        return nts.uk.request.ajax(paths.getLatestHistId);
    }

}