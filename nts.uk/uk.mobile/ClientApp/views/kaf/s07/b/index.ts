import { Vue } from '@app/provider';
import { component } from '@app/core/component';
import {CmmS45ComponentsApp2Component} from '../../../cmm/s45/components/app2/index';

@component({
    name: 'kafs07b',
    route: '/kaf/s07/b',
    style: require('./style.scss'),
    template: require('./index.vue'),
    resource: require('./resources.json'),
    validations: {},
    components: {
        cmms45componentsapp2: CmmS45ComponentsApp2Component
    },
    constraints: []
})
export class KafS07BComponent extends Vue {
    public title: string = 'KafS07B';
}