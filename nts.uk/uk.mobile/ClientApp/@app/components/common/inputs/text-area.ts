
import { component, Prop, Emit } from '@app/core/component';
import { Vue } from '@app/provider';
import { InputComponent } from '@app/components/common/inputs/input';

@component({
    template: `
    <div class="form-group row">

        <template v-if="showTitle">
            <div v-bind:class="columns.title">
                <v-label v-bind:constraint="constraint">{{ name | i18n }}</v-label>
            </div>
        </template>
        <template v-else />

        <div v-bind:class="columns.input">
            <div class="input-group input-group-transparent">
                <template>
                    <template v-if="icons.before">
                        <div class="input-group-prepend">
                            <span class="input-group-text" v-bind:class="iconsClass.before">{{  !iconsClass.before ? icons.before : '' }}</span>
                        </div>
                    </template>
                    <template v-else />
                    <template v-if="icons.after">
                        <div class="input-group-append">
                            <span class="input-group-text" v-bind:class="iconsClass.after">{{ !iconsClass.after ? icons.after : ''}}</span>
                        </div>
                    </template>
                    <template v-else />
                    <textarea class="form-control"
                        ref="input"
                        v-validate="{
                            always: !!errorsAlways,
                            errors: (errors || errorsAlways || {})
                        }"
                        v-bind:disabled="disabled"
                        v-bind:readonly="!editable"
                        v-bind:value="rawValue"
                        v-on:click="click()"
                        v-on:keydown.13="click()"
                        v-on:input="input()"
                    />
                </textarea>
                </template>
                
                <v-errors v-for="(error, k) in (errors || errorsAlways || {})" v-bind:key="k" v-bind:data="error" v-bind:name="name" />
            </div>
        </div>

    </div>`
})
export class TextArea extends InputComponent {

    type: string = 'text';

    get rawValue() {
        return (this.value || '');
    }

    @Emit()
    input() {
        return (<HTMLInputElement>this.$refs.input).value;
    }

}

Vue.component('nts-text-area', TextArea);
