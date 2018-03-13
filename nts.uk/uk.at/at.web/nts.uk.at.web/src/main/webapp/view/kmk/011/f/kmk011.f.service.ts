module nts.uk.at.view.kmk011.f {
    export module service {
        /**
         * define path to service
         */
        var path: any = {
            save: "at/record/divergence/time/history/companyDivergenceRefTime/save",
            findByHistId: "at/record/divergence/time/history/companyDivergenceRefTime/find"
        };
        
        export function add(): JQueryPromise<any> {
            return nts.uk.request.ajax("at", path.save);
        }
        
        export function findByHistoryId(historyId: string): JQueryPromise<any> {
            return nts.uk.request.ajax("at", path.findByHistId + "/" + historyId);
        }
    }
}