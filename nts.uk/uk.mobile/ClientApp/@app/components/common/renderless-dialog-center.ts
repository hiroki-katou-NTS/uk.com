import { component, Prop } from '@app/core/component';
import { Vue } from '@app/provider';

@component({})
export class RenderlessDialogCenter extends Vue {

    @Prop()
    dialogs: any;

    removeDialog(dialog:any){
    	this.$emit("updated", this.dialogs.filter(d => dialog.id !== d.id));
    }

    render(){
        return this.$scopedSlots.default({
          hide: this.removeDialog
      });
    }

}

Vue.component('nts-renderless-dialog-center', RenderlessDialogCenter);