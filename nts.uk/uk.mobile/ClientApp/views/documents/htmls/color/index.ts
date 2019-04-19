import { Vue } from '@app/provider';
import { component } from '@app/core/component';

@component({
    name: 'documentshtmlscolor',
    route: { 
        url: '/htmls/color',
        parent: '/documents'
    },
    template: require('./index.vue'),
    resource: require('./resources.json')
})
export class DocumentsHtmlsColorComponent extends Vue { }