
/// <reference path='../../../../lib/nittsu/viewcontext.d.ts' />
module nts.uk.pr.view.ccg015.c {
  @bean()
  export class ViewModel extends ko.ViewModel {
    parentTopPageCode: KnockoutObservable<string> = ko.observable(null);
    parentTopPageName: KnockoutObservable<string> = ko.observable(null);
    parentLayoutId: KnockoutObservable<number> = ko.observable(0);
    newTopPageCode: KnockoutObservable<string> = ko.observable("");
    newTopPageName: KnockoutObservable<string> = ko.observable("");
    isDuplicateCode: KnockoutObservable<boolean> = ko.observable(false);
    check: KnockoutObservable<boolean> = ko.observable(false);

    created(params: any) {
      const vm = this;
      if (params && params.topPageCode) {
        vm.parentTopPageCode = ko.observable(params.topPageCode);
      }
      if (params && params.topPageName) {
        vm.parentTopPageName = ko.observable(params.topPageName);
      }
      if (params && params.layoutDisp) {
        vm.parentLayoutId(params.layoutDisp);
      }
    }

    mounted(): JQueryPromise<void> {
      const vm = this;
      let dfd = $.Deferred<void>();
      nts.uk.ui.windows.setShared("codeOfNewTopPage", vm.parentTopPageCode());
      dfd.resolve();
      return dfd.promise();
    }
    
    copyTopPage() {
      const vm = this;
      nts.uk.ui.windows.setShared("codeOfNewTopPage", vm.newTopPageCode());
      let data: service.TopPageDto = {
        topPageCode: vm.newTopPageCode(),
        topPageName: vm.newTopPageName(),
        layoutDisp: vm.parentLayoutId(),
        isCheckOverwrite: vm.check(),
        copyCode: vm.parentTopPageCode()
      };
      service.copyTopPage(data).done(function () {
        nts.uk.ui.dialog.info({ messageId: "Msg_20" }).then(function () {
          nts.uk.ui.windows.close();
        });
      }).fail(function (res: any) {
        $("#inp-code").focus();
        vm.$dialog.alert({ messageId: res.messageId, messageParams: res.parameterIds });
      });
    }
  }
}