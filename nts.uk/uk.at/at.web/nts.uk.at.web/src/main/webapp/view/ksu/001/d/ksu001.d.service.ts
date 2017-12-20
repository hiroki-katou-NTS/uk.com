module nts.uk.at.view.ksu001.d.service {
    var paths = {
        updateBasicSchedule: "at/schedule/basicschedule/update"
    }
    
    export function updateBasicSchedule(command: any): JQueryPromise<any> {
        return nts.uk.request.ajax(paths.updateBasicSchedule, command);
    }
}