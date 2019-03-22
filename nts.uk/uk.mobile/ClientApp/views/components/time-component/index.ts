import { Vue } from '@app/provider';
import { component } from '@app/core/component';
import { TimeInputType} from '@app/utils/time'

@component({
    route: '/components/time-component',
    template: require('./index.html'),
    validations: {
        time: {
            minValue: -720,
            maxValue: 4319
        }
    },
})
export class TimeComponentTest extends Vue {

    time: number = 750;

    constructor() {
        super();
        let self = this;
    }
}