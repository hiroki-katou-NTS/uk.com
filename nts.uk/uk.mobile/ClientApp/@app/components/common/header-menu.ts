import { component, Prop } from '@app/core/component';
import { Vue } from '@app/provider';

@component({
    template: `
        <div class="nts-header-menu">
            <div class="menu-title"><slot></slot></div>
            <div class="menu-notify">
                <help-button icon-class="fa fa-bars" >notifies.length</help-button>
            </div>
        </div>
	`
})
export class HeaderMenu extends Vue {
    
    @Prop()
    notifies: any;
    
}

Vue.component('nts-header-menu', HeaderMenu);