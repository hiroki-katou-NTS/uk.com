import { Vue } from '@app/provider';
import { component } from '@app/core/component';
import { CdlS04AComponent } from '../a';
@component({
    name: 'cdls04test',
    route: '/cdl/s04/test',
    style: require('./style.scss'),
    template: require('./index.vue'),
    resource: require('./resources.json'),
    validations: {},
    constraints: []
})
export class CdlS04TestComponent extends Vue {
    public title: string = 'CdlS04Test';
    public returnCode: string = null;
    public data = {
        isShowNoSelectRow: false,
        selectedCode: '0000000001'
    };

    public openCdlS04A() {
        let self = this;
        self.$modal(CdlS04AComponent, self.data).then((returnCode: any) => {
            self.data.selectedCode = returnCode;
        });
    }
}