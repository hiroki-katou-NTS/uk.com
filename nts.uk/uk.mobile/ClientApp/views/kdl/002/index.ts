import { obj } from '@app/utils';
import { Vue } from '@app/provider';
import { component } from '@app/core/component';

import { WorkTypeComponent} from '../../components/kdl';
import { worktypes } from './mockdata';

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

    public selectedWorkType = {};

    public selectedWorkTime = {};

    public chooseWorkType(item: IWorkType) {
        if (item) {

            this.selectedWorkType = item;

            this.$modal('worktype')
                .then((v: any) => {
                    if (v) {
                        this.selectedWorkTime = {
                            code: v.code,
                            name: v.name,
                            remark: v.remark
                        };
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

interface IWorkTime {
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