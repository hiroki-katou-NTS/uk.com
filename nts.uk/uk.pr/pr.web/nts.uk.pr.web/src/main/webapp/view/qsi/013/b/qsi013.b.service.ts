module nts.uk.pr.view.qsi013.b.service {
    import ajax = nts.uk.request.ajax;

    let paths : any = {
        /*getHealthLossInfo: "ctx/pr/shared/lossinfo/healthLossInfo",
        getWelfPenLossInfoById : "ctx/pr/shared/lossinfo/pensionLossInfo",
        getEmpBasicPenNumInforById : "ctx/pr/shared/lossinfo/pensionBasic",
        getMultiEmpWorkInfoById : "ctx/pr/shared/lossinfo/multiEmpWork",
        insertWelfPenInsLossIf: "ctx/pr/shared/lossinfo/insertWelPensionLossInfo",
        insertHealthLossInfo: "ctx/pr/shared/lossinfo/insertHealthLossInf",
        insertMultiEmpWork: "ctx/pr/shared/lossinfo/insertMultiEmpWork",
        insertPensionBasic: "ctx/pr/shared/lossinfo/insertPensionBasic",*/
        registerLossInfo : "ctx/pr/shared/lossinfo/registerLossInfo",
        getLossInfoById : "ctx/pr/shared/lossinfo/getLossInfo/{0}"

    };

    /*export function getHealthLossInfo(empId: any): JQueryPromise<any> {
        return ajax(paths.getHealthLossInfo, empId);
    }
    export function getWelfPenLossInfoById(empId: any): JQueryPromise<any>{
        return ajax(paths.getWelfPenLossInfoById, empId);
    }
    export function getEmpBasicPenNumInforById(empId: any): JQueryPromise<any>{
        return ajax(paths.getEmpBasicPenNumInforById, empId);
    }
    export function getMultiEmpWorkInfoById(empId: any): JQueryPromise<any>{
        return ajax(paths.getMultiEmpWorkInfoById, empId);
    }

    export function insertWelfPenInsLossIf(welfPenInsLossInfoCommand): JQueryPromise<any>{
        return ajax(paths.insertWelfPenInsLossIf, welfPenInsLossInfoCommand);
    }
    export function insertHealthLossInfo(healthLossInfoCommand): JQueryPromise<any>{
        return ajax(paths.insertHealthLossInfo, healthLossInfoCommand);
    }

    export function insertMultiEmpWork(multiEmpWorkCommand): JQueryPromise<any>{
        return ajax(paths.insertMultiEmpWork, multiEmpWorkCommand);
    }

    export function insertPensionBasic(pensionBasicCommand): JQueryPromise<any>{
        return ajax(paths.insertPensionBasic, pensionBasicCommand);
    }*/

    export function registerLossInfo(lossInfoCommand): JQueryPromise<any>{
        return ajax(paths.registerLossInfo, lossInfoCommand);
    }

    export function getLossInfoById(empId: any): JQueryPromise<any>{
        let _path  =  nts.uk.text.format(paths.getLossInfoById, empId);
        return ajax("pr", _path);
    }
}