module kmk011.a.service {
    var paths = {
        getAllDivTime: "at/record/divergencetime/getalldivtime",
        updateDivTime: "at/record/divergencetime/updatedivtime",
        updateTimeItemId:"at/record/divergencetime/updateTimeItemId",
        getNameItemSelected: "at/record/divergencetime/getitemname/",
        getAllAttItem:"at/share/attendanceType/getByType/",
        getName: "at/record/divergencetime/getname"
    }
    /**
    * get all item selected(item da duoc chon)
    */
    export function getNameItemSelected(divTimeId: number): JQueryPromise<Array<model.ItemSelected>> {
        return nts.uk.request.ajax("at", paths.getNameItemSelected + divTimeId);
    }
    /**
    * get all attendance item id(id co the chon)
    */
    export function getAllAttItem(divType: number): JQueryPromise<Array<model.AttendanceType>> {
        return nts.uk.request.ajax("at", paths.getAllAttItem + divType);
    }
    /**
    * update time item id (da duoc chon lai)
    */
    export function updateTimeItemId(lstItemId: Array<model.DivergenceTimeItem>):JQueryPromise<Array<model.DivergenceTimeItem>>{
        return nts.uk.request.ajax("at", paths.updateTimeItemId, lstItemId);
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
    export function updateDivTime(Object: model.ObjectDivergence):JQueryPromise<Array<model.DivergenceTimeItem>>{
        return nts.uk.request.ajax("at", paths.updateDivTime, Object);
    }
    export function getName(listAttendanceItemId: Array<number>):JQueryPromise<Array<model.ItemSelected>>{
        return nts.uk.request.ajax("at", paths.getName,listAttendanceItemId);
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
            attendanceId: number;
            constructor(divTimeId: number,attendanceId: number){
                this.divTimeId = divTimeId;
                this.attendanceId = attendanceId;
            }
        }
        export class ItemSelected{
            id: number;
            name: string;
            constructor(id: number,name: string){
                this.id = id;
                this.name = name;
            }
        }
        export class AttendanceType{
            attendanceItemId: number;
        }
        export class DivergenceItem{
            id: number;
            name: string;
            displayNumber: number;
            useAtr: number;
            attendanceAtr: number;
    }
        export class TimeItemSet{
            divTimeId: number;
            attendanceId: number;
            constructor(divTimeId: number,attendanceId: number){
                this.divTimeId = divTimeId;
                this.attendanceId = attendanceId;
            }
        }
        export class ObjectDivergence{
            divTime: DivergenceTime;
            timeItem: List<TimeItemSet>;
            constructor(divTime: DivergenceTime,item: List<TimeItemSet>){
                this.divTime = divTime;
                this.timeItem = item;
            }
        }
}