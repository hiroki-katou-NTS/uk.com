import { moment, Vue, _ } from '@app/provider';
import { component, Prop } from '@app/core/component';
import { TotopComponent } from '@app/components/totop';
import { storage } from '@app/utils';
import { CmmS45CComponent } from '../c/index';

import { AppInfo} from '../common';
import { AppListExtractConditionDto } from '../common/index.d';

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
        //抽出条件を変更する
        this.prFilter.appType = Number(this.selectedValue);
        storage.local.setItem('CMMS45_AppListExtractCondition', this.prFilter);
        //データをフィルタする
        switch (this.selectedValue) {
            case '-1':
                return this.lstApp;
            case '0':
                return this.lstApp;
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
            self.prFilter.startDate = moment(self.dateRange.start).format('YYYY/MM/DD');
            self.prFilter.endDate = moment(self.dateRange.end).format('YYYY/MM/DD');
            self.prFilter.appType = Number(self.selectedValue);
        } else if (getCache && storage.local.hasItem('CMMS45_AppListExtractCondition')) {
            self.prFilter = storage.local.getItem('CMMS45_AppListExtractCondition') as AppListExtractConditionDto;
        } else {
            self.prFilter = {
                startDate: self.dateRange.start == null ? '' : moment(self.dateRange.start).format('YYYY/MM/DD'),
                endDate: self.dateRange.end == null ? '' : moment(self.dateRange.end).format('YYYY/MM/DD'),
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
            self.dateRange = { start: new Date(data.startDate), end: new Date(data.endDate) };
            self.isDisPreP = data.isDisPreP;
        }).catch(() => {
            self.$mask('hide');
        });
    }

    // convert data appInfo
    private convertAppInfo(data: any) {
        let self = this;
        self.lstApp = [];
        data.lstApp.forEach((app: any) => {
            self.lstApp.push(new AppInfo({
                id: app.applicationID,
                appDate: new Date(app.applicationDate),
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
        self.$modal('cmms45c', { 'listAppMeta': lstAppId, 'currentApp': id }).then((res: any) => {
            if (res.CMMS45A_Reload) {
                self.getData(true, false);
            }
            console.log(res);
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
        self.selectedValue = self.prFilter.appType.toString();
    }

    // create appContent
    private appContent(appName: string, prePostName: string) {
        return this.isDisPreP == 1 ? appName + ' ' + this.$i18n('CMMS45_24', prePostName) : appName;
    }
}
const servicePath = {
    getApplicationList: 'at/request/application/applist/getapplist'
};

