import { Vue, moment } from '@app/provider';
import { component, Prop } from '@app/core/component';
import * as _ from 'lodash';
import { KDL002Component } from '../../../kdl/002';
import { Kdl001Component } from '../../../kdl/001';

interface Parameter {
    date: string;
    opAchievementDetail: {
        opLeaveTime: number;
        opWorkTime: number;
        opWorkTimeName: string;
        opWorkTypeName: string;
        workTimeCD: string;
        workTypeCD: string;
    };
}

const defaultParam = (): Parameter => ({
    date: '',
    opAchievementDetail: {
        opLeaveTime: 0,
        opWorkTime: 0,
        opWorkTimeName: '',
        opWorkTypeName: '',
        workTimeCD: '',
        workTypeCD: ''
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
    public timetowork: number = null;
    public leavetime: number = null;

    
    public openKDLS02() {
        const vm = this;
        vm.$modal(KDL002Component, {}).then(console.log);
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

