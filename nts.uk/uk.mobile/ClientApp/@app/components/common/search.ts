import { Vue } from '@app/provider';
import { component, Prop } from '@app/core/component';

@component({
    template: `<div class="form-group mb-1">
    <div class="input-group input-group-transparent input-group-search">
        <div class="input-group-append">
            <span class="input-group-text fa fa-search"></span>
        </div>
        <input ref="input" v-bind:value="value" type="text" class="form-control" v-on:keyup="$emit('input', $refs.input.value)" />
    </div>
</div>`
})
class SearchComponent extends Vue {
    @Prop({ default: '' })
    public readonly value!: string;
}

Vue.component('nts-search-box', SearchComponent);