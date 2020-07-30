import { Vue, _ } from '@app/provider';
import { component, Prop, Watch } from '@app/core/component';

import { AppInfo } from '../shr';
import { AppListExtractConditionDto } from '../shr/index.d';

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
    public disableB24: boolean = false;
    public displayB513: number = 0;
    public appAllNumber: number = 0;
    public appPerNumber: number = 0;

    @Watch('modeAppr')
    public checkChangeMode(mode: boolean) {
        if (!mode) {
            this.lstAppr = [];
        }
    }

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
                startDate: self.$dt.date(self.dateRange.start, 'YYYY/MM/DD'),
                endDate: self.$dt.date(self.dateRange.end, 'YYYY/MM/DD'),
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
            self.selectedValue = self.prFilter.appType.toString();
        } else {
            self.prFilter = {
                startDate: self.dateRange.start == null ? '' : self.$dt.date(self.dateRange.start, 'YYYY/MM/DD'),
                endDate: self.dateRange.end == null ? '' : self.$dt.date(self.dateRange.end, 'YYYY/MM/DD'),
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
            self.dateRange = { start: self.$dt.fromUTCString(data.startDate, 'YYYY/MM/DD'), end: self.$dt.fromUTCString(data.endDate, 'YYYY/MM/DD') };
            self.isDisPreP = data.isDisPreP;
            self.disableB24 = data.appStatusCount.unApprovalNumber == 0 ? true : false;
        }).catch(() => {
            self.$mask('hide');
        });
    }

    public getHtmlPer() {
        return `<div>` + this.$i18n('CMMS45_91', this.appPerNumber.toString()).replace(/\n/g, '<br />') + `</div>`;
    }
    public getHtmlAll() {
        return `<div>` + this.$i18n('CMMS45_90', this.appAllNumber.toString()).replace(/\n/g, '<br />') + `</div>`;
    }
    public getHtmlNone() {
        return `<div>` + this.$i18n('CMMS45_89').replace(/\n/g, '<br />') + `</div>`;
    }

    // convert Application info
    private convertAppInfo(data: any) {
        let self = this;
        self.lstAppByEmp = [];
        if (data.lstApp.length == 0) {
            self.displayB513 = 1;
        } else if (data.lstApp.length > data.appAllNumber) {
            self.displayB513 = 2;
        } else {
            self.displayB513 = 0;
        }
        self.appPerNumber = data.appPerNumber;
        self.appAllNumber = data.appAllNumber;
        data.lstSCD.forEach((sCD) => {
            let appInfor = self.getLstApp(data, sCD);
            self.lstAppByEmp.push(new AppByEmp({
                empCD: sCD,
                empName: appInfor.sName,
                lstApp: self.convertLstApp(appInfor.lstApp),
                displayB52: appInfor.lstApp.length > data.appPerNumber,
                appPerNumber: data.appPerNumber
            }));
        });
    }

    private getLstApp(data: any, sCD: string) {
        let self = this;
        let lstResult = [];
        let lstObj = _.filter(data.lstMasterInfo, (c) => c.empSD == sCD).map((x) => {
            return { id: x.appID, name: x.empName };
        });
        data.lstApp.forEach((app) => {
            if (self.contains(lstObj, app.applicationID)) {
                lstResult.push(app);
            }
        });

        return { sName: lstObj.length >= 0 ? lstObj[0].name : '', lstApp: lstResult };
    }

    private contains(lst: Array<any>, id: any) {
        return _.find(lst, (c) => c.id == id) != undefined ? true : false;
    }

    private convertLstApp(lstApp: Array<any>) {
        let lst = [];
        lstApp.forEach((app: any) => {
            lst.push(new AppInfo({
                id: app.applicationID,
                appDate: this.$dt.fromUTCString(app.applicationDate, 'YYYY/MM/DD'),
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
            name: this.modeAppr ? 'CMMS45_55' : 'CMMS45_54'
        };
    }
    // 詳細を確認する
    private goToDetail(item: AppInfo) {
        let self = this;
        if (!self.modeAppr) {
            let lstAppId = self.findLstIdDisplay();
            //「D：申請内容確認（承認）」画面へ遷移する
            this.$modal('cmms45d', { 'listAppMeta': lstAppId, 'currentApp': item.id }).then(() => {
                //reload
                self.getData(true, false);
            });
        } else {
            if (!item.frameStatus) {//TH đơn không được approve thì bỏ qua
                return;
            }
            let checkSel = _.filter(self.lstAppr, (idSel) => idSel == item.id).length;
            if (checkSel > 0) {//bo check
                self.lstAppr = _.remove(self.lstAppr, (select) => {
                    return select != item.id;
                });
            } else {//them chek
                self.lstAppr.push(item.id);
            }
        }
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
        let self = this;
        //抽出条件を変更する
        self.prFilter.appType = Number(self.selectedValue);
        storage.local.setItem('CMMS45_AppListExtractCondition', self.prFilter);
        let lstDisplay = [];
        let count = 0;
        if (self.displayB513 == 2) {//TH tổng vượt quá
            self.lstAppByEmp.forEach((emp) => {
                if (count >= self.appAllNumber) {
                    return;
                } 
                if (count + emp.lstAppDisplay.length < self.appAllNumber) {//TH công thêm không vượt quá tổng
                    lstDisplay.push(new AppByEmp({
                        empCD: emp.empCD,
                        empName: emp.empName,
                        lstApp: emp.lstAppDisplay,
                        displayB52: emp.displayB52,
                        appPerNumber: emp.appPerNumber
                    }));
                    count = count + emp.lstAppDisplay.length;
                } else {//TH cộng thêm sẽ bị vượt quá tổng
                    lstDisplay.push(new AppByEmp({
                        empCD: emp.empCD,
                        empName: emp.empName,
                        lstApp: emp.lstAppDisplay.slice(0, self.appAllNumber - count),
                        displayB52: emp.displayB52,
                        appPerNumber: emp.appPerNumber
                    }));
                    count = count + emp.lstAppDisplay.slice(0, self.appAllNumber - count).length;
                }
            });
        } else {//TH tổng không vượt quá
            self.lstAppByEmp.forEach((emp) => {
                let lstAppD = emp.displayB52 ? emp.lstAppDisplay : emp.lstApp;
                lstDisplay.push(new AppByEmp({
                    empCD: emp.empCD,
                    empName: emp.empName,
                    lstApp: lstAppD,
                    displayB52: emp.displayB52,
                    appPerNumber: emp.appPerNumber
                }));
            });
        }
        let checkCountAll = 0;
        _.each(self.lstAppByEmp, (emp) => {
            checkCountAll = checkCountAll + emp.lstAppDisplay.length;
        });
        if (checkCountAll > self.appAllNumber) {//TH hiển thị vượt quá tổng
            self.displayB513 = 2;
        } else {//TH hiển thị không vượt quá tổng
            self.displayB513 = self.displayB513 == 1 ? self.displayB513 : 0;
        }
        //データをフィルタする
        switch (self.selectedValue) {
            case '-1':
                return lstDisplay;
            case '0':
                return lstDisplay;
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
}
const servicePath = {
    getApplicationList: 'at/request/application/applist/getapplist',
    approvalListApp: 'at/request/application/applist/approval'
};
interface IAppByEmp {
    empCD: string;
    empName: string;
    lstApp: Array<AppInfo>;
    displayB52: boolean;
    appPerNumber: number;
}

class AppByEmp {
    public empCD: string;
    public empName: string;
    public lstApp: Array<AppInfo>;
    public displayB52: boolean;
    public appPerNumber: number;

    constructor(param: IAppByEmp) {
        this.empCD = param.empCD;
        this.empName = param.empName;
        this.lstApp = param.lstApp;
        this.displayB52 = param.displayB52;
        this.appPerNumber = param.appPerNumber;
    }

    get lstAppDisplay() {
        return this.displayB52 ? this.lstApp.slice(0, this.appPerNumber) : this.lstApp;
    }
}