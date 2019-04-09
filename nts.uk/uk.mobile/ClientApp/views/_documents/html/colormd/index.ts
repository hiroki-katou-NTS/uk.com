import { Vue } from '@app/provider';
import { component } from '@app/core/component';


@component({
    route: {
        url: '/html/color-material-design',
        parent: '/documents'
    },
    name: 'docs_color_md',
    template: require('./index.html'),
    resource: {
        vi: {
            'docs_color_md': 'Màu sắc (material design)'
        },
        jp: {
            'docs_color_md': 'Color (material design)'
        }
    }
})
export class DocsColorMdUKMobile extends Vue {
}