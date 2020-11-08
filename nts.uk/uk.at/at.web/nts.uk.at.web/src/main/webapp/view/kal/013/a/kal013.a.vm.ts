/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />
module nts.uk.at.view.kal013.a {

  import common = nts.uk.at.view.kal013.common;
  import tab = nts.uk.at.view.kal013.a.tab;

  const PATH_API = {
    getEnumAlarmCategory: "at/function/alarm/get/enum/alarm/category"
  }

  @bean()
  class ViewModel extends ko.ViewModel {

    //categories
    selectedCategory: KnockoutObservable<common.CategoryPattern> = ko.observable(null);
    categoryList: KnockoutObservableArray<common.Category> = ko.observableArray([]);
    selectedCategoryCode: KnockoutObservable<number> = ko.observable(null);
    //Alarm list
    selectedAlarmCode: KnockoutObservable<string> = ko.observable('001');
    currentAlarm: KnockoutObservable<common.AlarmPattern> = ko.observable(null);
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

    uniqueConditions: KnockoutObservable<tab.UniqueCondition>;
    checkConditions: KnockoutObservable<tab.CheckCondition>;

    constructor(params: any) {
      super();
      const vm = this;

      vm.workplaceCategory = common.WorkplaceCategory;

      vm.getAlarmChecklist();
      vm.getEnumAlarmCategory();

      vm.tabSelections();

      vm.selectedCategoryCode.subscribe((newCode: any) => {
        if (!newCode) return;
        vm.getSelectedCategory(newCode);

        //reload with new category
        vm.getAlarmChecklist(newCode);
        vm.showHiddenTabByCategory(newCode);
      });

      vm.selectedAlarmCode.subscribe((newCode) => {
        if (newCode.length < 0 || !newCode) return;
        vm.getAlarmSelected(newCode);
      });

      vm.selectedTab.subscribe((newTab) => {
        const vm = this;
        let category: any = vm.selectedCategoryCode();

        switch (category) {
          case vm.workplaceCategory.SCHEDULE_DAILY:
            let  hasClicked:any = $("#fixedTableCCDT").attr('data-clicked');
            if (typeof hasClicked == 'undefined' || hasClicked === 'false') {
              $("#fixedTableCCDT")
              .attr('data-clicked', 'true')
              .ntsFixedTable({ height: 370 });
            }
            break;
        }
      });

      //show tabs
      vm.uniqueConditions = ko.observable(new tab.UniqueCondition(vm.selectedCategoryCode(), vm.currentAlarm()));
      vm.checkConditions = ko.observable(null);
    }

    created(params: any) {
      const vm = this;
    }

    mounted() {
      const vm = this;
      $("#fixedTable").ntsFixedTable({ height: 350 });
    }

    findItemSelected(code: any, seachArr: Array<any>): any {
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

    getEnumAlarmCategory() {
      const vm = this;
      vm.categoryList(common.workplaceCategory());
      vm.getSelectedCategory(common.WorkplaceCategory.MASTER_CHECK_BASIC);
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

    /**
     * Gets alarm checklist / アラームチェックリスト
     */
    getAlarmChecklist(code?: number) {
      const vm = this;

      vm.alarmListItems.push(new common.Alarm('001', '整数'));
      vm.alarmListItems.push(new common.Alarm('002', '文字列'));

      vm.alarmListItems(_.orderBy(vm.alarmListItems(), 'code', 'asc'));
      if (!_.isNil(vm.alarmListItems())) {
        let firtsItem = _.head(vm.alarmListItems());
        vm.getAlarmSelected(firtsItem.code);
      }
    }

    getAlarmSelected(code?: string) {
      const vm = this;

      let fountItem = vm.findItemSelected(code, vm.alarmListItems());
      if (!_.isNil(fountItem))
        vm.currentAlarm(new common.AlarmPattern(fountItem.code, fountItem.name));
      else
        vm.currentAlarm(new common.AlarmPattern('', ''));
    }

    /**
     * Gets selected category
     * @param categoryCode 
     * @returns  CategoryPattern
     */
    getSelectedCategory(categoryCode: number) {
      const vm = this;
      if (categoryCode < 0) return;

      let fountCategory = vm.findItemSelected(categoryCode, vm.categoryList());
      if (!_.isNil(fountCategory)) {
        vm.selectedCategory(new common.CategoryPattern(fountCategory.code, fountCategory.name));
      } else {
        vm.selectedCategory(new common.CategoryPattern('', ''));
      }
    }

    /**
     * Tabs selections
     */
    tabSelections() {
      const vm = this;

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
    }

    showHiddenTabByCategory(Category: any) {
      const vm = this;

      //hidden all tab      
      _.forEach(vm.tabs(), (tab: any, index) => {
        tab.visible(index === 0);
      });
      /*
      MASTER_CHECK_BASIC = 0,// マスタチェック(基本)    
      MASTER_CHECK_WORKPLACE = 1,// マスタチェック(職場)    
      MASTER_CHECK_DAILY = 2,// マスタチェック(日次)
      SCHEDULE_DAILY = 3, // "スケジュール／日次",    
      MONTHLY = 4,// 月次    
      APPLICATION_APPROVAL = 5, //"申請承認"/
      */

      //vm.checkConditions = ko.observable(null);

      switch (Category) {

        case vm.workplaceCategory.APPLICATION_APPROVAL:
        case vm.workplaceCategory.MASTER_CHECK_WORKPLACE:
        case vm.workplaceCategory.MASTER_CHECK_BASIC:
        case vm.workplaceCategory.MASTER_CHECK_DAILY:
          break;

        case vm.workplaceCategory.SCHEDULE_DAILY:
          vm.tabs()[1].visible(true);
          vm.checkConditions(new tab.CheckCondition(true));          
          break;

        case vm.workplaceCategory.MONTHLY:
          vm.tabs()[1].visible(true);
          vm.checkConditions(new tab.CheckCondition(true));          
          break;
      }
    }

    //check by tabs
  }
}