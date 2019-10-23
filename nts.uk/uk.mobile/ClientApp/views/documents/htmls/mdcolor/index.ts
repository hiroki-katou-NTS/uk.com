import { Vue } from '@app/provider';
import { component } from '@app/core/component';

@component({
    name: 'documentshtmlsmdcolor',
    route: { 
        url: '/htmls/mdcolor',
        parent: '/documents'
    },
    template: require('./index.vue'),
    resource: require('./resources.json')
})
export class DocumentsHtmlsMdcolorComponent extends Vue { }