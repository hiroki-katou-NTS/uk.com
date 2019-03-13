import { Vue } from '@app/provider';
import { component } from '@app/core/component';


@component({
    route: {
        url: '/html',
        parent: '/documents'
    },
    template:  require('./index.html')
})
export class HTMLDocumentsComponent extends Vue {
}