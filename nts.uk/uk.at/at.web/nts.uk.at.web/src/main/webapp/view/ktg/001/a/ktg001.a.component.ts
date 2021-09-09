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
       	dayDisplayAtrList: Array<IApprovedDataDetail>; //承認すべき日の実績が存在する
		monthDisplayAtrList: Array<IApprovedDataDetail>; //承認すべき月の実績が存在する
        agrDisplayAtr: Boolean; //承認すべき36協定が存在する
        approvedAppStatusDetailedSettings: Array<IApprovedAppStatusDetailedSetting>; //承認すべき申請状況の詳細設定
        closingPeriods: Array<IClosureIdPresentClosingPeriod>; //締めID, 現在の締め期間
    }

	//承認すべきデータの実行詳細
	export interface IApprovedDataDetail {
		displayAtr: Boolean; //存在する
		yearMonth: number; //年月
		closureId: number; //締めID
		name: string; //締名
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

    @component({
        name: 'ktg-001-a',
        template: `
            <div class="widget-title">
                <table style="width: 100%;">
                    <colgroup>
                        <col width="auto" />
                        <col width="30px" />
                    </colgroup>
                    <thead>
                        <tr>
                            <th class="ktg001-fontsize-larger">
                                <!-- A1_1 -->
                                <div data-bind="ntsFormLabel: { required: false, text: title }"></div>
                            </th>
                            <th>
                                <!-- A1_2 -->
                                <button class="icon ktg001-no-border" data-bind="
                                        click : $component.setting,
                                        visible: $component.settingIconVisible
                                    ">
                                    <i data-bind="ntsIcon: { no: 5, width: 25, height: 25 }"></i>
                                </button>
                            </th>
                        </tr>
                    </thead>
                </table>
            </div>
            <div class="ktg-001-a ktg001-fontsize" data-bind="widget-content: 100">
                <div class="ktg001-border" style="padding: 0px 40px 0px 30px;">
                    <table style="width: 100%;">
                        <colgroup>
                            <col width="1%" />
                            <col width="99%" />
                        </colgroup>
                        <tbody>
                            <tr class='row-header' data-bind="css: $component.appRowVisible() ? 'row-show' : '', visible: $component.appRowVisible">
                                <td class="text-center" style="position: relative;">
                                    <!-- A2_2 -->
                                    <button class="ktg001-no-border" data-bind="ntsIcon: { no: 200, width: 28, height: 28 },
                                            enable: $component.appIconVisible,
                                            click: $component.applicationList">
                                    </button>
                                    <!-- A2_3 -->
                                    <i style="position: absolute; bottom: 5px; left: 15px; cursor: pointer;"
                                        data-bind="visible: $component.appIconVisible, ntsIcon: { no: 165, width: 13, height: 13 }, click: $component.applicationList""></i>
                                </td>
                                <td style="padding-left: 5px;">
                                    <!-- A2_1 -->
                                    <div data-bind="ntsFormLabel: { required: false, text: $i18n('KTG001_1') }"></div>
                                </td>
                                <td style="padding-left: 5px;">
                                    <div data-bind="text: ''"></div>
                                </td>
                            </tr>
                            <!-- ko foreach: { data: $component.dayDisplayAtrList, as: 'day' } -->
                                <tr class='row-day' data-bind="css: $component.dayRowVisible() ? 'row-show' : '', visible: $component.dayRowVisible">
                                    <td class="" style="position: relative;">
                                        <!-- A3_2 -->
                                        <button class="ktg001-no-border" data-bind="ntsIcon: { no: 200, width: 28, height: 28 },
                                            click: function() { $component.dayPerformanceConfirm(day.closureId, day.yearMonth) },
                                            enable: day.displayAtr">
                                        </button>
                                        <!-- A3_3 -->
                                        <i style="position: absolute; bottom: 5px; left: 15px; cursor: pointer;"
                                            data-bind="visible: day.displayAtr, ntsIcon: { no: 165, width: 13, height: 13 }, click: function() { $component.dayPerformanceConfirm(day.closureId, day.yearMonth) }"></i>
                                    </td>
                                    <!-- ko if: day.closureId == 1 -->
                                        <td style="padding-left: 5px;">
                                            <!-- A3_1 -->
											<div class="ktg001-form-label" data-bind= "i18n: 'KTG001_2'"></div>
                                        </td>
                                    <!-- /ko -->
                                    <!-- ko if: day.closureId != 1 -->
                                        <td style="padding-left: 5px;">
                                            <!-- A3_1 -->
                                            <div data-bind="text: ''"></div>
                                        </td>
                                    <!-- /ko -->
                                    <td>
                                        <!-- A3_5 -->
                                        <div style="width: 70px;" data-bind="text: day.name"></div>
                                    </td>
                                </tr>
                            <!-- /ko -->
                            <!-- ko foreach: { data: $component.monthDisplayAtrList, as: 'month' } -->
                                <tr class='row-month' data-bind="css: $component.monRowVisible() ? 'row-show' : '', visible: $component.monRowVisible">
                                    <td class="text-center" style="position: relative;">
                                        <!-- A4_2 -->
                                        <button class="ktg001-no-border" data-bind="ntsIcon: { no: 200, width: 28, height: 28 },
                                            click: function() { $component.monPerformanceConfirm(month.closureId, month.yearMonth) },
                                            enable: month.displayAtr">
                                        </button>
                                        <!-- A4_3 -->
                                        <i style="position: absolute; bottom: 5px; left: 15px; cursor: pointer;"
                                            data-bind="visible: month.displayAtr, ntsIcon: { no: 165, width: 13, height: 13 }, click: function() { $component.monPerformanceConfirm(month.closureId, month.yearMonth) }"></i>
                                    </td>
                                    <!-- ko if: month.closureId == 1 -->
                                        <td style="padding-left: 5px;">
                                            <!-- A4_1 -->
                                            <div class="ktg001-form-label" data-bind="i18n: 'KTG001_3'"></div>
                                         </td>
                                    <!-- /ko -->
                                    <!-- ko if: month.closureId != 1 -->
                                        <td style="padding-left: 5px;">
											 <!-- A4_1 -->
                                            <div data-bind="text: ''"></div>
                                        </td>
                                    <!-- /ko -->
                                    <td>
                                        <!-- A4_5 -->
                                        <div style="width: 70px;" data-bind="text: month.name"></div>
                                 	</td>
                                </tr>
                            <!-- /ko -->

                            <tr class='row-approval' data-bind="css: $component.aggrRowVisible() ? 'row-show' : '', visible: $component.aggrRowVisible">
                                <td class="text-center" style="position: relative;;">
                                    <!-- A5_2 -->
                                    <button class="ktg001-no-border" data-bind="ntsIcon: { no: 200, width: 28, height: 28 },
                                        click: $component.aggrementApproval,
                                        enable: $component.aggrIconVisible">
                                    </button>
                                    <!-- A5_3 -->
                                    <i style="position: absolute; bottom: 5px; left: 15px; cursor: pointer;"
                                        data-bind="visible: $component.aggrIconVisible, ntsIcon: { no: 165, width: 13, height: 13 }, click: $component.aggrementApproval"></i>
                                </td>
                                <td style="padding-left: 5px;">
                                    <!-- A5_1 -->
                                    <div data-bind="ntsFormLabel: { required: false, text: $i18n('KTG001_4') }"></div>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
            <style rel="stylesheet">
                .ktg-001-a table tr {
                    height: 30px !important;
                }
                .ktg-001-a .text-center {
                    text-align: center;
                }
                .ktg-001-a .text-right {
                    text-align: right;
                }
                .ktg001-no-border {
                    border: none !important;
                }
                .ktg001-fontsize-larger div.form-label>span.text {
                    font-size: 1.2rem !important;
                }
                .ktg001-fontsize div.form-label>span.text {
                    font-size: 1rem !important;
                }
                .ktg001-border table tr td,
			    .ktg001-border table tr th {
                    border-width: 0px;
                    border-bottom: none;
			    }
				.row-show button {
				    box-shadow: none;
				}
				.ktg001-form-label {
					color: #808080;
    				font-weight: 500;
				}
            </style>
        `
    })
    export class KTG001AComponent extends ko.ViewModel {
        widget: string = 'KTG001A';

        title: KnockoutObservable<string> = ko.observable('');

        appRowVisible: KnockoutObservable<Boolean> = ko.observable(false);
        dayRowVisible: KnockoutObservable<Boolean> = ko.observable(false);
        monRowVisible: KnockoutObservable<Boolean> = ko.observable(false);
        aggrRowVisible: KnockoutObservable<Boolean> = ko.observable(false);

        appIconVisible: KnockoutObservable<Boolean> = ko.observable(false);
        aggrIconVisible: KnockoutObservable<Boolean> = ko.observable(false);

        settingIconVisible: KnockoutObservable<Boolean> = ko.observable(false);
		dayDisplayAtrList: KnockoutObservableArray<IApprovedDataDetail> = ko.observableArray([]);
		monthDisplayAtrList: KnockoutObservableArray<IApprovedDataDetail> = ko.observableArray([]);

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

                        vm.settingIconVisible(approvedDataExecution.haveParticipant);
						vm.dayDisplayAtrList(approvedDataExecution.dayDisplayAtrList);
						vm.monthDisplayAtrList(approvedDataExecution.monthDisplayAtrList);

                        if (approvedDataExecution.approvedAppStatusDetailedSettings) {
                            approvedDataExecution.approvedAppStatusDetailedSettings.forEach(i => {
                                if (i.item == APP) {
                                    vm.appRowVisible(i.displayType == USE);
                                    vm.appIconVisible(i.displayType == USE && approvedDataExecution.appDisplayAtr == true ? true : false);
                                }

                                if (i.item == DAY) {
                                    vm.dayRowVisible(i.displayType == USE && approvalProcessingUse.useDayApproverConfirm == true);
                                }

                                if (i.item == MON) {
                                    vm.monRowVisible(i.displayType == USE && approvalProcessingUse.useMonthApproverConfirm == true);
                                }

                                if (i.item == AGG) {
                                    vm.aggrRowVisible(i.displayType == USE && agreementOperationSetting.specicalConditionApplicationUse == true);
                                    vm.aggrIconVisible(i.displayType == USE && agreementOperationSetting.specicalConditionApplicationUse == true && approvedDataExecution.agrDisplayAtr == true ? true : false);
                                }

                            })
                        }
                    }
                })
                .then(() => {
                    vm.$nextTick(() => {
                        $(vm.$el)
                            .find('[data-bind]')
                            .removeAttr('data-bind');
                        _.forEach($(".row-show td"), element => $(element).removeClass("ktg001-no-border"));    
                        _.forEach($($(".row-show").last().children()), element => $(element).addClass("ktg001-no-border"));
						$(".row-header").last().children().css('border-bottom', '1px solid #BFBFBF');
						$(".row-day").last().children().css('border-bottom', '1px solid #BFBFBF');
						$(".row-month").last().children().css('border-bottom', '1px solid #BFBFBF');
						$(".row-approval").last().children().css('border-bottom', '1px solid #BFBFBF');
                    });
                })
                .always(() => vm.$blockui("clearView"));
        }

        applicationList() {
            const vm = this;

            vm.$jump('at', '/view/cmm/045/a/index.xhtml?a=1');
        }

        dayPerformanceConfirm(clsId: number, ym: number) {
            const vm = this;
			const param = {closureId: clsId, yearMonth: ym};

            vm.$jump('at', '/view/kdw/004/a/index.xhtml', param);
        }

        monPerformanceConfirm(clsId: number, ym: number) {
            const vm = this;
			const param = {closureId: clsId, yearMonth: ym};

            vm.$jump('at', '/view/kmw/003/a/index.xhtml?initmode=2', param);
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