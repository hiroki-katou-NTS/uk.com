/// <reference path='../../../../../lib/nittsu/viewcontext.d.ts' />
module nts.uk.at.view.kmt010.a.tabs.work.viewmodel {

  @component({
    name: 'kmt010-a-work',
    template: ``
  })

  class WorkTab extends ko.ViewModel {
    isNew: ko.KnockoutObservable<boolean> = ko.observable(true);
    checkAll: ko.KnockoutObservable<boolean> = ko.observable(false);
    isAllowRemove: ko.KnockoutObservable<boolean> = ko.observable(false);
    checkConditionList: KnockoutObservableArray<ListItem> = ko.observableArray([]);
    dataGetShare: KnockoutObservable<any> = ko.observable({});
    constructor(params: any) {
      super();
    }
    
    created(params: any) {
      const vm = this;      
    }
  }

  export class ListItem {
    checked: KnockoutObservable<boolean> = ko.observable(false);
    rowId: number;
    name_code: string;
    expiration: string;

    constructor(checked: boolean, rowId: number, name_code: string, expiration: string) {
      this.checked(checked);
      this.rowId = rowId;
      this.name_code = name_code;
      this.expiration = expiration;
    }
  }
}