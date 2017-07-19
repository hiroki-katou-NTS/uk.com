module nts.uk.at.view.ksm005.f {
    export module service {
        var paths = {
            findAllMonthlyPattern : "ctx/at/schedule/pattern/monthy/findAll",
            addMonthlyPatternSetting: "ctx/at/schedule/pattern/monthy/setting/add"
        }
        
         /**
         * call service find all monthly pattern
         */
        export function findAllMonthlyPattern(): JQueryPromise<model.MonthlyPatternDto[]> {
            return nts.uk.request.ajax('at', paths.findAllMonthlyPattern);
        }
         /**
         * call service add monthly pattern setting
         */
        export function addMonthlyPatternSetting(dto: model.MonthlyPatternSettingDto): JQueryPromise<void> {
            return nts.uk.request.ajax('at', paths.addMonthlyPatternSetting, dto);
        }
        
        export module model {

            export interface MonthlyPatternDto {
                code: string;
                name: string;
            }
            
            export interface  MonthlyPatternSettingDto{
                employeeId: string;
                monthlyPatternCode: string;    
            }
                       
        }

    }
}