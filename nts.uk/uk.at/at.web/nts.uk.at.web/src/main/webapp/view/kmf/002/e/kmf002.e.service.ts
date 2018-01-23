module nts.uk.at.view.kmf002.e {
    export module service {
        /**
         * define path to service
         */
        var path: any = {
                find: "bs/employee/holidaysetting/company/findCompanyMonthDaySetting",
                save: "bs/employee/holidaysetting/company/save",
                findFirstMonth: "basic/company/beginningmonth/find",
                remove: "bs/employee/holidaysetting/company/remove"
            };
        
        /**
         * 
         */
        export function find(year: number): JQueryPromise<>{
            return nts.uk.request.ajax("com",path.find + "/" + year);
        }

        
        
        export function save(year: number, data: any): JQueryPromise<any> {
            let sysResourceDto: model.SystemResourceDto= new model.SystemResourceDto(year, []);
            sysResourceDto.toDto(data);
            let command: any = {};
            command.year = year;
            command.publicHolidayMonthSettings = sysResourceDto.publicHolidayMonthSettings
            return nts.uk.request.ajax("com", path.save, command);
        }
        
        export function findFirstMonth(): JQueryPromise<any>{
            return nts.uk.request.ajax("com", path.findFirstMonth);
        }
        
        export function remove(year: number): JQueryPromise<any> {
            let command = {};
            command.year = year;
            return nts.uk.request.ajax("com", path.remove, command);
        }
    }
    
    /**
     * Model define.
     */
    export module model {
        export class SystemResourceDto {
            year: number;
            publicHolidayMonthSettings: PublicHolidayMonthSettingDto[];
            
            constructor(year: number, publicHolidayMonthSettings: PublicHolidayMonthSettingDto[]){
                let _self = this;
                _self.year = year;
                _self.publicHolidayMonthSettings = publicHolidayMonthSettings;
            }
            
            toDto(data: any): void {
                let _self = this;
                _.forEach(data, function(newValue) {
                    _self.publicHolidayMonthSettings.push(new PublicHolidayMonthSettingDto(_self.year,newValue.month(),newValue.day()));
                });
            }
        }
        
        export class CompanyMonthDaySettingRemoveCommand{
            year: number;
            
            constructor(year: number) {
                this.year = year;                    
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