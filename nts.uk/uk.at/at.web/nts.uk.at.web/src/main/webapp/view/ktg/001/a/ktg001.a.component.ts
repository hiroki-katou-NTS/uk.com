module nts.uk.ui.ktg001.a {

    export const KTG001_API = {
        GET_APPROVED_DATA_EXCECUTION: 'screen/at/ktg001/display',
        UPDATE_APPROVED_DATA_EXCECUTION: 'screen/at/ktg001/setting',
    };

    //承認すべきデータの実行結果
    export interface IApprovedDataExecutionResult {
        haveParticipant: Boolean; //勤怠担当者である
        topPagePartName: string; //名称
        appDisplayAtr: Boolean; //承認すべき申請データ
        dayDisplayAtr: Boolean; //承認すべき日の実績が存在する
        monthDisplayAtr: Boolean; //承認すべき月の実績が存在する
        agrDisplayAtr: Boolean; //承認すべき36協定が存在する
        approvedAppStatusDetailedSettings: Array<IApprovedAppStatusDetailedSetting>; //承認すべき申請状況の詳細設定
        closingPeriods: Array<IClosureIdPresentClosingPeriod>; //締めID, 現在の締め期間
    }

    //承認すべき申請状況の詳細設定
    export interface IApprovedAppStatusDetailedSetting {
        displayType: number; //表示区分
        item: number; //項目
    }

    //List＜締めID, 現在の締め期間＞
    export interface IClosureIdPresentClosingPeriod {
        closureId: number; //締めID
        currentClosingPeriod: IPresentClosingPeriodImport; //現在の締め期間
    }

    //現在の締め期間
    export interface IPresentClosingPeriodImport {
        processingYm: number; //処理年月
        closureStartDate: String; //締め開始日
        closureEndDate: String; //締め終了日
    }

    //承認すべきデータのウィジェットを起動する
    export interface IResponse {
        approvedDataExecutionResultDto: IApprovedDataExecutionResult; //承認すべきデータのウィジェットを起動する
        approvalProcessingUseSetting: IApprovalProcessingUseSetting; //承認処理の利用設定を取得する
        agreementOperationSetting: any; //ドメインモデル「３６協定運用設定」を取得する

    }

    //承認処理の利用設定を取得する
    export interface IApprovalProcessingUseSetting {
        useDayApproverConfirm: Boolean; //日の承認者確認を利用する
        useMonthApproverConfirm: Boolean; //月の承認者確認を利用する
    }

    interface IParam {
        //ym: number, //表示期間
        closureId: number //締めID
    }

    @component({
        name: 'ktg-001-a',
        template: `
            <div class="widget-title">
                <table>
                    <colgroup>
                        <col width="auto" />
                        <col width="auto" />
                        <col width="27px" />
                    </colgroup>
                    <thead>
                        <tr>
                            <th colspan="2" data-bind="i18n: title"></th>
                            <th>
                                <button class="icon" data-bind="
                                        click : $component.setting,
                                        timeClick: -1,
                                        visible: $component.settingIconVisible
                                    ">
                                    <i data-bind="ntsIcon: { no: 5 }"></i>
                                </button>
                            </th>
                        </tr>
                    </thead>
                </table>
            </div>
            <div class="ktg-001-a" data-bind="widget-content: 100">
                <div>
                    <table>
                        <colgroup>
                            <col width="auto" />
                            <col width="auto" />
                            <col width="120px" />
                        </colgroup>
                        <tbody>
                            <tr data-bind="visible: $component.appRowVisible">
                                <td data-bind="text: $i18n('KTG001_1')"></td>
                                <td class="text-center">
                                    <button class="icon" data-bind="
                                            click: $component.applicationList,
                                            visible: $component.appIconVisible
                                        ">
                                        <i data-bind="ntsIcon: { no: 145 }"></i>
                                    </button>
                                </td>
                                <td class="text-right" data-bind="text: $component.appText"></td>
                            </tr>
                            <tr data-bind="visible: $component.dayRowVisible">
                                <td data-bind="i18n: 'KTG001_2'"></td>
                                <td class="text-center">
                                    <button class="icon" data-bind="
                                            click: $component.dayPerformanceConfirm,
                                            visible: $component.dayIconVisible
                                        ">
                                        <i data-bind="ntsIcon: { no: 145 }"></i>
                                    </button>
                                </td>
                                <td class="text-right" data-bind="text: $component.dayText"></td>
                            </tr>
                            <tr data-bind="visible: $component.monRowVisible">
                                <td data-bind="i18n: 'KTG001_3'"></td>
                                <td class="text-center">
                                    <button class="icon" data-bind="
                                            click: $component.monPerformanceConfirm,
                                            visible: monIconVisible
                                        ">
                                        <i data-bind="ntsIcon: { no: 145 }"></i>
                                    </button>
                                </td>
                                <td class="text-right" data-bind="text: $component.monText"></td>
                            </tr>
                            <tr data-bind="visible: $component.aggrRowVisible">
                                <td data-bind="i18n: 'KTG001_4'"></td>
                                <td class="text-center">
                                    <button class="icon" data-bind="
                                            click: $component.aggrementApproval,
                                            visible: $component.aggrIconVisible
                                        ">
                                        <i data-bind="ntsIcon: { no: 145 }"></i>
                                    </button>
                                </td>
                                <td class="text-right" data-bind="text: $component.aggrText"></td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
            <style rel="stylesheet">
                .ktg-001-a .text-center {
                    text-align: center;
                }
                .ktg-001-a .text-right {
                    text-align: right;
                }
            </style>
        `
    })
    export class KTG001AComponent extends ko.ViewModel {
        widget: string = 'KTG001A';

        title: KnockoutObservable<string> = ko.observable('');
        appText: KnockoutObservable<string> = ko.observable('');
        dayText: KnockoutObservable<string> = ko.observable('');
        monText: KnockoutObservable<string> = ko.observable('');
        aggrText: KnockoutObservable<string> = ko.observable('');

        appRowVisible: KnockoutObservable<Boolean> = ko.observable(false);
        dayRowVisible: KnockoutObservable<Boolean> = ko.observable(false);
        monRowVisible: KnockoutObservable<Boolean> = ko.observable(false);
        aggrRowVisible: KnockoutObservable<Boolean> = ko.observable(false);

        appIconVisible: KnockoutObservable<Boolean> = ko.observable(false);
        dayIconVisible: KnockoutObservable<Boolean> = ko.observable(false);
        monIconVisible: KnockoutObservable<Boolean> = ko.observable(false);
        aggrIconVisible: KnockoutObservable<Boolean> = ko.observable(false);

        settingIconVisible: KnockoutObservable<Boolean> = ko.observable(false);

        constructor(private params: { currentOrNextMonth: 1 | 2; }) {
            super();

            if (this.params === undefined) {
                this.params = { currentOrNextMonth: 1 };
            }

            if (this.params.currentOrNextMonth === undefined) {
                this.params.currentOrNextMonth = 1;
            }

        }

        mounted() {
            const vm = this;

            vm.loadData();

            vm.$el.removeAttribute('data-bind');
        }

        loadData(): void {
            const vm = this;
            const { params } = vm;
            const query = [
                'nts.uk.ctx.sys.portal.dom.toppagepart.standardwidget.ApprovedApplicationStatusItem',
                'nts.uk.ctx.at.schedule.dom.plannedyearholiday.frame.NotUseAtr'
            ];

            const { GET_APPROVED_DATA_EXCECUTION } = KTG001_API;
            const enums = vm.$ajax('com', '/enums/map', query);
            const aprov = vm.$ajax('at', GET_APPROVED_DATA_EXCECUTION, { ...params, ym: params.currentOrNextMonth });

            vm.$blockui("invisibleView")
                .then(() => $.when(enums, aprov))
                .done((enumerable: EnumResponse, data: IResponse) => {
                    const { NotUseAtr, ApprovedApplicationStatusItem } = enumerable;
                    const [APPVV, APPNV] = NotUseAtr;
                    const [APPV, DAYV, MONV, AGGV] = ApprovedApplicationStatusItem;

                    const USE = APPNV.value;

                    const APP = APPV.value;
                    const DAY = DAYV.value;
                    const MON = MONV.value;
                    const AGG = AGGV.value;

                    if (data.approvedDataExecutionResultDto) {
                        let approvedDataExecution = data.approvedDataExecutionResultDto;
                        let approvalProcessingUse = data.approvalProcessingUseSetting;
                        let agreementOperationSetting = data.agreementOperationSetting;

                        vm.title(approvedDataExecution.topPagePartName);
                        vm.appText(approvedDataExecution.appDisplayAtr == true ? vm.$i18n('KTG001_5') : vm.$i18n('KTG001_6'));
                        vm.dayText(approvedDataExecution.dayDisplayAtr == true ? vm.$i18n('KTG001_5') : vm.$i18n('KTG001_6'));
                        vm.monText(approvedDataExecution.monthDisplayAtr == true ? vm.$i18n('KTG001_5') : vm.$i18n('KTG001_6'));
                        vm.aggrText(approvedDataExecution.agrDisplayAtr == true ? vm.$i18n('KTG001_5') : vm.$i18n('KTG001_6'));
                        vm.settingIconVisible(approvedDataExecution.haveParticipant);

                        if (approvedDataExecution.approvedAppStatusDetailedSettings) {
                            approvedDataExecution.approvedAppStatusDetailedSettings.forEach(i => {
                                if (i.item == APP) {
                                    vm.appRowVisible(i.displayType == USE);
                                    vm.appIconVisible(i.displayType == USE && approvedDataExecution.appDisplayAtr == true ? true : false);
                                }

                                if (i.item == DAY) {
                                    vm.dayRowVisible(i.displayType == USE && approvalProcessingUse.useDayApproverConfirm == true);
                                    vm.dayIconVisible(i.displayType == USE && approvalProcessingUse.useDayApproverConfirm == true && approvedDataExecution.dayDisplayAtr == true ? true : false);
                                }

                                if (i.item == MON) {
                                    vm.monRowVisible(i.displayType == USE && approvalProcessingUse.useMonthApproverConfirm == true);
                                    vm.monIconVisible(i.displayType == USE && approvalProcessingUse.useMonthApproverConfirm == true && approvedDataExecution.monthDisplayAtr == true ? true : false);
                                }

                                if (i.item == AGG) {
                                    vm.aggrRowVisible(i.displayType == USE && agreementOperationSetting.specicalConditionApplicationUse == true);
                                    vm.aggrIconVisible(i.displayType == USE && agreementOperationSetting.specicalConditionApplicationUse == true && approvedDataExecution.agrDisplayAtr == true ? true : false);
                                }

                            })
                        } else {
							vm.title(vm.$i18n('KTG001_12'));
							vm.appRowVisible(true);
							vm.aggrRowVisible(false);
							vm.dayRowVisible(approvalProcessingUse.useDayApproverConfirm);
							vm.monRowVisible(approvalProcessingUse.useMonthApproverConfirm);
						}
                    }
                })
                .then(() => {
                    vm.$nextTick(() => {
                        $(vm.$el)
                            .find('[data-bind]')
                            .removeAttr('data-bind');
                    });
                })
                .always(() => vm.$blockui("clearView"));
        }

        applicationList() {
            const vm = this;

            vm.$jump('at', '/view/cmm/045/a/index.xhtml?a=1');
        }

        dayPerformanceConfirm() {
            const vm = this;

            vm.$jump('at', '/view/kdw/004/a/index.xhtml');
        }

        monPerformanceConfirm() {
            const vm = this;

            vm.$jump('at', '/view/kmw/003/a/index.xhtml?initmode=2');
        }

        aggrementApproval() {
            const vm = this;

            vm.$jump('at', '/view/kaf/021/d/index.xhtml');
        }

        setting() {
            let vm = this;

            vm.$window
                .modal('at', '/view/ktg/001/b/index.xhtml')
                .then(() => vm.loadData());
        }
    }

    interface EnumResponse {
        NotUseAtr: NotUseAtr[];
        ApprovedApplicationStatusItem: ApprovedApplicationStatusItem[];
    }

    interface NotUseAtr {
        value: 0 | 1,
        name: string;
    }

    interface ApprovedApplicationStatusItem {
        value: 0 | 1 | 2 | 3;
        name: string;
    }
}