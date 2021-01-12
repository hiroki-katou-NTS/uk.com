/// <reference path='../../../../lib/nittsu/viewcontext.d.ts' />
module nts.uk.at.view.ktg026.test.viewmodel {

  @bean()
  export class ViewModel extends ko.ViewModel {
    placementsTopPage: KnockoutObservableArray<Placement> = ko.observableArray([]);

    created() {
      const vm = this;
      vm.placementsTopPage().push(new Placement());
    }

    openKtg026() {
      const vm = this;
      vm.$window.storage('KTG026_PARAM', {
        employeeId: "0117dcfc-200b-46e8-a734-d87f1d563b82",
        targetDate: "202002"
      })
      .then(() => vm.$window.modal('/view/ktg/026/a/superior.xhtml'));
    }
  }

  export class Placement {
    placementID: string;
    row: number;
    column: number;
    url: string;
    name: string;
    // TopPagePart info
    topPagePartID: string;
    html: string;
    constructor() {
        this.placementID = '01';
        this.name = '時間外労働・休日労働状況';
        this.row = 3;
        this.column = 3;
        this.url = origin + "/nts.uk.at.web/view/ktg/026/a/employee.xhtml?code=26";
        this.html = '<iframe src="' + this.url + '"/>';  
        this.topPagePartID = '1';
    }
  }
}

