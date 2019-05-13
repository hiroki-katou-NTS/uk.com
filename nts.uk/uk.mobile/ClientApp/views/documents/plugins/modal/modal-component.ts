import { Vue } from '@app/provider';
import { component, Prop } from '@app/core/component';

@component({
    template: `<div class="modal-component">
        <div class="modal-header">
            <h4 class="modal-title">
                <i class="fa fa-arrow-left btn-close text-dark mr-2" v-on:click="$close()"></i>
                <span>{{'title' | i18n}}</span>
            </h4>
        </div>
        <div>Hello {{params.name | i18n}} component!</div>
        <div class="modal-footer">
            <button class="btn btn-link" v-click="acceptEvent">{{'accept' | i18n}}</button>
            <button class="btn btn-link" v-click="cancelEvent">{{'cancel' | i18n}}</button>
        </div>
    </div>`
})
export class ModalComponent extends Vue {
    @Prop({ default: () => ({ name: '' }) })
    public params!: { [key: string]: any };

    public acceptEvent() {
        this.$close('accept');
    }

    public cancelEvent() {
        this.$close('cancel');
    }
}