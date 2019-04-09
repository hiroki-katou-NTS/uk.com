import { Vue } from '@app/provider';
import { component } from '@app/core/component';


@component({
    route: {
        url: '/plugin/validate',
        parent: '/documents'
    },
    template: require('./index.html'),
    markdown: {
        vi: require('./content/vi.md'),
        jp: require('./content/jp.md')
    },
    resource: {
        vi: {
            ModalDocumentComponent: 'Dialog/modal'
        },
        jp: {
            ModalDocumentComponent: 'Dialog/modal'
        }
    }
})
export class ValidatePluginDocument extends Vue {
}