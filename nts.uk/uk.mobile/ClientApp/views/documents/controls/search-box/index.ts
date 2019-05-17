import { Vue } from '@app/provider';
import { component } from '@app/core/component';

@component({
    name: 'documentscontrolssearch-box',
    route: '/documents/controls/search-box',
    style: require('./style.scss'),
    template: require('./index.vue'),
    resource: require('./resources.json'),
    validations: {},
    constraints: [],
    markdown: {
        vi: require('./content/vi.md'),
        jp: require('./content/jp.md')
    }
})
export class DocumentsControlsSearchBoxComponent extends Vue {
    public title: string = 'DocumentsControlsSearchBox';

    public searchList(startTime: number, endTime: number) {
        alert(`Catched search event with ${startTime} and ${endTime}`);
    }
}