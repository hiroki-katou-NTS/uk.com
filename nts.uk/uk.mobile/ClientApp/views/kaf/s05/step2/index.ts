import { Vue } from '@app/provider';
import { component } from '@app/core/component';
import { KafS05Component} from '../a/index';

@component({
    name: 'kafs05step2',
    route: '/kaf/s05/step2',
    style: require('./style.scss'),
    template: require('./index.vue'),
    resource: require('./resources.json'),
    validations: {},
    constraints: []
})
export class KafS05Step2Component extends Vue {
    public title: string = 'KafS05Step2';
    get $appContext(): KafS05Component {
        const self = this;

        return self.$parent as KafS05Component;
    }
    get nameInsert(): string {
        const self = this;

        return self.$appContext.modeNew ? self.$i18n('KAFS02_11') : self.$i18n('KAFS02_18');
    }
    public backStep1() {
        const self = this;
        self.$appContext.toStep(1);
    }
}