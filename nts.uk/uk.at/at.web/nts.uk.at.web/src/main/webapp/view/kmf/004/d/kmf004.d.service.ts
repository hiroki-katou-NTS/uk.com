module nts.uk.at.view.kmf004.d.service {
    
    var paths: any = {
        findBySphdCd: "shared/grantdatetbl/findBySphdCd/{0}",
        findByGrantDateCd: "shared/grantdatetbl/findByGrantDateCd/{0}/{1}",
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
    
    export function addGrantDate(data: Array<GrantDateTblCommand>): JQueryPromise<any> {
        var path = nts.uk.text.format(paths.addGrantDate);
        return nts.uk.request.ajax("at", path, data);
    }
    
    export function updateGrantDate(data: Array<GrantDateTblCommand>): JQueryPromise<any> {
        var path = nts.uk.text.format(paths.updateGrantDate);
        return nts.uk.request.ajax("at", path, data);
    }
    
    export function deleteGrantDate(specialHolidayCode: number, grantDateCode: string): JQueryPromise<any> {
        var path = nts.uk.text.format(paths.deleteGrantDate);
        return nts.uk.request.ajax(path, { specialHolidayCode: specialHolidayCode, grantDateCode: grantDateCode });
    }


    
    export interface GrantDateTblCommand {
        /** 特別休暇コード */
        specialHolidayCode: number,
        /** コード */
        grantDateCode: string,
        /** 名称 */
        grantDateName: string,
        /** 付与日数  */
        elapseYear: Array<GrantElapseYearMonthCommand>,
        /** 規定のテーブルとする */
        isSpecified: boolean,
        /** テーブル以降の付与日数 */
        grantedDays: number,      
        /** 経過年数テーブル */
        elapseYearMonthTblList: Array<ElapseYearMonthTblCommand>,
        /** テーブル以降の固定付与をおこなう */
        fixedAssign: boolean,
        year: number,
        month: number
    }
    
    export interface GrantElapseYearMonthCommand {
   	    /** 付与回数 */
        elapseNo: number,
        /** 付与日数 */
        grantedDays: number
    }
    export interface ElapseYearMonthTblCommand{
        /** 付与回数 */
        grantCnt: number,
        /** 経過年数 */
        elapseYearMonth: ElapseYearMonthCommand
    }
    export interface ElapseYearMonthCommand {
        year: number,
        month: number
    }
}          