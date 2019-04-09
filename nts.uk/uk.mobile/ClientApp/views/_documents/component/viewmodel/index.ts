import { Vue } from '@app/provider';
import { component } from '@app/core/component';


@component({
    route: {
        url: '/component/viewmodel',
        parent: '/documents'
    },
    template: '<div id="component"><markdown /></div>',
    markdown: {
        vi: require('./content/vi.md'),
        en: require('./content/jp.md')
    },
    resource: {
        vi: {
            ViewModelOfComponent: 'ViewModel'
        },
        jp: {
            ViewModelOfComponent: 'ViewModel'
        }
    }
})
export class ViewModelOfComponent extends Vue {
}