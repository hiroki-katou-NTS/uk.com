import { component, Prop } from '@app/core/component';
import { Vue } from '@app/provider';

@component({
    template: `
        <div class="nts-help-button">
			<action-button　:icon="icon"></action-button>
			<div class="nts-help-text">
				<span class="caret-helpbutton caret caret-left"></span>
				<span><slot></slot></span>
			</div>
		</div>
    `
})
export class HelpButton extends Vue {

    @Prop()
    icon: any;

    @Prop()
    iconClass: any;
    
}

Vue.component('nts-help-button', HelpButton);