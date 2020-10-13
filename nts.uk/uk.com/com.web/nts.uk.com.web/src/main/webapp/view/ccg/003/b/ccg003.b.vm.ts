/// <reference path='../../../../lib/nittsu/viewcontext.d.ts' />
module nts.uk.com.view.ccg003.b {

  @bean()
  export class ViewModel extends ko.ViewModel {
    dateValue: KnockoutObservable<DatePeriod> = ko.observable(new DatePeriod({
      startDate: '',
      endDate: ''
    }));

    itemList: KnockoutObservableArray<ItemModel> = ko.observableArray([
      new ItemModel({id: '1', ymDisplay: '11/11 - 11/12', content: 'a'}),
      new ItemModel({id: '1', ymDisplay: '1', content: 'a'}),
      new ItemModel({id: '1', ymDisplay: '1', content: 'a'}),
      new ItemModel({id: '1', ymDisplay: '1', content: 'a'}),
      new ItemModel({id: '1', ymDisplay: '1', content: 'a'}),
      new ItemModel({id: '1', ymDisplay: '1', content: 'a'}),
      new ItemModel({id: '1', ymDisplay: '1', content: 'a'}),
      new ItemModel({id: '1', ymDisplay: '1', content: 'a'}),
      new ItemModel({id: '1', ymDisplay: '1', content: 'a'}),
    ]);
    
    created() {}

    openScreenC(): void {
      const vm = this;
      vm.$window.modal('/view/ccg/003/c/index.xhtml');
    }
  }

  class DatePeriod {
    startDate: string;
    endDate: string;

    constructor(init?: Partial<DatePeriod>) {
      $.extend(this, init);
    }
  }

  class ItemModel {
    id: string;
    ymDisplay: string;
    content: string;

    constructor(init?: Partial<ItemModel>) {
      $.extend(this, init);
    }
}
}