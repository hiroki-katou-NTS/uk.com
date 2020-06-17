
import { component, Prop, Watch } from '@app/core/component';
import { _, Vue } from '@app/provider';
import { ItemKAF00A } from '../components/item_a/index';
import { Item, InputPram,SubItem } from '../ModelClass/ItemA';

@component({
    template: require('./index.html'),
    constraints: [],
    components: {
        'nts-item-kafsoo-a': ItemKAF00A
    }
})

export class KAFSOOA extends Vue {
    @Prop()
    public items ?: Array<Item>;
    public created() {
        console.log('created: KAFSOOA ');
    }
    public mounted() {
        console.log('mounted: KAFSOOA ');
    }
    // call API to assign items's value
    // data input is from each application screen
    public getDataAPI(ip: InputPram) {
        console.log('getDataAPI');
        // if done
        let dataSource = [];
        let a: SubItem = { isVisible: true, content: 'ContentA' };
        let b: SubItem = { isVisible: true, content: 'DEF' };
        let c: SubItem = { isVisible: true, content: 'Content1' };
        let d: SubItem = { isVisible: true, content: 'Content1' };
        let insert: Array<SubItem>;
        insert = [];
        insert.push(a);
        dataSource.push({
            title: 'A',
            lstItem: insert,
            isVisible: true
        });
        insert = [];
        insert.push(b);
        dataSource.push({
            title: 'B',
            lstItem: insert,
            isVisible: true
        });
        insert = [];
        insert.push(c);
        insert.push(b);
        dataSource.push({
            title: 'C',
            lstItem: insert,
            isVisible: true
        });
        this.items = dataSource;

    }
}
Vue.component('nts-kafs00-a', KAFSOOA);
Vue.component('nts-kafs00-a', KAFSOOA);