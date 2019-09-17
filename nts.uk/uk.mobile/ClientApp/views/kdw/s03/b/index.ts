import { Vue, _ } from '@app/provider';
import { component, Prop, Watch } from '@app/core/component';
import { KdwS03DComponent } from 'views/kdw/s03/d';
import { Kdl001Component } from 'views/kdl/001';
import { KDL002Component } from 'views/kdl/002';
import { TimeDuration } from '@app/utils';

@component({
    name: 'kdws03b',
    style: require('./style.scss'),
    template: require('./index.vue'),
    resource: require('./resources.json'),
    validations: {
        screenData: {}
    },
    constraints: [],
    components: {
        'kdws03d': KdwS03DComponent,
        'kdls01': Kdl001Component,
        'kdls02': KDL002Component
    },
})
export class KdwS03BComponent extends Vue {
    @Prop({ default: () => ({ 
        rowId: '',
        employeeID: '',
        employeeName: '', 
        date: new Date(), 
        data: {}, 
        paramData: {}
    }) })
    public readonly params!: {
        rowId: string, 
        employeeID: string,
        employeeName: string, 
        date: Date, 
        data: Array<RowData>, 
        paramData: any
    };
    public checked1s: Array<number> = [];
    public screenData: any = {};
    private masterData: any = {
        workType: [],
        workTime: [],
        workPlace: [],
        lstDoWork: [],
        lstCalc: [],
        lstCalcCompact: [],
        lstReasonGoOut: [],
        lstTimeLimit: []
    };
    private masterDialogParam: Array<number> = [];
    private primitiveAll: any = {};
    private oldData: any = [];

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
        let fakeValid = {};
        _.forEach(self.params.data, (rowData: RowData, index) => {
            self.formatData(rowData);
            self.setItemType(rowData);
            self.setItemText(rowData);
            self.setItemMasterType(rowData);
            self.setSpecCalcLst(rowData);
            self.setColorCode(rowData);
            self.addMasterDialogParam(rowData);
            fakeValid[rowData.key] = { required: false };
        });
        self.oldData = self.toJS(self.params.data);
        self.$updateValidator('screenData', fakeValid);
        self.createMasterComboBox();
        self.$http.post('at', API.getPrimitiveAll)
        .then((primitiveData: any) => {
            // self.createPrimitiveAll(primitiveData.data);
            self.addCustomValid();

            return self.$http.post('at', API.masterDialogData, {
                types: self.masterDialogParam,
                date: new Date()
            });
        }).then((masterData: any) => {
            self.createMasterData(masterData.data);    
        }).catch((res: any) => {
            console.log('FAIL');
        });
    }

    private createPrimitiveAll(data: any) {
        let self = this;
        let primitiveAll = [];
        _.forEach(data.keys(), (o) => { 
            primitiveAll.push({ 'id': o, 'value': data[o] });
        });
        self.primitiveAll(primitiveAll);
    }

    private createMasterComboBox() {
        let self = this;
        let lstControlDisplayItem: any = self.params.paramData.lstControlDisplayItem;
        _.forEach(lstControlDisplayItem.comboItemDoWork, (o) => { 
            self.masterData.lstDoWork.push({ 'value0': o.code, 'value': o.name });
        });
        _.forEach(lstControlDisplayItem.comboItemCalc, (o) => { 
            self.masterData.lstCalc.push({ 'value0': o.code, 'value': o.name });
        });
        _.forEach(lstControlDisplayItem.comboItemCalcCompact, (o) => { 
            self.masterData.lstCalcCompact.push({ 'value0': o.code, 'value': o.name });
        });
        _.forEach(lstControlDisplayItem.comboItemReason, (o) => { 
            self.masterData.lstReasonGoOut.push({ 'value0': o.code, 'value': o.name });
        });
        _.forEach(lstControlDisplayItem.comboTimeLimit, (o) => { 
            self.masterData.lstTimeLimit.push({ 'value0': o.code, 'value': o.name });
        });
    }

    private createMasterData(data: any) {
        let self = this;
        self.masterData.workTime = data[MasterType.KDLS01_WorkTime];
        self.masterData.workType = data[MasterType.KDLS02_WorkType];
        self.masterData.workPlace = data[MasterType.CDLS08_WorkPlace];
    }

    private formatData(rowData: RowData) {
        let self = this;
        let attendanceItem = self.getAttendanceItem(rowData.key);
        switch (attendanceItem.attendanceAtr) {
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

    private setItemType(rowData: RowData) {
        let self = this;
        Object.defineProperty(rowData, 'getItemType', {
            get() {
                let attendanceItem = self.getAttendanceItem(rowData.key);

                return attendanceItem.attendanceAtr;
            }
        });
        
    }

    private setItemText(rowData: RowData) {
        let self = this;
        Object.defineProperty(rowData, 'getItemText', {
            get() {
                return _.find(self.contentType, (item: ItemHeader) => item.key == rowData.key).headerText;
            }
        });
        
    }

    private setItemMasterType(rowData: RowData) {
        let self = this;
        Object.defineProperty(rowData, 'getItemMasterType', {
            get() {
                let attendanceItem = self.getAttendanceItem(rowData.key);

                return attendanceItem.typeGroup;
            }
        });
        
    }

    private setSpecCalcLst(rowData: RowData) {
        let self = this;
        let specLst = [628, 630, 631, 632];
        Object.defineProperty(rowData, 'isSpecCalcLst', {
            get() {
                let attendanceItem = self.getAttendanceItem(rowData.key);

                return _.includes(specLst, attendanceItem.id);
            }
        });
        
    }

    private setColorCode(rowData: RowData) {
        let self = this;
        let specLst = [628, 630, 631, 632];
        Object.defineProperty(rowData, 'getColorCode', {
            get() {
                if (rowData.class.includes('mgrid-error')) {
                    return 'ERROR';
                }
                if (rowData.class.includes('mgrid-alarm')) {
                    return 'ALARM';
                }

                return '';
            }
        });
        
    }

    private addCustomValid() {
        let self = this;
        let screenDataValid: any = {};
        _.forEach(self.params.data, (rowData: RowData, index) => {
            self.$set(self.screenData, rowData.key, rowData.value);
            let attendanceItem = self.getAttendanceItem(rowData.key);
            switch (attendanceItem.attendanceAtr) {
                case ItemType.Time: 
                    let contraint = _.find(self.contentType, (item: ItemHeader) => item.key == rowData.key).constraint;
                    screenDataValid[rowData.key] = {
                        required: contraint.required,
                        min: new TimeDuration(contraint.min).toNumber(),
                        max: new TimeDuration(contraint.max).toNumber(),
                        valueType: 'Duration'
                    };
                    break;
                default: 
                    break;
            }
        });
        self.$updateValidator('screenData', screenDataValid);
        // self.$updateValidator(`screenData.${index}`, newObj);
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
            date: self.params.date 
        }, { type : 'dropback' } )
        .then((v) => {

        });
    }

    public openDialog(rowData: RowData, value: MasterType) {
        let self = this;
        switch (value) {
            case MasterType.KDLS02_WorkType: self.openKDLS02(rowData); break;
            case MasterType.KDLS01_WorkTime: self.openKDLS01(rowData); break;
            default: break;
        }
    }

    private openKDLS02(rowData: RowData) {
        let self = this;
        let workTypeCDLst = _.map(self.masterData.workType, (o) => o.code);
        self.$modal(
            'kdls02',
            {
                seledtedWkTypeCDs: workTypeCDLst,
                selectedWorkTypeCD: rowData.value0,
                isAddNone: false,
                seledtedWkTimeCDs: null,
                selectedWorkTimeCD: null,
                isSelectWorkTime: false
            }
        ).then((data: any) => {
            if (data) {
                rowData.value0 = data.selectedWorkType.workTypeCode;
                rowData.value = _.find(self.masterData.workType, (o) => o.code == rowData.value0).name;
            }
        });
    }

    private openKDLS01(rowData: RowData) {
        let self = this;
        let workTimeCDLst = _.map(self.masterData.workTime, (o) => o.code);
        self.$modal(
            'kdls01',
            {
                isAddNone: true,
                seledtedWkTimeCDs: workTimeCDLst,
                selectedWorkTimeCD: rowData.value0,
                isSelectWorkTime: false
            }
        ).then((data: any) => {
            if (data) {
                rowData.value0 = data.selectedWorkTime.code;
                rowData.value = _.find(self.masterData.workTime, (o) => o.code == rowData.value0).name;
            }
        });
    }

    public register() {
        let self = this;
        self.$mask('show');
        self.$http.post('at', API.register, self.createRegisterParam())
        .then(() => {
            self.$mask('hide');
            self.$modal.info('Msg_15');
        }).catch((res: any) => {
            self.$mask('hide');
            self.$modal.error(res.messageId)
                .then(() => {
                    self.$close();
                });
        });
    }

    private createRegisterParam() {
        let self = this;
        let itemValues: any = [];
        _.forEach(self.params.data, (rowData: RowData, index) => {
            let itemValue: DPItemValue;
            let attendanceItem = self.getAttendanceItem(rowData.key);
            switch (attendanceItem.attendanceAtr) {
                case ItemType.InputStringCode: 
                    itemValue = new DPItemValue(attendanceItem, rowData.value0, self.params, self.itemValues);
                    break;
                case ItemType.ButtonDialog: 
                    itemValue = new DPItemValue(attendanceItem, rowData.value0, self.params, self.itemValues);
                    break;
                case ItemType.ComboBox: 
                    itemValue = new DPItemValue(attendanceItem, parseInt(rowData.value0), self.params, self.itemValues);
                    break;
                default: 
                    rowData.value = self.screenData[rowData.key];
                    itemValue = new DPItemValue(attendanceItem, rowData.value, self.params, self.itemValues);
                    break;
            }
            let oldRow = _.find(self.oldData, (o) => o.key == rowData.key);
            if (JSON.stringify(oldRow).localeCompare(JSON.stringify(rowData)) != 0) {
                itemValues.push(itemValue);
            }
            
        });
        let checkValue = false;
        if (!_.isEmpty(self.checked1s)) {
            checkValue = true;           
        }
        let dataCheckSign = [
            {
                rowId: self.params.rowId,
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

    private getAttendanceItem(value: any) {
        let self = this;
        let idKey = value.replace('A', '');

        return _.find(self.lstAttendanceItem, (item: any) => item.id == idKey);
    }
}

const API = {
    getPrimitiveAll: 'screen/at/correctionofdailyperformance/getPrimitiveAll',
    masterDialogData: 'screen/at/correctionofdailyperformance/getMasterDialogMob',
    register: 'screen/at/correctionofdailyperformance/addUpMobile'
};

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
    CDLS08_WorkPlace = 5,
    DoWork = 9,
    Calc = 10,
    ReasonGoOut = 11,
    Remasks = 12,
    TimeLimit = 13,
    BusinessType = 14
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