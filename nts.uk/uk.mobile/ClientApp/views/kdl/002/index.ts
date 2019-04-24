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
    public mounted() {
        this.pgName = 'worktype';
    }

    public listItems: Array<IWorkType> = worktypes().filter((w: any, i: number) => i <= 11);

    public chooseWorkType(item: IWorkType) {
        if (item) {
            this.$modal('worktype', item)
                .then((v: any) => {
                    if (v) {
                        item.code = v.code;
                        item.name = v.name;
                        item.remark = v.remark;
                    }
                });
        }
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