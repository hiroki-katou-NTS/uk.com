module nts.uk.at.view.kmf004.b.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    /**
     *  Service paths
     */
    var servicePath = {
        getComByCode: "shared/specialholiday/getComByCode/{0}",
        getAllSetByCode: "shared/specialholiday/getAllSetByCode/{0}",
        addGrantDateCom: "shared/specialholiday/addGrantDateCom"
    }  
    
    export function getComByCode(specialHolidayCode: string): JQueryPromise<ComItem> {
        var path = nts.uk.text.format(servicePath.getComByCode, specialHolidayCode);
        return nts.uk.request.ajax("at", path);
    }
    
    export function getAllSetByCode(specialHolidayCode: string): JQueryPromise<SetItem> {
        var path = nts.uk.text.format(servicePath.getAllSetByCode, specialHolidayCode);
        return nts.uk.request.ajax("at", path);
    }
    
    export function addGrantDateCom(data: ComItem): JQueryPromise<any> {
        var path = nts.uk.text.format(servicePath.addGrantDateCom);
        return nts.uk.request.ajax("at", path, data);
    }  
    
    export interface ComItem {
        specialHolidayCode: string;
        grantDateAtr: number;
        grantDate: string;
        grantDateSets: Array<SetItem>;
    }
    
    export interface SetItem {
        specialHolidayCode: string;
        grantDateType: number;
        grantDateMonth: number;
        grantDateYear: number;
    }
}