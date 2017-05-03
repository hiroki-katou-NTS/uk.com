module kmk011.a.service {
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
    }
}