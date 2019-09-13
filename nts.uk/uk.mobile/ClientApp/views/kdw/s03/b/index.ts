import { Vue, _ } from '@app/provider';
import { component, Prop, Watch } from '@app/core/component';
import { KdwS03DComponent } from 'views/kdw/s03/d';

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
        'dynamic-time-duration': {
            template: `<nts-time-editor class="mb-3" v-model="itemkey" time-input-type="time-duration" />`,
            props: ['itemkey']
        }
    },
})
export class KdwS03BComponent extends Vue {
    @Prop({ default: () => ({ employeeName: '', date: new Date(), data: {}, contentType: {} }) })
    public readonly params!: { employeeName: '', date: Date, data: Array<RowData>, contentType: Array<ItemHeader> };
    public checked1s: Array<number> = [1, 3];
    public screenData: any = {};

    public created() {
        let self = this;
        let screenDataValid = {};
        _.forEach(self.params.data, (rowData: RowData, index) => {
            let contraint = _.find(self.params.contentType, (item: ItemHeader) => item.key == rowData.key).constraint;
            if (contraint.cdisplayType == 'Primitive') {

            }
            let newObj: any = {
                required: true,
                min : 0,
                max : 120,
                valueType: 'Duration'  
            };
            self.$set(self.screenData, rowData.key, rowData.value);
            screenDataValid[rowData.key] = newObj;
            
        });
        // self.$updateValidator(`screenData.${index}`, newObj);
        self.$updateValidator('screenData', screenDataValid);
        self.$validate();
        console.log(self.validations);
    }

    public getItemTypeCode(value: RowData): number {
        let self = this;
        switch (value.key) {
            case 'A28': return ItemType.ButtonDialog;
            case 'A29': return ItemType.ButtonDialog;
            case 'A532': return ItemType.Time;
            case 'A75': return ItemType.TimeWithDay;
            case 'A31': return ItemType.TimeWithDay;
            case 'A794': return ItemType.TimeWithDay;
            case 'A157': return ItemType.TimeWithDay;
            case 'A159': return ItemType.TimeWithDay;
            case 'A163': return ItemType.TimeWithDay;
            case 'A165': return ItemType.TimeWithDay;
            case 'A795': return ItemType.TimeWithDay;
            case 'A34': return ItemType.TimeWithDay;
            case 'A77': return ItemType.TimeWithDay;
            default: return null;
        }
    }

    public getItemText(value: RowData): string {
        let self = this;

        return _.find(self.params.contentType, (item: ItemHeader) => item.key == value.key).headerText;
    }

    get itemType() {
        return ItemType;
    }

    public getItemValue(value: RowData) {
        return value.value ? value.value : null;
    }

    public openDScreen() {
        let self = this;
        self.$modal('kdws03d', { employeeName: self.params.employeeName, date: self.params.date }, { type : 'dropback' } )
        .then((v) => {

        });
    }

    public openKDLS02() {

    }

    public openKDLS01() {

    }

    public register() {
        let self = this;
        console.log(self.screenData);
    }

    public getHtml(value: any) {
        // return document.createElement(`<nts-time-editor class="mb-3" v-model="screenData.` + `A532` + `" time-input-type="time-duration" />`);
        return '';
        /*
        return {
            template: `""`
        };
        */
    }

    @Watch('screenData', { deep: true })
    public screenDataWatcher(value: any) {
        let self = this;
        console.log(self.screenData);
        self.$validate('screenData');
    }
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

interface RowData {
    key: string;
    value: any;
    groupKey: string;
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