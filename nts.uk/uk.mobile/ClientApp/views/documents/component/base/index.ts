import { Vue } from '@app/provider';
import { component } from '@app/core/component';


@component({
    route: {
        url: '/component/basic',
        parent: '/documents'
    },
    template: '<div id="component"><markdown /></div>',
    markdown: {
        vi: require('./content/vi.md'),
        en: require('./content/jp.md')
    },
    resource: {
        vi: {
            BaseOfComponent: 'Basic'
        },
        jp: {
            BaseOfComponent: 'Basic'
        }
    }
})
export class BaseOfComponent extends Vue {
}