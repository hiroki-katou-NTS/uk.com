import { _,Vue } from '@app/provider';
import {
    KafS00SubP3Component,
    KAFS00P3Params,
} from 'views/kaf/s00/sub/p3';

export class WorkHour {
    private workHours = { start: null, end: null };
    private actualHours = { applicationAchievementAtr: 0, startTime: null, endTime: null};
    private frame: number;
    private dispCheckbox: boolean;
    private disableCheckbox: boolean;
    private isCheck: boolean;
    private title: string;

    constructor(startTime: number, endTime: number, frame: number, title?: string, dispCheckbox?: boolean, disableCheckbox?: boolean, isCheck?: boolean, actualStart?: number, actualEnd?: number) {
        this.workHours.start = startTime,
        this.workHours.end = endTime,
        this.actualHours.startTime = actualStart,
        this.actualHours.endTime = actualEnd,
        this.frame = frame,
        this.dispCheckbox = dispCheckbox,
        this.disableCheckbox = disableCheckbox,
        this.isCheck = isCheck;
        this.title = title;
    }
}

export class GoBackHour {
    private hours = { start: null, end: null };
    private actualHours = { applicationAchievementAtr: 0, startTime: null, endTime: null};
    private frame: number;
    private dispCheckbox: boolean;
    private disableCheckbox: boolean;
    private isCheck: boolean;
    private title: string;
    private swtModel: number;
    constructor(startTime: number, endTime: number, frame: number, swtModel: number, title?: string, dispCheckbox?: boolean, disableCheckbox?: boolean, isCheck?: boolean, actualStart?: number, actualEnd?: number) {
        this.hours.start = startTime,
        this.hours.end = endTime,
        this.actualHours.startTime = actualStart,
        this.actualHours.endTime = actualEnd,
        this.frame = frame,
        this.dispCheckbox = dispCheckbox,
        this.disableCheckbox = disableCheckbox,
        this.isCheck = isCheck;
        this.title = title;
        this.swtModel = swtModel;
    }
}
