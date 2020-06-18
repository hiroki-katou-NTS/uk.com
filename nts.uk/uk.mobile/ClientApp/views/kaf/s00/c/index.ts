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
    public params: { application: any, appDispInfoStartupOutput: any };

    get $application() {
        return this.params.application;
    }

    get $appDispInfoStartupOutput() {
        return this.params.appDispInfoStartupOutput;
    }

    get $dropdownList() {
        let self = this;
        if (self.$appDispInfoStartupOutput.appDispInfoNoDateOutput) {
            return self.$appDispInfoStartupOutput.appDispInfoNoDateOutput.appReasonLst;
        }

        return [];
    }

    get dispComboReason() {
        let self = this;
        if (self.$appDispInfoStartupOutput.appDispInfoNoDateOutput) { 
            let listAppTypeSetting = self.$appDispInfoStartupOutput.appDispInfoNoDateOutput.requestSetting.applicationSetting.listAppTypeSetting;
            let appTypeSetting = _.find(listAppTypeSetting, (item: any) => item.appType == self.$application.appType);    
            
            return appTypeSetting.displayFixedReason == 1;
        }

        return true;
    }

    get dispTextReason() {
        let self = this;
        if (self.$appDispInfoStartupOutput.appDispInfoNoDateOutput) { 
            let listAppTypeSetting = self.$appDispInfoStartupOutput.appDispInfoNoDateOutput.requestSetting.applicationSetting.listAppTypeSetting;
            let appTypeSetting = _.find(listAppTypeSetting, (item: any) => item.appType == self.$application.appType);    
            
            return appTypeSetting.displayAppReason == 1;
        }

        return true;
    }

    get dispReason() {
        let self = this;

        return self.dispComboReason || self.dispTextReason;
    }
}