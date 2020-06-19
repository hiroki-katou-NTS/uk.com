import { Vue } from '@app/provider';
import { component } from '@app/core/component';

@component({
    name: 'cmms45componentsapp2',
    template: require('./index.vue'),
    style: require('./style.scss'),
    validations: {},
    constraints: []
})
export class CmmS45ComponentsApp2Component extends Vue {
    public title: string = 'CmmS45ComponentsApp1';
}