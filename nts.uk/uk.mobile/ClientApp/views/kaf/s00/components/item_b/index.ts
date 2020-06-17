import { Vue } from '@app/provider';
import { component, Prop } from '@app/core/component';

@component({
  template: `<div class="card">
  <div class="card-header">
    <span>Title</span>
    <span>primitivevlaue</span>
    <span class="badge badge-warning">Warn</span>
  </div>
  <div class="card-body">
    <!-- ddd -->
    <div v-for="item in items">
        <div class="card-body mb-3 mt-2">
            <div>1</div><div>2</div>
        </div>
    </div>
  </div>
</div>`
})
export class ItemKAF00B extends Vue {
  @Prop()
  public items!: Array<String>;
  @Prop()
  public title: string;
  public created() {
    console.log('created: ItemKAF00B ');
    this.items = ['dd','ggg'];
  }
  public mounted() {
    console.log('mounted: ItemKAF00B ');
  }
  // use v-for
  public datasource = [
    { id: 1, name: 'Option 1' },
    { id: 2, name: 'Option 2' }
];
public switchbox2: number = 1;

}
Vue.component('nts-item-kafsoo-b', ItemKAF00B);
Vue.component('nts-item-kafsoo-b', ItemKAF00B);