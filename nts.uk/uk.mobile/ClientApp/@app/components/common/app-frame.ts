import { component, Prop} from '@App/core/component'
import { Vue } from '@app/provider'

@component({
    template: `
    <div id="app-frame" v-if="props.isReady">
        <div id="header">{{ props.systemName }}</div>
            <div id="contents">
                <slot></slot>
            </div>
        <dialog-center></dialog-center>
    </div>
    `
})
export class AppFrame extends Vue {
    
    @Prop()
    props: any;

}

Vue.component('nts-app-frame', AppFrame);