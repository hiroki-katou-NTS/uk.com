module nts.uk.at.view.kmf002.b {
    export module service {
        /**
         * define path to service
         */
        var path: any = {
                save: "bs/employee/holidaysetting/workplace/save",
                find: "bs/employee/holidaysetting/workplace/findWorkplaceMonthDaySetting",
                remove: "bs/employee/holidaysetting/workplace/remove"
            };
        
         export function save(year: number, data: any, workplaceId: string): JQueryPromise<any> {
            let workplaceMonthDaySetting: model.WorkplaceMonthDaySetting = new model.WorkplaceMonthDaySetting(year, workplaceId, []);
            workplaceMonthDaySetting.toDto(data);
            let command: any = {};
            command.year = year;
            command.publicHolidayMonthSettings = workplaceMonthDaySetting.publicHolidayMonthSettingDto;
            command.workplaceId = workplaceId;
            return nts.uk.request.ajax("com", path.save, command);
        }
        
        export function find(year: number, workplaceId: string): JQueryPromise<any> {
            return nts.uk.request.ajax("com", path.find + "/" + year + "/" + workplaceId);
        }
        
        export function remove(year: number, workplaceId: string): JQueryPromise<any> {
            let command: any = {};
            command.year = year;
            command.workplaceId = workplaceId;
            return nts.uk.request.ajax("com", path.remove, command);
        }
        
    }
    
    /**
     * Model define.
     */
    export module model {
        export class WorkplaceMonthDaySetting {
            year: number;
            publicHolidayMonthSettingDto: PublicHolidayMonthSettingDto[];
            workplaceId: string;
            
            constructor(year: number, workplaceId: string, publicHolidayMonthSettingDto: PublicHolidayMonthSettingDto[]){
                let _self = this;
                _self.year = year;
                _self.publicHolidayMonthSettingDto = publicHolidayMonthSettingDto;
                _self.workplaceId = workplaceId;
            }
            
            toDto(data: any): void {
                let _self = this;
                _.forEach(data, function(newValue) {
                    _self.publicHolidayMonthSettingDto.push(new PublicHolidayMonthSettingDto(_self.year, newValue.month(), newValue.day()));
                });
            }
        }
        
        export class WorkplaceMonthDaySettingRemoveCommand {
            year: number;
            workplaceId: string;
            
            constructor(year: number, workplaceId: string){
                let _self = this;
                _self.year = year;
                _self.workplaceId = workplaceId;
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