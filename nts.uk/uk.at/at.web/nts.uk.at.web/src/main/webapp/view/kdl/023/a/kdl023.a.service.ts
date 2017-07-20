module nts.uk.at.view.kdl023.a.service {

    export function save(key: any, data: model.PatternReflection): void {
        nts.uk.characteristics.save(key, data);
    }

    export function find(key: string): JQueryPromise<model.PatternReflection> {
        return nts.uk.characteristics.restore(key);
    }

    export module model {
        export interface PatternReflection {
            employeeId: string;
            reflectionMethod: ReflectionMethod;
            patternClassification: PatternClassification;
            statutorySetting: DayOffSetting;
            nonStatutorySetting: DayOffSetting;
            holidaySetting: DayOffSetting;
        }
        export interface DailyPatternSetting {
            patternCode: string;
            patternName: string;
            workPatterns: Array<DailyPatternValue>;
        }
        export interface DailyPatternValue {
            dispOrder: number;
            workTypeCode: string;
            workingHoursCode: string;
            days: number;
        }
        export interface WorkType {
            workTypeCode: number;
            workTypeName: string;
        }
        export interface WorkTime {
            code: string;
            name: string;
        }
        export interface DayOffSetting {
            useClassification: boolean;
            workTypeCode: string;
        }
        export enum PatternClassification {
            Confirmation,
            Configuration
        }
        export enum ReflectionMethod {
            Overwrite,
            FillInTheBlank
        }
    }
}