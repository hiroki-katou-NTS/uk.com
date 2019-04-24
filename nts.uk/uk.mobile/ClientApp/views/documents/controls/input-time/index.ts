import { Vue } from '@app/provider';
import { component } from '@app/core/component';

@component({
    name: 'documentscontrolsinput-time',
    route: {
        url: '/controls/input-time',
        parent: '/documents'
    },
    template: require('./index.vue'),
    resource: require('./resources.json'),
    markdown: {
        vi: require('./content/vi.md'),
        jp: require('./content/jp.md')
    }
})
export class DocumentsControlsInputTimeComponent extends Vue {
    public time: number = 750;

    public timeWithDay: object = {
        name: 'Time-With-Day',
        value: 750,
        disabled: false,
        errors: null,
        errorsAlways: null,
        showTitle: true
    };
}