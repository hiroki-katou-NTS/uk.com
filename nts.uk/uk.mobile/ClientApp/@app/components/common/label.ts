import { Vue } from '@app/provider';
import { IRule } from 'declarations';
import { obj, constraint } from '@app/utils';
import { component, Prop } from '@app/core/component';

@component({
    template: `<div class="control-label control-label-block mb-1"
    v-bind:class="{ 'control-label-danger': constraint.required }">
        <span><slot /></span>
        <template v-if="primitive"><span>{{primitive}}</span></template>
        <template v-else />
    </div>`
})
class LabelComponent extends Vue {
    @Prop({ default: () => ({}) })
    public readonly constraint!: IRule;

    @Prop({ default: () => true })
    public readonly showConstraint!: 'true' | 'fasle' | boolean;

    get primitive(): string {
        let self = this;
        if ((self.showConstraint === true || self.showConstraint === 'true')
            && !!Object.keys(self.constraint).filter((k) => k != 'dirty').length) {
            let $const = constraint.html(obj.cloneObject(self.constraint));

            if ($const) {
                return `(${$const})`;
            }
        }

        return '';
    }
}

Vue.component('v-label', LabelComponent);
Vue.component('nts-label', LabelComponent);