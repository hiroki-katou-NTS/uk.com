import { Vue } from '@app/provider';
import { component, Prop, Model } from '@app/core/component';

export const select = () => component({
    template: `<div class="form-check">
        <label class="form-check-label">
            <input ref="input" :name="name" :type="type" :checked="checked" :disabled="disabled" v-on:click="onClick()" class="form-check-input" />
            <span><slot /></span>
        </label>
    </div>`
}), switchbtn = () => component({
    template: `<label class="btn btn-secondary">
        <input ref="input" :name="name" :type="type" :checked="checked" :disabled="disabled" v-on:click="onClick()" class="form-check-input" />
        <span><slot /></span>
    </label>`
});

export class SelectBoxComponent extends Vue {
    @Prop({ default: null })
    value!: any;

    @Model('input')
    selected!: any;

    @Prop({ default: null })
    name!: string;

    @Prop({ default: false })
    disabled!: boolean;
}