import {Vue,_} from '@app/provider';

export class DispInfoOfTimeLeaveRequest {
    public frame: number | null;
    public header: string;
    public attendanceTimeLabel: string;
    public goingBackTime = {start: null, end: null};
    public swtOutClassification: number;
    public title: string;
    public scheduleTime: number;

    constructor(iDispInfoOfTimeLeaveRequest: IDispInfoOfTimeLeaveRequest) {
        this.frame = iDispInfoOfTimeLeaveRequest.frame;
        this.header = iDispInfoOfTimeLeaveRequest.header;
        this.attendanceTimeLabel = iDispInfoOfTimeLeaveRequest.attendanceTimeLabel;
        this.goingBackTime.start = iDispInfoOfTimeLeaveRequest.startTime;
        this.goingBackTime.end = iDispInfoOfTimeLeaveRequest.endTime;
        this.swtOutClassification = iDispInfoOfTimeLeaveRequest.swtOutClassification;
        this.title = iDispInfoOfTimeLeaveRequest.title;
        this.scheduleTime = iDispInfoOfTimeLeaveRequest.scheduleTime;
    }
}

export class CalculationResult {
    public workHeader: string;
    public requiredTime: number;
    public applicationTime: number;
    public title: string;
    public frame: number;

    constructor (iCalculationResult: ICalculationResult) {
        this.workHeader = iCalculationResult.workHeader;
        this.requiredTime = iCalculationResult.requiredTime;
        this.applicationTime = iCalculationResult.applicationTime;
        this.title = iCalculationResult.title;
        this.frame = iCalculationResult.frame;
    }
}

export interface  IDispInfoOfTimeLeaveRequest {
    frame: number | null;
    header: string;
    attendanceTimeLabel: string;
    startTime: number;
    endTime: number;
    swtOutClassification: number;
    title: string;
    scheduleTime: number;
}

export interface ICalculationResult {
    frame: number;
    workHeader: string;
    requiredTime: number;
    applicationTime: number;
    title: string;
}


