/// <reference path='../../../../lib/nittsu/viewcontext.d.ts' />

module nts.uk.at.view.kbt002.k {

  const API = {
    exportCSV: "screen/at/outputexechistory/exportCSV",
  };

  const DATE_FORMAT = 'YYYY/MM/DD';

  @bean()
  export class KBT002KViewModel extends ko.ViewModel {
    empNames: KnockoutObservableArray<any> = ko.observableArray([]);

    selectEmpName: KnockoutObservable<number> = ko.observable(1);
    dateValue: KnockoutObservable<any> = ko.observable({});
    startDateString: KnockoutObservable<string> = ko.observable('');
    endDateString: KnockoutObservable<string> = ko.observable('');

    created() {
      const vm = this;
      let today = moment().utc();
      let currentDate = today.format(DATE_FORMAT);

      vm.dateValue().startDate = currentDate;
      vm.dateValue().endDate = currentDate;

      vm.empNames = ko.observableArray([
        { code: 1, name: vm.$i18n('KBT002_275') },
        { code: 2, name: vm.$i18n('KBT002_276') }
      ]);

      vm.startDateString.subscribe((value) => {
        vm.dateValue().startDate = value;
        vm.dateValue.valueHasMutated();
      });

      vm.endDateString.subscribe((value) => {
        vm.dateValue().endDate = value;
        vm.dateValue.valueHasMutated();
      });
    }

    getDataExportCsv() {
      const vm = this;
      vm.$validate().then((valid: boolean) => {
        if (valid) {
          const command: GetDataExportCSVModel = new GetDataExportCSVModel({
            isExportEmployeeName: vm.selectEmpName() === 2,
            startDate: moment.utc(vm.dateValue().startDate, DATE_FORMAT).toISOString(),
            endDate: moment.utc(vm.dateValue().endDate, DATE_FORMAT).toISOString()
          });
    
          vm.$blockui('grayout');
          nts.uk.request.exportFile(API.exportCSV, command)
            .done(res => console.log(res))
            .fail(err => vm.$dialog.info({ messageId: err.messageId }))
            .always(() => vm.$blockui('clear'));
        }
      });
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