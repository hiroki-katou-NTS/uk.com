/// <reference path='../../../../lib/nittsu/viewcontext.d.ts' />
module nts.uk.at.view.kdl047.a.screenModel {

  import text = nts.uk.resource.getText;
  import setShared = nts.uk.ui.windows.setShared;
  import getShared = nts.uk.ui.windows.getShared;

  @bean()
  export class ViewModel extends ko.ViewModel {
    // A1_2
    layoutCode: KnockoutObservable<string> = ko.observable('');
    // A1_3
    layoutName: KnockoutObservable<string> = ko.observable('');
    // A1_4
    comment: KnockoutObservable<string> = ko.observable('');
    // A3_2
    attendanceRecordName: KnockoutObservable<string> = ko.observable('');
    // Options of ntsComboBox A5_2
    itemList: KnockoutObservableArray<AttendaceType> = ko.observableArray([]);
    // Value for ntsComboBox A5_2
    selectedCode: KnockoutObservable<number> = ko.observable(0);
    // Value for ntsGridList A6 and ntsSearchBox A5_3
    currentCode: KnockoutObservable<any> = ko.observable('');
    // Datas for ntsGridList A6 and ntsSearchBox A5_3
    tableDatas: KnockoutObservableArray<ItemModel> = ko.observableArray([]);
    tableDatasClone: KnockoutObservableArray<ItemModel> = ko.observableArray([])
    // Columns for ntsGridList A6
    tableColumns: KnockoutObservableArray<NtsGridListColumn> = ko.observableArray([
      { headerText: 'ID', prop: 'id', hidden: true },
      { headerText: text('KDL047_6'), prop: 'code', width: 100 },
      { headerText: text('KDL047_7'), prop: 'name', width: 300 }
    ]);
    objectDisplay: Display = new Display();

    isVisibleA1: KnockoutObservable<boolean> = ko.observable(false);
    isVisibleA2: KnockoutObservable<boolean> = ko.observable(false);
    isEnableTextEditor: KnockoutObservable<boolean> = ko.observable(false);
    isEnableComboBox: KnockoutObservable<boolean> = ko.observable(false);

    created() {
      const vm = this;
      vm.objectDisplay = getShared('attendanceItem');
      // パラメータの【タイトル行】の【表示フラグ】で判断する
      vm.isVisibleA1(vm.objectDisplay.titleLine.displayFlag);
      // パラメータの【項目名行】の【表示フラグ】で判断する
      vm.isVisibleA2(vm.objectDisplay.itemNameLine.displayFlag);
      // パラメータの【項目名行】の【表示入力区分】で判断する
      vm.isEnableTextEditor(vm.objectDisplay.itemNameLine.displayInputCategory !== 1);
      // パラメータの【属性】の【選択区分】で判断する
      vm.isEnableComboBox(vm.objectDisplay.attribute.selectionCategory !== 1);
      // A1_2
      vm.layoutCode(vm.objectDisplay.titleLine.layoutCode);
      // A1_3
      vm.layoutName(vm.objectDisplay.titleLine.layoutName);
      // A1_4
      vm.comment(vm.objectDisplay.titleLine.directText);
      // A3_2
      vm.attendanceRecordName(vm.objectDisplay.itemNameLine.name);
      // Options A5_2
      vm.itemList(vm.objectDisplay.attribute.attributeList);
      // For Table
      let tableDatas = [];

      _.each(vm.objectDisplay.diligenceProjectList, tmp => {
        tableDatas.push(new ItemModel({
          id: tmp.id,
          code: tmp.indicatesNumber,
          name: tmp.name
        }));
      });
      tableDatas = _.orderBy(tableDatas, ['code'], ['asc']);
      vm.tableDatasClone(tableDatas);
      tableDatas.unshift(new ItemModel({
        id: -1,
        code: '',
        name: text('KDL047_10')
      }));
      vm.tableDatas(tableDatas);
      vm.selectedCode.subscribe((codeChanged) => {
        if (codeChanged === 0) {
          return;
        }
        vm.currentCode(-1);
        let name = '';
        vm.itemList().map(item => {
          if (item.attendanceTypeCode === codeChanged) {
            name = item.attendanceTypeName;
          }
        });
        const tableDatas = _.orderBy(
          vm.tableDatasClone().filter(data => data.name.indexOf(name) !== -1),
          ['code'], ['asc']
        );
        tableDatas.unshift(new ItemModel({
          id: -1,
          code: '',
          name: text('KDL047_10')
        }));
        vm.tableDatas(tableDatas);
      });
      // Selected A5_2
      vm.selectedCode(vm.objectDisplay.attribute.selected);
      // Selected item in table
      const currentCode = vm.objectDisplay.selectedTime ? vm.objectDisplay.selectedTime : -1;
      vm.currentCode(currentCode);
    }

    // Event on click A8_1 item
    onClickDecision(): void {
      const vm = this;
      $('#A3_2').trigger('validate');
      _.defer(() => {
        if (!$('#A3_2').ntsError('hasError')) {
          // 項目名行の表示フラグ == True：表示すると表示入力区分 == ２：入力可能
          if (vm.objectDisplay.itemNameLine.displayFlag && vm.objectDisplay.itemNameLine.displayInputCategory === 2) {
            // shared with value of A3_2, A5_2, A6_2_1
            vm.objectDisplay.itemNameLine.name = vm.attendanceRecordName();
            vm.objectDisplay.attribute.selected = vm.selectedCode();
            vm.objectDisplay.selectedTime = vm.currentCode();
            setShared('attendanceRecordExport', vm.objectDisplay);
          }
          // 項目名行の表示フラグ == False：表示しない
          // 項目名行の表示フラグ == True：表示すると表示入力区分 == １：表示のみ
          if (!vm.objectDisplay.itemNameLine.displayFlag || vm.objectDisplay.itemNameLine.displayInputCategory === 1) {
            // shared with value of A5_2, A6_2_1
            vm.objectDisplay.attribute.selected = vm.selectedCode();
            vm.objectDisplay.selectedTime = vm.currentCode() === -1 ? null : vm.currentCode();
            setShared('attendanceRecordExport', vm.objectDisplay);
          }
          vm.$window.close()
        }
      });
    }

    // Event on click A8_2 item
    onClickCancel(): void {
      const vm = this;
      vm.$window.close()
    }

  }

  export class ItemModel {
    id: any;
    code: string;
    name: string;

    constructor(init?: Partial<ItemModel>) {
      $.extend(this, init);
    }
  }

  export class NtsGridListColumn {
    headerText?: string;
    prop?: string;
    width?: number;
    hidden?: boolean
  }

  // Display object mock
  export class Display {
    // タイトル行
    titleLine: TitleLineObject = new TitleLineObject();
    // 項目名行
    itemNameLine: ItemNameLineObject = new ItemNameLineObject();
    // 属性
    attribute: AttributeObject = new AttributeObject();
    // List<勤怠項目>
    diligenceProjectList: DiligenceProject[] = [];
    // 選択済み勤怠項目ID
    selectedTime: number;
  }

  export class TitleLineObject {
    // 表示フラグ
    displayFlag: boolean = false;
    // 出力項目コード
    layoutCode: string | null = null;
    // 出力項目名
    layoutName: string | null = null;
    // コメント
    directText: string | null = null;
  }

  export class ItemNameLineObject {
    // 表示フラグ
    displayFlag: boolean = false;
    // 表示入力区分
    displayInputCategory: number = 1;
    // 名称
    name: string | null = null;
  }

  export class AttributeObject {
    // 選択区分
    selectionCategory: number = 2;
    // List<属性>
    attributeList: AttendaceType[] = [];
    // 選択済み
    selected: number = 1;
  }

  export class AttendaceType {
    attendanceTypeCode: number;
    attendanceTypeName: string;
    constructor(init: Partial<AttendaceType>) {
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
  }
}
