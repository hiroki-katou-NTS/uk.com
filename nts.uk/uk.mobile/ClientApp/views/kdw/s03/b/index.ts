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
    public checked1s: Array<number> = [1, 3];
    public screenData: any = {};
    private masterDialogData: any = {
        workType: [],
        workTime: [],
        workPlace: []
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

    public created() {
        let self = this;
        let fakeValid = {};
        _.forEach(self.params.data, (rowData: RowData, index) => {
            self.formatData(rowData);
            self.setItemType(rowData);
            self.setItemText(rowData);
            self.setItemDialogType(rowData);
            self.addMasterDialogParam(rowData);
            fakeValid[rowData.key] = { required: false };
        });
        self.oldData = self.toJS(self.params.data);
        self.$updateValidator('screenData', fakeValid);
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
        console.log('start');
    }

    private createPrimitiveAll(data: any) {
        let self = this;
        let primitiveAll = [];
        _.forEach(data.keys(), (o) => { 
            primitiveAll.push({ 'id': o, 'value': data[o] });
        });
        self.primitiveAll(primitiveAll);
    }

    private createMasterData(data: any) {
        let self = this;
        self.masterDialogData.workTime = data[DialogType.KDLS01_WorkTime];
        self.masterDialogData.workType = data[DialogType.KDLS02_WorkType];
        self.masterDialogData.workPlace = data[DialogType.CDLS08_WorkPlace];
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

    private setItemDialogType(rowData: RowData) {
        let self = this;
        Object.defineProperty(rowData, 'getItemDialogType', {
            get() {
                let attendanceItem = self.getAttendanceItem(rowData.key);

                return attendanceItem.typeGroup;
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
        if (attendanceItem.attendanceAtr == ItemType.InputStringCode || attendanceItem.attendanceAtr == ItemType.ButtonDialog) { 
            if (!_.includes(self.masterDialogParam, attendanceItem.typeGroup)) {
                self.masterDialogParam.push(attendanceItem.typeGroup);
            }
        }
    }

    get itemType() {
        return ItemType;
    }

    public openDScreen() {
        let self = this;
        self.$modal('kdws03d', { employeeName: self.params.employeeName, date: self.params.date }, { type : 'dropback' } )
        .then((v) => {

        });
    }

    public openDialog(rowData: RowData, value: DialogType) {
        let self = this;
        switch (value) {
            case DialogType.KDLS02_WorkType: self.openKDLS02(rowData); break;
            case DialogType.KDLS01_WorkTime: self.openKDLS01(rowData); break;
            default: break;
        }
    }

    private openKDLS02(rowData: RowData) {
        let self = this;
        let workTypeCDLst = _.map(self.masterDialogData.workType, (o) => o.code);
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
                rowData.value = _.find(self.masterDialogData.workType, (o) => o.code == rowData.value0).name;
            }
        });
    }

    private openKDLS01(rowData: RowData) {
        let self = this;
        let workTimeCDLst = _.map(self.masterDialogData.workTime, (o) => o.code);
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
                rowData.value = _.find(self.masterDialogData.workTime, (o) => o.code == rowData.value0).name;
            }
        });
    }

    public register() {
        let self = this;
        // console.log(self.params.data);
        self.$validate();
        console.log(self.$errors);
        console.log(self.$valid);
        self.$http.post('at', API.register, self.createRegisterParam());
    }

    private createRegisterParam() {
        let self = this;
        let itemValues: any = [];
        _.forEach(self.params.data, (rowData: RowData, index) => {
            let itemValue: DPItemValue;
            let attendanceItem = self.getAttendanceItem(rowData.key);
            if (attendanceItem.attendanceAtr == ItemType.InputStringCode || 
                attendanceItem.attendanceAtr == ItemType.ButtonDialog) {
                    itemValue = new DPItemValue(attendanceItem, rowData.value0, self.params, self.itemValues);
            } else {
                rowData.value = self.screenData[rowData.key];
                itemValue = new DPItemValue(attendanceItem, rowData.value, self.params, self.itemValues);
            }
            let oldRow = _.find(self.oldData, (o) => o.key == rowData.key);
            if (JSON.stringify(oldRow).localeCompare(JSON.stringify(rowData)) != 0) {
                itemValues.push(itemValue);
            }
            
        });
        let dataCheckSign = [];
        
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

export enum DialogType {
    KDLS02_WorkType = 1,
    KDLS01_WorkTime = 2,
    CDLS08_WorkPlace = 5
}

interface RowData {
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