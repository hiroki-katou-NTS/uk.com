import { component, Prop } from '@app/core/component';
import { Vue } from '@app/provider';
import { $uuid } from '@app/utils/uuid'

@component({
    template: `
        <div class="nts-radio-groups">
			<template v-for="one in options">
                    <radio-button 
                            :key="id + '-' + one.key" 
                            @input="selectChanging" 
                            v-model="selected" 
                            :keyValue="one.key" 
                            :name="name">
                        {{ one.text }}
                    </radio-button>
			</template>
        </div>
    `
})
export class RadioBox extends Vue {

    @Prop()
    options: any;

    @Prop()
    enums: any;

    @Prop()
    name: any;

    @Prop()
    value: any;

    id = $uuid.randomId();

    get selected() {
        return this.value;
    }

    set selected(value) {
        this.$emit("input", value);
    }

    selectChanging(val){
        this.selected = val;
    }

}

Vue.component('nts-radio-box', RadioBox);