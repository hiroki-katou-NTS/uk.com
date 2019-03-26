import { Vue } from '@app/provider';
import { component } from '@app/core/component';
import { TimeInputType} from '@app/utils/time'

@component({
    route: {
        url: '/controls/time-component',
        parent: '/documents'
    },
    template: require('./index.html'),
    validations: {
        time: {
            minValue: -720,
            maxValue: 4319
        },
        date: {
            minValue: -720,
            maxValue: 4319
        }
    },
})
export class TimeComponentTest extends Vue {

    time: number = 750;

    date1: string = "2017-08-04";

    date: Date = new Date("2017-08-04");

    constructor() {
        super();
        let self = this;
    }
}