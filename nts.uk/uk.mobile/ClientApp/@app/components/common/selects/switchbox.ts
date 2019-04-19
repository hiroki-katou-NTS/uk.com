import { obj, dom } from '@app/utils';
import { Vue } from '@app/provider';
import { Prop } from '@app/core/component';
import { switchbtn, SelectBoxComponent } from './select';



@switchbtn()
class SwitchButtonGroup extends SelectBoxComponent {
    @Prop({ default: 'radio' })
    type!: 'radio' | 'checkbox';

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

    mounted() {
        let el = this.$el as HTMLElement;

        if (el.nodeType !== 8) {
            dom.addClass(el.parentElement, 'btn-group btn-group-toggle mb-3');
        }
    }
}

Vue.component('nts-switchbox', SwitchButtonGroup);