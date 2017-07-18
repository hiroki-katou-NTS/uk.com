module nts.uk.at.view.ksm005.b {
    export module service {
        var paths = {
            findAllMonthlyPattern : "ctx/at/schedule/pattern/monthy/findAll",
            findByIdMonthlyPattern: "ctx/at/schedule/pattern/monthy/findById",
            addMonthlyPattern: "ctx/at/schedule/pattern/monthy/add",
            updateMonthlyPattern: "ctx/at/schedule/pattern/monthy/update",
            deleteMonthlyPattern: "ctx/at/schedule/pattern/monthy/delete",
            findByIdWorkMonthlySetting: "ctx/at/schedule/pattern/work/monthy/setting/findById"
        }
        
        /**
         * call service find all monthly pattern
         */
        export function findAllMonthlyPattern(): JQueryPromise<model.MonthlyPatternDto[]> {
            return nts.uk.request.ajax('at', paths.findAllMonthlyPattern);
        }
        /**
         * call service find by id of work monthly setting
         */
        export function findByIdWorkMonthlySetting(monthlyPatternCode: string): JQueryPromise<model.WorkMonthlySettingDto[]> {
            return nts.uk.request.ajax('at', paths.findByIdWorkMonthlySetting + "/" + monthlyPatternCode);
        }
        /**
         * call service find by id of monthly pattern
         */
        export function findByIdMonthlyPattern(monthlyPatternCode: string): JQueryPromise<model.WorkMonthlySettingDto[]> {
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
        
        export module model {

            export interface MonthlyPatternDto {
                code: string;
                name: string;
            }
            export interface WorkMonthlySettingDto {
                code: string;
                siftCode: string;
                date: Date;
                monthlyPatternCode: string;
            }
                       
        }

    }
}