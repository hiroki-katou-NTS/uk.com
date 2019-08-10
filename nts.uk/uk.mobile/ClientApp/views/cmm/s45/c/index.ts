import { Vue, _ } from '@app/provider';
import { component, Prop } from '@app/core/component';
import { ApprovedComponent } from '@app/components';
import { IApprovalPhase, IApprovalFrame, IApplication } from 'views/cmm/s45/common/index.d';
import { Phase } from 'views/cmm/s45/common/index';
import { IOvertime } from 'views/cmm/s45/components/app1';

import {
    CmmS45ComponentsApp1Component,
    CmmS45ComponentsApp2Component,
    CmmS45ComponentsApp3Component,
    CmmS45ComponentsApp4Component,
    CmmS45ComponentsApp5Component
} from 'views/cmm/s45/components';

@component({
    name: 'cmms45c',
    route: '/cmm/s45/c',
    style: require('./style.scss'),
    template: require('./index.vue'),
    resource: require('./resources.json'),
    validations: {},
    constraints: [],
    components: {
        // khai báo virtual tag name
        'approved': ApprovedComponent,
        'app1': CmmS45ComponentsApp1Component,
        'app2': CmmS45ComponentsApp2Component,
        'app3': CmmS45ComponentsApp3Component,
        'app4': CmmS45ComponentsApp4Component,
        'app5': CmmS45ComponentsApp5Component,
        'render': {
            template: `<div class="">{{params.id}} {{params.name}}</div>`,
            props: ['params']
        }
    }
})
export class CmmS45CComponent extends Vue {
    @Prop({ default: () => ({ listAppMeta: [], currentApp: '' }) })
    public readonly params: { listAppMeta: Array<string>, currentApp: string };
    public title: string = 'CmmS45C';
    public showApproval: boolean = false;
    public appCount: number = 0;
    public selected: number = 0;
    public listAppMeta: Array<string> = [];
    public currentApp: string = '';
    // 承認ルートインスタンス
    public phaseLst: Array<IApprovalPhase> = [];
    public appState: { appStatus: number, reflectStatus: number, version: number } = { appStatus: 0, reflectStatus: 1, version: 0 };
    public appOvertime: IOvertime = null;
    // 差し戻し理由
    public reversionReason: string = '';

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
        if (this.$router.currentRoute.name == 'cmms45a') {
            if (reloadValue) {
                this.$close({ CMMS45A_Reload: reloadValue });
            } else {
                this.$close({ CMMS45A_Reload: false });
            }
            
        } else {
            this.$goto('cmms45a', { CMMS45_FromMenu: false });   
        }
    }

    // delete APplication
    public deleteApp(): void {
        let self = this;
        self.$modal.confirm('Msg_18')
            .then((v) => {
                if (v == 'yes') {
                    self.$http.post('at', API.delete, {
                        version: self.appState.version,
                        appId: self.currentApp
                    }).then((resApproval: any) => {
                        return self.$modal.info('Msg_16');
                    }).then(() => {
                        this.back(true);
                    });               
                }
            });
    }

    // display delete button
    public get displayDeleteButton() {
        return this.appState.reflectStatus == 0 || this.appState.reflectStatus == 5;
    }

    // display update button
    public get displayUpdateButton() {
        return this.appState.reflectStatus == 0 || this.appState.reflectStatus == 5;
    }

    // display edit float button
    public get displayEditFloat() {
        return this.displayDeleteButton || this.displayUpdateButton;    
    }
    
    // update Application, go to detail screen
    public updateApp(): void {
        let self = this;
        this.$goto('kafS05a', { appID: self.currentApp }); 
    }
}

const API = {
    delete: 'at/request/application/deleteapp',
    getDetailMob: 'at/request/application/getDetailMob'
};