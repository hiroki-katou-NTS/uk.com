import { Vue, _ } from '@app/provider';
import { component, Prop, Watch } from '@app/core/component';

@component({
    name: 'kafs00c',
    style: require('./style.scss'),
    template: require('./index.vue'),
    resource: require('./resources.json'),
    validations: {
        reason: {
            constraint: 'AppReason'
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

    public created() {
        const self = this;
        let dropdownList = [{
            appStandardReasonCD: 0,
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
    appStandardReasonCD: number;
    displayOrder: number;
    defaultValue: boolean;
    opReasonForFixedForm?: string;     
}