module nts.uk.at.view.ksm005.c {
    export module service {
        var paths = {
            findByIdMonthlyPatternSetting: "ctx/at/shared/pattern/monthly/setting/findById",
            saveMonthlyPatternSetting: "ctx/at/shared/pattern/monthly/setting/save",
            copyMonthlyPatternSetting: "ctx/at/shared/pattern/monthly/setting/copy",
            deleteMonthlyPatternSetting: "ctx/at/shared/pattern/monthly/setting/delete",
            getListHistory: "ctx/at/shared/pattern/monthly/setting/getListHistory",
            getListMonthlyPattern: "ctx/at/schedule/pattern/monthly/findAll",
            getAllMonthlyPatternSetting: "ctx/at/shared/pattern/monthly/setting/findAll",
            getMonthlyPatternSettingBySid: "ctx/at/shared/pattern/monthly/setting/findBySId",
            filterSids: "ctx/at/shared/wcitem/filter/sids",
        }
        
        /**
         * call service find by employee id
         */
        export function findByIdMonthlyPatternSetting(historyId: string): JQueryPromise<model.MonthlyPatternSettingDto> {
            return nts.uk.request.ajax('at', paths.findByIdMonthlyPatternSetting + "/" + historyId);                
        }
        
         /**
         * call service save monthly pattern setting
         */
        export function saveMonthlyPatternSetting(dto: model.MonthlyPatternSettingDto): JQueryPromise<void> {
            return nts.uk.request.ajax('at', paths.saveMonthlyPatternSetting, dto);
        }
        
        export function copyMonthlyPatternSetting(dto: model.CopyMonthlyPatternSettingDto): JQueryPromise<void> {
            return nts.uk.request.ajax('at', paths.copyMonthlyPatternSetting, dto);
        }
        
        /**
        * call service save monthly pattern setting
        */
        export function deleteMonthlyPatternSetting(dto: model.MonthlyPatternSettingDto): JQueryPromise<void> {
            return nts.uk.request.ajax('at', paths.deleteMonthlyPatternSetting, dto);
        }
        
        /**
         * get list history
         */
        export function getListHistory(employeeId: string): JQueryPromise<any> {
            return nts.uk.request.ajax('at', paths.getListHistory + "/" + employeeId);
        }
        
        /**
         * get list MonthlyPattern
         */
        export function getListMonthlyPattern(): JQueryPromise<any> {
            return nts.uk.request.ajax('at', paths.getListMonthlyPattern);
        }
        
        export function findAllMonthlyPatternSetting(employeeIds: string[], monthlyPatternCodes: string[]): JQueryPromise<any> {
             return nts.uk.request.ajax('at', paths.getAllMonthlyPatternSetting, {employeeIds: employeeIds, monthlyPatternCodes: monthlyPatternCodes});
        }
        
        export function findMonthlyPatternSettingBySid(employeeId: string[]): JQueryPromise<any> {
             return nts.uk.request.ajax('at', paths.getAllMonthlyPatternSetting, {employeeIds: employeeId});
        }
        
        export function findWorkConditionBySids(employeeIds: string[]): JQueryPromise<any> {
             return nts.uk.request.ajax('at', paths.filterSids,employeeIds);
        }
        
        export module model {

            export class MonthlyPatternDto {
                code: string;
                name: string;
            }
            
            export interface CopyMonthlyPatternSettingDto{
                
                destSid: string[];
                
                sourceSid: string;
                
                isOverwrite: number;
            }
            
            export class MonthlyPatternSettingDto{
                employeeId: string;
                historyId: string;
                monthlyPatternCode: string;
                
                constructor(employeeId: string, historyId: string, monthlyPatternCode: string) {
                    this.employeeId = employeeId;
                    this.historyId = historyId;
                    this.monthlyPatternCode = monthlyPatternCode
                }
            }
            
            export class HistoryDto{
                historyId: string;
                period: Period;  
            }
            
            /**
             * Period
             */
            export class Period {
                startDate: string;
                endDate: string;
            }
        }

    }
}