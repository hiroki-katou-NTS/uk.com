module nts.uk.at.view.kmk012.d {
    export module service {
        var paths = {
            detailClosureHistory: "ctx/at/record/workrecord/closure/detailhistory",
            getDayMonth: "ctx/at/record/workrecord/closure/getday",
            getDayMonthChange: "ctx/at/record/workrecord/closure/getdaychange",
            addClosureHistory: "ctx/at/record/workrecord/closure/history/add"
        }
        
        // detail data
        export function detailClosureHistory(dto: model.ClosureHistoryInDto):JQueryPromise<model.ClosureDetailDto> {
            return nts.uk.request.ajax(paths.detailClosureHistory,dto);
        }
        
        // detail data
        export function getDayMonth(dto: model.DayMonthInDto):JQueryPromise<model.DayMonthDto> {
            return nts.uk.request.ajax(paths.getDayMonth,dto);
        }
        
        // detail data
        export function getDayMonthChange(dto: model.DayMonthChangeInDto):JQueryPromise<model.DayMonthChangeDto> {
            return nts.uk.request.ajax(paths.getDayMonthChange,dto);
        }
       
            // save data
        export function addClosureHistory(dto: model.ClosureHistoryAddDto): JQueryPromise<void> {
            var data = { closureHistoryAdd: dto };
            return nts.uk.request.ajax(paths.addClosureHistory, data);
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
               

            export class DayMonthDto {

                /** The begin day. */
                beginDay: string;

                /** The end day. */
                endDay: string;
            }
            
            export class DayMonthInDto {
                
                /** The month. */
                month: number;

                /** The closure date. */
                closureDate: number;
            }
            
            export class DayMonthChangeDto {

                /** The before closure date. */
                beforeClosureDate: DayMonthDto;

                /** The after closure date. */
                afterClosureDate: DayMonthDto;
            }
            
            export class DayMonthChangeInDto {

                /** The month. */
                month: number;


                /** The closure date. */
                closureDate: number;


                /** The change closure date. */
                changeClosureDate: number;
            }
            
            
            export class ClosureHistoryAddDto{
                
                /** The closure id. */
                // 締めＩＤ
                closureId: number;

                /** The closure year. */
                // 終了年月: 年月
                endDate: number;

                /** The closure date. */
                // 締め日: 日付
                closureDate: number;

                /** The start date. */
                // 開始年月: 年月
                startDate: number;
            }
        }

    }
}