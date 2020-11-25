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
      vm.diligenceData(
        _.orderBy(vm.initParam.diligenceProjectList, ["id"], ["asc"])
      );
      vm.titleLine(vm.initParam.titleLine);
      vm.attributeObject(vm.initParam.attribute);
      vm.itemNameLine(vm.initParam.itemNameLine);
      vm.paramSelectedTimeList(vm.initParam.selectedTimeList);
      vm.attendanceRecordName(vm.initParam.itemNameLine.name);

      //data for table
      vm.tableDataA6 = vm.initParam.diligenceProjectList;

      // Columns of table A6
      vm.columns = ko.observableArray([
        { headerText: 'ID', prop: "id", hidden: true },
        { headerText: vm.$i18n("KDL048_6"), prop: "indicatesNumber", width: 70 },
        { headerText: vm.$i18n("KDL048_7"), prop: "name", width: 200 },
      ]);

      // event when change itemCombo
      vm.valueCb.subscribe(function (codeChange) {
        vm.onChangeItemCombo(codeChange);
      });
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
        vm.tableDataA6.filter((data) => data.name.indexOf(name) != -1),
        ["code"],
        ["asc"]
      );
      if (tableDatas.length === 0) {
        vm.diligenceData([]);
      } else {
        vm.diligenceData(tableDatas);
        vm.diligenceData(_.orderBy(vm.diligenceData(), ["id"], ["asc"]));
        vm.currentCodeList.push(vm.diligenceData()[0].id);
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
      $("#A3_2").trigger("validate");
      _.defer(() => {
        if (!$("#A3_2").ntsError("hasError") ||vm.attendanceRecordName() === "") {
          // 項目名行の表示フラグ == True：表示すると表示入力区分 == ２：入力可能
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
          // 項目名行の表示フラグ == False：表示しない
          // 項目名行の表示フラグ == True：表示すると表示入力区分 == １：表示のみ
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
     // タイトル行
     titleLine: TitleLineObject = new TitleLineObject();
     // 項目名行
     itemNameLine: ItemNameLineObject = new ItemNameLineObject();
     // 属性
     attribute: Attribute = new Attribute();
     // List<勤怠項目>
     attendanceItems: Array<AttributeOfAttendanceItem> = [];
     // 選択済み勤怠項目ID
     selectedTime: number;
     // 加減算する項目
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
    // 項目ID
    itemId: any | null = null;
    // 演算子
    operator: string | null = null;

    name: string | null = null;

    indicatesNumber: any | null = null;
    constructor(init?: Partial<TitleLineObject>) {
      $.extend(this, init);
    }
  }

  export class SelectedTimeListParam {
    // 項目ID
    itemId: any | null = null;
    // 演算子
    operator: string | null = null;

    constructor(init?: Partial<TitleLineObject>) {
      $.extend(this, init);
    }
  }

  // タイトル行
  export class TitleLineObject {
    // 表示フラグ
    displayFlag: boolean = false;
    // 出力項目コード
    layoutCode: string | null = null;
    // 出力項目名
    layoutName: string | null = null;
    // コメント
    directText: string | null = null;

    constructor(init?: Partial<TitleLineObject>) {
      $.extend(this, init);
    }
  }

  // 項目名行
  export class ItemNameLineObject {
    // 表示フラグ
    displayFlag: boolean = false;
    // 表示入力区分
    displayInputCategory: number = 1;
    // 名称
    name: string | null = null;

    constructor(init?: Partial<ItemNameLineObject>) {
      $.extend(this, init);
    }
  }

  // 属性
  export class AttributeObject {
    // 選択区分
    selectionCategory: number = 2;
    // List<属性>
    attributeList: Attribute[] = [];
    // 選択済み
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
    // 名称
    name: any;
    // 属性
    attributes: any;
    // 表示番号
    indicatesNumber: any;

    constructor(init?: Partial<DiligenceProject>) {
      $.extend(this, init);
    }
  }

  export class AttributeOfAttendanceItem {
    /** 勤怠項目ID */
    attendanceItemId: number;
    /** 勤怠項目名称 */
    attendanceItemName: string;
    /** 勤怠項目の属性 */
    attributes: number;
    /** マスタの種類 */
    masterTypes: number | null;
    /** 表示番号 */
    displayNumbers: number;

    constructor(init?: Partial<DiligenceProject>) {
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