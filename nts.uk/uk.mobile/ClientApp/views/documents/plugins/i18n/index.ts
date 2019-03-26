import { Vue } from '@app/provider';
import { component } from '@app/core/component';


@component({
    route: {
        url: '/plugin/i18n',
        parent: '/documents'
    },
    template: require('./index.html'),
    markdown: {
        vi: require('./content/vi.md'),
        jp: require('./content/jp.md')
    },
    resource: {
        vi: {
            'I18nDocument': 'I18n',
            'read_documents_at': 'Vui lòng đọc tài liệu hướng dẫn tại: '
        }
    }
})
export class I18nDocument extends Vue {
}