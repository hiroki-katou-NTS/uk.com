module nts.uk.at.view.ksu001.q.service {
     var paths: any = {
        getDataComPattern: "at/schedule/shift/management/getListShijtPalletsByCom",
        //getDataWkpPattern: "screen/at/schedule/basicschedule/getDataWkpPattern",
        getDataWkpPattern: "at/schedule/shift/management/shiftpalletorg/getbyWorkplaceId",  
    }


    export function getDataComPattern(): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getDataComPattern);
    }

    export function getDataWkpPattern(obj): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getDataWkpPattern, obj);
    }

}