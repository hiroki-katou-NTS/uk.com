import { Vue } from '@app/provider';
import { component } from '@app/core/component';


@component({
    route: {
        url: '/html/button',
        parent: '/documents'
    },
    name: 'docs_button',
    template: require('./index.html'),
    resource: {
        vi: {
            'HTMLDocumentsComponent': 'Html'
        },
        jp: {
            'HTMLDocumentsComponent': 'Html'
        }
    },
    markdown: {
        vi: require('./content/vi.md'),
        jp: require('./content/jp.md')
    }
})
export class DocsHTMLButtonComponent extends Vue {
}