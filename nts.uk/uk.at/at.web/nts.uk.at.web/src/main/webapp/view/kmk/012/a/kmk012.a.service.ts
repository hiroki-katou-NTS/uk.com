module nts.uk.at.view.kmk012.a {
    export module service {
        var paths = {
            getAllClosureHistory: "ctx/at/record/workrecord/closure/history/getall"
        }
        
        
        export function getAllClosureHistory(): JQueryPromise<model.ClosureHistoryFindDto[]> {
            return nts.uk.request.ajax("at", paths.getAllClosureHistory);
        }


        export module model {
            
            export class ClosureHistoryFindDto{
                /** The id. */
                id: number;

                /** The name. */
                name: string;    
            }
        }

    }
}