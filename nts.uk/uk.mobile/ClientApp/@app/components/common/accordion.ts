import { component, Prop } from '@app/core/component';
import { Vue } from '@app/provider';

@component({
    template: `
        <div class="nts-accordion">

            <label class="accordion-title" @click.prevent.stop.self="processing">
                <slot name="title"></slot>
                <i class="fa fa-angle-double-down accordion-arrow"></i>
            </label>

            <input ref="input" :type="isNullOrEmpty(group) ? 'checkbox' : 'radio'" 
                    :name="isNullOrEmpty(group) ? '' : group" class="accordion-action none-display" 
                    :checked="openoninit">
            </input>

            <div class="accordion-content">
                <slot name="content"></slot>
            </div>
            
        </div>
    `
})
export class Accordion extends Vue {

    @Prop()
    group: any;

    @Prop()
    openoninit: any;

    processing(){
        let input = (<HTMLInputElement>this.$refs.input);
        input.checked = !input.checked;
    }

}

Vue.component('nts-accordion', Accordion);