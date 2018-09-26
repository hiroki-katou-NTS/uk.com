module nts.uk.at.view.kwr006.a {
    export module service {
        var paths = {
            exportSchedule: "screen/at/monthlyschedule/export",
            getPeriod: "at/function/annualworkschedule/get/period",
            getExistAuthority: "at/function/monthlyworkschedule/find/employment/authority",
            findAllOutputItemMonthlyWorkSchedule: "at/function/monthlyworkschedule/findall"
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
        
        export function exportSchedule(query: model.MonthlyWorkScheduleQuery): JQueryPromise<any> {
            return nts.uk.request.exportFile(paths.exportSchedule, query);
        }

        export function getPeriod(): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.getPeriod);
        }

        export function getExistAuthority(): JQueryPromise<boolean> {
            return nts.uk.request.ajax(paths.getExistAuthority);
        }

        export function findAllOutputItemMonthlyWorkSchedule(): JQueryPromise<Array<model.OutputItemMonthlyWorkScheduleDto>> {
            return nts.uk.request.ajax(paths.findAllOutputItemMonthlyWorkSchedule);
        }

        export module model {

            export interface OutputItemMonthlyWorkScheduleDto {
                itemCode: string;
                itemName: string;
            }

            export interface MonthlyWorkScheduleQuery {
                endYearMonth: number;
                workplaceIds: Array<string>;
                condition: MonthlyWorkScheduleConditionDto;
                fileType: number;
                baseDate: string;
            }

            export interface MonthlyWorkScheduleConditionDto {
                companyId: string;
                userId: string;
                selectedCode: string;
                outputType: number;
                pageBreakIndicator: number;
                totalOutputSetting: WorkScheduleSettingTotalOutputDto;
            }

            export interface WorkScheduleSettingTotalOutputDto {
                details: boolean;
                personalTotal: boolean;
                workplaceTotal: boolean;
                grossTotal: boolean;
                cumulativeWorkplace: boolean;
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
                eighthLevel: boolean;
                ninthLevel: boolean;
            }
        }

    }

}