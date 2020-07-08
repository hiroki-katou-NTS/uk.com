import { Vue } from '@app/provider';
import { component } from '@app/core/component';

@component({
    name: 'kafs00a',
    style: require('./style.scss'),
    template: require('./index.vue'),
    resource: require('./resources.json'),
    validations: {},
    constraints: []
})
export class KafS00AComponent extends Vue {
    public title: string = 'KafS00A';
}