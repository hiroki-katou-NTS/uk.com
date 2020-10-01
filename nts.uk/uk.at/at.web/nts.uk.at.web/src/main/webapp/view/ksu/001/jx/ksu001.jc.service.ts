module nts.uk.at.view.ksu001.jc.service {
    var paths: any = {
        getShiftMasterWorkInfo: "ctx/at/shared/workrule/shiftmaster/getlistByWorkPlace"
    }

    export function getShiftMasterWorkInfo(obj): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getShiftMasterWorkInfo, obj);
    }

}