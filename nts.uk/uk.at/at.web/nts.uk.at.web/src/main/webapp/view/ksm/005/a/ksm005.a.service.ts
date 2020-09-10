module nts.uk.at.view.ksm005.a {
    export module service {
        var paths = {
            findAllMonthlyPattern : "ctx/at/schedule/pattern/monthly/findAll",
            findByIdMonthlyPattern: "ctx/at/schedule/pattern/monthly/findById",
            addMonthlyPattern: "ctx/at/schedule/pattern/monthly/add",
            updateMonthlyPattern: "ctx/at/schedule/pattern/monthly/update",
            deleteMonthlyPattern: "ctx/at/schedule/pattern/monthly/delete",
            findByMonthWorkMonthlySetting: "ctx/at/schedule/pattern/work/monthly/setting/findByMonth",
            getItemOfMonth: "ctx/at/schedule/pattern/work/monthly/setting/getItemOfMonth",
            saveMonthWorkMonthlySetting: "ctx/at/schedule/pattern/work/monthly/setting/saveMonth",
	        getMonthlyPattern : 'screen/at/schedule/monthly-pattern/get',
	        getMonthlyAll: 'monthly/get/all',
            getWorkStyle: 'screen/at/schedule/monthly-pattern/getworkstyle'
        }
        
        /**
         * call service find all monthly pattern
         */
        export function findAllMonthlyPattern(): JQueryPromise<model.MonthlyPatternDto[]> {
            return nts.uk.request.ajax('at', paths.findAllMonthlyPattern);
        }

        export function getWorkStyle(workTypeCode: string, workingCode: string): JQueryPromise<model.WorkStyleDto> {
            return nts.uk.request.ajax('at', paths.getWorkStyle, { workTypeCode, workingCode });
        }

         /**
         * call service find by month of work monthly setting
         */
        export function findByMonthWorkMonthlySetting(monthlyPatternCode: string, month: number): JQueryPromise<model.WorkMonthlySettingDto[]> {
            return nts.uk.request.ajax('at', paths.findByMonthWorkMonthlySetting, { monthlyPatternCode: monthlyPatternCode, yearMonth: month });
        }
        
        /**
         * call service find by month of work monthly setting
         */
        export function getItemOfMonth(monthlyPatternCode: string, month: number): JQueryPromise<model.WorkMonthlySettingDto[]> {
            return nts.uk.request.ajax('at', paths.getItemOfMonth, { monthlyPatternCode: monthlyPatternCode, yearMonth: month });
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
            dto.code = nts.uk.text.padLeft(dto.code, '0', 3);
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
         * call service save all work monthly setting by month
         */
        export function saveMonthWorkMonthlySetting(settings: model.WorkMonthlySettingDto[], dto: model.MonthlyPatternDto, mode: number): JQueryPromise<void> {
            dto.code = nts.uk.text.padLeft(dto.code, '0', 3);
            return nts.uk.request.ajax('at', paths.saveMonthWorkMonthlySetting, {workMonthlySetting: settings, mode: mode, monthlyPattern: dto});
        }

	    /**
	     * call service get monthly pattern
	     */
	    export function getMonthlyPattern( params: model.MonthlyPatternDto) : JQueryPromise<void> {
		    return nts.uk.request.ajax('at', paths.getMonthlyPattern, params);
	    }

	    /**
	     * call service get monthly pattern
	     */
	    export function getMonthlyAll() : JQueryPromise<void> {
		    return nts.uk.request.ajax('at', paths.getMonthlyAll);
	    }

	    export module model {

            export interface MonthlyPatternDto {
                code: string;
                name: string;
            }
            export interface WorkMonthlySettingDto {
                workTypeCode: string;
                workingCode: string;
                ymdk: string;
                monthlyPatternCode: string;
                workTypeName: string;
                typeColor: number;
                workingName: string;
            }
                
             export interface WorkTypeDto {
                workTypeCode: string;
                name: string;
            }
            export interface WorkTimeDto {
                code: string;
                name: string;
            }
            export interface WorkStyleDto {
                workTypeCode: string;
                workingCode:string,
                typeColor: string
            }
        }

    }
}