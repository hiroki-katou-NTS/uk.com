import { Vue, _ } from '@app/provider';
import { component, Prop } from '@app/core/component';
import { ApprovedComponent } from '@app/components';
import { IApprovalPhase, AppDetailScreenInfo } from 'views/cmm/s45/shr/index.d';
import { Phase } from 'views/cmm/s45/shr/index';
import { AppType, AppTypeName } from 'views/kaf/s00/shr';

import {
    CmmS45ComponentsApp1Component,
    CmmS45ComponentsApp2Component,
    CmmS45ComponentsApp3Component,
    CmmS45ComponentsApp4Component,
    CmmS45ComponentsApp5Component
} from 'views/cmm/s45/shr/components';

@component({
    name: 'cmms45c',
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
    public readonly params: { listAppMeta: Array<string>, currentApp: string, action: number };
    public title: string = 'CmmS45C';
    public showApproval: boolean = false;
    public appCount: number = 0;
    public selected: number = 0;
    public listAppMeta: Array<string> = [];
    public currentApp: string = '';
    // 承認ルートインスタンス
    public phaseLst: Array<Phase> = [];
    public appState: { appStatus: number, reflectStatus: number, version: number } = { appStatus: 0, reflectStatus: 1, version: 0 };
    public appType: number = 99;
    public appTransferData: any = {
        appDispInfoStartupOutput: null,
        appDetail: null
    };
    // 差し戻し理由
    public reversionReason: string = '';
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
                    case 3: return 'CMMS45_10'; // 反映状態 = 取消済
                    case 4: return 'CMMS45_36'; // 反映状態 = 差し戻し
                    case 5: return 'CMMS45_11'; // 反映状態 = 否認
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
                    case 3: return 'apply-cancel'; // 反映状態 = 取消済
                    case 4: return 'apply-return'; // 反映状態 = 差し戻し
                    case 5: return 'apply-denial'; // 反映状態 = 否認
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
                    case 3: return 'CMMS45_42'; // 反映状態 = 取消済
                    case 4: return 'CMMS45_40'; // 反映状態 = 差し戻し
                    case 5: return 'CMMS45_41'; // 反映状態 = 否認
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
            .then((successData: any) => {
                self.appTransferData.appDispInfoStartupOutput = successData.data;
                let appDetailScreenInfoDto: AppDetailScreenInfo = successData.data.appDetailScreenInfo;
                self.createPhaseLst(appDetailScreenInfoDto.approvalLst);
                self.appState.appStatus = appDetailScreenInfoDto.reflectPlanState;
                self.appState.reflectStatus = appDetailScreenInfoDto.reflectPlanState;
                self.appState.version = appDetailScreenInfoDto.application.version;
                self.reversionReason = appDetailScreenInfoDto.application.opReversionReason;
                self.appType = appDetailScreenInfoDto.application.appType;
                self.$mask('hide');
            }).catch((res: any) => {
                self.$mask('hide');
                if (res.messageId == 'Msg_426') {
                    self.$modal.error('Msg_426').then(() => {
                        self.back();
                    });
                } else {
                    // self.$modal.error(res.message).then(() => {
                    //     self.back();
                    // }); 
                    let promise;
                    if (res.messageId) {
                        promise = self.$modal.error({ messageId: res.messageId });
                    } else {

                        if (_.isArray(res.errors)) {
                            promise = self.$modal.error({ messageId: res.errors[0].messageId });
                        } else {
                            promise = self.$modal.error({ messageId: res.errors.messageId });
                        }
                    }
                    promise.then(() => {
                        self.back();
                    });
                }
            });
    }

    // tạo dữ liệu người phê duyệt
    public createPhaseLst(listPhase: Array<IApprovalPhase>): void {
        let self = this;
        let phaseLstConvert: Array<Phase> = [];
        for (let i: number = 1; i <= 5; i++) {
            let containPhase: IApprovalPhase = _.find(listPhase, (phase: IApprovalPhase) => phase.phaseOrder == i);
            phaseLstConvert.push(new Phase(containPhase));
        }
        self.phaseLst = phaseLstConvert;
        self.selected = self.getSelectedPhase();
    }

    // lấy phase chỉ định 
    private getSelectedPhase(): number {
        let self = this;
        let denyPhase: Phase = _.find(self.phaseLst, (phase: Phase) => phase.approvalAtrValue == 2);
        if (denyPhase) {
            return denyPhase.phaseOrder - 1;
        }
        let returnPhase: Phase = _.find(self.phaseLst, (phase: Phase) => phase.approvalAtrValue == 3);
        if (returnPhase) {
            return returnPhase.phaseOrder - 1;
        }
        let unapprovePhaseLst: Array<Phase> = _.filter(self.phaseLst,
            (phase: Phase) => phase.approvalAtrValue == 0 || phase.approvalAtrValue == 4);
        if (unapprovePhaseLst.length > 0) {
            return _.sortBy(unapprovePhaseLst, 'phaseOrder').reverse()[0].phaseOrder - 1;
        }
        let approvePhaseLst: Array<Phase> = _.filter(self.phaseLst, (phase: Phase) => phase.approvalAtrValue == 1);
        if (approvePhaseLst.length > 0) {
            return _.sortBy(approvePhaseLst, 'phaseOrder')[0].phaseOrder - 1;
        }

        return 0;
    }

    // tiến tới đơn tiếp theo
    public toNextApp(): void {
        let self = this;
        self.$el.scrollTop = 0;
        self.showApproval = false;
        self.appCount++;
        self.currentApp = self.listAppMeta[self.appCount];
        self.$mask('show');
        self.initData();
    }

    // quay về đơn trước
    public toPreviousApp(): void {
        let self = this;
        self.$el.scrollTop = 0;
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
        return this.listAppMeta.length == 0;
    }

    // ẩn hiện người phê duyệt
    public reverseApproval(): void {
        let self = this;
        self.showApproval = !self.showApproval;
    }

    // quay về màn CMMS45A
    public back(reloadValue?: boolean) {
        let self = this;
        if (self.$router.currentRoute.name == 'cmms45a') {
            self.$close(self.params);
        } else {
            self.$goto('cmms45a', { 'CMMS45_FromMenu': true });
        }
    }

    // kích hoạt nút xóa đơn
    public deleteApp(): void {
        let self = this;
        self.$modal.confirm('Msg_18')
            .then((v) => {
                if (v == 'yes') {
                    self.$mask('show');
                    self.$http.post('at', API.delete, self.appTransferData.appDispInfoStartupOutput
                    ).then((resDelete: any) => {
                        self.$mask('hide');
                        self.$modal.info('Msg_16').then(() => {
                            self.params.action = 1;
                            self.back();
                        });
                    }).catch((res: any) => {
                        self.$mask('hide');
                        self.$modal.error(res.messageId).then(() => {
                            self.back();
                        });
                    });
                }
            });
    }

    // hiển thị nút xóa đơn
    public get displayDeleteButton() {
        let self = this;

        return self.appState.reflectStatus == 0 || self.appState.reflectStatus == 5;
    }

    // hiển thị nút cập nhật đơn
    public get displayUpdateButton() {
        let self = this;

        return self.appState.reflectStatus == 0 || self.appState.reflectStatus == 5;
    }

    // hiển thị menu chỉnh sửa đơn
    public get displayEditFloat() {
        let self = this;

        return self.displayDeleteButton || self.displayUpdateButton;
    }

    // tiến tới màn chi tiết KAF005
    public updateApp(): void {
        const self = this;
        switch (self.appType) {
            case 2:
                if (self.$router.currentRoute.name == 'kafs07a') {
                    self.$close(self.appTransferData.appDetail);
                } else {
                    self.$goto('kafs07a', self.appTransferData.appDetail);
                }
                break;
            case 3:
                if (self.$router.currentRoute.name == 'kafs08a') {
                    self.$close(self.appTransferData.appDetail);
                } else {
                    self.$goto('kafs08a', self.appTransferData.appDetail);
                }
                break;
            case 4:
                self.$goto('kafs09a', self.appTransferData.appDetail);
                break;
            default:
                break;
        }

        // if (self.$router.currentRoute.name == 'kafS05b') {
        //     self.$close({ appID: self.currentApp });
        // } else {
        //     self.$goto('kafS05b', { appID: self.currentApp }); 
        // }
    }

    get applicant() {
        const vm = this;
        if (!vm.appTransferData.appDispInfoStartupOutput) {
            return '';
        }
        let applicantID = vm.appTransferData.appDispInfoStartupOutput.appDetailScreenInfo.application.employeeID,
            employeeInfoLst = vm.appTransferData.appDispInfoStartupOutput.appDispInfoNoDateOutput.employeeInfoLst,
            empInfo = _.find(employeeInfoLst, (o: any) => o.sid == applicantID);
        if (empInfo) {
            return empInfo.bussinessName;
        }

        return '';
    }

    get representerDisp() {
        const vm = this;
        if (!vm.appTransferData.appDispInfoStartupOutput) {
            return false;
        }
        let employeeID = vm.appTransferData.appDispInfoStartupOutput.appDetailScreenInfo.application.employeeID,
            enteredPerson = vm.appTransferData.appDispInfoStartupOutput.appDetailScreenInfo.application.enteredPerson;
        if (employeeID == enteredPerson) {
            return false;
        } else {
            return true;
        }
    }

    get representer() {
        const vm = this;
        if (!vm.appTransferData.appDispInfoStartupOutput) {
            return false;
        }
        if (vm.representerDisp) {
            return vm.appTransferData.appDispInfoStartupOutput.appDispInfoNoDateOutput.opEmployeeInfo.bussinessName;
        }

        return '';
    }

    get appDate() {
        const vm = this;
        if (!vm.appTransferData.appDispInfoStartupOutput) {
            return '';
        }
        let appDate = vm.appTransferData.appDispInfoStartupOutput.appDetailScreenInfo.application.appDate;

        return vm.$dt(new Date(appDate), 'YYYY/MM/DD(dd)');
    }

    get appTypeName() {
        const vm = this;
        if (!vm.appTransferData.appDispInfoStartupOutput) {
            return '';
        }
        switch (vm.appTransferData.appDispInfoStartupOutput.appDetailScreenInfo.application.appType) {
            case AppType.OVER_TIME_APPLICATION:
                return AppTypeName.OVER_TIME_APPLICATION;
                break;
            case AppType.ABSENCE_APPLICATION:
                return AppTypeName.ABSENCE_APPLICATION;
                break;
            case AppType.WORK_CHANGE_APPLICATION:
                return AppTypeName.WORK_CHANGE_APPLICATION;
                break;
            case AppType.BUSINESS_TRIP_APPLICATION:
                return AppTypeName.BUSINESS_TRIP_APPLICATION;
                break;
            case AppType.GO_RETURN_DIRECTLY_APPLICATION:
                return AppTypeName.GO_RETURN_DIRECTLY_APPLICATION;
                break;
            case AppType.LEAVE_TIME_APPLICATION:
                return AppTypeName.LEAVE_TIME_APPLICATION;
                break;
            case AppType.STAMP_APPLICATION:
                return AppTypeName.STAMP_APPLICATION;
                break;
            case AppType.ANNUAL_HOLIDAY_APPLICATION:
                return AppTypeName.ANNUAL_HOLIDAY_APPLICATION;
                break;
            case AppType.EARLY_LEAVE_CANCEL_APPLICATION:
                return AppTypeName.EARLY_LEAVE_CANCEL_APPLICATION;
                break;
            case AppType.COMPLEMENT_LEAVE_APPLICATION:
                return AppTypeName.COMPLEMENT_LEAVE_APPLICATION;
                break;
            case AppType.OPTIONAL_ITEM_APPLICATION:
                return AppTypeName.OPTIONAL_ITEM_APPLICATION;
                break;
            default:
                return '';
                break;
        }
    }

    get prePost() {
        const vm = this;
        if (!vm.appTransferData.appDispInfoStartupOutput) {
            return '';
        }
        let prePostResource = [{
            code: 0,
            text: 'KAFS00_10'
        }, {
            code: 1,
            text: 'KAFS00_11'
        }];

        return _.find(prePostResource, (o: any) => o.code == vm.appTransferData.appDispInfoStartupOutput.appDetailScreenInfo.application.prePostAtr).text;
    }

    get inputDate() {
        const vm = this;
        if (!vm.appTransferData.appDispInfoStartupOutput) {
            return '';
        }
        let appDate = vm.appTransferData.appDispInfoStartupOutput.appDetailScreenInfo.application.inputDate;

        return vm.$dt(new Date(appDate), 'YYYY/MM/DD hh:mm');
    }

    get comboReasonDisp() {
        const vm = this;
        if (!vm.appTransferData.appDispInfoStartupOutput) {
            return false;
        }

        return vm.appTransferData.appDispInfoStartupOutput.appDispInfoNoDateOutput.displayStandardReason == 0 ? false : true;
    }

    get textReasonDisp() {
        const vm = this;
        if (!vm.appTransferData.appDispInfoStartupOutput) {
            return false;
        }

        return vm.appTransferData.appDispInfoStartupOutput.appDispInfoNoDateOutput.displayAppReason == 0 ? false : true;
    }

    get comboReason() {
        const vm = this;
        if (!vm.appTransferData.appDispInfoStartupOutput) {
            return '';
        }
        let dropdownList = vm.appTransferData.appDispInfoStartupOutput.appDispInfoNoDateOutput.reasonTypeItemLst,
            opComboReason = _.find(dropdownList, (o: any) => {
                return o.appStandardReasonCD == vm.appTransferData.appDispInfoStartupOutput.appDetailScreenInfo.application.opAppStandardReasonCD;
            });
        if (opComboReason) {
            return opComboReason.reasonForFixedForm;
        }

        return vm.appTransferData.appDispInfoStartupOutput.appDetailScreenInfo.application.opAppStandardReasonCD + ' ' + vm.$i18n('CMMS45_87');
    }

    get textReason() {
        const vm = this;
        if (!vm.appTransferData.appDispInfoStartupOutput) {
            return '';
        }

        return vm.appTransferData.appDispInfoStartupOutput.appDetailScreenInfo.application.opAppReason;
    }

}

const API = {
    delete: 'at/request/application/deleteapp',
    getDetailMob: 'at/request/app/smartphone/getDetailMob'
};