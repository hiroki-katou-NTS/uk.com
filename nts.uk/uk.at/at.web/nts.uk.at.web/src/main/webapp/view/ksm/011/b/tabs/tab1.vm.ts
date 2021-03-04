/// <reference path="../../../../../lib/nittsu/viewcontext.d.ts" />
module nts.uk.at.view.ksm011.b.tabs.tab1 {
  
  const PATH = {
    registerRulesCompanyShiftTable: '',
    getRulesOfCompanyShiftTable: ''
  }
  @bean()
  export class ViewModel extends ko.ViewModel {
    switchItems: KnockoutObservableArray<any>;
    publicMethod: KnockoutObservable<number> = ko.observable(0);
    workRequestSelected: KnockoutObservable<number> = ko.observable(1);
    workRequest: KnockoutObservable<number> = ko.observable(1);    
    maxDesiredHolidays: KnockoutObservable<number> = ko.observable(1);

    daysList: KnockoutObservableArray<any>;
    workRequestInput: KnockoutObservableArray<any>;
    workRequestInputSelected: KnockoutObservable<number> = ko.observable(1);  
    deadlineSelected: KnockoutObservable<number> = ko.observable(1); 
    deadlineWorkSelected: KnockoutObservable<number> = ko.observable(1); 

    constructor(params: any) {
      super();
      const vm = this;
      vm.switchItems = ko.observableArray([
        { code: 1, name: vm.$i18n('KSM011_21') },
        { code: 0, name: vm.$i18n('KSM011_22') }
      ]);

      vm.workRequestInput = ko.observableArray([
        { code: 1, name: vm.$i18n('KSM011_21') },
        { code: 0, name: vm.$i18n('KSM011_22') }
      ]);

      vm.initialLoadPage();
    }

    created(params: any) {
      const vm = this;
    }

    mounted() {
      const vm = this;
    }

    initialLoadPage() {
      const vm = this;      

      vm.daysList = ko.observableArray([]);
      let days = [];
      for( let day = 0; day < 30; day++) {
        days.push( { day: day, name: (day + 1) + vm.$i18n('KSM011_105')});
      }
      days.push( { day: 30, name: vm.$i18n('KSM011_106')});
      vm.daysList(days);

      //vm.getRulesOfCompanyShiftTable();
    }

    //会社のシフト表のルールを登録する
    registerRulesCompanyShiftTable() {
      const vm = this;

      vm.$blockui('show');

      let params = {
        publicMethod: vm.publicMethod(),//公開機能の利用区分
        workRequest:  vm.workRequest(),//勤務希望の利用区分
        maxDesiredHolidays: vm.maxDesiredHolidays(),//希望休日の上限日数入力
        deadline: vm.deadlineSelected(),//締め日                                       
        deadlineWork: vm.deadlineWorkSelected(),//締切日                                   
        workRequestInput: vm.workRequestInputSelected()//入力方法の利用区分
      };

      vm.$ajax( PATH.registerRulesCompanyShiftTable, params ).done((data) => {
        vm.$dialog.info({ messageId: 'Msg_15'}).then(() => {
          vm.$blockui('hide');
        });
      }).fail((error) => {
        vm.$dialog.error({ messageId: error.messageId}).then(() => {
          vm.$blockui('hide');
        });
      });
      
    }

    //Get the rules of the company shift table
    getRulesOfCompanyShiftTable() {
      const vm = this;
      vm.$ajax( PATH.getRulesOfCompanyShiftTable).done((data) => {
        if( data) {
          vm.publicMethod(data.public);//Ba3_2	 公開機能の利用区分							
          vm.workRequest(data.workRequest);//Ba4_2 勤務希望の利用区分		
          vm.maxDesiredHolidays(data.maxDesiredHolidays); //Ba4_6				希望休日の上限日数入力							
          vm.deadlineSelected(data.deadline); //Ba4_9				締め日                                       							
          vm.deadlineWorkSelected(data.deadlineWork); //Ba4_11				締切日                                   							
          vm.workRequestInput (data.workRequestInput); //Ba4_13				入力方法の利用区分							
        }
      }).fail((error) => {
        vm.$dialog.error({ messageId: error.messageId}).then(() => {
          vm.$blockui('hide');
        });
      });
    }
  }
}