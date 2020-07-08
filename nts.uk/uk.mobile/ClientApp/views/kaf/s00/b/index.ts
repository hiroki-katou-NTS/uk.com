import { Vue, _, moment } from '@app/provider';
import { component, Prop, Watch } from '@app/core/component';

@component({
    name: 'kafs00b',
    style: require('./style.scss'),
    template: require('./index.vue'),
    resource: require('./resources.json'),
    validations: {},
    constraints: []
})
export class KafS00BComponent extends Vue {
    @Prop({ default: () => ({}) })
    public params: { application: any, appDispInfoStartupOutput: any };

    public datasource: Array<Object> = [];
    public datasource2: Array<Object> = [];

    public created() {
        let self = this;
        self.datasource = [{
            code: 0,
            text: 'KAFS00_10'
        }, {
            code: 1,
            text: 'KAFS00_11'
        }];
        self.datasource2 = [{
            code: false,
            text: 'KAFS00_12'
        }, {
            code: true,
            text: 'KAFS00_13'
        }];
    }

    get $application() {
        return this.params.application;
    }

    get $appDispInfoStartupOutput() {
        return this.params.appDispInfoStartupOutput;
    }

    get prePostName() {
        let self = this;

        return _.find(self.datasource, (o: any) => o.code == self.$application.prePostAtr).text;
    }

    get displayPrePost() {
        let self = this;
        if (self.$appDispInfoStartupOutput.appDispInfoNoDateOutput) {
            return self.$appDispInfoStartupOutput.appDispInfoNoDateOutput.requestSetting.applicationSetting.appDisplaySetting.prePostAtrDisp == 1;
        }

        return true;
    }

    get enablePrePost() {
        let self = this;
        if (self.$appDispInfoStartupOutput.appDispInfoNoDateOutput) { 
            let listAppTypeSetting = self.$appDispInfoStartupOutput.appDispInfoNoDateOutput.requestSetting.applicationSetting.listAppTypeSetting;
            let appTypeSetting = _.find(listAppTypeSetting, (item: any) => item.appType == self.$application.appType);    
            
            return appTypeSetting.canClassificationChange;
        }

        return true;
    }

    get dateText() {
        let self = this;
        if (self.$application.isRangeDate) {    
            return '';
        } else {
            return moment(self.$application.appDate).format('yyyy/MM/dd');
        }
    }

    @Watch('$application.appDate')
    public dateWatcher(value) {
        let self = this;
        if (value.getDate() % 2 == 0) {
            self.$application.prePostAtr = 0;
        } else {
            self.$application.prePostAtr = 1;
        }
    }

    @Watch('$application.dateRange')
    public dateRangeWatcher(value) {
        let self = this;
        // if (value.getDate() % 2 == 0) {
        //     self.$application.prePostAtr = 0;
        // } else {
        //     self.$application.prePostAtr = 1;
        // }
    }

    @Watch('$appDispInfoStartupOutput')
    public appDispInfoStartupWatcher(value) {
        let self = this;
        if (value.appDispInfoWithDateOutput) {
            self.$application.prePostAtr = value.appDispInfoWithDateOutput.prePostAtr;
        }
    }
}

export interface AppDispInfoStartupDto {
    appDispInfoNoDateOutput: any;
    appDispInfoWithDateOutput: any;
    appDetailScreenInfo: any;
}