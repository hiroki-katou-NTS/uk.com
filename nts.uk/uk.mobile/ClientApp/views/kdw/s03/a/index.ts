import { _, Vue } from '@app/provider';
import { component, Watch } from '@app/core/component';
import { FixTableComponent } from '@app/components/fix-table';
import { TimeWithDay, TimePoint, TimeDuration } from '@app/utils/time';

@component({
    name: 'kdws03a',
    route: '/kdw/s03/a',
    style: require('./style.scss'),
    template: require('./index.vue'),
    resource: require('./resources.json'),
    constraints: [],
    components: {
        'fix-table': FixTableComponent
    },
    validations: {
        yearMonth: {
            required: false
        },
    }
})
export class Kdws03AComponent extends Vue {
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
    public hasErrorBuss: boolean = false;
    public lstCellDisByLock: Array<any> = [];
    public lstEmployee: Array<any> = [];
    public yearMonth: number = 0;
    public actualTimeOptionDisp: Array<any> = [];
    public timePeriodAllInfo: any = null;
    public actualTimeSelectedCode: number = 0;
    public selectedEmployee: string = '';
    public selectedDate: Date = new Date();
    public resetTable: number = 0;

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
            screenMode: 0,
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
                    self.hasErrorBuss = true;
                } else if (dataInit.errorInfomation == DCErrorInfomation.ITEM_HIDE_ALL) {
                    messageId = 'Msg_1452';
                    self.hasErrorBuss = true;
                } else if (dataInit.errorInfomation == DCErrorInfomation.NOT_EMP_IN_HIST) {
                    messageId = 'Msg_1543';
                    self.hasErrorBuss = true;
                }
                this.$modal.error({ messageId });
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

        let headers = (_.filter(self.optionalHeader, (o) => o.hidden == false));
        headers.forEach((header: any) => {
            self.displayHeaderLst.push({
                key: header.key,
                headerText: header.headerText,
                color: header.color,
                constraint: header.constraint
            });
        });

        self.lstDataSourceLoad.forEach((rowDataSrc: any) => {
            let rowData = [];
            headers.forEach((header: any) => {
                if (_.has(rowDataSrc, header.key)) {
                    rowData.push({ key: header.key, value: rowDataSrc[header.key] });
                } else {
                    rowData.push({ key: header.key, groupKey: header.group[1].key, value: rowDataSrc[header.group[1].key] });
                }
            });

            if (self.displayFormat == '0') {
                self.displayDataLst.push({ rowData, date: rowDataSrc.date, dateDetail: rowDataSrc.dateDetail, id: rowDataSrc.id });
            } else {
                self.displayDataLst.push({ rowData, employeeName: rowDataSrc.employeeName, employeeId: rowDataSrc.employeeId, id: rowDataSrc.id });
            }
        });

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
                row.dateColor = _.find(states, (x) => x.columnKey == 'date').state[0];
            }
        });

        if (self.lstDataSourceLoad.length < 6) {
            for (let i = 1; i <= (6 - self.lstDataSourceLoad.length); i++) {
                let rowData = [];
                headers.forEach((header: any) => {
                    rowData.push({ key: header.key, value: '' });
                });
                if (self.displayFormat == '0') {
                    self.displayDataLst.push({ rowData, date: '', id: '' });
                } else {
                    self.displayDataLst.push({ rowData, employeeName: '', id: '' });
                }
            }
        }

        let sumTimeKey = _.map((_.filter(data.lstControlDisplayItem.columnSettings, (o) => o.typeFormat == 5)), (x) => x.columnKey);
        let sumNumKey = _.map((_.filter(data.lstControlDisplayItem.columnSettings, (o) => o.typeFormat == 2 || o.typeFormat == 3)), (x) => x.columnKey);
        headers.forEach((header: any) => {
            if (_.includes(sumTimeKey, header.key)) {
                self.displaySumLst[header.key] = '00:00';
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
        let param1 = _.find(this.displayDataLst, (x) => x.id == id);
        let param2 = this.displayHeaderLst;
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