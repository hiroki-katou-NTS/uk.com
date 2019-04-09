import { Vue } from '@app/provider';
import { component } from '@app/core/component';


@component({
    route: {
        url: '/component/decorator',
        parent: '/documents'
    },
    template: '<div id="component"><markdown /></div>',
    markdown: {
        vi: require('./content/vi.md'),
        en: require('./content/jp.md')
    },
    resource: {
        vi: {
            DecoratorOfComponent: 'Decorator'
        },
        jp: {
            DecoratorOfComponent: 'Decorator'
        }
    }
})
export class DecoratorOfComponent extends Vue {
}