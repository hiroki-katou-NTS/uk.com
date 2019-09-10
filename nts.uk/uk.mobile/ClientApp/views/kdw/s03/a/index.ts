import { _, Vue } from '@app/provider';
import { component, Watch, Prop } from '@app/core/component';
import { FixTableComponent } from '@app/components/fix-table';
import { TimeDuration } from '@app/utils/time';
import { storage } from '@app/utils';
import { KdwS03BComponent } from 'views/kdw/s03/b';

@component({
    name: 'kdws03a',
    route: '/kdw/s03/a',
    style: require('./style.scss'),
    template: require('./index.vue'),
    resource: require('./resources.json'),
    constraints: [],
    components: {
        'fix-table': FixTableComponent,
        'kdws03b': KdwS03BComponent
    },
    validations: {
        yearMonth: {
            required: true
        },
    }
})
export class Kdws03AComponent extends Vue {
    @Prop({default: 0})
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
    public previousState: string = '';
    public nextState: string = '';
    public itemStart: number = 0;
    public itemEnd: number = 0;

    @Watch('yearMonth')
    public changeYearMonth(value: any) {
        let self = this;
        self.$http.post('at', servicePath.genDate, { yearMonth: value }).then((result: { data: any }) => {
            let data = result.data;
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
        this.startPage();
    }

    @Watch('actualTimeSelectedCode')
    public changeDateRange(value: any, valueOld: any) {
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

        this.$auth.user.then((user: null | { employeeCode: string }) => {
            this.selectedEmployee = user.employeeCode;
        });
        this.startPage();
    }

    public mounted() {
        this.$mask('show', { message: true });
    }

    public startPage() {
        let self = this;
        self.$mask('show', { message: true });

        let lstEmployeeParam = [];
        if (self.lstEmployee.length != 0) {
            if (self.displayFormat == '0') {
                lstEmployeeParam = _.filter(self.lstEmployee, (o) => o.code == self.selectedEmployee);
            } else {
                lstEmployeeParam = self.lstEmployee;
            }
        }

        let param = {
            changePeriodAtr: false,
            screenMode: self.screenMode,
            errorRefStartAtr: false,
            initDisplayDate: null,
            employeeID: self.selectedEmployee,
            objectDateRange: self.displayFormat == '0' ?
                (!_.isNil(self.dateRanger) ? { startDate: self.$dt.fromString(self.dateRanger.startDate), endDate: self.$dt.fromString(self.dateRanger.endDate) } : null) :
                { startDate: self.selectedDate, endDate: self.selectedDate },
            lstEmployee: lstEmployeeParam,
            initClock: null,
            displayFormat: this.displayFormat,
            displayDateRange: null,
            transitionDesScreen: null,
        };

        self.$http.post('at', servicePath.initMOB, param).then((result: { data: any }) => {
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
                let errors = [];
                _.forEach(dataInit.errors, (error) => {
                    errors.push({
                        message: error.message,
                        messageId: error.messageId,
                        supplements: {}
                    });
                });
                // pending
            } else {
                this.processMapData(result.data);
                storage.local.setItem('dailyCorrectionState', {
                    screenMode: self.screenMode,
                    displayFormat: self.displayFormat,
                    selectedEmployee: self.selectedEmployee,
                    lstEmployee: self.lstEmployee,
                    dateRange: self.displayFormat == '0' ?
                        (!_.isNil(self.dateRanger) ? { startDate: self.$dt.fromString(self.dateRanger.startDate), endDate: self.$dt.fromString(self.dateRanger.endDate) } : null) :
                        { startDate: self.selectedDate, endDate: self.selectedDate },
                    cellDataLst: this.displayDataLst,
                    headerLst: this.displayHeaderLst
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
        self.lstDataSourceLoad = self.formatDate(data.lstData);
        self.optionalHeader = data.lstControlDisplayItem.lstHeader;
        self.cellStates = data.lstCellState;
        self.timePeriodAllInfo = data.periodInfo;

        self.fixHeaders = data.lstFixedHeader;
        self.showPrincipal = data.showPrincipal;
        self.showSupervisor = data.showSupervisor;
        if (data.lstControlDisplayItem.lstHeader.length == 0) {
            self.hasLstHeader = false;
        }

        self.yearMonth = data.periodInfo.yearMonth;
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
                        value: rowDataSrc[header.key] });
                } else {
                    rowData.push({ 
                        key: header.key, 
                        groupKey: header.group[1].key, 
                        value: rowDataSrc[header.group[1].key] });
                }
            });

            if (self.displayFormat == '0') {
                self.displayDataLst.push({ 
                    rowData, 
                    date: rowDataSrc.date, 
                    dateDetail: rowDataSrc.dateDetail, id: rowDataSrc.id });
            } else {
                self.displayDataLst.push({ 
                    rowData, 
                    employeeName: rowDataSrc.employeeName, 
                    employeeId: rowDataSrc.employeeId, 
                    id: rowDataSrc.id });
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
            if (self.lstDataSourceLoad.length < 6) {
                for (let i = 1; i <= (6 - self.lstDataSourceLoad.length); i++) {
                    let rowData = [];
                    headers.forEach((header: any) => {
                        rowData.push({ key: header.key, value: '' });
                    });
                    self.displayDataLst.push({ rowData, date: '', id: '' });
                }
            }
        } else {           
            if (self.displayDataLst.length <= 6) {
                if (self.displayDataLst.length == 0) {
                    self.itemStart = 0;
                    self.itemEnd = 0;
                    self.previousState = '';
                    self.nextState = '';
                } else {
                    self.itemStart = 1;
                    self.itemEnd = self.displayDataLst.length;
                    self.previousState = '';
                    self.nextState = '';
                }
                self.displayDataLstEx = self.displayDataLst.slice();
                for (let i = 1; i <= (6 - self.displayDataLst.length); i++) {
                    let rowData = [];
                    headers.forEach((header: any) => {
                        rowData.push({ key: header.key, value: '' });
                    });
                    self.displayDataLstEx.push({ rowData, employeeName: '', id: '' });
                }
            } else {
                self.displayDataLstEx = _.slice(self.displayDataLst, 0, 6);
                self.itemStart = 1;
                self.itemEnd = 6;
                self.previousState = '';
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
                        self.displaySumLst[cell.key] = self.displaySumLst[cell.key] + cell.value;
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

        if (self.displayFormat == 0) {

        } else {

        }

        self.$modal('kdws03b', { employeeName: 'testName', date: new Date(), data :param1.rowData, contentType: param2 }, { type : 'dropback' } )
        .then((v) => {

        });
    }

    public nextPage() {
        if (this.nextState == '') {
            return;
        }
        this.itemStart = this.itemStart + 6;
        if (this.displayDataLst.length - this.itemEnd > 6) {
            this.itemEnd = this.itemEnd + 6;
            this.nextState = 'button-active';
            this.displayDataLstEx = _.slice(this.displayDataLst, this.itemStart - 2, 6);
        } else {
            this.itemEnd = this.displayDataLst.length;
            this.nextState = '';
            this.displayDataLstEx = _.slice(this.displayDataLst, this.displayDataLst.length - this.itemEnd);
        }
        this.previousState = 'button-active';
    }

    public previousPage() {
        if (this.previousState == '') {
            return;
        }
        this.itemEnd = this.itemEnd - 6;
        this.itemStart = this.itemStart - 6;
        this.displayDataLstEx = _.slice(this.displayDataLst, this.itemStart - 2, 6);
        if (this.itemStart == 1) {
            this.previousState = '';
        }
        this.previousState = 'button-active';
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