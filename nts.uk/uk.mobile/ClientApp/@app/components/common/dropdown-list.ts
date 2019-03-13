import { component, Prop } from '@app/core/component';
import { Vue } from '@app/provider';

@component({
    template: `
        <dropdown-list-renderless :slotData="fromSlot" :data="data" :dataText="dataText" 
        :dataValue="dataValue" :value="value" :nullText="nullText" :enable="enable" :columns="columns"
        @input="(value) => { $emit('input', value) }">
            <div class="nts-dropdown-list" slot-scope="{ datasource, isSelected, isDisable, changed }" :name="name">
                <span class="select-expand">
                    <i class="fa fa-caret-down fa-3" aria-hidden="true"></i>
                </span>
                <select @change="changed" ref="select">
                    <slot v-if="false"></slot>
                    <template v-for="(option, idx) in datasource">
                        <option :value="option.value" :selected="isSelected(option)" :disable="isDisable">{{ option.text }}</option>
                    </template>
                </select>
            </div>
        </dropdown-list-renderless>
	`
})
export class DropDownList extends Vue {

    @Prop()
    name: String;

    @Prop()
    data: Array<any>;

    @Prop()
    dataText: String;

    @Prop()
    dataValue: String;

    @Prop()
    value: [String, Number];

    @Prop()
    nullText: String;

    @Prop()
    enable: Boolean;

    @Prop()
    columns: Array<any>;

    fromSlot() {
        return this.$slots.default;
    };

    mounted() {
        let select = <HTMLInputElement>this.$refs.select;
        this.$emit("input", select.value);
    }

}

Vue.component('nts-dropdown-list', DropDownList);