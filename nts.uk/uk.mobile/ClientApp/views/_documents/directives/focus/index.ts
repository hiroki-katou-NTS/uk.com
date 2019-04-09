import { Vue } from '@app/provider';
import { component } from '@app/core/component';

@component({
    route: {
        url: '/directive/focus',
        parent: '/documents'
    },
    template: require('./index.html'),
    resource: {
        'vi': {
            'd_focus': 'Focus vào một đối tượng'
        },
        'jp': {
            'd_focus': 'Focus to element'
        }
    },
    markdown: {
        vi: require('./content/vi.md'),
        jp: require('./content/vi.md')
    },
    name: 'd_focus'
})
export class FocusDirectiveDocument extends Vue {
}