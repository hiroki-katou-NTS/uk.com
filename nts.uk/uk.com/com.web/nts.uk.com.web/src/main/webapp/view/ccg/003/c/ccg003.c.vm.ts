/// <reference path='../../../../lib/nittsu/viewcontext.d.ts' />
module nts.uk.com.view.ccg003.c {

  @bean()
  export class ViewModel extends ko.ViewModel {
    messageText: KnockoutObservable<string> = ko.observable('');
    radioBoxItemList: KnockoutObservableArray<any> = ko.observableArray([
      new BoxModel(1, this.$i18n('CCG003_22')),
      new BoxModel(2, this.$i18n('CCG003_23')),
      new BoxModel(3, this.$i18n('CCG003_24'))
    ]);
    radioBoxSelectedId: KnockoutObservable<number> = ko.observable(1);
    workPlaceText: KnockoutObservable<string> = ko.observable('');
    employeeText: KnockoutObservable<string> = ko.observable('');
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
    
    created() {
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

  class BoxModel {
    id: number;
    name: string;
    constructor(id: number, name: string){
        var self = this;
        self.id = id;
        self.name = name;
    }
}