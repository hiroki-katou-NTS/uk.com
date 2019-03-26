import { Vue } from '@app/provider';
import { component } from '@app/core/component';


@component({
    route: {
        url: '/html/form',
        parent: '/documents'
    },
    name: 'docs_form',
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
export class DocsHTMLFormComponent extends Vue {
}