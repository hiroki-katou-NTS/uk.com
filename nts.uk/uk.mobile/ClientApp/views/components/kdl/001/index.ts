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
    params: any;

    listItems: Array<any> = worktypes();

    selected(item: any) {
        return item.code === this.params.code ? 'selected' : '';
    }

    choose(item: any) {
        this.params = item;
    }

    pushData() {
        this.$close(obj.toJS(this.params));
    }
}