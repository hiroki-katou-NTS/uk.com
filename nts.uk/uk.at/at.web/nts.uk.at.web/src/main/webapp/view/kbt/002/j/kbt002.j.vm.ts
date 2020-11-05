/// <reference path='../../../../lib/nittsu/viewcontext.d.ts' />

module nts.uk.at.view.kbt002.j {

  const API = {
    findAll: "at/function/resultsperiod/findAll",
    saveAggrPeriod: "at/function/resultsperiod/save",
    removeAggrPeriod: "at/function/resultsperiod/removeAggrPeriod"
  };

  @bean()
  export class KBT002JViewModel extends ko.ViewModel {
    isNewMode: KnockoutObservable<boolean> = ko.observable(true);
    aggrFrameCode: KnockoutObservable<string> = ko.observable(null);
    optionalAggrName: KnockoutObservable<string> = ko.observable(null);
    startDate: KnockoutObservable<string> = ko.observable(null);
    endDate: KnockoutObservable<string> = ko.observable(null);

    aggrPeriodList: KnockoutObservableArray<AggrPeriodDto> = ko.observableArray([]);
    selectedAggrFrameCd: KnockoutObservable<string> = ko.observable('');
    refDate: KnockoutObservable<string> = ko.observable('');
    execScopeCls: KnockoutObservable<number> = ko.observable(null);

    mounted() {
      const vm = this;
      vm.findAll();
      vm.selectedAggrFrameCd.subscribe(aggrFrameCode => {
        vm.initProcExec();
        // set update mode
        const selectedItem: AggrPeriodDto = _.find(vm.aggrPeriodList(), (o) => o.aggrFrameCode === aggrFrameCode);
        if (selectedItem) {
          vm.bindingData(selectedItem);
          vm.isNewMode(false);
        } else {
          vm.isNewMode(true);
        }
        vm.$nextTick(() => vm.focusInput(vm.isNewMode()));
      });
    }

    bindingData(param: AggrPeriodDto) {
      const vm = this;
      vm.aggrFrameCode(param.aggrFrameCode || '');
      vm.optionalAggrName(param.optionalAggrName || '');
      vm.startDate(param.startDate || '');
      vm.endDate(param.endDate || '');
    }

    private focusInput(isNewMode: boolean) {
      if (isNewMode) {
        $('#aggrFrameCode').focus();
      } else {
        $('#optionalAggrName').focus();
      }
    }

    private initProcExec() {
      const vm = this;
      nts.uk.ui.errors.clearAll();
      vm.optionalAggrName('');
      vm.aggrFrameCode('');
      vm.startDate('');
      vm.endDate('');
      vm.isNewMode(true);
    }

    /**
     * ドメインモデル「任意集計期間」を取得する
     * Find all AggrPeriodDto by companyId
     */
    public findAll() {
      const vm = this;
      vm.$blockui('grayout');
      vm.$ajax(API.findAll)
        .then((res: AggrPeriodDto[]) => vm.aggrPeriodList(res))
        .always(() => vm.$blockui('clear'));
    }

    /**
     * 任意集計期間の登録処理
     * Create AggrPeriodDto
    */
    public save() {
      const vm = this;
      const command: AggrPeriodCommand = new AggrPeriodCommand({
        aggrFrameCode: vm.aggrFrameCode(),
        optionalAggrName: vm.optionalAggrName(),
        startDate: vm.startDate() ? moment.utc(vm.startDate(), 'YYYY/MM/DD').toISOString() : null,
        endDate: vm.endDate() ? moment.utc(vm.endDate(), 'YYYY/MM/DD').toISOString() : null,
      });
      vm.$blockui('grayout');
      vm.$validate()
        .then((valid: boolean) => {
          if (!valid) {
            return $.Deferred().reject();
          }
          //ドメインモデル「任意集計期間」を登録する
          return vm.$ajax(API.saveAggrPeriod, command);
        })
        // 情報メッセージ（ID：Msg_15）を表示する
        .then(() => {
          vm.$blockui('clear');
          vm.$dialog.info({ messageId: "Msg_16" }).then(() => vm.findAll());
        })
        .always(() => vm.$blockui('clear'));
    }

    /**
     * 任意集計期間の削除処理
     * Remove AggrPeriodDto
    */
    public remove() {
      const vm = this;
      vm.$dialog.confirm({ messageId: "Msg_18" })
        .then((result: 'no' | 'yes' | 'cancel') => {
          if (result === 'yes') {
            vm.$blockui('grayout');
            vm.$ajax(`${API.removeAggrPeriod}/${vm.aggrFrameCode()}`)
              .then(() => {
                vm.$blockui('clear');
                vm.$dialog.info({ messageId: "Msg_16" }).then(() => vm.findAll());
              })
              .fail((res) => {
                vm.$blockui('clear');
                vm.$dialog.alert({ messageId: res.messageId });
              });
          }
        });
    }

    /**
     * キャンセルボタン押下時処理
     * Close dialog
    */
    public closeDialog() {
      const vm = this;
      // 画面を閉じる
      vm.$window.close();
    }
  }

  /**
  * The Class AggrPeriodDto.
  * 	任意集計期間
  */
  export interface AggrPeriodDto {
    companyId: string,
    aggrFrameCode: string,
    optionalAggrName: string,
    startDate: string,
    endDate: string
  }

  export class AggrPeriodCommand {
    /**  会社ID. */
    companyId: string;
    /**  任意集計枠コード. */
    aggrFrameCode: string;
    /**  任意集計名称. */
    optionalAggrName: string;
    /**  対象期間. */
    startDate: string;
    /**  対象期間. */
    endDate: string;

    constructor(init?: Partial<AggrPeriodDto>) {
      $.extend(this, init);
    }
  }
}