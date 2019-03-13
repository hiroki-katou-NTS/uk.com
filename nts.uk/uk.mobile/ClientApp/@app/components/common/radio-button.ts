import { component, Prop } from '@app/core/component';
import { Vue } from '@app/provider';

@component({
    template: `
        <label class="nts-radiobox">
              <input ref="radio" type="radio" :name="name" :checked="isChecked()" @change="changed"/>
              <i class="fa fa-circle-o"></i>
              <slot></slot>
        </label>
    `
})
export class RadioButton extends Vue {

    @Prop()
    keyValue: any;

    @Prop()
    name: any;

    @Prop()
    value: any;

    get selected() {
        return this.value;
    }

    set selected(value) {
        this.$emit("input", this.keyValue);
    }

    changed() {
        this.selected = this.keyValue;
    }

    isChecked() {
        return this.selected === this.keyValue;
    }
}

Vue.component('nts-radio-button', RadioButton);