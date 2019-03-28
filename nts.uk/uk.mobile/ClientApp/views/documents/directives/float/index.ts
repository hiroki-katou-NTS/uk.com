import { Vue } from '@app/provider';
import { component } from '@app/core/component';

@component({
    route: {
        url: '/directive/float-action',
        parent: '/documents'
    },
    template: require('./index.html'),
    markdown: {
        vi: require('./content/vi.md'),
        jp: require('./content/vi.md')
    },
    name: 'float_action_docs',
    resource: {
        vi: {
            'float_action_docs': 'Float buttons'
        }
    }
})
export class FloatActionDirectiveDocument extends Vue {
}