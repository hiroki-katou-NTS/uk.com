import { Vue, _ } from '@app/provider';
import { component, Prop } from '@app/core/component';
import { TotopComponent } from '@app/components/totop';
import { storage } from '@app/utils';
import { CmmS45CComponent } from '../c/index';

import { AppInfo} from '../shr';
import { AppListExtractConditionDto } from '../shr/index.d';

@component({
    name: 'cmms45a',
    route: '/cmm/s45/a',
    style: require('./style.scss'),
    template: require('./index.vue'),
    resource: require('./resources.json'),
    validations: {
        selectedValue: {
            required: true
        },
        dateRange: {
            required: true,
            dateRange: true
        }
    },
    constraints: [],
    components: {
        'to-top': TotopComponent,
        'cmms45c': CmmS45CComponent
    }
})
export class CmmS45AComponent extends Vue {
    @Prop({ default: () => ({ CMMS45_FromMenu: true}) })
    public readonly params: { CMMS45_FromMenu: boolean};
    public prFilter: AppListExtractConditionDto = null;//抽出条件
    public dateRange: { start?: Date; end?: Date } = { start: null, end: null };//期間
    public selectedValue: string = '-1';//選択した申請種類
    public lstAppType: Array<{ code: string, appType: number; appName: string; }> = [];//申請種類リスト
    public lstApp: Array<AppInfo> = [];//申請一覧
    public isDisPreP: number = 0;//申請表示設定.事前事後区分
    public displayA512: number = 0;
    public appAllNumber: number = 0;

    public mounted() {
        this.pgName = 'cmm045a';
    }

    // 起動する
    public created() {
        this.getData(!this.params.CMMS45_FromMenu, false);
    }

    // 申請種類名称
    private appTypeName(appType: number) {
        return (_.find(this.lstAppType, (item) => item.appType === appType) || { appName: '' }).appName;
    }

    // 申請を絞り込む
    get filterByAppType() {
        let self = this;
        //抽出条件を変更する
        self.prFilter.appType = Number(this.selectedValue);
        storage.local.setItem('CMMS45_AppListExtractCondition', this.prFilter);
        //データをフィルタする
        switch (self.selectedValue) {
            case '-1':
                return self.displayA512 == 2 ? self.lstApp.slice(0, self.appAllNumber) : self.lstApp;
            case '0':
                return self.displayA512 == 2 ? self.lstApp.slice(0, self.appAllNumber) : self.lstApp;
            default:
                return [];
        }
    }

    //データを取る
    private getData(getCache: boolean, filter: boolean) {
        let self = this;
        self.$mask('show');
        // check: キャッシュを取るか？
        if (filter) {
            self.prFilter.startDate = self.$dt.date(self.dateRange.start, 'YYYY/MM/DD');
            self.prFilter.endDate = self.$dt.date(self.dateRange.end, 'YYYY/MM/DD');
            self.prFilter.appType = Number(self.selectedValue);
        } else if (getCache && storage.local.hasItem('CMMS45_AppListExtractCondition')) {
            self.prFilter = storage.local.getItem('CMMS45_AppListExtractCondition') as AppListExtractConditionDto;
            self.selectedValue = self.prFilter.appType.toString();
        } else {
            self.prFilter = {
                startDate: self.dateRange.start == null ? '' : self.$dt.date(self.dateRange.start, 'YYYY/MM/DD'),
                endDate: self.dateRange.end == null ? '' : self.$dt.date(self.dateRange.end, 'YYYY/MM/DD'),
                appListAtr: 0,
                appType: -1,
                unapprovalStatus: false,
                approvalStatus: false,
                denialStatus: false,
                agentApprovalStatus: false,
                remandStatus: false,
                cancelStatus: false,
                appDisplayAtr: 0,
                listEmployeeId: [],
                empRefineCondition: ''
            } as AppListExtractConditionDto;
        }

        let param = {
            condition: self.prFilter, //申請一覧抽出条件
            spr: false,//sprから呼ぶか？
            extractCondition: 0,
            device: 1,//デバイス：PC = 0 or スマートフォン = 1
            lstAppType: [0]//対象申請種類List
        };
        
        // サービスを呼ぶ
        self.$http.post('at', servicePath.getApplicationList, param).then((result: { data: any }) => {
            self.$mask('hide');
            let data = result.data;
            self.prFilter.startDate = data.startDate;
            self.prFilter.endDate = data.endDate;            
            // キャッシュを変更する
            storage.local.setItem('CMMS45_AppListExtractCondition', self.prFilter);

            self.createLstAppType(data.lstAppInfor);
            self.convertAppInfo(data);
            self.dateRange = { start: self.$dt.fromUTCString(data.startDate, 'YYYY/MM/DD'), end: self.$dt.fromUTCString(data.endDate, 'YYYY/MM/DD') };
            self.isDisPreP = data.isDisPreP;
        }).catch(() => {
            self.$mask('hide');
        });
    }

    // convert data appInfo
    private convertAppInfo(data: any) {
        let self = this;
        self.lstApp = [];
        self.appAllNumber = data.appAllNumber;
        if (data.lstApp.length == 0) {
            self.displayA512 = 1;
        } else if (data.lstApp.length > data.appAllNumber) {
            self.displayA512 = 2;
        } else {
            self.displayA512 = 0;
        }
        
        data.lstApp.forEach((app: any) => {
            self.lstApp.push(new AppInfo({
                id: app.applicationID,
                appDate: self.$dt.fromUTCString(app.applicationDate, 'YYYY/MM/DD'),
                appType: app.applicationType,
                appName: self.appTypeName(app.applicationType),
                prePostAtr: app.prePostAtr,
                reflectStatus: app.reflectStatus,
                appStatusNo: app.reflectPerState
            }));
        });
    }

    // 詳細を確認する
    private goToDetail(id: string) {
        let self = this;
        let lstAppId = [];
        self.lstApp.forEach((app: AppInfo) => {
            lstAppId.push(app.id);
        });
        // 「C：申請内容確認」画面へ遷移する
        self.$modal('cmms45c', { 'listAppMeta': lstAppId, 'currentApp': id }).then(() => {
                self.getData(true, false);
        });
    }

    // 抽出条件を変更する
    private filter() {
        this.$validate();
        if (this.$valid) {
            this.getData(false, true);
        }
    }

    // crrate List AppType
    private createLstAppType(lstAppInfor: Array<any>) {
        let self = this;
        self.lstAppType = [];
        lstAppInfor.forEach((appType) => {
            self.lstAppType.push({ code: appType.appType, appType: appType.appType, appName: appType.appName });
        });
        if (_.filter(self.lstAppType, (c) => c.appType == self.prFilter.appType).length > 0) {
            self.selectedValue = self.prFilter.appType.toString();
        } else {
            self.selectedValue = '-1';
        }
    }

    // create appContent
    private appContent(appName: string, prePostName: string) {
        return this.isDisPreP == 1 ? appName + ' ' + this.$i18n('CMMS45_24', prePostName) : appName;
    }
    public getHtmlAll() {
        return `<div>` + this.$i18n('CMMS45_90', this.appAllNumber.toString()).replace(/\n/g, '<br />') + `</div>`;
    }
    public getHtmlNone() {
        return `<div>` + this.$i18n('CMMS45_89').replace(/\n/g, '<br />') + `</div>`;
    }
}
const servicePath = {
    getApplicationList: 'at/request/application/applist/getapplist'
};

