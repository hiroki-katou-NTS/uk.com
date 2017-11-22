module nts.uk.at.view.kdw001.e.service {
    import shareModel = nts.uk.at.view.kdw001.share.model;
    
    var paths: any = {
        getImplementationResult: "at/record/log/findByEmpCalAndSumExecLogID",
        insertData: "at/record/log/addEmpCalSumAndTarget",
        executeTask: "at/record/log/executeTask"
    }
    export function getImplementationResult(empCalAndSumExecLogID: string): JQueryPromise<any> {
        return nts.uk.request.ajax("at", nts.uk.text.format(paths.getImplementationResult + "/" + empCalAndSumExecLogID));
    }
    
    export function insertData(params: shareModel.executionProcessingCommand): JQueryPromise<shareModel.executionResult> {
        return nts.uk.request.ajax("at", paths.insertData, params);
    }

    export function executeTask(params: shareModel.executionResult): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.executeTask, params);
    }

}