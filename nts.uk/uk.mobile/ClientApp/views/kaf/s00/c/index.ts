import { Vue, _ } from '@app/provider';
import { component, Prop, Watch } from '@app/core/component';

@component({
    name: 'kafs00c',
    style: require('./style.scss'),
    template: require('./index.vue'),
    resource: require('./resources.json'),
    validations: {
        // params: {
        //     output: {
        //         opAppReason: {
        //             constraint: 'AppReason'
        //         }         
        //     }
        // },
        opAppReason: {
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
        input: KAFS00CParams,
        output: {
            // 定型理由
            opAppStandardReasonCD: number,
            // 申請理由
            opAppReason: string
        }
    };
    public dropdownList: Array<any> = [];
    public opAppStandardReasonCD: any = '';
    public opAppReason: string = '';

    public created() {
        const self = this;
        self.initFromParams();    
    }

    @Watch('params')
    public paramsWatcher() {
        const self = this;
        self.initFromParams();
    }

    @Watch('params')
    private initFromParams() {
        const self = this;
        if (!self.params) {
            return;
        }
        self.dropdownList = [{
            appStandardReasonCD: '',
            displayOrder: 0,
            defaultValue: false,
            reasonForFixedForm: self.$i18n('KAFS00_23'),   
        }];
        _.forEach(self.$input.reasonTypeItemLst, (value) => {
            self.dropdownList.push({
                appStandardReasonCD: value.appStandardReasonCD,
                displayOrder: value.displayOrder,
                defaultValue: value.defaultValue,
                reasonForFixedForm: value.appStandardReasonCD + ' ' + value.reasonForFixedForm,     
            });   
        });


        // self.dropdownList = _.concat(dropdownList, self.$input.reasonTypeItemLst);
        if (self.$input.opAppStandardReasonCD) {
            // self.$output.opAppStandardReasonCD = _.find(self.dropdownList, (o: ReasonTypeItemDto) => {
            //                                         return o.appStandardReasonCD == self.$input.opAppStandardReasonCD;
            //                                     }).appStandardReasonCD;
            self.opAppStandardReasonCD = _.find(self.dropdownList, (o: ReasonTypeItemDto) => {
                                                    return o.appStandardReasonCD == self.$input.opAppStandardReasonCD;
                                                }).appStandardReasonCD;
        } else {
            let defaultReasonCD = _.find(self.dropdownList, (o: ReasonTypeItemDto) => o.defaultValue);
            if (defaultReasonCD) {
                // self.$output.opAppStandardReasonCD = defaultReasonCD.appStandardReasonCD;
                self.opAppStandardReasonCD = defaultReasonCD.appStandardReasonCD;  
            } else {
                // self.$output.opAppStandardReasonCD = _.head(self.dropdownList).appStandardReasonCD;
                self.opAppStandardReasonCD = _.head(self.dropdownList).appStandardReasonCD;
            }
        }
        if (self.$input.opAppReason) {
            // self.$output.opAppReason = self.$input.opAppReason;
            self.opAppReason = self.$input.opAppReason;
        }

        if (self.displayFixedReason) {
            if (self.$input.appLimitSetting.standardReasonRequired) {
                // self.$updateValidator('params.output.opAppStandardReasonCD', { required: true });
                self.$updateValidator('opAppStandardReasonCD', { required: true });    
            } else {
                // self.$updateValidator('params.output.opAppStandardReasonCD', { required: false });
                self.$updateValidator('opAppStandardReasonCD', { required: false });
            }
            // self.$updateValidator('params.output.opAppStandardReasonCD', { validate: true });
            self.$updateValidator('opAppStandardReasonCD', { validate: true });
        } else {
            // self.$updateValidator('params.output.opAppStandardReasonCD', { validate: false });
            self.$updateValidator('opAppStandardReasonCD', { validate: false });
        }
        if (self.displayAppReason) {
            if (self.$input.appLimitSetting.requiredAppReason) {
                // self.$updateValidator('params.output.opAppReason', { required: true });
                self.$updateValidator('opAppReason', { required: true });
            } else {
                // self.$updateValidator('params.output.opAppReason', { required: false });
                self.$updateValidator('opAppReason', { required: false });
            }
            // self.$updateValidator('params.output.opAppReason', { validate: true });
            self.$updateValidator('opAppReason', { validate: true });
        } else {
            // self.$updateValidator('params.output.opAppReason', { validate: false });
            self.$updateValidator('opAppReason', { validate: false });
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

    get standardReasonRequired() {
        const self = this;

        return self.$input.appLimitSetting.standardReasonRequired;
    }

    get requiredAppReason() {
        const self = this;

        return self.$input.appLimitSetting.requiredAppReason;
    }

    @Watch('opAppStandardReasonCD')
    public opAppStandardReasonCDWatcher(value) {
        const self = this;
        self.$output.opAppStandardReasonCD = value;
        self.$emit('kaf000CChangeReasonCD', value);
    }

    @Watch('opAppReason')
    public opAppReasonWatcher(value) {
        const self = this;
        self.$output.opAppReason = value;
        self.$emit('kaf000CChangeAppReason', value);
    }

}

interface ReasonTypeItemDto {
    appStandardReasonCD: any;
    displayOrder: number;
    defaultValue: boolean;
    reasonForFixedForm: string;     
}

// KAFS00_C_起動情報
export interface KAFS00CParams {
    // 定型理由の表示
    displayFixedReason: number;
    // 申請理由の表示
    displayAppReason: number;
    // 定型理由一覧
    reasonTypeItemLst: Array<ReasonTypeItemDto>;
    // 申請制限設定
    appLimitSetting: any;
    // 選択中の定型理由
    opAppStandardReasonCD?: number;
    // 入力中の申請理由
    opAppReason?: string;
}