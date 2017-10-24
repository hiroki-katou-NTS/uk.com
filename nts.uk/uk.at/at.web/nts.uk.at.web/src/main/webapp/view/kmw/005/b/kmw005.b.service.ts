module nts.uk.at.view.kmw005.b {
    export module service {
        var paths = {
            findHistByTargetYM: "ctx/at/record/workrecord/actuallock/findHistByTargetYM",
            findHistByClosure: "ctx/at/record/workrecord/actuallock/findHistByClosure"
        }
        
        /**
         * find all data closure history call service
         */
        /**
         * Find ActualLockHistory by TargetYM
         */
        export function findHistByTargetYM(closureId: number, targetYM: number): JQueryPromise<Array<model.ActualLockHistFindDto>> {
            return nts.uk.request.ajax(paths.findHistByTargetYM + "/" + closureId + "/" + targetYM);
        }
        
        /**
         * Find ActualLockHistory by Closure
         */
        export function findHistByClosure(closureId: number, targetYM: number): JQueryPromise<Array<model.ActualLockHistFindDto>> {
            return nts.uk.request.ajax(paths.findHistByClosure + "/" + closureId + "/" + targetYM);
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

//                public toActualLockHistFind(): ActualLockHistFind {
//                    return new ActualLockHistFind(this.closureId, this.dailyLockState, 
//                    this.monthlyLockState, this.lockDateTime, this.targetMonth, this.updater);
//                }
            }
            

            export class ActualLockHistFind {
                closureId: number;
                dailyLockState: number;
                monthlyLockState: number;
                lockDateTime: string;
                targetMonth: string;
                updater: string;
                
//                constructor(closureId: number, dailyLockState: number, monthlyLockState: number, 
//                lockDateTime: string, targetMonth: number, updater: string) {
//                    this.closureId = closureId;
//                    this.dailyLockState = dailyLockState;
//                    this.monthlyLockState = monthlyLockState;
//                    this.lockDateTime = lockDateTime;
//                    this.targetMonth = "";
//                    this.updater = updater;
//                }
                
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