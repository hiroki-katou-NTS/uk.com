/// <reference path='../../../../lib/nittsu/viewcontext.d.ts' />
module nts.uk.at.view.ccg015.g.screenModel {
  const API = {
    getSetting: 'screen/com/ccg008/get-setting',
    save: 'screen/com/ccg008/save',
  };

  @bean()
  export class ViewModel extends ko.ViewModel {
    itemListCb: KnockoutObservableArray<ItemModel> = ko.observableArray([]);
    reloadInterval: KnockoutObservable<string> = ko.observable('0');
    cId: KnockoutObservable<string> = ko.observable('');

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
      const vm = this;
      $('#combo-box').focus();
      vm
        .$blockui('grayout')
        .then(() => vm.$ajax('com', API.getSetting))
        .then(setting => {
          if (setting && setting.reloadInterval) {
            vm.reloadInterval(setting.reloadInterval);
          }
        })
        .always(() => vm.$blockui('clear'));
    }

    onClickDecision(){
      const vm = this;
      const command: ToppageReloadSettingCommand = new ToppageReloadSettingCommand(vm.cId(), parseInt(vm.reloadInterval()));
      vm.$ajax('com', API.save, command)
      .then(()=> vm.$dialog.info({messageId: 'Msg_15'}))
      .then(() => vm.$window.close());
    }

    onClickCancel(){
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
