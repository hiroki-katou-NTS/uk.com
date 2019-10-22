import { Vue } from '@app/provider';
import { component } from '@app/core/component';

@component({
    name: 'cdls03test',
    route: '/cdl/s03/test',
    style: require('./style.scss'),
    template: require('./index.vue'),
    resource: require('./resources.json'),
    validations: {},
    constraints: []
})
export class CdlS03TestComponent extends Vue {
    public title: string = 'CdlS03Test';
}