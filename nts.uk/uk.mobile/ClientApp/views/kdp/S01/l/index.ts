import { component, Prop } from '@app/core/component';
import { _, Vue } from '@app/provider';
import { model } from 'views/kdp/S01/shared/index.d';

@component({
    name: 'kdpS01l',
    style: require('./style.scss'),
    template: require('./index.vue'),
    resource: require('./resources.json'),
    constraints: []
})

export class KdpS01LComponent extends Vue {
    public taskName: string = '';
    public setting: any = {
        buttons: [
            { type: 1, dispName: '名駅広場', url: '' },
            { type: 2, dispName: '栄１丁目', url: '' },
            { type: 3, dispName: '広小路YYパーキング１２', url: '' },
            { type: 4, dispName: '大須演芸ホール２', url: '' },
            { type: 5, dispName: '名古屋市鶴舞図書館', url: '' },
            { type: 6, dispName: '１２３４５６７８９０１２３４５６７８９０１２３４５', url: '' },
        ]
    };

    public created() {
    }

    public mounted() {
    }

    public toPreviousTask() {

    }

    public toNextTask() {

    }

    public isFirstTask(): Boolean {
        return true;
    }

    public isLastTask(): Boolean {
        return true;
    }

    public isEmptyTask(): Boolean {
        return false;
    }


}

