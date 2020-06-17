import { Vue } from '@app/provider';
import { component, Prop } from '@app/core/component';

@component({
  template: `<div class="accordion">
    <div  class="card show">
      <div class="card-header">
        <button class="btn btn-link" type="button">
          {{title}}
        </button>
      </div>
      <div class="collapse">
        <div v-for="item in items"  v-if="item.isVisible" :key="item.content" class="card-body">
          {{item.content}}
        </div>
      </div>
    </div>
  </div>`
})
export class ItemKAF00A extends Vue {
  @Prop()
  public items!: Array<String>;
  @Prop()
  public title: string;
  public created() {
    console.log('created: ItemKAF00A ');
  }
  public mounted() {
    console.log('mounted: ItemKAF00A ');
  }


}
Vue.component('nts-search-box', ItemKAF00A);
Vue.component('nts-item-kafsoo-a', ItemKAF00A);