import { component, Prop } from '@app/core/component';
import { Vue } from '@app/provider';

@component({
    template: `
        <div class="nts-accordion">

            <label class="accordion-title" @click.prevent.stop.self="processing">
                <slot name="title"></slot>
                <i class="fa fa-angle-double-down accordion-arrow"></i>
            </label>

            <input :id="id" :type="isNullOrEmpty(group) ? 'checkbox' : 'radio'" 
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
    
    id = this.randomId();

    randomId() {
        return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
            var r = Math.random() * 16 | 0;
            return ((c == 'x') ? r : (r & 0x3 | 0x8)).toString(16);
        });
    }

    processing(){
        let input = document.getElementById(this.id);
        input.checked = !input.checked;
    }

}

Vue.component('nts-accordion', Accordion);