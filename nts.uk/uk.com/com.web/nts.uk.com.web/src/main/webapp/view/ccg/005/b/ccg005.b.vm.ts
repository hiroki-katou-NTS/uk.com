/// <reference path='../../../../lib/nittsu/viewcontext.d.ts' />
module nts.uk.at.view.ccg005.b.screenModel {
  @bean()
  export class ViewModel extends ko.ViewModel {
    listDataMulti: KnockoutObservableArray<any> = ko.observableArray([]);
    selectSingle: KnockoutObservable<string> = ko.observable();
    isEnable: KnockoutObservable<boolean> = ko.observable(true);
    isEditable: KnockoutObservable<boolean> = ko.observable(false);
    isRequired: KnockoutObservable<boolean> = ko.observable(false);
    selectFirstIfNull: KnockoutObservable<boolean> = ko.observable(false);
    columns: KnockoutObservableArray<any>;
    columns2: KnockoutObservableArray<any>;
    items: KnockoutObservableArray<any> = ko.observableArray([]);
    cId: KnockoutObservable<string> = ko.observable("");
    created() {
      const vm = this;
      var model = new ScreenModel();
      vm.columns = ko.observableArray([
        { headerText: vm.$i18n("CCG005_5"), key: "code", width: 100 },
      ]);
      vm.columns2 = ko.observableArray([
        { headerText: vm.$i18n("CCG005_7"), key: "code", width: 50 },
        { headerText: vm.$i18n("CCG005_6"), key: "name", width: 80 },
      ]);

      $("#grid").ntsGrid({
        width: "175px",
        height: "200px",
        dataSource: model.items,
        primaryKey: "id",
        virtualization: true,
        virtualizationMode: "continuous",
        columns: [
          { headerText: "ID", key: "id", dataType: "number", width: "50px" ,hidden: true},
          {
            headerText: vm.$i18n("CCG005_7"),
            key: "flag",
            dataType: "boolean",
            width: "65px",
            ntsControl: "Checkbox",
          },
          {
            headerText: vm.$i18n("CCG005_6"),
            key: "ruleCode",
            dataType: "string",
            width: "110px",
          }
          
        ],
        features: [{ name: "Sorting", type: "local" }],
        ntsControls: [
          {
            name: "Checkbox",
            options: { value: 1, text: "Custom Check" },
            optionsValue: "value",
            optionsText: "text",
            controlType: "CheckBox",
            enable: true,
          }
        ]
      });
      
    }

    onClickCancel() {
      this.$window.close();
    }
  }

  export class ToppageReloadSettingCommand {
    cId: string;
    reloadInteval: number;
    constructor(cId: string, reloadInteval: number) {
      this.cId = cId;
      this.reloadInteval = reloadInteval;
    }
  }
  class ScreenModel {
    items = (function () {
      var list = [];
      for (var i = 0; i < 500; i++) {
        list.push(new GridItem(i));
      }
      return list;
    })();
  }
  class GridItem {
    id: number;
    flag: boolean;
    ruleCode: string;
    combo: string;
    constructor(index: number) {
      this.id = index;
      this.flag = index % 2 == 0;
      this.ruleCode = String((index % 3) + 1);
      this.combo = String((index % 3) + 1);
    }
  }
  class ItemModel {
    code: string;
    name: string;

    constructor(code: string, name: string) {
      this.code = code;
      this.name = name;
    }
  }
}
