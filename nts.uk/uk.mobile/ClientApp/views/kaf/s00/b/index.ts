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
    public params: { 
        input: {
            mode: ScreenMode;
            appDisplaySetting: any;
            newModeContent?: NewModeContent;
            detailModeContent?: DetailModeContent;
        },
        output: {
            prePostAtr: number;
            startDate: Date;
            endDate: Date;
        }
    };
    public datasource: Array<Object> = [];
    public datasource2: Array<Object> = [];
    public dateRange: any = {};
    public displayPrePost: boolean = false;
    public enablePrePost: boolean = false;
    public displayMultiDaySwitch: boolean = false;
    public valueMultiDaySwitch: boolean = false;

    public created() {
        const self = this;
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
        self.dateRange = {
            start: self.params.output.startDate,
            end: self.params.output.endDate
        };
        self.displayPrePost = self.params.input.appDisplaySetting.prePostDisplayAtr == 0 ? false : true;
        self.enablePrePost = self.params.input.newModeContent.appTypeSetting.canClassificationChange;
        if (self.params.input.newModeContent.appTypeSetting.displayInitialSegment != 2) {
            self.params.output.prePostAtr = self.params.input.newModeContent.appTypeSetting.displayInitialSegment;
        }
        self.displayMultiDaySwitch = self.params.input.newModeContent.useMultiDaySwitch;
        self.valueMultiDaySwitch = self.params.input.newModeContent.initSelectMultiDay;
    }

    get dateText() {
        const self = this;
        if (self.params.input.detailModeContent.startDate == self.params.input.detailModeContent.endDate) {    
            return self.params.input.detailModeContent.startDate;
        } else {
            return self.params.input.detailModeContent.startDate + '~' + self.params.input.detailModeContent.endDate;
        }
    }

    get ScreenMode() {
        return ScreenMode;
    }
}

enum ScreenMode {
    NEW = 0,
    DETAIL = 1
}

interface NewModeContent {
    appTypeSetting: any;
    useMultiDaySwitch: boolean;
    initSelectMultiDay: boolean;
    appDate?: Date;
    dateRange?: any;
}

interface DetailModeContent {
    prePostAtrName: string;
    employeeName: string;
    startDate: string;
    endDate: string;       
}