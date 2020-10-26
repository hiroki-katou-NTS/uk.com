/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.com.view.ccg034.c {
  import getText = nts.uk.resource.getText;

  // URL API backend
  const API = {
    duplicate: "sys/portal/standardmenu/copy"
  }

  @bean()
  export class ScreenModel extends ko.ViewModel {
    flowMenu: nts.uk.com.view.ccg034.a.FlowMenuModel;
    flowMenuInfo: KnockoutObservable<string> = ko.observable('');
    flowMenuCode: KnockoutObservable<string> = ko.observable('');
    flowMenuName: KnockoutObservable<string> = ko.observable('');
    isChecked: KnockoutObservable<boolean> = ko.observable(false);

    created(params: any) {
      const vm = this;
      vm.flowMenu = params;
      vm.flowMenuInfo(vm.flowMenu.flowMenuCode + " " + vm.flowMenu.flowMenuName);
    }

    public duplicate() {
      const vm = this;
      if (vm.isChecked()) {
        vm.$dialog.confirm({ messageId: 'Msg_64' }).then((result: 'no' | 'yes' | 'cancel') => {
          if (result === 'no') {
            vm.closeDialog();
          }

          if (result === 'yes') {
            // CALL API
            vm.closeDialog();
          }
        })
      } else {
        // CALL API
        vm.closeDialog();
      }
    }

    public closeDialog() {
      const vm = this;
      vm.$window.close();
    }
  }
}