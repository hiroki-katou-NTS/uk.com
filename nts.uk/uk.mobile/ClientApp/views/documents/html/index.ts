import { Vue } from '@app/provider';
import { component } from '@app/core/component';


@component({
    route: {
        url: '/html',
        parent: '/documents'
    },
    template: require('./index.html'),
    resource: {
        vi: {
            'HTMLDocumentsComponent': 'Html'
        },
        jp: {
            'HTMLDocumentsComponent': 'Html'
        }
    }
})
export class HTMLDocumentsComponent extends Vue {
}