/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />
module nts.uk.at.view.kal013.a {
  @bean()
  class ViewModel extends ko.ViewModel {
 
    selectedAlarmCode: KnockoutObservable<string> = ko.observable('001');   
    alarmListItems: KnockoutObservableArray<Alarm> = ko.observableArray([]);
    selectedAlarm: KnockoutObservable<Alarm>= ko.observable(null);

    selectedAll: KnockoutObservable<boolean> = ko.observable(false);

    selectedCategory: KnockoutObservable<Category> = ko.observable(null);
    categoryList: KnockoutObservableArray<Category> = ko.observableArray([]);
    selectedCategoryCode: KnockoutObservable<string> = ko.observable(null);

    currentAlarm: KnockoutObservable<any>;
    tabs: KnockoutObservableArray<nts.uk.ui.NtsTabPanelModel>;

    constructor(params: any) {
      super();
      const vm = this;

      vm.alarmListItems.push( new Alarm('001', '整数') );
      vm.alarmListItems.push( new Alarm('002', '文字列') );

      vm.alarmListItems(_.orderBy(vm.alarmListItems(), 'code', 'asc'));
      vm.selectedAlarm(vm.findAlarmSelected('001'));

      vm.categoryList.push( new Alarm('001', 'アラームリストのカテゴリ') );
      vm.categoryList.push( new Alarm('002', 'ムリストのカテゴリ') );

      vm.currentAlarm = ko.observable({
        code: ko.observable('001'),
        name: ko.observable('文字列'),
      });

      vm.tabs = ko.observableArray([
        {id: 'tab-1', title: vm.$i18n('KAL013_15'), content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true)}        
      ]);

      vm.selectedAll.subscribe((newValue) => {
        if( newValue === null ) return;
      });
    }

    created(params: any) {
      const vm = this;
    }

    mounted() {
      const vm = this;
    }

    findAlarmSelected( code: string ) {
      const vm = this;
      let findAlarm = _.find(vm.alarmListItems(), ['code', code]);
      return findAlarm;
    }

    openScreenB() {
      const vm = this;
      //vm.$window.storage('');
      vm.$window.modal('/view/kal/013/b/index.xhtml').then(() => {});
    }
  }


  export class Alarm {
    code: string;
    name: string;
    constructor( code?: string,  name?: string) {
      this.code = code;
      this.name = name;
    }
  }

  export class Category {
    code: string;
    name: string;
    constructor( code?: string,  name?: string) {
      this.code = code;
      this.name = name;
    }
  }
}