/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.ksm007.c {

  const FORMAT_DAY = 'YYYY/MM/DD';
  const END_DAY = '9999/12/31';
  const PATH = {
    saveData: 'bs/schedule/employeeinfo/workplacegroup/addhospitalbusinessofficehist'
  }

  @bean()
  class ViewModel extends ko.ViewModel {

    startFromDate: KnockoutObservable<string> = ko.observable(moment().add(+1, 'days').format(FORMAT_DAY));
    beginStartDate: KnockoutObservable<string> = ko.observable(moment().add(+1, 'days').format(FORMAT_DAY));
    inputScreenC: KnockoutObservable<any> = ko.observable(null);

    constructor(params: any) {
      super();
      const vm = this;

      vm.getDataFromScreenB(params);

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
            
      if( moment(vm.startFromDate()).format(FORMAT_DAY) >= END_DAY) {
        vm.$dialog.error({ messageId: 'Msg_917'}).then(() => {
          $('#startDate').focus();
        });
        return;
      }

      vm.$blockui('grayout');

      let params = {
        workplaceGroupId: vm.inputScreenC().wpGroupId,
        nightShiftOperationAtr: vm.inputScreenC().nsOperationCls,    
        ClockHourMinuteStart: null, // HH:mm    
        ClockHourMinuteEnd: null, // HH:mm    
        startDate: moment(vm.startFromDate()).format("YYYY/MM/DD")
      };

      vm.$ajax('com', PATH.saveData, params).done(() => {
        vm.$dialog.info({ messageId: 'Msg_15' }).then(() => {
          vm.$window.close({ isSave: true });
          vm.$blockui('hide');
        });
      }).fail((error) => {
        vm.$dialog.error({ messageId: error.messageId }).then(() => {
          vm.$blockui('hide');
        });        
      });
    }

    getDataFromScreenB(params: any) {
      const vm = this;

      vm.inputScreenC(params);

      let beginStartDate = moment().format(FORMAT_DAY);        
      if (params && !_.isNil(params.lastItem) && !_.isNil(params.lastItem.startDate)) {
        beginStartDate = moment(params.lastItem.startDate).add(+1, 'days').format(FORMAT_DAY);        
      }
      vm.startFromDate(beginStartDate);
      vm.beginStartDate(beginStartDate);
    }
  }
}