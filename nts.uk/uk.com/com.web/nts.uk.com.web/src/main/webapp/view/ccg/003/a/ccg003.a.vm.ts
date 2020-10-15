/// <reference path='../../../../lib/nittsu/viewcontext.d.ts' />
module nts.uk.com.view.ccg003.a {

  @bean()
  export class ViewModel extends ko.ViewModel {
    dateValue: KnockoutObservable<DatePeriod> = ko.observable(new DatePeriod({
      startDate: '',
      endDate: ''
    }));

    demoText1: string = 
      `今週の金曜日はボーリング大会がありますので、みなさんのご参加をお待ちしています。<br>
      場所：　●●ボウル<br>
      URL：<a href="#">http://jhvnsnvjskv.co.jp</a><br>
      時間：19:00～20:30<br>
      優勝チームには賞品が出ます。<br>
      ボーリングが終わった後は恒例の食事会です。<br>
      記載者　：日通　A子<br>
      表示期間：7/5～7/10`

    dataText2: string =
      `娘の誕生日が近づいているので、仕事を早めに繰り上げて、プレゼントを購入することを忘れずにすること。<br>
      記念日：6/10`

    created() {}

    openScreenB(): void {
      this.$window.modal('/view/ccg/003/b/index.xhtml');
    }

    closeWindow(): void {
      $('.contents').closest('iframe').attr('id');
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