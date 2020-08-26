import { Vue } from '@app/provider';
import { component } from '@app/core/component';

@component({
    name: 'kafs08d',
    route: '/kaf/s08/d',
    style: require('./style.scss'),
    template: require('./index.vue'),
    resource: require('./resources.json'),
    validations: {},
    constraints: []
})
export class KafS08DComponent extends Vue {
    public name: string = 'Nittsu System Viet Nam';
    public title: string = 'KafS08D';
    
}