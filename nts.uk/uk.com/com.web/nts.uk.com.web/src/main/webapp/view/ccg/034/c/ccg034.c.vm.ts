/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.com.view.ccg034.c {
  import getText = nts.uk.resource.getText;

  // URL API backend
  const API = {
    getFlowMenu: "sys/portal/flowmenu/getFlowMenu/{0}",
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
                vm.closeDialog();
              }

              if (result === 'yes') {
                vm.$blockui("grayout");
                vm.performDuplicate()
                  .always(() => {
                    vm.$blockui("clear");
                  });
              }
            })
          } else {
            vm.$blockui("grayout");
            vm.performFindFlowMenu()
              .then(res => {
                if (res) {
                  vm.$dialog.error({ messageId: "Msg_3" })
                    .then(() => {
                      vm.$blockui("clear");
                      vm.closeDialog();
                    });
                } else vm.performDuplicate();
              })
              .always(() => {
                vm.$blockui("clear");
              });
          }
        }
      });
    }

    public closeDialog(data: FlowMenuModel = null) {
      const vm = this;
      vm.$window.close(data);
    }

    private performDuplicate(): JQueryPromise<any> {
      const vm = this;
      let newFlowMenu: FlowMenuModel = new FlowMenuModel();
      newFlowMenu = _.cloneDeep(vm.flowMenu);
      newFlowMenu.flowMenuCode = vm.flowMenuCode();
      newFlowMenu.flowMenuName = vm.flowMenuName();
      vm.deleteUnknownData(newFlowMenu);
      return vm.$ajax(API.duplicate, { flowMenuCode: vm.flowMenuCode(), createFlowMenu: newFlowMenu })
        .then((res: string[]) => {
          _.forEach(res, fileId => (nts.uk.request as any).file.remove(fileId));
          vm.$dialog.info({ messageId: "Msg_15" })
            .then(() => vm.closeDialog(newFlowMenu));
        })
        .fail(err => vm.$dialog.error({ messageId: err.messageId }));
    }

    private performFindFlowMenu(): JQueryPromise<any> {
      const vm = this;
      return vm.$ajax(nts.uk.text.format(API.getFlowMenu, vm.flowMenuCode()));
    }

    private deleteUnknownData(flowMenu: any) {
      delete flowMenu.arrowSettings;
      delete flowMenu.fileAttachmentSettings;
      delete flowMenu.imageSettings;
      delete flowMenu.labelSettings;
      delete flowMenu.linkSettings;
      delete flowMenu.menuSettings;
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