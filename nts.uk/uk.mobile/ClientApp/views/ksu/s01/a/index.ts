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

    public yearMonthOldVal: string = moment().format('YYYYMM');
    public yearMonth: string = moment().format('YYYYMM');
    public startDate: string = '';
    public endDate: string = '';
    public publicOpAtr: boolean = false;
    public workDesiredOpAtr: boolean = false;
    public endDatePublicationPeriod: string = null;

    public today = moment().format('YYYY/MM/DD');

    public dateCellList: Array<DateCell> = [];
    public listWorkSchedule: Array<WorkScheduleDto> = [];
    public listDesiredSubmissionStatusByDate: Array<DesiredSubmissionStatusByDate> = [];

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

        let diffMonth = moment(self.yearMonth, 'YYYY/MM/DD').diff(moment(self.yearMonthOldVal, 'YYYY/MM/DD'), 'months');

        self.startDate = moment(self.startDate, 'YYYY/MM/DD').add(diffMonth, 'months').format('YYYY/MM/DD');
        self.endDate = moment(self.endDate, 'YYYY/MM/DD').add(diffMonth, 'months').format('YYYY/MM/DD');
        self.yearMonthOldVal = self.yearMonth;
        
        self.showDetail(false);
        self.getInforOnTargetPeriod();
    }

    public created() {
        let self = this;
        self.initData();
        self.loadData();
    }

    public mounted() {
        
    }

    public fakeDataInit() {
        return new Promise((resolve, reject) => {
            let data: InitInformation = {
                publicOpAtr: true,
                workDesiredOpAtr: true,
                endDatePublicationPeriod: '2021/03/09',
                start: '2021/02/22',
                end: '2021/03/21'
            };
            resolve(data);
        });
    }

    public fakeDataDatePeriod(command: InforOnTargetPeriodInput) {
        let self = this;

        return new Promise((resolve, reject) => {
            let desiredPeriodWork: PeriodCommand = {
                start: '',
                end: ''
            };
            if (self.workDesiredOpAtr) {
                desiredPeriodWork.start = self.startDate;
                desiredPeriodWork.end = self.endDate;
            }
    
            let scheduledWorkingPeriod: PeriodCommand = {
                start: '',
                end: ''
            };
            if (self.publicOpAtr) {
                let diffStart = moment(self.endDatePublicationPeriod, 'YYYY/MM/DD').diff(moment(self.startDate, 'YYYY/MM/DD'), 'days');
                if (diffStart >= 0) {
                    scheduledWorkingPeriod.start = self.startDate;
                    let diffEnd = moment(self.endDate, 'YYYY/MM/DD').diff(moment(self.endDatePublicationPeriod, 'YYYY/MM/DD'), 'days');
                    if (diffEnd >= 0) {
                        scheduledWorkingPeriod.end = self.endDatePublicationPeriod;
                    } else {
                        scheduledWorkingPeriod.end = self.endDate;
                    }
                }
            } else {
                scheduledWorkingPeriod.start = self.startDate;
                scheduledWorkingPeriod.end = self.endDate;
            }

            let scheduleDiff = moment(scheduledWorkingPeriod.end, 'YYYY/MM/DD').diff(moment(scheduledWorkingPeriod.start, 'YYYY/MM/DD'), 'days');
            let desireDiff = moment(desiredPeriodWork.end, 'YYYY/MM/DD').diff(moment(desiredPeriodWork.start, 'YYYY/MM/DD'), 'days');

            let data: InforOnTargetPeriodDto = {
                listWorkSchedule: [],
                listDesiredSubmissionStatusByDate: []
            };

            let restScheRandom = Math.floor(Math.random() * scheduleDiff);
            let earlyScheRandom = Math.floor(Math.random() * scheduleDiff);
            let lateScheRandom = Math.floor(Math.random() * scheduleDiff);
            let workScheRandom = Math.floor(Math.random() * scheduleDiff);

            for (let index = 0; index <= scheduleDiff; index++) {
                let workSchedule: WorkScheduleDto = {
                    ymd: moment(self.startDate, 'YYYY/MM/DD').add(index, 'days').format('YYYY/MM/DD'),
                    shiftMaster: {
                        shiftMasterName: '休日', 
                        color: 'fabf8f'
                    },
                    workAtr: 0,
                    listAttendaceDto: []
                };
                if (index % restScheRandom == 0) {
                    workSchedule.shiftMaster = {
                        shiftMasterName: '休日', 
                        color: 'fabf8f'
                    };
                    workSchedule.workAtr = 0;
                }
                if (index % earlyScheRandom == 0) {
                    workSchedule.shiftMaster = {
                        shiftMasterName: '早番', 
                        color: 'faebd7'
                    };
                    workSchedule.workAtr = 1;
                    workSchedule.listAttendaceDto = [
                        {
                            attendanceStamp: '8:00',
                            leaveStamp: '12:00'
                        }
                    ];
                }
                if (index % lateScheRandom == 0) {
                    workSchedule.shiftMaster = {
                        shiftMasterName: '遅番', 
                        color: 'faebd7'
                    };
                    workSchedule.workAtr = 2;
                    workSchedule.listAttendaceDto = [
                        {
                            attendanceStamp: '13:30',
                            leaveStamp: '17:30'
                        }
                    ];
                }
                if (index % workScheRandom == 0) {
                    workSchedule.shiftMaster = {
                        shiftMasterName: '出勤', 
                        color: 'add8e6'
                    };
                    workSchedule.workAtr = 3;
                    workSchedule.listAttendaceDto = [
                        {
                            attendanceStamp: '8:00',
                            leaveStamp: '12:00'
                        },
                        {
                            attendanceStamp: '13:30',
                            leaveStamp: '17:30'
                        }
                    ];
                }
                if (Math.random() <= 0.2) {
                    workSchedule.shiftMaster = {
                        shiftMasterName: '', 
                        color: ''
                    };
                    workSchedule.workAtr = null;
                }
                data.listWorkSchedule.push(workSchedule);
            }

            let restDesireRandom = Math.floor(Math.random() * desireDiff);
            let workDesireRandom = Math.floor(Math.random() * desireDiff);
            for (let index = 0; index <= desireDiff; index++) {
                let desiredSubmissionStatusByDate: DesiredSubmissionStatusByDate = {
                    date: moment(self.startDate, 'YYYY/MM/DD').add(index, 'days').format('YYYY/MM/DD'),
                    status: index % restDesireRandom == 0 ? 1 : (index % workDesireRandom == 0 ? 2 : 0)
                };
                data.listDesiredSubmissionStatusByDate.push(desiredSubmissionStatusByDate);
            }
            resolve(data);
        });
    }

    public fakeDataDateTarget(command: InforOnTargetDateInput) {
        return new Promise((resolve, reject) => {
            let restDesire: WorkInforAndTimeZoneByShiftMasterDto = {
                wishName: '休日',
                timezones: [],
                workAtr: 0,
                color: 'fabf8f',
            };
            let earlyHalfDesire: WorkInforAndTimeZoneByShiftMasterDto = {
                wishName: '早番',
                timezones: [
                    {start: '8:00', end: '12:00'}
                ],
                workAtr: 1,
                color: 'faebd7',
            };
            let lateHalfDesire: WorkInforAndTimeZoneByShiftMasterDto = {
                wishName: '遅番',
                timezones: [
                    {start: '13:30', end: '17:30'}
                ],
                workAtr: 2,
                color: 'faebd7',
            };
            let workDesire: WorkInforAndTimeZoneByShiftMasterDto = {
                wishName: '出勤',
                timezones: [
                    {start: '8:00', end: '12:00'},
                    {start: '13:30', end: '17:30'}
                ],
                workAtr: 3,
                color: 'add8e6',
            };
            let data: InforOnTargetDateDto = {
                businessNames: ['test1', 'test2', 'test3', '名前例1', '名前例2', '名前例3', '名前例4', '名前例5', 'test1', 'test2', 'test3', '名前例1', '名前例2', '名前例3', '名前例4', '名前例5'],
                listWorkInforAndTimeZone: [restDesire, earlyHalfDesire, lateHalfDesire, workDesire],
                memo: 'demo memo',
                listAttendanceDto: []
            };
            if (command.desiredSubmissionStatus == 1) {
                data.memo = 'rest demo';
                data.listWorkInforAndTimeZone = [restDesire];
            } else if (command.desiredSubmissionStatus == 2) {
                data.memo = 'work demo';
                data.listWorkInforAndTimeZone = [earlyHalfDesire, lateHalfDesire, workDesire];
            } else {
                data.memo = 'empty demo';
                data.listWorkInforAndTimeZone = [];
            }
            if (command.workHolidayAtr == WorkHolidayAtr.ONE_DAY_REST) {
                data.listAttendanceDto = [];
            }
            if (command.workHolidayAtr == WorkHolidayAtr.MORNING_WORK) {
                data.listAttendanceDto = [
                    {
                        attendanceStamp: '8:00',
                        leaveStamp: '12:00'
                    }
                ];
            }
            if (command.workHolidayAtr == WorkHolidayAtr.AFTERNOON_WORK) {
                data.listAttendanceDto = [
                    {
                        attendanceStamp: '13:30',
                        leaveStamp: '17:30'
                    }
                ];
            }
            if (command.workHolidayAtr == WorkHolidayAtr.ONE_DAY_WORK) {
                data.listAttendanceDto = [
                    {
                        attendanceStamp: '8:00',
                        leaveStamp: '12:00'
                    },
                    {
                        attendanceStamp: '13:30',
                        leaveStamp: '17:30'
                    }
                ];
            }
            resolve(data);
        });
    }

    public initData() {
        let self = this;
        self.$mask('show');
        // self.fakeDataInit().then((data: InitInformation) => {
        self.$http.post('at', API.start).then((res: any) => {
            let data: InitInformation = res.data;

            self.startDate = data.start;
            self.endDate = data.end;
            self.endDatePublicationPeriod = data.endDatePublicationPeriod;
            self.publicOpAtr = data.publicOpAtr;
            self.workDesiredOpAtr = data.workDesiredOpAtr;

            self.getInforOnTargetPeriod();
        }).catch((error: any) => {
            self.errorHandler(error);
        }).then(() => self.$mask('hide'));

        self.createDateHeaderList();
        self.createDateCellList();
    }

    public getInforOnTargetPeriod() {
        let self = this;

        let desiredPeriodWork: PeriodCommand = {
            start: '',
            end: ''
        };
        if (self.workDesiredOpAtr) {
            desiredPeriodWork.start = self.startDate;
            desiredPeriodWork.end = self.endDate;
        }

        let scheduledWorkingPeriod: PeriodCommand = {
            start: '',
            end: ''
        };
        if (self.publicOpAtr) {
            let diffStart = moment(self.endDatePublicationPeriod, 'YYYY/MM/DD').diff(moment(self.startDate, 'YYYY/MM/DD'), 'days');
            if (diffStart >= 0) {
                scheduledWorkingPeriod.start = self.startDate;
                let diffEnd = moment(self.endDate, 'YYYY/MM/DD').diff(moment(self.endDatePublicationPeriod, 'YYYY/MM/DD'), 'days');
                if (diffEnd >= 0) {
                    scheduledWorkingPeriod.end = self.endDatePublicationPeriod;
                } else {
                    scheduledWorkingPeriod.end = self.endDate;
                }
            }
        } else {
            scheduledWorkingPeriod.start = self.startDate;
            scheduledWorkingPeriod.end = self.endDate;
        }

        let command: InforOnTargetPeriodInput = {
            desiredPeriodWork,
            scheduledWorkingPeriod
        };
        console.log(command, 'command');
        self.$mask('show');
        // self.fakeDataDatePeriod(command).then((data: InforOnTargetPeriodDto) => {
        self.$http.post('at', API.changeDatePeriod, command).then((res: any) => {
            let data: InforOnTargetPeriodDto = res.data;
            console.log(data, 'changeDatePeriod');
            self.listWorkSchedule = data.listWorkSchedule;
            self.listDesiredSubmissionStatusByDate = data.listDesiredSubmissionStatusByDate;
            self.bindDateCellList();
        }).catch((error: any) => {
            self.errorHandler(error);
        }).then(() => self.$mask('hide'));
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
                workScheduleStyle: 'padding: 0.25em 0.4em; font-weight: bold; border-radius: 0.25rem; display: inline-block;'
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

        if (Math.abs(xDiff) > 50 && Math.abs(xDiff) > Math.abs(yDiff)) {
            if (xDiff > 0) {    // right-left swipe
                self.changeWeek(true);
            } else {    //left-right swipe
                self.changeWeek(false);
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

        let count = 0;
        self.dateCellList = self.dateCellList.map((el, index) => {
            el = {
                isActive: false,
                isFocused: false,
                rowNumber: el.rowNumber,
                weekDayIndex: el.weekDayIndex,
                displayData: {} as DisplayData,
                workScheduleStyle: 'padding: 0.25em 0.4em; font-weight: bold; border-radius: 0.25rem; display: inline-block;'
            };
            if (!isFirstDay && el.weekDayIndex == moment(self.startDate).day()) {
                isActive = true;
                isFirstDay = true;
            }
            if (isActive) {
                el.date = moment(self.startDate).add(count, 'days').format('YYYY/MM/DD');
                if (el.date > self.endDate) {
                    isActive = false;
                    el.date = null;
                }
                if (el.date) {
                    el.isActive = self.publicOpAtr ? (isActive ? el.date <= self.endDatePublicationPeriod : isActive) : isActive;
                    el.formatedDate = moment(el.date).format('D');

                    //default
                    el.displayData.workScheduleTimeZone = [];
                    el.displayData.listWorkDesire = [];

                    let desiredSubmissionStatusByDate = _.find(self.listDesiredSubmissionStatusByDate, (desiredSubmissionStatusByDate) => {
                        return desiredSubmissionStatusByDate.date == el.date;
                    });
                    if (desiredSubmissionStatusByDate) {
                        el.displayData.workDesireDate = desiredSubmissionStatusByDate.date;
                        el.displayData.workDesireStatus = desiredSubmissionStatusByDate.status;
                    }
                    let workSchedule = _.find(self.listWorkSchedule, (workSchedule) => {
                        return workSchedule.ymd == el.date;
                    });
                    if (workSchedule) {
                        el.displayData.workScheduleDate = workSchedule.ymd;
                        el.displayData.workScheduleAtr = workSchedule.workAtr;
                        el.displayData.workScheduleTimeZone = workSchedule.listAttendaceDto;
                        el.displayData.workScheduleName = workSchedule.shiftMaster.shiftMasterName;
                        el.displayData.workScheduleColor = workSchedule.shiftMaster.color;
                    }
                    el.workScheduleStyle = self.setWorkScheduleStyle(el);
                    // el.workDesireStyle = self.setWorkDesireStyle(el);

                    if (el.formatedDate == '1' && el.date > self.startDate) {
                        el.formatedDate = moment(el.date).format('M/D');
                    }
                    count++;
                }
            }

            return el;
        });
    }

    public setWorkScheduleStyle(dateCell: DateCell) {
        return dateCell.workScheduleStyle + ' background-color: #' + dateCell.displayData.workScheduleColor + ';';
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

        console.log(command, 'getDateDetail command');
        self.$mask('show');
        // self.fakeDataDateTarget(command).then((data: InforOnTargetDateDto) => {
        self.$http.post('at', API.getDateDetail, command).then((res: any) => {
            let data: InforOnTargetDateDto = res.data;
            console.log(data, 'getDateDetail');
            self.detailCell.displayData.otherStaffs = data.businessNames.join(', ');
            self.detailCell.displayData.workDesireMemo = data.memo;
            self.detailCell.displayData.workScheduleTimeZone = data.listAttendanceDto;
            self.detailCell.displayData.listWorkDesire = [];
            data.listWorkInforAndTimeZone.map((el) => {
                let detailWorkDesire: DetailWorkDesire = {};

                detailWorkDesire.workDesireName = el.wishName;
                detailWorkDesire.workDesireColor = el.color;
                detailWorkDesire.workDesireAtr = el.workAtr;
                // 3: 勤務予定や勤務希望は休日の場合  ※1
                if (detailWorkDesire.workDesireAtr == WorkHolidayAtr.ONE_DAY_REST) {
                    detailWorkDesire.workDesireName = self.$i18n('KSUS01_14');
                }
                detailWorkDesire.workDesireTimeZone = el.timezones;
                detailWorkDesire.workDesireStyle = self.setWorkDesireStyle(detailWorkDesire);

                self.detailCell.displayData.listWorkDesire.push(detailWorkDesire);
            });

            self.memo = self.detailCell.displayData.workDesireMemo;
        }).catch((error: any) => {
            self.errorHandler(error);
        }).then(() => self.$mask('hide'));
        
        console.log(dateCell, ' detail ', dateCell.formatedDate);
    }

    public setWorkDesireStyle(detailWorkDesire: DetailWorkDesire) {
        let defaultStyle = 'padding: 0.25em 1em; font-weight: bold; border-radius: 0.25rem; display: inline-block;';

        return defaultStyle + ' background-color: #' + detailWorkDesire.workDesireColor + ';';
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
    
    date?: string;
    formatedDate?: string;
    formatedLongMdwDate?: string;
}

export interface DisplayData {
    workDesireDate?: string;
    workDesireStatus?: WorkDesireStatus;
    workDesireMemo?: string;
    listWorkDesire?: Array<DetailWorkDesire>;

    workScheduleDate?: string;
    workScheduleName?: string;
    workScheduleColor?: string;
    workScheduleAtr?: WorkHolidayAtr;
    workScheduleTimeZone?: Array<AttendanceDto>;
    otherStaffs?: string;
}

export interface DetailWorkDesire {
    workDesireName?: string;
    workDesireColor?: string;
    workDesireAtr?: WorkHolidayAtr;
    workDesireTimeZone?: Array<TimeZoneDto>;
    workDesireStyle?: string;
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
    workHolidayAtr: number;
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

export interface InforOnTargetPeriodDto {
    listWorkSchedule: Array<WorkScheduleDto>;
    listDesiredSubmissionStatusByDate: Array<DesiredSubmissionStatusByDate>;
}

export interface WorkScheduleDto {
    ymd: string;
    shiftMaster: ShiftMasterDto;
    workAtr: number;
    listAttendaceDto: Array<AttendanceDto>;
}

export interface ShiftMasterDto {
    shiftMasterName: string;
    color: string;
}

export interface DesiredSubmissionStatusByDate {
    date: string;
    status: number;
}

export interface InforOnTargetDateDto {
    businessNames: Array<string>;
    listWorkInforAndTimeZone: Array<WorkInforAndTimeZoneByShiftMasterDto>;
    memo: string;
    listAttendanceDto: Array<AttendanceDto>;
}

export interface WorkInforAndTimeZoneByShiftMasterDto {
    wishName: string;
    timezones: Array<TimeZoneDto>;
    workAtr: WorkHolidayAtr;
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



