import { component, Prop } from '@app/core/component';
import { Vue } from '@app/provider';

@component({
    template: `<label class="nts-checkbox" :class="mode">
                    <input type="checkbox" :name="name" v-model="checked"/>
                    <i class="fa fa-square-o"></i>
                    <slot></slot>
				</label>`
})
export class CheckBox extends Vue {

    @Prop()
    mode: any;

    @Prop()
    value: any;

    @Prop()
    name: any;


    get checked() {
        return this.value;
    }

    set checked(value) {
        this.$emit("input", value);
    }
}

Vue.component('nts-check-box', CheckBox);