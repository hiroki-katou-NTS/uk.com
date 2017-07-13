module ksm002.c.service {
     var paths = {
         getAllSpecificDate: "at/schedule/specificdateitem/getallspecificdate",
         updateSpecificDate: "at/schedule/specificdateitem/updatespecificdate"
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