import { obj } from '@app/utils';
import { Vue } from '@app/provider';
import { Emit } from '@app/core/component';
import { select, SelectBoxComponent } from './select';

@select()
class RadioBoxComponent extends SelectBoxComponent {
    type: string = 'radio';

    get checked() {
        return this.selected === this.value;
    }

    @Emit('input')
    onClick() { return this.value; }
}

Vue.component('nts-radio', RadioBoxComponent);