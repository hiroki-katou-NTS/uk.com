import { component, Prop } from '@app/core/component';
import { Vue } from '@app/provider';

@component({
    template: `
        <div :class="{ 'one-line': oneline, 'nts-label' : true, 'form-label': type === 'label' || type === 'required', required: type === 'required'}" >
            
            <label>
                <i v-if="icon !== null && icon !== undefined" 
                    :style=" { backgroundImage: 'url(' + icon + ')' }" 
                    class='nts-icon'>
                </i>
                <slot></slot>
            </label>

            <i v-if="!isNullOrEmpty(constraint)">
                {{ constraint }}
            </i>

        </div>
    `
})
export class FormLabel extends Vue {

    @Prop()
    type: any;

    @Prop()
    icon: any;

    @Prop()
    oneline: any;

    @Prop()
    constraint: any;

}

Vue.component('nts-form-label', FormLabel);