import { Vue } from '@app/provider';
import { component, Prop } from '@app/core/component';

@component({
    template: `<div>{{abx}}
        <hr />
        <button class="btn btn-primary" v-on:click="close()">Accept</button>
        <button class="btn btn-secondary" v-on:click="close()">Cancel</button>
    </div>`
})
export class SampleComponent extends Vue {
    @Prop({
        default: ''
    })
    params?: any;

    @Prop({

    })
    abx: string;

    get value() {
        return this.abx;
    }

    close() {
        this.$close({ id: 100 });
    }
}