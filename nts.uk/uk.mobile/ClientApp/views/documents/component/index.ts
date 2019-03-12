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
            MVVMComponent: 'Thành phần (view)'
        }
    }
})
export class MVVMComponent extends Vue {
}