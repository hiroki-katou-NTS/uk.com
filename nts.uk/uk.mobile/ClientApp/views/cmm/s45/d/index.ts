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
    public readonly params: { listAppMeta: Array<string>, currentApp: string, backToMenu?: boolean };
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
        let self = this;
        self.listAppMeta = self.params.listAppMeta;
        self.currentApp = self.params.currentApp;
        self.appCount = _.indexOf(self.listAppMeta, self.currentApp);
        Object.defineProperty(self.appState, 'getName', {
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
        
        Object.defineProperty(self.appState, 'getClass', {
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

        Object.defineProperty(self.appState, 'getNote', {
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
        let self = this;
        self.$mask('show');
        self.initData();
    }

    // lấy dữ liệu ban đầu
    public initData() {
        let self = this;
        self.selected = 0;
        self.$http.post('at', API.getDetailMob, self.currentApp)
        .then((resApp: any) => {
            let appData: IApplication = resApp.data;
            self.createPhaseLst(appData.listApprovalPhaseStateDto);
            self.appState.appStatus = appData.appStatus;
            self.appState.reflectStatus = appData.reflectStatus;
            self.appState.version = appData.version;
            self.appState.authorizableFlags = appData.authorizableFlags;
            self.appState.approvalATR = appData.approvalATR;
            self.appState.alternateExpiration = appData.alternateExpiration; 
            self.reversionReason = appData.reversionReason;
            self.appOvertime = appData.appOvertime;
            self.$mask('hide');
        }).catch((res: any) => {
            self.$modal.error(res.messageId)
                .then(() => {
                    self.back();
                });
        });
    }

    // tạo dữ liệu người phê duyệt
    public createPhaseLst(listPhase: Array<IApprovalPhase>): void {
        let self = this;
        let phaseLstConvert: Array<IApprovalPhase> = [];
        for (let i: number = 1; i <= 5; i++) {
            let containPhase: IApprovalPhase = _.find(listPhase, (phase: IApprovalPhase) => phase.phaseOrder == i);
            phaseLstConvert.push(new Phase(containPhase));
        }
        self.phaseLst = phaseLstConvert;
    }

    // tiến tới đơn tiếp theo
    public toNextApp(): void {
        let self = this;
        self.showApproval = false;
        self.appCount++;
        self.currentApp = self.listAppMeta[self.appCount];
        self.$mask('show');
        self.initData();
    }

    // quay về đơn trước
    public toPreviousApp(): void {
        let self = this;
        self.showApproval = false;
        self.appCount--;
        self.currentApp = self.listAppMeta[self.appCount];
        self.$mask('show');
        self.initData();
    }

    // kiểm tra có phải đơn đầu tiên không
    public isFirstApp(): boolean {
        let self = this;

        return self.appCount == 0;
    }

    // kiểm tra có phải đơn cuối cùng không
    public isLastApp(): boolean {
        let self = this;

        return self.appCount == self.listAppMeta.length - 1;
    }

    // kiểm tra list đơn xin rỗng
    public isEmptyApp(): boolean {
        let self = this;

        return self.listAppMeta.length == 0;
    }

    // ẩn hiện người phê duyệt
    public reverseApproval(): void {
        let self = this;
        self.showApproval = !self.showApproval;
    }

    // quay về màn CMMS45B
    public back() {
        let self = this;
        self.$close();
    }

    // kích hoạt nút giải phóng
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
                        if (resRelease.data.processDone) {
                            self.reflectApp(resRelease.data.reflectAppId);
                            self.$modal.info('Msg_221').then(() => { self.initData(); });
                        }
                    }).catch((failRelease: any) => {
                        self.$modal.error(failRelease.messageId)
                            .then(() => {
                                self.back();
                            });
                    });              
                }
            });
    }

    // kích hoạt nút chấp nhận
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
                        if (resApprove.data.processDone) {
                            self.reflectApp(resApprove.data.reflectAppId);
                            self.$modal.info('Msg_220').then(() => {
                                self.$modal('cmms45f', { 'action': 1, 'listAppMeta': self.listAppMeta, 'nextApp': self.getNextApp() })
                                .then((resAfterApprove: any) => {
                                    if (resAfterApprove.backToMenu) {
                                        self.$close();
                                    } else {
                                        self.toNextApp();
                                    }        
                                }); 
                            });
                        }
                    }).catch((failApprove: any) => {
                        self.$modal.error(failApprove.messageId)
                            .then(() => {
                                self.back();
                            });
                    });            
                }
            });
    }

    // kích hoạt nút từ chối
    public denyApp(): void {
        let self = this;
        self.$modal.confirm('Msg_1550')
            .then((v) => {
                if (v == 'yes') {
                    self.$http.post('at', API.deny, {
                        memo: self.memo,
                        applicationDto: {
                            version: self.appState.version,
                            applicationID: self.currentApp,
                            prePostAtr: self.appOvertime.prePostAtr,
                            applicantSID: self.appOvertime.applicant
                        }   
                    }).then((resDeny: any) => {
                        if (resDeny.data.processDone) {
                            self.reflectApp(resDeny.data.reflectAppId);
                            self.$modal.info('Msg_222').then(() => {
                                self.$modal('cmms45f', { 'action': 2, 'listAppMeta': self.listAppMeta, 'nextApp': self.getNextApp() })
                                .then((resAfterDeny: any) => {
                                    if (resAfterDeny.backToMenu) {
                                        self.$close();
                                    } else {
                                        self.toNextApp();
                                    }      
                                });
                            });
                        }
                    }).catch((failDeny: any) => {
                        self.$modal.error(failDeny.messageId)
                            .then(() => {
                                self.back();
                            });
                    });           
                }
            });
    }

    // kích hoạt nút trả về
    public returnApp(): void {
        let self = this;
        self.$modal('cmms45e', {'listAppMeta': self.listAppMeta, 'currentApp': self.currentApp, 'version': self.appState.version });
    }

    // phản ánh đơn xin sau khi chấp nhận, từ chối
    public reflectApp(appID: string): void {
        let self = this;
        if (!appID) {
            self.$http.post('at', API.reflectApp, [appID]);
        }
    }

    // lấy ID của đơn tiếp theo
    public getNextApp(): string {
        let self = this;
        
        return self.listAppMeta[self.appCount + 1];            
    }

    // kiểm tra có thể thay đổi không dựa vào trạng thái đơn
    public canChangeStatus(): boolean {
        let self = this;
        switch (self.appState.reflectStatus) {
            case 0: return true; // 反映状態 = 未反映
            case 1: return true; // 反映状態 = 反映待ち
            case 2: return false; // 反映状態 = 反映済
            case 3: return false; // 反映状態 = 取消待ち
            case 4: return false; // 反映状態 = 取消済
            case 5: return true; // 反映状態 = 差し戻し
            case 6: return true; // 反映状態 = 否認
            case 99: return false; //
            default: break;  
        }
    }

    // kiểm tra trạng thái của người login đối với đơn xin
    public isModify(): boolean {
        let self = this;
        switch (self.appState.approvalATR) {
            case 0: return false; // ログイン者の承認区分 = 未承認
            case 1: return true; // ログイン者の承認区分 = 承認済
            case 2: return true; // ログイン者の承認区分 = 否認
            default: break;  
        }
    }

    // hiển thị lý do approve
    public displayApproveReasonContent(): boolean {
        let self = this;
        if (self.canChangeStatus() && self.appState.authorizableFlags && !self.appState.alternateExpiration) {
            return true;
        }

        return !self.isModify();
    }

    // hiển thị ô nhập lý do approve
    public displayApproveReasonInput(): boolean {
        let self = this;
        if (self.canChangeStatus() && self.appState.authorizableFlags && !self.appState.alternateExpiration) {
            return !self.isModify();
        }

        return false;
    }

    // hiển thị nút giải phóng
    public displayReleaseLock(): boolean {
        let self = this;
        if (self.canChangeStatus() && self.appState.authorizableFlags) {
            return self.isModify();
        }
        
        return false;
    }

    // hiển thị nút chấp nhận, từ chối, trả về
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