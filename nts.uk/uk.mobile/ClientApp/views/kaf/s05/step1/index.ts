import { Vue } from '@app/provider';
import { component } from '@app/core/component';

@component({
    name: 'kafs05step1',
    route: '/kaf/s05/step1',
    style: require('./style.scss'),
    template: require('./index.vue'),
    resource: require('./resources.json'),
    validations: {},
    constraints: []
})
export class KafS05Step1Component extends Vue {
    public title: string = 'KafS05Step1';
}