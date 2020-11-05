/// <reference path='../../../../lib/nittsu/viewcontext.d.ts' />
module nts.uk.at.view.ccg008.e.screenModel {

  @bean()
  export class ViewModel extends ko.ViewModel {
    itemListCb: KnockoutObservableArray<ItemModel> = ko.observableArray([]);
    selectedCode: KnockoutObservable<string> = ko.observable('');
    isEnable: KnockoutObservable<boolean> = ko.observable(true);
    isEditable: KnockoutObservable<boolean> = ko.observable(false);
    isRequired: KnockoutObservable<boolean> = ko.observable(false);
    selectFirstIfNull: KnockoutObservable<boolean> = ko.observable(false);
    cId: KnockoutObservable<string> = ko.observable(__viewContext.user.companyId);
    created(){
      const vm = this;
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
    mounted(){

    }
    onClickDecision(){
      const vm = this;
      let command: ToppageReloadSettingCommand = new ToppageReloadSettingCommand(vm.cId(), parseInt(vm.selectedCode()));
      vm.$ajax('com','screen/com/ccg008/save',command).then(()=> {
        this.$window.close()
        nts.uk.ui.windows.setShared('DataFromScreenE',parseInt(vm.selectedCode()));
      })
    }
    onClickCancel(){
      this.$window.close()
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
