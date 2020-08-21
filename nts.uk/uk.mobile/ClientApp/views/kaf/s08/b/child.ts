import { Vue } from '@app/provider';
import { component, Prop } from '@app/core/component';

@component({
    template: `<div class="cp">Hello {{title | i18n}} component!</div>`
})
export class CpComponent extends Vue {
    @Prop({ default: () => 'Cp'})
    public  title!: string;
    public date!: string;

    public mounted() {
        const vm = this;

        vm.date = '20/03/2020';


        //vm.$emit('changeDateSetting',"20/3/2020");
        //vm.title = "XYZ";

        vm.$goto('kafs08b', { id: 100, name: 'xxx'});


        vm.$emit('xyz', 'XYZ');
    }
}
