module nts.uk.at.view.ksu001.common.modelgrid {
    import formatById = nts.uk.time.format.byId;
    
    export interface IExCell {
        workTypeCode: string,
        workTypeName: string,
        workTimeCode: string,
        workTimeName: string,
        symbolName?: string,
        startTime?: any,
        endTime?: any
    }

   export class ExCell {
        workTypeCode: string;
        workTypeName: string;
        workTimeCode: string;
        workTimeName: string;
        symbol: string;
        startTime: any;
        endTime: any;
        constructor(workTypeCode: string, workTypeName: string, workTimeCode: string, workTimeName: string, startTime?: string, endTime?: string, symbol?: any) {
            this.workTypeCode = workTypeCode;
            this.workTypeName = workTypeName;
            this.workTimeCode = workTimeCode;
            this.workTimeName = workTimeName;
            this.symbol = symbol !== null ? (parseInt(workTypeCode) % 3 === 0 ? "通" : "◯") : null;
            this.startTime = startTime !== undefined ? startTime : "8:30";
            this.endTime = endTime !== undefined ? endTime : "17:30";
        }
    }
    

    export interface IWorkType {
        workTypeCode: string,
        sortOrder: number,
        symbolicName: string,
        name: string,
        abbreviationName: string,
        memo: string,
        displayAtr: number
    }

    export class WorkType {
        workTypeCode: string;
        sortOrder: number;
        symbolicName: string;
        name: string;
        abbreviationName: string;
        memo: string;
        displayAtr: number;

        constructor(params: IWorkType) {
            this.workTypeCode = params.workTypeCode;
            this.sortOrder = params.sortOrder;
            this.symbolicName = params.symbolicName;
            this.name = params.name;
            this.abbreviationName = params.abbreviationName;
            this.memo = nts.uk.text.isNullOrEmpty(params.memo) ? "" : params.memo;
            this.displayAtr = params.displayAtr;
        }
    }
    
    
    export class TimeRange {
        columnKey: any;
        rowId: any;
        innerIdx: any;
        max: string;
        min: string;
        constructor(columnKey: any, rowId: any, max: string, min: string, innerIdx?: any) {
            this.columnKey = columnKey;
            this.rowId = rowId;
            this.innerIdx = innerIdx;
            this.max = max;
            this.min = min;
        }
    }

    export interface IWorkTime {
        workTimeCode: string,
        name: string,
        abName: string,
        symbolName: string,
        dailyWorkAtr: number,
        worktimeSetMethod: number,
        abolitionAtr: number,
        color: string,
        note: string,
        startTime: number,
        endTime: number,
        workNo: number,
        useAtr: number
    }

    export class WorkTime {
        workTimeCode: string;
        name: string;
        abName: string;
        symbolName: string;
        dailyWorkAtr: number;
        worktimeSetMethod: number;
        abolitionAtr: number;
        color: string;
        note: string;
        codeName: string;
        startTime: number;
        endTime: number;
        workNo: number;
        useAtr: number;
        timeZone1: string;
        timeZone2: string;

        constructor(params: IWorkTime) {
            this.workTimeCode = params.workTimeCode;
            this.name = params.name;
            this.abName = params.abName;
            this.symbolName = params.symbolName || '';
            this.dailyWorkAtr = params.dailyWorkAtr;
            this.worktimeSetMethod = params.worktimeSetMethod;
            this.abolitionAtr = params.abolitionAtr;
            this.note = params.note || '';
            this.codeName = this.workTimeCode + this.name;
            this.startTime = params.startTime;
            this.endTime = params.endTime;
            this.workNo = params.workNo;
            this.useAtr = params.useAtr;
            this.timeZone1 = this.workNo == 1 ? formatById("Clock_Short_HM", this.startTime)
                + nts.uk.resource.getText("KSU001_66") + formatById("Clock_Short_HM", this.endTime) : '';
            this.timeZone2 = this.useAtr == 1 ? (this.workNo == 2 ? formatById("Clock_Short_HM", this.startTime)
                + nts.uk.resource.getText("KSU001_66") + formatById("Clock_Short_HM", this.endTime) : '') : '';
        }
    }

}