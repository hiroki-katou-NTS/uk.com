import { Vue } from '@app/provider';
import { Emit } from '@app/core/component';
import { input, InputComponent } from "./input";

@input('select')
class DropdownComponent extends InputComponent {
    get rawValue() {
        return this.value;
    }

    mounted() {
        //this.icons.after = 'fa fa-caret-down';
    }

    @Emit()
    input() {
        return (<HTMLSelectElement>this.$refs.input).value;
    }
}

Vue.component('nts-dropdown', DropdownComponent);