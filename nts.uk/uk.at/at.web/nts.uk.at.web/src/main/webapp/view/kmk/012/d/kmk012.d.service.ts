module nts.uk.at.view.kmk012.d {
    export module service {
        var paths = {
            detailClosureHistory: "ctx/at/record/workrecord/closure/detailhistory"
        }
        
        // detail data
        export function detailClosureHistory(dto: model.ClosureHistoryInDto):JQueryPromise<model.ClosureDetailDto> {
            return nts.uk.request.ajax(paths.detailClosureHistory,dto);
        }
        
        export module model {

            export class ClosureDetailDto {

                /** The history id. */
                historyId: string;
    
                /** The closure id. */
                closureId: number;
    
                /** The closure name. */
                closureName: string;
    
                /** The closure date. */
                closureDate: number;
    
                /** The use classification. */
                useClassification: number;

                /** The end date. */
                // 終了年月: 年月
                endDate: number;

                /** The start date. */
                // 開始年月: 年月
                startDate: number;
    
                /** The month. */
                month: number;
            }
            
            export class ClosureHistoryInDto{
                
                /** The history id. */
                historyId: string;

                /** The closure id. */
                // 締めＩＤ
                closureId: number;    
            }
        }

    }
}