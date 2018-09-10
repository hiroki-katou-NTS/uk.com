module nts.uk.at.view.ksu001.common.viewmodel {
    import formatById = nts.uk.time.format.byId;

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