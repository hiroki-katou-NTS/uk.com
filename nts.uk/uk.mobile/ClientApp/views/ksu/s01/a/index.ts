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

    public yearMonth = moment().format('YYYYMM');

    public today = moment().format('YYYYMMDD');

    public dateCellList: Array<DateCell> = [];

    public dateHeaderList: Array<DateHeader> = [];

    public detailCell: DateCell = null;
    public isDetailShow: boolean = false;
    public rowFocus: number = null;
    public dateIndexFocus: number = null;

    public xDown: number = null;
    public yDown: number = null;

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
                weekDayIndex: index % 7
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
        // let startDate = '20210201';
        // let endDate = '20210305';
        let startDate = moment(self.yearMonth, 'YYYYMM').format('YYYYMMDD');
        let endDate = moment(self.yearMonth, 'YYYYMM').add(1, 'months').add(-1, 'days').format('YYYYMMDD');
        let count = 0;
        self.dateCellList = self.dateCellList.map((el, index) => {
            el = {
                isActive: false,
                isFocused: false,
                rowNumber: el.rowNumber,
                weekDayIndex: el.weekDayIndex
            };
            if (!isFirstDay && el.weekDayIndex == moment(startDate).day()) {
                isActive = true;
                isFirstDay = true;
            }
            if (isActive) {
                el.date = moment(startDate).add(count, 'days').format('YYYYMMDD');
                if (el.date > endDate) {
                    isActive = false;
                }
                el.isActive = isActive;
                el.formatedDate = moment(el.date).format('D');
                // huytodo test data----------------
                if (el.formatedDate == '3') {
                    el.workDesire = true;
                    el.workSchedule = '休日';
                }
                if (el.formatedDate == '6') {
                    el.workDesire = false;
                    el.workSchedule = '出勤';
                }
                // ----------------
                if (el.formatedDate == '1' && el.date > startDate) {
                    el.formatedDate = moment(el.date).format('M/D');
                }
                count++;
            }

            return el;
        });
    }

    public bindDetail(dateCell: DateCell) {
        let self = this;
        self.detailCell = dateCell;
        self.detailCell.formatedLongMdwDate = moment(self.detailCell.date).format('M月D日 (ddd)');

        console.log(dateCell, ' detail ', dateCell.formatedDate);
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
        //huytodo focus problem
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
        //huytodo   休日
        switch (dateCell.weekDayIndex) {
            case WeekDay.SUN:
                return cellClass + 'uk-text-red';
            case WeekDay.SAT:
                return cellClass + 'uk-text-blue';
            default:
                return cellClass;
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
    // start: 'at/request/application/holidaywork/mobile/start',
};

export interface DateCell {
    isActive: boolean;
    weekDayIndex: WeekDay;
    rowNumber: number;
    isFocused: boolean;
    date?: string;
    formatedDate?: string;
    formatedLongMdwDate?: string;
    workDesire?: boolean;
    workSchedule?: string;
}

export interface DateHeader {
    headerName: string;
    weekDayIndex: WeekDay;
}

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
    NONE,
    BREAK,
    WORK
}