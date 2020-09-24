/// <reference path='../../../../lib/nittsu/viewcontext.d.ts' />

module nts.uk.at.view.kbt002.j {
  import setShared = nts.uk.ui.windows.setShared;
  import getShared = nts.uk.ui.windows.getShared;
  import dialog = nts.uk.ui.dialog;
  const getTextResource = nts.uk.resource.getText;

  @bean()
  export class KBT002JViewModel extends ko.ViewModel {
    isNewMode: KnockoutObservable<boolean> = ko.observable(false);
    aggrFrameCode: KnockoutObservable<any> = ko.observable(null);
    aggrPeriod: KnockoutObservable<any> = ko.observable(null);
    companyId: KnockoutObservable<any> = ko.observable(null);
    optionalAggrName: KnockoutObservable<any> = ko.observable(null);
    startDate: KnockoutObservable<any> = ko.observable(null);
    endDate: KnockoutObservable<any> = ko.observable(null);
    mounted() {
      const vm = this;
      const params = getShared('inputDialogJ');
      // 集計枠コード
      vm.aggrFrameCode = params.aggrFrameCode;
    }

    public search() {
      
    }

    /**
     * 任意集計期間の登録処理
     * Create AggrPeriodDto
    */
    public save() {
      const vm = this;
      const aggrPeriodDto: AggrPeriodDto = new AggrPeriodDto({
        companyId: vm.aggrPeriod().companyId,
        aggrFrameCode: vm.aggrPeriod().aggrFrameCode,
        optionalAggrName: vm.aggrPeriod().optionalAggrName,
        startDate: vm.aggrPeriod().startDate,
        endDate: vm.aggrPeriod().endDate
      });
      vm.$blockui('grayout');
      vm.$validate()
        .then((valid: boolean) => {
          if (!valid) {
            return $.Deferred().reject();
          }
          // 「任意集計期間」の重複をチェックする
          const isExist = service.checkExistAggrPeriod(vm.aggrPeriod().companyId, vm.aggrPeriod().aggrFrameCode);
          if (isExist) {
            return vm.$dialog.info({ messageId: 'Msg_3' });
          }
          //ドメインモデル「任意集計期間」を登録する
          return service.createAggrPeriod(aggrPeriodDto);
        })
        // 情報メッセージ（ID：Msg_15）を表示する
        .then((response) => {
          vm.$blockui('clear');
          return vm.$dialog.info({ messageId: 'Msg_15' });
        })
        // 画面を閉じる
        .then(() => {
          nts.uk.ui.windows.setShared('KBT002_J_PARAMS', aggrPeriodDto);
          vm.$window.close();
        })
        .always(() => vm.$blockui('clear'));
    }

    /**
     * 任意集計期間の削除処理
     * Remove AggrPeriodDto
    */
    public remove() {
      let vm = this;
      vm.$blockui('grayout');
        dialog.confirm({ messageId: "Msg_18" }).ifYes(() => {

          service.removeAggrPeriod(vm.aggrPeriod().companyId, vm.aggrPeriod().aggrFrameCode).done(() => {
            dialog.info({ messageId: "Msg_16" }).then(() => {
              setShared('KBT002_J_PARAMS', { isSuccess: true });
              nts.uk.ui.windows.close();
            });
          }).fail(function(res) {
            dialog.alertError({ messageId: res.messageId });
            setShared('KBT002_J_PARAMS', { isSuccess: false });
          }).always(function() {
            vm.$blockui('clear')
          });
      }).then(() => {
      }).always(() => vm.$blockui('clear'));
    }

    /**
     * キャンセルボタン押下時処理
     * Close dialog
    */
    public closeDialog() {
      const vm = this;
      // 閉じるの確認メッセージ => キャンセルの確認メッセージ
      vm.$dialog.confirm({ messageId: "Msg_19" })
        .then((result: 'no' | 'yes' | 'cancel') => {
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

  /**
  * The Class AggrPeriodDto.
  * 	任意集計期間
  */
  export class AggrPeriodDto {
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