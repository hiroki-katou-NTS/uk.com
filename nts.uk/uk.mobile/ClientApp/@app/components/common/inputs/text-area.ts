
import { component, Prop, Emit } from '@app/core/component';
import { Vue } from '@app/provider';
import { input, InputComponent } from '@app/components/common/inputs/input';

@input('textarea')
export class TextArea extends InputComponent {
    get rawValue() {
        return (this.value || '');
    }

    @Emit()
    input() {
        return (<HTMLInputElement>this.$refs.input).value;
    }
}

Vue.component('nts-text-area', TextArea);
