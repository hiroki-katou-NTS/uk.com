import { Vue } from '@app/provider';
import { component, Prop } from '@app/core/component';
import * as _ from 'lodash';
import { KDL002Component } from '../../../kdl/002';
import { Kdl001Component } from '../../../kdl/001';

@component({
    name: 'kafs08d',
    style: require('./style.scss'),
    template: require('./index.vue')
})
export class KafS08DComponent extends Vue {
    @Prop({ default: (): Params => ({ timetowork: null, leavetime: null,date : null}) })
    public readonly params!: Params;

    //public name: string = 'Nittsu System Viet Nam';
    public title: string = 'KafS08D';
    public date: Date = new Date(2020, 2, 12);


    public openKDLS02() {
        const vm = this;
        vm.$modal(KDL002Component, {}).then(console.log);
    }

    public openKDLS01() {
        const vm = this;
        vm.$modal(Kdl001Component, {}).then(console.log);
    }

    public model: Params = {
        timetowork: null,
        leavetime: null,
        date : null,
    };

    public created() {
        const vm = this;
        vm.model.timetowork = vm.params.timetowork;
        vm.model.leavetime = vm.params.leavetime;
        vm.model.date = vm.params.date;
    }


    //Đóng dialog
   public close() {
        const vm = this;
        vm.$close(vm.model);
    }
}

interface Params {
    timetowork: number | null;
    leavetime: number | null;
    date: string | null;
}