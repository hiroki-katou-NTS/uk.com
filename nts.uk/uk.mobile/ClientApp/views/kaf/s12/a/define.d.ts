import { IAppDispInfoStartupOutput } from '../../s04/a/define';

export interface ITimeLeaveAppDispInfo {
    appDispInfoStartupOutput: IAppDispInfoStartupOutput;
    reflectSetting: {
        condition: {
            annualVacationTime: number;
            childNursing: number;
            nursing: number;
            specialVacationTime: number;
            substituteLeaveTime: number;
            superHoliday60H: number;
        }
        destination: {
            firstAfterWork: number;
            firstBeforeWork: number;
            privateGoingOut: number;
            secondAfterWork: number;
            secondBeforeWork: number;
            unionGoingOut: number;
        }
        reflectActualTimeZone: number
    }
    timeLeaveManagement: any;
    timeLeaveRemaining: any;
    workingConditionItem: any;
}

export interface IObjectChangeDate {
    startDate: Date;
    endDate: Date;
    appDispInfoStartupOutput: IAppDispInfoStartupOutput;
}

export interface ICondition {
    firstAfterWork: number;
    firstBeforeWork: number;
    privateGoingOut: number;
    secondAfterWork: number;
    secondBeforeWork: number;
    unionGoingOut: number;
    managementMultipleWorkCycles: boolean;
}