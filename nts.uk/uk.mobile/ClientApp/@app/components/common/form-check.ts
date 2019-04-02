import { obj } from '@app/utils';
import { Vue } from '@app/provider';
import { component, Prop, Emit, Model } from '@app/core/component';


let select = () => component({
    template: `<div class="form-check">
        <label class="form-check-label">
            <input ref="input" :name="name" :type="type" :checked="checked" :disabled="disabled" v-on:click="onClick()" class="form-check-input" />
            <span><slot /></span>
        </label>
    </div>`
});

class SelectBoxComponent extends Vue {
    @Prop({ default: null })
    value!: any;

    @Model('input')
    selected!: any;

    @Prop({ default: null })
    name!: string;

    @Prop({ default: false })
    disabled!: boolean;
}

@select()
class RadioBoxComponent extends SelectBoxComponent {
    type: string = 'radio';

    get checked() {
        return this.selected === this.value;
    }

    @Emit('input')
    onClick() { return this.value; }
}

@select()
class CheckBoxComponent extends SelectBoxComponent {
    type: string = 'checkbox';

    get checked() {
        return obj.isArray(this.selected) ? this.selected.indexOf(this.value) > -1 : this.selected === this.value;
    }

    onClick() {
        let self = this;

        if (obj.isArray(this.selected)) {
            if (self.selected.includes(self.value)) {
                self.selected.splice(self.selected.indexOf(self.value), 1)
            } else {
                self.selected.push(self.value)
            }
        } else {
            if ((<HTMLInputElement>this.$refs.input).checked) {
                this.$emit('input', this.value);
            } else {
                this.$emit('input', undefined);
            }
        }
    }
}

Vue.component('nts-radio', RadioBoxComponent);
Vue.component('nts-checkbox', CheckBoxComponent);