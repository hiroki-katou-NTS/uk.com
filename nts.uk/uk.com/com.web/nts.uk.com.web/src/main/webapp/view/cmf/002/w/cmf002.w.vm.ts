/// <reference path='../../../../lib/nittsu/viewcontext.d.ts' />

module nts.uk.com.view.cmf002.w {

  @bean()
  export class CMF002WViewModel extends ko.ViewModel {
    // W1
    listPeriodSetting: KnockoutObservableArray<any> = ko.observableArray([]);
    selectedPeriodSetting: KnockoutObservable<any> = ko.observable(null);
    // W2
    listDeadlineClassification: KnockoutObservableArray<any> = ko.observableArray([]);
    selectedDeadlineClassification: KnockoutObservable<any> = ko.observable(null);
    // W3
    listStartDateSegment: KnockoutObservableArray<any> = ko.observableArray([]);
    selectedStartDateSegment: KnockoutObservable<any> = ko.observable(null);
    // W4
    startDateAdjustment: KnockoutObservable<any> = ko.observable(null);
    startDateSpecified: KnockoutObservable<any> = ko.observable(null);
    // W5
    listEndDateSegment: KnockoutObservableArray<any> = ko.observableArray([]);
    selectedEndDateSegment: KnockoutObservable<any> = ko.observable(null);
    // W6
    endDateAdjustment: KnockoutObservable<any> = ko.observable(null);
    endDateSpecified: KnockoutObservable<any> = ko.observable(null);
    // W7
    listBaseDateSegment: KnockoutObservableArray<any> = ko.observableArray([]);
    selectedBaseDateSegment: KnockoutObservable<any> = ko.observable(null);
    // W8
    baseDateSpecified: KnockoutObservable<any> = ko.observable(null);

    constructor() {
      super();
      const vm = this;
      vm.listPeriodSetting = ko.observableArray([
        { code: '1', name: nts.uk.resource.getText("CMF002_275") },
        { code: '2', name: nts.uk.resource.getText("CMF002_276") },
      ]);
      vm.listStartDateSegment([
        { code: StartDateClassificationCode.DEADLINE_START, name: nts.uk.resource.getText('CMF002_542') },
        { code: StartDateClassificationCode.DEADLINE_END, name: nts.uk.resource.getText('CMF002_543') },
        { code: StartDateClassificationCode.DEADLINE_PROCESSING, name: nts.uk.resource.getText('CMF002_544') },
        { code: StartDateClassificationCode.SYSTEM_DATE, name: nts.uk.resource.getText('CMF002_545') },
        { code: StartDateClassificationCode.DATE_SPECIFICATION, name: nts.uk.resource.getText('CMF002_546') },
      ]);
      vm.listEndDateSegment([
        { code: EndDateClassificationCode.DEADLINE_START, name: nts.uk.resource.getText('CMF002_542') },
        { code: EndDateClassificationCode.DEADLINE_END, name: nts.uk.resource.getText('CMF002_543') },
        { code: EndDateClassificationCode.DEADLINE_PROCESSING, name: nts.uk.resource.getText('CMF002_544') },
        { code: EndDateClassificationCode.SYSTEM_DATE, name: nts.uk.resource.getText('CMF002_545') },
        { code: EndDateClassificationCode.DATE_SPECIFICATION, name: nts.uk.resource.getText('CMF002_546') },
      ]);
      vm.listBaseDateSegment([
        { code: BaseDateClassificationCode.DEADLINE_START, name: nts.uk.resource.getText('CMF002_547') },
        { code: BaseDateClassificationCode.DEADLINE_END, name: nts.uk.resource.getText('CMF002_548') },
        { code: BaseDateClassificationCode.SYSTEM_DATE, name: nts.uk.resource.getText('CMF002_545') },
        { code: BaseDateClassificationCode.OUTPUT_PERIOD_START, name: nts.uk.resource.getText('CMF002_549') },
        { code: BaseDateClassificationCode.OUTPUT_PERIOD_END, name: nts.uk.resource.getText('CMF002_550') },
        { code: BaseDateClassificationCode.DATE_SPECIFICATION, name: nts.uk.resource.getText('CMF002_546') },
      ]);
    }

    mounted() {
      const params = nts.uk.ui.windows.getShared('CMF002_W_PARAMS');
      const vm = this;
      vm.selectedPeriodSetting('1');
      vm.$blockui('grayout');
      // ドメインモデル「就業締め日」を取得する
      service.findAllClosureHistory()
        // 全ての取得した締め日名称をJ10_2「締め日区分ドロップダウンリスト」に表示する
        .done((response: ClosureHistoryFindDto[]) => vm.setListClosureHistory(response))
        // ドメインモデル「出力条件設定」を取得する
        .done(() => service.findOutputPeriodSetting(params.conditionSetCode))
        //「出力期間設定」の情報を画面に表示する
        .done((response: any) => vm.setOutputPeriodSetting(response))
        .always(() => vm.$blockui('clear'));
    }

    /**
     * 全ての取得した締め日名称をJ10_2「締め日区分ドロップダウンリスト」に表示する
     * Set ClosureHistory
     */
    private setListClosureHistory(response: ClosureHistoryFindDto[]): JQueryPromise<any> {
      const dfd = $.Deferred();
      const vm = this;
      vm.listDeadlineClassification(response);
      dfd.done();
      return dfd;
    }

    /**
     * 「出力期間設定」の情報を画面に表示する
     * Set OutputPeriodSetting
     */
    private setOutputPeriodSetting(response: any): JQueryPromise<any> {
      const dfd = $.Deferred();
      const vm = this;
      // TODO
      dfd.done();
      return dfd;
    }

    /**
     * キャンセルボタン押下時処理
     * Close dialog
     */
    public closeDialog() {
      const vm = this;
      // 閉じるの確認メッセージ => キャンセルの確認メッセージ
      vm.$dialog.confirm({ messageId: "Msg_19" }).then((result: 'no' | 'yes' | 'cancel') => {
        if (result === 'no') {
          //「閉じる処理をキャンセル」を選択した場合
          return;
        } else if (result === 'yes') {
          //「閉じる処理を実行」を選択した場合
          // 画面を閉じる
          vm.$window.close();
        }
      });
    }

  }

  // 開始日区分
  export class StartDateClassificationCode {
    static DEADLINE_START = 1;
    static DEADLINE_END = 2;
    static DEADLINE_PROCESSING = 3;
    static SYSTEM_DATE = 4;
    static DATE_SPECIFICATION = 5;
  }

  // 終了日区分
  export class EndDateClassificationCode {
    static DEADLINE_START = 1;
    static DEADLINE_END = 2;
    static DEADLINE_PROCESSING = 3;
    static SYSTEM_DATE = 4;
    static DATE_SPECIFICATION = 5;
  }

  // 基準日区分
  export class BaseDateClassificationCode {
    static DEADLINE_START = 1;
    static DEADLINE_END = 2;
    static SYSTEM_DATE = 3;
    static OUTPUT_PERIOD_START = 4;
    static OUTPUT_PERIOD_END = 5;
    static DATE_SPECIFICATION = 6;
  }

  export interface ClosureHistoryFindDto {
    /** The id. */
    id: number;
    /** The name. */
    name: string;
  }
}
