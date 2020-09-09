import { Vue } from '@app/provider';
import { component, Prop } from '@app/core/component';
import {KafS00DComponent} from '../../s00/d';

@component({
    name: 'kafs08c',
    route: '/kaf/s08/c',
    style: require('./style.scss'),
    template: require('./index.vue'),
    resource: require('./resources.json'),
    validations: {},
    components : {
        'kafs00d' : KafS00DComponent
    },
    constraints: []
})
export class KafS08CComponent extends Vue {
    @Prop({default: () => ({})})
    public title: string = 'KafS08C';
}