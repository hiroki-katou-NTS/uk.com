/// <reference path='../../../../lib/nittsu/viewcontext.d.ts' />
module nts.uk.com.view.ccg003.c {

  @bean()
  export class ViewModel extends ko.ViewModel {
    messageText: KnockoutObservable<string> = ko.observable('');
    radioBoxSelectedId: KnockoutObservable<number> = ko.observable(2);
    workPlaceText: KnockoutObservable<string> = ko.observable('');
    employeeText: KnockoutObservable<string> = ko.observable('');
    dateValue: KnockoutObservable<DatePeriod> = ko.observable(new DatePeriod({
      startDate: '',
      endDate: ''
    }));
    
    isNewMode: KnockoutObservable<boolean> = ko.observable(false);
    isActiveDelete: KnockoutComputed<boolean> = ko.computed(() => !this.isNewMode());

    // ※C1
    isVisibleAllEmployees: KnockoutObservable<boolean> = ko.observable(true);
    // ※C2
    isActiveWorkplaceBtn: KnockoutComputed<boolean> = ko.computed(() => this.radioBoxSelectedId() === 2);
    // ※C3
    isActiveEmployeeBtn: KnockoutComputed<boolean> = ko.computed(() => this.radioBoxSelectedId() === 3);
    // ※C4, !※C5
    isVisibleWorkplaceList: KnockoutObservable<boolean> = ko.observable(true);
    // ※C6
    isVisibleDestination: KnockoutComputed<boolean> = ko.computed(() => this.isNewMode() || this.radioBoxSelectedId() === 3);

    created(isNewMode: boolean) {
      const vm = this;
      vm.isNewMode(isNewMode);
      $('#C1_2').focus();
    }

    mounted() {}

    closeWindow(): void {
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