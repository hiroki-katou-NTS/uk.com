/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kml002.g {

  @bean()
  class ViewModel extends ko.ViewModel {
    selectableItems: KnockoutObservableArray<ItemModel> = ko.observableArray([]);
    currentCodeListSwap: KnockoutObservableArray<ItemModel> = ko.observableArray([]);

    columns: KnockoutObservableArray<nts.uk.ui.NtsGridListColumn>;

    constructor(params: any) {
      super();
      const vm = this;
      vm.columns = ko.observableArray([
        { headerText: vm.$i18n('KML002_111'), key: 'code', width: 50, formatter: _.escape },
        { headerText: vm.$i18n('KML002_112'), key: 'name', width: 100, formatter: _.escape },
      ]);

      vm.createSelectableItems();


      vm.$window.storage('KWL002_SCREEN_G_INPUT').then((data) => {
        if (typeof data != 'undefined') vm.currentCodeListSwap(data);
      });
    }

    created(params: any) {
      const vm = this;
      //_.extend(window, { vm });
    }

    mounted() {
      const vm = this;

      $('#swapList-gridArea1').attr('tabindex', '-1').focus();
    }

    closeDialog() {
      const vm = this;
      vm.$window.close();
    }

    createSelectableItems() {
      const vm = this;
      var array = [];
      for (let i = 1; i < 21; i++) {
        array.push(new ItemModel(i, 'な項目 ' + i));
      }

      vm.selectableItems(array);
    }

    proceed() {
      const vm = this;

      //G4_1「選択可能な項目」で項目はなにもない場合。
      if (vm.selectableItems().length <= 0 && vm.currentCodeListSwap().length <= 0) {
        vm.$dialog.error({ messageId: 'Msg_37' }).then(() => {
          vm.$window.close();
        })
        return;
      }

      if (vm.currentCodeListSwap().length > 10 || vm.currentCodeListSwap().length <= 0) {
        let msgId = vm.currentCodeListSwap().length > 10 ? 'Msg_1837' : 'Msg_1817';
        //「選択された対象項目」で回数集計項目は10項目以上に選択られた。 > 10
        //「選択された対象項目」でなにもない。 = 0
        vm.$dialog.error({ messageId: msgId }).then(() => {
          $('#swapList-gridArea2').attr('tabindex', '-1').focus();
        })
        return;
      }
      //エラーがない場合 - 回数集計情報を登録する
      vm.$window.storage('KWL002_SCREEN_G_OUTPUT', vm.currentCodeListSwap()).then(() => {
        vm.$dialog.info({ messageId: 'Msg_15' }).then(() => {
          vm.$window.close();
        });
      });
    }
  }

  export class ItemModel {
    code: number;
    name: string;
    constructor(code: number, name: string) {
      this.code = code;
      this.name = name;
    }
  }
}