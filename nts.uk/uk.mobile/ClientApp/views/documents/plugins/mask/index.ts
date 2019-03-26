import { Vue } from '@app/provider';
import { component } from '@app/core/component';


@component({
    route: {
        url: '/plugin/mask-layer',
        parent: '/documents'
    },
    template: require('./index.html'),
    markdown: {
        vi: require('./content/vi.md'),
        jp: require('./content/jp.md')
    },
    resource: {
    }
})
export class MaskLayerPluginDocument extends Vue {
}