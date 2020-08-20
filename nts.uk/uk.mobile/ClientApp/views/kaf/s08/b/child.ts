import { Vue } from '@app/provider';
import { component, Prop } from '@app/core/component';

@component({
    template: `<div class="cp">Hello {{title | i18n}} component!</div>`
})
export class CpComponent extends Vue {
    @Prop({ default: () => 'Cp'})
    public  title!: string;

    mounted() {
        const vm = this;

        //vm.title = "XYZ";

        vm.$goto('kafs08b', { id: 100, name: 'xxx'})

        vm.$emit('xyz', 'XYZ');
    }
}