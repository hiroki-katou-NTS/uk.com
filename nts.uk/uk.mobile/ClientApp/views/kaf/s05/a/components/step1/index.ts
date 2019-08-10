import { component, Prop, Watch } from '@app/core/component';
import { _, Vue } from '@app/provider';
import { KDL002Component } from '../../../../../kdl/002';
import { TimeWithDay, $ } from '@app/utils';
import { OvertimeAgreement, AgreementTimeStatusOfMonthly, Kafs05Model } from '../common/CommonClass';

@component({
    name: 'KafS05a1',
    template: require('./index.html'),
    resource: require('../../resources.json'),
    components: {
        'worktype': KDL002Component
    },
    validations: {
        kafs05ModelStep1: {
            appDate: {
                required: true
            },
            workTimeInput: {
                timeRange: true,
                required: true,
            },
            prePostSelected: {
                validateSwitchbox: {
                    test(value: number) {
                        if (null == this.kafs05ModelStep1.appID && this.kafs05ModelStep1.displayPrePostFlg && !_.isNil(document.body.getElementsByClassName('valid-switchbox')[0])) {
                            if (value != 0 && value != 1) {
                                document.body.getElementsByClassName('valid-switchbox')[0].className += ' invalid';
    
                                return false;
                            }
                            document.body.getElementsByClassName('valid-switchbox')[0].className += 'valid-switchbox';
    
                            return true;
                        }

                        return true;
                    },
                    messageId: 'MsgB_30'
                }
            },
        },
    },
})
export class KafS05aStep1Component extends Vue {
    @Prop()
    public kafs05ModelStep1: Kafs05Model;

    @Watch('kafs05ModelStep1.restTime', {deep: true})
    public setRestTimes(restTime: any) {
        _.map(restTime, (x) => { x.startTime = x.restTimeInput.start; x.endTime = x.restTimeInput.end; });
    }

    public mounted() {
        if (this.kafs05ModelStep1.step1Start) {
            this.$mask('show', { message: true });
        }
        this.applyWatcher();
    }

    public created() {
        if (this.kafs05ModelStep1.step1Start) {
            this.startPage();
        } else {
            this.$mask('hide');
        }
    }

    public openKDL002() {
        let self = this.kafs05ModelStep1;

        this.$validate('kafs05ModelStep1.appDate');
        if (!this.$valid) {
            window.scrollTo({ top: 0, behavior: 'smooth' });

            return;
        }

        this.$modal(
            'worktype',
            {
                seledtedWkTypeCDs: self.workTypecodes,
                selectedWorkTypeCD: self.workTypeCd,
                seledtedWkTimeCDs: self.workTimecodes,
                selectedWorkTimeCD: self.siftCD,
                isSelectWorkTime: '1',
            }
        ).then((f: any) => {
            let self = this.kafs05ModelStep1;
            if (f) {
                self.workTypeCd = f.selectedWorkType.workTypeCode;
                self.workTypeName = f.selectedWorkType.name;
                self.siftCD = f.selectedWorkTime.code;
                self.siftName = f.selectedWorkTime.name;
                self.selectedWorkTime = f.selectedWorkTime.workTime1;
                this.$http.post('at', servicePath.getRecordWork, {
                    employeeID: self.employeeID,
                    appDate: _.isNil(self.appDate) ? null : this.$dt(self.appDate),
                    siftCD: self.siftCD,
                    prePostAtr: self.prePostSelected,
                    overtimeHours: _.map(self.overtimeHours, (item) => this.initCalculateData(item)),
                    workTypeCode: self.workTypeCd,
                    startTimeRests: _.isEmpty(self.restTime) ? [] : _.map(self.restTime, (x) => x.restTimeInput.start),
                    endTimeRests: _.isEmpty(self.restTime) ? [] : _.map(self.restTime, (x) => x.restTimeInput.end),
                    restTimeDisFlg: self.restTimeDisFlg
                }).then((result: { data: any }) => {
                    self.workTimeInput.start = result.data.startTime1 == null ? null : result.data.startTime1;
                    self.workTimeInput.end = result.data.endTime1 == null ? null : result.data.endTime1;

                    // 休憩時間
                    this.setTimeZones(result.data.timezones);
                    self.resetTimeRange++;
                });
            }
        });
    }

    public calculate() {
        let self = this.kafs05ModelStep1;

        if (!self.displayCaculationTime) {
            this.$updateValidator('kafs05ModelStep1.workTimeInput', { required: false });
        }

        this.$validate();
        if (!this.$valid) {
            window.scrollTo({ top: 0, behavior: 'smooth' });

            return;
        }

        if (!self.displayCaculationTime) {
            this.$emit('toStep2', this.kafs05ModelStep1);

            return;
        }

        this.$mask('show', { message: true });
        let param: any = {
            overtimeHours: _.map(self.overtimeHours, (item) => this.initCalculateData(item)),
            bonusTimes: _.map(self.bonusTimes, (item) => this.initCalculateData(item)),
            prePostAtr: self.prePostSelected,
            appDate: _.isNil(self.appDate) ? null : this.$dt(self.appDate),
            siftCD: self.siftCD,
            workTypeCode: self.workTypeCd,
            startTimeRests: _.isEmpty(self.restTime) ? [] : _.map(self.restTime, (x) => x.restTimeInput.start),
            endTimeRests: _.isEmpty(self.restTime) ? [] : _.map(self.restTime, (x) => x.restTimeInput.end),
            startTime: _.isNil(self.workTimeInput.start) ? null : self.workTimeInput.start,
            endTime: _.isNil(self.workTimeInput.end) ? null : self.workTimeInput.end,
            displayCaculationTime: self.displayCaculationTime
        };

        let overtimeHoursResult: Array<any>;
        this.$http.post('at', servicePath.getCalculationResultMob, param).then((result: { data: any }) => {
            _.remove(self.overtimeHours);
            _.remove(self.bonusTimes);
            overtimeHoursResult = result.data.caculationTimes;
            if (overtimeHoursResult != null) {
                for (let i = 0; i < overtimeHoursResult.length; i++) {
                    //残業時間
                    if (overtimeHoursResult[i].attendanceID == 1) {
                        if (overtimeHoursResult[i].frameNo != 11 && overtimeHoursResult[i].frameNo != 12) {
                            self.overtimeHours.push({
                                companyID: '',
                                appID: '',
                                attendanceID: overtimeHoursResult[i].attendanceID,
                                attendanceName: '',
                                frameNo: overtimeHoursResult[i].frameNo,
                                timeItemTypeAtr: 0,
                                frameName: overtimeHoursResult[i].frameName,
                                applicationTime: overtimeHoursResult[i].applicationTime,
                                preAppTime: overtimeHoursResult[i].preAppTime,
                                caculationTime: overtimeHoursResult[i].caculationTime,
                                nameID: '#[KAF005_55]',
                                itemName: 'KAF005_55',
                                color: '',
                                preAppExceedState: overtimeHoursResult[i].preAppExceedState,
                                actualExceedState: overtimeHoursResult[i].actualExceedState,
                            });
                        } else if (overtimeHoursResult[i].frameNo == 11) {
                            self.overtimeHours.push({
                                companyID: '',
                                appID: '',
                                attendanceID: overtimeHoursResult[i].attendanceID,
                                attendanceName: '',
                                frameNo: overtimeHoursResult[i].frameNo,
                                timeItemTypeAtr: 0,
                                frameName: 'KAF005_63',
                                applicationTime: overtimeHoursResult[i].applicationTime,
                                preAppTime: overtimeHoursResult[i].preAppTime,
                                caculationTime: overtimeHoursResult[i].caculationTime,
                                nameID: '#[KAF005_64]',
                                itemName: 'KAF005_55',
                                color: '',
                                preAppExceedState: overtimeHoursResult[i].preAppExceedState,
                                actualExceedState: overtimeHoursResult[i].actualExceedState,
                            });
                        } else if (overtimeHoursResult[i].frameNo == 12) {
                            self.overtimeHours.push({
                                companyID: '',
                                appID: '',
                                attendanceID: overtimeHoursResult[i].attendanceID,
                                attendanceName: '',
                                frameNo: overtimeHoursResult[i].frameNo,
                                timeItemTypeAtr: 0,
                                frameName: 'KAF005_65',
                                applicationTime: overtimeHoursResult[i].applicationTime,
                                preAppTime: overtimeHoursResult[i].preAppTime,
                                caculationTime: overtimeHoursResult[i].caculationTime,
                                nameID: '#[KAF005_66]',
                                itemName: 'KAF005_55',
                                color: '',
                                preAppExceedState: overtimeHoursResult[i].preAppExceedState,
                                actualExceedState: overtimeHoursResult[i].actualExceedState,
                            });
                        }
                    }
                    //加給時間
                    if (overtimeHoursResult[i].attendanceID == 3) {
                        self.bonusTimes.push({
                            companyID: '',
                            appID: '',
                            attendanceID: overtimeHoursResult[i].attendanceID,
                            attendanceName: '',
                            frameNo: overtimeHoursResult[i].frameNo,
                            timeItemTypeAtr: overtimeHoursResult[i].timeItemTypeAtr,
                            frameName: overtimeHoursResult[i].frameName,
                            applicationTime: overtimeHoursResult[i].applicationTime,
                            preAppTime: overtimeHoursResult[i].preAppTime,
                            caculationTime: null,
                            nameID: '',
                            itemName: '',
                            color: '',
                            preAppExceedState: overtimeHoursResult[i].preAppExceedState,
                            actualExceedState: overtimeHoursResult[i].actualExceedState,
                        });
                    }
                }
            }
            this.$emit('toStep2', this.kafs05ModelStep1);
            this.$mask('hide');
        }).catch((res: any) => {
            if (res.messageId == 'Msg_424') {
                this.$modal.error({ messageId: 'Msg_424', messageParams: [res.parameterIds[0], res.parameterIds[1], res.parameterIds[2]] });
            } else if (res.messageId == 'Msg_1508') {
                this.$modal.error({ messageId: 'Msg_1508', messageParams: [res.parameterIds[0]] });
            } else {
                this.$modal.error({ messageId: res.messageId });
            }
            this.$mask('hide');
        });
    }
    public startPage() {
        let self = this.kafs05ModelStep1;

        this.$http.post('at', servicePath.getOvertimeByUI, {
            url: self.overtimeType,
            appDate: self.appDate,
            uiType: self.uiType,
            timeStart1: self.workTimeInput.start,
            timeEnd1: self.workTimeInput.end,
            reasonContent: self.multilContent,
            employeeIDs: self.employeeIDs,
            employeeID: self.employeeID
        }).then((result: { data: any }) => {
            this.initData(result.data);
            this.$mask('hide');
        }).catch((res: any) => {
            if (res.messageId == 'Msg_426') {
                this.$modal.error({ messageId: res.messageId }).then(() => {
                    this.$goto('ccg007b');
                });
            } else {
                this.$modal.error({ messageId: res.messageId }).then(() => {
                    this.$goto('ccg008a');
                });
            }
        });
    }

    public applyWatcher() {
        this.$watch('kafs05ModelStep1.appDate', function (value: Date) {
            let self = this.kafs05ModelStep1;
            this.$mask('show', { message: true });
            this.$http.post('at', servicePath.findByChangeAppDate, {
                appDate: this.$dt(value),
                prePostAtr: self.prePostSelected,
                siftCD: self.siftCD,
                overtimeHours: _.map(self.overtimeHours, (item) => this.initCalculateData(item)),
                workTypeCode: self.workTypeCd,
                startTimeRests: _.isEmpty(self.restTime) ? [] : _.map(self.restTime, (x) => x.restTimeInput.start),
                endTimeRests: _.isEmpty(self.restTime) ? [] : _.map(self.restTime, (x) => x.restTimeInput.end),
                startTime: _.isEmpty(self.workTimeInput.start) ? null : self.workTimeInput.start,
                endTime: _.isEmpty(self.workTimeInput.end) ? null : self.workTimeInput.end,
                overtimeAtr: self.overtimeAtr,
                changeEmployee: _.isEmpty(self.employeeList) ? null : self.employeeList[0].id
            }).then((result: { data: any }) => {
                this.changeAppDateData(result.data);
                this.checkAppDate(0, this.$dt(value), false, self.employeeID, self.overtimeAtr);
                self.resetTimeRange++;
                this.$mask('hide');
            }).catch((res: any) => {

            });
        });
        this.$watch('kafs05ModelStep1.prePostSelected', function (value: number) {
            let self = this.kafs05ModelStep1;
            this.$mask('show', { message: true });
            if (value == 1) {
                // edit ui
            } else if (value == 0) {

            }
            if (!_.isNil(self.appDate)) {

            }
            this.$http.post('at', servicePath.checkConvertPrePost, {
                prePostAtr: value,
                appDate: _.isNil(self.appDate) ? null : this.$dt(self.appDate),
                siftCD: self.siftCD,
                overtimeHours: _.map(self.overtimeHours, (item) => this.initCalculateData(item)),
                workTypeCode: self.workTypeCd,
                startTimeRests: _.isEmpty(self.restTime) ? [] : _.map(self.restTime, (x) => x.restTimeInput.start),
                endTimeRests: _.isEmpty(self.restTime) ? [] : _.map(self.restTime, (x) => x.restTimeInput.end),
                startTime: _.isEmpty(self.workTimeInput.start) ? null : self.workTimeInput.start,
                endTime: _.isEmpty(self.workTimeInput.end) ? null : self.workTimeInput.end
            }).then((result: { data: any }) => {
                self.displayDivergenceReasonForm = result.data.displayDivergenceReasonForm;
                self.displayDivergenceReasonInput = result.data.displayDivergenceReasonInput;
                this.$mask('hide');

            }).catch((res: any) => {
            });
        });
    }

    public checkAppDate(appType: number, appDate: string, isStartup: boolean, employeeID: string, overtimeAtr: any) {
        let self = this.kafs05ModelStep1;
        this.$http.post('at', servicePath.getAppDataDate, {
            appTypeValue: appType,
            appDate,
            isStartup,
            employeeID,
            overtimeAtrkafs05ModelStep1: overtimeAtr
        }).then((result: { data: any }) => {
            if (!_.isNil(result.data.errorFlag)) {
                if (isStartup == false) {
                    switch (result.data.errorFlag) {
                        case 1:
                            this.$modal.error({ messageId: 'Msg_324' }).then(() => {
                                return;
                            });
                            break;
                        case 2:
                            this.$modal.error({ messageId: 'Msg_238' }).then(() => {
                                return;
                            });
                            break;
                        case 3:
                            this.$modal.error({ messageId: 'Msg_237' }).then(() => {
                                return;
                            });
                            break;
                        default:
                    }
                }
            }
        }).catch((res: any) => {

        });
    }

    public initData(data) {
        let self = this.kafs05ModelStep1;
        self.requiredReason = data.requireAppReasonFlg;
        self.enableOvertimeInput = data.enableOvertimeInput;
        self.checkBoxValue = !data.manualSendMailAtr;
        self.enableSendMail = !data.sendMailWhenRegisterFlg;
        self.displayPrePostFlg = data.displayPrePostFlg ? true : false;
        self.prePostSelected = data.application.prePostAtr;
        self.displayCaculationTime = data.displayCaculationTime;
        self.typicalReasonDisplayFlg = data.typicalReasonDisplayFlg;
        self.displayAppReasonContentFlg = data.displayAppReasonContentFlg;
        self.displayDivergenceReasonForm = data.displayDivergenceReasonForm;
        self.displayDivergenceReasonInput = data.displayDivergenceReasonInput;
        self.displayBonusTime = data.displayBonusTime;
        self.restTimeDisFlg = data.displayRestTime;
        self.employeeName = data.employeeName;
        self.employeeID = data.employeeID;
        if (data.siftType != null) {
            self.siftCD = data.siftType.siftCode;
            self.siftName = this.getName(data.siftType.siftCode, data.siftType.siftName);
        }
        if (data.workType != null) {
            self.workTypeCd = data.workType.workTypeCode;
            self.workTypeName = this.getName(data.workType.workTypeCode, data.workType.workTypeName);
        }
        self.workTypecodes = data.workTypes;
        self.workTimecodes = data.siftTypes;
        self.workTimeInput.start = data.workClockFrom1 == null ? null : data.workClockFrom1;
        self.workTimeInput.end = data.workClockTo1 == null ? null : data.workClockTo1;
        if (data.applicationReasonDtos != null && data.applicationReasonDtos.length > 0) {
            self.reasonCombo = _.map(data.applicationReasonDtos, (o) => ({ reasonId: o.reasonID, reasonName: o.reasonTemp }));
            self.selectedReason = _.find(data.applicationReasonDtos, (o) => o.defaultFlg == 1).reasonID;
            if (data.application.applicationReason != null) {
                self.multilContent = data.application.applicationReason;
            }
        }
        if (data.divergenceReasonDtos != null && data.divergenceReasonDtos.length > 0) {
            self.reasonCombo2 = _.map(data.divergenceReasonDtos, (o) => ({ reasonId: o.divergenceReasonID, reasonName: o.reasonTemp }));
            let defaultID = _.find(data.divergenceReasonDtos, (o) => o.divergenceReasonIdDefault == 1);
            if (!_.isNil(defaultID)) {
                self.selectedReason2 = defaultID.divergenceReasonID;
            } else {
                self.selectedReason2 = '';
            }
            if (data.divergenceReasonContent != null) {
                self.multilContent2 = data.divergenceReasonContent;
            }
        }

        self.prePostEnable = data.prePostCanChangeFlg;
        self.indicationOvertimeFlg = data.extratimeDisplayFlag;
        if (_.isNil(data.agreementTimeDto)) {
            self.indicationOvertimeFlg = false;
        } else {
            this.setOvertimeWork(data.agreementTimeDto, self);
        }
        self.workTypeChangeFlg = data.workTypeChangeFlg;
        // list employeeID
        if (!_.isEmpty(data.employees)) {
            self.employeeFlag = true;
            for (let i = 0; i < data.employees.length; i++) {
                self.employeeList.push({ id: data.employees[i].employeeIDs, name: data.employees[i].employeeName });
            }
            let total = data.employees.length;
            //self.totalEmployee
        }
        // 休憩時間
        this.setTimeZones(data.timezones);

        // 残業時間
        if (!data.resultCaculationTimeFlg) {
            if (data.overTimeInputs != null) {
                for (let i = 0; i < data.overTimeInputs.length; i++) {
                    if (data.overTimeInputs[i].attendanceID == 1) {
                        self.overtimeHours.push({
                            companyID: '',
                            appID: '',
                            attendanceID: data.overTimeInputs[i].attendanceID,
                            attendanceName: '',
                            frameNo: data.overTimeInputs[i].frameNo,
                            timeItemTypeAtr: 0,
                            frameName: data.overTimeInputs[i].frameName,
                            applicationTime: null,
                            preAppTime: null,
                            caculationTime: null,
                            nameID: '#[KAF005_55]',
                            itemName: 'KAF005_85',
                            color: '',
                            preAppExceedState: false,
                            actualExceedState: false,
                        });
                    }
                    if (data.overTimeInputs[i].attendanceID == 3) {
                        self.bonusTimes.push({
                            companyID: '',
                            appID: '',
                            attendanceID: data.overTimeInputs[i].attendanceID,
                            attendanceName: '',
                            frameNo: data.overTimeInputs[i].frameNo,
                            timeItemTypeAtr: data.overTimeInputs[i].timeItemTypeAtr,
                            frameName: data.overTimeInputs[i].frameName,
                            applicationTime: null,
                            preAppTime: null,
                            caculationTime: null,
                            nameID: '',
                            itemName: '',
                            color: '',
                            preAppExceedState: false,
                            actualExceedState: false,
                        });
                    }
                }
            }

            if (data.appOvertimeNightFlg == 1) {
                self.overtimeHours.push({
                    companyID: '',
                    appID: '',
                    attendanceID: 1,
                    attendanceName: '',
                    frameNo: 11,
                    timeItemTypeAtr: 0,
                    frameName: this.$i18n('KAF005_63'),
                    applicationTime: null,
                    preAppTime: null,
                    caculationTime: null,
                    nameID: '#[KAF005_64]',
                    itemName: 'KAF005_85',
                    color: '',
                    preAppExceedState: false,
                    actualExceedState: false,
                });
            }

            if (data.flexFLag) {
                self.overtimeHours.push({
                    companyID: '',
                    appID: '',
                    attendanceID: 1,
                    attendanceName: '',
                    frameNo: 12,
                    timeItemTypeAtr: 0,
                    frameName: this.$i18n('KAF005_65'),
                    applicationTime: null,
                    preAppTime: null,
                    caculationTime: null,
                    nameID: '#[KAF005_66]',
                    itemName: 'KAF005_85',
                    color: '',
                    preAppExceedState: false,
                    actualExceedState: false,
                });
            }
        } else {
            let dataOverTime = _.filter(data.caculationTimes, { 'attendanceID': 1 });
            let dataBonusTime = _.filter(data.caculationTimes, { 'attendanceID': 3 });
            _.forEach(dataOverTime, (item: any) => {
                let color: string = '';
                if (item.errorCode == 1) {
                    color = '#FD4D4D';
                }
                if (item.errorCode == 2) {
                    color = '#F6F636';
                }
                if (item.errorCode == 3) {
                    color = '#F69164';
                }
                if (item.frameNo == 11) {
                    if (data.appOvertimeNightFlg == 1) {
                        self.overtimeHours.push({
                            companyID: item.companyID,
                            appID: item.appID,
                            attendanceID: item.attendanceID,
                            attendanceName: '',
                            frameNo: item.frameNo,
                            timeItemTypeAtr: item.timeItemTypeAtr,
                            frameName: 'KAF005_63',
                            applicationTime: item.applicationTime,
                            preAppTime: null,
                            caculationTime: null,
                            nameID: '#[KAF005_64]',
                            itemName: 'KAF005_85',
                            color,
                            preAppExceedState: false,
                            actualExceedState: false,
                        });
                    }
                } else if (item.frameNo == 12) {
                    if (data.flexFLag) {
                        self.overtimeHours.push({
                            companyID: item.companyID,
                            appID: item.appID,
                            attendanceID: item.attendanceID,
                            attendanceName: '',
                            frameNo: item.frameNo,
                            timeItemTypeAtr: item.timeItemTypeAtr,
                            frameName: 'KAF005_65',
                            applicationTime: item.applicationTime,
                            preAppTime: null,
                            caculationTime: null,
                            nameID: '#[KAF005_66]',
                            itemName: 'KAF005_85',
                            color,
                            preAppExceedState: false,
                            actualExceedState: false,
                        });
                    }
                } else {
                    self.overtimeHours.push({
                        companyID: item.companyID,
                        appID: item.appID,
                        attendanceID: item.attendanceID,
                        attendanceName: '',
                        frameNo: item.frameNo,
                        timeItemTypeAtr: item.timeItemTypeAtr,
                        frameName: item.frameName,
                        applicationTime: item.applicationTime,
                        preAppTime: null,
                        caculationTime: null,
                        nameID: '#[KAF005_55]',
                        itemName: 'KAF005_85',
                        color,
                        preAppExceedState: false,
                        actualExceedState: false,
                    });
                }

            });
            _.forEach(dataBonusTime, (item: any) => {
                self.bonusTimes.push({
                    companyID: item.companyID,
                    appID: item.appID,
                    attendanceID: item.attendanceID,
                    attendanceName: '',
                    frameNo: item.frameNo,
                    timeItemTypeAtr: item.timeItemTypeAtr,
                    frameName: item.frameName,
                    applicationTime: item.applicationTime,
                    preAppTime: null,
                    caculationTime: null,
                    nameID: '',
                    itemName: '',
                    color: '',
                    preAppExceedState: false,
                    actualExceedState: false,
                });
            });
        }

        self.overtimeAtr = data.overtimeAtr;
        if (!_.isNil(data.worktimeStart) && !_.isNil(data.worktimeEnd)) {
            self.selectedWorkTime = TimeWithDay.toString(data.worktimeStart) + '～' + TimeWithDay.toString(data.worktimeEnd);
        }
    }

    public changeAppDateData(data: any) {
        let self = this.kafs05ModelStep1;
        self.checkBoxValue = !data.manualSendMailAtr;
        self.enableSendMail = !data.sendMailWhenRegisterFlg;
        self.prePostSelected = data.application.prePostAtr;
        self.displayPrePostFlg = data.displayPrePostFlg ? true : false;
        self.displayCaculationTime = data.displayCaculationTime;
        self.employeeName = data.employeeName;
        if (data.siftType != null) {
            self.siftCD = data.siftType.siftCode;
            self.siftName = this.getName(data.siftType.siftCode, data.siftType.siftName);
        }
        if (data.workType != null) {
            self.workTypeCd = data.workType.workTypeCode;
            self.workTypeName = data.workType.workTypeName || this.$i18n('KAL003_120');
        }

        self.workTimeInput.start = data.workClockFrom1;
        self.workTimeInput.end = data.workClockTo1;

        if (data.applicationReasonDtos != null) {
            self.reasonCombo = _.map(data.applicationReasonDtos, (o) => ({ reasonId: o.reasonID, reasonName: o.reasonTemp }));
            self.selectedReason = _.find(data.applicationReasonDtos, (o) => o.defaultFlg == 1).reasonID;
            self.multilContent = data.application.applicationReason;
        }

        if (data.divergenceReasonDtos != null) {
            self.reasonCombo2 = _.map(data.divergenceReasonDtos, (o) => ({ reasonId: o.divergenceReasonID, reasonName: o.reasonTemp }));
            let defaultID = _.find(data.divergenceReasonDtos, (o) => o.divergenceReasonIdDefault == 1);
            if (!_.isNil(defaultID)) {
                self.selectedReason2 = defaultID.divergenceReasonID;
            } else {
                self.selectedReason2 = '';
            }
            self.multilContent2 = data.divergenceReasonContent;
        }

        // 残業時間
        if (data.overTimeInputs != null) {
            for (let i = 0; i < data.overTimeInputs.length; i++) {
                //1: 残業時間
                if (data.overTimeInputs[i].attendanceID == 1) {
                    self.overtimeHours.push({
                        companyID: '',
                        appID: '',
                        attendanceID: data.overTimeInputs[i].attendanceID,
                        attendanceName: '',
                        frameNo: data.overTimeInputs[i].frameNo,
                        timeItemTypeAtr: 0,
                        frameName: data.overTimeInputs[i].frameName,
                        applicationTime: null,
                        preAppTime: null,
                        caculationTime: null,
                        nameID: '#[KAF005_55]',
                        itemName: '',
                        color: '',
                        preAppExceedState: false,
                        actualExceedState: false,
                    });
                }
                if (data.overTimeInputs[i].attendanceID == 3) {
                    self.bonusTimes.push({
                        companyID: '',
                        appID: '',
                        attendanceID: data.overTimeInputs[i].attendanceID,
                        attendanceName: '',
                        frameNo: data.overTimeInputs[i].frameNo,
                        timeItemTypeAtr: data.overTimeInputs[i].timeItemTypeAtr,
                        frameName: data.overTimeInputs[i].frameName,
                        applicationTime: null,
                        preAppTime: null,
                        caculationTime: null,
                        nameID: '',
                        itemName: '',
                        color: '',
                        preAppExceedState: false,
                        actualExceedState: false,
                    });
                }
            }
        }
        // 休憩時間
        this.setTimeZones(data.timezones);
        if (!_.isNil(data.worktimeStart) && !_.isNil(data.worktimeEnd)) {
            self.selectedWorkTime = TimeWithDay.toString(data.worktimeStart) + '～' + TimeWithDay.toString(data.worktimeEnd);
        }
    }

    public setTimeZones(timeZones) {
        let self = this.kafs05ModelStep1;
        if (timeZones) {
            let times = [];
            for (let i = 1; i < 11; i++) {
                times.push({
                    companyID: '',
                    appID: '',
                    attendanceID: 0,
                    attendanceName: '',
                    frameNo: i,
                    timeItemTypeAtr: 0,
                    frameName: i,
                    applicationTime: null,
                    nameID: '',
                    restTimeInput: { start: timeZones[i - 1] ? timeZones[i - 1].start : null, end: timeZones[i - 1] ? timeZones[i - 1].end : null },
                    startTime: timeZones[i - 1] ? timeZones[i - 1].start : null,
                    endTime: timeZones[i - 1] ? timeZones[i - 1].end : null
                });
            }
            self.restTime = times;
        }
    }

    public getName(code, name) {
        let result = '';
        if (code) {
            result = name || this.$i18n('KAL003_120');
        }

        return result;
    }



    public initCalculateData(item: any): any {
        return {
            companyID: item.companyID,
            appID: item.appID,
            attendanceID: item.attendanceID,
            attendanceName: item.attendanceName,
            frameNo: item.frameNo,
            timeItemTypeAtr: item.timeItemTypeAtr,
            frameName: item.frameName,
            applicationTime: item.applicationTime,
            preAppTime: null,
            caculationTime: null,
            nameID: item.nameID,
            itemName: item.itemName
        };
    }



    public setOvertimeWork(overtimeAgreement: OvertimeAgreement, self: any): void {
        let overtimeWork1 = { yearMonth: '', limitTime: 0, actualTime: 0, color: '' };
        let overtimeWork2 = { yearMonth: '', limitTime: 0, actualTime: 0, color: '' };

        overtimeWork1.yearMonth = overtimeAgreement.currentMonth;
        let exceptionLimitErrorTime1 = overtimeAgreement.detailCurrentMonth.confirmed.exceptionLimitErrorTime;
        let limitErrorTime1 = overtimeAgreement.detailCurrentMonth.confirmed.limitErrorTime;
        if (!_.isNil(exceptionLimitErrorTime1)) {
            overtimeWork1.limitTime = exceptionLimitErrorTime1;
        } else if (!_.isNil(limitErrorTime1)) {
            overtimeWork1.limitTime = limitErrorTime1;
        }
        let agreementTime1 = overtimeAgreement.detailCurrentMonth.confirmed.agreementTime;
        if (!_.isNil(agreementTime1)) {
            overtimeWork1.actualTime = agreementTime1;
        }

        switch (overtimeAgreement.detailCurrentMonth.confirmed.status) {
            case AgreementTimeStatusOfMonthly.EXCESS_LIMIT_ALARM: {
                overtimeWork1.color = 'alarm';
                break;
            }
            case AgreementTimeStatusOfMonthly.EXCESS_LIMIT_ERROR: {
                overtimeWork1.color = 'error';
                break;
            }
            case AgreementTimeStatusOfMonthly.NORMAL_SPECIAL: {
                break;
            }
            case AgreementTimeStatusOfMonthly.EXCESS_LIMIT_ALARM_SP: {
                overtimeWork1.color = 'exception';
                break;
            }
            case AgreementTimeStatusOfMonthly.EXCESS_LIMIT_ERROR_SP: {
                overtimeWork1.color = 'exception';
                break;
            }
            case AgreementTimeStatusOfMonthly.EXCESS_EXCEPTION_LIMIT_ALARM: {
                overtimeWork1.color = 'alarm';
                break;
            }
            case AgreementTimeStatusOfMonthly.EXCESS_EXCEPTION_LIMIT_ERROR: {
                overtimeWork1.color = 'error';
                break;
            }
            default: break;
        }

        overtimeWork2.yearMonth = overtimeAgreement.nextMonth;
        let exceptionLimitErrorTime2 = overtimeAgreement.detailNextMonth.confirmed.exceptionLimitErrorTime;
        let limitErrorTime2 = overtimeAgreement.detailNextMonth.confirmed.limitErrorTime;
        if (!_.isNil(exceptionLimitErrorTime2)) {
            overtimeWork2.limitTime = exceptionLimitErrorTime2;
        } else if (!_.isNil(limitErrorTime2)) {
            overtimeWork2.limitTime = limitErrorTime2;
        }
        let agreementTime2 = overtimeAgreement.detailNextMonth.confirmed.agreementTime;
        if (!_.isNil(agreementTime2)) {
            overtimeWork2.actualTime = agreementTime2;
        }

        switch (overtimeAgreement.detailNextMonth.confirmed.status) {
            case AgreementTimeStatusOfMonthly.EXCESS_LIMIT_ALARM: {
                overtimeWork2.color = 'alarm';
                break;
            }
            case AgreementTimeStatusOfMonthly.EXCESS_LIMIT_ERROR: {
                overtimeWork2.color = 'error';
                break;
            }
            case AgreementTimeStatusOfMonthly.NORMAL_SPECIAL: {
                break;
            }
            case AgreementTimeStatusOfMonthly.EXCESS_LIMIT_ALARM_SP: {
                overtimeWork2.color = 'exception';
                break;
            }
            case AgreementTimeStatusOfMonthly.EXCESS_LIMIT_ERROR_SP: {
                overtimeWork2.color = 'exception';
                break;
            }
            case AgreementTimeStatusOfMonthly.EXCESS_EXCEPTION_LIMIT_ALARM: {
                overtimeWork2.color = 'alarm';
                break;
            }
            case AgreementTimeStatusOfMonthly.EXCESS_EXCEPTION_LIMIT_ERROR: {
                overtimeWork2.color = 'error';
                break;
            }
            default: break;
        }

        _.remove(self.overtimeWork);
        self.overtimeWork.push(overtimeWork1);
        self.overtimeWork.push(overtimeWork2);
    }
}
const servicePath = {
    getRecordWork: 'at/request/application/overtime/getRecordWork',
    calculationresultConfirm: 'at/request/application/overtime/calculationresultConfirm',
    getCalculationResultMob: 'at/request/application/overtime/getCalculationResultMob',
    getOvertimeByUI: 'at/request/application/overtime/getOvertimeByUI',
    findByChangeAppDate: 'at/request/application/overtime/findByChangeAppDate',
    checkConvertPrePost: 'at/request/application/overtime/checkConvertPrePost',
    getAppDataDate: 'at/request/application/getAppDataByDate'
};