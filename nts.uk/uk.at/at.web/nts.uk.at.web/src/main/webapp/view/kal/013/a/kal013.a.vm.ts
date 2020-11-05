/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />
module nts.uk.at.view.kal013.a {

  import common = nts.uk.at.view.kal013.common;

  const PATH_API = {
    getEnumAlarmCategory: "at/function/alarm/get/enum/alarm/category"
  }

  @bean()
  class ViewModel extends ko.ViewModel {

    //categories
    selectedCategory: KnockoutObservable<common.Category> = ko.observable(null);
    categoryList: KnockoutObservableArray<common.Category> = ko.observableArray([]);
    selectedCategoryCode: KnockoutObservable<string> = ko.observable(null);
    //Alarm list
    selectedAlarmCode: KnockoutObservable<string> = ko.observable('001');    
    currentAlarm: KnockoutObservable<common.Alarm> = ko.observable(null);
    alarmListItems: KnockoutObservableArray<common.Alarm> = ko.observableArray([]);

    //tab panel
    tabs: KnockoutObservableArray<nts.uk.ui.NtsTabPanelModel>;
    selectedTab: KnockoutObservable<string> = ko.observable(null); //active
    //grid list
    selectedAll: KnockoutObservable<boolean> = ko.observable(false);
    alarmArrangeList: KnockoutObservableArray<common.AlarmDto> = ko.observableArray([]);
    //switch button
    roundingRules: KnockoutObservableArray<any>;
    selectedRuleCode: KnockoutObservable<number> = ko.observable(1);

    workplaceCategory: any = {};

    constructor(params: any) {
      super();
      const vm = this;

      vm.workplaceCategory = common.WorkplaceCategory;

      vm.alarmListItems.push(new common.Alarm('001', '整数'));
      vm.alarmListItems.push(new common.Alarm('002', '文字列'));

      vm.alarmListItems(_.orderBy(vm.alarmListItems(), 'code', 'asc'));
      //vm.selectedAlarm(vm.findItemSelected('001', vm.alarmListItems()));

      vm.getEnumAlarmCategory();
      vm.selectedCategory(vm.findItemSelected('001', vm.categoryList()));

      vm.currentAlarm( new common.Alarm('001', '文字列'));

      vm.tabs = ko.observableArray([
        {
          id: 'tab-1',
          title: vm.$i18n('KAL013_15'),
          content: '.tab-content-1',
          enable: ko.observable(true),
          visible: ko.observable(true)
        },
        {
          id: 'tab-2',
          title: vm.$i18n('KAL013_15'),
          content: '.tab-content-2',
          enable: ko.observable(true),
          visible: ko.observable(false)
        }
      ]);

      vm.selectedTab('tab-1');

      vm.selectedAll.subscribe((newValue) => {
        if (newValue === null) return;
      });

      vm.getAlarmArrangeList();

      vm.roundingRules = ko.observableArray([
        { code: 0, name: '四捨' },
        { code: 1, name: '切り上' }
      ]);

      vm.selectedCategoryCode.subscribe((newCode: any) => {
        vm.tabs()[1].visible(false);
        switch (newCode) {
          case vm.workplaceCategory.SCHEDULE_DAILY:
            vm.tabs()[1].visible(true);
            break;
        }
      });

      vm.selectedTab.subscribe((newTab) => {
        const vm = this;
        let category: any = vm.selectedCategoryCode();
        console.log(newTab);
        switch (category) {
          case vm.workplaceCategory.SCHEDULE_DAILY:
            $("#scheduleDailyTable").ntsFixedTable({ height: 350 });
            break;
        }
      });
    }

    created(params: any) {
      const vm = this;
    }

    mounted() {
      const vm = this;
      $("#fixedTable").ntsFixedTable({ height: 350 });
    }

    findItemSelected(code: string, seachArr: Array<any>): any {
      const vm = this;
      if (!_.isArray(seachArr)) return null;
      let found = _.find(seachArr, ['code', code]);
      return (!_.isNil(found)) ? found : null;
    }

    openScreenB() {
      const vm = this;
      //vm.$window.storage('');
      vm.$window.modal('/view/kal/013/d/index.xhtml').then(() => { });
    }

    getAlarmArrangeList() {
      const vm = this;
      for (let i = 0; i < 20; i++) {
        vm.alarmArrangeList.push(new common.AlarmDto(false, true, '名称 ' + (i + 1), '表示するメッセージ ' + (i + 1)));
      }
    }

    getEnumAlarmCategory() {
      const vm = this;
      vm.categoryList(common.workplaceCategory());
    }

    /**
     * Registration of alarm list check conditions (by workplace)
     */
    registerAlarmListByWorkplace() {

    }

     /**
     * Delete of alarm list check conditions (by workplace)
     */
    deleteAlarmListByWorkplace() {

    }
  }
}