/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kbt002.d {

  const API = {
    getEnumDataList: 'at/function/processexec/getEnum',
  };

  @bean()
  export class ScreenModel extends ko.ViewModel {
    gridListColumns: KnockoutObservableArray<any>;
    monthDaysList: KnockoutObservableArray<any> = ko.observableArray([]);
    selectedDaysList: KnockoutObservableArray<number> = ko.observableArray([]);

    created(params: any) {
      const vm = this;
      if (params) {
        vm.selectedDaysList(params.repeatMonthDateList);
      }
      vm.gridListColumns = ko.observableArray([
        { headerText: '', key: 'value', hidden: true },
        { headerText: vm.$i18n("KBT002_108"), key: 'localizedName', width: 210 }
      ]);
      vm.getEnumDataList();
    }

    public select() {
      const vm = this;
      if (vm.selectedDaysList().length === 0) {
        vm.$dialog.alert({ messageId: "Msg_846" });
      } else {
        // close dialog.
        vm.$window.close({ selectedDays: vm.selectedDaysList() });
      }
    }

    private getEnumDataList() {
      const vm = this;
      vm.$blockui("grayout");
      vm.$ajax(API.getEnumDataList)
        .then((setting: any) => vm.monthDaysList(setting.monthDayList))
        .always(() => vm.$blockui("clear"));
    }

    public closeDialog() {
      const vm = this;
      vm.$window.close();
    }
  }
}
