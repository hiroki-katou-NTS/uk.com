/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.com.view.ccg034.a {

  import getText = nts.uk.resource.getText;

  // URL API backend
  const API = {
    getFlowMenuList: "sys/portal/flowmenu/getFlowMenu",
    getFlowMenu: "sys/portal/flowmenu/getFlowMenu/{0}",
    register: "sys/portal/flowmenu/register",
    update: "sys/portal/flowmenu/update",
    delete: "sys/portal/flowmenu/delete",
    copy: "sys/portal/flowmenu/copy",
  }

  @bean()
  export class ScreenModel extends ko.ViewModel {
    constraints: KnockoutObservableArray<string> = ko.observableArray(['TopPagePartCode', 'TopPagePartName']);
    toppagePartCode: KnockoutObservable<string> = ko.observable(null);
    toppagePartName: KnockoutObservable<string> = ko.observable(null);
    flowMenuList: KnockoutObservableArray<FlowMenuModel> = ko.observableArray([]);
    flowMenuColumns: any = [
      { headerText: getText('CCG034_21'), key: 'flowMenuCode', width: '50px' },
      { headerText: getText('CCG034_22'), key: 'flowMenuName', width: '250px' }
    ];
    selectedFlowMenuId: KnockoutObservable<string> = ko.observable('');
    selectedFlowMenu: KnockoutObservable<FlowMenuModel> = ko.observable(null);
    isNewMode: KnockoutObservable<boolean> = ko.observable(true);

    mounted() {
      // Code xử lý lúc khởi động màn hình
      const vm = this;
      vm.getFlowMenuList();

      vm.selectedFlowMenuId.subscribe(value => vm.selectFlowMenu());
    }

    public test() {

    }

    public refreshNew() {
      const vm = this;
      vm.selectedFlowMenuId('');
      vm.toppagePartCode('');
      vm.toppagePartName('');
      vm.isNewMode(true);
    }

    public register() {
      const vm = this;
      vm.$validate().done((valid: boolean) => {
        if (valid) {
          let action: JQueryPromise<any>;
          vm.$blockui("grayout");
          if (vm.isNewMode()) {
            action = vm.performRegister();
          } else {
            action = vm.performUpdate();
          }
          action.always(() => vm.$blockui("clear"));
        }
      })
    }

    private performRegister(): JQueryPromise<void> {
      const vm = this;
      return vm.$ajax(API.register, { flowMenuCode: vm.toppagePartCode(), flowMenuName: vm.toppagePartName() })
        .done(() => {
          vm.flowMenuList.push({ flowMenuCode: vm.toppagePartCode(), flowMenuName: vm.toppagePartName() });
          vm.flowMenuList.valueHasMutated();
          vm.selectedFlowMenuId(vm.toppagePartCode());
          vm.$dialog.info({ messageId: 'Msg_15' });
        })
        .fail((err) => vm.$dialog.error({ messageId: err.messageId }));
    }

    private performUpdate(): JQueryPromise<void> {
      const vm = this;
      return vm.$ajax(API.register, { flowMenuCode: vm.toppagePartCode(), flowMenuName: vm.toppagePartName() })
        .done(() => vm.$dialog.info({ messageId: 'Msg_15' }))
        .fail((err) => vm.$dialog.error({ messageId: err.messageId }));
    }

    public openDialogB() {

    }

    public openDialogD() {
      const vm = this;
      const params = {};
      vm.$window.modal('/view/ccg/034/d/index.xhtml', params, {
        height: 1000,
        width: 550,
        resizable: true,
      });
    }

    private getFlowMenuList() {
      const vm = this;
      vm.$blockui("grayout");
      vm.$ajax(API.getFlowMenuList).done((res: Map<string, string>) => {
        const arr: FlowMenuModel[] = [];
        _.forEach(res, (value, key) => { arr.push({ flowMenuCode: key, flowMenuName: value }) });
        vm.flowMenuList(arr);
      }).always(() => vm.$blockui("clear"));
    }

    private selectFlowMenu() {
      const vm = this;
      vm.$blockui("grayout");
      vm.isNewMode(false);
      const path = nts.uk.text.format(API.getFlowMenu, vm.selectedFlowMenuId());
      vm.$ajax(path)
        .done((res: FlowMenuModel) => {
          vm.toppagePartCode(res.flowMenuCode);
          vm.toppagePartName(res.flowMenuName);
          vm.selectedFlowMenu(res);
        }).always(() => vm.$blockui("clear"));
    }

  }

  export class FlowMenuModel {
    flowMenuCode: string;
    flowMenuName: string;
    fileId?: string;
    arrowSettings?: any[];
    fileAttachmentSettings?: any[];
    imageSettings?: any[];
    labelSettings?: any[];
    linkSettings?: any[];
    menuSettings?: any[];
  }

}