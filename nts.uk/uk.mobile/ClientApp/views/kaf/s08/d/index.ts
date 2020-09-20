import { Vue, moment } from '@app/provider';
import { component, Prop } from '@app/core/component';
import * as _ from 'lodash';
import { KDL002Component } from '../../../kdl/002';
import { Kdl001Component } from '../../../kdl/001';

interface Parameter {
    lstWorkDay: [];
    rowDate: {
        date: string
    };
    businessTripInfoOutput: {
        appDispInfoStartup: {
            appDispInfoWithDateOutput: {
                opWorkTimeLst: []
            }
        }
    };
}

const defaultParam = (): Parameter => ({
    lstWorkDay: [],
    rowDate: {
        date: ''
    },
    businessTripInfoOutput: {
        appDispInfoStartup : {
            appDispInfoWithDateOutput : {
                opWorkTimeLst : []
            }
        }
    }
});

@component({
    name: 'kafs08d',
    style: require('./style.scss'),
    template: require('./index.vue')
})
export class KafS08DComponent extends Vue {
    @Prop({ default: defaultParam })
    public readonly params!: Parameter;

    public title: string = 'KafS08D';
    public test: Date = new Date(2020, 2, 12);
    public seledtedWkTypeCDs = '001,002,003,004,005,006,007';
    public seledtedWkTimeCDs = '001,002,003,004,005,006,007,008';
    public isSelectWorkTime = 1;
    public selectedWorkType: any = {};
    public selectedWorkTime: any = {};
    public isAddNone = 1;


    public openKDLS02() {
        const vm = this;
        vm.$http.post('at', API.callKDLS02, {
            businessTripInfoOutputDto: vm.params.businessTripInfoOutput,
            selectedDate: vm.params.rowDate.date,
        }).then((res: any) => {
            let response = res.data;
            
            if (response) {
                vm.$modal(KDL002Component, {
                    seledtedWkTypeCDs: _.map(_.uniqBy(vm.params.lstWorkDay, (e: any) => e.workTypeCode), (item: any) => item.workTypeCode),
                    //selectedWorkTypeCD: this.model.workType.code,
                    seledtedWkTimeCDs: _.map(vm.params.businessTripInfoOutput.appDispInfoStartup.appDispInfoWithDateOutput.opWorkTimeLst, (item: any) => item.worktimeCode),
                    //selectedWorkTimeCD: this.model.workTime.code,
                    isSelectWorkTime: 1,
                }).then(console.log);
            } else {

            }
            //vm.registerData();
        });
    }

    public openKDLS01() {
        const vm = this;
        vm.$modal(Kdl001Component, {}).then(console.log);
    }

    public created() {
        const vm = this;
    }


    //Đóng dialog
    public close() {
        const vm = this;
        vm.$close();
    }
}

const API = {
    callKDLS02: 'at/request/application/businesstrip/mobile/startKDLS02'
};

