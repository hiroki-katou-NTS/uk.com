/// <reference path='../../../../lib/nittsu/viewcontext.d.ts' />

module nts.uk.at.view.kbt002.j {
  import setShared = nts.uk.ui.windows.setShared;
  import getShared = nts.uk.ui.windows.getShared;
  import dialog = nts.uk.ui.dialog;
  const getTextResource = nts.uk.resource.getText;

  @bean()
  export class KBT002JViewModel extends ko.ViewModel {
    isNewMode: KnockoutObservable<boolean> = ko.observable(true);
    aggrFrameCode: KnockoutObservable<any> = ko.observable(null);
    aggrPeriod: KnockoutObservable<any> = ko.observable(null);
    companyId: KnockoutObservable<any> = ko.observable(null);
    optionalAggrName: KnockoutObservable<any> = ko.observable(null);
    startDate: KnockoutObservable<any> = ko.observable(null);
    endDate: KnockoutObservable<any> = ko.observable(null);
    dateValue: KnockoutObservable<any> = ko.observable(null);

    aggrPeriodItem: KnockoutObservable<AggrPeriodModel> = ko.observable(new AggrPeriodModel(null));
    aggrPeriodList: KnockoutObservableArray<any> = ko.observableArray([]);
    selectedAggrFrameCd: KnockoutObservable<string> = ko.observable('');
    refDate: KnockoutObservable<string> = ko.observable('');
    execScopeCls: KnockoutObservable<number> = ko.observable(null);
  
    mounted() {
      const vm = this;
      vm.findAll();
      vm.selectedAggrFrameCd.subscribe(aggrFrameCode => {
        vm.initProcExec();
        // set update mode
        let data = _.filter(vm.aggrPeriodList(), function(o) {
          return o.aggrFrameCode == aggrFrameCode ? o : null; });
        if (data[0]) {
          vm.createData(data[0]);
          vm.isNewMode(false);
        } else {
          vm.isNewMode(true);
        }
        setTimeout(function() { vm.focusInput(); }, 100);
        //self.currentExecItem().refDate(moment(new Date()).toDate());
        nts.uk.ui.errors.clearAll();
    });
    }

    createData(param: AggrPeriodDto) {
      const vm = this;
      vm.aggrFrameCode(param.aggrFrameCode || '');
      vm.optionalAggrName(param.optionalAggrName || '');
      vm.startDate(param.startDate || '');
      vm.endDate(param.endDate || '');
      vm.companyId(param.companyId || '');
    }

    private focusInput() {
      const vm = this;
      if (vm.isNewMode()) {
        $('#aggrFrameCode').focus();
      } else {
        $('#optionalAggrName').focus();
      }
    }

    private initProcExec() {
      const vm = this;
      nts.uk.ui.errors.clearAll();
      vm.aggrPeriodItem(new AggrPeriodModel(null));
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
      service.findAll()
      .then(function(res: AggrPeriodDto[]) {
        vm.aggrPeriodList(res);
        console.log(vm.aggrPeriodList())
      }).always(function() {
        vm.$blockui('clear');
      });
    }

    /**
     * 任意集計期間の登録処理
     * Create AggrPeriodDto
    */
    public save() {
      const vm = this;
      const aggrPeriodDto: AggrPeriodModel = new AggrPeriodModel({
        aggrFrameCode: vm.aggrFrameCode(),
        optionalAggrName: vm.optionalAggrName(),
        startDate: vm.startDate(),
        endDate: vm.endDate(),
        companyId: vm.companyId()
      });
      vm.$blockui('grayout');
      vm.$validate()
        .then((valid: boolean) => {
          if (!valid) {
            return $.Deferred().reject();
          } 
          if (vm.isNewMode()) {
            //ドメインモデル「任意集計期間」を登録する
            return service.createAggrPeriod(aggrPeriodDto)
          }
        })
        // 情報メッセージ（ID：Msg_15）を表示する
        .then(() => {
          vm.$blockui('clear');
          vm.findAll();
          return vm.$dialog.info({ messageId: 'Msg_15' })
        })
        .always(() => vm.$blockui('clear'));
    }

    /**
     * 任意集計期間の削除処理
     * Remove AggrPeriodDto
    */
    public remove() {
      let vm = this;
      dialog.confirm({ messageId: "Msg_18" }).ifYes(() => {
        vm.$blockui('grayout');
        service.removeAggrPeriod(vm.aggrFrameCode()).done(() => {
          dialog.info({ messageId: "Msg_16" }).then(() => {
            vm.findAll();
          });
        }).fail(function(res) {
          dialog.alertError({ messageId: res.messageId });
        }).always(function() {
          vm.$blockui('clear');
        });
      });
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
  export interface AggrPeriodDto {
    companyId: string,
    aggrFrameCode: number,
    optionalAggrName: number,
    startDate: string,
    endDate: string
  }

  export class AggrPeriodModel {
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