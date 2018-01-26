module nts.uk.at.view.kcp006.b.service {
    var paths: any = {
        getCompanyEvent: "at/schedule/eventday/getCompanyEventsByListDate",
        getWorkplaceEvent: "at/schedule/eventday/getWorkplaceEventsByListDate",
        addCompanyEvent: "at/schedule/eventday/addCompanyEvent",
        removeCompanyEvent: "at/schedule/eventday/removeCompanyEvent",
        addWorkplaceEvent: "at/schedule/eventday/addWorkplaceEvent",
        removeWorkplaceEvent: "at/schedule/eventday/removeWorkplaceEvent"
    }
    
    export function getCompanyEvent(lstDate): JQueryPromise<Array<model.EventObj>> {
        return nts.uk.request.ajax(paths.getCompanyEvent, lstDate.map((date) => { return moment(date, "YYYYMMDD").utc().toISOString(); }));
    }

    export function getWorkplaceEvent(data): JQueryPromise<Array<model.EventObj>> {
        data.lstDate = data.lstDate.map((date) => { return moment(date, "YYYYMMDD").utc().toISOString(); });
        return nts.uk.request.ajax(paths.getWorkplaceEvent, data);
    }
    
    export function addCompanyEvent(event): JQueryPromise<Array<model.EventObj>> {
        return nts.uk.request.ajax(paths.addCompanyEvent, event);
    }

    export function removeCompanyEvent(event): JQueryPromise<Array<model.EventObj>> {
        return nts.uk.request.ajax(paths.removeCompanyEvent, event);
    }
    
    export function addWorkplaceEvent(event): JQueryPromise<Array<model.EventObj>> {
        return nts.uk.request.ajax(paths.addWorkplaceEvent, event);
    }

    export function removeWorkplaceEvent(event): JQueryPromise<Array<model.EventObj>> {
        return nts.uk.request.ajax(paths.removeWorkplaceEvent, event);
    }
}

module nts.uk.at.view.kcp006.b.model {
    export class EventObj {
        date: number;
        name: string;
    }
}