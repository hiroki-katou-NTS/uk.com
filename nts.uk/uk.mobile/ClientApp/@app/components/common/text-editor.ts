import { component, Prop } from '@app/core/component';
import { Vue } from '@app/provider';

@component({
  template: `
  <span >
    <input type="text"
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
    </input>
  </span>
  `
})

export class TextEditor extends Vue {
  @Prop()
  value : String;

  @Prop({ default: true})
  enable: Boolean;

  @Prop({ default: false })
  readonly: Boolean;

  @Prop({ default: false })
  immediate: Boolean;

  @Prop()
  option : Object;

  get valueUpdate() {
    return (this.immediate === true) ? 'input' : 'change';
  }

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

Vue.component('nts-text-editor', TextEditor);
