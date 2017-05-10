module kmk011.a.service {
    var paths = {
        getAllDivTime: "at/record/divergencetime/getalldivtime",
        getDivItemIdSel: "at/record/divergencetime/getItemSet/",
        getAllDivItemId: "at/record/divergencetime/getalldivitemid",
        updateDivTime: "at/record/divergencetime/updatedivtime"
    }

    /**
    * get all divergence item id selected(id da duoc chon)
    */
    export function getDivItemIdSelected(divTimeId: string): JQueryPromise<Array<model.DivergenceTimeItem>> {
        return nts.uk.request.ajax("at", paths.getDivItemIdSel + divTimeId);
    }
    /**
    * get all divergence item id(id co the chon)
    */
    export function getAllDivItemId(divTimeId: string): JQueryPromise<Array<model.DivergenceTimeItem>> {
        return nts.uk.request.ajax("at", paths.getAllDivItemId + divTimeId);
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
            divTimeName: string;
            alarmTime: number;
            errTime: number;
            selectSet: SelectSet;
            inputSet: SelectSet;
            constructor(divTimeId: number,divTimeName: string,
                        divTimeUseSet: number,
                        alarmTime: number,errTime: number,
                        selectSet: SelectSet,
                        inputSet: SelectSet){
                var self = this;
                self.divTimeId = divTimeId;
                self.divTimeName = divTimeName;
                self.divTimeUseSet = divTimeUseSet;
                self.alarmTime = alarmTime;
                self.errTime = errTime;
                self.selectSet = selectSet;
                self.inputSet = inputSet;
            }
        }
        export class SelectSet{
            selectUseSet: number;
            cancelErrSelReason: number;
            constructor(selectUseSet: number,cancelErrSelReason: number){
                this.selectUseSet = selectUseSet;
                this.cancelErrSelReason = cancelErrSelReason;
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