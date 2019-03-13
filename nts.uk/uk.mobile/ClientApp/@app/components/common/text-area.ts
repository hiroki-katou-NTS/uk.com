
import { component, Prop } from '@app/core/component';
import { Vue } from '@app/provider';

@component({
  template: `
    <span>
      <textarea
      class="form-control"
      :value="value"
      :disabled=!enable
      :readonly=readonly
      @blur="doWhenBlur"
      @change="doWhenChanged"
      @input="doWhenInput"
      @keyup="doWhenKeyUp"
      @keydown="doWhenKeyDown"
      >
      </textarea>
    </span>
    `,
})
export class TextArea extends Vue {
  @Prop()
  value : String;

  @Prop({ type: Boolean, default: true})
  enable;

  @Prop({ type: Boolean, default: false })
  readonly;

  @Prop({type: Boolean, default: false })
  immediate;

  @Prop()
  option : Object;

  doWhenBlur() {
    console.log("Bluring Bluring Bluring");
  }

  doWhenChanged() {
    console.log("Value was Changed");
  }

  doWhenInput(e) {
    this.$emit('input', e.target.value);
  }

  doWhenKeyUp(e) {
    console.log("You have just key up");
  }

  doWhenKeyDown(){
    console.log("You have just key down");
  }

}

Vue.component('nts-text-area', TextArea);
