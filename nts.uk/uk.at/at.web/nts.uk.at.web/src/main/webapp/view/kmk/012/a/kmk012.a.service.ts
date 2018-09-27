module nts.uk.at.view.kmk012.a {
    export module service {
        var paths = {
            findAllClosureHistory: "ctx/at/shared/workrule/closure/history/findAll",
            findByIdClosure: "ctx/at/shared/workrule/closure/findById",
            saveClosure: "ctx/at/shared/workrule/closure/save",
            findByIdClosureHistory: "ctx/at/shared/workrule/closure/history/findById",
            saveClosureHistory: "ctx/at/shared/workrule/closure/history/save"
        }
        
        /**
         * find all data closure history call service
         */
        export function findAllClosureHistory(): JQueryPromise<model.ClosureHistoryFindDto[]> {
            return nts.uk.request.ajax(paths.findAllClosureHistory);
        }

        /**
         * find by id data closure call service
         */
        export function findByIdClosure(closureId: number): JQueryPromise<model.ClosureDto> {
            return nts.uk.request.ajax(paths.findByIdClosure + "/" + closureId);
        }

        /**
         * save closure call service
         */
        export function saveClosure(dto: model.ClosureSaveDto): JQueryPromise<void> {
            return nts.uk.request.ajax(paths.saveClosure, dto);
        }


        /**
         * find by id data closure history call service
         */
        export function findByIdClosureHistory(master: model.ClosureHistoryMasterDto)
            : JQueryPromise<model.ClosureHistoryHeaderDto> {
            return nts.uk.request.ajax(paths.findByIdClosureHistory, master);
        }

        /**
         * save closure history call service
         */
        export function saveClosureHistory(dto: model.ClosureHistoryDto): JQueryPromise<void> {
            var data = { closureHistory: dto };
            return nts.uk.request.ajax(paths.saveClosureHistory, data);
        }

        

        export module model {
            
            export class ClosureHistoryMasterDto {
                
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
            
            export class ClosureHistoryHeaderDto {

                /** The closure id. */
                closureId: number;

                /** The end date. */
                // 終了年月: 年月
                closureName: string;

                /** The start date. */
                // 開始年月: 年月
                closureDate: number;
                
                
                startDate: number;
            }
            
            export class ClosureHistoryFindDto{
                /** The id. */
                id: number;

                /** The name. */
                name: string;   
                
                // the view
                view: string;
                
                updateData(): void {
                    this.view = this.id+": "+_.escape(this.name);    
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
                
                // selected
                closureSelected: ClosureHistoryMasterDto;
                
                // data history
                closureHistories: ClosureHistoryMasterDto[];
            }
            
            export class ClosureSaveDto{
                /** The closure id. */
                closureId: number;

                /** The use classification. */
                useClassification: number;

                /** The day. */
                month: number;
            }
            
            export class ClosureHistoryDto{
            
                /** The close name. */
                // 名称: 締め名称
                closeName: string;

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