import { Vue } from '@app/provider';
import { component } from '@app/core/component';

@component({
    name: 'kafs05step3',
    route: '/kaf/s05/step3',
    style: require('./style.scss'),
    template: require('./index.vue'),
    resource: require('./resources.json'),
    validations: {},
    constraints: []
})
export class KafS05Step3Component extends Vue {
    public title: string = 'KafS05Step3';
}