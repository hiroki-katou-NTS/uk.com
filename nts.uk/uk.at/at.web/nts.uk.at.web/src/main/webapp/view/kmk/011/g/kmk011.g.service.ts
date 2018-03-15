module nts.uk.at.view.kmk011.g {
    export module service {
        /**
         * define path to service
         */
        var path: any = {
            save: "at/record/divergence/time/history/companyDivergenceRefTime/save",
            findByHistId: "at/record/divergence/time/history/companyDivergenceRefTime/find"
        };
        
        export function save(data: model.CreateHistoryCommand): JQueryPromise<any> {
            return nts.uk.request.ajax("at", path.save, data);
        }
        
        export function findByHistoryId(historyId: string): JQueryPromise<model.CreateHistoryCommand> {
            return nts.uk.request.ajax("at", path.findByHistId + "/" + historyId);
        }
    }
    
    export module model {
        export class CreateHistoryCommand {
            historyId: string;
            startDate: string;
            endDate: string
            
            constructor(historyId: string, startDate: string, endDate: string) {
                this.historyId = historyId;
                this.startDate = startDate;
                this.endDate = endDate;
            }
        }    
    }
}