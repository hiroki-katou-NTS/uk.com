import { Vue } from '@app/provider';
import { component, Prop } from '@app/core/component';
import { CmmS45FComponent } from '../f/index';

@component({
    name: 'cmms45e',
    route: '/cmm/s45/e',
    style: require('./style.scss'),
    template: require('./index.vue'),
    resource: require('./resources.json'),
    validations: {
        reasonReturn: {
            required: true
        }
    },
    constraints: [],
    components: {
        'cmms45f': CmmS45FComponent
    }
})
export class CmmS45EComponent extends Vue {
    public title: string = 'CmmS45E';

    @Prop({ default: () => ({ listAppMeta: [], currentApp: '', version: 0}) })
    public readonly params: { listAppMeta: Array<string>, currentApp: string, version: number };
    public reasonRemand: string = '';
    public selectedValue: string = '1';
    public apprList: Array<{id: string, content: string}> = [{id: '1', content: '申請者　カカシ１'}];
    
    public created() {
        let self = this;
        self.$http.post('at', servicePath.getAppInfoByAppID, [self.params.currentApp]).then((data: any) => {
            if (data) {
                let lstAppr = [];
                let applicant = data.applicant;
                lstAppr.push({
                    sId: applicant.pid,
                    atr: 0,
                    name: applicant.pname,
                    phaseOrder: null
                });
                let approvalFrame = data.approvalFrameDtoForRemand;
                approvalFrame.forEach((approvalState) => {
                    approvalState.listApprover.forEach(function (approver) {
                    //TH approver
                        lstAppr.push({
                            sId: approver.approverID,
                            atr: 1,
                            name: approver.approverName,
                            phaseOrder: approvalState.phaseOrder
                        });
                        //check TH agent
                        if (approver.representerID != '') {
                            lstAppr.push({
                                sId: approver.representerID,
                                atr: 2,
                                name: approver.representerName,
                                phaseOrder: approvalState.phaseOrder
                            });
                        }
                    });
                });
                // self.apprList = self.creatContent(lstAppr);
            }
        });
    }
    private callBack() {
        //goto D
        this.$close();
    }

    private remand() {
        let self = this;
        self.$validate;
        if (self.$valid) {
            // 確認メッセージ（Msg_384）を表示する
            self.$modal.confirm('Msg_384').then((res) => {
                if (res == 'yes') {
                    let remandParam = {
                        appID: self.params.currentApp,
                        applicaintName: 'Ｄ＿社員０１',
                        order: null,
                        remandReason: self.reasonRemand,
                        version: self.params.version
                    };
                    // アルゴリズム「差戻処理」を実行する
                    self.$http.post('at', servicePath.remand, remandParam).then((res) => {
                        console.log('remand');
                        // 「F：処理完了」画面に遷移する
                        this.$modal('cmms45f', { action: 3 }).then((result: any) => {
                            self.$close(result.backToMenu);
                        });
                    }).catch((res) => {
                        self.$modal.error(res.messageId);
                    });
                }
            });
        }
    }

    private creatContent(lstAppr: Array<IApproverInfo>) {
        let lstResult = [];
        lstAppr.forEach((appr) => {
            let contentApp = '';
            if (appr.atr == 0) {//申請者
                contentApp = this.$i18n('CMMS45_72') + appr.name;
            }
            if (appr.atr == 1) {//承認者
                contentApp = this.$i18n('CMMS45_73', appr.phaseOrder.toString()) + appr.name;
            }
            if (appr.atr == 2) {//代行者
                contentApp = this.$i18n('CMMS45_73', appr.phaseOrder.toString()) + appr.name + this.$i18n('CMMS45_74');
            }
            lstResult.push({ id: appr.sId, content: contentApp});
        });

        return lstResult;
    }
}

const servicePath = {
    getAppInfoByAppID: 'at/request/application/getAppInfoForRemandByAppId',
    remand: 'at/request/application/remandapp'
};

interface IApproverInfo {
    sId: string;//社員Id
    atr: number;//
    name: string;//
    phaseOrder: number;//
}

// class ApproverInfo {
//     public sId: string;
//     public atr: number;
//     public name: string;
//     public phaseOrder: number;

//     constructor(param: IApproverInfo) {
//         this.sId = param.sId;
//         this.atr = param.atr;
//         this.name = param.name;
//         this.phaseOrder = param.phaseOrder;
//     }

//     get content() {
//         let content = '';
//         if (this.atr == 0) {
//             content = 
//         }

//         return content;
//     }
// }