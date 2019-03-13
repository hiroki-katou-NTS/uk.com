import { component, Prop } from '@app/core/component';
import { Vue } from '@app/provider';

@component({
    template: `
        <switch-button-renderless :data="data" 
            :dataValue="dataValue" 
            :dataText="dataText" 
            :value="value"
            @input="(value) => { $emit('input', value) }">

            <div class="nts-switch-button" 
                :name="name" 
                slot-scope="{ group, itemId, value, text, changed, options, isChecked }">

                <template v-for="(one, idx) in options">

                    <input type="radio" 
                        :name="group" 
                        class="switch-check none-display" 
                        :id="itemId(idx)" :value="value(idx)" 
                        :checked="isChecked(one)" 
                        @change="changed(one)"/>

                    <label class="switch-button-area" :for="itemId(idx)"> 
                        {{ text(idx) }}
                    </label>

                </template>
            </div>

        </switch-button-renderless>
    `
})
export class SwitchButton extends Vue {

    @Prop()
    data: any;

    @Prop()
    name: any;

    @Prop()
    dataValue: any;

    @Prop()
    dataText: any;

    @Prop()
    value: any;

}

Vue.component('nts-switch-button', SwitchButton);