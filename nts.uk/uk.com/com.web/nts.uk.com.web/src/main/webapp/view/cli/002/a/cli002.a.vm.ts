/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.com.view.cli002.a {
  import service = nts.uk.com.view.cli002.a.service;
  @bean()
  export class ScreenModel extends ko.ViewModel {
    public systemList: KnockoutObservableArray<systemType> = ko.observableArray([
      new systemType(this.$i18n("Enum_SystemType_PERSON_SYSTEM")),
      new systemType(this.$i18n("Enum_SystemType_ATTENDANCE_SYSTEM")),
      new systemType(this.$i18n("Enum_SystemType_PAYROLL_SYSTEM")),
      new systemType(this.$i18n("Enum_SystemType_OFFICE_HELPER")),
    ]);

    public dataSourceItem: KnockoutObservableArray<PGList> = ko.observableArray([
      {displayName: "item1", logLoginDisplay: false, logStartDisplay: true, logUpdateDisplay: false},
      {displayName: "item1", logLoginDisplay: false, logStartDisplay: true, logUpdateDisplay: false},
      {displayName: "item1", logLoginDisplay: false, logStartDisplay: true, logUpdateDisplay: false},
      {displayName: "item1", logLoginDisplay: false, logStartDisplay: true, logUpdateDisplay: false},
      {displayName: "item1", logLoginDisplay: false, logStartDisplay: true, logUpdateDisplay: false},
      {displayName: "item1", logLoginDisplay: false, logStartDisplay: true, logUpdateDisplay: false},
      {displayName: "item1", logLoginDisplay: false, logStartDisplay: true, logUpdateDisplay: false},
      {displayName: "item1", logLoginDisplay: false, logStartDisplay: true, logUpdateDisplay: false},
      {displayName: "item1", logLoginDisplay: false, logStartDisplay: true, logUpdateDisplay: false},
      {displayName: "item1", logLoginDisplay: false, logStartDisplay: true, logUpdateDisplay: false},
      {displayName: "item1", logLoginDisplay: false, logStartDisplay: true, logUpdateDisplay: false},
      {displayName: "item1", logLoginDisplay: false, logStartDisplay: true, logUpdateDisplay: false},
      {displayName: "item1", logLoginDisplay: false, logStartDisplay: true, logUpdateDisplay: false},
      {displayName: "item1", logLoginDisplay: false, logStartDisplay: true, logUpdateDisplay: false},
      {displayName: "item1", logLoginDisplay: false, logStartDisplay: true, logUpdateDisplay: false}
    ]);
    public selectedSystemCode: KnockoutObservable<number>;

    public systemColumns = [
      {
        headerText: nts.uk.resource.getText("CLI002_2"),
        prop: "localizedName",
        width: 160,
      },
    ];

    public columnsItem = [
      {
        headerText: this.$i18n(""),
        prop: "index",
        width: 30,
      },
      {
        headerText: this.$i18n("CLI002_7"),
        prop: "functionName",
        width: 180,
      },
      {
        headerText: this.$i18n("CLI002_4"),
        prop: "loginHistory",
        width: 180,
      },
      {
        headerText: this.$i18n("CLI002_5"),
        prop: "bootHistory",
        width: 180,
      },
      {
        headerText: this.$i18n("CLI002_6"),
        prop: "revisionHistory",
        width: 180,
      },
    ];

    mounted() {
      const vm = this;
      
      ($("#system-list").ntsGridList as any)({
        height: "120px",
        dataSource: vm.systemList,
        columns: vm.systemColumns,
        value: vm.selectedSystemCode
      });
      vm.selectedSystemCode = ko.observable(0);

      ($("#search-box").ntsSearchBox as any)({
        searchMode: "filter",
        targetKey: "",
        placeHolder: this.$i18n("CLI002_8"),
        searchText: this.$i18n("KMP001_23"),
      });

      // vm.$blockui("grayout");
      // service.findBySystem(vm.selectedSystemCode).done((response: PGList[]) => vm.findBySystem)
      // .always(() => vm.$blockui("clear"));

      new nts.uk.ui.mgrid.MGrid($("#item-list")[0], {
        height: "400px",
        headerHeight: '60px',
        primaryKey: "number",
        primaryKeyDataType: "displayName",
        rowVirtualization: true,
        virtualization: true,
        virtualizationMode: 'continuous',
        enter: 'right',
        autoFitWindow: false,
        dataSource: vm.dataSourceItem(),
        columns: [
          { headerText: "", key: "rowNumber", dataType: "number", width: "30px"},
          { headerText: this.$i18n("CLI002_7"), key: "displayName", dataType: "string", width: "180px"},
          { headerText: this.$i18n("CLI002_4"),
              group: [
                {headerText: "", key: "logLoginDisplay", dataType: "boolean", width: "180px", ntsControl: "Checkbox", checkbox: true, border: false}
              ] 
          },
          { headerText: this.$i18n("CLI002_5"),
              group: [
                {headerText: "", key: "logStartDisplay", dataType: "boolean", width: "180px", ntsControl: "Checkbox", checkbox: true, border: false}
              ] 
          },
          { headerText: this.$i18n("CLI002_6"),
              group: [
                {headerText: "", key: "logUpdateDisplay", dataType: "boolean", width: "180px", ntsControl: "Checkbox", checkbox: true, border: false}
              ]
          },
        ],
        ntsControls: [
          { name: 'Checkbox', options: { value: 1, text: '' }, optionsValue: 'value', optionsText: 'text', controlType: 'CheckBox', enable: true, onChange: function() {}}
        ],
        features: []
      }).create();
      
      vm.selectedSystemCode.subscribe(function(newValue) {
        vm.findBySystem(Number(newValue));
      }); 
    }

    private findBySystem(value: number) {

    }


    // private setSystemItemList(response: PGList): JQueryPromise<any> {
    //   const dfd = $.Deferred();
    //   const vm = this;
    //   if(response) {
    //     vm.dataSourceItem.push(response);
    //     ($("#item-list").nts.uk.ui.mgrid.MGrid as any)({
    //       height: "400px",
    //       rowVirtualization: true,
    //       virtualization: true,
    //       virtualizationMode: 'continuous',
    //       enter: "center",
    //       dataSource: vm.dataSourceItem,
    //       columns: [
    //         { headerText: "", key: "rowNumber", dataType: "number", width: "30px"},
    //         { headerText: this.$i18n("CLI002_7"), key: "displayName", dataType: "string", width: "180px"},
    //         { headerText: this.$i18n("CLI002_4"), key: "logLoginDisplay", dataType: "boolean", width: "180px", ntsControl: "Checkbox", showHeaderCheckbox: true},
    //         { headerText: this.$i18n("CLI002_5"), key: "logStartDisplay", dataType: "boolean", width: "180px", ntsControl: "Checkbox", showHeaderCheckbox: true},
    //         { headerText: this.$i18n("CLI002_6"), key: "logUpdateDisplay", dataType: "boolean", width: "180px", ntsControl: "Checkbox", showHeaderCheckbox: true}
    //       ],
    //       ntsControls: [
    //         {name: 'Checkbox', controlType: 'CheckBox', enable: true, options: { value: 1, text: '' }, optionsValue: 'value', optionsText: 'text' }
    //       ],
    //       features: [
    //         {
    //             name: 'Selection',
    //             mode: 'row',
    //             multipleSelection: true
    //         }
    //       ]
    //     });
    //   }
    //   dfd.done();
    //   return dfd;
    // }
  }
  class systemType {
    localizedName: string;
    constructor(localizedName: string) {
      this.localizedName = localizedName;
    }
  }

  // class systemItem {
  //   index: number;
  //   functionName: string;
  //   loginHistory: boolean;
  //   bootHistory: boolean;
  //   revisionHistory: boolean;
  //   constructor(
  //     index: number,
  //     functionName: string,
  //     loginHistory: boolean,
  //     bootHistory: boolean,
  //     revisionHistory: boolean
  //   ) {
  //     this.index = index;
  //     this.functionName = functionName;
  //     this.loginHistory = loginHistory;
  //     this.bootHistory = bootHistory;
  //     this.revisionHistory = revisionHistory;
  //   }
  // }

  export interface PGList {
    displayName: string,
    logLoginDisplay: boolean,
    logStartDisplay: boolean,
    logUpdateDisplay: boolean
  }
}
