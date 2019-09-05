import { _, Vue } from '@app/provider';
import { component, Watch } from '@app/core/component';
import { FixTableComponent } from '@app/components/fix-table';

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
    public displayFormat: number = 0;
    public lstDataSourceLoad: Array<any> = [];
    public lstDataHeader: Array<any> = [];
    public optionalHeader: Array<any> = [];
    public cellStates: Array<any> = [];
    public fixHeaders: Array<any> = [];
    public showPrincipal: boolean = true;
    public showSupervisor: boolean = true;
    public employeeModeHeader: Array<any> = [];
    public dateModeHeader: Array<any> = [];
    public errorModeHeader: Array<any> = [];
    public hasLstHeader: boolean = true;
    public displayDataLst: Array<any> = [];
    public hasErrorBuss: boolean = false;
    public lstCellDisByLock: Array<any> = [];
    public lstEmployee: Array<any> = [];
    public yearMonth: number = 0;
    public actualTimeOptionDisp: Array<any> = [];
    public timePeriodAllInfo: any = null;
    public actualTimeSelectedCode: number = 0;
    public selectedEmployee: string = '';
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
        this.startPage();
        this.$nextTick();
    }

    public mounted() {
        this.$mask('show', { message: true });
    }

    public startPage() {
        let self = this;

        self.$auth.user.then((user: null | { employeeCode: string }) => {
            self.selectedEmployee = user.employeeCode;
        });

        let param = {
            changePeriodAtr: false,
            screenMode: 0,
            errorRefStartAtr: false,
            initDisplayDate: null,
            employeeID: self.selectedEmployee,
            objectDateRange: !_.isNil(self.dateRanger) ? {startDate: self.$dt.fromString(self.dateRanger.startDate), endDate: self.$dt.fromString(self.dateRanger.endDate)} : null,
            lstEmployee: [],
            initClock: null,
            displayFormat: 0,
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
        if (self.showPrincipal || data.lstControlDisplayItem.lstHeader.length == 0) {
            self.employeeModeHeader = [self.fixHeaders[0], self.fixHeaders[1], self.fixHeaders[2], self.fixHeaders[3], self.fixHeaders[4]];
            self.dateModeHeader = [self.fixHeaders[0], self.fixHeaders[1], self.fixHeaders[2], self.fixHeaders[5], self.fixHeaders[6], self.fixHeaders[7], self.fixHeaders[4]];
            self.errorModeHeader = [self.fixHeaders[0], self.fixHeaders[1], self.fixHeaders[2], self.fixHeaders[5], self.fixHeaders[6], self.fixHeaders[3], self.fixHeaders[7], self.fixHeaders[4]];
        } else {
            self.employeeModeHeader = [self.fixHeaders[0], self.fixHeaders[1], self.fixHeaders[2], self.fixHeaders[3]];
            self.dateModeHeader = [self.fixHeaders[0], self.fixHeaders[1], self.fixHeaders[2], self.fixHeaders[5], self.fixHeaders[6], self.fixHeaders[7]];
            self.errorModeHeader = [self.fixHeaders[0], self.fixHeaders[1], self.fixHeaders[2], self.fixHeaders[5], self.fixHeaders[6], self.fixHeaders[3], self.fixHeaders[7]];
        }
        if (self.showSupervisor) {
            self.employeeModeHeader.push(self.fixHeaders[8]);
            self.dateModeHeader.push(self.fixHeaders[8]);
            self.errorModeHeader.push(self.fixHeaders[8]);
        }

        self.yearMonth = data.periodInfo.yearMonth;
        self.lstEmployee = _.orderBy(data.lstEmployee, ['code'], ['asc']);

        _.remove(self.displayDataLst);

        self.lstDataSourceLoad.forEach((rowDataSrc: any) => {
            let rowData = [];
            self.optionalHeader.forEach((header: any) => {
                if (_.has(rowDataSrc, header.key)) {
                    rowData.push({ key: header.key, value: rowDataSrc[header.key], headerText: header.headerText, color: header.color });
                } else {
                    rowData.push({ key: header.group[1].key, value: rowDataSrc[header.group[1].key], headerText: header.headerText, color: header.color });
                }               
            });
            
            self.displayDataLst.push({ rowData, date: rowDataSrc.date });
        });

        if (self.lstDataSourceLoad.length == 0) {
            let rowData = [];
            let headers = (_.filter(self.optionalHeader, (o) => o.hidden == false));
            headers.forEach((header: any) => {
                rowData.push({ key: header.key, headerText: header.headerText, color: header.color });
            });
            self.displayDataLst.push({ rowData });
        }

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