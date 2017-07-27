module nts.uk.at.view.ksm005.b {
    export module service {
        var paths = {
            findAllMonthlyPattern : "ctx/at/schedule/pattern/monthy/findAll",
            findByIdMonthlyPattern: "ctx/at/schedule/pattern/monthy/findById",
            addMonthlyPattern: "ctx/at/schedule/pattern/monthy/add",
            updateMonthlyPattern: "ctx/at/schedule/pattern/monthy/update",
            deleteMonthlyPattern: "ctx/at/schedule/pattern/monthy/delete",
            findByMonthWorkMonthlySetting: "ctx/at/schedule/pattern/work/monthy/setting/findByMonth",
            findAllWorkType : "at/share/worktype/findAll",
            findAllWorkTime: "at/shared/worktime/findByCompanyID"
        }
        
        /**
         * call service find all monthly pattern
         */
        export function findAllMonthlyPattern(): JQueryPromise<model.MonthlyPatternDto[]> {
            return nts.uk.request.ajax('at', paths.findAllMonthlyPattern);
        }
        /**
         * call service find by month of work monthly setting
         */
        export function findByMonthWorkMonthlySetting(monthlyPatternCode: string, month: number): JQueryPromise<model.WorkMonthlySettingDto[]> {
            return nts.uk.request.ajax('at', paths.findByMonthWorkMonthlySetting, { monthlyPatternCode: monthlyPatternCode, yearMonth: month });
        }
        /**
         * call service find by id of monthly pattern
         */
        export function findByIdMonthlyPattern(monthlyPatternCode: string): JQueryPromise<model.MonthlyPatternDto> {
            return nts.uk.request.ajax('at', paths.findByIdMonthlyPattern + "/" + monthlyPatternCode);
        }
        
        /**
         * call service add monthly pattern
         */
        export function addMonthlyPattern(dto: model.MonthlyPatternDto) :JQueryPromise<void> {
            return nts.uk.request.ajax('at', paths.addMonthlyPattern, {dto: dto});
        }
        /**
         * call service update monthly pattern
         */
        export function updateMonthlyPattern(dto: model.MonthlyPatternDto) :JQueryPromise<void> {
            return nts.uk.request.ajax('at', paths.updateMonthlyPattern, {dto: dto});
        }
        /**
         * call service delete monthly pattern
         */
        export function deleteMonthlyPattern(code: string) :JQueryPromise<void> {
            return nts.uk.request.ajax('at', paths.deleteMonthlyPattern, {monthlyPattnernCode: code});
        }
        
        /**
         * call service find all work type of company login
         */
        export function findAllWorkType(): JQueryPromise<model.WorkTypeDto[]> {
            return nts.uk.request.ajax('at', paths.findAllWorkType);
        }
        
        /**
         * call service find all work time of company login
         */
        export function findAllWorkTime(): JQueryPromise<model.WorkTimeDto[]> {
            return nts.uk.request.ajax('at', paths.findAllWorkTime);
        }
        export module model {

            export interface MonthlyPatternDto {
                code: string;
                name: string;
            }
            export interface WorkMonthlySettingDto {
                workTypeCode: string;
                workingCode: string;
                ymdk: number;
                monthlyPatternCode: string;
            }
                
             export interface WorkTypeDto {
                workTypeCode: string;
                name: string;
            }
            export interface WorkTimeDto {
                code: string;
                name: string;
            }
            
        }

    }
}