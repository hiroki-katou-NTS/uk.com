import { obj } from '@app/utils';
import { Vue } from '@app/provider';
import { component } from '@app/core/component';

import { WorkTypeComponent, worktypes } from '../../components/kdl';

@component({
    route: '/kdl/002',
    style: require('./style.scss'),
    resource: require('./resources.json'),
    template: require('./index.html'),
    components: {
        'worktype': WorkTypeComponent
    }
})
export class KDL002Component extends Vue {
    mounted() {
        this.pgName = 'worktype';
    }

    listItems: Array<IWorkType> = worktypes().filter((w: any, i: number) => i <= 11);

    chooseWorkType(i: number) {
        let item = this.listItems[i],
            callback = (v) => {
                console.log(i, item, obj.toJS(v));
            };

        this.$modal('worktype', item)
            .onClose(callback);
    }
}

interface IWorkType {
    code: string;
    name: string;
    remark?: string;
}

/*
008　出張（医療）
病院勤務用の出張です。
009　直行
直行での出勤です。
010　出張
直行直帰をする出勤です。
011　出張（医療）
病院勤務用の出張です。
戻る*/