import { Vue } from '@app/provider';
import { component } from '@app/core/component';


@component({
    route: {
        url: '/html/color',
        parent: '/documents'
    },
    name: 'docs_color',
    template: require('./index.html'),
    resource: {
        vi: {
            'docs_color': 'Màu sắc'
        },
        jp: {
            'docs_color': 'Color'
        }
    }
})
export class DocsColorUKMobile extends Vue {
}