module nts.uk.at.view.kmf002.c {
    export module service {
        /**
         * define path to service
         */
        var path: any = {
                save: "bs/employee/holidaysetting/employee/save",
                find: "bs/employee/holidaysetting/employee/findEmployeeMonthDaySetting",
                remove: "bs/employee/holidaysetting/employee/remove",
                findFirstMonth: "basic/company/beginningmonth/find",
                findAllEmployeeRegister: "bs/employee/holidaysetting/employee/findEmployeeMonthDaySetting/findAllEmployeeRegister",
            };
        
        /**
         * 
         */
        export function save(year: string, data: any, sId: string): JQueryPromise<any> {
            let employeeMonthDaySetting: model.EmployeeMonthDaySetting= new model.EmployeeMonthDaySetting(year, sId, []);
            employeeMonthDaySetting.toDto(data);
            let command: any = {};
            command.year = year;
            command.publicHolidayMonthSettings = employeeMonthDaySetting.publicHolidayMonthSettingDto;
            command.employeeID = sId;
            return nts.uk.request.ajax("at", path.save, command);
        }
        
        export function find(year: string, employeeId: string): JQueryPromise<any> {
            return nts.uk.request.ajax("at", path.find + "/" + year + "/" + employeeId);
        }
        
        export function findAllEmployeeRegister(): JQueryPromise<any> {
            return nts.uk.request.ajax("at", path.findAllEmployeeRegister);
        }
        
        export function remove(year: string, sId: string): JQueryPromise<any> {
            let command: any = {};
            command.year = year;
            command.employeeId = sId;
            return nts.uk.request.ajax("at", path.remove, command);
        }
        
        export function findFirstMonth(): JQueryPromise<any>{
            return nts.uk.request.ajax("com", path.findFirstMonth);
        }
    }
    
    /**
     * Model define.
     */
    export module model {
        export class EmployeeMonthDaySetting {
            year: string;
            publicHolidayMonthSettingDto: PublicHolidayMonthSettingDto[];
            sId: string;
            
            constructor(year: string, sId: string, publicHolidayMonthSettingDto: PublicHolidayMonthSettingDto[]){
                let _self = this;
                _self.year = year;
                _self.publicHolidayMonthSettingDto = publicHolidayMonthSettingDto;
                _self.sId = sId;
            }
            
            toDto(data: any): void {
                let _self = this;
                _.forEach(data, function(newValue) {
                    _self.publicHolidayMonthSettingDto.push(new PublicHolidayMonthSettingDto(_self.year, newValue.month(), newValue.day()));
                });
            }
        }
        
        export class EmployeeMonthDaySettingRemoveCommand {
            year: number;
            sId: number;
            
            constructor(year: number, sId: number){
                let _self = this;
                _self.year = year;
                _self.sId = sId;
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