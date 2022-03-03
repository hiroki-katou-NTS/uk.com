import { Vue, _ } from '@app/provider';
import { component, Prop, Watch } from '@app/core/component';
import {TimePoint} from '@app/utils/time';

@component({
    name: 'kafs05multi',
    template: require('./index.vue'),
    validations: {
        params: {
            valueHours: {
                required: false,
                timeRange: true,
                valueCheck: {
                    test(value: any) {
                        return (!value.start || (value.start >= -720 && value.start <= 4319))
                            && (!value.end || (value.end >= -720 && value.end <= 4319));
                    },
                    messageId: ['MsgB_45', TimePoint.toString(-720, 'h'), TimePoint.toString(4319, 'h')]
                },
                // requiredCheck: {
                //     test(value: any) {
                //         this.updateValidatorReason();
                //
                //         return ((!!value.start && !!value.end) || (!value.start && !value.end));
                //     },
                //     messageId: ['MsgB_30']
                // },
            },
            appReason: {
                constraint: 'AppReason'
            }
        }
    },
    constraints: [
        'nts.uk.ctx.at.request.dom.application.AppReason'  
    ]
})
export class KafS05MultiComponent extends Vue {
    @Prop({ default: {} })
    public readonly params: any;
    @Prop({ default: null })
    public appDispInfoStartupOutput: any;

    public dropdownList: Array<any> = [];

    public created() {
        const self = this;
        self.initFromParams();    
    }

    @Watch('params.fixedReasonCode')
    public paramsWatcher1() {
        const self = this;
        if (!self.params.fixedReasonCode) {
            let defaultReasonCD = _.find(self.dropdownList, (o: ReasonTypeItemDto) => o.defaultValue);
            if (defaultReasonCD) {
                self.params.fixedReasonCode = defaultReasonCD.appStandardReasonCD;
            } else {
                let headItem = _.head(self.dropdownList);
                self.params.fixedReasonCode = headItem ? headItem.appStandardReasonCD : '';
            }
        }
    }

    private updateValidatorReason() {
        const self = this;
        if (self.displayFixedReason) {
            if (self.standardReasonRequired && self.params.valueHours && (!!self.params.valueHours.start || !!self.params.valueHours.end)) {
                self.$updateValidator('params.fixedReasonCode', { required: true });
            } else {
                self.$updateValidator('params.fixedReasonCode', { required: false });
            }
            self.$updateValidator('params.fixedReasonCode', { validate: true });
        } else {
            self.$updateValidator('params.fixedReasonCode', { validate: false });
        }
        if (self.displayAppReason) {
            if (self.requiredAppReason && self.params.valueHours && (!!self.params.valueHours.start || !!self.params.valueHours.end)) {
                self.$updateValidator('params.appReason', { required: true });
            } else {
                self.$updateValidator('params.appReason', { required: false });
            }
            self.$updateValidator('params.appReason', { validate: true });
        } else {
            self.$updateValidator('params.appReason', { validate: false });
        }
    }

    @Watch('appDispInfoStartupOutput')
    private initFromParams() {
        const self = this;
        if (!self.appDispInfoStartupOutput) {
            return;
        }
        if (self.standardReasonRequired) {
            self.dropdownList = [];
        } else {
            self.dropdownList = [{
                appStandardReasonCD: '',
                displayOrder: 0,
                defaultValue: false,
                reasonForFixedForm: self.$i18n('KAFS00_23'),   
            }];
        }
        _.forEach(self.appDispInfoStartupOutput.appDispInfoNoDateOutput.reasonTypeItemLst, (value) => {
            self.dropdownList.push({
                appStandardReasonCD: value.appStandardReasonCD,
                displayOrder: value.displayOrder,
                defaultValue: value.defaultValue,
                reasonForFixedForm: value.appStandardReasonCD + ' ' + value.reasonForFixedForm,     
            });   
        });

        if (!self.params.fixedReasonCode) {
            let defaultReasonCD = _.find(self.dropdownList, (o: ReasonTypeItemDto) => o.defaultValue);
            if (defaultReasonCD) {
                self.params.fixedReasonCode = defaultReasonCD.appStandardReasonCD;
            } else {
                let headItem = _.head(self.dropdownList);
                self.params.fixedReasonCode = headItem ? headItem.appStandardReasonCD : '';
            }
        }

        self.updateValidatorReason();
    }

    get displayFixedReason() {
        const self = this;

        return self.appDispInfoStartupOutput && self.appDispInfoStartupOutput.appDispInfoNoDateOutput.displayStandardReason == 1;
    }

    get displayAppReason() {
        const self = this;

        return self.appDispInfoStartupOutput && self.appDispInfoStartupOutput.appDispInfoNoDateOutput.displayAppReason == 1;
    }

    get dispReason() {
        const self = this;

        return self.displayFixedReason || self.displayAppReason;
    }

    get standardReasonRequired() {
        const self = this;

        return self.appDispInfoStartupOutput && self.appDispInfoStartupOutput.appDispInfoNoDateOutput.applicationSetting.appLimitSetting.standardReasonRequired;
    }

    get requiredAppReason() {
        const self = this;

        return self.appDispInfoStartupOutput && self.appDispInfoStartupOutput.appDispInfoNoDateOutput.applicationSetting.appLimitSetting.requiredAppReason;
    }

    @Watch('timeRequired')
    public updateValidator(data: any) {
        const self = this;
        self.$updateValidator('params.valueHours', {
            required: !!data,
            timeRange: true
        });
        self.$validate();
        self.updateValidatorReason();
    }

    get timeRequired() {
        return this.params.valueHours && (!!this.params.valueHours.start || !!this.params.valueHours.end);
    }

}

interface ReasonTypeItemDto {
    appStandardReasonCD: any;
    displayOrder: number;
    defaultValue: boolean;
    reasonForFixedForm: string;     
}