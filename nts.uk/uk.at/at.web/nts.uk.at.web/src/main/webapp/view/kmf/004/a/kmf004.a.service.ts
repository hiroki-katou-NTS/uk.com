module nts.uk.at.view.kmf004.a.service {
    var paths: any = {
        findByCid: "shared/specialholidaynew/findByCid",
        getSpecialHoliday: "shared/specialholidaynew/getSpecialHoliday/{0}",
        add: "shared/specialholidaynew/add",
        update: "shared/specialholidaynew/update",
        remove: "shared/specialholidaynew/delete"
    }
    
    export function findByCid(): JQueryPromise<any> {
        var path = nts.uk.text.format(paths.findByCid);
        return nts.uk.request.ajax("at", path);
    }

    export function getSpecialHoliday(specialHolidayCode: number): JQueryPromise<any> {
        var path = nts.uk.text.format(paths.getSpecialHoliday, specialHolidayCode);
        return nts.uk.request.ajax("at", path);
    }
    
    export function add(data: SpecialHolidayItem): JQueryPromise<any> {
        var path = nts.uk.text.format(paths.add);
        return nts.uk.request.ajax("at", path, data);
    }
    
    export function update(data: SpecialHolidayItem): JQueryPromise<any> {
        var path = nts.uk.text.format(paths.update);
        return nts.uk.request.ajax("at", path, data);
    }
    
    export function remove(specialHolidayCode: number): JQueryPromise<any> {
        var path = nts.uk.text.format(paths.remove);
        return nts.uk.request.ajax("at", path, { specialHolidayCode: specialHolidayCode });
    }
    
    export interface SpecialHolidayItem {
        companyId: string;
        specialHolidayCode: number;
        specialHolidayName: string;
        memo: string;
    }
}