import { Vue, _ } from '@app/provider';
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
    @Prop({ default: () => ({ action: 0, listAppMeta: [], currentApp: '' }) })
    public readonly params: { 
        // 1: 処理区分 = 承認 
        // 2: 処理区分 = 否認
        // 3: 処理区分 = 差し戻し
        action: number,
        listAppMeta: Array<string>, 
        currentApp: string
    };
    public title: string = 'CmmS45F';

    // quay về màn CMMS45B
    public backToMenu() {
        let self = this;
        self.$close();
    }

    // tiến tới đơn tiếp theo
    public toNextApp() {
        let self = this;
        self.$close({ listAppMeta: self.params.listAppMeta, currentApp: '' });
    }

    // quay về đơn hiện tại
    public backToDetail() {
        let self = this;
        if (self.params.action != 3) {
            self.$close({ listAppMeta: self.params.listAppMeta, currentApp: self.params.currentApp });
        }
    }

    // kiểm tra có phải đơn cuối cùng không
    public isLastApp(): boolean {
        let self = this;

        return self.params.currentApp == self.params.listAppMeta[self.params.listAppMeta.length - 1];
    }
}