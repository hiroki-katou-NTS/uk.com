module nts.uk.at.view.kmf004.c.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    let paths: any = {
        getPerByCode: "shared/specialholiday/getPerByCode/{0}/{1}",
        getPerSetByCode: "shared/specialholiday/getPerSetByCode/{0}/{1}",
        addPer: "shared/specialholiday/addPer",
        UpdatePer: "shared/specialholiday/UpdatePer"
    }

    export function getPerByCode(specialHolidayCode: string, personalGrantDateCode: string): JQueryPromise<GrantDatePerItem> {
        var path = nts.uk.text.format(paths.getPerByCode, specialHolidayCode, personalGrantDateCode);
        return nts.uk.request.ajax("at", path);
    }
    
    export function getPerSetByCode(specialHolidayCode: string, personalGrantDateCode: string): JQueryPromise<GrantDatePerSetItem> {
        var path = nts.uk.text.format(paths.getPerSetByCode, specialHolidayCode, personalGrantDateCode);
        return nts.uk.request.ajax("at", path);
    }
    
    export function addPer(data: Array<GrantDatePerItem>): JQueryPromise<any> {
        var path = nts.uk.text.format(paths.addPer);
        return nts.uk.request.ajax("at", path, data);
    }  
    
    export function UpdatePer(data: Array<GrantDatePerItem>): JQueryPromise<any> {
        var path = nts.uk.text.format(paths.UpdatePer);
        return nts.uk.request.ajax("at", path, data);
    }
    
    export interface GrantDatePerItem {
        specialHolidayCode: string;
        personalGrantDateCode: string;
        personalGrantDateName: string;
        grantDate: number;
        grantDateAtr: number;
        grantDateSets: Array<GrantDatePerSetItem>;
    }
    
    export interface GrantDatePerSetItem {
        specialHolidayCode: string;
        personalGrantDateCode: string;
        grantDateNo: number;
        grantDateMonth: number;
        grantDateYear: number;
    }
}