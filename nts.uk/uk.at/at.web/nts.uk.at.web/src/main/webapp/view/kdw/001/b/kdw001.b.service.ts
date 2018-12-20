module nts.uk.at.view.kdw001.b {
    export module service {
        var path: any = {
                findMonthlyResult: "at/shared/workclosuredate/findbyclosureid"
            };
        
        export function findMonthlyResult(closureId: number, yearMonth: string): JQueryPromise<any> {
            return nts.uk.request.ajax("at", path.findMonthlyResult + "/" + closureId);
        }
    }
}
