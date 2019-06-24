import { Vue } from '@app/provider';
import { component } from '@app/core/component';

@component({
    name: 'documentscontrolsinput-year-month',
    style: require('./style.scss'),
    route: {
        url: '/controls/input-year-month',
        parent: '/documents'
    },
    template: require('./index.vue'),
    resource: require('./resources.json'),
    markdown: {
        vi: require('./content/vi.md'),
        jp: require('./content/jp.md')
    }
})
export class DocumentsControlsInputYearMonthComponent extends Vue {
    public yearMonth1: string = null;

    public yearMonth2: string = null;

    public yearMonth3: string = null;
}