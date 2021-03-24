/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />
module nts.uk.at.view.ksm011.d {
  
  const PATH = {
    getSetting: ''
  }

  @bean()
  class ViewModel extends ko.ViewModel {

    switchItems: KnockoutObservableArray<any>;
    switchItems1: KnockoutObservableArray<any>;
    completionMethod: KnockoutObservableArray<any>;
    conditionWorkSchedule: KnockoutObservableArray<any>;

    targetDate: KnockoutObservable<number> = ko.observable(1);
    deadline: KnockoutObservable<number> = ko.observable(0);
    completionFunction: KnockoutObservable<number> = ko.observable(1);    
    alarmCheck: KnockoutObservable<number> = ko.observable(1);
    confirm: KnockoutObservable<number> = ko.observable(0);
    alarmConditionList: KnockoutObservableArray<string> = ko.observableArray([]);
    alarmConditionListText: KnockoutObservable<string> = ko.observable(null);

    selectedCode: KnockoutObservable<string>;
    selectableCode: KnockoutObservable<string>;

    daysList: KnockoutObservableArray<any>= ko.observableArray([]);
    workDisplay: KnockoutObservable<number> = ko.observable(0);
    abbreviationDisplay: KnockoutObservable<number> = ko.observable(1);
    shiftDisplay: KnockoutObservable<number> = ko.observable(1);
    lastDayDisplay: KnockoutObservable<number> = ko.observable(1);
    _28DayCycle: KnockoutObservable<number> = ko.observable(0);
    displayByDate: KnockoutObservable<number> = ko.observable(1);
    completionExecutionMethod: KnockoutObservable<number> = ko.observable(1);
    personalInforDisplay: KnockoutObservableArray<string> = ko.observableArray([]);

    constructor(params: any) {
      super();
      const vm = this;

      vm.switchItems = ko.observableArray([
        { code: 1, name: vm.$i18n('KSM011_56') },
        { code: 0, name: vm.$i18n('KSM011_57') }
      ]);

      vm.switchItems1 = ko.observableArray([
        { code: 1, name: vm.$i18n('KSM011_21') },
        { code: 0, name: vm.$i18n('KSM011_22') }
      ]);
      
      vm.completionMethod = ko.observableArray([
        { code: 0, name: vm.$i18n('KSM011_80') },
        { code: 1, name: vm.$i18n('KSM011_81') }
      ]);
      
      vm.conditionWorkSchedule = ko.observableArray([
        { code: CWSchedule.INSURANCE_STATUS, name: vm.$i18n('CWSchedule_Insurance_Status') },
        { code: CWSchedule.TEAM, name: vm.$i18n('CWSchedule_Team') },
        { code: CWSchedule.RANK, name: vm.$i18n('CWSchedule_Rank') },
        { code: CWSchedule.QUALIFICATION, name: vm.$i18n('CWSchedule_Qualification') },
        { code: CWSchedule.LICENSE_ATR, name: vm.$i18n('CWSchedule_License') }     
      ]);
      //vm.getSetting();
      vm.getDayList();
    }

    created(params: any) {
      const vm = this;
    }

    mounted() {
      const vm = this;
      $('#KSM011_D24').focus();
    }

    list(str: string):Array<string>{
      return _.split(str, ',');
    }

    openDialogScreenF() {
      const vm = this;

      vm.$window.modal('/view/ksm/011/f/index.xhtml', vm.alarmConditionList()).then((data) => {
        vm.$blockui('show');        
        if( data ) {
          let conditionListText: Array<string> = data.listItemsSelected.map((i: any) => i.name);
          vm.alarmConditionList(data.listItemsSelected.map((i: any) => i.code));
          vm.alarmConditionListText(_.join(conditionListText, ' + '));
          vm.$blockui('hide');
        } else 
          vm.$blockui('hide');
      });
    }

    getSetting() {
      const vm = this;

      vm.$ajax(PATH.getSetting).done((data) => {
        if( data ) {
         /*  vm.regularWork(data.regularWork);
          vm.flexTime(data.flexTime);
          vm.fluidWork(data.fluidWork);
          vm.workTypeControl(data.workTypeControl);
          vm.achievementDisplay(data.achievementDisplay);
          vm.selectedCode(data.selectableCode);
          vm.selectableCode(data.selectableCode);*/
        }
      });
    }

    saveData() {
      const vm = this;

      if( vm.workDisplay() === 0 || vm.shiftDisplay() === 0 || vm.abbreviationDisplay() === 0) {
        vm.$dialog.error({ messageId: 'Msg_2125'}).then(() => {});
        return;
      }

      if( vm.alarmCheck() === 1 && vm.alarmConditionList().length <= 0) {
        vm.$dialog.error({ messageId: 'Msg_1690', messageParams: [vm.$i18n('KSM011_87')]}).then(() => {
          $('#KSM011_D6_14').focus();
        });
        return;
      }

      let params = {
       /*  regularWork: vm.regularWork(),
        flexTime: vm.flexTime(),
        fluidWork: vm.fluidWork(),
        workTypeControl: vm.workTypeControl(),
        achievementDisplay: vm.achievementDisplay() */
        //vm.selectedCode(data.selectableCode);
        //vm.selectableCode(data.selectableCode);
      };

     /*  vm.$ajax(PATH.saveData, params).done((data) => {
        if( data ) {
          vm.$dialog.info({ messageId: 'Msg_15'}).then(() => {
          return;
          });
        }
      }).fail(error => {
        vm.$dialog.info({ messageId: error.messageId}).then(() => {
          return;
          });
      }); */
    }

    getDayList(){
      const vm = this;
      vm.daysList = ko.observableArray([]);
      let days = [];
      for( let day = 0; day < 30; day++) {
        days.push( { day: day, name: (day + 1) + vm.$i18n('KSM011_105')});
      }
      days.push( { day: 30, name: vm.$i18n('KSM011_106')});
      vm.daysList(days);
    }
  }

  //勤務予定の条件区分（ランク、チーム、介護区分のみ）
  export enum CWSchedule { //Condition Word Schedule
    // 	0:保険加入状況	
    INSURANCE_STATUS = 0, //(0, "保険加入状況"),  CWSchedule_Insurance_Status
    // 	1:チーム		
    TEAM = 1, //(1, "チーム"),    CWSchedule_Team
    //  2:ランク	 
    RANK = 2, //(2, "ランク"), CWSchedule_Rank
    
    // 3:資格		 
    QUALIFICATION = 3,// (3, "資格"), CWSchedule_Qualification
    
    // 4:免許区分 
    LICENSE_ATR = 4 //(4, "免許区分"); CWSchedule_License
  }  
}