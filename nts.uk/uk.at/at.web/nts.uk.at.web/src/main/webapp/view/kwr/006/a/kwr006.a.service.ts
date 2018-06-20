module nts.uk.at.view.kwr006.a {
    export module service {
        var paths = {
           exportSchedule: "screen/at/monthlyschedule/export",
           getPeriod: "at/function/annualworkschedule/get/period"
        }
        export function saveCharacteristic(data: model.MonthlyWorkScheduleConditionDto): JQueryPromise<void> {
            return nts.uk.characteristics.save("MonthlyWorkScheduleCondition" +
                "_companyId_" + data.companyId +
                "_userId_" + data.userId, data);
        }

        export function restoreCharacteristic(): JQueryPromise<model.MonthlyWorkScheduleConditionDto> {
            return nts.uk.characteristics.restore("MonthlyWorkScheduleCondition" +
                "_companyId_" + __viewContext.user.companyId +
                "_userId_" + __viewContext.user.employeeId);
        }
        
        export function exportSchedule(query: any): JQueryPromise<any> {
            return nts.uk.request.exportFile(paths.exportSchedule, query);
        }

        export function getPeriod(): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.getPeriod);
        }

        export module model {
            
            export interface MonthlyWorkScheduleConditionDto {
                companyId: string;
                userId: string;
                outputType: number;
                pageBreakIndicator: number;
                totalOutputSetting: WorkScheduleSettingTotalOutputDto;
            }
            
            export interface WorkScheduleSettingTotalOutputDto {
                details: boolean;
                personalTotal: boolean;
                workplaceTotal: boolean; 
                grossTotal: boolean;
               
                workplaceHierarchyTotal: TotalWorkplaceHierachyDto;
            }
    
            export interface TotalWorkplaceHierachyDto {
                firstLevel: boolean;
                secondLevel: boolean;
                thirdLevel: boolean;
                fourthLevel: boolean;
                fifthLevel: boolean;
                sixthLevel: boolean;
                seventhLevel: boolean;
                eightLevel: boolean;
                ninthLevel: boolean;
                 cumulativeWorkplace: boolean;
            }
        }
        
    }

}