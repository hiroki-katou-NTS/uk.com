import { component, Prop } from '@app/core/component';
import { Vue } from '@app/provider';

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

    id = this.randomId();

    randomId() {
        return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
            var r = Math.random() * 16 | 0;
            return ((c == 'x') ? r : (r & 0x3 | 0x8)).toString(16);
        });
    }

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