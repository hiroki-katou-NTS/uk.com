import { Vue } from '@app/provider';
import { component } from '@app/core/component';

@component({
    name: 'dd_click',
    route: {
        url: '/directive/click',
        parent: '/documents'
    },
    style: require('./style.scss'),
    template: require('./index.vue'),
    resource: require('./resources.json')
})
export class DDClickComponent extends Vue {
    
}