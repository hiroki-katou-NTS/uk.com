/// <reference path='../../../../lib/nittsu/viewcontext.d.ts' />
module nts.uk.at.view.ccg008.e.screenModel {

  @bean()
  export class ViewModel extends ko.ViewModel {
    itemListCb: KnockoutObservableArray<ItemModel> = ko.observableArray([]);
    selectedCode: KnockoutObservable<string> = ko.observable('0');
    isEnable: KnockoutObservable<boolean> = ko.observable(true);
    isEditable: KnockoutObservable<boolean> = ko.observable(false);
    isRequired: KnockoutObservable<boolean> = ko.observable(false);
    selectFirstIfNull: KnockoutObservable<boolean> = ko.observable(false);
    cId: KnockoutObservable<string> = ko.observable('');
    created(){
      const vm = this;
      const data = nts.uk.ui.windows.getShared('DataFromScreenA');
      vm.selectedCode(data.toString());
      vm.itemListCb = ko.observableArray([
        new ItemModel('0', ''),
        new ItemModel('1', '1'),
        new ItemModel('2', '5'),
        new ItemModel('3', '10'),
        new ItemModel('4', '20'),
        new ItemModel('5', '30'),
        new ItemModel('6', '40'),
        new ItemModel('7', '50'),
        new ItemModel('8', '60')
    ]);
    }

    onClickDecision(){
      const vm = this;
      const command: ToppageReloadSettingCommand = new ToppageReloadSettingCommand(vm.cId(), parseInt(vm.selectedCode()));
      vm.$ajax('com','screen/com/ccg008/save',command).then(()=> {
        nts.uk.ui.windows.setShared('DataFromScreenE',parseInt(vm.selectedCode()));
        this.$window.close();
      });
    }
    onClickCancel(){
      const vm = this;
      nts.uk.ui.windows.setShared('DataFromScreenE',parseInt(vm.selectedCode()));
      this.$window.close();
    }
  }

  export class ItemModel {
    code: string;
    name: string;

    constructor(code: string, name: string) {
        this.code = code;
        this.name = name;
    }
  }

  export class ToppageReloadSettingCommand {
    cId: string;
    reloadInteval: number;
    constructor(cId: string, reloadInteval: number) {
      this.cId = cId;
      this.reloadInteval = reloadInteval;
  }
  }
}
