import { Vue, _ } from '@app/provider';
import { component, Prop } from '@app/core/component';

@component({
    name: 'kdws03b',
    style: require('./style.scss'),
    template: require('./index.vue'),
    resource: require('./resources.json'),
    validations: {
        timePoint : {
            min : 0,
            max : 1260,
            valueType: 'TimePoint'
        }
    },
    constraints: []
})
export class KdwS03BComponent extends Vue {
    @Prop({ default: () => ({ employeeName: '', date: new Date(), data: {}, contentType: {} }) })
    public readonly params!: { employeeName: '', date: Date, data: Array<RowData>, contentType: Array<ItemHeader> };
    public timePoint: number = null;
    public text: string = 'Init Value';
    public number: number = 10;
    public checked1s: Array<number> = [1, 3];
    public timeWithDay: number = null;

    public getItemTypeCode(value: RowData): number {
        let self = this;
        switch (value.key) {
            case 'A28': return ItemType.InputString;
            case 'A29': return ItemType.InputString;
            case 'A532': return ItemType.TimePoint;
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
            default: return ItemType.NotAvaiable;
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
}

export enum ItemType {
    NotAvaiable = 0,
    TimeWithDay = 1,
    InputString = 2,
    InputNumber = 3,
    TimePoint = 4
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