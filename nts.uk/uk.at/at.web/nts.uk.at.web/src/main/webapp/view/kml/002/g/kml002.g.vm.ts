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