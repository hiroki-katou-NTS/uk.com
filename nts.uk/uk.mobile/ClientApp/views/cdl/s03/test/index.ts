import { Vue } from '@app/provider';
import { component } from '@app/core/component';
import { CdlS03AComponent } from '../a';

@component({
    name: 'cdls03test',
    route: '/cdl/s03/test',
    style: require('./style.scss'),
    template: require('./index.vue'),
    resource: require('./resources.json'),
    validations: {},
    constraints: []
})
export class CdlS03TestComponent extends Vue {
    public title: string = 'CdlS03Test';
    public returnCode: string = null;
    public data = {
        isShowNoSelectRow: false,
        selectedCode: '0000000001'
    };

    public openCdlS03A() {
        let self = this;
        self.$modal(CdlS03AComponent, self.data).then((returnCode: any) => {
            self.data.selectedCode = returnCode;       
        });
    }
}