import { Vue } from '@app/provider';
import { component } from '@app/core/component';


@component({
    route: {
        url: '/component',
        parent: '/documents'
    },
    template: '<div id="component"><markdown /></div>',
    markdown: {
        vi: require('./content/vi.md'),
        en: require('./content/jp.md')
    },
    resource: {
        vi: {
            MVVMComponent: 'Component/View'
        },
        jp: {
            MVVMComponent: 'Component/View'
        }
    }
})
export class MVVMComponent extends Vue {
}