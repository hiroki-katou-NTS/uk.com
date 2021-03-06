/// <reference path='../../../lib/nittsu/viewcontext.d.ts' />
module nts.uk.at.view.kdl048.screenModel {
  import getShared = nts.uk.ui.windows.getShared;
  import setShared = nts.uk.ui.windows.setShared;

  @bean()
  export class ViewModel extends ko.ViewModel {
    initParam: any;
    objectDisplay: Display;
    //A1_1,2,3,4
    titleLine: KnockoutObservable<TitleLineObject> = ko.observable();
    //A3_1,2
    itemNameLine: KnockoutObservable<ItemNameLineObject> = ko.observable();
    attendanceRecordName: KnockoutObservable<string> = ko.observable("");
    attributeObject: KnockoutObservable<AttributeObject> = ko.observable();
    valueCb: KnockoutObservable<number> = ko.observable(0);

    exportAtr: number = 1;
    filterCode: KnockoutComputed<number> = ko.computed(() => {
      if (this.exportAtr === 1) {
        return this.valueCb() === 4
          ? 5
          : this.valueCb() === 5
            ? 2
            : this.valueCb() === 7
              ? 3
              : null;
      }

      if (this.exportAtr === 2) {
        return this.valueCb() === 4
          ? 1
          : this.valueCb() === 5
            ? 2
            : this.valueCb() === 6
              ? 3
              : this.valueCb() === 7
                ? 4
                : null;
      }
      return null;
    });
    // A5_3
    currentCodeList: KnockoutObservableArray<any> = ko.observableArray([]);
    headers: any = ko.observableArray([this.$i18n("KDL048_6"), this.$i18n("KDL048_7")]);
    columns: KnockoutObservableArray<any>;
    currentCode: KnockoutObservable<any> = ko.observable();
    // A6
    diligenceData: KnockoutObservableArray<DiligenceProject> = ko.observableArray([]);
    tableDataA6: DiligenceProject[] = [];

    // A9
    columnSelectedItemList: KnockoutObservableArray<any> = ko.observableArray([
      {
        headerText: this.$i18n("KDL048_11"),
        prop: "operator",
        width: 55,
        columnCssClass: "text-center",
      },
      { headerText: 'ID', prop: "itemId", hidden:true },
      { headerText: this.$i18n("KDL048_6"), prop: "indicatesNumber", width: 70 },
      { headerText: this.$i18n("KDL048_7"), prop: "name", width: 200 },
    ]);
    paramSelectedTimeList: KnockoutObservableArray<SelectedTimeListParam> = ko.observableArray([]);
    dataSelectedItemList: KnockoutObservableArray<SelectedTimeList> = ko.observableArray([]);
    currentCodeList2: KnockoutObservableArray<any> = ko.observableArray([]);

    created() {
      const vm = this;
      //get params
      vm.initParam = getShared("attendanceItem");
      vm.objectDisplay = vm.initParam;
      //Setting data
      vm.diligenceData(vm.initParam.diligenceProjectList);
      vm.titleLine(vm.initParam.titleLine);
      vm.attributeObject(vm.initParam.attribute);
      vm.itemNameLine(vm.initParam.itemNameLine);
      vm.paramSelectedTimeList(vm.initParam.selectedTimeList);
      vm.attendanceRecordName(vm.initParam.itemNameLine.name);
      vm.exportAtr = vm.initParam.exportAtr;
      //data for table
      vm.tableDataA6 = vm.initParam.diligenceProjectList;

      // Columns of table A6
      vm.columns = ko.observableArray([
        { headerText: 'ID', prop: "id", hidden: true },
        { headerText: vm.$i18n("KDL048_6"), prop: "indicatesNumber", width: 70 },
        { headerText: vm.$i18n("KDL048_7"), prop: "name", width: 235 },
      ]);

      // event when change itemCombo
      vm.valueCb.subscribe(function (codeChange) {
        vm.onChangeItemCombo(codeChange);
      });
      $(vm.$el).find('#A5_2').focus();

    }

    mounted() {
      // Fill data to combobox
      const vm = this;
      let list: KnockoutObservableArray<any> = ko.observableArray([]);
      vm.valueCb(vm.attributeObject().selected);

      // fill data to table
      _.each(vm.paramSelectedTimeList(), (item2: SelectedTimeListParam) => {
        _.each(vm.diligenceData(), (item1: DiligenceProject) => {
          if (parseInt(item1.id) === item2.itemId) {
            let data: SelectedTimeList = new SelectedTimeList();
            let data1: DiligenceProject = new DiligenceProject();
            data.name = item1.name;
            data.itemId = item2.itemId;
            data.operator = item2.operator;
            data.indicatesNumber = item1.indicatesNumber;
            data1.id = item1.id;
            vm.dataSelectedItemList.push(data);
            list.push(data1);
          }
        });
      });
      vm.dataSelectedItemList(_.orderBy(vm.dataSelectedItemList(), ["indicatesNumber"], ["asc"]))
      _.each(list(), (item: any) => {
        vm.diligenceData().filter((items: any) => {
          if (items.id === parseInt(item.id)) {
            vm.diligenceData.remove(items);
          }
        });
      });

    }

    // event when change item combo box
    private onChangeItemCombo(codeChange: number) {
      const vm = this;
      if (codeChange === 0) {
        return;
      }
      vm.currentCodeList2([]);
      vm.dataSelectedItemList([]);
      let name: any;
      _.each(vm.attributeObject().attributeList, (itemcb: any) => {
        if (itemcb.attendanceTypeCode === codeChange) {
          name = itemcb.attendanceTypeName;
        }
      });
      const tableDatas = _.orderBy(
        vm.tableDataA6.filter((data) => data.attendanceAtr === vm.filterCode()),
        ['indicatesNumber'],
        ['asc']
      );
      if (tableDatas.length === 0) {
        vm.diligenceData([]);
      } else {
        vm.diligenceData(tableDatas);
        vm.diligenceData(_.orderBy(vm.diligenceData(), ["indicatesNumber"], ["asc"]));
        // vm.currentCodeList.push(vm.diligenceData()[0].id);
      }
    }

    // Event when click +
    public onClickPlus() {
      const vm = this;
      vm.currentCodeList2.removeAll();
      _.each(vm.currentCodeList(), (code: any) => {
        vm.diligenceData().filter((e: DiligenceProject) => {
          if (e.id === parseInt(code)) {
            let data: SelectedTimeList = new SelectedTimeList();
            let dataParam: SelectedTimeListParam = new SelectedTimeListParam();
            data.itemId = e.id;
            data.operator = vm.$i18n("KDL048_8");
            data.name = e.name;
            data.indicatesNumber = e.indicatesNumber;
            vm.dataSelectedItemList.push(data);
            vm.currentCodeList2().push(e.id);
            vm.diligenceData.remove(e);
          }
        });
      });
      vm.dataSelectedItemList(
        _.orderBy(vm.dataSelectedItemList(), ["indicatesNumber"], ["asc"])
      );
    }

    // Event when click -
    public onClickMinus() {
      const vm = this;
      vm.currentCodeList2.removeAll();
      _.each(vm.currentCodeList(), (code: any) => {
        vm.diligenceData().filter((e: DiligenceProject) => {
          if (e.id === parseInt(code)) {
            let data: SelectedTimeList = new SelectedTimeList();
            let dataParam: SelectedTimeListParam = new SelectedTimeListParam();
            data.itemId = e.id;
            data.operator = vm.$i18n("KDL048_9");
            data.name = e.name;
            data.indicatesNumber = e.indicatesNumber;
            vm.dataSelectedItemList.push(data);
            vm.currentCodeList2().push(e.id);
            vm.diligenceData.remove(e);
          }
        });
      });
      vm.dataSelectedItemList(
        _.orderBy(vm.dataSelectedItemList(), ["indicatesNumber"], ["asc"])
      );
    }

    // Event when click <<
    public onClickPrevAll() {
      const vm = this;
      _.each(vm.currentCodeList2(), (code: any) => {
        vm.dataSelectedItemList().filter((e: SelectedTimeList) => {
          if (e.itemId === parseInt(code)) {
            let data: DiligenceProject = new DiligenceProject();
            data.id = e.itemId;
            data.name = e.name;
            data.indicatesNumber = e.indicatesNumber;
            vm.diligenceData.push(data);
            vm.dataSelectedItemList.remove(e);
          }
        });
      });
      vm.diligenceData(_.orderBy(vm.diligenceData(), ["id"], ["asc"]));
    }

    onClickOk() {
      const vm = this;
      if (vm.itemNameLine().displayInputCategory === 2) {
        $("#A3_2").trigger("validate");
      }
      _.defer(() => {
        if (!$("#A3_2").ntsError("hasError") || vm.attendanceRecordName() === "") {
          // ?????????????????????????????? == True???????????????????????????????????? == ??????????????????
          if (vm.itemNameLine().displayFlag && vm.itemNameLine().displayInputCategory === 2) {
            // shared with value of A3_2, A5_2, A9_2_1, A9_2_2
            nts.uk.ui.windows.setShared('attendanceRecordExport', {
              attendanceItemName: vm.attendanceRecordName(),
              layoutCode: vm.objectDisplay.titleLine.layoutCode,
              layoutName:  vm.objectDisplay.titleLine.layoutName,
              columnIndex: vm.objectDisplay.columnIndex,
              position:  vm.objectDisplay.position,
              exportAtr:  vm.objectDisplay.exportAtr,
              attendanceId: vm.dataSelectedItemList(),
              attribute: vm.valueCb()
            });
          }
          // ?????????????????????????????? == False??????????????????
          // ?????????????????????????????? == True???????????????????????????????????? == ??????????????????
          if (!vm.itemNameLine().displayFlag || vm.itemNameLine().displayInputCategory === 1) {
            // shared with value of A5_2, A9_2_1, A9_2_2
            nts.uk.ui.windows.setShared('attendanceRecordExport', {
              attendanceItemName: vm.objectDisplay.itemNameLine.name,
              layoutCode: vm.objectDisplay.titleLine.layoutCode,
              layoutName:  vm.objectDisplay.titleLine.layoutName,
              columnIndex: vm.objectDisplay.columnIndex,
              position:  vm.objectDisplay.position,
              exportAtr:  vm.objectDisplay.exportAtr,
              attendanceId: vm.dataSelectedItemList(),
              attribute: vm.valueCb()
            });
          }
          vm.$window.close();
        }
      });
    }

    closeDialog() {
      const vm = this;
      vm.$window.close();
    }
  }

  export class SelectType {
    static SELECT_BY_SELECTED_CODE = 1;
    static SELECT_ALL = 2;
    static SELECT_FIRST_ITEM = 3;
    static NO_SELECT = 4;
  }

  // Display object mock
  export class Display {
     // ???????????????
     titleLine: TitleLineObject = new TitleLineObject();
     // ????????????
     itemNameLine: ItemNameLineObject = new ItemNameLineObject();
     // ??????
     attribute: Attribute = new Attribute();
     // List<????????????>
     attendanceItems: Array<AttributeOfAttendanceItem> = [];
     // ????????????????????????ID
     selectedTime: number;
     // ?????????????????????
     attendanceIds: Array<SelectedItem>;
     // columnIndex
     columnIndex: number;
     // position
     position: number;
     // exportAtr
     exportAtr: number;
    constructor(init?: Partial<Display>) {
      $.extend(this, init);
    }
  }

  export class SelectedTimeList {
    // ??????ID
    itemId: any | null = null;
    // ?????????
    operator: string | null = null;

    name: string | null = null;

    indicatesNumber: any | null = null;
    constructor(init?: Partial<TitleLineObject>) {
      $.extend(this, init);
    }
  }

  export class SelectedTimeListParam {
    // ??????ID
    itemId: any | null = null;
    // ?????????
    operator: string | null = null;

    constructor(init?: Partial<TitleLineObject>) {
      $.extend(this, init);
    }
  }

  // ???????????????
  export class TitleLineObject {
    // ???????????????
    displayFlag: boolean = false;
    // ?????????????????????
    layoutCode: string | null = null;
    // ???????????????
    layoutName: string | null = null;
    // ????????????
    directText: string | null = null;

    constructor(init?: Partial<TitleLineObject>) {
      $.extend(this, init);
    }
  }

  // ????????????
  export class ItemNameLineObject {
    // ???????????????
    displayFlag: boolean = false;
    // ??????????????????
    displayInputCategory: number = 1;
    // ??????
    name: string | null = null;

    constructor(init?: Partial<ItemNameLineObject>) {
      $.extend(this, init);
    }
  }

  // ??????
  export class AttributeObject {
    // ????????????
    selectionCategory: number = 2;
    // List<??????>
    attributeList: Attribute[] = [];
    // ????????????
    selected: number = 1;

    constructor(init?: Partial<AttributeObject>) {
      $.extend(this, init);
    }
  }

  export class Attribute {
    attendanceTypeCode: number;
    attendanceTypeName: string;

    constructor(init?: Partial<Attribute>) {
      $.extend(this, init);
    }
  }

  export class DiligenceProject {
    // ID
    id: any;
    // ??????
    name: any;
    // ??????
    attendanceAtr: any;
    // ????????????
    indicatesNumber: any;

    constructor(init?: Partial<DiligenceProject>) {
      $.extend(this, init);
    }
  }

  export class AttributeOfAttendanceItem {
    /** ????????????ID */
    attendanceItemId: number;
    /** ?????????????????? */
    attendanceItemName: string;
    /** ????????????????????? */
    attendanceAtr: number;
    /** ?????????????????? */
    masterType: number | null;
    /** ???????????? */
    displayNumbers: number;

    constructor(init?: Partial<AttributeOfAttendanceItem>) {
      $.extend(this, init);
    }
  }
export class SelectedItem {
    action: string;
    code: string;
      constructor(action: string, code: string) {
          this.action = action;
          this.code = code;
      }
  }
}