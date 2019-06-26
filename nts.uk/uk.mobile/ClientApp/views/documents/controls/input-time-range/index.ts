import { Vue } from '@app/provider';
import { component } from '@app/core/component';

@component({
    name: 'Time Range Input',
    route: { 
        url: '/controls/input-time-range',
        parent: '/documents'
    },
    template: require('./index.vue'),
    resource: require('./resources.json'),
    markdown: {
        vi: require('./content/vi.md'),
        jp: require('./content/jp.md')
    }, 
    validations: {
        timeRangeValue: {
            timeRange: true
        }
    }
})
export class DocumentsControlsInputTimeRangeComponent extends Vue {

    public timeRangeValue: { start: number, end: number} = null;

    public validate() {
        this.$validate();
    }

}