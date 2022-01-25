import { Vue } from '@app/provider';
import { component } from '@app/core/component';
import { KDLS1OAComponent } from '../a';
import * as _ from 'lodash';

@component({
    name: 'kdls10test',
    route: '/kdl/s10/test',
    style: require('./style.scss'),
    template: require('./index.vue'),
    resource: require('./resources.json'),
    validations: {},
    constraints: []
})
export class KdlS10TestComponent extends Vue {
    public title: string = 'KdlS10Test';
    public data = {
        isShowNoSelectRow: false,
        selectedCode: '',
        selectedName: ''
    };

    public openDkls10() {
        let self = this;
        self.$modal(KDLS1OAComponent, self.data).then((result: any) => {
            if (_.isUndefined(result)) {
                return;
            }
            if (result.workLocationCD != '') {
                self.data.selectedCode = result.workLocationCD; 
                self.data.selectedName = result.workLocationName;  
            } else {
                self.data.selectedCode = null; 
                self.data.selectedName = null;    
            } 
        });
    }
}