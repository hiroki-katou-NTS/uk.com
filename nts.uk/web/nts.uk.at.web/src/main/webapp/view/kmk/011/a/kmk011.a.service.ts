module kmk011.a.service {
    var paths = {
        getAllDivTimeName: "",
        getAllDivTime: ""
    }

    /**
    * get all divergence time name
    */
    export function getAllDivTimeName(): JQueryPromise<Array<model.DivergenceTimeItem>> {
        var dfd = $.Deferred<Array<model.DivergenceTimeItem>>();
        nts.uk.request.ajax("at", paths.getAllDivTimeName).done(function(res: Array<model.DivergenceTimeItem>) {
            dfd.resolve(res);
        }).fail(function(res) {
            dfd.reject(res);
        })
        return dfd.promise();
    }
        /**
    * get all divergence time
    */
    export function getAllDivTime(): JQueryPromise<Array<model.DivergenceTimeItem>> {
        var dfd = $.Deferred<Array<model.DivergenceTimeItem>>();
        nts.uk.request.ajax("at", paths.getAllDivTimeName).done(function(res: Array<model.DivergenceTimeItem>) {
            dfd.resolve(res);
        }).fail(function(res) {
            dfd.reject(res);
        })
        return dfd.promise();
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