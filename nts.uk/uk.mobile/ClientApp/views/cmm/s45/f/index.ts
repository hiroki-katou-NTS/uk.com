import { Vue } from '@app/provider';
import { component, Prop } from '@app/core/component';

@component({
    name: 'cmms45f',
    style: require('./style.scss'),
    template: require('./index.vue'),
    resource: require('./resources.json'),
    validations: {},
    constraints: []
})
export class CmmS45FComponent extends Vue {
    @Prop({ default: () => ({ action: 0 }) })
    public readonly params: { 
        // 1: 処理区分 = 承認 
        // 2: 処理区分 = 否認
        // 3: 処理区分 = 差し戻し
        action: number 
    };
    public title: string = 'CmmS45F';

    // quay về màn CMMS45B
    public back() {
        let self = this;
        this.$close({ backToMenu: true });
    }

    // tiến tới đơn tiếp theo
    public toNextApp() {
        let self = this;
        self.$close({ backToMenu: false });    
    }
}