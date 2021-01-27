/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.ksm007.c {

  const FORMAT_DAY = 'YYYY/MM/DD';
  const PATH = {
    saveData: ''
  }

  @bean()
  class ViewModel extends ko.ViewModel {

    startFromDate: KnockoutObservable<string> = ko.observable(moment().add(+1, 'days').format(FORMAT_DAY));
    beginStartDate: KnockoutObservable<string> = ko.observable(moment().add(+1, 'days').format(FORMAT_DAY));
    inputScreenC: KnockoutObservable<any> = ko.observable(null);

    constructor(params: any) {
      super();
      const vm = this;

      vm.getDataFromScreenC(params);

    }

    created(params: any) {
      const vm = this;
    }

    mounted() {
      const vm = this;
      $('#startDate').focus();
    }

    closeDialog() {
      const vm = this;
      vm.$window.close({ isSave: false });
    }

    saveData() {
      const vm = this;
      let params = { ...vm.inputScreenC(), startDate: vm.startFromDate() };
      
      if( moment(vm.startFromDate()).format(FORMAT_DAY) >= '9999/12/31') {
        vm.$dialog.error({ messageId: 'Msg_917'}).then(() => {
          $('#startDate').focus();
        });
        return;
      }

      vm.$blockui('grayout');
      vm.$ajax(PATH.saveData, params).done(() => {
        vm.$window.close({ isSave: true });
        vm.$blockui('hide');
      }).fail(() => {
        vm.$window.close({ isSave: false });
        vm.$blockui('hide');
      });
    }

    getDataFromScreenC(params: any) {
      const vm = this;

      vm.inputScreenC(params);

      if (params) {
        let beginStartDate = moment(params.startDate).add(+1, 'days').format(FORMAT_DAY);
        vm.startFromDate(beginStartDate);
        vm.beginStartDate(beginStartDate);
      }
    }

  }
}