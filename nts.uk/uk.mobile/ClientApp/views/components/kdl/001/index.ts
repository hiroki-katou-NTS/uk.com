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
    readonly params: any;

    objsct: any = {};

    mounted() {
        this.objsct = obj.toJS(this.params);
    }

    listItems: Array<any> = worktypes();

    selected(item: any) {
        return item.code === this.objsct.code ? 'selected' : '';
    }

    choose(item: any) {
        this.objsct.code = item.code;
        this.objsct.name = item.name;
        this.objsct.remark = item.remark;
    }

    pushData() {
        this.$close(obj.toJS(this.objsct));
    }
}