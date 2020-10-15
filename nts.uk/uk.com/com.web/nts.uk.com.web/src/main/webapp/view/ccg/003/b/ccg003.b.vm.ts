/// <reference path='../../../../lib/nittsu/viewcontext.d.ts' />
module nts.uk.com.view.ccg003.b {

  @bean()
  export class ViewModel extends ko.ViewModel {
    dateValue: KnockoutObservable<DatePeriod> = ko.observable(new DatePeriod({
      startDate: '',
      endDate: ''
    }));

    itemList: KnockoutObservableArray<ItemModel> = ko.observableArray([
      new ItemModel({id: '1', ymDisplay: '11/11 - 11/12', content: 'Demo data to show in table'}),
      new ItemModel({id: '1', ymDisplay: '03/10 - 11/10', content: 'Demo data to show in table with limited labellllllllllllllllllllll'}),
      new ItemModel({id: '1', ymDisplay: '06/09 - 11/09', content: 'Demo data'}),
      new ItemModel({id: '1', ymDisplay: '03/08 - 11/08', content: 'Demo data'}),
      new ItemModel({id: '1', ymDisplay: '03/07 - 08/07', content: 'Demo data'}),
      new ItemModel({id: '1', ymDisplay: '04/06 - 11/06', content: 'Demo data'})
    ]);
    
    created() {
      $('#B20_1').focus();
    }

    openScreenC(): void {
      const vm = this;
      vm.$window.modal('/view/ccg/003/c/index.xhtml', true);
    }

    closeDialog(): void {
      this.$window.close();
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