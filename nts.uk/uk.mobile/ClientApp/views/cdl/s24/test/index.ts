import { _, Vue } from '@app/provider';
import { component } from '@app/core/component';
import { CdlS24AComponent } from '../a';

@component({
    name: 'cdls24test',
    route: '/cdl/s24/test',
    template: require('./index.vue'),
    resource: require('./resources.json'),
    validations: {},
    constraints: []
})
export class CdlS24TestComponent extends Vue {
    public title: string = 'CdlS24Test';
    public returnCode: string = null;
    public data = {
        selectedCode: ''
    };

    public openCdls24() {
        let self = this;
        self.$modal(CdlS24AComponent, self.data).then((returnCode: any) => {
            self.returnCode = returnCode;       
        });
    }
}