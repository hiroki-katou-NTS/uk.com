/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmp002.b {

  import setShared = nts.uk.ui.windows.setShared;

  // URL API backend
  const API = {
    GET_INITIAL_DISPLAY: "at/record/workrecord/stampmanagement/support/initialStartupSupportCardSetting",
    UPDATE_SUPPORT_CARD_SETTING: "at/record/workrecord/stampmanagement/support/updateSupportCardSetting"
  };

  @bean()
  export class ViewModel extends ko.ViewModel {
    itemList: KnockoutObservableArray<any>;
    supportCardSetting: KnockoutObservable<SupportCardSetting> = ko.observable(new SupportCardSetting(SupportCardEditSettingEnum.PreviousZero));

    created() {
      const self = this;
      self.itemList = ko.observableArray([
        new BoxModel(SupportCardEditSettingEnum.PreviousZero, nts.uk.resource.getText("KMP002_20")),
        new BoxModel(SupportCardEditSettingEnum.AfterZero, nts.uk.resource.getText("KMP002_21")),
        new BoxModel(SupportCardEditSettingEnum.PreviousSpace, nts.uk.resource.getText("KMP002_22")),
        new BoxModel(SupportCardEditSettingEnum.AfterSpace, nts.uk.resource.getText("KMP002_23"))
      ]);
      self.$ajax(API.GET_INITIAL_DISPLAY)
        .then((data: any) => {
          self.supportCardSetting().editMethod(data.editMethod);
        });
    }

    closeDialog() {
      const vm = this;
      vm.$window.close();
    }

    registerSetting() {
      // update the support card edit settings
      const vm = this;
      const data: SupportCardEditDto = {
        editMethod: vm.supportCardSetting().editMethod()
      }
      vm.$ajax(API.UPDATE_SUPPORT_CARD_SETTING, data)
        .then((data: any) => {
          // set return value
          setShared('KMP002B_Output', vm.supportCardSetting().editMethod());
          vm.$window.close();
        });
    }

	}

  class BoxModel {
    id: number;
    name: string;

    constructor(id: number, name: string){
      var self = this;
      self.id = id;
      self.name = name;
    }
  }

  export enum SupportCardEditSettingEnum {
    // 前ゼロ
    PreviousZero = 0,

    // 後ろゼロ
    AfterZero = 1,

    // 前スペース
    PreviousSpace = 2,

    // 後ろスペース
    AfterSpace = 3
  }

  class SupportCardSetting {
    editMethod: KnockoutObservable<number>;

    constructor(editMethod: number) {
      this.editMethod = ko.observable(editMethod);
    }
  }

  interface SupportCardEditDto {
    editMethod: number;
  }

}