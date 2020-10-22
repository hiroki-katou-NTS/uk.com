import { Vue, _, moment } from '@app/provider';
import { component, Prop, Watch } from '@app/core/component';
import { vmOf } from 'vue/types/umd';

@component({
    name: 'kafs00b',
    style: require('./style.scss'),
    template: require('./index.vue'),
    resource: require('./resources.json'),
    validations: {
        date: {
            required: true
        },
        dateRange: {
            required: true,
            dateRange: true
        },
        prePostAtr: {
            selectCheck: {
                test(value: number) {
                    const vm = this;
                    if (value == null || value < 0 || value > 1) {
                        document.getElementById('prePostSelect').className += ' invalid';

                        return false;
                    }
                    let prePostSelectElement = document.getElementById('prePostSelect');
                    if (!_.isNull(prePostSelectElement)) {
                        prePostSelectElement.classList.remove('invalid');
                    }

                    return true;
                },
                messageId: 'MsgB_30'
            }
        }
    },
    constraints: []
})
export class KafS00BComponent extends Vue {
    @Prop({ default: () => ({}) })
    public params: KAFS00BParams;
    public prePostResource: Array<Object> = [];
    public dateSwitchResource: Array<Object> = [];
    public prePostAtr: number = null;
    public date: Date = null;
    public dateRange: any = {};

    public created() {
        const self = this;
        self.prePostResource = [{
            code: 0,
            text: 'KAFS00_10'
        }, {
            code: 1,
            text: 'KAFS00_11'
        }];
        self.dateSwitchResource = [{
            code: false,
            text: 'KAFS00_12'
        }, {
            code: true,
            text: 'KAFS00_13'
        }];
        self.dateRange = {
            start: null,
            end: null,
        };
        self.initFromParams();
    }

    @Watch('params')
    public paramsWatcher() {
        const self = this;
        self.initFromParams();
    }

    private initFromParams() {
        const self = this;
        if (!self.params) {
            return;
        }
        if (self.params.newModeContent) {
            if (self.params.newModeContent.appTypeSetting[0].displayInitialSegment != 2) {
                self.prePostAtr = self.params.newModeContent.appTypeSetting[0].displayInitialSegment;
            } else {
                self.prePostAtr = null;
            }
            if (self.params.newModeContent.initSelectMultiDay) {
                self.$updateValidator('dateRange', { validate: true });
                self.$updateValidator('date', { validate: false });
            } else {
                self.$updateValidator('dateRange', { validate: false });
                self.$updateValidator('date', { validate: true });
            }
            if (self.displayPrePost) {
                self.$updateValidator('prePostAtr', { validate: true });
            } else {
                self.$updateValidator('prePostAtr', { validate: false });
            }
        }
        if (self.params.detailModeContent) {
            self.prePostAtr = self.params.detailModeContent.prePostAtr;
            self.$updateValidator('dateRange', { validate: false });
            self.$updateValidator('date', { validate: false });
            self.$updateValidator('prePostAtr', { validate: false });
        }
    }

    get displayPrePost() {
        const self = this;

        return self.params.appDisplaySetting.prePostDisplayAtr == 0 ? false : true;
    }

    get enablePrePost() {
        const self = this;

        return self.params.newModeContent.appTypeSetting[0].canClassificationChange;
    }

    get displayMultiDaySwitch() {
        const self = this;

        return self.params.newModeContent.useMultiDaySwitch;
    }

    get ScreenMode() {
        return ScreenMode;
    }

    get prePostAtrName() {
        const self = this;

        return _.find(self.prePostResource, (o: any) => o.code == self.params.detailModeContent.prePostAtr).text;
    }

    @Watch('params.newModeContent.initSelectMultiDay')
    public initSelectMultiDayWatcher(value: any) {
        const self = this;
        new Promise((resolve) => {
            self.$validate('clear');
            setTimeout(() => {
                resolve(true);
            }, 300);
        }).then(() => {
            if (value) {
                self.$updateValidator('dateRange', { validate: true });
                self.$updateValidator('date', { validate: false });
                self.$validate('dateRange');
                if (self.$valid) {
                    self.$emit('kaf000BChangeDate',
                        {
                            startDate: self.dateRange.start,
                            endDate: self.dateRange.end
                        }); 
                }
                 
            } else {
                self.$updateValidator('dateRange', { validate: false });
                self.$updateValidator('date', { validate: true });
                self.$validate('date');
                if (self.$valid) {
                    self.$emit('kaf000BChangeDate',
                        {
                            startDate: self.date,
                            endDate: self.date
                        }); 
                }
            }
        });
    }

    @Watch('date')
    public dateWatcher(value) {
        const self = this;
        self.$emit('kaf000BChangeDate',
            {
                startDate: value,
                endDate: value
            });
    }

    @Watch('dateRange')
    public dateRangeWatcher(value) {
        const self = this;
        new Promise((resolve) => {
            self.$validate('clear');
            setTimeout(() => {
                resolve(true);
            }, 200);
        })
        .then(() => self.$validate('dateRange'))
        .then(() => self.$valid)
        .then((valid: boolean) => {
            if (valid) {
                self.$emit('kaf000BChangeDate',
                    {
                        startDate: value.start,
                        endDate: value.end
                    });
            }
        });
    }

    @Watch('prePostAtr')
    public prePostAtrWatcher() {
        const self = this;
        if (self.displayPrePost) {
            self.$emit('kaf000BChangePrePost', self.prePostAtr);
        }
    }
}

// 画面モード
export enum ScreenMode {
    // 新規モード
    NEW = 0,
    // 詳細モード
    DETAIL = 1
}

// 新規モード内容
interface NewModeContent {
    // 申請種類別設定
    appTypeSetting: any;
    // 複数日切り替えを利用する
    useMultiDaySwitch: boolean;
    // 複数日を初期選択する
    initSelectMultiDay: boolean;
    // 申請日
    appDate?: Date;
    // 申請日期間
    dateRange?: any;
}

// 詳細モード内容
interface DetailModeContent {
    // 事前事後区分
    prePostAtr: number;
    // 申請者名
    employeeName: string;
    // 申請開始日
    startDate: string;
    // 申請終了日
    endDate: string;
}

// KAFS00_B_起動情報
export interface KAFS00BParams {
    // 画面モード
    mode: ScreenMode;
    // 申請表示設定
    appDisplaySetting: any;
    // 新規モード内容
    newModeContent?: NewModeContent;
    // 詳細モード内容
    detailModeContent?: DetailModeContent;
}