module nts.uk.at.view.kmk012.d {
    export module service {
        var paths = {
            findByMasterClosureHistory: "ctx/at/shared/workrule/closure/findByMaster",
            getDayMonth: "ctx/at/shared/workrule/closure/getday",
            getDayMonthChange: "ctx/at/shared/workrule/closure/getdaychange",
            addClosureHistory: "ctx/at/shared/workrule/closure/history/add"
        }
        /**
         * find by master data closure history call service
         */
        export function findByMasterClosureHistory(dto: model.ClosureHistoryInDto):JQueryPromise<model.ClosureDetailDto> {
            return nts.uk.request.ajax(paths.findByMasterClosureHistory,dto);
        }
        
        /**
         * get date month by select call service
         */
        export function getDayMonth(dto: model.DayMonthInDto):JQueryPromise<model.DayMonthDto> {
            return nts.uk.request.ajax(paths.getDayMonth,dto);
        }
        
        /**
         * get date month change select call service
         */
        export function getDayMonthChange(dto: model.DayMonthChangeInDto):JQueryPromise<model.DayMonthChangeDto> {
            return nts.uk.request.ajax(paths.getDayMonthChange,dto);
        }
       
        /**
         * add closure history call service
         */
        export function addClosureHistory(dto: model.ClosureHistoryAddDto): JQueryPromise<void> {
            var data = { closureHistoryAdd: dto };
            return nts.uk.request.ajax(paths.addClosureHistory, data);
        }
        
        export module model {

            export class ClosureDetailDto {
    
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
                startDate: number;

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
                
                closureName: string;
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