import { Vue } from '@app/provider';
import { component } from '@app/core/component';

@component({
    route: {
        url: '/controls/time-editor',
        parent: '/documents'
    },
    template: require('./index.html'),
    validations: {
        time: {
            minValue: -720,
            maxValue: 4319
        }
    },
})
export class TimeEditorControl extends Vue {

    time: number = 750;

    constructor() {
        super();
        let self = this;
    }
}