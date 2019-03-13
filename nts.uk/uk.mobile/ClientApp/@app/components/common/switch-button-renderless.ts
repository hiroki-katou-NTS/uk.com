import { component, Prop } from '@app/core/component';
import { Vue } from '@app/provider';

@component({})
export class SwitchButtonRenderless extends Vue {

    @Prop()
    data: any;

    @Prop()
    dataValue: any;

    @Prop()
    dataText: any;

    @Prop()
    value: any;

    id = this.randomId();

    textKey = this.isNullOrEmpty(this.dataText) ? "text" : this.dataText;

    valueKey = this.isNullOrEmpty(this.dataValue) ? "value" : this.dataValue;


    randomId() {
        return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
            var r = Math.random() * 16 | 0;
            return ((c == 'x') ? r : (r & 0x3 | 0x8)).toString(16);
        });
    }

    get checked() {
        return this.value;
    }

    set checked(value) {
        this.$emit("input", value);
    }

    changed(option: any){
        this.checked = this.valueFor(this.data.indexOf(option));
    }
    
    valueFor(idx: any){
        let value = this.data[idx][this.valueKey];
        return value === null || value === undefined ? idx : value;
    }
    
    textFor(idx: any){
        let text = this.data[idx][this.textKey];
        return text === null || text === undefined ? idx : text;
    }
    
    idFor(idx: any){
        return this.id + "-" + idx;
    }
    
    isChecked(option: any){
        return this.checked === this.valueFor(this.data.indexOf(option));
    }

    render(){
		return this.$scopedSlots.default({
			group: this.id,
			value: this.valueFor,
			text: this.textFor,
			changed: this.changed,
			options: this.data,
			itemId: this.idFor,
			isChecked: this.isChecked
		});
	}

}

Vue.component('nts-switch-button-renderless', SwitchButtonRenderless);