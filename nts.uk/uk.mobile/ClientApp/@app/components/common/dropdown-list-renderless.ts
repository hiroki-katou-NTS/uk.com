import { component, Prop } from '@app/core/component';
import { Vue } from '@app/provider';
import * as _ from 'lodash';

@component({})
export class DropDownListRenderless extends Vue {

    @Prop()
    name: any;

    @Prop()
    data: any;

    @Prop()
    dataText: any;

    @Prop()
    dataValue: any;

    @Prop()
    value: any;

    @Prop()
    nullText: any;

    @Prop()
    enable: any;

    @Prop()
    columns: any;

    @Prop()
    slotData: any;

    getDataSource(){
        let datasource = [], self = this;
        if(_.isEmpty(self.data)) {
            let slotted = self.slotData();
            if(!_.isEmpty(slotted)){
                _.forEach(slotted, function(slot, idx) {
                    if(slot.tag === "option") {
                        datasource.push(self.getOptionFromSlot(slot));
                    }
                });
            }
            return datasource;
        }
      
        _.forEach(self.data, function(option, idx) {
            datasource.push({
                text: self.buildTextWithColumn(option),
                value: option[self.dataValue]
            });
        });
      
        return datasource;
    }
    
    buildTextWithColumn(option){
        /** TODO: check columns*/
      return _.isNil(option[this.dataText]) ? this.nullText : option[this.dataText];
    }
    
    getOptionFromSlot(slot){
        let option = {};
        option['text'] = slot.children[0].text;
        if(!_.isNil(slot.data)){
            _.forEach(slot.data.attrs, function(value, key) {
                if(key === "label"){
                    option['text'] = value;
                } else {
                    option[key] = value;
                }
            });
        }
        if(_.isEmpty(option["value"])){
            option['value'] = option['text'];
        }
        return option;
    }
    
    isSelected(option){
        if(_.isEmpty(this.value)){
            return !_.isNil(option.selected)
        }
        return option.value === this.value;
    }
    
    isDisable(option){
        return !_.isNil(option.disabled)
    }
    
    changed(event){
        this.$emit("input", event.target.value);
    }

    // hook
    render(){
		return this.$scopedSlots.default({
			datasource: this.getDataSource(),
			isSelected: this.isSelected,
			isDisable: this.isDisable,
			changed: this.changed
		});
	}
    
}

Vue.component('nts-dropdown-list-renderless', DropDownListRenderless);