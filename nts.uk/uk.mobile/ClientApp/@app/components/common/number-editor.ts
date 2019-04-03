import { input, InputComponent } from "@app/components/common/input";
import { Emit } from '@app/core/component'
import { Vue } from '@app/provider'

@input()
class NumberComponent extends InputComponent {
    
    type: string = 'number';

    get rawValue() {
        return (this.value || '').toString();
    }

    @Emit()
    input() {
        let value = (<HTMLInputElement>this.$refs.input).value;

        if (value) {
            let numb = Number(value);

            if (!isNaN(numb)) {
                return numb;
            } else {
                return null;
            }
        }

        return null;
    }
}

Vue.component('nts-number-editor', NumberComponent);