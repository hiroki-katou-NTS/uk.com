module nts.uk.at.view.kmf002.b {
    export module service {
        /**
         * define path to service
         */
        var path: any = {
                save: "at/shared/holidaysetting/workplace/save",
                find: "at/shared/holidaysetting/workplace/findWorkplaceMonthDaySetting",
                remove: "at/shared/holidaysetting/workplace/remove",
                findFirstMonth: "basic/company/beginningmonth/find",
                findAll: "at/shared/holidaysetting/workplace/findWorkplaceMonthDaySetting"
            };
        
         export function save(year: string, data: any, workplaceId: string): JQueryPromise<any> {
            let workplaceMonthDaySetting: model.WorkplaceMonthDaySetting = new model.WorkplaceMonthDaySetting(year, workplaceId, []);
            workplaceMonthDaySetting.toDto(data);
            let command: any = {};
            command.year = year;
            command.publicHolidayMonthSettings = workplaceMonthDaySetting.publicHolidayMonthSettingDto;
            command.workplaceId = workplaceId;
            return nts.uk.request.ajax("at", path.save, command);
        }
        
        export function find(year: string, workplaceId: string): JQueryPromise<any> {
            return nts.uk.request.ajax("at", path.find + "/" + year + "/" + workplaceId);
        }
        
        export function remove(year: string, workplaceId: string): JQueryPromise<any> {
            let command: any = {};
            command.year = year;
            command.workplaceId = workplaceId;
            return nts.uk.request.ajax("at", path.remove, command);
        }
        
        export function findFirstMonth(): JQueryPromise<any>{
            return nts.uk.request.ajax("com", path.findFirstMonth);
        }
        
        /**
         * Get all workplace was register according to year
         */    
        export function findAll(year: string): JQueryPromise<any> {
            return nts.uk.request.ajax("at", path.findAll + "/" + year );
        }
        
    }
    
    /**
     * Model define.
     */
    export module model {
        export class WorkplaceMonthDaySetting {
            year: string;
            publicHolidayMonthSettingDto: PublicHolidayMonthSettingDto[];
            workplaceId: string;
            
            constructor(year: string, workplaceId: string, publicHolidayMonthSettingDto: PublicHolidayMonthSettingDto[]){
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