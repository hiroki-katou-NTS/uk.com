import { input, InputComponent } from "@app/components/common/inputs/input";
import { Emit } from '@app/core/component'
import { Vue } from '@app/provider'

@input()
class PasswordComponent extends InputComponent {
    type: string = 'password';

    get rawValue() {
        return (this.value || '');
    }

    @Emit()
    input() {
        return (<HTMLInputElement>this.$refs.input).value;
    }
}

Vue.component('nts-input-password', PasswordComponent);