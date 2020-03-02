module nts.uk.at.view.ksu001.jc.service {
    var paths: any = {
        
        getShiftMasterWorkInfo: "ctx/at/shared/workrule/shiftmaster/getlistByWorkPlace"
    }

    export function getShiftMasterWorkInfo(workplaceId: string , targetUnit : number): JQueryPromise<any> {
      var data = {
          workplaceId : workplaceId,
          targetUnit : targetUnit
          }
             
        return nts.uk.request.ajax("at", paths.getShiftMasterWorkInfo, data);
    }

}