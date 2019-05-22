import { Vue } from '@app/provider';
import { component } from '@app/core/component';

@component({
    name: 'documentscontrolstext-search-box',
    route: '/documents/controls/text-search-box',
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
export class DocumentsControlsTextSearchBoxComponent extends Vue {
    
    public searchList(value: string) {
        alert(`search with ${value}`);
    }
}