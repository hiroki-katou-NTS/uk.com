module nts.uk.at.view.kmf002.e {
    export module service {
        /**
         * define path to service
         */
        var path: any = {
                find: "at/shared/holidaysetting/company/findCompanyMonthDaySetting",
                save: "at/shared/holidaysetting/company/save",
                findFirstMonth: "basic/company/beginningmonth/find",
                remove: "at/shared/holidaysetting/company/remove"
            };
        
        /**
         * 
         */
        export function find(year: string): JQueryPromise<any>{
            return nts.uk.request.ajax("at",path.find + "/" + year);
        }

        
        
        export function save(year: string, data: any): JQueryPromise<any> {
            let sysResourceDto: model.SystemResourceDto= new model.SystemResourceDto(year, []);
            sysResourceDto.toDto(data);
            let command: any = {};
            command.year = year;
            command.publicHolidayMonthSettings = sysResourceDto.publicHolidayMonthSettings
            return nts.uk.request.ajax("at", path.save, command);
        }
        
        export function findFirstMonth(): JQueryPromise<any>{
            return nts.uk.request.ajax("com", path.findFirstMonth);
        }
        
        export function remove(year: string): JQueryPromise<any> {
            let command: any = {};
            command.year = year;
            return nts.uk.request.ajax("at", path.remove, command);
        }
    }
    
    /**
     * Model define.
     */
    export module model {
        export class SystemResourceDto {
            year: string;
            publicHolidayMonthSettings: PublicHolidayMonthSettingDto[];
            
            constructor(year: string, publicHolidayMonthSettings: PublicHolidayMonthSettingDto[]){
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