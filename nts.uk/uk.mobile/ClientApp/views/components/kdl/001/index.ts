import { obj } from '@app/utils';
import { Vue } from '@app/provider';
import { component, Prop } from '@app/core/component';

// mocks data
import { worktypes } from './mockdata';

@component({
    template: require('./index.html')
})
export class WorkTypeComponent extends Vue {
    @Prop({ default: {} })
    public readonly params: any;

    public objsct: any = {};

    public mounted() {
        this.objsct = obj.toJS(this.params);
    }

    public listItems: Array<any> = worktypes();

    public selected(item: any) {
        return item.code === this.objsct.code ? 'selected' : '';
    }

    public choose(item: any) {
        this.objsct.code = item.code;
        this.objsct.name = item.name;
        this.objsct.remark = item.remark;
    }

    public pushData() {
        this.$close(obj.toJS(this.objsct));
    }
}