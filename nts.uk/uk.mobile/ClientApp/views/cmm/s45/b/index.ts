import { Vue, moment, _ } from '@app/provider';
import { component, Prop } from '@app/core/component';

import { AppInfo } from '../common';
import { AppListExtractConditionDto } from '../common/index.d';

import { TotopComponent } from '@app/components/totop';
import { storage } from '@app/utils';
import { CmmS45DComponent } from '../d/index';
import { CmmS45EComponent } from '../e/index';

@component({
    name: 'cmms45b',
    route: '/cmm/s45/b',
    style: require('./style.scss'),
    template: require('./index.vue'),
    resource: require('./resources.json'),
    validations: {
        dateRange: {
            required: true,
            dateRange: true
        },
        selectedValue: {
            required: true
        },
        checkeds: {
            self: {
                test(value: any[]) {
                    return !!value.length;
                },
                messageId: 'Msg_360'
            }
        }
    },
    constraints: [],
    components: {
        'to-top': TotopComponent,
        'cmms45d': CmmS45DComponent,
        'cmms45e': CmmS45EComponent
    }
})

export class CmmS45BComponent extends Vue {
    @Prop({ default: () => ({ CMMS45_FromMenu: true }) })
    public readonly params: { CMMS45_FromMenu: boolean };
    public dateRange: { start?: Date, end?: Date } = { start: null, end: null };//期間
    public checkeds: Array<number> = [1, 5];//未承認: 1, 承認済み: 2, 否認: 3, 代行承認済み: 4, 差戻: 5, 取消: 6
    public selectedValue: string = '-1';//選択した申請種類
    public lstAppType: Array<{ code: string, appType: number; appName: string; }> = [];
    public lstAppByEmp: Array<AppByEmp> = [];
    public modeAppr: boolean = false;
    public prFilter: AppListExtractConditionDto = null;//抽出条件
    public lstAppr: Array<string> = [];
    public lstMasterInfo: Array<any> = [];
    public isDisPreP: number = 0;//申請表示設定.事前事後区分

    public mounted() {
        this.pgName = 'cmms45b';
    }
    // click button 抽出実行
    private filterAppr() {
        let self = this;
        self.$validate();
        if (self.$valid) {
            self.getData(false, true);
        }
    }

    public created() {
        this.getData(!this.params.CMMS45_FromMenu, false);
    }
    // 未承認のチェック：する　or　しない
    private checkUnapprovalStatus() {
        return _.filter(this.checkeds, (c) => c == 1).length > 0 ? true : false;
    }
    // 承認のチェック：する　or　しない
    private checkApprovalStatus() {
        return _.filter(this.checkeds, (c) => c == 2).length > 0 ? true : false;
    }
    // 否認のチェック：する　or　しない
    private checkDenialStatus() {
        return _.filter(this.checkeds, (c) => c == 3).length > 0 ? true : false;
    }
    // 代行者が承認済のチェック：する　or　しない
    private checkAgentApprovalStatus() {
        return _.filter(this.checkeds, (c) => c == 4).length > 0 ? true : false;
    }
    // 差戻のチェック：する　or　しない
    private checkRemandStatus() {
        return _.filter(this.checkeds, (c) => c == 5).length > 0 ? true : false;
    }
    // 取消のチェック：する　or　しない
    private checkCancelStatus() {
        return _.filter(this.checkeds, (c) => c == 6).length > 0 ? true : false;
    }

    //データを取る
    private getData(getCache: boolean, filter: boolean) {
        let self = this;
        self.$mask('show');
        // check: キャッシュを取るか？
        if (filter) {
            self.prFilter = {
                startDate: moment(self.dateRange.start).format('YYYY/MM/DD'),
                endDate: moment(self.dateRange.end).format('YYYY/MM/DD'),
                appListAtr: 1,
                appType: Number(self.selectedValue),
                unapprovalStatus: self.checkUnapprovalStatus(),
                approvalStatus: self.checkApprovalStatus(),
                denialStatus: self.checkDenialStatus(),
                agentApprovalStatus: self.checkAgentApprovalStatus(),
                remandStatus: self.checkRemandStatus(),
                cancelStatus: self.checkCancelStatus(),
                appDisplayAtr: 0,
                listEmployeeId: [],
                empRefineCondition: ''
            } as AppListExtractConditionDto;
        } else if (getCache && storage.local.hasItem('CMMS45_AppListExtractCondition')) {
            self.prFilter = storage.local.getItem('CMMS45_AppListExtractCondition') as AppListExtractConditionDto;
        } else {
            self.prFilter = {
                startDate: self.dateRange.start == null ? '' : moment(self.dateRange.start).format('YYYY/MM/DD'),
                endDate: self.dateRange.end == null ? '' : moment(self.dateRange.end).format('YYYY/MM/DD'),
                appListAtr: 1,
                appType: -1,
                unapprovalStatus: true,
                approvalStatus: false,
                denialStatus: false,
                agentApprovalStatus: false,
                remandStatus: true,
                cancelStatus: false,
                appDisplayAtr: 0,
                listEmployeeId: [],
                empRefineCondition: ''
            } as AppListExtractConditionDto;
        }

        let param = {
            condition: self.prFilter,//申請一覧抽出条件
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
            self.lstMasterInfo = data.lstMasterInfo;
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
    // convert Application info
    private convertAppInfo(data: any) {
        let self = this;
        self.lstAppByEmp = [];
        data.lstSCD.forEach((sCD) => {
            let appInfor = self.getLstApp(data, sCD);
            self.lstAppByEmp.push(new AppByEmp({
                empCD: sCD,
                empName: appInfor.sName,
                lstApp: self.convertLstApp(appInfor.lstApp)
            }));
        });
    }

    private getLstApp(data: any, sCD: string) {
        let lstResult = [];
        let name = '';
        data.lstMasterInfo.forEach((app) => {
            if (app.empSD == sCD) {
                lstResult.push(_.find(data.lstApp, (c) => c.applicationID == app.appID));
                name = app.empName;
            }
        });

        return { sName: name, lstApp: lstResult };
    }

    private convertLstApp(lstApp: Array<any>) {
        let lst = [];
        lstApp.forEach((app: any) => {
            lst.push(new AppInfo({
                id: app.applicationID,
                appDate: new Date(app.applicationDate),
                appType: app.applicationType,
                appName: this.appTypeName(app.applicationType),
                prePostAtr: app.prePostAtr,
                reflectStatus: app.reflectStatus,
                appStatusNo: app.reflectPerState,
                frameStatus: this.getFrameStatus(app.applicationID),
                version: app.version
            }));
        });

        return lst;
    }

    private getFrameStatus(appID: string) {
        return (_.find(this.lstMasterInfo, (app) => app.appID == appID) || { statusFrameAtr: false }).statusFrameAtr;
    }

    private appTypeName(appType: number) {
        return (_.find(this.lstAppType, (item) => item.appType === appType) || { appName: '' }).appName;
    }

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

    //一括承認モードに切り替える
    //通常モードに切り替える
    get btnChangeMode() {
        return {
            class: this.modeAppr ? 'btn btn-secondary' : 'btn btn-primary',
            name: this.modeAppr ? 'CMMS45_54' : 'CMMS45_55'
        };
    }
    // 詳細を確認する
    private goToDetail(id: string) {
        let self = this;
        let lstAppId = self.findLstIdDisplay();
        //「D：申請内容確認（承認）」画面へ遷移する
        this.$modal('cmms45d', { 'listAppMeta': lstAppId, 'currentApp': id }).then(() => {
            self.getData(true, false);
        });
    }
    //一括承認する
    private processAppr() {
        let self = this;
        self.$mask('show');
        let lstAppID = [];
        if (self.modeAppr && self.lstAppr.length > 0) {
            lstAppID = self.lstAppr;
        } else {
            lstAppID = this.findLstIdDisplay();
        }
        let lstAppr = [];
        lstAppID.forEach((id) => {
            lstAppr.push({ appId: id, version: self.findVersion(id) });
        });
        self.$modal.confirm({ messageId: 'Msg_1551' }).then((value) => {
            if (value == 'yes') {
                self.$http.post('at', servicePath.approvalListApp, lstAppr).then((result) => {
                    self.$modal.info({ messageId: 'Msg_220' }).then(() => {
                        self.$mask('hide');
                        self.lstAppr = [];
                        self.modeAppr = false;
                        self.getData(false, true);
                    });
                }).catch(() => {
                    self.$mask('hide');
                });
            } else {
                self.$mask('hide');
            }
        });
    }

    private findVersion(appId: String) {
        let version = 0;
        this.lstAppByEmp.forEach((emp) => {
            emp.lstApp.forEach((app) => {
                if (app.id == appId) { version = app.version; }
            });
        });

        return version;
    }
    // 申請を絞り込む
    get filterByAppType() {
        //抽出条件を変更する
        this.prFilter.appType = Number(this.selectedValue);
        storage.local.setItem('CMMS45_AppListExtractCondition', this.prFilter);
        //データをフィルタする
        switch (this.selectedValue) {
            case '-1':
                return this.lstAppByEmp;
            case '0':
                return this.lstAppByEmp;
            default:
                return [];
        }
    }
    private findLstIdDisplay() {
        let lstId = [];
        this.filterByAppType.forEach((app) => {
            app.lstApp.forEach((item) => {
                lstId.push(item.id);
            });
        });

        return lstId;
    }
    // create appContent
    private appContent(appName: string, prePostName: string) {
        return this.isDisPreP == 1 ? appName + ' ' + this.$i18n('CMMS45_24', prePostName) : appName;
    }
    public callE() {
        this.$modal('cmms45e', { appId: '9a02b621-5f76-470b-85c9-b584bab9adda' });
    }
}
const servicePath = {
    getApplicationList: 'at/request/application/applist/getapplist',
    approvalListApp: 'at/request/application/applist/approval'
};
interface IAppByEmp {
    empCD: string;
    empName: string;
    lstApp: Array<AppInfo>;
}

class AppByEmp {
    public empCD: string;
    public empName: string;
    public lstApp: Array<AppInfo>;

    constructor(param: IAppByEmp) {
        this.empCD = param.empCD;
        this.empName = param.empName;
        this.lstApp = param.lstApp;
    }

}