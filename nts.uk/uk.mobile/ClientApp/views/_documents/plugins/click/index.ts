import { Vue } from '@app/provider';
import { component } from '@app/core/component';


@component({
    route: {
        url: '/plugin/click',
        parent: '/documents'
    },
    template: require('./index.html'),
    markdown: {
        vi: require('./content/vi.md'),
        jp: require('./content/jp.md')
    },
    resource: {
    },
    name: 'name_of_component'
})
export class PreventMultiClickDocument extends Vue {
}