module nts.uk.at.view.kdw001.e.service {
    var paths: any = {
        getImplementationResult: "at/record/log/findByEmpCalAndSumExecLogID",
        asyncTask: "at/record/log/asyncTask"
    }
    export function getImplementationResult(empCalAndSumExecLogID: string): JQueryPromise<any> {
        return nts.uk.request.ajax("at", nts.uk.text.format(paths.getImplementationResult + "/" + empCalAndSumExecLogID));
    }

    export function asyncTask(): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.asyncTask);
    }

}