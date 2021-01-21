import { Vue, _ } from '@app/provider';
import { component, Prop, Watch } from '@app/core/component';
import { IApprovalPhase, IApprovalFrame, IApprover } from 'views/cmm/s45/shr/index.d';

@component({
    name: 'cmms45componentsapp1',
    style: require('./style.scss'),
    template: require('./index.vue'),
    validations: {},
    constraints: []
})
export class CmmS45ComponentsApp1Component extends Vue {
    @Prop({ default: () => ({ appOvertime: null }) })
    public params: { appOvertime: IOvertime };

    public title: string = 'CmmS45ComponentsApp1';

    get $app() {
        return this.params.appOvertime;
    }

    get appType() {
        if (this.$app.appType == 0) {
            return '残業申請';
        } else {
            return '';
        }
    }

    get prePost() {
        if (this.$app.prePostAtr == 0) {
            return '事前';
        } else {
            return '事後';
        }
    }

    get isPostApp() {
        if (this.$app.prePostAtr == 1) {
            return true;
        } else {
            return false;
        }
    }

    get breakTimeLst() {
        let breakTimeLst: Array<IOvertimeBreak> = this.$app.breakTimeLst;

        return breakTimeLst;
    }

    get overTimeLst() {
        let self = this;
        let overtimeLst: Array<OvertimeFrame> = _.filter(self.$app.frameLst, {'attendanceID': 1})
                                                .map((frame) => { 
                                                    let overtimeColor = _.find(self.$app.timeLst, (overtimeColor) => {
                                                        return overtimeColor.attendanceID == 1 &&
                                                                overtimeColor.frameNo == frame.frameNo;    
                                                    });

                                                    return new OvertimeFrame(frame, overtimeColor, self.$app.appOvertimeNightFlg, self.$app.flexFLag);
                                                });
        overtimeLst.push(new OvertimeFrame(
            {'attendanceID': 1, 'frameNo': 11, 'frameName': ''},
                _.find(self.$app.timeLst, {'attendanceID': 1, 'frameNo': 11}),
                self.$app.appOvertimeNightFlg, 
                self.$app.flexFLag
        ));
        overtimeLst.push(new OvertimeFrame(
            {'attendanceID': 1, 'frameNo': 12, 'frameName': ''},
                _.find(self.$app.timeLst, {'attendanceID': 1, 'frameNo': 12}),
                self.$app.appOvertimeNightFlg, 
                self.$app.flexFLag
        ));

        return overtimeLst;
    }

    get restTimeLst() {
        return [];
    }

    get payTimeLst() {
        let self = this;
        let payTimeLst: Array<OvertimeFrame> = _.filter(self.$app.frameLst, {'attendanceID': 3})
                                                .map((frame) => { 
                                                    let overtimeColor = _.find(self.$app.timeLst, (overtimeColor) => {
                                                        return overtimeColor.attendanceID == 3 &&
                                                                overtimeColor.frameNo == frame.frameNo;    
                                                    });

                                                    return new OvertimeFrame(frame, overtimeColor);
                                                });

        return payTimeLst;
    }
}

export interface IOvertime {
    appDate: string;
    appOvertimeNightFlg: boolean;
    appReason: string;
    appType: number;
    applicant: string;
    displayAppReasonContentFlg: boolean;
    displayBonusTime: boolean;
    displayCaculationTime: boolean;
    displayDivergenceReasonForm: boolean;
    displayDivergenceReasonInput: boolean;
    displayRestTime: boolean;
    divergenceReasonContent: string;
    endTime: number;
    flexFLag: boolean;
    inputDate: string;
    prePostAtr: number;
    representer: string;
    startTime: number;
    frameLst: Array<OvertimeFrame>;
    breakTimeLst: Array<IOvertimeBreak>;
    timeLst: Array<IOvertimeColor>;
    typicalReasonDisplayFlg: boolean;
    workTimeCD: string;
    workTimeName: string;
    workTypeCD: string;
    workTypeName: string;
}

export interface IOvertimeColor {
    actualError: number;
    actualTime: number;
    appTime: number;
    attendanceID: number;
    calcError: number;
    calcTime: number;
    frameNo: number;
    preAppError: number;
    preAppTime: number;
}

export interface IOvertimeBreak {
    frameNo: number;
    startTime: number;
    endTime: number;   
}

export interface IOvertimeFrame {
    attendanceID: number;
    frameNo: number;
    frameName: string;   
}

export class OvertimeFrame {
    public attendanceID: number = 0;
    public frameNo: number = 0;
    public frameName: string = '';
    public actualError: number = 0;
    public actualTime: number = null;
    public appTime: number = null;
    public calcError: number = 0;
    public calcTime: number = null;
    public preAppError: number = 0;
    public preAppTime: number = null;
    public appOvertimeNightFlg?: boolean = false;
    public flexFLag?: boolean = false;

    constructor(overtimeFrame: IOvertimeFrame, overtimeColor: IOvertimeColor, appOvertimeNightFlg?: boolean, flexFLag?: boolean) {
        this.attendanceID = overtimeFrame.attendanceID;
        this.frameNo = overtimeFrame.frameNo;
        this.frameName = overtimeFrame.frameName;
        if (overtimeColor) {
            this.actualError = overtimeColor.actualError;
            this.actualTime = overtimeColor.actualTime;
            this.appTime = overtimeColor.appTime;
            this.calcError = overtimeColor.calcError;
            this.calcTime = overtimeColor.calcTime;
            this.preAppError = overtimeColor.preAppError;
            this.preAppTime = overtimeColor.preAppTime;
        }
        if (appOvertimeNightFlg) {
            this.appOvertimeNightFlg = appOvertimeNightFlg;
        }
        if (flexFLag) {
            this.flexFLag = flexFLag;
        }
    }

    get getDisplay() {
        return (this.frameNo <= 10) ||
                (this.frameNo == 11 && this.appOvertimeNightFlg) ||
                (this.frameNo == 12 && this.flexFLag);  
    }
}

