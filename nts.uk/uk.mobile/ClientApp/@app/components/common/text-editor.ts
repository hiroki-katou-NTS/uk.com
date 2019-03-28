import { component, Prop, Emit } from '@app/core/component';
import { Vue } from '@app/provider';
import { input, InputComponent } from '@app/components/common/input';

@input()
class StringComponent extends InputComponent {

    type: string = 'text';

    get rawValue() {
        return (this.value || '');
    }

    @Emit()
    input() {
        return (<HTMLInputElement>this.$refs.input).value;
    }
    
}

Vue.component('nts-text-editor', StringComponent);
