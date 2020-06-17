
import { component, Prop, Watch } from '@app/core/component';
import { _, Vue } from '@app/provider';
import { KAFSOOA } from '../a/index';
import {ItemKAF00B} from '../components/item_b/index';

@component({
    name: 'KAFS00_A',
    route: '/kaf/s00/start',
    template: require('./index.html'),
    constraints: [],
    style: require('../../s05/style.scss'),
    components: {
        'nts-kafs00-a': KAFSOOA,
        'nts-item-kafsoo-b': ItemKAF00B
    }
})

export class ViewComponents extends Vue {
    public created() {
        console.log('created: ');
        
    }
}