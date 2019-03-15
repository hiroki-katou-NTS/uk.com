import { Vue } from '@app/provider';
import { component } from '@app/core/component';

@component({
    route: {
        url: '/form-check',
        parent: '/documents'
    },
    template: require('./index.html'),
    markdown: {
        jp: require('./content/jp.md'),
        vi: require('./content/vi.md')
    },
    resource: {
        'vi': {
            'FormCheckComponent': 'Checkbox & Radio'
        },
        'jp': {
            'FormCheckComponent': 'Checkbox & Radio'            
        }
    }
})
export class FormCheckComponent extends Vue {
    radios = [
        { id: 1, name: 'Option 1' },
        { id: 2, name: 'Option 2' },
        { id: 3, name: 'Option 3' },
        { id: 4, name: 'Option 4' }
    ];

    checked: number = 3;
    checked2: number = 2;
    checkeds: Array<number> = [2, 4];
    checked2s: Array<number> = [2, 4];
}