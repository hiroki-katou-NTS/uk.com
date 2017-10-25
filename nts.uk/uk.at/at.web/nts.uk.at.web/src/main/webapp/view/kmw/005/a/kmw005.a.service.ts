module nts.uk.at.view.kmw005.a {
    export module service {
        var paths = {
            findAllActualLock: "ctx/at/record/workrecord/actuallock/findAll",
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
            
            /**
             * Class ActualLockFinderDto
             */
            export class ActualLockFinderDto {
                closureId: number;
                dailyLockState: number;
                monthlyLockState: number;
                closureName: string;
                startDate: string;
                endDate: string;
            }
            
            /**
             * Class ActualLockFindDto
             */
            
            export class ActualLockFindDto {
                closureId: number;
                dailyLockState: number;
                monthlyLockState: number;
            }
            
        }

    }
}