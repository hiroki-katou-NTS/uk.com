import { _, Vue, moment } from '@app/provider';
import { Watch, component, Prop } from '@app/core/component';
import { KDL002Component } from '../../../kdl/002';
import { KSUS01BComponent } from '../b/index';

@component({
    name: 'ksus01a',
    route: '/ksu/s01/a',
    style: require('./style.scss'),
    template: require('./index.vue'),
    resource: require('./resources.json'),
    validations: {
        yearMonth: {
            required: true
        }
    },
    constraints: [],
    components: {
        'worktype': KDL002Component,
        'ksus01b': KSUS01BComponent
    }
})
export class KSUS01AComponent extends Vue {
    public title: string = 'KSUS01A';

    public yearMonth: string = moment().format('YYYYMM');
    public startDate: string = moment().format('YYYYMMDD');
    public endDate: string = moment().format('YYYYMMDD');
    public publicOpAtr: boolean = false;
    public workDesiredOpAtr: boolean = false;
    public endDatePublicationPeriod: string = null;

    public today = moment().format('YYYYMMDD');

    public dateCellList: Array<DateCell> = [];

    public dateHeaderList: Array<DateHeader> = [];

    public detailCell: DateCell = null;
    public isDetailShow: boolean = false;
    public rowFocus: number = null;
    public dateIndexFocus: number = null;

    public xDown: number = null;
    public yDown: number = null;

    public memo: string = '';

    @Watch('yearMonth')
    public subcribeYearMonth(value: string) {
        let self = this;
        self.bindDateCellList();
        console.log(self.yearMonth);
        console.log(moment(self.yearMonth, 'YYYYMM').format('YYYYMMDD'));
    }

    public created() {
        let self = this;
        self.initData();
        self.loadData();
    }

    public mounted() {

    }

    public initData() {
        let self = this;
        self.$mask('show');
        // self.$http.post('at', API.start).then((data: InitInformation) => {
        //     self.startDate = moment(data.start, 'yyyy/MM/dd').format('YYYYMMDD');
        //     self.endDate = moment(data.end, 'yyyy/MM/dd').format('YYYYMMDD');
        //     self.publicOpAtr = data.publicOpAtr;
        //     self.endDatePublicationPeriod = data.endDatePublicationPeriod;
        //     self.workDesiredOpAtr = data.workDesiredOpAtr;

        //     let desiredPeriodWork: PeriodCommand = {
        //         start: 'startTime', //huytodo
        //         end: 'endTime' //huytodo
        //     };
        //     let scheduledWorkingPeriod: PeriodCommand = {
        //         start: 'startTime', //huytodo
        //         end: 'endTime' //huytodo
        //     };
        //     let command: InforOnTargetPeriodInput = {
        //         desiredPeriodWork,
        //         scheduledWorkingPeriod
        //     };
        //     self.$http.post('at', API.changeDatePeriod, command).then((data: any ) => {  //huytodo any -> InforOnTargetPeriodDto
                
        //     });
        // }).catch((error: any) => {
        //     self.errorHandler(error);
        // }).then(() => self.$mask('hide'));

        self.createDateHeaderList();
        self.createDateCellList();
    }

    public createDateHeaderList() {
        let self = this;
        for (let index = 0; index < 7; index++) {
            self.dateHeaderList.push({
                headerName:  self.$i18n('KSUS01_' + (3 + index)),
                weekDayIndex: index
            });
        }
    }

    public createDateCellList() {
        let self = this;
        //1: 一ヶ月間以内の連続期間を表示される
        for (let index = 0; index < 6 * 7; index++) {
            self.dateCellList.push({
                isActive: false,
                isFocused: false,
                rowNumber: Math.floor(index / 7),
                weekDayIndex: index % 7,
                displayData: {} as DisplayData,
                workScheduleStyle: 'padding: 0.25em 1em; font-weight: bold; border-radius: 0.25rem; display: inline-block;',
                workDesireStyle: 'padding: 0.25em 1em; font-weight: bold; border-radius: 0.25rem; display: inline-block;'
            });
        }
    }

    public loadData() {
        let self = this;
        self.bindDateCellList();
    }

    public handleTouchStart(evt) {
        let self = this;
        self.xDown = evt.touches[0].clientX;
        self.yDown = evt.touches[0].clientY;
    }

    public handleTouchEnd(evt: TouchEvent) {
        let self = this;
        if (self.rowFocus == null || !self.xDown || !self.yDown) {
            return;
        }

        let xDiff = self.xDown - evt.changedTouches[0].clientX;
        let yDiff = self.yDown - evt.changedTouches[0].clientY;

        self.xDown = null;
        self.yDown = null;

        //huytodo set 50 for long swipe
        if (Math.abs(xDiff) > 50 && Math.abs(xDiff) > Math.abs(yDiff)) {
            if (xDiff > 0) {
                self.changeWeek(true);
                console.log(xDiff, 'right-left swipe');
            } else {
                self.changeWeek(false);
                console.log(xDiff, 'left-right swipe');
            }

            return;
        }
    }

    public changeWeek(isNext: boolean) {
        let self = this;
        if (isNext && self.rowFocus < 5) {
            let findResult = _.findLast(self.dateCellList, (el) => {
                return el.isActive;
            });
            if (findResult && findResult.rowNumber == self.rowFocus) {
                return;
            }
            self.rowFocus += 1;
            self.changeFocus(isNext);
        }
        if (!isNext && self.rowFocus > 0) {
            self.rowFocus -= 1;
            self.changeFocus(isNext);
        }
    }

    public changeFocus(isNext: boolean) {
        let self = this;
        let findResult = self.dateCellList.filter((el) => {
            return el.rowNumber == self.rowFocus && el.weekDayIndex == self.dateIndexFocus;
        });
        if (findResult.length > 0 && findResult[0].isActive) {
            self.dateCellList.map((el) => {el.isFocused = false;});
            findResult[0].isFocused = true;
            self.bindDetail(findResult[0]);
        } else {
            if (isNext) {
                let findResult = _.findLast(self.dateCellList, (el) => {
                    return el.isActive;
                });
                self.dateCellList.map((el) => {el.isFocused = false;});
                findResult.isFocused = true;
                self.dateIndexFocus = findResult.weekDayIndex;
                self.bindDetail(findResult);
            } else {
                let findResult = _.find(self.dateCellList, (el) => {
                    return el.isActive;
                });
                self.dateCellList.map((el) => {el.isFocused = false;});
                findResult.isFocused = true;
                self.dateIndexFocus = findResult.weekDayIndex;
                self.bindDetail(findResult);
            }
        }
    }

    public bindDateCellList() {
        let self = this;
        let isFirstDay = false;
        let isActive = false;
        //huytodo testdata ------------------------
        // let startDate = '20210201';
        // let endDate = '20210305';
        self.startDate = moment(self.yearMonth, 'YYYYMM').format('YYYYMMDD');
        self.endDate = moment(self.yearMonth, 'YYYYMM').add(1, 'months').add(-1, 'days').format('YYYYMMDD');
        // ------------------------
        let count = 0;
        self.dateCellList = self.dateCellList.map((el, index) => {
            el = {
                isActive: false,
                isFocused: false,
                rowNumber: el.rowNumber,
                weekDayIndex: el.weekDayIndex,
                displayData: {} as DisplayData,
                workScheduleStyle: 'padding: 0.25em 1em; font-weight: bold; border-radius: 0.25rem; display: inline-block;',
                workDesireStyle: 'padding: 0.25em 1em; font-weight: bold; border-radius: 0.25rem; display: inline-block;'
            };
            if (!isFirstDay && el.weekDayIndex == moment(self.startDate).day()) {
                isActive = true;
                isFirstDay = true;
            }
            if (isActive) {
                el.date = moment(self.startDate).add(count, 'days').format('YYYYMMDD');
                if (el.date > self.endDate) {
                    isActive = false;
                }
                el.isActive = isActive;
                el.formatedDate = moment(el.date).format('D');

                //default
                el.displayData.workScheduleTimeZone = [];
                el.displayData.workScheduleName = self.$i18n('KSUS01_14');
                // huytodo test data----------------
                if (el.formatedDate == '3') {
                    el.displayData.workScheduleAtr = WorkHolidayAtr.ONE_DAY_REST;
                    el.displayData.workScheduleName = '休日';

                    el.displayData.workDesireStatus = WorkDesireStatus.COMMUTING_HOPE;
                    el.displayData.workDesireAtr = WorkHolidayAtr.AFTERNOON_WORK;
                    el.displayData.workDesireTimeZone = [
                        {start: '12:30', end: '17:30'}
                    ];
                    el.displayData.workDesireName = '遅番';
                    el.displayData.workDesireMemo = 'memo';
                }
                if (el.formatedDate == '6') {
                    el.displayData.workScheduleAtr = WorkHolidayAtr.ONE_DAY_WORK;
                    el.displayData.workScheduleName = '出勤';
                    el.displayData.workScheduleTimeZone = [
                        {attendanceStamp: '7:00', leaveStamp: '12:00'},
                        {attendanceStamp: '12:30', leaveStamp: '17:30'}
                    ];

                    el.displayData.workDesireStatus = WorkDesireStatus.HOLIDAY_HOPE;
                    el.displayData.workDesireAtr = WorkHolidayAtr.ONE_DAY_REST;
                    el.displayData.workDesireName = '休日';
                }
                if (el.formatedDate == '9') {
                    el.displayData.workScheduleAtr = WorkHolidayAtr.MORNING_WORK;
                    el.displayData.workScheduleName = '早番';
                    el.displayData.workScheduleTimeZone = [
                        {attendanceStamp: '7:00', leaveStamp: '12:00'}
                    ];
                    el.displayData.otherStaffs = ['a', 'b'].join(', ');

                    el.displayData.workDesireStatus = WorkDesireStatus.NO_HOPE;
                }
                if (el.formatedDate == '12') {
                    
                    el.displayData.workScheduleAtr = WorkHolidayAtr.AFTERNOON_WORK;
                    el.displayData.workScheduleName = '遅番';
                    el.displayData.workScheduleTimeZone = [
                        {attendanceStamp: '12:30', leaveStamp: '17:30'}
                    ];
                    el.displayData.workDesireStatus = WorkDesireStatus.COMMUTING_HOPE;
                    el.displayData.workDesireAtr = WorkHolidayAtr.ONE_DAY_WORK;
                    el.displayData.workDesireTimeZone = [
                        {start: '7:00', end: '12:00'},
                        {start: '12:30', end: '17:30'}
                    ];
                    el.displayData.workDesireName = '出勤';
                    el.displayData.workDesireMemo = 'memo';
                }
                el.workScheduleStyle = self.setWorkScheduleStyle(el);
                el.workDesireStyle = self.setWorkDesireStyle(el);
                // ----------------
                if (el.formatedDate == '1' && el.date > self.startDate) {
                    el.formatedDate = moment(el.date).format('M/D');
                }
                count++;
            }

            return el;
        });
    }

    public setWorkScheduleStyle(dateCell: DateCell) {
        switch (dateCell.displayData.workScheduleAtr) {
            case WorkHolidayAtr.ONE_DAY_WORK:
                return dateCell.workScheduleStyle + ' background-color: lightblue;';
            case WorkHolidayAtr.MORNING_WORK:
            case WorkHolidayAtr.AFTERNOON_WORK:
                return dateCell.workScheduleStyle + ' background-color: antiquewhite;';
            case WorkHolidayAtr.ONE_DAY_REST:
            default:
                dateCell.displayData.workScheduleColor = 'ffcc88';

                return dateCell.workScheduleStyle + ' background-color: #' + dateCell.displayData.workScheduleColor + ';';    //huytodo correct way
        }
    }

    public bindDetail(dateCell: DateCell) {
        let self = this;
        self.detailCell = dateCell;
        self.detailCell.formatedLongMdwDate = moment(self.detailCell.date).format('M月D日 (ddd)');

        let command: InforOnTargetDateInput = {
            desiredSubmissionStatus: self.detailCell.displayData.workDesireStatus,
            workHolidayAtr: self.detailCell.displayData.workScheduleAtr,
            targetDate: self.detailCell.date
        };

        // self.$http.post(API.getDateDetail, command).then((data: InforOnTargetDateDto) => {
        //     self.detailCell.displayData.otherStaffs = data.businessNames.join(', ');
        //     self.detailCell.displayData.workDesireMemo = data.memo;
        //     self.detailCell.displayData.workScheduleTimeZone = data.listAttendanceDto;
        // });
        self.memo = dateCell.displayData.workDesireMemo;

        console.log(dateCell, ' detail ', dateCell.formatedDate);
    }

    public setWorkDesireStyle(dateCell: DateCell) {
        switch (dateCell.displayData.workDesireStatus) { 
            case WorkDesireStatus.HOLIDAY_HOPE:
                dateCell.displayData.workScheduleColor = 'ffcc88';

                return dateCell.workScheduleStyle + ' background-color: #' + dateCell.displayData.workScheduleColor + ';';   //huytodo correct way
            case WorkDesireStatus.COMMUTING_HOPE:                       //huytodo hoi? ve mau cho enum
                if (dateCell.displayData.workDesireName == '出勤') {
                    return dateCell.workScheduleStyle + ' background-color: lightblue;';
                } else {
                    return dateCell.workScheduleStyle + ' background-color: antiquewhite;';
                }
            case WorkDesireStatus.NO_HOPE:
            default:
                return dateCell.workScheduleStyle;
        }
    }

    //3: 年月を変更する方法
    //■next/backボタンをクリックする
    public changeYearMonth(isNext: boolean) {
        let self = this;
        self.yearMonth = isNext ? moment(self.yearMonth, 'YYYYMM').add(1, 'months').format('YYYYMM') : moment(self.yearMonth, 'YYYYMM').add(-1, 'months').format('YYYYMM');
    }

    //3: 年月を変更する方法
    //■「今月に戻る」にクリックする
    public backCurrentMonth() {
        let self = this;
        self.yearMonth = moment().format('YYYYMM');
    }

    public showDetail(isShow: boolean, dateCell?: DateCell) {
        let self = this;
        if (dateCell) {
            if (!dateCell.isActive) {
                return;
            }
            self.dateCellList.map((el) => {el.isFocused = false;});
            dateCell.isFocused = true;
        }
        if (isShow) {
            self.rowFocus = dateCell.rowNumber;
            self.dateIndexFocus = dateCell.weekDayIndex;
            self.bindDetail(dateCell);
        } else {
            self.detailCell = null;
            self.rowFocus = null;
            self.dateIndexFocus = null;
        }
        self.isDetailShow = isShow;
    }

    //2: 背景色と文字色
    //■曜日ヘッダ
    public setHeaderColor(weekDayIndex) {
        let defaultClass = 'date-header ';
        switch (weekDayIndex) {
            case WeekDay.SUN:
                return defaultClass + 'uk-bg-schedule-sunday uk-text-red';
            case WeekDay.SAT:
                return defaultClass + 'uk-bg-schedule-saturday uk-text-blue';
            default:
                return defaultClass + 'uk-bg-disable uk-text-weekdays';
        }
    }

    public setCellColor(dateCell: DateCell) {
        let cellClass = 'date-cell ';
        if (dateCell.isActive) {
            if (dateCell.isFocused) {
                cellClass += 'uk-bg-schedule-focus ';
            } else {
                cellClass += 'uk-bg-silver ';
            }
        } else {
            cellClass += 'uk-bg-white-smoke ';
        }
        if (dateCell.displayData.workScheduleAtr == WorkHolidayAtr.ONE_DAY_REST) {
            return cellClass + 'uk-text-red';
        }
        switch (dateCell.weekDayIndex) {
            case WeekDay.SUN:
                return cellClass + 'uk-text-red';
            case WeekDay.SAT:
                return cellClass + 'uk-text-blue';
            default:
                return cellClass;
        }
    }

    public errorHandler(error: any) {
        const vm = this;
        switch (error.messageId) {
            case 'Msg_2049':
                vm.$modal.error({ messageId: error.messageId, messageParams: error.parameterIds });
                break;
            default:
                vm.$modal.error({ messageId: error.messageId, messageParams: error.parameterIds });
                break;
        }
    }

    public openKSUS01B() {
        let self = this;
        self.$modal(
            'ksus01b',
            {},
            { type: 'dropback' }
        ).then((v) => {
            console.log('close ksus01b and return ', v);
        });
    }
}

const API = {
    start: 'screen/at/ksus01/a/getinforinitial',
    getDateDetail: 'screen/at/ksus01/a/getinfortargetdate',
    changeDatePeriod: 'screen/at/ksus01/a/getinfortargetperiod',
};

// for UI
export interface DateCell {
    isActive: boolean;
    weekDayIndex: WeekDay;
    rowNumber: number;
    isFocused: boolean;
    displayData: DisplayData;
    workScheduleStyle: string;
    workDesireStyle: string;
    date?: string;
    formatedDate?: string;
    formatedLongMdwDate?: string;
}

export interface DisplayData {
    workDesireDate?: string;        //huytodo to find and compare
    workDesireName?: string;
    workDesireColor?: string;
    workDesireStatus?: WorkDesireStatus;
    workDesireAtr?: WorkHolidayAtr;
    workDesireTimeZone?: Array<TimeZoneDto>;
    workDesireMemo?: string;
    workScheduleDate?: string;      //huytodo to find and compare
    workScheduleName?: string;
    workScheduleColor?: string;
    workScheduleAtr?: WorkHolidayAtr;
    workScheduleTimeZone?: Array<AttendanceDto>;
    otherStaffs?: string;
}

export interface DateHeader {
    headerName: string;
    weekDayIndex: WeekDay;
}

// enum
export enum WeekDay {
    SUN,
    MON,
    TUE,
    WED,
    THU,
    FRI,
    SAT
}

export enum WorkDesireStatus {
    NO_HOPE,
    HOLIDAY_HOPE,
    COMMUTING_HOPE
}

export enum WorkHolidayAtr {
    ONE_DAY_REST,
    MORNING_WORK,
    AFTERNOON_WORK,
    ONE_DAY_WORK
}

// api input
export interface InforOnTargetDateInput {
    desiredSubmissionStatus: number;
    workHolidayAtr: number;  //huytodo classification -> atr
    targetDate: String;
}

export interface InforOnTargetPeriodInput {
    desiredPeriodWork: PeriodCommand;
    scheduledWorkingPeriod: PeriodCommand;
}

export interface PeriodCommand {
    start: string;
    end: string;
}

// api dto
export interface InitInformation {
    publicOpAtr: boolean;
    workDesiredOpAtr: boolean;
    endDatePublicationPeriod: string;
    start: string;
    end: string;
}

export interface InforOnTargetDateDto {
    businessNames: Array<string>;
    listWorkInforAndTimeZone: Array<WorkInforAndTimeZoneByShiftMasterDto>;
    memo: string;
    listAttendanceDto: Array<AttendanceDto>;
}

export interface WorkInforAndTimeZoneByShiftMasterDto {
    wishName: string;
    timezones: TimeZoneDto;
    color: string;
}

export interface TimeZoneDto {
    start: string;
    end: string;
}

export interface AttendanceDto {
    attendanceStamp: string;
    leaveStamp: string;
}



