/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />
module nts.uk.at.view.kal013.a {

  const PATH_API = {
    getEnumAlarmCategory: "at/function/alarm/get/enum/alarm/category"
  }

  @bean()
  class ViewModel extends ko.ViewModel {

    selectedAlarmCode: KnockoutObservable<string> = ko.observable('001');
    alarmListItems: KnockoutObservableArray<Alarm> = ko.observableArray([]);
    selectedAlarm: KnockoutObservable<Alarm> = ko.observable(null);

    selectedAll: KnockoutObservable<boolean> = ko.observable(false);

    selectedCategory: KnockoutObservable<Category> = ko.observable(null);
    categoryList: KnockoutObservableArray<Category> = ko.observableArray([]);
    selectedCategoryCode: KnockoutObservable<string> = ko.observable(null);

    currentAlarm: KnockoutObservable<any>;
    tabs: KnockoutObservableArray<nts.uk.ui.NtsTabPanelModel>;

    alarmArrangeList: KnockoutObservableArray<AlarmDto> = ko.observableArray([]);

    constructor(params: any) {
      super();
      const vm = this;

      vm.alarmListItems.push(new Alarm('001', '整数'));
      vm.alarmListItems.push(new Alarm('002', '文字列'));

      vm.alarmListItems(_.orderBy(vm.alarmListItems(), 'code', 'asc'));
      vm.selectedAlarm(vm.findItemSelected('001', vm.alarmListItems()));

      vm.categoryList.push(new Alarm('001', 'アラームリストのカテゴリ'));
      vm.categoryList.push(new Alarm('002', 'ムリストのカテゴリ'));
      vm.selectedCategory(vm.findItemSelected('001', vm.categoryList()));

      vm.currentAlarm = ko.observable({
        code: ko.observable('001'),
        name: ko.observable('文字列'),
      });

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
          visible: ko.observable(true)
        }
      ]);

      vm.selectedAll.subscribe((newValue) => {
        if (newValue === null) return;
      });

      vm.getAlarmArrangeList();

      /* let Enum = __viewContext.enums.WorkplaceCategory;
      console.log(Enum); */
    }

    created(params: any) {
      const vm = this;
    }

    mounted() {
      const vm = this;
      $("#fixed-table").ntsFixedTable({ height: 350 });
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
      vm.$window.modal('/view/kal/013/b/index.xhtml').then(() => { });
    }

    getAlarmArrangeList() {
      const vm = this;
      for (let i = 0; i < 20; i++) {
        vm.alarmArrangeList.push(new AlarmDto(false, true, '名称 ' + (i + 1), '表示するメッセージ ' + (i + 1)));
      }
    }
  }


  export class Alarm {
    code: string;
    name: string;
    constructor(code?: string, name?: string) {
      this.code = code;
      this.name = name;
    }
  }

  export class Category {
    code: string;
    name: string;
    constructor(code?: string, name?: string) {
      this.code = code;
      this.name = name;
    }
  }

  export class AlarmDto {
    isChecked: KnockoutObservable<boolean> = ko.observable(false);
    register: KnockoutObservable<boolean> = ko.observable(false);
    name: KnockoutObservable<string> = ko.observable(null);
    message: KnockoutObservable<string> = ko.observable(null);
    constructor(isChecked?: boolean, register?: boolean, name?: string, message?: string) {
      this.isChecked(isChecked);
      this.register(register);
      this.name(name);
      this.message(message);
    }
  }
}