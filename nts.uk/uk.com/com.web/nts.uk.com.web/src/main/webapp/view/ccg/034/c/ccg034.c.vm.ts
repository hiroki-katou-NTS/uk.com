/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.com.view.ccg034.c {
  import getText = nts.uk.resource.getText;

  // URL API backend
  const API = {
    duplicate: "sys/portal/flowmenu/copy"
  }

  @bean()
  export class ScreenModel extends ko.ViewModel {
    flowMenu: FlowMenuModel;
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
      vm.$validate().then((valid: boolean) => {
        if (valid) {
          if (vm.isChecked()) {
            vm.$dialog.confirm({ messageId: 'Msg_64' }).then((result: 'no' | 'yes' | 'cancel') => {
              if (result === 'no') {
                vm.closeDialog(null);
              }

              if (result === 'yes') {
                // CALL API
                vm.$blockui("grayout");
                vm.performDuplicate(true)
                  .always(() => {
                    vm.$blockui("clear");
                  });
              }
            })
          } else {
            vm.$blockui("grayout");
            vm.performDuplicate(false)
              .always(() => {
                vm.$blockui("clear");
              });
          }
        }
      });
    }

    public closeDialog(data: FlowMenuModel) {
      const vm = this;
      vm.$window.close(data);
    }

    private performDuplicate(isClone: boolean): JQueryPromise<any> {
      const vm = this;
      let newFlowMenu: FlowMenuModel = new FlowMenuModel();
      if (isClone) {
        newFlowMenu = _.cloneDeep(vm.flowMenu);
      }
      newFlowMenu.flowMenuCode = vm.flowMenuCode();
      newFlowMenu.flowMenuName = vm.flowMenuName();
      return vm.$ajax(API.duplicate, { flowMenuCode: vm.flowMenu.flowMenuCode, createFlowMenu: newFlowMenu })
        .then(() => {
          if (vm.flowMenu.fileId) {
            (nts.uk.request as any).file.remove(vm.flowMenu.fileId);
          }
          vm.$dialog.info({ messageId: "Msg_15" })
            .then(() => vm.closeDialog(newFlowMenu));
        })
        .fail(err => vm.$dialog.error({ messageId: err.messageId })); 
    }
  }

  export class FlowMenuModel {
    flowMenuCode: string;
    flowMenuName: string;
    fileId?: string;
    menuData?: nts.uk.com.view.ccg034.d.MenuSettingDto[];
    labelData?: nts.uk.com.view.ccg034.d.LabelSettingDto[];
    linkData?: nts.uk.com.view.ccg034.d.LinkSettingDto[];
    fileAttachmentData?: nts.uk.com.view.ccg034.d.FileAttachmentSettingDto[];
    imageData?: nts.uk.com.view.ccg034.d.ImageSettingDto[];
    arrowData?: nts.uk.com.view.ccg034.d.ArrowSettingDto[];
  }
}