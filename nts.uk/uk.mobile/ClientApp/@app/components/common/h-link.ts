import { component, Prop } from '@app/core/component';
import { Vue } from '@app/provider';

@component({
    template: `
        <div class="nts-link" :style="{ textAlign: 'center' } ">
            <a :href="jumpto">
                <slot></slot>
            </a>
        </div>
    `
})
export class HLink extends Vue {
    @Prop() 
    jumpto: String;
}

Vue.component('nts-h-link', HLink);