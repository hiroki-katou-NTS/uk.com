module ksu001.common.viewmodel {
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
            this.memo = params.memo;
            this.displayAtr = params.displayAtr;
        }
    }

    export interface IWorkTime {
        siftCd: string,
        name: string,
        abName: string,
        symbolName: string,
        dailyWorkAtr: number,
        methodAtr: number,
        displayAtr: number,
        note: string,
        start: number,
        end: number,
        timeNumberCnt: number,
    }

    export class WorkTime {
        siftCd: string;
        name: string;
        abName: string;
        symbolName: string;
        dailyWorkAtr: number;
        methodAtr: number;
        displayAtr: number;
        note: string;
        codeName: string;
        start: number;
        end: number;
        timeNumberCnt: number;
        timeZone1: string;
        timeZone2: string;

        constructor(params: IWorkTime) {
            this.siftCd = params.siftCd;
            this.name = params.name;
            this.abName = params.abName;
            this.symbolName = params.symbolName || '';
            this.dailyWorkAtr = params.dailyWorkAtr;
            this.methodAtr = params.methodAtr;
            this.displayAtr = params.displayAtr;
            this.note = params.note || '';
            this.codeName = this.siftCd + this.name;
            this.start = params.start;
            this.end = params.end;
            this.timeNumberCnt = params.timeNumberCnt;
            this.timeZone1 = this.timeNumberCnt == 1 ? nts.uk.time.parseTime(this.start, true).format() + nts.uk.resource.getText("KSU001_66") + nts.uk.time.parseTime(this.end, true).format() : '';
            this.timeZone2 = this.timeNumberCnt == 2 ? nts.uk.time.parseTime(this.start, true).format() + nts.uk.resource.getText("KSU001_66") + nts.uk.time.parseTime(this.end, true).format() : '';
        }
    }

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
        symbolName: string;
        startTime: any;
        endTime: any;
        constructor(params: IExCell) {
            this.workTypeCode = params.workTypeCode;
            this.workTypeName = params.workTypeName;
            this.workTimeCode = params.workTimeCode;
            this.workTimeName = params.workTimeName;
            this.symbolName = params.symbolName;
            this.startTime = params.startTime;
            this.endTime = params.endTime;
        }
    }

    export class CellColor {
        columnKey: any;
        rowId: any;
        innerIdx: any;
        clazz: any;
        constructor(columnKey: any, rowId: any, clazz: any, innerIdx?: any) {
            this.columnKey = columnKey;
            this.rowId = rowId;
            this.innerIdx = innerIdx;
            this.clazz = clazz;
        }
    }
}