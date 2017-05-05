module kmk011.a.service {
    var paths = {
        getAllDivTimeName: "at/record/divergencetime/?",
        getAllDivTime: "at/record/divergencetime/getalldivtime",
        updateDivTime: "at/record/divergencetime/updatedivtime"
    }

    /**
    * get all divergence time name
    */
    export function getAllDivTimeName(): JQueryPromise<Array<model.DivergenceTimeItem>> {
        return nts.uk.request.ajax("at", paths.getAllDivTimeName);
    }
    /**
    * get all divergence time
    */
    export function getAllDivTime(): JQueryPromise<Array<model.DivergenceTime>>{
        return nts.uk.request.ajax("at", paths.getAllDivTime);
    }
    /**
     * update divergence time
     */
    export function updateDivTime(divTime: model.DivergenceTime):JQueryPromise<Array<model.DivergenceTimeItem>>{
        return nts.uk.request.ajax("at", paths.updateDivTime, divTime);
    }
    export module model {
        export class DivergenceTime {
            divTimeId: number;
            divTimeUseSet: number;
            alarmTime: number;
            errTime: number;
            selUseSet: number;
            cancelErrSelReason: number;
            inpUseSet: number;
            cancelErrInpReason: number;
            constructor(divTimeId: number,divTimeUseSet: number,
                        alarmTime: number,errTime: number,
                        selUseSet: number,cancelErrSelReason: number,
                        inpUseSet: number,cancelErrInpReason: number){
                var self = this;
                self.divTimeId = divTimeId;
                self.divTimeUseSet = divTimeUseSet;
                self.alarmTime = alarmTime;
                self.errTime = errTime;
                self.selUseSet = selUseSet;
                self.cancelErrSelReason = cancelErrSelReason;
                self.inpUseSet = inpUseSet;
                self.cancelErrInpReason = cancelErrInpReason;
            }
        }
        export class DivergenceTimeItem{
            divTimeId: number;
            divTimeName: string;
            constructor(divTimeId: number,divTimeName: string){
                this.divTimeId = divTimeId;
                this.divTimeName = divTimeName;
            }
        }
        
    }
}