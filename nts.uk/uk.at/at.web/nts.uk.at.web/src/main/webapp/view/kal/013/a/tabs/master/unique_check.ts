module nts.uk.at.view.kal013.a.tab {
  import common = nts.uk.at.view.kal013.common;

  const PATH = {
    getAlarmList: 'at/schedule/alarm/cateory/list'
  };

  export class UniqueCondition {

    categoryCode: KnockoutObservable<number> = ko.observable(null);
    alarmPattern: KnockoutObservable<common.AlarmPattern> = ko.observable(null);

    selectedAll: KnockoutObservable<boolean> = ko.observable(false);

    alarmListItem: KnockoutObservableArray<common.AlarmDto> = ko.observableArray([]);

    constructor(categoryCode?: number, alarmPattern?: common.AlarmPattern) {
      
      const vm = this;
      
      vm.getAlarmListByCategory();

      vm.selectedAll.subscribe((newValue: any) => {
        if (newValue === null) return;
        _.forEach(vm.alarmListItem(), (item) => {
          item.isChecked(newValue);
        });
      });

      vm.alarmListItem.subscribe((newList) => {
        if (!newList || newList.length <= 0) {
          vm.selectedAll(false);
          return;
        }
        //Check if all the values in the settingListItemsDetails array are true:
        let isSelectedAll: any = vm.alarmListItem().every(item => item.isChecked() === true);
        //there is least one item which is not checked
        if (isSelectedAll === false) isSelectedAll = null;
        vm.selectedAll(isSelectedAll);
        //_.every(vm.alarmListItem(), (x) => x.isChecked() === true );
      });
    }

    getAlarmListByCategory()  {
      const vm = this;
      for (let i = 0; i < 20; i++) {
        let newAlarm = new common.AlarmDto(false, true, '名称 ' + (i + 1), '表示するメッセージ ' + (i + 1));

        newAlarm.isChecked.subscribe((newValue) => {
          vm.alarmListItem.valueHasMutated();
        });

        vm.alarmListItem.push(newAlarm);
      }
    }
  }
}