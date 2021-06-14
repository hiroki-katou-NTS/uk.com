/// <reference path="../../../../../lib/nittsu/viewcontext.d.ts" />
module nts.uk.at.view.ksm011.b.tabs.tab1 {
  
  const PATH = {
    registerRulesCompanyShiftTable: 'at/schedule/shift/table/company/register',
    getRulesOfCompanyShiftTable: 'at/schedule/shift/table/company/init'
  };

  @bean()
  export class ViewModel extends ko.ViewModel {
    switchItems: KnockoutObservableArray<any>;
    publicMethod: KnockoutObservable<number> = ko.observable(0);
    workRequestSelected: KnockoutObservable<number> = ko.observable(0);
    workRequest: KnockoutObservable<number> = ko.observable(0);    
    maxDesiredHolidays: KnockoutObservable<number> = ko.observable(0);

    daysList: KnockoutObservableArray<any>;
    workRequestInput: KnockoutObservableArray<any>;
    workRequestInputSelected: KnockoutObservable<number> = ko.observable(1);
    deadlineSelected: KnockoutObservable<number> = ko.observable(0);
    deadlineWorkSelected: KnockoutObservable<number> = ko.observable(0);

    enableWorkRequestInput: KnockoutComputed<boolean>;

    constructor(params: any) {
      super();
      const vm = this;
      vm.switchItems = ko.observableArray([
        { code: 1, name: vm.$i18n('KSM011_21') },
        { code: 0, name: vm.$i18n('KSM011_22') }
      ]);

      vm.workRequestInput = ko.observableArray([
        { code: 1, name: vm.$i18n('KSM011_31') },
        { code: 0, name: vm.$i18n('KSM011_32') }
      ]);

      vm.daysList = ko.observableArray([]);
      let days = [];
      for( let day = 1; day <= 31; day++) {
        days.push( { day: day, name: day + vm.$i18n('KSM011_105')});
      }
      days.push( { day: 0, name: vm.$i18n('KSM011_106')});
      vm.daysList(days);

      vm.initialLoadPage();
      vm.enableWorkRequestInput = ko.computed(() => {
          return vm.workRequest() == 1;
      });
    }

    mounted() {
      const vm = this;
    }

    initialLoadPage() {
      const vm = this;
      vm.getRulesOfCompanyShiftTable();
    }

    //会社のシフト表のルールを登録する
    registerRulesCompanyShiftTable() {
      const vm = this;
        vm.$validate('.nts-input').then(function (valid) {
            if (valid) {
                vm.$blockui('show');
                let params = {
                    usePublicAtr: vm.publicMethod(),//公開機能の利用区分
                    useWorkAvailabilityAtr:  vm.workRequest(),//勤務希望の利用区分
                    holidayMaxDays: vm.maxDesiredHolidays(),//希望休日の上限日数入力
                    closureDate: vm.deadlineSelected(),//締め日
                    availabilityDeadLine: vm.deadlineWorkSelected(),//締切日
                    availabilityAssignMethod: vm.workRequestInputSelected()//入力方法の利用区分
                };

                vm.$ajax( PATH.registerRulesCompanyShiftTable, params ).done((data) => {
                    vm.$dialog.info({ messageId: 'Msg_15'});
                }).fail((error) => {
                    vm.$dialog.error({ messageId: error.messageId});
                }).always(() => {
                    vm.$blockui('hide');
                });
            }
        });
    }

    //Get the rules of the company shift table
    getRulesOfCompanyShiftTable() {
      const vm = this;
      vm.$blockui('show');
      vm.$ajax( PATH.getRulesOfCompanyShiftTable).done((data) => {
        if( data) {
          vm.publicMethod(data.usePublicAtr);//Ba3_2	 公開機能の利用区分
          vm.workRequest(data.useWorkAvailabilityAtr);//Ba4_2 勤務希望の利用区分
          vm.maxDesiredHolidays(data.holidayMaxDays); //Ba4_6				希望休日の上限日数入力
          vm.deadlineSelected(data.closureDate); //Ba4_9				締め日
          vm.deadlineWorkSelected(data.availabilityDeadLine); //Ba4_11				締切日
          vm.workRequestInputSelected(data.availabilityAssignMethod); //Ba4_13				入力方法の利用区分
        }
        $("#Ba3_2").focus();
      }).fail((error) => {
        vm.$dialog.error(error);
      }).always(() => {
        vm.$blockui('hide');
      });
    }
  }
}