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
    
    export class ExItem {
        empId: string;
        empName: string;
        __25: string;
        __26: string;
        __27: string;
        __28: string;
        __29: string;
        __30: string;
        __31: string;
        _1: any;
        _2: string;
        _3: string;
        _4: string;
        _5: string;
        _6: string;
        _7: string;
        _8: string;
        _9: string;
        _10: string;
        _11: string;
        _12: string;
        _13: string;
        _14: string;
        _15: string;
        _16: string;
        _17: string;
        _18: string;
        _19: string;
        _20: string;
        _21: string;
        _22: string;
        _23: string;
        _24: string;
        _25: string;
        _26: string;
        _27: string;
        _28: string;
        _29: string;
        _30: string;
        _31: string;
        
        constructor(empId: string, manual?: boolean) {
            this.empId = empId;
            this.empName = empId;
            if (manual) {
                let days = [ "日", "月", "火", "水", "木", "金", "土" ];
                for (let i = -6; i <= 31; i++) {
                    if (i <= 0) {
                        let d = 31 + i;
                        this["__" + d] = d + "<br/>" + days[7 + i === 7 ? 0 : 7 + i];
                    } else {
                        this["_" + i] = "4/" + i + "<br/>" + days[i % 7]; 
                    }
                }
                return;
            }
            for (let i = -6; i <= 31; i++) {
                if (i <= 0) {
                    let d = 31 + i;
                    this["__" + d] = new ExCell("001", "出勤A" + this.empId, "1", "通常８ｈ");
                } else if (i === 1) this["_" + i] = new ExCell("001", "出勤A" + this.empId, "1", "通常８ｈ" + this.empId);
                else if (i === 2) this["_" + i] = new ExCell("002", "出勤B" + this.empId, "1", "通常８ｈ" + this.empId);
                else if (i === 3) this["_" + i] = new ExCell("003", "出勤C" + this.empId, "1", "通常８ｈ" + this.empId);
                else if (i === 4) this["_" + i] = new ExCell("004", "出勤D" + this.empId, "1", "通常８ｈ" + this.empId);
                else if (i === 6) this["_" + i] = new ExCell(null, null, null, null, null, null);
                else this["_" + i] = new ExCell("00" + i, "出勤" + i + this.empId, "1", "通常８ｈ" + this.empId);
            }
            
            if (empId === "1") {
                this["_" + 2] = new ExCell(null, null, null, null, null, null, null);
            }
        }
    }
    
    let detailHeaderDs = [];
    detailHeaderDs.push(new ExItem(undefined, true));
    detailHeaderDs.push({
        empId: "", __25: "over", __26: "", __27: "", __28: "", __29: "", __30: "", __31: "",
        _1: "セール", _2: "<div class='header-image'></div>", _3: "", _4: "", _5: "", _6: "", _7: "", _8: "", _9: "特別", _10: "",
        _11: "", _12: "", _13: "", _14: "", _15: "", _16: "Oouch", _17: "", _18: "", _19: "", _20: "", _21: "", _22: "", _23: "",
        _24: "", _25: "", _26: "設定", _27: "", _28: "", _29: "", _30: "", _31: "",
    });
    
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