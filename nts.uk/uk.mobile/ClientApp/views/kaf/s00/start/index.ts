
import { component, Prop, Watch } from '@app/core/component';
import { _, Vue } from '@app/provider';
import { KAFSOOA } from '../a/index';
import { Item, SubItem } from '../ModelClass/ItemA';

@component({
    name: 'KAFS00_A',
    route: '/kaf/s00/start',
    template: require('./index.html'),
    constraints: [],
    components: {
        'nts-kafs00-a': KAFSOOA
    }
})

export class ViewComponents extends Vue {
    public created() {
        console.log('created: ');
        
    }
}