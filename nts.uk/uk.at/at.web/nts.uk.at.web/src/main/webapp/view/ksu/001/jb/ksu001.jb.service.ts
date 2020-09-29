module nts.uk.at.view.ksu001.jb.service {
    var paths: any = {
        //registerWorkPairPattern: "at/schedule/shift/team/workpairpattern/register",
        registerWorkPairPattern: "at/schedule/shift/management/registerShijtPalletsByCom",
        //deleteWorkPairPattern: "at/schedule/shift/team/workpairpattern/delete",
        deleteWorkPairPattern: "at/schedule/shift/management/delete",
        getDataComPattern: "at/schedule/shift/management/getListShijtPalletsByCom",
        //getDataWkpPattern: "screen/at/schedule/basicschedule/getDataWkpPattern",
        getDataWkpPattern: "at/schedule/shift/management/shiftpalletorg/getbyWorkplaceId",  
        getDataWkpGrPattern: "at/schedule/shift/management/shiftpalletorg/getbyWorkplaceGrId",  
        //Get data tooltip
        getShiftMasterWorkInfo: "ctx/at/shared/workrule/shiftmaster/getlistByWorkPlace"
    }

    export function registerWorkPairPattern(obj): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.registerWorkPairPattern, obj);
    } 

    export function deleteWorkPairPattern(obj): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.deleteWorkPairPattern, obj);
    }

    export function getDataComPattern(): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getDataComPattern);
    }

    export function getDataWkpPattern(obj): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getDataWkpPattern, obj);
    }

    export function getDataWkpGrPattern(obj): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getDataWkpGrPattern, obj);
    }

    export function getShiftMasterWorkInfo(obj): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getShiftMasterWorkInfo, obj);
    }
    
}