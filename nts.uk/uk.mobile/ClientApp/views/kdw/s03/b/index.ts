import { Vue, _ } from '@app/provider';
import { component, Prop, Watch } from '@app/core/component';
import { TimeDuration } from '@app/utils';
import { KdwS03DComponent } from 'views/kdw/s03/d';
import { Kdl001Component } from 'views/kdl/001';
import { KDL002Component } from 'views/kdl/002';
import { Cdl008AComponent } from 'views/cdl/s08/a';

@component({
    name: 'kdws03b',
    style: require('./style.scss'),
    template: require('./index.vue'),
    resource: require('./resources.json'),
    validations: {
        screenData: {},
        fixedConstraint: {
            AttendanceTime: { constraint: 'AttendanceTime' },
            AttendanceTimeOfExistMinus: { constraint: 'AttendanceTimeOfExistMinus' },
            WorkTimes: { constraint: 'WorkTimes' },
            WorkTypeCode: { constraint: 'WorkTypeCode' },
            WorkTimeCode: { constraint: 'WorkTimeCode' },
            WorkLocationCD: { constraint: 'WorkLocationCD' },
            EmploymentCode: { constraint: 'EmploymentCode' },
            ClassificationCode: { constraint: 'ClassificationCode' },
            JobTitleCode: { constraint: 'JobTitleCode' },
            WorkplaceCode: { constraint: 'WorkplaceCode' },
            DivergenceReasonContent: { constraint: 'DivergenceReasonContent' },
            BreakTimeGoOutTimes: { constraint: 'BreakTimeGoOutTimes' },
            RecordRemarks: { constraint: 'RecordRemarks' },
            DiverdenceReasonCode: { constraint: 'DiverdenceReasonCode' },
            TimeWithDayAttr: { constraint: 'TimeWithDayAttr' },
            BusinessTypeCode: { constraint: 'BusinessTypeCode' },
            AnyItemAmount: { constraint: 'AnyItemAmount' },
            AnyAmountMonth: { constraint: 'AnyAmountMonth' },
            AnyItemTime: { constraint: 'AnyItemTime' },
            AnyTimeMonth: { constraint: 'AnyTimeMonth' },
            AnyItemTimes: { constraint: 'AnyItemTimes' },
            AnyTimesMonth: { constraint: 'AnyTimesMonth' }
        }
    },
    constraints: [
        'nts.uk.ctx.at.shared.dom.common.time.AttendanceTime',
        'nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeOfExistMinus',
        'nts.uk.ctx.at.record.dom.daily.WorkTimes',
        'nts.uk.ctx.at.schedule.dom.shift.pattern.WorkTypeCode',
        'nts.uk.ctx.at.request.dom.application.holidayshipment.absenceleaveapp.WorkTimeCode',
        'nts.uk.ctx.at.record.dom.worklocation.WorkLocationCD',
        'nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode',
        'nts.uk.ctx.at.schedule.dom.shift.basicworkregister.ClassificationCode',
        'nts.uk.ctx.bs.employee.dom.jobtitle.info.JobTitleCode',
        'nts.uk.shr.com.primitive.WorkplaceCode',
        'nts.uk.ctx.at.record.dom.divergencetime.DivergenceReasonContent',
        'nts.uk.ctx.at.record.dom.daily.breaktimegoout.BreakTimeGoOutTimes',
        'nts.uk.ctx.at.record.dom.daily.remarks.RecordRemarks',
        'nts.uk.ctx.at.record.dom.divergencetime.DiverdenceReasonCode',
        'nts.uk.shr.com.time.TimeWithDayAttr',
        'nts.uk.ctx.at.record.dom.dailyperformanceformat.primitivevalue.BusinessTypeCode',
        'nts.uk.ctx.at.record.dom.daily.optionalitemtime.AnyItemAmount',
        'nts.uk.ctx.at.shared.dom.common.anyitem.AnyAmountMonth',
        'nts.uk.ctx.at.record.dom.daily.optionalitemtime.AnyItemTime',
        'nts.uk.ctx.at.shared.dom.common.anyitem.AnyTimeMonth',
        'nts.uk.ctx.at.record.dom.daily.optionalitemtime.AnyItemTimes',
        'nts.uk.ctx.at.shared.dom.common.anyitem.AnyTimesMonth',
        'nts.uk.ctx.at.record.dom.divergencetime.DiverdenceReasonCode'
    ],
    components: {
        'kdws03d': KdwS03DComponent,
        'kdls01': Kdl001Component,
        'kdls02': KDL002Component,
        'cdls08a': Cdl008AComponent
    },
})
export class KdwS03BComponent extends Vue {
    @Prop({
        default: () => ({
            employeeID: '',
            employeeName: '',
            date: new Date(),
            rowData: {},
            paramData: {}
        })
    })
    public readonly params!: {
        employeeID: string,
        employeeName: string,
        date: Date,
        rowData: any,
        paramData: any
    };
    public checked1s: Array<number> = [];
    public screenData: any = {};
    public screenData1: any = {};
    private masterData: any = {
        workType: [],
        workTime: [],
        servicePlace: [],
        reason: [],
        workPlace: [],
        classification: [],
        possition: [],
        employment: [],
        lstDoWork: [],
        lstCalc: [],
        lstCalcCompact: [],
        lstReasonGoOut: [],
        lstTimeLimit: [],
        businessType: []
    };
    private masterDialogParam: Array<number> = [];
    private oldData: any = [];
    private listCareError: any = [];
    private listCareInputError: any = [];
    private listErAlHolidays: any = [];
    private listCheck28: any = [];
    private listCheckDeviation: any = [];
    private listErrorMonth: any = [];

    get lstAttendanceItem() {
        let self = this;

        return self.params.paramData.lstControlDisplayItem.lstAttendanceItem;
    }

    get contentType() {
        let self = this;

        return self.params.paramData.lstControlDisplayItem.lstHeader;
    }

    get itemValues() {
        let self = this;

        return self.params.paramData.itemValues;
    }

    get itemType() {
        return ItemType;
    }

    get masterType() {
        return MasterType;
    }

    public created() {
        let self = this;
        if (self.params.rowData.sign) {
            self.checked1s.push(2);
        }
        self.$mask('show');
        self.addCustomValid();
        self.oldData = self.toJS(self.screenData[0]);
        self.createMasterComboBox();
        self.$http.post('at', API.masterDialogData, {
            types: self.masterDialogParam,
            date: new Date()
        }).then((masterData: any) => {
            self.createMasterData(masterData.data);
            self.$mask('hide');
        }).catch((res: any) => {
            self.$mask('hide');
            self.$modal.error(res.messageId)
                .then(() => {
                    self.$close();
                });
        });
    }

    public beforeUpdate() {
        let self = this;
        if (self.validations.fixedConstraint) {
            self.addCustomConstraint();
        }
    }

    public getLockContent() {
        let self = this;
        let data: any = self.params.rowData.state;
        if (data != '') {
            let lock = data.split('|');
            let tempD = `<span>`;
            _.forEach(lock, (char) => {
                switch (char) {
                    case 'D':
                        tempD += self.$i18n('KDW003_66') + `<br/>`;
                        break;
                    case 'M':
                        tempD += self.$i18n('KDW003_66') + `<br/>`;
                        break;
                    case 'C':
                        tempD += self.$i18n('KDW003_67') + `<br/>`;
                        break;
                    case 'S':
                        tempD += self.$i18n('KDW003_113') + `<br/>`;
                        break;
                    case 'CM':
                        tempD += self.$i18n('KDW003_112') + `<br/>`;
                        break;
                    case 'AM':
                        tempD += self.$i18n('KDW003_68') + `<br/>`;
                        break;
                    case 'H':
                        tempD += self.$i18n('KDW003_70') + `<br/>`;
                        break;
                    case 'A':
                        tempD += self.$i18n('KDW003_69') + `<br/>`;
                        break;
                    default:
                        break;
                }
            });
            tempD += `</span>`;

            return tempD;
        }
    }

    private createMasterComboBox() {
        let self = this;
        let lstControlDisplayItem: any = self.params.paramData.lstControlDisplayItem;
        _.forEach(lstControlDisplayItem.comboItemDoWork, (o) => {
            self.masterData.lstDoWork.push({ code: o.code, name: o.name });
        });
        _.forEach(lstControlDisplayItem.comboItemCalc, (o) => {
            self.masterData.lstCalc.push({ code: o.code, name: o.name });
        });
        _.forEach(lstControlDisplayItem.comboItemCalcCompact, (o) => {
            self.masterData.lstCalcCompact.push({ code: o.code, name: o.name });
        });
        _.forEach(lstControlDisplayItem.comboItemReason, (o) => {
            self.masterData.lstReasonGoOut.push({ code: o.code, name: o.name });
        });
        _.forEach(lstControlDisplayItem.comboTimeLimit, (o) => {
            self.masterData.lstTimeLimit.push({ code: o.code, name: o.name });
        });
    }

    private createMasterData(data: any) {
        let self = this;
        self.masterData.workTime = data[MasterType.KDLS01_WorkTime];
        self.masterData.workType = data[MasterType.KDLS02_WorkType];
        self.masterData.workPlace = data[MasterType.CDLS08_WorkPlace];
        // tạo dữ liệu conbo box tạm thời cho các dialog chưa dc làm
        _.forEach(data[MasterType.KDLS10_ServicePlace], (o) => {
            self.masterData.servicePlace.push({ code: o.code, name: o.name });
        });
        if (_.isEmpty(self.masterData.servicePlace)) {
            self.masterData.servicePlace.push({ code: '', name: 'なし' });
        }
        _.forEach(data[MasterType.KDLS32_Reason], (o) => {
            self.masterData.reason.push({ code: o.code, name: o.name });
        });
        if (_.isEmpty(self.masterData.reason)) {
            self.masterData.reason.push({ code: '', name: 'なし' });
        }
        _.forEach(data[MasterType.KCPS02_Classification], (o) => {
            self.masterData.classification.push({ code: o.code, name: o.name });
        });
        if (_.isEmpty(self.masterData.classification)) {
            self.masterData.classification.push({ code: '', name: 'なし' });
        }
        _.forEach(data[MasterType.KCPS03_Possition], (o) => {
            self.masterData.possition.push({ code: o.code, name: o.name });
        });
        if (_.isEmpty(self.masterData.possition)) {
            self.masterData.possition.push({ code: '', name: 'なし' });
        }
        _.forEach(data[MasterType.KCPS01_Employment], (o) => {
            self.masterData.employment.push({ code: o.code, name: o.name });
        });
        if (_.isEmpty(self.masterData.employment)) {
            self.masterData.employment.push({ code: '', name: 'なし' });
        }
        _.forEach(data[MasterType.KCP001_BusinessType], (o) => {
            self.masterData.businessType.push({ code: o.code, name: o.name });
        });
        if (_.isEmpty(self.masterData.businessType)) {
            self.masterData.businessType.push({ code: '', name: 'なし' });
        }
    }

    private formatData(rowData: RowData) {
        let self = this;
        let attendanceItem = self.getAttendanceItem(rowData.key);
        switch (attendanceItem.attendanceAtr) {
            case ItemType.InputNumber:
                rowData.value = _.isEmpty(rowData.value) ? null : _.toNumber(rowData.value);
                break;
            case ItemType.InputMoney:
                rowData.value = _.isEmpty(rowData.value) ? null : _.toNumber(rowData.value);
                break;
            case ItemType.Time:
                rowData.value = _.isEmpty(rowData.value) ? null : new TimeDuration(rowData.value).toNumber();
                break;
            case ItemType.TimeWithDay:
                rowData.value = _.isEmpty(rowData.value) ? null : new TimeDuration(rowData.value).toNumber();
                break;
            default:
                break;
        }
    }

    public getItemType(key: string) {
        let self = this;
        let attendanceItem = self.getAttendanceItem(key);

        return attendanceItem.attendanceAtr;
    }

    public getItemText(key: string) {
        let self = this;

        return _.find(self.contentType, (item: ItemHeader) => item.key == key).headerText;
    }

    public getItemMasterType(key: string) {
        let self = this;
        let attendanceItem = self.getAttendanceItem(key);

        return attendanceItem.typeGroup;
    }

    public isSpecCalcLst(key: string) {
        let self = this;
        let specLst = [628, 630, 631, 632];
        let attendanceItem = self.getAttendanceItem(key);

        return _.includes(specLst, attendanceItem.id);
    }

    public getIcon(key: string) {
        let self = this;
        let rowClass = _.find(self.params.rowData.rowData, (rowData: RowData) => rowData.key == key).class;
        if (rowClass.includes('mgrid-error')) {
            return 'fas fa-exclamation-circle align-bottom text-danger';
        }
        if (rowClass.includes('mgrid-alarm')) {
            return 'fas fa-exclamation-triangle align-bottom text-danger';
        }

        return '';
    }

    public getBackGroundColor(key: string) {
        let self = this;
        let rowClass = _.find(self.params.rowData.rowData, (rowData: RowData) => rowData.key == key).class;
        if (rowClass.includes('mgrid-error')) {
            return 'uk-bg-schedule-no-empl-insurance';
        }
        if (rowClass.includes('mgrid-alarm')) {
            return 'uk-bg-schedule-work-by-dow';
        }

        return '';    
    }

    public getItemDialogName(key: string) {
        let self = this;
        let rowData = _.find(self.params.rowData.rowData, (rowData: RowData) => rowData.key == key);
        let attendanceItem = self.getAttendanceItem(key);
        let item: any = {};
        switch (attendanceItem.typeGroup) {
            case MasterType.KDLS02_WorkType:
                item = _.find(self.masterData.workType, (o) => o.code == rowData.value0);
                if (item) {
                    return item.name;
                } else {
                    return rowData.value;
                }
            case MasterType.KDLS01_WorkTime: 
                item = _.find(self.masterData.workTime, (o) => o.code == rowData.value0); 
                if (item) {
                    return item.name;
                } else {
                    return rowData.value;
                }
            case MasterType.CDLS08_WorkPlace: 
                item = _.find(self.masterData.workPlace, (o) => o.code == rowData.value0); 
                if (item) {
                    return item.name;
                } else {
                    return rowData.value;
                }
            default: 
                break;
        }
    }

    private addCustomValid() {
        let self = this;
        let screenDataValid: any = {};
        _.forEach(self.params.rowData.rowData, (rowData: RowData, index) => {
            self.formatData(rowData);
            self.addMasterDialogParam(rowData);
            let attendanceItem = self.getAttendanceItem(rowData.key);
            let contraint = _.find(self.contentType, (item: ItemHeader) => item.key == rowData.key).constraint;
            switch (attendanceItem.attendanceAtr) {
                case ItemType.InputStringCode:
                    self.$set(self.screenData1, rowData.key, rowData.value0);
                    break;
                case ItemType.ButtonDialog:
                    self.$set(self.screenData1, rowData.key, rowData.value0);
                    break;
                case ItemType.InputNumber:
                    self.$set(self.screenData1, rowData.key, rowData.value);
                    if (contraint.cdisplayType == 'Primitive') {
                        screenDataValid[rowData.key] = {
                            loop: true,
                            required: contraint.required
                        };
                    } else {
                        screenDataValid[rowData.key] = {
                            loop: true,
                            required: contraint.required,
                            min: _.toNumber(contraint.min),
                            max: _.toNumber(contraint.max)
                        };
                    }
                    break;
                case ItemType.InputMoney:
                    self.$set(self.screenData1, rowData.key, rowData.value);
                    if (contraint.cdisplayType == 'Primitive') {
                        screenDataValid[rowData.key] = {
                            loop: true,
                            required: contraint.required
                        };
                    } else {
                        screenDataValid[rowData.key] = {
                            loop: true,
                            required: contraint.required,
                            min: _.toNumber(contraint.min),
                            max: _.toNumber(contraint.max),
                            valueType: 'Integer'
                        };
                    }
                    break;
                case ItemType.ComboBox:
                    self.$set(self.screenData1, rowData.key, rowData.value0);
                    break;
                case ItemType.Time:
                    self.$set(self.screenData1, rowData.key, rowData.value);
                    if (contraint.cdisplayType == 'Primitive') {
                        screenDataValid[rowData.key] = {
                            loop: true,
                            required: contraint.required
                        };
                    } else {
                        screenDataValid[rowData.key] = {
                            loop: true,
                            required: contraint.required,
                            min: new TimeDuration(contraint.min).toNumber(),
                            max: new TimeDuration(contraint.max).toNumber(),
                            valueType: 'Duration'
                        };
                    }
                    break;
                case ItemType.InputStringChar:
                    self.$set(self.screenData1, rowData.key, rowData.value);
                    if (contraint.cdisplayType == 'Primitive') {
                        screenDataValid[rowData.key] = {
                            loop: true,
                            required: contraint.required
                        };
                    } else {
                        screenDataValid[rowData.key] = {
                            loop: true,
                            required: contraint.required
                        };
                    }
                    break;
                default:
                    self.$set(self.screenData1, rowData.key, rowData.value);
                    break;
            }
        });
        self.screenData = [self.screenData1];
        self.$updateValidator('screenData', screenDataValid);
        // self.$updateValidator(`screenData.${index}`, newObj);
    }

    public addCustomConstraint() {
        let self = this;
        _.forEach(self.params.rowData.rowData, (rowData: RowData, index) => {
            let attendanceItem = self.getAttendanceItem(rowData.key);
            let contraint = _.find(self.contentType, (item: ItemHeader) => item.key == rowData.key).constraint;
            let constraintObj: any = {};
            switch (attendanceItem.attendanceAtr) {
                case ItemType.InputNumber:
                    if (contraint.cdisplayType == 'Primitive') {
                        constraintObj = _.get(self.validations.fixedConstraint, PrimitiveAll['No' + attendanceItem.primitive]);
                        constraintObj.loop = true;
                        constraintObj.required = contraint.required;
                        self.$updateValidator( `screenData.${rowData.key}`, constraintObj);
                    } 
                    break;
                case ItemType.InputMoney:
                    if (contraint.cdisplayType == 'Primitive') {
                        constraintObj = _.get(self.validations.fixedConstraint, PrimitiveAll['No' + attendanceItem.primitive]);
                        constraintObj.loop = true;
                        constraintObj.required = contraint.required;
                        self.$updateValidator( `screenData.${rowData.key}`, constraintObj);
                    } 
                    break;
                case ItemType.Time:
                    if (contraint.cdisplayType == 'Primitive') {
                        constraintObj = _.get(self.validations.fixedConstraint, PrimitiveAll['No' + attendanceItem.primitive]);
                        constraintObj.loop = true;
                        constraintObj.required = contraint.required;
                        self.$updateValidator( `screenData.${rowData.key}`, constraintObj);
                    } 
                    break;
                case ItemType.InputStringChar:
                    if (contraint.cdisplayType == 'Primitive') {
                        constraintObj = _.get(self.validations.fixedConstraint, PrimitiveAll['No' + attendanceItem.primitive]);
                        constraintObj.loop = true;
                        constraintObj.required = contraint.required;
                        self.$updateValidator( `screenData.${rowData.key}`, constraintObj);
                        // self.$updateValidator( {screenData : { [rowData.key] : constraintObj}});
                    }
                    break;
                default:
                    break;
            }
        });
        delete self.validations.fixedConstraint;
    }

    private addMasterDialogParam(rowData: RowData) {
        let self = this;
        let idKey = rowData.key.replace('A', '');
        let attendanceItem = _.find(self.lstAttendanceItem, (item: any) => item.id == idKey);
        switch (attendanceItem.attendanceAtr) {
            case ItemType.InputStringCode:
                if (!_.includes(self.masterDialogParam, attendanceItem.typeGroup)) {
                    self.masterDialogParam.push(attendanceItem.typeGroup);
                }
                break;
            case ItemType.ButtonDialog:
                if (!_.includes(self.masterDialogParam, attendanceItem.typeGroup)) {
                    self.masterDialogParam.push(attendanceItem.typeGroup);
                }
                break;
            case ItemType.ComboBox:
                if (!_.includes(self.masterDialogParam, attendanceItem.typeGroup)) {
                    self.masterDialogParam.push(attendanceItem.typeGroup);
                }
                break;
            default: break;
        }
    }

    public openDScreen() {
        let self = this;
        self.$modal('kdws03d', {
            employeeID: self.params.employeeID,
            employeeName: self.params.employeeName,
            startDate: self.params.date,
            endDate: self.params.date
        }, { type: 'dropback' })
            .then((v) => {

            });
    }

    public openDialog(key: string) {
        let self = this;
        let type: MasterType = self.getItemMasterType(key);
        switch (type) {
            case MasterType.KDLS02_WorkType: self.openKDLS02(key); break;
            case MasterType.KDLS01_WorkTime: self.openKDLS01(key); break;
            case MasterType.CDLS08_WorkPlace: self.openCDLS08(key); break;
            default: break;
        }
    }

    private openKDLS02(key: string) {
        let self = this;
        let rowData = _.find(self.params.rowData.rowData, (o) => o.key == key);
        let selectedCD = self.screenData[0][key];
        let workTypeCDLst = _.map(self.masterData.workType, (o) => o.code);
        self.$modal(
            'kdls02',
            {
                seledtedWkTypeCDs: workTypeCDLst,
                selectedWorkTypeCD: selectedCD,
                isAddNone: false,
                seledtedWkTimeCDs: null,
                selectedWorkTimeCD: null,
                isSelectWorkTime: false
            }
        ).then((data: any) => {
            if (data) {
                self.screenData[0][key] = data.selectedWorkType.workTypeCode;
                rowData.value0 = data.selectedWorkType.workTypeCode;
                rowData.value = _.find(self.masterData.workType, (o) => o.code == rowData.value0).name;
            }
        });
    }

    private openKDLS01(key: string) {
        let self = this;
        let rowData = _.find(self.params.rowData.rowData, (o) => o.key == key);
        let selectedCD = self.screenData[0][key];
        let workTimeCDLst = _.map(self.masterData.workTime, (o) => o.code);
        self.$modal(
            'kdls01',
            {
                isAddNone: true,
                seledtedWkTimeCDs: workTimeCDLst,
                selectedWorkTimeCD: selectedCD,
                isSelectWorkTime: false
            }
        ).then((data: any) => {
            if (data) {
                self.screenData[0][key] = data.selectedWorkTime.code;
                rowData.value0 = data.selectedWorkTime.code;
                rowData.value = _.find(self.masterData.workTime, (o) => o.code == rowData.value0).name;
            }
        });
    }

    private openCDLS08(key: string) {
        let self = this;
        let id = '';
        let rowData = _.find(self.params.rowData.rowData, (o) => o.key == key);
        let selectedItem: any = _.find(self.masterData.workPlace, (o) => o.code == self.screenData[0][key]);
        if (!_.isUndefined(selectedItem)) {
            id = selectedItem.id;
        }
        self.$modal(
            'cdls08a',
            {
                workPlaceType: 0,
                startMode: false,
                baseDate: self.params.date,
                systemType: 2,
                referenceRangeNarrow: true,
                selectedItem: [id],
                isSelectionRequired: true
            },
            {
                title: 'CDLS08_1'
            }
        ).then((data: any) => {
            if (data) {
                let selectedWkp = _.find(self.masterData.workPlace, (o) => o.id == data.workplaceId);
                self.screenData[0][key] = selectedWkp.code;
                rowData.value0 = selectedWkp.code;
                rowData.value = selectedWkp.name;
            }

            return 0;
        });
    }

    public isEnableRegister() {
        let self = this;
        if (!self.params.paramData.showPrincipal) {
            return self.$valid;
        }

        return self.$valid && self.params.paramData.showPrincipal && !_.isEmpty(self.checked1s);
    }

    public register() {
        let self = this;
        let registerParam = self.createRegisterParam();
        if (_.isEmpty(registerParam.itemValues)) {
            return;
        }
        self.$mask('show');
        self.$http.post('at', API.register, registerParam)
            .then((data: any) => {
                let dataAfter = data.data;
                if ((_.isEmpty(dataAfter.errorMap) && dataAfter.errorMap[5] == undefined)) {
                    if (!_.isEmpty(dataAfter.messageAlert) && dataAfter.messageAlert == 'Msg_15') {
                        self.$modal.info('Msg_15');
                    }
                    if (dataAfter.errorMap[6] != undefined) {
                        self.$modal.info('Msg_1455');
                    }
                } else {
                    let errorAll = false,
                        errorReleaseCheckbox = false, errorMonth = false;
                    let errorNoReload = true;
                    if (dataAfter.errorMap[6] != undefined) {
                        errorReleaseCheckbox = true;
                    }
                    if (dataAfter.errorMap[0] != undefined) {
                        self.listCareError(dataAfter.errorMap[0]);
                        errorAll = true;
                    }
                    if (dataAfter.errorMap[1] != undefined) {
                        self.listCareInputError(dataAfter.errorMap[1]);
                        errorAll = true;
                    }
                    if (dataAfter.errorMap[2] != undefined) {
                        self.listErAlHolidays = dataAfter.errorMap[2];
                        errorNoReload = false;
                    }
                    if (dataAfter.errorMap[3] != undefined) {
                        self.listCheck28(dataAfter.errorMap[3]);
                        errorAll = true;
                    }
                    if (dataAfter.errorMap[4] != undefined) {
                        self.listCheckDeviation = dataAfter.errorMap[4];
                        errorAll = true;
                    }
                    if (dataAfter.errorMap[5] != undefined) {
                        self.listErrorMonth = dataAfter.errorMap[5];
                        errorMonth = true;
                        errorAll = true;
                    }
                    if (dataAfter.errorMap[7] != undefined) {
                        errorAll = true;
                    }
                    if (!_.isEmpty(dataAfter.messageAlert) && dataAfter.messageAlert == 'Msg_15') {
                        if (errorReleaseCheckbox) {
                            self.$modal.info('Msg_1455').then(() => {
                                self.$modal.info('Msg_15').then(() => {
                                    if (dataAfter.showErrorDialog) {
                                        self.showErrorDialog();
                                    }
                                });
                            });
                        } else {
                            self.$modal.info('Msg_15').then(() => {
                                if (dataAfter.showErrorDialog) {
                                    self.showErrorDialog();
                                }
                            });
                        }
                    } else {
                        let errorShowMessage = errorAll;
                        if (errorShowMessage && errorReleaseCheckbox) {
                            self.$modal.info('Msg_1455').then(() => {
                                self.showErrorDialog();
                            });
                        } else if (errorShowMessage) {
                            self.showErrorDialog();
                        } else if (errorReleaseCheckbox) {
                            self.$modal.info('Msg_1455');
                        } else {
                            if (dataAfter.showErrorDialog) {
                                self.showErrorDialog();
                            }
                        }
                    }
                }
                self.$mask('hide');
            }).catch((res: any) => {
                self.$mask('hide');
                self.$modal.error(res.messageId)
                    .then(() => {
                        self.$close();
                    });
            });
    }

    private showErrorDialog(messageAlert?: string) {
        let self = this;
        let lstEmployee = [];
        let errorValidateScreeen: any = [];

        _.each(self.listCareError(), (value) => {
            let object = { date: '', employeeCode: '', employeeName: '', message: self.$i18n('Msg_996'), itemName: '', columnKey: value.itemId };
            errorValidateScreeen.push(object);
        });

        _.each(self.listCareInputError(), (value) => {
            let object = { date: '', employeeCode: '', employeeName: '', message: value.message, itemName: '', columnKey: value.itemId };
            let item = _.find(self.contentType, (data) => {
                return String(data.key) === 'A' + value.itemId;
            });
            object.itemName = (item == undefined) ? '' : item.headerText;
            let itemOtherInGroup = CHECK_INPUT[value.itemId + ''];
            let itemGroup = self.params.paramData.lstControlDisplayItem.itemInputName[Number(itemOtherInGroup)];
            let nameGroup: any = (itemGroup == undefined) ? '' : itemGroup;
            object.message = self.$i18n(value.message, [object.itemName, nameGroup]);
            errorValidateScreeen.push(object);
        });

        _.each(self.listCheck28(), (value) => {

            let object = { date: '', employeeCode: '', employeeName: '', message: value.layoutCode, itemName: '', columnKey: value.itemId };
            let item = _.find(self.contentType, (data) => {
                if (data.group != undefined && data.group != null) {
                    return String(data.group[0].key) === 'Code' + value.itemId;
                } else {
                    return String(data.key) === 'A' + value.itemId;
                }
            });
            object.itemName = (item == undefined) ? '' : item.headerText;
            errorValidateScreeen.push(object);
        });

        _.each(self.listCheckDeviation, (value) => {
            let object = { date: '', employeeCode: '', employeeName: '', message: value.valueType, itemName: '', columnKey: value.itemId };
            let item = _.find(self.contentType, (data) => {
                if (data.group != undefined && data.group != null) {
                    return String(data.group[0].key) === 'Code' + value.itemId;
                } else {
                    return data.key != undefined && String(data.key) === 'A' + value.itemId;
                }
            });
            object.itemName = (item == undefined) ? '' : item.headerText;
            object.message = self.$i18n('Msg_996', [object.itemName, value.value]);
            errorValidateScreeen.push(object);
        });

        _.each(self.listErrorMonth, (value) => {
            let object = { date: '', employeeCode: '', employeeName: '', value, message: value.message, columnKey: '' };
            errorValidateScreeen.push(object);
        });
    }

    private createRegisterParam() {
        let self = this;
        let itemValues: any = self.getItemChange();
        let checkValue = false;
        if (!_.isEmpty(self.checked1s)) {
            checkValue = true;
        }
        let dataCheckSign = [
            {
                rowId: self.params.rowData.id,
                itemId: '',
                value: checkValue,
                valueType: '',
                employeeId: self.params.employeeID,
                date: self.params.date,
                flagRemoveAll: false
            }
        ];

        return {
            'employeeId': self.params.employeeID,
            'itemValues': itemValues,
            'dataCheckSign': dataCheckSign,
            'dataCheckApproval': [],
            'dateRange': {
                'startDate': self.params.date,
                'endDate': self.params.date
            },
            'lstNotFoundWorkType': []
        };
    }

    private getItemChange() {
        let self = this;
        let itemValues: any = [];
        _.forEach(Object.keys(self.screenData[0]), (key: string) => {
            let itemValue: DPItemValue;
            let attendanceItem = self.getAttendanceItem(key);
            let rowData = _.find(self.params.rowData.rowData, (o) => o.key == key);
            let oldRow = self.oldData[key];
            if (JSON.stringify(oldRow).localeCompare(JSON.stringify(self.screenData[0][key])) != 0) {
                switch (attendanceItem.attendanceAtr) {
                    case ItemType.InputStringCode:
                        itemValue = new DPItemValue(attendanceItem, self.screenData[0][key], self.params, self.itemValues);
                        break;
                    case ItemType.ButtonDialog:
                        itemValue = new DPItemValue(attendanceItem, self.screenData[0][key], self.params, self.itemValues);
                        break;
                    case ItemType.InputNumber:
                        rowData.value = self.screenData[0][key];
                        itemValue = new DPItemValue(attendanceItem, self.screenData[0][key], self.params, self.itemValues);
                        break;
                    case ItemType.InputMoney:
                        rowData.value = self.screenData[0][key];
                        itemValue = new DPItemValue(attendanceItem, self.screenData[0][key], self.params, self.itemValues);
                        break;
                    case ItemType.ComboBox:
                        itemValue = new DPItemValue(attendanceItem, parseInt(self.screenData[0][key]), self.params, self.itemValues);
                        break;
                    case ItemType.Time:
                        rowData.value = self.screenData[0][key];
                        itemValue = new DPItemValue(attendanceItem, self.screenData[0][key], self.params, self.itemValues);
                        break;
                    case ItemType.TimeWithDay:
                        itemValue = new DPItemValue(attendanceItem, self.screenData[0][key], self.params, self.itemValues);
                        break;
                    case ItemType.InputStringChar:
                        rowData.value = self.screenData[0][key];
                        itemValue = new DPItemValue(attendanceItem, self.screenData[0][key], self.params, self.itemValues);
                        break;
                    default:
                        break;
                }
                itemValues.push(itemValue);
            }
        });    

        return itemValues;
    }

    private getAttendanceItem(value: any) {
        let self = this;
        let idKey = value.replace('A', '');

        return _.find(self.lstAttendanceItem, (item: any) => item.id == idKey);
    }

    private autoCalc(key: string) {
        let self = this;
        let attendanceItem = self.getAttendanceItem(key);
        let itemValues = self.getItemChange();
        if (_.isEmpty(itemValues)) {
            return;
        }
        _.forEach(itemValues, (item) => {
            if (item.itemId != attendanceItem.id) {
                item.columnKey = 'USE';
            }
        });
        let param = {
            dailyEdits: [],
            itemEdits: itemValues,
            changeSpr31: false,
            changeSpr34: false,
            notChangeCell: false
        };
        self.$http.post('at', API.linkItemCalc, param)
        .then((data: any) => {
            _.forEach(data.data.cellEdits, (item: any) => {
                if (!_.isUndefined(self.screenData1[item.item])) {
                    let attendanceItemLoop = self.getAttendanceItem(item.item);
                    switch (attendanceItemLoop.attendanceAtr) {
                        case ItemType.InputNumber:
                            self.screenData1[item.item] = _.isEmpty(item.value) ? null : _.toNumber(item.value);
                            break;
                        case ItemType.InputMoney:
                            self.screenData1[item.item] = _.isEmpty(item.value) ? null : _.toNumber(item.value);
                            break;
                        case ItemType.Time:
                            self.screenData1[item.item] = _.isEmpty(item.value) ? null : new TimeDuration(item.value).toNumber();
                            break;
                        case ItemType.TimeWithDay:
                            self.screenData1[item.item] = _.isEmpty(item.value) ? null : new TimeDuration(item.value).toNumber();
                            break;
                        default:
                            self.screenData1[item.item] = item.value;
                            break;
                    }
                }
            });
        });
    }

    @Watch('screenData1.A28')
    public watcherA28(value: any) {
        let self = this;
        self.autoCalc('A28');    
    }

    @Watch('screenData1.A29')
    public watcherA29(value: any) {
        let self = this;
        self.autoCalc('A29');    
    }

    @Watch('screenData1.A31')
    public watcherA31(value: any) {
        let self = this;
        self.autoCalc('A31');    
    }

    @Watch('screenData1.A34')
    public watcherA34(value: any) {
        let self = this;
        self.autoCalc('A34');    
    }

    @Watch('screenData1.A41')
    public watcherA41(value: any) {
        let self = this;
        self.autoCalc('A41');    
    }

    @Watch('screenData1.A44')
    public watcherA44(value: any) {
        let self = this;
        self.autoCalc('A44');    
    }
}

const API = {
    getPrimitiveAll: 'screen/at/correctionofdailyperformance/getPrimitiveAll',
    masterDialogData: 'screen/at/correctionofdailyperformance/getMasterDialogMob',
    register: 'screen/at/correctionofdailyperformance/addUpMobile',
    linkItemCalc: 'screen/at/correctionofdailyperformance/calcTime'
};

const CHECK_INPUT = {
    '759': '760', '760': '759', '761': '762',
    '762': '761', '763': '764', '764': '763',
    '765': '766', '766': '765', '157': '159',
    '159': '157', '163': '165', '165': '163',
    '169': '171', '171': '169',
    '175': '177', '177': '175', '181': '183',
    '183': '181', '187': '189', '189': '187',
    '193': '195', '195': '193', '199': '201',
    '201': '199', '205': '207', '207': '205',
    '211': '213', '213': '211',
    '7': '8', '8': '7', '9': '10',
    '10': '9', '11': '12', '12': '11',
    '13': '14', '14': '13', '15': '16',
    '16': '15',
    '17': '18', '18': '17', '19': '20',
    '20': '19', '21': '22', '22': '21',
    '23': '24', '24': '23', '25': '26',
    '26': '25'
};

export enum PrimitiveAll {
    No1 = 'AttendanceTime',
    No2 = 'AttendanceTimeOfExistMinus',
    No3 = 'WorkTimes',
    No4 = 'WorkTypeCode',
    No5 = 'WorkTimeCode',
    No6 = 'WorkLocationCD',
    No7 = 'EmploymentCode',
    No8 = 'ClassificationCode',
    No9 = 'JobTitleCode',
    No10 = 'WorkplaceCode',
    No11 = 'DivergenceReasonContent',
    No12 = 'BreakTimeGoOutTimes',
    No13 = 'RecordRemarks',
    No14 = 'DiverdenceReasonCode',
    No15 = 'TimeWithDayAttr',
    No21 = 'BusinessTypeCode',
    No54 = 'AnyItemAmount',
    No55 = 'AnyAmountMonth',
    No56 = 'AnyItemTime',
    No57 = 'AnyTimeMonth',
    No58 = 'AnyItemTimes',
    No59 = 'AnyTimesMonth',
    No60 = 'DiverdenceReasonCode',
}

export enum ItemType {
    InputStringCode = 0,
    ButtonDialog = 1,
    InputNumber = 2,
    InputMoney = 3,
    ComboBox = 4,
    Time = 5,
    TimeWithDay = 6,
    InputStringChar = 7
}

export enum MasterType {
    KDLS02_WorkType = 1,
    KDLS01_WorkTime = 2,
    KDLS10_ServicePlace = 3,
    KDLS32_Reason = 4,
    CDLS08_WorkPlace = 5,
    KCPS02_Classification = 6,
    KCPS03_Possition = 7,
    KCPS01_Employment = 8,
    DoWork = 9,
    Calc = 10,
    ReasonGoOut = 11,
    Remasks = 12,
    TimeLimit = 13,
    KCP001_BusinessType = 14
}

interface RowData {
    class: any;
    key: string;
    value: any;
    groupKey: string;
    value0: any;
}

interface ItemHeader {
    color: string;
    constraint: ItemConstraint;
    headerText: string;
    key: string;
}

interface ItemConstraint {
    cdisplayType: string;
    max: string;
    min: string;
    primitiveValue: any;
    required: boolean;
    values: any;
}

class DPItemValue {
    public rowId: string;
    public columnKey: string;
    public itemId: number;
    public value: string;
    public valueType: string;
    public layoutCode: string;
    public employeeId: string;
    public date: Date;
    public typeGroup: number;
    public message: string;

    constructor(attendanceItem: any, value: any, params: any, itemValues: any) {
        this.rowId = params.rowId;
        this.columnKey = '';
        this.itemId = attendanceItem.id;
        this.value = value;
        this.valueType = _.find(itemValues, (o) => o.itemId == this.itemId).valueType;
        this.layoutCode = _.find(itemValues, (o) => o.itemId == this.itemId).layoutCode;
        this.employeeId = params.employeeID;
        this.date = params.date;
        this.typeGroup = attendanceItem.typeGroup;
        this.message = '';
    }

}   