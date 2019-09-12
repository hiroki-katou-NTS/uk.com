import { Vue } from '@app/provider';
import { component, Prop } from '@app/core/component';
import { KdwS03CComponent } from 'views/kdw/s03/c';

@component({
    name: 'kdws03amenu',
    style: require('../style.scss'),
    template: require('./index.vue'),
    resource: require('../resources.json'),
    components: {
        'kdws03c': KdwS03CComponent,
    }
})
export class KdwS03AMenuComponent extends Vue {  
    @Prop({ default: () => ({ displayFormat: ''})})
    public params: MenuParam;

    public title: string = 'KdwS03AMenu';
    public errorScreen: boolean = false;

    public created() {
        if (this.params.displayFormat == '0') {
            this.errorScreen = true;
        }
    }

    public openKdws03c() {
        this.$modal('kdws03c', { }, { type: 'dropback', title: 'KDWS03_6' })
            .then((v) => {

            });
    }
}
interface MenuParam {
    displayFormat: string;
}