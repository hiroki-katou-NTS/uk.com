module ksm002.c.service {
     var paths = {
         getAllSpecificDate: "at/schedule/shift/businesscalendar/specificdate/getallspecificdate",
         updateSpecificDate: "at/schedule/shift/businesscalendar/specificdate/updatespecificdate"
    }

    /**
    * get all SpecificDate
    */
    export function getAllSpecificDate(): JQueryPromise<Array<any>>{
        return nts.uk.request.ajax("at", paths.getAllSpecificDate);
    }  
    /**
    * update SpecificDate
    */
    export function updateSpecificDate(lstSpecificDateItem: Array<viewmodel.SpecificDateItemCommand>): JQueryPromise<Array<any>>{
        return nts.uk.request.ajax("at", paths.updateSpecificDate,lstSpecificDateItem);
    }
}