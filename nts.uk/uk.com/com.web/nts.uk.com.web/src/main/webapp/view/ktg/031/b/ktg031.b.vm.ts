/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.com.view.ktg031.b {

  // URL API backend
  const API = {

  };

  @bean()
  export class ScreenModel extends ko.ViewModel {
    columns: KnockoutObservableArray<any> = ko.observableArray([]);
    listSetting: KnockoutObservableArray<AlarmDisplaySettingDto> = ko.observableArray([]);
    currentRow: KnockoutObservable<any> = ko.observable(null);

    created(params: any) {
      const vm = this;
      vm.columns([
        { headerText: vm.$i18n('KTG031_21'), key: 'classification', width: 150 },
        { headerText: vm.$i18n('KTG031_22'), key: 'alarmProcessing', width: 280 },
        { headerText: vm.$i18n('KTG031_23'), key: 'isDisplay', width: 120 },
      ]);
      const lastIndex = 0;
      vm.listSetting([
        new AlarmDisplaySettingDto({
          rowNumber: lastIndex + 1,
          classification: vm.$i18n('KTG031_26'),
          alarmProcessing: vm.$i18n('KTG031_33'),
          isDisplay: vm.$i18n('KTG031_28'),
        }),
        new AlarmDisplaySettingDto({
          rowNumber: lastIndex + 2,
          classification: vm.$i18n('KTG031_26'),
          alarmProcessing: vm.$i18n('KTG031_34'),
          isDisplay: vm.$i18n('KTG031_28'),
        }),
        new AlarmDisplaySettingDto({
          rowNumber: lastIndex + 3,
          classification: vm.$i18n('KTG031_27'),
          alarmProcessing: vm.$i18n('KTG031_35'),
          isDisplay: vm.$i18n('KTG031_32'),
        }),
      ]);
    }

    mounted() {
      const vm = this;
    }

    public closeDialog() {
      const vm = this;
      vm.$window.close();
    }
  }

  class AlarmDisplaySettingDto {
    rowNumber: number;
    classification: string;
    alarmProcessing: string;
    isDisplay: string;

    constructor(init?: Partial<AlarmDisplaySettingDto>) {
      $.extend(this, init);
    }
  }
}
