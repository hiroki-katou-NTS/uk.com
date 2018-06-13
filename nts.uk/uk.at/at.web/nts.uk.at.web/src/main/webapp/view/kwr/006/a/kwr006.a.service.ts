module nts.uk.at.view.kwr006.a {
    export module service {
        var paths = {
           exportSchedule: "screen/at/monthlyschedule/export"
        }
        export function saveCharacteristic(data: WorkScheduleOutputConditionDto): void {
            nts.uk.characteristics.save("MonthlyWorkScheduleCondition" +
                "_companyId_" + __viewContext.user.companyId +
                "_employeeId_" + __viewContext.user.employeeId, data);
        }

        export function restoreCharacteristic(): JQueryPromise<WorkScheduleOutputConditionDto> {
            return nts.uk.characteristics.restore("MonthlyWorkScheduleCondition" +
                "_companyId_" + __viewContext.user.companyId +
                "_employeeId_" + __viewContext.user.employeeId);
        }
        
        export function exportSchedule(query: any): JQueryPromise<any> {
            return nts.uk.request.exportFile(paths.exportSchedule, query);
        }
        
        export interface WorkScheduleOutputConditionDto {
            companyId: string;
            userId: string;
            outputType: number;
            pageBreakIndicator: number;
        }
    }

}