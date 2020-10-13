/// <reference path='../../../../lib/nittsu/viewcontext.d.ts' />
module nts.uk.com.view.ccg003.a {

  @bean()
  export class ViewModel extends ko.ViewModel {
    dateValue: KnockoutObservable<DatePeriod> = ko.observable(new DatePeriod({
      startDate: '',
      endDate: ''
    }));

    created() {
      
    }

    mounted() {
      $('#A2').click(() => this.$window.modal('/view/ccg/003/b/index.xhtml'));
    }
  }

  class DatePeriod {
    startDate: string;
    endDate: string;

    constructor(init?: Partial<DatePeriod>) {
      $.extend(this, init);
    }
  }
}