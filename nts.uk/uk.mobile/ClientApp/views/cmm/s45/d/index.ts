import { Vue, _ } from '@app/provider';
import { component, Prop } from '@app/core/component';
import { ApprovedComponent } from '@app/components';
import { IApprovalPhase, IApprovalFrame, IApplication } from 'views/cmm/s45/common/index.d';
import { Phase } from 'views/cmm/s45/common/index';
import { IOvertime } from 'views/cmm/s45/components/app1';
import { CmmS45EComponent } from 'views/cmm/s45/e';
import { CmmS45FComponent } from 'views/cmm/s45/f';

import {
    CmmS45ComponentsApp1Component,
    CmmS45ComponentsApp2Component,
    CmmS45ComponentsApp3Component,
    CmmS45ComponentsApp4Component,
    CmmS45ComponentsApp5Component
} from 'views/cmm/s45/components';

@component({
    name: 'cmms45d',
    route: '/cmm/s45/d',
    style: require('./style.scss'),
    template: require('./index.vue'),
    resource: require('./resources.json'),
    validations: {
        memo: {
            constraint: 'ApproverReason'
        }
    },
    constraints: ['nts.uk.ctx.at.request.dom.application.ApproverReason'],
    components: {
        // khai báo virtual tag name
        'approved': ApprovedComponent,
        'app1': CmmS45ComponentsApp1Component,
        'app2': CmmS45ComponentsApp2Component,
        'app3': CmmS45ComponentsApp3Component,
        'app4': CmmS45ComponentsApp4Component,
        'app5': CmmS45ComponentsApp5Component,
        'cmms45e': CmmS45EComponent,
        'cmms45f': CmmS45FComponent
    }
})
export class CmmS45DComponent extends Vue {
    @Prop({ default: () => ({ listAppMeta: [], currentApp: '' }) })
    public readonly params: { listAppMeta: Array<string>, currentApp: string };
    public title: string = 'CmmS45D';
    public showApproval: boolean = false;
    public appCount: number = 0;
    public selected: number = 0;
    public listAppMeta: Array<string> = [];
    public currentApp: string = '';
    // 承認ルートインスタンス
    public phaseLst: Array<IApprovalPhase> = [];
    public appState: { 
        appStatus: number, 
        reflectStatus: number, 
        version: number,
        authorizableFlags: boolean,
        approvalATR: number,
        alternateExpiration: boolean 
    } = { appStatus: 0, reflectStatus: 1, version: 0, authorizableFlags: false, approvalATR: 0, alternateExpiration: false };
    public appOvertime: IOvertime = null;
    // 差し戻し理由
    public reversionReason: string = '';

    public memo: string = '';

    public created() {
        this.listAppMeta = this.params.listAppMeta;
        this.currentApp = this.params.currentApp;
        this.appCount = _.indexOf(this.listAppMeta, this.currentApp);
        Object.defineProperty(this.appState, 'getName', {
            get() {
                switch (this.appStatus) {
                    case 0: return 'CMMS45_7'; // 反映状態 = 未反映
                    case 1: return 'CMMS45_8'; // 反映状態 = 反映待ち
                    case 2: return 'CMMS45_9'; // 反映状態 = 反映済
                    case 3: return 'CMMS45_10'; // 反映状態 = 取消待ち
                    case 4: return 'CMMS45_10'; // 反映状態 = 取消済
                    case 5: return 'CMMS45_36'; // 反映状態 = 差し戻し
                    case 6: return 'CMMS45_11'; // 反映状態 = 否認
                    default: break;
                }
            }
        });
        
        Object.defineProperty(this.appState, 'getClass', {
            get() {
                switch (this.appStatus) {
                    case 0: return 'apply-unapproved'; // 反映状態 = 未反映
                    case 1: return 'apply-approved'; // 反映状態 = 反映待ち
                    case 2: return 'apply-reflected'; // 反映状態 = 反映済
                    case 3: return 'apply-cancel'; // 反映状態 = 取消待ち
                    case 4: return 'apply-cancel'; // 反映状態 = 取消済
                    case 5: return 'apply-return'; // 反映状態 = 差し戻し
                    case 6: return 'apply-denial'; // 反映状態 = 否認
                    default: break;
                }
            }
        });

        Object.defineProperty(this.appState, 'getNote', {
            get() {
                switch (this.appStatus) {
                    case 0: return 'CMMS45_39'; // 反映状態 = 未反映
                    case 1: return 'CMMS45_37'; // 反映状態 = 反映待ち
                    case 2: return 'CMMS45_38'; // 反映状態 = 反映済
                    case 3: return 'CMMS45_42'; // 反映状態 = 取消待ち
                    case 4: return 'CMMS45_42'; // 反映状態 = 取消済
                    case 5: return 'CMMS45_40'; // 反映状態 = 差し戻し
                    case 6: return 'CMMS45_41'; // 反映状態 = 否認
                    default: break;
                }
            }
        });
    }

    public mounted() {
        this.$mask('show');
        this.initData();
    }

    // get approver list data
    public initData() {
        this.selected = 0;
        this.$http.post('at', API.getDetailMob, this.currentApp)
        .then((resApp: any) => {
            let appData: IApplication = resApp.data;
            this.createPhaseLst(appData.listApprovalPhaseStateDto);
            this.appState.appStatus = appData.appStatus;
            this.appState.reflectStatus = appData.reflectStatus;
            this.appState.version = appData.version;
            this.appState.authorizableFlags = appData.authorizableFlags;
            this.appState.approvalATR = appData.approvalATR;
            this.appState.alternateExpiration = appData.alternateExpiration; 
            this.reversionReason = appData.reversionReason;
            this.appOvertime = appData.appOvertime;
            this.$mask('hide');
        }).catch((res: any) => {
            this.$modal.error(this.$i18n(res.messageId))
                .then(() => {
                    this.back(true);
                });
        });
    }

    // render data for approver list
    public createPhaseLst(listPhase: Array<IApprovalPhase>): void {
        let phaseLstConvert: Array<IApprovalPhase> = [];
        for (let i: number = 1; i <= 5; i++) {
            let containPhase: IApprovalPhase = _.find(listPhase, (phase: IApprovalPhase) => phase.phaseOrder == i);
            phaseLstConvert.push(new Phase(containPhase));
        }
        this.phaseLst = phaseLstConvert;
    }

    // go to next application
    public toNextApp(): void {
        this.showApproval = false;
        this.appCount++;
        this.currentApp = this.listAppMeta[this.appCount];
        this.$mask('show');
        this.initData();
    }

    // back to previous application
    public toPreviousApp(): void {
        this.showApproval = false;
        this.appCount--;
        this.currentApp = this.listAppMeta[this.appCount];
        this.$mask('show');
        this.initData();
    }

    // check first application
    public isFirstApp(): boolean {
        return this.appCount == 0;
    }

    // check last application
    public isLastApp(): boolean {
        return this.appCount == this.listAppMeta.length - 1;
    }

    // check list app empty
    public isEmptyApp(): boolean {
        return this.listAppMeta.length == 0;
    }

    // show/hide approver list
    public reverseApproval(): void {
        this.showApproval = !this.showApproval;
    }

    // back to CMMS45A
    public back(reloadValue?: boolean) {
        this.$close({ CMMS45A_Reload: false });
    }

    // active release app
    public releaseApp(): void {
        let self = this;
        self.$modal.confirm('Msg_248')
            .then((v) => {
                if (v == 'yes') {
                    self.$http.post('at', API.release, {
                        memo: self.memo,
                        applicationDto: {
                            version: self.appState.version,
                            applicationID: self.currentApp
                        }
                    }).then((resRelease: any) => {
                        if (resRelease.processDone) {
                            self.reflectApp(resRelease.reflectAppId);
                            self.$modal.info('Msg_221');
                        }
                    });            
                }
            });
    }

    // active approve app
    public approveApp(): void {
        let self = this;
        self.$modal.confirm('Msg_1549')
            .then((v) => {
                if (v == 'yes') {
                    self.$http.post('at', API.approve, {
                        applicationDto: {
                            version: self.appState.version,
                            applicationID: self.currentApp    
                        },	
                        memo: self.memo, 
                        comboBoxReason: '',
                        textAreaReason: '',
                        holidayAppType: 0,
                        user: 1,
                        reflectPerState: self.appState.reflectStatus,
                        mobileCall: true
                    }).then((resApprove: any) => {
                        if (resApprove.processDone) {
                            self.reflectApp(resApprove.reflectAppId);
                            self.$modal.info('Msg_220').then(() => {
                                self.$modal('cmms45f', { 'action': 1, 'listAppMeta': self.listAppMeta, 'nextApp': self.getNextApp() })
                                .then((resAfterApprove: any) => {
                                    self.currentApp = resAfterApprove.currentApp;
                                    self.listAppMeta = resAfterApprove.listAppMeta;
                                    self.initData();        
                                }); 
                            });
                        }
                    });            
                }
            });
    }

    // active deny app
    public denyApp(): void {
        let self = this;
        self.$modal.confirm('Msg_1550')
            .then((v) => {
                if (v == 'yes') {
                    self.$http.post('at', API.deny, {
                        version: self.appState.version,
                        appId: self.currentApp
                    }).then((resDeny: any) => {
                        if (resDeny.processDone) {
                            self.reflectApp(resDeny.reflectAppId);
                            self.$modal.info('Msg_222').then(() => {
                                self.$modal('cmms45f', { 'action': 2, 'listAppMeta': self.listAppMeta, 'nextApp': self.getNextApp() })
                                .then((resAfterDeny: any) => {
                                    self.currentApp = resAfterDeny.currentApp;
                                    self.listAppMeta = resAfterDeny.listAppMeta;
                                    self.initData();        
                                });
                            });
                        }
                    });            
                }
            });
    }

    // active return app
    public returnApp(): void {
        let self = this;
        self.$modal('cmms45e', {'listAppMeta': self.listAppMeta, 'currentApp': self.currentApp, 'version': self.appState.version });
    }

    // reflect app after approve/deny
    public reflectApp(appID: string): void {
        let self = this;
        if (!appID) {
            self.$http.post('at', API.reflectApp, [appID]);
        }
    }

    // get next appID
    public getNextApp(): string {
        return this.listAppMeta[this.appCount + 1];            
    }

    public canChangeStatus(): boolean {
        let self = this;
        switch (self.appState.reflectStatus) {
            case 0: return true; // 反映状態 = 未反映
            // case 1: return false; // 反映状態 = 反映待ち
            case 2: return false; // 反映状態 = 反映済
            case 3: return false; // 反映状態 = 取消待ち
            case 4: return false; // 反映状態 = 取消済
            case 5: return true; // 反映状態 = 差し戻し
            case 6: return true; // 反映状態 = 否認
            case 99: return false; //
            default: break;  
        }
    }

    public isModify(): boolean {
        let self = this;
        switch (self.appState.approvalATR) {
            case 0: return false; // ログイン者の承認区分 = 未承認
            case 1: return true; // ログイン者の承認区分 = 承認済
            case 2: return false; // ログイン者の承認区分 = 否認
            default: break;  
        }
    }



    public displayApproveReasonContent(): boolean {
        let self = this;
        if (self.canChangeStatus() && self.appState.authorizableFlags && !self.appState.alternateExpiration) {
            return true;
        }

        return !self.isModify();
    }

    public displayApproveReasonInput(): boolean {
        let self = this;
        if (self.canChangeStatus() && self.appState.authorizableFlags && !self.appState.alternateExpiration) {
            return !self.isModify();
        }

        return false;
    }

    public displayReleaseLock(): boolean {
        let self = this;
        if (self.canChangeStatus() && self.appState.authorizableFlags) {
            return self.isModify();
        }
        
        return false;
    }

    public displayReleaseOpen(): boolean {
        let self = this;
        if (self.canChangeStatus() && self.appState.authorizableFlags && !self.appState.alternateExpiration) {
            return !self.isModify();
        }

        return false;
    }
}

const API = {
    getDetailMob: 'at/request/application/getDetailMob',
    approve: 'at/request/application/approveapp',
    deny: 'at/request/application/denyapp',
    release: 'at/request/application/releaseapp',
    reflectApp: 'at/request/application/reflect-app'
};