/// <reference path='../../../../lib/nittsu/viewcontext.d.ts' />
module nts.uk.at.view.kbt002.k {

  const API = {
    exportCSV: "at/function/outputexechistory/exportCSV",
  };

  @bean()
  export class KBT002KViewModel extends ko.ViewModel {
    empNames: KnockoutObservableArray<any> = ko.observableArray([]);

    selectEmpName: KnockoutObservable<number> = ko.observable(2);
    startDate: KnockoutObservable<string> = ko.observable('');
    endDate: KnockoutObservable<string> = ko.observable('');

    created() {
      const vm = this;
      let today = moment();
      let currentDate = today.format("YYYY/MM/DD");
      
      vm.startDate(currentDate);
      vm.endDate(currentDate);

      vm.empNames = ko.observableArray([
        { code: 1, name: vm.$i18n('KBT002_275') },
        { code: 2, name: vm.$i18n('KBT002_276') }
      ]);
    }

    getDataExportCsv() {
      const vm = this;
      const command: GetDataExportCSVModel = new GetDataExportCSVModel({
        isExportEmployeeName: vm.selectEmpName() === 1 ? true : false,
        startDate: moment.utc(vm.startDate(), 'YYYY/MM/DD').toISOString(),
        endDate: moment.utc(vm.endDate(), 'YYYY/MM/DD').toISOString()
      });

      vm.$blockui('grayout');
      vm.$ajax(API.exportCSV)
        .then((data: any) => {
          vm.$blockui('clear');
        })
        .always(() => {
          vm.$blockui('clear');
        })
    }

    closeDialog() {
      const vm = this;
      vm.$window.close();
    }
  }

  export class GetDataExportCSVModel {
    isExportEmployeeName: boolean;
    startDate: string;
    endDate: string;
    constructor(init?: Partial<GetDataExportCSVModel>) {
      $.extend(this, init)
    }
  }
}