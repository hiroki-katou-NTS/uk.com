import { Vue } from '@app/provider';
import { component } from '@app/core/component';


@component({
    route: {
        url: '/component/view',
        parent: '/documents'
    },
    template: '<div id="component"><markdown /></div>',
    markdown: {
        vi: require('./content/vi.md'),
        en: require('./content/jp.md')
    },
    resource: {
        vi: {
            'component': 'View - ViewModel',
            HookEventOfComponent: 'Global event'
        },
        jp: {
            'component': 'View - ViewModel',
            HookEventOfComponent: 'Global event'
        }
    }
})
export class HookEventOfComponent extends Vue {
}