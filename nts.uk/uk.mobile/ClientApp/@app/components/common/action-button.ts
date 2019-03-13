import { component, Prop } from '@app/core/component';
import { Vue } from '@app/provider';

@component({
    template: `<div class="nts-button">
					<button @click="jump" :style="isNullOrEmpty(icon) ? {} : { backgroundImage: 'url(' + icon + ')' }" 
						:class=" { 'nts-button-icon' : icon !== null && icon !== undefined}">
						
						<slot></slot>
					</button>

				</div>`
})
export class ActionButton extends Vue {
    
    @Prop()
    action: any;

    @Prop()
    icon: any;

    @Prop()
    jumpto: any;
    
    jump(){
        if(this.jumpto !== undefined && this.jumpto !== null && this.jumpto.length > 0){
            window.location.href = this.jumpto;
        }
        if(this.action !== undefined){
                this.action();
        }
        
    }

}

Vue.component('nts-action-button', ActionButton);