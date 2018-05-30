module nts.uk.at.view.kmf004.b.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    let paths: any = {
        getAllPerByCode: "shared/specialholiday/getAllPerByCode/{0}",
        getPerByCode: "shared/specialholiday/getPerByCode/{0}/{1}",
        getPerSetByCode: "shared/specialholiday/getPerSetByCode/{0}/{1}",
        addPer: "shared/specialholiday/addPer",
        updatePer: "shared/specialholiday/updatePer",
        removePer: "shared/specialholiday/removePer"
    }
    
    export function getAllPerByCode(specialHolidayCode: string): JQueryPromise<GrantDatePerItem> {
        var path = nts.uk.text.format(paths.getAllPerByCode, specialHolidayCode);
        return nts.uk.request.ajax("at", path);
    }

    export function getPerByCode(specialHolidayCode: string, personalGrantDateCode: string): JQueryPromise<GrantDatePerItem> {
        var path = nts.uk.text.format(paths.getPerByCode, specialHolidayCode, personalGrantDateCode);
        return nts.uk.request.ajax("at", path);
    }
    
    export function getPerSetByCode(specialHolidayCode: string, personalGrantDateCode: string): JQueryPromise<GrantDatePerSetItem> {
        var path = nts.uk.text.format(paths.getPerSetByCode, specialHolidayCode, personalGrantDateCode);
        return nts.uk.request.ajax("at", path);
    }
    
    export function addPer(data: GrantDatePerItem): JQueryPromise<any> {
        var path = nts.uk.text.format(paths.addPer);
        return nts.uk.request.ajax("at", path, data);
    }  
    
    export function UpdatePer(data: GrantDatePerItem): JQueryPromise<any> {
        var path = nts.uk.text.format(paths.updatePer);
        return nts.uk.request.ajax("at", path, data);
    }
    
    export function removePer(specialHolidayCode: string, personalGrantDateCode: string): JQueryPromise<any> {
        var path = nts.uk.text.format(paths.removePer);
        return nts.uk.request.ajax("at", path, {specialHolidayCode: specialHolidayCode, personalGrantDateCode: personalGrantDateCode});
    } 
    
    export interface GrantDatePerItem {
        specialHolidayCode: string;
        personalGrantDateCode: string;
        personalGrantDateName: string;
        provision: number;
        grantDate: string;
        grantDateAtr: number;
        grantDatePerSet: Array<GrantDatePerSetItem>;
    }
    
    export interface GrantDatePerSetItem {
        specialHolidayCode: string;
        personalGrantDateCode: string;
        grantDateNo: number;
        grantDateMonth: number;
        grantDateYear: number;
    }
}