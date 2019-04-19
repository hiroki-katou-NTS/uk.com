import { Vue } from '@app/provider';
import { Emit } from '@app/core/component';
import { input, InputComponent } from "./input";

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