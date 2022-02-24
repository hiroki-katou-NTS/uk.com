/// <reference path='../../../../lib/nittsu/viewcontext.d.ts' />

module nts.uk.com.view.cmm030.c {

  const API = {
    getApprovalRootLastStartDate: "screen/com/cmm030/getApprovalRootLastStartDate/{0}"
  };

  @bean()
  export class ScreenModel extends ko.ViewModel {
    sid: string;
    startDate: KnockoutObservable<string> = ko.observable("");
    transferList: KnockoutObservableArray<any> = ko.observableArray([]);
    selectedTransfer: KnockoutObservable<number> = ko.observable(0);

    created(params?: any): void {
      const vm = this;
      vm.sid = params.sid;
      vm.transferList([
        { id: 0, name: vm.$i18n("CMM030_55") },
        { id: 1, name: vm.$i18n("CMM030_56") }
      ]);
    }

    public processSave() {
      const vm = this;
      vm.$blockui("grayout");
      vm.getApprovalRootLastStartDate().then(isSuccess => {
        if (!isSuccess) {
          vm.$dialog.error({ messageId: "Msg_3296" });
        } else {
          const result = {
            startDate: vm.startDate(),
            transfer: vm.selectedTransfer()
          };
          vm.$window.close(result);
        }
      }).always(() => vm.$blockui("clear"));
    }

    public closeDialog() {
      const vm = this;
      vm.$window.close();
    }

    /**
     * Ｃ：承認ルートの最終開始日を取得する
     */
    private getApprovalRootLastStartDate(): JQueryPromise<boolean> {
      const vm = this;
      return vm.$ajax(nts.uk.text.format(API.getApprovalRootLastStartDate, vm.sid))
      .then(result => {
        if (!_.isNil(result)) {
          return moment.utc(vm.startDate()).isAfter(result);
        }
        return true;
      });
    }
  }
}