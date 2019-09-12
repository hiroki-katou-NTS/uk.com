import { Vue } from '@app/provider';
import { component, Prop } from '@app/core/component';
import { KdwS03CComponent } from 'views/kdw/s03/c';
import { KdwS03FComponent } from 'views/kdw/s03/f';
import { KdwS03GComponent } from 'views/kdw/s03/g';

@component({
    name: 'kdws03amenu',
    style: require('../style.scss'),
    template: require('./index.vue'),
    resource: require('../resources.json'),
    components: {
        'kdws03c': KdwS03CComponent,
        'kdws03f': KdwS03FComponent,
        'kdws03g': KdwS03GComponent,
    }
})
export class KdwS03AMenuComponent extends Vue {
    @Prop({ default: () => ({ displayFormat: '' }) })
    public params: MenuParam;

    public title: string = 'KdwS03AMenu';

    public openKdws03c() {
        this.$modal('kdws03c', {}, { type: 'dropback', title: 'KDWS03_6' })
            .then((v) => {

            });
    }
    public openKdws03f(param: number) {
        this.$modal('kdws03f', {}, { type: 'dropback' });
    }
    public openKdws03g(param: number) {
        console.log(param);
        this.$modal('kdws03g', { 'remainOrtime36': param }, { type: 'dropback' });
    }
}
interface MenuParam {
    displayFormat: string;
    restReferButtonDis: boolean;
    monthActualReferButtonDis: boolean;
    timeExcessReferButtonDis: boolean;
    allConfirmButtonDis: boolean;
}