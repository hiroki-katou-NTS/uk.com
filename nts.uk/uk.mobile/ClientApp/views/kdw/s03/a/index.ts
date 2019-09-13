
import { _, Vue, moment } from '@app/provider';
import { component, Watch, Prop } from '@app/core/component';
import { FixTableComponent } from '@app/components/fix-table';
import { TimeDuration } from '@app/utils/time';
import { storage } from '@app/utils';
import { KdwS03BComponent } from 'views/kdw/s03/b';
import { KdwS03AMenuComponent } from 'views/kdw/s03/a/menu';

@component({
    name: 'kdws03a',
    route: '/kdw/s03/a',
    style: require('./style.scss'),
    template: require('./index.vue'),
    resource: require('./resources.json'),
    constraints: [],
    components: {
        'fix-table': FixTableComponent,
        'kdws03b': KdwS03BComponent,
        'kdws03amenu': KdwS03AMenuComponent
    },
    validations: {
        yearMonth: {
            required: true
        },
        selectedDate: {
            required: true
        },
    }
})
export class Kdws03AComponent extends Vue {
    @Prop({ default: 0 })
    public screenMode: number;

    public title: string = 'Kdws03A';
    public displayFormat: any = '0';
    public lstDataSourceLoad: Array<any> = [];
    public lstDataHeader: Array<any> = [];
    public optionalHeader: Array<any> = [];
    public cellStates: Array<any> = [];
    public fixHeaders: Array<any> = [];
    public showPrincipal: boolean = true;
    public showSupervisor: boolean = true;
    public hasLstHeader: boolean = true;
    public displayDataLst: Array<any> = [];
    public displayHeaderLst: Array<any> = [];
    public displaySumLst: any = {};
    public displayDataLstEx: Array<any> = [];
    public lstCellDisByLock: Array<any> = [];
    public lstEmployee: Array<any> = [];
    public yearMonth: number = 0;
    public actualTimeOptionDisp: Array<any> = [];
    public timePeriodAllInfo: any = null;
    public actualTimeSelectedCode: number = 0;
    public selectedEmployee: string = '';
    public selectedDate: Date = new Date();
    public resetTable: number = 0;
    public previousState: string = 'button-deactive';
    public nextState: string = 'button-deactive';
    public itemStart: number = 0;
    public itemEnd: number = 0;
    // backup item to recover when error
    public actualTimeSelectedCodeTemp: number = 0;
    public yearMonthTemp: number = 0;
    public selectedEmployeeTemp: string = '';
    public dPCorrectionMenuDto: any = null;
    public lstAttendanceItem: Array<any> = [];

    @Watch('yearMonth')
    public changeYearMonth(value: any, valueOld: any) {
        let self = this;
        this.yearMonthTemp = valueOld;
        this.selectedEmployeeTemp = this.selectedEmployee;
        this.actualTimeSelectedCodeTemp = this.actualTimeSelectedCode;

        self.$http.post('at', servicePath.genDate, { yearMonth: value }).then((result: { data: any }) => {
            let data = result.data;
            self.timePeriodAllInfo = data;
            _.remove(self.actualTimeOptionDisp);
            if (data.lstRange && data.lstRange.length > 0) {
                for (let i = 0; i < data.lstRange.length; i++) {
                    let startDate = data.lstRange[i].startDate,
                        endDate = data.lstRange[i].endDate;
                    self.actualTimeOptionDisp.push({ code: i, name: (i + 1) + ': ' + this.$dt(startDate, 'M/D') + '～' + this.$dt(endDate, 'M/D') });
                }
            }
        });
    }

    @Watch('selectedEmployee')
    public changeEmployee(value: any, valueOld: any) {
        if (_.isNil(valueOld) || '' == valueOld) {
            return;
        }
        this.selectedEmployeeTemp = valueOld;
        this.yearMonthTemp = this.yearMonth;
        this.actualTimeSelectedCodeTemp = this.actualTimeSelectedCode;
        this.startPage();
    }

    @Watch('dateRanger', { deep: true })
    public changeDateRange(value: any, valueOld: any) {
        if (_.isNil(valueOld) || _.isEqual(value, valueOld)) {
            return;
        }
        this.startPage();
    }

    @Watch('actualTimeSelectedCode')
    public changeTimeCode(value: any, valueOld: any) {
        this.actualTimeSelectedCodeTemp = valueOld;
        this.selectedEmployeeTemp = this.selectedEmployee;
        this.yearMonthTemp = this.yearMonth;
    }

    @Watch('selectedDate')
    public changeDate(value: any, valueOld: any) {
        if (_.isNil(valueOld)) {
            return;
        }
        this.startPage();
    }

    get dateRanger() {
        let self = this;
        if (!_.isNil(self.timePeriodAllInfo)) {
            for (let i = 0; i < self.timePeriodAllInfo.lstRange.length; i++) {
                if (self.actualTimeSelectedCode == i) {
                    return ({ startDate: self.timePeriodAllInfo.lstRange[i].startDate, endDate: self.timePeriodAllInfo.lstRange[i].endDate });
                }
            }
        }
    }

    public created() {
        if (this.$route.query.displayformat == '0' || this.$route.query.displayformat == '1') {
            this.displayFormat = this.$route.query.displayformat;
        }

        if (this.screenMode == 0) {
            this.pgName = this.displayFormat == '0' ? 'name1' : 'name2';
        } else {
            this.pgName = this.displayFormat == '0' ? 'name3' : 'name4';
        }

        this.$auth.user.then((user: null | { employeeId: string }) => {
            this.selectedEmployee = user.employeeId;
            this.startPage();
        });

    }

    public mounted() {
        this.$mask('show', { message: true });
    }
    public updated() {
        let styleTag = null;
        let styleTagAr: any = [];
        if (this.displayFormat == '1') {
            styleTag = document.querySelector('.table-body') as HTMLStyleElement;
            styleTag.style.height = '100%';
        }
        styleTagAr = document.querySelectorAll('.btn-sm');
        _.forEach(styleTagAr, (x) => x.style.fontSize = '10px');
    }

    public startPage() {
        let self = this;
        self.$mask('show', { message: true });

        let param = {
            changePeriodAtr: false,
            screenMode: self.screenMode,
            errorRefStartAtr: false,
            initDisplayDate: null,
            employeeID: self.selectedEmployee,
            objectDateRange: self.displayFormat == '0' ?
                (!_.isNil(self.dateRanger) ? { startDate: self.$dt.fromString(self.dateRanger.startDate), endDate: self.$dt.fromString(self.dateRanger.endDate) } : null) :
                { startDate: self.selectedDate, endDate: self.selectedDate },
            lstEmployee: [],
            initClock: null,
            displayFormat: this.displayFormat,
            displayDateRange: null,
            transitionDesScreen: null,
        };

        self.$http.post('at', servicePath.initMOB, param).then(async (result: { data: any }) => {
            let dataInit = result.data;
            if (dataInit.lstEmployee == undefined || dataInit.lstEmployee.length == 0 || dataInit.errorInfomation != 0) {
                let messageId = 'Msg_1342';
                if (dataInit.errorInfomation == DCErrorInfomation.APPROVAL_NOT_EMP) {
                    messageId = 'Msg_916';
                } else if (dataInit.errorInfomation == DCErrorInfomation.ITEM_HIDE_ALL) {
                    messageId = 'Msg_1452';
                } else if (dataInit.errorInfomation == DCErrorInfomation.NOT_EMP_IN_HIST) {
                    messageId = 'Msg_1543';
                }
                this.$modal.error({ messageId }).then(() => {
                    this.$goto('ccg008a');
                });
            } else if (!_.isEmpty(dataInit.errors)) {
                for (let i = 0; i < dataInit.errors.length; i++) {
                    await new Promise((next) => {
                        this.$modal.error(dataInit.errors[i].message).then(() => {
                            if (i == dataInit.errors.length - 1) {
                                this.$mask('hide');
                                this.actualTimeSelectedCode = this.actualTimeSelectedCodeTemp;
                                this.yearMonth = this.yearMonthTemp;
                                this.selectedEmployee = this.selectedEmployeeTemp;
                            }
                            next();
                        });
                    });
                }
            } else {
                await new Promise((next) => {
                    this.processMapData(result.data);
                    next();
                });
                storage.local.setItem('dailyCorrectionState', {
                    screenMode: self.screenMode,
                    displayFormat: self.displayFormat,
                    selectedEmployee: self.selectedEmployee,
                    lstEmployee: self.lstEmployee,
                    dateRange: self.displayFormat == '0' ?
                        (!_.isNil(self.dateRanger) ? { startDate: self.$dt.fromString(self.dateRanger.startDate), endDate: self.$dt.fromString(self.dateRanger.endDate) } : null) :
                        { startDate: self.selectedDate, endDate: self.selectedDate },
                    cellDataLst: this.displayDataLst,
                    headerLst: this.displayHeaderLst,
                    timePeriodAllInfo: self.timePeriodAllInfo
                });
                this.$mask('hide');
            }
        }).catch((res: any) => {
            if (res.messageId == 'Msg_672') {
                this.$modal.info({ messageId: res.messageId });
            } else {
                if (res.messageId != undefined) {
                    this.$modal.error(res.messageId == 'Msg_1430' ? res.message : { messageId: res.messageId }).then(() => {
                        this.$goto('ccg008a');
                    });

                } else if ((res.messageId == undefined && res.errors.length > 0)) {
                    //nts.uk.ui.dialog.bundledErrors({ errors: res.errors }).then(function () {
                    //nts.uk.request.jumpToTopPage();
                    //});

                }
            }
        });
    }

    public processMapData(data: any) {
        let self = this;
        self.dPCorrectionMenuDto = data.dpcorrectionMenuDto;
        self.lstDataSourceLoad = self.formatDate(data.lstData);
        self.optionalHeader = data.lstControlDisplayItem.lstHeader;
        self.lstAttendanceItem = data.lstControlDisplayItem.lstAttendanceItem;
        self.cellStates = data.lstCellState;

        if (_.isNil(self.timePeriodAllInfo) && self.displayFormat == 0) {
            self.timePeriodAllInfo = data.periodInfo;
            self.yearMonth = data.periodInfo.yearMonth;
        }

        self.fixHeaders = data.lstFixedHeader;
        self.showPrincipal = data.showPrincipal;
        self.showSupervisor = data.showSupervisor;
        if (data.lstControlDisplayItem.lstHeader.length == 0) {
            self.hasLstHeader = false;
        }

        if (self.lstEmployee.length == 0) {
            self.lstEmployee = _.orderBy(data.lstEmployee, ['code'], ['asc']);
        }

        _.remove(self.displayDataLst);
        _.remove(self.displayHeaderLst);
        _.remove(self.displaySumLst);

        // create header data
        let headers = (_.filter(self.optionalHeader, (o) => o.hidden == false));
        headers.forEach((header: any) => {
            let setting = _.find(data.lstControlDisplayItem.columnSettings, (x) => x.columnKey == header.key);
            self.displayHeaderLst.push({
                key: header.key,
                headerText: header.headerText,
                color: header.color,
                constraint: header.constraint,
                typeFormat: setting.typeFormat
            });
        });

        // create body data
        self.lstDataSourceLoad.forEach((rowDataSrc: any) => {
            let rowData = [];
            headers.forEach((header: any) => {
                if (_.has(rowDataSrc, header.key)) {
                    rowData.push({
                        key: header.key,
                        value: rowDataSrc[header.key],
                    });
                } else {
                    rowData.push({
                        key: header.key,
                        groupKey: header.group[1].key,
                        value: rowDataSrc[header.group[1].key]
                    });
                }
            });

            if (self.displayFormat == '0') {
                self.displayDataLst.push({
                    rowData,
                    date: rowDataSrc.date,
                    dateDetail: rowDataSrc.dateDetail, id: rowDataSrc.id,
                    ERAL: rowDataSrc.error
                });
            } else {
                self.displayDataLst.push({
                    rowData,
                    employeeName: rowDataSrc.employeeName,
                    employeeNameDis: this.countHalf(rowDataSrc.employeeName) > 10 ? rowDataSrc.employeeName.substring(0, 5) + '...' : rowDataSrc.employeeName,
                    employeeId: rowDataSrc.employeeId,
                    id: rowDataSrc.id,
                    ERAL: rowDataSrc.error
                });
            }
        });

        // set cell color
        self.displayDataLst.forEach((row: any) => {
            let states = _.filter(self.cellStates, (x) => x.rowId == row.id);
            row.rowData.forEach((cell: any) => {
                if (!_.isNil(_.find(states, (x) => x.columnKey == cell.key || x.columnKey == cell.groupKey))) {
                    cell.class = '';
                    let classArray = _.find(states, (x) => x.columnKey == cell.key || x.columnKey == cell.groupKey).state;
                    _.forEach(classArray, (x) => cell.class = cell.class + x);
                }
            });
            if (!_.isNil(_.find(states, (x) => x.columnKey == 'date'))) {
                row.dateColor = '';
                let classArray = _.find(states, (x) => x.columnKey == 'date').state;
                _.forEach(classArray, (x) => row.dateColor = row.dateColor + ' ' + x);
            }
        });

        // fill blank row when no data
        if (self.displayFormat == 0) {
            if (self.lstDataSourceLoad.length < 7) {
                for (let i = 1; i <= (7 - self.lstDataSourceLoad.length); i++) {
                    let rowData = [];
                    headers.forEach((header: any) => {
                        rowData.push({ key: header.key, value: '' });
                    });
                    self.displayDataLst.push({ rowData, date: '', id: '' });
                }
            }
        } else {
            if (self.displayDataLst.length <= 7) {
                if (self.displayDataLst.length == 0) {
                    self.itemStart = 0;
                    self.itemEnd = 0;
                    self.previousState = 'button-deactive';
                    self.nextState = 'button-deactive';
                } else {
                    self.itemStart = 1;
                    self.itemEnd = self.displayDataLst.length;
                    self.previousState = 'button-deactive';
                    self.nextState = 'button-deactive';
                }
                self.displayDataLstEx = self.displayDataLst.slice();
                for (let i = 1; i <= (7 - self.displayDataLst.length); i++) {
                    let rowData = [];
                    headers.forEach((header: any) => {
                        rowData.push({ key: header.key, value: '' });
                    });
                    self.displayDataLstEx.push({ rowData, employeeName: '', id: '', employeeNameDis: '' });
                }
            } else {
                self.displayDataLstEx = _.slice(self.displayDataLst, 0, 7);
                self.itemStart = 1;
                self.itemEnd = 7;
                self.previousState = 'button-deactive';
                self.nextState = 'button-active';
            }
        }

        // create sum row
        let sumTimeKey = _.map((_.filter(data.lstControlDisplayItem.columnSettings, (o) => o.typeFormat == 5)), (x) => x.columnKey);
        let sumNumKey = _.map((_.filter(data.lstControlDisplayItem.columnSettings, (o) => o.typeFormat == 2 || o.typeFormat == 3)), (x) => x.columnKey);
        headers.forEach((header: any) => {
            if (_.includes(sumTimeKey, header.key)) {
                self.displaySumLst[header.key] = '0:00';
            } else if (_.includes(sumNumKey, header.key)) {
                self.displaySumLst[header.key] = 0;
            } else {
                self.displaySumLst[header.key] = '';
            }

        });
        self.displayDataLst.forEach((row: any) => {
            row.rowData.forEach((cell: any) => {
                if (!_.isNil(cell.value) && '' != cell.value) {
                    if (_.includes(sumTimeKey, cell.key)) {
                        self.displaySumLst[cell.key] = this.$dt.timedr((new TimeDuration(self.displaySumLst[cell.key])).toNumber() + (new TimeDuration(cell.value)).toNumber());
                    } else if (_.includes(sumNumKey, cell.key)) {
                        self.displaySumLst[cell.key] = self.displaySumLst[cell.key] + Number(cell.value);
                    }
                }
            });
        });

        self.resetTable++;
    }

    public formatDate(lstData: any) {
        let data = lstData.map((data) => {
            let object = {
                id: '_' + data.id,
                state: data.state,
                error: data.error,
                date: this.$dt(data.date, 'Do(dd)'),
                sign: data.sign,
                approval: data.approval,
                employeeId: data.employeeId,
                employeeCode: data.employeeCode,
                employeeName: data.employeeName,
                workplaceId: data.workplaceId,
                employmentCode: data.employmentCode,
                dateDetail: this.$dt(data.date, 'YYYY/MM/DD'),
                typeGroup: data.typeGroup
            };
            _.each(data.cellDatas, function (item) {
                object[item.columnKey] = item.value;
            });

            return object;
        });

        return data;
    }

    public openEdit(id: any) {
        if (id == '') {
            return;
        }
        let self = this;
        let param1 = _.find(this.displayDataLst, (x) => x.id == id);
        let param2 = this.displayHeaderLst;
        let param3 = this.lstAttendanceItem;
        let employeeName = '';
        let date = new Date();
        if (self.displayFormat == 0) {
            employeeName = _.find(self.lstEmployee, (emp) => emp.id == self.selectedEmployee).businessName;
            date = new Date(param1.dateDetail);
        } else {
            employeeName = param1.employeeName;
            date = self.selectedDate;
        }

        self.$modal('kdws03b', { 'employeeName': employeeName, 'date': date, data :param1.rowData, contentType: param2 }, { type : 'dropback' } )
        .then((v) => {

        });
    }

    public openMenu() {

        let allConfirmButtonDis = false;
        this.$auth.user.then((user: null | { employeeId: string }) => {
            if (this.selectedEmployee == user.employeeId) {
                allConfirmButtonDis = true;
            }
            this.$modal(
                'kdws03amenu',
                {
                    displayFormat: this.displayFormat,
                    restReferButtonDis: this.dPCorrectionMenuDto.restReferButtonDis,
                    monthActualReferButtonDis: this.dPCorrectionMenuDto.monthActualReferButtonDis,
                    timeExcessReferButtonDis: this.dPCorrectionMenuDto.timeExcessReferButtonDis,
                    selectedEmployee: this.selectedEmployee,
                    allConfirmButtonDis
                }, 
                { 
                    type: 'dropback', 
                    title: null 
                }).then((v) => {
    
                });
        });
    }

    public nextPage() {
        if (this.nextState == 'button-deactive') {
            return;
        }
        this.itemStart = this.itemStart + 7;
        if (this.displayDataLst.length - this.itemEnd > 7) {
            this.itemEnd = this.itemEnd + 7;
            this.nextState = 'button-active';
            this.displayDataLstEx = _.slice(this.displayDataLst, this.itemStart - 1, this.itemStart + 6);
        } else {
            this.itemEnd = this.displayDataLst.length;
            this.nextState = 'button-deactive';
            this.displayDataLstEx = _.slice(this.displayDataLst, this.itemStart - 1);
            for (let i = 1; i <= (6 - (this.itemEnd - this.itemStart)); i++) {
                let rowData = [];
                this.displayHeaderLst.forEach((header: any) => {
                    rowData.push({ key: header.key, value: '' });
                });
                this.displayDataLstEx.push({ rowData, employeeName: '', id: '', employeeNameDis: '' });
            }
        }
        this.previousState = 'button-active';
        this.resetTable++;
    }

    public previousPage() {
        if (this.previousState == 'button-deactive') {
            return;
        }

        this.itemStart = this.itemStart - 7;
        this.itemEnd = this.itemStart + 6;
        if (this.itemStart == 1) {
            this.previousState = 'button-deactive';
        }
        this.nextState = 'button-active';
        this.displayDataLstEx = _.slice(this.displayDataLst, this.itemStart - 1, this.itemStart + 6);
        this.resetTable++;
    }

    public countHalf(text: string) {
        let count = 0;
        for (let i = 0; i < text.length; i++) {
            let c = text.charCodeAt(i);

            // 0x20 ～ 0x80: 半角記号と半角英数字
            // 0xff61 ～ 0xff9f: 半角カタカナ
            if ((0x20 <= c && c <= 0x7e) || (0xff61 <= c && c <= 0xff9f)) {
                count += 1;
            } else {
                count += 2;
            }
        }

        return count;
    }
}
const servicePath = {
    initMOB: 'screen/at/correctionofdailyperformance/initMOB',
    genDate: 'screen/at/correctionofdailyperformance/gendate'
};
enum DCErrorInfomation {
    NORMAL = 0,
    APPROVAL_NOT_EMP = 1,
    ITEM_HIDE_ALL = 2,
    NOT_EMP_IN_HIST = 3
}