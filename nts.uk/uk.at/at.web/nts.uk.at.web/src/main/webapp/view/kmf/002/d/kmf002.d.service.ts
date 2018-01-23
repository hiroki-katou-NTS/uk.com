module nts.uk.at.view.kmf002.d {
    export module service {
        /**
         * define path to service
         */
        var path: any = {
                save: "bs/employee/holidaysetting/employment/save",
                find: "bs/employee/holidaysetting/employment/findEmploymentMonthDaySetting",
                remove: "bs/employee/holidaysetting/employment/remove",
                findFirstMonth: "basic/company/beginningmonth/find",
            };
        
        /**
         * 
         */
        export function save(year: number, data: any, empCd: string): JQueryPromise<any> {
            model.EmploymentMonthDaySetting employmentMonthDaySetting = new model.EmploymentMonthDaySetting(year, empCd, new Array<model.PublicHolidayMonthSettingDto>());
            employmentMonthDaySetting.toDto(data);
            let command = {};
            command.year = year;
            command.publicHolidayMonthSettings = employmentMonthDaySetting.publicHolidayMonthSettingDto;
            command.empCd = empCd;
            return nts.uk.request.ajax("com", path.save, command);
        }
        
        export function find(year: number, employmentCode: string): JQueryPromise<any> {
            return nts.uk.request.ajax("com", path.find + "/" + year + "/" + employmentCode);
        }
        
        export function remove(year: number, employmentCode: string): JQueryPromise<any> {
            model.EmploymentMonthDaySettingRemoveCommand employmentMonthDaySettingRemoveCommand = new model.EmploymentMonthDaySettingRemoveCommand(year, employmentCode);
            let command = {};
            command.year = year;
            command.empCd = employmentCode;
            return nts.uk.request.ajax("com", path.remove, command);
        }
        
        export function findFirstMonth(): JQueryPromise<any>{
            return nts.uk.request.ajax("com", path.findFirstMonth);
        }
    }
    
    /**
     * Model define.
     */
    export module model {
        export class EmploymentMonthDaySetting {
            year: number;
            publicHolidayMonthSettingDto: Array<PublicHolidayMonthSettingDto>;
            empCd: string;
            
            constructor(year: number, empCd: string, publicHolidayMonthSettingDto: Array<PublicHolidayMonthSettingDto>){
                let _self = this;
                _self.year = year;
                _self.publicHolidayMonthSettingDto = publicHolidayMonthSettingDto;
                _self.empCd = empCd;
            }
            
            toDto(data: any): void {
                let _self = this;
                _.forEach(data, function(newValue) {
                    _self.publicHolidayMonthSettingDto.push(new PublicHolidayMonthSettingDto(_self.year, newValue.month(), newValue.day()));
                });
            }
        }
        
        export class EmploymentMonthDaySettingRemoveCommand {
            year: number;
            empCd: string;
            
            constructor(year: number, empCd: string){
                let _self = this;
                _self.year = year;
                _self.empCd = empCd;
            }
        }
        
        export class PublicHolidayMonthSettingDto{
            publicHdManagementYear: number;
            month: number;
            inLegalHoliday: number;
            
            constructor(publicHdManagementYear: number, month: number, inLegalHoliday: number) {
                this.publicHdManagementYear = publicHdManagementYear;
                this.month = month;
                this.inLegalHoliday = inLegalHoliday;
            }
        }
    }
    
}