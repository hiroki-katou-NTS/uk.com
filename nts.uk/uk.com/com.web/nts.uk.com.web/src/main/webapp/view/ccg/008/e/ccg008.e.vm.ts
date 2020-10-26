/// <reference path='../../../../lib/nittsu/viewcontext.d.ts' />
module nts.uk.at.view.kdl048.screenModel {
  @bean()
  export class ViewModel extends ko.ViewModel {
    itemListCb: KnockoutObservableArray<ItemModel> = ko.observableArray([]);
    selectedCode: KnockoutObservable<string> = ko.observable('');
    isEnable: KnockoutObservable<boolean> = ko.observable(true);
    isEditable: KnockoutObservable<boolean> = ko.observable(false);
    isRequired: KnockoutObservable<boolean> = ko.observable(false);
    selectFirstIfNull: KnockoutObservable<boolean> = ko.observable(false); 
    created(){
      const vm = this;
      vm.itemListCb = ko.observableArray([
        new ItemModel('1', ''),
        new ItemModel('2', ''),
        new ItemModel('3', '')
    ]);
    }
    mounted(){

    }
    onClickDecision(){
      this.$window.close()
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
}