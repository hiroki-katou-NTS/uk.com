module nts.uk.at.view.kmw005.b {
    export module service {
        /**
         * Paths
         */
        var paths = {
            findHistByTargetYM: "ctx/at/record/workrecord/actuallock/findHistByTargetYM",
            findHistByClosure: "ctx/at/record/workrecord/actuallock/findHistByClosure"
        }
        
        /**
         * Find ActualLockHistory by TargetYM
         */
        export function findHistByTargetYM(closureId: number, targetYM: number): JQueryPromise<Array<model.ActualLockHistFindDto>> {
            return nts.uk.request.ajax(paths.findHistByTargetYM + "/" + closureId + "/" + targetYM);
        }
        
        /**
         * Find ActualLockHistory by Closure
         */
        export function findHistByClosure(closureId: number): JQueryPromise<Array<model.ActualLockHistFindDto>> {
            return nts.uk.request.ajax(paths.findHistByClosure + "/" + closureId);
        }

        

        /**
         * Model
         */
        export module model {
            
            /**
             * Class ActualLockHistFindDto
             */
            export class ActualLockHistFindDto {
                closureId: number;
                dailyLockState: number;
                monthlyLockState: number;
                lockDateTime: string;
                targetMonth: number;
                updater: string;
            }
            

            /**
             * class ActualLockHistFind
             */
            export class ActualLockHistFind {
                closureId: number;
                dailyLockState: number;
                monthlyLockState: number;
                lockDateTime: string;
                targetMonth: string;
                updater: string;
                
                constructor() {
                    this.closureId = 0;
                    this.dailyLockState = 0;
                    this.monthlyLockState = 0;
                    this.lockDateTime = "";
                    this.targetMonth = "";
                    this.updater = "";
                }
            }
            
        }

    }
}