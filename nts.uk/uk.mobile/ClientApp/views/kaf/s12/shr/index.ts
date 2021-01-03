import { Vue, _ } from '@app/provider';
import { KAFS00P1Params } from 'views/kaf/s00/sub/p1';
import { IAppDispInfoStartupOutput, IOpActualContentDisplayLst } from '../../s04/a/define';

export class DispInfoOfTimeLeaveRequest {
    public frame: number | null;
    public header: string;
    public attendanceTimeLabel: string;
    public attendanceTime: number | null = null;
    public titleOfAttendanceTime: string;
    public kafS00P1Params: KAFS00P1Params;
    public numberOfHoursLeft: number | null;
    public destination = {
        firstAfterWork: 0,
        firstBeforeWork: 0,
        privateGoingOut: 0,
        secondAfterWork: 0,
        secondBeforeWork: 0,
        unionGoingOut: 0,
    };

    constructor(iDispInfoOfTimeLeaveRequest: IDispInfoOfTimeLeaveRequest) {
        this.frame = iDispInfoOfTimeLeaveRequest.frame;
        this.header = iDispInfoOfTimeLeaveRequest.header;
        this.attendanceTimeLabel = iDispInfoOfTimeLeaveRequest.attendanceTimeLabel;
        this.attendanceTime = iDispInfoOfTimeLeaveRequest.attendanceTime;
        this.titleOfAttendanceTime = iDispInfoOfTimeLeaveRequest.titleOfAttendanceTime;
        this.kafS00P1Params = iDispInfoOfTimeLeaveRequest.kafS00P1Params;
        this.numberOfHoursLeft = iDispInfoOfTimeLeaveRequest.numberOfHoursLeft;
        this.destination = iDispInfoOfTimeLeaveRequest.destination;
    }
}

export class CalculationResult {
    public workHeader: string;
    public requiredTime: number;
    public applicationTime: number;
    public frame: number;
    public appliesTime: IAppliesTime[];

    constructor(iCalculationResult: ICalculationResult) {
        this.workHeader = iCalculationResult.workHeader;
        this.requiredTime = iCalculationResult.requiredTime;
        this.applicationTime = iCalculationResult.applicationTime;
        this.frame = iCalculationResult.frame;
        this.appliesTime = iCalculationResult.appliesTime;
    }
}

export class GoBackTime {
    public frame: number;
    public name: string;
    public swtOutClassification: number;
    public goBackTime = { start: null, end: null };

    constructor(iGoBackTime: IGoBackTime) {
        this.frame = iGoBackTime.frame;
        this.goBackTime.start = iGoBackTime.startTime;
        this.goBackTime.end = iGoBackTime.endTime;
        this.name = iGoBackTime.name;
        this.swtOutClassification = iGoBackTime.swtOutClassification;
    }
}

//時間休暇申請の表示情報
export interface IDispInfoOfTimeLeaveRequest {
    frame: number | null;
    header: string;
    attendanceTimeLabel: string;
    attendanceTime: number | null;
    titleOfAttendanceTime: string;
    kafS00P1Params: KAFS00P1Params;
    numberOfHoursLeft: number | null;
    destination: {
        firstAfterWork: number;
        firstBeforeWork: number;
        privateGoingOut: number;
        secondAfterWork: number;
        secondBeforeWork: number;
        unionGoingOut: number;
    };
}

export interface IGoBackTime {
    frame: number;
    name: string;
    swtOutClassification: number;
    startTime: number;
    endTime: number;
}
export interface ICalculationResult {
    frame: number;
    workHeader: string;
    requiredTime: number;
    applicationTime: number;
    appliesTime: IAppliesTime[];
}

export interface IAppliesTime {
    title: string;
    hoursOfWorkType: number | string | null;
    frame: number;
}


