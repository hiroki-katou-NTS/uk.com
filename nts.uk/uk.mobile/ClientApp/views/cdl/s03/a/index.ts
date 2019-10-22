import { Vue } from '@app/provider';
import { component } from '@app/core/component';

@component({
    name: 'cdls03a',
    route: '/cdl/s03/a',
    style: require('./style.scss'),
    template: require('./index.vue'),
    resource: require('./resources.json'),
    validations: {},
    constraints: []
})
export class CdlS03AComponent extends Vue {
    public title: string = 'CdlS03A';
}