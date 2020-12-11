import {Vue,_} from '@app/provider';
import {KAFS00P1Params} from 'views/kaf/s00/sub/p1';

export class DispInfoOfTimeLeaveRequest {
    public frame: number | null;
    public header: string;
    public attendanceTimeLabel: string;
    public attendanceTime: number | null = null;
    public swtOutClassification: number;
    public titleOfAttendanceTime: string;
    public kafS00P1Params: KAFS00P1Params;

    constructor(iDispInfoOfTimeLeaveRequest: IDispInfoOfTimeLeaveRequest) {
        this.frame = iDispInfoOfTimeLeaveRequest.frame;
        this.header = iDispInfoOfTimeLeaveRequest.header;
        this.attendanceTimeLabel = iDispInfoOfTimeLeaveRequest.attendanceTimeLabel;
        this.attendanceTime = iDispInfoOfTimeLeaveRequest.attendanceTime;
        this.swtOutClassification = iDispInfoOfTimeLeaveRequest.swtOutClassification;
        this.titleOfAttendanceTime = iDispInfoOfTimeLeaveRequest.titleOfAttendanceTime;
        this.kafS00P1Params = iDispInfoOfTimeLeaveRequest.kafS00P1Params;
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
    attendanceTime: number | null;
    swtOutClassification: number;
    titleOfAttendanceTime: string;
    kafS00P1Params: KAFS00P1Params;
}

export interface ICalculationResult {
    frame: number;
    workHeader: string;
    requiredTime: number;
    applicationTime: number;
    title: string;
}


