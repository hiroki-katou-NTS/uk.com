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
    }

    created(params: any) {
      const vm = this;
      _.extend(window, { vm });
    }

    mounted(params: any) {
      const vm = this;
    }

    closeDialog() {
      const vm = this;
      vm.$window.close();
    }

    createSelectableItems() {
      const vm = this;
      var array = [];
      for( let i= 1; i < 21; i++) {
        array.push( new  ItemModel(i, 'な項目 ' + i));
      }

      vm.selectableItems(array);
    }

    proceed() {
      const vm = this;

      if( vm.currentCodeListSwap().length > 10 || vm.currentCodeListSwap().length <= 0) {
        let msgId = vm.currentCodeListSwap().length > 10 ? 'Msg_1837' : 'Msg_1817';
        //「選択された対象項目」で回数集計項目は10項目以上に選択られた。 > 10
        //「選択された対象項目」でなにもない。 = 0
        vm.$dialog.error({messageId: msgId}).then(() => {
          $('#swapList-gridArea2').attr('tabindex', '-1').focus();
        })
        return;
      }

      //G4_1「選択可能な項目」で項目はなにもない場合。
      if( vm.selectableItems().length <= 0) {
        vm.$dialog.error({messageId: 'Msg_37'}).then(() => {
          vm.$window.close();
        })
      }

      
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