/// <reference path='../../../../lib/nittsu/viewcontext.d.ts' />
module nts.uk.at.view.kdl047.a.screenModel {

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
    tableDatasClone: KnockoutObservableArray<ItemModel> = ko.observableArray([]);
    // Columns for ntsGridList A6
    tableColumns: KnockoutObservableArray<NtsGridListColumn> = ko.observableArray([
      { headerText: 'ID', prop: 'id', hidden: true },
      { headerText: this.$i18n('KDL047_6'), prop: 'code', width: 100 },
      { headerText: this.$i18n('KDL047_7'), prop: 'name', width: 300 }
    ]);
    objectDisplay: AttendanceItemShare = new AttendanceItemShare();

    isVisibleA1: KnockoutObservable<boolean> = ko.observable(false);
    isVisibleA2: KnockoutObservable<boolean> = ko.observable(false);
    isEnableTextEditor: KnockoutObservable<boolean> = ko.observable(false);
    isEnableComboBox: KnockoutObservable<boolean> = ko.observable(false);

    filterObject: KnockoutComputed<{attendanceAtr: number, masterType: number}> = ko.computed(() => {
      return this.selectedCode() === 1
        ? {attendanceAtr: 0, masterType: 1}
        : this.selectedCode() === 2
          ? {attendanceAtr: 0, masterType: 2}
          : this.selectedCode() === 3
            ? {attendanceAtr: 6, masterType: null}
            : {attendanceAtr: null, masterType: null};
    });

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
      let tableDatas: any[] = [];

      _.each(vm.objectDisplay.attendanceItems, tmp => {
        tableDatas.push(new ItemModel({
          id: tmp.attendanceItemId,
          code: tmp.displayNumbers,
          name: tmp.attendanceItemName,
          attendanceAtr: tmp.attendanceAtr,
          masterType: tmp.masterType
        }));
      });
      tableDatas = _.orderBy(tableDatas, ['code'], ['asc']);
      vm.tableDatasClone(tableDatas);
      tableDatas.unshift(new ItemModel({
        id: -1,
        code: '',
        name: vm.$i18n('KDL047_10')
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
          vm.tableDatasClone().filter(data => {
            const attendanceAtr = vm.filterObject().attendanceAtr;
            const masterType = vm.filterObject().masterType;
            if (attendanceAtr === 6) {
              return data.attendanceAtr === attendanceAtr;
            }
            return data.attendanceAtr === attendanceAtr && data.masterType === masterType;
          }),
          ['code'], ['asc']
        );
        tableDatas.unshift(new ItemModel({
          id: -1,
          code: '',
          name: this.$i18n('KDL047_10')
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
          let attendanceRecord: AttendanceRecordExport = new AttendanceRecordExport();

          // 項目名行の表示フラグ == True：表示すると表示入力区分 == ２：入力可能
          if (vm.objectDisplay.itemNameLine.displayFlag && vm.objectDisplay.itemNameLine.displayInputCategory === 2) {

            attendanceRecord = new AttendanceRecordExport({
              attendanceItemName: vm.attendanceRecordName(),
              layoutCode: vm.objectDisplay.titleLine.layoutCode,
              layoutName: vm.objectDisplay.titleLine.layoutName,
              columnIndex: vm.objectDisplay.columnIndex,
              position: vm.objectDisplay.position,
              exportAtr: vm.objectDisplay.exportAtr,
              attendanceId: vm.currentCode() === -1 ? null : vm.currentCode(),
              attribute: vm.selectedCode()
            });

          }
          // 項目名行の表示フラグ == False：表示しない
          // 項目名行の表示フラグ == True：表示すると表示入力区分 == １：表示のみ
          if (!vm.objectDisplay.itemNameLine.displayFlag || vm.objectDisplay.itemNameLine.displayInputCategory === 1) {

            attendanceRecord = new AttendanceRecordExport({
              attendanceItemName: vm.objectDisplay.itemNameLine.name,
              layoutCode: vm.objectDisplay.titleLine.layoutCode,
              layoutName: vm.objectDisplay.titleLine.layoutName,
              columnIndex: vm.objectDisplay.columnIndex,
              position: vm.objectDisplay.position,
              exportAtr: vm.objectDisplay.exportAtr,
              attendanceId: vm.currentCode() === -1 ? null : vm.currentCode(),
              attribute: vm.selectedCode()
            });

          }

          setShared('attendanceRecordExport', attendanceRecord);
          vm.$window.close();
        }
      });

    }

    // Event on click A8_2 item
    onClickCancel(): void {
      const vm = this;
      vm.$window.close();
    }

  }

  export class ItemModel {
    id: any;
    code: any;
    name: string;
    attendanceAtr: number;
    masterType: number;

    constructor(init?: Partial<ItemModel>) {
      $.extend(this, init);
    }
  }

  export class AttendanceRecordExport {
    attendanceItemName: string;
    layoutCode: string;
    layoutName: string;
    columnIndex: number;
    position: number;
    exportAtr: number;
    attendanceId: number;
    attribute: number;

    constructor(init?: Partial<AttendanceRecordExport>) {
      $.extend(this, init);
    }
  }

  export class NtsGridListColumn {
    headerText?: string;
    prop?: string;
    width?: number;
    hidden?: boolean;
  }

  export class AttendanceItemShare {
    // タイトル行
    titleLine: TitleLine;
    // 項目名行
    itemNameLine: ItemNameLine;
    // 属性
    attribute: Attribute;
    // List<勤怠項目>
    attendanceItems: Array<AttributeOfAttendanceItem>;
    // 選択済み勤怠項目ID
    selectedTime: number;
    // 加減算する項目
    attendanceIds: Array<any>;
    // columnIndex
    columnIndex: number;
    // position
    position: number;
    // exportAtr
    exportAtr: number;

    constructor(init?: Partial<AttendanceItemShare>) {
        $.extend(this, init);
    }
  }

  export class TitleLine {
    // 表示フラグ
    displayFlag: boolean = false;
    // 出力項目コード
    layoutCode: string;
    // 出力項目名
    layoutName: string;
    // コメント
    directText: string;
  }

  export class ItemNameLine {
    // 表示フラグ
    displayFlag: boolean;
    // 表示入力区分
    displayInputCategory: number;
    // 名称
    name: string;
  }

  export class Attribute {
    // 選択区分
    selectionCategory: number;
    // List<属性>
    attributeList: Array<AttendaceType>;
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

  export class AttributeOfAttendanceItem {
    /** 勤怠項目ID */
    attendanceItemId: number;
    /** 勤怠項目名称 */
    attendanceItemName: string;
    /** 勤怠項目の属性 */
    attendanceAtr: number;
    /** マスタの種類 */
    masterType: number | null;
    /** 表示番号 */
    displayNumbers: number;
  }
}
