import { Vue } from '@app/provider';
import { component, Prop } from '@app/core/component';

@component({
    template: `<div class="modal-component">
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