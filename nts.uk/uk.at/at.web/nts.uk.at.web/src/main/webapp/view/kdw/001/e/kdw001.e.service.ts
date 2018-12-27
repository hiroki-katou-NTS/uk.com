module nts.uk.at.view.kdw001.e.service {
    import shareModel = nts.uk.at.view.kdw001.share.model;
    
    var paths: any = {
        getErrorMessageInfo: "at/record/log/getErrorMessageInfo",
        addEmpCalSumAndTarget: "at/record/log/addEmpCalSumAndTarget",
        checkprocess: "at/record/log/checkprocess",
        updateExcutionTime : "at/record/log/updateExcutionTime",
        updateLogState : "at/record/log/updateLogState"
    }
    
    export function getErrorMessageInfo(params: any): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getErrorMessageInfo, params);
    }
    
    export function insertData(params: shareModel.executionProcessingCommand): JQueryPromise<shareModel.AddEmpCalSumAndTargetCommandResult> {
        return nts.uk.request.ajax("at", paths.addEmpCalSumAndTarget, params);
    }

    export function checkTask(params: shareModel.CheckProcessCommand): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.checkprocess, params);
    }
    
    export function saveAsCsv(data:any): JQueryPromise<any> {
        return nts.uk.request.exportFile('/masterlist/report/print', { domainId: "personError", domainType: "personerror", languageId: 'ja', reportType: 3 ,data:data});
    }
    
    export function updateExcutionTime(params: any): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.updateExcutionTime, params);
    }
    
    export function updateLogState(params: any): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.updateLogState, params);
    }

}