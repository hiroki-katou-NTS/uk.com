import { Vue } from '@app/provider';
import { component } from '@app/core/component';
import {KafS00AComponent,KAFS00AParams} from '../../s00/a';
import {KafS00BComponent,KAFS00BParams} from '../../s00/b';
import {KafS00CComponent,KAFS00CParams} from '../../s00/c';

@component({
    name: 'kafs20a2',
    route: '/kaf/s20/a2',
    style: require('./style.scss'),
    template: require('./index.vue'),
    components: {
        'kaf-s00-a': KafS00AComponent,
        'kaf-s00-b': KafS00BComponent,
        'kaf-s00-c': KafS00CComponent,
    },
    resource: require('./resources.json'),
    validations: {},
    constraints: []
})
export class KafS20A2Component extends Vue {
    public title: string = 'KafS20A2';
    public kafS00AParams!: KAFS00AParams;
    public kafS00BParams!: KAFS00BParams;
    public kafS00CParams!: KAFS00CParams;

    public beforeCreate() {
        const vm = this;

    }

    public created() {
        const vm = this;


    }
}