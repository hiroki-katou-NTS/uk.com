import { Vue } from '@app/provider';
import { component } from '@app/core/component';

@component({
    route: {
        url: '/controls/date-input',
        parent: '/documents'
    },
    template: require('./index.html'),
    validations: {
        date: {
            minValue: -720,
            maxValue: 4319
        }
    },
})
export class DateInputControl extends Vue {

    date: Date = new Date("2017-08-04");

    constructor() {
        super();
        let self = this;
    }
}