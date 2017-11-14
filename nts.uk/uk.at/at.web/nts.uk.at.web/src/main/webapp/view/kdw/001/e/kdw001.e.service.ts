module nts.uk.at.view.kdw001.e.service {
    import shareModel = nts.uk.at.view.kdw001.share.model;
    
    var paths: any = {
        getImplementationResult: "at/record/log/findByEmpCalAndSumExecLogID",
        executeTask: "at/record/log/executeTask"
    }
    export function getImplementationResult(empCalAndSumExecLogID: string): JQueryPromise<any> {
        return nts.uk.request.ajax("at", nts.uk.text.format(paths.getImplementationResult + "/" + empCalAndSumExecLogID));
    }

    export function executeTask(params: shareModel.executionProcessingCommand): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.executeTask, params);
    }

}