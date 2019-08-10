import { Vue } from '@app/provider';
import { component, Prop } from '@app/core/component';

@component({
    name: 'cmms45f',
    route: '/cmm/s45/f',
    style: require('./style.scss'),
    template: require('./index.vue'),
    resource: require('./resources.json'),
    validations: {},
    constraints: []
})
export class CmmS45FComponent extends Vue {
    @Prop({ default: () => ({ action: 0, listAppMeta: [], nextApp: '' }) })
    public readonly params: { action: number, listAppMeta: Array<string>, nextApp: string };
    public title: string = 'CmmS45F';

    public back() {
        this.$goto('cmms45b', { CMMS45_FromMenu: false });
    }

    public toNextApp() {
        let self = this;
        self.$close({ 'listAppMeta':self.params.listAppMeta, 'currentApp': self.params.nextApp });    
    }
}