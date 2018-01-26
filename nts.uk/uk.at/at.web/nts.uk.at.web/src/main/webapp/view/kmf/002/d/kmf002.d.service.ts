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
        export function save(year: string, data: any, empCd: string): JQueryPromise<any> {
            let employmentMonthDaySetting: model.EmploymentMonthDaySetting= new model.EmploymentMonthDaySetting(year, empCd, []);
            employmentMonthDaySetting.toDto(data);
            let command: any = {};
            command.year = year;
            command.publicHolidayMonthSettings = employmentMonthDaySetting.publicHolidayMonthSettingDto;
            command.empCd = empCd;
            return nts.uk.request.ajax("com", path.save, command);
        }
        
        export function find(year: string, employmentCode: string): JQueryPromise<any> {
            return nts.uk.request.ajax("com", path.find + "/" + year + "/" + employmentCode);
        }
        
        export function remove(year: number, employmentCode: string): JQueryPromise<any> {
            let employmentMonthDaySettingRemoveCommand: model.EmploymentMonthDaySettingRemoveCommand= new model.EmploymentMonthDaySettingRemoveCommand(year, employmentCode);
            let command: any = {};
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
            year: string;
            publicHolidayMonthSettingDto: PublicHolidayMonthSettingDto[];
            empCd: string;
            
            constructor(year: string, empCd: string, publicHolidayMonthSettingDto: PublicHolidayMonthSettingDto[]){
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
            publicHdManagementYear: string;
            month: number;
            inLegalHoliday: number;
            
            constructor(publicHdManagementYear: string, month: number, inLegalHoliday: number) {
                this.publicHdManagementYear = publicHdManagementYear;
                this.month = month;
                this.inLegalHoliday = inLegalHoliday;
            }
        }
    }
    
}