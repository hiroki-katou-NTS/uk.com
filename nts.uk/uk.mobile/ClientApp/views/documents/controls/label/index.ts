import { Vue } from '@app/provider';
import { component } from '@app/core/component';

@component({
    route: {
        url: '/controls/label',
        parent: '/documents'
    },
    template: require('./index.html'),
    markdown: {
        jp: require('./content/jp.md'),
        vi: require('./content/vi.md')
    },
    resource: {
        'vi': {
            'LabelControlDocument': 'Label'
        },
        'jp': {
            'LabelControlDocument': 'Label'
        }
    }
})
export class LabelControlDocument extends Vue {

}