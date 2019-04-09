import { Vue } from '@app/provider';
import { component } from '@app/core/component';

@component({
    route: {
        url: '/directive/toolbar',
        parent: '/documents'
    },
    template: require('./index.html'),
    markdown: {
        vi: require('./content/vi.md'),
        jp: require('./content/vi.md')
    },
    resource: {
        vi: {
            'd_toolbar': 'Thanh công cụ'
        },
        jp: {
            'd_toolbar': 'Toolbar'
        }
    },
    name: 'd_toolbar'
})
export class ToolbarDirectiveDocument extends Vue {
}