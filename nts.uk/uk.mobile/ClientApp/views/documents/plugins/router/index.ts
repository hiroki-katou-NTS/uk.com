import { Vue } from '@app/provider';
import { component } from '@app/core/component';


@component({
    route: {
        url: '/plugin/router',
        parent: '/documents'
    },
    template: require('./index.html'),
    markdown: {
        vi: require('./content/vi.md'),
        jp: require('./content/jp.md')
    },
    resource: {
        vi: {
            RouterPluginDocument: 'Điều hướng (router)'
        },
        jp: {
            RouterPluginDocument: 'Navigator (router)'
        }
    }
})
export class RouterPluginDocument extends Vue {
}