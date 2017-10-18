module nts.uk.at.view.kmw005.a {
    export module service {
        var paths = {
            findAllActualLock: "ctx/at/record/workrecord/actuallock/findAll",
            findHistByTargetYM: "ctx/at/record/workrecord/actuallock/findHistByTargetYM",
            findLockByClosureId: "ctx/at/record/workrecord/actuallock/findLockByClosureId",
            
            saveActualLock: "ctx/at/record/workrecord/actuallock/saveLock",
            saveActualLockHist: "ctx/at/record/workrecord/actuallock/saveLockHist"
        }
        
        /**
         * find all data closure history call service
         */
        export function findAllActualLock(): JQueryPromise<model.ActualLockFinderDto[]> {
            return nts.uk.request.ajax(paths.findAllActualLock);
        }
        
        /**
         * Find ActualLockHistory by TargetYM (B scr?)
         */
        export function findHistByTargetYM(closureId: number, targetYM: number): JQueryPromise<Array<model.ActualLockHistFindDto>> {
            return nts.uk.request.ajax(paths.findHistByTargetYM + "/" + closureId + "/" + targetYM);
        }
        
        /**
         * Find ActualLock by ClosureId
         */
        export function findLockByClosureId(closureId: number) : JQueryPromise<model.ActualLockFindDto> {
            return nts.uk.request.ajax(paths.findLockByClosureId + "/" + closureId);
        }
        
        /**
         * Save ActualLock
         */
        export function saveActualLock(command: any): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.saveActualLock, command);
        }
        
        /**
         * Save ActualLockHist
         */
        export function saveActualLockHist(command: any): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.saveActualLockHist, command);
        }
//
//        /**
//         * save closure history call service
//         */
//        export function saveClosureHistory(dto: model.ClosureHistoryDto): JQueryPromise<void> {
//            var data = { closureHistory: dto };
//            return nts.uk.request.ajax(paths.saveClosureHistory, data);
//        }

        

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
            
            export class ActualLockFinderDto {
                closureId: number;
                dailyLockState: number;
                monthlyLockState: number;
                closureName: string;
                startDate: string;
                endDate: string;
            }
            
            export class ActualLockHistFindDto {
                closureId: number;
                dailyLockState: number;
                monthlyLockState: number;
                lockDateTime: string;
                targetMonth: number;
                updater: string;
            }
            
            export class ActualLockFindDto {
                closureId: number;
                dailyLockState: number;
                monthlyLockState: number;
            }
            
        }

    }
}