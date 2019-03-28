import { Vue } from '@app/provider';
import { component } from '@app/core/component';


@component({
    route: {
        url: '/html/color',
        parent: '/documents'
    },
    name: 'docs_form',
    template: require('./index.html'),
    resource: {
        vi: {
            'DocsColorUKMobile': 'Màu sắc'
        },
        jp: {
            'DocsColorUKMobile': 'Color'
        }
    }
})
export class DocsColorUKMobile extends Vue {
}