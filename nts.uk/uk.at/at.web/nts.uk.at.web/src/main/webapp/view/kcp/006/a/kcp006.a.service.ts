module nts.uk.at.view.kcp006.a.service {
    var paths: any = {
        getHoliday: "at/schedule/holiday/getHolidayByListDate",
        getCompanyEvent: "at/schedule/eventday/getCompanyEventsByListDate",
        getWorkplaceEvent: "at/schedule/eventday/getWorkplaceEventsByListDate"
    }
    
    export function getPublicHoliday(lstDate): JQueryPromise<Array<model.EventObj>> {
        return nts.uk.request.ajax(paths.getHoliday, lstDate);
    }
    
    export function getCompanyEvent(lstDate): JQueryPromise<Array<model.EventObj>> {
        return nts.uk.request.ajax(paths.getCompanyEvent, lstDate);
    }
    
    export function getWorkplaceEvent(data): JQueryPromise<Array<model.EventObj>> {
        return nts.uk.request.ajax(paths.getWorkplaceEvent, data);
    }
}

module nts.uk.at.view.kcp006.a.model {
    export class EventObj {
        date: number;
        name: string;
    }
}