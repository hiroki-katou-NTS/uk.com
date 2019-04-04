import { InputComponent } from "@app/components/common/inputs/input";
import { Vue } from '@app/provider'
import { component, Emit } from '@app/core/component';


@component({
    template: `
        <div class="form-group row">

            <template v-if="showTitle">
                <div v-bind:class="columns.title">
                    <nts-label v-bind:constraint="constraint" v-bind:class="{ 'control-label-inline': inlineTitle }">
                        {{ name | i18n }}</nts-label>
                </div>
            </template>
            <template v-else />

            <div v-bind:class="columns.input">
                <div class="input-group input-group-transparent">

                    <template v-if="icons.after">
                        <div class="input-group-append">
                            <span class="input-group-text"
                                v-bind:class="iconsClass.after">{{ !iconsClass.after ? icons.after : ''}}</span>
                        </div>
                    </template>

                    <select class="form-control" ref="input" v-validate="{
                                always: !!errorsAlways,
                                errors: (errors || errorsAlways || {})
                            }" v-bind:disabled="disabled" v-bind:value="rawValue" v-on:change="input()">

                        <slot />

                    </select>

                    <v-errors v-for="(error, k) in (errors || errorsAlways || {})" v-bind:key="k" v-bind:data="error"
                        v-bind:name="name" />

                </div>
            </div>

        </div>
    `
})
class DropdownComponent extends InputComponent {
    type: string = 'select';

    get rawValue() {
        return this.value;
    }

    mounted() {
        //this.icons.after = 'fa fa-caret-down';
        (<HTMLSelectElement>this.$refs.input).parentElement.classList.add("nts-dropdown-list");
    }

    @Emit()
    input() {
        return (<HTMLSelectElement>this.$refs.input).value;
    }
}

Vue.component('nts-dropdown', DropdownComponent);