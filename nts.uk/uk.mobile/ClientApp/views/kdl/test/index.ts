import { component, Prop } from '@app/core/component';
import { _, Vue } from '@app/provider';
import { Kdl001Component } from '../001/index';
import { KDL002Component } from '../002/index';

@component({
    name: 'kdltest',
    route: '/kdl/test',
    style: require('./style.scss'),
    template: require('./index.html'),
    resource: require('./resources.json'),
    validations: {},
    constraints: [],
    components: {
        'worktime': Kdl001Component,
        'worktype': KDL002Component
    }
})
export class KdlTestComponent extends Vue {
    public title: string = 'KdlTest';
    public seledtedWkTypeCDs = '001,002,003,004,005,006,007';
    public seledtedWkTimeCDs = '001,002,003,004,005,006,007,008';
    public isSelectWorkTime = 1;
    public selectedWorkType: any = {};
    public selectedWorkTime: any = {};
    public isAddNone = 1;
    public openKDL002() {
        let self = this;
        self.$modal(
            'worktype',
            {
                seledtedWkTypeCDs: self.seledtedWkTypeCDs.split(','),
                selectedWorkTypeCD: self.selectedWorkType.workTypeCode,
                isAddNone: self.isAddNone,
                seledtedWkTimeCDs: self.seledtedWkTimeCDs.split(','),
                selectedWorkTimeCD: self.selectedWorkTime.code,
                isSelectWorkTime: self.isSelectWorkTime
            }
        ).then((f: any) => {
            let self = this;
            if (f) {
                console.log(f);
                self.selectedWorkType = f.selectedWorkType;
                self.selectedWorkTime = f.selectedWorkTime || {};
            }
        });
    }
    public openKDL001() {
        let self = this;
        self.$modal(
            'worktime',
            {
                isAddNone: self.isAddNone,
                seledtedWkTimeCDs: self.seledtedWkTimeCDs.split(','),
                selectedWorkTimeCD: self.selectedWorkTime.code,
                isSelectWorkTime: self.isSelectWorkTime
            }
        ).then((f: any) => {
            if (f) {
                //console.log(f);
                self.selectedWorkType = f.selectedWorkType || {};
                self.selectedWorkTime = f.selectedWorkTime;
            }
        });
    }

}