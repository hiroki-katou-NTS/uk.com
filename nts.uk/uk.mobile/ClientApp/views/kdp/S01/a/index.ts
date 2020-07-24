import { component, Prop } from '@app/core/component';
import { _, Vue } from '@app/provider';

@component({
    style: require('./style.scss'),
    template: require('./index.vue'),
    resource: require('./resources.json'),
    validations: {},
    constraints: []
})
export class KdpS01Component extends Vue {
    @Prop({ default: () => ({}) })
    public params!: any;

    public created() {
        let vm = this;

        
    }

    public mounted() {
    }
}