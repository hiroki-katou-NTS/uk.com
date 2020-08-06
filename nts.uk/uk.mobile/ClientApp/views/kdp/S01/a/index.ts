import { component, Prop } from '@app/core/component';
import { _, Vue } from '@app/provider';
import { model } from 'views/kdp/S01/a/index.d';

const servicePath = {
    checkCanUseStamp: 'at/record/stamp/smart-phone/check-can-use-stamp',
    getSetting: 'at/record/stamp/smart-phone/get-setting/'
};

@component({
    name: 'kdpS01a',
    route: '/kdp/s01/a',
    style: require('./style.scss'),
    template: require('./index.vue'),
    resource: require('./resources.json'),
    constraints: []
})


export class KdpS01AComponent extends Vue {
    @Prop({ default: () => ({}) })
    public params!: any;
    public setting: ISetting = {
        stampPageComment: {
            pageComment: '打刻入力を忘れず、行って下さい',
            commentColor: '#fabf8f'
        },
        buttons: [
            {
                buttonValueType:-1 ,
                buttonPositionNo: 1,
                buttonDisSet: {
                    buttonNameSet: {
                        textColor: '#0000ff',
                        buttonName: '出勤'
                    },
                    backGroundColor: '#E4C9FF'
                }
            },
            {
                buttonValueType:-1 ,
                buttonPositionNo: 2,
                buttonDisSet: {
                    buttonNameSet: {
                        textColor: '#0000ff',
                        buttonName: '退勤'
                    },
                    backGroundColor: '#E4C9FF'
                }
            },
            {
                buttonValueType:-1 ,
                buttonPositionNo: 3,
                buttonDisSet: {
                    buttonNameSet: {
                        textColor: '#0000ff',
                        buttonName: '外出'
                    },
                    backGroundColor: '#88D8FF'
                }
            },
            {
                buttonValueType:-1 ,
                buttonPositionNo: 4,
                buttonDisSet: {
                    buttonNameSet: {
                        textColor: '#0000ff',
                        buttonName: '戻り'
                    },
                    backGroundColor: '#88D8FF'
                }
            },
            {
                buttonValueType:-1 ,
                buttonPositionNo: 5,
                buttonDisSet: {
                    buttonNameSet: {
                        textColor: '#0000ff',
                        buttonName: '応援出勤'
                    },
                    backGroundColor: '#FED3C6'
                }
            },
            {
                buttonValueType:-1 ,
                buttonPositionNo: 6,
                buttonDisSet: {
                    buttonNameSet: {
                        textColor: '#0000ff',
                        buttonName: '応援退勤'
                    },
                    backGroundColor: '#FED3C6'
                }
            }
        ]
    };

    public created() {
        let self = this;
        self.startPage();
    }

    private startPage() {
        let self = this,
            param = {};

        self.$mask('show');
        self.$http.post('at', servicePath.checkCanUseStamp, param).then((result: any) => {

            self.$mask('hide');

            let used: CanEngravingUsed = result.data.used;

            if (used !== CanEngravingUsed.AVAILABLE) {

                self.$modal.error({ messageId: self.getErrorMsg(used), messageParams: ['KDPS01_1'] }).then(() => {

                    self.$goto('ccg008a');
                });
            }

            self.$http.post('at', servicePath.getSetting).then((settingSmartPhone: any) => {

                let data: model.ISettingSmartPhone = settingSmartPhone.data;

                if (_.has(data, 'setting.pageLayoutSettings') && data.setting.pageLayoutSettings.length > 0) {

                    let page = _.find(data.setting.pageLayoutSettings, ['pageNo', 1]) as model.IStampPageLayoutDto;

                    if (page) {

                        if (page.lstButtonSet.length > 0) {
                            self.setting.buttons = self.getLstButton(page.lstButtonSet, data.stampToSuppress);
                        }

                        self.setting.stampPageComment = page.stampPageComment;

                    } else {
                        self.$modal.error('Not Found Button Data');
                    }

                } else {
                    self.$modal.error('Not Found Button Data');
                }

            }).catch((res: any) => {
                self.showError(res);
            });


        }).catch((res: any) => {
            self.showError(res);
        });
    }

    private getLstButton(lstButtonSet: Array<model.ButtonSettingsDto>, stampToSuppress: model.IStampToSuppress) {

        let self = this,
            buttonCount = 6,
            resultList: Array<model.ButtonSettingsDto> = [];


        for (let i = 1; i <= buttonCount; i++) {

            let button = _.find(lstButtonSet, ['buttonPositionNo', i]),
                buttonSetting: model.ButtonSettingsDto = {
                    buttonValueType:-1 ,
                    buttonPositionNo: i,
                    buttonDisSet: {
                        buttonNameSet: {
                            textColor: '',
                            buttonName: ''
                        },
                        backGroundColor: ''
                    }
                };


            if (button) {
                buttonSetting = button;
            }

            self.setBtnColor(buttonSetting, stampToSuppress);

            resultList.push(buttonSetting);
        }

        return resultList;
    }

    private setBtnColor(buttonSetting: model.ButtonSettingsDto, stampToSuppress: model.IStampToSuppress) {

        const DEFAULT_GRAY = '#E8E9EB';

        if (buttonSetting.buttonValueType === ButtonType.GOING_TO_WORK) {
            // 出勤
            buttonSetting.buttonDisSet.backGroundColor = stampToSuppress.goingToWork ? buttonSetting.buttonDisSet.backGroundColor : DEFAULT_GRAY;
        }

        if (buttonSetting.buttonValueType === ButtonType.WORKING_OUT) {
            // 退勤
            buttonSetting.buttonDisSet.backGroundColor = stampToSuppress.departure ? buttonSetting.buttonDisSet.backGroundColor : DEFAULT_GRAY;
        }

        if (buttonSetting.buttonValueType === ButtonType.GO_OUT) {
            // 外出
            buttonSetting.buttonDisSet.backGroundColor = stampToSuppress.goOut ? buttonSetting.buttonDisSet.backGroundColor : DEFAULT_GRAY;
        }

        if (buttonSetting.buttonValueType === ButtonType.RETURN) {
            // 戻り
            buttonSetting.buttonDisSet.backGroundColor = stampToSuppress.turnBack ? buttonSetting.buttonDisSet.backGroundColor : DEFAULT_GRAY;
        }
    }



    private getErrorMsg(used: CanEngravingUsed) {
    const msgs = [{ value: CanEngravingUsed.NOT_PURCHASED_STAMPING_OPTION, msg: 'Msg_1644' },
    { value: CanEngravingUsed.ENGTAVING_FUNCTION_CANNOT_USED, msg: 'Msg_1645' },
    { value: CanEngravingUsed.UNREGISTERED_STAMP_CARD, msg: 'Msg_1619' }];

    let item = _.find(msgs, ['value', used]);

    return item ? item.msg : '';
}

    private showError(res: any) {
    let self = this;
    self.$mask('hide');
    if (!_.isEqual(res.message, 'can not found message id')) {
        self.$modal.error({ messageId: res.messageId, messageParams: res.parameterIds });
    } else {
        self.$modal.error(res.message);
    }
}

    public login() {


}

    public mounted() {
}
}

enum CanEngravingUsed {
    // 0 利用できる
    AVAILABLE = 0,

    // 1 打刻オプション未購入
    NOT_PURCHASED_STAMPING_OPTION = 1,

    // 2 打刻機能利用不可
    ENGTAVING_FUNCTION_CANNOT_USED = 2,

    // 3 打刻カード未登録
    UNREGISTERED_STAMP_CARD = 3

}

enum ButtonType {
    // 出勤系
    GOING_TO_WORK = 1,
    // 退勤系
    WORKING_OUT = 2,
    // "外出系"
    GO_OUT = 3,
    // 戻り系
    RETURN = 4
}

interface ITime {
    date: string;
    time: string;
}

interface ISetting {
    buttons: Array<model.ButtonSettingsDto>;
    stampPageComment: model.IStampPageCommentDto;
}