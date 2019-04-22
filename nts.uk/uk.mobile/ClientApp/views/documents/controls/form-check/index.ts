import { Vue } from '@app/provider';
import { component } from '@app/core/component';

@component({
    name: 'documentscontrolsform-check',
    route: {
        url: '/controls/form-check',
        parent: '/documents'
    },
    template: require('./index.vue'),
    resource: require('./resources.json'),
    markdown: {
        vi: require('./content/vi.md'),
        jp: require('./content/jp.md')
    }
})
export class DocumentsControlsFormCheckComponent extends Vue {
    public radios = [
        { id: 1, name: 'Option 1' },
        { id: 2, name: 'Option 2' },
        { id: 3, name: 'Option 3' },
        { id: 4, name: 'Option 4' }
    ];

    public checked: number = 3;
    public checked2: number = 2;
    public checkeds: Array<number> = [2, 4];
    public checked2s: Array<number> = [2, 4];

    public switchbox: number = 3;
}