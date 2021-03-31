module nts.uk.at.view.kmf004.d.service {
    
    var paths: any = {
        findBySphdCd: "shared/grantdatetbl/findBySphdCd/{0}",
        findByGrantDateCd: "shared/grantdatetbl/findByGrantDateCd/{0}/{1}",
        findElapseYearByCd: "shared/grantdatetbl/findElapseYearByCd/{0}",
        addGrantDate: "shared/grantdatetbl/add",
        updateGrantDate: "shared/grantdatetbl/update",
        deleteGrantDate: "shared/grantdatetbl/delete"
    }
        
    export function findBySphdCd(specialHolidayCode: number): JQueryPromise<any> {
        var path = nts.uk.text.format(paths.findBySphdCd, specialHolidayCode);
        return nts.uk.request.ajax("at", path);
    }

    export function findByGrantDateCd(specialHolidayCode: number, grantDateCode: string): JQueryPromise<any> {
        var path = nts.uk.text.format(paths.findByGrantDateCd, specialHolidayCode, grantDateCode);
        return nts.uk.request.ajax("at", path);
    }
    export function findElapseYearByCd(specialHolidayCode: number): JQueryPromise<any> {
        var path = nts.uk.text.format(paths.findElapseYearByCd, specialHolidayCode);
        return nts.uk.request.ajax("at", path);
    }
    
    export function addGrantDate(data: GrantDateTblCommand): JQueryPromise<any> {
        var path = nts.uk.text.format(paths.addGrantDate);
        return nts.uk.request.ajax("at", path, data);
    }
    
    export function updateGrantDate(data: GrantDateTblCommand): JQueryPromise<any> {
        var path = nts.uk.text.format(paths.updateGrantDate);
        return nts.uk.request.ajax("at", path, data);
    }
    
    export function deleteGrantDate(specialHolidayCode: number, grantDateCode: string): JQueryPromise<any> {
        var path = nts.uk.text.format(paths.deleteGrantDate);
        return nts.uk.request.ajax(path, { specialHolidayCode: specialHolidayCode, grantDateCode: grantDateCode });
    }


    
    export class GrantDateTblCommand {
        /** 特別休暇コード */ 
        specialHolidayCode: number;
        /** コード */ // D3_6
        grantDateCode: string; //shared data
        /** 名称 */ // D3_7
        grantDateName: string;
        /** 付与日数  */    
        elapseYear: Array<GrantElapseYearMonthCommand>; // elapseNo, D4_9
        /** 規定のテーブルとする */ 
        isSpecified: boolean; // D3_8
        /** テーブル以降の付与日数 */
        grantedDays: number;     // D5_3 
        /** 経過年数テーブル */
        elapseYearMonthTblList: Array<ElapseYearMonthTblCommand>; // grantCnt, D4_7, D4_8
        /** テーブル以降の固定付与をおこなう */
        fixedAssign: boolean; // D5_1
        year: number; // D5_5_cycleYear
        month: number; // D5_6_cycleMonth
        constructor(specialHolidayCode: number,
                    grantDateCode: string,
                    grantDateName: string,
                    elapseYear: Array<any>,
                    isSpecified: boolean,
                    grantedDays: number,
                    elapseYearMonthTblList: Array<any>,
                    fixedAssign: boolean,
                    year: number,
                    month: number){
            this.specialHolidayCode = specialHolidayCode;
            this.grantDateCode = grantDateCode
            this.grantDateName = grantDateName;
            this.elapseYear = elapseYear;
            this.isSpecified = isSpecified;
            this.grantedDays = grantedDays;
            this.elapseYearMonthTblList = elapseYearMonthTblList;
            this.fixedAssign = fixedAssign;
            this.year = year;
            this.month = month;
        }
    }
    
    export class GrantElapseYearMonthCommand {
   	    /** 付与回数 */
        elapseNo: number; // mapping grantCnt
        /** 付与日数 */
        grantedDays: number; // D4_9
        constructor(elapseNo: number, grantedDays: number){
            this.elapseNo = elapseNo;
            this.grantedDays = grantedDays;
        }
    }
    export class ElapseYearMonthTblCommand{
        /** 付与回数 */
        grantCnt: number; // mapping elapseNo
        /** 経過年数 */
        elapseYearMonth: ElapseYearMonthCommand; // D4_7, D4_8
        constructor(grantCnt: number, elapseYearMonth: ElapseYearMonthCommand){
            this.grantCnt = grantCnt;
            this.elapseYearMonth = elapseYearMonth
        }
    }
    export class ElapseYearMonthCommand {
        year: number;   // D4_7
        month: number;  // D4_8
        constructor(year: number, month: number){
            this.year = year;
            this.month = month;
        }
    }
}          