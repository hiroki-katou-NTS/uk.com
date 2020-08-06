import { Vue, _, moment } from '@app/provider';
import { component, Prop, Watch } from '@app/core/component';

@component({
    name: 'kafs00a',
    style: require('./style.scss'),
    template: require('./index.vue'),
    resource: require('./resources.json'),
    validations: {},
    constraints: []
})
export class KafS00AComponent extends Vue {
    @Prop({ default: () => ({}) })
    public params: { 
        companyID: string, 
        employeeID: string,
        employmentCD: string,
        applicationUseSetting: any,
        receptionRestrictionSetting: any,
        opOvertimeAppAtr?: any,
    };

    public appMsg: string = '';
    public appMsgForCurrentMonth: string = '';
    public preAppPeriod: string = '';
    public postAppPeriod: string = '';

    public displayAppMsg: boolean = false;
    public displayAppMsgForCurrentMonth: boolean = false;
    public displayAppPeriod: boolean = false;
    public displayPreAppPeriod: boolean = false;
    public displayPostAppPeriod: boolean = false;

    public created() {
        const self = this;
        self.$mask('show');
        self.$http.post('at', API.getRequestMsg, {  
            companyID: self.params.companyID,
            employeeID: self.params.employeeID,
            employmentCD: self.params.employmentCD,
            applicationUseSetting: self.params.applicationUseSetting,
            receptionRestrictionSetting: self.params.receptionRestrictionSetting,
            opOvertimeAppAtr: self.params.opOvertimeAppAtr
        }).then((data: any) => {
            self.appMsg = data.data.applicationUseSetting.memo;
            self.appMsgForCurrentMonth = data.data.deadlineLimitCurrentMonth.opAppDeadline;
            if (data.data.preAppAcceptLimit.opAvailableTime) {
                self.preAppPeriod = self.$i18n('KAFS00_22', data.data.preAppAcceptLimit.opAvailableTime);    
            } else {
                self.preAppPeriod = self.$i18n('KAFS00_5', data.data.preAppAcceptLimit.opAcceptableDate);
            }
            self.postAppPeriod = data.data.postAppAcceptLimit.opAcceptableDate;   
            
            self.displayAppMsg = data.data.applicationUseSetting.useDivision && data.data.applicationUseSetting.memo;
            self.displayAppMsgForCurrentMonth = data.data.deadlineLimitCurrentMonth.useAtr;
            self.displayPreAppPeriod = data.data.preAppAcceptLimit.useReceptionRestriction;
            self.displayPostAppPeriod = data.data.postAppAcceptLimit.useReceptionRestriction;
            self.displayAppPeriod = self.displayPreAppPeriod || self.displayPostAppPeriod;
            self.$mask('hide');
        }).catch((res: any) => {
            self.$mask('hide');
            self.$modal.error(res.messageId);
        });
    }

}

const API = {
    getRequestMsg: 'at/request/app/smartphone/requestmsg'
};