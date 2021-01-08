import { Vue } from '@app/provider';
import { component } from '@app/core/component';

@component({
    name: 'cmms45shrcomponentsapp6',
    route: '/cmm/s45/shr/components/app6',
    style: require('./style.scss'),
    template: require('./index.vue'),
    resource: require('./resources.json'),
    validations: {},
    constraints: []
})
export class CmmS45ShrComponentsApp6Component extends Vue {
    public title: string = 'CmmS45ShrComponentsApp6';
}