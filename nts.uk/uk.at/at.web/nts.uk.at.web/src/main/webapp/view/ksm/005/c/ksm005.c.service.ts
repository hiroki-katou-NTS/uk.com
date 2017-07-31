module nts.uk.at.view.ksm005.c {
    export module service {
        var paths = {
            findByIdMonthlyPatternSetting: "ctx/at/schedule/pattern/monthly/setting/findById",
            findAllMonthlyPatternSetting: "ctx/at/schedule/pattern/monthly/setting/findAll",
            saveMonthlyPatternSetting: "ctx/at/schedule/pattern/monthly/setting/save",
            deleteMonthlyPatternSetting: "ctx/at/schedule/pattern/monthly/setting/delete",
            findByIdMonthlyPattern: "ctx/at/schedule/pattern/monthly/findById"
        }
        
        /**
         * call service find by employee id
         */
        export function findByIdMonthlyPatternSetting(employeeId: string): JQueryPromise<model.MonthlyPatternSettingDto> {
            return nts.uk.request.ajax('at', paths.findByIdMonthlyPatternSetting + "/" + employeeId);                
        }
        /**
         * call service find by employee id
         */
        export function findAllMonthlyPatternSetting(employeeIds: string[]): JQueryPromise<string[]> {
            return nts.uk.request.ajax('at', paths.findAllMonthlyPatternSetting, {employeeIds: employeeIds});                
        }
        
         /**
         * call service save monthly pattern setting
         */
        export function saveMonthlyPatternSetting(dto: model.MonthlyPatternSettingActionDto): JQueryPromise<void> {
            return nts.uk.request.ajax('at', paths.saveMonthlyPatternSetting, dto);
        }
        
        /**
        * call service save monthly pattern setting
        */
        export function deleteMonthlyPatternSetting(dto: model.MonthlyPatternSettingActionDto): JQueryPromise<void> {
            return nts.uk.request.ajax('at', paths.deleteMonthlyPatternSetting, dto);
        }
        
        /**
         * call service find by id monthly pattern
         */
        export function findByIdMonthlyPattern(monthlyPatternCode: string):  JQueryPromise<model.MonthlyPatternDto>{
            return nts.uk.request.ajax('at', paths.findByIdMonthlyPattern + "/" + monthlyPatternCode);
        }
        
        export module model {

            export interface MonthlyPatternDto {
                code: string;
                name: string;
            }
            
            export interface MonthlyPatternSettingDto{
                setting: boolean;
                info: MonthlyPatternDto; 
            }
            
            export interface MonthlyPatternSettingActionDto {
                employeeId: string;
                monthlyPatternCode: string;
            }
                       
        }

    }
}