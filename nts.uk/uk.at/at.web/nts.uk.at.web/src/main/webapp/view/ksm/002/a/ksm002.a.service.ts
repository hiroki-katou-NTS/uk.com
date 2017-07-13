module ksm002.a.service {
    var paths = {
        //getSpecDateByCompany: "at/schedule/shift/businesscalendar/specificdate/getallspecificdate",
        getSpecDateByIsUse: "at/schedule/shift/businesscalendar/specificdate/getspecificdatebyuse",
        getCompanyStartDay: "at/schedule/shift/businesscalendar/businesscalendar/getcompanystartday",
        getComSpecDateByCompanyDate: "at/schedule/shift/specificdayset/company/getcompanyspecificdaysetbydate"
    }
    /**
     * 
     */
    export function getSpecificDateByIsUse(useAtr:number): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getSpecDateByIsUse +"/" + useAtr);
    }
    /**
     *get companySpecificDate 
     */
    export function getCompanySpecificDateByCompanyDate(processDate:number): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getComSpecDateByCompanyDate + "/" + processDate);
    }
    /**
     * 
     */
    export function getCompanyStartDay(): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getCompanyStartDay);
    }
}