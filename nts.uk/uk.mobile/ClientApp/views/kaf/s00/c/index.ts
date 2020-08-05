import { Vue, _ } from '@app/provider';
import { component, Prop, Watch } from '@app/core/component';

@component({
    name: 'kafs00c',
    style: require('./style.scss'),
    template: require('./index.vue'),
    resource: require('./resources.json'),
    validations: {
        params: {
            output: {
                opAppReason: {
                    constraint: 'AppReason'
                }         
            }
        }
    },
    constraints: [
        'nts.uk.ctx.at.request.dom.application.AppReason'  
    ]
})
export class KafS00CComponent extends Vue {
    @Prop({ default: () => ({}) })
    public params: { 
        // KAFS00_C_起動情報
        input: {
            // 定型理由の表示
            displayFixedReason: number,
            // 申請理由の表示
            displayAppReason: number,
            // 定型理由一覧
            reasonTypeItemLst: Array<ReasonTypeItemDto>,
            // 申請制限設定
            appLimitSetting: any,
            // 選択中の定型理由
            opAppStandardReasonCD?: number,
            // 入力中の申請理由
            opAppReason?: string 
        },
        output: {
            // 定型理由
            opAppStandardReasonCD: number,
            // 申請理由
            opAppReason: string
        }
    };
    public dropdownList: Array<any> = [];
    public currentAppReasonCD: any = 0;

    public created() {
        const self = this;
        let dropdownList = [{
            appStandardReasonCD: '',
            displayOrder: 0,
            defaultValue: false,
            opReasonForFixedForm: self.$i18n('KAFS00_23'),   
        }];
        self.dropdownList = _.concat(dropdownList, self.$input.reasonTypeItemLst);
        if (self.$input.opAppStandardReasonCD) {
            self.$output.opAppStandardReasonCD = _.find(self.dropdownList, (o: ReasonTypeItemDto) => {
                                                    return o.appStandardReasonCD == self.$input.opAppStandardReasonCD;
                                                }).appStandardReasonCD;
        } else {
            let defaultReasonCD = _.find(self.dropdownList, (o: ReasonTypeItemDto) => o.defaultValue);
            if (defaultReasonCD) {
                self.$output.opAppStandardReasonCD = defaultReasonCD.appStandardReasonCD;  
            } else {
                self.$output.opAppStandardReasonCD = _.head(self.dropdownList).appStandardReasonCD;
            }
        }
        if (self.$input.opAppReason) {
            self.$output.opAppReason = self.$input.opAppReason;
        }

        if (self.$input.appLimitSetting.standardReasonRequired) {
            self.$updateValidator('params.output.opAppStandardReasonCD', { 
                required: true
            });    
        }
        if (self.$input.appLimitSetting.requiredAppReason) {
            self.$updateValidator('params.output.opAppReason', { 
                required: true
            });
        }
    }

    get $input() {
        const self = this;

        return self.params.input;
    }

    get $output() {
        const self = this;

        return self.params.output;
    }

    get displayFixedReason() {
        const self = this;

        return self.params.input.displayFixedReason == 0 ? false : true;
    }

    get displayAppReason() {
        const self = this;

        return self.params.input.displayAppReason == 0 ? false : true;
    }

    get dispReason() {
        const self = this;

        return self.displayFixedReason || self.displayAppReason;
    }
}

interface ReasonTypeItemDto {
    appStandardReasonCD: any;
    displayOrder: number;
    defaultValue: boolean;
    opReasonForFixedForm?: string;     
}