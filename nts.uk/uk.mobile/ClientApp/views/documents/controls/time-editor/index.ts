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
    markdown: {
        jp: require('./content/jp.md'),
        vi: require('./content/vi.md')
    },
    resource: {
        'vi': {
            'LabelControlDocument': 'Label'
        },
        'jp': {
            'LabelControlDocument': 'Label'
        }
    }
})
export class TimeEditorControl extends Vue {

    time: number = 750;

    timeWithDay: object = {
        name: 'Time-With-Day',
        value: 750,
        disabled: false,
        errors: null,
        errorsAlways: null,
        showTitle: true
    }
}