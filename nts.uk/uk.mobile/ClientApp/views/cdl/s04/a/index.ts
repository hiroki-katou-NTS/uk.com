import { Vue } from '@app/provider';
import { component } from '@app/core/component';

@component({
    name: 'cdls04a',
    route: '/cdl/s04/a',
    style: require('./style.scss'),
    template: require('./index.vue'),
    resource: require('./resources.json'),
    validations: {},
    constraints: []
})
export class CdlS04AComponent extends Vue {
    public title: string = 'CdlS04A';
}