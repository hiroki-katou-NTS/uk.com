module nts.uk.at.view.kmk012.a {
    export module service {
        var paths = {
            getAllClosureHistory: "ctx/at/record/workrecord/closure/history/getall",
            detailClosure: "ctx/at/record/workrecord/closure/detail",
            detailClosureHistory: "ctx/at/record/workrecord/closure/history/detail"
        }
        
        // get all data        
        export function getAllClosureHistory(): JQueryPromise<model.ClosureHistoryFindDto[]> {
            return nts.uk.request.ajax(paths.getAllClosureHistory);
        }

        // detail data
        export function detailClosure(closureId: number):JQueryPromise<model.ClosureDto> {
            return nts.uk.request.ajax(paths.detailClosure + "/" + closureId);
        }
        
        
        // detail data
        export function detailClosureHistory(master: model.ClosureHistoryMDto):JQueryPromise<model.ClosureHistoryDDto> {
            return nts.uk.request.ajax(paths.detailClosureHistory,master);
        }
        
        

        export module model {
            
            export class ClosureHistoryMDto {

                /** The history id. */
                historyId: string;

                /** The closure id. */
                closureId: number;

                /** The end date. */
                // 終了年月: 年月
                endDate: number;

                /** The start date. */
                // 開始年月: 年月
                startDate: number;

                view: string;
                
                updateData(): void {
                    var startMonthRage: string = nts.uk.time.formatYearMonth(this.startDate);
                    var endMonthRage: string = nts.uk.time.formatYearMonth(this.endDate);
                    this.view = startMonthRage + ' ~ ' + endMonthRage;
                }
            }
            
            export class ClosureHistoryDDto {

                /** The history id. */
                historyId: string;

                /** The closure id. */
                closureId: number;

                /** The end date. */
                // 終了年月: 年月
                closureName: string;

                /** The start date. */
                // 開始年月: 年月
                closureDate: number;
                
            }
            
            export class ClosureHistoryFindDto{
                /** The id. */
                id: number;

                /** The name. */
                name: string;   
                
                // the view
                view: string;
                
                updateData(): void {
                    this.view = this.id+": "+this.name;    
                }
            }
            
            export enum UseClassification{
                    
            }
            
            export class DayofMonth{
                day: number;
                name: string;
            }
            
            export class ClosureDto {
                /** The closure id. */
                closureId: number;

                /** The use classification. */
                useClassification: number;

                /** The day. */
                month: number;
                
                closureHistories: ClosureHistoryMDto[];
            }
        }

    }
}